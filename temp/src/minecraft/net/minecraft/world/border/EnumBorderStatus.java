package net.minecraft.world.border;

public enum EnumBorderStatus {
   GROWING(4259712),
   SHRINKING(16724016),
   STATIONARY(2138367);

   private final int field_177767_d;

   private EnumBorderStatus(int p_i45647_3_) {
      this.field_177767_d = p_i45647_3_;
   }

   public int func_177766_a() {
      return this.field_177767_d;
   }
}
