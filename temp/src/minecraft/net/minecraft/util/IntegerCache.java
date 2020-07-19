package net.minecraft.util;

public class IntegerCache {
   private static final Integer[] field_181757_a = new Integer['\uffff'];

   public static Integer func_181756_a(int p_181756_0_) {
      return p_181756_0_ > 0 && p_181756_0_ < field_181757_a.length ? field_181757_a[p_181756_0_] : p_181756_0_;
   }

   static {
      int i = 0;

      for(int j = field_181757_a.length; i < j; ++i) {
         field_181757_a[i] = i;
      }

   }
}
