package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.ModelShulker;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderShulker;
import net.minecraft.entity.monster.EntityShulker;

public class ModelAdapterShulker extends ModelAdapter
{
    public ModelAdapterShulker()
    {
        super(EntityShulker.class, "shulker", 0.0F);
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
        RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
        RenderShulker rendershulker = new RenderShulker(rendermanager);
        rendershulker.mainModel = modelBase;
        rendershulker.shadowSize = shadowSize;
        return rendershulker;
    }
}
