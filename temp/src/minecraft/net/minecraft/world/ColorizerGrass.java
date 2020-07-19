package net.minecraft.world;

public class ColorizerGrass {
   private static int[] field_77481_a = new int[65536];

   public static void func_77479_a(int[] p_77479_0_) {
      field_77481_a = p_77479_0_;
   }

   public static int func_77480_a(double p_77480_0_, double p_77480_2_) {
      p_77480_2_ = p_77480_2_ * p_77480_0_;
      int i = (int)((1.0D - p_77480_0_) * 255.0D);
      int j = (int)((1.0D - p_77480_2_) * 255.0D);
      int k = j << 8 | i;
      return k > field_77481_a.length ? -65281 : field_77481_a[k];
   }
}
