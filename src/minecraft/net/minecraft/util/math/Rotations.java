package net.minecraft.util.math;

import net.minecraft.nbt.NBTTagFloat;
import net.minecraft.nbt.NBTTagList;

public class Rotations
{
    /** Rotation on the X axis */
    protected final float x;

    /** Rotation on the Y axis */
    protected final float y;

    /** Rotation on the Z axis */
    protected final float z;

    public Rotations(float x, float y, float z)
    {
        this.x = !Float.isInfinite(x) && !Float.isNaN(x) ? x % 360.0F : 0.0F;
        this.y = !Float.isInfinite(y) && !Float.isNaN(y) ? y % 360.0F : 0.0F;
        this.z = !Float.isInfinite(z) && !Float.isNaN(z) ? z % 360.0F : 0.0F;
    }

    public Rotations(NBTTagList nbt)
    {
        this(nbt.getFloatAt(0), nbt.getFloatAt(1), nbt.getFloatAt(2));
    }

    public NBTTagList writeToNBT()
    {
        NBTTagList nbttaglist = new NBTTagList();
        nbttaglist.appendTag(new NBTTagFloat(this.x));
        nbttaglist.appendTag(new NBTTagFloat(this.y));
        nbttaglist.appendTag(new NBTTagFloat(this.z));
        return nbttaglist;
    }

    public boolean equals(Object p_equals_1_)
    {
        if (!(p_equals_1_ instanceof Rotations))
        {
            return false;
        }
        else
        {
            Rotations rotations = (Rotations)p_equals_1_;
            return this.x == rotations.x && this.y == rotations.y && this.z == rotations.z;
        }
    }

    /**
     * Gets the X axis rotation
     */
    public float getX()
    {
        return this.x;
    }

    /**
     * Gets the Y axis rotation
     */
    public float getY()
    {
        return this.y;
    }

    /**
     * Gets the Z axis rotation
     */
    public float getZ()
    {
        return this.z;
    }
}
