package net.minecraft.realms;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiTextField;

public class RealmsEditBox
{
    private final GuiTextField editBox;

    public RealmsEditBox(int id, int x, int y, int width, int height)
    {
        this.editBox = new GuiTextField(id, Minecraft.getMinecraft().fontRenderer, x, y, width, height);
    }

    public String getValue()
    {
        return this.editBox.getText();
    }

    public void tick()
    {
        this.editBox.updateCursorCounter();
    }

    public void setFocus(boolean p_setFocus_1_)
    {
        this.editBox.setFocused(p_setFocus_1_);
    }

    public void setValue(String p_setValue_1_)
    {
        this.editBox.setText(p_setValue_1_);
    }

    public void keyPressed(char p_keyPressed_1_, int p_keyPressed_2_)
    {
        this.editBox.textboxKeyTyped(p_keyPressed_1_, p_keyPressed_2_);
    }

    public boolean isFocused()
    {
        return this.editBox.isFocused();
    }

    public void mouseClicked(int p_mouseClicked_1_, int p_mouseClicked_2_, int p_mouseClicked_3_)
    {
        this.editBox.mouseClicked(p_mouseClicked_1_, p_mouseClicked_2_, p_mouseClicked_3_);
    }

    public void render()
    {
        this.editBox.drawTextBox();
    }

    public void setMaxLength(int p_setMaxLength_1_)
    {
        this.editBox.setMaxStringLength(p_setMaxLength_1_);
    }

    public void setIsEditable(boolean p_setIsEditable_1_)
    {
        this.editBox.setEnabled(p_setIsEditable_1_);
    }
}
