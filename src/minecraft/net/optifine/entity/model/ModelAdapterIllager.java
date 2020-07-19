package net.optifine.entity.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelIllager;
import net.minecraft.client.model.ModelRenderer;

public abstract class ModelAdapterIllager extends ModelAdapter
{
    public ModelAdapterIllager(Class entityClass, String name, float shadowSize)
    {
        super(entityClass, name, shadowSize);
    }

    public ModelRenderer getModelRenderer(ModelBase model, String modelPart)
    {
        if (!(model instanceof ModelIllager))
        {
            return null;
        }
        else
        {
            ModelIllager modelillager = (ModelIllager)model;

            if (modelPart.equals("head"))
            {
                return modelillager.head;
            }
            else if (modelPart.equals("body"))
            {
                return modelillager.body;
            }
            else if (modelPart.equals("arms"))
            {
                return modelillager.arms;
            }
            else if (modelPart.equals("left_leg"))
            {
                return modelillager.leg1;
            }
            else if (modelPart.equals("right_leg"))
            {
                return modelillager.leg0;
            }
            else if (modelPart.equals("nose"))
            {
                return modelillager.nose;
            }
            else if (modelPart.equals("left_arm"))
            {
                return modelillager.leftArm;
            }
            else
            {
                return modelPart.equals("right_arm") ? modelillager.rightArm : null;
            }
        }
    }
}
