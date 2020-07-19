package net.minecraft.network.play.client;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;

public class CPacketPlayer implements Packet<INetHandlerPlayServer> {
   protected double field_149479_a;
   protected double field_149477_b;
   protected double field_149478_c;
   protected float field_149476_e;
   protected float field_149473_f;
   protected boolean field_149474_g;
   protected boolean field_149480_h;
   protected boolean field_149481_i;

   public CPacketPlayer() {
   }

   public CPacketPlayer(boolean p_i46875_1_) {
      this.field_149474_g = p_i46875_1_;
   }

   public void func_148833_a(INetHandlerPlayServer p_148833_1_) {
      p_148833_1_.func_147347_a(this);
   }

   public void func_148837_a(PacketBuffer p_148837_1_) throws IOException {
      this.field_149474_g = p_148837_1_.readUnsignedByte() != 0;
   }

   public void func_148840_b(PacketBuffer p_148840_1_) throws IOException {
      p_148840_1_.writeByte(this.field_149474_g ? 1 : 0);
   }

   public double func_186997_a(double p_186997_1_) {
      return this.field_149480_h ? this.field_149479_a : p_186997_1_;
   }

   public double func_186996_b(double p_186996_1_) {
      return this.field_149480_h ? this.field_149477_b : p_186996_1_;
   }

   public double func_187000_c(double p_187000_1_) {
      return this.field_149480_h ? this.field_149478_c : p_187000_1_;
   }

   public float func_186999_a(float p_186999_1_) {
      return this.field_149481_i ? this.field_149476_e : p_186999_1_;
   }

   public float func_186998_b(float p_186998_1_) {
      return this.field_149481_i ? this.field_149473_f : p_186998_1_;
   }

   public boolean func_149465_i() {
      return this.field_149474_g;
   }

   public static class Position extends CPacketPlayer {
      public Position() {
         this.field_149480_h = true;
      }

      public Position(double p_i46867_1_, double p_i46867_3_, double p_i46867_5_, boolean p_i46867_7_) {
         this.field_149479_a = p_i46867_1_;
         this.field_149477_b = p_i46867_3_;
         this.field_149478_c = p_i46867_5_;
         this.field_149474_g = p_i46867_7_;
         this.field_149480_h = true;
      }

      public void func_148837_a(PacketBuffer p_148837_1_) throws IOException {
         this.field_149479_a = p_148837_1_.readDouble();
         this.field_149477_b = p_148837_1_.readDouble();
         this.field_149478_c = p_148837_1_.readDouble();
         super.func_148837_a(p_148837_1_);
      }

      public void func_148840_b(PacketBuffer p_148840_1_) throws IOException {
         p_148840_1_.writeDouble(this.field_149479_a);
         p_148840_1_.writeDouble(this.field_149477_b);
         p_148840_1_.writeDouble(this.field_149478_c);
         super.func_148840_b(p_148840_1_);
      }
   }

   public static class PositionRotation extends CPacketPlayer {
      public PositionRotation() {
         this.field_149480_h = true;
         this.field_149481_i = true;
      }

      public PositionRotation(double p_i46865_1_, double p_i46865_3_, double p_i46865_5_, float p_i46865_7_, float p_i46865_8_, boolean p_i46865_9_) {
         this.field_149479_a = p_i46865_1_;
         this.field_149477_b = p_i46865_3_;
         this.field_149478_c = p_i46865_5_;
         this.field_149476_e = p_i46865_7_;
         this.field_149473_f = p_i46865_8_;
         this.field_149474_g = p_i46865_9_;
         this.field_149481_i = true;
         this.field_149480_h = true;
      }

      public void func_148837_a(PacketBuffer p_148837_1_) throws IOException {
         this.field_149479_a = p_148837_1_.readDouble();
         this.field_149477_b = p_148837_1_.readDouble();
         this.field_149478_c = p_148837_1_.readDouble();
         this.field_149476_e = p_148837_1_.readFloat();
         this.field_149473_f = p_148837_1_.readFloat();
         super.func_148837_a(p_148837_1_);
      }

      public void func_148840_b(PacketBuffer p_148840_1_) throws IOException {
         p_148840_1_.writeDouble(this.field_149479_a);
         p_148840_1_.writeDouble(this.field_149477_b);
         p_148840_1_.writeDouble(this.field_149478_c);
         p_148840_1_.writeFloat(this.field_149476_e);
         p_148840_1_.writeFloat(this.field_149473_f);
         super.func_148840_b(p_148840_1_);
      }
   }

   public static class Rotation extends CPacketPlayer {
      public Rotation() {
         this.field_149481_i = true;
      }

      public Rotation(float p_i46863_1_, float p_i46863_2_, boolean p_i46863_3_) {
         this.field_149476_e = p_i46863_1_;
         this.field_149473_f = p_i46863_2_;
         this.field_149474_g = p_i46863_3_;
         this.field_149481_i = true;
      }

      public void func_148837_a(PacketBuffer p_148837_1_) throws IOException {
         this.field_149476_e = p_148837_1_.readFloat();
         this.field_149473_f = p_148837_1_.readFloat();
         super.func_148837_a(p_148837_1_);
      }

      public void func_148840_b(PacketBuffer p_148840_1_) throws IOException {
         p_148840_1_.writeFloat(this.field_149476_e);
         p_148840_1_.writeFloat(this.field_149473_f);
         super.func_148840_b(p_148840_1_);
      }
   }
}
