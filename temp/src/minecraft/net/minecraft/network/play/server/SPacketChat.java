package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.ITextComponent;

public class SPacketChat implements Packet<INetHandlerPlayClient> {
   private ITextComponent field_148919_a;
   private ChatType field_179842_b;

   public SPacketChat() {
   }

   public SPacketChat(ITextComponent p_i46960_1_) {
      this(p_i46960_1_, ChatType.SYSTEM);
   }

   public SPacketChat(ITextComponent p_i47428_1_, ChatType p_i47428_2_) {
      this.field_148919_a = p_i47428_1_;
      this.field_179842_b = p_i47428_2_;
   }

   public void func_148837_a(PacketBuffer p_148837_1_) throws IOException {
      this.field_148919_a = p_148837_1_.func_179258_d();
      this.field_179842_b = ChatType.func_192582_a(p_148837_1_.readByte());
   }

   public void func_148840_b(PacketBuffer p_148840_1_) throws IOException {
      p_148840_1_.func_179256_a(this.field_148919_a);
      p_148840_1_.writeByte(this.field_179842_b.func_192583_a());
   }

   public void func_148833_a(INetHandlerPlayClient p_148833_1_) {
      p_148833_1_.func_147251_a(this);
   }

   public ITextComponent func_148915_c() {
      return this.field_148919_a;
   }

   public boolean func_148916_d() {
      return this.field_179842_b == ChatType.SYSTEM || this.field_179842_b == ChatType.GAME_INFO;
   }

   public ChatType func_192590_c() {
      return this.field_179842_b;
   }
}
