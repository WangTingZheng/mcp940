package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import org.apache.commons.lang3.Validate;

public class SPacketSoundEffect implements Packet<INetHandlerPlayClient> {
   private SoundEvent field_186979_a;
   private SoundCategory field_186980_b;
   private int field_149217_b;
   private int field_149218_c;
   private int field_149215_d;
   private float field_149216_e;
   private float field_149214_f;

   public SPacketSoundEffect() {
   }

   public SPacketSoundEffect(SoundEvent p_i46896_1_, SoundCategory p_i46896_2_, double p_i46896_3_, double p_i46896_5_, double p_i46896_7_, float p_i46896_9_, float p_i46896_10_) {
      Validate.notNull(p_i46896_1_, "sound");
      this.field_186979_a = p_i46896_1_;
      this.field_186980_b = p_i46896_2_;
      this.field_149217_b = (int)(p_i46896_3_ * 8.0D);
      this.field_149218_c = (int)(p_i46896_5_ * 8.0D);
      this.field_149215_d = (int)(p_i46896_7_ * 8.0D);
      this.field_149216_e = p_i46896_9_;
      this.field_149214_f = p_i46896_10_;
   }

   public void func_148837_a(PacketBuffer p_148837_1_) throws IOException {
      this.field_186979_a = SoundEvent.field_187505_a.func_148754_a(p_148837_1_.func_150792_a());
      this.field_186980_b = (SoundCategory)p_148837_1_.func_179257_a(SoundCategory.class);
      this.field_149217_b = p_148837_1_.readInt();
      this.field_149218_c = p_148837_1_.readInt();
      this.field_149215_d = p_148837_1_.readInt();
      this.field_149216_e = p_148837_1_.readFloat();
      this.field_149214_f = p_148837_1_.readFloat();
   }

   public void func_148840_b(PacketBuffer p_148840_1_) throws IOException {
      p_148840_1_.func_150787_b(SoundEvent.field_187505_a.func_148757_b(this.field_186979_a));
      p_148840_1_.func_179249_a(this.field_186980_b);
      p_148840_1_.writeInt(this.field_149217_b);
      p_148840_1_.writeInt(this.field_149218_c);
      p_148840_1_.writeInt(this.field_149215_d);
      p_148840_1_.writeFloat(this.field_149216_e);
      p_148840_1_.writeFloat(this.field_149214_f);
   }

   public SoundEvent func_186978_a() {
      return this.field_186979_a;
   }

   public SoundCategory func_186977_b() {
      return this.field_186980_b;
   }

   public double func_149207_d() {
      return (double)((float)this.field_149217_b / 8.0F);
   }

   public double func_149211_e() {
      return (double)((float)this.field_149218_c / 8.0F);
   }

   public double func_149210_f() {
      return (double)((float)this.field_149215_d / 8.0F);
   }

   public float func_149208_g() {
      return this.field_149216_e;
   }

   public float func_149209_h() {
      return this.field_149214_f;
   }

   public void func_148833_a(INetHandlerPlayClient p_148833_1_) {
      p_148833_1_.func_184327_a(this);
   }
}
