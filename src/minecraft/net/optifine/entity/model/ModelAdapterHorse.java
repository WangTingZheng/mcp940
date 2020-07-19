package net.optifine.entity.model;

import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelHorse;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.entity.RenderHorse;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.src.Reflector;

public class ModelAdapterHorse extends ModelAdapter
{
    private static Map<String, Integer> mapPartFields = null;

    public ModelAdapterHorse()
    {
        super(EntityHorse.class, "horse", 0.75F);
    }

    protected ModelAdapterHorse(Class entityClass, String name, float shadowSize)
    {
        super(entityClass, name, shadowSize);
    }

    public ModelBase makeModel()
    {
        return new ModelHorse();
    }

    public ModelRenderer getModelRenderer(ModelBase model, String modelPart)
    {
        if (!(model instanceof ModelHorse))
        {
            return null;
        }
        else
        {
            ModelHorse modelhorse = (ModelHorse)model;
            Map<String, Integer> map = getMapPartFields();

            if (map.containsKey(modelPart))
            {
                int i = ((Integer)map.get(modelPart)).intValue();
                return (ModelRenderer)Reflector.getFieldValue(modelhorse, Reflector.ModelHorse_ModelRenderers, i);
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
            mapPartFields.put("head", Integer.valueOf(0));
            mapPartFields.put("upper_mouth", Integer.valueOf(1));
            mapPartFields.put("lower_mouth", Integer.valueOf(2));
            mapPartFields.put("horse_left_ear", Integer.valueOf(3));
            mapPartFields.put("horse_right_ear", Integer.valueOf(4));
            mapPartFields.put("mule_left_ear", Integer.valueOf(5));
            mapPartFields.put("mule_right_ear", Integer.valueOf(6));
            mapPartFields.put("neck", Integer.valueOf(7));
            mapPartFields.put("horse_face_ropes", Integer.valueOf(8));
            mapPartFields.put("mane", Integer.valueOf(9));
            mapPartFields.put("body", Integer.valueOf(10));
            mapPartFields.put("tail_base", Integer.valueOf(11));
            mapPartFields.put("tail_middle", Integer.valueOf(12));
            mapPartFields.put("tail_tip", Integer.valueOf(13));
            mapPartFields.put("back_left_leg", Integer.valueOf(14));
            mapPartFields.put("back_left_shin", Integer.valueOf(15));
            mapPartFields.put("back_left_hoof", Integer.valueOf(16));
            mapPartFields.put("back_right_leg", Integer.valueOf(17));
            mapPartFields.put("back_right_shin", Integer.valueOf(18));
            mapPartFields.put("back_right_hoof", Integer.valueOf(19));
            mapPartFields.put("front_left_leg", Integer.valueOf(20));
            mapPartFields.put("front_left_shin", Integer.valueOf(21));
            mapPartFields.put("front_left_hoof", Integer.valueOf(22));
            mapPartFields.put("front_right_leg", Integer.valueOf(23));
            mapPartFields.put("front_right_shin", Integer.valueOf(24));
            mapPartFields.put("front_right_hoof", Integer.valueOf(25));
            mapPartFields.put("mule_left_chest", Integer.valueOf(26));
            mapPartFields.put("mule_right_chest", Integer.valueOf(27));
            mapPartFields.put("horse_saddle_bottom", Integer.valueOf(28));
            mapPartFields.put("horse_saddle_front", Integer.valueOf(29));
            mapPartFields.put("horse_saddle_back", Integer.valueOf(30));
            mapPartFields.put("horse_left_saddle_rope", Integer.valueOf(31));
            mapPartFields.put("horse_left_saddle_metal", Integer.valueOf(32));
            mapPartFields.put("horse_right_saddle_rope", Integer.valueOf(33));
            mapPartFields.put("horse_right_saddle_metal", Integer.valueOf(34));
            mapPartFields.put("horse_left_face_metal", Integer.valueOf(35));
            mapPartFields.put("horse_right_face_metal", Integer.valueOf(36));
            mapPartFields.put("horse_left_rein", Integer.valueOf(37));
            mapPartFields.put("horse_right_rein", Integer.valueOf(38));
            return mapPartFields;
        }
    }

    public IEntityRenderer makeEntityRender(ModelBase modelBase, float shadowSize)
    {
        RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
        RenderHorse renderhorse = new RenderHorse(rendermanager);
        renderhorse.mainModel = modelBase;
        renderhorse.shadowSize = shadowSize;
        return renderhorse;
    }
}
