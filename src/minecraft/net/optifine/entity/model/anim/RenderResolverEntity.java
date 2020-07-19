package net.optifine.entity.model.anim;

public class RenderResolverEntity implements IRenderResolver
{
    public IExpression getParameter(String name)
    {
        EnumRenderParameterEntity enumrenderparameterentity = EnumRenderParameterEntity.parse(name);
        return enumrenderparameterentity;
    }
}
