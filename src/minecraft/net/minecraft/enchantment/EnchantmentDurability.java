package net.minecraft.enchantment;

import java.util.Random;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

public class EnchantmentDurability extends Enchantment
{
    protected EnchantmentDurability(Enchantment.Rarity rarityIn, EntityEquipmentSlot... slots)
    {
        super(rarityIn, EnumEnchantmentType.BREAKABLE, slots);
        this.setName("durability");
    }

    /**
     * Returns the minimal value of enchantability needed on the enchantment level passed.
     */
    public int getMinEnchantability(int enchantmentLevel)
    {
        return 5 + (enchantmentLevel - 1) * 8;
    }

    /**
     * Returns the maximum value of enchantability nedded on the enchantment level passed.
     */
    public int getMaxEnchantability(int enchantmentLevel)
    {
        return super.getMinEnchantability(enchantmentLevel) + 50;
    }

    /**
     * Returns the maximum level that the enchantment can have.
     */
    public int getMaxLevel()
    {
        return 3;
    }

    /**
     * Determines if this enchantment can be applied to a specific ItemStack.
     */
    public boolean canApply(ItemStack stack)
    {
        return stack.isItemStackDamageable() ? true : super.canApply(stack);
    }

    /**
     * Used by ItemStack.attemptDamageItem. Randomly determines if a point of damage should be negated using the
     * enchantment level (par1). If the ItemStack is Armor then there is a flat 60% chance for damage to be negated no
     * matter the enchantment level, otherwise there is a 1-(par/1) chance for damage to be negated.
     */
    public static boolean negateDamage(ItemStack stack, int level, Random rand)
    {
        if (stack.getItem() instanceof ItemArmor && rand.nextFloat() < 0.6F)
        {
            return false;
        }
        else
        {
            return rand.nextInt(level + 1) > 0;
        }
    }
}
