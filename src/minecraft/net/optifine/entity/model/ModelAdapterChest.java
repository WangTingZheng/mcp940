package net.optifine.entity.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelChest;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityChestRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.src.Config;
import net.minecraft.src.Reflector;
import net.minecraft.tileentity.TileEntityChest;

public class ModelAdapterChest extends ModelAdapter
{
    public ModelAdapterChest()
    {
        super(TileEntityChest.class, "chest", 0.0F);
    }

    public ModelBase makeModel()
    {
        return new ModelChest();
    }

    public ModelRenderer getModelRenderer(ModelBase model, String modelPart)
    {
        if (!(model instanceof ModelChest))
        {
            return null;
        }
        else
        {
            ModelChest modelchest = (ModelChest)model;

            if (modelPart.equals("lid"))
            {
                return modelchest.chestLid;
            }
            else if (modelPart.equals("base"))
            {
                return modelchest.chestBelow;
            }
            else
            {
                return modelPart.equals("knob") ? modelchest.chestKnob : null;
            }
        }
    }

    public IEntityRenderer makeEntityRender(ModelBase modelBase, float shadowSize)
    {
        TileEntityRendererDispatcher tileentityrendererdispatcher = TileEntityRendererDispatcher.instance;
        TileEntitySpecialRenderer tileentityspecialrenderer = tileentityrendererdispatcher.getRenderer(TileEntityChest.class);

        if (!(tileentityspecialrenderer instanceof TileEntityChestRenderer))
        {
            return null;
        }
        else
        {
            if (tileentityspecialrenderer.getEntityClass() == null)
            {
                tileentityspecialrenderer = new TileEntityChestRenderer();
                tileentityspecialrenderer.setRendererDispatcher(tileentityrendererdispatcher);
            }

            if (!Reflector.TileEntityChestRenderer_simpleChest.exists())
            {
                Config.warn("Field not found: TileEntityChestRenderer.simpleChest");
                return null;
            }
            else
            {
                Reflector.setFieldValue(tileentityspecialrenderer, Reflector.TileEntityChestRenderer_simpleChest, modelBase);
                return tileentityspecialrenderer;
            }
        }
    }
}
