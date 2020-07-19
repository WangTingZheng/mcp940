package net.minecraft.enchantment;

import net.minecraft.util.WeightedRandom;

public class EnchantmentData extends WeightedRandom.Item
{
    /** Enchantment object associated with this EnchantmentData */
    public final Enchantment enchantment;

    /** Enchantment level associated with this EnchantmentData */
    public final int enchantmentLevel;

    public EnchantmentData(Enchantment enchantmentObj, int enchLevel)
    {
        super(enchantmentObj.getRarity().getWeight());
        this.enchantment = enchantmentObj;
        this.enchantmentLevel = enchLevel;
    }
}
