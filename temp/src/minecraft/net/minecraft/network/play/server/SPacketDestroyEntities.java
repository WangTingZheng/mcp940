package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class SPacketDestroyEntities implements Packet<INetHandlerPlayClient> {
   private int[] field_149100_a;

   public SPacketDestroyEntities() {
   }

   public SPacketDestroyEntities(int... p_i46926_1_) {
      this.field_149100_a = p_i46926_1_;
   }

   public void func_148837_a(PacketBuffer p_148837_1_) throws IOException {
      this.field_149100_a = new int[p_148837_1_.func_150792_a()];

      for(int i = 0; i < this.field_149100_a.length; ++i) {
         this.field_149100_a[i] = p_148837_1_.func_150792_a();
      }

   }

   public void func_148840_b(PacketBuffer p_148840_1_) throws IOException {
      p_148840_1_.func_150787_b(this.field_149100_a.length);

      for(int i : this.field_149100_a) {
         p_148840_1_.func_150787_b(i);
      }

   }

   public void func_148833_a(INetHandlerPlayClient p_148833_1_) {
      p_148833_1_.func_147238_a(this);
   }

   public int[] func_149098_c() {
      return this.field_149100_a;
   }
}
