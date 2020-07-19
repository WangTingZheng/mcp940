package net.minecraft.client.renderer.texture;

import java.awt.image.BufferedImage;
import java.io.IOException;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.src.Config;
import shadersmod.client.ShadersTex;

public class DynamicTexture extends AbstractTexture
{
    private final int[] dynamicTextureData;

    /** width of this icon in pixels */
    private final int width;

    /** height of this icon in pixels */
    private final int height;
    private boolean shadersInitialized;

    public DynamicTexture(BufferedImage bufferedImage)
    {
        this(bufferedImage.getWidth(), bufferedImage.getHeight());
        bufferedImage.getRGB(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight(), this.dynamicTextureData, 0, bufferedImage.getWidth());
        this.updateDynamicTexture();
    }

    public DynamicTexture(int textureWidth, int textureHeight)
    {
        this.shadersInitialized = false;
        this.width = textureWidth;
        this.height = textureHeight;
        this.dynamicTextureData = new int[textureWidth * textureHeight * 3];

        if (Config.isShaders())
        {
            ShadersTex.initDynamicTexture(this.getGlTextureId(), textureWidth, textureHeight, this);
            this.shadersInitialized = true;
        }
        else
        {
            TextureUtil.allocateTexture(this.getGlTextureId(), textureWidth, textureHeight);
        }
    }

    public void loadTexture(IResourceManager resourceManager) throws IOException
    {
    }

    public void updateDynamicTexture()
    {
        if (Config.isShaders())
        {
            if (!this.shadersInitialized)
            {
                ShadersTex.initDynamicTexture(this.getGlTextureId(), this.width, this.height, this);
                this.shadersInitialized = true;
            }

            ShadersTex.updateDynamicTexture(this.getGlTextureId(), this.dynamicTextureData, this.width, this.height, this);
        }
        else
        {
            TextureUtil.uploadTexture(this.getGlTextureId(), this.dynamicTextureData, this.width, this.height);
        }
    }

    public int[] getTextureData()
    {
        return this.dynamicTextureData;
    }
}
