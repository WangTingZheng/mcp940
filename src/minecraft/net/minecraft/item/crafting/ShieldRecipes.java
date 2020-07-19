package net.minecraft.item.crafting;

import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;

public class ShieldRecipes
{
    public static class Decoration implements IRecipe
    {
        public boolean matches(InventoryCrafting inv, World worldIn)
        {
            ItemStack itemstack = ItemStack.EMPTY;
            ItemStack itemstack1 = ItemStack.EMPTY;

            for (int i = 0; i < inv.getSizeInventory(); ++i)
            {
                ItemStack itemstack2 = inv.getStackInSlot(i);

                if (!itemstack2.isEmpty())
                {
                    if (itemstack2.getItem() == Items.BANNER)
                    {
                        if (!itemstack1.isEmpty())
                        {
                            return false;
                        }

                        itemstack1 = itemstack2;
                    }
                    else
                    {
                        if (itemstack2.getItem() != Items.SHIELD)
                        {
                            return false;
                        }

                        if (!itemstack.isEmpty())
                        {
                            return false;
                        }

                        if (itemstack2.getSubCompound("BlockEntityTag") != null)
                        {
                            return false;
                        }

                        itemstack = itemstack2;
                    }
                }
            }

            if (!itemstack.isEmpty() && !itemstack1.isEmpty())
            {
                return true;
            }
            else
            {
                return false;
            }
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
                    if (itemstack2.getItem() == Items.BANNER)
                    {
                        itemstack = itemstack2;
                    }
                    else if (itemstack2.getItem() == Items.SHIELD)
                    {
                        itemstack1 = itemstack2.copy();
                    }
                }
            }

            if (itemstack1.isEmpty())
            {
                return itemstack1;
            }
            else
            {
                NBTTagCompound nbttagcompound = itemstack.getSubCompound("BlockEntityTag");
                NBTTagCompound nbttagcompound1 = nbttagcompound == null ? new NBTTagCompound() : nbttagcompound.copy();
                nbttagcompound1.setInteger("Base", itemstack.getMetadata() & 15);
                itemstack1.setTagInfo("BlockEntityTag", nbttagcompound1);
                return itemstack1;
            }
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
