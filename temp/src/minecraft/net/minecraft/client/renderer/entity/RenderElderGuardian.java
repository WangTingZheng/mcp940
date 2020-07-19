package net.minecraft.client.renderer.entity;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.monster.EntityGuardian;
import net.minecraft.util.ResourceLocation;

public class RenderElderGuardian extends RenderGuardian {
   private static final ResourceLocation field_177116_j = new ResourceLocation("textures/entity/guardian_elder.png");

   public RenderElderGuardian(RenderManager p_i47209_1_) {
      super(p_i47209_1_);
   }

   protected void func_77041_b(EntityGuardian p_77041_1_, float p_77041_2_) {
      GlStateManager.func_179152_a(2.35F, 2.35F, 2.35F);
   }

   protected ResourceLocation func_110775_a(EntityGuardian p_110775_1_) {
      return field_177116_j;
   }
}
