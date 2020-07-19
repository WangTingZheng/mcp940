package net.minecraft.entity.boss.dragon.phase;

import javax.annotation.Nullable;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.gen.feature.WorldGenEndPodium;

public class PhaseLanding extends PhaseBase
{
    private Vec3d targetLocation;

    public PhaseLanding(EntityDragon dragonIn)
    {
        super(dragonIn);
    }

    /**
     * Generates particle effects appropriate to the phase (or sometimes sounds).
     * Called by dragon's onLivingUpdate. Only used when worldObj.isRemote.
     */
    public void doClientRenderEffects()
    {
        Vec3d vec3d = this.dragon.getHeadLookVec(1.0F).normalize();
        vec3d.rotateYaw(-((float)Math.PI / 4F));
        double d0 = this.dragon.dragonPartHead.posX;
        double d1 = this.dragon.dragonPartHead.posY + (double)(this.dragon.dragonPartHead.height / 2.0F);
        double d2 = this.dragon.dragonPartHead.posZ;

        for (int i = 0; i < 8; ++i)
        {
            double d3 = d0 + this.dragon.getRNG().nextGaussian() / 2.0D;
            double d4 = d1 + this.dragon.getRNG().nextGaussian() / 2.0D;
            double d5 = d2 + this.dragon.getRNG().nextGaussian() / 2.0D;
            this.dragon.world.spawnParticle(EnumParticleTypes.DRAGON_BREATH, d3, d4, d5, -vec3d.x * 0.07999999821186066D + this.dragon.motionX, -vec3d.y * 0.30000001192092896D + this.dragon.motionY, -vec3d.z * 0.07999999821186066D + this.dragon.motionZ);
            vec3d.rotateYaw(0.19634955F);
        }
    }

    /**
     * Gives the phase a chance to update its status.
     * Called by dragon's onLivingUpdate. Only used when !worldObj.isRemote.
     */
    public void doLocalUpdate()
    {
        if (this.targetLocation == null)
        {
            this.targetLocation = new Vec3d(this.dragon.world.getTopSolidOrLiquidBlock(WorldGenEndPodium.END_PODIUM_LOCATION));
        }

        if (this.targetLocation.squareDistanceTo(this.dragon.posX, this.dragon.posY, this.dragon.posZ) < 1.0D)
        {
            ((PhaseSittingFlaming)this.dragon.getPhaseManager().getPhase(PhaseList.SITTING_FLAMING)).resetFlameCount();
            this.dragon.getPhaseManager().setPhase(PhaseList.SITTING_SCANNING);
        }
    }

    /**
     * Returns the maximum amount dragon may rise or fall during this phase
     */
    public float getMaxRiseOrFall()
    {
        return 1.5F;
    }

    public float getYawFactor()
    {
        float f = MathHelper.sqrt(this.dragon.motionX * this.dragon.motionX + this.dragon.motionZ * this.dragon.motionZ) + 1.0F;
        float f1 = Math.min(f, 40.0F);
        return f1 / f;
    }

    /**
     * Called when this phase is set to active
     */
    public void initPhase()
    {
        this.targetLocation = null;
    }

    @Nullable

    /**
     * Returns the location the dragon is flying toward
     */
    public Vec3d getTargetLocation()
    {
        return this.targetLocation;
    }

    public PhaseList<PhaseLanding> getPhaseList()
    {
        return PhaseList.LANDING;
    }
}
