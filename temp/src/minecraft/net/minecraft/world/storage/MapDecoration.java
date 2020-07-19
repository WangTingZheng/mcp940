package net.minecraft.world.storage;

import net.minecraft.util.math.MathHelper;

public class MapDecoration {
   private final MapDecoration.Type field_191181_a;
   private byte field_176115_b;
   private byte field_176116_c;
   private byte field_176114_d;

   public MapDecoration(MapDecoration.Type p_i47236_1_, byte p_i47236_2_, byte p_i47236_3_, byte p_i47236_4_) {
      this.field_191181_a = p_i47236_1_;
      this.field_176115_b = p_i47236_2_;
      this.field_176116_c = p_i47236_3_;
      this.field_176114_d = p_i47236_4_;
   }

   public byte func_176110_a() {
      return this.field_191181_a.func_191163_a();
   }

   public MapDecoration.Type func_191179_b() {
      return this.field_191181_a;
   }

   public byte func_176112_b() {
      return this.field_176115_b;
   }

   public byte func_176113_c() {
      return this.field_176116_c;
   }

   public byte func_176111_d() {
      return this.field_176114_d;
   }

   public boolean func_191180_f() {
      return this.field_191181_a.func_191160_b();
   }

   public boolean equals(Object p_equals_1_) {
      if (this == p_equals_1_) {
         return true;
      } else if (!(p_equals_1_ instanceof MapDecoration)) {
         return false;
      } else {
         MapDecoration mapdecoration = (MapDecoration)p_equals_1_;
         if (this.field_191181_a != mapdecoration.field_191181_a) {
            return false;
         } else if (this.field_176114_d != mapdecoration.field_176114_d) {
            return false;
         } else if (this.field_176115_b != mapdecoration.field_176115_b) {
            return false;
         } else {
            return this.field_176116_c == mapdecoration.field_176116_c;
         }
      }
   }

   public int hashCode() {
      int i = this.field_191181_a.func_191163_a();
      i = 31 * i + this.field_176115_b;
      i = 31 * i + this.field_176116_c;
      i = 31 * i + this.field_176114_d;
      return i;
   }

   public static enum Type {
      PLAYER(false),
      FRAME(true),
      RED_MARKER(false),
      BLUE_MARKER(false),
      TARGET_X(true),
      TARGET_POINT(true),
      PLAYER_OFF_MAP(false),
      PLAYER_OFF_LIMITS(false),
      MANSION(true, 5393476),
      MONUMENT(true, 3830373);

      private final byte field_191175_k;
      private final boolean field_191176_l;
      private final int field_191177_m;

      private Type(boolean p_i47343_3_) {
         this(p_i47343_3_, -1);
      }

      private Type(boolean p_i47344_3_, int p_i47344_4_) {
         this.field_191175_k = (byte)this.ordinal();
         this.field_191176_l = p_i47344_3_;
         this.field_191177_m = p_i47344_4_;
      }

      public byte func_191163_a() {
         return this.field_191175_k;
      }

      public boolean func_191160_b() {
         return this.field_191176_l;
      }

      public boolean func_191162_c() {
         return this.field_191177_m >= 0;
      }

      public int func_191161_d() {
         return this.field_191177_m;
      }

      public static MapDecoration.Type func_191159_a(byte p_191159_0_) {
         return values()[MathHelper.func_76125_a(p_191159_0_, 0, values().length - 1)];
      }
   }
}
