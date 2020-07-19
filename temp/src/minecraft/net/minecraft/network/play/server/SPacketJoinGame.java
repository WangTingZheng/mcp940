package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.GameType;
import net.minecraft.world.WorldType;

public class SPacketJoinGame implements Packet<INetHandlerPlayClient> {
   private int field_149206_a;
   private boolean field_149204_b;
   private GameType field_149205_c;
   private int field_149202_d;
   private EnumDifficulty field_149203_e;
   private int field_149200_f;
   private WorldType field_149201_g;
   private boolean field_179745_h;

   public SPacketJoinGame() {
   }

   public SPacketJoinGame(int p_i46938_1_, GameType p_i46938_2_, boolean p_i46938_3_, int p_i46938_4_, EnumDifficulty p_i46938_5_, int p_i46938_6_, WorldType p_i46938_7_, boolean p_i46938_8_) {
      this.field_149206_a = p_i46938_1_;
      this.field_149202_d = p_i46938_4_;
      this.field_149203_e = p_i46938_5_;
      this.field_149205_c = p_i46938_2_;
      this.field_149200_f = p_i46938_6_;
      this.field_149204_b = p_i46938_3_;
      this.field_149201_g = p_i46938_7_;
      this.field_179745_h = p_i46938_8_;
   }

   public void func_148837_a(PacketBuffer p_148837_1_) throws IOException {
      this.field_149206_a = p_148837_1_.readInt();
      int i = p_148837_1_.readUnsignedByte();
      this.field_149204_b = (i & 8) == 8;
      i = i & -9;
      this.field_149205_c = GameType.func_77146_a(i);
      this.field_149202_d = p_148837_1_.readInt();
      this.field_149203_e = EnumDifficulty.func_151523_a(p_148837_1_.readUnsignedByte());
      this.field_149200_f = p_148837_1_.readUnsignedByte();
      this.field_149201_g = WorldType.func_77130_a(p_148837_1_.func_150789_c(16));
      if (this.field_149201_g == null) {
         this.field_149201_g = WorldType.field_77137_b;
      }

      this.field_179745_h = p_148837_1_.readBoolean();
   }

   public void func_148840_b(PacketBuffer p_148840_1_) throws IOException {
      p_148840_1_.writeInt(this.field_149206_a);
      int i = this.field_149205_c.func_77148_a();
      if (this.field_149204_b) {
         i |= 8;
      }

      p_148840_1_.writeByte(i);
      p_148840_1_.writeInt(this.field_149202_d);
      p_148840_1_.writeByte(this.field_149203_e.func_151525_a());
      p_148840_1_.writeByte(this.field_149200_f);
      p_148840_1_.func_180714_a(this.field_149201_g.func_77127_a());
      p_148840_1_.writeBoolean(this.field_179745_h);
   }

   public void func_148833_a(INetHandlerPlayClient p_148833_1_) {
      p_148833_1_.func_147282_a(this);
   }

   public int func_149197_c() {
      return this.field_149206_a;
   }

   public boolean func_149195_d() {
      return this.field_149204_b;
   }

   public GameType func_149198_e() {
      return this.field_149205_c;
   }

   public int func_149194_f() {
      return this.field_149202_d;
   }

   public EnumDifficulty func_149192_g() {
      return this.field_149203_e;
   }

   public int func_149193_h() {
      return this.field_149200_f;
   }

   public WorldType func_149196_i() {
      return this.field_149201_g;
   }

   public boolean func_179744_h() {
      return this.field_179745_h;
   }
}
