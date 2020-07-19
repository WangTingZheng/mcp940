package net.minecraft.world.gen;

import java.util.Random;

public class NoiseGeneratorImproved extends NoiseGenerator {
   private final int[] field_76312_d;
   public double field_76315_a;
   public double field_76313_b;
   public double field_76314_c;
   private static final double[] field_152381_e = new double[]{1.0D, -1.0D, 1.0D, -1.0D, 1.0D, -1.0D, 1.0D, -1.0D, 0.0D, 0.0D, 0.0D, 0.0D, 1.0D, 0.0D, -1.0D, 0.0D};
   private static final double[] field_152382_f = new double[]{1.0D, 1.0D, -1.0D, -1.0D, 0.0D, 0.0D, 0.0D, 0.0D, 1.0D, -1.0D, 1.0D, -1.0D, 1.0D, -1.0D, 1.0D, -1.0D};
   private static final double[] field_152383_g = new double[]{0.0D, 0.0D, 0.0D, 0.0D, 1.0D, 1.0D, -1.0D, -1.0D, 1.0D, 1.0D, -1.0D, -1.0D, 0.0D, 1.0D, 0.0D, -1.0D};
   private static final double[] field_152384_h = new double[]{1.0D, -1.0D, 1.0D, -1.0D, 1.0D, -1.0D, 1.0D, -1.0D, 0.0D, 0.0D, 0.0D, 0.0D, 1.0D, 0.0D, -1.0D, 0.0D};
   private static final double[] field_152385_i = new double[]{0.0D, 0.0D, 0.0D, 0.0D, 1.0D, 1.0D, -1.0D, -1.0D, 1.0D, 1.0D, -1.0D, -1.0D, 0.0D, 1.0D, 0.0D, -1.0D};

   public NoiseGeneratorImproved() {
      this(new Random());
   }

   public NoiseGeneratorImproved(Random p_i45469_1_) {
      this.field_76312_d = new int[512];
      this.field_76315_a = p_i45469_1_.nextDouble() * 256.0D;
      this.field_76313_b = p_i45469_1_.nextDouble() * 256.0D;
      this.field_76314_c = p_i45469_1_.nextDouble() * 256.0D;

      for(int i = 0; i < 256; this.field_76312_d[i] = i++) {
         ;
      }

      for(int l = 0; l < 256; ++l) {
         int j = p_i45469_1_.nextInt(256 - l) + l;
         int k = this.field_76312_d[l];
         this.field_76312_d[l] = this.field_76312_d[j];
         this.field_76312_d[j] = k;
         this.field_76312_d[l + 256] = this.field_76312_d[l];
      }

   }

   public final double func_76311_b(double p_76311_1_, double p_76311_3_, double p_76311_5_) {
      return p_76311_3_ + p_76311_1_ * (p_76311_5_ - p_76311_3_);
   }

   public final double func_76309_a(int p_76309_1_, double p_76309_2_, double p_76309_4_) {
      int i = p_76309_1_ & 15;
      return field_152384_h[i] * p_76309_2_ + field_152385_i[i] * p_76309_4_;
   }

   public final double func_76310_a(int p_76310_1_, double p_76310_2_, double p_76310_4_, double p_76310_6_) {
      int i = p_76310_1_ & 15;
      return field_152381_e[i] * p_76310_2_ + field_152382_f[i] * p_76310_4_ + field_152383_g[i] * p_76310_6_;
   }

   public void func_76308_a(double[] p_76308_1_, double p_76308_2_, double p_76308_4_, double p_76308_6_, int p_76308_8_, int p_76308_9_, int p_76308_10_, double p_76308_11_, double p_76308_13_, double p_76308_15_, double p_76308_17_) {
      if (p_76308_9_ == 1) {
         int i5 = 0;
         int j5 = 0;
         int j = 0;
         int k5 = 0;
         double d14 = 0.0D;
         double d15 = 0.0D;
         int l5 = 0;
         double d16 = 1.0D / p_76308_17_;

         for(int j2 = 0; j2 < p_76308_8_; ++j2) {
            double d17 = p_76308_2_ + (double)j2 * p_76308_11_ + this.field_76315_a;
            int i6 = (int)d17;
            if (d17 < (double)i6) {
               --i6;
            }

            int k2 = i6 & 255;
            d17 = d17 - (double)i6;
            double d18 = d17 * d17 * d17 * (d17 * (d17 * 6.0D - 15.0D) + 10.0D);

            for(int j6 = 0; j6 < p_76308_10_; ++j6) {
               double d19 = p_76308_6_ + (double)j6 * p_76308_15_ + this.field_76314_c;
               int k6 = (int)d19;
               if (d19 < (double)k6) {
                  --k6;
               }

               int l6 = k6 & 255;
               d19 = d19 - (double)k6;
               double d20 = d19 * d19 * d19 * (d19 * (d19 * 6.0D - 15.0D) + 10.0D);
               i5 = this.field_76312_d[k2] + 0;
               j5 = this.field_76312_d[i5] + l6;
               j = this.field_76312_d[k2 + 1] + 0;
               k5 = this.field_76312_d[j] + l6;
               d14 = this.func_76311_b(d18, this.func_76309_a(this.field_76312_d[j5], d17, d19), this.func_76310_a(this.field_76312_d[k5], d17 - 1.0D, 0.0D, d19));
               d15 = this.func_76311_b(d18, this.func_76310_a(this.field_76312_d[j5 + 1], d17, 0.0D, d19 - 1.0D), this.func_76310_a(this.field_76312_d[k5 + 1], d17 - 1.0D, 0.0D, d19 - 1.0D));
               double d21 = this.func_76311_b(d20, d14, d15);
               int i7 = l5++;
               p_76308_1_[i7] += d21 * d16;
            }
         }

      } else {
         int i = 0;
         double d0 = 1.0D / p_76308_17_;
         int k = -1;
         int l = 0;
         int i1 = 0;
         int j1 = 0;
         int k1 = 0;
         int l1 = 0;
         int i2 = 0;
         double d1 = 0.0D;
         double d2 = 0.0D;
         double d3 = 0.0D;
         double d4 = 0.0D;

         for(int l2 = 0; l2 < p_76308_8_; ++l2) {
            double d5 = p_76308_2_ + (double)l2 * p_76308_11_ + this.field_76315_a;
            int i3 = (int)d5;
            if (d5 < (double)i3) {
               --i3;
            }

            int j3 = i3 & 255;
            d5 = d5 - (double)i3;
            double d6 = d5 * d5 * d5 * (d5 * (d5 * 6.0D - 15.0D) + 10.0D);

            for(int k3 = 0; k3 < p_76308_10_; ++k3) {
               double d7 = p_76308_6_ + (double)k3 * p_76308_15_ + this.field_76314_c;
               int l3 = (int)d7;
               if (d7 < (double)l3) {
                  --l3;
               }

               int i4 = l3 & 255;
               d7 = d7 - (double)l3;
               double d8 = d7 * d7 * d7 * (d7 * (d7 * 6.0D - 15.0D) + 10.0D);

               for(int j4 = 0; j4 < p_76308_9_; ++j4) {
                  double d9 = p_76308_4_ + (double)j4 * p_76308_13_ + this.field_76313_b;
                  int k4 = (int)d9;
                  if (d9 < (double)k4) {
                     --k4;
                  }

                  int l4 = k4 & 255;
                  d9 = d9 - (double)k4;
                  double d10 = d9 * d9 * d9 * (d9 * (d9 * 6.0D - 15.0D) + 10.0D);
                  if (j4 == 0 || l4 != k) {
                     k = l4;
                     l = this.field_76312_d[j3] + l4;
                     i1 = this.field_76312_d[l] + i4;
                     j1 = this.field_76312_d[l + 1] + i4;
                     k1 = this.field_76312_d[j3 + 1] + l4;
                     l1 = this.field_76312_d[k1] + i4;
                     i2 = this.field_76312_d[k1 + 1] + i4;
                     d1 = this.func_76311_b(d6, this.func_76310_a(this.field_76312_d[i1], d5, d9, d7), this.func_76310_a(this.field_76312_d[l1], d5 - 1.0D, d9, d7));
                     d2 = this.func_76311_b(d6, this.func_76310_a(this.field_76312_d[j1], d5, d9 - 1.0D, d7), this.func_76310_a(this.field_76312_d[i2], d5 - 1.0D, d9 - 1.0D, d7));
                     d3 = this.func_76311_b(d6, this.func_76310_a(this.field_76312_d[i1 + 1], d5, d9, d7 - 1.0D), this.func_76310_a(this.field_76312_d[l1 + 1], d5 - 1.0D, d9, d7 - 1.0D));
                     d4 = this.func_76311_b(d6, this.func_76310_a(this.field_76312_d[j1 + 1], d5, d9 - 1.0D, d7 - 1.0D), this.func_76310_a(this.field_76312_d[i2 + 1], d5 - 1.0D, d9 - 1.0D, d7 - 1.0D));
                  }

                  double d11 = this.func_76311_b(d10, d1, d2);
                  double d12 = this.func_76311_b(d10, d3, d4);
                  double d13 = this.func_76311_b(d8, d11, d12);
                  int j7 = i++;
                  p_76308_1_[j7] += d13 * d0;
               }
            }
         }

      }
   }
}
