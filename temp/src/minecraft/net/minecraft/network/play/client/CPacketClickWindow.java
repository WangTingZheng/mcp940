package net.minecraft.network.play.client;

import java.io.IOException;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;

public class CPacketClickWindow implements Packet<INetHandlerPlayServer> {
   private int field_149554_a;
   private int field_149552_b;
   private int field_149553_c;
   private short field_149550_d;
   private ItemStack field_149551_e = ItemStack.field_190927_a;
   private ClickType field_149549_f;

   public CPacketClickWindow() {
   }

   public CPacketClickWindow(int p_i46882_1_, int p_i46882_2_, int p_i46882_3_, ClickType p_i46882_4_, ItemStack p_i46882_5_, short p_i46882_6_) {
      this.field_149554_a = p_i46882_1_;
      this.field_149552_b = p_i46882_2_;
      this.field_149553_c = p_i46882_3_;
      this.field_149551_e = p_i46882_5_.func_77946_l();
      this.field_149550_d = p_i46882_6_;
      this.field_149549_f = p_i46882_4_;
   }

   public void func_148833_a(INetHandlerPlayServer p_148833_1_) {
      p_148833_1_.func_147351_a(this);
   }

   public void func_148837_a(PacketBuffer p_148837_1_) throws IOException {
      this.field_149554_a = p_148837_1_.readByte();
      this.field_149552_b = p_148837_1_.readShort();
      this.field_149553_c = p_148837_1_.readByte();
      this.field_149550_d = p_148837_1_.readShort();
      this.field_149549_f = (ClickType)p_148837_1_.func_179257_a(ClickType.class);
      this.field_149551_e = p_148837_1_.func_150791_c();
   }

   public void func_148840_b(PacketBuffer p_148840_1_) throws IOException {
      p_148840_1_.writeByte(this.field_149554_a);
      p_148840_1_.writeShort(this.field_149552_b);
      p_148840_1_.writeByte(this.field_149553_c);
      p_148840_1_.writeShort(this.field_149550_d);
      p_148840_1_.func_179249_a(this.field_149549_f);
      p_148840_1_.func_150788_a(this.field_149551_e);
   }

   public int func_149548_c() {
      return this.field_149554_a;
   }

   public int func_149544_d() {
      return this.field_149552_b;
   }

   public int func_149543_e() {
      return this.field_149553_c;
   }

   public short func_149547_f() {
      return this.field_149550_d;
   }

   public ItemStack func_149546_g() {
      return this.field_149551_e;
   }

   public ClickType func_186993_f() {
      return this.field_149549_f;
   }
}
