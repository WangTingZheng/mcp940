package net.minecraft.block;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.NonNullList;
import net.minecraft.util.Tuple;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;

public class BlockSponge extends Block
{
    public static final PropertyBool WET = PropertyBool.create("wet");

    protected BlockSponge()
    {
        super(Material.SPONGE);
        this.setDefaultState(this.blockState.getBaseState().withProperty(WET, Boolean.valueOf(false)));
        this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
    }

    /**
     * Gets the localized name of this block. Used for the statistics page.
     */
    public String getLocalizedName()
    {
        return I18n.translateToLocal(this.getUnlocalizedName() + ".dry.name");
    }

    /**
     * Gets the metadata of the item this Block can drop. This method is called when the block gets destroyed. It
     * returns the metadata of the dropped item based on the old metadata of the block.
     */
    public int damageDropped(IBlockState state)
    {
        return ((Boolean)state.getValue(WET)).booleanValue() ? 1 : 0;
    }

    /**
     * Called after the block is set in the Chunk data, but before the Tile Entity is set
     */
    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state)
    {
        this.tryAbsorb(worldIn, pos, state);
    }

    /**
     * Called when a neighboring block was changed and marks that this state should perform any checks during a neighbor
     * change. Cases may include when redstone power is updated, cactus blocks popping off due to a neighboring solid
     * block, etc.
     */
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos)
    {
        this.tryAbsorb(worldIn, pos, state);
        super.neighborChanged(state, worldIn, pos, blockIn, fromPos);
    }

    protected void tryAbsorb(World worldIn, BlockPos pos, IBlockState state)
    {
        if (!((Boolean)state.getValue(WET)).booleanValue() && this.absorb(worldIn, pos))
        {
            worldIn.setBlockState(pos, state.withProperty(WET, Boolean.valueOf(true)), 2);
            worldIn.playEvent(2001, pos, Block.getIdFromBlock(Blocks.WATER));
        }
    }

    private boolean absorb(World worldIn, BlockPos pos)
    {
        Queue<Tuple<BlockPos, Integer>> queue = Lists.<Tuple<BlockPos, Integer>>newLinkedList();
        List<BlockPos> list = Lists.<BlockPos>newArrayList();
        queue.add(new Tuple(pos, Integer.valueOf(0)));
        int i = 0;

        while (!queue.isEmpty())
        {
            Tuple<BlockPos, Integer> tuple = (Tuple)queue.poll();
            BlockPos blockpos = tuple.getFirst();
            int j = ((Integer)tuple.getSecond()).intValue();

            for (EnumFacing enumfacing : EnumFacing.values())
            {
                BlockPos blockpos1 = blockpos.offset(enumfacing);

                if (worldIn.getBlockState(blockpos1).getMaterial() == Material.WATER)
                {
                    worldIn.setBlockState(blockpos1, Blocks.AIR.getDefaultState(), 2);
                    list.add(blockpos1);
                    ++i;

                    if (j < 6)
                    {
                        queue.add(new Tuple(blockpos1, j + 1));
                    }
                }
            }

            if (i > 64)
            {
                break;
            }
        }

        for (BlockPos blockpos2 : list)
        {
            worldIn.notifyNeighborsOfStateChange(blockpos2, Blocks.AIR, false);
        }

        return i > 0;
    }

    /**
     * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
     */
    public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items)
    {
        items.add(new ItemStack(this, 1, 0));
        items.add(new ItemStack(this, 1, 1));
    }

    /**
     * Convert the given metadata into a BlockState for this Block
     */
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(WET, Boolean.valueOf((meta & 1) == 1));
    }

    /**
     * Convert the BlockState into the correct metadata value
     */
    public int getMetaFromState(IBlockState state)
    {
        return ((Boolean)state.getValue(WET)).booleanValue() ? 1 : 0;
    }

    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {WET});
    }

    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand)
    {
        if (((Boolean)stateIn.getValue(WET)).booleanValue())
        {
            EnumFacing enumfacing = EnumFacing.random(rand);

            if (enumfacing != EnumFacing.UP && !worldIn.getBlockState(pos.offset(enumfacing)).isTopSolid())
            {
                double d0 = (double)pos.getX();
                double d1 = (double)pos.getY();
                double d2 = (double)pos.getZ();

                if (enumfacing == EnumFacing.DOWN)
                {
                    d1 = d1 - 0.05D;
                    d0 += rand.nextDouble();
                    d2 += rand.nextDouble();
                }
                else
                {
                    d1 = d1 + rand.nextDouble() * 0.8D;

                    if (enumfacing.getAxis() == EnumFacing.Axis.X)
                    {
                        d2 += rand.nextDouble();

                        if (enumfacing == EnumFacing.EAST)
                        {
                            ++d0;
                        }
                        else
                        {
                            d0 += 0.05D;
                        }
                    }
                    else
                    {
                        d0 += rand.nextDouble();

                        if (enumfacing == EnumFacing.SOUTH)
                        {
                            ++d2;
                        }
                        else
                        {
                            d2 += 0.05D;
                        }
                    }
                }

                worldIn.spawnParticle(EnumParticleTypes.DRIP_WATER, d0, d1, d2, 0.0D, 0.0D, 0.0D);
            }
        }
    }
}
