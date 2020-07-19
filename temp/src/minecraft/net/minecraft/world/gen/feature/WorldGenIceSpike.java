package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class WorldGenIceSpike extends WorldGenerator {
   public boolean func_180709_b(World p_180709_1_, Random p_180709_2_, BlockPos p_180709_3_) {
      while(p_180709_1_.func_175623_d(p_180709_3_) && p_180709_3_.func_177956_o() > 2) {
         p_180709_3_ = p_180709_3_.func_177977_b();
      }

      if (p_180709_1_.func_180495_p(p_180709_3_).func_177230_c() != Blocks.field_150433_aE) {
         return false;
      } else {
         p_180709_3_ = p_180709_3_.func_177981_b(p_180709_2_.nextInt(4));
         int i = p_180709_2_.nextInt(4) + 7;
         int j = i / 4 + p_180709_2_.nextInt(2);
         if (j > 1 && p_180709_2_.nextInt(60) == 0) {
            p_180709_3_ = p_180709_3_.func_177981_b(10 + p_180709_2_.nextInt(30));
         }

         for(int k = 0; k < i; ++k) {
            float f = (1.0F - (float)k / (float)i) * (float)j;
            int l = MathHelper.func_76123_f(f);

            for(int i1 = -l; i1 <= l; ++i1) {
               float f1 = (float)MathHelper.func_76130_a(i1) - 0.25F;

               for(int j1 = -l; j1 <= l; ++j1) {
                  float f2 = (float)MathHelper.func_76130_a(j1) - 0.25F;
                  if ((i1 == 0 && j1 == 0 || f1 * f1 + f2 * f2 <= f * f) && (i1 != -l && i1 != l && j1 != -l && j1 != l || p_180709_2_.nextFloat() <= 0.75F)) {
                     IBlockState iblockstate = p_180709_1_.func_180495_p(p_180709_3_.func_177982_a(i1, k, j1));
                     Block block = iblockstate.func_177230_c();
                     if (iblockstate.func_185904_a() == Material.field_151579_a || block == Blocks.field_150346_d || block == Blocks.field_150433_aE || block == Blocks.field_150432_aD) {
                        this.func_175903_a(p_180709_1_, p_180709_3_.func_177982_a(i1, k, j1), Blocks.field_150403_cj.func_176223_P());
                     }

                     if (k != 0 && l > 1) {
                        iblockstate = p_180709_1_.func_180495_p(p_180709_3_.func_177982_a(i1, -k, j1));
                        block = iblockstate.func_177230_c();
                        if (iblockstate.func_185904_a() == Material.field_151579_a || block == Blocks.field_150346_d || block == Blocks.field_150433_aE || block == Blocks.field_150432_aD) {
                           this.func_175903_a(p_180709_1_, p_180709_3_.func_177982_a(i1, -k, j1), Blocks.field_150403_cj.func_176223_P());
                        }
                     }
                  }
               }
            }
         }

         int k1 = j - 1;
         if (k1 < 0) {
            k1 = 0;
         } else if (k1 > 1) {
            k1 = 1;
         }

         for(int l1 = -k1; l1 <= k1; ++l1) {
            for(int i2 = -k1; i2 <= k1; ++i2) {
               BlockPos blockpos = p_180709_3_.func_177982_a(l1, -1, i2);
               int j2 = 50;
               if (Math.abs(l1) == 1 && Math.abs(i2) == 1) {
                  j2 = p_180709_2_.nextInt(5);
               }

               while(blockpos.func_177956_o() > 50) {
                  IBlockState iblockstate1 = p_180709_1_.func_180495_p(blockpos);
                  Block block1 = iblockstate1.func_177230_c();
                  if (iblockstate1.func_185904_a() != Material.field_151579_a && block1 != Blocks.field_150346_d && block1 != Blocks.field_150433_aE && block1 != Blocks.field_150432_aD && block1 != Blocks.field_150403_cj) {
                     break;
                  }

                  this.func_175903_a(p_180709_1_, blockpos, Blocks.field_150403_cj.func_176223_P());
                  blockpos = blockpos.func_177977_b();
                  --j2;
                  if (j2 <= 0) {
                     blockpos = blockpos.func_177979_c(p_180709_2_.nextInt(5) + 1);
                     j2 = p_180709_2_.nextInt(5);
                  }
               }
            }
         }

         return true;
      }
   }
}
