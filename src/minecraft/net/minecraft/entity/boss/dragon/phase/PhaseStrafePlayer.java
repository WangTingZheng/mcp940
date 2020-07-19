package net.minecraft.entity.boss.dragon.phase;

import javax.annotation.Nullable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityDragonFireball;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PhaseStrafePlayer extends PhaseBase
{
    private static final Logger LOGGER = LogManager.getLogger();
    private int fireballCharge;
    private Path currentPath;
    private Vec3d targetLocation;
    private EntityLivingBase attackTarget;
    private boolean holdingPatternClockwise;

    public PhaseStrafePlayer(EntityDragon dragonIn)
    {
        super(dragonIn);
    }

    /**
     * Gives the phase a chance to update its status.
     * Called by dragon's onLivingUpdate. Only used when !worldObj.isRemote.
     */
    public void doLocalUpdate()
    {
        if (this.attackTarget == null)
        {
            LOGGER.warn("Skipping player strafe phase because no player was found");
            this.dragon.getPhaseManager().setPhase(PhaseList.HOLDING_PATTERN);
        }
        else
        {
            if (this.currentPath != null && this.currentPath.isFinished())
            {
                double d0 = this.attackTarget.posX;
                double d1 = this.attackTarget.posZ;
                double d2 = d0 - this.dragon.posX;
                double d3 = d1 - this.dragon.posZ;
                double d4 = (double)MathHelper.sqrt(d2 * d2 + d3 * d3);
                double d5 = Math.min(0.4000000059604645D + d4 / 80.0D - 1.0D, 10.0D);
                this.targetLocation = new Vec3d(d0, this.attackTarget.posY + d5, d1);
            }

            double d12 = this.targetLocation == null ? 0.0D : this.targetLocation.squareDistanceTo(this.dragon.posX, this.dragon.posY, this.dragon.posZ);

            if (d12 < 100.0D || d12 > 22500.0D)
            {
                this.findNewTarget();
            }

            double d13 = 64.0D;

            if (this.attackTarget.getDistanceSqToEntity(this.dragon) < 4096.0D)
            {
                if (this.dragon.canEntityBeSeen(this.attackTarget))
                {
                    ++this.fireballCharge;
                    Vec3d vec3d1 = (new Vec3d(this.attackTarget.posX - this.dragon.posX, 0.0D, this.attackTarget.posZ - this.dragon.posZ)).normalize();
                    Vec3d vec3d = (new Vec3d((double)MathHelper.sin(this.dragon.rotationYaw * 0.017453292F), 0.0D, (double)(-MathHelper.cos(this.dragon.rotationYaw * 0.017453292F)))).normalize();
                    float f1 = (float)vec3d.dotProduct(vec3d1);
                    float f = (float)(Math.acos((double)f1) * (180D / Math.PI));
                    f = f + 0.5F;

                    if (this.fireballCharge >= 5 && f >= 0.0F && f < 10.0F)
                    {
                        double d14 = 1.0D;
                        Vec3d vec3d2 = this.dragon.getLook(1.0F);
                        double d6 = this.dragon.dragonPartHead.posX - vec3d2.x * 1.0D;
                        double d7 = this.dragon.dragonPartHead.posY + (double)(this.dragon.dragonPartHead.height / 2.0F) + 0.5D;
                        double d8 = this.dragon.dragonPartHead.posZ - vec3d2.z * 1.0D;
                        double d9 = this.attackTarget.posX - d6;
                        double d10 = this.attackTarget.posY + (double)(this.attackTarget.height / 2.0F) - (d7 + (double)(this.dragon.dragonPartHead.height / 2.0F));
                        double d11 = this.attackTarget.posZ - d8;
                        this.dragon.world.playEvent((EntityPlayer)null, 1017, new BlockPos(this.dragon), 0);
                        EntityDragonFireball entitydragonfireball = new EntityDragonFireball(this.dragon.world, this.dragon, d9, d10, d11);
                        entitydragonfireball.setLocationAndAngles(d6, d7, d8, 0.0F, 0.0F);
                        this.dragon.world.spawnEntity(entitydragonfireball);
                        this.fireballCharge = 0;

                        if (this.currentPath != null)
                        {
                            while (!this.currentPath.isFinished())
                            {
                                this.currentPath.incrementPathIndex();
                            }
                        }

                        this.dragon.getPhaseManager().setPhase(PhaseList.HOLDING_PATTERN);
                    }
                }
                else if (this.fireballCharge > 0)
                {
                    --this.fireballCharge;
                }
            }
            else if (this.fireballCharge > 0)
            {
                --this.fireballCharge;
            }
        }
    }

    private void findNewTarget()
    {
        if (this.currentPath == null || this.currentPath.isFinished())
        {
            int i = this.dragon.initPathPoints();
            int j = i;

            if (this.dragon.getRNG().nextInt(8) == 0)
            {
                this.holdingPatternClockwise = !this.holdingPatternClockwise;
                j = i + 6;
            }

            if (this.holdingPatternClockwise)
            {
                ++j;
            }
            else
            {
                --j;
            }

            if (this.dragon.getFightManager() != null && this.dragon.getFightManager().getNumAliveCrystals() > 0)
            {
                j = j % 12;

                if (j < 0)
                {
                    j += 12;
                }
            }
            else
            {
                j = j - 12;
                j = j & 7;
                j = j + 12;
            }

            this.currentPath = this.dragon.findPath(i, j, (PathPoint)null);

            if (this.currentPath != null)
            {
                this.currentPath.incrementPathIndex();
            }
        }

        this.navigateToNextPathNode();
    }

    private void navigateToNextPathNode()
    {
        if (this.currentPath != null && !this.currentPath.isFinished())
        {
            Vec3d vec3d = this.currentPath.getCurrentPos();
            this.currentPath.incrementPathIndex();
            double d0 = vec3d.x;
            double d2 = vec3d.z;
            double d1;

            while (true)
            {
                d1 = vec3d.y + (double)(this.dragon.getRNG().nextFloat() * 20.0F);

                if (d1 >= vec3d.y)
                {
                    break;
                }
            }

            this.targetLocation = new Vec3d(d0, d1, d2);
        }
    }

    /**
     * Called when this phase is set to active
     */
    public void initPhase()
    {
        this.fireballCharge = 0;
        this.targetLocation = null;
        this.currentPath = null;
        this.attackTarget = null;
    }

    public void setTarget(EntityLivingBase p_188686_1_)
    {
        this.attackTarget = p_188686_1_;
        int i = this.dragon.initPathPoints();
        int j = this.dragon.getNearestPpIdx(this.attackTarget.posX, this.attackTarget.posY, this.attackTarget.posZ);
        int k = MathHelper.floor(this.attackTarget.posX);
        int l = MathHelper.floor(this.attackTarget.posZ);
        double d0 = (double)k - this.dragon.posX;
        double d1 = (double)l - this.dragon.posZ;
        double d2 = (double)MathHelper.sqrt(d0 * d0 + d1 * d1);
        double d3 = Math.min(0.4000000059604645D + d2 / 80.0D - 1.0D, 10.0D);
        int i1 = MathHelper.floor(this.attackTarget.posY + d3);
        PathPoint pathpoint = new PathPoint(k, i1, l);
        this.currentPath = this.dragon.findPath(i, j, pathpoint);

        if (this.currentPath != null)
        {
            this.currentPath.incrementPathIndex();
            this.navigateToNextPathNode();
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

    public PhaseList<PhaseStrafePlayer> getPhaseList()
    {
        return PhaseList.STRAFE_PLAYER;
    }
}
