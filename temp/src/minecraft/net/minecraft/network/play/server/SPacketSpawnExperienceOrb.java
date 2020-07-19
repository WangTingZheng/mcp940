package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class SPacketSpawnExperienceOrb implements Packet<INetHandlerPlayClient> {
   private int field_148992_a;
   private double field_148990_b;
   private double field_148991_c;
   private double field_148988_d;
   private int field_148989_e;

   public SPacketSpawnExperienceOrb() {
   }

   public SPacketSpawnExperienceOrb(EntityXPOrb p_i46975_1_) {
      this.field_148992_a = p_i46975_1_.func_145782_y();
      this.field_148990_b = p_i46975_1_.field_70165_t;
      this.field_148991_c = p_i46975_1_.field_70163_u;
      this.field_148988_d = p_i46975_1_.field_70161_v;
      this.field_148989_e = p_i46975_1_.func_70526_d();
   }

   public void func_148837_a(PacketBuffer p_148837_1_) throws IOException {
      this.field_148992_a = p_148837_1_.func_150792_a();
      this.field_148990_b = p_148837_1_.readDouble();
      this.field_148991_c = p_148837_1_.readDouble();
      this.field_148988_d = p_148837_1_.readDouble();
      this.field_148989_e = p_148837_1_.readShort();
   }

   public void func_148840_b(PacketBuffer p_148840_1_) throws IOException {
      p_148840_1_.func_150787_b(this.field_148992_a);
      p_148840_1_.writeDouble(this.field_148990_b);
      p_148840_1_.writeDouble(this.field_148991_c);
      p_148840_1_.writeDouble(this.field_148988_d);
      p_148840_1_.writeShort(this.field_148989_e);
   }

   public void func_148833_a(INetHandlerPlayClient p_148833_1_) {
      p_148833_1_.func_147286_a(this);
   }

   public int func_148985_c() {
      return this.field_148992_a;
   }

   public double func_186885_b() {
      return this.field_148990_b;
   }

   public double func_186886_c() {
      return this.field_148991_c;
   }

   public double func_186884_d() {
      return this.field_148988_d;
   }

   public int func_148986_g() {
      return this.field_148989_e;
   }
}
