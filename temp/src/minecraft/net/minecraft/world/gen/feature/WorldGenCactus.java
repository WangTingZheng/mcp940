package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WorldGenCactus extends WorldGenerator {
   public boolean func_180709_b(World p_180709_1_, Random p_180709_2_, BlockPos p_180709_3_) {
      for(int i = 0; i < 10; ++i) {
         BlockPos blockpos = p_180709_3_.func_177982_a(p_180709_2_.nextInt(8) - p_180709_2_.nextInt(8), p_180709_2_.nextInt(4) - p_180709_2_.nextInt(4), p_180709_2_.nextInt(8) - p_180709_2_.nextInt(8));
         if (p_180709_1_.func_175623_d(blockpos)) {
            int j = 1 + p_180709_2_.nextInt(p_180709_2_.nextInt(3) + 1);

            for(int k = 0; k < j; ++k) {
               if (Blocks.field_150434_aF.func_176586_d(p_180709_1_, blockpos)) {
                  p_180709_1_.func_180501_a(blockpos.func_177981_b(k), Blocks.field_150434_aF.func_176223_P(), 2);
               }
            }
         }
      }

      return true;
   }
}
