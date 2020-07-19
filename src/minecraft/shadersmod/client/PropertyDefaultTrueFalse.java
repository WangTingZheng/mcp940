package shadersmod.client;

import net.minecraft.src.Lang;

public class PropertyDefaultTrueFalse extends Property
{
    public static final String[] PROPERTY_VALUES = new String[] {"default", "true", "false"};
    public static final String[] USER_VALUES = new String[] {"Default", "ON", "OFF"};

    public PropertyDefaultTrueFalse(String propertyName, String userName, int defaultValue)
    {
        super(propertyName, PROPERTY_VALUES, userName, USER_VALUES, defaultValue);
    }

    public String getUserValue()
    {
        if (this.isDefault())
        {
            return Lang.getDefault();
        }
        else if (this.isTrue())
        {
            return Lang.getOn();
        }
        else
        {
            return this.isFalse() ? Lang.getOff() : super.getUserValue();
        }
    }

    public boolean isDefault()
    {
        return this.getValue() == 0;
    }

    public boolean isTrue()
    {
        return this.getValue() == 1;
    }

    public boolean isFalse()
    {
        return this.getValue() == 2;
    }
}
