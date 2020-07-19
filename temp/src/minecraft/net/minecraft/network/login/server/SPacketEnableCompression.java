package net.minecraft.network.login.server;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.login.INetHandlerLoginClient;

public class SPacketEnableCompression implements Packet<INetHandlerLoginClient> {
   private int field_179733_a;

   public SPacketEnableCompression() {
   }

   public SPacketEnableCompression(int p_i46854_1_) {
      this.field_179733_a = p_i46854_1_;
   }

   public void func_148837_a(PacketBuffer p_148837_1_) throws IOException {
      this.field_179733_a = p_148837_1_.func_150792_a();
   }

   public void func_148840_b(PacketBuffer p_148840_1_) throws IOException {
      p_148840_1_.func_150787_b(this.field_179733_a);
   }

   public void func_148833_a(INetHandlerLoginClient p_148833_1_) {
      p_148833_1_.func_180464_a(this);
   }

   public int func_179731_a() {
      return this.field_179733_a;
   }
}
