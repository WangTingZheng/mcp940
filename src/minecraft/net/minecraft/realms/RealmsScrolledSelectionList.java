package net.minecraft.realms;

import net.minecraft.client.gui.GuiSlotRealmsProxy;

public class RealmsScrolledSelectionList
{
    private final GuiSlotRealmsProxy proxy;

    public RealmsScrolledSelectionList(int width, int height, int top, int bottom, int slotHeight)
    {
        this.proxy = new GuiSlotRealmsProxy(this, width, height, top, bottom, slotHeight);
    }

    public void render(int p_render_1_, int p_render_2_, float p_render_3_)
    {
        this.proxy.drawScreen(p_render_1_, p_render_2_, p_render_3_);
    }

    public int width()
    {
        return this.proxy.getWidth();
    }

    public int ym()
    {
        return this.proxy.getMouseY();
    }

    public int xm()
    {
        return this.proxy.getMouseX();
    }

    protected void renderItem(int p_renderItem_1_, int p_renderItem_2_, int p_renderItem_3_, int p_renderItem_4_, Tezzelator p_renderItem_5_, int p_renderItem_6_, int p_renderItem_7_)
    {
    }

    public void renderItem(int p_renderItem_1_, int p_renderItem_2_, int p_renderItem_3_, int p_renderItem_4_, int p_renderItem_5_, int p_renderItem_6_)
    {
        this.renderItem(p_renderItem_1_, p_renderItem_2_, p_renderItem_3_, p_renderItem_4_, Tezzelator.instance, p_renderItem_5_, p_renderItem_6_);
    }

    public int getItemCount()
    {
        return 0;
    }

    public void selectItem(int p_selectItem_1_, boolean p_selectItem_2_, int p_selectItem_3_, int p_selectItem_4_)
    {
    }

    public boolean isSelectedItem(int p_isSelectedItem_1_)
    {
        return false;
    }

    public void renderBackground()
    {
    }

    public int getMaxPosition()
    {
        return 0;
    }

    public int getScrollbarPosition()
    {
        return this.proxy.getWidth() / 2 + 124;
    }

    public void mouseEvent()
    {
        this.proxy.handleMouseInput();
    }

    public void scroll(int p_scroll_1_)
    {
        this.proxy.scrollBy(p_scroll_1_);
    }

    public int getScroll()
    {
        return this.proxy.getAmountScrolled();
    }

    protected void renderList(int p_renderList_1_, int p_renderList_2_, int p_renderList_3_, int p_renderList_4_)
    {
    }
}
