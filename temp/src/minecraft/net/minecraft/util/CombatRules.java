package net.minecraft.util;

import net.minecraft.util.math.MathHelper;

public class CombatRules {
   public static float func_189427_a(float p_189427_0_, float p_189427_1_, float p_189427_2_) {
      float f = 2.0F + p_189427_2_ / 4.0F;
      float f1 = MathHelper.func_76131_a(p_189427_1_ - p_189427_0_ / f, p_189427_1_ * 0.2F, 20.0F);
      return p_189427_0_ * (1.0F - f1 / 25.0F);
   }

   public static float func_188401_b(float p_188401_0_, float p_188401_1_) {
      float f = MathHelper.func_76131_a(p_188401_1_, 0.0F, 20.0F);
      return p_188401_0_ * (1.0F - f / 25.0F);
   }
}
