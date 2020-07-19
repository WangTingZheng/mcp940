package net.minecraft.util.datafix.fixes;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.datafix.IFixableData;

public class SpawnerEntityTypes implements IFixableData
{
    public int getFixVersion()
    {
        return 107;
    }

    public NBTTagCompound fixTagCompound(NBTTagCompound compound)
    {
        if (!"MobSpawner".equals(compound.getString("id")))
        {
            return compound;
        }
        else
        {
            if (compound.hasKey("EntityId", 8))
            {
                String s = compound.getString("EntityId");
                NBTTagCompound nbttagcompound = compound.getCompoundTag("SpawnData");
                nbttagcompound.setString("id", s.isEmpty() ? "Pig" : s);
                compound.setTag("SpawnData", nbttagcompound);
                compound.removeTag("EntityId");
            }

            if (compound.hasKey("SpawnPotentials", 9))
            {
                NBTTagList nbttaglist = compound.getTagList("SpawnPotentials", 10);

                for (int i = 0; i < nbttaglist.tagCount(); ++i)
                {
                    NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);

                    if (nbttagcompound1.hasKey("Type", 8))
                    {
                        NBTTagCompound nbttagcompound2 = nbttagcompound1.getCompoundTag("Properties");
                        nbttagcompound2.setString("id", nbttagcompound1.getString("Type"));
                        nbttagcompound1.setTag("Entity", nbttagcompound2);
                        nbttagcompound1.removeTag("Type");
                        nbttagcompound1.removeTag("Properties");
                    }
                }
            }

            return compound;
        }
    }
}
