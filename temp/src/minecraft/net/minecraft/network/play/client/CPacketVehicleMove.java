package net.minecraft.network.play.client;

import java.io.IOException;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;

public class CPacketVehicleMove implements Packet<INetHandlerPlayServer> {
   private double field_187007_a;
   private double field_187008_b;
   private double field_187009_c;
   private float field_187010_d;
   private float field_187011_e;

   public CPacketVehicleMove() {
   }

   public CPacketVehicleMove(Entity p_i46874_1_) {
      this.field_187007_a = p_i46874_1_.field_70165_t;
      this.field_187008_b = p_i46874_1_.field_70163_u;
      this.field_187009_c = p_i46874_1_.field_70161_v;
      this.field_187010_d = p_i46874_1_.field_70177_z;
      this.field_187011_e = p_i46874_1_.field_70125_A;
   }

   public void func_148837_a(PacketBuffer p_148837_1_) throws IOException {
      this.field_187007_a = p_148837_1_.readDouble();
      this.field_187008_b = p_148837_1_.readDouble();
      this.field_187009_c = p_148837_1_.readDouble();
      this.field_187010_d = p_148837_1_.readFloat();
      this.field_187011_e = p_148837_1_.readFloat();
   }

   public void func_148840_b(PacketBuffer p_148840_1_) throws IOException {
      p_148840_1_.writeDouble(this.field_187007_a);
      p_148840_1_.writeDouble(this.field_187008_b);
      p_148840_1_.writeDouble(this.field_187009_c);
      p_148840_1_.writeFloat(this.field_187010_d);
      p_148840_1_.writeFloat(this.field_187011_e);
   }

   public void func_148833_a(INetHandlerPlayServer p_148833_1_) {
      p_148833_1_.func_184338_a(this);
   }

   public double func_187004_a() {
      return this.field_187007_a;
   }

   public double func_187002_b() {
      return this.field_187008_b;
   }

   public double func_187003_c() {
      return this.field_187009_c;
   }

   public float func_187006_d() {
      return this.field_187010_d;
   }

   public float func_187005_e() {
      return this.field_187011_e;
   }
}
