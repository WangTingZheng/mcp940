package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockNewLeaf;
import net.minecraft.block.BlockNewLog;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WorldGenCanopyTree extends WorldGenAbstractTree
{
    private static final IBlockState DARK_OAK_LOG = Blocks.LOG2.getDefaultState().withProperty(BlockNewLog.VARIANT, BlockPlanks.EnumType.DARK_OAK);
    private static final IBlockState DARK_OAK_LEAVES = Blocks.LEAVES2.getDefaultState().withProperty(BlockNewLeaf.VARIANT, BlockPlanks.EnumType.DARK_OAK).withProperty(BlockLeaves.CHECK_DECAY, Boolean.valueOf(false));

    public WorldGenCanopyTree(boolean notify)
    {
        super(notify);
    }

    public boolean generate(World worldIn, Random rand, BlockPos position)
    {
        int i = rand.nextInt(3) + rand.nextInt(2) + 6;
        int j = position.getX();
        int k = position.getY();
        int l = position.getZ();

        if (k >= 1 && k + i + 1 < 256)
        {
            BlockPos blockpos = position.down();
            Block block = worldIn.getBlockState(blockpos).getBlock();

            if (block != Blocks.GRASS && block != Blocks.DIRT)
            {
                return false;
            }
            else if (!this.placeTreeOfHeight(worldIn, position, i))
            {
                return false;
            }
            else
            {
                this.setDirtAt(worldIn, blockpos);
                this.setDirtAt(worldIn, blockpos.east());
                this.setDirtAt(worldIn, blockpos.south());
                this.setDirtAt(worldIn, blockpos.south().east());
                EnumFacing enumfacing = EnumFacing.Plane.HORIZONTAL.random(rand);
                int i1 = i - rand.nextInt(4);
                int j1 = 2 - rand.nextInt(3);
                int k1 = j;
                int l1 = l;
                int i2 = k + i - 1;

                for (int j2 = 0; j2 < i; ++j2)
                {
                    if (j2 >= i1 && j1 > 0)
                    {
                        k1 += enumfacing.getFrontOffsetX();
                        l1 += enumfacing.getFrontOffsetZ();
                        --j1;
                    }

                    int k2 = k + j2;
                    BlockPos blockpos1 = new BlockPos(k1, k2, l1);
                    Material material = worldIn.getBlockState(blockpos1).getMaterial();

                    if (material == Material.AIR || material == Material.LEAVES)
                    {
                        this.placeLogAt(worldIn, blockpos1);
                        this.placeLogAt(worldIn, blockpos1.east());
                        this.placeLogAt(worldIn, blockpos1.south());
                        this.placeLogAt(worldIn, blockpos1.east().south());
                    }
                }

                for (int i3 = -2; i3 <= 0; ++i3)
                {
                    for (int l3 = -2; l3 <= 0; ++l3)
                    {
                        int k4 = -1;
                        this.placeLeafAt(worldIn, k1 + i3, i2 + k4, l1 + l3);
                        this.placeLeafAt(worldIn, 1 + k1 - i3, i2 + k4, l1 + l3);
                        this.placeLeafAt(worldIn, k1 + i3, i2 + k4, 1 + l1 - l3);
                        this.placeLeafAt(worldIn, 1 + k1 - i3, i2 + k4, 1 + l1 - l3);

                        if ((i3 > -2 || l3 > -1) && (i3 != -1 || l3 != -2))
                        {
                            k4 = 1;
                            this.placeLeafAt(worldIn, k1 + i3, i2 + k4, l1 + l3);
                            this.placeLeafAt(worldIn, 1 + k1 - i3, i2 + k4, l1 + l3);
                            this.placeLeafAt(worldIn, k1 + i3, i2 + k4, 1 + l1 - l3);
                            this.placeLeafAt(worldIn, 1 + k1 - i3, i2 + k4, 1 + l1 - l3);
                        }
                    }
                }

                if (rand.nextBoolean())
                {
                    this.placeLeafAt(worldIn, k1, i2 + 2, l1);
                    this.placeLeafAt(worldIn, k1 + 1, i2 + 2, l1);
                    this.placeLeafAt(worldIn, k1 + 1, i2 + 2, l1 + 1);
                    this.placeLeafAt(worldIn, k1, i2 + 2, l1 + 1);
                }

                for (int j3 = -3; j3 <= 4; ++j3)
                {
                    for (int i4 = -3; i4 <= 4; ++i4)
                    {
                        if ((j3 != -3 || i4 != -3) && (j3 != -3 || i4 != 4) && (j3 != 4 || i4 != -3) && (j3 != 4 || i4 != 4) && (Math.abs(j3) < 3 || Math.abs(i4) < 3))
                        {
                            this.placeLeafAt(worldIn, k1 + j3, i2, l1 + i4);
                        }
                    }
                }

                for (int k3 = -1; k3 <= 2; ++k3)
                {
                    for (int j4 = -1; j4 <= 2; ++j4)
                    {
                        if ((k3 < 0 || k3 > 1 || j4 < 0 || j4 > 1) && rand.nextInt(3) <= 0)
                        {
                            int l4 = rand.nextInt(3) + 2;

                            for (int i5 = 0; i5 < l4; ++i5)
                            {
                                this.placeLogAt(worldIn, new BlockPos(j + k3, i2 - i5 - 1, l + j4));
                            }

                            for (int j5 = -1; j5 <= 1; ++j5)
                            {
                                for (int l2 = -1; l2 <= 1; ++l2)
                                {
                                    this.placeLeafAt(worldIn, k1 + k3 + j5, i2, l1 + j4 + l2);
                                }
                            }

                            for (int k5 = -2; k5 <= 2; ++k5)
                            {
                                for (int l5 = -2; l5 <= 2; ++l5)
                                {
                                    if (Math.abs(k5) != 2 || Math.abs(l5) != 2)
                                    {
                                        this.placeLeafAt(worldIn, k1 + k3 + k5, i2 - 1, l1 + j4 + l5);
                                    }
                                }
                            }
                        }
                    }
                }

                return true;
            }
        }
        else
        {
            return false;
        }
    }

    private boolean placeTreeOfHeight(World worldIn, BlockPos pos, int height)
    {
        int i = pos.getX();
        int j = pos.getY();
        int k = pos.getZ();
        BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

        for (int l = 0; l <= height + 1; ++l)
        {
            int i1 = 1;

            if (l == 0)
            {
                i1 = 0;
            }

            if (l >= height - 1)
            {
                i1 = 2;
            }

            for (int j1 = -i1; j1 <= i1; ++j1)
            {
                for (int k1 = -i1; k1 <= i1; ++k1)
                {
                    if (!this.canGrowInto(worldIn.getBlockState(blockpos$mutableblockpos.setPos(i + j1, j + l, k + k1)).getBlock()))
                    {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    private void placeLogAt(World worldIn, BlockPos pos)
    {
        if (this.canGrowInto(worldIn.getBlockState(pos).getBlock()))
        {
            this.setBlockAndNotifyAdequately(worldIn, pos, DARK_OAK_LOG);
        }
    }

    private void placeLeafAt(World worldIn, int x, int y, int z)
    {
        BlockPos blockpos = new BlockPos(x, y, z);
        Material material = worldIn.getBlockState(blockpos).getMaterial();

        if (material == Material.AIR)
        {
            this.setBlockAndNotifyAdequately(worldIn, blockpos, DARK_OAK_LEAVES);
        }
    }
}
