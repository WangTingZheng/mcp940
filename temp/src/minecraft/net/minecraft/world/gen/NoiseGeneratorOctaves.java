package net.minecraft.world.gen;

import java.util.Random;
import net.minecraft.util.math.MathHelper;

public class NoiseGeneratorOctaves extends NoiseGenerator {
   private final NoiseGeneratorImproved[] field_76307_a;
   private final int field_76306_b;

   public NoiseGeneratorOctaves(Random p_i2111_1_, int p_i2111_2_) {
      this.field_76306_b = p_i2111_2_;
      this.field_76307_a = new NoiseGeneratorImproved[p_i2111_2_];

      for(int i = 0; i < p_i2111_2_; ++i) {
         this.field_76307_a[i] = new NoiseGeneratorImproved(p_i2111_1_);
      }

   }

   public double[] func_76304_a(double[] p_76304_1_, int p_76304_2_, int p_76304_3_, int p_76304_4_, int p_76304_5_, int p_76304_6_, int p_76304_7_, double p_76304_8_, double p_76304_10_, double p_76304_12_) {
      if (p_76304_1_ == null) {
         p_76304_1_ = new double[p_76304_5_ * p_76304_6_ * p_76304_7_];
      } else {
         for(int i = 0; i < p_76304_1_.length; ++i) {
            p_76304_1_[i] = 0.0D;
         }
      }

      double d3 = 1.0D;

      for(int j = 0; j < this.field_76306_b; ++j) {
         double d0 = (double)p_76304_2_ * d3 * p_76304_8_;
         double d1 = (double)p_76304_3_ * d3 * p_76304_10_;
         double d2 = (double)p_76304_4_ * d3 * p_76304_12_;
         long k = MathHelper.func_76124_d(d0);
         long l = MathHelper.func_76124_d(d2);
         d0 = d0 - (double)k;
         d2 = d2 - (double)l;
         k = k % 16777216L;
         l = l % 16777216L;
         d0 = d0 + (double)k;
         d2 = d2 + (double)l;
         this.field_76307_a[j].func_76308_a(p_76304_1_, d0, d1, d2, p_76304_5_, p_76304_6_, p_76304_7_, p_76304_8_ * d3, p_76304_10_ * d3, p_76304_12_ * d3, d3);
         d3 /= 2.0D;
      }

      return p_76304_1_;
   }

   public double[] func_76305_a(double[] p_76305_1_, int p_76305_2_, int p_76305_3_, int p_76305_4_, int p_76305_5_, double p_76305_6_, double p_76305_8_, double p_76305_10_) {
      return this.func_76304_a(p_76305_1_, p_76305_2_, 10, p_76305_3_, p_76305_4_, 1, p_76305_5_, p_76305_6_, 1.0D, p_76305_8_);
   }
}
