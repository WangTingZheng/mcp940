package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelMinecart;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderMinecart;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.src.Config;
import net.minecraft.src.Reflector;

public class ModelAdapterMinecart extends ModelAdapter
{
    public ModelAdapterMinecart()
    {
        super(EntityMinecart.class, "minecart", 0.5F);
    }

    protected ModelAdapterMinecart(Class entityClass, String name, float shadow)
    {
        super(entityClass, name, shadow);
    }

    public ModelBase makeModel()
    {
        return new ModelMinecart();
    }

    public ModelRenderer getModelRenderer(ModelBase model, String modelPart)
    {
        if (!(model instanceof ModelMinecart))
        {
            return null;
        }
        else
        {
            ModelMinecart modelminecart = (ModelMinecart)model;

            if (modelPart.equals("bottom"))
            {
                return modelminecart.sideModels[0];
            }
            else if (modelPart.equals("back"))
            {
                return modelminecart.sideModels[1];
            }
            else if (modelPart.equals("front"))
            {
                return modelminecart.sideModels[2];
            }
            else if (modelPart.equals("right"))
            {
                return modelminecart.sideModels[3];
            }
            else if (modelPart.equals("left"))
            {
                return modelminecart.sideModels[4];
            }
            else
            {
                return modelPart.equals("dirt") ? modelminecart.sideModels[5] : null;
            }
        }
    }

    public IEntityRenderer makeEntityRender(ModelBase modelBase, float shadowSize)
    {
        RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
        RenderMinecart renderminecart = new RenderMinecart(rendermanager);

        if (!Reflector.RenderMinecart_modelMinecart.exists())
        {
            Config.warn("Field not found: RenderMinecart.modelMinecart");
            return null;
        }
        else
        {
            Reflector.setFieldValue(renderminecart, Reflector.RenderMinecart_modelMinecart, modelBase);
            renderminecart.shadowSize = shadowSize;
            return renderminecart;
        }
    }
}
