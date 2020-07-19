package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class SPacketEntityEffect implements Packet<INetHandlerPlayClient> {
   private int field_149434_a;
   private byte field_149432_b;
   private byte field_149433_c;
   private int field_149431_d;
   private byte field_186985_e;

   public SPacketEntityEffect() {
   }

   public SPacketEntityEffect(int p_i46891_1_, PotionEffect p_i46891_2_) {
      this.field_149434_a = p_i46891_1_;
      this.field_149432_b = (byte)(Potion.func_188409_a(p_i46891_2_.func_188419_a()) & 255);
      this.field_149433_c = (byte)(p_i46891_2_.func_76458_c() & 255);
      if (p_i46891_2_.func_76459_b() > 32767) {
         this.field_149431_d = 32767;
      } else {
         this.field_149431_d = p_i46891_2_.func_76459_b();
      }

      this.field_186985_e = 0;
      if (p_i46891_2_.func_82720_e()) {
         this.field_186985_e = (byte)(this.field_186985_e | 1);
      }

      if (p_i46891_2_.func_188418_e()) {
         this.field_186985_e = (byte)(this.field_186985_e | 2);
      }

   }

   public void func_148837_a(PacketBuffer p_148837_1_) throws IOException {
      this.field_149434_a = p_148837_1_.func_150792_a();
      this.field_149432_b = p_148837_1_.readByte();
      this.field_149433_c = p_148837_1_.readByte();
      this.field_149431_d = p_148837_1_.func_150792_a();
      this.field_186985_e = p_148837_1_.readByte();
   }

   public void func_148840_b(PacketBuffer p_148840_1_) throws IOException {
      p_148840_1_.func_150787_b(this.field_149434_a);
      p_148840_1_.writeByte(this.field_149432_b);
      p_148840_1_.writeByte(this.field_149433_c);
      p_148840_1_.func_150787_b(this.field_149431_d);
      p_148840_1_.writeByte(this.field_186985_e);
   }

   public boolean func_149429_c() {
      return this.field_149431_d == 32767;
   }

   public void func_148833_a(INetHandlerPlayClient p_148833_1_) {
      p_148833_1_.func_147260_a(this);
   }

   public int func_149426_d() {
      return this.field_149434_a;
   }

   public byte func_149427_e() {
      return this.field_149432_b;
   }

   public byte func_149428_f() {
      return this.field_149433_c;
   }

   public int func_180755_e() {
      return this.field_149431_d;
   }

   public boolean func_179707_f() {
      return (this.field_186985_e & 2) == 2;
   }

   public boolean func_186984_g() {
      return (this.field_186985_e & 1) == 1;
   }
}
