package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class SPacketSetExperience implements Packet<INetHandlerPlayClient>
{
    private float experienceBar;
    private int totalExperience;
    private int level;

    public SPacketSetExperience()
    {
    }

    public SPacketSetExperience(float experienceBarIn, int totalExperienceIn, int levelIn)
    {
        this.experienceBar = experienceBarIn;
        this.totalExperience = totalExperienceIn;
        this.level = levelIn;
    }

    /**
     * Reads the raw packet data from the data stream.
     */
    public void readPacketData(PacketBuffer buf) throws IOException
    {
        this.experienceBar = buf.readFloat();
        this.level = buf.readVarInt();
        this.totalExperience = buf.readVarInt();
    }

    /**
     * Writes the raw packet data to the data stream.
     */
    public void writePacketData(PacketBuffer buf) throws IOException
    {
        buf.writeFloat(this.experienceBar);
        buf.writeVarInt(this.level);
        buf.writeVarInt(this.totalExperience);
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    public void processPacket(INetHandlerPlayClient handler)
    {
        handler.handleSetExperience(this);
    }

    public float getExperienceBar()
    {
        return this.experienceBar;
    }

    public int getTotalExperience()
    {
        return this.totalExperience;
    }

    public int getLevel()
    {
        return this.level;
    }
}
