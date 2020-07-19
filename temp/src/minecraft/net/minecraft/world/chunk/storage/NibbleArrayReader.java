package net.minecraft.world.chunk.storage;

public class NibbleArrayReader {
   public final byte[] field_76689_a;
   private final int field_76687_b;
   private final int field_76688_c;

   public NibbleArrayReader(byte[] p_i1998_1_, int p_i1998_2_) {
      this.field_76689_a = p_i1998_1_;
      this.field_76687_b = p_i1998_2_;
      this.field_76688_c = p_i1998_2_ + 4;
   }

   public int func_76686_a(int p_76686_1_, int p_76686_2_, int p_76686_3_) {
      int i = p_76686_1_ << this.field_76688_c | p_76686_3_ << this.field_76687_b | p_76686_2_;
      int j = i >> 1;
      int k = i & 1;
      return k == 0 ? this.field_76689_a[j] & 15 : this.field_76689_a[j] >> 4 & 15;
   }
}
