package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class SPacketTabComplete implements Packet<INetHandlerPlayClient>
{
    private String[] matches;

    public SPacketTabComplete()
    {
    }

    public SPacketTabComplete(String[] matchesIn)
    {
        this.matches = matchesIn;
    }

    /**
     * Reads the raw packet data from the data stream.
     */
    public void readPacketData(PacketBuffer buf) throws IOException
    {
        this.matches = new String[buf.readVarInt()];

        for (int i = 0; i < this.matches.length; ++i)
        {
            this.matches[i] = buf.readString(32767);
        }
    }

    /**
     * Writes the raw packet data to the data stream.
     */
    public void writePacketData(PacketBuffer buf) throws IOException
    {
        buf.writeVarInt(this.matches.length);

        for (String s : this.matches)
        {
            buf.writeString(s);
        }
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    public void processPacket(INetHandlerPlayClient handler)
    {
        handler.handleTabComplete(this);
    }

    public String[] getMatches()
    {
        return this.matches;
    }
}
