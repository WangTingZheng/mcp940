package net.minecraft.world.gen.layer;

public class GenLayerAddIsland extends GenLayer {
   public GenLayerAddIsland(long p_i2119_1_, GenLayer p_i2119_3_) {
      super(p_i2119_1_);
      this.field_75909_a = p_i2119_3_;
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
            int k1 = aint[j1 + 0 + (i1 + 0) * k];
            int l1 = aint[j1 + 2 + (i1 + 0) * k];
            int i2 = aint[j1 + 0 + (i1 + 2) * k];
            int j2 = aint[j1 + 2 + (i1 + 2) * k];
            int k2 = aint[j1 + 1 + (i1 + 1) * k];
            this.func_75903_a((long)(j1 + p_75904_1_), (long)(i1 + p_75904_2_));
            if (k2 != 0 || k1 == 0 && l1 == 0 && i2 == 0 && j2 == 0) {
               if (k2 > 0 && (k1 == 0 || l1 == 0 || i2 == 0 || j2 == 0)) {
                  if (this.func_75902_a(5) == 0) {
                     if (k2 == 4) {
                        aint1[j1 + i1 * p_75904_3_] = 4;
                     } else {
                        aint1[j1 + i1 * p_75904_3_] = 0;
                     }
                  } else {
                     aint1[j1 + i1 * p_75904_3_] = k2;
                  }
               } else {
                  aint1[j1 + i1 * p_75904_3_] = k2;
               }
            } else {
               int l2 = 1;
               int i3 = 1;
               if (k1 != 0 && this.func_75902_a(l2++) == 0) {
                  i3 = k1;
               }

               if (l1 != 0 && this.func_75902_a(l2++) == 0) {
                  i3 = l1;
               }

               if (i2 != 0 && this.func_75902_a(l2++) == 0) {
                  i3 = i2;
               }

               if (j2 != 0 && this.func_75902_a(l2++) == 0) {
                  i3 = j2;
               }

               if (this.func_75902_a(3) == 0) {
                  aint1[j1 + i1 * p_75904_3_] = i3;
               } else if (i3 == 4) {
                  aint1[j1 + i1 * p_75904_3_] = 4;
               } else {
                  aint1[j1 + i1 * p_75904_3_] = 0;
               }
            }
         }
      }

      return aint1;
   }
}
