package net.minecraft.world.gen.layer;

import net.minecraft.init.Biomes;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGeneratorSettings;

public class GenLayerBiome extends GenLayer {
   private Biome[] field_151623_c = new Biome[]{Biomes.field_76769_d, Biomes.field_76769_d, Biomes.field_76769_d, Biomes.field_150588_X, Biomes.field_150588_X, Biomes.field_76772_c};
   private final Biome[] field_151621_d = new Biome[]{Biomes.field_76767_f, Biomes.field_150585_R, Biomes.field_76770_e, Biomes.field_76772_c, Biomes.field_150583_P, Biomes.field_76780_h};
   private final Biome[] field_151622_e = new Biome[]{Biomes.field_76767_f, Biomes.field_76770_e, Biomes.field_76768_g, Biomes.field_76772_c};
   private final Biome[] field_151620_f = new Biome[]{Biomes.field_76774_n, Biomes.field_76774_n, Biomes.field_76774_n, Biomes.field_150584_S};
   private final ChunkGeneratorSettings field_175973_g;

   public GenLayerBiome(long p_i45560_1_, GenLayer p_i45560_3_, WorldType p_i45560_4_, ChunkGeneratorSettings p_i45560_5_) {
      super(p_i45560_1_);
      this.field_75909_a = p_i45560_3_;
      if (p_i45560_4_ == WorldType.field_77136_e) {
         this.field_151623_c = new Biome[]{Biomes.field_76769_d, Biomes.field_76767_f, Biomes.field_76770_e, Biomes.field_76780_h, Biomes.field_76772_c, Biomes.field_76768_g};
         this.field_175973_g = null;
      } else {
         this.field_175973_g = p_i45560_5_;
      }

   }

   public int[] func_75904_a(int p_75904_1_, int p_75904_2_, int p_75904_3_, int p_75904_4_) {
      int[] aint = this.field_75909_a.func_75904_a(p_75904_1_, p_75904_2_, p_75904_3_, p_75904_4_);
      int[] aint1 = IntCache.func_76445_a(p_75904_3_ * p_75904_4_);

      for(int i = 0; i < p_75904_4_; ++i) {
         for(int j = 0; j < p_75904_3_; ++j) {
            this.func_75903_a((long)(j + p_75904_1_), (long)(i + p_75904_2_));
            int k = aint[j + i * p_75904_3_];
            int l = (k & 3840) >> 8;
            k = k & -3841;
            if (this.field_175973_g != null && this.field_175973_g.field_177779_F >= 0) {
               aint1[j + i * p_75904_3_] = this.field_175973_g.field_177779_F;
            } else if (func_151618_b(k)) {
               aint1[j + i * p_75904_3_] = k;
            } else if (k == Biome.func_185362_a(Biomes.field_76789_p)) {
               aint1[j + i * p_75904_3_] = k;
            } else if (k == 1) {
               if (l > 0) {
                  if (this.func_75902_a(3) == 0) {
                     aint1[j + i * p_75904_3_] = Biome.func_185362_a(Biomes.field_150608_ab);
                  } else {
                     aint1[j + i * p_75904_3_] = Biome.func_185362_a(Biomes.field_150607_aa);
                  }
               } else {
                  aint1[j + i * p_75904_3_] = Biome.func_185362_a(this.field_151623_c[this.func_75902_a(this.field_151623_c.length)]);
               }
            } else if (k == 2) {
               if (l > 0) {
                  aint1[j + i * p_75904_3_] = Biome.func_185362_a(Biomes.field_76782_w);
               } else {
                  aint1[j + i * p_75904_3_] = Biome.func_185362_a(this.field_151621_d[this.func_75902_a(this.field_151621_d.length)]);
               }
            } else if (k == 3) {
               if (l > 0) {
                  aint1[j + i * p_75904_3_] = Biome.func_185362_a(Biomes.field_150578_U);
               } else {
                  aint1[j + i * p_75904_3_] = Biome.func_185362_a(this.field_151622_e[this.func_75902_a(this.field_151622_e.length)]);
               }
            } else if (k == 4) {
               aint1[j + i * p_75904_3_] = Biome.func_185362_a(this.field_151620_f[this.func_75902_a(this.field_151620_f.length)]);
            } else {
               aint1[j + i * p_75904_3_] = Biome.func_185362_a(Biomes.field_76789_p);
            }
         }
      }

      return aint1;
   }
}
