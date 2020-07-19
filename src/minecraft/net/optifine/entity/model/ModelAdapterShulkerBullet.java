package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.ModelShulkerBullet;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderShulkerBullet;
import net.minecraft.entity.projectile.EntityShulkerBullet;
import net.minecraft.src.Config;
import net.minecraft.src.Reflector;

public class ModelAdapterShulkerBullet extends ModelAdapter
{
    public ModelAdapterShulkerBullet()
    {
        super(EntityShulkerBullet.class, "shulker_bullet", 0.0F);
    }

    public ModelBase makeModel()
    {
        return new ModelShulkerBullet();
    }

    public ModelRenderer getModelRenderer(ModelBase model, String modelPart)
    {
        if (!(model instanceof ModelShulkerBullet))
        {
            return null;
        }
        else
        {
            ModelShulkerBullet modelshulkerbullet = (ModelShulkerBullet)model;
            return modelPart.equals("bullet") ? modelshulkerbullet.renderer : null;
        }
    }

    public IEntityRenderer makeEntityRender(ModelBase modelBase, float shadowSize)
    {
        RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
        RenderShulkerBullet rendershulkerbullet = new RenderShulkerBullet(rendermanager);

        if (!Reflector.RenderShulkerBullet_model.exists())
        {
            Config.warn("Field not found: RenderShulkerBullet.model");
            return null;
        }
        else
        {
            Reflector.setFieldValue(rendershulkerbullet, Reflector.RenderShulkerBullet_model, modelBase);
            rendershulkerbullet.shadowSize = shadowSize;
            return rendershulkerbullet;
        }
    }
}
