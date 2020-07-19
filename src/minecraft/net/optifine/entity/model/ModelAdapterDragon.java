package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelDragon;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.entity.RenderDragon;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.src.Reflector;

public class ModelAdapterDragon extends ModelAdapter
{
    public ModelAdapterDragon()
    {
        super(EntityDragon.class, "dragon", 0.5F);
    }

    public ModelBase makeModel()
    {
        return new ModelDragon(0.0F);
    }

    public ModelRenderer getModelRenderer(ModelBase model, String modelPart)
    {
        if (!(model instanceof ModelDragon))
        {
            return null;
        }
        else
        {
            ModelDragon modeldragon = (ModelDragon)model;

            if (modelPart.equals("head"))
            {
                return (ModelRenderer)Reflector.getFieldValue(modeldragon, Reflector.ModelDragon_ModelRenderers, 0);
            }
            else if (modelPart.equals("spine"))
            {
                return (ModelRenderer)Reflector.getFieldValue(modeldragon, Reflector.ModelDragon_ModelRenderers, 1);
            }
            else if (modelPart.equals("jaw"))
            {
                return (ModelRenderer)Reflector.getFieldValue(modeldragon, Reflector.ModelDragon_ModelRenderers, 2);
            }
            else if (modelPart.equals("body"))
            {
                return (ModelRenderer)Reflector.getFieldValue(modeldragon, Reflector.ModelDragon_ModelRenderers, 3);
            }
            else if (modelPart.equals("rear_leg"))
            {
                return (ModelRenderer)Reflector.getFieldValue(modeldragon, Reflector.ModelDragon_ModelRenderers, 4);
            }
            else if (modelPart.equals("front_leg"))
            {
                return (ModelRenderer)Reflector.getFieldValue(modeldragon, Reflector.ModelDragon_ModelRenderers, 5);
            }
            else if (modelPart.equals("rear_leg_tip"))
            {
                return (ModelRenderer)Reflector.getFieldValue(modeldragon, Reflector.ModelDragon_ModelRenderers, 6);
            }
            else if (modelPart.equals("front_leg_tip"))
            {
                return (ModelRenderer)Reflector.getFieldValue(modeldragon, Reflector.ModelDragon_ModelRenderers, 7);
            }
            else if (modelPart.equals("rear_foot"))
            {
                return (ModelRenderer)Reflector.getFieldValue(modeldragon, Reflector.ModelDragon_ModelRenderers, 8);
            }
            else if (modelPart.equals("front_foot"))
            {
                return (ModelRenderer)Reflector.getFieldValue(modeldragon, Reflector.ModelDragon_ModelRenderers, 9);
            }
            else if (modelPart.equals("wing"))
            {
                return (ModelRenderer)Reflector.getFieldValue(modeldragon, Reflector.ModelDragon_ModelRenderers, 10);
            }
            else
            {
                return modelPart.equals("wing_tip") ? (ModelRenderer)Reflector.getFieldValue(modeldragon, Reflector.ModelDragon_ModelRenderers, 11) : null;
            }
        }
    }

    public IEntityRenderer makeEntityRender(ModelBase modelBase, float shadowSize)
    {
        RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
        RenderDragon renderdragon = new RenderDragon(rendermanager);
        renderdragon.mainModel = modelBase;
        renderdragon.shadowSize = shadowSize;
        return renderdragon;
    }
}
