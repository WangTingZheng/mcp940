package net.minecraft.util.math;

import javax.annotation.Nullable;

public class Vec3d {
   public static final Vec3d field_186680_a = new Vec3d(0.0D, 0.0D, 0.0D);
   public final double field_72450_a;
   public final double field_72448_b;
   public final double field_72449_c;

   public Vec3d(double p_i47092_1_, double p_i47092_3_, double p_i47092_5_) {
      if (p_i47092_1_ == -0.0D) {
         p_i47092_1_ = 0.0D;
      }

      if (p_i47092_3_ == -0.0D) {
         p_i47092_3_ = 0.0D;
      }

      if (p_i47092_5_ == -0.0D) {
         p_i47092_5_ = 0.0D;
      }

      this.field_72450_a = p_i47092_1_;
      this.field_72448_b = p_i47092_3_;
      this.field_72449_c = p_i47092_5_;
   }

   public Vec3d(Vec3i p_i47093_1_) {
      this((double)p_i47093_1_.func_177958_n(), (double)p_i47093_1_.func_177956_o(), (double)p_i47093_1_.func_177952_p());
   }

   public Vec3d func_72444_a(Vec3d p_72444_1_) {
      return new Vec3d(p_72444_1_.field_72450_a - this.field_72450_a, p_72444_1_.field_72448_b - this.field_72448_b, p_72444_1_.field_72449_c - this.field_72449_c);
   }

   public Vec3d func_72432_b() {
      double d0 = (double)MathHelper.func_76133_a(this.field_72450_a * this.field_72450_a + this.field_72448_b * this.field_72448_b + this.field_72449_c * this.field_72449_c);
      return d0 < 1.0E-4D ? field_186680_a : new Vec3d(this.field_72450_a / d0, this.field_72448_b / d0, this.field_72449_c / d0);
   }

   public double func_72430_b(Vec3d p_72430_1_) {
      return this.field_72450_a * p_72430_1_.field_72450_a + this.field_72448_b * p_72430_1_.field_72448_b + this.field_72449_c * p_72430_1_.field_72449_c;
   }

   public Vec3d func_72431_c(Vec3d p_72431_1_) {
      return new Vec3d(this.field_72448_b * p_72431_1_.field_72449_c - this.field_72449_c * p_72431_1_.field_72448_b, this.field_72449_c * p_72431_1_.field_72450_a - this.field_72450_a * p_72431_1_.field_72449_c, this.field_72450_a * p_72431_1_.field_72448_b - this.field_72448_b * p_72431_1_.field_72450_a);
   }

   public Vec3d func_178788_d(Vec3d p_178788_1_) {
      return this.func_178786_a(p_178788_1_.field_72450_a, p_178788_1_.field_72448_b, p_178788_1_.field_72449_c);
   }

   public Vec3d func_178786_a(double p_178786_1_, double p_178786_3_, double p_178786_5_) {
      return this.func_72441_c(-p_178786_1_, -p_178786_3_, -p_178786_5_);
   }

   public Vec3d func_178787_e(Vec3d p_178787_1_) {
      return this.func_72441_c(p_178787_1_.field_72450_a, p_178787_1_.field_72448_b, p_178787_1_.field_72449_c);
   }

   public Vec3d func_72441_c(double p_72441_1_, double p_72441_3_, double p_72441_5_) {
      return new Vec3d(this.field_72450_a + p_72441_1_, this.field_72448_b + p_72441_3_, this.field_72449_c + p_72441_5_);
   }

   public double func_72438_d(Vec3d p_72438_1_) {
      double d0 = p_72438_1_.field_72450_a - this.field_72450_a;
      double d1 = p_72438_1_.field_72448_b - this.field_72448_b;
      double d2 = p_72438_1_.field_72449_c - this.field_72449_c;
      return (double)MathHelper.func_76133_a(d0 * d0 + d1 * d1 + d2 * d2);
   }

   public double func_72436_e(Vec3d p_72436_1_) {
      double d0 = p_72436_1_.field_72450_a - this.field_72450_a;
      double d1 = p_72436_1_.field_72448_b - this.field_72448_b;
      double d2 = p_72436_1_.field_72449_c - this.field_72449_c;
      return d0 * d0 + d1 * d1 + d2 * d2;
   }

   public double func_186679_c(double p_186679_1_, double p_186679_3_, double p_186679_5_) {
      double d0 = p_186679_1_ - this.field_72450_a;
      double d1 = p_186679_3_ - this.field_72448_b;
      double d2 = p_186679_5_ - this.field_72449_c;
      return d0 * d0 + d1 * d1 + d2 * d2;
   }

   public Vec3d func_186678_a(double p_186678_1_) {
      return new Vec3d(this.field_72450_a * p_186678_1_, this.field_72448_b * p_186678_1_, this.field_72449_c * p_186678_1_);
   }

   public double func_72433_c() {
      return (double)MathHelper.func_76133_a(this.field_72450_a * this.field_72450_a + this.field_72448_b * this.field_72448_b + this.field_72449_c * this.field_72449_c);
   }

   public double func_189985_c() {
      return this.field_72450_a * this.field_72450_a + this.field_72448_b * this.field_72448_b + this.field_72449_c * this.field_72449_c;
   }

   @Nullable
   public Vec3d func_72429_b(Vec3d p_72429_1_, double p_72429_2_) {
      double d0 = p_72429_1_.field_72450_a - this.field_72450_a;
      double d1 = p_72429_1_.field_72448_b - this.field_72448_b;
      double d2 = p_72429_1_.field_72449_c - this.field_72449_c;
      if (d0 * d0 < 1.0000000116860974E-7D) {
         return null;
      } else {
         double d3 = (p_72429_2_ - this.field_72450_a) / d0;
         return d3 >= 0.0D && d3 <= 1.0D ? new Vec3d(this.field_72450_a + d0 * d3, this.field_72448_b + d1 * d3, this.field_72449_c + d2 * d3) : null;
      }
   }

   @Nullable
   public Vec3d func_72435_c(Vec3d p_72435_1_, double p_72435_2_) {
      double d0 = p_72435_1_.field_72450_a - this.field_72450_a;
      double d1 = p_72435_1_.field_72448_b - this.field_72448_b;
      double d2 = p_72435_1_.field_72449_c - this.field_72449_c;
      if (d1 * d1 < 1.0000000116860974E-7D) {
         return null;
      } else {
         double d3 = (p_72435_2_ - this.field_72448_b) / d1;
         return d3 >= 0.0D && d3 <= 1.0D ? new Vec3d(this.field_72450_a + d0 * d3, this.field_72448_b + d1 * d3, this.field_72449_c + d2 * d3) : null;
      }
   }

   @Nullable
   public Vec3d func_72434_d(Vec3d p_72434_1_, double p_72434_2_) {
      double d0 = p_72434_1_.field_72450_a - this.field_72450_a;
      double d1 = p_72434_1_.field_72448_b - this.field_72448_b;
      double d2 = p_72434_1_.field_72449_c - this.field_72449_c;
      if (d2 * d2 < 1.0000000116860974E-7D) {
         return null;
      } else {
         double d3 = (p_72434_2_ - this.field_72449_c) / d2;
         return d3 >= 0.0D && d3 <= 1.0D ? new Vec3d(this.field_72450_a + d0 * d3, this.field_72448_b + d1 * d3, this.field_72449_c + d2 * d3) : null;
      }
   }

   public boolean equals(Object p_equals_1_) {
      if (this == p_equals_1_) {
         return true;
      } else if (!(p_equals_1_ instanceof Vec3d)) {
         return false;
      } else {
         Vec3d vec3d = (Vec3d)p_equals_1_;
         if (Double.compare(vec3d.field_72450_a, this.field_72450_a) != 0) {
            return false;
         } else if (Double.compare(vec3d.field_72448_b, this.field_72448_b) != 0) {
            return false;
         } else {
            return Double.compare(vec3d.field_72449_c, this.field_72449_c) == 0;
         }
      }
   }

   public int hashCode() {
      long j = Double.doubleToLongBits(this.field_72450_a);
      int i = (int)(j ^ j >>> 32);
      j = Double.doubleToLongBits(this.field_72448_b);
      i = 31 * i + (int)(j ^ j >>> 32);
      j = Double.doubleToLongBits(this.field_72449_c);
      i = 31 * i + (int)(j ^ j >>> 32);
      return i;
   }

   public String toString() {
      return "(" + this.field_72450_a + ", " + this.field_72448_b + ", " + this.field_72449_c + ")";
   }

   public Vec3d func_178789_a(float p_178789_1_) {
      float f = MathHelper.func_76134_b(p_178789_1_);
      float f1 = MathHelper.func_76126_a(p_178789_1_);
      double d0 = this.field_72450_a;
      double d1 = this.field_72448_b * (double)f + this.field_72449_c * (double)f1;
      double d2 = this.field_72449_c * (double)f - this.field_72448_b * (double)f1;
      return new Vec3d(d0, d1, d2);
   }

   public Vec3d func_178785_b(float p_178785_1_) {
      float f = MathHelper.func_76134_b(p_178785_1_);
      float f1 = MathHelper.func_76126_a(p_178785_1_);
      double d0 = this.field_72450_a * (double)f + this.field_72449_c * (double)f1;
      double d1 = this.field_72448_b;
      double d2 = this.field_72449_c * (double)f - this.field_72450_a * (double)f1;
      return new Vec3d(d0, d1, d2);
   }

   public static Vec3d func_189984_a(Vec2f p_189984_0_) {
      return func_189986_a(p_189984_0_.field_189982_i, p_189984_0_.field_189983_j);
   }

   public static Vec3d func_189986_a(float p_189986_0_, float p_189986_1_) {
      float f = MathHelper.func_76134_b(-p_189986_1_ * 0.017453292F - 3.1415927F);
      float f1 = MathHelper.func_76126_a(-p_189986_1_ * 0.017453292F - 3.1415927F);
      float f2 = -MathHelper.func_76134_b(-p_189986_0_ * 0.017453292F);
      float f3 = MathHelper.func_76126_a(-p_189986_0_ * 0.017453292F);
      return new Vec3d((double)(f1 * f2), (double)f3, (double)(f * f2));
   }
}
