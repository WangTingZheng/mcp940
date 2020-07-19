package net.minecraft.entity;

import javax.annotation.Nullable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;
import net.minecraft.world.World;

public interface IMerchant
{
    void setCustomer(EntityPlayer player);

    @Nullable
    EntityPlayer getCustomer();

    @Nullable
    MerchantRecipeList getRecipes(EntityPlayer player);

    void setRecipes(MerchantRecipeList recipeList);

    void useRecipe(MerchantRecipe recipe);

    /**
     * Notifies the merchant of a possible merchantrecipe being fulfilled or not. Usually, this is just a sound byte
     * being played depending if the suggested itemstack is not null.
     */
    void verifySellingItem(ItemStack stack);

    /**
     * Get the formatted ChatComponent that will be used for the sender's username in chat
     */
    ITextComponent getDisplayName();

    World getWorld();

    BlockPos getPos();
}
