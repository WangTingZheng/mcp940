package net.minecraft.pathfinding;

public class PathHeap
{
    /** Contains the points in this path */
    private PathPoint[] pathPoints = new PathPoint[128];

    /** The number of points in this path */
    private int count;

    /**
     * Adds a point to the path
     */
    public PathPoint addPoint(PathPoint point)
    {
        if (point.index >= 0)
        {
            throw new IllegalStateException("OW KNOWS!");
        }
        else
        {
            if (this.count == this.pathPoints.length)
            {
                PathPoint[] apathpoint = new PathPoint[this.count << 1];
                System.arraycopy(this.pathPoints, 0, apathpoint, 0, this.count);
                this.pathPoints = apathpoint;
            }

            this.pathPoints[this.count] = point;
            point.index = this.count;
            this.sortBack(this.count++);
            return point;
        }
    }

    /**
     * Clears the path
     */
    public void clearPath()
    {
        this.count = 0;
    }

    /**
     * Returns and removes the first point in the path
     */
    public PathPoint dequeue()
    {
        PathPoint pathpoint = this.pathPoints[0];
        this.pathPoints[0] = this.pathPoints[--this.count];
        this.pathPoints[this.count] = null;

        if (this.count > 0)
        {
            this.sortForward(0);
        }

        pathpoint.index = -1;
        return pathpoint;
    }

    /**
     * Changes the provided point's distance to target
     */
    public void changeDistance(PathPoint point, float distance)
    {
        float f = point.distanceToTarget;
        point.distanceToTarget = distance;

        if (distance < f)
        {
            this.sortBack(point.index);
        }
        else
        {
            this.sortForward(point.index);
        }
    }

    /**
     * Sorts a point to the left
     */
    private void sortBack(int index)
    {
        PathPoint pathpoint = this.pathPoints[index];
        int i;

        for (float f = pathpoint.distanceToTarget; index > 0; index = i)
        {
            i = index - 1 >> 1;
            PathPoint pathpoint1 = this.pathPoints[i];

            if (f >= pathpoint1.distanceToTarget)
            {
                break;
            }

            this.pathPoints[index] = pathpoint1;
            pathpoint1.index = index;
        }

        this.pathPoints[index] = pathpoint;
        pathpoint.index = index;
    }

    /**
     * Sorts a point to the right
     */
    private void sortForward(int index)
    {
        PathPoint pathpoint = this.pathPoints[index];
        float f = pathpoint.distanceToTarget;

        while (true)
        {
            int i = 1 + (index << 1);
            int j = i + 1;

            if (i >= this.count)
            {
                break;
            }

            PathPoint pathpoint1 = this.pathPoints[i];
            float f1 = pathpoint1.distanceToTarget;
            PathPoint pathpoint2;
            float f2;

            if (j >= this.count)
            {
                pathpoint2 = null;
                f2 = Float.POSITIVE_INFINITY;
            }
            else
            {
                pathpoint2 = this.pathPoints[j];
                f2 = pathpoint2.distanceToTarget;
            }

            if (f1 < f2)
            {
                if (f1 >= f)
                {
                    break;
                }

                this.pathPoints[index] = pathpoint1;
                pathpoint1.index = index;
                index = i;
            }
            else
            {
                if (f2 >= f)
                {
                    break;
                }

                this.pathPoints[index] = pathpoint2;
                pathpoint2.index = index;
                index = j;
            }
        }

        this.pathPoints[index] = pathpoint;
        pathpoint.index = index;
    }

    /**
     * Returns true if this path contains no points
     */
    public boolean isPathEmpty()
    {
        return this.count == 0;
    }
}
