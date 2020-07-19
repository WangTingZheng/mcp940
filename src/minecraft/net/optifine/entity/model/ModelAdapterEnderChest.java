package net.optifine.entity.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelChest;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityEnderChestRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.src.Config;
import net.minecraft.src.Reflector;
import net.minecraft.tileentity.TileEntityEnderChest;

public class ModelAdapterEnderChest extends ModelAdapter
{
    public ModelAdapterEnderChest()
    {
        super(TileEntityEnderChest.class, "ender_chest", 0.0F);
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
        TileEntitySpecialRenderer tileentityspecialrenderer = tileentityrendererdispatcher.getRenderer(TileEntityEnderChest.class);

        if (!(tileentityspecialrenderer instanceof TileEntityEnderChestRenderer))
        {
            return null;
        }
        else
        {
            if (tileentityspecialrenderer.getEntityClass() == null)
            {
                tileentityspecialrenderer = new TileEntityEnderChestRenderer();
                tileentityspecialrenderer.setRendererDispatcher(tileentityrendererdispatcher);
            }

            if (!Reflector.TileEntityEnderChestRenderer_modelChest.exists())
            {
                Config.warn("Field not found: TileEntityEnderChestRenderer.modelChest");
                return null;
            }
            else
            {
                Reflector.setFieldValue(tileentityspecialrenderer, Reflector.TileEntityEnderChestRenderer_modelChest, modelBase);
                return tileentityspecialrenderer;
            }
        }
    }
}
