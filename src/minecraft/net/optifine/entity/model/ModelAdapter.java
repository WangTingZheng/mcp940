package net.optifine.entity.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public abstract class ModelAdapter
{
    private Class entityClass;
    private String name;
    private float shadowSize;

    public ModelAdapter(Class entityClass, String name, float shadowSize)
    {
        this.entityClass = entityClass;
        this.name = name;
        this.shadowSize = shadowSize;
    }

    public Class getEntityClass()
    {
        return this.entityClass;
    }

    public String getName()
    {
        return this.name;
    }

    public float getShadowSize()
    {
        return this.shadowSize;
    }

    public abstract ModelBase makeModel();

    public abstract ModelRenderer getModelRenderer(ModelBase var1, String var2);

    public abstract IEntityRenderer makeEntityRender(ModelBase var1, float var2);
}
