package net.optifine.entity.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.ModelShulker;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.tileentity.TileEntityShulkerBoxRenderer;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.src.Config;
import net.minecraft.src.Reflector;
import net.minecraft.tileentity.TileEntityShulkerBox;

public class ModelAdapterShulkerBox extends ModelAdapter
{
    public ModelAdapterShulkerBox()
    {
        super(TileEntityShulkerBox.class, "shulker_box", 0.0F);
    }

    public ModelBase makeModel()
    {
        return new ModelShulker();
    }

    public ModelRenderer getModelRenderer(ModelBase model, String modelPart)
    {
        if (!(model instanceof ModelShulker))
        {
            return null;
        }
        else
        {
            ModelShulker modelshulker = (ModelShulker)model;

            if (modelPart.equals("head"))
            {
                return modelshulker.head;
            }
            else if (modelPart.equals("base"))
            {
                return modelshulker.base;
            }
            else
            {
                return modelPart.equals("lid") ? modelshulker.lid : null;
            }
        }
    }

    public IEntityRenderer makeEntityRender(ModelBase modelBase, float shadowSize)
    {
        TileEntityRendererDispatcher tileentityrendererdispatcher = TileEntityRendererDispatcher.instance;
        TileEntitySpecialRenderer tileentityspecialrenderer = tileentityrendererdispatcher.getRenderer(TileEntityShulkerBox.class);

        if (!(tileentityspecialrenderer instanceof TileEntityShulkerBoxRenderer))
        {
            return null;
        }
        else
        {
            if (tileentityspecialrenderer.getEntityClass() == null)
            {
                tileentityspecialrenderer = new TileEntityShulkerBoxRenderer((ModelShulker)modelBase);
                tileentityspecialrenderer.setRendererDispatcher(tileentityrendererdispatcher);
            }

            if (!Reflector.TileEntityShulkerBoxRenderer_model.exists())
            {
                Config.warn("Field not found: TileEntityShulkerBoxRenderer.model");
                return null;
            }
            else
            {
                Reflector.setFieldValue(tileentityspecialrenderer, Reflector.TileEntityShulkerBoxRenderer_model, modelBase);
                return tileentityspecialrenderer;
            }
        }
    }
}
