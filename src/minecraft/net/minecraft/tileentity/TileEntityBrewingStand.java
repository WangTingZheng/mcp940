package net.minecraft.tileentity;

import java.util.Arrays;
import net.minecraft.block.BlockBrewingStand;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerBrewingStand;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionHelper;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.datafix.FixTypes;
import net.minecraft.util.datafix.walkers.ItemStackDataLists;
import net.minecraft.util.math.BlockPos;

public class TileEntityBrewingStand extends TileEntityLockable implements ITickable, ISidedInventory
{
    /** an array of the input slot indices */
    private static final int[] SLOTS_FOR_UP = new int[] {3};
    private static final int[] SLOTS_FOR_DOWN = new int[] {0, 1, 2, 3};

    /** an array of the output slot indices */
    private static final int[] OUTPUT_SLOTS = new int[] {0, 1, 2, 4};
    private NonNullList<ItemStack> brewingItemStacks = NonNullList.<ItemStack>withSize(5, ItemStack.EMPTY);
    private int brewTime;

    /**
     * an integer with each bit specifying whether that slot of the stand contains a potion
     */
    private boolean[] filledSlots;

    /**
     * used to check if the current ingredient has been removed from the brewing stand during brewing
     */
    private Item ingredientID;
    private String customName;
    private int fuel;

    /**
     * Get the name of this object. For players this returns their username
     */
    public String getName()
    {
        return this.hasCustomName() ? this.customName : "container.brewing";
    }

    /**
     * Returns true if this thing is named
     */
    public boolean hasCustomName()
    {
        return this.customName != null && !this.customName.isEmpty();
    }

    public void setName(String name)
    {
        this.customName = name;
    }

    /**
     * Returns the number of slots in the inventory.
     */
    public int getSizeInventory()
    {
        return this.brewingItemStacks.size();
    }

    public boolean isEmpty()
    {
        for (ItemStack itemstack : this.brewingItemStacks)
        {
            if (!itemstack.isEmpty())
            {
                return false;
            }
        }

        return true;
    }

    /**
     * Like the old updateEntity(), except more generic.
     */
    public void update()
    {
        ItemStack itemstack = this.brewingItemStacks.get(4);

        if (this.fuel <= 0 && itemstack.getItem() == Items.BLAZE_POWDER)
        {
            this.fuel = 20;
            itemstack.shrink(1);
            this.markDirty();
        }

        boolean flag = this.canBrew();
        boolean flag1 = this.brewTime > 0;
        ItemStack itemstack1 = this.brewingItemStacks.get(3);

        if (flag1)
        {
            --this.brewTime;
            boolean flag2 = this.brewTime == 0;

            if (flag2 && flag)
            {
                this.brewPotions();
                this.markDirty();
            }
            else if (!flag)
            {
                this.brewTime = 0;
                this.markDirty();
            }
            else if (this.ingredientID != itemstack1.getItem())
            {
                this.brewTime = 0;
                this.markDirty();
            }
        }
        else if (flag && this.fuel > 0)
        {
            --this.fuel;
            this.brewTime = 400;
            this.ingredientID = itemstack1.getItem();
            this.markDirty();
        }

        if (!this.world.isRemote)
        {
            boolean[] aboolean = this.createFilledSlotsArray();

            if (!Arrays.equals(aboolean, this.filledSlots))
            {
                this.filledSlots = aboolean;
                IBlockState iblockstate = this.world.getBlockState(this.getPos());

                if (!(iblockstate.getBlock() instanceof BlockBrewingStand))
                {
                    return;
                }

                for (int i = 0; i < BlockBrewingStand.HAS_BOTTLE.length; ++i)
                {
                    iblockstate = iblockstate.withProperty(BlockBrewingStand.HAS_BOTTLE[i], Boolean.valueOf(aboolean[i]));
                }

                this.world.setBlockState(this.pos, iblockstate, 2);
            }
        }
    }

    /**
     * Creates an array of boolean values, each value represents a potion input slot, value is true if the slot is not
     * null.
     */
    public boolean[] createFilledSlotsArray()
    {
        boolean[] aboolean = new boolean[3];

        for (int i = 0; i < 3; ++i)
        {
            if (!((ItemStack)this.brewingItemStacks.get(i)).isEmpty())
            {
                aboolean[i] = true;
            }
        }

        return aboolean;
    }

    private boolean canBrew()
    {
        ItemStack itemstack = this.brewingItemStacks.get(3);

        if (itemstack.isEmpty())
        {
            return false;
        }
        else if (!PotionHelper.isReagent(itemstack))
        {
            return false;
        }
        else
        {
            for (int i = 0; i < 3; ++i)
            {
                ItemStack itemstack1 = this.brewingItemStacks.get(i);

                if (!itemstack1.isEmpty() && PotionHelper.hasConversions(itemstack1, itemstack))
                {
                    return true;
                }
            }

            return false;
        }
    }

    private void brewPotions()
    {
        ItemStack itemstack = this.brewingItemStacks.get(3);

        for (int i = 0; i < 3; ++i)
        {
            this.brewingItemStacks.set(i, PotionHelper.doReaction(itemstack, this.brewingItemStacks.get(i)));
        }

        itemstack.shrink(1);
        BlockPos blockpos = this.getPos();

        if (itemstack.getItem().hasContainerItem())
        {
            ItemStack itemstack1 = new ItemStack(itemstack.getItem().getContainerItem());

            if (itemstack.isEmpty())
            {
                itemstack = itemstack1;
            }
            else
            {
                InventoryHelper.spawnItemStack(this.world, (double)blockpos.getX(), (double)blockpos.getY(), (double)blockpos.getZ(), itemstack1);
            }
        }

        this.brewingItemStacks.set(3, itemstack);
        this.world.playEvent(1035, blockpos, 0);
    }

    public static void registerFixesBrewingStand(DataFixer fixer)
    {
        fixer.registerWalker(FixTypes.BLOCK_ENTITY, new ItemStackDataLists(TileEntityBrewingStand.class, new String[] {"Items"}));
    }

    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        this.brewingItemStacks = NonNullList.<ItemStack>withSize(this.getSizeInventory(), ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(compound, this.brewingItemStacks);
        this.brewTime = compound.getShort("BrewTime");

        if (compound.hasKey("CustomName", 8))
        {
            this.customName = compound.getString("CustomName");
        }

        this.fuel = compound.getByte("Fuel");
    }

    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        compound.setShort("BrewTime", (short)this.brewTime);
        ItemStackHelper.saveAllItems(compound, this.brewingItemStacks);

        if (this.hasCustomName())
        {
            compound.setString("CustomName", this.customName);
        }

        compound.setByte("Fuel", (byte)this.fuel);
        return compound;
    }

    /**
     * Returns the stack in the given slot.
     */
    public ItemStack getStackInSlot(int index)
    {
        return index >= 0 && index < this.brewingItemStacks.size() ? (ItemStack)this.brewingItemStacks.get(index) : ItemStack.EMPTY;
    }

    /**
     * Removes up to a specified number of items from an inventory slot and returns them in a new stack.
     */
    public ItemStack decrStackSize(int index, int count)
    {
        return ItemStackHelper.getAndSplit(this.brewingItemStacks, index, count);
    }

    /**
     * Removes a stack from the given slot and returns it.
     */
    public ItemStack removeStackFromSlot(int index)
    {
        return ItemStackHelper.getAndRemove(this.brewingItemStacks, index);
    }

    /**
     * Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections).
     */
    public void setInventorySlotContents(int index, ItemStack stack)
    {
        if (index >= 0 && index < this.brewingItemStacks.size())
        {
            this.brewingItemStacks.set(index, stack);
        }
    }

    /**
     * Returns the maximum stack size for a inventory slot. Seems to always be 64, possibly will be extended.
     */
    public int getInventoryStackLimit()
    {
        return 64;
    }

    /**
     * Don't rename this method to canInteractWith due to conflicts with Container
     */
    public boolean isUsableByPlayer(EntityPlayer player)
    {
        if (this.world.getTileEntity(this.pos) != this)
        {
            return false;
        }
        else
        {
            return player.getDistanceSq((double)this.pos.getX() + 0.5D, (double)this.pos.getY() + 0.5D, (double)this.pos.getZ() + 0.5D) <= 64.0D;
        }
    }

    public void openInventory(EntityPlayer player)
    {
    }

    public void closeInventory(EntityPlayer player)
    {
    }

    /**
     * Returns true if automation is allowed to insert the given stack (ignoring stack size) into the given slot. For
     * guis use Slot.isItemValid
     */
    public boolean isItemValidForSlot(int index, ItemStack stack)
    {
        if (index == 3)
        {
            return PotionHelper.isReagent(stack);
        }
        else
        {
            Item item = stack.getItem();

            if (index == 4)
            {
                return item == Items.BLAZE_POWDER;
            }
            else
            {
                return (item == Items.POTIONITEM || item == Items.SPLASH_POTION || item == Items.LINGERING_POTION || item == Items.GLASS_BOTTLE) && this.getStackInSlot(index).isEmpty();
            }
        }
    }

    public int[] getSlotsForFace(EnumFacing side)
    {
        if (side == EnumFacing.UP)
        {
            return SLOTS_FOR_UP;
        }
        else
        {
            return side == EnumFacing.DOWN ? SLOTS_FOR_DOWN : OUTPUT_SLOTS;
        }
    }

    /**
     * Returns true if automation can insert the given item in the given slot from the given side.
     */
    public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction)
    {
        return this.isItemValidForSlot(index, itemStackIn);
    }

    /**
     * Returns true if automation can extract the given item in the given slot from the given side.
     */
    public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction)
    {
        if (index == 3)
        {
            return stack.getItem() == Items.GLASS_BOTTLE;
        }
        else
        {
            return true;
        }
    }

    public String getGuiID()
    {
        return "minecraft:brewing_stand";
    }

    public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn)
    {
        return new ContainerBrewingStand(playerInventory, this);
    }

    public int getField(int id)
    {
        switch (id)
        {
            case 0:
                return this.brewTime;

            case 1:
                return this.fuel;

            default:
                return 0;
        }
    }

    public void setField(int id, int value)
    {
        switch (id)
        {
            case 0:
                this.brewTime = value;
                break;

            case 1:
                this.fuel = value;
        }
    }

    public int getFieldCount()
    {
        return 2;
    }

    public void clear()
    {
        this.brewingItemStacks.clear();
    }
}
