package net.minecraft.inventory;

import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;

public interface ISidedInventory extends IInventory {
   int[] func_180463_a(EnumFacing var1);

   boolean func_180462_a(int var1, ItemStack var2, EnumFacing var3);

   boolean func_180461_b(int var1, ItemStack var2, EnumFacing var3);
}
