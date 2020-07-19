package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WorldGenGlowStone1 extends WorldGenerator {
   public boolean func_180709_b(World p_180709_1_, Random p_180709_2_, BlockPos p_180709_3_) {
      if (!p_180709_1_.func_175623_d(p_180709_3_)) {
         return false;
      } else if (p_180709_1_.func_180495_p(p_180709_3_.func_177984_a()).func_177230_c() != Blocks.field_150424_aL) {
         return false;
      } else {
         p_180709_1_.func_180501_a(p_180709_3_, Blocks.field_150426_aN.func_176223_P(), 2);

         for(int i = 0; i < 1500; ++i) {
            BlockPos blockpos = p_180709_3_.func_177982_a(p_180709_2_.nextInt(8) - p_180709_2_.nextInt(8), -p_180709_2_.nextInt(12), p_180709_2_.nextInt(8) - p_180709_2_.nextInt(8));
            if (p_180709_1_.func_180495_p(blockpos).func_185904_a() == Material.field_151579_a) {
               int j = 0;

               for(EnumFacing enumfacing : EnumFacing.values()) {
                  if (p_180709_1_.func_180495_p(blockpos.func_177972_a(enumfacing)).func_177230_c() == Blocks.field_150426_aN) {
                     ++j;
                  }

                  if (j > 1) {
                     break;
                  }
               }

               if (j == 1) {
                  p_180709_1_.func_180501_a(blockpos, Blocks.field_150426_aN.func_176223_P(), 2);
               }
            }
         }

         return true;
      }
   }
}
