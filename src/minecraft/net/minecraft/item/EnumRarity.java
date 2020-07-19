package net.minecraft.item;

import net.minecraft.util.text.TextFormatting;

public enum EnumRarity
{
    COMMON(TextFormatting.WHITE, "Common"),
    UNCOMMON(TextFormatting.YELLOW, "Uncommon"),
    RARE(TextFormatting.AQUA, "Rare"),
    EPIC(TextFormatting.LIGHT_PURPLE, "Epic");

    /**
     * A decimal representation of the hex color codes of a the color assigned to this rarity type. (13 becomes d as in
     * \247d which is light purple)
     */
    public final TextFormatting rarityColor;

    /** Rarity name. */
    public final String rarityName;

    private EnumRarity(TextFormatting color, String name)
    {
        this.rarityColor = color;
        this.rarityName = name;
    }
}
