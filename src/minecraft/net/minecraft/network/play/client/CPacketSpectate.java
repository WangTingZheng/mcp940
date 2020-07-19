package net.minecraft.network.play.client;

import java.io.IOException;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;
import net.minecraft.world.WorldServer;

public class CPacketSpectate implements Packet<INetHandlerPlayServer>
{
    private UUID id;

    public CPacketSpectate()
    {
    }

    public CPacketSpectate(UUID uniqueIdIn)
    {
        this.id = uniqueIdIn;
    }

    /**
     * Reads the raw packet data from the data stream.
     */
    public void readPacketData(PacketBuffer buf) throws IOException
    {
        this.id = buf.readUniqueId();
    }

    /**
     * Writes the raw packet data to the data stream.
     */
    public void writePacketData(PacketBuffer buf) throws IOException
    {
        buf.writeUniqueId(this.id);
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    public void processPacket(INetHandlerPlayServer handler)
    {
        handler.handleSpectate(this);
    }

    @Nullable
    public Entity getEntity(WorldServer worldIn)
    {
        return worldIn.getEntityFromUuid(this.id);
    }
}
