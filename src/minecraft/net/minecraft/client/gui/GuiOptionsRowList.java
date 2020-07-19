package net.minecraft.client.gui;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;

public class GuiOptionsRowList extends GuiListExtended
{
    private final List<GuiOptionsRowList.Row> options = Lists.<GuiOptionsRowList.Row>newArrayList();

    public GuiOptionsRowList(Minecraft mcIn, int p_i45015_2_, int p_i45015_3_, int p_i45015_4_, int p_i45015_5_, int p_i45015_6_, GameSettings.Options... p_i45015_7_)
    {
        super(mcIn, p_i45015_2_, p_i45015_3_, p_i45015_4_, p_i45015_5_, p_i45015_6_);
        this.centerListVertically = false;

        for (int i = 0; i < p_i45015_7_.length; i += 2)
        {
            GameSettings.Options gamesettings$options = p_i45015_7_[i];
            GameSettings.Options gamesettings$options1 = i < p_i45015_7_.length - 1 ? p_i45015_7_[i + 1] : null;
            GuiButton guibutton = this.createButton(mcIn, p_i45015_2_ / 2 - 155, 0, gamesettings$options);
            GuiButton guibutton1 = this.createButton(mcIn, p_i45015_2_ / 2 - 155 + 160, 0, gamesettings$options1);
            this.options.add(new GuiOptionsRowList.Row(guibutton, guibutton1));
        }
    }

    private GuiButton createButton(Minecraft mcIn, int p_148182_2_, int p_148182_3_, GameSettings.Options options)
    {
        if (options == null)
        {
            return null;
        }
        else
        {
            int i = options.returnEnumOrdinal();
            return (GuiButton)(options.getEnumFloat() ? new GuiOptionSlider(i, p_148182_2_, p_148182_3_, options) : new GuiOptionButton(i, p_148182_2_, p_148182_3_, options, mcIn.gameSettings.getKeyBinding(options)));
        }
    }

    /**
     * Gets the IGuiListEntry object for the given index
     */
    public GuiOptionsRowList.Row getListEntry(int index)
    {
        return this.options.get(index);
    }

    protected int getSize()
    {
        return this.options.size();
    }

    /**
     * Gets the width of the list
     */
    public int getListWidth()
    {
        return 400;
    }

    protected int getScrollBarX()
    {
        return super.getScrollBarX() + 32;
    }

    public static class Row implements GuiListExtended.IGuiListEntry
    {
        private final Minecraft client = Minecraft.getMinecraft();
        private final GuiButton buttonA;
        private final GuiButton buttonB;

        public Row(GuiButton buttonAIn, GuiButton buttonBIn)
        {
            this.buttonA = buttonAIn;
            this.buttonB = buttonBIn;
        }

        public void drawEntry(int slotIndex, int x, int y, int listWidth, int slotHeight, int mouseX, int mouseY, boolean isSelected, float partialTicks)
        {
            if (this.buttonA != null)
            {
                this.buttonA.y = y;
                this.buttonA.drawButton(this.client, mouseX, mouseY, partialTicks);
            }

            if (this.buttonB != null)
            {
                this.buttonB.y = y;
                this.buttonB.drawButton(this.client, mouseX, mouseY, partialTicks);
            }
        }

        public boolean mousePressed(int slotIndex, int mouseX, int mouseY, int mouseEvent, int relativeX, int relativeY)
        {
            if (this.buttonA.mousePressed(this.client, mouseX, mouseY))
            {
                if (this.buttonA instanceof GuiOptionButton)
                {
                    this.client.gameSettings.setOptionValue(((GuiOptionButton)this.buttonA).getOption(), 1);
                    this.buttonA.displayString = this.client.gameSettings.getKeyBinding(GameSettings.Options.getEnumOptions(this.buttonA.id));
                }

                return true;
            }
            else if (this.buttonB != null && this.buttonB.mousePressed(this.client, mouseX, mouseY))
            {
                if (this.buttonB instanceof GuiOptionButton)
                {
                    this.client.gameSettings.setOptionValue(((GuiOptionButton)this.buttonB).getOption(), 1);
                    this.buttonB.displayString = this.client.gameSettings.getKeyBinding(GameSettings.Options.getEnumOptions(this.buttonB.id));
                }

                return true;
            }
            else
            {
                return false;
            }
        }

        public void mouseReleased(int slotIndex, int x, int y, int mouseEvent, int relativeX, int relativeY)
        {
            if (this.buttonA != null)
            {
                this.buttonA.mouseReleased(x, y);
            }

            if (this.buttonB != null)
            {
                this.buttonB.mouseReleased(x, y);
            }
        }

        public void updatePosition(int p_192633_1_, int p_192633_2_, int p_192633_3_, float p_192633_4_)
        {
        }
    }
}
