package net.minecraft.network.play.server;

import java.io.IOException;
import java.util.List;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.NonNullList;

public class SPacketWindowItems implements Packet<INetHandlerPlayClient> {
   private int field_148914_a;
   private List<ItemStack> field_148913_b;

   public SPacketWindowItems() {
   }

   public SPacketWindowItems(int p_i47317_1_, NonNullList<ItemStack> p_i47317_2_) {
      this.field_148914_a = p_i47317_1_;
      this.field_148913_b = NonNullList.<ItemStack>func_191197_a(p_i47317_2_.size(), ItemStack.field_190927_a);

      for(int i = 0; i < this.field_148913_b.size(); ++i) {
         ItemStack itemstack = p_i47317_2_.get(i);
         this.field_148913_b.set(i, itemstack.func_77946_l());
      }

   }

   public void func_148837_a(PacketBuffer p_148837_1_) throws IOException {
      this.field_148914_a = p_148837_1_.readUnsignedByte();
      int i = p_148837_1_.readShort();
      this.field_148913_b = NonNullList.<ItemStack>func_191197_a(i, ItemStack.field_190927_a);

      for(int j = 0; j < i; ++j) {
         this.field_148913_b.set(j, p_148837_1_.func_150791_c());
      }

   }

   public void func_148840_b(PacketBuffer p_148840_1_) throws IOException {
      p_148840_1_.writeByte(this.field_148914_a);
      p_148840_1_.writeShort(this.field_148913_b.size());

      for(ItemStack itemstack : this.field_148913_b) {
         p_148840_1_.func_150788_a(itemstack);
      }

   }

   public void func_148833_a(INetHandlerPlayClient p_148833_1_) {
      p_148833_1_.func_147241_a(this);
   }

   public int func_148911_c() {
      return this.field_148914_a;
   }

   public List<ItemStack> func_148910_d() {
      return this.field_148913_b;
   }
}
