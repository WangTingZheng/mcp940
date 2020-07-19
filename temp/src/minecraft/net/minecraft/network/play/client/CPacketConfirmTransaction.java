package net.minecraft.network.play.client;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;

public class CPacketConfirmTransaction implements Packet<INetHandlerPlayServer> {
   private int field_149536_a;
   private short field_149534_b;
   private boolean field_149535_c;

   public CPacketConfirmTransaction() {
   }

   public CPacketConfirmTransaction(int p_i46884_1_, short p_i46884_2_, boolean p_i46884_3_) {
      this.field_149536_a = p_i46884_1_;
      this.field_149534_b = p_i46884_2_;
      this.field_149535_c = p_i46884_3_;
   }

   public void func_148833_a(INetHandlerPlayServer p_148833_1_) {
      p_148833_1_.func_147339_a(this);
   }

   public void func_148837_a(PacketBuffer p_148837_1_) throws IOException {
      this.field_149536_a = p_148837_1_.readByte();
      this.field_149534_b = p_148837_1_.readShort();
      this.field_149535_c = p_148837_1_.readByte() != 0;
   }

   public void func_148840_b(PacketBuffer p_148840_1_) throws IOException {
      p_148840_1_.writeByte(this.field_149536_a);
      p_148840_1_.writeShort(this.field_149534_b);
      p_148840_1_.writeByte(this.field_149535_c ? 1 : 0);
   }

   public int func_149532_c() {
      return this.field_149536_a;
   }

   public short func_149533_d() {
      return this.field_149534_b;
   }
}
