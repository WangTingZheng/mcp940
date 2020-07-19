package net.minecraft.entity.ai;

import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.AbstractHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.Vec3d;

public class EntityAIRunAroundLikeCrazy extends EntityAIBase
{
    private final AbstractHorse horseHost;
    private final double speed;
    private double targetX;
    private double targetY;
    private double targetZ;

    public EntityAIRunAroundLikeCrazy(AbstractHorse horse, double speedIn)
    {
        this.horseHost = horse;
        this.speed = speedIn;
        this.setMutexBits(1);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        if (!this.horseHost.isTame() && this.horseHost.isBeingRidden())
        {
            Vec3d vec3d = RandomPositionGenerator.findRandomTarget(this.horseHost, 5, 4);

            if (vec3d == null)
            {
                return false;
            }
            else
            {
                this.targetX = vec3d.x;
                this.targetY = vec3d.y;
                this.targetZ = vec3d.z;
                return true;
            }
        }
        else
        {
            return false;
        }
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
        this.horseHost.getNavigator().tryMoveToXYZ(this.targetX, this.targetY, this.targetZ, this.speed);
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean shouldContinueExecuting()
    {
        return !this.horseHost.isTame() && !this.horseHost.getNavigator().noPath() && this.horseHost.isBeingRidden();
    }

    /**
     * Keep ticking a continuous task that has already been started
     */
    public void updateTask()
    {
        if (!this.horseHost.isTame() && this.horseHost.getRNG().nextInt(50) == 0)
        {
            Entity entity = (Entity)this.horseHost.getPassengers().get(0);

            if (entity == null)
            {
                return;
            }

            if (entity instanceof EntityPlayer)
            {
                int i = this.horseHost.getTemper();
                int j = this.horseHost.getMaxTemper();

                if (j > 0 && this.horseHost.getRNG().nextInt(j) < i)
                {
                    this.horseHost.setTamedBy((EntityPlayer)entity);
                    return;
                }

                this.horseHost.increaseTemper(5);
            }

            this.horseHost.removePassengers();
            this.horseHost.makeMad();
            this.horseHost.world.setEntityState(this.horseHost, (byte)6);
        }
    }
}
