package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class SPacketEntityVelocity implements Packet<INetHandlerPlayClient> {
   private int field_149417_a;
   private int field_149415_b;
   private int field_149416_c;
   private int field_149414_d;

   public SPacketEntityVelocity() {
   }

   public SPacketEntityVelocity(Entity p_i46914_1_) {
      this(p_i46914_1_.func_145782_y(), p_i46914_1_.field_70159_w, p_i46914_1_.field_70181_x, p_i46914_1_.field_70179_y);
   }

   public SPacketEntityVelocity(int p_i46915_1_, double p_i46915_2_, double p_i46915_4_, double p_i46915_6_) {
      this.field_149417_a = p_i46915_1_;
      double d0 = 3.9D;
      if (p_i46915_2_ < -3.9D) {
         p_i46915_2_ = -3.9D;
      }

      if (p_i46915_4_ < -3.9D) {
         p_i46915_4_ = -3.9D;
      }

      if (p_i46915_6_ < -3.9D) {
         p_i46915_6_ = -3.9D;
      }

      if (p_i46915_2_ > 3.9D) {
         p_i46915_2_ = 3.9D;
      }

      if (p_i46915_4_ > 3.9D) {
         p_i46915_4_ = 3.9D;
      }

      if (p_i46915_6_ > 3.9D) {
         p_i46915_6_ = 3.9D;
      }

      this.field_149415_b = (int)(p_i46915_2_ * 8000.0D);
      this.field_149416_c = (int)(p_i46915_4_ * 8000.0D);
      this.field_149414_d = (int)(p_i46915_6_ * 8000.0D);
   }

   public void func_148837_a(PacketBuffer p_148837_1_) throws IOException {
      this.field_149417_a = p_148837_1_.func_150792_a();
      this.field_149415_b = p_148837_1_.readShort();
      this.field_149416_c = p_148837_1_.readShort();
      this.field_149414_d = p_148837_1_.readShort();
   }

   public void func_148840_b(PacketBuffer p_148840_1_) throws IOException {
      p_148840_1_.func_150787_b(this.field_149417_a);
      p_148840_1_.writeShort(this.field_149415_b);
      p_148840_1_.writeShort(this.field_149416_c);
      p_148840_1_.writeShort(this.field_149414_d);
   }

   public void func_148833_a(INetHandlerPlayClient p_148833_1_) {
      p_148833_1_.func_147244_a(this);
   }

   public int func_149412_c() {
      return this.field_149417_a;
   }

   public int func_149411_d() {
      return this.field_149415_b;
   }

   public int func_149410_e() {
      return this.field_149416_c;
   }

   public int func_149409_f() {
      return this.field_149414_d;
   }
}
