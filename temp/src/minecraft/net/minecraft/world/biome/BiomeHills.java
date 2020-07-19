package net.minecraft.world.biome;

import java.util.Random;
import net.minecraft.block.BlockSilverfish;
import net.minecraft.entity.passive.EntityLlama;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenTaiga2;
import net.minecraft.world.gen.feature.WorldGenerator;

public class BiomeHills extends Biome {
   private final WorldGenerator field_82915_S = new WorldGenMinable(Blocks.field_150418_aU.func_176223_P().func_177226_a(BlockSilverfish.field_176378_a, BlockSilverfish.EnumType.STONE), 9);
   private final WorldGenTaiga2 field_150634_aD = new WorldGenTaiga2(false);
   private final BiomeHills.Type field_150638_aH;

   protected BiomeHills(BiomeHills.Type p_i46710_1_, Biome.BiomeProperties p_i46710_2_) {
      super(p_i46710_2_);
      if (p_i46710_1_ == BiomeHills.Type.EXTRA_TREES) {
         this.field_76760_I.field_76832_z = 3;
      }

      this.field_76762_K.add(new Biome.SpawnListEntry(EntityLlama.class, 5, 4, 6));
      this.field_150638_aH = p_i46710_1_;
   }

   public WorldGenAbstractTree func_150567_a(Random p_150567_1_) {
      return (WorldGenAbstractTree)(p_150567_1_.nextInt(3) > 0 ? this.field_150634_aD : super.func_150567_a(p_150567_1_));
   }

   public void func_180624_a(World p_180624_1_, Random p_180624_2_, BlockPos p_180624_3_) {
      super.func_180624_a(p_180624_1_, p_180624_2_, p_180624_3_);
      int i = 3 + p_180624_2_.nextInt(6);

      for(int j = 0; j < i; ++j) {
         int k = p_180624_2_.nextInt(16);
         int l = p_180624_2_.nextInt(28) + 4;
         int i1 = p_180624_2_.nextInt(16);
         BlockPos blockpos = p_180624_3_.func_177982_a(k, l, i1);
         if (p_180624_1_.func_180495_p(blockpos).func_177230_c() == Blocks.field_150348_b) {
            p_180624_1_.func_180501_a(blockpos, Blocks.field_150412_bA.func_176223_P(), 2);
         }
      }

      for(int j1 = 0; j1 < 7; ++j1) {
         int k1 = p_180624_2_.nextInt(16);
         int l1 = p_180624_2_.nextInt(64);
         int i2 = p_180624_2_.nextInt(16);
         this.field_82915_S.func_180709_b(p_180624_1_, p_180624_2_, p_180624_3_.func_177982_a(k1, l1, i2));
      }

   }

   public void func_180622_a(World p_180622_1_, Random p_180622_2_, ChunkPrimer p_180622_3_, int p_180622_4_, int p_180622_5_, double p_180622_6_) {
      this.field_76752_A = Blocks.field_150349_c.func_176223_P();
      this.field_76753_B = Blocks.field_150346_d.func_176223_P();
      if ((p_180622_6_ < -1.0D || p_180622_6_ > 2.0D) && this.field_150638_aH == BiomeHills.Type.MUTATED) {
         this.field_76752_A = Blocks.field_150351_n.func_176223_P();
         this.field_76753_B = Blocks.field_150351_n.func_176223_P();
      } else if (p_180622_6_ > 1.0D && this.field_150638_aH != BiomeHills.Type.EXTRA_TREES) {
         this.field_76752_A = Blocks.field_150348_b.func_176223_P();
         this.field_76753_B = Blocks.field_150348_b.func_176223_P();
      }

      this.func_180628_b(p_180622_1_, p_180622_2_, p_180622_3_, p_180622_4_, p_180622_5_, p_180622_6_);
   }

   public static enum Type {
      NORMAL,
      EXTRA_TREES,
      MUTATED;
   }
}
