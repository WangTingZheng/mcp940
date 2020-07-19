package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class SPacketEntityEquipment implements Packet<INetHandlerPlayClient> {
   private int field_149394_a;
   private EntityEquipmentSlot field_149392_b;
   private ItemStack field_149393_c = ItemStack.field_190927_a;

   public SPacketEntityEquipment() {
   }

   public SPacketEntityEquipment(int p_i46913_1_, EntityEquipmentSlot p_i46913_2_, ItemStack p_i46913_3_) {
      this.field_149394_a = p_i46913_1_;
      this.field_149392_b = p_i46913_2_;
      this.field_149393_c = p_i46913_3_.func_77946_l();
   }

   public void func_148837_a(PacketBuffer p_148837_1_) throws IOException {
      this.field_149394_a = p_148837_1_.func_150792_a();
      this.field_149392_b = (EntityEquipmentSlot)p_148837_1_.func_179257_a(EntityEquipmentSlot.class);
      this.field_149393_c = p_148837_1_.func_150791_c();
   }

   public void func_148840_b(PacketBuffer p_148840_1_) throws IOException {
      p_148840_1_.func_150787_b(this.field_149394_a);
      p_148840_1_.func_179249_a(this.field_149392_b);
      p_148840_1_.func_150788_a(this.field_149393_c);
   }

   public void func_148833_a(INetHandlerPlayClient p_148833_1_) {
      p_148833_1_.func_147242_a(this);
   }

   public ItemStack func_149390_c() {
      return this.field_149393_c;
   }

   public int func_149389_d() {
      return this.field_149394_a;
   }

   public EntityEquipmentSlot func_186969_c() {
      return this.field_149392_b;
   }
}
