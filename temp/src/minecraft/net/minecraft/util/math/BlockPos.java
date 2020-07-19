package net.minecraft.util.math;

import com.google.common.collect.AbstractIterator;
import com.google.common.collect.Lists;
import java.util.Iterator;
import java.util.List;
import javax.annotation.concurrent.Immutable;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Rotation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Immutable
public class BlockPos extends Vec3i {
   private static final Logger field_185335_c = LogManager.getLogger();
   public static final BlockPos field_177992_a = new BlockPos(0, 0, 0);
   private static final int field_177990_b = 1 + MathHelper.func_151239_c(MathHelper.func_151236_b(30000000));
   private static final int field_177991_c = field_177990_b;
   private static final int field_177989_d = 64 - field_177990_b - field_177991_c;
   private static final int field_177987_f = 0 + field_177991_c;
   private static final int field_177988_g = field_177987_f + field_177989_d;
   private static final long field_177994_h = (1L << field_177990_b) - 1L;
   private static final long field_177995_i = (1L << field_177989_d) - 1L;
   private static final long field_177993_j = (1L << field_177991_c) - 1L;

   public BlockPos(int p_i46030_1_, int p_i46030_2_, int p_i46030_3_) {
      super(p_i46030_1_, p_i46030_2_, p_i46030_3_);
   }

   public BlockPos(double p_i46031_1_, double p_i46031_3_, double p_i46031_5_) {
      super(p_i46031_1_, p_i46031_3_, p_i46031_5_);
   }

   public BlockPos(Entity p_i46032_1_) {
      this(p_i46032_1_.field_70165_t, p_i46032_1_.field_70163_u, p_i46032_1_.field_70161_v);
   }

   public BlockPos(Vec3d p_i47100_1_) {
      this(p_i47100_1_.field_72450_a, p_i47100_1_.field_72448_b, p_i47100_1_.field_72449_c);
   }

   public BlockPos(Vec3i p_i46034_1_) {
      this(p_i46034_1_.func_177958_n(), p_i46034_1_.func_177956_o(), p_i46034_1_.func_177952_p());
   }

   public BlockPos func_177963_a(double p_177963_1_, double p_177963_3_, double p_177963_5_) {
      return p_177963_1_ == 0.0D && p_177963_3_ == 0.0D && p_177963_5_ == 0.0D ? this : new BlockPos((double)this.func_177958_n() + p_177963_1_, (double)this.func_177956_o() + p_177963_3_, (double)this.func_177952_p() + p_177963_5_);
   }

   public BlockPos func_177982_a(int p_177982_1_, int p_177982_2_, int p_177982_3_) {
      return p_177982_1_ == 0 && p_177982_2_ == 0 && p_177982_3_ == 0 ? this : new BlockPos(this.func_177958_n() + p_177982_1_, this.func_177956_o() + p_177982_2_, this.func_177952_p() + p_177982_3_);
   }

   public BlockPos func_177971_a(Vec3i p_177971_1_) {
      return this.func_177982_a(p_177971_1_.func_177958_n(), p_177971_1_.func_177956_o(), p_177971_1_.func_177952_p());
   }

   public BlockPos func_177973_b(Vec3i p_177973_1_) {
      return this.func_177982_a(-p_177973_1_.func_177958_n(), -p_177973_1_.func_177956_o(), -p_177973_1_.func_177952_p());
   }

   public BlockPos func_177984_a() {
      return this.func_177981_b(1);
   }

   public BlockPos func_177981_b(int p_177981_1_) {
      return this.func_177967_a(EnumFacing.UP, p_177981_1_);
   }

   public BlockPos func_177977_b() {
      return this.func_177979_c(1);
   }

   public BlockPos func_177979_c(int p_177979_1_) {
      return this.func_177967_a(EnumFacing.DOWN, p_177979_1_);
   }

   public BlockPos func_177978_c() {
      return this.func_177964_d(1);
   }

   public BlockPos func_177964_d(int p_177964_1_) {
      return this.func_177967_a(EnumFacing.NORTH, p_177964_1_);
   }

   public BlockPos func_177968_d() {
      return this.func_177970_e(1);
   }

   public BlockPos func_177970_e(int p_177970_1_) {
      return this.func_177967_a(EnumFacing.SOUTH, p_177970_1_);
   }

   public BlockPos func_177976_e() {
      return this.func_177985_f(1);
   }

   public BlockPos func_177985_f(int p_177985_1_) {
      return this.func_177967_a(EnumFacing.WEST, p_177985_1_);
   }

   public BlockPos func_177974_f() {
      return this.func_177965_g(1);
   }

   public BlockPos func_177965_g(int p_177965_1_) {
      return this.func_177967_a(EnumFacing.EAST, p_177965_1_);
   }

   public BlockPos func_177972_a(EnumFacing p_177972_1_) {
      return this.func_177967_a(p_177972_1_, 1);
   }

   public BlockPos func_177967_a(EnumFacing p_177967_1_, int p_177967_2_) {
      return p_177967_2_ == 0 ? this : new BlockPos(this.func_177958_n() + p_177967_1_.func_82601_c() * p_177967_2_, this.func_177956_o() + p_177967_1_.func_96559_d() * p_177967_2_, this.func_177952_p() + p_177967_1_.func_82599_e() * p_177967_2_);
   }

   public BlockPos func_190942_a(Rotation p_190942_1_) {
      switch(p_190942_1_) {
      case NONE:
      default:
         return this;
      case CLOCKWISE_90:
         return new BlockPos(-this.func_177952_p(), this.func_177956_o(), this.func_177958_n());
      case CLOCKWISE_180:
         return new BlockPos(-this.func_177958_n(), this.func_177956_o(), -this.func_177952_p());
      case COUNTERCLOCKWISE_90:
         return new BlockPos(this.func_177952_p(), this.func_177956_o(), -this.func_177958_n());
      }
   }

   public BlockPos func_177955_d(Vec3i p_177955_1_) {
      return new BlockPos(this.func_177956_o() * p_177955_1_.func_177952_p() - this.func_177952_p() * p_177955_1_.func_177956_o(), this.func_177952_p() * p_177955_1_.func_177958_n() - this.func_177958_n() * p_177955_1_.func_177952_p(), this.func_177958_n() * p_177955_1_.func_177956_o() - this.func_177956_o() * p_177955_1_.func_177958_n());
   }

   public long func_177986_g() {
      return ((long)this.func_177958_n() & field_177994_h) << field_177988_g | ((long)this.func_177956_o() & field_177995_i) << field_177987_f | ((long)this.func_177952_p() & field_177993_j) << 0;
   }

   public static BlockPos func_177969_a(long p_177969_0_) {
      int i = (int)(p_177969_0_ << 64 - field_177988_g - field_177990_b >> 64 - field_177990_b);
      int j = (int)(p_177969_0_ << 64 - field_177987_f - field_177989_d >> 64 - field_177989_d);
      int k = (int)(p_177969_0_ << 64 - field_177991_c >> 64 - field_177991_c);
      return new BlockPos(i, j, k);
   }

   public static Iterable<BlockPos> func_177980_a(BlockPos p_177980_0_, BlockPos p_177980_1_) {
      return func_191532_a(Math.min(p_177980_0_.func_177958_n(), p_177980_1_.func_177958_n()), Math.min(p_177980_0_.func_177956_o(), p_177980_1_.func_177956_o()), Math.min(p_177980_0_.func_177952_p(), p_177980_1_.func_177952_p()), Math.max(p_177980_0_.func_177958_n(), p_177980_1_.func_177958_n()), Math.max(p_177980_0_.func_177956_o(), p_177980_1_.func_177956_o()), Math.max(p_177980_0_.func_177952_p(), p_177980_1_.func_177952_p()));
   }

   public static Iterable<BlockPos> func_191532_a(final int p_191532_0_, final int p_191532_1_, final int p_191532_2_, final int p_191532_3_, final int p_191532_4_, final int p_191532_5_) {
      return new Iterable<BlockPos>() {
         public Iterator<BlockPos> iterator() {
            return new AbstractIterator<BlockPos>() {
               private boolean field_191534_b = true;
               private int field_191535_c;
               private int field_191536_d;
               private int field_191537_e;

               protected BlockPos computeNext() {
                  if (this.field_191534_b) {
                     this.field_191534_b = false;
                     this.field_191535_c = p_191532_0_;
                     this.field_191536_d = p_191532_1_;
                     this.field_191537_e = p_191532_2_;
                     return new BlockPos(p_191532_0_, p_191532_1_, p_191532_2_);
                  } else if (this.field_191535_c == p_191532_3_ && this.field_191536_d == p_191532_4_ && this.field_191537_e == p_191532_5_) {
                     return (BlockPos)this.endOfData();
                  } else {
                     if (this.field_191535_c < p_191532_3_) {
                        ++this.field_191535_c;
                     } else if (this.field_191536_d < p_191532_4_) {
                        this.field_191535_c = p_191532_0_;
                        ++this.field_191536_d;
                     } else if (this.field_191537_e < p_191532_5_) {
                        this.field_191535_c = p_191532_0_;
                        this.field_191536_d = p_191532_1_;
                        ++this.field_191537_e;
                     }

                     return new BlockPos(this.field_191535_c, this.field_191536_d, this.field_191537_e);
                  }
               }
            };
         }
      };
   }

   public BlockPos func_185334_h() {
      return this;
   }

   public static Iterable<BlockPos.MutableBlockPos> func_177975_b(BlockPos p_177975_0_, BlockPos p_177975_1_) {
      return func_191531_b(Math.min(p_177975_0_.func_177958_n(), p_177975_1_.func_177958_n()), Math.min(p_177975_0_.func_177956_o(), p_177975_1_.func_177956_o()), Math.min(p_177975_0_.func_177952_p(), p_177975_1_.func_177952_p()), Math.max(p_177975_0_.func_177958_n(), p_177975_1_.func_177958_n()), Math.max(p_177975_0_.func_177956_o(), p_177975_1_.func_177956_o()), Math.max(p_177975_0_.func_177952_p(), p_177975_1_.func_177952_p()));
   }

   public static Iterable<BlockPos.MutableBlockPos> func_191531_b(final int p_191531_0_, final int p_191531_1_, final int p_191531_2_, final int p_191531_3_, final int p_191531_4_, final int p_191531_5_) {
      return new Iterable<BlockPos.MutableBlockPos>() {
         public Iterator<BlockPos.MutableBlockPos> iterator() {
            return new AbstractIterator<BlockPos.MutableBlockPos>() {
               private BlockPos.MutableBlockPos field_179314_b;

               protected BlockPos.MutableBlockPos computeNext() {
                  if (this.field_179314_b == null) {
                     this.field_179314_b = new BlockPos.MutableBlockPos(p_191531_0_, p_191531_1_, p_191531_2_);
                     return this.field_179314_b;
                  } else if (this.field_179314_b.field_177997_b == p_191531_3_ && this.field_179314_b.field_177998_c == p_191531_4_ && this.field_179314_b.field_177996_d == p_191531_5_) {
                     return (BlockPos.MutableBlockPos)this.endOfData();
                  } else {
                     if (this.field_179314_b.field_177997_b < p_191531_3_) {
                        ++this.field_179314_b.field_177997_b;
                     } else if (this.field_179314_b.field_177998_c < p_191531_4_) {
                        this.field_179314_b.field_177997_b = p_191531_0_;
                        ++this.field_179314_b.field_177998_c;
                     } else if (this.field_179314_b.field_177996_d < p_191531_5_) {
                        this.field_179314_b.field_177997_b = p_191531_0_;
                        this.field_179314_b.field_177998_c = p_191531_1_;
                        ++this.field_179314_b.field_177996_d;
                     }

                     return this.field_179314_b;
                  }
               }
            };
         }
      };
   }

   public static class MutableBlockPos extends BlockPos {
      protected int field_177997_b;
      protected int field_177998_c;
      protected int field_177996_d;

      public MutableBlockPos() {
         this(0, 0, 0);
      }

      public MutableBlockPos(BlockPos p_i46587_1_) {
         this(p_i46587_1_.func_177958_n(), p_i46587_1_.func_177956_o(), p_i46587_1_.func_177952_p());
      }

      public MutableBlockPos(int p_i46024_1_, int p_i46024_2_, int p_i46024_3_) {
         super(0, 0, 0);
         this.field_177997_b = p_i46024_1_;
         this.field_177998_c = p_i46024_2_;
         this.field_177996_d = p_i46024_3_;
      }

      public BlockPos func_177963_a(double p_177963_1_, double p_177963_3_, double p_177963_5_) {
         return super.func_177963_a(p_177963_1_, p_177963_3_, p_177963_5_).func_185334_h();
      }

      public BlockPos func_177982_a(int p_177982_1_, int p_177982_2_, int p_177982_3_) {
         return super.func_177982_a(p_177982_1_, p_177982_2_, p_177982_3_).func_185334_h();
      }

      public BlockPos func_177967_a(EnumFacing p_177967_1_, int p_177967_2_) {
         return super.func_177967_a(p_177967_1_, p_177967_2_).func_185334_h();
      }

      public BlockPos func_190942_a(Rotation p_190942_1_) {
         return super.func_190942_a(p_190942_1_).func_185334_h();
      }

      public int func_177958_n() {
         return this.field_177997_b;
      }

      public int func_177956_o() {
         return this.field_177998_c;
      }

      public int func_177952_p() {
         return this.field_177996_d;
      }

      public BlockPos.MutableBlockPos func_181079_c(int p_181079_1_, int p_181079_2_, int p_181079_3_) {
         this.field_177997_b = p_181079_1_;
         this.field_177998_c = p_181079_2_;
         this.field_177996_d = p_181079_3_;
         return this;
      }

      public BlockPos.MutableBlockPos func_189535_a(Entity p_189535_1_) {
         return this.func_189532_c(p_189535_1_.field_70165_t, p_189535_1_.field_70163_u, p_189535_1_.field_70161_v);
      }

      public BlockPos.MutableBlockPos func_189532_c(double p_189532_1_, double p_189532_3_, double p_189532_5_) {
         return this.func_181079_c(MathHelper.func_76128_c(p_189532_1_), MathHelper.func_76128_c(p_189532_3_), MathHelper.func_76128_c(p_189532_5_));
      }

      public BlockPos.MutableBlockPos func_189533_g(Vec3i p_189533_1_) {
         return this.func_181079_c(p_189533_1_.func_177958_n(), p_189533_1_.func_177956_o(), p_189533_1_.func_177952_p());
      }

      public BlockPos.MutableBlockPos func_189536_c(EnumFacing p_189536_1_) {
         return this.func_189534_c(p_189536_1_, 1);
      }

      public BlockPos.MutableBlockPos func_189534_c(EnumFacing p_189534_1_, int p_189534_2_) {
         return this.func_181079_c(this.field_177997_b + p_189534_1_.func_82601_c() * p_189534_2_, this.field_177998_c + p_189534_1_.func_96559_d() * p_189534_2_, this.field_177996_d + p_189534_1_.func_82599_e() * p_189534_2_);
      }

      public void func_185336_p(int p_185336_1_) {
         this.field_177998_c = p_185336_1_;
      }

      public BlockPos func_185334_h() {
         return new BlockPos(this);
      }
   }

   public static final class PooledMutableBlockPos extends BlockPos.MutableBlockPos {
      private boolean field_185350_f;
      private static final List<BlockPos.PooledMutableBlockPos> field_185351_g = Lists.<BlockPos.PooledMutableBlockPos>newArrayList();

      private PooledMutableBlockPos(int p_i46586_1_, int p_i46586_2_, int p_i46586_3_) {
         super(p_i46586_1_, p_i46586_2_, p_i46586_3_);
      }

      public static BlockPos.PooledMutableBlockPos func_185346_s() {
         return func_185339_c(0, 0, 0);
      }

      public static BlockPos.PooledMutableBlockPos func_185345_c(double p_185345_0_, double p_185345_2_, double p_185345_4_) {
         return func_185339_c(MathHelper.func_76128_c(p_185345_0_), MathHelper.func_76128_c(p_185345_2_), MathHelper.func_76128_c(p_185345_4_));
      }

      public static BlockPos.PooledMutableBlockPos func_185342_g(Vec3i p_185342_0_) {
         return func_185339_c(p_185342_0_.func_177958_n(), p_185342_0_.func_177956_o(), p_185342_0_.func_177952_p());
      }

      public static BlockPos.PooledMutableBlockPos func_185339_c(int p_185339_0_, int p_185339_1_, int p_185339_2_) {
         synchronized(field_185351_g) {
            if (!field_185351_g.isEmpty()) {
               BlockPos.PooledMutableBlockPos blockpos$pooledmutableblockpos = field_185351_g.remove(field_185351_g.size() - 1);
               if (blockpos$pooledmutableblockpos != null && blockpos$pooledmutableblockpos.field_185350_f) {
                  blockpos$pooledmutableblockpos.field_185350_f = false;
                  blockpos$pooledmutableblockpos.func_181079_c(p_185339_0_, p_185339_1_, p_185339_2_);
                  return blockpos$pooledmutableblockpos;
               }
            }
         }

         return new BlockPos.PooledMutableBlockPos(p_185339_0_, p_185339_1_, p_185339_2_);
      }

      public void func_185344_t() {
         synchronized(field_185351_g) {
            if (field_185351_g.size() < 100) {
               field_185351_g.add(this);
            }

            this.field_185350_f = true;
         }
      }

      public BlockPos.PooledMutableBlockPos func_181079_c(int p_181079_1_, int p_181079_2_, int p_181079_3_) {
         if (this.field_185350_f) {
            BlockPos.field_185335_c.error("PooledMutableBlockPosition modified after it was released.", new Throwable());
            this.field_185350_f = false;
         }

         return (BlockPos.PooledMutableBlockPos)super.func_181079_c(p_181079_1_, p_181079_2_, p_181079_3_);
      }

      public BlockPos.PooledMutableBlockPos func_189535_a(Entity p_189535_1_) {
         return (BlockPos.PooledMutableBlockPos)super.func_189535_a(p_189535_1_);
      }

      public BlockPos.PooledMutableBlockPos func_189532_c(double p_189532_1_, double p_189532_3_, double p_189532_5_) {
         return (BlockPos.PooledMutableBlockPos)super.func_189532_c(p_189532_1_, p_189532_3_, p_189532_5_);
      }

      public BlockPos.PooledMutableBlockPos func_189533_g(Vec3i p_189533_1_) {
         return (BlockPos.PooledMutableBlockPos)super.func_189533_g(p_189533_1_);
      }

      public BlockPos.PooledMutableBlockPos func_189536_c(EnumFacing p_189536_1_) {
         return (BlockPos.PooledMutableBlockPos)super.func_189536_c(p_189536_1_);
      }

      public BlockPos.PooledMutableBlockPos func_189534_c(EnumFacing p_189534_1_, int p_189534_2_) {
         return (BlockPos.PooledMutableBlockPos)super.func_189534_c(p_189534_1_, p_189534_2_);
      }
   }
}
