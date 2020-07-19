package net.minecraft.world.gen;

import java.util.Random;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkPrimer;

public class MapGenRavine extends MapGenBase {
   protected static final IBlockState field_186135_a = Blocks.field_150356_k.func_176223_P();
   protected static final IBlockState field_186136_b = Blocks.field_150350_a.func_176223_P();
   private final float[] field_75046_d = new float[1024];

   protected void func_180707_a(long p_180707_1_, int p_180707_3_, int p_180707_4_, ChunkPrimer p_180707_5_, double p_180707_6_, double p_180707_8_, double p_180707_10_, float p_180707_12_, float p_180707_13_, float p_180707_14_, int p_180707_15_, int p_180707_16_, double p_180707_17_) {
      Random random = new Random(p_180707_1_);
      double d0 = (double)(p_180707_3_ * 16 + 8);
      double d1 = (double)(p_180707_4_ * 16 + 8);
      float f = 0.0F;
      float f1 = 0.0F;
      if (p_180707_16_ <= 0) {
         int i = this.field_75040_a * 16 - 16;
         p_180707_16_ = i - random.nextInt(i / 4);
      }

      boolean flag1 = false;
      if (p_180707_15_ == -1) {
         p_180707_15_ = p_180707_16_ / 2;
         flag1 = true;
      }

      float f2 = 1.0F;

      for(int j = 0; j < 256; ++j) {
         if (j == 0 || random.nextInt(3) == 0) {
            f2 = 1.0F + random.nextFloat() * random.nextFloat();
         }

         this.field_75046_d[j] = f2 * f2;
      }

      for(; p_180707_15_ < p_180707_16_; ++p_180707_15_) {
         double d9 = 1.5D + (double)(MathHelper.func_76126_a((float)p_180707_15_ * 3.1415927F / (float)p_180707_16_) * p_180707_12_);
         double d2 = d9 * p_180707_17_;
         d9 = d9 * ((double)random.nextFloat() * 0.25D + 0.75D);
         d2 = d2 * ((double)random.nextFloat() * 0.25D + 0.75D);
         float f3 = MathHelper.func_76134_b(p_180707_14_);
         float f4 = MathHelper.func_76126_a(p_180707_14_);
         p_180707_6_ += (double)(MathHelper.func_76134_b(p_180707_13_) * f3);
         p_180707_8_ += (double)f4;
         p_180707_10_ += (double)(MathHelper.func_76126_a(p_180707_13_) * f3);
         p_180707_14_ = p_180707_14_ * 0.7F;
         p_180707_14_ = p_180707_14_ + f1 * 0.05F;
         p_180707_13_ += f * 0.05F;
         f1 = f1 * 0.8F;
         f = f * 0.5F;
         f1 = f1 + (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 2.0F;
         f = f + (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 4.0F;
         if (flag1 || random.nextInt(4) != 0) {
            double d3 = p_180707_6_ - d0;
            double d4 = p_180707_10_ - d1;
            double d5 = (double)(p_180707_16_ - p_180707_15_);
            double d6 = (double)(p_180707_12_ + 2.0F + 16.0F);
            if (d3 * d3 + d4 * d4 - d5 * d5 > d6 * d6) {
               return;
            }

            if (p_180707_6_ >= d0 - 16.0D - d9 * 2.0D && p_180707_10_ >= d1 - 16.0D - d9 * 2.0D && p_180707_6_ <= d0 + 16.0D + d9 * 2.0D && p_180707_10_ <= d1 + 16.0D + d9 * 2.0D) {
               int k2 = MathHelper.func_76128_c(p_180707_6_ - d9) - p_180707_3_ * 16 - 1;
               int k = MathHelper.func_76128_c(p_180707_6_ + d9) - p_180707_3_ * 16 + 1;
               int l2 = MathHelper.func_76128_c(p_180707_8_ - d2) - 1;
               int l = MathHelper.func_76128_c(p_180707_8_ + d2) + 1;
               int i3 = MathHelper.func_76128_c(p_180707_10_ - d9) - p_180707_4_ * 16 - 1;
               int i1 = MathHelper.func_76128_c(p_180707_10_ + d9) - p_180707_4_ * 16 + 1;
               if (k2 < 0) {
                  k2 = 0;
               }

               if (k > 16) {
                  k = 16;
               }

               if (l2 < 1) {
                  l2 = 1;
               }

               if (l > 248) {
                  l = 248;
               }

               if (i3 < 0) {
                  i3 = 0;
               }

               if (i1 > 16) {
                  i1 = 16;
               }

               boolean flag2 = false;

               for(int j1 = k2; !flag2 && j1 < k; ++j1) {
                  for(int k1 = i3; !flag2 && k1 < i1; ++k1) {
                     for(int l1 = l + 1; !flag2 && l1 >= l2 - 1; --l1) {
                        if (l1 >= 0 && l1 < 256) {
                           IBlockState iblockstate = p_180707_5_.func_177856_a(j1, l1, k1);
                           if (iblockstate.func_177230_c() == Blocks.field_150358_i || iblockstate.func_177230_c() == Blocks.field_150355_j) {
                              flag2 = true;
                           }

                           if (l1 != l2 - 1 && j1 != k2 && j1 != k - 1 && k1 != i3 && k1 != i1 - 1) {
                              l1 = l2;
                           }
                        }
                     }
                  }
               }

               if (!flag2) {
                  BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

                  for(int j3 = k2; j3 < k; ++j3) {
                     double d10 = ((double)(j3 + p_180707_3_ * 16) + 0.5D - p_180707_6_) / d9;

                     for(int i2 = i3; i2 < i1; ++i2) {
                        double d7 = ((double)(i2 + p_180707_4_ * 16) + 0.5D - p_180707_10_) / d9;
                        boolean flag = false;
                        if (d10 * d10 + d7 * d7 < 1.0D) {
                           for(int j2 = l; j2 > l2; --j2) {
                              double d8 = ((double)(j2 - 1) + 0.5D - p_180707_8_) / d2;
                              if ((d10 * d10 + d7 * d7) * (double)this.field_75046_d[j2 - 1] + d8 * d8 / 6.0D < 1.0D) {
                                 IBlockState iblockstate1 = p_180707_5_.func_177856_a(j3, j2, i2);
                                 if (iblockstate1.func_177230_c() == Blocks.field_150349_c) {
                                    flag = true;
                                 }

                                 if (iblockstate1.func_177230_c() == Blocks.field_150348_b || iblockstate1.func_177230_c() == Blocks.field_150346_d || iblockstate1.func_177230_c() == Blocks.field_150349_c) {
                                    if (j2 - 1 < 10) {
                                       p_180707_5_.func_177855_a(j3, j2, i2, field_186135_a);
                                    } else {
                                       p_180707_5_.func_177855_a(j3, j2, i2, field_186136_b);
                                       if (flag && p_180707_5_.func_177856_a(j3, j2 - 1, i2).func_177230_c() == Blocks.field_150346_d) {
                                          blockpos$mutableblockpos.func_181079_c(j3 + p_180707_3_ * 16, 0, i2 + p_180707_4_ * 16);
                                          p_180707_5_.func_177855_a(j3, j2 - 1, i2, this.field_75039_c.func_180494_b(blockpos$mutableblockpos).field_76752_A);
                                       }
                                    }
                                 }
                              }
                           }
                        }
                     }
                  }

                  if (flag1) {
                     break;
                  }
               }
            }
         }
      }

   }

   protected void func_180701_a(World p_180701_1_, int p_180701_2_, int p_180701_3_, int p_180701_4_, int p_180701_5_, ChunkPrimer p_180701_6_) {
      if (this.field_75038_b.nextInt(50) == 0) {
         double d0 = (double)(p_180701_2_ * 16 + this.field_75038_b.nextInt(16));
         double d1 = (double)(this.field_75038_b.nextInt(this.field_75038_b.nextInt(40) + 8) + 20);
         double d2 = (double)(p_180701_3_ * 16 + this.field_75038_b.nextInt(16));
         int i = 1;

         for(int j = 0; j < 1; ++j) {
            float f = this.field_75038_b.nextFloat() * 6.2831855F;
            float f1 = (this.field_75038_b.nextFloat() - 0.5F) * 2.0F / 8.0F;
            float f2 = (this.field_75038_b.nextFloat() * 2.0F + this.field_75038_b.nextFloat()) * 2.0F;
            this.func_180707_a(this.field_75038_b.nextLong(), p_180701_4_, p_180701_5_, p_180701_6_, d0, d1, d2, f2, f, f1, 0, 0, 3.0D);
         }

      }
   }
}
