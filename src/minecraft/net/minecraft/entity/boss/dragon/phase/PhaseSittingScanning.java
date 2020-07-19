package net.minecraft.entity.boss.dragon.phase;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class PhaseSittingScanning extends PhaseSittingBase
{
    private int scanningTime;

    public PhaseSittingScanning(EntityDragon dragonIn)
    {
        super(dragonIn);
    }

    /**
     * Gives the phase a chance to update its status.
     * Called by dragon's onLivingUpdate. Only used when !worldObj.isRemote.
     */
    public void doLocalUpdate()
    {
        ++this.scanningTime;
        EntityLivingBase entitylivingbase = this.dragon.world.getNearestAttackablePlayer(this.dragon, 20.0D, 10.0D);

        if (entitylivingbase != null)
        {
            if (this.scanningTime > 25)
            {
                this.dragon.getPhaseManager().setPhase(PhaseList.SITTING_ATTACKING);
            }
            else
            {
                Vec3d vec3d = (new Vec3d(entitylivingbase.posX - this.dragon.posX, 0.0D, entitylivingbase.posZ - this.dragon.posZ)).normalize();
                Vec3d vec3d1 = (new Vec3d((double)MathHelper.sin(this.dragon.rotationYaw * 0.017453292F), 0.0D, (double)(-MathHelper.cos(this.dragon.rotationYaw * 0.017453292F)))).normalize();
                float f = (float)vec3d1.dotProduct(vec3d);
                float f1 = (float)(Math.acos((double)f) * (180D / Math.PI)) + 0.5F;

                if (f1 < 0.0F || f1 > 10.0F)
                {
                    double d0 = entitylivingbase.posX - this.dragon.dragonPartHead.posX;
                    double d1 = entitylivingbase.posZ - this.dragon.dragonPartHead.posZ;
                    double d2 = MathHelper.clamp(MathHelper.wrapDegrees(180.0D - MathHelper.atan2(d0, d1) * (180D / Math.PI) - (double)this.dragon.rotationYaw), -100.0D, 100.0D);
                    this.dragon.randomYawVelocity *= 0.8F;
                    float f2 = MathHelper.sqrt(d0 * d0 + d1 * d1) + 1.0F;
                    float f3 = f2;

                    if (f2 > 40.0F)
                    {
                        f2 = 40.0F;
                    }

                    this.dragon.randomYawVelocity = (float)((double)this.dragon.randomYawVelocity + d2 * (double)(0.7F / f2 / f3));
                    this.dragon.rotationYaw += this.dragon.randomYawVelocity;
                }
            }
        }
        else if (this.scanningTime >= 100)
        {
            entitylivingbase = this.dragon.world.getNearestAttackablePlayer(this.dragon, 150.0D, 150.0D);
            this.dragon.getPhaseManager().setPhase(PhaseList.TAKEOFF);

            if (entitylivingbase != null)
            {
                this.dragon.getPhaseManager().setPhase(PhaseList.CHARGING_PLAYER);
                ((PhaseChargingPlayer)this.dragon.getPhaseManager().getPhase(PhaseList.CHARGING_PLAYER)).setTarget(new Vec3d(entitylivingbase.posX, entitylivingbase.posY, entitylivingbase.posZ));
            }
        }
    }

    /**
     * Called when this phase is set to active
     */
    public void initPhase()
    {
        this.scanningTime = 0;
    }

    public PhaseList<PhaseSittingScanning> getPhaseList()
    {
        return PhaseList.SITTING_SCANNING;
    }
}
