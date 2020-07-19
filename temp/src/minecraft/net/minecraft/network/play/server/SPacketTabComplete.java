package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class SPacketTabComplete implements Packet<INetHandlerPlayClient> {
   private String[] field_149632_a;

   public SPacketTabComplete() {
   }

   public SPacketTabComplete(String[] p_i46962_1_) {
      this.field_149632_a = p_i46962_1_;
   }

   public void func_148837_a(PacketBuffer p_148837_1_) throws IOException {
      this.field_149632_a = new String[p_148837_1_.func_150792_a()];

      for(int i = 0; i < this.field_149632_a.length; ++i) {
         this.field_149632_a[i] = p_148837_1_.func_150789_c(32767);
      }

   }

   public void func_148840_b(PacketBuffer p_148840_1_) throws IOException {
      p_148840_1_.func_150787_b(this.field_149632_a.length);

      for(String s : this.field_149632_a) {
         p_148840_1_.func_180714_a(s);
      }

   }

   public void func_148833_a(INetHandlerPlayClient p_148833_1_) {
      p_148833_1_.func_147274_a(this);
   }

   public String[] func_149630_c() {
      return this.field_149632_a;
   }
}
