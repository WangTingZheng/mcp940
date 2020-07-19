package net.minecraft.world.gen;

import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldEntitySpawner;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.feature.WorldGenDungeons;
import net.minecraft.world.gen.feature.WorldGenLakes;
import net.minecraft.world.gen.structure.MapGenMineshaft;
import net.minecraft.world.gen.structure.MapGenScatteredFeature;
import net.minecraft.world.gen.structure.MapGenStronghold;
import net.minecraft.world.gen.structure.MapGenVillage;
import net.minecraft.world.gen.structure.StructureOceanMonument;
import net.minecraft.world.gen.structure.WoodlandMansion;

public class ChunkGeneratorOverworld implements IChunkGenerator {
   protected static final IBlockState field_185982_a = Blocks.field_150348_b.func_176223_P();
   private final Random field_185990_i;
   private final NoiseGeneratorOctaves field_185991_j;
   private final NoiseGeneratorOctaves field_185992_k;
   private final NoiseGeneratorOctaves field_185993_l;
   private final NoiseGeneratorPerlin field_185994_m;
   public NoiseGeneratorOctaves field_185983_b;
   public NoiseGeneratorOctaves field_185984_c;
   public NoiseGeneratorOctaves field_185985_d;
   private final World field_185995_n;
   private final boolean field_185996_o;
   private final WorldType field_185997_p;
   private final double[] field_185998_q;
   private final float[] field_185999_r;
   private ChunkGeneratorSettings field_186000_s;
   private IBlockState field_186001_t = Blocks.field_150355_j.func_176223_P();
   private double[] field_186002_u = new double[256];
   private final MapGenBase field_186003_v = new MapGenCaves();
   private final MapGenStronghold field_186004_w = new MapGenStronghold();
   private final MapGenVillage field_186005_x = new MapGenVillage();
   private final MapGenMineshaft field_186006_y = new MapGenMineshaft();
   private final MapGenScatteredFeature field_186007_z = new MapGenScatteredFeature();
   private final MapGenBase field_185979_A = new MapGenRavine();
   private final StructureOceanMonument field_185980_B = new StructureOceanMonument();
   private final WoodlandMansion field_191060_C = new WoodlandMansion(this);
   private Biome[] field_185981_C;
   double[] field_185986_e;
   double[] field_185987_f;
   double[] field_185988_g;
   double[] field_185989_h;

   public ChunkGeneratorOverworld(World p_i46668_1_, long p_i46668_2_, boolean p_i46668_4_, String p_i46668_5_) {
      this.field_185995_n = p_i46668_1_;
      this.field_185996_o = p_i46668_4_;
      this.field_185997_p = p_i46668_1_.func_72912_H().func_76067_t();
      this.field_185990_i = new Random(p_i46668_2_);
      this.field_185991_j = new NoiseGeneratorOctaves(this.field_185990_i, 16);
      this.field_185992_k = new NoiseGeneratorOctaves(this.field_185990_i, 16);
      this.field_185993_l = new NoiseGeneratorOctaves(this.field_185990_i, 8);
      this.field_185994_m = new NoiseGeneratorPerlin(this.field_185990_i, 4);
      this.field_185983_b = new NoiseGeneratorOctaves(this.field_185990_i, 10);
      this.field_185984_c = new NoiseGeneratorOctaves(this.field_185990_i, 16);
      this.field_185985_d = new NoiseGeneratorOctaves(this.field_185990_i, 8);
      this.field_185998_q = new double[825];
      this.field_185999_r = new float[25];

      for(int i = -2; i <= 2; ++i) {
         for(int j = -2; j <= 2; ++j) {
            float f = 10.0F / MathHelper.func_76129_c((float)(i * i + j * j) + 0.2F);
            this.field_185999_r[i + 2 + (j + 2) * 5] = f;
         }
      }

      if (p_i46668_5_ != null) {
         this.field_186000_s = ChunkGeneratorSettings.Factory.func_177865_a(p_i46668_5_).func_177864_b();
         this.field_186001_t = this.field_186000_s.field_177778_E ? Blocks.field_150353_l.func_176223_P() : Blocks.field_150355_j.func_176223_P();
         p_i46668_1_.func_181544_b(this.field_186000_s.field_177841_q);
      }

   }

   public void func_185976_a(int p_185976_1_, int p_185976_2_, ChunkPrimer p_185976_3_) {
      this.field_185981_C = this.field_185995_n.func_72959_q().func_76937_a(this.field_185981_C, p_185976_1_ * 4 - 2, p_185976_2_ * 4 - 2, 10, 10);
      this.func_185978_a(p_185976_1_ * 4, 0, p_185976_2_ * 4);

      for(int i = 0; i < 4; ++i) {
         int j = i * 5;
         int k = (i + 1) * 5;

         for(int l = 0; l < 4; ++l) {
            int i1 = (j + l) * 33;
            int j1 = (j + l + 1) * 33;
            int k1 = (k + l) * 33;
            int l1 = (k + l + 1) * 33;

            for(int i2 = 0; i2 < 32; ++i2) {
               double d0 = 0.125D;
               double d1 = this.field_185998_q[i1 + i2];
               double d2 = this.field_185998_q[j1 + i2];
               double d3 = this.field_185998_q[k1 + i2];
               double d4 = this.field_185998_q[l1 + i2];
               double d5 = (this.field_185998_q[i1 + i2 + 1] - d1) * 0.125D;
               double d6 = (this.field_185998_q[j1 + i2 + 1] - d2) * 0.125D;
               double d7 = (this.field_185998_q[k1 + i2 + 1] - d3) * 0.125D;
               double d8 = (this.field_185998_q[l1 + i2 + 1] - d4) * 0.125D;

               for(int j2 = 0; j2 < 8; ++j2) {
                  double d9 = 0.25D;
                  double d10 = d1;
                  double d11 = d2;
                  double d12 = (d3 - d1) * 0.25D;
                  double d13 = (d4 - d2) * 0.25D;

                  for(int k2 = 0; k2 < 4; ++k2) {
                     double d14 = 0.25D;
                     double d16 = (d11 - d10) * 0.25D;
                     double lvt_45_1_ = d10 - d16;

                     for(int l2 = 0; l2 < 4; ++l2) {
                        if ((lvt_45_1_ += d16) > 0.0D) {
                           p_185976_3_.func_177855_a(i * 4 + k2, i2 * 8 + j2, l * 4 + l2, field_185982_a);
                        } else if (i2 * 8 + j2 < this.field_186000_s.field_177841_q) {
                           p_185976_3_.func_177855_a(i * 4 + k2, i2 * 8 + j2, l * 4 + l2, this.field_186001_t);
                        }
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

   public void func_185977_a(int p_185977_1_, int p_185977_2_, ChunkPrimer p_185977_3_, Biome[] p_185977_4_) {
      double d0 = 0.03125D;
      this.field_186002_u = this.field_185994_m.func_151599_a(this.field_186002_u, (double)(p_185977_1_ * 16), (double)(p_185977_2_ * 16), 16, 16, 0.0625D, 0.0625D, 1.0D);

      for(int i = 0; i < 16; ++i) {
         for(int j = 0; j < 16; ++j) {
            Biome biome = p_185977_4_[j + i * 16];
            biome.func_180622_a(this.field_185995_n, this.field_185990_i, p_185977_3_, p_185977_1_ * 16 + i, p_185977_2_ * 16 + j, this.field_186002_u[j + i * 16]);
         }
      }

   }

   public Chunk func_185932_a(int p_185932_1_, int p_185932_2_) {
      this.field_185990_i.setSeed((long)p_185932_1_ * 341873128712L + (long)p_185932_2_ * 132897987541L);
      ChunkPrimer chunkprimer = new ChunkPrimer();
      this.func_185976_a(p_185932_1_, p_185932_2_, chunkprimer);
      this.field_185981_C = this.field_185995_n.func_72959_q().func_76933_b(this.field_185981_C, p_185932_1_ * 16, p_185932_2_ * 16, 16, 16);
      this.func_185977_a(p_185932_1_, p_185932_2_, chunkprimer, this.field_185981_C);
      if (this.field_186000_s.field_177839_r) {
         this.field_186003_v.func_186125_a(this.field_185995_n, p_185932_1_, p_185932_2_, chunkprimer);
      }

      if (this.field_186000_s.field_177850_z) {
         this.field_185979_A.func_186125_a(this.field_185995_n, p_185932_1_, p_185932_2_, chunkprimer);
      }

      if (this.field_185996_o) {
         if (this.field_186000_s.field_177829_w) {
            this.field_186006_y.func_186125_a(this.field_185995_n, p_185932_1_, p_185932_2_, chunkprimer);
         }

         if (this.field_186000_s.field_177831_v) {
            this.field_186005_x.func_186125_a(this.field_185995_n, p_185932_1_, p_185932_2_, chunkprimer);
         }

         if (this.field_186000_s.field_177833_u) {
            this.field_186004_w.func_186125_a(this.field_185995_n, p_185932_1_, p_185932_2_, chunkprimer);
         }

         if (this.field_186000_s.field_177854_x) {
            this.field_186007_z.func_186125_a(this.field_185995_n, p_185932_1_, p_185932_2_, chunkprimer);
         }

         if (this.field_186000_s.field_177852_y) {
            this.field_185980_B.func_186125_a(this.field_185995_n, p_185932_1_, p_185932_2_, chunkprimer);
         }

         if (this.field_186000_s.field_191077_z) {
            this.field_191060_C.func_186125_a(this.field_185995_n, p_185932_1_, p_185932_2_, chunkprimer);
         }
      }

      Chunk chunk = new Chunk(this.field_185995_n, chunkprimer, p_185932_1_, p_185932_2_);
      byte[] abyte = chunk.func_76605_m();

      for(int i = 0; i < abyte.length; ++i) {
         abyte[i] = (byte)Biome.func_185362_a(this.field_185981_C[i]);
      }

      chunk.func_76603_b();
      return chunk;
   }

   private void func_185978_a(int p_185978_1_, int p_185978_2_, int p_185978_3_) {
      this.field_185989_h = this.field_185984_c.func_76305_a(this.field_185989_h, p_185978_1_, p_185978_3_, 5, 5, (double)this.field_186000_s.field_177808_e, (double)this.field_186000_s.field_177803_f, (double)this.field_186000_s.field_177804_g);
      float f = this.field_186000_s.field_177811_a;
      float f1 = this.field_186000_s.field_177809_b;
      this.field_185986_e = this.field_185993_l.func_76304_a(this.field_185986_e, p_185978_1_, p_185978_2_, p_185978_3_, 5, 33, 5, (double)(f / this.field_186000_s.field_177825_h), (double)(f1 / this.field_186000_s.field_177827_i), (double)(f / this.field_186000_s.field_177821_j));
      this.field_185987_f = this.field_185991_j.func_76304_a(this.field_185987_f, p_185978_1_, p_185978_2_, p_185978_3_, 5, 33, 5, (double)f, (double)f1, (double)f);
      this.field_185988_g = this.field_185992_k.func_76304_a(this.field_185988_g, p_185978_1_, p_185978_2_, p_185978_3_, 5, 33, 5, (double)f, (double)f1, (double)f);
      int i = 0;
      int j = 0;

      for(int k = 0; k < 5; ++k) {
         for(int l = 0; l < 5; ++l) {
            float f2 = 0.0F;
            float f3 = 0.0F;
            float f4 = 0.0F;
            int i1 = 2;
            Biome biome = this.field_185981_C[k + 2 + (l + 2) * 10];

            for(int j1 = -2; j1 <= 2; ++j1) {
               for(int k1 = -2; k1 <= 2; ++k1) {
                  Biome biome1 = this.field_185981_C[k + j1 + 2 + (l + k1 + 2) * 10];
                  float f5 = this.field_186000_s.field_177813_n + biome1.func_185355_j() * this.field_186000_s.field_177819_m;
                  float f6 = this.field_186000_s.field_177843_p + biome1.func_185360_m() * this.field_186000_s.field_177815_o;
                  if (this.field_185997_p == WorldType.field_151360_e && f5 > 0.0F) {
                     f5 = 1.0F + f5 * 2.0F;
                     f6 = 1.0F + f6 * 4.0F;
                  }

                  float f7 = this.field_185999_r[j1 + 2 + (k1 + 2) * 5] / (f5 + 2.0F);
                  if (biome1.func_185355_j() > biome.func_185355_j()) {
                     f7 /= 2.0F;
                  }

                  f2 += f6 * f7;
                  f3 += f5 * f7;
                  f4 += f7;
               }
            }

            f2 = f2 / f4;
            f3 = f3 / f4;
            f2 = f2 * 0.9F + 0.1F;
            f3 = (f3 * 4.0F - 1.0F) / 8.0F;
            double d7 = this.field_185989_h[j] / 8000.0D;
            if (d7 < 0.0D) {
               d7 = -d7 * 0.3D;
            }

            d7 = d7 * 3.0D - 2.0D;
            if (d7 < 0.0D) {
               d7 = d7 / 2.0D;
               if (d7 < -1.0D) {
                  d7 = -1.0D;
               }

               d7 = d7 / 1.4D;
               d7 = d7 / 2.0D;
            } else {
               if (d7 > 1.0D) {
                  d7 = 1.0D;
               }

               d7 = d7 / 8.0D;
            }

            ++j;
            double d8 = (double)f3;
            double d9 = (double)f2;
            d8 = d8 + d7 * 0.2D;
            d8 = d8 * (double)this.field_186000_s.field_177823_k / 8.0D;
            double d0 = (double)this.field_186000_s.field_177823_k + d8 * 4.0D;

            for(int l1 = 0; l1 < 33; ++l1) {
               double d1 = ((double)l1 - d0) * (double)this.field_186000_s.field_177817_l * 128.0D / 256.0D / d9;
               if (d1 < 0.0D) {
                  d1 *= 4.0D;
               }

               double d2 = this.field_185987_f[i] / (double)this.field_186000_s.field_177806_d;
               double d3 = this.field_185988_g[i] / (double)this.field_186000_s.field_177810_c;
               double d4 = (this.field_185986_e[i] / 10.0D + 1.0D) / 2.0D;
               double d5 = MathHelper.func_151238_b(d2, d3, d4) - d1;
               if (l1 > 29) {
                  double d6 = (double)((float)(l1 - 29) / 3.0F);
                  d5 = d5 * (1.0D - d6) + -10.0D * d6;
               }

               this.field_185998_q[i] = d5;
               ++i;
            }
         }
      }

   }

   public void func_185931_b(int p_185931_1_, int p_185931_2_) {
      BlockFalling.field_149832_M = true;
      int i = p_185931_1_ * 16;
      int j = p_185931_2_ * 16;
      BlockPos blockpos = new BlockPos(i, 0, j);
      Biome biome = this.field_185995_n.func_180494_b(blockpos.func_177982_a(16, 0, 16));
      this.field_185990_i.setSeed(this.field_185995_n.func_72905_C());
      long k = this.field_185990_i.nextLong() / 2L * 2L + 1L;
      long l = this.field_185990_i.nextLong() / 2L * 2L + 1L;
      this.field_185990_i.setSeed((long)p_185931_1_ * k + (long)p_185931_2_ * l ^ this.field_185995_n.func_72905_C());
      boolean flag = false;
      ChunkPos chunkpos = new ChunkPos(p_185931_1_, p_185931_2_);
      if (this.field_185996_o) {
         if (this.field_186000_s.field_177829_w) {
            this.field_186006_y.func_175794_a(this.field_185995_n, this.field_185990_i, chunkpos);
         }

         if (this.field_186000_s.field_177831_v) {
            flag = this.field_186005_x.func_175794_a(this.field_185995_n, this.field_185990_i, chunkpos);
         }

         if (this.field_186000_s.field_177833_u) {
            this.field_186004_w.func_175794_a(this.field_185995_n, this.field_185990_i, chunkpos);
         }

         if (this.field_186000_s.field_177854_x) {
            this.field_186007_z.func_175794_a(this.field_185995_n, this.field_185990_i, chunkpos);
         }

         if (this.field_186000_s.field_177852_y) {
            this.field_185980_B.func_175794_a(this.field_185995_n, this.field_185990_i, chunkpos);
         }

         if (this.field_186000_s.field_191077_z) {
            this.field_191060_C.func_175794_a(this.field_185995_n, this.field_185990_i, chunkpos);
         }
      }

      if (biome != Biomes.field_76769_d && biome != Biomes.field_76786_s && this.field_186000_s.field_177781_A && !flag && this.field_185990_i.nextInt(this.field_186000_s.field_177782_B) == 0) {
         int i1 = this.field_185990_i.nextInt(16) + 8;
         int j1 = this.field_185990_i.nextInt(256);
         int k1 = this.field_185990_i.nextInt(16) + 8;
         (new WorldGenLakes(Blocks.field_150355_j)).func_180709_b(this.field_185995_n, this.field_185990_i, blockpos.func_177982_a(i1, j1, k1));
      }

      if (!flag && this.field_185990_i.nextInt(this.field_186000_s.field_177777_D / 10) == 0 && this.field_186000_s.field_177783_C) {
         int i2 = this.field_185990_i.nextInt(16) + 8;
         int l2 = this.field_185990_i.nextInt(this.field_185990_i.nextInt(248) + 8);
         int k3 = this.field_185990_i.nextInt(16) + 8;
         if (l2 < this.field_185995_n.func_181545_F() || this.field_185990_i.nextInt(this.field_186000_s.field_177777_D / 8) == 0) {
            (new WorldGenLakes(Blocks.field_150353_l)).func_180709_b(this.field_185995_n, this.field_185990_i, blockpos.func_177982_a(i2, l2, k3));
         }
      }

      if (this.field_186000_s.field_177837_s) {
         for(int j2 = 0; j2 < this.field_186000_s.field_177835_t; ++j2) {
            int i3 = this.field_185990_i.nextInt(16) + 8;
            int l3 = this.field_185990_i.nextInt(256);
            int l1 = this.field_185990_i.nextInt(16) + 8;
            (new WorldGenDungeons()).func_180709_b(this.field_185995_n, this.field_185990_i, blockpos.func_177982_a(i3, l3, l1));
         }
      }

      biome.func_180624_a(this.field_185995_n, this.field_185990_i, new BlockPos(i, 0, j));
      WorldEntitySpawner.func_77191_a(this.field_185995_n, biome, i + 8, j + 8, 16, 16, this.field_185990_i);
      blockpos = blockpos.func_177982_a(8, 0, 8);

      for(int k2 = 0; k2 < 16; ++k2) {
         for(int j3 = 0; j3 < 16; ++j3) {
            BlockPos blockpos1 = this.field_185995_n.func_175725_q(blockpos.func_177982_a(k2, 0, j3));
            BlockPos blockpos2 = blockpos1.func_177977_b();
            if (this.field_185995_n.func_175675_v(blockpos2)) {
               this.field_185995_n.func_180501_a(blockpos2, Blocks.field_150432_aD.func_176223_P(), 2);
            }

            if (this.field_185995_n.func_175708_f(blockpos1, true)) {
               this.field_185995_n.func_180501_a(blockpos1, Blocks.field_150431_aC.func_176223_P(), 2);
            }
         }
      }

      BlockFalling.field_149832_M = false;
   }

   public boolean func_185933_a(Chunk p_185933_1_, int p_185933_2_, int p_185933_3_) {
      boolean flag = false;
      if (this.field_186000_s.field_177852_y && this.field_185996_o && p_185933_1_.func_177416_w() < 3600L) {
         flag |= this.field_185980_B.func_175794_a(this.field_185995_n, this.field_185990_i, new ChunkPos(p_185933_2_, p_185933_3_));
      }

      return flag;
   }

   public List<Biome.SpawnListEntry> func_177458_a(EnumCreatureType p_177458_1_, BlockPos p_177458_2_) {
      Biome biome = this.field_185995_n.func_180494_b(p_177458_2_);
      if (this.field_185996_o) {
         if (p_177458_1_ == EnumCreatureType.MONSTER && this.field_186007_z.func_175798_a(p_177458_2_)) {
            return this.field_186007_z.func_82667_a();
         }

         if (p_177458_1_ == EnumCreatureType.MONSTER && this.field_186000_s.field_177852_y && this.field_185980_B.func_175796_a(this.field_185995_n, p_177458_2_)) {
            return this.field_185980_B.func_175799_b();
         }
      }

      return biome.func_76747_a(p_177458_1_);
   }

   public boolean func_193414_a(World p_193414_1_, String p_193414_2_, BlockPos p_193414_3_) {
      if (!this.field_185996_o) {
         return false;
      } else if ("Stronghold".equals(p_193414_2_) && this.field_186004_w != null) {
         return this.field_186004_w.func_175795_b(p_193414_3_);
      } else if ("Mansion".equals(p_193414_2_) && this.field_191060_C != null) {
         return this.field_191060_C.func_175795_b(p_193414_3_);
      } else if ("Monument".equals(p_193414_2_) && this.field_185980_B != null) {
         return this.field_185980_B.func_175795_b(p_193414_3_);
      } else if ("Village".equals(p_193414_2_) && this.field_186005_x != null) {
         return this.field_186005_x.func_175795_b(p_193414_3_);
      } else if ("Mineshaft".equals(p_193414_2_) && this.field_186006_y != null) {
         return this.field_186006_y.func_175795_b(p_193414_3_);
      } else {
         return "Temple".equals(p_193414_2_) && this.field_186007_z != null ? this.field_186007_z.func_175795_b(p_193414_3_) : false;
      }
   }

   @Nullable
   public BlockPos func_180513_a(World p_180513_1_, String p_180513_2_, BlockPos p_180513_3_, boolean p_180513_4_) {
      if (!this.field_185996_o) {
         return null;
      } else if ("Stronghold".equals(p_180513_2_) && this.field_186004_w != null) {
         return this.field_186004_w.func_180706_b(p_180513_1_, p_180513_3_, p_180513_4_);
      } else if ("Mansion".equals(p_180513_2_) && this.field_191060_C != null) {
         return this.field_191060_C.func_180706_b(p_180513_1_, p_180513_3_, p_180513_4_);
      } else if ("Monument".equals(p_180513_2_) && this.field_185980_B != null) {
         return this.field_185980_B.func_180706_b(p_180513_1_, p_180513_3_, p_180513_4_);
      } else if ("Village".equals(p_180513_2_) && this.field_186005_x != null) {
         return this.field_186005_x.func_180706_b(p_180513_1_, p_180513_3_, p_180513_4_);
      } else if ("Mineshaft".equals(p_180513_2_) && this.field_186006_y != null) {
         return this.field_186006_y.func_180706_b(p_180513_1_, p_180513_3_, p_180513_4_);
      } else {
         return "Temple".equals(p_180513_2_) && this.field_186007_z != null ? this.field_186007_z.func_180706_b(p_180513_1_, p_180513_3_, p_180513_4_) : null;
      }
   }

   public void func_180514_a(Chunk p_180514_1_, int p_180514_2_, int p_180514_3_) {
      if (this.field_185996_o) {
         if (this.field_186000_s.field_177829_w) {
            this.field_186006_y.func_186125_a(this.field_185995_n, p_180514_2_, p_180514_3_, (ChunkPrimer)null);
         }

         if (this.field_186000_s.field_177831_v) {
            this.field_186005_x.func_186125_a(this.field_185995_n, p_180514_2_, p_180514_3_, (ChunkPrimer)null);
         }

         if (this.field_186000_s.field_177833_u) {
            this.field_186004_w.func_186125_a(this.field_185995_n, p_180514_2_, p_180514_3_, (ChunkPrimer)null);
         }

         if (this.field_186000_s.field_177854_x) {
            this.field_186007_z.func_186125_a(this.field_185995_n, p_180514_2_, p_180514_3_, (ChunkPrimer)null);
         }

         if (this.field_186000_s.field_177852_y) {
            this.field_185980_B.func_186125_a(this.field_185995_n, p_180514_2_, p_180514_3_, (ChunkPrimer)null);
         }

         if (this.field_186000_s.field_191077_z) {
            this.field_191060_C.func_186125_a(this.field_185995_n, p_180514_2_, p_180514_3_, (ChunkPrimer)null);
         }
      }

   }
}
