package net.minecraft.entity.ai.attributes;

import java.util.Collection;
import java.util.UUID;
import javax.annotation.Nullable;

public interface IAttributeInstance
{
    /**
     * Get the Attribute this is an instance of
     */
    IAttribute getAttribute();

    double getBaseValue();

    void setBaseValue(double baseValue);

    Collection<AttributeModifier> getModifiersByOperation(int operation);

    Collection<AttributeModifier> getModifiers();

    boolean hasModifier(AttributeModifier modifier);

    @Nullable

    /**
     * Returns attribute modifier, if any, by the given UUID
     */
    AttributeModifier getModifier(UUID uuid);

    void applyModifier(AttributeModifier modifier);

    void removeModifier(AttributeModifier modifier);

    void removeModifier(UUID p_188479_1_);

    void removeAllModifiers();

    double getAttributeValue();
}
