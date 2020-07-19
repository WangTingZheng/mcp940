package net.minecraft.client.network;

import net.minecraft.network.NetworkManager;
import net.minecraft.network.handshake.INetHandlerHandshakeServer;
import net.minecraft.network.handshake.client.C00Handshake;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.NetHandlerLoginServer;
import net.minecraft.util.text.ITextComponent;

public class NetHandlerHandshakeMemory implements INetHandlerHandshakeServer {
   private final MinecraftServer field_147385_a;
   private final NetworkManager field_147384_b;

   public NetHandlerHandshakeMemory(MinecraftServer p_i45287_1_, NetworkManager p_i45287_2_) {
      this.field_147385_a = p_i45287_1_;
      this.field_147384_b = p_i45287_2_;
   }

   public void func_147383_a(C00Handshake p_147383_1_) {
      this.field_147384_b.func_150723_a(p_147383_1_.func_149594_c());
      this.field_147384_b.func_150719_a(new NetHandlerLoginServer(this.field_147385_a, this.field_147384_b));
   }

   public void func_147231_a(ITextComponent p_147231_1_) {
   }
}
