package net.minecraft.util.math;

import net.minecraft.entity.Entity;

public class ChunkPos {
   public final int field_77276_a;
   public final int field_77275_b;

   public ChunkPos(int p_i1947_1_, int p_i1947_2_) {
      this.field_77276_a = p_i1947_1_;
      this.field_77275_b = p_i1947_2_;
   }

   public ChunkPos(BlockPos p_i46717_1_) {
      this.field_77276_a = p_i46717_1_.func_177958_n() >> 4;
      this.field_77275_b = p_i46717_1_.func_177952_p() >> 4;
   }

   public static long func_77272_a(int p_77272_0_, int p_77272_1_) {
      return (long)p_77272_0_ & 4294967295L | ((long)p_77272_1_ & 4294967295L) << 32;
   }

   public int hashCode() {
      int i = 1664525 * this.field_77276_a + 1013904223;
      int j = 1664525 * (this.field_77275_b ^ -559038737) + 1013904223;
      return i ^ j;
   }

   public boolean equals(Object p_equals_1_) {
      if (this == p_equals_1_) {
         return true;
      } else if (!(p_equals_1_ instanceof ChunkPos)) {
         return false;
      } else {
         ChunkPos chunkpos = (ChunkPos)p_equals_1_;
         return this.field_77276_a == chunkpos.field_77276_a && this.field_77275_b == chunkpos.field_77275_b;
      }
   }

   public double func_185327_a(Entity p_185327_1_) {
      double d0 = (double)(this.field_77276_a * 16 + 8);
      double d1 = (double)(this.field_77275_b * 16 + 8);
      double d2 = d0 - p_185327_1_.field_70165_t;
      double d3 = d1 - p_185327_1_.field_70161_v;
      return d2 * d2 + d3 * d3;
   }

   public int func_180334_c() {
      return this.field_77276_a << 4;
   }

   public int func_180333_d() {
      return this.field_77275_b << 4;
   }

   public int func_180332_e() {
      return (this.field_77276_a << 4) + 15;
   }

   public int func_180330_f() {
      return (this.field_77275_b << 4) + 15;
   }

   public BlockPos func_180331_a(int p_180331_1_, int p_180331_2_, int p_180331_3_) {
      return new BlockPos((this.field_77276_a << 4) + p_180331_1_, p_180331_2_, (this.field_77275_b << 4) + p_180331_3_);
   }

   public String toString() {
      return "[" + this.field_77276_a + ", " + this.field_77275_b + "]";
   }
}
