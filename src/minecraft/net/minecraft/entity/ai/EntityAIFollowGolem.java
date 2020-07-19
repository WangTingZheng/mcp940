package net.minecraft.entity.ai;

import java.util.List;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.passive.EntityVillager;

public class EntityAIFollowGolem extends EntityAIBase
{
    private final EntityVillager villager;
    private EntityIronGolem ironGolem;
    private int takeGolemRoseTick;
    private boolean tookGolemRose;

    public EntityAIFollowGolem(EntityVillager villagerIn)
    {
        this.villager = villagerIn;
        this.setMutexBits(3);
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
        else if (!this.villager.world.isDaytime())
        {
            return false;
        }
        else
        {
            List<EntityIronGolem> list = this.villager.world.<EntityIronGolem>getEntitiesWithinAABB(EntityIronGolem.class, this.villager.getEntityBoundingBox().grow(6.0D, 2.0D, 6.0D));

            if (list.isEmpty())
            {
                return false;
            }
            else
            {
                for (EntityIronGolem entityirongolem : list)
                {
                    if (entityirongolem.getHoldRoseTick() > 0)
                    {
                        this.ironGolem = entityirongolem;
                        break;
                    }
                }

                return this.ironGolem != null;
            }
        }
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean shouldContinueExecuting()
    {
        return this.ironGolem.getHoldRoseTick() > 0;
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
        this.takeGolemRoseTick = this.villager.getRNG().nextInt(320);
        this.tookGolemRose = false;
        this.ironGolem.getNavigator().clearPathEntity();
    }

    /**
     * Reset the task's internal state. Called when this task is interrupted by another one
     */
    public void resetTask()
    {
        this.ironGolem = null;
        this.villager.getNavigator().clearPathEntity();
    }

    /**
     * Keep ticking a continuous task that has already been started
     */
    public void updateTask()
    {
        this.villager.getLookHelper().setLookPositionWithEntity(this.ironGolem, 30.0F, 30.0F);

        if (this.ironGolem.getHoldRoseTick() == this.takeGolemRoseTick)
        {
            this.villager.getNavigator().tryMoveToEntityLiving(this.ironGolem, 0.5D);
            this.tookGolemRose = true;
        }

        if (this.tookGolemRose && this.villager.getDistanceSqToEntity(this.ironGolem) < 4.0D)
        {
            this.ironGolem.setHoldingRose(false);
            this.villager.getNavigator().clearPathEntity();
        }
    }
}
