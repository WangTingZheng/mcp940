package net.minecraft.util;

public enum Rotation {
   NONE("rotate_0"),
   CLOCKWISE_90("rotate_90"),
   CLOCKWISE_180("rotate_180"),
   COUNTERCLOCKWISE_90("rotate_270");

   private final String field_185838_e;
   private static final String[] field_185839_f = new String[values().length];

   private Rotation(String p_i46680_3_) {
      this.field_185838_e = p_i46680_3_;
   }

   public Rotation func_185830_a(Rotation p_185830_1_) {
      switch(p_185830_1_) {
      case CLOCKWISE_180:
         switch(this) {
         case NONE:
            return CLOCKWISE_180;
         case CLOCKWISE_90:
            return COUNTERCLOCKWISE_90;
         case CLOCKWISE_180:
            return NONE;
         case COUNTERCLOCKWISE_90:
            return CLOCKWISE_90;
         }
      case COUNTERCLOCKWISE_90:
         switch(this) {
         case NONE:
            return COUNTERCLOCKWISE_90;
         case CLOCKWISE_90:
            return NONE;
         case CLOCKWISE_180:
            return CLOCKWISE_90;
         case COUNTERCLOCKWISE_90:
            return CLOCKWISE_180;
         }
      case CLOCKWISE_90:
         switch(this) {
         case NONE:
            return CLOCKWISE_90;
         case CLOCKWISE_90:
            return CLOCKWISE_180;
         case CLOCKWISE_180:
            return COUNTERCLOCKWISE_90;
         case COUNTERCLOCKWISE_90:
            return NONE;
         }
      default:
         return this;
      }
   }

   public EnumFacing func_185831_a(EnumFacing p_185831_1_) {
      if (p_185831_1_.func_176740_k() == EnumFacing.Axis.Y) {
         return p_185831_1_;
      } else {
         switch(this) {
         case CLOCKWISE_90:
            return p_185831_1_.func_176746_e();
         case CLOCKWISE_180:
            return p_185831_1_.func_176734_d();
         case COUNTERCLOCKWISE_90:
            return p_185831_1_.func_176735_f();
         default:
            return p_185831_1_;
         }
      }
   }

   public int func_185833_a(int p_185833_1_, int p_185833_2_) {
      switch(this) {
      case CLOCKWISE_90:
         return (p_185833_1_ + p_185833_2_ / 4) % p_185833_2_;
      case CLOCKWISE_180:
         return (p_185833_1_ + p_185833_2_ / 2) % p_185833_2_;
      case COUNTERCLOCKWISE_90:
         return (p_185833_1_ + p_185833_2_ * 3 / 4) % p_185833_2_;
      default:
         return p_185833_1_;
      }
   }

   static {
      int i = 0;

      for(Rotation rotation : values()) {
         field_185839_f[i++] = rotation.field_185838_e;
      }

   }
}
