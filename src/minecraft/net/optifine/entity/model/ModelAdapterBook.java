package net.optifine.entity.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBook;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityEnchantmentTableRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.src.Config;
import net.minecraft.src.Reflector;
import net.minecraft.tileentity.TileEntityEnchantmentTable;

public class ModelAdapterBook extends ModelAdapter
{
    public ModelAdapterBook()
    {
        super(TileEntityEnchantmentTable.class, "book", 0.0F);
    }

    public ModelBase makeModel()
    {
        return new ModelBook();
    }

    public ModelRenderer getModelRenderer(ModelBase model, String modelPart)
    {
        if (!(model instanceof ModelBook))
        {
            return null;
        }
        else
        {
            ModelBook modelbook = (ModelBook)model;

            if (modelPart.equals("cover_right"))
            {
                return modelbook.coverRight;
            }
            else if (modelPart.equals("cover_left"))
            {
                return modelbook.coverLeft;
            }
            else if (modelPart.equals("pages_right"))
            {
                return modelbook.pagesRight;
            }
            else if (modelPart.equals("pages_left"))
            {
                return modelbook.pagesLeft;
            }
            else if (modelPart.equals("flipping_page_right"))
            {
                return modelbook.flippingPageRight;
            }
            else if (modelPart.equals("flipping_page_left"))
            {
                return modelbook.flippingPageLeft;
            }
            else
            {
                return modelPart.equals("book_spine") ? modelbook.bookSpine : null;
            }
        }
    }

    public IEntityRenderer makeEntityRender(ModelBase modelBase, float shadowSize)
    {
        TileEntityRendererDispatcher tileentityrendererdispatcher = TileEntityRendererDispatcher.instance;
        TileEntitySpecialRenderer tileentityspecialrenderer = tileentityrendererdispatcher.getRenderer(TileEntityEnchantmentTable.class);

        if (!(tileentityspecialrenderer instanceof TileEntityEnchantmentTableRenderer))
        {
            return null;
        }
        else
        {
            if (tileentityspecialrenderer.getEntityClass() == null)
            {
                tileentityspecialrenderer = new TileEntityEnchantmentTableRenderer();
                tileentityspecialrenderer.setRendererDispatcher(tileentityrendererdispatcher);
            }

            if (!Reflector.TileEntityEnchantmentTableRenderer_modelBook.exists())
            {
                Config.warn("Field not found: TileEntityEnchantmentTableRenderer.modelBook");
                return null;
            }
            else
            {
                Reflector.setFieldValue(tileentityspecialrenderer, Reflector.TileEntityEnchantmentTableRenderer_modelBook, modelBase);
                return tileentityspecialrenderer;
            }
        }
    }
}
