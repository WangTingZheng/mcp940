package net.minecraft.client.gui.recipebook;

import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.ints.IntList;
import java.util.BitSet;
import java.util.List;
import net.minecraft.client.util.RecipeItemHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.stats.RecipeBook;

public class RecipeList
{
    private List<IRecipe> recipes = Lists.<IRecipe>newArrayList();
    private final BitSet craftable = new BitSet();
    private final BitSet canFit = new BitSet();
    private final BitSet inBook = new BitSet();
    private boolean singleResultItem = true;

    /**
     * Checks if recipebook is not empty
     */
    public boolean isNotEmpty()
    {
        return !this.inBook.isEmpty();
    }

    public void updateKnownRecipes(RecipeBook book)
    {
        for (int i = 0; i < this.recipes.size(); ++i)
        {
            this.inBook.set(i, book.containsRecipe(this.recipes.get(i)));
        }
    }

    public void canCraft(RecipeItemHelper handler, int width, int height, RecipeBook book)
    {
        for (int i = 0; i < this.recipes.size(); ++i)
        {
            IRecipe irecipe = this.recipes.get(i);
            boolean flag = irecipe.canFit(width, height) && book.containsRecipe(irecipe);
            this.canFit.set(i, flag);
            this.craftable.set(i, flag && handler.canCraft(irecipe, (IntList)null));
        }
    }

    public boolean isCraftable(IRecipe recipe)
    {
        return this.craftable.get(this.recipes.indexOf(recipe));
    }

    public boolean containsCraftableRecipes()
    {
        return !this.craftable.isEmpty();
    }

    public boolean containsValidRecipes()
    {
        return !this.canFit.isEmpty();
    }

    public List<IRecipe> getRecipes()
    {
        return this.recipes;
    }

    public List<IRecipe> getRecipes(boolean p_194208_1_)
    {
        List<IRecipe> list = Lists.<IRecipe>newArrayList();

        for (int i = this.inBook.nextSetBit(0); i >= 0; i = this.inBook.nextSetBit(i + 1))
        {
            if ((p_194208_1_ ? this.craftable : this.canFit).get(i))
            {
                list.add(this.recipes.get(i));
            }
        }

        return list;
    }

    public List<IRecipe> getDisplayRecipes(boolean onlyCraftable)
    {
        List<IRecipe> list = Lists.<IRecipe>newArrayList();

        for (int i = this.inBook.nextSetBit(0); i >= 0; i = this.inBook.nextSetBit(i + 1))
        {
            if (this.canFit.get(i) && this.craftable.get(i) == onlyCraftable)
            {
                list.add(this.recipes.get(i));
            }
        }

        return list;
    }

    public void add(IRecipe recipe)
    {
        this.recipes.add(recipe);

        if (this.singleResultItem)
        {
            ItemStack itemstack = ((IRecipe)this.recipes.get(0)).getRecipeOutput();
            ItemStack itemstack1 = recipe.getRecipeOutput();
            this.singleResultItem = ItemStack.areItemsEqual(itemstack, itemstack1) && ItemStack.areItemStackTagsEqual(itemstack, itemstack1);
        }
    }

    public boolean hasSingleResultItem()
    {
        return this.singleResultItem;
    }
}
