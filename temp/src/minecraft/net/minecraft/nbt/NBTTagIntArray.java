package net.minecraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class NBTTagIntArray extends NBTBase {
   private int[] field_74749_a;

   NBTTagIntArray() {
   }

   public NBTTagIntArray(int[] p_i45132_1_) {
      this.field_74749_a = p_i45132_1_;
   }

   public NBTTagIntArray(List<Integer> p_i47528_1_) {
      this(func_193584_a(p_i47528_1_));
   }

   private static int[] func_193584_a(List<Integer> p_193584_0_) {
      int[] aint = new int[p_193584_0_.size()];

      for(int i = 0; i < p_193584_0_.size(); ++i) {
         Integer integer = p_193584_0_.get(i);
         aint[i] = integer == null ? 0 : integer.intValue();
      }

      return aint;
   }

   void func_74734_a(DataOutput p_74734_1_) throws IOException {
      p_74734_1_.writeInt(this.field_74749_a.length);

      for(int i : this.field_74749_a) {
         p_74734_1_.writeInt(i);
      }

   }

   void func_152446_a(DataInput p_152446_1_, int p_152446_2_, NBTSizeTracker p_152446_3_) throws IOException {
      p_152446_3_.func_152450_a(192L);
      int i = p_152446_1_.readInt();
      p_152446_3_.func_152450_a((long)(32 * i));
      this.field_74749_a = new int[i];

      for(int j = 0; j < i; ++j) {
         this.field_74749_a[j] = p_152446_1_.readInt();
      }

   }

   public byte func_74732_a() {
      return 11;
   }

   public String toString() {
      StringBuilder stringbuilder = new StringBuilder("[I;");

      for(int i = 0; i < this.field_74749_a.length; ++i) {
         if (i != 0) {
            stringbuilder.append(',');
         }

         stringbuilder.append(this.field_74749_a[i]);
      }

      return stringbuilder.append(']').toString();
   }

   public NBTTagIntArray func_74737_b() {
      int[] aint = new int[this.field_74749_a.length];
      System.arraycopy(this.field_74749_a, 0, aint, 0, this.field_74749_a.length);
      return new NBTTagIntArray(aint);
   }

   public boolean equals(Object p_equals_1_) {
      return super.equals(p_equals_1_) && Arrays.equals(this.field_74749_a, ((NBTTagIntArray)p_equals_1_).field_74749_a);
   }

   public int hashCode() {
      return super.hashCode() ^ Arrays.hashCode(this.field_74749_a);
   }

   public int[] func_150302_c() {
      return this.field_74749_a;
   }
}
