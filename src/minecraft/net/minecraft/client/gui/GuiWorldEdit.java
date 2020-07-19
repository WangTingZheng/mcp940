package net.minecraft.client.gui;

import java.io.IOException;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.world.storage.ISaveFormat;
import net.minecraft.world.storage.WorldInfo;
import org.apache.commons.io.FileUtils;
import org.lwjgl.input.Keyboard;

public class GuiWorldEdit extends GuiScreen
{
    private final GuiScreen lastScreen;
    private GuiTextField nameEdit;
    private final String worldId;

    public GuiWorldEdit(GuiScreen p_i46593_1_, String p_i46593_2_)
    {
        this.lastScreen = p_i46593_1_;
        this.worldId = p_i46593_2_;
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
        this.nameEdit.updateCursorCounter();
    }

    /**
     * Adds the buttons (and other controls) to the screen in question. Called when the GUI is displayed and when the
     * window resizes, the buttonList is cleared beforehand.
     */
    public void initGui()
    {
        Keyboard.enableRepeatEvents(true);
        this.buttonList.clear();
        GuiButton guibutton = this.addButton(new GuiButton(3, this.width / 2 - 100, this.height / 4 + 24 + 12, I18n.format("selectWorld.edit.resetIcon")));
        this.buttonList.add(new GuiButton(4, this.width / 2 - 100, this.height / 4 + 48 + 12, I18n.format("selectWorld.edit.openFolder")));
        this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 96 + 12, I18n.format("selectWorld.edit.save")));
        this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 120 + 12, I18n.format("gui.cancel")));
        guibutton.enabled = this.mc.getSaveLoader().getFile(this.worldId, "icon.png").isFile();
        ISaveFormat isaveformat = this.mc.getSaveLoader();
        WorldInfo worldinfo = isaveformat.getWorldInfo(this.worldId);
        String s = worldinfo == null ? "" : worldinfo.getWorldName();
        this.nameEdit = new GuiTextField(2, this.fontRenderer, this.width / 2 - 100, 60, 200, 20);
        this.nameEdit.setFocused(true);
        this.nameEdit.setText(s);
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
                this.mc.displayGuiScreen(this.lastScreen);
            }
            else if (button.id == 0)
            {
                ISaveFormat isaveformat = this.mc.getSaveLoader();
                isaveformat.renameWorld(this.worldId, this.nameEdit.getText().trim());
                this.mc.displayGuiScreen(this.lastScreen);
            }
            else if (button.id == 3)
            {
                ISaveFormat isaveformat1 = this.mc.getSaveLoader();
                FileUtils.deleteQuietly(isaveformat1.getFile(this.worldId, "icon.png"));
                button.enabled = false;
            }
            else if (button.id == 4)
            {
                ISaveFormat isaveformat2 = this.mc.getSaveLoader();
                OpenGlHelper.openFile(isaveformat2.getFile(this.worldId, "icon.png").getParentFile());
            }
        }
    }

    /**
     * Fired when a key is typed (except F11 which toggles full screen). This is the equivalent of
     * KeyListener.keyTyped(KeyEvent e). Args : character (character on the key), keyCode (lwjgl Keyboard key code)
     */
    protected void keyTyped(char typedChar, int keyCode) throws IOException
    {
        this.nameEdit.textboxKeyTyped(typedChar, keyCode);
        (this.buttonList.get(2)).enabled = !this.nameEdit.getText().trim().isEmpty();

        if (keyCode == 28 || keyCode == 156)
        {
            this.actionPerformed(this.buttonList.get(2));
        }
    }

    /**
     * Called when the mouse is clicked. Args : mouseX, mouseY, clickedButton
     */
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
    {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        this.nameEdit.mouseClicked(mouseX, mouseY, mouseButton);
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRenderer, I18n.format("selectWorld.edit.title"), this.width / 2, 20, 16777215);
        this.drawString(this.fontRenderer, I18n.format("selectWorld.enterName"), this.width / 2 - 100, 47, 10526880);
        this.nameEdit.drawTextBox();
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
}
