package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelGuardian;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.entity.RenderGuardian;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.monster.EntityGuardian;
import net.minecraft.src.Config;
import net.minecraft.src.Reflector;

public class ModelAdapterGuardian extends ModelAdapter
{
    public ModelAdapterGuardian()
    {
        super(EntityGuardian.class, "guardian", 0.5F);
    }

    public ModelBase makeModel()
    {
        return new ModelGuardian();
    }

    public ModelRenderer getModelRenderer(ModelBase model, String modelPart)
    {
        if (!(model instanceof ModelGuardian))
        {
            return null;
        }
        else
        {
            ModelGuardian modelguardian = (ModelGuardian)model;

            if (modelPart.equals("body"))
            {
                return (ModelRenderer)Reflector.getFieldValue(modelguardian, Reflector.ModelGuardian_body);
            }
            else if (modelPart.equals("eye"))
            {
                return (ModelRenderer)Reflector.getFieldValue(modelguardian, Reflector.ModelGuardian_eye);
            }
            else
            {
                String s = "spine";

                if (modelPart.startsWith(s))
                {
                    ModelRenderer[] amodelrenderer1 = (ModelRenderer[])Reflector.getFieldValue(modelguardian, Reflector.ModelGuardian_spines);

                    if (amodelrenderer1 == null)
                    {
                        return null;
                    }
                    else
                    {
                        String s3 = modelPart.substring(s.length());
                        int j = Config.parseInt(s3, -1);
                        --j;
                        return j >= 0 && j < amodelrenderer1.length ? amodelrenderer1[j] : null;
                    }
                }
                else
                {
                    String s1 = "tail";

                    if (modelPart.startsWith(s1))
                    {
                        ModelRenderer[] amodelrenderer = (ModelRenderer[])Reflector.getFieldValue(modelguardian, Reflector.ModelGuardian_tail);

                        if (amodelrenderer == null)
                        {
                            return null;
                        }
                        else
                        {
                            String s2 = modelPart.substring(s1.length());
                            int i = Config.parseInt(s2, -1);
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
    }

    public IEntityRenderer makeEntityRender(ModelBase modelBase, float shadowSize)
    {
        RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
        RenderGuardian renderguardian = new RenderGuardian(rendermanager);
        renderguardian.mainModel = modelBase;
        renderguardian.shadowSize = shadowSize;
        return renderguardian;
    }
}
