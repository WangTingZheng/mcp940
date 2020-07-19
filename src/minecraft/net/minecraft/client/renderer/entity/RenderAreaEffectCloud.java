package net.minecraft.client.renderer.entity;

import javax.annotation.Nullable;
import net.minecraft.entity.EntityAreaEffectCloud;
import net.minecraft.util.ResourceLocation;

public class RenderAreaEffectCloud extends Render<EntityAreaEffectCloud>
{
    public RenderAreaEffectCloud(RenderManager manager)
    {
        super(manager);
    }

    @Nullable

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(EntityAreaEffectCloud entity)
    {
        return null;
    }
}
