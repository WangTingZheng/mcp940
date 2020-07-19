package net.minecraft.nbt;

public class NBTSizeTracker
{
    public static final NBTSizeTracker INFINITE = new NBTSizeTracker(0L)
    {
        public void read(long bits)
        {
        }
    };
    private final long max;
    private long read;

    public NBTSizeTracker(long max)
    {
        this.max = max;
    }

    /**
     * Tracks the reading of the given amount of bits(!)
     */
    public void read(long bits)
    {
        this.read += bits / 8L;

        if (this.read > this.max)
        {
            throw new RuntimeException("Tried to read NBT tag that was too big; tried to allocate: " + this.read + "bytes where max allowed: " + this.max);
        }
    }
}
