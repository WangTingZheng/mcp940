package net.minecraft.world.gen.layer;

public class GenLayerAddSnow extends GenLayer {
   public GenLayerAddSnow(long p_i2121_1_, GenLayer p_i2121_3_) {
      super(p_i2121_1_);
      this.field_75909_a = p_i2121_3_;
   }

   public int[] func_75904_a(int p_75904_1_, int p_75904_2_, int p_75904_3_, int p_75904_4_) {
      int i = p_75904_1_ - 1;
      int j = p_75904_2_ - 1;
      int k = p_75904_3_ + 2;
      int l = p_75904_4_ + 2;
      int[] aint = this.field_75909_a.func_75904_a(i, j, k, l);
      int[] aint1 = IntCache.func_76445_a(p_75904_3_ * p_75904_4_);

      for(int i1 = 0; i1 < p_75904_4_; ++i1) {
         for(int j1 = 0; j1 < p_75904_3_; ++j1) {
            int k1 = aint[j1 + 1 + (i1 + 1) * k];
            this.func_75903_a((long)(j1 + p_75904_1_), (long)(i1 + p_75904_2_));
            if (k1 == 0) {
               aint1[j1 + i1 * p_75904_3_] = 0;
            } else {
               int l1 = this.func_75902_a(6);
               if (l1 == 0) {
                  l1 = 4;
               } else if (l1 <= 1) {
                  l1 = 3;
               } else {
                  l1 = 1;
               }

               aint1[j1 + i1 * p_75904_3_] = l1;
            }
         }
      }

      return aint1;
   }
}
