package net.minecraft.world.gen.feature;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockLog;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class WorldGenBigTree extends WorldGenAbstractTree {
   private Random field_175949_k;
   private World field_175946_l;
   private BlockPos field_175947_m = BlockPos.field_177992_a;
   int field_76504_e;
   int field_76501_f;
   double field_76502_g = 0.618D;
   double field_175944_d = 0.381D;
   double field_175945_e = 1.0D;
   double field_76513_k = 1.0D;
   int field_175943_g = 1;
   int field_175950_h = 12;
   int field_76508_n = 4;
   List<WorldGenBigTree.FoliageCoordinates> field_175948_j;

   public WorldGenBigTree(boolean p_i2008_1_) {
      super(p_i2008_1_);
   }

   void func_76489_a() {
      this.field_76501_f = (int)((double)this.field_76504_e * this.field_76502_g);
      if (this.field_76501_f >= this.field_76504_e) {
         this.field_76501_f = this.field_76504_e - 1;
      }

      int i = (int)(1.382D + Math.pow(this.field_76513_k * (double)this.field_76504_e / 13.0D, 2.0D));
      if (i < 1) {
         i = 1;
      }

      int j = this.field_175947_m.func_177956_o() + this.field_76501_f;
      int k = this.field_76504_e - this.field_76508_n;
      this.field_175948_j = Lists.<WorldGenBigTree.FoliageCoordinates>newArrayList();
      this.field_175948_j.add(new WorldGenBigTree.FoliageCoordinates(this.field_175947_m.func_177981_b(k), j));

      for(; k >= 0; --k) {
         float f = this.func_76490_a(k);
         if (f >= 0.0F) {
            for(int l = 0; l < i; ++l) {
               double d0 = this.field_175945_e * (double)f * ((double)this.field_175949_k.nextFloat() + 0.328D);
               double d1 = (double)(this.field_175949_k.nextFloat() * 2.0F) * 3.141592653589793D;
               double d2 = d0 * Math.sin(d1) + 0.5D;
               double d3 = d0 * Math.cos(d1) + 0.5D;
               BlockPos blockpos = this.field_175947_m.func_177963_a(d2, (double)(k - 1), d3);
               BlockPos blockpos1 = blockpos.func_177981_b(this.field_76508_n);
               if (this.func_175936_a(blockpos, blockpos1) == -1) {
                  int i1 = this.field_175947_m.func_177958_n() - blockpos.func_177958_n();
                  int j1 = this.field_175947_m.func_177952_p() - blockpos.func_177952_p();
                  double d4 = (double)blockpos.func_177956_o() - Math.sqrt((double)(i1 * i1 + j1 * j1)) * this.field_175944_d;
                  int k1 = d4 > (double)j ? j : (int)d4;
                  BlockPos blockpos2 = new BlockPos(this.field_175947_m.func_177958_n(), k1, this.field_175947_m.func_177952_p());
                  if (this.func_175936_a(blockpos2, blockpos) == -1) {
                     this.field_175948_j.add(new WorldGenBigTree.FoliageCoordinates(blockpos, blockpos2.func_177956_o()));
                  }
               }
            }
         }
      }

   }

   void func_181631_a(BlockPos p_181631_1_, float p_181631_2_, IBlockState p_181631_3_) {
      int i = (int)((double)p_181631_2_ + 0.618D);

      for(int j = -i; j <= i; ++j) {
         for(int k = -i; k <= i; ++k) {
            if (Math.pow((double)Math.abs(j) + 0.5D, 2.0D) + Math.pow((double)Math.abs(k) + 0.5D, 2.0D) <= (double)(p_181631_2_ * p_181631_2_)) {
               BlockPos blockpos = p_181631_1_.func_177982_a(j, 0, k);
               Material material = this.field_175946_l.func_180495_p(blockpos).func_185904_a();
               if (material == Material.field_151579_a || material == Material.field_151584_j) {
                  this.func_175903_a(this.field_175946_l, blockpos, p_181631_3_);
               }
            }
         }
      }

   }

   float func_76490_a(int p_76490_1_) {
      if ((float)p_76490_1_ < (float)this.field_76504_e * 0.3F) {
         return -1.0F;
      } else {
         float f = (float)this.field_76504_e / 2.0F;
         float f1 = f - (float)p_76490_1_;
         float f2 = MathHelper.func_76129_c(f * f - f1 * f1);
         if (f1 == 0.0F) {
            f2 = f;
         } else if (Math.abs(f1) >= f) {
            return 0.0F;
         }

         return f2 * 0.5F;
      }
   }

   float func_76495_b(int p_76495_1_) {
      if (p_76495_1_ >= 0 && p_76495_1_ < this.field_76508_n) {
         return p_76495_1_ != 0 && p_76495_1_ != this.field_76508_n - 1 ? 3.0F : 2.0F;
      } else {
         return -1.0F;
      }
   }

   void func_175940_a(BlockPos p_175940_1_) {
      for(int i = 0; i < this.field_76508_n; ++i) {
         this.func_181631_a(p_175940_1_.func_177981_b(i), this.func_76495_b(i), Blocks.field_150362_t.func_176223_P().func_177226_a(BlockLeaves.field_176236_b, Boolean.valueOf(false)));
      }

   }

   void func_175937_a(BlockPos p_175937_1_, BlockPos p_175937_2_, Block p_175937_3_) {
      BlockPos blockpos = p_175937_2_.func_177982_a(-p_175937_1_.func_177958_n(), -p_175937_1_.func_177956_o(), -p_175937_1_.func_177952_p());
      int i = this.func_175935_b(blockpos);
      float f = (float)blockpos.func_177958_n() / (float)i;
      float f1 = (float)blockpos.func_177956_o() / (float)i;
      float f2 = (float)blockpos.func_177952_p() / (float)i;

      for(int j = 0; j <= i; ++j) {
         BlockPos blockpos1 = p_175937_1_.func_177963_a((double)(0.5F + (float)j * f), (double)(0.5F + (float)j * f1), (double)(0.5F + (float)j * f2));
         BlockLog.EnumAxis blocklog$enumaxis = this.func_175938_b(p_175937_1_, blockpos1);
         this.func_175903_a(this.field_175946_l, blockpos1, p_175937_3_.func_176223_P().func_177226_a(BlockLog.field_176299_a, blocklog$enumaxis));
      }

   }

   private int func_175935_b(BlockPos p_175935_1_) {
      int i = MathHelper.func_76130_a(p_175935_1_.func_177958_n());
      int j = MathHelper.func_76130_a(p_175935_1_.func_177956_o());
      int k = MathHelper.func_76130_a(p_175935_1_.func_177952_p());
      if (k > i && k > j) {
         return k;
      } else {
         return j > i ? j : i;
      }
   }

   private BlockLog.EnumAxis func_175938_b(BlockPos p_175938_1_, BlockPos p_175938_2_) {
      BlockLog.EnumAxis blocklog$enumaxis = BlockLog.EnumAxis.Y;
      int i = Math.abs(p_175938_2_.func_177958_n() - p_175938_1_.func_177958_n());
      int j = Math.abs(p_175938_2_.func_177952_p() - p_175938_1_.func_177952_p());
      int k = Math.max(i, j);
      if (k > 0) {
         if (i == k) {
            blocklog$enumaxis = BlockLog.EnumAxis.X;
         } else if (j == k) {
            blocklog$enumaxis = BlockLog.EnumAxis.Z;
         }
      }

      return blocklog$enumaxis;
   }

   void func_175941_b() {
      for(WorldGenBigTree.FoliageCoordinates worldgenbigtree$foliagecoordinates : this.field_175948_j) {
         this.func_175940_a(worldgenbigtree$foliagecoordinates);
      }

   }

   boolean func_76493_c(int p_76493_1_) {
      return (double)p_76493_1_ >= (double)this.field_76504_e * 0.2D;
   }

   void func_175942_c() {
      BlockPos blockpos = this.field_175947_m;
      BlockPos blockpos1 = this.field_175947_m.func_177981_b(this.field_76501_f);
      Block block = Blocks.field_150364_r;
      this.func_175937_a(blockpos, blockpos1, block);
      if (this.field_175943_g == 2) {
         this.func_175937_a(blockpos.func_177974_f(), blockpos1.func_177974_f(), block);
         this.func_175937_a(blockpos.func_177974_f().func_177968_d(), blockpos1.func_177974_f().func_177968_d(), block);
         this.func_175937_a(blockpos.func_177968_d(), blockpos1.func_177968_d(), block);
      }

   }

   void func_175939_d() {
      for(WorldGenBigTree.FoliageCoordinates worldgenbigtree$foliagecoordinates : this.field_175948_j) {
         int i = worldgenbigtree$foliagecoordinates.func_177999_q();
         BlockPos blockpos = new BlockPos(this.field_175947_m.func_177958_n(), i, this.field_175947_m.func_177952_p());
         if (!blockpos.equals(worldgenbigtree$foliagecoordinates) && this.func_76493_c(i - this.field_175947_m.func_177956_o())) {
            this.func_175937_a(blockpos, worldgenbigtree$foliagecoordinates, Blocks.field_150364_r);
         }
      }

   }

   int func_175936_a(BlockPos p_175936_1_, BlockPos p_175936_2_) {
      BlockPos blockpos = p_175936_2_.func_177982_a(-p_175936_1_.func_177958_n(), -p_175936_1_.func_177956_o(), -p_175936_1_.func_177952_p());
      int i = this.func_175935_b(blockpos);
      float f = (float)blockpos.func_177958_n() / (float)i;
      float f1 = (float)blockpos.func_177956_o() / (float)i;
      float f2 = (float)blockpos.func_177952_p() / (float)i;
      if (i == 0) {
         return -1;
      } else {
         for(int j = 0; j <= i; ++j) {
            BlockPos blockpos1 = p_175936_1_.func_177963_a((double)(0.5F + (float)j * f), (double)(0.5F + (float)j * f1), (double)(0.5F + (float)j * f2));
            if (!this.func_150523_a(this.field_175946_l.func_180495_p(blockpos1).func_177230_c())) {
               return j;
            }
         }

         return -1;
      }
   }

   public void func_175904_e() {
      this.field_76508_n = 5;
   }

   public boolean func_180709_b(World p_180709_1_, Random p_180709_2_, BlockPos p_180709_3_) {
      this.field_175946_l = p_180709_1_;
      this.field_175947_m = p_180709_3_;
      this.field_175949_k = new Random(p_180709_2_.nextLong());
      if (this.field_76504_e == 0) {
         this.field_76504_e = 5 + this.field_175949_k.nextInt(this.field_175950_h);
      }

      if (!this.func_76497_e()) {
         return false;
      } else {
         this.func_76489_a();
         this.func_175941_b();
         this.func_175942_c();
         this.func_175939_d();
         return true;
      }
   }

   private boolean func_76497_e() {
      Block block = this.field_175946_l.func_180495_p(this.field_175947_m.func_177977_b()).func_177230_c();
      if (block != Blocks.field_150346_d && block != Blocks.field_150349_c && block != Blocks.field_150458_ak) {
         return false;
      } else {
         int i = this.func_175936_a(this.field_175947_m, this.field_175947_m.func_177981_b(this.field_76504_e - 1));
         if (i == -1) {
            return true;
         } else if (i < 6) {
            return false;
         } else {
            this.field_76504_e = i;
            return true;
         }
      }
   }

   static class FoliageCoordinates extends BlockPos {
      private final int field_178000_b;

      public FoliageCoordinates(BlockPos p_i45635_1_, int p_i45635_2_) {
         super(p_i45635_1_.func_177958_n(), p_i45635_1_.func_177956_o(), p_i45635_1_.func_177952_p());
         this.field_178000_b = p_i45635_2_;
      }

      public int func_177999_q() {
         return this.field_178000_b;
      }
   }
}
