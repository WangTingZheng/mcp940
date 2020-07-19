package net.minecraft.dispenser;

import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;

public interface IBlockSource extends ILocatableSource {
   double func_82615_a();

   double func_82617_b();

   double func_82616_c();

   BlockPos func_180699_d();

   IBlockState func_189992_e();

   <T extends TileEntity> T func_150835_j();
}
