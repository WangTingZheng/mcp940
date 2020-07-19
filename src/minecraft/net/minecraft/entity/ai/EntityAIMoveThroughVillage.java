package net.minecraft.entity.ai;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.entity.EntityCreature;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.village.Village;
import net.minecraft.village.VillageDoorInfo;

public class EntityAIMoveThroughVillage extends EntityAIBase
{
    private final EntityCreature entity;
    private final double movementSpeed;

    /** The PathNavigate of our entity. */
    private Path entityPathNavigate;
    private VillageDoorInfo doorInfo;
    private final boolean isNocturnal;
    private final List<VillageDoorInfo> doorList = Lists.<VillageDoorInfo>newArrayList();

    public EntityAIMoveThroughVillage(EntityCreature entityIn, double movementSpeedIn, boolean isNocturnalIn)
    {
        this.entity = entityIn;
        this.movementSpeed = movementSpeedIn;
        this.isNocturnal = isNocturnalIn;
        this.setMutexBits(1);

        if (!(entityIn.getNavigator() instanceof PathNavigateGround))
        {
            throw new IllegalArgumentException("Unsupported mob for MoveThroughVillageGoal");
        }
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        this.resizeDoorList();

        if (this.isNocturnal && this.entity.world.isDaytime())
        {
            return false;
        }
        else
        {
            Village village = this.entity.world.getVillageCollection().getNearestVillage(new BlockPos(this.entity), 0);

            if (village == null)
            {
                return false;
            }
            else
            {
                this.doorInfo = this.findNearestDoor(village);

                if (this.doorInfo == null)
                {
                    return false;
                }
                else
                {
                    PathNavigateGround pathnavigateground = (PathNavigateGround)this.entity.getNavigator();
                    boolean flag = pathnavigateground.getEnterDoors();
                    pathnavigateground.setBreakDoors(false);
                    this.entityPathNavigate = pathnavigateground.getPathToPos(this.doorInfo.getDoorBlockPos());
                    pathnavigateground.setBreakDoors(flag);

                    if (this.entityPathNavigate != null)
                    {
                        return true;
                    }
                    else
                    {
                        Vec3d vec3d = RandomPositionGenerator.findRandomTargetBlockTowards(this.entity, 10, 7, new Vec3d((double)this.doorInfo.getDoorBlockPos().getX(), (double)this.doorInfo.getDoorBlockPos().getY(), (double)this.doorInfo.getDoorBlockPos().getZ()));

                        if (vec3d == null)
                        {
                            return false;
                        }
                        else
                        {
                            pathnavigateground.setBreakDoors(false);
                            this.entityPathNavigate = this.entity.getNavigator().getPathToXYZ(vec3d.x, vec3d.y, vec3d.z);
                            pathnavigateground.setBreakDoors(flag);
                            return this.entityPathNavigate != null;
                        }
                    }
                }
            }
        }
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean shouldContinueExecuting()
    {
        if (this.entity.getNavigator().noPath())
        {
            return false;
        }
        else
        {
            float f = this.entity.width + 4.0F;
            return this.entity.getDistanceSq(this.doorInfo.getDoorBlockPos()) > (double)(f * f);
        }
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
        this.entity.getNavigator().setPath(this.entityPathNavigate, this.movementSpeed);
    }

    /**
     * Reset the task's internal state. Called when this task is interrupted by another one
     */
    public void resetTask()
    {
        if (this.entity.getNavigator().noPath() || this.entity.getDistanceSq(this.doorInfo.getDoorBlockPos()) < 16.0D)
        {
            this.doorList.add(this.doorInfo);
        }
    }

    private VillageDoorInfo findNearestDoor(Village villageIn)
    {
        VillageDoorInfo villagedoorinfo = null;
        int i = Integer.MAX_VALUE;

        for (VillageDoorInfo villagedoorinfo1 : villageIn.getVillageDoorInfoList())
        {
            int j = villagedoorinfo1.getDistanceSquared(MathHelper.floor(this.entity.posX), MathHelper.floor(this.entity.posY), MathHelper.floor(this.entity.posZ));

            if (j < i && !this.doesDoorListContain(villagedoorinfo1))
            {
                villagedoorinfo = villagedoorinfo1;
                i = j;
            }
        }

        return villagedoorinfo;
    }

    private boolean doesDoorListContain(VillageDoorInfo doorInfoIn)
    {
        for (VillageDoorInfo villagedoorinfo : this.doorList)
        {
            if (doorInfoIn.getDoorBlockPos().equals(villagedoorinfo.getDoorBlockPos()))
            {
                return true;
            }
        }

        return false;
    }

    private void resizeDoorList()
    {
        if (this.doorList.size() > 15)
        {
            this.doorList.remove(0);
        }
    }
}
