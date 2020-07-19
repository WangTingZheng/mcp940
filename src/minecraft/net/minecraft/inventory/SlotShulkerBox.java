package net.minecraft.inventory;

import net.minecraft.block.Block;
import net.minecraft.block.BlockShulkerBox;
import net.minecraft.item.ItemStack;

public class SlotShulkerBox extends Slot
{
    public SlotShulkerBox(IInventory p_i47265_1_, int slotIndexIn, int xPosition, int yPosition)
    {
        super(p_i47265_1_, slotIndexIn, xPosition, yPosition);
    }

    /**
     * Check if the stack is allowed to be placed in this slot, used for armor slots as well as furnace fuel.
     */
    public boolean isItemValid(ItemStack stack)
    {
        return !(Block.getBlockFromItem(stack.getItem()) instanceof BlockShulkerBox);
    }
}
