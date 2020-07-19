package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WorldGenHellLava extends WorldGenerator {
   private final Block field_150553_a;
   private final boolean field_94524_b;

   public WorldGenHellLava(Block p_i45453_1_, boolean p_i45453_2_) {
      this.field_150553_a = p_i45453_1_;
      this.field_94524_b = p_i45453_2_;
   }

   public boolean func_180709_b(World p_180709_1_, Random p_180709_2_, BlockPos p_180709_3_) {
      if (p_180709_1_.func_180495_p(p_180709_3_.func_177984_a()).func_177230_c() != Blocks.field_150424_aL) {
         return false;
      } else if (p_180709_1_.func_180495_p(p_180709_3_).func_185904_a() != Material.field_151579_a && p_180709_1_.func_180495_p(p_180709_3_).func_177230_c() != Blocks.field_150424_aL) {
         return false;
      } else {
         int i = 0;
         if (p_180709_1_.func_180495_p(p_180709_3_.func_177976_e()).func_177230_c() == Blocks.field_150424_aL) {
            ++i;
         }

         if (p_180709_1_.func_180495_p(p_180709_3_.func_177974_f()).func_177230_c() == Blocks.field_150424_aL) {
            ++i;
         }

         if (p_180709_1_.func_180495_p(p_180709_3_.func_177978_c()).func_177230_c() == Blocks.field_150424_aL) {
            ++i;
         }

         if (p_180709_1_.func_180495_p(p_180709_3_.func_177968_d()).func_177230_c() == Blocks.field_150424_aL) {
            ++i;
         }

         if (p_180709_1_.func_180495_p(p_180709_3_.func_177977_b()).func_177230_c() == Blocks.field_150424_aL) {
            ++i;
         }

         int j = 0;
         if (p_180709_1_.func_175623_d(p_180709_3_.func_177976_e())) {
            ++j;
         }

         if (p_180709_1_.func_175623_d(p_180709_3_.func_177974_f())) {
            ++j;
         }

         if (p_180709_1_.func_175623_d(p_180709_3_.func_177978_c())) {
            ++j;
         }

         if (p_180709_1_.func_175623_d(p_180709_3_.func_177968_d())) {
            ++j;
         }

         if (p_180709_1_.func_175623_d(p_180709_3_.func_177977_b())) {
            ++j;
         }

         if (!this.field_94524_b && i == 4 && j == 1 || i == 5) {
            IBlockState iblockstate = this.field_150553_a.func_176223_P();
            p_180709_1_.func_180501_a(p_180709_3_, iblockstate, 2);
            p_180709_1_.func_189507_a(p_180709_3_, iblockstate, p_180709_2_);
         }

         return true;
      }
   }
}
