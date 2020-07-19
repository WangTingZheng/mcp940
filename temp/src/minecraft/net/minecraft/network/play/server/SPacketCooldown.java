package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.item.Item;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class SPacketCooldown implements Packet<INetHandlerPlayClient> {
   private Item field_186923_a;
   private int field_186924_b;

   public SPacketCooldown() {
   }

   public SPacketCooldown(Item p_i46950_1_, int p_i46950_2_) {
      this.field_186923_a = p_i46950_1_;
      this.field_186924_b = p_i46950_2_;
   }

   public void func_148837_a(PacketBuffer p_148837_1_) throws IOException {
      this.field_186923_a = Item.func_150899_d(p_148837_1_.func_150792_a());
      this.field_186924_b = p_148837_1_.func_150792_a();
   }

   public void func_148840_b(PacketBuffer p_148840_1_) throws IOException {
      p_148840_1_.func_150787_b(Item.func_150891_b(this.field_186923_a));
      p_148840_1_.func_150787_b(this.field_186924_b);
   }

   public void func_148833_a(INetHandlerPlayClient p_148833_1_) {
      p_148833_1_.func_184324_a(this);
   }

   public Item func_186920_a() {
      return this.field_186923_a;
   }

   public int func_186922_b() {
      return this.field_186924_b;
   }
}
