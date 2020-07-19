package net.optifine.entity.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;

public abstract class ModelAdapterBiped extends ModelAdapter
{
    public ModelAdapterBiped(Class entityClass, String name, float shadowSize)
    {
        super(entityClass, name, shadowSize);
    }

    public ModelRenderer getModelRenderer(ModelBase model, String modelPart)
    {
        if (!(model instanceof ModelBiped))
        {
            return null;
        }
        else
        {
            ModelBiped modelbiped = (ModelBiped)model;

            if (modelPart.equals("head"))
            {
                return modelbiped.bipedHead;
            }
            else if (modelPart.equals("headwear"))
            {
                return modelbiped.bipedHeadwear;
            }
            else if (modelPart.equals("body"))
            {
                return modelbiped.bipedBody;
            }
            else if (modelPart.equals("left_arm"))
            {
                return modelbiped.bipedLeftArm;
            }
            else if (modelPart.equals("right_arm"))
            {
                return modelbiped.bipedRightArm;
            }
            else if (modelPart.equals("left_leg"))
            {
                return modelbiped.bipedLeftLeg;
            }
            else
            {
                return modelPart.equals("right_leg") ? modelbiped.bipedRightLeg : null;
            }
        }
    }
}
