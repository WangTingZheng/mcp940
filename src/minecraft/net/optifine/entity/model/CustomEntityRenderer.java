package net.optifine.entity.model;

import net.minecraft.util.ResourceLocation;

public class CustomEntityRenderer
{
    private String name = null;
    private String basePath = null;
    private ResourceLocation textureLocation = null;
    private CustomModelRenderer[] customModelRenderers = null;
    private float shadowSize = 0.0F;

    public CustomEntityRenderer(String name, String basePath, ResourceLocation textureLocation, CustomModelRenderer[] customModelRenderers, float shadowSize)
    {
        this.name = name;
        this.basePath = basePath;
        this.textureLocation = textureLocation;
        this.customModelRenderers = customModelRenderers;
        this.shadowSize = shadowSize;
    }

    public String getName()
    {
        return this.name;
    }

    public String getBasePath()
    {
        return this.basePath;
    }

    public ResourceLocation getTextureLocation()
    {
        return this.textureLocation;
    }

    public CustomModelRenderer[] getCustomModelRenderers()
    {
        return this.customModelRenderers;
    }

    public float getShadowSize()
    {
        return this.shadowSize;
    }
}
