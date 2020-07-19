package net.minecraft.world;

public class ColorizerFoliage {
   private static int[] field_77471_a = new int[65536];

   public static void func_77467_a(int[] p_77467_0_) {
      field_77471_a = p_77467_0_;
   }

   public static int func_77470_a(double p_77470_0_, double p_77470_2_) {
      p_77470_2_ = p_77470_2_ * p_77470_0_;
      int i = (int)((1.0D - p_77470_0_) * 255.0D);
      int j = (int)((1.0D - p_77470_2_) * 255.0D);
      return field_77471_a[j << 8 | i];
   }

   public static int func_77466_a() {
      return 6396257;
   }

   public static int func_77469_b() {
      return 8431445;
   }

   public static int func_77468_c() {
      return 4764952;
   }
}
