package net.minecraft.inventory;

import javax.annotation.Nullable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class Slot
{
    /** The index of the slot in the inventory. */
    private final int slotIndex;

    /** The inventory we want to extract a slot from. */
    public final IInventory inventory;

    /** the id of the slot(also the index in the inventory arraylist) */
    public int slotNumber;

    /** display position of the inventory slot on the screen x axis */
    public int xPos;

    /** display position of the inventory slot on the screen y axis */
    public int yPos;

    public Slot(IInventory inventoryIn, int index, int xPosition, int yPosition)
    {
        this.inventory = inventoryIn;
        this.slotIndex = index;
        this.xPos = xPosition;
        this.yPos = yPosition;
    }

    /**
     * if par2 has more items than par1, onCrafting(item,countIncrease) is called
     */
    public void onSlotChange(ItemStack p_75220_1_, ItemStack p_75220_2_)
    {
        int i = p_75220_2_.getCount() - p_75220_1_.getCount();

        if (i > 0)
        {
            this.onCrafting(p_75220_2_, i);
        }
    }

    /**
     * the itemStack passed in is the output - ie, iron ingots, and pickaxes, not ore and wood. Typically increases an
     * internal count then calls onCrafting(item).
     */
    protected void onCrafting(ItemStack stack, int amount)
    {
    }

    protected void onSwapCraft(int p_190900_1_)
    {
    }

    /**
     * the itemStack passed in is the output - ie, iron ingots, and pickaxes, not ore and wood.
     */
    protected void onCrafting(ItemStack stack)
    {
    }

    public ItemStack onTake(EntityPlayer thePlayer, ItemStack stack)
    {
        this.onSlotChanged();
        return stack;
    }

    /**
     * Check if the stack is allowed to be placed in this slot, used for armor slots as well as furnace fuel.
     */
    public boolean isItemValid(ItemStack stack)
    {
        return true;
    }

    /**
     * Helper fnct to get the stack in the slot.
     */
    public ItemStack getStack()
    {
        return this.inventory.getStackInSlot(this.slotIndex);
    }

    /**
     * Returns if this slot contains a stack.
     */
    public boolean getHasStack()
    {
        return !this.getStack().isEmpty();
    }

    /**
     * Helper method to put a stack in the slot.
     */
    public void putStack(ItemStack stack)
    {
        this.inventory.setInventorySlotContents(this.slotIndex, stack);
        this.onSlotChanged();
    }

    /**
     * Called when the stack in a Slot changes
     */
    public void onSlotChanged()
    {
        this.inventory.markDirty();
    }

    /**
     * Returns the maximum stack size for a given slot (usually the same as getInventoryStackLimit(), but 1 in the case
     * of armor slots)
     */
    public int getSlotStackLimit()
    {
        return this.inventory.getInventoryStackLimit();
    }

    public int getItemStackLimit(ItemStack stack)
    {
        return this.getSlotStackLimit();
    }

    @Nullable
    public String getSlotTexture()
    {
        return null;
    }

    /**
     * Decrease the size of the stack in slot (first int arg) by the amount of the second int arg. Returns the new
     * stack.
     */
    public ItemStack decrStackSize(int amount)
    {
        return this.inventory.decrStackSize(this.slotIndex, amount);
    }

    /**
     * returns true if the slot exists in the given inventory and location
     */
    public boolean isHere(IInventory inv, int slotIn)
    {
        return inv == this.inventory && slotIn == this.slotIndex;
    }

    /**
     * Return whether this slot's stack can be taken from this slot.
     */
    public boolean canTakeStack(EntityPlayer playerIn)
    {
        return true;
    }

    /**
     * Actualy only call when we want to render the white square effect over the slots. Return always True, except for
     * the armor slot of the Donkey/Mule (we can't interact with the Undead and Skeleton horses)
     */
    public boolean isEnabled()
    {
        return true;
    }
}
