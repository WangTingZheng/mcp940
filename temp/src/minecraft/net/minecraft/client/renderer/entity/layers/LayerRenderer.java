package net.minecraft.client.renderer.entity.layers;

import net.minecraft.entity.EntityLivingBase;

public interface LayerRenderer<E extends EntityLivingBase> {
   void func_177141_a(E var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8);

   boolean func_177142_b();
}
