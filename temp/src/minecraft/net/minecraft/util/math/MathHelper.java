package net.minecraft.util.math;

import java.util.Random;
import java.util.UUID;

public class MathHelper {
   public static final float field_180189_a = func_76129_c(2.0F);
   private static final float[] field_76144_a = new float[65536];
   private static final Random field_188211_c = new Random();
   private static final int[] field_151242_b;
   private static final double field_181163_d;
   private static final double[] field_181164_e;
   private static final double[] field_181165_f;

   public static float func_76126_a(float p_76126_0_) {
      return field_76144_a[(int)(p_76126_0_ * 10430.378F) & '\uffff'];
   }

   public static float func_76134_b(float p_76134_0_) {
      return field_76144_a[(int)(p_76134_0_ * 10430.378F + 16384.0F) & '\uffff'];
   }

   public static float func_76129_c(float p_76129_0_) {
      return (float)Math.sqrt((double)p_76129_0_);
   }

   public static float func_76133_a(double p_76133_0_) {
      return (float)Math.sqrt(p_76133_0_);
   }

   public static int func_76141_d(float p_76141_0_) {
      int i = (int)p_76141_0_;
      return p_76141_0_ < (float)i ? i - 1 : i;
   }

   public static int func_76140_b(double p_76140_0_) {
      return (int)(p_76140_0_ + 1024.0D) - 1024;
   }

   public static int func_76128_c(double p_76128_0_) {
      int i = (int)p_76128_0_;
      return p_76128_0_ < (double)i ? i - 1 : i;
   }

   public static long func_76124_d(double p_76124_0_) {
      long i = (long)p_76124_0_;
      return p_76124_0_ < (double)i ? i - 1L : i;
   }

   public static int func_154353_e(double p_154353_0_) {
      return (int)(p_154353_0_ >= 0.0D ? p_154353_0_ : -p_154353_0_ + 1.0D);
   }

   public static float func_76135_e(float p_76135_0_) {
      return p_76135_0_ >= 0.0F ? p_76135_0_ : -p_76135_0_;
   }

   public static int func_76130_a(int p_76130_0_) {
      return p_76130_0_ >= 0 ? p_76130_0_ : -p_76130_0_;
   }

   public static int func_76123_f(float p_76123_0_) {
      int i = (int)p_76123_0_;
      return p_76123_0_ > (float)i ? i + 1 : i;
   }

   public static int func_76143_f(double p_76143_0_) {
      int i = (int)p_76143_0_;
      return p_76143_0_ > (double)i ? i + 1 : i;
   }

   public static int func_76125_a(int p_76125_0_, int p_76125_1_, int p_76125_2_) {
      if (p_76125_0_ < p_76125_1_) {
         return p_76125_1_;
      } else {
         return p_76125_0_ > p_76125_2_ ? p_76125_2_ : p_76125_0_;
      }
   }

   public static float func_76131_a(float p_76131_0_, float p_76131_1_, float p_76131_2_) {
      if (p_76131_0_ < p_76131_1_) {
         return p_76131_1_;
      } else {
         return p_76131_0_ > p_76131_2_ ? p_76131_2_ : p_76131_0_;
      }
   }

   public static double func_151237_a(double p_151237_0_, double p_151237_2_, double p_151237_4_) {
      if (p_151237_0_ < p_151237_2_) {
         return p_151237_2_;
      } else {
         return p_151237_0_ > p_151237_4_ ? p_151237_4_ : p_151237_0_;
      }
   }

   public static double func_151238_b(double p_151238_0_, double p_151238_2_, double p_151238_4_) {
      if (p_151238_4_ < 0.0D) {
         return p_151238_0_;
      } else {
         return p_151238_4_ > 1.0D ? p_151238_2_ : p_151238_0_ + (p_151238_2_ - p_151238_0_) * p_151238_4_;
      }
   }

   public static double func_76132_a(double p_76132_0_, double p_76132_2_) {
      if (p_76132_0_ < 0.0D) {
         p_76132_0_ = -p_76132_0_;
      }

      if (p_76132_2_ < 0.0D) {
         p_76132_2_ = -p_76132_2_;
      }

      return p_76132_0_ > p_76132_2_ ? p_76132_0_ : p_76132_2_;
   }

   public static int func_76137_a(int p_76137_0_, int p_76137_1_) {
      return p_76137_0_ < 0 ? -((-p_76137_0_ - 1) / p_76137_1_) - 1 : p_76137_0_ / p_76137_1_;
   }

   public static int func_76136_a(Random p_76136_0_, int p_76136_1_, int p_76136_2_) {
      return p_76136_1_ >= p_76136_2_ ? p_76136_1_ : p_76136_0_.nextInt(p_76136_2_ - p_76136_1_ + 1) + p_76136_1_;
   }

   public static float func_151240_a(Random p_151240_0_, float p_151240_1_, float p_151240_2_) {
      return p_151240_1_ >= p_151240_2_ ? p_151240_1_ : p_151240_0_.nextFloat() * (p_151240_2_ - p_151240_1_) + p_151240_1_;
   }

   public static double func_82716_a(Random p_82716_0_, double p_82716_1_, double p_82716_3_) {
      return p_82716_1_ >= p_82716_3_ ? p_82716_1_ : p_82716_0_.nextDouble() * (p_82716_3_ - p_82716_1_) + p_82716_1_;
   }

   public static double func_76127_a(long[] p_76127_0_) {
      long i = 0L;

      for(long j : p_76127_0_) {
         i += j;
      }

      return (double)i / (double)p_76127_0_.length;
   }

   public static boolean func_180185_a(float p_180185_0_, float p_180185_1_) {
      return func_76135_e(p_180185_1_ - p_180185_0_) < 1.0E-5F;
   }

   public static int func_180184_b(int p_180184_0_, int p_180184_1_) {
      return (p_180184_0_ % p_180184_1_ + p_180184_1_) % p_180184_1_;
   }

   public static float func_188207_b(float p_188207_0_, float p_188207_1_) {
      return (p_188207_0_ % p_188207_1_ + p_188207_1_) % p_188207_1_;
   }

   public static double func_191273_b(double p_191273_0_, double p_191273_2_) {
      return (p_191273_0_ % p_191273_2_ + p_191273_2_) % p_191273_2_;
   }

   public static float func_76142_g(float p_76142_0_) {
      p_76142_0_ = p_76142_0_ % 360.0F;
      if (p_76142_0_ >= 180.0F) {
         p_76142_0_ -= 360.0F;
      }

      if (p_76142_0_ < -180.0F) {
         p_76142_0_ += 360.0F;
      }

      return p_76142_0_;
   }

   public static double func_76138_g(double p_76138_0_) {
      p_76138_0_ = p_76138_0_ % 360.0D;
      if (p_76138_0_ >= 180.0D) {
         p_76138_0_ -= 360.0D;
      }

      if (p_76138_0_ < -180.0D) {
         p_76138_0_ += 360.0D;
      }

      return p_76138_0_;
   }

   public static int func_188209_b(int p_188209_0_) {
      p_188209_0_ = p_188209_0_ % 360;
      if (p_188209_0_ >= 180) {
         p_188209_0_ -= 360;
      }

      if (p_188209_0_ < -180) {
         p_188209_0_ += 360;
      }

      return p_188209_0_;
   }

   public static int func_82715_a(String p_82715_0_, int p_82715_1_) {
      try {
         return Integer.parseInt(p_82715_0_);
      } catch (Throwable var3) {
         return p_82715_1_;
      }
   }

   public static int func_82714_a(String p_82714_0_, int p_82714_1_, int p_82714_2_) {
      return Math.max(p_82714_2_, func_82715_a(p_82714_0_, p_82714_1_));
   }

   public static double func_82712_a(String p_82712_0_, double p_82712_1_) {
      try {
         return Double.parseDouble(p_82712_0_);
      } catch (Throwable var4) {
         return p_82712_1_;
      }
   }

   public static double func_82713_a(String p_82713_0_, double p_82713_1_, double p_82713_3_) {
      return Math.max(p_82713_3_, func_82712_a(p_82713_0_, p_82713_1_));
   }

   public static int func_151236_b(int p_151236_0_) {
      int i = p_151236_0_ - 1;
      i = i | i >> 1;
      i = i | i >> 2;
      i = i | i >> 4;
      i = i | i >> 8;
      i = i | i >> 16;
      return i + 1;
   }

   private static boolean func_151235_d(int p_151235_0_) {
      return p_151235_0_ != 0 && (p_151235_0_ & p_151235_0_ - 1) == 0;
   }

   public static int func_151241_e(int p_151241_0_) {
      p_151241_0_ = func_151235_d(p_151241_0_) ? p_151241_0_ : func_151236_b(p_151241_0_);
      return field_151242_b[(int)((long)p_151241_0_ * 125613361L >> 27) & 31];
   }

   public static int func_151239_c(int p_151239_0_) {
      return func_151241_e(p_151239_0_) - (func_151235_d(p_151239_0_) ? 0 : 1);
   }

   public static int func_154354_b(int p_154354_0_, int p_154354_1_) {
      if (p_154354_1_ == 0) {
         return 0;
      } else if (p_154354_0_ == 0) {
         return p_154354_1_;
      } else {
         if (p_154354_0_ < 0) {
            p_154354_1_ *= -1;
         }

         int i = p_154354_0_ % p_154354_1_;
         return i == 0 ? p_154354_0_ : p_154354_0_ + p_154354_1_ - i;
      }
   }

   public static int func_180183_b(float p_180183_0_, float p_180183_1_, float p_180183_2_) {
      return func_180181_b(func_76141_d(p_180183_0_ * 255.0F), func_76141_d(p_180183_1_ * 255.0F), func_76141_d(p_180183_2_ * 255.0F));
   }

   public static int func_180181_b(int p_180181_0_, int p_180181_1_, int p_180181_2_) {
      int lvt_3_1_ = (p_180181_0_ << 8) + p_180181_1_;
      lvt_3_1_ = (lvt_3_1_ << 8) + p_180181_2_;
      return lvt_3_1_;
   }

   public static int func_180188_d(int p_180188_0_, int p_180188_1_) {
      int i = (p_180188_0_ & 16711680) >> 16;
      int j = (p_180188_1_ & 16711680) >> 16;
      int k = (p_180188_0_ & '\uff00') >> 8;
      int l = (p_180188_1_ & '\uff00') >> 8;
      int i1 = (p_180188_0_ & 255) >> 0;
      int j1 = (p_180188_1_ & 255) >> 0;
      int k1 = (int)((float)i * (float)j / 255.0F);
      int l1 = (int)((float)k * (float)l / 255.0F);
      int i2 = (int)((float)i1 * (float)j1 / 255.0F);
      return p_180188_0_ & -16777216 | k1 << 16 | l1 << 8 | i2;
   }

   public static double func_181162_h(double p_181162_0_) {
      return p_181162_0_ - Math.floor(p_181162_0_);
   }

   public static long func_180186_a(Vec3i p_180186_0_) {
      return func_180187_c(p_180186_0_.func_177958_n(), p_180186_0_.func_177956_o(), p_180186_0_.func_177952_p());
   }

   public static long func_180187_c(int p_180187_0_, int p_180187_1_, int p_180187_2_) {
      long i = (long)(p_180187_0_ * 3129871) ^ (long)p_180187_2_ * 116129781L ^ (long)p_180187_1_;
      i = i * i * 42317861L + i * 11L;
      return i;
   }

   public static UUID func_180182_a(Random p_180182_0_) {
      long i = p_180182_0_.nextLong() & -61441L | 16384L;
      long j = p_180182_0_.nextLong() & 4611686018427387903L | Long.MIN_VALUE;
      return new UUID(i, j);
   }

   public static UUID func_188210_a() {
      return func_180182_a(field_188211_c);
   }

   public static double func_181160_c(double p_181160_0_, double p_181160_2_, double p_181160_4_) {
      return (p_181160_0_ - p_181160_2_) / (p_181160_4_ - p_181160_2_);
   }

   public static double func_181159_b(double p_181159_0_, double p_181159_2_) {
      double d0 = p_181159_2_ * p_181159_2_ + p_181159_0_ * p_181159_0_;
      if (Double.isNaN(d0)) {
         return Double.NaN;
      } else {
         boolean flag = p_181159_0_ < 0.0D;
         if (flag) {
            p_181159_0_ = -p_181159_0_;
         }

         boolean flag1 = p_181159_2_ < 0.0D;
         if (flag1) {
            p_181159_2_ = -p_181159_2_;
         }

         boolean flag2 = p_181159_0_ > p_181159_2_;
         if (flag2) {
            double d1 = p_181159_2_;
            p_181159_2_ = p_181159_0_;
            p_181159_0_ = d1;
         }

         double d9 = func_181161_i(d0);
         p_181159_2_ = p_181159_2_ * d9;
         p_181159_0_ = p_181159_0_ * d9;
         double d2 = field_181163_d + p_181159_0_;
         int i = (int)Double.doubleToRawLongBits(d2);
         double d3 = field_181164_e[i];
         double d4 = field_181165_f[i];
         double d5 = d2 - field_181163_d;
         double d6 = p_181159_0_ * d4 - p_181159_2_ * d5;
         double d7 = (6.0D + d6 * d6) * d6 * 0.16666666666666666D;
         double d8 = d3 + d7;
         if (flag2) {
            d8 = 1.5707963267948966D - d8;
         }

         if (flag1) {
            d8 = 3.141592653589793D - d8;
         }

         if (flag) {
            d8 = -d8;
         }

         return d8;
      }
   }

   public static double func_181161_i(double p_181161_0_) {
      double d0 = 0.5D * p_181161_0_;
      long i = Double.doubleToRawLongBits(p_181161_0_);
      i = 6910469410427058090L - (i >> 1);
      p_181161_0_ = Double.longBitsToDouble(i);
      p_181161_0_ = p_181161_0_ * (1.5D - d0 * p_181161_0_ * p_181161_0_);
      return p_181161_0_;
   }

   public static int func_181758_c(float p_181758_0_, float p_181758_1_, float p_181758_2_) {
      int i = (int)(p_181758_0_ * 6.0F) % 6;
      float f = p_181758_0_ * 6.0F - (float)i;
      float f1 = p_181758_2_ * (1.0F - p_181758_1_);
      float f2 = p_181758_2_ * (1.0F - f * p_181758_1_);
      float f3 = p_181758_2_ * (1.0F - (1.0F - f) * p_181758_1_);
      float f4;
      float f5;
      float f6;
      switch(i) {
      case 0:
         f4 = p_181758_2_;
         f5 = f3;
         f6 = f1;
         break;
      case 1:
         f4 = f2;
         f5 = p_181758_2_;
         f6 = f1;
         break;
      case 2:
         f4 = f1;
         f5 = p_181758_2_;
         f6 = f3;
         break;
      case 3:
         f4 = f1;
         f5 = f2;
         f6 = p_181758_2_;
         break;
      case 4:
         f4 = f3;
         f5 = f1;
         f6 = p_181758_2_;
         break;
      case 5:
         f4 = p_181758_2_;
         f5 = f1;
         f6 = f2;
         break;
      default:
         throw new RuntimeException("Something went wrong when converting from HSV to RGB. Input was " + p_181758_0_ + ", " + p_181758_1_ + ", " + p_181758_2_);
      }

      int j = func_76125_a((int)(f4 * 255.0F), 0, 255);
      int k = func_76125_a((int)(f5 * 255.0F), 0, 255);
      int l = func_76125_a((int)(f6 * 255.0F), 0, 255);
      return j << 16 | k << 8 | l;
   }

   public static int func_188208_f(int p_188208_0_) {
      p_188208_0_ = p_188208_0_ ^ p_188208_0_ >>> 16;
      p_188208_0_ = p_188208_0_ * -2048144789;
      p_188208_0_ = p_188208_0_ ^ p_188208_0_ >>> 13;
      p_188208_0_ = p_188208_0_ * -1028477387;
      p_188208_0_ = p_188208_0_ ^ p_188208_0_ >>> 16;
      return p_188208_0_;
   }

   static {
      for(int i = 0; i < 65536; ++i) {
         field_76144_a[i] = (float)Math.sin((double)i * 3.141592653589793D * 2.0D / 65536.0D);
      }

      field_151242_b = new int[]{0, 1, 28, 2, 29, 14, 24, 3, 30, 22, 20, 15, 25, 17, 4, 8, 31, 27, 13, 23, 21, 19, 16, 7, 26, 12, 18, 6, 11, 5, 10, 9};
      field_181163_d = Double.longBitsToDouble(4805340802404319232L);
      field_181164_e = new double[257];
      field_181165_f = new double[257];

      for(int j = 0; j < 257; ++j) {
         double d0 = (double)j / 256.0D;
         double d1 = Math.asin(d0);
         field_181165_f[j] = Math.cos(d1);
         field_181164_e[j] = d1;
      }

   }
}
