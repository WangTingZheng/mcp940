package net.minecraft.world.gen.layer;

import com.google.common.collect.Lists;
import java.util.List;

public class IntCache
{
    private static int intCacheSize = 256;
    private static final List<int[]> freeSmallArrays = Lists.<int[]>newArrayList();
    private static final List<int[]> inUseSmallArrays = Lists.<int[]>newArrayList();
    private static final List<int[]> freeLargeArrays = Lists.<int[]>newArrayList();
    private static final List<int[]> inUseLargeArrays = Lists.<int[]>newArrayList();

    public static synchronized int[] getIntCache(int size)
    {
        if (size <= 256)
        {
            if (freeSmallArrays.isEmpty())
            {
                int[] aint4 = new int[256];
                inUseSmallArrays.add(aint4);
                return aint4;
            }
            else
            {
                int[] aint3 = freeSmallArrays.remove(freeSmallArrays.size() - 1);
                inUseSmallArrays.add(aint3);
                return aint3;
            }
        }
        else if (size > intCacheSize)
        {
            intCacheSize = size;
            freeLargeArrays.clear();
            inUseLargeArrays.clear();
            int[] aint2 = new int[intCacheSize];
            inUseLargeArrays.add(aint2);
            return aint2;
        }
        else if (freeLargeArrays.isEmpty())
        {
            int[] aint1 = new int[intCacheSize];
            inUseLargeArrays.add(aint1);
            return aint1;
        }
        else
        {
            int[] aint = freeLargeArrays.remove(freeLargeArrays.size() - 1);
            inUseLargeArrays.add(aint);
            return aint;
        }
    }

    /**
     * Mark all pre-allocated arrays as available for re-use by moving them to the appropriate free lists.
     */
    public static synchronized void resetIntCache()
    {
        if (!freeLargeArrays.isEmpty())
        {
            freeLargeArrays.remove(freeLargeArrays.size() - 1);
        }

        if (!freeSmallArrays.isEmpty())
        {
            freeSmallArrays.remove(freeSmallArrays.size() - 1);
        }

        freeLargeArrays.addAll(inUseLargeArrays);
        freeSmallArrays.addAll(inUseSmallArrays);
        inUseLargeArrays.clear();
        inUseSmallArrays.clear();
    }

    /**
     * Gets a human-readable string that indicates the sizes of all the cache fields.  Basically a synchronized static
     * toString.
     */
    public static synchronized String getCacheSizes()
    {
        return "cache: " + freeLargeArrays.size() + ", tcache: " + freeSmallArrays.size() + ", allocated: " + inUseLargeArrays.size() + ", tallocated: " + inUseSmallArrays.size();
    }
}
