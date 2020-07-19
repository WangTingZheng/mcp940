package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelSkeleton;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderStray;
import net.minecraft.entity.monster.EntityStray;

public class ModelAdapterStray extends ModelAdapterBiped
{
    public ModelAdapterStray()
    {
        super(EntityStray.class, "stray", 0.7F);
    }

    public ModelBase makeModel()
    {
        return new ModelSkeleton();
    }

    public IEntityRenderer makeEntityRender(ModelBase modelBase, float shadowSize)
    {
        RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
        RenderStray renderstray = new RenderStray(rendermanager);
        renderstray.mainModel = modelBase;
        renderstray.shadowSize = shadowSize;
        return renderstray;
    }
}
