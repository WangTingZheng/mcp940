package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelSheep2;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderSheep;
import net.minecraft.entity.passive.EntitySheep;

public class ModelAdapterSheep extends ModelAdapterQuadruped
{
    public ModelAdapterSheep()
    {
        super(EntitySheep.class, "sheep", 0.7F);
    }

    public ModelBase makeModel()
    {
        return new ModelSheep2();
    }

    public IEntityRenderer makeEntityRender(ModelBase modelBase, float shadowSize)
    {
        RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
        RenderSheep rendersheep = new RenderSheep(rendermanager);
        rendersheep.mainModel = modelBase;
        rendersheep.shadowSize = shadowSize;
        return rendersheep;
    }
}
