package net.minecraft.util.datafix.fixes;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.datafix.IFixableData;

public class PaintingDirection implements IFixableData
{
    public int getFixVersion()
    {
        return 111;
    }

    public NBTTagCompound fixTagCompound(NBTTagCompound compound)
    {
        String s = compound.getString("id");
        boolean flag = "Painting".equals(s);
        boolean flag1 = "ItemFrame".equals(s);

        if ((flag || flag1) && !compound.hasKey("Facing", 99))
        {
            EnumFacing enumfacing;

            if (compound.hasKey("Direction", 99))
            {
                enumfacing = EnumFacing.getHorizontal(compound.getByte("Direction"));
                compound.setInteger("TileX", compound.getInteger("TileX") + enumfacing.getFrontOffsetX());
                compound.setInteger("TileY", compound.getInteger("TileY") + enumfacing.getFrontOffsetY());
                compound.setInteger("TileZ", compound.getInteger("TileZ") + enumfacing.getFrontOffsetZ());
                compound.removeTag("Direction");

                if (flag1 && compound.hasKey("ItemRotation", 99))
                {
                    compound.setByte("ItemRotation", (byte)(compound.getByte("ItemRotation") * 2));
                }
            }
            else
            {
                enumfacing = EnumFacing.getHorizontal(compound.getByte("Dir"));
                compound.removeTag("Dir");
            }

            compound.setByte("Facing", (byte)enumfacing.getHorizontalIndex());
        }

        return compound;
    }
}
