package net.minecraft.client.gui;

import io.netty.buffer.Unpooled;
import java.io.IOException;
import javax.annotation.Nullable;
import net.minecraft.client.resources.I18n;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.CPacketCustomPayload;
import net.minecraft.tileentity.CommandBlockBaseLogic;
import net.minecraft.tileentity.TileEntityCommandBlock;
import net.minecraft.util.ITabCompleter;
import net.minecraft.util.TabCompleter;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import org.lwjgl.input.Keyboard;

public class GuiCommandBlock extends GuiScreen implements ITabCompleter
{
    /** Text field containing the command block's command. */
    private GuiTextField commandTextField;
    private GuiTextField previousOutputTextField;
    private final TileEntityCommandBlock commandBlock;

    /** "Done" button for the GUI. */
    private GuiButton doneBtn;
    private GuiButton cancelBtn;
    private GuiButton outputBtn;
    private GuiButton modeBtn;
    private GuiButton conditionalBtn;
    private GuiButton autoExecBtn;
    private boolean trackOutput;
    private TileEntityCommandBlock.Mode commandBlockMode = TileEntityCommandBlock.Mode.REDSTONE;
    private TabCompleter tabCompleter;
    private boolean conditional;
    private boolean automatic;

    public GuiCommandBlock(TileEntityCommandBlock commandBlockIn)
    {
        this.commandBlock = commandBlockIn;
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
        this.commandTextField.updateCursorCounter();
    }

    /**
     * Adds the buttons (and other controls) to the screen in question. Called when the GUI is displayed and when the
     * window resizes, the buttonList is cleared beforehand.
     */
    public void initGui()
    {
        final CommandBlockBaseLogic commandblockbaselogic = this.commandBlock.getCommandBlockLogic();
        Keyboard.enableRepeatEvents(true);
        this.buttonList.clear();
        this.doneBtn = this.addButton(new GuiButton(0, this.width / 2 - 4 - 150, this.height / 4 + 120 + 12, 150, 20, I18n.format("gui.done")));
        this.cancelBtn = this.addButton(new GuiButton(1, this.width / 2 + 4, this.height / 4 + 120 + 12, 150, 20, I18n.format("gui.cancel")));
        this.outputBtn = this.addButton(new GuiButton(4, this.width / 2 + 150 - 20, 135, 20, 20, "O"));
        this.modeBtn = this.addButton(new GuiButton(5, this.width / 2 - 50 - 100 - 4, 165, 100, 20, I18n.format("advMode.mode.sequence")));
        this.conditionalBtn = this.addButton(new GuiButton(6, this.width / 2 - 50, 165, 100, 20, I18n.format("advMode.mode.unconditional")));
        this.autoExecBtn = this.addButton(new GuiButton(7, this.width / 2 + 50 + 4, 165, 100, 20, I18n.format("advMode.mode.redstoneTriggered")));
        this.commandTextField = new GuiTextField(2, this.fontRenderer, this.width / 2 - 150, 50, 300, 20);
        this.commandTextField.setMaxStringLength(32500);
        this.commandTextField.setFocused(true);
        this.previousOutputTextField = new GuiTextField(3, this.fontRenderer, this.width / 2 - 150, 135, 276, 20);
        this.previousOutputTextField.setMaxStringLength(32500);
        this.previousOutputTextField.setEnabled(false);
        this.previousOutputTextField.setText("-");
        this.doneBtn.enabled = false;
        this.outputBtn.enabled = false;
        this.modeBtn.enabled = false;
        this.conditionalBtn.enabled = false;
        this.autoExecBtn.enabled = false;
        this.tabCompleter = new TabCompleter(this.commandTextField, true)
        {
            @Nullable
            public BlockPos getTargetBlockPos()
            {
                return commandblockbaselogic.getPosition();
            }
        };
    }

    public void updateGui()
    {
        CommandBlockBaseLogic commandblockbaselogic = this.commandBlock.getCommandBlockLogic();
        this.commandTextField.setText(commandblockbaselogic.getCommand());
        this.trackOutput = commandblockbaselogic.shouldTrackOutput();
        this.commandBlockMode = this.commandBlock.getMode();
        this.conditional = this.commandBlock.isConditional();
        this.automatic = this.commandBlock.isAuto();
        this.updateCmdOutput();
        this.updateMode();
        this.updateConditional();
        this.updateAutoExec();
        this.doneBtn.enabled = true;
        this.outputBtn.enabled = true;
        this.modeBtn.enabled = true;
        this.conditionalBtn.enabled = true;
        this.autoExecBtn.enabled = true;
    }

    /**
     * Called when the screen is unloaded. Used to disable keyboard repeat events
     */
    public void onGuiClosed()
    {
        Keyboard.enableRepeatEvents(false);
    }

    /**
     * Called by the controls from the buttonList when activated. (Mouse pressed for buttons)
     */
    protected void actionPerformed(GuiButton button) throws IOException
    {
        if (button.enabled)
        {
            CommandBlockBaseLogic commandblockbaselogic = this.commandBlock.getCommandBlockLogic();

            if (button.id == 1)
            {
                commandblockbaselogic.setTrackOutput(this.trackOutput);
                this.mc.displayGuiScreen((GuiScreen)null);
            }
            else if (button.id == 0)
            {
                PacketBuffer packetbuffer = new PacketBuffer(Unpooled.buffer());
                commandblockbaselogic.fillInInfo(packetbuffer);
                packetbuffer.writeString(this.commandTextField.getText());
                packetbuffer.writeBoolean(commandblockbaselogic.shouldTrackOutput());
                packetbuffer.writeString(this.commandBlockMode.name());
                packetbuffer.writeBoolean(this.conditional);
                packetbuffer.writeBoolean(this.automatic);
                this.mc.getConnection().sendPacket(new CPacketCustomPayload("MC|AutoCmd", packetbuffer));

                if (!commandblockbaselogic.shouldTrackOutput())
                {
                    commandblockbaselogic.setLastOutput((ITextComponent)null);
                }

                this.mc.displayGuiScreen((GuiScreen)null);
            }
            else if (button.id == 4)
            {
                commandblockbaselogic.setTrackOutput(!commandblockbaselogic.shouldTrackOutput());
                this.updateCmdOutput();
            }
            else if (button.id == 5)
            {
                this.nextMode();
                this.updateMode();
            }
            else if (button.id == 6)
            {
                this.conditional = !this.conditional;
                this.updateConditional();
            }
            else if (button.id == 7)
            {
                this.automatic = !this.automatic;
                this.updateAutoExec();
            }
        }
    }

    /**
     * Fired when a key is typed (except F11 which toggles full screen). This is the equivalent of
     * KeyListener.keyTyped(KeyEvent e). Args : character (character on the key), keyCode (lwjgl Keyboard key code)
     */
    protected void keyTyped(char typedChar, int keyCode) throws IOException
    {
        this.tabCompleter.resetRequested();

        if (keyCode == 15)
        {
            this.tabCompleter.complete();
        }
        else
        {
            this.tabCompleter.resetDidComplete();
        }

        this.commandTextField.textboxKeyTyped(typedChar, keyCode);
        this.previousOutputTextField.textboxKeyTyped(typedChar, keyCode);

        if (keyCode != 28 && keyCode != 156)
        {
            if (keyCode == 1)
            {
                this.actionPerformed(this.cancelBtn);
            }
        }
        else
        {
            this.actionPerformed(this.doneBtn);
        }
    }

    /**
     * Called when the mouse is clicked. Args : mouseX, mouseY, clickedButton
     */
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
    {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        this.commandTextField.mouseClicked(mouseX, mouseY, mouseButton);
        this.previousOutputTextField.mouseClicked(mouseX, mouseY, mouseButton);
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRenderer, I18n.format("advMode.setCommand"), this.width / 2, 20, 16777215);
        this.drawString(this.fontRenderer, I18n.format("advMode.command"), this.width / 2 - 150, 40, 10526880);
        this.commandTextField.drawTextBox();
        int i = 75;
        int j = 0;
        this.drawString(this.fontRenderer, I18n.format("advMode.nearestPlayer"), this.width / 2 - 140, i + j++ * this.fontRenderer.FONT_HEIGHT, 10526880);
        this.drawString(this.fontRenderer, I18n.format("advMode.randomPlayer"), this.width / 2 - 140, i + j++ * this.fontRenderer.FONT_HEIGHT, 10526880);
        this.drawString(this.fontRenderer, I18n.format("advMode.allPlayers"), this.width / 2 - 140, i + j++ * this.fontRenderer.FONT_HEIGHT, 10526880);
        this.drawString(this.fontRenderer, I18n.format("advMode.allEntities"), this.width / 2 - 140, i + j++ * this.fontRenderer.FONT_HEIGHT, 10526880);
        this.drawString(this.fontRenderer, I18n.format("advMode.self"), this.width / 2 - 140, i + j++ * this.fontRenderer.FONT_HEIGHT, 10526880);

        if (!this.previousOutputTextField.getText().isEmpty())
        {
            i = i + j * this.fontRenderer.FONT_HEIGHT + 1;
            this.drawString(this.fontRenderer, I18n.format("advMode.previousOutput"), this.width / 2 - 150, i + 4, 10526880);
            this.previousOutputTextField.drawTextBox();
        }

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    private void updateCmdOutput()
    {
        CommandBlockBaseLogic commandblockbaselogic = this.commandBlock.getCommandBlockLogic();

        if (commandblockbaselogic.shouldTrackOutput())
        {
            this.outputBtn.displayString = "O";

            if (commandblockbaselogic.getLastOutput() != null)
            {
                this.previousOutputTextField.setText(commandblockbaselogic.getLastOutput().getUnformattedText());
            }
        }
        else
        {
            this.outputBtn.displayString = "X";
            this.previousOutputTextField.setText("-");
        }
    }

    private void updateMode()
    {
        switch (this.commandBlockMode)
        {
            case SEQUENCE:
                this.modeBtn.displayString = I18n.format("advMode.mode.sequence");
                break;

            case AUTO:
                this.modeBtn.displayString = I18n.format("advMode.mode.auto");
                break;

            case REDSTONE:
                this.modeBtn.displayString = I18n.format("advMode.mode.redstone");
        }
    }

    private void nextMode()
    {
        switch (this.commandBlockMode)
        {
            case SEQUENCE:
                this.commandBlockMode = TileEntityCommandBlock.Mode.AUTO;
                break;

            case AUTO:
                this.commandBlockMode = TileEntityCommandBlock.Mode.REDSTONE;
                break;

            case REDSTONE:
                this.commandBlockMode = TileEntityCommandBlock.Mode.SEQUENCE;
        }
    }

    private void updateConditional()
    {
        if (this.conditional)
        {
            this.conditionalBtn.displayString = I18n.format("advMode.mode.conditional");
        }
        else
        {
            this.conditionalBtn.displayString = I18n.format("advMode.mode.unconditional");
        }
    }

    private void updateAutoExec()
    {
        if (this.automatic)
        {
            this.autoExecBtn.displayString = I18n.format("advMode.mode.autoexec.bat");
        }
        else
        {
            this.autoExecBtn.displayString = I18n.format("advMode.mode.redstoneTriggered");
        }
    }

    /**
     * Sets the list of tab completions, as long as they were previously requested.
     */
    public void setCompletions(String... newCompletions)
    {
        this.tabCompleter.setCompletions(newCompletions);
    }
}
