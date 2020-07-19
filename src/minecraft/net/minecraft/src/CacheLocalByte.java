package net.minecraft.src;

public class CacheLocalByte
{
    private int maxX = 18;
    private int maxY = 128;
    private int maxZ = 18;
    private int offsetX = 0;
    private int offsetY = 0;
    private int offsetZ = 0;
    private byte[][][] cache = (byte[][][])null;
    private byte[] lastZs = null;
    private int lastDz = 0;

    public CacheLocalByte(int p_i20_1_, int p_i20_2_, int p_i20_3_)
    {
        this.maxX = p_i20_1_;
        this.maxY = p_i20_2_;
        this.maxZ = p_i20_3_;
        this.cache = new byte[p_i20_1_][p_i20_2_][p_i20_3_];
        this.resetCache();
    }

    public void resetCache()
    {
        for (int i = 0; i < this.maxX; ++i)
        {
            byte[][] abyte = this.cache[i];

            for (int j = 0; j < this.maxY; ++j)
            {
                byte[] abyte1 = abyte[j];

                for (int k = 0; k < this.maxZ; ++k)
                {
                    abyte1[k] = -1;
                }
            }
        }
    }

    public void setOffset(int p_setOffset_1_, int p_setOffset_2_, int p_setOffset_3_)
    {
        this.offsetX = p_setOffset_1_;
        this.offsetY = p_setOffset_2_;
        this.offsetZ = p_setOffset_3_;
        this.resetCache();
    }

    public byte get(int p_get_1_, int p_get_2_, int p_get_3_)
    {
        try
        {
            this.lastZs = this.cache[p_get_1_ - this.offsetX][p_get_2_ - this.offsetY];
            this.lastDz = p_get_3_ - this.offsetZ;
            return this.lastZs[this.lastDz];
        }
        catch (ArrayIndexOutOfBoundsException arrayindexoutofboundsexception)
        {
            arrayindexoutofboundsexception.printStackTrace();
            return -1;
        }
    }

    public void setLast(byte p_setLast_1_)
    {
        try
        {
            this.lastZs[this.lastDz] = p_setLast_1_;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }
}
