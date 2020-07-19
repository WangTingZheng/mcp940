package net.minecraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import net.minecraft.util.math.MathHelper;

public class NBTTagDouble extends NBTPrimitive {
   private double field_74755_a;

   NBTTagDouble() {
   }

   public NBTTagDouble(double p_i45130_1_) {
      this.field_74755_a = p_i45130_1_;
   }

   void func_74734_a(DataOutput p_74734_1_) throws IOException {
      p_74734_1_.writeDouble(this.field_74755_a);
   }

   void func_152446_a(DataInput p_152446_1_, int p_152446_2_, NBTSizeTracker p_152446_3_) throws IOException {
      p_152446_3_.func_152450_a(128L);
      this.field_74755_a = p_152446_1_.readDouble();
   }

   public byte func_74732_a() {
      return 6;
   }

   public String toString() {
      return this.field_74755_a + "d";
   }

   public NBTTagDouble func_74737_b() {
      return new NBTTagDouble(this.field_74755_a);
   }

   public boolean equals(Object p_equals_1_) {
      return super.equals(p_equals_1_) && this.field_74755_a == ((NBTTagDouble)p_equals_1_).field_74755_a;
   }

   public int hashCode() {
      long i = Double.doubleToLongBits(this.field_74755_a);
      return super.hashCode() ^ (int)(i ^ i >>> 32);
   }

   public long func_150291_c() {
      return (long)Math.floor(this.field_74755_a);
   }

   public int func_150287_d() {
      return MathHelper.func_76128_c(this.field_74755_a);
   }

   public short func_150289_e() {
      return (short)(MathHelper.func_76128_c(this.field_74755_a) & '\uffff');
   }

   public byte func_150290_f() {
      return (byte)(MathHelper.func_76128_c(this.field_74755_a) & 255);
   }

   public double func_150286_g() {
      return this.field_74755_a;
   }

   public float func_150288_h() {
      return (float)this.field_74755_a;
   }
}
