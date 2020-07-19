package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IGrowable {
   boolean func_176473_a(World var1, BlockPos var2, IBlockState var3, boolean var4);

   boolean func_180670_a(World var1, Random var2, BlockPos var3, IBlockState var4);

   void func_176474_b(World var1, Random var2, BlockPos var3, IBlockState var4);
}
