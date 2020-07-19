package net.minecraft.item;

import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;

public class ItemFishFood extends ItemFood
{
    /** Indicates whether this fish is "cooked" or not. */
    private final boolean cooked;

    public ItemFishFood(boolean cooked)
    {
        super(0, 0.0F, false);
        this.cooked = cooked;
    }

    public int getHealAmount(ItemStack stack)
    {
        ItemFishFood.FishType itemfishfood$fishtype = ItemFishFood.FishType.byItemStack(stack);
        return this.cooked && itemfishfood$fishtype.canCook() ? itemfishfood$fishtype.getCookedHealAmount() : itemfishfood$fishtype.getUncookedHealAmount();
    }

    public float getSaturationModifier(ItemStack stack)
    {
        ItemFishFood.FishType itemfishfood$fishtype = ItemFishFood.FishType.byItemStack(stack);
        return this.cooked && itemfishfood$fishtype.canCook() ? itemfishfood$fishtype.getCookedSaturationModifier() : itemfishfood$fishtype.getUncookedSaturationModifier();
    }

    protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player)
    {
        ItemFishFood.FishType itemfishfood$fishtype = ItemFishFood.FishType.byItemStack(stack);

        if (itemfishfood$fishtype == ItemFishFood.FishType.PUFFERFISH)
        {
            player.addPotionEffect(new PotionEffect(MobEffects.POISON, 1200, 3));
            player.addPotionEffect(new PotionEffect(MobEffects.HUNGER, 300, 2));
            player.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 300, 1));
        }

        super.onFoodEaten(stack, worldIn, player);
    }

    /**
     * returns a list of items with the same ID, but different meta (eg: dye returns 16 items)
     */
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items)
    {
        if (this.isInCreativeTab(tab))
        {
            for (ItemFishFood.FishType itemfishfood$fishtype : ItemFishFood.FishType.values())
            {
                if (!this.cooked || itemfishfood$fishtype.canCook())
                {
                    items.add(new ItemStack(this, 1, itemfishfood$fishtype.getMetadata()));
                }
            }
        }
    }

    /**
     * Returns the unlocalized name of this item. This version accepts an ItemStack so different stacks can have
     * different names based on their damage or NBT.
     */
    public String getUnlocalizedName(ItemStack stack)
    {
        ItemFishFood.FishType itemfishfood$fishtype = ItemFishFood.FishType.byItemStack(stack);
        return this.getUnlocalizedName() + "." + itemfishfood$fishtype.getUnlocalizedName() + "." + (this.cooked && itemfishfood$fishtype.canCook() ? "cooked" : "raw");
    }

    public static enum FishType
    {
        COD(0, "cod", 2, 0.1F, 5, 0.6F),
        SALMON(1, "salmon", 2, 0.1F, 6, 0.8F),
        CLOWNFISH(2, "clownfish", 1, 0.1F),
        PUFFERFISH(3, "pufferfish", 1, 0.1F);

        private static final Map<Integer, ItemFishFood.FishType> META_LOOKUP = Maps.<Integer, ItemFishFood.FishType>newHashMap();
        private final int meta;
        private final String unlocalizedName;
        private final int uncookedHealAmount;
        private final float uncookedSaturationModifier;
        private final int cookedHealAmount;
        private final float cookedSaturationModifier;
        private final boolean cookable;

        private FishType(int meta, String unlocalizedName, int uncookedHeal, float uncookedSaturation, int cookedHeal, float cookedSaturation)
        {
            this.meta = meta;
            this.unlocalizedName = unlocalizedName;
            this.uncookedHealAmount = uncookedHeal;
            this.uncookedSaturationModifier = uncookedSaturation;
            this.cookedHealAmount = cookedHeal;
            this.cookedSaturationModifier = cookedSaturation;
            this.cookable = true;
        }

        private FishType(int meta, String unlocalizedName, int uncookedHeal, float uncookedSaturation)
        {
            this.meta = meta;
            this.unlocalizedName = unlocalizedName;
            this.uncookedHealAmount = uncookedHeal;
            this.uncookedSaturationModifier = uncookedSaturation;
            this.cookedHealAmount = 0;
            this.cookedSaturationModifier = 0.0F;
            this.cookable = false;
        }

        public int getMetadata()
        {
            return this.meta;
        }

        public String getUnlocalizedName()
        {
            return this.unlocalizedName;
        }

        public int getUncookedHealAmount()
        {
            return this.uncookedHealAmount;
        }

        public float getUncookedSaturationModifier()
        {
            return this.uncookedSaturationModifier;
        }

        public int getCookedHealAmount()
        {
            return this.cookedHealAmount;
        }

        public float getCookedSaturationModifier()
        {
            return this.cookedSaturationModifier;
        }

        public boolean canCook()
        {
            return this.cookable;
        }

        public static ItemFishFood.FishType byMetadata(int meta)
        {
            ItemFishFood.FishType itemfishfood$fishtype = META_LOOKUP.get(Integer.valueOf(meta));
            return itemfishfood$fishtype == null ? COD : itemfishfood$fishtype;
        }

        public static ItemFishFood.FishType byItemStack(ItemStack stack)
        {
            return stack.getItem() instanceof ItemFishFood ? byMetadata(stack.getMetadata()) : COD;
        }

        static {
            for (ItemFishFood.FishType itemfishfood$fishtype : values())
            {
                META_LOOKUP.put(Integer.valueOf(itemfishfood$fishtype.getMetadata()), itemfishfood$fishtype);
            }
        }
    }
}
