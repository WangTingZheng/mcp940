package net.minecraft.stats;

import java.util.BitSet;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;

public class RecipeBook
{
    protected final BitSet recipes = new BitSet();

    /** Recipes the player has not yet seen, so the GUI can play an animation */
    protected final BitSet unseenRecipes = new BitSet();
    protected boolean isGuiOpen;
    protected boolean isFilteringCraftable;

    public void apply(RecipeBook that)
    {
        this.recipes.clear();
        this.unseenRecipes.clear();
        this.recipes.or(that.recipes);
        this.unseenRecipes.or(that.unseenRecipes);
    }

    public void setRecipes(IRecipe recipe)
    {
        if (!recipe.isHidden())
        {
            this.recipes.set(getRecipeId(recipe));
        }
    }

    public boolean containsRecipe(IRecipe recipe)
    {
        return this.recipes.get(getRecipeId(recipe));
    }

    public void removeRecipe(IRecipe recipe)
    {
        int i = getRecipeId(recipe);
        this.recipes.clear(i);
        this.unseenRecipes.clear(i);
    }

    protected static int getRecipeId(IRecipe recipe)
    {
        return CraftingManager.REGISTRY.getIDForObject(recipe);
    }

    public boolean isRecipeUnseen(IRecipe recipe)
    {
        return this.unseenRecipes.get(getRecipeId(recipe));
    }

    public void setRecipeSeen(IRecipe recipe)
    {
        this.unseenRecipes.clear(getRecipeId(recipe));
    }

    public void addDisplayedRecipe(IRecipe recipe)
    {
        this.unseenRecipes.set(getRecipeId(recipe));
    }

    public boolean isGuiOpen()
    {
        return this.isGuiOpen;
    }

    public void setGuiOpen(boolean open)
    {
        this.isGuiOpen = open;
    }

    public boolean isFilteringCraftable()
    {
        return this.isFilteringCraftable;
    }

    public void setFilteringCraftable(boolean shouldFilter)
    {
        this.isFilteringCraftable = shouldFilter;
    }
}
