package net.minecraft.nbt;

abstract class NBTPrimitive extends NBTBase
{
    public abstract long getLong();

    public abstract int getInt();

    public abstract short getShort();

    public abstract byte getByte();

    public abstract double getDouble();

    public abstract float getFloat();
}
