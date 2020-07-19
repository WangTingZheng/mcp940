package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelLlama;
import net.minecraft.client.renderer.entity.layers.LayerLlamaDecor;
import net.minecraft.entity.passive.EntityLlama;
import net.minecraft.util.ResourceLocation;

public class RenderLlama extends RenderLiving<EntityLlama>
{
    private static final ResourceLocation[] LLAMA_TEXTURES = new ResourceLocation[] {new ResourceLocation("textures/entity/llama/llama_creamy.png"), new ResourceLocation("textures/entity/llama/llama_white.png"), new ResourceLocation("textures/entity/llama/llama_brown.png"), new ResourceLocation("textures/entity/llama/llama_gray.png")};

    public RenderLlama(RenderManager p_i47203_1_)
    {
        super(p_i47203_1_, new ModelLlama(0.0F), 0.7F);
        this.addLayer(new LayerLlamaDecor(this));
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(EntityLlama entity)
    {
        return LLAMA_TEXTURES[entity.getVariant()];
    }
}
