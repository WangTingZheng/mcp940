package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class SPacketSetSlot implements Packet<INetHandlerPlayClient> {
   private int field_149179_a;
   private int field_149177_b;
   private ItemStack field_149178_c = ItemStack.field_190927_a;

   public SPacketSetSlot() {
   }

   public SPacketSetSlot(int p_i46951_1_, int p_i46951_2_, ItemStack p_i46951_3_) {
      this.field_149179_a = p_i46951_1_;
      this.field_149177_b = p_i46951_2_;
      this.field_149178_c = p_i46951_3_.func_77946_l();
   }

   public void func_148833_a(INetHandlerPlayClient p_148833_1_) {
      p_148833_1_.func_147266_a(this);
   }

   public void func_148837_a(PacketBuffer p_148837_1_) throws IOException {
      this.field_149179_a = p_148837_1_.readByte();
      this.field_149177_b = p_148837_1_.readShort();
      this.field_149178_c = p_148837_1_.func_150791_c();
   }

   public void func_148840_b(PacketBuffer p_148840_1_) throws IOException {
      p_148840_1_.writeByte(this.field_149179_a);
      p_148840_1_.writeShort(this.field_149177_b);
      p_148840_1_.func_150788_a(this.field_149178_c);
   }

   public int func_149175_c() {
      return this.field_149179_a;
   }

   public int func_149173_d() {
      return this.field_149177_b;
   }

   public ItemStack func_149174_e() {
      return this.field_149178_c;
   }
}
