package net.minecraft.client.gui;

import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.resources.ResourcePackListEntry;

public class GuiResourcePackSelected extends GuiResourcePackList
{
    public GuiResourcePackSelected(Minecraft mcIn, int p_i45056_2_, int p_i45056_3_, List<ResourcePackListEntry> p_i45056_4_)
    {
        super(mcIn, p_i45056_2_, p_i45056_3_, p_i45056_4_);
    }

    protected String getListHeader()
    {
        return I18n.format("resourcePack.selected.title");
    }
}
