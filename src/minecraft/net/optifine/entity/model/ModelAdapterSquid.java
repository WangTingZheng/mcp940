package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.ModelSquid;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderSquid;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.src.Config;
import net.minecraft.src.Reflector;

public class ModelAdapterSquid extends ModelAdapter
{
    public ModelAdapterSquid()
    {
        super(EntitySquid.class, "squid", 0.7F);
    }

    public ModelBase makeModel()
    {
        return new ModelSquid();
    }

    public ModelRenderer getModelRenderer(ModelBase model, String modelPart)
    {
        if (!(model instanceof ModelSquid))
        {
            return null;
        }
        else
        {
            ModelSquid modelsquid = (ModelSquid)model;

            if (modelPart.equals("body"))
            {
                return (ModelRenderer)Reflector.getFieldValue(modelsquid, Reflector.ModelSquid_body);
            }
            else
            {
                String s = "tentacle";

                if (modelPart.startsWith(s))
                {
                    ModelRenderer[] amodelrenderer = (ModelRenderer[])Reflector.getFieldValue(modelsquid, Reflector.ModelSquid_tentacles);

                    if (amodelrenderer == null)
                    {
                        return null;
                    }
                    else
                    {
                        String s1 = modelPart.substring(s.length());
                        int i = Config.parseInt(s1, -1);
                        --i;
                        return i >= 0 && i < amodelrenderer.length ? amodelrenderer[i] : null;
                    }
                }
                else
                {
                    return null;
                }
            }
        }
    }

    public IEntityRenderer makeEntityRender(ModelBase modelBase, float shadowSize)
    {
        RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
        RenderSquid rendersquid = new RenderSquid(rendermanager);
        rendersquid.mainModel = modelBase;
        rendersquid.shadowSize = shadowSize;
        return rendersquid;
    }
}
