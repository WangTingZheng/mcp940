package net.minecraft.network.play.client;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;

public class CPacketConfirmTeleport implements Packet<INetHandlerPlayServer> {
   private int field_186988_a;

   public CPacketConfirmTeleport() {
   }

   public CPacketConfirmTeleport(int p_i46889_1_) {
      this.field_186988_a = p_i46889_1_;
   }

   public void func_148837_a(PacketBuffer p_148837_1_) throws IOException {
      this.field_186988_a = p_148837_1_.func_150792_a();
   }

   public void func_148840_b(PacketBuffer p_148840_1_) throws IOException {
      p_148840_1_.func_150787_b(this.field_186988_a);
   }

   public void func_148833_a(INetHandlerPlayServer p_148833_1_) {
      p_148833_1_.func_184339_a(this);
   }

   public int func_186987_a() {
      return this.field_186988_a;
   }
}
