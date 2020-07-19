package net.minecraft.entity.ai;

import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;

public class EntityAILookAtTradePlayer extends EntityAIWatchClosest
{
    private final EntityVillager villager;

    public EntityAILookAtTradePlayer(EntityVillager villagerIn)
    {
        super(villagerIn, EntityPlayer.class, 8.0F);
        this.villager = villagerIn;
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        if (this.villager.isTrading())
        {
            this.closestEntity = this.villager.getCustomer();
            return true;
        }
        else
        {
            return false;
        }
    }
}
