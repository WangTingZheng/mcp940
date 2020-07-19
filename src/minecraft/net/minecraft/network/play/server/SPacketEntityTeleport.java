package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class SPacketEntityTeleport implements Packet<INetHandlerPlayClient>
{
    private int entityId;
    private double posX;
    private double posY;
    private double posZ;
    private byte yaw;
    private byte pitch;
    private boolean onGround;

    public SPacketEntityTeleport()
    {
    }

    public SPacketEntityTeleport(Entity entityIn)
    {
        this.entityId = entityIn.getEntityId();
        this.posX = entityIn.posX;
        this.posY = entityIn.posY;
        this.posZ = entityIn.posZ;
        this.yaw = (byte)((int)(entityIn.rotationYaw * 256.0F / 360.0F));
        this.pitch = (byte)((int)(entityIn.rotationPitch * 256.0F / 360.0F));
        this.onGround = entityIn.onGround;
    }

    /**
     * Reads the raw packet data from the data stream.
     */
    public void readPacketData(PacketBuffer buf) throws IOException
    {
        this.entityId = buf.readVarInt();
        this.posX = buf.readDouble();
        this.posY = buf.readDouble();
        this.posZ = buf.readDouble();
        this.yaw = buf.readByte();
        this.pitch = buf.readByte();
        this.onGround = buf.readBoolean();
    }

    /**
     * Writes the raw packet data to the data stream.
     */
    public void writePacketData(PacketBuffer buf) throws IOException
    {
        buf.writeVarInt(this.entityId);
        buf.writeDouble(this.posX);
        buf.writeDouble(this.posY);
        buf.writeDouble(this.posZ);
        buf.writeByte(this.yaw);
        buf.writeByte(this.pitch);
        buf.writeBoolean(this.onGround);
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    public void processPacket(INetHandlerPlayClient handler)
    {
        handler.handleEntityTeleport(this);
    }

    public int getEntityId()
    {
        return this.entityId;
    }

    public double getX()
    {
        return this.posX;
    }

    public double getY()
    {
        return this.posY;
    }

    public double getZ()
    {
        return this.posZ;
    }

    public byte getYaw()
    {
        return this.yaw;
    }

    public byte getPitch()
    {
        return this.pitch;
    }

    public boolean getOnGround()
    {
        return this.onGround;
    }
}
