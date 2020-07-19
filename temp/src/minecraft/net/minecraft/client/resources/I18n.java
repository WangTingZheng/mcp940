package net.minecraft.client.resources;

public class I18n {
   private static Locale field_135054_a;

   static void func_135051_a(Locale p_135051_0_) {
      field_135054_a = p_135051_0_;
   }

   public static String func_135052_a(String p_135052_0_, Object... p_135052_1_) {
      return field_135054_a.func_135023_a(p_135052_0_, p_135052_1_);
   }

   public static boolean func_188566_a(String p_188566_0_) {
      return field_135054_a.func_188568_a(p_188566_0_);
   }
}
