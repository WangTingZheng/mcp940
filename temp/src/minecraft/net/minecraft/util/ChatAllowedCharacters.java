package net.minecraft.util;

import io.netty.util.ResourceLeakDetector;
import io.netty.util.ResourceLeakDetector.Level;

public class ChatAllowedCharacters {
   public static final Level field_184877_a = Level.DISABLED;
   public static final char[] field_189861_b = new char[]{'.', '\n', '\r', '\t', '\u0000', '\f', '`', '?', '*', '\\', '<', '>', '|', '"'};
   public static final char[] field_71567_b = new char[]{'/', '\n', '\r', '\t', '\u0000', '\f', '`', '?', '*', '\\', '<', '>', '|', '"', ':'};

   public static boolean func_71566_a(char p_71566_0_) {
      return p_71566_0_ != 167 && p_71566_0_ >= ' ' && p_71566_0_ != 127;
   }

   public static String func_71565_a(String p_71565_0_) {
      StringBuilder stringbuilder = new StringBuilder();

      for(char c0 : p_71565_0_.toCharArray()) {
         if (func_71566_a(c0)) {
            stringbuilder.append(c0);
         }
      }

      return stringbuilder.toString();
   }

   static {
      ResourceLeakDetector.setLevel(field_184877_a);
   }
}
