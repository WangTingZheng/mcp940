package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelEnderMite;
import net.minecraft.entity.monster.EntityEndermite;
import net.minecraft.util.ResourceLocation;

public class RenderEndermite extends RenderLiving<EntityEndermite>
{
    private static final ResourceLocation ENDERMITE_TEXTURES = new ResourceLocation("textures/entity/endermite.png");

    public RenderEndermite(RenderManager renderManagerIn)
    {
        super(renderManagerIn, new ModelEnderMite(), 0.3F);
    }

    protected float getDeathMaxRotation(EntityEndermite entityLivingBaseIn)
    {
        return 180.0F;
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(EntityEndermite entity)
    {
        return ENDERMITE_TEXTURES;
    }
}
