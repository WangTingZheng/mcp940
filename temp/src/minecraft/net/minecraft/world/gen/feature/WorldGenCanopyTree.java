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

public class WorldGenCanopyTree extends WorldGenAbstractTree {
   private static final IBlockState field_181640_a = Blocks.field_150363_s.func_176223_P().func_177226_a(BlockNewLog.field_176300_b, BlockPlanks.EnumType.DARK_OAK);
   private static final IBlockState field_181641_b = Blocks.field_150361_u.func_176223_P().func_177226_a(BlockNewLeaf.field_176240_P, BlockPlanks.EnumType.DARK_OAK).func_177226_a(BlockLeaves.field_176236_b, Boolean.valueOf(false));

   public WorldGenCanopyTree(boolean p_i45461_1_) {
      super(p_i45461_1_);
   }

   public boolean func_180709_b(World p_180709_1_, Random p_180709_2_, BlockPos p_180709_3_) {
      int i = p_180709_2_.nextInt(3) + p_180709_2_.nextInt(2) + 6;
      int j = p_180709_3_.func_177958_n();
      int k = p_180709_3_.func_177956_o();
      int l = p_180709_3_.func_177952_p();
      if (k >= 1 && k + i + 1 < 256) {
         BlockPos blockpos = p_180709_3_.func_177977_b();
         Block block = p_180709_1_.func_180495_p(blockpos).func_177230_c();
         if (block != Blocks.field_150349_c && block != Blocks.field_150346_d) {
            return false;
         } else if (!this.func_181638_a(p_180709_1_, p_180709_3_, i)) {
            return false;
         } else {
            this.func_175921_a(p_180709_1_, blockpos);
            this.func_175921_a(p_180709_1_, blockpos.func_177974_f());
            this.func_175921_a(p_180709_1_, blockpos.func_177968_d());
            this.func_175921_a(p_180709_1_, blockpos.func_177968_d().func_177974_f());
            EnumFacing enumfacing = EnumFacing.Plane.HORIZONTAL.func_179518_a(p_180709_2_);
            int i1 = i - p_180709_2_.nextInt(4);
            int j1 = 2 - p_180709_2_.nextInt(3);
            int k1 = j;
            int l1 = l;
            int i2 = k + i - 1;

            for(int j2 = 0; j2 < i; ++j2) {
               if (j2 >= i1 && j1 > 0) {
                  k1 += enumfacing.func_82601_c();
                  l1 += enumfacing.func_82599_e();
                  --j1;
               }

               int k2 = k + j2;
               BlockPos blockpos1 = new BlockPos(k1, k2, l1);
               Material material = p_180709_1_.func_180495_p(blockpos1).func_185904_a();
               if (material == Material.field_151579_a || material == Material.field_151584_j) {
                  this.func_181639_b(p_180709_1_, blockpos1);
                  this.func_181639_b(p_180709_1_, blockpos1.func_177974_f());
                  this.func_181639_b(p_180709_1_, blockpos1.func_177968_d());
                  this.func_181639_b(p_180709_1_, blockpos1.func_177974_f().func_177968_d());
               }
            }

            for(int i3 = -2; i3 <= 0; ++i3) {
               for(int l3 = -2; l3 <= 0; ++l3) {
                  int k4 = -1;
                  this.func_150526_a(p_180709_1_, k1 + i3, i2 + k4, l1 + l3);
                  this.func_150526_a(p_180709_1_, 1 + k1 - i3, i2 + k4, l1 + l3);
                  this.func_150526_a(p_180709_1_, k1 + i3, i2 + k4, 1 + l1 - l3);
                  this.func_150526_a(p_180709_1_, 1 + k1 - i3, i2 + k4, 1 + l1 - l3);
                  if ((i3 > -2 || l3 > -1) && (i3 != -1 || l3 != -2)) {
                     k4 = 1;
                     this.func_150526_a(p_180709_1_, k1 + i3, i2 + k4, l1 + l3);
                     this.func_150526_a(p_180709_1_, 1 + k1 - i3, i2 + k4, l1 + l3);
                     this.func_150526_a(p_180709_1_, k1 + i3, i2 + k4, 1 + l1 - l3);
                     this.func_150526_a(p_180709_1_, 1 + k1 - i3, i2 + k4, 1 + l1 - l3);
                  }
               }
            }

            if (p_180709_2_.nextBoolean()) {
               this.func_150526_a(p_180709_1_, k1, i2 + 2, l1);
               this.func_150526_a(p_180709_1_, k1 + 1, i2 + 2, l1);
               this.func_150526_a(p_180709_1_, k1 + 1, i2 + 2, l1 + 1);
               this.func_150526_a(p_180709_1_, k1, i2 + 2, l1 + 1);
            }

            for(int j3 = -3; j3 <= 4; ++j3) {
               for(int i4 = -3; i4 <= 4; ++i4) {
                  if ((j3 != -3 || i4 != -3) && (j3 != -3 || i4 != 4) && (j3 != 4 || i4 != -3) && (j3 != 4 || i4 != 4) && (Math.abs(j3) < 3 || Math.abs(i4) < 3)) {
                     this.func_150526_a(p_180709_1_, k1 + j3, i2, l1 + i4);
                  }
               }
            }

            for(int k3 = -1; k3 <= 2; ++k3) {
               for(int j4 = -1; j4 <= 2; ++j4) {
                  if ((k3 < 0 || k3 > 1 || j4 < 0 || j4 > 1) && p_180709_2_.nextInt(3) <= 0) {
                     int l4 = p_180709_2_.nextInt(3) + 2;

                     for(int i5 = 0; i5 < l4; ++i5) {
                        this.func_181639_b(p_180709_1_, new BlockPos(j + k3, i2 - i5 - 1, l + j4));
                     }

                     for(int j5 = -1; j5 <= 1; ++j5) {
                        for(int l2 = -1; l2 <= 1; ++l2) {
                           this.func_150526_a(p_180709_1_, k1 + k3 + j5, i2, l1 + j4 + l2);
                        }
                     }

                     for(int k5 = -2; k5 <= 2; ++k5) {
                        for(int l5 = -2; l5 <= 2; ++l5) {
                           if (Math.abs(k5) != 2 || Math.abs(l5) != 2) {
                              this.func_150526_a(p_180709_1_, k1 + k3 + k5, i2 - 1, l1 + j4 + l5);
                           }
                        }
                     }
                  }
               }
            }

            return true;
         }
      } else {
         return false;
      }
   }

   private boolean func_181638_a(World p_181638_1_, BlockPos p_181638_2_, int p_181638_3_) {
      int i = p_181638_2_.func_177958_n();
      int j = p_181638_2_.func_177956_o();
      int k = p_181638_2_.func_177952_p();
      BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

      for(int l = 0; l <= p_181638_3_ + 1; ++l) {
         int i1 = 1;
         if (l == 0) {
            i1 = 0;
         }

         if (l >= p_181638_3_ - 1) {
            i1 = 2;
         }

         for(int j1 = -i1; j1 <= i1; ++j1) {
            for(int k1 = -i1; k1 <= i1; ++k1) {
               if (!this.func_150523_a(p_181638_1_.func_180495_p(blockpos$mutableblockpos.func_181079_c(i + j1, j + l, k + k1)).func_177230_c())) {
                  return false;
               }
            }
         }
      }

      return true;
   }

   private void func_181639_b(World p_181639_1_, BlockPos p_181639_2_) {
      if (this.func_150523_a(p_181639_1_.func_180495_p(p_181639_2_).func_177230_c())) {
         this.func_175903_a(p_181639_1_, p_181639_2_, field_181640_a);
      }

   }

   private void func_150526_a(World p_150526_1_, int p_150526_2_, int p_150526_3_, int p_150526_4_) {
      BlockPos blockpos = new BlockPos(p_150526_2_, p_150526_3_, p_150526_4_);
      Material material = p_150526_1_.func_180495_p(blockpos).func_185904_a();
      if (material == Material.field_151579_a) {
         this.func_175903_a(p_150526_1_, blockpos, field_181641_b);
      }

   }
}
