package net.minecraft.inventory;

import net.minecraft.util.text.ITextComponent;

public class ContainerHorseChest extends InventoryBasic
{
    public ContainerHorseChest(String inventoryTitle, int slotCount)
    {
        super(inventoryTitle, false, slotCount);
    }

    public ContainerHorseChest(ITextComponent inventoryTitle, int slotCount)
    {
        super(inventoryTitle, slotCount);
    }
}
