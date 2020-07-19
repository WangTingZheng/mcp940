package net.minecraft.util.datafix.fixes;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.datafix.IFixableData;

public class RedundantChanceTags implements IFixableData
{
    public int getFixVersion()
    {
        return 113;
    }

    public NBTTagCompound fixTagCompound(NBTTagCompound compound)
    {
        if (compound.hasKey("HandDropChances", 9))
        {
            NBTTagList nbttaglist = compound.getTagList("HandDropChances", 5);

            if (nbttaglist.tagCount() == 2 && nbttaglist.getFloatAt(0) == 0.0F && nbttaglist.getFloatAt(1) == 0.0F)
            {
                compound.removeTag("HandDropChances");
            }
        }

        if (compound.hasKey("ArmorDropChances", 9))
        {
            NBTTagList nbttaglist1 = compound.getTagList("ArmorDropChances", 5);

            if (nbttaglist1.tagCount() == 4 && nbttaglist1.getFloatAt(0) == 0.0F && nbttaglist1.getFloatAt(1) == 0.0F && nbttaglist1.getFloatAt(2) == 0.0F && nbttaglist1.getFloatAt(3) == 0.0F)
            {
                compound.removeTag("ArmorDropChances");
            }
        }

        return compound;
    }
}
