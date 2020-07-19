package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelSquid;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.util.ResourceLocation;

public class RenderSquid extends RenderLiving<EntitySquid> {
   private static final ResourceLocation field_110901_a = new ResourceLocation("textures/entity/squid.png");

   public RenderSquid(RenderManager p_i47192_1_) {
      super(p_i47192_1_, new ModelSquid(), 0.7F);
   }

   protected ResourceLocation func_110775_a(EntitySquid p_110775_1_) {
      return field_110901_a;
   }

   protected void func_77043_a(EntitySquid p_77043_1_, float p_77043_2_, float p_77043_3_, float p_77043_4_) {
      float f = p_77043_1_.field_70862_e + (p_77043_1_.field_70861_d - p_77043_1_.field_70862_e) * p_77043_4_;
      float f1 = p_77043_1_.field_70860_g + (p_77043_1_.field_70859_f - p_77043_1_.field_70860_g) * p_77043_4_;
      GlStateManager.func_179109_b(0.0F, 0.5F, 0.0F);
      GlStateManager.func_179114_b(180.0F - p_77043_3_, 0.0F, 1.0F, 0.0F);
      GlStateManager.func_179114_b(f, 1.0F, 0.0F, 0.0F);
      GlStateManager.func_179114_b(f1, 0.0F, 1.0F, 0.0F);
      GlStateManager.func_179109_b(0.0F, -1.2F, 0.0F);
   }

   protected float func_77044_a(EntitySquid p_77044_1_, float p_77044_2_) {
      return p_77044_1_.field_70865_by + (p_77044_1_.field_70866_j - p_77044_1_.field_70865_by) * p_77044_2_;
   }
}
