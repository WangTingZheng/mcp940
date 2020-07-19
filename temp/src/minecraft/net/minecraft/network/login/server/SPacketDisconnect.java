package net.minecraft.network.login.server;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.login.INetHandlerLoginClient;
import net.minecraft.util.text.ITextComponent;

public class SPacketDisconnect implements Packet<INetHandlerLoginClient> {
   private ITextComponent field_149605_a;

   public SPacketDisconnect() {
   }

   public SPacketDisconnect(ITextComponent p_i46853_1_) {
      this.field_149605_a = p_i46853_1_;
   }

   public void func_148837_a(PacketBuffer p_148837_1_) throws IOException {
      this.field_149605_a = ITextComponent.Serializer.func_186877_b(p_148837_1_.func_150789_c(32767));
   }

   public void func_148840_b(PacketBuffer p_148840_1_) throws IOException {
      p_148840_1_.func_179256_a(this.field_149605_a);
   }

   public void func_148833_a(INetHandlerLoginClient p_148833_1_) {
      p_148833_1_.func_147388_a(this);
   }

   public ITextComponent func_149603_c() {
      return this.field_149605_a;
   }
}
