package net.minecraft.world.gen.layer;

import net.minecraft.init.Biomes;
import net.minecraft.world.biome.Biome;

public class GenLayerRareBiome extends GenLayer {
   public GenLayerRareBiome(long p_i45478_1_, GenLayer p_i45478_3_) {
      super(p_i45478_1_);
      this.field_75909_a = p_i45478_3_;
   }

   public int[] func_75904_a(int p_75904_1_, int p_75904_2_, int p_75904_3_, int p_75904_4_) {
      int[] aint = this.field_75909_a.func_75904_a(p_75904_1_ - 1, p_75904_2_ - 1, p_75904_3_ + 2, p_75904_4_ + 2);
      int[] aint1 = IntCache.func_76445_a(p_75904_3_ * p_75904_4_);

      for(int i = 0; i < p_75904_4_; ++i) {
         for(int j = 0; j < p_75904_3_; ++j) {
            this.func_75903_a((long)(j + p_75904_1_), (long)(i + p_75904_2_));
            int k = aint[j + 1 + (i + 1) * (p_75904_3_ + 2)];
            if (this.func_75902_a(57) == 0) {
               if (k == Biome.func_185362_a(Biomes.field_76772_c)) {
                  aint1[j + i * p_75904_3_] = Biome.func_185362_a(Biomes.field_185441_Q);
               } else {
                  aint1[j + i * p_75904_3_] = k;
               }
            } else {
               aint1[j + i * p_75904_3_] = k;
            }
         }
      }

      return aint1;
   }
}
