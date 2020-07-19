package net.minecraft.entity.ai;

import java.util.List;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.util.math.Vec3d;

public class EntityAIPlay extends EntityAIBase
{
    private final EntityVillager villager;
    private EntityLivingBase targetVillager;
    private final double speed;
    private int playTime;

    public EntityAIPlay(EntityVillager villagerIn, double speedIn)
    {
        this.villager = villagerIn;
        this.speed = speedIn;
        this.setMutexBits(1);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        if (this.villager.getGrowingAge() >= 0)
        {
            return false;
        }
        else if (this.villager.getRNG().nextInt(400) != 0)
        {
            return false;
        }
        else
        {
            List<EntityVillager> list = this.villager.world.<EntityVillager>getEntitiesWithinAABB(EntityVillager.class, this.villager.getEntityBoundingBox().grow(6.0D, 3.0D, 6.0D));
            double d0 = Double.MAX_VALUE;

            for (EntityVillager entityvillager : list)
            {
                if (entityvillager != this.villager && !entityvillager.isPlaying() && entityvillager.getGrowingAge() < 0)
                {
                    double d1 = entityvillager.getDistanceSqToEntity(this.villager);

                    if (d1 <= d0)
                    {
                        d0 = d1;
                        this.targetVillager = entityvillager;
                    }
                }
            }

            if (this.targetVillager == null)
            {
                Vec3d vec3d = RandomPositionGenerator.findRandomTarget(this.villager, 16, 3);

                if (vec3d == null)
                {
                    return false;
                }
            }

            return true;
        }
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean shouldContinueExecuting()
    {
        return this.playTime > 0;
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
        if (this.targetVillager != null)
        {
            this.villager.setPlaying(true);
        }

        this.playTime = 1000;
    }

    /**
     * Reset the task's internal state. Called when this task is interrupted by another one
     */
    public void resetTask()
    {
        this.villager.setPlaying(false);
        this.targetVillager = null;
    }

    /**
     * Keep ticking a continuous task that has already been started
     */
    public void updateTask()
    {
        --this.playTime;

        if (this.targetVillager != null)
        {
            if (this.villager.getDistanceSqToEntity(this.targetVillager) > 4.0D)
            {
                this.villager.getNavigator().tryMoveToEntityLiving(this.targetVillager, this.speed);
            }
        }
        else if (this.villager.getNavigator().noPath())
        {
            Vec3d vec3d = RandomPositionGenerator.findRandomTarget(this.villager, 16, 3);

            if (vec3d == null)
            {
                return;
            }

            this.villager.getNavigator().tryMoveToXYZ(vec3d.x, vec3d.y, vec3d.z, this.speed);
        }
    }
}
