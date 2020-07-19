package net.minecraft.world.gen.layer;

public class GenLayerZoom extends GenLayer {
   public GenLayerZoom(long p_i2134_1_, GenLayer p_i2134_3_) {
      super(p_i2134_1_);
      super.field_75909_a = p_i2134_3_;
   }

   public int[] func_75904_a(int p_75904_1_, int p_75904_2_, int p_75904_3_, int p_75904_4_) {
      int i = p_75904_1_ >> 1;
      int j = p_75904_2_ >> 1;
      int k = (p_75904_3_ >> 1) + 2;
      int l = (p_75904_4_ >> 1) + 2;
      int[] aint = this.field_75909_a.func_75904_a(i, j, k, l);
      int i1 = k - 1 << 1;
      int j1 = l - 1 << 1;
      int[] aint1 = IntCache.func_76445_a(i1 * j1);

      for(int k1 = 0; k1 < l - 1; ++k1) {
         int l1 = (k1 << 1) * i1;
         int i2 = 0;
         int j2 = aint[i2 + 0 + (k1 + 0) * k];

         for(int k2 = aint[i2 + 0 + (k1 + 1) * k]; i2 < k - 1; ++i2) {
            this.func_75903_a((long)(i2 + i << 1), (long)(k1 + j << 1));
            int l2 = aint[i2 + 1 + (k1 + 0) * k];
            int i3 = aint[i2 + 1 + (k1 + 1) * k];
            aint1[l1] = j2;
            aint1[l1++ + i1] = this.func_151619_a(new int[]{j2, k2});
            aint1[l1] = this.func_151619_a(new int[]{j2, l2});
            aint1[l1++ + i1] = this.func_151617_b(j2, l2, k2, i3);
            j2 = l2;
            k2 = i3;
         }
      }

      int[] aint2 = IntCache.func_76445_a(p_75904_3_ * p_75904_4_);

      for(int j3 = 0; j3 < p_75904_4_; ++j3) {
         System.arraycopy(aint1, (j3 + (p_75904_2_ & 1)) * i1 + (p_75904_1_ & 1), aint2, j3 * p_75904_3_, p_75904_3_);
      }

      return aint2;
   }

   public static GenLayer func_75915_a(long p_75915_0_, GenLayer p_75915_2_, int p_75915_3_) {
      GenLayer genlayer = p_75915_2_;

      for(int i = 0; i < p_75915_3_; ++i) {
         genlayer = new GenLayerZoom(p_75915_0_ + (long)i, genlayer);
      }

      return genlayer;
   }
}
