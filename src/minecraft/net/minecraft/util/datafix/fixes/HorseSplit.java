package net.minecraft.util.datafix.fixes;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.datafix.IFixableData;

public class HorseSplit implements IFixableData
{
    public int getFixVersion()
    {
        return 703;
    }

    public NBTTagCompound fixTagCompound(NBTTagCompound compound)
    {
        if ("EntityHorse".equals(compound.getString("id")))
        {
            int i = compound.getInteger("Type");

            switch (i)
            {
                case 0:
                default:
                    compound.setString("id", "Horse");
                    break;

                case 1:
                    compound.setString("id", "Donkey");
                    break;

                case 2:
                    compound.setString("id", "Mule");
                    break;

                case 3:
                    compound.setString("id", "ZombieHorse");
                    break;

                case 4:
                    compound.setString("id", "SkeletonHorse");
            }

            compound.removeTag("Type");
        }

        return compound;
    }
}
