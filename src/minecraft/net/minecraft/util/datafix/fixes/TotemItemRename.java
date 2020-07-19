package net.minecraft.util.datafix.fixes;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.datafix.IFixableData;

public class TotemItemRename implements IFixableData
{
    public int getFixVersion()
    {
        return 820;
    }

    public NBTTagCompound fixTagCompound(NBTTagCompound compound)
    {
        if ("minecraft:totem".equals(compound.getString("id")))
        {
            compound.setString("id", "minecraft:totem_of_undying");
        }

        return compound;
    }
}
