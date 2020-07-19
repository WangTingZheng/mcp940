package net.minecraft.network.play.server;

import java.io.IOException;
import java.util.EnumSet;
import java.util.Set;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class SPacketPlayerPosLook implements Packet<INetHandlerPlayClient> {
   private double field_148940_a;
   private double field_148938_b;
   private double field_148939_c;
   private float field_148936_d;
   private float field_148937_e;
   private Set<SPacketPlayerPosLook.EnumFlags> field_179835_f;
   private int field_186966_g;

   public SPacketPlayerPosLook() {
   }

   public SPacketPlayerPosLook(double p_i46928_1_, double p_i46928_3_, double p_i46928_5_, float p_i46928_7_, float p_i46928_8_, Set<SPacketPlayerPosLook.EnumFlags> p_i46928_9_, int p_i46928_10_) {
      this.field_148940_a = p_i46928_1_;
      this.field_148938_b = p_i46928_3_;
      this.field_148939_c = p_i46928_5_;
      this.field_148936_d = p_i46928_7_;
      this.field_148937_e = p_i46928_8_;
      this.field_179835_f = p_i46928_9_;
      this.field_186966_g = p_i46928_10_;
   }

   public void func_148837_a(PacketBuffer p_148837_1_) throws IOException {
      this.field_148940_a = p_148837_1_.readDouble();
      this.field_148938_b = p_148837_1_.readDouble();
      this.field_148939_c = p_148837_1_.readDouble();
      this.field_148936_d = p_148837_1_.readFloat();
      this.field_148937_e = p_148837_1_.readFloat();
      this.field_179835_f = SPacketPlayerPosLook.EnumFlags.func_187044_a(p_148837_1_.readUnsignedByte());
      this.field_186966_g = p_148837_1_.func_150792_a();
   }

   public void func_148840_b(PacketBuffer p_148840_1_) throws IOException {
      p_148840_1_.writeDouble(this.field_148940_a);
      p_148840_1_.writeDouble(this.field_148938_b);
      p_148840_1_.writeDouble(this.field_148939_c);
      p_148840_1_.writeFloat(this.field_148936_d);
      p_148840_1_.writeFloat(this.field_148937_e);
      p_148840_1_.writeByte(SPacketPlayerPosLook.EnumFlags.func_187040_a(this.field_179835_f));
      p_148840_1_.func_150787_b(this.field_186966_g);
   }

   public void func_148833_a(INetHandlerPlayClient p_148833_1_) {
      p_148833_1_.func_184330_a(this);
   }

   public double func_148932_c() {
      return this.field_148940_a;
   }

   public double func_148928_d() {
      return this.field_148938_b;
   }

   public double func_148933_e() {
      return this.field_148939_c;
   }

   public float func_148931_f() {
      return this.field_148936_d;
   }

   public float func_148930_g() {
      return this.field_148937_e;
   }

   public int func_186965_f() {
      return this.field_186966_g;
   }

   public Set<SPacketPlayerPosLook.EnumFlags> func_179834_f() {
      return this.field_179835_f;
   }

   public static enum EnumFlags {
      X(0),
      Y(1),
      Z(2),
      Y_ROT(3),
      X_ROT(4);

      private final int field_187050_f;

      private EnumFlags(int p_i46690_3_) {
         this.field_187050_f = p_i46690_3_;
      }

      private int func_187042_a() {
         return 1 << this.field_187050_f;
      }

      private boolean func_187043_b(int p_187043_1_) {
         return (p_187043_1_ & this.func_187042_a()) == this.func_187042_a();
      }

      public static Set<SPacketPlayerPosLook.EnumFlags> func_187044_a(int p_187044_0_) {
         Set<SPacketPlayerPosLook.EnumFlags> set = EnumSet.<SPacketPlayerPosLook.EnumFlags>noneOf(SPacketPlayerPosLook.EnumFlags.class);

         for(SPacketPlayerPosLook.EnumFlags spacketplayerposlook$enumflags : values()) {
            if (spacketplayerposlook$enumflags.func_187043_b(p_187044_0_)) {
               set.add(spacketplayerposlook$enumflags);
            }
         }

         return set;
      }

      public static int func_187040_a(Set<SPacketPlayerPosLook.EnumFlags> p_187040_0_) {
         int i = 0;

         for(SPacketPlayerPosLook.EnumFlags spacketplayerposlook$enumflags : p_187040_0_) {
            i |= spacketplayerposlook$enumflags.func_187042_a();
         }

         return i;
      }
   }
}
