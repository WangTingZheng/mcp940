package net.minecraft.realms;

import net.minecraft.client.gui.GuiClickableScrolledSelectionListProxy;

public class RealmsClickableScrolledSelectionList
{
    private final GuiClickableScrolledSelectionListProxy proxy;

    public RealmsClickableScrolledSelectionList(int p_i46052_1_, int p_i46052_2_, int p_i46052_3_, int p_i46052_4_, int p_i46052_5_)
    {
        this.proxy = new GuiClickableScrolledSelectionListProxy(this, p_i46052_1_, p_i46052_2_, p_i46052_3_, p_i46052_4_, p_i46052_5_);
    }

    public void render(int p_render_1_, int p_render_2_, float p_render_3_)
    {
        this.proxy.drawScreen(p_render_1_, p_render_2_, p_render_3_);
    }

    public int width()
    {
        return this.proxy.width();
    }

    public int ym()
    {
        return this.proxy.mouseY();
    }

    public int xm()
    {
        return this.proxy.mouseX();
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
        return this.proxy.width() / 2 + 124;
    }

    public void mouseEvent()
    {
        this.proxy.handleMouseInput();
    }

    public void customMouseEvent(int p_customMouseEvent_1_, int p_customMouseEvent_2_, int p_customMouseEvent_3_, float p_customMouseEvent_4_, int p_customMouseEvent_5_)
    {
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

    public void itemClicked(int p_itemClicked_1_, int p_itemClicked_2_, int p_itemClicked_3_, int p_itemClicked_4_, int p_itemClicked_5_)
    {
    }

    public void renderSelected(int p_renderSelected_1_, int p_renderSelected_2_, int p_renderSelected_3_, Tezzelator p_renderSelected_4_)
    {
    }

    public void setLeftPos(int p_setLeftPos_1_)
    {
        this.proxy.setSlotXBoundsFromLeft(p_setLeftPos_1_);
    }
}
