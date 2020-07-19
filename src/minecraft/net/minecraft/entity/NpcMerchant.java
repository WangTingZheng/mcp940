package net.minecraft.entity;

import javax.annotation.Nullable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryMerchant;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;
import net.minecraft.world.World;

public class NpcMerchant implements IMerchant
{
    /** Instance of Merchants Inventory. */
    private final InventoryMerchant merchantInventory;

    /** This merchant's current player customer. */
    private final EntityPlayer customer;

    /** The MerchantRecipeList instance. */
    private MerchantRecipeList recipeList;
    private final ITextComponent name;

    public NpcMerchant(EntityPlayer customerIn, ITextComponent nameIn)
    {
        this.customer = customerIn;
        this.name = nameIn;
        this.merchantInventory = new InventoryMerchant(customerIn, this);
    }

    @Nullable
    public EntityPlayer getCustomer()
    {
        return this.customer;
    }

    public void setCustomer(@Nullable EntityPlayer player)
    {
    }

    @Nullable
    public MerchantRecipeList getRecipes(EntityPlayer player)
    {
        return this.recipeList;
    }

    public void setRecipes(@Nullable MerchantRecipeList recipeList)
    {
        this.recipeList = recipeList;
    }

    public void useRecipe(MerchantRecipe recipe)
    {
        recipe.incrementToolUses();
    }

    /**
     * Notifies the merchant of a possible merchantrecipe being fulfilled or not. Usually, this is just a sound byte
     * being played depending if the suggested itemstack is not null.
     */
    public void verifySellingItem(ItemStack stack)
    {
    }

    /**
     * Get the formatted ChatComponent that will be used for the sender's username in chat
     */
    public ITextComponent getDisplayName()
    {
        return (ITextComponent)(this.name != null ? this.name : new TextComponentTranslation("entity.Villager.name", new Object[0]));
    }

    public World getWorld()
    {
        return this.customer.world;
    }

    public BlockPos getPos()
    {
        return new BlockPos(this.customer);
    }
}
