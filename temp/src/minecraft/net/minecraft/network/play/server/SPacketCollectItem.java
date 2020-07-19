package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class SPacketCollectItem implements Packet<INetHandlerPlayClient> {
   private int field_149357_a;
   private int field_149356_b;
   private int field_191209_c;

   public SPacketCollectItem() {
   }

   public SPacketCollectItem(int p_i47316_1_, int p_i47316_2_, int p_i47316_3_) {
      this.field_149357_a = p_i47316_1_;
      this.field_149356_b = p_i47316_2_;
      this.field_191209_c = p_i47316_3_;
   }

   public void func_148837_a(PacketBuffer p_148837_1_) throws IOException {
      this.field_149357_a = p_148837_1_.func_150792_a();
      this.field_149356_b = p_148837_1_.func_150792_a();
      this.field_191209_c = p_148837_1_.func_150792_a();
   }

   public void func_148840_b(PacketBuffer p_148840_1_) throws IOException {
      p_148840_1_.func_150787_b(this.field_149357_a);
      p_148840_1_.func_150787_b(this.field_149356_b);
      p_148840_1_.func_150787_b(this.field_191209_c);
   }

   public void func_148833_a(INetHandlerPlayClient p_148833_1_) {
      p_148833_1_.func_147246_a(this);
   }

   public int func_149354_c() {
      return this.field_149357_a;
   }

   public int func_149353_d() {
      return this.field_149356_b;
   }

   public int func_191208_c() {
      return this.field_191209_c;
   }
}
