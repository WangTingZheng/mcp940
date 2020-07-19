package net.minecraft.util;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.network.play.server.SPacketCooldown;

public class CooldownTrackerServer extends CooldownTracker
{
    private final EntityPlayerMP player;

    public CooldownTrackerServer(EntityPlayerMP playerIn)
    {
        this.player = playerIn;
    }

    protected void notifyOnSet(Item itemIn, int ticksIn)
    {
        super.notifyOnSet(itemIn, ticksIn);
        this.player.connection.sendPacket(new SPacketCooldown(itemIn, ticksIn));
    }

    protected void notifyOnRemove(Item itemIn)
    {
        super.notifyOnRemove(itemIn);
        this.player.connection.sendPacket(new SPacketCooldown(itemIn, 0));
    }
}
