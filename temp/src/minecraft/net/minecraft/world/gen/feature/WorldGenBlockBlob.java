package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WorldGenBlockBlob extends WorldGenerator {
   private final Block field_150545_a;
   private final int field_150544_b;

   public WorldGenBlockBlob(Block p_i45450_1_, int p_i45450_2_) {
      super(false);
      this.field_150545_a = p_i45450_1_;
      this.field_150544_b = p_i45450_2_;
   }

   public boolean func_180709_b(World p_180709_1_, Random p_180709_2_, BlockPos p_180709_3_) {
      while(true) {
         label50: {
            if (p_180709_3_.func_177956_o() > 3) {
               if (p_180709_1_.func_175623_d(p_180709_3_.func_177977_b())) {
                  break label50;
               }

               Block block = p_180709_1_.func_180495_p(p_180709_3_.func_177977_b()).func_177230_c();
               if (block != Blocks.field_150349_c && block != Blocks.field_150346_d && block != Blocks.field_150348_b) {
                  break label50;
               }
            }

            if (p_180709_3_.func_177956_o() <= 3) {
               return false;
            }

            int i1 = this.field_150544_b;

            for(int i = 0; i1 >= 0 && i < 3; ++i) {
               int j = i1 + p_180709_2_.nextInt(2);
               int k = i1 + p_180709_2_.nextInt(2);
               int l = i1 + p_180709_2_.nextInt(2);
               float f = (float)(j + k + l) * 0.333F + 0.5F;

               for(BlockPos blockpos : BlockPos.func_177980_a(p_180709_3_.func_177982_a(-j, -k, -l), p_180709_3_.func_177982_a(j, k, l))) {
                  if (blockpos.func_177951_i(p_180709_3_) <= (double)(f * f)) {
                     p_180709_1_.func_180501_a(blockpos, this.field_150545_a.func_176223_P(), 4);
                  }
               }

               p_180709_3_ = p_180709_3_.func_177982_a(-(i1 + 1) + p_180709_2_.nextInt(2 + i1 * 2), 0 - p_180709_2_.nextInt(2), -(i1 + 1) + p_180709_2_.nextInt(2 + i1 * 2));
            }

            return true;
         }

         p_180709_3_ = p_180709_3_.func_177977_b();
      }
   }
}
