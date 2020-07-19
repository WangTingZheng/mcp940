package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.ModelVillager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderVillager;
import net.minecraft.entity.passive.EntityVillager;

public class ModelAdapterVillager extends ModelAdapter
{
    public ModelAdapterVillager()
    {
        super(EntityVillager.class, "villager", 0.5F);
    }

    public ModelBase makeModel()
    {
        return new ModelVillager(0.0F);
    }

    public ModelRenderer getModelRenderer(ModelBase model, String modelPart)
    {
        if (!(model instanceof ModelVillager))
        {
            return null;
        }
        else
        {
            ModelVillager modelvillager = (ModelVillager)model;

            if (modelPart.equals("head"))
            {
                return modelvillager.villagerHead;
            }
            else if (modelPart.equals("body"))
            {
                return modelvillager.villagerBody;
            }
            else if (modelPart.equals("arms"))
            {
                return modelvillager.villagerArms;
            }
            else if (modelPart.equals("left_leg"))
            {
                return modelvillager.leftVillagerLeg;
            }
            else if (modelPart.equals("right_leg"))
            {
                return modelvillager.rightVillagerLeg;
            }
            else
            {
                return modelPart.equals("nose") ? modelvillager.villagerNose : null;
            }
        }
    }

    public IEntityRenderer makeEntityRender(ModelBase modelBase, float shadowSize)
    {
        RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
        RenderVillager rendervillager = new RenderVillager(rendermanager);
        rendervillager.mainModel = modelBase;
        rendervillager.shadowSize = shadowSize;
        return rendervillager;
    }
}
