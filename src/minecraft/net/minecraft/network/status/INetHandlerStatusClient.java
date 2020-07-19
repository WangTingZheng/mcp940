package net.minecraft.network.status;

import net.minecraft.network.INetHandler;
import net.minecraft.network.status.server.SPacketPong;
import net.minecraft.network.status.server.SPacketServerInfo;

public interface INetHandlerStatusClient extends INetHandler
{
    void handleServerInfo(SPacketServerInfo packetIn);

    void handlePong(SPacketPong packetIn);
}
