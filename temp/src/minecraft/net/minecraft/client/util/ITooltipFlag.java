package net.minecraft.client.util;

public interface ITooltipFlag {
   boolean func_194127_a();

   public static enum TooltipFlags implements ITooltipFlag {
      NORMAL(false),
      ADVANCED(true);

      final boolean field_194131_c;

      private TooltipFlags(boolean p_i47611_3_) {
         this.field_194131_c = p_i47611_3_;
      }

      public boolean func_194127_a() {
         return this.field_194131_c;
      }
   }
}
