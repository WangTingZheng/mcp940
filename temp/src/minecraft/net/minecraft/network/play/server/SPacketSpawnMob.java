package net.minecraft.network.play.server;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.play.INetHandlerPlayClient;

public class SPacketSpawnMob implements Packet<INetHandlerPlayClient> {
   private int field_149042_a;
   private UUID field_186894_b;
   private int field_149040_b;
   private double field_149041_c;
   private double field_149038_d;
   private double field_149039_e;
   private int field_149036_f;
   private int field_149037_g;
   private int field_149047_h;
   private byte field_149048_i;
   private byte field_149045_j;
   private byte field_149046_k;
   private EntityDataManager field_149043_l;
   private List<EntityDataManager.DataEntry<?>> field_149044_m;

   public SPacketSpawnMob() {
   }

   public SPacketSpawnMob(EntityLivingBase p_i46973_1_) {
      this.field_149042_a = p_i46973_1_.func_145782_y();
      this.field_186894_b = p_i46973_1_.func_110124_au();
      this.field_149040_b = EntityList.field_191308_b.func_148757_b(p_i46973_1_.getClass());
      this.field_149041_c = p_i46973_1_.field_70165_t;
      this.field_149038_d = p_i46973_1_.field_70163_u;
      this.field_149039_e = p_i46973_1_.field_70161_v;
      this.field_149048_i = (byte)((int)(p_i46973_1_.field_70177_z * 256.0F / 360.0F));
      this.field_149045_j = (byte)((int)(p_i46973_1_.field_70125_A * 256.0F / 360.0F));
      this.field_149046_k = (byte)((int)(p_i46973_1_.field_70759_as * 256.0F / 360.0F));
      double d0 = 3.9D;
      double d1 = p_i46973_1_.field_70159_w;
      double d2 = p_i46973_1_.field_70181_x;
      double d3 = p_i46973_1_.field_70179_y;
      if (d1 < -3.9D) {
         d1 = -3.9D;
      }

      if (d2 < -3.9D) {
         d2 = -3.9D;
      }

      if (d3 < -3.9D) {
         d3 = -3.9D;
      }

      if (d1 > 3.9D) {
         d1 = 3.9D;
      }

      if (d2 > 3.9D) {
         d2 = 3.9D;
      }

      if (d3 > 3.9D) {
         d3 = 3.9D;
      }

      this.field_149036_f = (int)(d1 * 8000.0D);
      this.field_149037_g = (int)(d2 * 8000.0D);
      this.field_149047_h = (int)(d3 * 8000.0D);
      this.field_149043_l = p_i46973_1_.func_184212_Q();
   }

   public void func_148837_a(PacketBuffer p_148837_1_) throws IOException {
      this.field_149042_a = p_148837_1_.func_150792_a();
      this.field_186894_b = p_148837_1_.func_179253_g();
      this.field_149040_b = p_148837_1_.func_150792_a();
      this.field_149041_c = p_148837_1_.readDouble();
      this.field_149038_d = p_148837_1_.readDouble();
      this.field_149039_e = p_148837_1_.readDouble();
      this.field_149048_i = p_148837_1_.readByte();
      this.field_149045_j = p_148837_1_.readByte();
      this.field_149046_k = p_148837_1_.readByte();
      this.field_149036_f = p_148837_1_.readShort();
      this.field_149037_g = p_148837_1_.readShort();
      this.field_149047_h = p_148837_1_.readShort();
      this.field_149044_m = EntityDataManager.func_187215_b(p_148837_1_);
   }

   public void func_148840_b(PacketBuffer p_148840_1_) throws IOException {
      p_148840_1_.func_150787_b(this.field_149042_a);
      p_148840_1_.func_179252_a(this.field_186894_b);
      p_148840_1_.func_150787_b(this.field_149040_b);
      p_148840_1_.writeDouble(this.field_149041_c);
      p_148840_1_.writeDouble(this.field_149038_d);
      p_148840_1_.writeDouble(this.field_149039_e);
      p_148840_1_.writeByte(this.field_149048_i);
      p_148840_1_.writeByte(this.field_149045_j);
      p_148840_1_.writeByte(this.field_149046_k);
      p_148840_1_.writeShort(this.field_149036_f);
      p_148840_1_.writeShort(this.field_149037_g);
      p_148840_1_.writeShort(this.field_149047_h);
      this.field_149043_l.func_187216_a(p_148840_1_);
   }

   public void func_148833_a(INetHandlerPlayClient p_148833_1_) {
      p_148833_1_.func_147281_a(this);
   }

   @Nullable
   public List<EntityDataManager.DataEntry<?>> func_149027_c() {
      return this.field_149044_m;
   }

   public int func_149024_d() {
      return this.field_149042_a;
   }

   public UUID func_186890_c() {
      return this.field_186894_b;
   }

   public int func_149025_e() {
      return this.field_149040_b;
   }

   public double func_186891_e() {
      return this.field_149041_c;
   }

   public double func_186892_f() {
      return this.field_149038_d;
   }

   public double func_186893_g() {
      return this.field_149039_e;
   }

   public int func_149026_i() {
      return this.field_149036_f;
   }

   public int func_149033_j() {
      return this.field_149037_g;
   }

   public int func_149031_k() {
      return this.field_149047_h;
   }

   public byte func_149028_l() {
      return this.field_149048_i;
   }

   public byte func_149030_m() {
      return this.field_149045_j;
   }

   public byte func_149032_n() {
      return this.field_149046_k;
   }
}
