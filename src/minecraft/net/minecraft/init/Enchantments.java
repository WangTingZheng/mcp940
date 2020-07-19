package net.minecraft.init;

import javax.annotation.Nullable;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.util.ResourceLocation;

public class Enchantments
{
    public static final Enchantment PROTECTION = getRegisteredEnchantment("protection");

    /** Protection against fire */
    public static final Enchantment FIRE_PROTECTION = getRegisteredEnchantment("fire_protection");
    public static final Enchantment FEATHER_FALLING = getRegisteredEnchantment("feather_falling");
    public static final Enchantment BLAST_PROTECTION = getRegisteredEnchantment("blast_protection");
    public static final Enchantment PROJECTILE_PROTECTION = getRegisteredEnchantment("projectile_protection");
    public static final Enchantment RESPIRATION = getRegisteredEnchantment("respiration");
    public static final Enchantment AQUA_AFFINITY = getRegisteredEnchantment("aqua_affinity");
    public static final Enchantment THORNS = getRegisteredEnchantment("thorns");
    public static final Enchantment DEPTH_STRIDER = getRegisteredEnchantment("depth_strider");
    public static final Enchantment FROST_WALKER = getRegisteredEnchantment("frost_walker");
    public static final Enchantment BINDING_CURSE = getRegisteredEnchantment("binding_curse");
    public static final Enchantment SHARPNESS = getRegisteredEnchantment("sharpness");
    public static final Enchantment SMITE = getRegisteredEnchantment("smite");
    public static final Enchantment BANE_OF_ARTHROPODS = getRegisteredEnchantment("bane_of_arthropods");
    public static final Enchantment KNOCKBACK = getRegisteredEnchantment("knockback");

    /** Lights mobs on fire */
    public static final Enchantment FIRE_ASPECT = getRegisteredEnchantment("fire_aspect");
    public static final Enchantment LOOTING = getRegisteredEnchantment("looting");
    public static final Enchantment SWEEPING = getRegisteredEnchantment("sweeping");
    public static final Enchantment EFFICIENCY = getRegisteredEnchantment("efficiency");
    public static final Enchantment SILK_TOUCH = getRegisteredEnchantment("silk_touch");
    public static final Enchantment UNBREAKING = getRegisteredEnchantment("unbreaking");
    public static final Enchantment FORTUNE = getRegisteredEnchantment("fortune");
    public static final Enchantment POWER = getRegisteredEnchantment("power");
    public static final Enchantment PUNCH = getRegisteredEnchantment("punch");
    public static final Enchantment FLAME = getRegisteredEnchantment("flame");
    public static final Enchantment INFINITY = getRegisteredEnchantment("infinity");
    public static final Enchantment LUCK_OF_THE_SEA = getRegisteredEnchantment("luck_of_the_sea");
    public static final Enchantment LURE = getRegisteredEnchantment("lure");
    public static final Enchantment MENDING = getRegisteredEnchantment("mending");
    public static final Enchantment VANISHING_CURSE = getRegisteredEnchantment("vanishing_curse");

    @Nullable
    private static Enchantment getRegisteredEnchantment(String id)
    {
        Enchantment enchantment = Enchantment.REGISTRY.getObject(new ResourceLocation(id));

        if (enchantment == null)
        {
            throw new IllegalStateException("Invalid Enchantment requested: " + id);
        }
        else
        {
            return enchantment;
        }
    }

    static
    {
        if (!Bootstrap.isRegistered())
        {
            throw new RuntimeException("Accessed Enchantments before Bootstrap!");
        }
    }
}
