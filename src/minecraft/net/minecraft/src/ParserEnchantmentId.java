package net.minecraft.src;

import net.minecraft.enchantment.Enchantment;

public class ParserEnchantmentId implements IParserInt
{
    public int parse(String p_parse_1_, int p_parse_2_)
    {
        Enchantment enchantment = Enchantment.getEnchantmentByLocation(p_parse_1_);
        return enchantment == null ? p_parse_2_ : Enchantment.getEnchantmentID(enchantment);
    }
}
