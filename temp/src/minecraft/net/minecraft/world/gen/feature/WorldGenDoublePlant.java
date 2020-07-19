package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WorldGenDoublePlant extends WorldGenerator {
   private BlockDoublePlant.EnumPlantType field_150549_a;

   public void func_180710_a(BlockDoublePlant.EnumPlantType p_180710_1_) {
      this.field_150549_a = p_180710_1_;
   }

   public boolean func_180709_b(World p_180709_1_, Random p_180709_2_, BlockPos p_180709_3_) {
      boolean flag = false;

      for(int i = 0; i < 64; ++i) {
         BlockPos blockpos = p_180709_3_.func_177982_a(p_180709_2_.nextInt(8) - p_180709_2_.nextInt(8), p_180709_2_.nextInt(4) - p_180709_2_.nextInt(4), p_180709_2_.nextInt(8) - p_180709_2_.nextInt(8));
         if (p_180709_1_.func_175623_d(blockpos) && (!p_180709_1_.field_73011_w.func_177495_o() || blockpos.func_177956_o() < 254) && Blocks.field_150398_cm.func_176196_c(p_180709_1_, blockpos)) {
            Blocks.field_150398_cm.func_176491_a(p_180709_1_, blockpos, this.field_150549_a, 2);
            flag = true;
         }
      }

      return flag;
   }
}
