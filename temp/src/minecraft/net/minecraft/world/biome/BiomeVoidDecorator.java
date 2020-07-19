package net.minecraft.world.biome;

import java.util.Random;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BiomeVoidDecorator extends BiomeDecorator {
   public void func_180292_a(World p_180292_1_, Random p_180292_2_, Biome p_180292_3_, BlockPos p_180292_4_) {
      BlockPos blockpos = p_180292_1_.func_175694_M();
      int i = 16;
      double d0 = blockpos.func_177951_i(p_180292_4_.func_177982_a(8, blockpos.func_177956_o(), 8));
      if (d0 <= 1024.0D) {
         BlockPos blockpos1 = new BlockPos(blockpos.func_177958_n() - 16, blockpos.func_177956_o() - 1, blockpos.func_177952_p() - 16);
         BlockPos blockpos2 = new BlockPos(blockpos.func_177958_n() + 16, blockpos.func_177956_o() - 1, blockpos.func_177952_p() + 16);
         BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos(blockpos1);

         for(int j = p_180292_4_.func_177952_p(); j < p_180292_4_.func_177952_p() + 16; ++j) {
            for(int k = p_180292_4_.func_177958_n(); k < p_180292_4_.func_177958_n() + 16; ++k) {
               if (j >= blockpos1.func_177952_p() && j <= blockpos2.func_177952_p() && k >= blockpos1.func_177958_n() && k <= blockpos2.func_177958_n()) {
                  blockpos$mutableblockpos.func_181079_c(k, blockpos$mutableblockpos.func_177956_o(), j);
                  if (blockpos.func_177958_n() == k && blockpos.func_177952_p() == j) {
                     p_180292_1_.func_180501_a(blockpos$mutableblockpos, Blocks.field_150347_e.func_176223_P(), 2);
                  } else {
                     p_180292_1_.func_180501_a(blockpos$mutableblockpos, Blocks.field_150348_b.func_176223_P(), 2);
                  }
               }
            }
         }

      }
   }
}
