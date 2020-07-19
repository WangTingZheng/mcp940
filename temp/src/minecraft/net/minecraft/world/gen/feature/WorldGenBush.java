package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.BlockBush;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WorldGenBush extends WorldGenerator {
   private final BlockBush field_175908_a;

   public WorldGenBush(BlockBush p_i45633_1_) {
      this.field_175908_a = p_i45633_1_;
   }

   public boolean func_180709_b(World p_180709_1_, Random p_180709_2_, BlockPos p_180709_3_) {
      for(int i = 0; i < 64; ++i) {
         BlockPos blockpos = p_180709_3_.func_177982_a(p_180709_2_.nextInt(8) - p_180709_2_.nextInt(8), p_180709_2_.nextInt(4) - p_180709_2_.nextInt(4), p_180709_2_.nextInt(8) - p_180709_2_.nextInt(8));
         if (p_180709_1_.func_175623_d(blockpos) && (!p_180709_1_.field_73011_w.func_177495_o() || blockpos.func_177956_o() < 255) && this.field_175908_a.func_180671_f(p_180709_1_, blockpos, this.field_175908_a.func_176223_P())) {
            p_180709_1_.func_180501_a(blockpos, this.field_175908_a.func_176223_P(), 2);
         }
      }

      return true;
   }
}
