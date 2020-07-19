package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class SPacketResourcePackSend implements Packet<INetHandlerPlayClient> {
   private String field_179786_a;
   private String field_179785_b;

   public SPacketResourcePackSend() {
   }

   public SPacketResourcePackSend(String p_i46924_1_, String p_i46924_2_) {
      this.field_179786_a = p_i46924_1_;
      this.field_179785_b = p_i46924_2_;
      if (p_i46924_2_.length() > 40) {
         throw new IllegalArgumentException("Hash is too long (max 40, was " + p_i46924_2_.length() + ")");
      }
   }

   public void func_148837_a(PacketBuffer p_148837_1_) throws IOException {
      this.field_179786_a = p_148837_1_.func_150789_c(32767);
      this.field_179785_b = p_148837_1_.func_150789_c(40);
   }

   public void func_148840_b(PacketBuffer p_148840_1_) throws IOException {
      p_148840_1_.func_180714_a(this.field_179786_a);
      p_148840_1_.func_180714_a(this.field_179785_b);
   }

   public void func_148833_a(INetHandlerPlayClient p_148833_1_) {
      p_148833_1_.func_175095_a(this);
   }

   public String func_179783_a() {
      return this.field_179786_a;
   }

   public String func_179784_b() {
      return this.field_179785_b;
   }
}
