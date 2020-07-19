package net.minecraft.util.datafix;

import net.minecraft.nbt.NBTTagCompound;

public interface IDataWalker
{
    NBTTagCompound process(IDataFixer fixer, NBTTagCompound compound, int versionIn);
}
