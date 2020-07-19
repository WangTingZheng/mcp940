package net.minecraft.client.resources.data;

public class FontMetadataSection implements IMetadataSection
{
    private final float[] charWidths;
    private final float[] charLefts;
    private final float[] charSpacings;

    public FontMetadataSection(float[] charWidthsIn, float[] charLeftsIn, float[] charSpacingsIn)
    {
        this.charWidths = charWidthsIn;
        this.charLefts = charLeftsIn;
        this.charSpacings = charSpacingsIn;
    }
}
