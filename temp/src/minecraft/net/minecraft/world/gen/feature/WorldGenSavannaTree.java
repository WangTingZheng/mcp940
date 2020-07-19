package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockNewLeaf;
import net.minecraft.block.BlockNewLog;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WorldGenSavannaTree extends WorldGenAbstractTree {
   private static final IBlockState field_181643_a = Blocks.field_150363_s.func_176223_P().func_177226_a(BlockNewLog.field_176300_b, BlockPlanks.EnumType.ACACIA);
   private static final IBlockState field_181644_b = Blocks.field_150361_u.func_176223_P().func_177226_a(BlockNewLeaf.field_176240_P, BlockPlanks.EnumType.ACACIA).func_177226_a(BlockLeaves.field_176236_b, Boolean.valueOf(false));

   public WorldGenSavannaTree(boolean p_i45463_1_) {
      super(p_i45463_1_);
   }

   public boolean func_180709_b(World p_180709_1_, Random p_180709_2_, BlockPos p_180709_3_) {
      int i = p_180709_2_.nextInt(3) + p_180709_2_.nextInt(3) + 5;
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
            if ((block == Blocks.field_150349_c || block == Blocks.field_150346_d) && p_180709_3_.func_177956_o() < 256 - i - 1) {
               this.func_175921_a(p_180709_1_, p_180709_3_.func_177977_b());
               EnumFacing enumfacing = EnumFacing.Plane.HORIZONTAL.func_179518_a(p_180709_2_);
               int k2 = i - p_180709_2_.nextInt(4) - 1;
               int l2 = 3 - p_180709_2_.nextInt(3);
               int i3 = p_180709_3_.func_177958_n();
               int j1 = p_180709_3_.func_177952_p();
               int k1 = 0;

               for(int l1 = 0; l1 < i; ++l1) {
                  int i2 = p_180709_3_.func_177956_o() + l1;
                  if (l1 >= k2 && l2 > 0) {
                     i3 += enumfacing.func_82601_c();
                     j1 += enumfacing.func_82599_e();
                     --l2;
                  }

                  BlockPos blockpos = new BlockPos(i3, i2, j1);
                  Material material = p_180709_1_.func_180495_p(blockpos).func_185904_a();
                  if (material == Material.field_151579_a || material == Material.field_151584_j) {
                     this.func_181642_b(p_180709_1_, blockpos);
                     k1 = i2;
                  }
               }

               BlockPos blockpos2 = new BlockPos(i3, k1, j1);

               for(int j3 = -3; j3 <= 3; ++j3) {
                  for(int i4 = -3; i4 <= 3; ++i4) {
                     if (Math.abs(j3) != 3 || Math.abs(i4) != 3) {
                        this.func_175924_b(p_180709_1_, blockpos2.func_177982_a(j3, 0, i4));
                     }
                  }
               }

               blockpos2 = blockpos2.func_177984_a();

               for(int k3 = -1; k3 <= 1; ++k3) {
                  for(int j4 = -1; j4 <= 1; ++j4) {
                     this.func_175924_b(p_180709_1_, blockpos2.func_177982_a(k3, 0, j4));
                  }
               }

               this.func_175924_b(p_180709_1_, blockpos2.func_177965_g(2));
               this.func_175924_b(p_180709_1_, blockpos2.func_177985_f(2));
               this.func_175924_b(p_180709_1_, blockpos2.func_177970_e(2));
               this.func_175924_b(p_180709_1_, blockpos2.func_177964_d(2));
               i3 = p_180709_3_.func_177958_n();
               j1 = p_180709_3_.func_177952_p();
               EnumFacing enumfacing1 = EnumFacing.Plane.HORIZONTAL.func_179518_a(p_180709_2_);
               if (enumfacing1 != enumfacing) {
                  int l3 = k2 - p_180709_2_.nextInt(2) - 1;
                  int k4 = 1 + p_180709_2_.nextInt(3);
                  k1 = 0;

                  for(int l4 = l3; l4 < i && k4 > 0; --k4) {
                     if (l4 >= 1) {
                        int j2 = p_180709_3_.func_177956_o() + l4;
                        i3 += enumfacing1.func_82601_c();
                        j1 += enumfacing1.func_82599_e();
                        BlockPos blockpos1 = new BlockPos(i3, j2, j1);
                        Material material1 = p_180709_1_.func_180495_p(blockpos1).func_185904_a();
                        if (material1 == Material.field_151579_a || material1 == Material.field_151584_j) {
                           this.func_181642_b(p_180709_1_, blockpos1);
                           k1 = j2;
                        }
                     }

                     ++l4;
                  }

                  if (k1 > 0) {
                     BlockPos blockpos3 = new BlockPos(i3, k1, j1);

                     for(int i5 = -2; i5 <= 2; ++i5) {
                        for(int k5 = -2; k5 <= 2; ++k5) {
                           if (Math.abs(i5) != 2 || Math.abs(k5) != 2) {
                              this.func_175924_b(p_180709_1_, blockpos3.func_177982_a(i5, 0, k5));
                           }
                        }
                     }

                     blockpos3 = blockpos3.func_177984_a();

                     for(int j5 = -1; j5 <= 1; ++j5) {
                        for(int l5 = -1; l5 <= 1; ++l5) {
                           this.func_175924_b(p_180709_1_, blockpos3.func_177982_a(j5, 0, l5));
                        }
                     }
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

   private void func_181642_b(World p_181642_1_, BlockPos p_181642_2_) {
      this.func_175903_a(p_181642_1_, p_181642_2_, field_181643_a);
   }

   private void func_175924_b(World p_175924_1_, BlockPos p_175924_2_) {
      Material material = p_175924_1_.func_180495_p(p_175924_2_).func_185904_a();
      if (material == Material.field_151579_a || material == Material.field_151584_j) {
         this.func_175903_a(p_175924_1_, p_175924_2_, field_181644_b);
      }

   }
}
