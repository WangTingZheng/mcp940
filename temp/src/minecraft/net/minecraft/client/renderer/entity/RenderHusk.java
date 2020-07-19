package net.minecraft.client.renderer.entity;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.util.ResourceLocation;

public class RenderHusk extends RenderZombie {
   private static final ResourceLocation field_190086_r = new ResourceLocation("textures/entity/zombie/husk.png");

   public RenderHusk(RenderManager p_i47204_1_) {
      super(p_i47204_1_);
   }

   protected void func_77041_b(EntityZombie p_77041_1_, float p_77041_2_) {
      float f = 1.0625F;
      GlStateManager.func_179152_a(1.0625F, 1.0625F, 1.0625F);
      super.func_77041_b(p_77041_1_, p_77041_2_);
   }

   protected ResourceLocation func_110775_a(EntityZombie p_110775_1_) {
      return field_190086_r;
   }
}
