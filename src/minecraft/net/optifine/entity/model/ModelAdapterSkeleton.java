package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelSkeleton;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderSkeleton;
import net.minecraft.entity.monster.EntitySkeleton;

public class ModelAdapterSkeleton extends ModelAdapterBiped
{
    public ModelAdapterSkeleton()
    {
        super(EntitySkeleton.class, "skeleton", 0.7F);
    }

    public ModelBase makeModel()
    {
        return new ModelSkeleton();
    }

    public IEntityRenderer makeEntityRender(ModelBase modelBase, float shadowSize)
    {
        RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
        RenderSkeleton renderskeleton = new RenderSkeleton(rendermanager);
        renderskeleton.mainModel = modelBase;
        renderskeleton.shadowSize = shadowSize;
        return renderskeleton;
    }
}
