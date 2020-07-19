package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.EnumParticleTypes;

public class SPacketParticles implements Packet<INetHandlerPlayClient> {
   private EnumParticleTypes field_179751_a;
   private float field_149234_b;
   private float field_149235_c;
   private float field_149232_d;
   private float field_149233_e;
   private float field_149230_f;
   private float field_149231_g;
   private float field_149237_h;
   private int field_149238_i;
   private boolean field_179752_j;
   private int[] field_179753_k;

   public SPacketParticles() {
   }

   public SPacketParticles(EnumParticleTypes p_i46939_1_, boolean p_i46939_2_, float p_i46939_3_, float p_i46939_4_, float p_i46939_5_, float p_i46939_6_, float p_i46939_7_, float p_i46939_8_, float p_i46939_9_, int p_i46939_10_, int... p_i46939_11_) {
      this.field_179751_a = p_i46939_1_;
      this.field_179752_j = p_i46939_2_;
      this.field_149234_b = p_i46939_3_;
      this.field_149235_c = p_i46939_4_;
      this.field_149232_d = p_i46939_5_;
      this.field_149233_e = p_i46939_6_;
      this.field_149230_f = p_i46939_7_;
      this.field_149231_g = p_i46939_8_;
      this.field_149237_h = p_i46939_9_;
      this.field_149238_i = p_i46939_10_;
      this.field_179753_k = p_i46939_11_;
   }

   public void func_148837_a(PacketBuffer p_148837_1_) throws IOException {
      this.field_179751_a = EnumParticleTypes.func_179342_a(p_148837_1_.readInt());
      if (this.field_179751_a == null) {
         this.field_179751_a = EnumParticleTypes.BARRIER;
      }

      this.field_179752_j = p_148837_1_.readBoolean();
      this.field_149234_b = p_148837_1_.readFloat();
      this.field_149235_c = p_148837_1_.readFloat();
      this.field_149232_d = p_148837_1_.readFloat();
      this.field_149233_e = p_148837_1_.readFloat();
      this.field_149230_f = p_148837_1_.readFloat();
      this.field_149231_g = p_148837_1_.readFloat();
      this.field_149237_h = p_148837_1_.readFloat();
      this.field_149238_i = p_148837_1_.readInt();
      int i = this.field_179751_a.func_179345_d();
      this.field_179753_k = new int[i];

      for(int j = 0; j < i; ++j) {
         this.field_179753_k[j] = p_148837_1_.func_150792_a();
      }

   }

   public void func_148840_b(PacketBuffer p_148840_1_) throws IOException {
      p_148840_1_.writeInt(this.field_179751_a.func_179348_c());
      p_148840_1_.writeBoolean(this.field_179752_j);
      p_148840_1_.writeFloat(this.field_149234_b);
      p_148840_1_.writeFloat(this.field_149235_c);
      p_148840_1_.writeFloat(this.field_149232_d);
      p_148840_1_.writeFloat(this.field_149233_e);
      p_148840_1_.writeFloat(this.field_149230_f);
      p_148840_1_.writeFloat(this.field_149231_g);
      p_148840_1_.writeFloat(this.field_149237_h);
      p_148840_1_.writeInt(this.field_149238_i);
      int i = this.field_179751_a.func_179345_d();

      for(int j = 0; j < i; ++j) {
         p_148840_1_.func_150787_b(this.field_179753_k[j]);
      }

   }

   public EnumParticleTypes func_179749_a() {
      return this.field_179751_a;
   }

   public boolean func_179750_b() {
      return this.field_179752_j;
   }

   public double func_149220_d() {
      return (double)this.field_149234_b;
   }

   public double func_149226_e() {
      return (double)this.field_149235_c;
   }

   public double func_149225_f() {
      return (double)this.field_149232_d;
   }

   public float func_149221_g() {
      return this.field_149233_e;
   }

   public float func_149224_h() {
      return this.field_149230_f;
   }

   public float func_149223_i() {
      return this.field_149231_g;
   }

   public float func_149227_j() {
      return this.field_149237_h;
   }

   public int func_149222_k() {
      return this.field_149238_i;
   }

   public int[] func_179748_k() {
      return this.field_179753_k;
   }

   public void func_148833_a(INetHandlerPlayClient p_148833_1_) {
      p_148833_1_.func_147289_a(this);
   }
}
