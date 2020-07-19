package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelChicken;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class RenderChicken extends RenderLiving<EntityChicken> {
   private static final ResourceLocation field_110920_a = new ResourceLocation("textures/entity/chicken.png");

   public RenderChicken(RenderManager p_i47211_1_) {
      super(p_i47211_1_, new ModelChicken(), 0.3F);
   }

   protected ResourceLocation func_110775_a(EntityChicken p_110775_1_) {
      return field_110920_a;
   }

   protected float func_77044_a(EntityChicken p_77044_1_, float p_77044_2_) {
      float f = p_77044_1_.field_70888_h + (p_77044_1_.field_70886_e - p_77044_1_.field_70888_h) * p_77044_2_;
      float f1 = p_77044_1_.field_70884_g + (p_77044_1_.field_70883_f - p_77044_1_.field_70884_g) * p_77044_2_;
      return (MathHelper.func_76126_a(f) + 1.0F) * f1;
   }
}
