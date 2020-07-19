package net.minecraft.network.play.client;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;

public class CPacketHeldItemChange implements Packet<INetHandlerPlayServer> {
   private int field_149615_a;

   public CPacketHeldItemChange() {
   }

   public CPacketHeldItemChange(int p_i46864_1_) {
      this.field_149615_a = p_i46864_1_;
   }

   public void func_148837_a(PacketBuffer p_148837_1_) throws IOException {
      this.field_149615_a = p_148837_1_.readShort();
   }

   public void func_148840_b(PacketBuffer p_148840_1_) throws IOException {
      p_148840_1_.writeShort(this.field_149615_a);
   }

   public void func_148833_a(INetHandlerPlayServer p_148833_1_) {
      p_148833_1_.func_147355_a(this);
   }

   public int func_149614_c() {
      return this.field_149615_a;
   }
}
