package net.minecraft.entity.ai;

import java.util.List;
import net.minecraft.entity.passive.EntityLlama;
import net.minecraft.util.math.Vec3d;

public class EntityAILlamaFollowCaravan extends EntityAIBase
{
    public EntityLlama llama;
    private double speedModifier;
    private int distCheckCounter;

    public EntityAILlamaFollowCaravan(EntityLlama llamaIn, double speedModifierIn)
    {
        this.llama = llamaIn;
        this.speedModifier = speedModifierIn;
        this.setMutexBits(1);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        if (!this.llama.getLeashed() && !this.llama.inCaravan())
        {
            List<EntityLlama> list = this.llama.world.<EntityLlama>getEntitiesWithinAABB(this.llama.getClass(), this.llama.getEntityBoundingBox().grow(9.0D, 4.0D, 9.0D));
            EntityLlama entityllama = null;
            double d0 = Double.MAX_VALUE;

            for (EntityLlama entityllama1 : list)
            {
                if (entityllama1.inCaravan() && !entityllama1.hasCaravanTrail())
                {
                    double d1 = this.llama.getDistanceSqToEntity(entityllama1);

                    if (d1 <= d0)
                    {
                        d0 = d1;
                        entityllama = entityllama1;
                    }
                }
            }

            if (entityllama == null)
            {
                for (EntityLlama entityllama2 : list)
                {
                    if (entityllama2.getLeashed() && !entityllama2.hasCaravanTrail())
                    {
                        double d2 = this.llama.getDistanceSqToEntity(entityllama2);

                        if (d2 <= d0)
                        {
                            d0 = d2;
                            entityllama = entityllama2;
                        }
                    }
                }
            }

            if (entityllama == null)
            {
                return false;
            }
            else if (d0 < 4.0D)
            {
                return false;
            }
            else if (!entityllama.getLeashed() && !this.firstIsLeashed(entityllama, 1))
            {
                return false;
            }
            else
            {
                this.llama.joinCaravan(entityllama);
                return true;
            }
        }
        else
        {
            return false;
        }
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean shouldContinueExecuting()
    {
        if (this.llama.inCaravan() && this.llama.getCaravanHead().isEntityAlive() && this.firstIsLeashed(this.llama, 0))
        {
            double d0 = this.llama.getDistanceSqToEntity(this.llama.getCaravanHead());

            if (d0 > 676.0D)
            {
                if (this.speedModifier <= 3.0D)
                {
                    this.speedModifier *= 1.2D;
                    this.distCheckCounter = 40;
                    return true;
                }

                if (this.distCheckCounter == 0)
                {
                    return false;
                }
            }

            if (this.distCheckCounter > 0)
            {
                --this.distCheckCounter;
            }

            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * Reset the task's internal state. Called when this task is interrupted by another one
     */
    public void resetTask()
    {
        this.llama.leaveCaravan();
        this.speedModifier = 2.1D;
    }

    /**
     * Keep ticking a continuous task that has already been started
     */
    public void updateTask()
    {
        if (this.llama.inCaravan())
        {
            EntityLlama entityllama = this.llama.getCaravanHead();
            double d0 = (double)this.llama.getDistanceToEntity(entityllama);
            float f = 2.0F;
            Vec3d vec3d = (new Vec3d(entityllama.posX - this.llama.posX, entityllama.posY - this.llama.posY, entityllama.posZ - this.llama.posZ)).normalize().scale(Math.max(d0 - 2.0D, 0.0D));
            this.llama.getNavigator().tryMoveToXYZ(this.llama.posX + vec3d.x, this.llama.posY + vec3d.y, this.llama.posZ + vec3d.z, this.speedModifier);
        }
    }

    private boolean firstIsLeashed(EntityLlama p_190858_1_, int p_190858_2_)
    {
        if (p_190858_2_ > 8)
        {
            return false;
        }
        else if (p_190858_1_.inCaravan())
        {
            if (p_190858_1_.getCaravanHead().getLeashed())
            {
                return true;
            }
            else
            {
                EntityLlama entityllama = p_190858_1_.getCaravanHead();
                ++p_190858_2_;
                return this.firstIsLeashed(entityllama, p_190858_2_);
            }
        }
        else
        {
            return false;
        }
    }
}
