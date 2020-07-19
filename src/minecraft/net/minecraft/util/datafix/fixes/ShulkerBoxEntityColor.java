package net.minecraft.util.datafix.fixes;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.datafix.IFixableData;

public class ShulkerBoxEntityColor implements IFixableData
{
    public int getFixVersion()
    {
        return 808;
    }

    public NBTTagCompound fixTagCompound(NBTTagCompound compound)
    {
        if ("minecraft:shulker".equals(compound.getString("id")) && !compound.hasKey("Color", 99))
        {
            compound.setByte("Color", (byte)10);
        }

        return compound;
    }
}
