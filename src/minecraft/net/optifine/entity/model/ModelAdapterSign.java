package net.optifine.entity.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.ModelSign;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.tileentity.TileEntitySignRenderer;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.src.Config;
import net.minecraft.src.Reflector;
import net.minecraft.tileentity.TileEntitySign;

public class ModelAdapterSign extends ModelAdapter
{
    public ModelAdapterSign()
    {
        super(TileEntitySign.class, "sign", 0.0F);
    }

    public ModelBase makeModel()
    {
        return new ModelSign();
    }

    public ModelRenderer getModelRenderer(ModelBase model, String modelPart)
    {
        if (!(model instanceof ModelSign))
        {
            return null;
        }
        else
        {
            ModelSign modelsign = (ModelSign)model;

            if (modelPart.equals("board"))
            {
                return modelsign.signBoard;
            }
            else
            {
                return modelPart.equals("stick") ? modelsign.signStick : null;
            }
        }
    }

    public IEntityRenderer makeEntityRender(ModelBase modelBase, float shadowSize)
    {
        TileEntityRendererDispatcher tileentityrendererdispatcher = TileEntityRendererDispatcher.instance;
        TileEntitySpecialRenderer tileentityspecialrenderer = tileentityrendererdispatcher.getRenderer(TileEntitySign.class);

        if (!(tileentityspecialrenderer instanceof TileEntitySignRenderer))
        {
            return null;
        }
        else
        {
            if (tileentityspecialrenderer.getEntityClass() == null)
            {
                tileentityspecialrenderer = new TileEntitySignRenderer();
                tileentityspecialrenderer.setRendererDispatcher(tileentityrendererdispatcher);
            }

            if (!Reflector.TileEntitySignRenderer_model.exists())
            {
                Config.warn("Field not found: TileEntitySignRenderer.model");
                return null;
            }
            else
            {
                Reflector.setFieldValue(tileentityspecialrenderer, Reflector.TileEntitySignRenderer_model, modelBase);
                return tileentityspecialrenderer;
            }
        }
    }
}
