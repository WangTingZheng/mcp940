package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class SPacketSpawnGlobalEntity implements Packet<INetHandlerPlayClient> {
   private int field_149059_a;
   private double field_149057_b;
   private double field_149058_c;
   private double field_149055_d;
   private int field_149056_e;

   public SPacketSpawnGlobalEntity() {
   }

   public SPacketSpawnGlobalEntity(Entity p_i46974_1_) {
      this.field_149059_a = p_i46974_1_.func_145782_y();
      this.field_149057_b = p_i46974_1_.field_70165_t;
      this.field_149058_c = p_i46974_1_.field_70163_u;
      this.field_149055_d = p_i46974_1_.field_70161_v;
      if (p_i46974_1_ instanceof EntityLightningBolt) {
         this.field_149056_e = 1;
      }

   }

   public void func_148837_a(PacketBuffer p_148837_1_) throws IOException {
      this.field_149059_a = p_148837_1_.func_150792_a();
      this.field_149056_e = p_148837_1_.readByte();
      this.field_149057_b = p_148837_1_.readDouble();
      this.field_149058_c = p_148837_1_.readDouble();
      this.field_149055_d = p_148837_1_.readDouble();
   }

   public void func_148840_b(PacketBuffer p_148840_1_) throws IOException {
      p_148840_1_.func_150787_b(this.field_149059_a);
      p_148840_1_.writeByte(this.field_149056_e);
      p_148840_1_.writeDouble(this.field_149057_b);
      p_148840_1_.writeDouble(this.field_149058_c);
      p_148840_1_.writeDouble(this.field_149055_d);
   }

   public void func_148833_a(INetHandlerPlayClient p_148833_1_) {
      p_148833_1_.func_147292_a(this);
   }

   public int func_149052_c() {
      return this.field_149059_a;
   }

   public double func_186888_b() {
      return this.field_149057_b;
   }

   public double func_186889_c() {
      return this.field_149058_c;
   }

   public double func_186887_d() {
      return this.field_149055_d;
   }

   public int func_149053_g() {
      return this.field_149056_e;
   }
}
