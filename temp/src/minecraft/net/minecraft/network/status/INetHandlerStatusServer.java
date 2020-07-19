package net.minecraft.network.status;

import net.minecraft.network.INetHandler;
import net.minecraft.network.status.client.CPacketPing;
import net.minecraft.network.status.client.CPacketServerQuery;

public interface INetHandlerStatusServer extends INetHandler {
   void func_147311_a(CPacketPing var1);

   void func_147312_a(CPacketServerQuery var1);
}
