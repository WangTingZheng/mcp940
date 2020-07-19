package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCocoa;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockOldLeaf;
import net.minecraft.block.BlockOldLog;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.BlockVine;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WorldGenTrees extends WorldGenAbstractTree {
   private static final IBlockState field_181653_a = Blocks.field_150364_r.func_176223_P().func_177226_a(BlockOldLog.field_176301_b, BlockPlanks.EnumType.OAK);
   private static final IBlockState field_181654_b = Blocks.field_150362_t.func_176223_P().func_177226_a(BlockOldLeaf.field_176239_P, BlockPlanks.EnumType.OAK).func_177226_a(BlockLeaves.field_176236_b, Boolean.valueOf(false));
   private final int field_76533_a;
   private final boolean field_76531_b;
   private final IBlockState field_76532_c;
   private final IBlockState field_76530_d;

   public WorldGenTrees(boolean p_i2027_1_) {
      this(p_i2027_1_, 4, field_181653_a, field_181654_b, false);
   }

   public WorldGenTrees(boolean p_i46446_1_, int p_i46446_2_, IBlockState p_i46446_3_, IBlockState p_i46446_4_, boolean p_i46446_5_) {
      super(p_i46446_1_);
      this.field_76533_a = p_i46446_2_;
      this.field_76532_c = p_i46446_3_;
      this.field_76530_d = p_i46446_4_;
      this.field_76531_b = p_i46446_5_;
   }

   public boolean func_180709_b(World p_180709_1_, Random p_180709_2_, BlockPos p_180709_3_) {
      int i = p_180709_2_.nextInt(3) + this.field_76533_a;
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
               int k2 = 3;
               int l2 = 0;

               for(int i3 = p_180709_3_.func_177956_o() - 3 + i; i3 <= p_180709_3_.func_177956_o() + i; ++i3) {
                  int i4 = i3 - (p_180709_3_.func_177956_o() + i);
                  int j1 = 1 - i4 / 2;

                  for(int k1 = p_180709_3_.func_177958_n() - j1; k1 <= p_180709_3_.func_177958_n() + j1; ++k1) {
                     int l1 = k1 - p_180709_3_.func_177958_n();

                     for(int i2 = p_180709_3_.func_177952_p() - j1; i2 <= p_180709_3_.func_177952_p() + j1; ++i2) {
                        int j2 = i2 - p_180709_3_.func_177952_p();
                        if (Math.abs(l1) != j1 || Math.abs(j2) != j1 || p_180709_2_.nextInt(2) != 0 && i4 != 0) {
                           BlockPos blockpos = new BlockPos(k1, i3, i2);
                           Material material = p_180709_1_.func_180495_p(blockpos).func_185904_a();
                           if (material == Material.field_151579_a || material == Material.field_151584_j || material == Material.field_151582_l) {
                              this.func_175903_a(p_180709_1_, blockpos, this.field_76530_d);
                           }
                        }
                     }
                  }
               }

               for(int j3 = 0; j3 < i; ++j3) {
                  Material material1 = p_180709_1_.func_180495_p(p_180709_3_.func_177981_b(j3)).func_185904_a();
                  if (material1 == Material.field_151579_a || material1 == Material.field_151584_j || material1 == Material.field_151582_l) {
                     this.func_175903_a(p_180709_1_, p_180709_3_.func_177981_b(j3), this.field_76532_c);
                     if (this.field_76531_b && j3 > 0) {
                        if (p_180709_2_.nextInt(3) > 0 && p_180709_1_.func_175623_d(p_180709_3_.func_177982_a(-1, j3, 0))) {
                           this.func_181651_a(p_180709_1_, p_180709_3_.func_177982_a(-1, j3, 0), BlockVine.field_176278_M);
                        }

                        if (p_180709_2_.nextInt(3) > 0 && p_180709_1_.func_175623_d(p_180709_3_.func_177982_a(1, j3, 0))) {
                           this.func_181651_a(p_180709_1_, p_180709_3_.func_177982_a(1, j3, 0), BlockVine.field_176280_O);
                        }

                        if (p_180709_2_.nextInt(3) > 0 && p_180709_1_.func_175623_d(p_180709_3_.func_177982_a(0, j3, -1))) {
                           this.func_181651_a(p_180709_1_, p_180709_3_.func_177982_a(0, j3, -1), BlockVine.field_176279_N);
                        }

                        if (p_180709_2_.nextInt(3) > 0 && p_180709_1_.func_175623_d(p_180709_3_.func_177982_a(0, j3, 1))) {
                           this.func_181651_a(p_180709_1_, p_180709_3_.func_177982_a(0, j3, 1), BlockVine.field_176273_b);
                        }
                     }
                  }
               }

               if (this.field_76531_b) {
                  for(int k3 = p_180709_3_.func_177956_o() - 3 + i; k3 <= p_180709_3_.func_177956_o() + i; ++k3) {
                     int j4 = k3 - (p_180709_3_.func_177956_o() + i);
                     int k4 = 2 - j4 / 2;
                     BlockPos.MutableBlockPos blockpos$mutableblockpos1 = new BlockPos.MutableBlockPos();

                     for(int l4 = p_180709_3_.func_177958_n() - k4; l4 <= p_180709_3_.func_177958_n() + k4; ++l4) {
                        for(int i5 = p_180709_3_.func_177952_p() - k4; i5 <= p_180709_3_.func_177952_p() + k4; ++i5) {
                           blockpos$mutableblockpos1.func_181079_c(l4, k3, i5);
                           if (p_180709_1_.func_180495_p(blockpos$mutableblockpos1).func_185904_a() == Material.field_151584_j) {
                              BlockPos blockpos2 = blockpos$mutableblockpos1.func_177976_e();
                              BlockPos blockpos3 = blockpos$mutableblockpos1.func_177974_f();
                              BlockPos blockpos4 = blockpos$mutableblockpos1.func_177978_c();
                              BlockPos blockpos1 = blockpos$mutableblockpos1.func_177968_d();
                              if (p_180709_2_.nextInt(4) == 0 && p_180709_1_.func_180495_p(blockpos2).func_185904_a() == Material.field_151579_a) {
                                 this.func_181650_b(p_180709_1_, blockpos2, BlockVine.field_176278_M);
                              }

                              if (p_180709_2_.nextInt(4) == 0 && p_180709_1_.func_180495_p(blockpos3).func_185904_a() == Material.field_151579_a) {
                                 this.func_181650_b(p_180709_1_, blockpos3, BlockVine.field_176280_O);
                              }

                              if (p_180709_2_.nextInt(4) == 0 && p_180709_1_.func_180495_p(blockpos4).func_185904_a() == Material.field_151579_a) {
                                 this.func_181650_b(p_180709_1_, blockpos4, BlockVine.field_176279_N);
                              }

                              if (p_180709_2_.nextInt(4) == 0 && p_180709_1_.func_180495_p(blockpos1).func_185904_a() == Material.field_151579_a) {
                                 this.func_181650_b(p_180709_1_, blockpos1, BlockVine.field_176273_b);
                              }
                           }
                        }
                     }
                  }

                  if (p_180709_2_.nextInt(5) == 0 && i > 5) {
                     for(int l3 = 0; l3 < 2; ++l3) {
                        for(EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
                           if (p_180709_2_.nextInt(4 - l3) == 0) {
                              EnumFacing enumfacing1 = enumfacing.func_176734_d();
                              this.func_181652_a(p_180709_1_, p_180709_2_.nextInt(3), p_180709_3_.func_177982_a(enumfacing1.func_82601_c(), i - 5 + l3, enumfacing1.func_82599_e()), enumfacing);
                           }
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

   private void func_181652_a(World p_181652_1_, int p_181652_2_, BlockPos p_181652_3_, EnumFacing p_181652_4_) {
      this.func_175903_a(p_181652_1_, p_181652_3_, Blocks.field_150375_by.func_176223_P().func_177226_a(BlockCocoa.field_176501_a, Integer.valueOf(p_181652_2_)).func_177226_a(BlockCocoa.field_185512_D, p_181652_4_));
   }

   private void func_181651_a(World p_181651_1_, BlockPos p_181651_2_, PropertyBool p_181651_3_) {
      this.func_175903_a(p_181651_1_, p_181651_2_, Blocks.field_150395_bd.func_176223_P().func_177226_a(p_181651_3_, Boolean.valueOf(true)));
   }

   private void func_181650_b(World p_181650_1_, BlockPos p_181650_2_, PropertyBool p_181650_3_) {
      this.func_181651_a(p_181650_1_, p_181650_2_, p_181650_3_);
      int i = 4;

      for(BlockPos blockpos = p_181650_2_.func_177977_b(); p_181650_1_.func_180495_p(blockpos).func_185904_a() == Material.field_151579_a && i > 0; --i) {
         this.func_181651_a(p_181650_1_, blockpos, p_181650_3_);
         blockpos = blockpos.func_177977_b();
      }

   }
}
