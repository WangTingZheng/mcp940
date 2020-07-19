package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WorldGenIcePath extends WorldGenerator {
   private final Block field_150555_a = Blocks.field_150403_cj;
   private final int field_150554_b;

   public WorldGenIcePath(int p_i45454_1_) {
      this.field_150554_b = p_i45454_1_;
   }

   public boolean func_180709_b(World p_180709_1_, Random p_180709_2_, BlockPos p_180709_3_) {
      while(p_180709_1_.func_175623_d(p_180709_3_) && p_180709_3_.func_177956_o() > 2) {
         p_180709_3_ = p_180709_3_.func_177977_b();
      }

      if (p_180709_1_.func_180495_p(p_180709_3_).func_177230_c() != Blocks.field_150433_aE) {
         return false;
      } else {
         int i = p_180709_2_.nextInt(this.field_150554_b - 2) + 2;
         int j = 1;

         for(int k = p_180709_3_.func_177958_n() - i; k <= p_180709_3_.func_177958_n() + i; ++k) {
            for(int l = p_180709_3_.func_177952_p() - i; l <= p_180709_3_.func_177952_p() + i; ++l) {
               int i1 = k - p_180709_3_.func_177958_n();
               int j1 = l - p_180709_3_.func_177952_p();
               if (i1 * i1 + j1 * j1 <= i * i) {
                  for(int k1 = p_180709_3_.func_177956_o() - 1; k1 <= p_180709_3_.func_177956_o() + 1; ++k1) {
                     BlockPos blockpos = new BlockPos(k, k1, l);
                     Block block = p_180709_1_.func_180495_p(blockpos).func_177230_c();
                     if (block == Blocks.field_150346_d || block == Blocks.field_150433_aE || block == Blocks.field_150432_aD) {
                        p_180709_1_.func_180501_a(blockpos, this.field_150555_a.func_176223_P(), 2);
                     }
                  }
               }
            }
         }

         return true;
      }
   }
}
