package net.minecraft.item.crafting;

import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapData;
import net.minecraft.world.storage.MapDecoration;

public class RecipesMapExtending extends ShapedRecipes
{
    public RecipesMapExtending()
    {
        super("", 3, 3, NonNullList.from(Ingredient.EMPTY, Ingredient.fromItems(Items.PAPER), Ingredient.fromItems(Items.PAPER), Ingredient.fromItems(Items.PAPER), Ingredient.fromItems(Items.PAPER), Ingredient.fromItem(Items.FILLED_MAP), Ingredient.fromItems(Items.PAPER), Ingredient.fromItems(Items.PAPER), Ingredient.fromItems(Items.PAPER), Ingredient.fromItems(Items.PAPER)), new ItemStack(Items.MAP));
    }

    /**
     * Used to check if a recipe matches current crafting inventory
     */
    public boolean matches(InventoryCrafting inv, World worldIn)
    {
        if (!super.matches(inv, worldIn))
        {
            return false;
        }
        else
        {
            ItemStack itemstack = ItemStack.EMPTY;

            for (int i = 0; i < inv.getSizeInventory() && itemstack.isEmpty(); ++i)
            {
                ItemStack itemstack1 = inv.getStackInSlot(i);

                if (itemstack1.getItem() == Items.FILLED_MAP)
                {
                    itemstack = itemstack1;
                }
            }

            if (itemstack.isEmpty())
            {
                return false;
            }
            else
            {
                MapData mapdata = Items.FILLED_MAP.getMapData(itemstack, worldIn);

                if (mapdata == null)
                {
                    return false;
                }
                else if (this.isExplorationMap(mapdata))
                {
                    return false;
                }
                else
                {
                    return mapdata.scale < 4;
                }
            }
        }
    }

    private boolean isExplorationMap(MapData p_190934_1_)
    {
        if (p_190934_1_.mapDecorations != null)
        {
            for (MapDecoration mapdecoration : p_190934_1_.mapDecorations.values())
            {
                if (mapdecoration.getType() == MapDecoration.Type.MANSION || mapdecoration.getType() == MapDecoration.Type.MONUMENT)
                {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Returns an Item that is the result of this recipe
     */
    public ItemStack getCraftingResult(InventoryCrafting inv)
    {
        ItemStack itemstack = ItemStack.EMPTY;

        for (int i = 0; i < inv.getSizeInventory() && itemstack.isEmpty(); ++i)
        {
            ItemStack itemstack1 = inv.getStackInSlot(i);

            if (itemstack1.getItem() == Items.FILLED_MAP)
            {
                itemstack = itemstack1;
            }
        }

        itemstack = itemstack.copy();
        itemstack.setCount(1);

        if (itemstack.getTagCompound() == null)
        {
            itemstack.setTagCompound(new NBTTagCompound());
        }

        itemstack.getTagCompound().setInteger("map_scale_direction", 1);
        return itemstack;
    }

    public boolean isHidden()
    {
        return true;
    }
}
