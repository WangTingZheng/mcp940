package net.minecraft.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.ILockableContainer;
import net.minecraft.world.LockCode;

public class InventoryLargeChest implements ILockableContainer
{
    /** Name of the chest. */
    private final String name;

    /** Inventory object corresponding to double chest upper part */
    private final ILockableContainer upperChest;

    /** Inventory object corresponding to double chest lower part */
    private final ILockableContainer lowerChest;

    public InventoryLargeChest(String nameIn, ILockableContainer upperChestIn, ILockableContainer lowerChestIn)
    {
        this.name = nameIn;

        if (upperChestIn == null)
        {
            upperChestIn = lowerChestIn;
        }

        if (lowerChestIn == null)
        {
            lowerChestIn = upperChestIn;
        }

        this.upperChest = upperChestIn;
        this.lowerChest = lowerChestIn;

        if (upperChestIn.isLocked())
        {
            lowerChestIn.setLockCode(upperChestIn.getLockCode());
        }
        else if (lowerChestIn.isLocked())
        {
            upperChestIn.setLockCode(lowerChestIn.getLockCode());
        }
    }

    /**
     * Returns the number of slots in the inventory.
     */
    public int getSizeInventory()
    {
        return this.upperChest.getSizeInventory() + this.lowerChest.getSizeInventory();
    }

    public boolean isEmpty()
    {
        return this.upperChest.isEmpty() && this.lowerChest.isEmpty();
    }

    /**
     * Return whether the given inventory is part of this large chest.
     */
    public boolean isPartOfLargeChest(IInventory inventoryIn)
    {
        return this.upperChest == inventoryIn || this.lowerChest == inventoryIn;
    }

    /**
     * Get the name of this object. For players this returns their username
     */
    public String getName()
    {
        if (this.upperChest.hasCustomName())
        {
            return this.upperChest.getName();
        }
        else
        {
            return this.lowerChest.hasCustomName() ? this.lowerChest.getName() : this.name;
        }
    }

    /**
     * Returns true if this thing is named
     */
    public boolean hasCustomName()
    {
        return this.upperChest.hasCustomName() || this.lowerChest.hasCustomName();
    }

    /**
     * Get the formatted ChatComponent that will be used for the sender's username in chat
     */
    public ITextComponent getDisplayName()
    {
        return (ITextComponent)(this.hasCustomName() ? new TextComponentString(this.getName()) : new TextComponentTranslation(this.getName(), new Object[0]));
    }

    /**
     * Returns the stack in the given slot.
     */
    public ItemStack getStackInSlot(int index)
    {
        return index >= this.upperChest.getSizeInventory() ? this.lowerChest.getStackInSlot(index - this.upperChest.getSizeInventory()) : this.upperChest.getStackInSlot(index);
    }

    /**
     * Removes up to a specified number of items from an inventory slot and returns them in a new stack.
     */
    public ItemStack decrStackSize(int index, int count)
    {
        return index >= this.upperChest.getSizeInventory() ? this.lowerChest.decrStackSize(index - this.upperChest.getSizeInventory(), count) : this.upperChest.decrStackSize(index, count);
    }

    /**
     * Removes a stack from the given slot and returns it.
     */
    public ItemStack removeStackFromSlot(int index)
    {
        return index >= this.upperChest.getSizeInventory() ? this.lowerChest.removeStackFromSlot(index - this.upperChest.getSizeInventory()) : this.upperChest.removeStackFromSlot(index);
    }

    /**
     * Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections).
     */
    public void setInventorySlotContents(int index, ItemStack stack)
    {
        if (index >= this.upperChest.getSizeInventory())
        {
            this.lowerChest.setInventorySlotContents(index - this.upperChest.getSizeInventory(), stack);
        }
        else
        {
            this.upperChest.setInventorySlotContents(index, stack);
        }
    }

    /**
     * Returns the maximum stack size for a inventory slot. Seems to always be 64, possibly will be extended.
     */
    public int getInventoryStackLimit()
    {
        return this.upperChest.getInventoryStackLimit();
    }

    /**
     * For tile entities, ensures the chunk containing the tile entity is saved to disk later - the game won't think it
     * hasn't changed and skip it.
     */
    public void markDirty()
    {
        this.upperChest.markDirty();
        this.lowerChest.markDirty();
    }

    /**
     * Don't rename this method to canInteractWith due to conflicts with Container
     */
    public boolean isUsableByPlayer(EntityPlayer player)
    {
        return this.upperChest.isUsableByPlayer(player) && this.lowerChest.isUsableByPlayer(player);
    }

    public void openInventory(EntityPlayer player)
    {
        this.upperChest.openInventory(player);
        this.lowerChest.openInventory(player);
    }

    public void closeInventory(EntityPlayer player)
    {
        this.upperChest.closeInventory(player);
        this.lowerChest.closeInventory(player);
    }

    /**
     * Returns true if automation is allowed to insert the given stack (ignoring stack size) into the given slot. For
     * guis use Slot.isItemValid
     */
    public boolean isItemValidForSlot(int index, ItemStack stack)
    {
        return true;
    }

    public int getField(int id)
    {
        return 0;
    }

    public void setField(int id, int value)
    {
    }

    public int getFieldCount()
    {
        return 0;
    }

    public boolean isLocked()
    {
        return this.upperChest.isLocked() || this.lowerChest.isLocked();
    }

    public void setLockCode(LockCode code)
    {
        this.upperChest.setLockCode(code);
        this.lowerChest.setLockCode(code);
    }

    public LockCode getLockCode()
    {
        return this.upperChest.getLockCode();
    }

    public String getGuiID()
    {
        return this.upperChest.getGuiID();
    }

    public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn)
    {
        return new ContainerChest(playerInventory, this, playerIn);
    }

    public void clear()
    {
        this.upperChest.clear();
        this.lowerChest.clear();
    }
}
