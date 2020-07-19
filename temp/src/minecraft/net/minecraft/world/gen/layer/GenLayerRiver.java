package net.minecraft.world.gen.layer;

import net.minecraft.init.Biomes;
import net.minecraft.world.biome.Biome;

public class GenLayerRiver extends GenLayer {
   public GenLayerRiver(long p_i2128_1_, GenLayer p_i2128_3_) {
      super(p_i2128_1_);
      super.field_75909_a = p_i2128_3_;
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
            int k1 = this.func_151630_c(aint[j1 + 0 + (i1 + 1) * k]);
            int l1 = this.func_151630_c(aint[j1 + 2 + (i1 + 1) * k]);
            int i2 = this.func_151630_c(aint[j1 + 1 + (i1 + 0) * k]);
            int j2 = this.func_151630_c(aint[j1 + 1 + (i1 + 2) * k]);
            int k2 = this.func_151630_c(aint[j1 + 1 + (i1 + 1) * k]);
            if (k2 == k1 && k2 == i2 && k2 == l1 && k2 == j2) {
               aint1[j1 + i1 * p_75904_3_] = -1;
            } else {
               aint1[j1 + i1 * p_75904_3_] = Biome.func_185362_a(Biomes.field_76781_i);
            }
         }
      }

      return aint1;
   }

   private int func_151630_c(int p_151630_1_) {
      return p_151630_1_ >= 2 ? 2 + (p_151630_1_ & 1) : p_151630_1_;
   }
}
