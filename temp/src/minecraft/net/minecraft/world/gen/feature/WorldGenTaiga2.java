package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockOldLeaf;
import net.minecraft.block.BlockOldLog;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WorldGenTaiga2 extends WorldGenAbstractTree {
   private static final IBlockState field_181645_a = Blocks.field_150364_r.func_176223_P().func_177226_a(BlockOldLog.field_176301_b, BlockPlanks.EnumType.SPRUCE);
   private static final IBlockState field_181646_b = Blocks.field_150362_t.func_176223_P().func_177226_a(BlockOldLeaf.field_176239_P, BlockPlanks.EnumType.SPRUCE).func_177226_a(BlockLeaves.field_176236_b, Boolean.valueOf(false));

   public WorldGenTaiga2(boolean p_i2025_1_) {
      super(p_i2025_1_);
   }

   public boolean func_180709_b(World p_180709_1_, Random p_180709_2_, BlockPos p_180709_3_) {
      int i = p_180709_2_.nextInt(4) + 6;
      int j = 1 + p_180709_2_.nextInt(2);
      int k = i - j;
      int l = 2 + p_180709_2_.nextInt(2);
      boolean flag = true;
      if (p_180709_3_.func_177956_o() >= 1 && p_180709_3_.func_177956_o() + i + 1 <= 256) {
         for(int i1 = p_180709_3_.func_177956_o(); i1 <= p_180709_3_.func_177956_o() + 1 + i && flag; ++i1) {
            int j1;
            if (i1 - p_180709_3_.func_177956_o() < j) {
               j1 = 0;
            } else {
               j1 = l;
            }

            BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

            for(int k1 = p_180709_3_.func_177958_n() - j1; k1 <= p_180709_3_.func_177958_n() + j1 && flag; ++k1) {
               for(int l1 = p_180709_3_.func_177952_p() - j1; l1 <= p_180709_3_.func_177952_p() + j1 && flag; ++l1) {
                  if (i1 >= 0 && i1 < 256) {
                     Material material = p_180709_1_.func_180495_p(blockpos$mutableblockpos.func_181079_c(k1, i1, l1)).func_185904_a();
                     if (material != Material.field_151579_a && material != Material.field_151584_j) {
                        flag = false;
                     }
                  } else {
                     flag = false;
                  }
               }
            }
         }

         if (!flag) {
            return false;
         } else {
            Block block = p_180709_1_.func_180495_p(p_180709_3_.func_177977_b()).func_177230_c();
            if ((block == Blocks.field_150349_c || block == Blocks.field_150346_d || block == Blocks.field_150458_ak) && p_180709_3_.func_177956_o() < 256 - i - 1) {
               this.func_175921_a(p_180709_1_, p_180709_3_.func_177977_b());
               int i3 = p_180709_2_.nextInt(2);
               int j3 = 1;
               int k3 = 0;

               for(int l3 = 0; l3 <= k; ++l3) {
                  int j4 = p_180709_3_.func_177956_o() + i - l3;

                  for(int i2 = p_180709_3_.func_177958_n() - i3; i2 <= p_180709_3_.func_177958_n() + i3; ++i2) {
                     int j2 = i2 - p_180709_3_.func_177958_n();

                     for(int k2 = p_180709_3_.func_177952_p() - i3; k2 <= p_180709_3_.func_177952_p() + i3; ++k2) {
                        int l2 = k2 - p_180709_3_.func_177952_p();
                        if (Math.abs(j2) != i3 || Math.abs(l2) != i3 || i3 <= 0) {
                           BlockPos blockpos = new BlockPos(i2, j4, k2);
                           if (!p_180709_1_.func_180495_p(blockpos).func_185913_b()) {
                              this.func_175903_a(p_180709_1_, blockpos, field_181646_b);
                           }
                        }
                     }
                  }

                  if (i3 >= j3) {
                     i3 = k3;
                     k3 = 1;
                     ++j3;
                     if (j3 > l) {
                        j3 = l;
                     }
                  } else {
                     ++i3;
                  }
               }

               int i4 = p_180709_2_.nextInt(3);

               for(int k4 = 0; k4 < i - i4; ++k4) {
                  Material material1 = p_180709_1_.func_180495_p(p_180709_3_.func_177981_b(k4)).func_185904_a();
                  if (material1 == Material.field_151579_a || material1 == Material.field_151584_j) {
                     this.func_175903_a(p_180709_1_, p_180709_3_.func_177981_b(k4), field_181645_a);
                  }
               }

               return true;
            } else {
               return false;
            }
         }
      } else {
         return false;
      }
   }
}
