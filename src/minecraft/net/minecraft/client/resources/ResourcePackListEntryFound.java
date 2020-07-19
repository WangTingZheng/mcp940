package net.minecraft.client.resources;

import net.minecraft.client.gui.GuiScreenResourcePacks;

public class ResourcePackListEntryFound extends ResourcePackListEntry
{
    private final ResourcePackRepository.Entry resourcePackEntry;

    public ResourcePackListEntryFound(GuiScreenResourcePacks resourcePacksGUIIn, ResourcePackRepository.Entry entry)
    {
        super(resourcePacksGUIIn);
        this.resourcePackEntry = entry;
    }

    protected void bindResourcePackIcon()
    {
        this.resourcePackEntry.bindTexturePackIcon(this.mc.getTextureManager());
    }

    protected int getResourcePackFormat()
    {
        return this.resourcePackEntry.getPackFormat();
    }

    protected String getResourcePackDescription()
    {
        return this.resourcePackEntry.getTexturePackDescription();
    }

    protected String getResourcePackName()
    {
        return this.resourcePackEntry.getResourcePackName();
    }

    public ResourcePackRepository.Entry getResourcePackEntry()
    {
        return this.resourcePackEntry;
    }
}
