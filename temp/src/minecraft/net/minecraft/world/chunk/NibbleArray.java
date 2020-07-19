package net.minecraft.world.chunk;

public class NibbleArray {
   private final byte[] field_76585_a;

   public NibbleArray() {
      this.field_76585_a = new byte[2048];
   }

   public NibbleArray(byte[] p_i45646_1_) {
      this.field_76585_a = p_i45646_1_;
      if (p_i45646_1_.length != 2048) {
         throw new IllegalArgumentException("ChunkNibbleArrays should be 2048 bytes not: " + p_i45646_1_.length);
      }
   }

   public int func_76582_a(int p_76582_1_, int p_76582_2_, int p_76582_3_) {
      return this.func_177480_a(this.func_177483_b(p_76582_1_, p_76582_2_, p_76582_3_));
   }

   public void func_76581_a(int p_76581_1_, int p_76581_2_, int p_76581_3_, int p_76581_4_) {
      this.func_177482_a(this.func_177483_b(p_76581_1_, p_76581_2_, p_76581_3_), p_76581_4_);
   }

   private int func_177483_b(int p_177483_1_, int p_177483_2_, int p_177483_3_) {
      return p_177483_2_ << 8 | p_177483_3_ << 4 | p_177483_1_;
   }

   public int func_177480_a(int p_177480_1_) {
      int i = this.func_177478_c(p_177480_1_);
      return this.func_177479_b(p_177480_1_) ? this.field_76585_a[i] & 15 : this.field_76585_a[i] >> 4 & 15;
   }

   public void func_177482_a(int p_177482_1_, int p_177482_2_) {
      int i = this.func_177478_c(p_177482_1_);
      if (this.func_177479_b(p_177482_1_)) {
         this.field_76585_a[i] = (byte)(this.field_76585_a[i] & 240 | p_177482_2_ & 15);
      } else {
         this.field_76585_a[i] = (byte)(this.field_76585_a[i] & 15 | (p_177482_2_ & 15) << 4);
      }

   }

   private boolean func_177479_b(int p_177479_1_) {
      return (p_177479_1_ & 1) == 0;
   }

   private int func_177478_c(int p_177478_1_) {
      return p_177478_1_ >> 1;
   }

   public byte[] func_177481_a() {
      return this.field_76585_a;
   }
}
