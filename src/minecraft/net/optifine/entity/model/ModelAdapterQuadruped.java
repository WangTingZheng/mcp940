package net.optifine.entity.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelQuadruped;
import net.minecraft.client.model.ModelRenderer;

public abstract class ModelAdapterQuadruped extends ModelAdapter
{
    public ModelAdapterQuadruped(Class entityClass, String name, float shadowSize)
    {
        super(entityClass, name, shadowSize);
    }

    public ModelRenderer getModelRenderer(ModelBase model, String modelPart)
    {
        if (!(model instanceof ModelQuadruped))
        {
            return null;
        }
        else
        {
            ModelQuadruped modelquadruped = (ModelQuadruped)model;

            if (modelPart.equals("head"))
            {
                return modelquadruped.head;
            }
            else if (modelPart.equals("body"))
            {
                return modelquadruped.body;
            }
            else if (modelPart.equals("leg1"))
            {
                return modelquadruped.leg1;
            }
            else if (modelPart.equals("leg2"))
            {
                return modelquadruped.leg2;
            }
            else if (modelPart.equals("leg3"))
            {
                return modelquadruped.leg3;
            }
            else
            {
                return modelPart.equals("leg4") ? modelquadruped.leg4 : null;
            }
        }
    }
}
