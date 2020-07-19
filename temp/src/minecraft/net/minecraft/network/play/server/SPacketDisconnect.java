package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.text.ITextComponent;

public class SPacketDisconnect implements Packet<INetHandlerPlayClient> {
   private ITextComponent field_149167_a;

   public SPacketDisconnect() {
   }

   public SPacketDisconnect(ITextComponent p_i46947_1_) {
      this.field_149167_a = p_i46947_1_;
   }

   public void func_148837_a(PacketBuffer p_148837_1_) throws IOException {
      this.field_149167_a = p_148837_1_.func_179258_d();
   }

   public void func_148840_b(PacketBuffer p_148840_1_) throws IOException {
      p_148840_1_.func_179256_a(this.field_149167_a);
   }

   public void func_148833_a(INetHandlerPlayClient p_148833_1_) {
      p_148833_1_.func_147253_a(this);
   }

   public ITextComponent func_149165_c() {
      return this.field_149167_a;
   }
}
