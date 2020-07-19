package net.minecraft.network.play.server;

import java.io.IOException;
import java.util.UUID;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

public class SPacketSpawnObject implements Packet<INetHandlerPlayClient> {
   private int field_149018_a;
   private UUID field_186883_b;
   private double field_149016_b;
   private double field_149017_c;
   private double field_149014_d;
   private int field_149015_e;
   private int field_149012_f;
   private int field_149013_g;
   private int field_149021_h;
   private int field_149022_i;
   private int field_149019_j;
   private int field_149020_k;

   public SPacketSpawnObject() {
   }

   public SPacketSpawnObject(Entity p_i46976_1_, int p_i46976_2_) {
      this(p_i46976_1_, p_i46976_2_, 0);
   }

   public SPacketSpawnObject(Entity p_i46977_1_, int p_i46977_2_, int p_i46977_3_) {
      this.field_149018_a = p_i46977_1_.func_145782_y();
      this.field_186883_b = p_i46977_1_.func_110124_au();
      this.field_149016_b = p_i46977_1_.field_70165_t;
      this.field_149017_c = p_i46977_1_.field_70163_u;
      this.field_149014_d = p_i46977_1_.field_70161_v;
      this.field_149021_h = MathHelper.func_76141_d(p_i46977_1_.field_70125_A * 256.0F / 360.0F);
      this.field_149022_i = MathHelper.func_76141_d(p_i46977_1_.field_70177_z * 256.0F / 360.0F);
      this.field_149019_j = p_i46977_2_;
      this.field_149020_k = p_i46977_3_;
      double d0 = 3.9D;
      this.field_149015_e = (int)(MathHelper.func_151237_a(p_i46977_1_.field_70159_w, -3.9D, 3.9D) * 8000.0D);
      this.field_149012_f = (int)(MathHelper.func_151237_a(p_i46977_1_.field_70181_x, -3.9D, 3.9D) * 8000.0D);
      this.field_149013_g = (int)(MathHelper.func_151237_a(p_i46977_1_.field_70179_y, -3.9D, 3.9D) * 8000.0D);
   }

   public SPacketSpawnObject(Entity p_i46978_1_, int p_i46978_2_, int p_i46978_3_, BlockPos p_i46978_4_) {
      this(p_i46978_1_, p_i46978_2_, p_i46978_3_);
      this.field_149016_b = (double)p_i46978_4_.func_177958_n();
      this.field_149017_c = (double)p_i46978_4_.func_177956_o();
      this.field_149014_d = (double)p_i46978_4_.func_177952_p();
   }

   public void func_148837_a(PacketBuffer p_148837_1_) throws IOException {
      this.field_149018_a = p_148837_1_.func_150792_a();
      this.field_186883_b = p_148837_1_.func_179253_g();
      this.field_149019_j = p_148837_1_.readByte();
      this.field_149016_b = p_148837_1_.readDouble();
      this.field_149017_c = p_148837_1_.readDouble();
      this.field_149014_d = p_148837_1_.readDouble();
      this.field_149021_h = p_148837_1_.readByte();
      this.field_149022_i = p_148837_1_.readByte();
      this.field_149020_k = p_148837_1_.readInt();
      this.field_149015_e = p_148837_1_.readShort();
      this.field_149012_f = p_148837_1_.readShort();
      this.field_149013_g = p_148837_1_.readShort();
   }

   public void func_148840_b(PacketBuffer p_148840_1_) throws IOException {
      p_148840_1_.func_150787_b(this.field_149018_a);
      p_148840_1_.func_179252_a(this.field_186883_b);
      p_148840_1_.writeByte(this.field_149019_j);
      p_148840_1_.writeDouble(this.field_149016_b);
      p_148840_1_.writeDouble(this.field_149017_c);
      p_148840_1_.writeDouble(this.field_149014_d);
      p_148840_1_.writeByte(this.field_149021_h);
      p_148840_1_.writeByte(this.field_149022_i);
      p_148840_1_.writeInt(this.field_149020_k);
      p_148840_1_.writeShort(this.field_149015_e);
      p_148840_1_.writeShort(this.field_149012_f);
      p_148840_1_.writeShort(this.field_149013_g);
   }

   public void func_148833_a(INetHandlerPlayClient p_148833_1_) {
      p_148833_1_.func_147235_a(this);
   }

   public int func_149001_c() {
      return this.field_149018_a;
   }

   public UUID func_186879_b() {
      return this.field_186883_b;
   }

   public double func_186880_c() {
      return this.field_149016_b;
   }

   public double func_186882_d() {
      return this.field_149017_c;
   }

   public double func_186881_e() {
      return this.field_149014_d;
   }

   public int func_149010_g() {
      return this.field_149015_e;
   }

   public int func_149004_h() {
      return this.field_149012_f;
   }

   public int func_148999_i() {
      return this.field_149013_g;
   }

   public int func_149008_j() {
      return this.field_149021_h;
   }

   public int func_149006_k() {
      return this.field_149022_i;
   }

   public int func_148993_l() {
      return this.field_149019_j;
   }

   public int func_149009_m() {
      return this.field_149020_k;
   }

   public void func_149003_d(int p_149003_1_) {
      this.field_149015_e = p_149003_1_;
   }

   public void func_149000_e(int p_149000_1_) {
      this.field_149012_f = p_149000_1_;
   }

   public void func_149007_f(int p_149007_1_) {
      this.field_149013_g = p_149007_1_;
   }

   public void func_149002_g(int p_149002_1_) {
      this.field_149020_k = p_149002_1_;
   }
}
