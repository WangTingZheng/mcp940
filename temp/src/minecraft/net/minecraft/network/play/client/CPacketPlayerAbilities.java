package net.minecraft.network.play.client;

import java.io.IOException;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;

public class CPacketPlayerAbilities implements Packet<INetHandlerPlayServer> {
   private boolean field_149500_a;
   private boolean field_149498_b;
   private boolean field_149499_c;
   private boolean field_149496_d;
   private float field_149497_e;
   private float field_149495_f;

   public CPacketPlayerAbilities() {
   }

   public CPacketPlayerAbilities(PlayerCapabilities p_i46872_1_) {
      this.func_149490_a(p_i46872_1_.field_75102_a);
      this.func_149483_b(p_i46872_1_.field_75100_b);
      this.func_149491_c(p_i46872_1_.field_75101_c);
      this.func_149493_d(p_i46872_1_.field_75098_d);
      this.func_149485_a(p_i46872_1_.func_75093_a());
      this.func_149492_b(p_i46872_1_.func_75094_b());
   }

   public void func_148837_a(PacketBuffer p_148837_1_) throws IOException {
      byte b0 = p_148837_1_.readByte();
      this.func_149490_a((b0 & 1) > 0);
      this.func_149483_b((b0 & 2) > 0);
      this.func_149491_c((b0 & 4) > 0);
      this.func_149493_d((b0 & 8) > 0);
      this.func_149485_a(p_148837_1_.readFloat());
      this.func_149492_b(p_148837_1_.readFloat());
   }

   public void func_148840_b(PacketBuffer p_148840_1_) throws IOException {
      byte b0 = 0;
      if (this.func_149494_c()) {
         b0 = (byte)(b0 | 1);
      }

      if (this.func_149488_d()) {
         b0 = (byte)(b0 | 2);
      }

      if (this.func_149486_e()) {
         b0 = (byte)(b0 | 4);
      }

      if (this.func_149484_f()) {
         b0 = (byte)(b0 | 8);
      }

      p_148840_1_.writeByte(b0);
      p_148840_1_.writeFloat(this.field_149497_e);
      p_148840_1_.writeFloat(this.field_149495_f);
   }

   public void func_148833_a(INetHandlerPlayServer p_148833_1_) {
      p_148833_1_.func_147348_a(this);
   }

   public boolean func_149494_c() {
      return this.field_149500_a;
   }

   public void func_149490_a(boolean p_149490_1_) {
      this.field_149500_a = p_149490_1_;
   }

   public boolean func_149488_d() {
      return this.field_149498_b;
   }

   public void func_149483_b(boolean p_149483_1_) {
      this.field_149498_b = p_149483_1_;
   }

   public boolean func_149486_e() {
      return this.field_149499_c;
   }

   public void func_149491_c(boolean p_149491_1_) {
      this.field_149499_c = p_149491_1_;
   }

   public boolean func_149484_f() {
      return this.field_149496_d;
   }

   public void func_149493_d(boolean p_149493_1_) {
      this.field_149496_d = p_149493_1_;
   }

   public void func_149485_a(float p_149485_1_) {
      this.field_149497_e = p_149485_1_;
   }

   public void func_149492_b(float p_149492_1_) {
      this.field_149495_f = p_149492_1_;
   }
}
