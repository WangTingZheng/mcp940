package net.minecraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class NBTTagLongArray extends NBTBase {
   private long[] field_193587_b;

   NBTTagLongArray() {
   }

   public NBTTagLongArray(long[] p_i47524_1_) {
      this.field_193587_b = p_i47524_1_;
   }

   public NBTTagLongArray(List<Long> p_i47525_1_) {
      this(func_193586_a(p_i47525_1_));
   }

   private static long[] func_193586_a(List<Long> p_193586_0_) {
      long[] along = new long[p_193586_0_.size()];

      for(int i = 0; i < p_193586_0_.size(); ++i) {
         Long olong = p_193586_0_.get(i);
         along[i] = olong == null ? 0L : olong.longValue();
      }

      return along;
   }

   void func_74734_a(DataOutput p_74734_1_) throws IOException {
      p_74734_1_.writeInt(this.field_193587_b.length);

      for(long i : this.field_193587_b) {
         p_74734_1_.writeLong(i);
      }

   }

   void func_152446_a(DataInput p_152446_1_, int p_152446_2_, NBTSizeTracker p_152446_3_) throws IOException {
      p_152446_3_.func_152450_a(192L);
      int i = p_152446_1_.readInt();
      p_152446_3_.func_152450_a((long)(64 * i));
      this.field_193587_b = new long[i];

      for(int j = 0; j < i; ++j) {
         this.field_193587_b[j] = p_152446_1_.readLong();
      }

   }

   public byte func_74732_a() {
      return 12;
   }

   public String toString() {
      StringBuilder stringbuilder = new StringBuilder("[L;");

      for(int i = 0; i < this.field_193587_b.length; ++i) {
         if (i != 0) {
            stringbuilder.append(',');
         }

         stringbuilder.append(this.field_193587_b[i]).append('L');
      }

      return stringbuilder.append(']').toString();
   }

   public NBTTagLongArray func_74737_b() {
      long[] along = new long[this.field_193587_b.length];
      System.arraycopy(this.field_193587_b, 0, along, 0, this.field_193587_b.length);
      return new NBTTagLongArray(along);
   }

   public boolean equals(Object p_equals_1_) {
      return super.equals(p_equals_1_) && Arrays.equals(this.field_193587_b, ((NBTTagLongArray)p_equals_1_).field_193587_b);
   }

   public int hashCode() {
      return super.hashCode() ^ Arrays.hashCode(this.field_193587_b);
   }
}
