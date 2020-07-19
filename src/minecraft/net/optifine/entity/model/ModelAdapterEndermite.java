package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelEnderMite;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.entity.RenderEndermite;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.monster.EntityEndermite;
import net.minecraft.src.Config;
import net.minecraft.src.Reflector;

public class ModelAdapterEndermite extends ModelAdapter
{
    public ModelAdapterEndermite()
    {
        super(EntityEndermite.class, "endermite", 0.3F);
    }

    public ModelBase makeModel()
    {
        return new ModelEnderMite();
    }

    public ModelRenderer getModelRenderer(ModelBase model, String modelPart)
    {
        if (!(model instanceof ModelEnderMite))
        {
            return null;
        }
        else
        {
            ModelEnderMite modelendermite = (ModelEnderMite)model;
            String s = "body";

            if (modelPart.startsWith(s))
            {
                ModelRenderer[] amodelrenderer = (ModelRenderer[])Reflector.getFieldValue(modelendermite, Reflector.ModelEnderMite_bodyParts);

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

    public IEntityRenderer makeEntityRender(ModelBase modelBase, float shadowSize)
    {
        RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
        RenderEndermite renderendermite = new RenderEndermite(rendermanager);
        renderendermite.mainModel = modelBase;
        renderendermite.shadowSize = shadowSize;
        return renderendermite;
    }
}
