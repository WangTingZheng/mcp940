package net.minecraft.potion;

import com.google.common.base.Predicate;
import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.init.Items;
import net.minecraft.init.PotionTypes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFishFood;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;

public class PotionHelper
{
    private static final List<PotionHelper.MixPredicate<PotionType>> POTION_TYPE_CONVERSIONS = Lists.<PotionHelper.MixPredicate<PotionType>>newArrayList();
    private static final List<PotionHelper.MixPredicate<Item>> POTION_ITEM_CONVERSIONS = Lists.<PotionHelper.MixPredicate<Item>>newArrayList();
    private static final List<Ingredient> POTION_ITEMS = Lists.<Ingredient>newArrayList();
    private static final Predicate<ItemStack> IS_POTION_ITEM = new Predicate<ItemStack>()
    {
        public boolean apply(ItemStack p_apply_1_)
        {
            for (Ingredient ingredient : PotionHelper.POTION_ITEMS)
            {
                if (ingredient.apply(p_apply_1_))
                {
                    return true;
                }
            }

            return false;
        }
    };

    public static boolean isReagent(ItemStack stack)
    {
        return isItemConversionReagent(stack) || isTypeConversionReagent(stack);
    }

    protected static boolean isItemConversionReagent(ItemStack stack)
    {
        int i = 0;

        for (int j = POTION_ITEM_CONVERSIONS.size(); i < j; ++i)
        {
            if ((POTION_ITEM_CONVERSIONS.get(i)).reagent.apply(stack))
            {
                return true;
            }
        }

        return false;
    }

    protected static boolean isTypeConversionReagent(ItemStack stack)
    {
        int i = 0;

        for (int j = POTION_TYPE_CONVERSIONS.size(); i < j; ++i)
        {
            if ((POTION_TYPE_CONVERSIONS.get(i)).reagent.apply(stack))
            {
                return true;
            }
        }

        return false;
    }

    public static boolean hasConversions(ItemStack input, ItemStack reagent)
    {
        if (!IS_POTION_ITEM.apply(input))
        {
            return false;
        }
        else
        {
            return hasItemConversions(input, reagent) || hasTypeConversions(input, reagent);
        }
    }

    protected static boolean hasItemConversions(ItemStack input, ItemStack reagent)
    {
        Item item = input.getItem();
        int i = 0;

        for (int j = POTION_ITEM_CONVERSIONS.size(); i < j; ++i)
        {
            PotionHelper.MixPredicate<Item> mixpredicate = (PotionHelper.MixPredicate)POTION_ITEM_CONVERSIONS.get(i);

            if (mixpredicate.input == item && mixpredicate.reagent.apply(reagent))
            {
                return true;
            }
        }

        return false;
    }

    protected static boolean hasTypeConversions(ItemStack input, ItemStack reagent)
    {
        PotionType potiontype = PotionUtils.getPotionFromItem(input);
        int i = 0;

        for (int j = POTION_TYPE_CONVERSIONS.size(); i < j; ++i)
        {
            PotionHelper.MixPredicate<PotionType> mixpredicate = (PotionHelper.MixPredicate)POTION_TYPE_CONVERSIONS.get(i);

            if (mixpredicate.input == potiontype && mixpredicate.reagent.apply(reagent))
            {
                return true;
            }
        }

        return false;
    }

    public static ItemStack doReaction(ItemStack reagent, ItemStack potionIn)
    {
        if (!potionIn.isEmpty())
        {
            PotionType potiontype = PotionUtils.getPotionFromItem(potionIn);
            Item item = potionIn.getItem();
            int i = 0;

            for (int j = POTION_ITEM_CONVERSIONS.size(); i < j; ++i)
            {
                PotionHelper.MixPredicate<Item> mixpredicate = (PotionHelper.MixPredicate)POTION_ITEM_CONVERSIONS.get(i);

                if (mixpredicate.input == item && mixpredicate.reagent.apply(reagent))
                {
                    return PotionUtils.addPotionToItemStack(new ItemStack((Item)mixpredicate.output), potiontype);
                }
            }

            i = 0;

            for (int k = POTION_TYPE_CONVERSIONS.size(); i < k; ++i)
            {
                PotionHelper.MixPredicate<PotionType> mixpredicate1 = (PotionHelper.MixPredicate)POTION_TYPE_CONVERSIONS.get(i);

                if (mixpredicate1.input == potiontype && mixpredicate1.reagent.apply(reagent))
                {
                    return PotionUtils.addPotionToItemStack(new ItemStack(item), (PotionType)mixpredicate1.output);
                }
            }
        }

        return potionIn;
    }

    public static void init()
    {
        addContainer(Items.POTIONITEM);
        addContainer(Items.SPLASH_POTION);
        addContainer(Items.LINGERING_POTION);
        addContainerRecipe(Items.POTIONITEM, Items.GUNPOWDER, Items.SPLASH_POTION);
        addContainerRecipe(Items.SPLASH_POTION, Items.DRAGON_BREATH, Items.LINGERING_POTION);
        addMix(PotionTypes.WATER, Items.SPECKLED_MELON, PotionTypes.MUNDANE);
        addMix(PotionTypes.WATER, Items.GHAST_TEAR, PotionTypes.MUNDANE);
        addMix(PotionTypes.WATER, Items.RABBIT_FOOT, PotionTypes.MUNDANE);
        addMix(PotionTypes.WATER, Items.BLAZE_POWDER, PotionTypes.MUNDANE);
        addMix(PotionTypes.WATER, Items.SPIDER_EYE, PotionTypes.MUNDANE);
        addMix(PotionTypes.WATER, Items.SUGAR, PotionTypes.MUNDANE);
        addMix(PotionTypes.WATER, Items.MAGMA_CREAM, PotionTypes.MUNDANE);
        addMix(PotionTypes.WATER, Items.GLOWSTONE_DUST, PotionTypes.THICK);
        addMix(PotionTypes.WATER, Items.REDSTONE, PotionTypes.MUNDANE);
        addMix(PotionTypes.WATER, Items.NETHER_WART, PotionTypes.AWKWARD);
        addMix(PotionTypes.AWKWARD, Items.GOLDEN_CARROT, PotionTypes.NIGHT_VISION);
        addMix(PotionTypes.NIGHT_VISION, Items.REDSTONE, PotionTypes.LONG_NIGHT_VISION);
        addMix(PotionTypes.NIGHT_VISION, Items.FERMENTED_SPIDER_EYE, PotionTypes.INVISIBILITY);
        addMix(PotionTypes.LONG_NIGHT_VISION, Items.FERMENTED_SPIDER_EYE, PotionTypes.LONG_INVISIBILITY);
        addMix(PotionTypes.INVISIBILITY, Items.REDSTONE, PotionTypes.LONG_INVISIBILITY);
        addMix(PotionTypes.AWKWARD, Items.MAGMA_CREAM, PotionTypes.FIRE_RESISTANCE);
        addMix(PotionTypes.FIRE_RESISTANCE, Items.REDSTONE, PotionTypes.LONG_FIRE_RESISTANCE);
        addMix(PotionTypes.AWKWARD, Items.RABBIT_FOOT, PotionTypes.LEAPING);
        addMix(PotionTypes.LEAPING, Items.REDSTONE, PotionTypes.LONG_LEAPING);
        addMix(PotionTypes.LEAPING, Items.GLOWSTONE_DUST, PotionTypes.STRONG_LEAPING);
        addMix(PotionTypes.LEAPING, Items.FERMENTED_SPIDER_EYE, PotionTypes.SLOWNESS);
        addMix(PotionTypes.LONG_LEAPING, Items.FERMENTED_SPIDER_EYE, PotionTypes.LONG_SLOWNESS);
        addMix(PotionTypes.SLOWNESS, Items.REDSTONE, PotionTypes.LONG_SLOWNESS);
        addMix(PotionTypes.SWIFTNESS, Items.FERMENTED_SPIDER_EYE, PotionTypes.SLOWNESS);
        addMix(PotionTypes.LONG_SWIFTNESS, Items.FERMENTED_SPIDER_EYE, PotionTypes.LONG_SLOWNESS);
        addMix(PotionTypes.AWKWARD, Items.SUGAR, PotionTypes.SWIFTNESS);
        addMix(PotionTypes.SWIFTNESS, Items.REDSTONE, PotionTypes.LONG_SWIFTNESS);
        addMix(PotionTypes.SWIFTNESS, Items.GLOWSTONE_DUST, PotionTypes.STRONG_SWIFTNESS);
        addMix(PotionTypes.AWKWARD, Ingredient.fromStacks(new ItemStack(Items.FISH, 1, ItemFishFood.FishType.PUFFERFISH.getMetadata())), PotionTypes.WATER_BREATHING);
        addMix(PotionTypes.WATER_BREATHING, Items.REDSTONE, PotionTypes.LONG_WATER_BREATHING);
        addMix(PotionTypes.AWKWARD, Items.SPECKLED_MELON, PotionTypes.HEALING);
        addMix(PotionTypes.HEALING, Items.GLOWSTONE_DUST, PotionTypes.STRONG_HEALING);
        addMix(PotionTypes.HEALING, Items.FERMENTED_SPIDER_EYE, PotionTypes.HARMING);
        addMix(PotionTypes.STRONG_HEALING, Items.FERMENTED_SPIDER_EYE, PotionTypes.STRONG_HARMING);
        addMix(PotionTypes.HARMING, Items.GLOWSTONE_DUST, PotionTypes.STRONG_HARMING);
        addMix(PotionTypes.POISON, Items.FERMENTED_SPIDER_EYE, PotionTypes.HARMING);
        addMix(PotionTypes.LONG_POISON, Items.FERMENTED_SPIDER_EYE, PotionTypes.HARMING);
        addMix(PotionTypes.STRONG_POISON, Items.FERMENTED_SPIDER_EYE, PotionTypes.STRONG_HARMING);
        addMix(PotionTypes.AWKWARD, Items.SPIDER_EYE, PotionTypes.POISON);
        addMix(PotionTypes.POISON, Items.REDSTONE, PotionTypes.LONG_POISON);
        addMix(PotionTypes.POISON, Items.GLOWSTONE_DUST, PotionTypes.STRONG_POISON);
        addMix(PotionTypes.AWKWARD, Items.GHAST_TEAR, PotionTypes.REGENERATION);
        addMix(PotionTypes.REGENERATION, Items.REDSTONE, PotionTypes.LONG_REGENERATION);
        addMix(PotionTypes.REGENERATION, Items.GLOWSTONE_DUST, PotionTypes.STRONG_REGENERATION);
        addMix(PotionTypes.AWKWARD, Items.BLAZE_POWDER, PotionTypes.STRENGTH);
        addMix(PotionTypes.STRENGTH, Items.REDSTONE, PotionTypes.LONG_STRENGTH);
        addMix(PotionTypes.STRENGTH, Items.GLOWSTONE_DUST, PotionTypes.STRONG_STRENGTH);
        addMix(PotionTypes.WATER, Items.FERMENTED_SPIDER_EYE, PotionTypes.WEAKNESS);
        addMix(PotionTypes.WEAKNESS, Items.REDSTONE, PotionTypes.LONG_WEAKNESS);
    }

    private static void addContainerRecipe(ItemPotion p_193355_0_, Item p_193355_1_, ItemPotion p_193355_2_)
    {
        POTION_ITEM_CONVERSIONS.add(new PotionHelper.MixPredicate(p_193355_0_, Ingredient.fromItems(p_193355_1_), p_193355_2_));
    }

    private static void addContainer(ItemPotion p_193354_0_)
    {
        POTION_ITEMS.add(Ingredient.fromItems(p_193354_0_));
    }

    private static void addMix(PotionType p_193357_0_, Item p_193357_1_, PotionType p_193357_2_)
    {
        addMix(p_193357_0_, Ingredient.fromItems(p_193357_1_), p_193357_2_);
    }

    private static void addMix(PotionType p_193356_0_, Ingredient p_193356_1_, PotionType p_193356_2_)
    {
        POTION_TYPE_CONVERSIONS.add(new PotionHelper.MixPredicate(p_193356_0_, p_193356_1_, p_193356_2_));
    }

    static class MixPredicate<T>
    {
        final T input;
        final Ingredient reagent;
        final T output;

        public MixPredicate(T p_i47570_1_, Ingredient p_i47570_2_, T p_i47570_3_)
        {
            this.input = p_i47570_1_;
            this.reagent = p_i47570_2_;
            this.output = p_i47570_3_;
        }
    }
}
