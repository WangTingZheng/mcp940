package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class SPacketHeldItemChange implements Packet<INetHandlerPlayClient> {
   private int field_149387_a;

   public SPacketHeldItemChange() {
   }

   public SPacketHeldItemChange(int p_i46919_1_) {
      this.field_149387_a = p_i46919_1_;
   }

   public void func_148837_a(PacketBuffer p_148837_1_) throws IOException {
      this.field_149387_a = p_148837_1_.readByte();
   }

   public void func_148840_b(PacketBuffer p_148840_1_) throws IOException {
      p_148840_1_.writeByte(this.field_149387_a);
   }

   public void func_148833_a(INetHandlerPlayClient p_148833_1_) {
      p_148833_1_.func_147257_a(this);
   }

   public int func_149385_c() {
      return this.field_149387_a;
   }
}
