package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class WorldGenHugeTrees extends WorldGenAbstractTree {
   protected final int field_76522_a;
   protected final IBlockState field_76520_b;
   protected final IBlockState field_76521_c;
   protected int field_150538_d;

   public WorldGenHugeTrees(boolean p_i46447_1_, int p_i46447_2_, int p_i46447_3_, IBlockState p_i46447_4_, IBlockState p_i46447_5_) {
      super(p_i46447_1_);
      this.field_76522_a = p_i46447_2_;
      this.field_150538_d = p_i46447_3_;
      this.field_76520_b = p_i46447_4_;
      this.field_76521_c = p_i46447_5_;
   }

   protected int func_150533_a(Random p_150533_1_) {
      int i = p_150533_1_.nextInt(3) + this.field_76522_a;
      if (this.field_150538_d > 1) {
         i += p_150533_1_.nextInt(this.field_150538_d);
      }

      return i;
   }

   private boolean func_175926_c(World p_175926_1_, BlockPos p_175926_2_, int p_175926_3_) {
      boolean flag = true;
      if (p_175926_2_.func_177956_o() >= 1 && p_175926_2_.func_177956_o() + p_175926_3_ + 1 <= 256) {
         for(int i = 0; i <= 1 + p_175926_3_; ++i) {
            int j = 2;
            if (i == 0) {
               j = 1;
            } else if (i >= 1 + p_175926_3_ - 2) {
               j = 2;
            }

            for(int k = -j; k <= j && flag; ++k) {
               for(int l = -j; l <= j && flag; ++l) {
                  if (p_175926_2_.func_177956_o() + i < 0 || p_175926_2_.func_177956_o() + i >= 256 || !this.func_150523_a(p_175926_1_.func_180495_p(p_175926_2_.func_177982_a(k, i, l)).func_177230_c())) {
                     flag = false;
                  }
               }
            }
         }

         return flag;
      } else {
         return false;
      }
   }

   private boolean func_175927_a(BlockPos p_175927_1_, World p_175927_2_) {
      BlockPos blockpos = p_175927_1_.func_177977_b();
      Block block = p_175927_2_.func_180495_p(blockpos).func_177230_c();
      if ((block == Blocks.field_150349_c || block == Blocks.field_150346_d) && p_175927_1_.func_177956_o() >= 2) {
         this.func_175921_a(p_175927_2_, blockpos);
         this.func_175921_a(p_175927_2_, blockpos.func_177974_f());
         this.func_175921_a(p_175927_2_, blockpos.func_177968_d());
         this.func_175921_a(p_175927_2_, blockpos.func_177968_d().func_177974_f());
         return true;
      } else {
         return false;
      }
   }

   protected boolean func_175929_a(World p_175929_1_, Random p_175929_2_, BlockPos p_175929_3_, int p_175929_4_) {
      return this.func_175926_c(p_175929_1_, p_175929_3_, p_175929_4_) && this.func_175927_a(p_175929_3_, p_175929_1_);
   }

   protected void func_175925_a(World p_175925_1_, BlockPos p_175925_2_, int p_175925_3_) {
      int i = p_175925_3_ * p_175925_3_;

      for(int j = -p_175925_3_; j <= p_175925_3_ + 1; ++j) {
         for(int k = -p_175925_3_; k <= p_175925_3_ + 1; ++k) {
            int l = j - 1;
            int i1 = k - 1;
            if (j * j + k * k <= i || l * l + i1 * i1 <= i || j * j + i1 * i1 <= i || l * l + k * k <= i) {
               BlockPos blockpos = p_175925_2_.func_177982_a(j, 0, k);
               Material material = p_175925_1_.func_180495_p(blockpos).func_185904_a();
               if (material == Material.field_151579_a || material == Material.field_151584_j) {
                  this.func_175903_a(p_175925_1_, blockpos, this.field_76521_c);
               }
            }
         }
      }

   }

   protected void func_175928_b(World p_175928_1_, BlockPos p_175928_2_, int p_175928_3_) {
      int i = p_175928_3_ * p_175928_3_;

      for(int j = -p_175928_3_; j <= p_175928_3_; ++j) {
         for(int k = -p_175928_3_; k <= p_175928_3_; ++k) {
            if (j * j + k * k <= i) {
               BlockPos blockpos = p_175928_2_.func_177982_a(j, 0, k);
               Material material = p_175928_1_.func_180495_p(blockpos).func_185904_a();
               if (material == Material.field_151579_a || material == Material.field_151584_j) {
                  this.func_175903_a(p_175928_1_, blockpos, this.field_76521_c);
               }
            }
         }
      }

   }
}
