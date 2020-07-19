package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelPolarBear;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderPolarBear;
import net.minecraft.entity.monster.EntityPolarBear;

public class ModelAdapterPolarBear extends ModelAdapterQuadruped
{
    public ModelAdapterPolarBear()
    {
        super(EntityPolarBear.class, "polar_bear", 0.7F);
    }

    public ModelBase makeModel()
    {
        return new ModelPolarBear();
    }

    public IEntityRenderer makeEntityRender(ModelBase modelBase, float shadowSize)
    {
        RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
        RenderPolarBear renderpolarbear = new RenderPolarBear(rendermanager);
        renderpolarbear.mainModel = modelBase;
        renderpolarbear.shadowSize = shadowSize;
        return renderpolarbear;
    }
}
