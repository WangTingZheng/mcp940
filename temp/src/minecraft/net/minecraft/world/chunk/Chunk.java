package net.minecraft.world.chunk;

import com.google.common.base.Predicate;
import com.google.common.collect.Maps;
import com.google.common.collect.Queues;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.crash.ICrashReportDetail;
import net.minecraft.entity.Entity;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ClassInheritanceMultiMap;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ReportedException;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;
import net.minecraft.world.gen.ChunkGeneratorDebug;
import net.minecraft.world.gen.IChunkGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Chunk {
   private static final Logger field_150817_t = LogManager.getLogger();
   public static final ExtendedBlockStorage field_186036_a = null;
   private final ExtendedBlockStorage[] field_76652_q;
   private final byte[] field_76651_r;
   private final int[] field_76638_b;
   private final boolean[] field_76639_c;
   private boolean field_76636_d;
   private final World field_76637_e;
   private final int[] field_76634_f;
   public final int field_76635_g;
   public final int field_76647_h;
   private boolean field_76650_s;
   private final Map<BlockPos, TileEntity> field_150816_i;
   private final ClassInheritanceMultiMap<Entity>[] field_76645_j;
   private boolean field_76646_k;
   private boolean field_150814_l;
   private boolean field_150815_m;
   private boolean field_76643_l;
   private boolean field_76644_m;
   private long field_76641_n;
   private int field_82912_p;
   private long field_111204_q;
   private int field_76649_t;
   private final ConcurrentLinkedQueue<BlockPos> field_177447_w;
   public boolean field_189550_d;

   public Chunk(World p_i1995_1_, int p_i1995_2_, int p_i1995_3_) {
      this.field_76652_q = new ExtendedBlockStorage[16];
      this.field_76651_r = new byte[256];
      this.field_76638_b = new int[256];
      this.field_76639_c = new boolean[256];
      this.field_150816_i = Maps.<BlockPos, TileEntity>newHashMap();
      this.field_76649_t = 4096;
      this.field_177447_w = Queues.<BlockPos>newConcurrentLinkedQueue();
      this.field_76645_j = (ClassInheritanceMultiMap[])(new ClassInheritanceMultiMap[16]);
      this.field_76637_e = p_i1995_1_;
      this.field_76635_g = p_i1995_2_;
      this.field_76647_h = p_i1995_3_;
      this.field_76634_f = new int[256];

      for(int i = 0; i < this.field_76645_j.length; ++i) {
         this.field_76645_j[i] = new ClassInheritanceMultiMap(Entity.class);
      }

      Arrays.fill(this.field_76638_b, -999);
      Arrays.fill(this.field_76651_r, (byte)-1);
   }

   public Chunk(World p_i45645_1_, ChunkPrimer p_i45645_2_, int p_i45645_3_, int p_i45645_4_) {
      this(p_i45645_1_, p_i45645_3_, p_i45645_4_);
      int i = 256;
      boolean flag = p_i45645_1_.field_73011_w.func_191066_m();

      for(int j = 0; j < 16; ++j) {
         for(int k = 0; k < 16; ++k) {
            for(int l = 0; l < 256; ++l) {
               IBlockState iblockstate = p_i45645_2_.func_177856_a(j, l, k);
               if (iblockstate.func_185904_a() != Material.field_151579_a) {
                  int i1 = l >> 4;
                  if (this.field_76652_q[i1] == field_186036_a) {
                     this.field_76652_q[i1] = new ExtendedBlockStorage(i1 << 4, flag);
                  }

                  this.field_76652_q[i1].func_177484_a(j, l & 15, k, iblockstate);
               }
            }
         }
      }

   }

   public boolean func_76600_a(int p_76600_1_, int p_76600_2_) {
      return p_76600_1_ == this.field_76635_g && p_76600_2_ == this.field_76647_h;
   }

   public int func_177433_f(BlockPos p_177433_1_) {
      return this.func_76611_b(p_177433_1_.func_177958_n() & 15, p_177433_1_.func_177952_p() & 15);
   }

   public int func_76611_b(int p_76611_1_, int p_76611_2_) {
      return this.field_76634_f[p_76611_2_ << 4 | p_76611_1_];
   }

   @Nullable
   private ExtendedBlockStorage func_186031_y() {
      for(int i = this.field_76652_q.length - 1; i >= 0; --i) {
         if (this.field_76652_q[i] != field_186036_a) {
            return this.field_76652_q[i];
         }
      }

      return null;
   }

   public int func_76625_h() {
      ExtendedBlockStorage extendedblockstorage = this.func_186031_y();
      return extendedblockstorage == null ? 0 : extendedblockstorage.func_76662_d();
   }

   public ExtendedBlockStorage[] func_76587_i() {
      return this.field_76652_q;
   }

   protected void func_76590_a() {
      int i = this.func_76625_h();
      this.field_82912_p = Integer.MAX_VALUE;

      for(int j = 0; j < 16; ++j) {
         for(int k = 0; k < 16; ++k) {
            this.field_76638_b[j + (k << 4)] = -999;

            for(int l = i + 16; l > 0; --l) {
               IBlockState iblockstate = this.func_186032_a(j, l - 1, k);
               if (iblockstate.func_185891_c() != 0) {
                  this.field_76634_f[k << 4 | j] = l;
                  if (l < this.field_82912_p) {
                     this.field_82912_p = l;
                  }
                  break;
               }
            }
         }
      }

      this.field_76643_l = true;
   }

   public void func_76603_b() {
      int i = this.func_76625_h();
      this.field_82912_p = Integer.MAX_VALUE;

      for(int j = 0; j < 16; ++j) {
         for(int k = 0; k < 16; ++k) {
            this.field_76638_b[j + (k << 4)] = -999;

            for(int l = i + 16; l > 0; --l) {
               if (this.func_150808_b(j, l - 1, k) != 0) {
                  this.field_76634_f[k << 4 | j] = l;
                  if (l < this.field_82912_p) {
                     this.field_82912_p = l;
                  }
                  break;
               }
            }

            if (this.field_76637_e.field_73011_w.func_191066_m()) {
               int k1 = 15;
               int i1 = i + 16 - 1;

               while(true) {
                  int j1 = this.func_150808_b(j, i1, k);
                  if (j1 == 0 && k1 != 15) {
                     j1 = 1;
                  }

                  k1 -= j1;
                  if (k1 > 0) {
                     ExtendedBlockStorage extendedblockstorage = this.field_76652_q[i1 >> 4];
                     if (extendedblockstorage != field_186036_a) {
                        extendedblockstorage.func_76657_c(j, i1 & 15, k, k1);
                        this.field_76637_e.func_175679_n(new BlockPos((this.field_76635_g << 4) + j, i1, (this.field_76647_h << 4) + k));
                     }
                  }

                  --i1;
                  if (i1 <= 0 || k1 <= 0) {
                     break;
                  }
               }
            }
         }
      }

      this.field_76643_l = true;
   }

   private void func_76595_e(int p_76595_1_, int p_76595_2_) {
      this.field_76639_c[p_76595_1_ + p_76595_2_ * 16] = true;
      this.field_76650_s = true;
   }

   private void func_150803_c(boolean p_150803_1_) {
      this.field_76637_e.field_72984_F.func_76320_a("recheckGaps");
      if (this.field_76637_e.func_175697_a(new BlockPos(this.field_76635_g * 16 + 8, 0, this.field_76647_h * 16 + 8), 16)) {
         for(int i = 0; i < 16; ++i) {
            for(int j = 0; j < 16; ++j) {
               if (this.field_76639_c[i + j * 16]) {
                  this.field_76639_c[i + j * 16] = false;
                  int k = this.func_76611_b(i, j);
                  int l = this.field_76635_g * 16 + i;
                  int i1 = this.field_76647_h * 16 + j;
                  int j1 = Integer.MAX_VALUE;

                  for(EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
                     j1 = Math.min(j1, this.field_76637_e.func_82734_g(l + enumfacing.func_82601_c(), i1 + enumfacing.func_82599_e()));
                  }

                  this.func_76599_g(l, i1, j1);

                  for(EnumFacing enumfacing1 : EnumFacing.Plane.HORIZONTAL) {
                     this.func_76599_g(l + enumfacing1.func_82601_c(), i1 + enumfacing1.func_82599_e(), k);
                  }

                  if (p_150803_1_) {
                     this.field_76637_e.field_72984_F.func_76319_b();
                     return;
                  }
               }
            }
         }

         this.field_76650_s = false;
      }

      this.field_76637_e.field_72984_F.func_76319_b();
   }

   private void func_76599_g(int p_76599_1_, int p_76599_2_, int p_76599_3_) {
      int i = this.field_76637_e.func_175645_m(new BlockPos(p_76599_1_, 0, p_76599_2_)).func_177956_o();
      if (i > p_76599_3_) {
         this.func_76609_d(p_76599_1_, p_76599_2_, p_76599_3_, i + 1);
      } else if (i < p_76599_3_) {
         this.func_76609_d(p_76599_1_, p_76599_2_, i, p_76599_3_ + 1);
      }

   }

   private void func_76609_d(int p_76609_1_, int p_76609_2_, int p_76609_3_, int p_76609_4_) {
      if (p_76609_4_ > p_76609_3_ && this.field_76637_e.func_175697_a(new BlockPos(p_76609_1_, 0, p_76609_2_), 16)) {
         for(int i = p_76609_3_; i < p_76609_4_; ++i) {
            this.field_76637_e.func_180500_c(EnumSkyBlock.SKY, new BlockPos(p_76609_1_, i, p_76609_2_));
         }

         this.field_76643_l = true;
      }

   }

   private void func_76615_h(int p_76615_1_, int p_76615_2_, int p_76615_3_) {
      int i = this.field_76634_f[p_76615_3_ << 4 | p_76615_1_] & 255;
      int j = i;
      if (p_76615_2_ > i) {
         j = p_76615_2_;
      }

      while(j > 0 && this.func_150808_b(p_76615_1_, j - 1, p_76615_3_) == 0) {
         --j;
      }

      if (j != i) {
         this.field_76637_e.func_72975_g(p_76615_1_ + this.field_76635_g * 16, p_76615_3_ + this.field_76647_h * 16, j, i);
         this.field_76634_f[p_76615_3_ << 4 | p_76615_1_] = j;
         int k = this.field_76635_g * 16 + p_76615_1_;
         int l = this.field_76647_h * 16 + p_76615_3_;
         if (this.field_76637_e.field_73011_w.func_191066_m()) {
            if (j < i) {
               for(int j1 = j; j1 < i; ++j1) {
                  ExtendedBlockStorage extendedblockstorage2 = this.field_76652_q[j1 >> 4];
                  if (extendedblockstorage2 != field_186036_a) {
                     extendedblockstorage2.func_76657_c(p_76615_1_, j1 & 15, p_76615_3_, 15);
                     this.field_76637_e.func_175679_n(new BlockPos((this.field_76635_g << 4) + p_76615_1_, j1, (this.field_76647_h << 4) + p_76615_3_));
                  }
               }
            } else {
               for(int i1 = i; i1 < j; ++i1) {
                  ExtendedBlockStorage extendedblockstorage = this.field_76652_q[i1 >> 4];
                  if (extendedblockstorage != field_186036_a) {
                     extendedblockstorage.func_76657_c(p_76615_1_, i1 & 15, p_76615_3_, 0);
                     this.field_76637_e.func_175679_n(new BlockPos((this.field_76635_g << 4) + p_76615_1_, i1, (this.field_76647_h << 4) + p_76615_3_));
                  }
               }
            }

            int k1 = 15;

            while(j > 0 && k1 > 0) {
               --j;
               int i2 = this.func_150808_b(p_76615_1_, j, p_76615_3_);
               if (i2 == 0) {
                  i2 = 1;
               }

               k1 -= i2;
               if (k1 < 0) {
                  k1 = 0;
               }

               ExtendedBlockStorage extendedblockstorage1 = this.field_76652_q[j >> 4];
               if (extendedblockstorage1 != field_186036_a) {
                  extendedblockstorage1.func_76657_c(p_76615_1_, j & 15, p_76615_3_, k1);
               }
            }
         }

         int l1 = this.field_76634_f[p_76615_3_ << 4 | p_76615_1_];
         int j2 = i;
         int k2 = l1;
         if (l1 < i) {
            j2 = l1;
            k2 = i;
         }

         if (l1 < this.field_82912_p) {
            this.field_82912_p = l1;
         }

         if (this.field_76637_e.field_73011_w.func_191066_m()) {
            for(EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
               this.func_76609_d(k + enumfacing.func_82601_c(), l + enumfacing.func_82599_e(), j2, k2);
            }

            this.func_76609_d(k, l, j2, k2);
         }

         this.field_76643_l = true;
      }
   }

   public int func_177437_b(BlockPos p_177437_1_) {
      return this.func_177435_g(p_177437_1_).func_185891_c();
   }

   private int func_150808_b(int p_150808_1_, int p_150808_2_, int p_150808_3_) {
      return this.func_186032_a(p_150808_1_, p_150808_2_, p_150808_3_).func_185891_c();
   }

   public IBlockState func_177435_g(BlockPos p_177435_1_) {
      return this.func_186032_a(p_177435_1_.func_177958_n(), p_177435_1_.func_177956_o(), p_177435_1_.func_177952_p());
   }

   public IBlockState func_186032_a(final int p_186032_1_, final int p_186032_2_, final int p_186032_3_) {
      if (this.field_76637_e.func_175624_G() == WorldType.field_180272_g) {
         IBlockState iblockstate = null;
         if (p_186032_2_ == 60) {
            iblockstate = Blocks.field_180401_cv.func_176223_P();
         }

         if (p_186032_2_ == 70) {
            iblockstate = ChunkGeneratorDebug.func_177461_b(p_186032_1_, p_186032_3_);
         }

         return iblockstate == null ? Blocks.field_150350_a.func_176223_P() : iblockstate;
      } else {
         try {
            if (p_186032_2_ >= 0 && p_186032_2_ >> 4 < this.field_76652_q.length) {
               ExtendedBlockStorage extendedblockstorage = this.field_76652_q[p_186032_2_ >> 4];
               if (extendedblockstorage != field_186036_a) {
                  return extendedblockstorage.func_177485_a(p_186032_1_ & 15, p_186032_2_ & 15, p_186032_3_ & 15);
               }
            }

            return Blocks.field_150350_a.func_176223_P();
         } catch (Throwable throwable) {
            CrashReport crashreport = CrashReport.func_85055_a(throwable, "Getting block state");
            CrashReportCategory crashreportcategory = crashreport.func_85058_a("Block being got");
            crashreportcategory.func_189529_a("Location", new ICrashReportDetail<String>() {
               public String call() throws Exception {
                  return CrashReportCategory.func_184876_a(p_186032_1_, p_186032_2_, p_186032_3_);
               }
            });
            throw new ReportedException(crashreport);
         }
      }
   }

   @Nullable
   public IBlockState func_177436_a(BlockPos p_177436_1_, IBlockState p_177436_2_) {
      int i = p_177436_1_.func_177958_n() & 15;
      int j = p_177436_1_.func_177956_o();
      int k = p_177436_1_.func_177952_p() & 15;
      int l = k << 4 | i;
      if (j >= this.field_76638_b[l] - 1) {
         this.field_76638_b[l] = -999;
      }

      int i1 = this.field_76634_f[l];
      IBlockState iblockstate = this.func_177435_g(p_177436_1_);
      if (iblockstate == p_177436_2_) {
         return null;
      } else {
         Block block = p_177436_2_.func_177230_c();
         Block block1 = iblockstate.func_177230_c();
         ExtendedBlockStorage extendedblockstorage = this.field_76652_q[j >> 4];
         boolean flag = false;
         if (extendedblockstorage == field_186036_a) {
            if (block == Blocks.field_150350_a) {
               return null;
            }

            extendedblockstorage = new ExtendedBlockStorage(j >> 4 << 4, this.field_76637_e.field_73011_w.func_191066_m());
            this.field_76652_q[j >> 4] = extendedblockstorage;
            flag = j >= i1;
         }

         extendedblockstorage.func_177484_a(i, j & 15, k, p_177436_2_);
         if (block1 != block) {
            if (!this.field_76637_e.field_72995_K) {
               block1.func_180663_b(this.field_76637_e, p_177436_1_, iblockstate);
            } else if (block1 instanceof ITileEntityProvider) {
               this.field_76637_e.func_175713_t(p_177436_1_);
            }
         }

         if (extendedblockstorage.func_177485_a(i, j & 15, k).func_177230_c() != block) {
            return null;
         } else {
            if (flag) {
               this.func_76603_b();
            } else {
               int j1 = p_177436_2_.func_185891_c();
               int k1 = iblockstate.func_185891_c();
               if (j1 > 0) {
                  if (j >= i1) {
                     this.func_76615_h(i, j + 1, k);
                  }
               } else if (j == i1 - 1) {
                  this.func_76615_h(i, j, k);
               }

               if (j1 != k1 && (j1 < k1 || this.func_177413_a(EnumSkyBlock.SKY, p_177436_1_) > 0 || this.func_177413_a(EnumSkyBlock.BLOCK, p_177436_1_) > 0)) {
                  this.func_76595_e(i, k);
               }
            }

            if (block1 instanceof ITileEntityProvider) {
               TileEntity tileentity = this.func_177424_a(p_177436_1_, Chunk.EnumCreateEntityType.CHECK);
               if (tileentity != null) {
                  tileentity.func_145836_u();
               }
            }

            if (!this.field_76637_e.field_72995_K && block1 != block) {
               block.func_176213_c(this.field_76637_e, p_177436_1_, p_177436_2_);
            }

            if (block instanceof ITileEntityProvider) {
               TileEntity tileentity1 = this.func_177424_a(p_177436_1_, Chunk.EnumCreateEntityType.CHECK);
               if (tileentity1 == null) {
                  tileentity1 = ((ITileEntityProvider)block).func_149915_a(this.field_76637_e, block.func_176201_c(p_177436_2_));
                  this.field_76637_e.func_175690_a(p_177436_1_, tileentity1);
               }

               if (tileentity1 != null) {
                  tileentity1.func_145836_u();
               }
            }

            this.field_76643_l = true;
            return iblockstate;
         }
      }
   }

   public int func_177413_a(EnumSkyBlock p_177413_1_, BlockPos p_177413_2_) {
      int i = p_177413_2_.func_177958_n() & 15;
      int j = p_177413_2_.func_177956_o();
      int k = p_177413_2_.func_177952_p() & 15;
      ExtendedBlockStorage extendedblockstorage = this.field_76652_q[j >> 4];
      if (extendedblockstorage == field_186036_a) {
         return this.func_177444_d(p_177413_2_) ? p_177413_1_.field_77198_c : 0;
      } else if (p_177413_1_ == EnumSkyBlock.SKY) {
         return !this.field_76637_e.field_73011_w.func_191066_m() ? 0 : extendedblockstorage.func_76670_c(i, j & 15, k);
      } else {
         return p_177413_1_ == EnumSkyBlock.BLOCK ? extendedblockstorage.func_76674_d(i, j & 15, k) : p_177413_1_.field_77198_c;
      }
   }

   public void func_177431_a(EnumSkyBlock p_177431_1_, BlockPos p_177431_2_, int p_177431_3_) {
      int i = p_177431_2_.func_177958_n() & 15;
      int j = p_177431_2_.func_177956_o();
      int k = p_177431_2_.func_177952_p() & 15;
      ExtendedBlockStorage extendedblockstorage = this.field_76652_q[j >> 4];
      if (extendedblockstorage == field_186036_a) {
         extendedblockstorage = new ExtendedBlockStorage(j >> 4 << 4, this.field_76637_e.field_73011_w.func_191066_m());
         this.field_76652_q[j >> 4] = extendedblockstorage;
         this.func_76603_b();
      }

      this.field_76643_l = true;
      if (p_177431_1_ == EnumSkyBlock.SKY) {
         if (this.field_76637_e.field_73011_w.func_191066_m()) {
            extendedblockstorage.func_76657_c(i, j & 15, k, p_177431_3_);
         }
      } else if (p_177431_1_ == EnumSkyBlock.BLOCK) {
         extendedblockstorage.func_76677_d(i, j & 15, k, p_177431_3_);
      }

   }

   public int func_177443_a(BlockPos p_177443_1_, int p_177443_2_) {
      int i = p_177443_1_.func_177958_n() & 15;
      int j = p_177443_1_.func_177956_o();
      int k = p_177443_1_.func_177952_p() & 15;
      ExtendedBlockStorage extendedblockstorage = this.field_76652_q[j >> 4];
      if (extendedblockstorage == field_186036_a) {
         return this.field_76637_e.field_73011_w.func_191066_m() && p_177443_2_ < EnumSkyBlock.SKY.field_77198_c ? EnumSkyBlock.SKY.field_77198_c - p_177443_2_ : 0;
      } else {
         int l = !this.field_76637_e.field_73011_w.func_191066_m() ? 0 : extendedblockstorage.func_76670_c(i, j & 15, k);
         l = l - p_177443_2_;
         int i1 = extendedblockstorage.func_76674_d(i, j & 15, k);
         if (i1 > l) {
            l = i1;
         }

         return l;
      }
   }

   public void func_76612_a(Entity p_76612_1_) {
      this.field_76644_m = true;
      int i = MathHelper.func_76128_c(p_76612_1_.field_70165_t / 16.0D);
      int j = MathHelper.func_76128_c(p_76612_1_.field_70161_v / 16.0D);
      if (i != this.field_76635_g || j != this.field_76647_h) {
         field_150817_t.warn("Wrong location! ({}, {}) should be ({}, {}), {}", Integer.valueOf(i), Integer.valueOf(j), Integer.valueOf(this.field_76635_g), Integer.valueOf(this.field_76647_h), p_76612_1_);
         p_76612_1_.func_70106_y();
      }

      int k = MathHelper.func_76128_c(p_76612_1_.field_70163_u / 16.0D);
      if (k < 0) {
         k = 0;
      }

      if (k >= this.field_76645_j.length) {
         k = this.field_76645_j.length - 1;
      }

      p_76612_1_.field_70175_ag = true;
      p_76612_1_.field_70176_ah = this.field_76635_g;
      p_76612_1_.field_70162_ai = k;
      p_76612_1_.field_70164_aj = this.field_76647_h;
      this.field_76645_j[k].add(p_76612_1_);
   }

   public void func_76622_b(Entity p_76622_1_) {
      this.func_76608_a(p_76622_1_, p_76622_1_.field_70162_ai);
   }

   public void func_76608_a(Entity p_76608_1_, int p_76608_2_) {
      if (p_76608_2_ < 0) {
         p_76608_2_ = 0;
      }

      if (p_76608_2_ >= this.field_76645_j.length) {
         p_76608_2_ = this.field_76645_j.length - 1;
      }

      this.field_76645_j[p_76608_2_].remove(p_76608_1_);
   }

   public boolean func_177444_d(BlockPos p_177444_1_) {
      int i = p_177444_1_.func_177958_n() & 15;
      int j = p_177444_1_.func_177956_o();
      int k = p_177444_1_.func_177952_p() & 15;
      return j >= this.field_76634_f[k << 4 | i];
   }

   @Nullable
   private TileEntity func_177422_i(BlockPos p_177422_1_) {
      IBlockState iblockstate = this.func_177435_g(p_177422_1_);
      Block block = iblockstate.func_177230_c();
      return !block.func_149716_u() ? null : ((ITileEntityProvider)block).func_149915_a(this.field_76637_e, iblockstate.func_177230_c().func_176201_c(iblockstate));
   }

   @Nullable
   public TileEntity func_177424_a(BlockPos p_177424_1_, Chunk.EnumCreateEntityType p_177424_2_) {
      TileEntity tileentity = this.field_150816_i.get(p_177424_1_);
      if (tileentity == null) {
         if (p_177424_2_ == Chunk.EnumCreateEntityType.IMMEDIATE) {
            tileentity = this.func_177422_i(p_177424_1_);
            this.field_76637_e.func_175690_a(p_177424_1_, tileentity);
         } else if (p_177424_2_ == Chunk.EnumCreateEntityType.QUEUED) {
            this.field_177447_w.add(p_177424_1_);
         }
      } else if (tileentity.func_145837_r()) {
         this.field_150816_i.remove(p_177424_1_);
         return null;
      }

      return tileentity;
   }

   public void func_150813_a(TileEntity p_150813_1_) {
      this.func_177426_a(p_150813_1_.func_174877_v(), p_150813_1_);
      if (this.field_76636_d) {
         this.field_76637_e.func_175700_a(p_150813_1_);
      }

   }

   public void func_177426_a(BlockPos p_177426_1_, TileEntity p_177426_2_) {
      p_177426_2_.func_145834_a(this.field_76637_e);
      p_177426_2_.func_174878_a(p_177426_1_);
      if (this.func_177435_g(p_177426_1_).func_177230_c() instanceof ITileEntityProvider) {
         if (this.field_150816_i.containsKey(p_177426_1_)) {
            ((TileEntity)this.field_150816_i.get(p_177426_1_)).func_145843_s();
         }

         p_177426_2_.func_145829_t();
         this.field_150816_i.put(p_177426_1_, p_177426_2_);
      }
   }

   public void func_177425_e(BlockPos p_177425_1_) {
      if (this.field_76636_d) {
         TileEntity tileentity = this.field_150816_i.remove(p_177425_1_);
         if (tileentity != null) {
            tileentity.func_145843_s();
         }
      }

   }

   public void func_76631_c() {
      this.field_76636_d = true;
      this.field_76637_e.func_147448_a(this.field_150816_i.values());

      for(ClassInheritanceMultiMap<Entity> classinheritancemultimap : this.field_76645_j) {
         this.field_76637_e.func_175650_b(classinheritancemultimap);
      }

   }

   public void func_76623_d() {
      this.field_76636_d = false;

      for(TileEntity tileentity : this.field_150816_i.values()) {
         this.field_76637_e.func_147457_a(tileentity);
      }

      for(ClassInheritanceMultiMap<Entity> classinheritancemultimap : this.field_76645_j) {
         this.field_76637_e.func_175681_c(classinheritancemultimap);
      }

   }

   public void func_76630_e() {
      this.field_76643_l = true;
   }

   public void func_177414_a(@Nullable Entity p_177414_1_, AxisAlignedBB p_177414_2_, List<Entity> p_177414_3_, Predicate<? super Entity> p_177414_4_) {
      int i = MathHelper.func_76128_c((p_177414_2_.field_72338_b - 2.0D) / 16.0D);
      int j = MathHelper.func_76128_c((p_177414_2_.field_72337_e + 2.0D) / 16.0D);
      i = MathHelper.func_76125_a(i, 0, this.field_76645_j.length - 1);
      j = MathHelper.func_76125_a(j, 0, this.field_76645_j.length - 1);

      for(int k = i; k <= j; ++k) {
         if (!this.field_76645_j[k].isEmpty()) {
            for(Entity entity : this.field_76645_j[k]) {
               if (entity.func_174813_aQ().func_72326_a(p_177414_2_) && entity != p_177414_1_) {
                  if (p_177414_4_ == null || p_177414_4_.apply(entity)) {
                     p_177414_3_.add(entity);
                  }

                  Entity[] aentity = entity.func_70021_al();
                  if (aentity != null) {
                     for(Entity entity1 : aentity) {
                        if (entity1 != p_177414_1_ && entity1.func_174813_aQ().func_72326_a(p_177414_2_) && (p_177414_4_ == null || p_177414_4_.apply(entity1))) {
                           p_177414_3_.add(entity1);
                        }
                     }
                  }
               }
            }
         }
      }

   }

   public <T extends Entity> void func_177430_a(Class<? extends T> p_177430_1_, AxisAlignedBB p_177430_2_, List<T> p_177430_3_, Predicate<? super T> p_177430_4_) {
      int i = MathHelper.func_76128_c((p_177430_2_.field_72338_b - 2.0D) / 16.0D);
      int j = MathHelper.func_76128_c((p_177430_2_.field_72337_e + 2.0D) / 16.0D);
      i = MathHelper.func_76125_a(i, 0, this.field_76645_j.length - 1);
      j = MathHelper.func_76125_a(j, 0, this.field_76645_j.length - 1);

      for(int k = i; k <= j; ++k) {
         for(T t : this.field_76645_j[k].func_180215_b(p_177430_1_)) {
            if (t.func_174813_aQ().func_72326_a(p_177430_2_) && (p_177430_4_ == null || p_177430_4_.apply(t))) {
               p_177430_3_.add(t);
            }
         }
      }

   }

   public boolean func_76601_a(boolean p_76601_1_) {
      if (p_76601_1_) {
         if (this.field_76644_m && this.field_76637_e.func_82737_E() != this.field_76641_n || this.field_76643_l) {
            return true;
         }
      } else if (this.field_76644_m && this.field_76637_e.func_82737_E() >= this.field_76641_n + 600L) {
         return true;
      }

      return this.field_76643_l;
   }

   public Random func_76617_a(long p_76617_1_) {
      return new Random(this.field_76637_e.func_72905_C() + (long)(this.field_76635_g * this.field_76635_g * 4987142) + (long)(this.field_76635_g * 5947611) + (long)(this.field_76647_h * this.field_76647_h) * 4392871L + (long)(this.field_76647_h * 389711) ^ p_76617_1_);
   }

   public boolean func_76621_g() {
      return false;
   }

   public void func_186030_a(IChunkProvider p_186030_1_, IChunkGenerator p_186030_2_) {
      Chunk chunk = p_186030_1_.func_186026_b(this.field_76635_g, this.field_76647_h - 1);
      Chunk chunk1 = p_186030_1_.func_186026_b(this.field_76635_g + 1, this.field_76647_h);
      Chunk chunk2 = p_186030_1_.func_186026_b(this.field_76635_g, this.field_76647_h + 1);
      Chunk chunk3 = p_186030_1_.func_186026_b(this.field_76635_g - 1, this.field_76647_h);
      if (chunk1 != null && chunk2 != null && p_186030_1_.func_186026_b(this.field_76635_g + 1, this.field_76647_h + 1) != null) {
         this.func_186034_a(p_186030_2_);
      }

      if (chunk3 != null && chunk2 != null && p_186030_1_.func_186026_b(this.field_76635_g - 1, this.field_76647_h + 1) != null) {
         chunk3.func_186034_a(p_186030_2_);
      }

      if (chunk != null && chunk1 != null && p_186030_1_.func_186026_b(this.field_76635_g + 1, this.field_76647_h - 1) != null) {
         chunk.func_186034_a(p_186030_2_);
      }

      if (chunk != null && chunk3 != null) {
         Chunk chunk4 = p_186030_1_.func_186026_b(this.field_76635_g - 1, this.field_76647_h - 1);
         if (chunk4 != null) {
            chunk4.func_186034_a(p_186030_2_);
         }
      }

   }

   protected void func_186034_a(IChunkGenerator p_186034_1_) {
      if (this.func_177419_t()) {
         if (p_186034_1_.func_185933_a(this, this.field_76635_g, this.field_76647_h)) {
            this.func_76630_e();
         }
      } else {
         this.func_150809_p();
         p_186034_1_.func_185931_b(this.field_76635_g, this.field_76647_h);
         this.func_76630_e();
      }

   }

   public BlockPos func_177440_h(BlockPos p_177440_1_) {
      int i = p_177440_1_.func_177958_n() & 15;
      int j = p_177440_1_.func_177952_p() & 15;
      int k = i | j << 4;
      BlockPos blockpos = new BlockPos(p_177440_1_.func_177958_n(), this.field_76638_b[k], p_177440_1_.func_177952_p());
      if (blockpos.func_177956_o() == -999) {
         int l = this.func_76625_h() + 15;
         blockpos = new BlockPos(p_177440_1_.func_177958_n(), l, p_177440_1_.func_177952_p());
         int i1 = -1;

         while(blockpos.func_177956_o() > 0 && i1 == -1) {
            IBlockState iblockstate = this.func_177435_g(blockpos);
            Material material = iblockstate.func_185904_a();
            if (!material.func_76230_c() && !material.func_76224_d()) {
               blockpos = blockpos.func_177977_b();
            } else {
               i1 = blockpos.func_177956_o() + 1;
            }
         }

         this.field_76638_b[k] = i1;
      }

      return new BlockPos(p_177440_1_.func_177958_n(), this.field_76638_b[k], p_177440_1_.func_177952_p());
   }

   public void func_150804_b(boolean p_150804_1_) {
      if (this.field_76650_s && this.field_76637_e.field_73011_w.func_191066_m() && !p_150804_1_) {
         this.func_150803_c(this.field_76637_e.field_72995_K);
      }

      this.field_150815_m = true;
      if (!this.field_150814_l && this.field_76646_k) {
         this.func_150809_p();
      }

      while(!this.field_177447_w.isEmpty()) {
         BlockPos blockpos = this.field_177447_w.poll();
         if (this.func_177424_a(blockpos, Chunk.EnumCreateEntityType.CHECK) == null && this.func_177435_g(blockpos).func_177230_c().func_149716_u()) {
            TileEntity tileentity = this.func_177422_i(blockpos);
            this.field_76637_e.func_175690_a(blockpos, tileentity);
            this.field_76637_e.func_175704_b(blockpos, blockpos);
         }
      }

   }

   public boolean func_150802_k() {
      return this.field_150815_m && this.field_76646_k && this.field_150814_l;
   }

   public boolean func_186035_j() {
      return this.field_150815_m;
   }

   public ChunkPos func_76632_l() {
      return new ChunkPos(this.field_76635_g, this.field_76647_h);
   }

   public boolean func_76606_c(int p_76606_1_, int p_76606_2_) {
      if (p_76606_1_ < 0) {
         p_76606_1_ = 0;
      }

      if (p_76606_2_ >= 256) {
         p_76606_2_ = 255;
      }

      for(int i = p_76606_1_; i <= p_76606_2_; i += 16) {
         ExtendedBlockStorage extendedblockstorage = this.field_76652_q[i >> 4];
         if (extendedblockstorage != field_186036_a && !extendedblockstorage.func_76663_a()) {
            return false;
         }
      }

      return true;
   }

   public void func_76602_a(ExtendedBlockStorage[] p_76602_1_) {
      if (this.field_76652_q.length != p_76602_1_.length) {
         field_150817_t.warn("Could not set level chunk sections, array length is {} instead of {}", Integer.valueOf(p_76602_1_.length), Integer.valueOf(this.field_76652_q.length));
      } else {
         System.arraycopy(p_76602_1_, 0, this.field_76652_q, 0, this.field_76652_q.length);
      }
   }

   public void func_186033_a(PacketBuffer p_186033_1_, int p_186033_2_, boolean p_186033_3_) {
      boolean flag = this.field_76637_e.field_73011_w.func_191066_m();

      for(int i = 0; i < this.field_76652_q.length; ++i) {
         ExtendedBlockStorage extendedblockstorage = this.field_76652_q[i];
         if ((p_186033_2_ & 1 << i) == 0) {
            if (p_186033_3_ && extendedblockstorage != field_186036_a) {
               this.field_76652_q[i] = field_186036_a;
            }
         } else {
            if (extendedblockstorage == field_186036_a) {
               extendedblockstorage = new ExtendedBlockStorage(i << 4, flag);
               this.field_76652_q[i] = extendedblockstorage;
            }

            extendedblockstorage.func_186049_g().func_186010_a(p_186033_1_);
            p_186033_1_.readBytes(extendedblockstorage.func_76661_k().func_177481_a());
            if (flag) {
               p_186033_1_.readBytes(extendedblockstorage.func_76671_l().func_177481_a());
            }
         }
      }

      if (p_186033_3_) {
         p_186033_1_.readBytes(this.field_76651_r);
      }

      for(int j = 0; j < this.field_76652_q.length; ++j) {
         if (this.field_76652_q[j] != field_186036_a && (p_186033_2_ & 1 << j) != 0) {
            this.field_76652_q[j].func_76672_e();
         }
      }

      this.field_150814_l = true;
      this.field_76646_k = true;
      this.func_76590_a();

      for(TileEntity tileentity : this.field_150816_i.values()) {
         tileentity.func_145836_u();
      }

   }

   public Biome func_177411_a(BlockPos p_177411_1_, BiomeProvider p_177411_2_) {
      int i = p_177411_1_.func_177958_n() & 15;
      int j = p_177411_1_.func_177952_p() & 15;
      int k = this.field_76651_r[j << 4 | i] & 255;
      if (k == 255) {
         Biome biome = p_177411_2_.func_180300_a(p_177411_1_, Biomes.field_76772_c);
         k = Biome.func_185362_a(biome);
         this.field_76651_r[j << 4 | i] = (byte)(k & 255);
      }

      Biome biome1 = Biome.func_150568_d(k);
      return biome1 == null ? Biomes.field_76772_c : biome1;
   }

   public byte[] func_76605_m() {
      return this.field_76651_r;
   }

   public void func_76616_a(byte[] p_76616_1_) {
      if (this.field_76651_r.length != p_76616_1_.length) {
         field_150817_t.warn("Could not set level chunk biomes, array length is {} instead of {}", Integer.valueOf(p_76616_1_.length), Integer.valueOf(this.field_76651_r.length));
      } else {
         System.arraycopy(p_76616_1_, 0, this.field_76651_r, 0, this.field_76651_r.length);
      }
   }

   public void func_76613_n() {
      this.field_76649_t = 0;
   }

   public void func_76594_o() {
      if (this.field_76649_t < 4096) {
         BlockPos blockpos = new BlockPos(this.field_76635_g << 4, 0, this.field_76647_h << 4);

         for(int i = 0; i < 8; ++i) {
            if (this.field_76649_t >= 4096) {
               return;
            }

            int j = this.field_76649_t % 16;
            int k = this.field_76649_t / 16 % 16;
            int l = this.field_76649_t / 256;
            ++this.field_76649_t;

            for(int i1 = 0; i1 < 16; ++i1) {
               BlockPos blockpos1 = blockpos.func_177982_a(k, (j << 4) + i1, l);
               boolean flag = i1 == 0 || i1 == 15 || k == 0 || k == 15 || l == 0 || l == 15;
               if (this.field_76652_q[j] == field_186036_a && flag || this.field_76652_q[j] != field_186036_a && this.field_76652_q[j].func_177485_a(k, i1, l).func_185904_a() == Material.field_151579_a) {
                  for(EnumFacing enumfacing : EnumFacing.values()) {
                     BlockPos blockpos2 = blockpos1.func_177972_a(enumfacing);
                     if (this.field_76637_e.func_180495_p(blockpos2).func_185906_d() > 0) {
                        this.field_76637_e.func_175664_x(blockpos2);
                     }
                  }

                  this.field_76637_e.func_175664_x(blockpos1);
               }
            }
         }

      }
   }

   public void func_150809_p() {
      this.field_76646_k = true;
      this.field_150814_l = true;
      BlockPos blockpos = new BlockPos(this.field_76635_g << 4, 0, this.field_76647_h << 4);
      if (this.field_76637_e.field_73011_w.func_191066_m()) {
         if (this.field_76637_e.func_175707_a(blockpos.func_177982_a(-1, 0, -1), blockpos.func_177982_a(16, this.field_76637_e.func_181545_F(), 16))) {
            label44:
            for(int i = 0; i < 16; ++i) {
               for(int j = 0; j < 16; ++j) {
                  if (!this.func_150811_f(i, j)) {
                     this.field_150814_l = false;
                     break label44;
                  }
               }
            }

            if (this.field_150814_l) {
               for(EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
                  int k = enumfacing.func_176743_c() == EnumFacing.AxisDirection.POSITIVE ? 16 : 1;
                  this.field_76637_e.func_175726_f(blockpos.func_177967_a(enumfacing, k)).func_180700_a(enumfacing.func_176734_d());
               }

               this.func_177441_y();
            }
         } else {
            this.field_150814_l = false;
         }
      }

   }

   private void func_177441_y() {
      for(int i = 0; i < this.field_76639_c.length; ++i) {
         this.field_76639_c[i] = true;
      }

      this.func_150803_c(false);
   }

   private void func_180700_a(EnumFacing p_180700_1_) {
      if (this.field_76646_k) {
         if (p_180700_1_ == EnumFacing.EAST) {
            for(int i = 0; i < 16; ++i) {
               this.func_150811_f(15, i);
            }
         } else if (p_180700_1_ == EnumFacing.WEST) {
            for(int j = 0; j < 16; ++j) {
               this.func_150811_f(0, j);
            }
         } else if (p_180700_1_ == EnumFacing.SOUTH) {
            for(int k = 0; k < 16; ++k) {
               this.func_150811_f(k, 15);
            }
         } else if (p_180700_1_ == EnumFacing.NORTH) {
            for(int l = 0; l < 16; ++l) {
               this.func_150811_f(l, 0);
            }
         }

      }
   }

   private boolean func_150811_f(int p_150811_1_, int p_150811_2_) {
      int i = this.func_76625_h();
      boolean flag = false;
      boolean flag1 = false;
      BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos((this.field_76635_g << 4) + p_150811_1_, 0, (this.field_76647_h << 4) + p_150811_2_);

      for(int j = i + 16 - 1; j > this.field_76637_e.func_181545_F() || j > 0 && !flag1; --j) {
         blockpos$mutableblockpos.func_181079_c(blockpos$mutableblockpos.func_177958_n(), j, blockpos$mutableblockpos.func_177952_p());
         int k = this.func_177437_b(blockpos$mutableblockpos);
         if (k == 255 && blockpos$mutableblockpos.func_177956_o() < this.field_76637_e.func_181545_F()) {
            flag1 = true;
         }

         if (!flag && k > 0) {
            flag = true;
         } else if (flag && k == 0 && !this.field_76637_e.func_175664_x(blockpos$mutableblockpos)) {
            return false;
         }
      }

      for(int l = blockpos$mutableblockpos.func_177956_o(); l > 0; --l) {
         blockpos$mutableblockpos.func_181079_c(blockpos$mutableblockpos.func_177958_n(), l, blockpos$mutableblockpos.func_177952_p());
         if (this.func_177435_g(blockpos$mutableblockpos).func_185906_d() > 0) {
            this.field_76637_e.func_175664_x(blockpos$mutableblockpos);
         }
      }

      return true;
   }

   public boolean func_177410_o() {
      return this.field_76636_d;
   }

   public void func_177417_c(boolean p_177417_1_) {
      this.field_76636_d = p_177417_1_;
   }

   public World func_177412_p() {
      return this.field_76637_e;
   }

   public int[] func_177445_q() {
      return this.field_76634_f;
   }

   public void func_177420_a(int[] p_177420_1_) {
      if (this.field_76634_f.length != p_177420_1_.length) {
         field_150817_t.warn("Could not set level chunk heightmap, array length is {} instead of {}", Integer.valueOf(p_177420_1_.length), Integer.valueOf(this.field_76634_f.length));
      } else {
         System.arraycopy(p_177420_1_, 0, this.field_76634_f, 0, this.field_76634_f.length);
      }
   }

   public Map<BlockPos, TileEntity> func_177434_r() {
      return this.field_150816_i;
   }

   public ClassInheritanceMultiMap<Entity>[] func_177429_s() {
      return this.field_76645_j;
   }

   public boolean func_177419_t() {
      return this.field_76646_k;
   }

   public void func_177446_d(boolean p_177446_1_) {
      this.field_76646_k = p_177446_1_;
   }

   public boolean func_177423_u() {
      return this.field_150814_l;
   }

   public void func_177421_e(boolean p_177421_1_) {
      this.field_150814_l = p_177421_1_;
   }

   public void func_177427_f(boolean p_177427_1_) {
      this.field_76643_l = p_177427_1_;
   }

   public void func_177409_g(boolean p_177409_1_) {
      this.field_76644_m = p_177409_1_;
   }

   public void func_177432_b(long p_177432_1_) {
      this.field_76641_n = p_177432_1_;
   }

   public int func_177442_v() {
      return this.field_82912_p;
   }

   public long func_177416_w() {
      return this.field_111204_q;
   }

   public void func_177415_c(long p_177415_1_) {
      this.field_111204_q = p_177415_1_;
   }

   public static enum EnumCreateEntityType {
      IMMEDIATE,
      QUEUED,
      CHECK;
   }
}
