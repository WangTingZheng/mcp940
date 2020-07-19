package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IGrowable
{
    /**
     * Whether this IGrowable can grow
     */
    boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient);

    boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state);

    void grow(World worldIn, Random rand, BlockPos pos, IBlockState state);
}
