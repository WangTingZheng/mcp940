package net.minecraft.network;

import java.io.IOException;

public interface Packet<T extends INetHandler>
{
    /**
     * Reads the raw packet data from the data stream.
     */
    void readPacketData(PacketBuffer buf) throws IOException;

    /**
     * Writes the raw packet data to the data stream.
     */
    void writePacketData(PacketBuffer buf) throws IOException;

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    void processPacket(T handler);
}
