package net.optifine.entity.model.anim;

import net.minecraft.client.model.ModelRenderer;

public interface IModelResolver
{
    ModelRenderer getModelRenderer(String var1);

    ModelVariable getModelVariable(String var1);

    IExpression getExpression(String var1);
}
