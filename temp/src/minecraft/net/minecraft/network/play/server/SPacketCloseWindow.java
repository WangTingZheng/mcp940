package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class SPacketCloseWindow implements Packet<INetHandlerPlayClient> {
   private int field_148896_a;

   public SPacketCloseWindow() {
   }

   public SPacketCloseWindow(int p_i46957_1_) {
      this.field_148896_a = p_i46957_1_;
   }

   public void func_148833_a(INetHandlerPlayClient p_148833_1_) {
      p_148833_1_.func_147276_a(this);
   }

   public void func_148837_a(PacketBuffer p_148837_1_) throws IOException {
      this.field_148896_a = p_148837_1_.readUnsignedByte();
   }

   public void func_148840_b(PacketBuffer p_148840_1_) throws IOException {
      p_148840_1_.writeByte(this.field_148896_a);
   }
}
