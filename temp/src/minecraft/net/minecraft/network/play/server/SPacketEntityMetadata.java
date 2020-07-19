package net.minecraft.network.play.server;

import java.io.IOException;
import java.util.List;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.play.INetHandlerPlayClient;

public class SPacketEntityMetadata implements Packet<INetHandlerPlayClient> {
   private int field_149379_a;
   private List<EntityDataManager.DataEntry<?>> field_149378_b;

   public SPacketEntityMetadata() {
   }

   public SPacketEntityMetadata(int p_i46917_1_, EntityDataManager p_i46917_2_, boolean p_i46917_3_) {
      this.field_149379_a = p_i46917_1_;
      if (p_i46917_3_) {
         this.field_149378_b = p_i46917_2_.func_187231_c();
         p_i46917_2_.func_187230_e();
      } else {
         this.field_149378_b = p_i46917_2_.func_187221_b();
      }

   }

   public void func_148837_a(PacketBuffer p_148837_1_) throws IOException {
      this.field_149379_a = p_148837_1_.func_150792_a();
      this.field_149378_b = EntityDataManager.func_187215_b(p_148837_1_);
   }

   public void func_148840_b(PacketBuffer p_148840_1_) throws IOException {
      p_148840_1_.func_150787_b(this.field_149379_a);
      EntityDataManager.func_187229_a(this.field_149378_b, p_148840_1_);
   }

   public void func_148833_a(INetHandlerPlayClient p_148833_1_) {
      p_148833_1_.func_147284_a(this);
   }

   public List<EntityDataManager.DataEntry<?>> func_149376_c() {
      return this.field_149378_b;
   }

   public int func_149375_d() {
      return this.field_149379_a;
   }
}
