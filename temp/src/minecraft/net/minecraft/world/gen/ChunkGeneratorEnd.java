package net.minecraft.world.gen;

import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.BlockChorusFlower;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityEndGateway;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.feature.WorldGenEndGateway;
import net.minecraft.world.gen.feature.WorldGenEndIsland;
import net.minecraft.world.gen.structure.MapGenEndCity;

public class ChunkGeneratorEnd implements IChunkGenerator {
   private final Random field_73220_k;
   protected static final IBlockState field_185964_a = Blocks.field_150377_bs.func_176223_P();
   protected static final IBlockState field_185965_b = Blocks.field_150350_a.func_176223_P();
   private final NoiseGeneratorOctaves field_185969_i;
   private final NoiseGeneratorOctaves field_185970_j;
   private final NoiseGeneratorOctaves field_185971_k;
   public NoiseGeneratorOctaves field_73214_a;
   public NoiseGeneratorOctaves field_73212_b;
   private final World field_73230_p;
   private final boolean field_73229_q;
   private final BlockPos field_191061_n;
   private final MapGenEndCity field_185972_n = new MapGenEndCity(this);
   private final NoiseGeneratorSimplex field_185973_o;
   private double[] field_185974_p;
   private Biome[] field_73231_z;
   double[] field_185966_e;
   double[] field_185967_f;
   double[] field_185968_g;
   private final WorldGenEndIsland field_185975_r = new WorldGenEndIsland();

   public ChunkGeneratorEnd(World p_i47241_1_, boolean p_i47241_2_, long p_i47241_3_, BlockPos p_i47241_5_) {
      this.field_73230_p = p_i47241_1_;
      this.field_73229_q = p_i47241_2_;
      this.field_191061_n = p_i47241_5_;
      this.field_73220_k = new Random(p_i47241_3_);
      this.field_185969_i = new NoiseGeneratorOctaves(this.field_73220_k, 16);
      this.field_185970_j = new NoiseGeneratorOctaves(this.field_73220_k, 16);
      this.field_185971_k = new NoiseGeneratorOctaves(this.field_73220_k, 8);
      this.field_73214_a = new NoiseGeneratorOctaves(this.field_73220_k, 10);
      this.field_73212_b = new NoiseGeneratorOctaves(this.field_73220_k, 16);
      this.field_185973_o = new NoiseGeneratorSimplex(this.field_73220_k);
   }

   public void func_180518_a(int p_180518_1_, int p_180518_2_, ChunkPrimer p_180518_3_) {
      int i = 2;
      int j = 3;
      int k = 33;
      int l = 3;
      this.field_185974_p = this.func_185963_a(this.field_185974_p, p_180518_1_ * 2, 0, p_180518_2_ * 2, 3, 33, 3);

      for(int i1 = 0; i1 < 2; ++i1) {
         for(int j1 = 0; j1 < 2; ++j1) {
            for(int k1 = 0; k1 < 32; ++k1) {
               double d0 = 0.25D;
               double d1 = this.field_185974_p[((i1 + 0) * 3 + j1 + 0) * 33 + k1 + 0];
               double d2 = this.field_185974_p[((i1 + 0) * 3 + j1 + 1) * 33 + k1 + 0];
               double d3 = this.field_185974_p[((i1 + 1) * 3 + j1 + 0) * 33 + k1 + 0];
               double d4 = this.field_185974_p[((i1 + 1) * 3 + j1 + 1) * 33 + k1 + 0];
               double d5 = (this.field_185974_p[((i1 + 0) * 3 + j1 + 0) * 33 + k1 + 1] - d1) * 0.25D;
               double d6 = (this.field_185974_p[((i1 + 0) * 3 + j1 + 1) * 33 + k1 + 1] - d2) * 0.25D;
               double d7 = (this.field_185974_p[((i1 + 1) * 3 + j1 + 0) * 33 + k1 + 1] - d3) * 0.25D;
               double d8 = (this.field_185974_p[((i1 + 1) * 3 + j1 + 1) * 33 + k1 + 1] - d4) * 0.25D;

               for(int l1 = 0; l1 < 4; ++l1) {
                  double d9 = 0.125D;
                  double d10 = d1;
                  double d11 = d2;
                  double d12 = (d3 - d1) * 0.125D;
                  double d13 = (d4 - d2) * 0.125D;

                  for(int i2 = 0; i2 < 8; ++i2) {
                     double d14 = 0.125D;
                     double d15 = d10;
                     double d16 = (d11 - d10) * 0.125D;

                     for(int j2 = 0; j2 < 8; ++j2) {
                        IBlockState iblockstate = field_185965_b;
                        if (d15 > 0.0D) {
                           iblockstate = field_185964_a;
                        }

                        int k2 = i2 + i1 * 8;
                        int l2 = l1 + k1 * 4;
                        int i3 = j2 + j1 * 8;
                        p_180518_3_.func_177855_a(k2, l2, i3, iblockstate);
                        d15 += d16;
                     }

                     d10 += d12;
                     d11 += d13;
                  }

                  d1 += d5;
                  d2 += d6;
                  d3 += d7;
                  d4 += d8;
               }
            }
         }
      }

   }

   public void func_185962_a(ChunkPrimer p_185962_1_) {
      for(int i = 0; i < 16; ++i) {
         for(int j = 0; j < 16; ++j) {
            int k = 1;
            int l = -1;
            IBlockState iblockstate = field_185964_a;
            IBlockState iblockstate1 = field_185964_a;

            for(int i1 = 127; i1 >= 0; --i1) {
               IBlockState iblockstate2 = p_185962_1_.func_177856_a(i, i1, j);
               if (iblockstate2.func_185904_a() == Material.field_151579_a) {
                  l = -1;
               } else if (iblockstate2.func_177230_c() == Blocks.field_150348_b) {
                  if (l == -1) {
                     l = 1;
                     if (i1 >= 0) {
                        p_185962_1_.func_177855_a(i, i1, j, iblockstate);
                     } else {
                        p_185962_1_.func_177855_a(i, i1, j, iblockstate1);
                     }
                  } else if (l > 0) {
                     --l;
                     p_185962_1_.func_177855_a(i, i1, j, iblockstate1);
                  }
               }
            }
         }
      }

   }

   public Chunk func_185932_a(int p_185932_1_, int p_185932_2_) {
      this.field_73220_k.setSeed((long)p_185932_1_ * 341873128712L + (long)p_185932_2_ * 132897987541L);
      ChunkPrimer chunkprimer = new ChunkPrimer();
      this.field_73231_z = this.field_73230_p.func_72959_q().func_76933_b(this.field_73231_z, p_185932_1_ * 16, p_185932_2_ * 16, 16, 16);
      this.func_180518_a(p_185932_1_, p_185932_2_, chunkprimer);
      this.func_185962_a(chunkprimer);
      if (this.field_73229_q) {
         this.field_185972_n.func_186125_a(this.field_73230_p, p_185932_1_, p_185932_2_, chunkprimer);
      }

      Chunk chunk = new Chunk(this.field_73230_p, chunkprimer, p_185932_1_, p_185932_2_);
      byte[] abyte = chunk.func_76605_m();

      for(int i = 0; i < abyte.length; ++i) {
         abyte[i] = (byte)Biome.func_185362_a(this.field_73231_z[i]);
      }

      chunk.func_76603_b();
      return chunk;
   }

   private float func_185960_a(int p_185960_1_, int p_185960_2_, int p_185960_3_, int p_185960_4_) {
      float f = (float)(p_185960_1_ * 2 + p_185960_3_);
      float f1 = (float)(p_185960_2_ * 2 + p_185960_4_);
      float f2 = 100.0F - MathHelper.func_76129_c(f * f + f1 * f1) * 8.0F;
      if (f2 > 80.0F) {
         f2 = 80.0F;
      }

      if (f2 < -100.0F) {
         f2 = -100.0F;
      }

      for(int i = -12; i <= 12; ++i) {
         for(int j = -12; j <= 12; ++j) {
            long k = (long)(p_185960_1_ + i);
            long l = (long)(p_185960_2_ + j);
            if (k * k + l * l > 4096L && this.field_185973_o.func_151605_a((double)k, (double)l) < -0.8999999761581421D) {
               float f3 = (MathHelper.func_76135_e((float)k) * 3439.0F + MathHelper.func_76135_e((float)l) * 147.0F) % 13.0F + 9.0F;
               f = (float)(p_185960_3_ - i * 2);
               f1 = (float)(p_185960_4_ - j * 2);
               float f4 = 100.0F - MathHelper.func_76129_c(f * f + f1 * f1) * f3;
               if (f4 > 80.0F) {
                  f4 = 80.0F;
               }

               if (f4 < -100.0F) {
                  f4 = -100.0F;
               }

               if (f4 > f2) {
                  f2 = f4;
               }
            }
         }
      }

      return f2;
   }

   public boolean func_185961_c(int p_185961_1_, int p_185961_2_) {
      return (long)p_185961_1_ * (long)p_185961_1_ + (long)p_185961_2_ * (long)p_185961_2_ > 4096L && this.func_185960_a(p_185961_1_, p_185961_2_, 1, 1) >= 0.0F;
   }

   private double[] func_185963_a(double[] p_185963_1_, int p_185963_2_, int p_185963_3_, int p_185963_4_, int p_185963_5_, int p_185963_6_, int p_185963_7_) {
      if (p_185963_1_ == null) {
         p_185963_1_ = new double[p_185963_5_ * p_185963_6_ * p_185963_7_];
      }

      double d0 = 684.412D;
      double d1 = 684.412D;
      d0 = d0 * 2.0D;
      this.field_185966_e = this.field_185971_k.func_76304_a(this.field_185966_e, p_185963_2_, p_185963_3_, p_185963_4_, p_185963_5_, p_185963_6_, p_185963_7_, d0 / 80.0D, 4.277575000000001D, d0 / 80.0D);
      this.field_185967_f = this.field_185969_i.func_76304_a(this.field_185967_f, p_185963_2_, p_185963_3_, p_185963_4_, p_185963_5_, p_185963_6_, p_185963_7_, d0, 684.412D, d0);
      this.field_185968_g = this.field_185970_j.func_76304_a(this.field_185968_g, p_185963_2_, p_185963_3_, p_185963_4_, p_185963_5_, p_185963_6_, p_185963_7_, d0, 684.412D, d0);
      int i = p_185963_2_ / 2;
      int j = p_185963_4_ / 2;
      int k = 0;

      for(int l = 0; l < p_185963_5_; ++l) {
         for(int i1 = 0; i1 < p_185963_7_; ++i1) {
            float f = this.func_185960_a(i, j, l, i1);

            for(int j1 = 0; j1 < p_185963_6_; ++j1) {
               double d2 = this.field_185967_f[k] / 512.0D;
               double d3 = this.field_185968_g[k] / 512.0D;
               double d5 = (this.field_185966_e[k] / 10.0D + 1.0D) / 2.0D;
               double d4;
               if (d5 < 0.0D) {
                  d4 = d2;
               } else if (d5 > 1.0D) {
                  d4 = d3;
               } else {
                  d4 = d2 + (d3 - d2) * d5;
               }

               d4 = d4 - 8.0D;
               d4 = d4 + (double)f;
               int k1 = 2;
               if (j1 > p_185963_6_ / 2 - k1) {
                  double d6 = (double)((float)(j1 - (p_185963_6_ / 2 - k1)) / 64.0F);
                  d6 = MathHelper.func_151237_a(d6, 0.0D, 1.0D);
                  d4 = d4 * (1.0D - d6) + -3000.0D * d6;
               }

               k1 = 8;
               if (j1 < k1) {
                  double d7 = (double)((float)(k1 - j1) / ((float)k1 - 1.0F));
                  d4 = d4 * (1.0D - d7) + -30.0D * d7;
               }

               p_185963_1_[k] = d4;
               ++k;
            }
         }
      }

      return p_185963_1_;
   }

   public void func_185931_b(int p_185931_1_, int p_185931_2_) {
      BlockFalling.field_149832_M = true;
      BlockPos blockpos = new BlockPos(p_185931_1_ * 16, 0, p_185931_2_ * 16);
      if (this.field_73229_q) {
         this.field_185972_n.func_175794_a(this.field_73230_p, this.field_73220_k, new ChunkPos(p_185931_1_, p_185931_2_));
      }

      this.field_73230_p.func_180494_b(blockpos.func_177982_a(16, 0, 16)).func_180624_a(this.field_73230_p, this.field_73230_p.field_73012_v, blockpos);
      long i = (long)p_185931_1_ * (long)p_185931_1_ + (long)p_185931_2_ * (long)p_185931_2_;
      if (i > 4096L) {
         float f = this.func_185960_a(p_185931_1_, p_185931_2_, 1, 1);
         if (f < -20.0F && this.field_73220_k.nextInt(14) == 0) {
            this.field_185975_r.func_180709_b(this.field_73230_p, this.field_73220_k, blockpos.func_177982_a(this.field_73220_k.nextInt(16) + 8, 55 + this.field_73220_k.nextInt(16), this.field_73220_k.nextInt(16) + 8));
            if (this.field_73220_k.nextInt(4) == 0) {
               this.field_185975_r.func_180709_b(this.field_73230_p, this.field_73220_k, blockpos.func_177982_a(this.field_73220_k.nextInt(16) + 8, 55 + this.field_73220_k.nextInt(16), this.field_73220_k.nextInt(16) + 8));
            }
         }

         if (this.func_185960_a(p_185931_1_, p_185931_2_, 1, 1) > 40.0F) {
            int j = this.field_73220_k.nextInt(5);

            for(int k = 0; k < j; ++k) {
               int l = this.field_73220_k.nextInt(16) + 8;
               int i1 = this.field_73220_k.nextInt(16) + 8;
               int j1 = this.field_73230_p.func_175645_m(blockpos.func_177982_a(l, 0, i1)).func_177956_o();
               if (j1 > 0) {
                  int k1 = j1 - 1;
                  if (this.field_73230_p.func_175623_d(blockpos.func_177982_a(l, k1 + 1, i1)) && this.field_73230_p.func_180495_p(blockpos.func_177982_a(l, k1, i1)).func_177230_c() == Blocks.field_150377_bs) {
                     BlockChorusFlower.func_185603_a(this.field_73230_p, blockpos.func_177982_a(l, k1 + 1, i1), this.field_73220_k, 8);
                  }
               }
            }

            if (this.field_73220_k.nextInt(700) == 0) {
               int l1 = this.field_73220_k.nextInt(16) + 8;
               int i2 = this.field_73220_k.nextInt(16) + 8;
               int j2 = this.field_73230_p.func_175645_m(blockpos.func_177982_a(l1, 0, i2)).func_177956_o();
               if (j2 > 0) {
                  int k2 = j2 + 3 + this.field_73220_k.nextInt(7);
                  BlockPos blockpos1 = blockpos.func_177982_a(l1, k2, i2);
                  (new WorldGenEndGateway()).func_180709_b(this.field_73230_p, this.field_73220_k, blockpos1);
                  TileEntity tileentity = this.field_73230_p.func_175625_s(blockpos1);
                  if (tileentity instanceof TileEntityEndGateway) {
                     TileEntityEndGateway tileentityendgateway = (TileEntityEndGateway)tileentity;
                     tileentityendgateway.func_190603_b(this.field_191061_n);
                  }
               }
            }
         }
      }

      BlockFalling.field_149832_M = false;
   }

   public boolean func_185933_a(Chunk p_185933_1_, int p_185933_2_, int p_185933_3_) {
      return false;
   }

   public List<Biome.SpawnListEntry> func_177458_a(EnumCreatureType p_177458_1_, BlockPos p_177458_2_) {
      return this.field_73230_p.func_180494_b(p_177458_2_).func_76747_a(p_177458_1_);
   }

   @Nullable
   public BlockPos func_180513_a(World p_180513_1_, String p_180513_2_, BlockPos p_180513_3_, boolean p_180513_4_) {
      return "EndCity".equals(p_180513_2_) && this.field_185972_n != null ? this.field_185972_n.func_180706_b(p_180513_1_, p_180513_3_, p_180513_4_) : null;
   }

   public boolean func_193414_a(World p_193414_1_, String p_193414_2_, BlockPos p_193414_3_) {
      return "EndCity".equals(p_193414_2_) && this.field_185972_n != null ? this.field_185972_n.func_175795_b(p_193414_3_) : false;
   }

   public void func_180514_a(Chunk p_180514_1_, int p_180514_2_, int p_180514_3_) {
   }
}
