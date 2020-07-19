package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class SPacketConfirmTransaction implements Packet<INetHandlerPlayClient> {
   private int field_148894_a;
   private short field_148892_b;
   private boolean field_148893_c;

   public SPacketConfirmTransaction() {
   }

   public SPacketConfirmTransaction(int p_i46958_1_, short p_i46958_2_, boolean p_i46958_3_) {
      this.field_148894_a = p_i46958_1_;
      this.field_148892_b = p_i46958_2_;
      this.field_148893_c = p_i46958_3_;
   }

   public void func_148833_a(INetHandlerPlayClient p_148833_1_) {
      p_148833_1_.func_147239_a(this);
   }

   public void func_148837_a(PacketBuffer p_148837_1_) throws IOException {
      this.field_148894_a = p_148837_1_.readUnsignedByte();
      this.field_148892_b = p_148837_1_.readShort();
      this.field_148893_c = p_148837_1_.readBoolean();
   }

   public void func_148840_b(PacketBuffer p_148840_1_) throws IOException {
      p_148840_1_.writeByte(this.field_148894_a);
      p_148840_1_.writeShort(this.field_148892_b);
      p_148840_1_.writeBoolean(this.field_148893_c);
   }

   public int func_148889_c() {
      return this.field_148894_a;
   }

   public short func_148890_d() {
      return this.field_148892_b;
   }

   public boolean func_148888_e() {
      return this.field_148893_c;
   }
}
