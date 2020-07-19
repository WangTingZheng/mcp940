package net.minecraft.network.login;

import net.minecraft.network.INetHandler;
import net.minecraft.network.login.client.CPacketEncryptionResponse;
import net.minecraft.network.login.client.CPacketLoginStart;

public interface INetHandlerLoginServer extends INetHandler {
   void func_147316_a(CPacketLoginStart var1);

   void func_147315_a(CPacketEncryptionResponse var1);
}
