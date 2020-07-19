package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.BlockRenderLayer;

public class BlockGlass extends BlockBreakable {
   public BlockGlass(Material p_i45408_1_, boolean p_i45408_2_) {
      super(p_i45408_1_, p_i45408_2_);
      this.func_149647_a(CreativeTabs.field_78030_b);
   }

   public int func_149745_a(Random p_149745_1_) {
      return 0;
   }

   public BlockRenderLayer func_180664_k() {
      return BlockRenderLayer.CUTOUT;
   }

   public boolean func_149686_d(IBlockState p_149686_1_) {
      return false;
   }

   protected boolean func_149700_E() {
      return true;
   }
}
