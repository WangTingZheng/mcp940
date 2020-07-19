package net.optifine.entity.model;

import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelOcelot;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderOcelot;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.src.Reflector;

public class ModelAdapterOcelot extends ModelAdapter
{
    private static Map<String, Integer> mapPartFields = null;

    public ModelAdapterOcelot()
    {
        super(EntityOcelot.class, "ocelot", 0.4F);
    }

    public ModelBase makeModel()
    {
        return new ModelOcelot();
    }

    public ModelRenderer getModelRenderer(ModelBase model, String modelPart)
    {
        if (!(model instanceof ModelOcelot))
        {
            return null;
        }
        else
        {
            ModelOcelot modelocelot = (ModelOcelot)model;
            Map<String, Integer> map = getMapPartFields();

            if (map.containsKey(modelPart))
            {
                int i = ((Integer)map.get(modelPart)).intValue();
                return (ModelRenderer)Reflector.getFieldValue(modelocelot, Reflector.ModelOcelot_ModelRenderers, i);
            }
            else
            {
                return null;
            }
        }
    }

    private static Map<String, Integer> getMapPartFields()
    {
        if (mapPartFields != null)
        {
            return mapPartFields;
        }
        else
        {
            mapPartFields = new HashMap<String, Integer>();
            mapPartFields.put("back_left_leg", Integer.valueOf(0));
            mapPartFields.put("back_right_leg", Integer.valueOf(1));
            mapPartFields.put("front_left_leg", Integer.valueOf(2));
            mapPartFields.put("front_right_leg", Integer.valueOf(3));
            mapPartFields.put("tail", Integer.valueOf(4));
            mapPartFields.put("tail2", Integer.valueOf(5));
            mapPartFields.put("head", Integer.valueOf(6));
            mapPartFields.put("body", Integer.valueOf(7));
            return mapPartFields;
        }
    }

    public IEntityRenderer makeEntityRender(ModelBase modelBase, float shadowSize)
    {
        RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
        RenderOcelot renderocelot = new RenderOcelot(rendermanager);
        renderocelot.mainModel = modelBase;
        renderocelot.shadowSize = shadowSize;
        return renderocelot;
    }
}
