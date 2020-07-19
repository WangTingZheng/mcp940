package net.minecraft.src;

import java.io.IOException;
import java.util.List;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiVideoSettings;

public class GuiScreenOF extends GuiScreen
{
    protected void actionPerformedRightClick(GuiButton p_actionPerformedRightClick_1_) throws IOException
    {
    }

    /**
     * Called when the mouse is clicked. Args : mouseX, mouseY, clickedButton
     */
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
    {
        super.mouseClicked(mouseX, mouseY, mouseButton);

        if (mouseButton == 1)
        {
            GuiButton guibutton = getSelectedButton(this.buttonList, mouseX, mouseY);

            if (guibutton != null && guibutton.enabled)
            {
                guibutton.playPressSound(this.mc.getSoundHandler());
                this.actionPerformedRightClick(guibutton);
            }
        }
    }

    public static GuiButton getSelectedButton(List<GuiButton> p_getSelectedButton_0_, int p_getSelectedButton_1_, int p_getSelectedButton_2_)
    {
        for (int i = 0; i < p_getSelectedButton_0_.size(); ++i)
        {
            GuiButton guibutton = p_getSelectedButton_0_.get(i);

            if (guibutton.visible)
            {
                int j = GuiVideoSettings.getButtonWidth(guibutton);
                int k = GuiVideoSettings.getButtonHeight(guibutton);

                if (p_getSelectedButton_1_ >= guibutton.x && p_getSelectedButton_2_ >= guibutton.y && p_getSelectedButton_1_ < guibutton.x + j && p_getSelectedButton_2_ < guibutton.y + k)
                {
                    return guibutton;
                }
            }
        }

        return null;
    }
}
