package net.minecraft.client.gui.inventory;

import io.netty.buffer.Unpooled;
import java.io.IOException;
import javax.annotation.Nullable;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.resources.I18n;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.CPacketCustomPayload;
import net.minecraft.tileentity.CommandBlockBaseLogic;
import net.minecraft.util.ITabCompleter;
import net.minecraft.util.TabCompleter;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import org.lwjgl.input.Keyboard;

public class GuiEditCommandBlockMinecart extends GuiScreen implements ITabCompleter
{
    private GuiTextField commandField;
    private GuiTextField previousEdit;
    private final CommandBlockBaseLogic commandBlockLogic;
    private GuiButton doneButton;
    private GuiButton cancelButton;
    private GuiButton outputButton;
    private boolean trackOutput;
    private TabCompleter tabCompleter;

    public GuiEditCommandBlockMinecart(CommandBlockBaseLogic p_i46595_1_)
    {
        this.commandBlockLogic = p_i46595_1_;
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
        this.commandField.updateCursorCounter();
    }

    /**
     * Adds the buttons (and other controls) to the screen in question. Called when the GUI is displayed and when the
     * window resizes, the buttonList is cleared beforehand.
     */
    public void initGui()
    {
        Keyboard.enableRepeatEvents(true);
        this.buttonList.clear();
        this.doneButton = this.addButton(new GuiButton(0, this.width / 2 - 4 - 150, this.height / 4 + 120 + 12, 150, 20, I18n.format("gui.done")));
        this.cancelButton = this.addButton(new GuiButton(1, this.width / 2 + 4, this.height / 4 + 120 + 12, 150, 20, I18n.format("gui.cancel")));
        this.outputButton = this.addButton(new GuiButton(4, this.width / 2 + 150 - 20, 150, 20, 20, "O"));
        this.commandField = new GuiTextField(2, this.fontRenderer, this.width / 2 - 150, 50, 300, 20);
        this.commandField.setMaxStringLength(32500);
        this.commandField.setFocused(true);
        this.commandField.setText(this.commandBlockLogic.getCommand());
        this.previousEdit = new GuiTextField(3, this.fontRenderer, this.width / 2 - 150, 150, 276, 20);
        this.previousEdit.setMaxStringLength(32500);
        this.previousEdit.setEnabled(false);
        this.previousEdit.setText("-");
        this.trackOutput = this.commandBlockLogic.shouldTrackOutput();
        this.updateCommandOutput();
        this.doneButton.enabled = !this.commandField.getText().trim().isEmpty();
        this.tabCompleter = new TabCompleter(this.commandField, true)
        {
            @Nullable
            public BlockPos getTargetBlockPos()
            {
                return GuiEditCommandBlockMinecart.this.commandBlockLogic.getPosition();
            }
        };
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
            if (button.id == 1)
            {
                this.commandBlockLogic.setTrackOutput(this.trackOutput);
                this.mc.displayGuiScreen((GuiScreen)null);
            }
            else if (button.id == 0)
            {
                PacketBuffer packetbuffer = new PacketBuffer(Unpooled.buffer());
                packetbuffer.writeByte(this.commandBlockLogic.getCommandBlockType());
                this.commandBlockLogic.fillInInfo(packetbuffer);
                packetbuffer.writeString(this.commandField.getText());
                packetbuffer.writeBoolean(this.commandBlockLogic.shouldTrackOutput());
                this.mc.getConnection().sendPacket(new CPacketCustomPayload("MC|AdvCmd", packetbuffer));

                if (!this.commandBlockLogic.shouldTrackOutput())
                {
                    this.commandBlockLogic.setLastOutput((ITextComponent)null);
                }

                this.mc.displayGuiScreen((GuiScreen)null);
            }
            else if (button.id == 4)
            {
                this.commandBlockLogic.setTrackOutput(!this.commandBlockLogic.shouldTrackOutput());
                this.updateCommandOutput();
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

        this.commandField.textboxKeyTyped(typedChar, keyCode);
        this.previousEdit.textboxKeyTyped(typedChar, keyCode);
        this.doneButton.enabled = !this.commandField.getText().trim().isEmpty();

        if (keyCode != 28 && keyCode != 156)
        {
            if (keyCode == 1)
            {
                this.actionPerformed(this.cancelButton);
            }
        }
        else
        {
            this.actionPerformed(this.doneButton);
        }
    }

    /**
     * Called when the mouse is clicked. Args : mouseX, mouseY, clickedButton
     */
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
    {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        this.commandField.mouseClicked(mouseX, mouseY, mouseButton);
        this.previousEdit.mouseClicked(mouseX, mouseY, mouseButton);
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRenderer, I18n.format("advMode.setCommand"), this.width / 2, 20, 16777215);
        this.drawString(this.fontRenderer, I18n.format("advMode.command"), this.width / 2 - 150, 40, 10526880);
        this.commandField.drawTextBox();
        int i = 75;
        int j = 0;
        this.drawString(this.fontRenderer, I18n.format("advMode.nearestPlayer"), this.width / 2 - 140, i + j++ * this.fontRenderer.FONT_HEIGHT, 10526880);
        this.drawString(this.fontRenderer, I18n.format("advMode.randomPlayer"), this.width / 2 - 140, i + j++ * this.fontRenderer.FONT_HEIGHT, 10526880);
        this.drawString(this.fontRenderer, I18n.format("advMode.allPlayers"), this.width / 2 - 140, i + j++ * this.fontRenderer.FONT_HEIGHT, 10526880);
        this.drawString(this.fontRenderer, I18n.format("advMode.allEntities"), this.width / 2 - 140, i + j++ * this.fontRenderer.FONT_HEIGHT, 10526880);
        this.drawString(this.fontRenderer, I18n.format("advMode.self"), this.width / 2 - 140, i + j++ * this.fontRenderer.FONT_HEIGHT, 10526880);

        if (!this.previousEdit.getText().isEmpty())
        {
            i = i + j * this.fontRenderer.FONT_HEIGHT + 20;
            this.drawString(this.fontRenderer, I18n.format("advMode.previousOutput"), this.width / 2 - 150, i, 10526880);
            this.previousEdit.drawTextBox();
        }

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    private void updateCommandOutput()
    {
        if (this.commandBlockLogic.shouldTrackOutput())
        {
            this.outputButton.displayString = "O";

            if (this.commandBlockLogic.getLastOutput() != null)
            {
                this.previousEdit.setText(this.commandBlockLogic.getLastOutput().getUnformattedText());
            }
        }
        else
        {
            this.outputButton.displayString = "X";
            this.previousEdit.setText("-");
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
