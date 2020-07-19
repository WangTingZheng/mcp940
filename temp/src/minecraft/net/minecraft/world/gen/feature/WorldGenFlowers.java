package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WorldGenFlowers extends WorldGenerator {
   private BlockFlower field_150552_a;
   private IBlockState field_175915_b;

   public WorldGenFlowers(BlockFlower p_i45632_1_, BlockFlower.EnumFlowerType p_i45632_2_) {
      this.func_175914_a(p_i45632_1_, p_i45632_2_);
   }

   public void func_175914_a(BlockFlower p_175914_1_, BlockFlower.EnumFlowerType p_175914_2_) {
      this.field_150552_a = p_175914_1_;
      this.field_175915_b = p_175914_1_.func_176223_P().func_177226_a(p_175914_1_.func_176494_l(), p_175914_2_);
   }

   public boolean func_180709_b(World p_180709_1_, Random p_180709_2_, BlockPos p_180709_3_) {
      for(int i = 0; i < 64; ++i) {
         BlockPos blockpos = p_180709_3_.func_177982_a(p_180709_2_.nextInt(8) - p_180709_2_.nextInt(8), p_180709_2_.nextInt(4) - p_180709_2_.nextInt(4), p_180709_2_.nextInt(8) - p_180709_2_.nextInt(8));
         if (p_180709_1_.func_175623_d(blockpos) && (!p_180709_1_.field_73011_w.func_177495_o() || blockpos.func_177956_o() < 255) && this.field_150552_a.func_180671_f(p_180709_1_, blockpos, this.field_175915_b)) {
            p_180709_1_.func_180501_a(blockpos, this.field_175915_b, 2);
         }
      }

      return true;
   }
}
