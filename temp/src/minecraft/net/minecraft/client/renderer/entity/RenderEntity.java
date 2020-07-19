package net.minecraft.client.renderer.entity;

import javax.annotation.Nullable;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderEntity extends Render<Entity> {
   public RenderEntity(RenderManager p_i46185_1_) {
      super(p_i46185_1_);
   }

   public void func_76986_a(Entity p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_) {
      GlStateManager.func_179094_E();
      func_76978_a(p_76986_1_.func_174813_aQ(), p_76986_2_ - p_76986_1_.field_70142_S, p_76986_4_ - p_76986_1_.field_70137_T, p_76986_6_ - p_76986_1_.field_70136_U);
      GlStateManager.func_179121_F();
      super.func_76986_a(p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
   }

   @Nullable
   protected ResourceLocation func_110775_a(Entity p_110775_1_) {
      return null;
   }
}
