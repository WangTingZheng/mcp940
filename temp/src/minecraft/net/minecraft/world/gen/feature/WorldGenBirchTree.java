package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockOldLeaf;
import net.minecraft.block.BlockOldLog;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WorldGenBirchTree extends WorldGenAbstractTree {
   private static final IBlockState field_181629_a = Blocks.field_150364_r.func_176223_P().func_177226_a(BlockOldLog.field_176301_b, BlockPlanks.EnumType.BIRCH);
   private static final IBlockState field_181630_b = Blocks.field_150362_t.func_176223_P().func_177226_a(BlockOldLeaf.field_176239_P, BlockPlanks.EnumType.BIRCH).func_177226_a(BlockOldLeaf.field_176236_b, Boolean.valueOf(false));
   private final boolean field_150531_a;

   public WorldGenBirchTree(boolean p_i45449_1_, boolean p_i45449_2_) {
      super(p_i45449_1_);
      this.field_150531_a = p_i45449_2_;
   }

   public boolean func_180709_b(World p_180709_1_, Random p_180709_2_, BlockPos p_180709_3_) {
      int i = p_180709_2_.nextInt(3) + 5;
      if (this.field_150531_a) {
         i += p_180709_2_.nextInt(7);
      }

      boolean flag = true;
      if (p_180709_3_.func_177956_o() >= 1 && p_180709_3_.func_177956_o() + i + 1 <= 256) {
         for(int j = p_180709_3_.func_177956_o(); j <= p_180709_3_.func_177956_o() + 1 + i; ++j) {
            int k = 1;
            if (j == p_180709_3_.func_177956_o()) {
               k = 0;
            }

            if (j >= p_180709_3_.func_177956_o() + 1 + i - 2) {
               k = 2;
            }

            BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

            for(int l = p_180709_3_.func_177958_n() - k; l <= p_180709_3_.func_177958_n() + k && flag; ++l) {
               for(int i1 = p_180709_3_.func_177952_p() - k; i1 <= p_180709_3_.func_177952_p() + k && flag; ++i1) {
                  if (j >= 0 && j < 256) {
                     if (!this.func_150523_a(p_180709_1_.func_180495_p(blockpos$mutableblockpos.func_181079_c(l, j, i1)).func_177230_c())) {
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

               for(int i2 = p_180709_3_.func_177956_o() - 3 + i; i2 <= p_180709_3_.func_177956_o() + i; ++i2) {
                  int k2 = i2 - (p_180709_3_.func_177956_o() + i);
                  int l2 = 1 - k2 / 2;

                  for(int i3 = p_180709_3_.func_177958_n() - l2; i3 <= p_180709_3_.func_177958_n() + l2; ++i3) {
                     int j1 = i3 - p_180709_3_.func_177958_n();

                     for(int k1 = p_180709_3_.func_177952_p() - l2; k1 <= p_180709_3_.func_177952_p() + l2; ++k1) {
                        int l1 = k1 - p_180709_3_.func_177952_p();
                        if (Math.abs(j1) != l2 || Math.abs(l1) != l2 || p_180709_2_.nextInt(2) != 0 && k2 != 0) {
                           BlockPos blockpos = new BlockPos(i3, i2, k1);
                           Material material = p_180709_1_.func_180495_p(blockpos).func_185904_a();
                           if (material == Material.field_151579_a || material == Material.field_151584_j) {
                              this.func_175903_a(p_180709_1_, blockpos, field_181630_b);
                           }
                        }
                     }
                  }
               }

               for(int j2 = 0; j2 < i; ++j2) {
                  Material material1 = p_180709_1_.func_180495_p(p_180709_3_.func_177981_b(j2)).func_185904_a();
                  if (material1 == Material.field_151579_a || material1 == Material.field_151584_j) {
                     this.func_175903_a(p_180709_1_, p_180709_3_.func_177981_b(j2), field_181629_a);
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
