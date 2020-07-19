package net.minecraft.world;

import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;

public class NextTickListEntry implements Comparable<NextTickListEntry> {
   private static long field_77177_f;
   private final Block field_151352_g;
   public final BlockPos field_180282_a;
   public long field_77180_e;
   public int field_82754_f;
   private final long field_77178_g;

   public NextTickListEntry(BlockPos p_i45745_1_, Block p_i45745_2_) {
      this.field_77178_g = (long)(field_77177_f++);
      this.field_180282_a = p_i45745_1_.func_185334_h();
      this.field_151352_g = p_i45745_2_;
   }

   public boolean equals(Object p_equals_1_) {
      if (!(p_equals_1_ instanceof NextTickListEntry)) {
         return false;
      } else {
         NextTickListEntry nextticklistentry = (NextTickListEntry)p_equals_1_;
         return this.field_180282_a.equals(nextticklistentry.field_180282_a) && Block.func_149680_a(this.field_151352_g, nextticklistentry.field_151352_g);
      }
   }

   public int hashCode() {
      return this.field_180282_a.hashCode();
   }

   public NextTickListEntry func_77176_a(long p_77176_1_) {
      this.field_77180_e = p_77176_1_;
      return this;
   }

   public void func_82753_a(int p_82753_1_) {
      this.field_82754_f = p_82753_1_;
   }

   public int compareTo(NextTickListEntry p_compareTo_1_) {
      if (this.field_77180_e < p_compareTo_1_.field_77180_e) {
         return -1;
      } else if (this.field_77180_e > p_compareTo_1_.field_77180_e) {
         return 1;
      } else if (this.field_82754_f != p_compareTo_1_.field_82754_f) {
         return this.field_82754_f - p_compareTo_1_.field_82754_f;
      } else if (this.field_77178_g < p_compareTo_1_.field_77178_g) {
         return -1;
      } else {
         return this.field_77178_g > p_compareTo_1_.field_77178_g ? 1 : 0;
      }
   }

   public String toString() {
      return Block.func_149682_b(this.field_151352_g) + ": " + this.field_180282_a + ", " + this.field_77180_e + ", " + this.field_82754_f + ", " + this.field_77178_g;
   }

   public Block func_151351_a() {
      return this.field_151352_g;
   }
}
