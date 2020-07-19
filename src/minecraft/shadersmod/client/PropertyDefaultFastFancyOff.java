package shadersmod.client;

import net.minecraft.src.Config;

public class PropertyDefaultFastFancyOff extends Property
{
    public static final String[] PROPERTY_VALUES = new String[] {"default", "fast", "fancy", "off"};
    public static final String[] USER_VALUES = new String[] {"Default", "Fast", "Fancy", "OFF"};

    public PropertyDefaultFastFancyOff(String propertyName, String userName, int defaultValue)
    {
        super(propertyName, PROPERTY_VALUES, userName, USER_VALUES, defaultValue);
    }

    public boolean isDefault()
    {
        return this.getValue() == 0;
    }

    public boolean isFast()
    {
        return this.getValue() == 1;
    }

    public boolean isFancy()
    {
        return this.getValue() == 2;
    }

    public boolean isOff()
    {
        return this.getValue() == 3;
    }

    public boolean setPropertyValue(String propVal)
    {
        if (Config.equals(propVal, "none"))
        {
            propVal = "off";
        }

        return super.setPropertyValue(propVal);
    }
}
