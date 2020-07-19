package net.minecraft.inventory;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public interface IContainerListener {
   void func_71110_a(Container var1, NonNullList<ItemStack> var2);

   void func_71111_a(Container var1, int var2, ItemStack var3);

   void func_71112_a(Container var1, int var2, int var3);

   void func_175173_a(Container var1, IInventory var2);
}
