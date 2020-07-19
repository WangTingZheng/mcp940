package net.minecraft.util;

public class FrameTimer
{
    /** An array with the last 240 frames */
    private final long[] frames = new long[240];

    /** The last index used when 240 frames have been set */
    private int lastIndex;

    /** A counter */
    private int counter;

    /** The next index to use in the array */
    private int index;

    /**
     * Add a frame at the next index in the array frames
     */
    public void addFrame(long runningTime)
    {
        this.frames[this.index] = runningTime;
        ++this.index;

        if (this.index == 240)
        {
            this.index = 0;
        }

        if (this.counter < 240)
        {
            this.lastIndex = 0;
            ++this.counter;
        }
        else
        {
            this.lastIndex = this.parseIndex(this.index + 1);
        }
    }

    /**
     * Return a value from time and multiplier to display the lagometer
     */
    public int getLagometerValue(long time, int multiplier)
    {
        double d0 = (double)time / 1.6666666E7D;
        return (int)(d0 * (double)multiplier);
    }

    /**
     * Return the last index used when 240 frames have been set
     */
    public int getLastIndex()
    {
        return this.lastIndex;
    }

    /**
     * Return the index of the next frame in the array
     */
    public int getIndex()
    {
        return this.index;
    }

    /**
     * Change 240 to 0
     */
    public int parseIndex(int rawIndex)
    {
        return rawIndex % 240;
    }

    /**
     * Return the array of frames
     */
    public long[] getFrames()
    {
        return this.frames;
    }
}
