package net.minecraft.item.crafting;

import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;

public class RecipeTippedArrow implements IRecipe
{
    /**
     * Used to check if a recipe matches current crafting inventory
     */
    public boolean matches(InventoryCrafting inv, World worldIn)
    {
        if (inv.getWidth() == 3 && inv.getHeight() == 3)
        {
            for (int i = 0; i < inv.getWidth(); ++i)
            {
                for (int j = 0; j < inv.getHeight(); ++j)
                {
                    ItemStack itemstack = inv.getStackInRowAndColumn(i, j);

                    if (itemstack.isEmpty())
                    {
                        return false;
                    }

                    Item item = itemstack.getItem();

                    if (i == 1 && j == 1)
                    {
                        if (item != Items.LINGERING_POTION)
                        {
                            return false;
                        }
                    }
                    else if (item != Items.ARROW)
                    {
                        return false;
                    }
                }
            }

            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * Returns an Item that is the result of this recipe
     */
    public ItemStack getCraftingResult(InventoryCrafting inv)
    {
        ItemStack itemstack = inv.getStackInRowAndColumn(1, 1);

        if (itemstack.getItem() != Items.LINGERING_POTION)
        {
            return ItemStack.EMPTY;
        }
        else
        {
            ItemStack itemstack1 = new ItemStack(Items.TIPPED_ARROW, 8);
            PotionUtils.addPotionToItemStack(itemstack1, PotionUtils.getPotionFromItem(itemstack));
            PotionUtils.appendEffects(itemstack1, PotionUtils.getFullEffectsFromItem(itemstack));
            return itemstack1;
        }
    }

    public ItemStack getRecipeOutput()
    {
        return ItemStack.EMPTY;
    }

    public NonNullList<ItemStack> getRemainingItems(InventoryCrafting inv)
    {
        return NonNullList.<ItemStack>withSize(inv.getSizeInventory(), ItemStack.EMPTY);
    }

    public boolean isHidden()
    {
        return true;
    }

    /**
     * Used to determine if this recipe can fit in a grid of the given width/height
     */
    public boolean canFit(int width, int height)
    {
        return width >= 2 && height >= 2;
    }
}
