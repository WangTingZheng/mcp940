package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class SPacketUnloadChunk implements Packet<INetHandlerPlayClient> {
   private int field_186942_a;
   private int field_186943_b;

   public SPacketUnloadChunk() {
   }

   public SPacketUnloadChunk(int p_i46944_1_, int p_i46944_2_) {
      this.field_186942_a = p_i46944_1_;
      this.field_186943_b = p_i46944_2_;
   }

   public void func_148837_a(PacketBuffer p_148837_1_) throws IOException {
      this.field_186942_a = p_148837_1_.readInt();
      this.field_186943_b = p_148837_1_.readInt();
   }

   public void func_148840_b(PacketBuffer p_148840_1_) throws IOException {
      p_148840_1_.writeInt(this.field_186942_a);
      p_148840_1_.writeInt(this.field_186943_b);
   }

   public void func_148833_a(INetHandlerPlayClient p_148833_1_) {
      p_148833_1_.func_184326_a(this);
   }

   public int func_186940_a() {
      return this.field_186942_a;
   }

   public int func_186941_b() {
      return this.field_186943_b;
   }
}
