package net.minecraft.world;

import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import java.util.Random;
import net.minecraft.block.BlockPortal;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockPattern;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;

public class Teleporter {
   private final WorldServer field_85192_a;
   private final Random field_77187_a;
   private final Long2ObjectMap<Teleporter.PortalPosition> field_85191_c = new Long2ObjectOpenHashMap<Teleporter.PortalPosition>(4096);

   public Teleporter(WorldServer p_i1963_1_) {
      this.field_85192_a = p_i1963_1_;
      this.field_77187_a = new Random(p_i1963_1_.func_72905_C());
   }

   public void func_180266_a(Entity p_180266_1_, float p_180266_2_) {
      if (this.field_85192_a.field_73011_w.func_186058_p().func_186068_a() != 1) {
         if (!this.func_180620_b(p_180266_1_, p_180266_2_)) {
            this.func_85188_a(p_180266_1_);
            this.func_180620_b(p_180266_1_, p_180266_2_);
         }
      } else {
         int i = MathHelper.func_76128_c(p_180266_1_.field_70165_t);
         int j = MathHelper.func_76128_c(p_180266_1_.field_70163_u) - 1;
         int k = MathHelper.func_76128_c(p_180266_1_.field_70161_v);
         int l = 1;
         int i1 = 0;

         for(int j1 = -2; j1 <= 2; ++j1) {
            for(int k1 = -2; k1 <= 2; ++k1) {
               for(int l1 = -1; l1 < 3; ++l1) {
                  int i2 = i + k1 * 1 + j1 * 0;
                  int j2 = j + l1;
                  int k2 = k + k1 * 0 - j1 * 1;
                  boolean flag = l1 < 0;
                  this.field_85192_a.func_175656_a(new BlockPos(i2, j2, k2), flag ? Blocks.field_150343_Z.func_176223_P() : Blocks.field_150350_a.func_176223_P());
               }
            }
         }

         p_180266_1_.func_70012_b((double)i, (double)j, (double)k, p_180266_1_.field_70177_z, 0.0F);
         p_180266_1_.field_70159_w = 0.0D;
         p_180266_1_.field_70181_x = 0.0D;
         p_180266_1_.field_70179_y = 0.0D;
      }
   }

   public boolean func_180620_b(Entity p_180620_1_, float p_180620_2_) {
      int i = 128;
      double d0 = -1.0D;
      int j = MathHelper.func_76128_c(p_180620_1_.field_70165_t);
      int k = MathHelper.func_76128_c(p_180620_1_.field_70161_v);
      boolean flag = true;
      BlockPos blockpos = BlockPos.field_177992_a;
      long l = ChunkPos.func_77272_a(j, k);
      if (this.field_85191_c.containsKey(l)) {
         Teleporter.PortalPosition teleporter$portalposition = (Teleporter.PortalPosition)this.field_85191_c.get(l);
         d0 = 0.0D;
         blockpos = teleporter$portalposition;
         teleporter$portalposition.field_85087_d = this.field_85192_a.func_82737_E();
         flag = false;
      } else {
         BlockPos blockpos3 = new BlockPos(p_180620_1_);

         for(int i1 = -128; i1 <= 128; ++i1) {
            BlockPos blockpos2;
            for(int j1 = -128; j1 <= 128; ++j1) {
               for(BlockPos blockpos1 = blockpos3.func_177982_a(i1, this.field_85192_a.func_72940_L() - 1 - blockpos3.func_177956_o(), j1); blockpos1.func_177956_o() >= 0; blockpos1 = blockpos2) {
                  blockpos2 = blockpos1.func_177977_b();
                  if (this.field_85192_a.func_180495_p(blockpos1).func_177230_c() == Blocks.field_150427_aO) {
                     for(blockpos2 = blockpos1.func_177977_b(); this.field_85192_a.func_180495_p(blockpos2).func_177230_c() == Blocks.field_150427_aO; blockpos2 = blockpos2.func_177977_b()) {
                        blockpos1 = blockpos2;
                     }

                     double d1 = blockpos1.func_177951_i(blockpos3);
                     if (d0 < 0.0D || d1 < d0) {
                        d0 = d1;
                        blockpos = blockpos1;
                     }
                  }
               }
            }
         }
      }

      if (d0 >= 0.0D) {
         if (flag) {
            this.field_85191_c.put(l, new Teleporter.PortalPosition(blockpos, this.field_85192_a.func_82737_E()));
         }

         double d5 = (double)blockpos.func_177958_n() + 0.5D;
         double d7 = (double)blockpos.func_177952_p() + 0.5D;
         BlockPattern.PatternHelper blockpattern$patternhelper = Blocks.field_150427_aO.func_181089_f(this.field_85192_a, blockpos);
         boolean flag1 = blockpattern$patternhelper.func_177669_b().func_176746_e().func_176743_c() == EnumFacing.AxisDirection.NEGATIVE;
         double d2 = blockpattern$patternhelper.func_177669_b().func_176740_k() == EnumFacing.Axis.X ? (double)blockpattern$patternhelper.func_181117_a().func_177952_p() : (double)blockpattern$patternhelper.func_181117_a().func_177958_n();
         double d6 = (double)(blockpattern$patternhelper.func_181117_a().func_177956_o() + 1) - p_180620_1_.func_181014_aG().field_72448_b * (double)blockpattern$patternhelper.func_181119_e();
         if (flag1) {
            ++d2;
         }

         if (blockpattern$patternhelper.func_177669_b().func_176740_k() == EnumFacing.Axis.X) {
            d7 = d2 + (1.0D - p_180620_1_.func_181014_aG().field_72450_a) * (double)blockpattern$patternhelper.func_181118_d() * (double)blockpattern$patternhelper.func_177669_b().func_176746_e().func_176743_c().func_179524_a();
         } else {
            d5 = d2 + (1.0D - p_180620_1_.func_181014_aG().field_72450_a) * (double)blockpattern$patternhelper.func_181118_d() * (double)blockpattern$patternhelper.func_177669_b().func_176746_e().func_176743_c().func_179524_a();
         }

         float f = 0.0F;
         float f1 = 0.0F;
         float f2 = 0.0F;
         float f3 = 0.0F;
         if (blockpattern$patternhelper.func_177669_b().func_176734_d() == p_180620_1_.func_181012_aH()) {
            f = 1.0F;
            f1 = 1.0F;
         } else if (blockpattern$patternhelper.func_177669_b().func_176734_d() == p_180620_1_.func_181012_aH().func_176734_d()) {
            f = -1.0F;
            f1 = -1.0F;
         } else if (blockpattern$patternhelper.func_177669_b().func_176734_d() == p_180620_1_.func_181012_aH().func_176746_e()) {
            f2 = 1.0F;
            f3 = -1.0F;
         } else {
            f2 = -1.0F;
            f3 = 1.0F;
         }

         double d3 = p_180620_1_.field_70159_w;
         double d4 = p_180620_1_.field_70179_y;
         p_180620_1_.field_70159_w = d3 * (double)f + d4 * (double)f3;
         p_180620_1_.field_70179_y = d3 * (double)f2 + d4 * (double)f1;
         p_180620_1_.field_70177_z = p_180620_2_ - (float)(p_180620_1_.func_181012_aH().func_176734_d().func_176736_b() * 90) + (float)(blockpattern$patternhelper.func_177669_b().func_176736_b() * 90);
         if (p_180620_1_ instanceof EntityPlayerMP) {
            ((EntityPlayerMP)p_180620_1_).field_71135_a.func_147364_a(d5, d6, d7, p_180620_1_.field_70177_z, p_180620_1_.field_70125_A);
         } else {
            p_180620_1_.func_70012_b(d5, d6, d7, p_180620_1_.field_70177_z, p_180620_1_.field_70125_A);
         }

         return true;
      } else {
         return false;
      }
   }

   public boolean func_85188_a(Entity p_85188_1_) {
      int i = 16;
      double d0 = -1.0D;
      int j = MathHelper.func_76128_c(p_85188_1_.field_70165_t);
      int k = MathHelper.func_76128_c(p_85188_1_.field_70163_u);
      int l = MathHelper.func_76128_c(p_85188_1_.field_70161_v);
      int i1 = j;
      int j1 = k;
      int k1 = l;
      int l1 = 0;
      int i2 = this.field_77187_a.nextInt(4);
      BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

      for(int j2 = j - 16; j2 <= j + 16; ++j2) {
         double d1 = (double)j2 + 0.5D - p_85188_1_.field_70165_t;

         for(int l2 = l - 16; l2 <= l + 16; ++l2) {
            double d2 = (double)l2 + 0.5D - p_85188_1_.field_70161_v;

            label293:
            for(int j3 = this.field_85192_a.func_72940_L() - 1; j3 >= 0; --j3) {
               if (this.field_85192_a.func_175623_d(blockpos$mutableblockpos.func_181079_c(j2, j3, l2))) {
                  while(j3 > 0 && this.field_85192_a.func_175623_d(blockpos$mutableblockpos.func_181079_c(j2, j3 - 1, l2))) {
                     --j3;
                  }

                  for(int k3 = i2; k3 < i2 + 4; ++k3) {
                     int l3 = k3 % 2;
                     int i4 = 1 - l3;
                     if (k3 % 4 >= 2) {
                        l3 = -l3;
                        i4 = -i4;
                     }

                     for(int j4 = 0; j4 < 3; ++j4) {
                        for(int k4 = 0; k4 < 4; ++k4) {
                           for(int l4 = -1; l4 < 4; ++l4) {
                              int i5 = j2 + (k4 - 1) * l3 + j4 * i4;
                              int j5 = j3 + l4;
                              int k5 = l2 + (k4 - 1) * i4 - j4 * l3;
                              blockpos$mutableblockpos.func_181079_c(i5, j5, k5);
                              if (l4 < 0 && !this.field_85192_a.func_180495_p(blockpos$mutableblockpos).func_185904_a().func_76220_a() || l4 >= 0 && !this.field_85192_a.func_175623_d(blockpos$mutableblockpos)) {
                                 continue label293;
                              }
                           }
                        }
                     }

                     double d5 = (double)j3 + 0.5D - p_85188_1_.field_70163_u;
                     double d7 = d1 * d1 + d5 * d5 + d2 * d2;
                     if (d0 < 0.0D || d7 < d0) {
                        d0 = d7;
                        i1 = j2;
                        j1 = j3;
                        k1 = l2;
                        l1 = k3 % 4;
                     }
                  }
               }
            }
         }
      }

      if (d0 < 0.0D) {
         for(int l5 = j - 16; l5 <= j + 16; ++l5) {
            double d3 = (double)l5 + 0.5D - p_85188_1_.field_70165_t;

            for(int j6 = l - 16; j6 <= l + 16; ++j6) {
               double d4 = (double)j6 + 0.5D - p_85188_1_.field_70161_v;

               label231:
               for(int i7 = this.field_85192_a.func_72940_L() - 1; i7 >= 0; --i7) {
                  if (this.field_85192_a.func_175623_d(blockpos$mutableblockpos.func_181079_c(l5, i7, j6))) {
                     while(i7 > 0 && this.field_85192_a.func_175623_d(blockpos$mutableblockpos.func_181079_c(l5, i7 - 1, j6))) {
                        --i7;
                     }

                     for(int k7 = i2; k7 < i2 + 2; ++k7) {
                        int j8 = k7 % 2;
                        int j9 = 1 - j8;

                        for(int j10 = 0; j10 < 4; ++j10) {
                           for(int j11 = -1; j11 < 4; ++j11) {
                              int j12 = l5 + (j10 - 1) * j8;
                              int i13 = i7 + j11;
                              int j13 = j6 + (j10 - 1) * j9;
                              blockpos$mutableblockpos.func_181079_c(j12, i13, j13);
                              if (j11 < 0 && !this.field_85192_a.func_180495_p(blockpos$mutableblockpos).func_185904_a().func_76220_a() || j11 >= 0 && !this.field_85192_a.func_175623_d(blockpos$mutableblockpos)) {
                                 continue label231;
                              }
                           }
                        }

                        double d6 = (double)i7 + 0.5D - p_85188_1_.field_70163_u;
                        double d8 = d3 * d3 + d6 * d6 + d4 * d4;
                        if (d0 < 0.0D || d8 < d0) {
                           d0 = d8;
                           i1 = l5;
                           j1 = i7;
                           k1 = j6;
                           l1 = k7 % 2;
                        }
                     }
                  }
               }
            }
         }
      }

      int i6 = i1;
      int k2 = j1;
      int k6 = k1;
      int l6 = l1 % 2;
      int i3 = 1 - l6;
      if (l1 % 4 >= 2) {
         l6 = -l6;
         i3 = -i3;
      }

      if (d0 < 0.0D) {
         j1 = MathHelper.func_76125_a(j1, 70, this.field_85192_a.func_72940_L() - 10);
         k2 = j1;

         for(int j7 = -1; j7 <= 1; ++j7) {
            for(int l7 = 1; l7 < 3; ++l7) {
               for(int k8 = -1; k8 < 3; ++k8) {
                  int k9 = i6 + (l7 - 1) * l6 + j7 * i3;
                  int k10 = k2 + k8;
                  int k11 = k6 + (l7 - 1) * i3 - j7 * l6;
                  boolean flag = k8 < 0;
                  this.field_85192_a.func_175656_a(new BlockPos(k9, k10, k11), flag ? Blocks.field_150343_Z.func_176223_P() : Blocks.field_150350_a.func_176223_P());
               }
            }
         }
      }

      IBlockState iblockstate = Blocks.field_150427_aO.func_176223_P().func_177226_a(BlockPortal.field_176550_a, l6 == 0 ? EnumFacing.Axis.Z : EnumFacing.Axis.X);

      for(int i8 = 0; i8 < 4; ++i8) {
         for(int l8 = 0; l8 < 4; ++l8) {
            for(int l9 = -1; l9 < 4; ++l9) {
               int l10 = i6 + (l8 - 1) * l6;
               int l11 = k2 + l9;
               int k12 = k6 + (l8 - 1) * i3;
               boolean flag1 = l8 == 0 || l8 == 3 || l9 == -1 || l9 == 3;
               this.field_85192_a.func_180501_a(new BlockPos(l10, l11, k12), flag1 ? Blocks.field_150343_Z.func_176223_P() : iblockstate, 2);
            }
         }

         for(int i9 = 0; i9 < 4; ++i9) {
            for(int i10 = -1; i10 < 4; ++i10) {
               int i11 = i6 + (i9 - 1) * l6;
               int i12 = k2 + i10;
               int l12 = k6 + (i9 - 1) * i3;
               BlockPos blockpos = new BlockPos(i11, i12, l12);
               this.field_85192_a.func_175685_c(blockpos, this.field_85192_a.func_180495_p(blockpos).func_177230_c(), false);
            }
         }
      }

      return true;
   }

   public void func_85189_a(long p_85189_1_) {
      if (p_85189_1_ % 100L == 0L) {
         long i = p_85189_1_ - 300L;
         ObjectIterator<Teleporter.PortalPosition> objectiterator = this.field_85191_c.values().iterator();

         while(objectiterator.hasNext()) {
            Teleporter.PortalPosition teleporter$portalposition = (Teleporter.PortalPosition)objectiterator.next();
            if (teleporter$portalposition == null || teleporter$portalposition.field_85087_d < i) {
               objectiterator.remove();
            }
         }
      }

   }

   public class PortalPosition extends BlockPos {
      public long field_85087_d;

      public PortalPosition(BlockPos p_i45747_2_, long p_i45747_3_) {
         super(p_i45747_2_.func_177958_n(), p_i45747_2_.func_177956_o(), p_i45747_2_.func_177952_p());
         this.field_85087_d = p_i45747_3_;
      }
   }
}
