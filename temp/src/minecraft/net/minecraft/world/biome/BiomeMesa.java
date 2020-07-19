package net.minecraft.world.biome;

import java.util.Arrays;
import java.util.Random;
import net.minecraft.block.BlockColored;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.BlockSand;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.NoiseGeneratorPerlin;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

public class BiomeMesa extends Biome {
   protected static final IBlockState field_185385_y = Blocks.field_150346_d.func_176223_P().func_177226_a(BlockDirt.field_176386_a, BlockDirt.DirtType.COARSE_DIRT);
   protected static final IBlockState field_185386_z = Blocks.field_150349_c.func_176223_P();
   protected static final IBlockState field_185381_A = Blocks.field_150405_ch.func_176223_P();
   protected static final IBlockState field_185382_B = Blocks.field_150406_ce.func_176223_P();
   protected static final IBlockState field_185383_C = field_185382_B.func_177226_a(BlockColored.field_176581_a, EnumDyeColor.ORANGE);
   protected static final IBlockState field_185384_D = Blocks.field_150354_m.func_176223_P().func_177226_a(BlockSand.field_176504_a, BlockSand.EnumType.RED_SAND);
   private IBlockState[] field_150621_aC;
   private long field_150622_aD;
   private NoiseGeneratorPerlin field_150623_aE;
   private NoiseGeneratorPerlin field_150624_aF;
   private NoiseGeneratorPerlin field_150625_aG;
   private final boolean field_150626_aH;
   private final boolean field_150620_aI;

   public BiomeMesa(boolean p_i46704_1_, boolean p_i46704_2_, Biome.BiomeProperties p_i46704_3_) {
      super(p_i46704_3_);
      this.field_150626_aH = p_i46704_1_;
      this.field_150620_aI = p_i46704_2_;
      this.field_76762_K.clear();
      this.field_76752_A = field_185384_D;
      this.field_76753_B = field_185382_B;
      this.field_76760_I.field_76832_z = -999;
      this.field_76760_I.field_76804_C = 20;
      this.field_76760_I.field_76799_E = 3;
      this.field_76760_I.field_76800_F = 5;
      this.field_76760_I.field_76802_A = 0;
      this.field_76762_K.clear();
      if (p_i46704_2_) {
         this.field_76760_I.field_76832_z = 5;
      }

   }

   protected BiomeDecorator func_76729_a() {
      return new BiomeMesa.Decorator();
   }

   public WorldGenAbstractTree func_150567_a(Random p_150567_1_) {
      return field_76757_N;
   }

   public int func_180625_c(BlockPos p_180625_1_) {
      return 10387789;
   }

   public int func_180627_b(BlockPos p_180627_1_) {
      return 9470285;
   }

   public void func_180622_a(World p_180622_1_, Random p_180622_2_, ChunkPrimer p_180622_3_, int p_180622_4_, int p_180622_5_, double p_180622_6_) {
      if (this.field_150621_aC == null || this.field_150622_aD != p_180622_1_.func_72905_C()) {
         this.func_150619_a(p_180622_1_.func_72905_C());
      }

      if (this.field_150623_aE == null || this.field_150624_aF == null || this.field_150622_aD != p_180622_1_.func_72905_C()) {
         Random random = new Random(this.field_150622_aD);
         this.field_150623_aE = new NoiseGeneratorPerlin(random, 4);
         this.field_150624_aF = new NoiseGeneratorPerlin(random, 1);
      }

      this.field_150622_aD = p_180622_1_.func_72905_C();
      double d4 = 0.0D;
      if (this.field_150626_aH) {
         int i = (p_180622_4_ & -16) + (p_180622_5_ & 15);
         int j = (p_180622_5_ & -16) + (p_180622_4_ & 15);
         double d0 = Math.min(Math.abs(p_180622_6_), this.field_150623_aE.func_151601_a((double)i * 0.25D, (double)j * 0.25D));
         if (d0 > 0.0D) {
            double d1 = 0.001953125D;
            double d2 = Math.abs(this.field_150624_aF.func_151601_a((double)i * 0.001953125D, (double)j * 0.001953125D));
            d4 = d0 * d0 * 2.5D;
            double d3 = Math.ceil(d2 * 50.0D) + 14.0D;
            if (d4 > d3) {
               d4 = d3;
            }

            d4 = d4 + 64.0D;
         }
      }

      int k1 = p_180622_4_ & 15;
      int l1 = p_180622_5_ & 15;
      int i2 = p_180622_1_.func_181545_F();
      IBlockState iblockstate = field_185382_B;
      IBlockState iblockstate3 = this.field_76753_B;
      int k = (int)(p_180622_6_ / 3.0D + 3.0D + p_180622_2_.nextDouble() * 0.25D);
      boolean flag = Math.cos(p_180622_6_ / 3.0D * 3.141592653589793D) > 0.0D;
      int l = -1;
      boolean flag1 = false;
      int i1 = 0;

      for(int j1 = 255; j1 >= 0; --j1) {
         if (p_180622_3_.func_177856_a(l1, j1, k1).func_185904_a() == Material.field_151579_a && j1 < (int)d4) {
            p_180622_3_.func_177855_a(l1, j1, k1, field_185365_a);
         }

         if (j1 <= p_180622_2_.nextInt(5)) {
            p_180622_3_.func_177855_a(l1, j1, k1, field_185367_c);
         } else if (i1 < 15 || this.field_150626_aH) {
            IBlockState iblockstate1 = p_180622_3_.func_177856_a(l1, j1, k1);
            if (iblockstate1.func_185904_a() == Material.field_151579_a) {
               l = -1;
            } else if (iblockstate1.func_177230_c() == Blocks.field_150348_b) {
               if (l == -1) {
                  flag1 = false;
                  if (k <= 0) {
                     iblockstate = field_185366_b;
                     iblockstate3 = field_185365_a;
                  } else if (j1 >= i2 - 4 && j1 <= i2 + 1) {
                     iblockstate = field_185382_B;
                     iblockstate3 = this.field_76753_B;
                  }

                  if (j1 < i2 && (iblockstate == null || iblockstate.func_185904_a() == Material.field_151579_a)) {
                     iblockstate = field_185372_h;
                  }

                  l = k + Math.max(0, j1 - i2);
                  if (j1 >= i2 - 1) {
                     if (this.field_150620_aI && j1 > 86 + k * 2) {
                        if (flag) {
                           p_180622_3_.func_177855_a(l1, j1, k1, field_185385_y);
                        } else {
                           p_180622_3_.func_177855_a(l1, j1, k1, field_185386_z);
                        }
                     } else if (j1 > i2 + 3 + k) {
                        IBlockState iblockstate2;
                        if (j1 >= 64 && j1 <= 127) {
                           if (flag) {
                              iblockstate2 = field_185381_A;
                           } else {
                              iblockstate2 = this.func_180629_a(p_180622_4_, j1, p_180622_5_);
                           }
                        } else {
                           iblockstate2 = field_185383_C;
                        }

                        p_180622_3_.func_177855_a(l1, j1, k1, iblockstate2);
                     } else {
                        p_180622_3_.func_177855_a(l1, j1, k1, this.field_76752_A);
                        flag1 = true;
                     }
                  } else {
                     p_180622_3_.func_177855_a(l1, j1, k1, iblockstate3);
                     if (iblockstate3.func_177230_c() == Blocks.field_150406_ce) {
                        p_180622_3_.func_177855_a(l1, j1, k1, field_185383_C);
                     }
                  }
               } else if (l > 0) {
                  --l;
                  if (flag1) {
                     p_180622_3_.func_177855_a(l1, j1, k1, field_185383_C);
                  } else {
                     p_180622_3_.func_177855_a(l1, j1, k1, this.func_180629_a(p_180622_4_, j1, p_180622_5_));
                  }
               }

               ++i1;
            }
         }
      }

   }

   private void func_150619_a(long p_150619_1_) {
      this.field_150621_aC = new IBlockState[64];
      Arrays.fill(this.field_150621_aC, field_185381_A);
      Random random = new Random(p_150619_1_);
      this.field_150625_aG = new NoiseGeneratorPerlin(random, 1);

      for(int l1 = 0; l1 < 64; ++l1) {
         l1 += random.nextInt(5) + 1;
         if (l1 < 64) {
            this.field_150621_aC[l1] = field_185383_C;
         }
      }

      int i2 = random.nextInt(4) + 2;

      for(int i = 0; i < i2; ++i) {
         int j = random.nextInt(3) + 1;
         int k = random.nextInt(64);

         for(int l = 0; k + l < 64 && l < j; ++l) {
            this.field_150621_aC[k + l] = field_185382_B.func_177226_a(BlockColored.field_176581_a, EnumDyeColor.YELLOW);
         }
      }

      int j2 = random.nextInt(4) + 2;

      for(int k2 = 0; k2 < j2; ++k2) {
         int i3 = random.nextInt(3) + 2;
         int l3 = random.nextInt(64);

         for(int i1 = 0; l3 + i1 < 64 && i1 < i3; ++i1) {
            this.field_150621_aC[l3 + i1] = field_185382_B.func_177226_a(BlockColored.field_176581_a, EnumDyeColor.BROWN);
         }
      }

      int l2 = random.nextInt(4) + 2;

      for(int j3 = 0; j3 < l2; ++j3) {
         int i4 = random.nextInt(3) + 1;
         int k4 = random.nextInt(64);

         for(int j1 = 0; k4 + j1 < 64 && j1 < i4; ++j1) {
            this.field_150621_aC[k4 + j1] = field_185382_B.func_177226_a(BlockColored.field_176581_a, EnumDyeColor.RED);
         }
      }

      int k3 = random.nextInt(3) + 3;
      int j4 = 0;

      for(int l4 = 0; l4 < k3; ++l4) {
         int i5 = 1;
         j4 += random.nextInt(16) + 4;

         for(int k1 = 0; j4 + k1 < 64 && k1 < 1; ++k1) {
            this.field_150621_aC[j4 + k1] = field_185382_B.func_177226_a(BlockColored.field_176581_a, EnumDyeColor.WHITE);
            if (j4 + k1 > 1 && random.nextBoolean()) {
               this.field_150621_aC[j4 + k1 - 1] = field_185382_B.func_177226_a(BlockColored.field_176581_a, EnumDyeColor.SILVER);
            }

            if (j4 + k1 < 63 && random.nextBoolean()) {
               this.field_150621_aC[j4 + k1 + 1] = field_185382_B.func_177226_a(BlockColored.field_176581_a, EnumDyeColor.SILVER);
            }
         }
      }

   }

   private IBlockState func_180629_a(int p_180629_1_, int p_180629_2_, int p_180629_3_) {
      int i = (int)Math.round(this.field_150625_aG.func_151601_a((double)p_180629_1_ / 512.0D, (double)p_180629_1_ / 512.0D) * 2.0D);
      return this.field_150621_aC[(p_180629_2_ + i + 64) % 64];
   }

   class Decorator extends BiomeDecorator {
      private Decorator() {
      }

      protected void func_76797_b(World p_76797_1_, Random p_76797_2_) {
         super.func_76797_b(p_76797_1_, p_76797_2_);
         this.func_76795_a(p_76797_1_, p_76797_2_, 20, this.field_76819_m, 32, 80);
      }
   }
}
