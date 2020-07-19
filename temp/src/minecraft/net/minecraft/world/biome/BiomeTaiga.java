package net.minecraft.world.biome;

import java.util.Random;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.entity.passive.EntityRabbit;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenBlockBlob;
import net.minecraft.world.gen.feature.WorldGenMegaPineTree;
import net.minecraft.world.gen.feature.WorldGenTaiga1;
import net.minecraft.world.gen.feature.WorldGenTaiga2;
import net.minecraft.world.gen.feature.WorldGenTallGrass;
import net.minecraft.world.gen.feature.WorldGenerator;

public class BiomeTaiga extends Biome {
   private static final WorldGenTaiga1 field_150639_aC = new WorldGenTaiga1();
   private static final WorldGenTaiga2 field_150640_aD = new WorldGenTaiga2(false);
   private static final WorldGenMegaPineTree field_150641_aE = new WorldGenMegaPineTree(false, false);
   private static final WorldGenMegaPineTree field_150642_aF = new WorldGenMegaPineTree(false, true);
   private static final WorldGenBlockBlob field_150643_aG = new WorldGenBlockBlob(Blocks.field_150341_Y, 0);
   private final BiomeTaiga.Type field_150644_aH;

   public BiomeTaiga(BiomeTaiga.Type p_i46694_1_, Biome.BiomeProperties p_i46694_2_) {
      super(p_i46694_2_);
      this.field_150644_aH = p_i46694_1_;
      this.field_76762_K.add(new Biome.SpawnListEntry(EntityWolf.class, 8, 4, 4));
      this.field_76762_K.add(new Biome.SpawnListEntry(EntityRabbit.class, 4, 2, 3));
      this.field_76760_I.field_76832_z = 10;
      if (p_i46694_1_ != BiomeTaiga.Type.MEGA && p_i46694_1_ != BiomeTaiga.Type.MEGA_SPRUCE) {
         this.field_76760_I.field_76803_B = 1;
         this.field_76760_I.field_76798_D = 1;
      } else {
         this.field_76760_I.field_76803_B = 7;
         this.field_76760_I.field_76804_C = 1;
         this.field_76760_I.field_76798_D = 3;
      }

   }

   public WorldGenAbstractTree func_150567_a(Random p_150567_1_) {
      if ((this.field_150644_aH == BiomeTaiga.Type.MEGA || this.field_150644_aH == BiomeTaiga.Type.MEGA_SPRUCE) && p_150567_1_.nextInt(3) == 0) {
         return this.field_150644_aH != BiomeTaiga.Type.MEGA_SPRUCE && p_150567_1_.nextInt(13) != 0 ? field_150641_aE : field_150642_aF;
      } else {
         return (WorldGenAbstractTree)(p_150567_1_.nextInt(3) == 0 ? field_150639_aC : field_150640_aD);
      }
   }

   public WorldGenerator func_76730_b(Random p_76730_1_) {
      return p_76730_1_.nextInt(5) > 0 ? new WorldGenTallGrass(BlockTallGrass.EnumType.FERN) : new WorldGenTallGrass(BlockTallGrass.EnumType.GRASS);
   }

   public void func_180624_a(World p_180624_1_, Random p_180624_2_, BlockPos p_180624_3_) {
      if (this.field_150644_aH == BiomeTaiga.Type.MEGA || this.field_150644_aH == BiomeTaiga.Type.MEGA_SPRUCE) {
         int i = p_180624_2_.nextInt(3);

         for(int j = 0; j < i; ++j) {
            int k = p_180624_2_.nextInt(16) + 8;
            int l = p_180624_2_.nextInt(16) + 8;
            BlockPos blockpos = p_180624_1_.func_175645_m(p_180624_3_.func_177982_a(k, 0, l));
            field_150643_aG.func_180709_b(p_180624_1_, p_180624_2_, blockpos);
         }
      }

      field_180280_ag.func_180710_a(BlockDoublePlant.EnumPlantType.FERN);

      for(int i1 = 0; i1 < 7; ++i1) {
         int j1 = p_180624_2_.nextInt(16) + 8;
         int k1 = p_180624_2_.nextInt(16) + 8;
         int l1 = p_180624_2_.nextInt(p_180624_1_.func_175645_m(p_180624_3_.func_177982_a(j1, 0, k1)).func_177956_o() + 32);
         field_180280_ag.func_180709_b(p_180624_1_, p_180624_2_, p_180624_3_.func_177982_a(j1, l1, k1));
      }

      super.func_180624_a(p_180624_1_, p_180624_2_, p_180624_3_);
   }

   public void func_180622_a(World p_180622_1_, Random p_180622_2_, ChunkPrimer p_180622_3_, int p_180622_4_, int p_180622_5_, double p_180622_6_) {
      if (this.field_150644_aH == BiomeTaiga.Type.MEGA || this.field_150644_aH == BiomeTaiga.Type.MEGA_SPRUCE) {
         this.field_76752_A = Blocks.field_150349_c.func_176223_P();
         this.field_76753_B = Blocks.field_150346_d.func_176223_P();
         if (p_180622_6_ > 1.75D) {
            this.field_76752_A = Blocks.field_150346_d.func_176223_P().func_177226_a(BlockDirt.field_176386_a, BlockDirt.DirtType.COARSE_DIRT);
         } else if (p_180622_6_ > -0.95D) {
            this.field_76752_A = Blocks.field_150346_d.func_176223_P().func_177226_a(BlockDirt.field_176386_a, BlockDirt.DirtType.PODZOL);
         }
      }

      this.func_180628_b(p_180622_1_, p_180622_2_, p_180622_3_, p_180622_4_, p_180622_5_, p_180622_6_);
   }

   public static enum Type {
      NORMAL,
      MEGA,
      MEGA_SPRUCE;
   }
}
