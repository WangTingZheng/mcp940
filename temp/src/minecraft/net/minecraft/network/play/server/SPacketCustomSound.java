package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.SoundCategory;
import org.apache.commons.lang3.Validate;

public class SPacketCustomSound implements Packet<INetHandlerPlayClient> {
   private String field_149219_a;
   private SoundCategory field_186933_b;
   private int field_186934_c;
   private int field_186935_d = Integer.MAX_VALUE;
   private int field_186936_e;
   private float field_186937_f;
   private float field_186938_g;

   public SPacketCustomSound() {
   }

   public SPacketCustomSound(String p_i46948_1_, SoundCategory p_i46948_2_, double p_i46948_3_, double p_i46948_5_, double p_i46948_7_, float p_i46948_9_, float p_i46948_10_) {
      Validate.notNull(p_i46948_1_, "name");
      this.field_149219_a = p_i46948_1_;
      this.field_186933_b = p_i46948_2_;
      this.field_186934_c = (int)(p_i46948_3_ * 8.0D);
      this.field_186935_d = (int)(p_i46948_5_ * 8.0D);
      this.field_186936_e = (int)(p_i46948_7_ * 8.0D);
      this.field_186937_f = p_i46948_9_;
      this.field_186938_g = p_i46948_10_;
   }

   public void func_148837_a(PacketBuffer p_148837_1_) throws IOException {
      this.field_149219_a = p_148837_1_.func_150789_c(256);
      this.field_186933_b = (SoundCategory)p_148837_1_.func_179257_a(SoundCategory.class);
      this.field_186934_c = p_148837_1_.readInt();
      this.field_186935_d = p_148837_1_.readInt();
      this.field_186936_e = p_148837_1_.readInt();
      this.field_186937_f = p_148837_1_.readFloat();
      this.field_186938_g = p_148837_1_.readFloat();
   }

   public void func_148840_b(PacketBuffer p_148840_1_) throws IOException {
      p_148840_1_.func_180714_a(this.field_149219_a);
      p_148840_1_.func_179249_a(this.field_186933_b);
      p_148840_1_.writeInt(this.field_186934_c);
      p_148840_1_.writeInt(this.field_186935_d);
      p_148840_1_.writeInt(this.field_186936_e);
      p_148840_1_.writeFloat(this.field_186937_f);
      p_148840_1_.writeFloat(this.field_186938_g);
   }

   public String func_186930_a() {
      return this.field_149219_a;
   }

   public SoundCategory func_186929_b() {
      return this.field_186933_b;
   }

   public double func_186932_c() {
      return (double)((float)this.field_186934_c / 8.0F);
   }

   public double func_186926_d() {
      return (double)((float)this.field_186935_d / 8.0F);
   }

   public double func_186925_e() {
      return (double)((float)this.field_186936_e / 8.0F);
   }

   public float func_186927_f() {
      return this.field_186937_f;
   }

   public float func_186928_g() {
      return this.field_186938_g;
   }

   public void func_148833_a(INetHandlerPlayClient p_148833_1_) {
      p_148833_1_.func_184329_a(this);
   }
}
