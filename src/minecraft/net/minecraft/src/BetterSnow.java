package net.minecraft.src;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.BlockFlowerPot;
import net.minecraft.block.BlockLever;
import net.minecraft.block.BlockMushroom;
import net.minecraft.block.BlockPane;
import net.minecraft.block.BlockRedstoneTorch;
import net.minecraft.block.BlockReed;
import net.minecraft.block.BlockSapling;
import net.minecraft.block.BlockSnow;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.block.BlockTorch;
import net.minecraft.block.BlockWall;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BetterSnow
{
    private static IBakedModel modelSnowLayer = null;

    public static void update()
    {
        modelSnowLayer = Config.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes().getModelForState(Blocks.SNOW_LAYER.getDefaultState());
    }

    public static IBakedModel getModelSnowLayer()
    {
        return modelSnowLayer;
    }

    public static IBlockState getStateSnowLayer()
    {
        return Blocks.SNOW_LAYER.getDefaultState();
    }

    public static boolean shouldRender(IBlockAccess p_shouldRender_0_, IBlockState p_shouldRender_1_, BlockPos p_shouldRender_2_)
    {
        Block block = p_shouldRender_1_.getBlock();
        return !checkBlock(block, p_shouldRender_1_) ? false : hasSnowNeighbours(p_shouldRender_0_, p_shouldRender_2_);
    }

    private static boolean hasSnowNeighbours(IBlockAccess p_hasSnowNeighbours_0_, BlockPos p_hasSnowNeighbours_1_)
    {
        Block block = Blocks.SNOW_LAYER;
        return p_hasSnowNeighbours_0_.getBlockState(p_hasSnowNeighbours_1_.north()).getBlock() != block && p_hasSnowNeighbours_0_.getBlockState(p_hasSnowNeighbours_1_.south()).getBlock() != block && p_hasSnowNeighbours_0_.getBlockState(p_hasSnowNeighbours_1_.west()).getBlock() != block && p_hasSnowNeighbours_0_.getBlockState(p_hasSnowNeighbours_1_.east()).getBlock() != block ? false : p_hasSnowNeighbours_0_.getBlockState(p_hasSnowNeighbours_1_.down()).isOpaqueCube();
    }

    private static boolean checkBlock(Block p_checkBlock_0_, IBlockState p_checkBlock_1_)
    {
        if (p_checkBlock_1_.isFullCube())
        {
            return false;
        }
        else if (p_checkBlock_1_.isOpaqueCube())
        {
            return false;
        }
        else if (p_checkBlock_0_ instanceof BlockSnow)
        {
            return false;
        }
        else if (!(p_checkBlock_0_ instanceof BlockBush) || !(p_checkBlock_0_ instanceof BlockDoublePlant) && !(p_checkBlock_0_ instanceof BlockFlower) && !(p_checkBlock_0_ instanceof BlockMushroom) && !(p_checkBlock_0_ instanceof BlockSapling) && !(p_checkBlock_0_ instanceof BlockTallGrass))
        {
            if (!(p_checkBlock_0_ instanceof BlockFence) && !(p_checkBlock_0_ instanceof BlockFenceGate) && !(p_checkBlock_0_ instanceof BlockFlowerPot) && !(p_checkBlock_0_ instanceof BlockPane) && !(p_checkBlock_0_ instanceof BlockReed) && !(p_checkBlock_0_ instanceof BlockWall))
            {
                if (p_checkBlock_0_ instanceof BlockRedstoneTorch && p_checkBlock_1_.getValue(BlockTorch.FACING) == EnumFacing.UP)
                {
                    return true;
                }
                else
                {
                    if (p_checkBlock_0_ instanceof BlockLever)
                    {
                        Object object = p_checkBlock_1_.getValue(BlockLever.FACING);

                        if (object == BlockLever.EnumOrientation.UP_X || object == BlockLever.EnumOrientation.UP_Z)
                        {
                            return true;
                        }
                    }

                    return false;
                }
            }
            else
            {
                return true;
            }
        }
        else
        {
            return true;
        }
    }
}
