package net.minecraft.pathfinding;

import com.google.common.collect.Sets;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class PathFinder
{
    /** The path being generated */
    private final PathHeap path = new PathHeap();
    private final Set<PathPoint> closedSet = Sets.<PathPoint>newHashSet();

    /** Selection of path points to add to the path */
    private final PathPoint[] pathOptions = new PathPoint[32];
    private final NodeProcessor nodeProcessor;

    public PathFinder(NodeProcessor processor)
    {
        this.nodeProcessor = processor;
    }

    @Nullable
    public Path findPath(IBlockAccess worldIn, EntityLiving p_186333_2_, Entity p_186333_3_, float p_186333_4_)
    {
        return this.findPath(worldIn, p_186333_2_, p_186333_3_.posX, p_186333_3_.getEntityBoundingBox().minY, p_186333_3_.posZ, p_186333_4_);
    }

    @Nullable
    public Path findPath(IBlockAccess worldIn, EntityLiving p_186336_2_, BlockPos p_186336_3_, float p_186336_4_)
    {
        return this.findPath(worldIn, p_186336_2_, (double)((float)p_186336_3_.getX() + 0.5F), (double)((float)p_186336_3_.getY() + 0.5F), (double)((float)p_186336_3_.getZ() + 0.5F), p_186336_4_);
    }

    @Nullable
    private Path findPath(IBlockAccess worldIn, EntityLiving p_186334_2_, double p_186334_3_, double p_186334_5_, double p_186334_7_, float p_186334_9_)
    {
        this.path.clearPath();
        this.nodeProcessor.initProcessor(worldIn, p_186334_2_);
        PathPoint pathpoint = this.nodeProcessor.getStart();
        PathPoint pathpoint1 = this.nodeProcessor.getPathPointToCoords(p_186334_3_, p_186334_5_, p_186334_7_);
        Path path = this.findPath(pathpoint, pathpoint1, p_186334_9_);
        this.nodeProcessor.postProcess();
        return path;
    }

    @Nullable
    private Path findPath(PathPoint p_186335_1_, PathPoint p_186335_2_, float p_186335_3_)
    {
        p_186335_1_.totalPathDistance = 0.0F;
        p_186335_1_.distanceToNext = p_186335_1_.distanceManhattan(p_186335_2_);
        p_186335_1_.distanceToTarget = p_186335_1_.distanceToNext;
        this.path.clearPath();
        this.closedSet.clear();
        this.path.addPoint(p_186335_1_);
        PathPoint pathpoint = p_186335_1_;
        int i = 0;

        while (!this.path.isPathEmpty())
        {
            ++i;

            if (i >= 200)
            {
                break;
            }

            PathPoint pathpoint1 = this.path.dequeue();

            if (pathpoint1.equals(p_186335_2_))
            {
                pathpoint = p_186335_2_;
                break;
            }

            if (pathpoint1.distanceManhattan(p_186335_2_) < pathpoint.distanceManhattan(p_186335_2_))
            {
                pathpoint = pathpoint1;
            }

            pathpoint1.visited = true;
            int j = this.nodeProcessor.findPathOptions(this.pathOptions, pathpoint1, p_186335_2_, p_186335_3_);

            for (int k = 0; k < j; ++k)
            {
                PathPoint pathpoint2 = this.pathOptions[k];
                float f = pathpoint1.distanceManhattan(pathpoint2);
                pathpoint2.distanceFromOrigin = pathpoint1.distanceFromOrigin + f;
                pathpoint2.cost = f + pathpoint2.costMalus;
                float f1 = pathpoint1.totalPathDistance + pathpoint2.cost;

                if (pathpoint2.distanceFromOrigin < p_186335_3_ && (!pathpoint2.isAssigned() || f1 < pathpoint2.totalPathDistance))
                {
                    pathpoint2.previous = pathpoint1;
                    pathpoint2.totalPathDistance = f1;
                    pathpoint2.distanceToNext = pathpoint2.distanceManhattan(p_186335_2_) + pathpoint2.costMalus;

                    if (pathpoint2.isAssigned())
                    {
                        this.path.changeDistance(pathpoint2, pathpoint2.totalPathDistance + pathpoint2.distanceToNext);
                    }
                    else
                    {
                        pathpoint2.distanceToTarget = pathpoint2.totalPathDistance + pathpoint2.distanceToNext;
                        this.path.addPoint(pathpoint2);
                    }
                }
            }
        }

        if (pathpoint == p_186335_1_)
        {
            return null;
        }
        else
        {
            Path path = this.createEntityPath(p_186335_1_, pathpoint);
            return path;
        }
    }

    /**
     * Returns a new PathEntity for a given start and end point
     */
    private Path createEntityPath(PathPoint start, PathPoint end)
    {
        int i = 1;

        for (PathPoint pathpoint = end; pathpoint.previous != null; pathpoint = pathpoint.previous)
        {
            ++i;
        }

        PathPoint[] apathpoint = new PathPoint[i];
        PathPoint pathpoint1 = end;
        --i;

        for (apathpoint[i] = end; pathpoint1.previous != null; apathpoint[i] = pathpoint1)
        {
            pathpoint1 = pathpoint1.previous;
            --i;
        }

        return new Path(apathpoint);
    }
}
