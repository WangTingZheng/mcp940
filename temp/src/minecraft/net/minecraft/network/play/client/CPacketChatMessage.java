package net.minecraft.network.play.client;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;

public class CPacketChatMessage implements Packet<INetHandlerPlayServer> {
   private String field_149440_a;

   public CPacketChatMessage() {
   }

   public CPacketChatMessage(String p_i46887_1_) {
      if (p_i46887_1_.length() > 256) {
         p_i46887_1_ = p_i46887_1_.substring(0, 256);
      }

      this.field_149440_a = p_i46887_1_;
   }

   public void func_148837_a(PacketBuffer p_148837_1_) throws IOException {
      this.field_149440_a = p_148837_1_.func_150789_c(256);
   }

   public void func_148840_b(PacketBuffer p_148840_1_) throws IOException {
      p_148840_1_.func_180714_a(this.field_149440_a);
   }

   public void func_148833_a(INetHandlerPlayServer p_148833_1_) {
      p_148833_1_.func_147354_a(this);
   }

   public String func_149439_c() {
      return this.field_149440_a;
   }
}
