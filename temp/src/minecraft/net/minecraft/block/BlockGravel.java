package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BlockGravel extends BlockFalling {
   public Item func_180660_a(IBlockState p_180660_1_, Random p_180660_2_, int p_180660_3_) {
      if (p_180660_3_ > 3) {
         p_180660_3_ = 3;
      }

      return p_180660_2_.nextInt(10 - p_180660_3_ * 3) == 0 ? Items.field_151145_ak : super.func_180660_a(p_180660_1_, p_180660_2_, p_180660_3_);
   }

   public MapColor func_180659_g(IBlockState p_180659_1_, IBlockAccess p_180659_2_, BlockPos p_180659_3_) {
      return MapColor.field_151665_m;
   }

   public int func_189876_x(IBlockState p_189876_1_) {
      return -8356741;
   }
}
