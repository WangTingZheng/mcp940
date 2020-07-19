package net.minecraft.client.renderer.texture;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import net.minecraft.client.renderer.StitcherException;
import net.minecraft.util.math.MathHelper;

public class Stitcher {
   private final int field_147971_a;
   private final Set<Stitcher.Holder> field_94319_a = Sets.<Stitcher.Holder>newHashSetWithExpectedSize(256);
   private final List<Stitcher.Slot> field_94317_b = Lists.<Stitcher.Slot>newArrayListWithCapacity(256);
   private int field_94318_c;
   private int field_94315_d;
   private final int field_94316_e;
   private final int field_94313_f;
   private final int field_94323_h;

   public Stitcher(int p_i46542_1_, int p_i46542_2_, int p_i46542_3_, int p_i46542_4_) {
      this.field_147971_a = p_i46542_4_;
      this.field_94316_e = p_i46542_1_;
      this.field_94313_f = p_i46542_2_;
      this.field_94323_h = p_i46542_3_;
   }

   public int func_110935_a() {
      return this.field_94318_c;
   }

   public int func_110936_b() {
      return this.field_94315_d;
   }

   public void func_110934_a(TextureAtlasSprite p_110934_1_) {
      Stitcher.Holder stitcher$holder = new Stitcher.Holder(p_110934_1_, this.field_147971_a);
      if (this.field_94323_h > 0) {
         stitcher$holder.func_94196_a(this.field_94323_h);
      }

      this.field_94319_a.add(stitcher$holder);
   }

   public void func_94305_f() {
      Stitcher.Holder[] astitcher$holder = (Stitcher.Holder[])this.field_94319_a.toArray(new Stitcher.Holder[this.field_94319_a.size()]);
      Arrays.sort((Object[])astitcher$holder);

      for(Stitcher.Holder stitcher$holder : astitcher$holder) {
         if (!this.func_94310_b(stitcher$holder)) {
            String s = String.format("Unable to fit: %s - size: %dx%d - Maybe try a lowerresolution resourcepack?", stitcher$holder.func_98150_a().func_94215_i(), stitcher$holder.func_98150_a().func_94211_a(), stitcher$holder.func_98150_a().func_94216_b());
            throw new StitcherException(stitcher$holder, s);
         }
      }

      this.field_94318_c = MathHelper.func_151236_b(this.field_94318_c);
      this.field_94315_d = MathHelper.func_151236_b(this.field_94315_d);
   }

   public List<TextureAtlasSprite> func_94309_g() {
      List<Stitcher.Slot> list = Lists.<Stitcher.Slot>newArrayList();

      for(Stitcher.Slot stitcher$slot : this.field_94317_b) {
         stitcher$slot.func_94184_a(list);
      }

      List<TextureAtlasSprite> list1 = Lists.<TextureAtlasSprite>newArrayList();

      for(Stitcher.Slot stitcher$slot1 : list) {
         Stitcher.Holder stitcher$holder = stitcher$slot1.func_94183_a();
         TextureAtlasSprite textureatlassprite = stitcher$holder.func_98150_a();
         textureatlassprite.func_110971_a(this.field_94318_c, this.field_94315_d, stitcher$slot1.func_94186_b(), stitcher$slot1.func_94185_c(), stitcher$holder.func_94195_e());
         list1.add(textureatlassprite);
      }

      return list1;
   }

   private static int func_147969_b(int p_147969_0_, int p_147969_1_) {
      return (p_147969_0_ >> p_147969_1_) + ((p_147969_0_ & (1 << p_147969_1_) - 1) == 0 ? 0 : 1) << p_147969_1_;
   }

   private boolean func_94310_b(Stitcher.Holder p_94310_1_) {
      TextureAtlasSprite textureatlassprite = p_94310_1_.func_98150_a();
      boolean flag = textureatlassprite.func_94211_a() != textureatlassprite.func_94216_b();

      for(int i = 0; i < this.field_94317_b.size(); ++i) {
         if (((Stitcher.Slot)this.field_94317_b.get(i)).func_94182_a(p_94310_1_)) {
            return true;
         }

         if (flag) {
            p_94310_1_.func_94194_d();
            if (((Stitcher.Slot)this.field_94317_b.get(i)).func_94182_a(p_94310_1_)) {
               return true;
            }

            p_94310_1_.func_94194_d();
         }
      }

      return this.func_94311_c(p_94310_1_);
   }

   private boolean func_94311_c(Stitcher.Holder p_94311_1_) {
      int i = Math.min(p_94311_1_.func_94197_a(), p_94311_1_.func_94199_b());
      int j = Math.max(p_94311_1_.func_94197_a(), p_94311_1_.func_94199_b());
      int k = MathHelper.func_151236_b(this.field_94318_c);
      int l = MathHelper.func_151236_b(this.field_94315_d);
      int i1 = MathHelper.func_151236_b(this.field_94318_c + i);
      int j1 = MathHelper.func_151236_b(this.field_94315_d + i);
      boolean flag1 = i1 <= this.field_94316_e;
      boolean flag2 = j1 <= this.field_94313_f;
      if (!flag1 && !flag2) {
         return false;
      } else {
         boolean flag3 = flag1 && k != i1;
         boolean flag4 = flag2 && l != j1;
         boolean flag;
         if (flag3 ^ flag4) {
            flag = flag3;
         } else {
            flag = flag1 && k <= l;
         }

         Stitcher.Slot stitcher$slot;
         if (flag) {
            if (p_94311_1_.func_94197_a() > p_94311_1_.func_94199_b()) {
               p_94311_1_.func_94194_d();
            }

            if (this.field_94315_d == 0) {
               this.field_94315_d = p_94311_1_.func_94199_b();
            }

            stitcher$slot = new Stitcher.Slot(this.field_94318_c, 0, p_94311_1_.func_94197_a(), this.field_94315_d);
            this.field_94318_c += p_94311_1_.func_94197_a();
         } else {
            stitcher$slot = new Stitcher.Slot(0, this.field_94315_d, this.field_94318_c, p_94311_1_.func_94199_b());
            this.field_94315_d += p_94311_1_.func_94199_b();
         }

         stitcher$slot.func_94182_a(p_94311_1_);
         this.field_94317_b.add(stitcher$slot);
         return true;
      }
   }

   public static class Holder implements Comparable<Stitcher.Holder> {
      private final TextureAtlasSprite field_98151_a;
      private final int field_94204_c;
      private final int field_94201_d;
      private final int field_147968_d;
      private boolean field_94202_e;
      private float field_94205_a = 1.0F;

      public Holder(TextureAtlasSprite p_i45094_1_, int p_i45094_2_) {
         this.field_98151_a = p_i45094_1_;
         this.field_94204_c = p_i45094_1_.func_94211_a();
         this.field_94201_d = p_i45094_1_.func_94216_b();
         this.field_147968_d = p_i45094_2_;
         this.field_94202_e = Stitcher.func_147969_b(this.field_94201_d, p_i45094_2_) > Stitcher.func_147969_b(this.field_94204_c, p_i45094_2_);
      }

      public TextureAtlasSprite func_98150_a() {
         return this.field_98151_a;
      }

      public int func_94197_a() {
         int i = this.field_94202_e ? this.field_94201_d : this.field_94204_c;
         return Stitcher.func_147969_b((int)((float)i * this.field_94205_a), this.field_147968_d);
      }

      public int func_94199_b() {
         int i = this.field_94202_e ? this.field_94204_c : this.field_94201_d;
         return Stitcher.func_147969_b((int)((float)i * this.field_94205_a), this.field_147968_d);
      }

      public void func_94194_d() {
         this.field_94202_e = !this.field_94202_e;
      }

      public boolean func_94195_e() {
         return this.field_94202_e;
      }

      public void func_94196_a(int p_94196_1_) {
         if (this.field_94204_c > p_94196_1_ && this.field_94201_d > p_94196_1_) {
            this.field_94205_a = (float)p_94196_1_ / (float)Math.min(this.field_94204_c, this.field_94201_d);
         }
      }

      public String toString() {
         return "Holder{width=" + this.field_94204_c + ", height=" + this.field_94201_d + '}';
      }

      public int compareTo(Stitcher.Holder p_compareTo_1_) {
         int i;
         if (this.func_94199_b() == p_compareTo_1_.func_94199_b()) {
            if (this.func_94197_a() == p_compareTo_1_.func_94197_a()) {
               if (this.field_98151_a.func_94215_i() == null) {
                  return p_compareTo_1_.field_98151_a.func_94215_i() == null ? 0 : -1;
               }

               return this.field_98151_a.func_94215_i().compareTo(p_compareTo_1_.field_98151_a.func_94215_i());
            }

            i = this.func_94197_a() < p_compareTo_1_.func_94197_a() ? 1 : -1;
         } else {
            i = this.func_94199_b() < p_compareTo_1_.func_94199_b() ? 1 : -1;
         }

         return i;
      }
   }

   public static class Slot {
      private final int field_94192_a;
      private final int field_94190_b;
      private final int field_94191_c;
      private final int field_94188_d;
      private List<Stitcher.Slot> field_94189_e;
      private Stitcher.Holder field_94187_f;

      public Slot(int p_i1277_1_, int p_i1277_2_, int p_i1277_3_, int p_i1277_4_) {
         this.field_94192_a = p_i1277_1_;
         this.field_94190_b = p_i1277_2_;
         this.field_94191_c = p_i1277_3_;
         this.field_94188_d = p_i1277_4_;
      }

      public Stitcher.Holder func_94183_a() {
         return this.field_94187_f;
      }

      public int func_94186_b() {
         return this.field_94192_a;
      }

      public int func_94185_c() {
         return this.field_94190_b;
      }

      public boolean func_94182_a(Stitcher.Holder p_94182_1_) {
         if (this.field_94187_f != null) {
            return false;
         } else {
            int i = p_94182_1_.func_94197_a();
            int j = p_94182_1_.func_94199_b();
            if (i <= this.field_94191_c && j <= this.field_94188_d) {
               if (i == this.field_94191_c && j == this.field_94188_d) {
                  this.field_94187_f = p_94182_1_;
                  return true;
               } else {
                  if (this.field_94189_e == null) {
                     this.field_94189_e = Lists.<Stitcher.Slot>newArrayListWithCapacity(1);
                     this.field_94189_e.add(new Stitcher.Slot(this.field_94192_a, this.field_94190_b, i, j));
                     int k = this.field_94191_c - i;
                     int l = this.field_94188_d - j;
                     if (l > 0 && k > 0) {
                        int i1 = Math.max(this.field_94188_d, k);
                        int j1 = Math.max(this.field_94191_c, l);
                        if (i1 >= j1) {
                           this.field_94189_e.add(new Stitcher.Slot(this.field_94192_a, this.field_94190_b + j, i, l));
                           this.field_94189_e.add(new Stitcher.Slot(this.field_94192_a + i, this.field_94190_b, k, this.field_94188_d));
                        } else {
                           this.field_94189_e.add(new Stitcher.Slot(this.field_94192_a + i, this.field_94190_b, k, j));
                           this.field_94189_e.add(new Stitcher.Slot(this.field_94192_a, this.field_94190_b + j, this.field_94191_c, l));
                        }
                     } else if (k == 0) {
                        this.field_94189_e.add(new Stitcher.Slot(this.field_94192_a, this.field_94190_b + j, i, l));
                     } else if (l == 0) {
                        this.field_94189_e.add(new Stitcher.Slot(this.field_94192_a + i, this.field_94190_b, k, j));
                     }
                  }

                  for(Stitcher.Slot stitcher$slot : this.field_94189_e) {
                     if (stitcher$slot.func_94182_a(p_94182_1_)) {
                        return true;
                     }
                  }

                  return false;
               }
            } else {
               return false;
            }
         }
      }

      public void func_94184_a(List<Stitcher.Slot> p_94184_1_) {
         if (this.field_94187_f != null) {
            p_94184_1_.add(this);
         } else if (this.field_94189_e != null) {
            for(Stitcher.Slot stitcher$slot : this.field_94189_e) {
               stitcher$slot.func_94184_a(p_94184_1_);
            }
         }

      }

      public String toString() {
         return "Slot{originX=" + this.field_94192_a + ", originY=" + this.field_94190_b + ", width=" + this.field_94191_c + ", height=" + this.field_94188_d + ", texture=" + this.field_94187_f + ", subSlots=" + this.field_94189_e + '}';
      }
   }
}
