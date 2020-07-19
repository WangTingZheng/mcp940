package net.minecraft.network.play.client;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;
import net.minecraft.util.EnumHand;

public class CPacketPlayerTryUseItem implements Packet<INetHandlerPlayServer> {
   private EnumHand field_187029_a;

   public CPacketPlayerTryUseItem() {
   }

   public CPacketPlayerTryUseItem(EnumHand p_i46857_1_) {
      this.field_187029_a = p_i46857_1_;
   }

   public void func_148837_a(PacketBuffer p_148837_1_) throws IOException {
      this.field_187029_a = (EnumHand)p_148837_1_.func_179257_a(EnumHand.class);
   }

   public void func_148840_b(PacketBuffer p_148840_1_) throws IOException {
      p_148840_1_.func_179249_a(this.field_187029_a);
   }

   public void func_148833_a(INetHandlerPlayServer p_148833_1_) {
      p_148833_1_.func_147346_a(this);
   }

   public EnumHand func_187028_a() {
      return this.field_187029_a;
   }
}
