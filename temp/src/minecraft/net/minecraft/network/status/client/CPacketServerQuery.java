package net.minecraft.network.status.client;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.status.INetHandlerStatusServer;

public class CPacketServerQuery implements Packet<INetHandlerStatusServer> {
   public void func_148837_a(PacketBuffer p_148837_1_) throws IOException {
   }

   public void func_148840_b(PacketBuffer p_148840_1_) throws IOException {
   }

   public void func_148833_a(INetHandlerStatusServer p_148833_1_) {
      p_148833_1_.func_147312_a(this);
   }
}
