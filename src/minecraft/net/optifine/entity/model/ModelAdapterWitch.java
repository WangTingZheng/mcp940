package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.ModelWitch;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderWitch;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.src.Reflector;

public class ModelAdapterWitch extends ModelAdapter
{
    public ModelAdapterWitch()
    {
        super(EntityWitch.class, "witch", 0.5F);
    }

    public ModelBase makeModel()
    {
        return new ModelWitch(0.0F);
    }

    public ModelRenderer getModelRenderer(ModelBase model, String modelPart)
    {
        if (!(model instanceof ModelWitch))
        {
            return null;
        }
        else
        {
            ModelWitch modelwitch = (ModelWitch)model;

            if (modelPart.equals("mole"))
            {
                return (ModelRenderer)Reflector.getFieldValue(modelwitch, Reflector.ModelWitch_mole);
            }
            else if (modelPart.equals("hat"))
            {
                return (ModelRenderer)Reflector.getFieldValue(modelwitch, Reflector.ModelWitch_hat);
            }
            else if (modelPart.equals("head"))
            {
                return modelwitch.villagerHead;
            }
            else if (modelPart.equals("body"))
            {
                return modelwitch.villagerBody;
            }
            else if (modelPart.equals("arms"))
            {
                return modelwitch.villagerArms;
            }
            else if (modelPart.equals("left_leg"))
            {
                return modelwitch.leftVillagerLeg;
            }
            else if (modelPart.equals("right_leg"))
            {
                return modelwitch.rightVillagerLeg;
            }
            else
            {
                return modelPart.equals("nose") ? modelwitch.villagerNose : null;
            }
        }
    }

    public IEntityRenderer makeEntityRender(ModelBase modelBase, float shadowSize)
    {
        RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
        RenderWitch renderwitch = new RenderWitch(rendermanager);
        renderwitch.mainModel = modelBase;
        renderwitch.shadowSize = shadowSize;
        return renderwitch;
    }
}
