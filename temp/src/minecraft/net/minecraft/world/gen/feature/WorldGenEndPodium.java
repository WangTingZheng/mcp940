package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.BlockTorch;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WorldGenEndPodium extends WorldGenerator {
   public static final BlockPos field_186139_a = BlockPos.field_177992_a;
   public static final BlockPos field_186140_b = new BlockPos(field_186139_a.func_177958_n() - 4 & -16, 0, field_186139_a.func_177952_p() - 4 & -16);
   private final boolean field_186141_c;

   public WorldGenEndPodium(boolean p_i46666_1_) {
      this.field_186141_c = p_i46666_1_;
   }

   public boolean func_180709_b(World p_180709_1_, Random p_180709_2_, BlockPos p_180709_3_) {
      for(BlockPos.MutableBlockPos blockpos$mutableblockpos : BlockPos.func_177975_b(new BlockPos(p_180709_3_.func_177958_n() - 4, p_180709_3_.func_177956_o() - 1, p_180709_3_.func_177952_p() - 4), new BlockPos(p_180709_3_.func_177958_n() + 4, p_180709_3_.func_177956_o() + 32, p_180709_3_.func_177952_p() + 4))) {
         double d0 = blockpos$mutableblockpos.func_185332_f(p_180709_3_.func_177958_n(), blockpos$mutableblockpos.func_177956_o(), p_180709_3_.func_177952_p());
         if (d0 <= 3.5D) {
            if (blockpos$mutableblockpos.func_177956_o() < p_180709_3_.func_177956_o()) {
               if (d0 <= 2.5D) {
                  this.func_175903_a(p_180709_1_, blockpos$mutableblockpos, Blocks.field_150357_h.func_176223_P());
               } else if (blockpos$mutableblockpos.func_177956_o() < p_180709_3_.func_177956_o()) {
                  this.func_175903_a(p_180709_1_, blockpos$mutableblockpos, Blocks.field_150377_bs.func_176223_P());
               }
            } else if (blockpos$mutableblockpos.func_177956_o() > p_180709_3_.func_177956_o()) {
               this.func_175903_a(p_180709_1_, blockpos$mutableblockpos, Blocks.field_150350_a.func_176223_P());
            } else if (d0 > 2.5D) {
               this.func_175903_a(p_180709_1_, blockpos$mutableblockpos, Blocks.field_150357_h.func_176223_P());
            } else if (this.field_186141_c) {
               this.func_175903_a(p_180709_1_, new BlockPos(blockpos$mutableblockpos), Blocks.field_150384_bq.func_176223_P());
            } else {
               this.func_175903_a(p_180709_1_, new BlockPos(blockpos$mutableblockpos), Blocks.field_150350_a.func_176223_P());
            }
         }
      }

      for(int i = 0; i < 4; ++i) {
         this.func_175903_a(p_180709_1_, p_180709_3_.func_177981_b(i), Blocks.field_150357_h.func_176223_P());
      }

      BlockPos blockpos = p_180709_3_.func_177981_b(2);

      for(EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
         this.func_175903_a(p_180709_1_, blockpos.func_177972_a(enumfacing), Blocks.field_150478_aa.func_176223_P().func_177226_a(BlockTorch.field_176596_a, enumfacing));
      }

      return true;
   }
}
