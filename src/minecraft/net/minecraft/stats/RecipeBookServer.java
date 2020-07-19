package net.minecraft.stats;

import com.google.common.collect.Lists;
import java.util.Collections;
import java.util.List;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.network.play.server.SPacketRecipeBook;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RecipeBookServer extends RecipeBook
{
    private static final Logger LOGGER = LogManager.getLogger();

    public void add(List<IRecipe> recipesIn, EntityPlayerMP player)
    {
        List<IRecipe> list = Lists.<IRecipe>newArrayList();

        for (IRecipe irecipe : recipesIn)
        {
            if (!this.recipes.get(getRecipeId(irecipe)) && !irecipe.isHidden())
            {
                this.setRecipes(irecipe);
                this.addDisplayedRecipe(irecipe);
                list.add(irecipe);
                CriteriaTriggers.RECIPE_UNLOCKED.trigger(player, irecipe);
            }
        }

        this.sendPacket(SPacketRecipeBook.State.ADD, player, list);
    }

    public void remove(List<IRecipe> recipesIn, EntityPlayerMP player)
    {
        List<IRecipe> list = Lists.<IRecipe>newArrayList();

        for (IRecipe irecipe : recipesIn)
        {
            if (this.recipes.get(getRecipeId(irecipe)))
            {
                this.removeRecipe(irecipe);
                list.add(irecipe);
            }
        }

        this.sendPacket(SPacketRecipeBook.State.REMOVE, player, list);
    }

    private void sendPacket(SPacketRecipeBook.State state, EntityPlayerMP player, List<IRecipe> recipesIn)
    {
        player.connection.sendPacket(new SPacketRecipeBook(state, recipesIn, Collections.emptyList(), this.isGuiOpen, this.isFilteringCraftable));
    }

    public NBTTagCompound write()
    {
        NBTTagCompound nbttagcompound = new NBTTagCompound();
        nbttagcompound.setBoolean("isGuiOpen", this.isGuiOpen);
        nbttagcompound.setBoolean("isFilteringCraftable", this.isFilteringCraftable);
        NBTTagList nbttaglist = new NBTTagList();

        for (IRecipe irecipe : this.getRecipes())
        {
            nbttaglist.appendTag(new NBTTagString(((ResourceLocation)CraftingManager.REGISTRY.getNameForObject(irecipe)).toString()));
        }

        nbttagcompound.setTag("recipes", nbttaglist);
        NBTTagList nbttaglist1 = new NBTTagList();

        for (IRecipe irecipe1 : this.getDisplayedRecipes())
        {
            nbttaglist1.appendTag(new NBTTagString(((ResourceLocation)CraftingManager.REGISTRY.getNameForObject(irecipe1)).toString()));
        }

        nbttagcompound.setTag("toBeDisplayed", nbttaglist1);
        return nbttagcompound;
    }

    public void read(NBTTagCompound tag)
    {
        this.isGuiOpen = tag.getBoolean("isGuiOpen");
        this.isFilteringCraftable = tag.getBoolean("isFilteringCraftable");
        NBTTagList nbttaglist = tag.getTagList("recipes", 8);

        for (int i = 0; i < nbttaglist.tagCount(); ++i)
        {
            ResourceLocation resourcelocation = new ResourceLocation(nbttaglist.getStringTagAt(i));
            IRecipe irecipe = CraftingManager.getRecipe(resourcelocation);

            if (irecipe == null)
            {
                LOGGER.info("Tried to load unrecognized recipe: {} removed now.", (Object)resourcelocation);
            }
            else
            {
                this.setRecipes(irecipe);
            }
        }

        NBTTagList nbttaglist1 = tag.getTagList("toBeDisplayed", 8);

        for (int j = 0; j < nbttaglist1.tagCount(); ++j)
        {
            ResourceLocation resourcelocation1 = new ResourceLocation(nbttaglist1.getStringTagAt(j));
            IRecipe irecipe1 = CraftingManager.getRecipe(resourcelocation1);

            if (irecipe1 == null)
            {
                LOGGER.info("Tried to load unrecognized recipe: {} removed now.", (Object)resourcelocation1);
            }
            else
            {
                this.addDisplayedRecipe(irecipe1);
            }
        }
    }

    private List<IRecipe> getRecipes()
    {
        List<IRecipe> list = Lists.<IRecipe>newArrayList();

        for (int i = this.recipes.nextSetBit(0); i >= 0; i = this.recipes.nextSetBit(i + 1))
        {
            list.add(CraftingManager.REGISTRY.getObjectById(i));
        }

        return list;
    }

    private List<IRecipe> getDisplayedRecipes()
    {
        List<IRecipe> list = Lists.<IRecipe>newArrayList();

        for (int i = this.unseenRecipes.nextSetBit(0); i >= 0; i = this.unseenRecipes.nextSetBit(i + 1))
        {
            list.add(CraftingManager.REGISTRY.getObjectById(i));
        }

        return list;
    }

    public void init(EntityPlayerMP player)
    {
        player.connection.sendPacket(new SPacketRecipeBook(SPacketRecipeBook.State.INIT, this.getRecipes(), this.getDisplayedRecipes(), this.isGuiOpen, this.isFilteringCraftable));
    }
}
