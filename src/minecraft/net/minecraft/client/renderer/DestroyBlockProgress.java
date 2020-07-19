package net.minecraft.client.renderer;

import net.minecraft.util.math.BlockPos;

public class DestroyBlockProgress
{
    /**
     * entity ID of the player associated with this partially destroyed Block. Used to identify the Blocks in the client
     * Renderer, max 1 per player on a server
     */
    private final int miningPlayerEntId;
    private final BlockPos position;

    /**
     * damage ranges from 1 to 10. -1 causes the client to delete the partial block renderer.
     */
    private int partialBlockProgress;

    /**
     * keeps track of how many ticks this PartiallyDestroyedBlock already exists
     */
    private int createdAtCloudUpdateTick;

    public DestroyBlockProgress(int miningPlayerEntIdIn, BlockPos positionIn)
    {
        this.miningPlayerEntId = miningPlayerEntIdIn;
        this.position = positionIn;
    }

    public BlockPos getPosition()
    {
        return this.position;
    }

    /**
     * inserts damage value into this partially destroyed Block. -1 causes client renderer to delete it, otherwise
     * ranges from 1 to 10
     */
    public void setPartialBlockDamage(int damage)
    {
        if (damage > 10)
        {
            damage = 10;
        }

        this.partialBlockProgress = damage;
    }

    public int getPartialBlockDamage()
    {
        return this.partialBlockProgress;
    }

    /**
     * saves the current Cloud update tick into the PartiallyDestroyedBlock
     */
    public void setCloudUpdateTick(int createdAtCloudUpdateTickIn)
    {
        this.createdAtCloudUpdateTick = createdAtCloudUpdateTickIn;
    }

    /**
     * retrieves the 'date' at which the PartiallyDestroyedBlock was created
     */
    public int getCreationCloudUpdateTick()
    {
        return this.createdAtCloudUpdateTick;
    }
}
