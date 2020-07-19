package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelZombie;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderPigZombie;
import net.minecraft.entity.monster.EntityPigZombie;

public class ModelAdapterPigZombie extends ModelAdapterBiped
{
    public ModelAdapterPigZombie()
    {
        super(EntityPigZombie.class, "zombie_pigman", 0.5F);
    }

    public ModelBase makeModel()
    {
        return new ModelZombie();
    }

    public IEntityRenderer makeEntityRender(ModelBase modelBase, float shadowSize)
    {
        RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
        RenderPigZombie renderpigzombie = new RenderPigZombie(rendermanager);
        renderpigzombie.mainModel = modelBase;
        renderpigzombie.shadowSize = shadowSize;
        return renderpigzombie;
    }
}
