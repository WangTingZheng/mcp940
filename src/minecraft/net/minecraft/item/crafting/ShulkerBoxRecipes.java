package net.minecraft.item.crafting;

import net.minecraft.block.Block;
import net.minecraft.block.BlockShulkerBox;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;

public class ShulkerBoxRecipes
{
    public static class ShulkerBoxColoring implements IRecipe
    {
        public boolean matches(InventoryCrafting inv, World worldIn)
        {
            int i = 0;
            int j = 0;

            for (int k = 0; k < inv.getSizeInventory(); ++k)
            {
                ItemStack itemstack = inv.getStackInSlot(k);

                if (!itemstack.isEmpty())
                {
                    if (Block.getBlockFromItem(itemstack.getItem()) instanceof BlockShulkerBox)
                    {
                        ++i;
                    }
                    else
                    {
                        if (itemstack.getItem() != Items.DYE)
                        {
                            return false;
                        }

                        ++j;
                    }

                    if (j > 1 || i > 1)
                    {
                        return false;
                    }
                }
            }

            return i == 1 && j == 1;
        }

        public ItemStack getCraftingResult(InventoryCrafting inv)
        {
            ItemStack itemstack = ItemStack.EMPTY;
            ItemStack itemstack1 = ItemStack.EMPTY;

            for (int i = 0; i < inv.getSizeInventory(); ++i)
            {
                ItemStack itemstack2 = inv.getStackInSlot(i);

                if (!itemstack2.isEmpty())
                {
                    if (Block.getBlockFromItem(itemstack2.getItem()) instanceof BlockShulkerBox)
                    {
                        itemstack = itemstack2;
                    }
                    else if (itemstack2.getItem() == Items.DYE)
                    {
                        itemstack1 = itemstack2;
                    }
                }
            }

            ItemStack itemstack3 = BlockShulkerBox.getColoredItemStack(EnumDyeColor.byDyeDamage(itemstack1.getMetadata()));

            if (itemstack.hasTagCompound())
            {
                itemstack3.setTagCompound(itemstack.getTagCompound().copy());
            }

            return itemstack3;
        }

        public ItemStack getRecipeOutput()
        {
            return ItemStack.EMPTY;
        }

        public NonNullList<ItemStack> getRemainingItems(InventoryCrafting inv)
        {
            NonNullList<ItemStack> nonnulllist = NonNullList.<ItemStack>withSize(inv.getSizeInventory(), ItemStack.EMPTY);

            for (int i = 0; i < nonnulllist.size(); ++i)
            {
                ItemStack itemstack = inv.getStackInSlot(i);

                if (itemstack.getItem().hasContainerItem())
                {
                    nonnulllist.set(i, new ItemStack(itemstack.getItem().getContainerItem()));
                }
            }

            return nonnulllist;
        }

        public boolean isHidden()
        {
            return true;
        }

        public boolean canFit(int width, int height)
        {
            return width * height >= 2;
        }
    }
}
