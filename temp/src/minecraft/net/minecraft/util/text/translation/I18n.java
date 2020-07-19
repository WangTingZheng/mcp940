package net.minecraft.util.text.translation;

@Deprecated
public class I18n {
   private static final LanguageMap field_74839_a = LanguageMap.func_74808_a();
   private static final LanguageMap field_150828_b = new LanguageMap();

   @Deprecated
   public static String func_74838_a(String p_74838_0_) {
      return field_74839_a.func_74805_b(p_74838_0_);
   }

   @Deprecated
   public static String func_74837_a(String p_74837_0_, Object... p_74837_1_) {
      return field_74839_a.func_74803_a(p_74837_0_, p_74837_1_);
   }

   @Deprecated
   public static String func_150826_b(String p_150826_0_) {
      return field_150828_b.func_74805_b(p_150826_0_);
   }

   @Deprecated
   public static boolean func_94522_b(String p_94522_0_) {
      return field_74839_a.func_94520_b(p_94522_0_);
   }

   public static long func_150827_a() {
      return field_74839_a.func_150510_c();
   }
}
