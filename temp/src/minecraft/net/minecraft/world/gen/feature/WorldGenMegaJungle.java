package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.BlockVine;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class WorldGenMegaJungle extends WorldGenHugeTrees {
   public WorldGenMegaJungle(boolean p_i46448_1_, int p_i46448_2_, int p_i46448_3_, IBlockState p_i46448_4_, IBlockState p_i46448_5_) {
      super(p_i46448_1_, p_i46448_2_, p_i46448_3_, p_i46448_4_, p_i46448_5_);
   }

   public boolean func_180709_b(World p_180709_1_, Random p_180709_2_, BlockPos p_180709_3_) {
      int i = this.func_150533_a(p_180709_2_);
      if (!this.func_175929_a(p_180709_1_, p_180709_2_, p_180709_3_, i)) {
         return false;
      } else {
         this.func_175930_c(p_180709_1_, p_180709_3_.func_177981_b(i), 2);

         for(int j = p_180709_3_.func_177956_o() + i - 2 - p_180709_2_.nextInt(4); j > p_180709_3_.func_177956_o() + i / 2; j -= 2 + p_180709_2_.nextInt(4)) {
            float f = p_180709_2_.nextFloat() * 6.2831855F;
            int k = p_180709_3_.func_177958_n() + (int)(0.5F + MathHelper.func_76134_b(f) * 4.0F);
            int l = p_180709_3_.func_177952_p() + (int)(0.5F + MathHelper.func_76126_a(f) * 4.0F);

            for(int i1 = 0; i1 < 5; ++i1) {
               k = p_180709_3_.func_177958_n() + (int)(1.5F + MathHelper.func_76134_b(f) * (float)i1);
               l = p_180709_3_.func_177952_p() + (int)(1.5F + MathHelper.func_76126_a(f) * (float)i1);
               this.func_175903_a(p_180709_1_, new BlockPos(k, j - 3 + i1 / 2, l), this.field_76520_b);
            }

            int j2 = 1 + p_180709_2_.nextInt(2);
            int j1 = j;

            for(int k1 = j - j2; k1 <= j1; ++k1) {
               int l1 = k1 - j1;
               this.func_175928_b(p_180709_1_, new BlockPos(k, k1, l), 1 - l1);
            }
         }

         for(int i2 = 0; i2 < i; ++i2) {
            BlockPos blockpos = p_180709_3_.func_177981_b(i2);
            if (this.func_150523_a(p_180709_1_.func_180495_p(blockpos).func_177230_c())) {
               this.func_175903_a(p_180709_1_, blockpos, this.field_76520_b);
               if (i2 > 0) {
                  this.func_181632_a(p_180709_1_, p_180709_2_, blockpos.func_177976_e(), BlockVine.field_176278_M);
                  this.func_181632_a(p_180709_1_, p_180709_2_, blockpos.func_177978_c(), BlockVine.field_176279_N);
               }
            }

            if (i2 < i - 1) {
               BlockPos blockpos1 = blockpos.func_177974_f();
               if (this.func_150523_a(p_180709_1_.func_180495_p(blockpos1).func_177230_c())) {
                  this.func_175903_a(p_180709_1_, blockpos1, this.field_76520_b);
                  if (i2 > 0) {
                     this.func_181632_a(p_180709_1_, p_180709_2_, blockpos1.func_177974_f(), BlockVine.field_176280_O);
                     this.func_181632_a(p_180709_1_, p_180709_2_, blockpos1.func_177978_c(), BlockVine.field_176279_N);
                  }
               }

               BlockPos blockpos2 = blockpos.func_177968_d().func_177974_f();
               if (this.func_150523_a(p_180709_1_.func_180495_p(blockpos2).func_177230_c())) {
                  this.func_175903_a(p_180709_1_, blockpos2, this.field_76520_b);
                  if (i2 > 0) {
                     this.func_181632_a(p_180709_1_, p_180709_2_, blockpos2.func_177974_f(), BlockVine.field_176280_O);
                     this.func_181632_a(p_180709_1_, p_180709_2_, blockpos2.func_177968_d(), BlockVine.field_176273_b);
                  }
               }

               BlockPos blockpos3 = blockpos.func_177968_d();
               if (this.func_150523_a(p_180709_1_.func_180495_p(blockpos3).func_177230_c())) {
                  this.func_175903_a(p_180709_1_, blockpos3, this.field_76520_b);
                  if (i2 > 0) {
                     this.func_181632_a(p_180709_1_, p_180709_2_, blockpos3.func_177976_e(), BlockVine.field_176278_M);
                     this.func_181632_a(p_180709_1_, p_180709_2_, blockpos3.func_177968_d(), BlockVine.field_176273_b);
                  }
               }
            }
         }

         return true;
      }
   }

   private void func_181632_a(World p_181632_1_, Random p_181632_2_, BlockPos p_181632_3_, PropertyBool p_181632_4_) {
      if (p_181632_2_.nextInt(3) > 0 && p_181632_1_.func_175623_d(p_181632_3_)) {
         this.func_175903_a(p_181632_1_, p_181632_3_, Blocks.field_150395_bd.func_176223_P().func_177226_a(p_181632_4_, Boolean.valueOf(true)));
      }

   }

   private void func_175930_c(World p_175930_1_, BlockPos p_175930_2_, int p_175930_3_) {
      int i = 2;

      for(int j = -2; j <= 0; ++j) {
         this.func_175925_a(p_175930_1_, p_175930_2_.func_177981_b(j), p_175930_3_ + 1 - j);
      }

   }
}
