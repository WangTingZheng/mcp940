package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.text.ITextComponent;

public class SPacketOpenWindow implements Packet<INetHandlerPlayClient> {
   private int field_148909_a;
   private String field_148907_b;
   private ITextComponent field_148908_c;
   private int field_148905_d;
   private int field_148904_f;

   public SPacketOpenWindow() {
   }

   public SPacketOpenWindow(int p_i46954_1_, String p_i46954_2_, ITextComponent p_i46954_3_) {
      this(p_i46954_1_, p_i46954_2_, p_i46954_3_, 0);
   }

   public SPacketOpenWindow(int p_i46955_1_, String p_i46955_2_, ITextComponent p_i46955_3_, int p_i46955_4_) {
      this.field_148909_a = p_i46955_1_;
      this.field_148907_b = p_i46955_2_;
      this.field_148908_c = p_i46955_3_;
      this.field_148905_d = p_i46955_4_;
   }

   public SPacketOpenWindow(int p_i46956_1_, String p_i46956_2_, ITextComponent p_i46956_3_, int p_i46956_4_, int p_i46956_5_) {
      this(p_i46956_1_, p_i46956_2_, p_i46956_3_, p_i46956_4_);
      this.field_148904_f = p_i46956_5_;
   }

   public void func_148833_a(INetHandlerPlayClient p_148833_1_) {
      p_148833_1_.func_147265_a(this);
   }

   public void func_148837_a(PacketBuffer p_148837_1_) throws IOException {
      this.field_148909_a = p_148837_1_.readUnsignedByte();
      this.field_148907_b = p_148837_1_.func_150789_c(32);
      this.field_148908_c = p_148837_1_.func_179258_d();
      this.field_148905_d = p_148837_1_.readUnsignedByte();
      if (this.field_148907_b.equals("EntityHorse")) {
         this.field_148904_f = p_148837_1_.readInt();
      }

   }

   public void func_148840_b(PacketBuffer p_148840_1_) throws IOException {
      p_148840_1_.writeByte(this.field_148909_a);
      p_148840_1_.func_180714_a(this.field_148907_b);
      p_148840_1_.func_179256_a(this.field_148908_c);
      p_148840_1_.writeByte(this.field_148905_d);
      if (this.field_148907_b.equals("EntityHorse")) {
         p_148840_1_.writeInt(this.field_148904_f);
      }

   }

   public int func_148901_c() {
      return this.field_148909_a;
   }

   public String func_148902_e() {
      return this.field_148907_b;
   }

   public ITextComponent func_179840_c() {
      return this.field_148908_c;
   }

   public int func_148898_f() {
      return this.field_148905_d;
   }

   public int func_148897_h() {
      return this.field_148904_f;
   }

   public boolean func_148900_g() {
      return this.field_148905_d > 0;
   }
}
