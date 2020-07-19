package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelChicken;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.entity.RenderChicken;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.passive.EntityChicken;

public class ModelAdapterChicken extends ModelAdapter
{
    public ModelAdapterChicken()
    {
        super(EntityChicken.class, "chicken", 0.3F);
    }

    public ModelBase makeModel()
    {
        return new ModelChicken();
    }

    public ModelRenderer getModelRenderer(ModelBase model, String modelPart)
    {
        if (!(model instanceof ModelChicken))
        {
            return null;
        }
        else
        {
            ModelChicken modelchicken = (ModelChicken)model;

            if (modelPart.equals("head"))
            {
                return modelchicken.head;
            }
            else if (modelPart.equals("body"))
            {
                return modelchicken.body;
            }
            else if (modelPart.equals("right_leg"))
            {
                return modelchicken.rightLeg;
            }
            else if (modelPart.equals("left_leg"))
            {
                return modelchicken.leftLeg;
            }
            else if (modelPart.equals("right_wing"))
            {
                return modelchicken.rightWing;
            }
            else if (modelPart.equals("left_wing"))
            {
                return modelchicken.leftWing;
            }
            else if (modelPart.equals("bill"))
            {
                return modelchicken.bill;
            }
            else
            {
                return modelPart.equals("chin") ? modelchicken.chin : null;
            }
        }
    }

    public IEntityRenderer makeEntityRender(ModelBase modelBase, float shadowSize)
    {
        RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
        RenderChicken renderchicken = new RenderChicken(rendermanager);
        renderchicken.mainModel = modelBase;
        renderchicken.shadowSize = shadowSize;
        return renderchicken;
    }
}
