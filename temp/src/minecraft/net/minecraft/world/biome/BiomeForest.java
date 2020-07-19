package net.minecraft.world.biome;

import java.util.Random;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.block.BlockFlower;
import net.minecraft.entity.passive.EntityRabbit;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenBigMushroom;
import net.minecraft.world.gen.feature.WorldGenBirchTree;
import net.minecraft.world.gen.feature.WorldGenCanopyTree;

public class BiomeForest extends Biome {
   protected static final WorldGenBirchTree field_150629_aC = new WorldGenBirchTree(false, true);
   protected static final WorldGenBirchTree field_150630_aD = new WorldGenBirchTree(false, false);
   protected static final WorldGenCanopyTree field_150631_aE = new WorldGenCanopyTree(false);
   private final BiomeForest.Type field_150632_aF;

   public BiomeForest(BiomeForest.Type p_i46708_1_, Biome.BiomeProperties p_i46708_2_) {
      super(p_i46708_2_);
      this.field_150632_aF = p_i46708_1_;
      this.field_76760_I.field_76832_z = 10;
      this.field_76760_I.field_76803_B = 2;
      if (this.field_150632_aF == BiomeForest.Type.FLOWER) {
         this.field_76760_I.field_76832_z = 6;
         this.field_76760_I.field_76802_A = 100;
         this.field_76760_I.field_76803_B = 1;
         this.field_76762_K.add(new Biome.SpawnListEntry(EntityRabbit.class, 4, 2, 3));
      }

      if (this.field_150632_aF == BiomeForest.Type.NORMAL) {
         this.field_76762_K.add(new Biome.SpawnListEntry(EntityWolf.class, 5, 4, 4));
      }

      if (this.field_150632_aF == BiomeForest.Type.ROOFED) {
         this.field_76760_I.field_76832_z = -999;
      }

   }

   public WorldGenAbstractTree func_150567_a(Random p_150567_1_) {
      if (this.field_150632_aF == BiomeForest.Type.ROOFED && p_150567_1_.nextInt(3) > 0) {
         return field_150631_aE;
      } else if (this.field_150632_aF != BiomeForest.Type.BIRCH && p_150567_1_.nextInt(5) != 0) {
         return (WorldGenAbstractTree)(p_150567_1_.nextInt(10) == 0 ? field_76758_O : field_76757_N);
      } else {
         return field_150630_aD;
      }
   }

   public BlockFlower.EnumFlowerType func_180623_a(Random p_180623_1_, BlockPos p_180623_2_) {
      if (this.field_150632_aF == BiomeForest.Type.FLOWER) {
         double d0 = MathHelper.func_151237_a((1.0D + field_180281_af.func_151601_a((double)p_180623_2_.func_177958_n() / 48.0D, (double)p_180623_2_.func_177952_p() / 48.0D)) / 2.0D, 0.0D, 0.9999D);
         BlockFlower.EnumFlowerType blockflower$enumflowertype = BlockFlower.EnumFlowerType.values()[(int)(d0 * (double)BlockFlower.EnumFlowerType.values().length)];
         return blockflower$enumflowertype == BlockFlower.EnumFlowerType.BLUE_ORCHID ? BlockFlower.EnumFlowerType.POPPY : blockflower$enumflowertype;
      } else {
         return super.func_180623_a(p_180623_1_, p_180623_2_);
      }
   }

   public void func_180624_a(World p_180624_1_, Random p_180624_2_, BlockPos p_180624_3_) {
      if (this.field_150632_aF == BiomeForest.Type.ROOFED) {
         this.func_185379_b(p_180624_1_, p_180624_2_, p_180624_3_);
      }

      int i = p_180624_2_.nextInt(5) - 3;
      if (this.field_150632_aF == BiomeForest.Type.FLOWER) {
         i += 2;
      }

      this.func_185378_a(p_180624_1_, p_180624_2_, p_180624_3_, i);
      super.func_180624_a(p_180624_1_, p_180624_2_, p_180624_3_);
   }

   protected void func_185379_b(World p_185379_1_, Random p_185379_2_, BlockPos p_185379_3_) {
      for(int i = 0; i < 4; ++i) {
         for(int j = 0; j < 4; ++j) {
            int k = i * 4 + 1 + 8 + p_185379_2_.nextInt(3);
            int l = j * 4 + 1 + 8 + p_185379_2_.nextInt(3);
            BlockPos blockpos = p_185379_1_.func_175645_m(p_185379_3_.func_177982_a(k, 0, l));
            if (p_185379_2_.nextInt(20) == 0) {
               WorldGenBigMushroom worldgenbigmushroom = new WorldGenBigMushroom();
               worldgenbigmushroom.func_180709_b(p_185379_1_, p_185379_2_, blockpos);
            } else {
               WorldGenAbstractTree worldgenabstracttree = this.func_150567_a(p_185379_2_);
               worldgenabstracttree.func_175904_e();
               if (worldgenabstracttree.func_180709_b(p_185379_1_, p_185379_2_, blockpos)) {
                  worldgenabstracttree.func_180711_a(p_185379_1_, p_185379_2_, blockpos);
               }
            }
         }
      }

   }

   protected void func_185378_a(World p_185378_1_, Random p_185378_2_, BlockPos p_185378_3_, int p_185378_4_) {
      for(int i = 0; i < p_185378_4_; ++i) {
         int j = p_185378_2_.nextInt(3);
         if (j == 0) {
            field_180280_ag.func_180710_a(BlockDoublePlant.EnumPlantType.SYRINGA);
         } else if (j == 1) {
            field_180280_ag.func_180710_a(BlockDoublePlant.EnumPlantType.ROSE);
         } else if (j == 2) {
            field_180280_ag.func_180710_a(BlockDoublePlant.EnumPlantType.PAEONIA);
         }

         for(int k = 0; k < 5; ++k) {
            int l = p_185378_2_.nextInt(16) + 8;
            int i1 = p_185378_2_.nextInt(16) + 8;
            int j1 = p_185378_2_.nextInt(p_185378_1_.func_175645_m(p_185378_3_.func_177982_a(l, 0, i1)).func_177956_o() + 32);
            if (field_180280_ag.func_180709_b(p_185378_1_, p_185378_2_, new BlockPos(p_185378_3_.func_177958_n() + l, j1, p_185378_3_.func_177952_p() + i1))) {
               break;
            }
         }
      }

   }

   public Class<? extends Biome> func_150562_l() {
      return BiomeForest.class;
   }

   public int func_180627_b(BlockPos p_180627_1_) {
      int i = super.func_180627_b(p_180627_1_);
      return this.field_150632_aF == BiomeForest.Type.ROOFED ? (i & 16711422) + 2634762 >> 1 : i;
   }

   public static enum Type {
      NORMAL,
      FLOWER,
      BIRCH,
      ROOFED;
   }
}
