package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class SPacketSpawnGlobalEntity implements Packet<INetHandlerPlayClient>
{
    private int entityId;
    private double x;
    private double y;
    private double z;
    private int type;

    public SPacketSpawnGlobalEntity()
    {
    }

    public SPacketSpawnGlobalEntity(Entity entityIn)
    {
        this.entityId = entityIn.getEntityId();
        this.x = entityIn.posX;
        this.y = entityIn.posY;
        this.z = entityIn.posZ;

        if (entityIn instanceof EntityLightningBolt)
        {
            this.type = 1;
        }
    }

    /**
     * Reads the raw packet data from the data stream.
     */
    public void readPacketData(PacketBuffer buf) throws IOException
    {
        this.entityId = buf.readVarInt();
        this.type = buf.readByte();
        this.x = buf.readDouble();
        this.y = buf.readDouble();
        this.z = buf.readDouble();
    }

    /**
     * Writes the raw packet data to the data stream.
     */
    public void writePacketData(PacketBuffer buf) throws IOException
    {
        buf.writeVarInt(this.entityId);
        buf.writeByte(this.type);
        buf.writeDouble(this.x);
        buf.writeDouble(this.y);
        buf.writeDouble(this.z);
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    public void processPacket(INetHandlerPlayClient handler)
    {
        handler.handleSpawnGlobalEntity(this);
    }

    public int getEntityId()
    {
        return this.entityId;
    }

    public double getX()
    {
        return this.x;
    }

    public double getY()
    {
        return this.y;
    }

    public double getZ()
    {
        return this.z;
    }

    public int getType()
    {
        return this.type;
    }
}
