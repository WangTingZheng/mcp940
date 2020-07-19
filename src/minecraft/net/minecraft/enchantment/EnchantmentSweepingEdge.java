package net.minecraft.enchantment;

import net.minecraft.inventory.EntityEquipmentSlot;

public class EnchantmentSweepingEdge extends Enchantment
{
    public EnchantmentSweepingEdge(Enchantment.Rarity p_i47366_1_, EntityEquipmentSlot... p_i47366_2_)
    {
        super(p_i47366_1_, EnumEnchantmentType.WEAPON, p_i47366_2_);
    }

    /**
     * Returns the minimal value of enchantability needed on the enchantment level passed.
     */
    public int getMinEnchantability(int enchantmentLevel)
    {
        return 5 + (enchantmentLevel - 1) * 9;
    }

    /**
     * Returns the maximum value of enchantability nedded on the enchantment level passed.
     */
    public int getMaxEnchantability(int enchantmentLevel)
    {
        return this.getMinEnchantability(enchantmentLevel) + 15;
    }

    /**
     * Returns the maximum level that the enchantment can have.
     */
    public int getMaxLevel()
    {
        return 3;
    }

    public static float getSweepingDamageRatio(int p_191526_0_)
    {
        return 1.0F - 1.0F / (float)(p_191526_0_ + 1);
    }

    /**
     * Return the name of key in translation table of this enchantment.
     */
    public String getName()
    {
        return "enchantment.sweeping";
    }
}
