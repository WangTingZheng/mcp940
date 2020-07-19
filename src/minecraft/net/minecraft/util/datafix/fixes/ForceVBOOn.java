package net.minecraft.util.datafix.fixes;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.datafix.IFixableData;

public class ForceVBOOn implements IFixableData
{
    public int getFixVersion()
    {
        return 505;
    }

    public NBTTagCompound fixTagCompound(NBTTagCompound compound)
    {
        compound.setString("useVbo", "true");
        return compound;
    }
}
