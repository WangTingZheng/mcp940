package net.minecraft.src;

import java.util.Comparator;

public class CustomItemsComparator implements Comparator
{
    public int compare(Object p_compare_1_, Object p_compare_2_)
    {
        CustomItemProperties customitemproperties = (CustomItemProperties)p_compare_1_;
        CustomItemProperties customitemproperties1 = (CustomItemProperties)p_compare_2_;

        if (customitemproperties.weight != customitemproperties1.weight)
        {
            return customitemproperties1.weight - customitemproperties.weight;
        }
        else
        {
            return !Config.equals(customitemproperties.basePath, customitemproperties1.basePath) ? customitemproperties.basePath.compareTo(customitemproperties1.basePath) : customitemproperties.name.compareTo(customitemproperties1.name);
        }
    }
}
