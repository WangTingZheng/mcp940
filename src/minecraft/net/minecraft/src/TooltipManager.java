package net.minecraft.src;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiVideoSettings;
import net.minecraft.client.settings.GameSettings;

public class TooltipManager
{
    private GuiScreen guiScreen = null;
    private int lastMouseX = 0;
    private int lastMouseY = 0;
    private long mouseStillTime = 0L;

    public TooltipManager(GuiScreen p_i96_1_)
    {
        this.guiScreen = p_i96_1_;
    }

    public void drawTooltips(int p_drawTooltips_1_, int p_drawTooltips_2_, List p_drawTooltips_3_)
    {
        if (Math.abs(p_drawTooltips_1_ - this.lastMouseX) <= 5 && Math.abs(p_drawTooltips_2_ - this.lastMouseY) <= 5)
        {
            int i = 700;

            if (System.currentTimeMillis() >= this.mouseStillTime + (long)i)
            {
                int j = this.guiScreen.width / 2 - 150;
                int k = this.guiScreen.height / 6 - 7;

                if (p_drawTooltips_2_ <= k + 98)
                {
                    k += 105;
                }

                int l = j + 150 + 150;
                int i1 = k + 84 + 10;
                GuiButton guibutton = this.getSelectedButton(p_drawTooltips_1_, p_drawTooltips_2_, p_drawTooltips_3_);

                if (guibutton instanceof IOptionControl)
                {
                    IOptionControl ioptioncontrol = (IOptionControl)guibutton;
                    GameSettings.Options gamesettings$options = ioptioncontrol.getOption();
                    String[] astring = getTooltipLines(gamesettings$options);

                    if (astring == null)
                    {
                        return;
                    }

                    GuiVideoSettings.drawRect(j, k, l, i1, -536870912);

                    for (int j1 = 0; j1 < astring.length; ++j1)
                    {
                        String s = astring[j1];
                        int k1 = 14540253;

                        if (s.endsWith("!"))
                        {
                            k1 = 16719904;
                        }

                        FontRenderer fontrenderer = Minecraft.getMinecraft().fontRenderer;
                        fontrenderer.drawStringWithShadow(s, (float)(j + 5), (float)(k + 5 + j1 * 11), k1);
                    }
                }
            }
        }
        else
        {
            this.lastMouseX = p_drawTooltips_1_;
            this.lastMouseY = p_drawTooltips_2_;
            this.mouseStillTime = System.currentTimeMillis();
        }
    }

    private GuiButton getSelectedButton(int p_getSelectedButton_1_, int p_getSelectedButton_2_, List p_getSelectedButton_3_)
    {
        for (int i = 0; i < p_getSelectedButton_3_.size(); ++i)
        {
            GuiButton guibutton = (GuiButton)p_getSelectedButton_3_.get(i);
            int j = GuiVideoSettings.getButtonWidth(guibutton);
            int k = GuiVideoSettings.getButtonHeight(guibutton);
            boolean flag = p_getSelectedButton_1_ >= guibutton.x && p_getSelectedButton_2_ >= guibutton.y && p_getSelectedButton_1_ < guibutton.x + j && p_getSelectedButton_2_ < guibutton.y + k;

            if (flag)
            {
                return guibutton;
            }
        }

        return null;
    }

    private static String[] getTooltipLines(GameSettings.Options p_getTooltipLines_0_)
    {
        return getTooltipLines(p_getTooltipLines_0_.getEnumString());
    }

    private static String[] getTooltipLines(String p_getTooltipLines_0_)
    {
        List<String> list = new ArrayList<String>();

        for (int i = 0; i < 10; ++i)
        {
            String s = p_getTooltipLines_0_ + ".tooltip." + (i + 1);
            String s1 = Lang.get(s, (String)null);

            if (s1 == null)
            {
                break;
            }

            list.add(s1);
        }

        if (list.size() <= 0)
        {
            return null;
        }
        else
        {
            String[] astring = (String[])list.toArray(new String[list.size()]);
            return astring;
        }
    }
}
