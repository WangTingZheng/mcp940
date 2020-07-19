package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class SPacketHeldItemChange implements Packet<INetHandlerPlayClient>
{
    private int heldItemHotbarIndex;

    public SPacketHeldItemChange()
    {
    }

    public SPacketHeldItemChange(int hotbarIndexIn)
    {
        this.heldItemHotbarIndex = hotbarIndexIn;
    }

    /**
     * Reads the raw packet data from the data stream.
     */
    public void readPacketData(PacketBuffer buf) throws IOException
    {
        this.heldItemHotbarIndex = buf.readByte();
    }

    /**
     * Writes the raw packet data to the data stream.
     */
    public void writePacketData(PacketBuffer buf) throws IOException
    {
        buf.writeByte(this.heldItemHotbarIndex);
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    public void processPacket(INetHandlerPlayClient handler)
    {
        handler.handleHeldItemChange(this);
    }

    public int getHeldItemHotbarIndex()
    {
        return this.heldItemHotbarIndex;
    }
}
