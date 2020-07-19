package net.minecraft.util.math;

import net.minecraft.entity.Entity;

public class ChunkPos
{
    /** The X position of this Chunk Coordinate Pair */
    public final int x;

    /** The Z position of this Chunk Coordinate Pair */
    public final int z;
    private int cachedHashCode = 0;

    public ChunkPos(int x, int z)
    {
        this.x = x;
        this.z = z;
    }

    public ChunkPos(BlockPos pos)
    {
        this.x = pos.getX() >> 4;
        this.z = pos.getZ() >> 4;
    }

    /**
     * Converts the chunk coordinate pair to a long
     */
    public static long asLong(int x, int z)
    {
        return (long)x & 4294967295L | ((long)z & 4294967295L) << 32;
    }

    public int hashCode()
    {
        if (this.cachedHashCode != 0)
        {
            return this.cachedHashCode;
        }
        else
        {
            int i = 1664525 * this.x + 1013904223;
            int j = 1664525 * (this.z ^ -559038737) + 1013904223;
            this.cachedHashCode = i ^ j;
            return this.cachedHashCode;
        }
    }

    public boolean equals(Object p_equals_1_)
    {
        if (this == p_equals_1_)
        {
            return true;
        }
        else if (!(p_equals_1_ instanceof ChunkPos))
        {
            return false;
        }
        else
        {
            ChunkPos chunkpos = (ChunkPos)p_equals_1_;
            return this.x == chunkpos.x && this.z == chunkpos.z;
        }
    }

    public double getDistanceSq(Entity entityIn)
    {
        double d0 = (double)(this.x * 16 + 8);
        double d1 = (double)(this.z * 16 + 8);
        double d2 = d0 - entityIn.posX;
        double d3 = d1 - entityIn.posZ;
        return d2 * d2 + d3 * d3;
    }

    /**
     * Get the first world X coordinate that belongs to this Chunk
     */
    public int getXStart()
    {
        return this.x << 4;
    }

    /**
     * Get the first world Z coordinate that belongs to this Chunk
     */
    public int getZStart()
    {
        return this.z << 4;
    }

    /**
     * Get the last world X coordinate that belongs to this Chunk
     */
    public int getXEnd()
    {
        return (this.x << 4) + 15;
    }

    /**
     * Get the last world Z coordinate that belongs to this Chunk
     */
    public int getZEnd()
    {
        return (this.z << 4) + 15;
    }

    /**
     * Get the World coordinates of the Block with the given Chunk coordinates relative to this chunk
     */
    public BlockPos getBlock(int x, int y, int z)
    {
        return new BlockPos((this.x << 4) + x, y, (this.z << 4) + z);
    }

    public String toString()
    {
        return "[" + this.x + ", " + this.z + "]";
    }
}
