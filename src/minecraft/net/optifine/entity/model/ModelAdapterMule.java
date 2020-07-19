package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderAbstractHorse;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.passive.EntityMule;

public class ModelAdapterMule extends ModelAdapterHorse
{
    public ModelAdapterMule()
    {
        super(EntityMule.class, "mule", 0.75F);
    }

    public IEntityRenderer makeEntityRender(ModelBase modelBase, float shadowSize)
    {
        RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
        RenderAbstractHorse renderabstracthorse = new RenderAbstractHorse(rendermanager);
        renderabstracthorse.mainModel = modelBase;
        renderabstracthorse.shadowSize = shadowSize;
        return renderabstracthorse;
    }
}
