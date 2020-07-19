package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelEnderCrystal;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderEnderCrystal;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.src.Config;
import net.minecraft.src.Reflector;

public class ModelAdapterEnderCrystalNoBase extends ModelAdapterEnderCrystal
{
    public ModelAdapterEnderCrystalNoBase()
    {
        super("end_crystal_no_base");
    }

    public ModelBase makeModel()
    {
        return new ModelEnderCrystal(0.0F, false);
    }

    public IEntityRenderer makeEntityRender(ModelBase modelBase, float shadowSize)
    {
        RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
        Render render = (Render)rendermanager.getEntityRenderMap().get(EntityEnderCrystal.class);

        if (!(render instanceof RenderEnderCrystal))
        {
            Config.warn("Not an instance of RenderEnderCrystal: " + render);
            return null;
        }
        else
        {
            RenderEnderCrystal renderendercrystal = (RenderEnderCrystal)render;

            if (!Reflector.RenderEnderCrystal_modelEnderCrystalNoBase.exists())
            {
                Config.warn("Field not found: RenderEnderCrystal.modelEnderCrystalNoBase");
                return null;
            }
            else
            {
                Reflector.setFieldValue(renderendercrystal, Reflector.RenderEnderCrystal_modelEnderCrystalNoBase, modelBase);
                renderendercrystal.shadowSize = shadowSize;
                return renderendercrystal;
            }
        }
    }
}
