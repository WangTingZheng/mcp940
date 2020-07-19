package net.minecraft.client.resources;

import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiListExtended;
import net.minecraft.client.gui.GuiScreenResourcePacks;
import net.minecraft.client.gui.GuiYesNo;
import net.minecraft.client.gui.GuiYesNoCallback;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;

public abstract class ResourcePackListEntry implements GuiListExtended.IGuiListEntry
{
    private static final ResourceLocation RESOURCE_PACKS_TEXTURE = new ResourceLocation("textures/gui/resource_packs.png");
    private static final ITextComponent INCOMPATIBLE = new TextComponentTranslation("resourcePack.incompatible", new Object[0]);
    private static final ITextComponent INCOMPATIBLE_OLD = new TextComponentTranslation("resourcePack.incompatible.old", new Object[0]);
    private static final ITextComponent INCOMPATIBLE_NEW = new TextComponentTranslation("resourcePack.incompatible.new", new Object[0]);
    protected final Minecraft mc;
    protected final GuiScreenResourcePacks resourcePacksGUI;

    public ResourcePackListEntry(GuiScreenResourcePacks resourcePacksGUIIn)
    {
        this.resourcePacksGUI = resourcePacksGUIIn;
        this.mc = Minecraft.getMinecraft();
    }

    public void drawEntry(int slotIndex, int x, int y, int listWidth, int slotHeight, int mouseX, int mouseY, boolean isSelected, float partialTicks)
    {
        int i = this.getResourcePackFormat();

        if (i != 3)
        {
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            Gui.drawRect(x - 1, y - 1, x + listWidth - 9, y + slotHeight + 1, -8978432);
        }

        this.bindResourcePackIcon();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        Gui.drawModalRectWithCustomSizedTexture(x, y, 0.0F, 0.0F, 32, 32, 32.0F, 32.0F);
        String s = this.getResourcePackName();
        String s1 = this.getResourcePackDescription();

        if (this.showHoverOverlay() && (this.mc.gameSettings.touchscreen || isSelected))
        {
            this.mc.getTextureManager().bindTexture(RESOURCE_PACKS_TEXTURE);
            Gui.drawRect(x, y, x + 32, y + 32, -1601138544);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            int j = mouseX - x;
            int k = mouseY - y;

            if (i < 3)
            {
                s = INCOMPATIBLE.getFormattedText();
                s1 = INCOMPATIBLE_OLD.getFormattedText();
            }
            else if (i > 3)
            {
                s = INCOMPATIBLE.getFormattedText();
                s1 = INCOMPATIBLE_NEW.getFormattedText();
            }

            if (this.canMoveRight())
            {
                if (j < 32)
                {
                    Gui.drawModalRectWithCustomSizedTexture(x, y, 0.0F, 32.0F, 32, 32, 256.0F, 256.0F);
                }
                else
                {
                    Gui.drawModalRectWithCustomSizedTexture(x, y, 0.0F, 0.0F, 32, 32, 256.0F, 256.0F);
                }
            }
            else
            {
                if (this.canMoveLeft())
                {
                    if (j < 16)
                    {
                        Gui.drawModalRectWithCustomSizedTexture(x, y, 32.0F, 32.0F, 32, 32, 256.0F, 256.0F);
                    }
                    else
                    {
                        Gui.drawModalRectWithCustomSizedTexture(x, y, 32.0F, 0.0F, 32, 32, 256.0F, 256.0F);
                    }
                }

                if (this.canMoveUp())
                {
                    if (j < 32 && j > 16 && k < 16)
                    {
                        Gui.drawModalRectWithCustomSizedTexture(x, y, 96.0F, 32.0F, 32, 32, 256.0F, 256.0F);
                    }
                    else
                    {
                        Gui.drawModalRectWithCustomSizedTexture(x, y, 96.0F, 0.0F, 32, 32, 256.0F, 256.0F);
                    }
                }

                if (this.canMoveDown())
                {
                    if (j < 32 && j > 16 && k > 16)
                    {
                        Gui.drawModalRectWithCustomSizedTexture(x, y, 64.0F, 32.0F, 32, 32, 256.0F, 256.0F);
                    }
                    else
                    {
                        Gui.drawModalRectWithCustomSizedTexture(x, y, 64.0F, 0.0F, 32, 32, 256.0F, 256.0F);
                    }
                }
            }
        }

        int i1 = this.mc.fontRenderer.getStringWidth(s);

        if (i1 > 157)
        {
            s = this.mc.fontRenderer.trimStringToWidth(s, 157 - this.mc.fontRenderer.getStringWidth("...")) + "...";
        }

        this.mc.fontRenderer.drawStringWithShadow(s, (float)(x + 32 + 2), (float)(y + 1), 16777215);
        List<String> list = this.mc.fontRenderer.listFormattedStringToWidth(s1, 157);

        for (int l = 0; l < 2 && l < list.size(); ++l)
        {
            this.mc.fontRenderer.drawStringWithShadow(list.get(l), (float)(x + 32 + 2), (float)(y + 12 + 10 * l), 8421504);
        }
    }

    protected abstract int getResourcePackFormat();

    protected abstract String getResourcePackDescription();

    protected abstract String getResourcePackName();

    protected abstract void bindResourcePackIcon();

    protected boolean showHoverOverlay()
    {
        return true;
    }

    protected boolean canMoveRight()
    {
        return !this.resourcePacksGUI.hasResourcePackEntry(this);
    }

    protected boolean canMoveLeft()
    {
        return this.resourcePacksGUI.hasResourcePackEntry(this);
    }

    protected boolean canMoveUp()
    {
        List<ResourcePackListEntry> list = this.resourcePacksGUI.getListContaining(this);
        int i = list.indexOf(this);
        return i > 0 && ((ResourcePackListEntry)list.get(i - 1)).showHoverOverlay();
    }

    protected boolean canMoveDown()
    {
        List<ResourcePackListEntry> list = this.resourcePacksGUI.getListContaining(this);
        int i = list.indexOf(this);
        return i >= 0 && i < list.size() - 1 && ((ResourcePackListEntry)list.get(i + 1)).showHoverOverlay();
    }

    /**
     * Called when the mouse is clicked within this entry. Returning true means that something within this entry was
     * clicked and the list should not be dragged.
     */
    public boolean mousePressed(int slotIndex, int mouseX, int mouseY, int mouseEvent, int relativeX, int relativeY)
    {
        if (this.showHoverOverlay() && relativeX <= 32)
        {
            if (this.canMoveRight())
            {
                this.resourcePacksGUI.markChanged();
                final int j = ((ResourcePackListEntry)this.resourcePacksGUI.getSelectedResourcePacks().get(0)).isServerPack() ? 1 : 0;
                int l = this.getResourcePackFormat();

                if (l == 3)
                {
                    this.resourcePacksGUI.getListContaining(this).remove(this);
                    this.resourcePacksGUI.getSelectedResourcePacks().add(j, this);
                }
                else
                {
                    String s = I18n.format("resourcePack.incompatible.confirm.title");
                    String s1 = I18n.format("resourcePack.incompatible.confirm." + (l > 3 ? "new" : "old"));
                    this.mc.displayGuiScreen(new GuiYesNo(new GuiYesNoCallback()
                    {
                        public void confirmClicked(boolean result, int id)
                        {
                            List<ResourcePackListEntry> list2 = ResourcePackListEntry.this.resourcePacksGUI.getListContaining(ResourcePackListEntry.this);
                            ResourcePackListEntry.this.mc.displayGuiScreen(ResourcePackListEntry.this.resourcePacksGUI);

                            if (result)
                            {
                                list2.remove(ResourcePackListEntry.this);
                                ResourcePackListEntry.this.resourcePacksGUI.getSelectedResourcePacks().add(j, ResourcePackListEntry.this);
                            }
                        }
                    }, s, s1, 0));
                }

                return true;
            }

            if (relativeX < 16 && this.canMoveLeft())
            {
                this.resourcePacksGUI.getListContaining(this).remove(this);
                this.resourcePacksGUI.getAvailableResourcePacks().add(0, this);
                this.resourcePacksGUI.markChanged();
                return true;
            }

            if (relativeX > 16 && relativeY < 16 && this.canMoveUp())
            {
                List<ResourcePackListEntry> list1 = this.resourcePacksGUI.getListContaining(this);
                int k = list1.indexOf(this);
                list1.remove(this);
                list1.add(k - 1, this);
                this.resourcePacksGUI.markChanged();
                return true;
            }

            if (relativeX > 16 && relativeY > 16 && this.canMoveDown())
            {
                List<ResourcePackListEntry> list = this.resourcePacksGUI.getListContaining(this);
                int i = list.indexOf(this);
                list.remove(this);
                list.add(i + 1, this);
                this.resourcePacksGUI.markChanged();
                return true;
            }
        }

        return false;
    }

    public void updatePosition(int p_192633_1_, int p_192633_2_, int p_192633_3_, float p_192633_4_)
    {
    }

    /**
     * Fired when the mouse button is released. Arguments: index, x, y, mouseEvent, relativeX, relativeY
     */
    public void mouseReleased(int slotIndex, int x, int y, int mouseEvent, int relativeX, int relativeY)
    {
    }

    public boolean isServerPack()
    {
        return false;
    }
}
