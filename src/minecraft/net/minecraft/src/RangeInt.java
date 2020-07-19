package net.minecraft.src;

public class RangeInt
{
    private int min;
    private int max;

    public RangeInt(int p_i77_1_, int p_i77_2_)
    {
        this.min = Math.min(p_i77_1_, p_i77_2_);
        this.max = Math.max(p_i77_1_, p_i77_2_);
    }

    public boolean isInRange(int p_isInRange_1_)
    {
        if (p_isInRange_1_ < this.min)
        {
            return false;
        }
        else
        {
            return p_isInRange_1_ <= this.max;
        }
    }

    public int getMin()
    {
        return this.min;
    }

    public int getMax()
    {
        return this.max;
    }

    public String toString()
    {
        return "min: " + this.min + ", max: " + this.max;
    }
}
