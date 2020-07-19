package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WorldGenShrub extends WorldGenTrees {
   private final IBlockState field_150528_a;
   private final IBlockState field_150527_b;

   public WorldGenShrub(IBlockState p_i46450_1_, IBlockState p_i46450_2_) {
      super(false);
      this.field_150527_b = p_i46450_1_;
      this.field_150528_a = p_i46450_2_;
   }

   public boolean func_180709_b(World p_180709_1_, Random p_180709_2_, BlockPos p_180709_3_) {
      for(IBlockState iblockstate = p_180709_1_.func_180495_p(p_180709_3_); (iblockstate.func_185904_a() == Material.field_151579_a || iblockstate.func_185904_a() == Material.field_151584_j) && p_180709_3_.func_177956_o() > 0; iblockstate = p_180709_1_.func_180495_p(p_180709_3_)) {
         p_180709_3_ = p_180709_3_.func_177977_b();
      }

      Block block = p_180709_1_.func_180495_p(p_180709_3_).func_177230_c();
      if (block == Blocks.field_150346_d || block == Blocks.field_150349_c) {
         p_180709_3_ = p_180709_3_.func_177984_a();
         this.func_175903_a(p_180709_1_, p_180709_3_, this.field_150527_b);

         for(int i = p_180709_3_.func_177956_o(); i <= p_180709_3_.func_177956_o() + 2; ++i) {
            int j = i - p_180709_3_.func_177956_o();
            int k = 2 - j;

            for(int l = p_180709_3_.func_177958_n() - k; l <= p_180709_3_.func_177958_n() + k; ++l) {
               int i1 = l - p_180709_3_.func_177958_n();

               for(int j1 = p_180709_3_.func_177952_p() - k; j1 <= p_180709_3_.func_177952_p() + k; ++j1) {
                  int k1 = j1 - p_180709_3_.func_177952_p();
                  if (Math.abs(i1) != k || Math.abs(k1) != k || p_180709_2_.nextInt(2) != 0) {
                     BlockPos blockpos = new BlockPos(l, i, j1);
                     Material material = p_180709_1_.func_180495_p(blockpos).func_185904_a();
                     if (material == Material.field_151579_a || material == Material.field_151584_j) {
                        this.func_175903_a(p_180709_1_, blockpos, this.field_150528_a);
                     }
                  }
               }
            }
         }
      }

      return true;
   }
}
