package net.minecraft.pathfinding;

import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.ChunkCache;
import net.minecraft.world.World;

public abstract class PathNavigate
{
    protected EntityLiving entity;
    protected World world;
    @Nullable

    /** The PathEntity being followed. */
    protected Path currentPath;
    protected double speed;

    /**
     * The number of blocks (extra) +/- in each axis that get pulled out as cache for the pathfinder's search space
     */
    private final IAttributeInstance pathSearchRange;

    /** Time, in number of ticks, following the current path */
    protected int totalTicks;

    /**
     * The time when the last position check was done (to detect successful movement)
     */
    private int ticksAtLastPos;

    /**
     * Coordinates of the entity's position last time a check was done (part of monitoring getting 'stuck')
     */
    private Vec3d lastPosCheck = Vec3d.ZERO;
    private Vec3d timeoutCachedNode = Vec3d.ZERO;
    private long timeoutTimer;
    private long lastTimeoutCheck;
    private double timeoutLimit;
    protected float maxDistanceToWaypoint = 0.5F;
    protected boolean tryUpdatePath;
    private long lastTimeUpdated;
    protected NodeProcessor nodeProcessor;
    private BlockPos targetPos;
    private final PathFinder pathFinder;

    public PathNavigate(EntityLiving entityIn, World worldIn)
    {
        this.entity = entityIn;
        this.world = worldIn;
        this.pathSearchRange = entityIn.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE);
        this.pathFinder = this.getPathFinder();
    }

    protected abstract PathFinder getPathFinder();

    /**
     * Sets the speed
     */
    public void setSpeed(double speedIn)
    {
        this.speed = speedIn;
    }

    /**
     * Gets the maximum distance that the path finding will search in.
     */
    public float getPathSearchRange()
    {
        return (float)this.pathSearchRange.getAttributeValue();
    }

    /**
     * Returns true if path can be changed by {@link net.minecraft.pathfinding.PathNavigate#onUpdateNavigation()
     * onUpdateNavigation()}
     */
    public boolean canUpdatePathOnTimeout()
    {
        return this.tryUpdatePath;
    }

    public void updatePath()
    {
        if (this.world.getTotalWorldTime() - this.lastTimeUpdated > 20L)
        {
            if (this.targetPos != null)
            {
                this.currentPath = null;
                this.currentPath = this.getPathToPos(this.targetPos);
                this.lastTimeUpdated = this.world.getTotalWorldTime();
                this.tryUpdatePath = false;
            }
        }
        else
        {
            this.tryUpdatePath = true;
        }
    }

    @Nullable

    /**
     * Returns the path to the given coordinates. Args : x, y, z
     */
    public final Path getPathToXYZ(double x, double y, double z)
    {
        return this.getPathToPos(new BlockPos(x, y, z));
    }

    @Nullable

    /**
     * Returns path to given BlockPos
     */
    public Path getPathToPos(BlockPos pos)
    {
        if (!this.canNavigate())
        {
            return null;
        }
        else if (this.currentPath != null && !this.currentPath.isFinished() && pos.equals(this.targetPos))
        {
            return this.currentPath;
        }
        else
        {
            this.targetPos = pos;
            float f = this.getPathSearchRange();
            this.world.profiler.startSection("pathfind");
            BlockPos blockpos = new BlockPos(this.entity);
            int i = (int)(f + 8.0F);
            ChunkCache chunkcache = new ChunkCache(this.world, blockpos.add(-i, -i, -i), blockpos.add(i, i, i), 0);
            Path path = this.pathFinder.findPath(chunkcache, this.entity, this.targetPos, f);
            this.world.profiler.endSection();
            return path;
        }
    }

    @Nullable

    /**
     * Returns the path to the given EntityLiving. Args : entity
     */
    public Path getPathToEntityLiving(Entity entityIn)
    {
        if (!this.canNavigate())
        {
            return null;
        }
        else
        {
            BlockPos blockpos = new BlockPos(entityIn);

            if (this.currentPath != null && !this.currentPath.isFinished() && blockpos.equals(this.targetPos))
            {
                return this.currentPath;
            }
            else
            {
                this.targetPos = blockpos;
                float f = this.getPathSearchRange();
                this.world.profiler.startSection("pathfind");
                BlockPos blockpos1 = (new BlockPos(this.entity)).up();
                int i = (int)(f + 16.0F);
                ChunkCache chunkcache = new ChunkCache(this.world, blockpos1.add(-i, -i, -i), blockpos1.add(i, i, i), 0);
                Path path = this.pathFinder.findPath(chunkcache, this.entity, entityIn, f);
                this.world.profiler.endSection();
                return path;
            }
        }
    }

    /**
     * Try to find and set a path to XYZ. Returns true if successful. Args : x, y, z, speed
     */
    public boolean tryMoveToXYZ(double x, double y, double z, double speedIn)
    {
        return this.setPath(this.getPathToXYZ(x, y, z), speedIn);
    }

    /**
     * Try to find and set a path to EntityLiving. Returns true if successful. Args : entity, speed
     */
    public boolean tryMoveToEntityLiving(Entity entityIn, double speedIn)
    {
        Path path = this.getPathToEntityLiving(entityIn);
        return path != null && this.setPath(path, speedIn);
    }

    /**
     * Sets a new path. If it's diferent from the old path. Checks to adjust path for sun avoiding, and stores start
     * coords. Args : path, speed
     */
    public boolean setPath(@Nullable Path pathentityIn, double speedIn)
    {
        if (pathentityIn == null)
        {
            this.currentPath = null;
            return false;
        }
        else
        {
            if (!pathentityIn.isSamePath(this.currentPath))
            {
                this.currentPath = pathentityIn;
            }

            this.removeSunnyPath();

            if (this.currentPath.getCurrentPathLength() <= 0)
            {
                return false;
            }
            else
            {
                this.speed = speedIn;
                Vec3d vec3d = this.getEntityPosition();
                this.ticksAtLastPos = this.totalTicks;
                this.lastPosCheck = vec3d;
                return true;
            }
        }
    }

    @Nullable

    /**
     * gets the actively used PathEntity
     */
    public Path getPath()
    {
        return this.currentPath;
    }

    public void onUpdateNavigation()
    {
        ++this.totalTicks;

        if (this.tryUpdatePath)
        {
            this.updatePath();
        }

        if (!this.noPath())
        {
            if (this.canNavigate())
            {
                this.pathFollow();
            }
            else if (this.currentPath != null && this.currentPath.getCurrentPathIndex() < this.currentPath.getCurrentPathLength())
            {
                Vec3d vec3d = this.getEntityPosition();
                Vec3d vec3d1 = this.currentPath.getVectorFromIndex(this.entity, this.currentPath.getCurrentPathIndex());

                if (vec3d.y > vec3d1.y && !this.entity.onGround && MathHelper.floor(vec3d.x) == MathHelper.floor(vec3d1.x) && MathHelper.floor(vec3d.z) == MathHelper.floor(vec3d1.z))
                {
                    this.currentPath.setCurrentPathIndex(this.currentPath.getCurrentPathIndex() + 1);
                }
            }

            this.debugPathFinding();

            if (!this.noPath())
            {
                Vec3d vec3d2 = this.currentPath.getPosition(this.entity);
                BlockPos blockpos = (new BlockPos(vec3d2)).down();
                AxisAlignedBB axisalignedbb = this.world.getBlockState(blockpos).getBoundingBox(this.world, blockpos);
                vec3d2 = vec3d2.subtract(0.0D, 1.0D - axisalignedbb.maxY, 0.0D);
                this.entity.getMoveHelper().setMoveTo(vec3d2.x, vec3d2.y, vec3d2.z, this.speed);
            }
        }
    }

    protected void debugPathFinding()
    {
    }

    protected void pathFollow()
    {
        Vec3d vec3d = this.getEntityPosition();
        int i = this.currentPath.getCurrentPathLength();

        for (int j = this.currentPath.getCurrentPathIndex(); j < this.currentPath.getCurrentPathLength(); ++j)
        {
            if ((double)this.currentPath.getPathPointFromIndex(j).y != Math.floor(vec3d.y))
            {
                i = j;
                break;
            }
        }

        this.maxDistanceToWaypoint = this.entity.width > 0.75F ? this.entity.width / 2.0F : 0.75F - this.entity.width / 2.0F;
        Vec3d vec3d1 = this.currentPath.getCurrentPos();

        if (MathHelper.abs((float)(this.entity.posX - (vec3d1.x + 0.5D))) < this.maxDistanceToWaypoint && MathHelper.abs((float)(this.entity.posZ - (vec3d1.z + 0.5D))) < this.maxDistanceToWaypoint && Math.abs(this.entity.posY - vec3d1.y) < 1.0D)
        {
            this.currentPath.setCurrentPathIndex(this.currentPath.getCurrentPathIndex() + 1);
        }

        int k = MathHelper.ceil(this.entity.width);
        int l = MathHelper.ceil(this.entity.height);
        int i1 = k;

        for (int j1 = i - 1; j1 >= this.currentPath.getCurrentPathIndex(); --j1)
        {
            if (this.isDirectPathBetweenPoints(vec3d, this.currentPath.getVectorFromIndex(this.entity, j1), k, l, i1))
            {
                this.currentPath.setCurrentPathIndex(j1);
                break;
            }
        }

        this.checkForStuck(vec3d);
    }

    /**
     * Checks if entity haven't been moved when last checked and if so, clears current {@link
     * net.minecraft.pathfinding.PathEntity}
     */
    protected void checkForStuck(Vec3d positionVec3)
    {
        if (this.totalTicks - this.ticksAtLastPos > 100)
        {
            if (positionVec3.squareDistanceTo(this.lastPosCheck) < 2.25D)
            {
                this.clearPathEntity();
            }

            this.ticksAtLastPos = this.totalTicks;
            this.lastPosCheck = positionVec3;
        }

        if (this.currentPath != null && !this.currentPath.isFinished())
        {
            Vec3d vec3d = this.currentPath.getCurrentPos();

            if (vec3d.equals(this.timeoutCachedNode))
            {
                this.timeoutTimer += System.currentTimeMillis() - this.lastTimeoutCheck;
            }
            else
            {
                this.timeoutCachedNode = vec3d;
                double d0 = positionVec3.distanceTo(this.timeoutCachedNode);
                this.timeoutLimit = this.entity.getAIMoveSpeed() > 0.0F ? d0 / (double)this.entity.getAIMoveSpeed() * 1000.0D : 0.0D;
            }

            if (this.timeoutLimit > 0.0D && (double)this.timeoutTimer > this.timeoutLimit * 3.0D)
            {
                this.timeoutCachedNode = Vec3d.ZERO;
                this.timeoutTimer = 0L;
                this.timeoutLimit = 0.0D;
                this.clearPathEntity();
            }

            this.lastTimeoutCheck = System.currentTimeMillis();
        }
    }

    /**
     * If null path or reached the end
     */
    public boolean noPath()
    {
        return this.currentPath == null || this.currentPath.isFinished();
    }

    /**
     * sets active PathEntity to null
     */
    public void clearPathEntity()
    {
        this.currentPath = null;
    }

    protected abstract Vec3d getEntityPosition();

    /**
     * If on ground or swimming and can swim
     */
    protected abstract boolean canNavigate();

    /**
     * Returns true if the entity is in water or lava, false otherwise
     */
    protected boolean isInLiquid()
    {
        return this.entity.isInWater() || this.entity.isInLava();
    }

    /**
     * Trims path data from the end to the first sun covered block
     */
    protected void removeSunnyPath()
    {
        if (this.currentPath != null)
        {
            for (int i = 0; i < this.currentPath.getCurrentPathLength(); ++i)
            {
                PathPoint pathpoint = this.currentPath.getPathPointFromIndex(i);
                PathPoint pathpoint1 = i + 1 < this.currentPath.getCurrentPathLength() ? this.currentPath.getPathPointFromIndex(i + 1) : null;
                IBlockState iblockstate = this.world.getBlockState(new BlockPos(pathpoint.x, pathpoint.y, pathpoint.z));
                Block block = iblockstate.getBlock();

                if (block == Blocks.CAULDRON)
                {
                    this.currentPath.setPoint(i, pathpoint.cloneMove(pathpoint.x, pathpoint.y + 1, pathpoint.z));

                    if (pathpoint1 != null && pathpoint.y >= pathpoint1.y)
                    {
                        this.currentPath.setPoint(i + 1, pathpoint1.cloneMove(pathpoint1.x, pathpoint.y + 1, pathpoint1.z));
                    }
                }
            }
        }
    }

    /**
     * Checks if the specified entity can safely walk to the specified location.
     */
    protected abstract boolean isDirectPathBetweenPoints(Vec3d posVec31, Vec3d posVec32, int sizeX, int sizeY, int sizeZ);

    public boolean canEntityStandOnPos(BlockPos pos)
    {
        return this.world.getBlockState(pos.down()).isFullBlock();
    }

    public NodeProcessor getNodeProcessor()
    {
        return this.nodeProcessor;
    }
}
