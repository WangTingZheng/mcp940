package net.minecraft.client.resources.data;

import net.minecraft.util.text.ITextComponent;

public class PackMetadataSection implements IMetadataSection
{
    private final ITextComponent packDescription;
    private final int packFormat;

    public PackMetadataSection(ITextComponent packDescriptionIn, int packFormatIn)
    {
        this.packDescription = packDescriptionIn;
        this.packFormat = packFormatIn;
    }

    public ITextComponent getPackDescription()
    {
        return this.packDescription;
    }

    public int getPackFormat()
    {
        return this.packFormat;
    }
}
