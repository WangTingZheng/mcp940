package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelIronGolem;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.entity.RenderIronGolem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.monster.EntityIronGolem;

public class ModelAdapterIronGolem extends ModelAdapter
{
    public ModelAdapterIronGolem()
    {
        super(EntityIronGolem.class, "iron_golem", 0.5F);
    }

    public ModelBase makeModel()
    {
        return new ModelIronGolem();
    }

    public ModelRenderer getModelRenderer(ModelBase model, String modelPart)
    {
        if (!(model instanceof ModelIronGolem))
        {
            return null;
        }
        else
        {
            ModelIronGolem modelirongolem = (ModelIronGolem)model;

            if (modelPart.equals("head"))
            {
                return modelirongolem.ironGolemHead;
            }
            else if (modelPart.equals("body"))
            {
                return modelirongolem.ironGolemBody;
            }
            else if (modelPart.equals("left_arm"))
            {
                return modelirongolem.ironGolemLeftArm;
            }
            else if (modelPart.equals("right_arm"))
            {
                return modelirongolem.ironGolemRightArm;
            }
            else if (modelPart.equals("left_leg"))
            {
                return modelirongolem.ironGolemLeftLeg;
            }
            else
            {
                return modelPart.equals("right_leg") ? modelirongolem.ironGolemRightLeg : null;
            }
        }
    }

    public IEntityRenderer makeEntityRender(ModelBase modelBase, float shadowSize)
    {
        RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
        RenderIronGolem renderirongolem = new RenderIronGolem(rendermanager);
        renderirongolem.mainModel = modelBase;
        renderirongolem.shadowSize = shadowSize;
        return renderirongolem;
    }
}
