package net.minecraft.client.resources.data;

public class TextureMetadataSection implements IMetadataSection
{
    private final boolean textureBlur;
    private final boolean textureClamp;

    public TextureMetadataSection(boolean textureBlurIn, boolean textureClampIn)
    {
        this.textureBlur = textureBlurIn;
        this.textureClamp = textureClampIn;
    }

    public boolean getTextureBlur()
    {
        return this.textureBlur;
    }

    public boolean getTextureClamp()
    {
        return this.textureClamp;
    }
}
