package net.minecraft.village;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class MerchantRecipe
{
    /** Item the Villager buys. */
    private ItemStack itemToBuy;

    /** Second Item the Villager buys. */
    private ItemStack secondItemToBuy;

    /** Item the Villager sells. */
    private ItemStack itemToSell;

    /**
     * Saves how much has been tool used when put into to slot to be enchanted.
     */
    private int toolUses;

    /** Maximum times this trade can be used. */
    private int maxTradeUses;
    private boolean rewardsExp;

    public MerchantRecipe(NBTTagCompound tagCompound)
    {
        this.itemToBuy = ItemStack.EMPTY;
        this.secondItemToBuy = ItemStack.EMPTY;
        this.itemToSell = ItemStack.EMPTY;
        this.readFromTags(tagCompound);
    }

    public MerchantRecipe(ItemStack buy1, ItemStack buy2, ItemStack sell)
    {
        this(buy1, buy2, sell, 0, 7);
    }

    public MerchantRecipe(ItemStack buy1, ItemStack buy2, ItemStack sell, int toolUsesIn, int maxTradeUsesIn)
    {
        this.itemToBuy = ItemStack.EMPTY;
        this.secondItemToBuy = ItemStack.EMPTY;
        this.itemToSell = ItemStack.EMPTY;
        this.itemToBuy = buy1;
        this.secondItemToBuy = buy2;
        this.itemToSell = sell;
        this.toolUses = toolUsesIn;
        this.maxTradeUses = maxTradeUsesIn;
        this.rewardsExp = true;
    }

    public MerchantRecipe(ItemStack buy1, ItemStack sell)
    {
        this(buy1, ItemStack.EMPTY, sell);
    }

    public MerchantRecipe(ItemStack buy1, Item sellItem)
    {
        this(buy1, new ItemStack(sellItem));
    }

    /**
     * Gets the itemToBuy.
     */
    public ItemStack getItemToBuy()
    {
        return this.itemToBuy;
    }

    /**
     * Gets secondItemToBuy.
     */
    public ItemStack getSecondItemToBuy()
    {
        return this.secondItemToBuy;
    }

    /**
     * Gets if Villager has secondItemToBuy.
     */
    public boolean hasSecondItemToBuy()
    {
        return !this.secondItemToBuy.isEmpty();
    }

    /**
     * Gets itemToSell.
     */
    public ItemStack getItemToSell()
    {
        return this.itemToSell;
    }

    public int getToolUses()
    {
        return this.toolUses;
    }

    public int getMaxTradeUses()
    {
        return this.maxTradeUses;
    }

    public void incrementToolUses()
    {
        ++this.toolUses;
    }

    public void increaseMaxTradeUses(int increment)
    {
        this.maxTradeUses += increment;
    }

    public boolean isRecipeDisabled()
    {
        return this.toolUses >= this.maxTradeUses;
    }

    /**
     * Compensates {@link net.minecraft.village.MerchantRecipe#toolUses toolUses} with {@link
     * net.minecraft.village.MerchantRecipe#maxTradeUses maxTradeUses}
     */
    public void compensateToolUses()
    {
        this.toolUses = this.maxTradeUses;
    }

    public boolean getRewardsExp()
    {
        return this.rewardsExp;
    }

    public void readFromTags(NBTTagCompound tagCompound)
    {
        NBTTagCompound nbttagcompound = tagCompound.getCompoundTag("buy");
        this.itemToBuy = new ItemStack(nbttagcompound);
        NBTTagCompound nbttagcompound1 = tagCompound.getCompoundTag("sell");
        this.itemToSell = new ItemStack(nbttagcompound1);

        if (tagCompound.hasKey("buyB", 10))
        {
            this.secondItemToBuy = new ItemStack(tagCompound.getCompoundTag("buyB"));
        }

        if (tagCompound.hasKey("uses", 99))
        {
            this.toolUses = tagCompound.getInteger("uses");
        }

        if (tagCompound.hasKey("maxUses", 99))
        {
            this.maxTradeUses = tagCompound.getInteger("maxUses");
        }
        else
        {
            this.maxTradeUses = 7;
        }

        if (tagCompound.hasKey("rewardExp", 1))
        {
            this.rewardsExp = tagCompound.getBoolean("rewardExp");
        }
        else
        {
            this.rewardsExp = true;
        }
    }

    public NBTTagCompound writeToTags()
    {
        NBTTagCompound nbttagcompound = new NBTTagCompound();
        nbttagcompound.setTag("buy", this.itemToBuy.writeToNBT(new NBTTagCompound()));
        nbttagcompound.setTag("sell", this.itemToSell.writeToNBT(new NBTTagCompound()));

        if (!this.secondItemToBuy.isEmpty())
        {
            nbttagcompound.setTag("buyB", this.secondItemToBuy.writeToNBT(new NBTTagCompound()));
        }

        nbttagcompound.setInteger("uses", this.toolUses);
        nbttagcompound.setInteger("maxUses", this.maxTradeUses);
        nbttagcompound.setBoolean("rewardExp", this.rewardsExp);
        return nbttagcompound;
    }
}
