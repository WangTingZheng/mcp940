package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class SPacketResourcePackSend implements Packet<INetHandlerPlayClient>
{
    private String url;
    private String hash;

    public SPacketResourcePackSend()
    {
    }

    public SPacketResourcePackSend(String urlIn, String hashIn)
    {
        this.url = urlIn;
        this.hash = hashIn;

        if (hashIn.length() > 40)
        {
            throw new IllegalArgumentException("Hash is too long (max 40, was " + hashIn.length() + ")");
        }
    }

    /**
     * Reads the raw packet data from the data stream.
     */
    public void readPacketData(PacketBuffer buf) throws IOException
    {
        this.url = buf.readString(32767);
        this.hash = buf.readString(40);
    }

    /**
     * Writes the raw packet data to the data stream.
     */
    public void writePacketData(PacketBuffer buf) throws IOException
    {
        buf.writeString(this.url);
        buf.writeString(this.hash);
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    public void processPacket(INetHandlerPlayClient handler)
    {
        handler.handleResourcePack(this);
    }

    public String getURL()
    {
        return this.url;
    }

    public String getHash()
    {
        return this.hash;
    }
}
