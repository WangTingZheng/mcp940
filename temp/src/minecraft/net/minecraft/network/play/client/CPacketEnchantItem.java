package net.minecraft.network.play.client;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;

public class CPacketEnchantItem implements Packet<INetHandlerPlayServer> {
   private int field_149541_a;
   private int field_149540_b;

   public CPacketEnchantItem() {
   }

   public CPacketEnchantItem(int p_i46883_1_, int p_i46883_2_) {
      this.field_149541_a = p_i46883_1_;
      this.field_149540_b = p_i46883_2_;
   }

   public void func_148833_a(INetHandlerPlayServer p_148833_1_) {
      p_148833_1_.func_147338_a(this);
   }

   public void func_148837_a(PacketBuffer p_148837_1_) throws IOException {
      this.field_149541_a = p_148837_1_.readByte();
      this.field_149540_b = p_148837_1_.readByte();
   }

   public void func_148840_b(PacketBuffer p_148840_1_) throws IOException {
      p_148840_1_.writeByte(this.field_149541_a);
      p_148840_1_.writeByte(this.field_149540_b);
   }

   public int func_149539_c() {
      return this.field_149541_a;
   }

   public int func_149537_d() {
      return this.field_149540_b;
   }
}
