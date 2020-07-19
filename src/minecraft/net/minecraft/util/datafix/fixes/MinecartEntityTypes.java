package net.minecraft.util.datafix.fixes;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.datafix.IFixableData;

public class MinecartEntityTypes implements IFixableData
{
    private static final List<String> MINECART_TYPE_LIST = Lists.newArrayList("MinecartRideable", "MinecartChest", "MinecartFurnace", "MinecartTNT", "MinecartSpawner", "MinecartHopper", "MinecartCommandBlock");

    public int getFixVersion()
    {
        return 106;
    }

    public NBTTagCompound fixTagCompound(NBTTagCompound compound)
    {
        if ("Minecart".equals(compound.getString("id")))
        {
            String s = "MinecartRideable";
            int i = compound.getInteger("Type");

            if (i > 0 && i < MINECART_TYPE_LIST.size())
            {
                s = MINECART_TYPE_LIST.get(i);
            }

            compound.setString("id", s);
            compound.removeTag("Type");
        }

        return compound;
    }
}
