package net.minecraft.util.datafix.fixes;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.datafix.IFixableData;

public class SkeletonSplit implements IFixableData
{
    public int getFixVersion()
    {
        return 701;
    }

    public NBTTagCompound fixTagCompound(NBTTagCompound compound)
    {
        String s = compound.getString("id");

        if ("Skeleton".equals(s))
        {
            int i = compound.getInteger("SkeletonType");

            if (i == 1)
            {
                compound.setString("id", "WitherSkeleton");
            }
            else if (i == 2)
            {
                compound.setString("id", "Stray");
            }

            compound.removeTag("SkeletonType");
        }

        return compound;
    }
}
