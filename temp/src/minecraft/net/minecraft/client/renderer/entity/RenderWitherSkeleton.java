package net.minecraft.client.renderer.entity;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.monster.AbstractSkeleton;
import net.minecraft.util.ResourceLocation;

public class RenderWitherSkeleton extends RenderSkeleton {
   private static final ResourceLocation field_110861_l = new ResourceLocation("textures/entity/skeleton/wither_skeleton.png");

   public RenderWitherSkeleton(RenderManager p_i47188_1_) {
      super(p_i47188_1_);
   }

   protected ResourceLocation func_110775_a(AbstractSkeleton p_110775_1_) {
      return field_110861_l;
   }

   protected void func_77041_b(AbstractSkeleton p_77041_1_, float p_77041_2_) {
      GlStateManager.func_179152_a(1.2F, 1.2F, 1.2F);
   }
}
