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

public class WorldGenDesertWells extends WorldGenerator
{
    private static final BlockStateMatcher IS_SAND = BlockStateMatcher.forBlock(Blocks.SAND).where(BlockSand.VARIANT, Predicates.equalTo(BlockSand.EnumType.SAND));
    private final IBlockState sandSlab = Blocks.STONE_SLAB.getDefaultState().withProperty(BlockStoneSlab.VARIANT, BlockStoneSlab.EnumType.SAND).withProperty(BlockSlab.HALF, BlockSlab.EnumBlockHalf.BOTTOM);
    private final IBlockState sandstone = Blocks.SANDSTONE.getDefaultState();
    private final IBlockState water = Blocks.FLOWING_WATER.getDefaultState();

    public boolean generate(World worldIn, Random rand, BlockPos position)
    {
        while (worldIn.isAirBlock(position) && position.getY() > 2)
        {
            position = position.down();
        }

        if (!IS_SAND.apply(worldIn.getBlockState(position)))
        {
            return false;
        }
        else
        {
            for (int i = -2; i <= 2; ++i)
            {
                for (int j = -2; j <= 2; ++j)
                {
                    if (worldIn.isAirBlock(position.add(i, -1, j)) && worldIn.isAirBlock(position.add(i, -2, j)))
                    {
                        return false;
                    }
                }
            }

            for (int l = -1; l <= 0; ++l)
            {
                for (int l1 = -2; l1 <= 2; ++l1)
                {
                    for (int k = -2; k <= 2; ++k)
                    {
                        worldIn.setBlockState(position.add(l1, l, k), this.sandstone, 2);
                    }
                }
            }

            worldIn.setBlockState(position, this.water, 2);

            for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL)
            {
                worldIn.setBlockState(position.offset(enumfacing), this.water, 2);
            }

            for (int i1 = -2; i1 <= 2; ++i1)
            {
                for (int i2 = -2; i2 <= 2; ++i2)
                {
                    if (i1 == -2 || i1 == 2 || i2 == -2 || i2 == 2)
                    {
                        worldIn.setBlockState(position.add(i1, 1, i2), this.sandstone, 2);
                    }
                }
            }

            worldIn.setBlockState(position.add(2, 1, 0), this.sandSlab, 2);
            worldIn.setBlockState(position.add(-2, 1, 0), this.sandSlab, 2);
            worldIn.setBlockState(position.add(0, 1, 2), this.sandSlab, 2);
            worldIn.setBlockState(position.add(0, 1, -2), this.sandSlab, 2);

            for (int j1 = -1; j1 <= 1; ++j1)
            {
                for (int j2 = -1; j2 <= 1; ++j2)
                {
                    if (j1 == 0 && j2 == 0)
                    {
                        worldIn.setBlockState(position.add(j1, 4, j2), this.sandstone, 2);
                    }
                    else
                    {
                        worldIn.setBlockState(position.add(j1, 4, j2), this.sandSlab, 2);
                    }
                }
            }

            for (int k1 = 1; k1 <= 3; ++k1)
            {
                worldIn.setBlockState(position.add(-1, k1, -1), this.sandstone, 2);
                worldIn.setBlockState(position.add(-1, k1, 1), this.sandstone, 2);
                worldIn.setBlockState(position.add(1, k1, -1), this.sandstone, 2);
                worldIn.setBlockState(position.add(1, k1, 1), this.sandstone, 2);
            }

            return true;
        }
    }
}
