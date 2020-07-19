package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WorldGenDeadBush extends WorldGenerator {
   public boolean func_180709_b(World p_180709_1_, Random p_180709_2_, BlockPos p_180709_3_) {
      for(IBlockState iblockstate = p_180709_1_.func_180495_p(p_180709_3_); (iblockstate.func_185904_a() == Material.field_151579_a || iblockstate.func_185904_a() == Material.field_151584_j) && p_180709_3_.func_177956_o() > 0; iblockstate = p_180709_1_.func_180495_p(p_180709_3_)) {
         p_180709_3_ = p_180709_3_.func_177977_b();
      }

      for(int i = 0; i < 4; ++i) {
         BlockPos blockpos = p_180709_3_.func_177982_a(p_180709_2_.nextInt(8) - p_180709_2_.nextInt(8), p_180709_2_.nextInt(4) - p_180709_2_.nextInt(4), p_180709_2_.nextInt(8) - p_180709_2_.nextInt(8));
         if (p_180709_1_.func_175623_d(blockpos) && Blocks.field_150330_I.func_180671_f(p_180709_1_, blockpos, Blocks.field_150330_I.func_176223_P())) {
            p_180709_1_.func_180501_a(blockpos, Blocks.field_150330_I.func_176223_P(), 2);
         }
      }

      return true;
   }
}
