package net.minecraft.world.gen.feature;

import com.google.common.base.Predicates;
import java.util.Random;
import net.minecraft.block.BlockSand;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.BlockStoneSlab;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockStateMatcher;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WorldGenDesertWells extends WorldGenerator {
   private static final BlockStateMatcher field_175913_a = BlockStateMatcher.func_177638_a(Blocks.field_150354_m).func_177637_a(BlockSand.field_176504_a, Predicates.equalTo(BlockSand.EnumType.SAND));
   private final IBlockState field_175911_b = Blocks.field_150333_U.func_176223_P().func_177226_a(BlockStoneSlab.field_176556_M, BlockStoneSlab.EnumType.SAND).func_177226_a(BlockSlab.field_176554_a, BlockSlab.EnumBlockHalf.BOTTOM);
   private final IBlockState field_175912_c = Blocks.field_150322_A.func_176223_P();
   private final IBlockState field_175910_d = Blocks.field_150358_i.func_176223_P();

   public boolean func_180709_b(World p_180709_1_, Random p_180709_2_, BlockPos p_180709_3_) {
      while(p_180709_1_.func_175623_d(p_180709_3_) && p_180709_3_.func_177956_o() > 2) {
         p_180709_3_ = p_180709_3_.func_177977_b();
      }

      if (!field_175913_a.apply(p_180709_1_.func_180495_p(p_180709_3_))) {
         return false;
      } else {
         for(int i = -2; i <= 2; ++i) {
            for(int j = -2; j <= 2; ++j) {
               if (p_180709_1_.func_175623_d(p_180709_3_.func_177982_a(i, -1, j)) && p_180709_1_.func_175623_d(p_180709_3_.func_177982_a(i, -2, j))) {
                  return false;
               }
            }
         }

         for(int l = -1; l <= 0; ++l) {
            for(int l1 = -2; l1 <= 2; ++l1) {
               for(int k = -2; k <= 2; ++k) {
                  p_180709_1_.func_180501_a(p_180709_3_.func_177982_a(l1, l, k), this.field_175912_c, 2);
               }
            }
         }

         p_180709_1_.func_180501_a(p_180709_3_, this.field_175910_d, 2);

         for(EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
            p_180709_1_.func_180501_a(p_180709_3_.func_177972_a(enumfacing), this.field_175910_d, 2);
         }

         for(int i1 = -2; i1 <= 2; ++i1) {
            for(int i2 = -2; i2 <= 2; ++i2) {
               if (i1 == -2 || i1 == 2 || i2 == -2 || i2 == 2) {
                  p_180709_1_.func_180501_a(p_180709_3_.func_177982_a(i1, 1, i2), this.field_175912_c, 2);
               }
            }
         }

         p_180709_1_.func_180501_a(p_180709_3_.func_177982_a(2, 1, 0), this.field_175911_b, 2);
         p_180709_1_.func_180501_a(p_180709_3_.func_177982_a(-2, 1, 0), this.field_175911_b, 2);
         p_180709_1_.func_180501_a(p_180709_3_.func_177982_a(0, 1, 2), this.field_175911_b, 2);
         p_180709_1_.func_180501_a(p_180709_3_.func_177982_a(0, 1, -2), this.field_175911_b, 2);

         for(int j1 = -1; j1 <= 1; ++j1) {
            for(int j2 = -1; j2 <= 1; ++j2) {
               if (j1 == 0 && j2 == 0) {
                  p_180709_1_.func_180501_a(p_180709_3_.func_177982_a(j1, 4, j2), this.field_175912_c, 2);
               } else {
                  p_180709_1_.func_180501_a(p_180709_3_.func_177982_a(j1, 4, j2), this.field_175911_b, 2);
               }
            }
         }

         for(int k1 = 1; k1 <= 3; ++k1) {
            p_180709_1_.func_180501_a(p_180709_3_.func_177982_a(-1, k1, -1), this.field_175912_c, 2);
            p_180709_1_.func_180501_a(p_180709_3_.func_177982_a(-1, k1, 1), this.field_175912_c, 2);
            p_180709_1_.func_180501_a(p_180709_3_.func_177982_a(1, k1, -1), this.field_175912_c, 2);
            p_180709_1_.func_180501_a(p_180709_3_.func_177982_a(1, k1, 1), this.field_175912_c, 2);
         }

         return true;
      }
   }
}
