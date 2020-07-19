package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelCow;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderMooshroom;
import net.minecraft.entity.passive.EntityMooshroom;

public class ModelAdapterMooshroom extends ModelAdapterQuadruped
{
    public ModelAdapterMooshroom()
    {
        super(EntityMooshroom.class, "mooshroom", 0.7F);
    }

    public ModelBase makeModel()
    {
        return new ModelCow();
    }

    public IEntityRenderer makeEntityRender(ModelBase modelBase, float shadowSize)
    {
        RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
        RenderMooshroom rendermooshroom = new RenderMooshroom(rendermanager);
        rendermooshroom.mainModel = modelBase;
        rendermooshroom.shadowSize = shadowSize;
        return rendermooshroom;
    }
}
