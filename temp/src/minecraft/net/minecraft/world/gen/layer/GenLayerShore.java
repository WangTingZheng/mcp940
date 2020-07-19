package net.minecraft.world.gen.layer;

import net.minecraft.init.Biomes;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeJungle;
import net.minecraft.world.biome.BiomeMesa;

public class GenLayerShore extends GenLayer {
   public GenLayerShore(long p_i2130_1_, GenLayer p_i2130_3_) {
      super(p_i2130_1_);
      this.field_75909_a = p_i2130_3_;
   }

   public int[] func_75904_a(int p_75904_1_, int p_75904_2_, int p_75904_3_, int p_75904_4_) {
      int[] aint = this.field_75909_a.func_75904_a(p_75904_1_ - 1, p_75904_2_ - 1, p_75904_3_ + 2, p_75904_4_ + 2);
      int[] aint1 = IntCache.func_76445_a(p_75904_3_ * p_75904_4_);

      for(int i = 0; i < p_75904_4_; ++i) {
         for(int j = 0; j < p_75904_3_; ++j) {
            this.func_75903_a((long)(j + p_75904_1_), (long)(i + p_75904_2_));
            int k = aint[j + 1 + (i + 1) * (p_75904_3_ + 2)];
            Biome biome = Biome.func_150568_d(k);
            if (k == Biome.func_185362_a(Biomes.field_76789_p)) {
               int j2 = aint[j + 1 + (i + 1 - 1) * (p_75904_3_ + 2)];
               int i3 = aint[j + 1 + 1 + (i + 1) * (p_75904_3_ + 2)];
               int l3 = aint[j + 1 - 1 + (i + 1) * (p_75904_3_ + 2)];
               int k4 = aint[j + 1 + (i + 1 + 1) * (p_75904_3_ + 2)];
               if (j2 != Biome.func_185362_a(Biomes.field_76771_b) && i3 != Biome.func_185362_a(Biomes.field_76771_b) && l3 != Biome.func_185362_a(Biomes.field_76771_b) && k4 != Biome.func_185362_a(Biomes.field_76771_b)) {
                  aint1[j + i * p_75904_3_] = k;
               } else {
                  aint1[j + i * p_75904_3_] = Biome.func_185362_a(Biomes.field_76788_q);
               }
            } else if (biome != null && biome.func_150562_l() == BiomeJungle.class) {
               int i2 = aint[j + 1 + (i + 1 - 1) * (p_75904_3_ + 2)];
               int l2 = aint[j + 1 + 1 + (i + 1) * (p_75904_3_ + 2)];
               int k3 = aint[j + 1 - 1 + (i + 1) * (p_75904_3_ + 2)];
               int j4 = aint[j + 1 + (i + 1 + 1) * (p_75904_3_ + 2)];
               if (this.func_151631_c(i2) && this.func_151631_c(l2) && this.func_151631_c(k3) && this.func_151631_c(j4)) {
                  if (!func_151618_b(i2) && !func_151618_b(l2) && !func_151618_b(k3) && !func_151618_b(j4)) {
                     aint1[j + i * p_75904_3_] = k;
                  } else {
                     aint1[j + i * p_75904_3_] = Biome.func_185362_a(Biomes.field_76787_r);
                  }
               } else {
                  aint1[j + i * p_75904_3_] = Biome.func_185362_a(Biomes.field_150574_L);
               }
            } else if (k != Biome.func_185362_a(Biomes.field_76770_e) && k != Biome.func_185362_a(Biomes.field_150580_W) && k != Biome.func_185362_a(Biomes.field_76783_v)) {
               if (biome != null && biome.func_150559_j()) {
                  this.func_151632_a(aint, aint1, j, i, p_75904_3_, k, Biome.func_185362_a(Biomes.field_150577_O));
               } else if (k != Biome.func_185362_a(Biomes.field_150589_Z) && k != Biome.func_185362_a(Biomes.field_150607_aa)) {
                  if (k != Biome.func_185362_a(Biomes.field_76771_b) && k != Biome.func_185362_a(Biomes.field_150575_M) && k != Biome.func_185362_a(Biomes.field_76781_i) && k != Biome.func_185362_a(Biomes.field_76780_h)) {
                     int l1 = aint[j + 1 + (i + 1 - 1) * (p_75904_3_ + 2)];
                     int k2 = aint[j + 1 + 1 + (i + 1) * (p_75904_3_ + 2)];
                     int j3 = aint[j + 1 - 1 + (i + 1) * (p_75904_3_ + 2)];
                     int i4 = aint[j + 1 + (i + 1 + 1) * (p_75904_3_ + 2)];
                     if (!func_151618_b(l1) && !func_151618_b(k2) && !func_151618_b(j3) && !func_151618_b(i4)) {
                        aint1[j + i * p_75904_3_] = k;
                     } else {
                        aint1[j + i * p_75904_3_] = Biome.func_185362_a(Biomes.field_76787_r);
                     }
                  } else {
                     aint1[j + i * p_75904_3_] = k;
                  }
               } else {
                  int l = aint[j + 1 + (i + 1 - 1) * (p_75904_3_ + 2)];
                  int i1 = aint[j + 1 + 1 + (i + 1) * (p_75904_3_ + 2)];
                  int j1 = aint[j + 1 - 1 + (i + 1) * (p_75904_3_ + 2)];
                  int k1 = aint[j + 1 + (i + 1 + 1) * (p_75904_3_ + 2)];
                  if (!func_151618_b(l) && !func_151618_b(i1) && !func_151618_b(j1) && !func_151618_b(k1)) {
                     if (this.func_151633_d(l) && this.func_151633_d(i1) && this.func_151633_d(j1) && this.func_151633_d(k1)) {
                        aint1[j + i * p_75904_3_] = k;
                     } else {
                        aint1[j + i * p_75904_3_] = Biome.func_185362_a(Biomes.field_76769_d);
                     }
                  } else {
                     aint1[j + i * p_75904_3_] = k;
                  }
               }
            } else {
               this.func_151632_a(aint, aint1, j, i, p_75904_3_, k, Biome.func_185362_a(Biomes.field_150576_N));
            }
         }
      }

      return aint1;
   }

   private void func_151632_a(int[] p_151632_1_, int[] p_151632_2_, int p_151632_3_, int p_151632_4_, int p_151632_5_, int p_151632_6_, int p_151632_7_) {
      if (func_151618_b(p_151632_6_)) {
         p_151632_2_[p_151632_3_ + p_151632_4_ * p_151632_5_] = p_151632_6_;
      } else {
         int i = p_151632_1_[p_151632_3_ + 1 + (p_151632_4_ + 1 - 1) * (p_151632_5_ + 2)];
         int j = p_151632_1_[p_151632_3_ + 1 + 1 + (p_151632_4_ + 1) * (p_151632_5_ + 2)];
         int k = p_151632_1_[p_151632_3_ + 1 - 1 + (p_151632_4_ + 1) * (p_151632_5_ + 2)];
         int l = p_151632_1_[p_151632_3_ + 1 + (p_151632_4_ + 1 + 1) * (p_151632_5_ + 2)];
         if (!func_151618_b(i) && !func_151618_b(j) && !func_151618_b(k) && !func_151618_b(l)) {
            p_151632_2_[p_151632_3_ + p_151632_4_ * p_151632_5_] = p_151632_6_;
         } else {
            p_151632_2_[p_151632_3_ + p_151632_4_ * p_151632_5_] = p_151632_7_;
         }

      }
   }

   private boolean func_151631_c(int p_151631_1_) {
      if (Biome.func_150568_d(p_151631_1_) != null && Biome.func_150568_d(p_151631_1_).func_150562_l() == BiomeJungle.class) {
         return true;
      } else {
         return p_151631_1_ == Biome.func_185362_a(Biomes.field_150574_L) || p_151631_1_ == Biome.func_185362_a(Biomes.field_76782_w) || p_151631_1_ == Biome.func_185362_a(Biomes.field_76792_x) || p_151631_1_ == Biome.func_185362_a(Biomes.field_76767_f) || p_151631_1_ == Biome.func_185362_a(Biomes.field_76768_g) || func_151618_b(p_151631_1_);
      }
   }

   private boolean func_151633_d(int p_151633_1_) {
      return Biome.func_150568_d(p_151633_1_) instanceof BiomeMesa;
   }
}
