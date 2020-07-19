package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WorldGenEndGateway extends WorldGenerator {
   public boolean func_180709_b(World p_180709_1_, Random p_180709_2_, BlockPos p_180709_3_) {
      for(BlockPos.MutableBlockPos blockpos$mutableblockpos : BlockPos.func_177975_b(p_180709_3_.func_177982_a(-1, -2, -1), p_180709_3_.func_177982_a(1, 2, 1))) {
         boolean flag = blockpos$mutableblockpos.func_177958_n() == p_180709_3_.func_177958_n();
         boolean flag1 = blockpos$mutableblockpos.func_177956_o() == p_180709_3_.func_177956_o();
         boolean flag2 = blockpos$mutableblockpos.func_177952_p() == p_180709_3_.func_177952_p();
         boolean flag3 = Math.abs(blockpos$mutableblockpos.func_177956_o() - p_180709_3_.func_177956_o()) == 2;
         if (flag && flag1 && flag2) {
            this.func_175903_a(p_180709_1_, new BlockPos(blockpos$mutableblockpos), Blocks.field_185775_db.func_176223_P());
         } else if (flag1) {
            this.func_175903_a(p_180709_1_, blockpos$mutableblockpos, Blocks.field_150350_a.func_176223_P());
         } else if (flag3 && flag && flag2) {
            this.func_175903_a(p_180709_1_, blockpos$mutableblockpos, Blocks.field_150357_h.func_176223_P());
         } else if ((flag || flag2) && !flag3) {
            this.func_175903_a(p_180709_1_, blockpos$mutableblockpos, Blocks.field_150357_h.func_176223_P());
         } else {
            this.func_175903_a(p_180709_1_, blockpos$mutableblockpos, Blocks.field_150350_a.func_176223_P());
         }
      }

      return true;
   }
}
