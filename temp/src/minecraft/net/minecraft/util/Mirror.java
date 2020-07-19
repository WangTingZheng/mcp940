package net.minecraft.util;

public enum Mirror {
   NONE("no_mirror"),
   LEFT_RIGHT("mirror_left_right"),
   FRONT_BACK("mirror_front_back");

   private final String field_185807_d;
   private static final String[] field_185808_e = new String[values().length];

   private Mirror(String p_i47090_3_) {
      this.field_185807_d = p_i47090_3_;
   }

   public int func_185802_a(int p_185802_1_, int p_185802_2_) {
      int i = p_185802_2_ / 2;
      int j = p_185802_1_ > i ? p_185802_1_ - p_185802_2_ : p_185802_1_;
      switch(this) {
      case FRONT_BACK:
         return (p_185802_2_ - j) % p_185802_2_;
      case LEFT_RIGHT:
         return (i - j + p_185802_2_) % p_185802_2_;
      default:
         return p_185802_1_;
      }
   }

   public Rotation func_185800_a(EnumFacing p_185800_1_) {
      EnumFacing.Axis enumfacing$axis = p_185800_1_.func_176740_k();
      return (this != LEFT_RIGHT || enumfacing$axis != EnumFacing.Axis.Z) && (this != FRONT_BACK || enumfacing$axis != EnumFacing.Axis.X) ? Rotation.NONE : Rotation.CLOCKWISE_180;
   }

   public EnumFacing func_185803_b(EnumFacing p_185803_1_) {
      switch(this) {
      case FRONT_BACK:
         if (p_185803_1_ == EnumFacing.WEST) {
            return EnumFacing.EAST;
         } else {
            if (p_185803_1_ == EnumFacing.EAST) {
               return EnumFacing.WEST;
            }

            return p_185803_1_;
         }
      case LEFT_RIGHT:
         if (p_185803_1_ == EnumFacing.NORTH) {
            return EnumFacing.SOUTH;
         } else {
            if (p_185803_1_ == EnumFacing.SOUTH) {
               return EnumFacing.NORTH;
            }

            return p_185803_1_;
         }
      default:
         return p_185803_1_;
      }
   }

   static {
      int i = 0;

      for(Mirror mirror : values()) {
         field_185808_e[i++] = mirror.field_185807_d;
      }

   }
}
