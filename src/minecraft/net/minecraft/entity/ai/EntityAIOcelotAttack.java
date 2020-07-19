package net.minecraft.entity.ai;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;

public class EntityAIOcelotAttack extends EntityAIBase
{
    World world;
    EntityLiving entity;
    EntityLivingBase target;
    int attackCountdown;

    public EntityAIOcelotAttack(EntityLiving theEntityIn)
    {
        this.entity = theEntityIn;
        this.world = theEntityIn.world;
        this.setMutexBits(3);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        EntityLivingBase entitylivingbase = this.entity.getAttackTarget();

        if (entitylivingbase == null)
        {
            return false;
        }
        else
        {
            this.target = entitylivingbase;
            return true;
        }
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean shouldContinueExecuting()
    {
        if (!this.target.isEntityAlive())
        {
            return false;
        }
        else if (this.entity.getDistanceSqToEntity(this.target) > 225.0D)
        {
            return false;
        }
        else
        {
            return !this.entity.getNavigator().noPath() || this.shouldExecute();
        }
    }

    /**
     * Reset the task's internal state. Called when this task is interrupted by another one
     */
    public void resetTask()
    {
        this.target = null;
        this.entity.getNavigator().clearPathEntity();
    }

    /**
     * Keep ticking a continuous task that has already been started
     */
    public void updateTask()
    {
        this.entity.getLookHelper().setLookPositionWithEntity(this.target, 30.0F, 30.0F);
        double d0 = (double)(this.entity.width * 2.0F * this.entity.width * 2.0F);
        double d1 = this.entity.getDistanceSq(this.target.posX, this.target.getEntityBoundingBox().minY, this.target.posZ);
        double d2 = 0.8D;

        if (d1 > d0 && d1 < 16.0D)
        {
            d2 = 1.33D;
        }
        else if (d1 < 225.0D)
        {
            d2 = 0.6D;
        }

        this.entity.getNavigator().tryMoveToEntityLiving(this.target, d2);
        this.attackCountdown = Math.max(this.attackCountdown - 1, 0);

        if (d1 <= d0)
        {
            if (this.attackCountdown <= 0)
            {
                this.attackCountdown = 20;
                this.entity.attackEntityAsMob(this.target);
            }
        }
    }
}
