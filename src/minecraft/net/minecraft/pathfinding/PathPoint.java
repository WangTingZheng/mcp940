package net.minecraft.pathfinding;

import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.MathHelper;

public class PathPoint
{
    /** The x coordinate of this point */
    public final int x;

    /** The y coordinate of this point */
    public final int y;

    /** The z coordinate of this point */
    public final int z;

    /** A hash of the coordinates used to identify this point */
    private final int hash;

    /** The index of this point in its assigned path */
    public int index = -1;

    /** The distance along the path to this point */
    public float totalPathDistance;

    /** The linear distance to the next point */
    public float distanceToNext;

    /** The distance to the target */
    public float distanceToTarget;

    /** The point preceding this in its assigned path */
    public PathPoint previous;

    /** True if the pathfinder has already visited this point */
    public boolean visited;
    public float distanceFromOrigin;
    public float cost;
    public float costMalus;
    public PathNodeType nodeType = PathNodeType.BLOCKED;

    public PathPoint(int x, int y, int z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
        this.hash = makeHash(x, y, z);
    }

    public PathPoint cloneMove(int x, int y, int z)
    {
        PathPoint pathpoint = new PathPoint(x, y, z);
        pathpoint.index = this.index;
        pathpoint.totalPathDistance = this.totalPathDistance;
        pathpoint.distanceToNext = this.distanceToNext;
        pathpoint.distanceToTarget = this.distanceToTarget;
        pathpoint.previous = this.previous;
        pathpoint.visited = this.visited;
        pathpoint.distanceFromOrigin = this.distanceFromOrigin;
        pathpoint.cost = this.cost;
        pathpoint.costMalus = this.costMalus;
        pathpoint.nodeType = this.nodeType;
        return pathpoint;
    }

    public static int makeHash(int x, int y, int z)
    {
        return y & 255 | (x & 32767) << 8 | (z & 32767) << 24 | (x < 0 ? Integer.MIN_VALUE : 0) | (z < 0 ? 32768 : 0);
    }

    /**
     * Returns the linear distance to another path point
     */
    public float distanceTo(PathPoint pathpointIn)
    {
        float f = (float)(pathpointIn.x - this.x);
        float f1 = (float)(pathpointIn.y - this.y);
        float f2 = (float)(pathpointIn.z - this.z);
        return MathHelper.sqrt(f * f + f1 * f1 + f2 * f2);
    }

    /**
     * Returns the squared distance to another path point
     */
    public float distanceToSquared(PathPoint pathpointIn)
    {
        float f = (float)(pathpointIn.x - this.x);
        float f1 = (float)(pathpointIn.y - this.y);
        float f2 = (float)(pathpointIn.z - this.z);
        return f * f + f1 * f1 + f2 * f2;
    }

    public float distanceManhattan(PathPoint p_186281_1_)
    {
        float f = (float)Math.abs(p_186281_1_.x - this.x);
        float f1 = (float)Math.abs(p_186281_1_.y - this.y);
        float f2 = (float)Math.abs(p_186281_1_.z - this.z);
        return f + f1 + f2;
    }

    public boolean equals(Object p_equals_1_)
    {
        if (!(p_equals_1_ instanceof PathPoint))
        {
            return false;
        }
        else
        {
            PathPoint pathpoint = (PathPoint)p_equals_1_;
            return this.hash == pathpoint.hash && this.x == pathpoint.x && this.y == pathpoint.y && this.z == pathpoint.z;
        }
    }

    public int hashCode()
    {
        return this.hash;
    }

    /**
     * Returns true if this point has already been assigned to a path
     */
    public boolean isAssigned()
    {
        return this.index >= 0;
    }

    public String toString()
    {
        return this.x + ", " + this.y + ", " + this.z;
    }

    public static PathPoint createFromBuffer(PacketBuffer buf)
    {
        PathPoint pathpoint = new PathPoint(buf.readInt(), buf.readInt(), buf.readInt());
        pathpoint.distanceFromOrigin = buf.readFloat();
        pathpoint.cost = buf.readFloat();
        pathpoint.costMalus = buf.readFloat();
        pathpoint.visited = buf.readBoolean();
        pathpoint.nodeType = PathNodeType.values()[buf.readInt()];
        pathpoint.distanceToTarget = buf.readFloat();
        return pathpoint;
    }
}
