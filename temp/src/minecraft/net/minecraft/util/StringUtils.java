package net.minecraft.util;

import java.util.regex.Pattern;
import javax.annotation.Nullable;

public class StringUtils {
   private static final Pattern field_76339_a = Pattern.compile("(?i)\\u00A7[0-9A-FK-OR]");

   public static String func_76337_a(int p_76337_0_) {
      int i = p_76337_0_ / 20;
      int j = i / 60;
      i = i % 60;
      return i < 10 ? j + ":0" + i : j + ":" + i;
   }

   public static String func_76338_a(String p_76338_0_) {
      return field_76339_a.matcher(p_76338_0_).replaceAll("");
   }

   public static boolean func_151246_b(@Nullable String p_151246_0_) {
      return org.apache.commons.lang3.StringUtils.isEmpty(p_151246_0_);
   }
}
