package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockBarrier extends Block {
   protected BlockBarrier() {
      super(Material.field_175972_I);
      this.func_149722_s();
      this.func_149752_b(6000001.0F);
      this.func_149649_H();
      this.field_149785_s = true;
   }

   public EnumBlockRenderType func_149645_b(IBlockState p_149645_1_) {
      return EnumBlockRenderType.INVISIBLE;
   }

   public boolean func_149662_c(IBlockState p_149662_1_) {
      return false;
   }

   public float func_185485_f(IBlockState p_185485_1_) {
      return 1.0F;
   }

   public void func_180653_a(World p_180653_1_, BlockPos p_180653_2_, IBlockState p_180653_3_, float p_180653_4_, int p_180653_5_) {
   }
}
