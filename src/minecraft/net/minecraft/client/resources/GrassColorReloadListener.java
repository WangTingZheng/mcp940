package net.minecraft.client.resources;

import java.io.IOException;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.ColorizerGrass;

public class GrassColorReloadListener implements IResourceManagerReloadListener
{
    private static final ResourceLocation LOC_GRASS_PNG = new ResourceLocation("textures/colormap/grass.png");

    public void onResourceManagerReload(IResourceManager resourceManager)
    {
        try
        {
            ColorizerGrass.setGrassBiomeColorizer(TextureUtil.readImageData(resourceManager, LOC_GRASS_PNG));
        }
        catch (IOException var3)
        {
            ;
        }
    }
}
