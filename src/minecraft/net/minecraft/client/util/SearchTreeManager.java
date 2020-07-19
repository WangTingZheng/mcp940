package net.minecraft.client.util;

import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.client.gui.recipebook.RecipeList;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.item.ItemStack;

public class SearchTreeManager implements IResourceManagerReloadListener
{
    public static final SearchTreeManager.Key<ItemStack> ITEMS = new SearchTreeManager.Key<ItemStack>();
    public static final SearchTreeManager.Key<RecipeList> RECIPES = new SearchTreeManager.Key<RecipeList>();
    private final Map < SearchTreeManager.Key<?>, SearchTree<? >> trees = Maps. < SearchTreeManager.Key<?>, SearchTree<? >> newHashMap();

    public void onResourceManagerReload(IResourceManager resourceManager)
    {
        for (SearchTree<?> searchtree : this.trees.values())
        {
            searchtree.recalculate();
        }
    }

    public <T> void register(SearchTreeManager.Key<T> key, SearchTree<T> searchTreeIn)
    {
        this.trees.put(key, searchTreeIn);
    }

    public <T> ISearchTree<T> get(SearchTreeManager.Key<T> key)
    {
        return (ISearchTree)this.trees.get(key);
    }

    public static class Key<T>
    {
    }
}
