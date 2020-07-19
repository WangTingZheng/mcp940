package net.minecraft.network.play.client;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;

public class CPacketClientStatus implements Packet<INetHandlerPlayServer> {
   private CPacketClientStatus.State field_149437_a;

   public CPacketClientStatus() {
   }

   public CPacketClientStatus(CPacketClientStatus.State p_i46886_1_) {
      this.field_149437_a = p_i46886_1_;
   }

   public void func_148837_a(PacketBuffer p_148837_1_) throws IOException {
      this.field_149437_a = (CPacketClientStatus.State)p_148837_1_.func_179257_a(CPacketClientStatus.State.class);
   }

   public void func_148840_b(PacketBuffer p_148840_1_) throws IOException {
      p_148840_1_.func_179249_a(this.field_149437_a);
   }

   public void func_148833_a(INetHandlerPlayServer p_148833_1_) {
      p_148833_1_.func_147342_a(this);
   }

   public CPacketClientStatus.State func_149435_c() {
      return this.field_149437_a;
   }

   public static enum State {
      PERFORM_RESPAWN,
      REQUEST_STATS;
   }
}
