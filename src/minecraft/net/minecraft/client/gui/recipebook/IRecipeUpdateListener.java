package net.minecraft.client.gui.recipebook;

import java.util.List;
import net.minecraft.item.crafting.IRecipe;

public interface IRecipeUpdateListener
{
    void recipesShown(List<IRecipe> recipes);
}
