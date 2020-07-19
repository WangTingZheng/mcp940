package net.minecraft.pathfinding;

import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.MathHelper;

public class PathPoint {
   public final int field_75839_a;
   public final int field_75837_b;
   public final int field_75838_c;
   private final int field_75840_j;
   public int field_75835_d = -1;
   public float field_75836_e;
   public float field_75833_f;
   public float field_75834_g;
   public PathPoint field_75841_h;
   public boolean field_75842_i;
   public float field_186284_j;
   public float field_186285_k;
   public float field_186286_l;
   public PathNodeType field_186287_m = PathNodeType.BLOCKED;

   public PathPoint(int p_i2135_1_, int p_i2135_2_, int p_i2135_3_) {
      this.field_75839_a = p_i2135_1_;
      this.field_75837_b = p_i2135_2_;
      this.field_75838_c = p_i2135_3_;
      this.field_75840_j = func_75830_a(p_i2135_1_, p_i2135_2_, p_i2135_3_);
   }

   public PathPoint func_186283_a(int p_186283_1_, int p_186283_2_, int p_186283_3_) {
      PathPoint pathpoint = new PathPoint(p_186283_1_, p_186283_2_, p_186283_3_);
      pathpoint.field_75835_d = this.field_75835_d;
      pathpoint.field_75836_e = this.field_75836_e;
      pathpoint.field_75833_f = this.field_75833_f;
      pathpoint.field_75834_g = this.field_75834_g;
      pathpoint.field_75841_h = this.field_75841_h;
      pathpoint.field_75842_i = this.field_75842_i;
      pathpoint.field_186284_j = this.field_186284_j;
      pathpoint.field_186285_k = this.field_186285_k;
      pathpoint.field_186286_l = this.field_186286_l;
      pathpoint.field_186287_m = this.field_186287_m;
      return pathpoint;
   }

   public static int func_75830_a(int p_75830_0_, int p_75830_1_, int p_75830_2_) {
      return p_75830_1_ & 255 | (p_75830_0_ & 32767) << 8 | (p_75830_2_ & 32767) << 24 | (p_75830_0_ < 0 ? Integer.MIN_VALUE : 0) | (p_75830_2_ < 0 ? '\u8000' : 0);
   }

   public float func_75829_a(PathPoint p_75829_1_) {
      float f = (float)(p_75829_1_.field_75839_a - this.field_75839_a);
      float f1 = (float)(p_75829_1_.field_75837_b - this.field_75837_b);
      float f2 = (float)(p_75829_1_.field_75838_c - this.field_75838_c);
      return MathHelper.func_76129_c(f * f + f1 * f1 + f2 * f2);
   }

   public float func_75832_b(PathPoint p_75832_1_) {
      float f = (float)(p_75832_1_.field_75839_a - this.field_75839_a);
      float f1 = (float)(p_75832_1_.field_75837_b - this.field_75837_b);
      float f2 = (float)(p_75832_1_.field_75838_c - this.field_75838_c);
      return f * f + f1 * f1 + f2 * f2;
   }

   public float func_186281_c(PathPoint p_186281_1_) {
      float f = (float)Math.abs(p_186281_1_.field_75839_a - this.field_75839_a);
      float f1 = (float)Math.abs(p_186281_1_.field_75837_b - this.field_75837_b);
      float f2 = (float)Math.abs(p_186281_1_.field_75838_c - this.field_75838_c);
      return f + f1 + f2;
   }

   public boolean equals(Object p_equals_1_) {
      if (!(p_equals_1_ instanceof PathPoint)) {
         return false;
      } else {
         PathPoint pathpoint = (PathPoint)p_equals_1_;
         return this.field_75840_j == pathpoint.field_75840_j && this.field_75839_a == pathpoint.field_75839_a && this.field_75837_b == pathpoint.field_75837_b && this.field_75838_c == pathpoint.field_75838_c;
      }
   }

   public int hashCode() {
      return this.field_75840_j;
   }

   public boolean func_75831_a() {
      return this.field_75835_d >= 0;
   }

   public String toString() {
      return this.field_75839_a + ", " + this.field_75837_b + ", " + this.field_75838_c;
   }

   public static PathPoint func_186282_b(PacketBuffer p_186282_0_) {
      PathPoint pathpoint = new PathPoint(p_186282_0_.readInt(), p_186282_0_.readInt(), p_186282_0_.readInt());
      pathpoint.field_186284_j = p_186282_0_.readFloat();
      pathpoint.field_186285_k = p_186282_0_.readFloat();
      pathpoint.field_186286_l = p_186282_0_.readFloat();
      pathpoint.field_75842_i = p_186282_0_.readBoolean();
      pathpoint.field_186287_m = PathNodeType.values()[p_186282_0_.readInt()];
      pathpoint.field_75834_g = p_186282_0_.readFloat();
      return pathpoint;
   }
}
