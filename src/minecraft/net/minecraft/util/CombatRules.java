package net.minecraft.util;

import net.minecraft.util.math.MathHelper;

public class CombatRules
{
    public static float getDamageAfterAbsorb(float damage, float totalArmor, float toughnessAttribute)
    {
        float f = 2.0F + toughnessAttribute / 4.0F;
        float f1 = MathHelper.clamp(totalArmor - damage / f, totalArmor * 0.2F, 20.0F);
        return damage * (1.0F - f1 / 25.0F);
    }

    public static float getDamageAfterMagicAbsorb(float damage, float enchantModifiers)
    {
        float f = MathHelper.clamp(enchantModifiers, 0.0F, 20.0F);
        return damage * (1.0F - f / 25.0F);
    }
}
