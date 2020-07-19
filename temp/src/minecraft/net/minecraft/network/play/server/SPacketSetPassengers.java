package net.minecraft.network.play.server;

import java.io.IOException;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class SPacketSetPassengers implements Packet<INetHandlerPlayClient> {
   private int field_186973_a;
   private int[] field_186974_b;

   public SPacketSetPassengers() {
   }

   public SPacketSetPassengers(Entity p_i46909_1_) {
      this.field_186973_a = p_i46909_1_.func_145782_y();
      List<Entity> list = p_i46909_1_.func_184188_bt();
      this.field_186974_b = new int[list.size()];

      for(int i = 0; i < list.size(); ++i) {
         this.field_186974_b[i] = ((Entity)list.get(i)).func_145782_y();
      }

   }

   public void func_148837_a(PacketBuffer p_148837_1_) throws IOException {
      this.field_186973_a = p_148837_1_.func_150792_a();
      this.field_186974_b = p_148837_1_.func_186863_b();
   }

   public void func_148840_b(PacketBuffer p_148840_1_) throws IOException {
      p_148840_1_.func_150787_b(this.field_186973_a);
      p_148840_1_.func_186875_a(this.field_186974_b);
   }

   public void func_148833_a(INetHandlerPlayClient p_148833_1_) {
      p_148833_1_.func_184328_a(this);
   }

   public int[] func_186971_a() {
      return this.field_186974_b;
   }

   public int func_186972_b() {
      return this.field_186973_a;
   }
}
