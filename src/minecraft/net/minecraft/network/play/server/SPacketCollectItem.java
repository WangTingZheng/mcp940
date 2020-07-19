package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class SPacketCollectItem implements Packet<INetHandlerPlayClient>
{
    private int collectedItemEntityId;
    private int entityId;
    private int collectedQuantity;

    public SPacketCollectItem()
    {
    }

    public SPacketCollectItem(int p_i47316_1_, int p_i47316_2_, int p_i47316_3_)
    {
        this.collectedItemEntityId = p_i47316_1_;
        this.entityId = p_i47316_2_;
        this.collectedQuantity = p_i47316_3_;
    }

    /**
     * Reads the raw packet data from the data stream.
     */
    public void readPacketData(PacketBuffer buf) throws IOException
    {
        this.collectedItemEntityId = buf.readVarInt();
        this.entityId = buf.readVarInt();
        this.collectedQuantity = buf.readVarInt();
    }

    /**
     * Writes the raw packet data to the data stream.
     */
    public void writePacketData(PacketBuffer buf) throws IOException
    {
        buf.writeVarInt(this.collectedItemEntityId);
        buf.writeVarInt(this.entityId);
        buf.writeVarInt(this.collectedQuantity);
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    public void processPacket(INetHandlerPlayClient handler)
    {
        handler.handleCollectItem(this);
    }

    public int getCollectedItemEntityID()
    {
        return this.collectedItemEntityId;
    }

    public int getEntityID()
    {
        return this.entityId;
    }

    public int getAmount()
    {
        return this.collectedQuantity;
    }
}
