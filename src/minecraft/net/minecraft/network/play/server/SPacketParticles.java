package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.EnumParticleTypes;

public class SPacketParticles implements Packet<INetHandlerPlayClient>
{
    private EnumParticleTypes particleType;
    private float xCoord;
    private float yCoord;
    private float zCoord;
    private float xOffset;
    private float yOffset;
    private float zOffset;
    private float particleSpeed;
    private int particleCount;
    private boolean longDistance;

    /**
     * These are the block/item ids and possibly metaData ids that are used to color or texture the particle.
     */
    private int[] particleArguments;

    public SPacketParticles()
    {
    }

    public SPacketParticles(EnumParticleTypes particleIn, boolean longDistanceIn, float xIn, float yIn, float zIn, float xOffsetIn, float yOffsetIn, float zOffsetIn, float speedIn, int countIn, int... argumentsIn)
    {
        this.particleType = particleIn;
        this.longDistance = longDistanceIn;
        this.xCoord = xIn;
        this.yCoord = yIn;
        this.zCoord = zIn;
        this.xOffset = xOffsetIn;
        this.yOffset = yOffsetIn;
        this.zOffset = zOffsetIn;
        this.particleSpeed = speedIn;
        this.particleCount = countIn;
        this.particleArguments = argumentsIn;
    }

    /**
     * Reads the raw packet data from the data stream.
     */
    public void readPacketData(PacketBuffer buf) throws IOException
    {
        this.particleType = EnumParticleTypes.getParticleFromId(buf.readInt());

        if (this.particleType == null)
        {
            this.particleType = EnumParticleTypes.BARRIER;
        }

        this.longDistance = buf.readBoolean();
        this.xCoord = buf.readFloat();
        this.yCoord = buf.readFloat();
        this.zCoord = buf.readFloat();
        this.xOffset = buf.readFloat();
        this.yOffset = buf.readFloat();
        this.zOffset = buf.readFloat();
        this.particleSpeed = buf.readFloat();
        this.particleCount = buf.readInt();
        int i = this.particleType.getArgumentCount();
        this.particleArguments = new int[i];

        for (int j = 0; j < i; ++j)
        {
            this.particleArguments[j] = buf.readVarInt();
        }
    }

    /**
     * Writes the raw packet data to the data stream.
     */
    public void writePacketData(PacketBuffer buf) throws IOException
    {
        buf.writeInt(this.particleType.getParticleID());
        buf.writeBoolean(this.longDistance);
        buf.writeFloat(this.xCoord);
        buf.writeFloat(this.yCoord);
        buf.writeFloat(this.zCoord);
        buf.writeFloat(this.xOffset);
        buf.writeFloat(this.yOffset);
        buf.writeFloat(this.zOffset);
        buf.writeFloat(this.particleSpeed);
        buf.writeInt(this.particleCount);
        int i = this.particleType.getArgumentCount();

        for (int j = 0; j < i; ++j)
        {
            buf.writeVarInt(this.particleArguments[j]);
        }
    }

    public EnumParticleTypes getParticleType()
    {
        return this.particleType;
    }

    public boolean isLongDistance()
    {
        return this.longDistance;
    }

    /**
     * Gets the x coordinate to spawn the particle.
     */
    public double getXCoordinate()
    {
        return (double)this.xCoord;
    }

    /**
     * Gets the y coordinate to spawn the particle.
     */
    public double getYCoordinate()
    {
        return (double)this.yCoord;
    }

    /**
     * Gets the z coordinate to spawn the particle.
     */
    public double getZCoordinate()
    {
        return (double)this.zCoord;
    }

    /**
     * Gets the x coordinate offset for the particle. The particle may use the offset for particle spread.
     */
    public float getXOffset()
    {
        return this.xOffset;
    }

    /**
     * Gets the y coordinate offset for the particle. The particle may use the offset for particle spread.
     */
    public float getYOffset()
    {
        return this.yOffset;
    }

    /**
     * Gets the z coordinate offset for the particle. The particle may use the offset for particle spread.
     */
    public float getZOffset()
    {
        return this.zOffset;
    }

    /**
     * Gets the speed of the particle animation (used in client side rendering).
     */
    public float getParticleSpeed()
    {
        return this.particleSpeed;
    }

    /**
     * Gets the amount of particles to spawn
     */
    public int getParticleCount()
    {
        return this.particleCount;
    }

    /**
     * Gets the particle arguments. Some particles rely on block and/or item ids and sometimes metadata ids to color or
     * texture the particle.
     */
    public int[] getParticleArgs()
    {
        return this.particleArguments;
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    public void processPacket(INetHandlerPlayClient handler)
    {
        handler.handleParticles(this);
    }
}
