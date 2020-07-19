package net.minecraft.network.play.client;

import java.io.IOException;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;

public class CPacketCreativeInventoryAction implements Packet<INetHandlerPlayServer> {
   private int field_149629_a;
   private ItemStack field_149628_b = ItemStack.field_190927_a;

   public CPacketCreativeInventoryAction() {
   }

   public CPacketCreativeInventoryAction(int p_i46862_1_, ItemStack p_i46862_2_) {
      this.field_149629_a = p_i46862_1_;
      this.field_149628_b = p_i46862_2_.func_77946_l();
   }

   public void func_148833_a(INetHandlerPlayServer p_148833_1_) {
      p_148833_1_.func_147344_a(this);
   }

   public void func_148837_a(PacketBuffer p_148837_1_) throws IOException {
      this.field_149629_a = p_148837_1_.readShort();
      this.field_149628_b = p_148837_1_.func_150791_c();
   }

   public void func_148840_b(PacketBuffer p_148840_1_) throws IOException {
      p_148840_1_.writeShort(this.field_149629_a);
      p_148840_1_.func_150788_a(this.field_149628_b);
   }

   public int func_149627_c() {
      return this.field_149629_a;
   }

   public ItemStack func_149625_d() {
      return this.field_149628_b;
   }
}
