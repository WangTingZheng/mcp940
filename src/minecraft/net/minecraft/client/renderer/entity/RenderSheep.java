package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelSheep2;
import net.minecraft.client.renderer.entity.layers.LayerSheepWool;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.util.ResourceLocation;

public class RenderSheep extends RenderLiving<EntitySheep>
{
    private static final ResourceLocation SHEARED_SHEEP_TEXTURES = new ResourceLocation("textures/entity/sheep/sheep.png");

    public RenderSheep(RenderManager p_i47195_1_)
    {
        super(p_i47195_1_, new ModelSheep2(), 0.7F);
        this.addLayer(new LayerSheepWool(this));
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(EntitySheep entity)
    {
        return SHEARED_SHEEP_TEXTURES;
    }
}
