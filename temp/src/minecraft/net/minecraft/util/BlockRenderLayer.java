package net.minecraft.util;

public enum BlockRenderLayer {
   SOLID("Solid"),
   CUTOUT_MIPPED("Mipped Cutout"),
   CUTOUT("Cutout"),
   TRANSLUCENT("Translucent");

   private final String field_180338_e;

   private BlockRenderLayer(String p_i45755_3_) {
      this.field_180338_e = p_i45755_3_;
   }

   public String toString() {
      return this.field_180338_e;
   }
}
