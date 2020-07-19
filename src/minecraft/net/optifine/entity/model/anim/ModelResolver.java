package net.optifine.entity.model.anim;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.src.Config;
import net.minecraft.tileentity.TileEntity;
import net.optifine.entity.model.CustomModelRenderer;
import net.optifine.entity.model.ModelAdapter;

public class ModelResolver implements IModelResolver
{
    private ModelAdapter modelAdapter;
    private ModelBase model;
    private CustomModelRenderer[] customModelRenderers;
    private ModelRenderer thisModelRenderer;
    private ModelRenderer partModelRenderer;
    private IRenderResolver renderResolver;

    public ModelResolver(ModelAdapter modelAdapter, ModelBase model, CustomModelRenderer[] customModelRenderers)
    {
        this.modelAdapter = modelAdapter;
        this.model = model;
        this.customModelRenderers = customModelRenderers;
        Class oclass = modelAdapter.getEntityClass();

        if (TileEntity.class.isAssignableFrom(oclass))
        {
            this.renderResolver = new RenderResolverTileEntity();
        }
        else
        {
            this.renderResolver = new RenderResolverEntity();
        }
    }

    public IExpression getExpression(String name)
    {
        ModelVariable modelvariable = this.getModelVariable(name);

        if (modelvariable != null)
        {
            return modelvariable;
        }
        else
        {
            IExpression iexpression = this.renderResolver.getParameter(name);
            return iexpression != null ? iexpression : null;
        }
    }

    public ModelRenderer getModelRenderer(String name)
    {
        if (name == null)
        {
            return null;
        }
        else if (name.indexOf(":") >= 0)
        {
            String[] astring = Config.tokenize(name, ":");
            ModelRenderer modelrenderer3 = this.getModelRenderer(astring[0]);

            for (int j = 1; j < astring.length; ++j)
            {
                String s = astring[j];
                ModelRenderer modelrenderer4 = modelrenderer3.getChildDeep(s);

                if (modelrenderer4 == null)
                {
                    return null;
                }

                modelrenderer3 = modelrenderer4;
            }

            return modelrenderer3;
        }
        else if (this.thisModelRenderer != null && name.equals("this"))
        {
            return this.thisModelRenderer;
        }
        else if (this.partModelRenderer != null && name.equals("part"))
        {
            return this.partModelRenderer;
        }
        else
        {
            ModelRenderer modelrenderer = this.modelAdapter.getModelRenderer(this.model, name);

            if (modelrenderer != null)
            {
                return modelrenderer;
            }
            else
            {
                for (int i = 0; i < this.customModelRenderers.length; ++i)
                {
                    CustomModelRenderer custommodelrenderer = this.customModelRenderers[i];
                    ModelRenderer modelrenderer1 = custommodelrenderer.getModelRenderer();

                    if (name.equals(modelrenderer1.getId()))
                    {
                        return modelrenderer1;
                    }

                    ModelRenderer modelrenderer2 = modelrenderer1.getChildDeep(name);

                    if (modelrenderer2 != null)
                    {
                        return modelrenderer2;
                    }
                }

                return null;
            }
        }
    }

    public ModelVariable getModelVariable(String name)
    {
        String[] astring = Config.tokenize(name, ".");

        if (astring.length != 2)
        {
            return null;
        }
        else
        {
            String s = astring[0];
            String s1 = astring[1];
            ModelRenderer modelrenderer = this.getModelRenderer(s);

            if (modelrenderer == null)
            {
                return null;
            }
            else
            {
                EnumModelVariable enummodelvariable = EnumModelVariable.parse(s1);
                return enummodelvariable == null ? null : new ModelVariable(name, modelrenderer, enummodelvariable);
            }
        }
    }

    public void setPartModelRenderer(ModelRenderer partModelRenderer)
    {
        this.partModelRenderer = partModelRenderer;
    }

    public void setThisModelRenderer(ModelRenderer thisModelRenderer)
    {
        this.thisModelRenderer = thisModelRenderer;
    }
}
