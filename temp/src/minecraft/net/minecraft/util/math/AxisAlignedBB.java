package net.minecraft.util.math;

import com.google.common.annotations.VisibleForTesting;
import javax.annotation.Nullable;
import net.minecraft.util.EnumFacing;

public class AxisAlignedBB {
   public final double field_72340_a;
   public final double field_72338_b;
   public final double field_72339_c;
   public final double field_72336_d;
   public final double field_72337_e;
   public final double field_72334_f;

   public AxisAlignedBB(double p_i2300_1_, double p_i2300_3_, double p_i2300_5_, double p_i2300_7_, double p_i2300_9_, double p_i2300_11_) {
      this.field_72340_a = Math.min(p_i2300_1_, p_i2300_7_);
      this.field_72338_b = Math.min(p_i2300_3_, p_i2300_9_);
      this.field_72339_c = Math.min(p_i2300_5_, p_i2300_11_);
      this.field_72336_d = Math.max(p_i2300_1_, p_i2300_7_);
      this.field_72337_e = Math.max(p_i2300_3_, p_i2300_9_);
      this.field_72334_f = Math.max(p_i2300_5_, p_i2300_11_);
   }

   public AxisAlignedBB(BlockPos p_i46612_1_) {
      this((double)p_i46612_1_.func_177958_n(), (double)p_i46612_1_.func_177956_o(), (double)p_i46612_1_.func_177952_p(), (double)(p_i46612_1_.func_177958_n() + 1), (double)(p_i46612_1_.func_177956_o() + 1), (double)(p_i46612_1_.func_177952_p() + 1));
   }

   public AxisAlignedBB(BlockPos p_i45554_1_, BlockPos p_i45554_2_) {
      this((double)p_i45554_1_.func_177958_n(), (double)p_i45554_1_.func_177956_o(), (double)p_i45554_1_.func_177952_p(), (double)p_i45554_2_.func_177958_n(), (double)p_i45554_2_.func_177956_o(), (double)p_i45554_2_.func_177952_p());
   }

   public AxisAlignedBB(Vec3d p_i47144_1_, Vec3d p_i47144_2_) {
      this(p_i47144_1_.field_72450_a, p_i47144_1_.field_72448_b, p_i47144_1_.field_72449_c, p_i47144_2_.field_72450_a, p_i47144_2_.field_72448_b, p_i47144_2_.field_72449_c);
   }

   public AxisAlignedBB func_186666_e(double p_186666_1_) {
      return new AxisAlignedBB(this.field_72340_a, this.field_72338_b, this.field_72339_c, this.field_72336_d, p_186666_1_, this.field_72334_f);
   }

   public boolean equals(Object p_equals_1_) {
      if (this == p_equals_1_) {
         return true;
      } else if (!(p_equals_1_ instanceof AxisAlignedBB)) {
         return false;
      } else {
         AxisAlignedBB axisalignedbb = (AxisAlignedBB)p_equals_1_;
         if (Double.compare(axisalignedbb.field_72340_a, this.field_72340_a) != 0) {
            return false;
         } else if (Double.compare(axisalignedbb.field_72338_b, this.field_72338_b) != 0) {
            return false;
         } else if (Double.compare(axisalignedbb.field_72339_c, this.field_72339_c) != 0) {
            return false;
         } else if (Double.compare(axisalignedbb.field_72336_d, this.field_72336_d) != 0) {
            return false;
         } else if (Double.compare(axisalignedbb.field_72337_e, this.field_72337_e) != 0) {
            return false;
         } else {
            return Double.compare(axisalignedbb.field_72334_f, this.field_72334_f) == 0;
         }
      }
   }

   public int hashCode() {
      long i = Double.doubleToLongBits(this.field_72340_a);
      int j = (int)(i ^ i >>> 32);
      i = Double.doubleToLongBits(this.field_72338_b);
      j = 31 * j + (int)(i ^ i >>> 32);
      i = Double.doubleToLongBits(this.field_72339_c);
      j = 31 * j + (int)(i ^ i >>> 32);
      i = Double.doubleToLongBits(this.field_72336_d);
      j = 31 * j + (int)(i ^ i >>> 32);
      i = Double.doubleToLongBits(this.field_72337_e);
      j = 31 * j + (int)(i ^ i >>> 32);
      i = Double.doubleToLongBits(this.field_72334_f);
      j = 31 * j + (int)(i ^ i >>> 32);
      return j;
   }

   public AxisAlignedBB func_191195_a(double p_191195_1_, double p_191195_3_, double p_191195_5_) {
      double d0 = this.field_72340_a;
      double d1 = this.field_72338_b;
      double d2 = this.field_72339_c;
      double d3 = this.field_72336_d;
      double d4 = this.field_72337_e;
      double d5 = this.field_72334_f;
      if (p_191195_1_ < 0.0D) {
         d0 -= p_191195_1_;
      } else if (p_191195_1_ > 0.0D) {
         d3 -= p_191195_1_;
      }

      if (p_191195_3_ < 0.0D) {
         d1 -= p_191195_3_;
      } else if (p_191195_3_ > 0.0D) {
         d4 -= p_191195_3_;
      }

      if (p_191195_5_ < 0.0D) {
         d2 -= p_191195_5_;
      } else if (p_191195_5_ > 0.0D) {
         d5 -= p_191195_5_;
      }

      return new AxisAlignedBB(d0, d1, d2, d3, d4, d5);
   }

   public AxisAlignedBB func_72321_a(double p_72321_1_, double p_72321_3_, double p_72321_5_) {
      double d0 = this.field_72340_a;
      double d1 = this.field_72338_b;
      double d2 = this.field_72339_c;
      double d3 = this.field_72336_d;
      double d4 = this.field_72337_e;
      double d5 = this.field_72334_f;
      if (p_72321_1_ < 0.0D) {
         d0 += p_72321_1_;
      } else if (p_72321_1_ > 0.0D) {
         d3 += p_72321_1_;
      }

      if (p_72321_3_ < 0.0D) {
         d1 += p_72321_3_;
      } else if (p_72321_3_ > 0.0D) {
         d4 += p_72321_3_;
      }

      if (p_72321_5_ < 0.0D) {
         d2 += p_72321_5_;
      } else if (p_72321_5_ > 0.0D) {
         d5 += p_72321_5_;
      }

      return new AxisAlignedBB(d0, d1, d2, d3, d4, d5);
   }

   public AxisAlignedBB func_72314_b(double p_72314_1_, double p_72314_3_, double p_72314_5_) {
      double d0 = this.field_72340_a - p_72314_1_;
      double d1 = this.field_72338_b - p_72314_3_;
      double d2 = this.field_72339_c - p_72314_5_;
      double d3 = this.field_72336_d + p_72314_1_;
      double d4 = this.field_72337_e + p_72314_3_;
      double d5 = this.field_72334_f + p_72314_5_;
      return new AxisAlignedBB(d0, d1, d2, d3, d4, d5);
   }

   public AxisAlignedBB func_186662_g(double p_186662_1_) {
      return this.func_72314_b(p_186662_1_, p_186662_1_, p_186662_1_);
   }

   public AxisAlignedBB func_191500_a(AxisAlignedBB p_191500_1_) {
      double d0 = Math.max(this.field_72340_a, p_191500_1_.field_72340_a);
      double d1 = Math.max(this.field_72338_b, p_191500_1_.field_72338_b);
      double d2 = Math.max(this.field_72339_c, p_191500_1_.field_72339_c);
      double d3 = Math.min(this.field_72336_d, p_191500_1_.field_72336_d);
      double d4 = Math.min(this.field_72337_e, p_191500_1_.field_72337_e);
      double d5 = Math.min(this.field_72334_f, p_191500_1_.field_72334_f);
      return new AxisAlignedBB(d0, d1, d2, d3, d4, d5);
   }

   public AxisAlignedBB func_111270_a(AxisAlignedBB p_111270_1_) {
      double d0 = Math.min(this.field_72340_a, p_111270_1_.field_72340_a);
      double d1 = Math.min(this.field_72338_b, p_111270_1_.field_72338_b);
      double d2 = Math.min(this.field_72339_c, p_111270_1_.field_72339_c);
      double d3 = Math.max(this.field_72336_d, p_111270_1_.field_72336_d);
      double d4 = Math.max(this.field_72337_e, p_111270_1_.field_72337_e);
      double d5 = Math.max(this.field_72334_f, p_111270_1_.field_72334_f);
      return new AxisAlignedBB(d0, d1, d2, d3, d4, d5);
   }

   public AxisAlignedBB func_72317_d(double p_72317_1_, double p_72317_3_, double p_72317_5_) {
      return new AxisAlignedBB(this.field_72340_a + p_72317_1_, this.field_72338_b + p_72317_3_, this.field_72339_c + p_72317_5_, this.field_72336_d + p_72317_1_, this.field_72337_e + p_72317_3_, this.field_72334_f + p_72317_5_);
   }

   public AxisAlignedBB func_186670_a(BlockPos p_186670_1_) {
      return new AxisAlignedBB(this.field_72340_a + (double)p_186670_1_.func_177958_n(), this.field_72338_b + (double)p_186670_1_.func_177956_o(), this.field_72339_c + (double)p_186670_1_.func_177952_p(), this.field_72336_d + (double)p_186670_1_.func_177958_n(), this.field_72337_e + (double)p_186670_1_.func_177956_o(), this.field_72334_f + (double)p_186670_1_.func_177952_p());
   }

   public AxisAlignedBB func_191194_a(Vec3d p_191194_1_) {
      return this.func_72317_d(p_191194_1_.field_72450_a, p_191194_1_.field_72448_b, p_191194_1_.field_72449_c);
   }

   public double func_72316_a(AxisAlignedBB p_72316_1_, double p_72316_2_) {
      if (p_72316_1_.field_72337_e > this.field_72338_b && p_72316_1_.field_72338_b < this.field_72337_e && p_72316_1_.field_72334_f > this.field_72339_c && p_72316_1_.field_72339_c < this.field_72334_f) {
         if (p_72316_2_ > 0.0D && p_72316_1_.field_72336_d <= this.field_72340_a) {
            double d1 = this.field_72340_a - p_72316_1_.field_72336_d;
            if (d1 < p_72316_2_) {
               p_72316_2_ = d1;
            }
         } else if (p_72316_2_ < 0.0D && p_72316_1_.field_72340_a >= this.field_72336_d) {
            double d0 = this.field_72336_d - p_72316_1_.field_72340_a;
            if (d0 > p_72316_2_) {
               p_72316_2_ = d0;
            }
         }

         return p_72316_2_;
      } else {
         return p_72316_2_;
      }
   }

   public double func_72323_b(AxisAlignedBB p_72323_1_, double p_72323_2_) {
      if (p_72323_1_.field_72336_d > this.field_72340_a && p_72323_1_.field_72340_a < this.field_72336_d && p_72323_1_.field_72334_f > this.field_72339_c && p_72323_1_.field_72339_c < this.field_72334_f) {
         if (p_72323_2_ > 0.0D && p_72323_1_.field_72337_e <= this.field_72338_b) {
            double d1 = this.field_72338_b - p_72323_1_.field_72337_e;
            if (d1 < p_72323_2_) {
               p_72323_2_ = d1;
            }
         } else if (p_72323_2_ < 0.0D && p_72323_1_.field_72338_b >= this.field_72337_e) {
            double d0 = this.field_72337_e - p_72323_1_.field_72338_b;
            if (d0 > p_72323_2_) {
               p_72323_2_ = d0;
            }
         }

         return p_72323_2_;
      } else {
         return p_72323_2_;
      }
   }

   public double func_72322_c(AxisAlignedBB p_72322_1_, double p_72322_2_) {
      if (p_72322_1_.field_72336_d > this.field_72340_a && p_72322_1_.field_72340_a < this.field_72336_d && p_72322_1_.field_72337_e > this.field_72338_b && p_72322_1_.field_72338_b < this.field_72337_e) {
         if (p_72322_2_ > 0.0D && p_72322_1_.field_72334_f <= this.field_72339_c) {
            double d1 = this.field_72339_c - p_72322_1_.field_72334_f;
            if (d1 < p_72322_2_) {
               p_72322_2_ = d1;
            }
         } else if (p_72322_2_ < 0.0D && p_72322_1_.field_72339_c >= this.field_72334_f) {
            double d0 = this.field_72334_f - p_72322_1_.field_72339_c;
            if (d0 > p_72322_2_) {
               p_72322_2_ = d0;
            }
         }

         return p_72322_2_;
      } else {
         return p_72322_2_;
      }
   }

   public boolean func_72326_a(AxisAlignedBB p_72326_1_) {
      return this.func_186668_a(p_72326_1_.field_72340_a, p_72326_1_.field_72338_b, p_72326_1_.field_72339_c, p_72326_1_.field_72336_d, p_72326_1_.field_72337_e, p_72326_1_.field_72334_f);
   }

   public boolean func_186668_a(double p_186668_1_, double p_186668_3_, double p_186668_5_, double p_186668_7_, double p_186668_9_, double p_186668_11_) {
      return this.field_72340_a < p_186668_7_ && this.field_72336_d > p_186668_1_ && this.field_72338_b < p_186668_9_ && this.field_72337_e > p_186668_3_ && this.field_72339_c < p_186668_11_ && this.field_72334_f > p_186668_5_;
   }

   public boolean func_189973_a(Vec3d p_189973_1_, Vec3d p_189973_2_) {
      return this.func_186668_a(Math.min(p_189973_1_.field_72450_a, p_189973_2_.field_72450_a), Math.min(p_189973_1_.field_72448_b, p_189973_2_.field_72448_b), Math.min(p_189973_1_.field_72449_c, p_189973_2_.field_72449_c), Math.max(p_189973_1_.field_72450_a, p_189973_2_.field_72450_a), Math.max(p_189973_1_.field_72448_b, p_189973_2_.field_72448_b), Math.max(p_189973_1_.field_72449_c, p_189973_2_.field_72449_c));
   }

   public boolean func_72318_a(Vec3d p_72318_1_) {
      if (p_72318_1_.field_72450_a > this.field_72340_a && p_72318_1_.field_72450_a < this.field_72336_d) {
         if (p_72318_1_.field_72448_b > this.field_72338_b && p_72318_1_.field_72448_b < this.field_72337_e) {
            return p_72318_1_.field_72449_c > this.field_72339_c && p_72318_1_.field_72449_c < this.field_72334_f;
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   public double func_72320_b() {
      double d0 = this.field_72336_d - this.field_72340_a;
      double d1 = this.field_72337_e - this.field_72338_b;
      double d2 = this.field_72334_f - this.field_72339_c;
      return (d0 + d1 + d2) / 3.0D;
   }

   public AxisAlignedBB func_186664_h(double p_186664_1_) {
      return this.func_186662_g(-p_186664_1_);
   }

   @Nullable
   public RayTraceResult func_72327_a(Vec3d p_72327_1_, Vec3d p_72327_2_) {
      Vec3d vec3d = this.func_186671_a(this.field_72340_a, p_72327_1_, p_72327_2_);
      EnumFacing enumfacing = EnumFacing.WEST;
      Vec3d vec3d1 = this.func_186671_a(this.field_72336_d, p_72327_1_, p_72327_2_);
      if (vec3d1 != null && this.func_186661_a(p_72327_1_, vec3d, vec3d1)) {
         vec3d = vec3d1;
         enumfacing = EnumFacing.EAST;
      }

      vec3d1 = this.func_186663_b(this.field_72338_b, p_72327_1_, p_72327_2_);
      if (vec3d1 != null && this.func_186661_a(p_72327_1_, vec3d, vec3d1)) {
         vec3d = vec3d1;
         enumfacing = EnumFacing.DOWN;
      }

      vec3d1 = this.func_186663_b(this.field_72337_e, p_72327_1_, p_72327_2_);
      if (vec3d1 != null && this.func_186661_a(p_72327_1_, vec3d, vec3d1)) {
         vec3d = vec3d1;
         enumfacing = EnumFacing.UP;
      }

      vec3d1 = this.func_186665_c(this.field_72339_c, p_72327_1_, p_72327_2_);
      if (vec3d1 != null && this.func_186661_a(p_72327_1_, vec3d, vec3d1)) {
         vec3d = vec3d1;
         enumfacing = EnumFacing.NORTH;
      }

      vec3d1 = this.func_186665_c(this.field_72334_f, p_72327_1_, p_72327_2_);
      if (vec3d1 != null && this.func_186661_a(p_72327_1_, vec3d, vec3d1)) {
         vec3d = vec3d1;
         enumfacing = EnumFacing.SOUTH;
      }

      return vec3d == null ? null : new RayTraceResult(vec3d, enumfacing);
   }

   @VisibleForTesting
   boolean func_186661_a(Vec3d p_186661_1_, @Nullable Vec3d p_186661_2_, Vec3d p_186661_3_) {
      return p_186661_2_ == null || p_186661_1_.func_72436_e(p_186661_3_) < p_186661_1_.func_72436_e(p_186661_2_);
   }

   @Nullable
   @VisibleForTesting
   Vec3d func_186671_a(double p_186671_1_, Vec3d p_186671_3_, Vec3d p_186671_4_) {
      Vec3d vec3d = p_186671_3_.func_72429_b(p_186671_4_, p_186671_1_);
      return vec3d != null && this.func_186660_b(vec3d) ? vec3d : null;
   }

   @Nullable
   @VisibleForTesting
   Vec3d func_186663_b(double p_186663_1_, Vec3d p_186663_3_, Vec3d p_186663_4_) {
      Vec3d vec3d = p_186663_3_.func_72435_c(p_186663_4_, p_186663_1_);
      return vec3d != null && this.func_186667_c(vec3d) ? vec3d : null;
   }

   @Nullable
   @VisibleForTesting
   Vec3d func_186665_c(double p_186665_1_, Vec3d p_186665_3_, Vec3d p_186665_4_) {
      Vec3d vec3d = p_186665_3_.func_72434_d(p_186665_4_, p_186665_1_);
      return vec3d != null && this.func_186669_d(vec3d) ? vec3d : null;
   }

   @VisibleForTesting
   public boolean func_186660_b(Vec3d p_186660_1_) {
      return p_186660_1_.field_72448_b >= this.field_72338_b && p_186660_1_.field_72448_b <= this.field_72337_e && p_186660_1_.field_72449_c >= this.field_72339_c && p_186660_1_.field_72449_c <= this.field_72334_f;
   }

   @VisibleForTesting
   public boolean func_186667_c(Vec3d p_186667_1_) {
      return p_186667_1_.field_72450_a >= this.field_72340_a && p_186667_1_.field_72450_a <= this.field_72336_d && p_186667_1_.field_72449_c >= this.field_72339_c && p_186667_1_.field_72449_c <= this.field_72334_f;
   }

   @VisibleForTesting
   public boolean func_186669_d(Vec3d p_186669_1_) {
      return p_186669_1_.field_72450_a >= this.field_72340_a && p_186669_1_.field_72450_a <= this.field_72336_d && p_186669_1_.field_72448_b >= this.field_72338_b && p_186669_1_.field_72448_b <= this.field_72337_e;
   }

   public String toString() {
      return "box[" + this.field_72340_a + ", " + this.field_72338_b + ", " + this.field_72339_c + " -> " + this.field_72336_d + ", " + this.field_72337_e + ", " + this.field_72334_f + "]";
   }

   public boolean func_181656_b() {
      return Double.isNaN(this.field_72340_a) || Double.isNaN(this.field_72338_b) || Double.isNaN(this.field_72339_c) || Double.isNaN(this.field_72336_d) || Double.isNaN(this.field_72337_e) || Double.isNaN(this.field_72334_f);
   }

   public Vec3d func_189972_c() {
      return new Vec3d(this.field_72340_a + (this.field_72336_d - this.field_72340_a) * 0.5D, this.field_72338_b + (this.field_72337_e - this.field_72338_b) * 0.5D, this.field_72339_c + (this.field_72334_f - this.field_72339_c) * 0.5D);
   }
}
