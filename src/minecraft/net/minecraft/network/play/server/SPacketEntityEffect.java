package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class SPacketEntityEffect implements Packet<INetHandlerPlayClient>
{
    private int entityId;
    private byte effectId;
    private byte amplifier;
    private int duration;
    private byte flags;

    public SPacketEntityEffect()
    {
    }

    public SPacketEntityEffect(int entityIdIn, PotionEffect effect)
    {
        this.entityId = entityIdIn;
        this.effectId = (byte)(Potion.getIdFromPotion(effect.getPotion()) & 255);
        this.amplifier = (byte)(effect.getAmplifier() & 255);

        if (effect.getDuration() > 32767)
        {
            this.duration = 32767;
        }
        else
        {
            this.duration = effect.getDuration();
        }

        this.flags = 0;

        if (effect.getIsAmbient())
        {
            this.flags = (byte)(this.flags | 1);
        }

        if (effect.doesShowParticles())
        {
            this.flags = (byte)(this.flags | 2);
        }
    }

    /**
     * Reads the raw packet data from the data stream.
     */
    public void readPacketData(PacketBuffer buf) throws IOException
    {
        this.entityId = buf.readVarInt();
        this.effectId = buf.readByte();
        this.amplifier = buf.readByte();
        this.duration = buf.readVarInt();
        this.flags = buf.readByte();
    }

    /**
     * Writes the raw packet data to the data stream.
     */
    public void writePacketData(PacketBuffer buf) throws IOException
    {
        buf.writeVarInt(this.entityId);
        buf.writeByte(this.effectId);
        buf.writeByte(this.amplifier);
        buf.writeVarInt(this.duration);
        buf.writeByte(this.flags);
    }

    public boolean isMaxDuration()
    {
        return this.duration == 32767;
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    public void processPacket(INetHandlerPlayClient handler)
    {
        handler.handleEntityEffect(this);
    }

    public int getEntityId()
    {
        return this.entityId;
    }

    public byte getEffectId()
    {
        return this.effectId;
    }

    public byte getAmplifier()
    {
        return this.amplifier;
    }

    public int getDuration()
    {
        return this.duration;
    }

    public boolean doesShowParticles()
    {
        return (this.flags & 2) == 2;
    }

    public boolean getIsAmbient()
    {
        return (this.flags & 1) == 1;
    }
}
