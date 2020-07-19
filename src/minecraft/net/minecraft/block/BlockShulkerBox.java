package net.minecraft.block;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityShulkerBox;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Mirror;
import net.minecraft.util.NonNullList;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockShulkerBox extends BlockContainer
{
    public static final PropertyEnum<EnumFacing> FACING = PropertyDirection.create("facing");
    private final EnumDyeColor color;

    public BlockShulkerBox(EnumDyeColor colorIn)
    {
        super(Material.ROCK, MapColor.AIR);
        this.color = colorIn;
        this.setCreativeTab(CreativeTabs.DECORATIONS);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.UP));
    }

    /**
     * Returns a new instance of a block's tile entity class. Called on placing the block.
     */
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new TileEntityShulkerBox(this.color);
    }

    /**
     * Used to determine ambient occlusion and culling when rebuilding chunks for render
     */
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    public boolean causesSuffocation(IBlockState state)
    {
        return true;
    }

    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    public boolean hasCustomBreakingProgress(IBlockState state)
    {
        return true;
    }

    /**
     * The type of render function called. MODEL for mixed tesr and static model, MODELBLOCK_ANIMATED for TESR-only,
     * LIQUID for vanilla liquids, INVISIBLE to skip all rendering
     */
    public EnumBlockRenderType getRenderType(IBlockState state)
    {
        return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
    }

    /**
     * Called when the block is right clicked by a player.
     */
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if (worldIn.isRemote)
        {
            return true;
        }
        else if (playerIn.isSpectator())
        {
            return true;
        }
        else
        {
            TileEntity tileentity = worldIn.getTileEntity(pos);

            if (tileentity instanceof TileEntityShulkerBox)
            {
                EnumFacing enumfacing = (EnumFacing)state.getValue(FACING);
                boolean flag;

                if (((TileEntityShulkerBox)tileentity).getAnimationStatus() == TileEntityShulkerBox.AnimationStatus.CLOSED)
                {
                    AxisAlignedBB axisalignedbb = FULL_BLOCK_AABB.expand((double)(0.5F * (float)enumfacing.getFrontOffsetX()), (double)(0.5F * (float)enumfacing.getFrontOffsetY()), (double)(0.5F * (float)enumfacing.getFrontOffsetZ())).contract((double)enumfacing.getFrontOffsetX(), (double)enumfacing.getFrontOffsetY(), (double)enumfacing.getFrontOffsetZ());
                    flag = !worldIn.collidesWithAnyBlock(axisalignedbb.offset(pos.offset(enumfacing)));
                }
                else
                {
                    flag = true;
                }

                if (flag)
                {
                    playerIn.addStat(StatList.OPEN_SHULKER_BOX);
                    playerIn.displayGUIChest((IInventory)tileentity);
                }

                return true;
            }
            else
            {
                return false;
            }
        }
    }

    /**
     * Called by ItemBlocks just before a block is actually set in the world, to allow for adjustments to the
     * IBlockstate
     */
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        return this.getDefaultState().withProperty(FACING, facing);
    }

    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {FACING});
    }

    /**
     * Convert the BlockState into the correct metadata value
     */
    public int getMetaFromState(IBlockState state)
    {
        return ((EnumFacing)state.getValue(FACING)).getIndex();
    }

    /**
     * Convert the given metadata into a BlockState for this Block
     */
    public IBlockState getStateFromMeta(int meta)
    {
        EnumFacing enumfacing = EnumFacing.getFront(meta);
        return this.getDefaultState().withProperty(FACING, enumfacing);
    }

    /**
     * Called before the Block is set to air in the world. Called regardless of if the player's tool can actually
     * collect this block
     */
    public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player)
    {
        if (worldIn.getTileEntity(pos) instanceof TileEntityShulkerBox)
        {
            TileEntityShulkerBox tileentityshulkerbox = (TileEntityShulkerBox)worldIn.getTileEntity(pos);
            tileentityshulkerbox.setDestroyedByCreativePlayer(player.capabilities.isCreativeMode);
            tileentityshulkerbox.fillWithLoot(player);
        }
    }

    /**
     * Spawns this Block's drops into the World as EntityItems.
     */
    public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state, float chance, int fortune)
    {
    }

    /**
     * Called by ItemBlocks after a block is set in the world, to allow post-place logic
     */
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
    {
        if (stack.hasDisplayName())
        {
            TileEntity tileentity = worldIn.getTileEntity(pos);

            if (tileentity instanceof TileEntityShulkerBox)
            {
                ((TileEntityShulkerBox)tileentity).setCustomName(stack.getDisplayName());
            }
        }
    }

    /**
     * Called serverside after this block is replaced with another in Chunk, but before the Tile Entity is updated
     */
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
    {
        TileEntity tileentity = worldIn.getTileEntity(pos);

        if (tileentity instanceof TileEntityShulkerBox)
        {
            TileEntityShulkerBox tileentityshulkerbox = (TileEntityShulkerBox)tileentity;

            if (!tileentityshulkerbox.isCleared() && tileentityshulkerbox.shouldDrop())
            {
                ItemStack itemstack = new ItemStack(Item.getItemFromBlock(this));
                NBTTagCompound nbttagcompound = new NBTTagCompound();
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound.setTag("BlockEntityTag", ((TileEntityShulkerBox)tileentity).saveToNbt(nbttagcompound1));
                itemstack.setTagCompound(nbttagcompound);

                if (tileentityshulkerbox.hasCustomName())
                {
                    itemstack.setStackDisplayName(tileentityshulkerbox.getName());
                    tileentityshulkerbox.setCustomName("");
                }

                spawnAsEntity(worldIn, pos, itemstack);
            }

            worldIn.updateComparatorOutputLevel(pos, state.getBlock());
        }

        super.breakBlock(worldIn, pos, state);
    }

    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, ITooltipFlag advanced)
    {
        super.addInformation(stack, player, tooltip, advanced);
        NBTTagCompound nbttagcompound = stack.getTagCompound();

        if (nbttagcompound != null && nbttagcompound.hasKey("BlockEntityTag", 10))
        {
            NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("BlockEntityTag");

            if (nbttagcompound1.hasKey("LootTable", 8))
            {
                tooltip.add("???????");
            }

            if (nbttagcompound1.hasKey("Items", 9))
            {
                NonNullList<ItemStack> nonnulllist = NonNullList.<ItemStack>withSize(27, ItemStack.EMPTY);
                ItemStackHelper.loadAllItems(nbttagcompound1, nonnulllist);
                int i = 0;
                int j = 0;

                for (ItemStack itemstack : nonnulllist)
                {
                    if (!itemstack.isEmpty())
                    {
                        ++j;

                        if (i <= 4)
                        {
                            ++i;
                            tooltip.add(String.format("%s x%d", itemstack.getDisplayName(), itemstack.getCount()));
                        }
                    }
                }

                if (j - i > 0)
                {
                    tooltip.add(String.format(TextFormatting.ITALIC + I18n.translateToLocal("container.shulkerBox.more"), j - i));
                }
            }
        }
    }

    public EnumPushReaction getMobilityFlag(IBlockState state)
    {
        return EnumPushReaction.DESTROY;
    }

    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        TileEntity tileentity = source.getTileEntity(pos);
        return tileentity instanceof TileEntityShulkerBox ? ((TileEntityShulkerBox)tileentity).getBoundingBox(state) : FULL_BLOCK_AABB;
    }

    public boolean hasComparatorInputOverride(IBlockState state)
    {
        return true;
    }

    public int getComparatorInputOverride(IBlockState blockState, World worldIn, BlockPos pos)
    {
        return Container.calcRedstoneFromInventory((IInventory)worldIn.getTileEntity(pos));
    }

    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state)
    {
        ItemStack itemstack = super.getItem(worldIn, pos, state);
        TileEntityShulkerBox tileentityshulkerbox = (TileEntityShulkerBox)worldIn.getTileEntity(pos);
        NBTTagCompound nbttagcompound = tileentityshulkerbox.saveToNbt(new NBTTagCompound());

        if (!nbttagcompound.hasNoTags())
        {
            itemstack.setTagInfo("BlockEntityTag", nbttagcompound);
        }

        return itemstack;
    }

    public static EnumDyeColor getColorFromItem(Item itemIn)
    {
        return getColorFromBlock(Block.getBlockFromItem(itemIn));
    }

    public static EnumDyeColor getColorFromBlock(Block blockIn)
    {
        return blockIn instanceof BlockShulkerBox ? ((BlockShulkerBox)blockIn).getColor() : EnumDyeColor.PURPLE;
    }

    public static Block getBlockByColor(EnumDyeColor colorIn)
    {
        switch (colorIn)
        {
            case WHITE:
                return Blocks.WHITE_SHULKER_BOX;

            case ORANGE:
                return Blocks.ORANGE_SHULKER_BOX;

            case MAGENTA:
                return Blocks.MAGENTA_SHULKER_BOX;

            case LIGHT_BLUE:
                return Blocks.LIGHT_BLUE_SHULKER_BOX;

            case YELLOW:
                return Blocks.YELLOW_SHULKER_BOX;

            case LIME:
                return Blocks.LIME_SHULKER_BOX;

            case PINK:
                return Blocks.PINK_SHULKER_BOX;

            case GRAY:
                return Blocks.GRAY_SHULKER_BOX;

            case SILVER:
                return Blocks.SILVER_SHULKER_BOX;

            case CYAN:
                return Blocks.CYAN_SHULKER_BOX;

            case PURPLE:
            default:
                return Blocks.PURPLE_SHULKER_BOX;

            case BLUE:
                return Blocks.BLUE_SHULKER_BOX;

            case BROWN:
                return Blocks.BROWN_SHULKER_BOX;

            case GREEN:
                return Blocks.GREEN_SHULKER_BOX;

            case RED:
                return Blocks.RED_SHULKER_BOX;

            case BLACK:
                return Blocks.BLACK_SHULKER_BOX;
        }
    }

    public EnumDyeColor getColor()
    {
        return this.color;
    }

    public static ItemStack getColoredItemStack(EnumDyeColor colorIn)
    {
        return new ItemStack(getBlockByColor(colorIn));
    }

    /**
     * Returns the blockstate with the given rotation from the passed blockstate. If inapplicable, returns the passed
     * blockstate.
     */
    public IBlockState withRotation(IBlockState state, Rotation rot)
    {
        return state.withProperty(FACING, rot.rotate((EnumFacing)state.getValue(FACING)));
    }

    /**
     * Returns the blockstate with the given mirror of the passed blockstate. If inapplicable, returns the passed
     * blockstate.
     */
    public IBlockState withMirror(IBlockState state, Mirror mirrorIn)
    {
        return state.withRotation(mirrorIn.toRotation((EnumFacing)state.getValue(FACING)));
    }

    public BlockFaceShape getBlockFaceShape(IBlockAccess p_193383_1_, IBlockState p_193383_2_, BlockPos p_193383_3_, EnumFacing p_193383_4_)
    {
        p_193383_2_ = this.getActualState(p_193383_2_, p_193383_1_, p_193383_3_);
        EnumFacing enumfacing = (EnumFacing)p_193383_2_.getValue(FACING);
        TileEntityShulkerBox.AnimationStatus tileentityshulkerbox$animationstatus = ((TileEntityShulkerBox)p_193383_1_.getTileEntity(p_193383_3_)).getAnimationStatus();
        return tileentityshulkerbox$animationstatus != TileEntityShulkerBox.AnimationStatus.CLOSED && (tileentityshulkerbox$animationstatus != TileEntityShulkerBox.AnimationStatus.OPENED || enumfacing != p_193383_4_.getOpposite() && enumfacing != p_193383_4_) ? BlockFaceShape.UNDEFINED : BlockFaceShape.SOLID;
    }
}
