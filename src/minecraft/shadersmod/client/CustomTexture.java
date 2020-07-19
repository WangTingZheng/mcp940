package shadersmod.client;

import net.minecraft.client.renderer.texture.ITextureObject;

public class CustomTexture
{
    private int textureUnit = -1;
    private String path = null;
    private ITextureObject texture = null;

    public CustomTexture(int textureUnit, String path, ITextureObject texture)
    {
        this.textureUnit = textureUnit;
        this.path = path;
        this.texture = texture;
    }

    public int getTextureUnit()
    {
        return this.textureUnit;
    }

    public String getPath()
    {
        return this.path;
    }

    public ITextureObject getTexture()
    {
        return this.texture;
    }

    public String toString()
    {
        return "textureUnit: " + this.textureUnit + ", path: " + this.path + ", glTextureId: " + this.texture.getGlTextureId();
    }
}
