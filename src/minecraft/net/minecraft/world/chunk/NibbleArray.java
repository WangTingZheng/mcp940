package net.minecraft.world.chunk;

public class NibbleArray
{
    /**
     * Byte array of data stored in this holder. Possibly a light map or some chunk data. Data is accessed in 4-bit
     * pieces.
     */
    private final byte[] data;

    public NibbleArray()
    {
        this.data = new byte[2048];
    }

    public NibbleArray(byte[] storageArray)
    {
        this.data = storageArray;

        if (storageArray.length != 2048)
        {
            throw new IllegalArgumentException("ChunkNibbleArrays should be 2048 bytes not: " + storageArray.length);
        }
    }

    /**
     * Returns the nibble of data corresponding to the passed in x, y, z. y is at most 6 bits, z is at most 4.
     */
    public int get(int x, int y, int z)
    {
        return this.getFromIndex(this.getCoordinateIndex(x, y, z));
    }

    /**
     * Arguments are x, y, z, val. Sets the nibble of data at x << 11 | z << 7 | y to val.
     */
    public void set(int x, int y, int z, int value)
    {
        this.setIndex(this.getCoordinateIndex(x, y, z), value);
    }

    private int getCoordinateIndex(int x, int y, int z)
    {
        return y << 8 | z << 4 | x;
    }

    public int getFromIndex(int index)
    {
        int i = this.getNibbleIndex(index);
        return this.isLowerNibble(index) ? this.data[i] & 15 : this.data[i] >> 4 & 15;
    }

    public void setIndex(int index, int value)
    {
        int i = this.getNibbleIndex(index);

        if (this.isLowerNibble(index))
        {
            this.data[i] = (byte)(this.data[i] & 240 | value & 15);
        }
        else
        {
            this.data[i] = (byte)(this.data[i] & 15 | (value & 15) << 4);
        }
    }

    private boolean isLowerNibble(int index)
    {
        return (index & 1) == 0;
    }

    private int getNibbleIndex(int index)
    {
        return index >> 1;
    }

    public byte[] getData()
    {
        return this.data;
    }
}
