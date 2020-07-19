package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class SPacketMoveVehicle implements Packet<INetHandlerPlayClient> {
   private double field_186960_a;
   private double field_186961_b;
   private double field_186962_c;
   private float field_186963_d;
   private float field_186964_e;

   public SPacketMoveVehicle() {
   }

   public SPacketMoveVehicle(Entity p_i46935_1_) {
      this.field_186960_a = p_i46935_1_.field_70165_t;
      this.field_186961_b = p_i46935_1_.field_70163_u;
      this.field_186962_c = p_i46935_1_.field_70161_v;
      this.field_186963_d = p_i46935_1_.field_70177_z;
      this.field_186964_e = p_i46935_1_.field_70125_A;
   }

   public void func_148837_a(PacketBuffer p_148837_1_) throws IOException {
      this.field_186960_a = p_148837_1_.readDouble();
      this.field_186961_b = p_148837_1_.readDouble();
      this.field_186962_c = p_148837_1_.readDouble();
      this.field_186963_d = p_148837_1_.readFloat();
      this.field_186964_e = p_148837_1_.readFloat();
   }

   public void func_148840_b(PacketBuffer p_148840_1_) throws IOException {
      p_148840_1_.writeDouble(this.field_186960_a);
      p_148840_1_.writeDouble(this.field_186961_b);
      p_148840_1_.writeDouble(this.field_186962_c);
      p_148840_1_.writeFloat(this.field_186963_d);
      p_148840_1_.writeFloat(this.field_186964_e);
   }

   public void func_148833_a(INetHandlerPlayClient p_148833_1_) {
      p_148833_1_.func_184323_a(this);
   }

   public double func_186957_a() {
      return this.field_186960_a;
   }

   public double func_186955_b() {
      return this.field_186961_b;
   }

   public double func_186956_c() {
      return this.field_186962_c;
   }

   public float func_186959_d() {
      return this.field_186963_d;
   }

   public float func_186958_e() {
      return this.field_186964_e;
   }
}
