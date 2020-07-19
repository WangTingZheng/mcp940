package net.minecraft.client.renderer.color;

import javax.annotation.Nullable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public interface IBlockColor {
   int func_186720_a(IBlockState var1, @Nullable IBlockAccess var2, @Nullable BlockPos var3, int var4);
}
