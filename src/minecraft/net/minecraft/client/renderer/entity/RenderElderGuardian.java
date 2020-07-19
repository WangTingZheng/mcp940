package net.minecraft.client.renderer.entity;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.monster.EntityGuardian;
import net.minecraft.util.ResourceLocation;

public class RenderElderGuardian extends RenderGuardian
{
    private static final ResourceLocation GUARDIAN_ELDER_TEXTURE = new ResourceLocation("textures/entity/guardian_elder.png");

    public RenderElderGuardian(RenderManager p_i47209_1_)
    {
        super(p_i47209_1_);
    }

    /**
     * Allows the render to do state modifications necessary before the model is rendered.
     */
    protected void preRenderCallback(EntityGuardian entitylivingbaseIn, float partialTickTime)
    {
        GlStateManager.scale(2.35F, 2.35F, 2.35F);
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(EntityGuardian entity)
    {
        return GUARDIAN_ELDER_TEXTURE;
    }
}
