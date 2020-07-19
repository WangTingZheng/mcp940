package net.minecraft.tileentity;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockShulkerBox;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerShulkerBox;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.datafix.FixTypes;
import net.minecraft.util.datafix.walkers.ItemStackDataLists;
import net.minecraft.util.math.AxisAlignedBB;

public class TileEntityShulkerBox extends TileEntityLockableLoot implements ITickable, ISidedInventory
{
    private static final int[] SLOTS = new int[27];
    private NonNullList<ItemStack> items;
    private boolean hasBeenCleared;
    private int openCount;
    private TileEntityShulkerBox.AnimationStatus animationStatus;
    private float progress;
    private float progressOld;
    private EnumDyeColor color;
    private boolean destroyedByCreativePlayer;

    public TileEntityShulkerBox()
    {
        this((EnumDyeColor)null);
    }

    public TileEntityShulkerBox(@Nullable EnumDyeColor colorIn)
    {
        this.items = NonNullList.<ItemStack>withSize(27, ItemStack.EMPTY);
        this.animationStatus = TileEntityShulkerBox.AnimationStatus.CLOSED;
        this.color = colorIn;
    }

    /**
     * Like the old updateEntity(), except more generic.
     */
    public void update()
    {
        this.updateAnimation();

        if (this.animationStatus == TileEntityShulkerBox.AnimationStatus.OPENING || this.animationStatus == TileEntityShulkerBox.AnimationStatus.CLOSING)
        {
            this.moveCollidedEntities();
        }
    }

    protected void updateAnimation()
    {
        this.progressOld = this.progress;

        switch (this.animationStatus)
        {
            case CLOSED:
                this.progress = 0.0F;
                break;

            case OPENING:
                this.progress += 0.1F;

                if (this.progress >= 1.0F)
                {
                    this.moveCollidedEntities();
                    this.animationStatus = TileEntityShulkerBox.AnimationStatus.OPENED;
                    this.progress = 1.0F;
                }

                break;

            case CLOSING:
                this.progress -= 0.1F;

                if (this.progress <= 0.0F)
                {
                    this.animationStatus = TileEntityShulkerBox.AnimationStatus.CLOSED;
                    this.progress = 0.0F;
                }

                break;

            case OPENED:
                this.progress = 1.0F;
        }
    }

    public TileEntityShulkerBox.AnimationStatus getAnimationStatus()
    {
        return this.animationStatus;
    }

    public AxisAlignedBB getBoundingBox(IBlockState p_190584_1_)
    {
        return this.getBoundingBox((EnumFacing)p_190584_1_.getValue(BlockShulkerBox.FACING));
    }

    public AxisAlignedBB getBoundingBox(EnumFacing p_190587_1_)
    {
        return Block.FULL_BLOCK_AABB.expand((double)(0.5F * this.getProgress(1.0F) * (float)p_190587_1_.getFrontOffsetX()), (double)(0.5F * this.getProgress(1.0F) * (float)p_190587_1_.getFrontOffsetY()), (double)(0.5F * this.getProgress(1.0F) * (float)p_190587_1_.getFrontOffsetZ()));
    }

    private AxisAlignedBB getTopBoundingBox(EnumFacing p_190588_1_)
    {
        EnumFacing enumfacing = p_190588_1_.getOpposite();
        return this.getBoundingBox(p_190588_1_).contract((double)enumfacing.getFrontOffsetX(), (double)enumfacing.getFrontOffsetY(), (double)enumfacing.getFrontOffsetZ());
    }

    private void moveCollidedEntities()
    {
        IBlockState iblockstate = this.world.getBlockState(this.getPos());

        if (iblockstate.getBlock() instanceof BlockShulkerBox)
        {
            EnumFacing enumfacing = (EnumFacing)iblockstate.getValue(BlockShulkerBox.FACING);
            AxisAlignedBB axisalignedbb = this.getTopBoundingBox(enumfacing).offset(this.pos);
            List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity((Entity)null, axisalignedbb);

            if (!list.isEmpty())
            {
                for (int i = 0; i < list.size(); ++i)
                {
                    Entity entity = list.get(i);

                    if (entity.getPushReaction() != EnumPushReaction.IGNORE)
                    {
                        double d0 = 0.0D;
                        double d1 = 0.0D;
                        double d2 = 0.0D;
                        AxisAlignedBB axisalignedbb1 = entity.getEntityBoundingBox();

                        switch (enumfacing.getAxis())
                        {
                            case X:
                                if (enumfacing.getAxisDirection() == EnumFacing.AxisDirection.POSITIVE)
                                {
                                    d0 = axisalignedbb.maxX - axisalignedbb1.minX;
                                }
                                else
                                {
                                    d0 = axisalignedbb1.maxX - axisalignedbb.minX;
                                }

                                d0 = d0 + 0.01D;
                                break;

                            case Y:
                                if (enumfacing.getAxisDirection() == EnumFacing.AxisDirection.POSITIVE)
                                {
                                    d1 = axisalignedbb.maxY - axisalignedbb1.minY;
                                }
                                else
                                {
                                    d1 = axisalignedbb1.maxY - axisalignedbb.minY;
                                }

                                d1 = d1 + 0.01D;
                                break;

                            case Z:
                                if (enumfacing.getAxisDirection() == EnumFacing.AxisDirection.POSITIVE)
                                {
                                    d2 = axisalignedbb.maxZ - axisalignedbb1.minZ;
                                }
                                else
                                {
                                    d2 = axisalignedbb1.maxZ - axisalignedbb.minZ;
                                }

                                d2 = d2 + 0.01D;
                        }

                        entity.move(MoverType.SHULKER_BOX, d0 * (double)enumfacing.getFrontOffsetX(), d1 * (double)enumfacing.getFrontOffsetY(), d2 * (double)enumfacing.getFrontOffsetZ());
                    }
                }
            }
        }
    }

    /**
     * Returns the number of slots in the inventory.
     */
    public int getSizeInventory()
    {
        return this.items.size();
    }

    /**
     * Returns the maximum stack size for a inventory slot. Seems to always be 64, possibly will be extended.
     */
    public int getInventoryStackLimit()
    {
        return 64;
    }

    public boolean receiveClientEvent(int id, int type)
    {
        if (id == 1)
        {
            this.openCount = type;

            if (type == 0)
            {
                this.animationStatus = TileEntityShulkerBox.AnimationStatus.CLOSING;
            }

            if (type == 1)
            {
                this.animationStatus = TileEntityShulkerBox.AnimationStatus.OPENING;
            }

            return true;
        }
        else
        {
            return super.receiveClientEvent(id, type);
        }
    }

    public void openInventory(EntityPlayer player)
    {
        if (!player.isSpectator())
        {
            if (this.openCount < 0)
            {
                this.openCount = 0;
            }

            ++this.openCount;
            this.world.addBlockEvent(this.pos, this.getBlockType(), 1, this.openCount);

            if (this.openCount == 1)
            {
                this.world.playSound((EntityPlayer)null, this.pos, SoundEvents.BLOCK_SHULKER_BOX_OPEN, SoundCategory.BLOCKS, 0.5F, this.world.rand.nextFloat() * 0.1F + 0.9F);
            }
        }
    }

    public void closeInventory(EntityPlayer player)
    {
        if (!player.isSpectator())
        {
            --this.openCount;
            this.world.addBlockEvent(this.pos, this.getBlockType(), 1, this.openCount);

            if (this.openCount <= 0)
            {
                this.world.playSound((EntityPlayer)null, this.pos, SoundEvents.BLOCK_SHULKER_BOX_CLOSE, SoundCategory.BLOCKS, 0.5F, this.world.rand.nextFloat() * 0.1F + 0.9F);
            }
        }
    }

    public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn)
    {
        return new ContainerShulkerBox(playerInventory, this, playerIn);
    }

    public String getGuiID()
    {
        return "minecraft:shulker_box";
    }

    /**
     * Get the name of this object. For players this returns their username
     */
    public String getName()
    {
        return this.hasCustomName() ? this.customName : "container.shulkerBox";
    }

    public static void registerFixesShulkerBox(DataFixer fixer)
    {
        fixer.registerWalker(FixTypes.BLOCK_ENTITY, new ItemStackDataLists(TileEntityShulkerBox.class, new String[] {"Items"}));
    }

    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        this.loadFromNbt(compound);
    }

    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        return this.saveToNbt(compound);
    }

    public void loadFromNbt(NBTTagCompound compound)
    {
        this.items = NonNullList.<ItemStack>withSize(this.getSizeInventory(), ItemStack.EMPTY);

        if (!this.checkLootAndRead(compound) && compound.hasKey("Items", 9))
        {
            ItemStackHelper.loadAllItems(compound, this.items);
        }

        if (compound.hasKey("CustomName", 8))
        {
            this.customName = compound.getString("CustomName");
        }
    }

    public NBTTagCompound saveToNbt(NBTTagCompound compound)
    {
        if (!this.checkLootAndWrite(compound))
        {
            ItemStackHelper.saveAllItems(compound, this.items, false);
        }

        if (this.hasCustomName())
        {
            compound.setString("CustomName", this.customName);
        }

        if (!compound.hasKey("Lock") && this.isLocked())
        {
            this.getLockCode().toNBT(compound);
        }

        return compound;
    }

    protected NonNullList<ItemStack> getItems()
    {
        return this.items;
    }

    public boolean isEmpty()
    {
        for (ItemStack itemstack : this.items)
        {
            if (!itemstack.isEmpty())
            {
                return false;
            }
        }

        return true;
    }

    public int[] getSlotsForFace(EnumFacing side)
    {
        return SLOTS;
    }

    /**
     * Returns true if automation can insert the given item in the given slot from the given side.
     */
    public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction)
    {
        return !(Block.getBlockFromItem(itemStackIn.getItem()) instanceof BlockShulkerBox);
    }

    /**
     * Returns true if automation can extract the given item in the given slot from the given side.
     */
    public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction)
    {
        return true;
    }

    public void clear()
    {
        this.hasBeenCleared = true;
        super.clear();
    }

    public boolean isCleared()
    {
        return this.hasBeenCleared;
    }

    public float getProgress(float p_190585_1_)
    {
        return this.progressOld + (this.progress - this.progressOld) * p_190585_1_;
    }

    public EnumDyeColor getColor()
    {
        if (this.color == null)
        {
            this.color = BlockShulkerBox.getColorFromBlock(this.getBlockType());
        }

        return this.color;
    }

    @Nullable
    public SPacketUpdateTileEntity getUpdatePacket()
    {
        return new SPacketUpdateTileEntity(this.pos, 10, this.getUpdateTag());
    }

    public boolean isDestroyedByCreativePlayer()
    {
        return this.destroyedByCreativePlayer;
    }

    public void setDestroyedByCreativePlayer(boolean p_190579_1_)
    {
        this.destroyedByCreativePlayer = p_190579_1_;
    }

    public boolean shouldDrop()
    {
        return !this.isDestroyedByCreativePlayer() || !this.isEmpty() || this.hasCustomName() || this.lootTable != null;
    }

    static
    {
        for (int i = 0; i < SLOTS.length; SLOTS[i] = i++)
        {
            ;
        }
    }

    public static enum AnimationStatus
    {
        CLOSED,
        OPENING,
        OPENED,
        CLOSING;
    }
}
