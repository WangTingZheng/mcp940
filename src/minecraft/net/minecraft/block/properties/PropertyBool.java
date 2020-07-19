package net.minecraft.block.properties;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSet;
import java.util.Collection;

public class PropertyBool extends PropertyHelper<Boolean>
{
    private final ImmutableSet<Boolean> allowedValues = ImmutableSet.<Boolean>of(Boolean.valueOf(true), Boolean.valueOf(false));

    protected PropertyBool(String name)
    {
        super(name, Boolean.class);
    }

    public Collection<Boolean> getAllowedValues()
    {
        return this.allowedValues;
    }

    public static PropertyBool create(String name)
    {
        return new PropertyBool(name);
    }

    public Optional<Boolean> parseValue(String value)
    {
        return !"true".equals(value) && !"false".equals(value) ? Optional.absent() : Optional.of(Boolean.valueOf(value));
    }

    /**
     * Get the name for the given value.
     */
    public String getName(Boolean value)
    {
        return value.toString();
    }

    public boolean equals(Object p_equals_1_)
    {
        if (this == p_equals_1_)
        {
            return true;
        }
        else if (p_equals_1_ instanceof PropertyBool && super.equals(p_equals_1_))
        {
            PropertyBool propertybool = (PropertyBool)p_equals_1_;
            return this.allowedValues.equals(propertybool.allowedValues);
        }
        else
        {
            return false;
        }
    }

    public int hashCode()
    {
        return 31 * super.hashCode() + this.allowedValues.hashCode();
    }
}
