package net.minecraft.network.play.client;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;

public class CPacketResourcePackStatus implements Packet<INetHandlerPlayServer> {
   private CPacketResourcePackStatus.Action field_179719_b;

   public CPacketResourcePackStatus() {
   }

   public CPacketResourcePackStatus(CPacketResourcePackStatus.Action p_i47156_1_) {
      this.field_179719_b = p_i47156_1_;
   }

   public void func_148837_a(PacketBuffer p_148837_1_) throws IOException {
      this.field_179719_b = (CPacketResourcePackStatus.Action)p_148837_1_.func_179257_a(CPacketResourcePackStatus.Action.class);
   }

   public void func_148840_b(PacketBuffer p_148840_1_) throws IOException {
      p_148840_1_.func_179249_a(this.field_179719_b);
   }

   public void func_148833_a(INetHandlerPlayServer p_148833_1_) {
      p_148833_1_.func_175086_a(this);
   }

   public static enum Action {
      SUCCESSFULLY_LOADED,
      DECLINED,
      FAILED_DOWNLOAD,
      ACCEPTED;
   }
}
