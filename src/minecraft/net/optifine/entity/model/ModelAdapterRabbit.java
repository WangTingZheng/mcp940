package net.optifine.entity.model;

import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRabbit;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderRabbit;
import net.minecraft.entity.passive.EntityRabbit;
import net.minecraft.src.Reflector;

public class ModelAdapterRabbit extends ModelAdapter
{
    private static Map<String, Integer> mapPartFields = null;

    public ModelAdapterRabbit()
    {
        super(EntityRabbit.class, "rabbit", 0.3F);
    }

    public ModelBase makeModel()
    {
        return new ModelRabbit();
    }

    public ModelRenderer getModelRenderer(ModelBase model, String modelPart)
    {
        if (!(model instanceof ModelRabbit))
        {
            return null;
        }
        else
        {
            ModelRabbit modelrabbit = (ModelRabbit)model;
            Map<String, Integer> map = getMapPartFields();

            if (map.containsKey(modelPart))
            {
                int i = ((Integer)map.get(modelPart)).intValue();
                return (ModelRenderer)Reflector.getFieldValue(modelrabbit, Reflector.ModelRabbit_renderers, i);
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
            mapPartFields.put("left_foot", Integer.valueOf(0));
            mapPartFields.put("right_foot", Integer.valueOf(1));
            mapPartFields.put("left_thigh", Integer.valueOf(2));
            mapPartFields.put("right_thigh", Integer.valueOf(3));
            mapPartFields.put("body", Integer.valueOf(4));
            mapPartFields.put("left_arm", Integer.valueOf(5));
            mapPartFields.put("right_arm", Integer.valueOf(6));
            mapPartFields.put("head", Integer.valueOf(7));
            mapPartFields.put("right_ear", Integer.valueOf(8));
            mapPartFields.put("left_ear", Integer.valueOf(9));
            mapPartFields.put("tail", Integer.valueOf(10));
            mapPartFields.put("nose", Integer.valueOf(11));
            return mapPartFields;
        }
    }

    public IEntityRenderer makeEntityRender(ModelBase modelBase, float shadowSize)
    {
        RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
        RenderRabbit renderrabbit = new RenderRabbit(rendermanager);
        renderrabbit.mainModel = modelBase;
        renderrabbit.shadowSize = shadowSize;
        return renderrabbit;
    }
}
