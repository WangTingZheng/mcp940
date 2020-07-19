package net.minecraft.network.play.server;

import java.io.IOException;
import java.util.Collection;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.world.storage.MapData;
import net.minecraft.world.storage.MapDecoration;

public class SPacketMaps implements Packet<INetHandlerPlayClient> {
   private int field_149191_a;
   private byte field_179739_b;
   private boolean field_186950_c;
   private MapDecoration[] field_179740_c;
   private int field_179737_d;
   private int field_179738_e;
   private int field_179735_f;
   private int field_179736_g;
   private byte[] field_179741_h;

   public SPacketMaps() {
   }

   public SPacketMaps(int p_i46937_1_, byte p_i46937_2_, boolean p_i46937_3_, Collection<MapDecoration> p_i46937_4_, byte[] p_i46937_5_, int p_i46937_6_, int p_i46937_7_, int p_i46937_8_, int p_i46937_9_) {
      this.field_149191_a = p_i46937_1_;
      this.field_179739_b = p_i46937_2_;
      this.field_186950_c = p_i46937_3_;
      this.field_179740_c = (MapDecoration[])p_i46937_4_.toArray(new MapDecoration[p_i46937_4_.size()]);
      this.field_179737_d = p_i46937_6_;
      this.field_179738_e = p_i46937_7_;
      this.field_179735_f = p_i46937_8_;
      this.field_179736_g = p_i46937_9_;
      this.field_179741_h = new byte[p_i46937_8_ * p_i46937_9_];

      for(int i = 0; i < p_i46937_8_; ++i) {
         for(int j = 0; j < p_i46937_9_; ++j) {
            this.field_179741_h[i + j * p_i46937_8_] = p_i46937_5_[p_i46937_6_ + i + (p_i46937_7_ + j) * 128];
         }
      }

   }

   public void func_148837_a(PacketBuffer p_148837_1_) throws IOException {
      this.field_149191_a = p_148837_1_.func_150792_a();
      this.field_179739_b = p_148837_1_.readByte();
      this.field_186950_c = p_148837_1_.readBoolean();
      this.field_179740_c = new MapDecoration[p_148837_1_.func_150792_a()];

      for(int i = 0; i < this.field_179740_c.length; ++i) {
         short short1 = (short)p_148837_1_.readByte();
         this.field_179740_c[i] = new MapDecoration(MapDecoration.Type.func_191159_a((byte)(short1 >> 4 & 15)), p_148837_1_.readByte(), p_148837_1_.readByte(), (byte)(short1 & 15));
      }

      this.field_179735_f = p_148837_1_.readUnsignedByte();
      if (this.field_179735_f > 0) {
         this.field_179736_g = p_148837_1_.readUnsignedByte();
         this.field_179737_d = p_148837_1_.readUnsignedByte();
         this.field_179738_e = p_148837_1_.readUnsignedByte();
         this.field_179741_h = p_148837_1_.func_179251_a();
      }

   }

   public void func_148840_b(PacketBuffer p_148840_1_) throws IOException {
      p_148840_1_.func_150787_b(this.field_149191_a);
      p_148840_1_.writeByte(this.field_179739_b);
      p_148840_1_.writeBoolean(this.field_186950_c);
      p_148840_1_.func_150787_b(this.field_179740_c.length);

      for(MapDecoration mapdecoration : this.field_179740_c) {
         p_148840_1_.writeByte((mapdecoration.func_176110_a() & 15) << 4 | mapdecoration.func_176111_d() & 15);
         p_148840_1_.writeByte(mapdecoration.func_176112_b());
         p_148840_1_.writeByte(mapdecoration.func_176113_c());
      }

      p_148840_1_.writeByte(this.field_179735_f);
      if (this.field_179735_f > 0) {
         p_148840_1_.writeByte(this.field_179736_g);
         p_148840_1_.writeByte(this.field_179737_d);
         p_148840_1_.writeByte(this.field_179738_e);
         p_148840_1_.func_179250_a(this.field_179741_h);
      }

   }

   public void func_148833_a(INetHandlerPlayClient p_148833_1_) {
      p_148833_1_.func_147264_a(this);
   }

   public int func_149188_c() {
      return this.field_149191_a;
   }

   public void func_179734_a(MapData p_179734_1_) {
      p_179734_1_.field_76197_d = this.field_179739_b;
      p_179734_1_.field_186210_e = this.field_186950_c;
      p_179734_1_.field_76203_h.clear();

      for(int i = 0; i < this.field_179740_c.length; ++i) {
         MapDecoration mapdecoration = this.field_179740_c[i];
         p_179734_1_.field_76203_h.put("icon-" + i, mapdecoration);
      }

      for(int j = 0; j < this.field_179735_f; ++j) {
         for(int k = 0; k < this.field_179736_g; ++k) {
            p_179734_1_.field_76198_e[this.field_179737_d + j + (this.field_179738_e + k) * 128] = this.field_179741_h[j + k * this.field_179735_f];
         }
      }

   }
}
