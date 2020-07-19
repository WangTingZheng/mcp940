package net.minecraft.util.datafix;

import net.minecraft.nbt.NBTTagCompound;

public interface IFixableData
{
    int getFixVersion();

    NBTTagCompound fixTagCompound(NBTTagCompound compound);
}
