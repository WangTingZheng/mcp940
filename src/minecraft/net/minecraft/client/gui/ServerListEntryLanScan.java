package net.minecraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;

public class ServerListEntryLanScan implements GuiListExtended.IGuiListEntry
{
    private final Minecraft mc = Minecraft.getMinecraft();

    public void drawEntry(int slotIndex, int x, int y, int listWidth, int slotHeight, int mouseX, int mouseY, boolean isSelected, float partialTicks)
    {
        int i = y + slotHeight / 2 - this.mc.fontRenderer.FONT_HEIGHT / 2;
        this.mc.fontRenderer.drawString(I18n.format("lanServer.scanning"), this.mc.currentScreen.width / 2 - this.mc.fontRenderer.getStringWidth(I18n.format("lanServer.scanning")) / 2, i, 16777215);
        String s;

        switch ((int)(Minecraft.getSystemTime() / 300L % 4L))
        {
            case 0:
            default:
                s = "O o o";
                break;

            case 1:
            case 3:
                s = "o O o";
                break;

            case 2:
                s = "o o O";
        }

        this.mc.fontRenderer.drawString(s, this.mc.currentScreen.width / 2 - this.mc.fontRenderer.getStringWidth(s) / 2, i + this.mc.fontRenderer.FONT_HEIGHT, 8421504);
    }

    public void updatePosition(int p_192633_1_, int p_192633_2_, int p_192633_3_, float p_192633_4_)
    {
    }

    /**
     * Called when the mouse is clicked within this entry. Returning true means that something within this entry was
     * clicked and the list should not be dragged.
     */
    public boolean mousePressed(int slotIndex, int mouseX, int mouseY, int mouseEvent, int relativeX, int relativeY)
    {
        return false;
    }

    /**
     * Fired when the mouse button is released. Arguments: index, x, y, mouseEvent, relativeX, relativeY
     */
    public void mouseReleased(int slotIndex, int x, int y, int mouseEvent, int relativeX, int relativeY)
    {
    }
}
