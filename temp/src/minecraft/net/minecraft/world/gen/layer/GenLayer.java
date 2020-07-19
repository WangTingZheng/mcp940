package net.minecraft.world.gen.layer;

import net.minecraft.init.Biomes;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGeneratorSettings;

public abstract class GenLayer {
   private long field_75907_b;
   protected GenLayer field_75909_a;
   private long field_75908_c;
   protected long field_75906_d;

   public static GenLayer[] func_180781_a(long p_180781_0_, WorldType p_180781_2_, ChunkGeneratorSettings p_180781_3_) {
      GenLayer genlayer = new GenLayerIsland(1L);
      genlayer = new GenLayerFuzzyZoom(2000L, genlayer);
      GenLayer genlayeraddisland = new GenLayerAddIsland(1L, genlayer);
      GenLayer genlayerzoom = new GenLayerZoom(2001L, genlayeraddisland);
      GenLayer genlayeraddisland1 = new GenLayerAddIsland(2L, genlayerzoom);
      genlayeraddisland1 = new GenLayerAddIsland(50L, genlayeraddisland1);
      genlayeraddisland1 = new GenLayerAddIsland(70L, genlayeraddisland1);
      GenLayer genlayerremovetoomuchocean = new GenLayerRemoveTooMuchOcean(2L, genlayeraddisland1);
      GenLayer genlayeraddsnow = new GenLayerAddSnow(2L, genlayerremovetoomuchocean);
      GenLayer genlayeraddisland2 = new GenLayerAddIsland(3L, genlayeraddsnow);
      GenLayer genlayeredge = new GenLayerEdge(2L, genlayeraddisland2, GenLayerEdge.Mode.COOL_WARM);
      genlayeredge = new GenLayerEdge(2L, genlayeredge, GenLayerEdge.Mode.HEAT_ICE);
      genlayeredge = new GenLayerEdge(3L, genlayeredge, GenLayerEdge.Mode.SPECIAL);
      GenLayer genlayerzoom1 = new GenLayerZoom(2002L, genlayeredge);
      genlayerzoom1 = new GenLayerZoom(2003L, genlayerzoom1);
      GenLayer genlayeraddisland3 = new GenLayerAddIsland(4L, genlayerzoom1);
      GenLayer genlayeraddmushroomisland = new GenLayerAddMushroomIsland(5L, genlayeraddisland3);
      GenLayer genlayerdeepocean = new GenLayerDeepOcean(4L, genlayeraddmushroomisland);
      GenLayer genlayer4 = GenLayerZoom.func_75915_a(1000L, genlayerdeepocean, 0);
      int i = 4;
      int j = i;
      if (p_180781_3_ != null) {
         i = p_180781_3_.field_177780_G;
         j = p_180781_3_.field_177788_H;
      }

      if (p_180781_2_ == WorldType.field_77135_d) {
         i = 6;
      }

      GenLayer lvt_7_1_ = GenLayerZoom.func_75915_a(1000L, genlayer4, 0);
      GenLayer genlayerriverinit = new GenLayerRiverInit(100L, lvt_7_1_);
      GenLayer lvt_8_1_ = new GenLayerBiome(200L, genlayer4, p_180781_2_, p_180781_3_);
      GenLayer genlayer6 = GenLayerZoom.func_75915_a(1000L, lvt_8_1_, 2);
      GenLayer genlayerbiomeedge = new GenLayerBiomeEdge(1000L, genlayer6);
      GenLayer lvt_9_1_ = GenLayerZoom.func_75915_a(1000L, genlayerriverinit, 2);
      GenLayer genlayerhills = new GenLayerHills(1000L, genlayerbiomeedge, lvt_9_1_);
      GenLayer genlayer5 = GenLayerZoom.func_75915_a(1000L, genlayerriverinit, 2);
      genlayer5 = GenLayerZoom.func_75915_a(1000L, genlayer5, j);
      GenLayer genlayerriver = new GenLayerRiver(1L, genlayer5);
      GenLayer genlayersmooth = new GenLayerSmooth(1000L, genlayerriver);
      genlayerhills = new GenLayerRareBiome(1001L, genlayerhills);

      for(int k = 0; k < i; ++k) {
         genlayerhills = new GenLayerZoom((long)(1000 + k), genlayerhills);
         if (k == 0) {
            genlayerhills = new GenLayerAddIsland(3L, genlayerhills);
         }

         if (k == 1 || i == 1) {
            genlayerhills = new GenLayerShore(1000L, genlayerhills);
         }
      }

      GenLayer genlayersmooth1 = new GenLayerSmooth(1000L, genlayerhills);
      GenLayer genlayerrivermix = new GenLayerRiverMix(100L, genlayersmooth1, genlayersmooth);
      GenLayer genlayer3 = new GenLayerVoronoiZoom(10L, genlayerrivermix);
      genlayerrivermix.func_75905_a(p_180781_0_);
      genlayer3.func_75905_a(p_180781_0_);
      return new GenLayer[]{genlayerrivermix, genlayer3, genlayerrivermix};
   }

   public GenLayer(long p_i2125_1_) {
      this.field_75906_d = p_i2125_1_;
      this.field_75906_d *= this.field_75906_d * 6364136223846793005L + 1442695040888963407L;
      this.field_75906_d += p_i2125_1_;
      this.field_75906_d *= this.field_75906_d * 6364136223846793005L + 1442695040888963407L;
      this.field_75906_d += p_i2125_1_;
      this.field_75906_d *= this.field_75906_d * 6364136223846793005L + 1442695040888963407L;
      this.field_75906_d += p_i2125_1_;
   }

   public void func_75905_a(long p_75905_1_) {
      this.field_75907_b = p_75905_1_;
      if (this.field_75909_a != null) {
         this.field_75909_a.func_75905_a(p_75905_1_);
      }

      this.field_75907_b *= this.field_75907_b * 6364136223846793005L + 1442695040888963407L;
      this.field_75907_b += this.field_75906_d;
      this.field_75907_b *= this.field_75907_b * 6364136223846793005L + 1442695040888963407L;
      this.field_75907_b += this.field_75906_d;
      this.field_75907_b *= this.field_75907_b * 6364136223846793005L + 1442695040888963407L;
      this.field_75907_b += this.field_75906_d;
   }

   public void func_75903_a(long p_75903_1_, long p_75903_3_) {
      this.field_75908_c = this.field_75907_b;
      this.field_75908_c *= this.field_75908_c * 6364136223846793005L + 1442695040888963407L;
      this.field_75908_c += p_75903_1_;
      this.field_75908_c *= this.field_75908_c * 6364136223846793005L + 1442695040888963407L;
      this.field_75908_c += p_75903_3_;
      this.field_75908_c *= this.field_75908_c * 6364136223846793005L + 1442695040888963407L;
      this.field_75908_c += p_75903_1_;
      this.field_75908_c *= this.field_75908_c * 6364136223846793005L + 1442695040888963407L;
      this.field_75908_c += p_75903_3_;
   }

   protected int func_75902_a(int p_75902_1_) {
      int i = (int)((this.field_75908_c >> 24) % (long)p_75902_1_);
      if (i < 0) {
         i += p_75902_1_;
      }

      this.field_75908_c *= this.field_75908_c * 6364136223846793005L + 1442695040888963407L;
      this.field_75908_c += this.field_75907_b;
      return i;
   }

   public abstract int[] func_75904_a(int var1, int var2, int var3, int var4);

   protected static boolean func_151616_a(int p_151616_0_, int p_151616_1_) {
      if (p_151616_0_ == p_151616_1_) {
         return true;
      } else {
         Biome biome = Biome.func_150568_d(p_151616_0_);
         Biome biome1 = Biome.func_150568_d(p_151616_1_);
         if (biome != null && biome1 != null) {
            if (biome != Biomes.field_150607_aa && biome != Biomes.field_150608_ab) {
               return biome == biome1 || biome.func_150562_l() == biome1.func_150562_l();
            } else {
               return biome1 == Biomes.field_150607_aa || biome1 == Biomes.field_150608_ab;
            }
         } else {
            return false;
         }
      }
   }

   protected static boolean func_151618_b(int p_151618_0_) {
      Biome biome = Biome.func_150568_d(p_151618_0_);
      return biome == Biomes.field_76771_b || biome == Biomes.field_150575_M || biome == Biomes.field_76776_l;
   }

   protected int func_151619_a(int... p_151619_1_) {
      return p_151619_1_[this.func_75902_a(p_151619_1_.length)];
   }

   protected int func_151617_b(int p_151617_1_, int p_151617_2_, int p_151617_3_, int p_151617_4_) {
      if (p_151617_2_ == p_151617_3_ && p_151617_3_ == p_151617_4_) {
         return p_151617_2_;
      } else if (p_151617_1_ == p_151617_2_ && p_151617_1_ == p_151617_3_) {
         return p_151617_1_;
      } else if (p_151617_1_ == p_151617_2_ && p_151617_1_ == p_151617_4_) {
         return p_151617_1_;
      } else if (p_151617_1_ == p_151617_3_ && p_151617_1_ == p_151617_4_) {
         return p_151617_1_;
      } else if (p_151617_1_ == p_151617_2_ && p_151617_3_ != p_151617_4_) {
         return p_151617_1_;
      } else if (p_151617_1_ == p_151617_3_ && p_151617_2_ != p_151617_4_) {
         return p_151617_1_;
      } else if (p_151617_1_ == p_151617_4_ && p_151617_2_ != p_151617_3_) {
         return p_151617_1_;
      } else if (p_151617_2_ == p_151617_3_ && p_151617_1_ != p_151617_4_) {
         return p_151617_2_;
      } else if (p_151617_2_ == p_151617_4_ && p_151617_1_ != p_151617_3_) {
         return p_151617_2_;
      } else {
         return p_151617_3_ == p_151617_4_ && p_151617_1_ != p_151617_2_ ? p_151617_3_ : this.func_151619_a(p_151617_1_, p_151617_2_, p_151617_3_, p_151617_4_);
      }
   }
}
