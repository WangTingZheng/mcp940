package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WorldGenReed extends WorldGenerator {
   public boolean func_180709_b(World p_180709_1_, Random p_180709_2_, BlockPos p_180709_3_) {
      for(int i = 0; i < 20; ++i) {
         BlockPos blockpos = p_180709_3_.func_177982_a(p_180709_2_.nextInt(4) - p_180709_2_.nextInt(4), 0, p_180709_2_.nextInt(4) - p_180709_2_.nextInt(4));
         if (p_180709_1_.func_175623_d(blockpos)) {
            BlockPos blockpos1 = blockpos.func_177977_b();
            if (p_180709_1_.func_180495_p(blockpos1.func_177976_e()).func_185904_a() == Material.field_151586_h || p_180709_1_.func_180495_p(blockpos1.func_177974_f()).func_185904_a() == Material.field_151586_h || p_180709_1_.func_180495_p(blockpos1.func_177978_c()).func_185904_a() == Material.field_151586_h || p_180709_1_.func_180495_p(blockpos1.func_177968_d()).func_185904_a() == Material.field_151586_h) {
               int j = 2 + p_180709_2_.nextInt(p_180709_2_.nextInt(3) + 1);

               for(int k = 0; k < j; ++k) {
                  if (Blocks.field_150436_aH.func_176354_d(p_180709_1_, blockpos)) {
                     p_180709_1_.func_180501_a(blockpos.func_177981_b(k), Blocks.field_150436_aH.func_176223_P(), 2);
                  }
               }
            }
         }
      }

      return true;
   }
}
