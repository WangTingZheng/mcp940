package net.minecraft.entity.boss.dragon.phase;

import javax.annotation.Nullable;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.gen.feature.WorldGenEndPodium;

public class PhaseLandingApproach extends PhaseBase
{
    private Path currentPath;
    private Vec3d targetLocation;

    public PhaseLandingApproach(EntityDragon dragonIn)
    {
        super(dragonIn);
    }

    public PhaseList<PhaseLandingApproach> getPhaseList()
    {
        return PhaseList.LANDING_APPROACH;
    }

    /**
     * Called when this phase is set to active
     */
    public void initPhase()
    {
        this.currentPath = null;
        this.targetLocation = null;
    }

    /**
     * Gives the phase a chance to update its status.
     * Called by dragon's onLivingUpdate. Only used when !worldObj.isRemote.
     */
    public void doLocalUpdate()
    {
        double d0 = this.targetLocation == null ? 0.0D : this.targetLocation.squareDistanceTo(this.dragon.posX, this.dragon.posY, this.dragon.posZ);

        if (d0 < 100.0D || d0 > 22500.0D || this.dragon.isCollidedHorizontally || this.dragon.isCollidedVertically)
        {
            this.findNewTarget();
        }
    }

    @Nullable

    /**
     * Returns the location the dragon is flying toward
     */
    public Vec3d getTargetLocation()
    {
        return this.targetLocation;
    }

    private void findNewTarget()
    {
        if (this.currentPath == null || this.currentPath.isFinished())
        {
            int i = this.dragon.initPathPoints();
            BlockPos blockpos = this.dragon.world.getTopSolidOrLiquidBlock(WorldGenEndPodium.END_PODIUM_LOCATION);
            EntityPlayer entityplayer = this.dragon.world.getNearestAttackablePlayer(blockpos, 128.0D, 128.0D);
            int j;

            if (entityplayer != null)
            {
                Vec3d vec3d = (new Vec3d(entityplayer.posX, 0.0D, entityplayer.posZ)).normalize();
                j = this.dragon.getNearestPpIdx(-vec3d.x * 40.0D, 105.0D, -vec3d.z * 40.0D);
            }
            else
            {
                j = this.dragon.getNearestPpIdx(40.0D, (double)blockpos.getY(), 0.0D);
            }

            PathPoint pathpoint = new PathPoint(blockpos.getX(), blockpos.getY(), blockpos.getZ());
            this.currentPath = this.dragon.findPath(i, j, pathpoint);

            if (this.currentPath != null)
            {
                this.currentPath.incrementPathIndex();
            }
        }

        this.navigateToNextPathNode();

        if (this.currentPath != null && this.currentPath.isFinished())
        {
            this.dragon.getPhaseManager().setPhase(PhaseList.LANDING);
        }
    }

    private void navigateToNextPathNode()
    {
        if (this.currentPath != null && !this.currentPath.isFinished())
        {
            Vec3d vec3d = this.currentPath.getCurrentPos();
            this.currentPath.incrementPathIndex();
            double d0 = vec3d.x;
            double d1 = vec3d.z;
            double d2;

            while (true)
            {
                d2 = vec3d.y + (double)(this.dragon.getRNG().nextFloat() * 20.0F);

                if (d2 >= vec3d.y)
                {
                    break;
                }
            }

            this.targetLocation = new Vec3d(d0, d2, d1);
        }
    }
}
