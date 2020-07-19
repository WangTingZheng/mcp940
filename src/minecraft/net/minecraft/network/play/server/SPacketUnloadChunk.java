package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class SPacketUnloadChunk implements Packet<INetHandlerPlayClient>
{
    private int x;
    private int z;

    public SPacketUnloadChunk()
    {
    }

    public SPacketUnloadChunk(int xIn, int zIn)
    {
        this.x = xIn;
        this.z = zIn;
    }

    /**
     * Reads the raw packet data from the data stream.
     */
    public void readPacketData(PacketBuffer buf) throws IOException
    {
        this.x = buf.readInt();
        this.z = buf.readInt();
    }

    /**
     * Writes the raw packet data to the data stream.
     */
    public void writePacketData(PacketBuffer buf) throws IOException
    {
        buf.writeInt(this.x);
        buf.writeInt(this.z);
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    public void processPacket(INetHandlerPlayClient handler)
    {
        handler.processChunkUnload(this);
    }

    public int getX()
    {
        return this.x;
    }

    public int getZ()
    {
        return this.z;
    }
}
