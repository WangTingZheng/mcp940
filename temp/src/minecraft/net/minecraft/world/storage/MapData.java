package net.minecraft.world.storage;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.SPacketMaps;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class MapData extends WorldSavedData {
   public int field_76201_a;
   public int field_76199_b;
   public byte field_76200_c;
   public boolean field_186210_e;
   public boolean field_191096_f;
   public byte field_76197_d;
   public byte[] field_76198_e = new byte[16384];
   public List<MapData.MapInfo> field_76196_g = Lists.<MapData.MapInfo>newArrayList();
   private final Map<EntityPlayer, MapData.MapInfo> field_76202_j = Maps.<EntityPlayer, MapData.MapInfo>newHashMap();
   public Map<String, MapDecoration> field_76203_h = Maps.<String, MapDecoration>newLinkedHashMap();

   public MapData(String p_i2140_1_) {
      super(p_i2140_1_);
   }

   public void func_176054_a(double p_176054_1_, double p_176054_3_, int p_176054_5_) {
      int i = 128 * (1 << p_176054_5_);
      int j = MathHelper.func_76128_c((p_176054_1_ + 64.0D) / (double)i);
      int k = MathHelper.func_76128_c((p_176054_3_ + 64.0D) / (double)i);
      this.field_76201_a = j * i + i / 2 - 64;
      this.field_76199_b = k * i + i / 2 - 64;
   }

   public void func_76184_a(NBTTagCompound p_76184_1_) {
      this.field_76200_c = p_76184_1_.func_74771_c("dimension");
      this.field_76201_a = p_76184_1_.func_74762_e("xCenter");
      this.field_76199_b = p_76184_1_.func_74762_e("zCenter");
      this.field_76197_d = p_76184_1_.func_74771_c("scale");
      this.field_76197_d = (byte)MathHelper.func_76125_a(this.field_76197_d, 0, 4);
      if (p_76184_1_.func_150297_b("trackingPosition", 1)) {
         this.field_186210_e = p_76184_1_.func_74767_n("trackingPosition");
      } else {
         this.field_186210_e = true;
      }

      this.field_191096_f = p_76184_1_.func_74767_n("unlimitedTracking");
      int i = p_76184_1_.func_74765_d("width");
      int j = p_76184_1_.func_74765_d("height");
      if (i == 128 && j == 128) {
         this.field_76198_e = p_76184_1_.func_74770_j("colors");
      } else {
         byte[] abyte = p_76184_1_.func_74770_j("colors");
         this.field_76198_e = new byte[16384];
         int k = (128 - i) / 2;
         int l = (128 - j) / 2;

         for(int i1 = 0; i1 < j; ++i1) {
            int j1 = i1 + l;
            if (j1 >= 0 || j1 < 128) {
               for(int k1 = 0; k1 < i; ++k1) {
                  int l1 = k1 + k;
                  if (l1 >= 0 || l1 < 128) {
                     this.field_76198_e[l1 + j1 * 128] = abyte[k1 + i1 * i];
                  }
               }
            }
         }
      }

   }

   public NBTTagCompound func_189551_b(NBTTagCompound p_189551_1_) {
      p_189551_1_.func_74774_a("dimension", this.field_76200_c);
      p_189551_1_.func_74768_a("xCenter", this.field_76201_a);
      p_189551_1_.func_74768_a("zCenter", this.field_76199_b);
      p_189551_1_.func_74774_a("scale", this.field_76197_d);
      p_189551_1_.func_74777_a("width", (short)128);
      p_189551_1_.func_74777_a("height", (short)128);
      p_189551_1_.func_74773_a("colors", this.field_76198_e);
      p_189551_1_.func_74757_a("trackingPosition", this.field_186210_e);
      p_189551_1_.func_74757_a("unlimitedTracking", this.field_191096_f);
      return p_189551_1_;
   }

   public void func_76191_a(EntityPlayer p_76191_1_, ItemStack p_76191_2_) {
      if (!this.field_76202_j.containsKey(p_76191_1_)) {
         MapData.MapInfo mapdata$mapinfo = new MapData.MapInfo(p_76191_1_);
         this.field_76202_j.put(p_76191_1_, mapdata$mapinfo);
         this.field_76196_g.add(mapdata$mapinfo);
      }

      if (!p_76191_1_.field_71071_by.func_70431_c(p_76191_2_)) {
         this.field_76203_h.remove(p_76191_1_.func_70005_c_());
      }

      for(int i = 0; i < this.field_76196_g.size(); ++i) {
         MapData.MapInfo mapdata$mapinfo1 = this.field_76196_g.get(i);
         if (!mapdata$mapinfo1.field_76211_a.field_70128_L && (mapdata$mapinfo1.field_76211_a.field_71071_by.func_70431_c(p_76191_2_) || p_76191_2_.func_82839_y())) {
            if (!p_76191_2_.func_82839_y() && mapdata$mapinfo1.field_76211_a.field_71093_bK == this.field_76200_c && this.field_186210_e) {
               this.func_191095_a(MapDecoration.Type.PLAYER, mapdata$mapinfo1.field_76211_a.field_70170_p, mapdata$mapinfo1.field_76211_a.func_70005_c_(), mapdata$mapinfo1.field_76211_a.field_70165_t, mapdata$mapinfo1.field_76211_a.field_70161_v, (double)mapdata$mapinfo1.field_76211_a.field_70177_z);
            }
         } else {
            this.field_76202_j.remove(mapdata$mapinfo1.field_76211_a);
            this.field_76196_g.remove(mapdata$mapinfo1);
         }
      }

      if (p_76191_2_.func_82839_y() && this.field_186210_e) {
         EntityItemFrame entityitemframe = p_76191_2_.func_82836_z();
         BlockPos blockpos = entityitemframe.func_174857_n();
         this.func_191095_a(MapDecoration.Type.FRAME, p_76191_1_.field_70170_p, "frame-" + entityitemframe.func_145782_y(), (double)blockpos.func_177958_n(), (double)blockpos.func_177952_p(), (double)(entityitemframe.field_174860_b.func_176736_b() * 90));
      }

      if (p_76191_2_.func_77942_o() && p_76191_2_.func_77978_p().func_150297_b("Decorations", 9)) {
         NBTTagList nbttaglist = p_76191_2_.func_77978_p().func_150295_c("Decorations", 10);

         for(int j = 0; j < nbttaglist.func_74745_c(); ++j) {
            NBTTagCompound nbttagcompound = nbttaglist.func_150305_b(j);
            if (!this.field_76203_h.containsKey(nbttagcompound.func_74779_i("id"))) {
               this.func_191095_a(MapDecoration.Type.func_191159_a(nbttagcompound.func_74771_c("type")), p_76191_1_.field_70170_p, nbttagcompound.func_74779_i("id"), nbttagcompound.func_74769_h("x"), nbttagcompound.func_74769_h("z"), nbttagcompound.func_74769_h("rot"));
            }
         }
      }

   }

   public static void func_191094_a(ItemStack p_191094_0_, BlockPos p_191094_1_, String p_191094_2_, MapDecoration.Type p_191094_3_) {
      NBTTagList nbttaglist;
      if (p_191094_0_.func_77942_o() && p_191094_0_.func_77978_p().func_150297_b("Decorations", 9)) {
         nbttaglist = p_191094_0_.func_77978_p().func_150295_c("Decorations", 10);
      } else {
         nbttaglist = new NBTTagList();
         p_191094_0_.func_77983_a("Decorations", nbttaglist);
      }

      NBTTagCompound nbttagcompound = new NBTTagCompound();
      nbttagcompound.func_74774_a("type", p_191094_3_.func_191163_a());
      nbttagcompound.func_74778_a("id", p_191094_2_);
      nbttagcompound.func_74780_a("x", (double)p_191094_1_.func_177958_n());
      nbttagcompound.func_74780_a("z", (double)p_191094_1_.func_177952_p());
      nbttagcompound.func_74780_a("rot", 180.0D);
      nbttaglist.func_74742_a(nbttagcompound);
      if (p_191094_3_.func_191162_c()) {
         NBTTagCompound nbttagcompound1 = p_191094_0_.func_190925_c("display");
         nbttagcompound1.func_74768_a("MapColor", p_191094_3_.func_191161_d());
      }

   }

   private void func_191095_a(MapDecoration.Type p_191095_1_, World p_191095_2_, String p_191095_3_, double p_191095_4_, double p_191095_6_, double p_191095_8_) {
      int i = 1 << this.field_76197_d;
      float f = (float)(p_191095_4_ - (double)this.field_76201_a) / (float)i;
      float f1 = (float)(p_191095_6_ - (double)this.field_76199_b) / (float)i;
      byte b0 = (byte)((int)((double)(f * 2.0F) + 0.5D));
      byte b1 = (byte)((int)((double)(f1 * 2.0F) + 0.5D));
      int j = 63;
      byte b2;
      if (f >= -63.0F && f1 >= -63.0F && f <= 63.0F && f1 <= 63.0F) {
         p_191095_8_ = p_191095_8_ + (p_191095_8_ < 0.0D ? -8.0D : 8.0D);
         b2 = (byte)((int)(p_191095_8_ * 16.0D / 360.0D));
         if (this.field_76200_c < 0) {
            int l = (int)(p_191095_2_.func_72912_H().func_76073_f() / 10L);
            b2 = (byte)(l * l * 34187121 + l * 121 >> 15 & 15);
         }
      } else {
         if (p_191095_1_ != MapDecoration.Type.PLAYER) {
            this.field_76203_h.remove(p_191095_3_);
            return;
         }

         int k = 320;
         if (Math.abs(f) < 320.0F && Math.abs(f1) < 320.0F) {
            p_191095_1_ = MapDecoration.Type.PLAYER_OFF_MAP;
         } else {
            if (!this.field_191096_f) {
               this.field_76203_h.remove(p_191095_3_);
               return;
            }

            p_191095_1_ = MapDecoration.Type.PLAYER_OFF_LIMITS;
         }

         b2 = 0;
         if (f <= -63.0F) {
            b0 = -128;
         }

         if (f1 <= -63.0F) {
            b1 = -128;
         }

         if (f >= 63.0F) {
            b0 = 127;
         }

         if (f1 >= 63.0F) {
            b1 = 127;
         }
      }

      this.field_76203_h.put(p_191095_3_, new MapDecoration(p_191095_1_, b0, b1, b2));
   }

   @Nullable
   public Packet<?> func_176052_a(ItemStack p_176052_1_, World p_176052_2_, EntityPlayer p_176052_3_) {
      MapData.MapInfo mapdata$mapinfo = this.field_76202_j.get(p_176052_3_);
      return mapdata$mapinfo == null ? null : mapdata$mapinfo.func_176101_a(p_176052_1_);
   }

   public void func_176053_a(int p_176053_1_, int p_176053_2_) {
      super.func_76185_a();

      for(MapData.MapInfo mapdata$mapinfo : this.field_76196_g) {
         mapdata$mapinfo.func_176102_a(p_176053_1_, p_176053_2_);
      }

   }

   public MapData.MapInfo func_82568_a(EntityPlayer p_82568_1_) {
      MapData.MapInfo mapdata$mapinfo = this.field_76202_j.get(p_82568_1_);
      if (mapdata$mapinfo == null) {
         mapdata$mapinfo = new MapData.MapInfo(p_82568_1_);
         this.field_76202_j.put(p_82568_1_, mapdata$mapinfo);
         this.field_76196_g.add(mapdata$mapinfo);
      }

      return mapdata$mapinfo;
   }

   public class MapInfo {
      public final EntityPlayer field_76211_a;
      private boolean field_176105_d = true;
      private int field_176106_e;
      private int field_176103_f;
      private int field_176104_g = 127;
      private int field_176108_h = 127;
      private int field_176109_i;
      public int field_82569_d;

      public MapInfo(EntityPlayer p_i2138_2_) {
         this.field_76211_a = p_i2138_2_;
      }

      @Nullable
      public Packet<?> func_176101_a(ItemStack p_176101_1_) {
         if (this.field_176105_d) {
            this.field_176105_d = false;
            return new SPacketMaps(p_176101_1_.func_77960_j(), MapData.this.field_76197_d, MapData.this.field_186210_e, MapData.this.field_76203_h.values(), MapData.this.field_76198_e, this.field_176106_e, this.field_176103_f, this.field_176104_g + 1 - this.field_176106_e, this.field_176108_h + 1 - this.field_176103_f);
         } else {
            return this.field_176109_i++ % 5 == 0 ? new SPacketMaps(p_176101_1_.func_77960_j(), MapData.this.field_76197_d, MapData.this.field_186210_e, MapData.this.field_76203_h.values(), MapData.this.field_76198_e, 0, 0, 0, 0) : null;
         }
      }

      public void func_176102_a(int p_176102_1_, int p_176102_2_) {
         if (this.field_176105_d) {
            this.field_176106_e = Math.min(this.field_176106_e, p_176102_1_);
            this.field_176103_f = Math.min(this.field_176103_f, p_176102_2_);
            this.field_176104_g = Math.max(this.field_176104_g, p_176102_1_);
            this.field_176108_h = Math.max(this.field_176108_h, p_176102_2_);
         } else {
            this.field_176105_d = true;
            this.field_176106_e = p_176102_1_;
            this.field_176103_f = p_176102_2_;
            this.field_176104_g = p_176102_1_;
            this.field_176108_h = p_176102_2_;
         }

      }
   }
}
