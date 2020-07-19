package net.minecraft.village;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public class VillageDoorInfo
{
    /** a block representing the door. Could be either upper or lower part */
    private final BlockPos doorBlockPos;
    private final BlockPos insideBlock;

    /** the inside direction is where can see less sky */
    private final EnumFacing insideDirection;
    private int lastActivityTimestamp;
    private boolean isDetachedFromVillageFlag;
    private int doorOpeningRestrictionCounter;

    public VillageDoorInfo(BlockPos pos, int deltaX, int deltaZ, int timestamp)
    {
        this(pos, getFaceDirection(deltaX, deltaZ), timestamp);
    }

    private static EnumFacing getFaceDirection(int deltaX, int deltaZ)
    {
        if (deltaX < 0)
        {
            return EnumFacing.WEST;
        }
        else if (deltaX > 0)
        {
            return EnumFacing.EAST;
        }
        else
        {
            return deltaZ < 0 ? EnumFacing.NORTH : EnumFacing.SOUTH;
        }
    }

    public VillageDoorInfo(BlockPos pos, EnumFacing facing, int timestamp)
    {
        this.doorBlockPos = pos;
        this.insideDirection = facing;
        this.insideBlock = pos.offset(facing, 2);
        this.lastActivityTimestamp = timestamp;
    }

    /**
     * Returns the squared distance between this door and the given coordinate.
     */
    public int getDistanceSquared(int x, int y, int z)
    {
        return (int)this.doorBlockPos.distanceSq((double)x, (double)y, (double)z);
    }

    public int getDistanceToDoorBlockSq(BlockPos pos)
    {
        return (int)pos.distanceSq(this.getDoorBlockPos());
    }

    public int getDistanceToInsideBlockSq(BlockPos pos)
    {
        return (int)this.insideBlock.distanceSq(pos);
    }

    public boolean isInsideSide(BlockPos pos)
    {
        int i = pos.getX() - this.doorBlockPos.getX();
        int j = pos.getZ() - this.doorBlockPos.getY();
        return i * this.insideDirection.getFrontOffsetX() + j * this.insideDirection.getFrontOffsetZ() >= 0;
    }

    public void resetDoorOpeningRestrictionCounter()
    {
        this.doorOpeningRestrictionCounter = 0;
    }

    public void incrementDoorOpeningRestrictionCounter()
    {
        ++this.doorOpeningRestrictionCounter;
    }

    public int getDoorOpeningRestrictionCounter()
    {
        return this.doorOpeningRestrictionCounter;
    }

    public BlockPos getDoorBlockPos()
    {
        return this.doorBlockPos;
    }

    public BlockPos getInsideBlockPos()
    {
        return this.insideBlock;
    }

    public int getInsideOffsetX()
    {
        return this.insideDirection.getFrontOffsetX() * 2;
    }

    public int getInsideOffsetZ()
    {
        return this.insideDirection.getFrontOffsetZ() * 2;
    }

    public int getLastActivityTimestamp()
    {
        return this.lastActivityTimestamp;
    }

    public void setLastActivityTimestamp(int timestamp)
    {
        this.lastActivityTimestamp = timestamp;
    }

    public boolean getIsDetachedFromVillageFlag()
    {
        return this.isDetachedFromVillageFlag;
    }

    public void setIsDetachedFromVillageFlag(boolean detached)
    {
        this.isDetachedFromVillageFlag = detached;
    }

    public EnumFacing getInsideDirection()
    {
        return this.insideDirection;
    }
}
