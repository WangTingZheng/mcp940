package net.minecraft.util.text;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;
import javax.annotation.Nullable;

public enum TextFormatting {
   BLACK("BLACK", '0', 0),
   DARK_BLUE("DARK_BLUE", '1', 1),
   DARK_GREEN("DARK_GREEN", '2', 2),
   DARK_AQUA("DARK_AQUA", '3', 3),
   DARK_RED("DARK_RED", '4', 4),
   DARK_PURPLE("DARK_PURPLE", '5', 5),
   GOLD("GOLD", '6', 6),
   GRAY("GRAY", '7', 7),
   DARK_GRAY("DARK_GRAY", '8', 8),
   BLUE("BLUE", '9', 9),
   GREEN("GREEN", 'a', 10),
   AQUA("AQUA", 'b', 11),
   RED("RED", 'c', 12),
   LIGHT_PURPLE("LIGHT_PURPLE", 'd', 13),
   YELLOW("YELLOW", 'e', 14),
   WHITE("WHITE", 'f', 15),
   OBFUSCATED("OBFUSCATED", 'k', true),
   BOLD("BOLD", 'l', true),
   STRIKETHROUGH("STRIKETHROUGH", 'm', true),
   UNDERLINE("UNDERLINE", 'n', true),
   ITALIC("ITALIC", 'o', true),
   RESET("RESET", 'r', -1);

   private static final Map<String, TextFormatting> field_96331_x = Maps.<String, TextFormatting>newHashMap();
   private static final Pattern field_96330_y = Pattern.compile("(?i)\u00a7[0-9A-FK-OR]");
   private final String field_175748_y;
   private final char field_96329_z;
   private final boolean field_96303_A;
   private final String field_96304_B;
   private final int field_175747_C;

   private static String func_175745_c(String p_175745_0_) {
      return p_175745_0_.toLowerCase(Locale.ROOT).replaceAll("[^a-z]", "");
   }

   private TextFormatting(String p_i46291_3_, char p_i46291_4_, int p_i46291_5_) {
      this(p_i46291_3_, p_i46291_4_, false, p_i46291_5_);
   }

   private TextFormatting(String p_i46292_3_, char p_i46292_4_, boolean p_i46292_5_) {
      this(p_i46292_3_, p_i46292_4_, p_i46292_5_, -1);
   }

   private TextFormatting(String p_i46293_3_, char p_i46293_4_, boolean p_i46293_5_, int p_i46293_6_) {
      this.field_175748_y = p_i46293_3_;
      this.field_96329_z = p_i46293_4_;
      this.field_96303_A = p_i46293_5_;
      this.field_175747_C = p_i46293_6_;
      this.field_96304_B = "\u00a7" + p_i46293_4_;
   }

   public int func_175746_b() {
      return this.field_175747_C;
   }

   public boolean func_96301_b() {
      return this.field_96303_A;
   }

   public boolean func_96302_c() {
      return !this.field_96303_A && this != RESET;
   }

   public String func_96297_d() {
      return this.name().toLowerCase(Locale.ROOT);
   }

   public String toString() {
      return this.field_96304_B;
   }

   @Nullable
   public static String func_110646_a(@Nullable String p_110646_0_) {
      return p_110646_0_ == null ? null : field_96330_y.matcher(p_110646_0_).replaceAll("");
   }

   @Nullable
   public static TextFormatting func_96300_b(@Nullable String p_96300_0_) {
      return p_96300_0_ == null ? null : (TextFormatting)field_96331_x.get(func_175745_c(p_96300_0_));
   }

   @Nullable
   public static TextFormatting func_175744_a(int p_175744_0_) {
      if (p_175744_0_ < 0) {
         return RESET;
      } else {
         for(TextFormatting textformatting : values()) {
            if (textformatting.func_175746_b() == p_175744_0_) {
               return textformatting;
            }
         }

         return null;
      }
   }

   public static Collection<String> func_96296_a(boolean p_96296_0_, boolean p_96296_1_) {
      List<String> list = Lists.<String>newArrayList();

      for(TextFormatting textformatting : values()) {
         if ((!textformatting.func_96302_c() || p_96296_0_) && (!textformatting.func_96301_b() || p_96296_1_)) {
            list.add(textformatting.func_96297_d());
         }
      }

      return list;
   }

   static {
      for(TextFormatting textformatting : values()) {
         field_96331_x.put(func_175745_c(textformatting.field_175748_y), textformatting);
      }

   }
}
