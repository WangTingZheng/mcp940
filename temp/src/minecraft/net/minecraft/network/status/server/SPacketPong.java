package net.minecraft.network.status.server;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.status.INetHandlerStatusClient;

public class SPacketPong implements Packet<INetHandlerStatusClient> {
   private long field_149293_a;

   public SPacketPong() {
   }

   public SPacketPong(long p_i46850_1_) {
      this.field_149293_a = p_i46850_1_;
   }

   public void func_148837_a(PacketBuffer p_148837_1_) throws IOException {
      this.field_149293_a = p_148837_1_.readLong();
   }

   public void func_148840_b(PacketBuffer p_148840_1_) throws IOException {
      p_148840_1_.writeLong(this.field_149293_a);
   }

   public void func_148833_a(INetHandlerStatusClient p_148833_1_) {
      p_148833_1_.func_147398_a(this);
   }
}
