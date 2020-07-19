package net.minecraft.util.math;

import net.minecraft.nbt.NBTTagFloat;
import net.minecraft.nbt.NBTTagList;

public class Rotations {
   protected final float field_179419_a;
   protected final float field_179417_b;
   protected final float field_179418_c;

   public Rotations(float p_i46009_1_, float p_i46009_2_, float p_i46009_3_) {
      this.field_179419_a = !Float.isInfinite(p_i46009_1_) && !Float.isNaN(p_i46009_1_) ? p_i46009_1_ % 360.0F : 0.0F;
      this.field_179417_b = !Float.isInfinite(p_i46009_2_) && !Float.isNaN(p_i46009_2_) ? p_i46009_2_ % 360.0F : 0.0F;
      this.field_179418_c = !Float.isInfinite(p_i46009_3_) && !Float.isNaN(p_i46009_3_) ? p_i46009_3_ % 360.0F : 0.0F;
   }

   public Rotations(NBTTagList p_i46010_1_) {
      this(p_i46010_1_.func_150308_e(0), p_i46010_1_.func_150308_e(1), p_i46010_1_.func_150308_e(2));
   }

   public NBTTagList func_179414_a() {
      NBTTagList nbttaglist = new NBTTagList();
      nbttaglist.func_74742_a(new NBTTagFloat(this.field_179419_a));
      nbttaglist.func_74742_a(new NBTTagFloat(this.field_179417_b));
      nbttaglist.func_74742_a(new NBTTagFloat(this.field_179418_c));
      return nbttaglist;
   }

   public boolean equals(Object p_equals_1_) {
      if (!(p_equals_1_ instanceof Rotations)) {
         return false;
      } else {
         Rotations rotations = (Rotations)p_equals_1_;
         return this.field_179419_a == rotations.field_179419_a && this.field_179417_b == rotations.field_179417_b && this.field_179418_c == rotations.field_179418_c;
      }
   }

   public float func_179415_b() {
      return this.field_179419_a;
   }

   public float func_179416_c() {
      return this.field_179417_b;
   }

   public float func_179413_d() {
      return this.field_179418_c;
   }
}
