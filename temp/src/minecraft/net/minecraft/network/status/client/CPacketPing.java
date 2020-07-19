package net.minecraft.network.status.client;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.status.INetHandlerStatusServer;

public class CPacketPing implements Packet<INetHandlerStatusServer> {
   private long field_149290_a;

   public CPacketPing() {
   }

   public CPacketPing(long p_i46842_1_) {
      this.field_149290_a = p_i46842_1_;
   }

   public void func_148837_a(PacketBuffer p_148837_1_) throws IOException {
      this.field_149290_a = p_148837_1_.readLong();
   }

   public void func_148840_b(PacketBuffer p_148840_1_) throws IOException {
      p_148840_1_.writeLong(this.field_149290_a);
   }

   public void func_148833_a(INetHandlerStatusServer p_148833_1_) {
      p_148833_1_.func_147311_a(this);
   }

   public long func_149289_c() {
      return this.field_149290_a;
   }
}
