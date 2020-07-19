package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelOcelot;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.util.ResourceLocation;

public class RenderOcelot extends RenderLiving<EntityOcelot>
{
    private static final ResourceLocation BLACK_OCELOT_TEXTURES = new ResourceLocation("textures/entity/cat/black.png");
    private static final ResourceLocation OCELOT_TEXTURES = new ResourceLocation("textures/entity/cat/ocelot.png");
    private static final ResourceLocation RED_OCELOT_TEXTURES = new ResourceLocation("textures/entity/cat/red.png");
    private static final ResourceLocation SIAMESE_OCELOT_TEXTURES = new ResourceLocation("textures/entity/cat/siamese.png");

    public RenderOcelot(RenderManager p_i47199_1_)
    {
        super(p_i47199_1_, new ModelOcelot(), 0.4F);
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(EntityOcelot entity)
    {
        switch (entity.getTameSkin())
        {
            case 0:
            default:
                return OCELOT_TEXTURES;

            case 1:
                return BLACK_OCELOT_TEXTURES;

            case 2:
                return RED_OCELOT_TEXTURES;

            case 3:
                return SIAMESE_OCELOT_TEXTURES;
        }
    }

    /**
     * Allows the render to do state modifications necessary before the model is rendered.
     */
    protected void preRenderCallback(EntityOcelot entitylivingbaseIn, float partialTickTime)
    {
        super.preRenderCallback(entitylivingbaseIn, partialTickTime);

        if (entitylivingbaseIn.isTamed())
        {
            GlStateManager.scale(0.8F, 0.8F, 0.8F);
        }
    }
}
