package net.minecraft.util;

import javax.annotation.Nullable;

public class IntHashMap<V> {
   private transient IntHashMap.Entry<V>[] field_76055_a = new IntHashMap.Entry[16];
   private transient int field_76053_b;
   private int field_76054_c = 12;
   private final float field_76051_d = 0.75F;

   private static int func_76044_g(int p_76044_0_) {
      p_76044_0_ = p_76044_0_ ^ p_76044_0_ >>> 20 ^ p_76044_0_ >>> 12;
      return p_76044_0_ ^ p_76044_0_ >>> 7 ^ p_76044_0_ >>> 4;
   }

   private static int func_76043_a(int p_76043_0_, int p_76043_1_) {
      return p_76043_0_ & p_76043_1_ - 1;
   }

   @Nullable
   public V func_76041_a(int p_76041_1_) {
      int i = func_76044_g(p_76041_1_);

      for(IntHashMap.Entry<V> entry = this.field_76055_a[func_76043_a(i, this.field_76055_a.length)]; entry != null; entry = entry.field_76034_c) {
         if (entry.field_76035_a == p_76041_1_) {
            return entry.field_76033_b;
         }
      }

      return (V)null;
   }

   public boolean func_76037_b(int p_76037_1_) {
      return this.func_76045_c(p_76037_1_) != null;
   }

   @Nullable
   final IntHashMap.Entry<V> func_76045_c(int p_76045_1_) {
      int i = func_76044_g(p_76045_1_);

      for(IntHashMap.Entry<V> entry = this.field_76055_a[func_76043_a(i, this.field_76055_a.length)]; entry != null; entry = entry.field_76034_c) {
         if (entry.field_76035_a == p_76045_1_) {
            return entry;
         }
      }

      return null;
   }

   public void func_76038_a(int p_76038_1_, V p_76038_2_) {
      int i = func_76044_g(p_76038_1_);
      int j = func_76043_a(i, this.field_76055_a.length);

      for(IntHashMap.Entry<V> entry = this.field_76055_a[j]; entry != null; entry = entry.field_76034_c) {
         if (entry.field_76035_a == p_76038_1_) {
            entry.field_76033_b = p_76038_2_;
            return;
         }
      }

      this.func_76040_a(i, p_76038_1_, p_76038_2_, j);
   }

   private void func_76047_h(int p_76047_1_) {
      IntHashMap.Entry<V>[] entry = this.field_76055_a;
      int i = entry.length;
      if (i == 1073741824) {
         this.field_76054_c = Integer.MAX_VALUE;
      } else {
         IntHashMap.Entry<V>[] entry1 = new IntHashMap.Entry[p_76047_1_];
         this.func_76048_a(entry1);
         this.field_76055_a = entry1;
         this.field_76054_c = (int)((float)p_76047_1_ * this.field_76051_d);
      }
   }

   private void func_76048_a(IntHashMap.Entry<V>[] p_76048_1_) {
      IntHashMap.Entry<V>[] entry = this.field_76055_a;
      int i = p_76048_1_.length;

      for(int j = 0; j < entry.length; ++j) {
         IntHashMap.Entry<V> entry1 = entry[j];
         if (entry1 != null) {
            entry[j] = null;

            while(true) {
               IntHashMap.Entry<V> entry2 = entry1.field_76034_c;
               int k = func_76043_a(entry1.field_76032_d, i);
               entry1.field_76034_c = p_76048_1_[k];
               p_76048_1_[k] = entry1;
               entry1 = entry2;
               if (entry2 == null) {
                  break;
               }
            }
         }
      }

   }

   @Nullable
   public V func_76049_d(int p_76049_1_) {
      IntHashMap.Entry<V> entry = this.func_76036_e(p_76049_1_);
      return (V)(entry == null ? null : entry.field_76033_b);
   }

   @Nullable
   final IntHashMap.Entry<V> func_76036_e(int p_76036_1_) {
      int i = func_76044_g(p_76036_1_);
      int j = func_76043_a(i, this.field_76055_a.length);
      IntHashMap.Entry<V> entry = this.field_76055_a[j];

      IntHashMap.Entry<V> entry1;
      IntHashMap.Entry<V> entry2;
      for(entry1 = entry; entry1 != null; entry1 = entry2) {
         entry2 = entry1.field_76034_c;
         if (entry1.field_76035_a == p_76036_1_) {
            --this.field_76053_b;
            if (entry == entry1) {
               this.field_76055_a[j] = entry2;
            } else {
               entry.field_76034_c = entry2;
            }

            return entry1;
         }

         entry = entry1;
      }

      return entry1;
   }

   public void func_76046_c() {
      IntHashMap.Entry<V>[] entry = this.field_76055_a;

      for(int i = 0; i < entry.length; ++i) {
         entry[i] = null;
      }

      this.field_76053_b = 0;
   }

   private void func_76040_a(int p_76040_1_, int p_76040_2_, V p_76040_3_, int p_76040_4_) {
      IntHashMap.Entry<V> entry = this.field_76055_a[p_76040_4_];
      this.field_76055_a[p_76040_4_] = new IntHashMap.Entry(p_76040_1_, p_76040_2_, p_76040_3_, entry);
      if (this.field_76053_b++ >= this.field_76054_c) {
         this.func_76047_h(2 * this.field_76055_a.length);
      }

   }

   static class Entry<V> {
      final int field_76035_a;
      V field_76033_b;
      IntHashMap.Entry<V> field_76034_c;
      final int field_76032_d;

      Entry(int p_i1552_1_, int p_i1552_2_, V p_i1552_3_, IntHashMap.Entry<V> p_i1552_4_) {
         this.field_76033_b = p_i1552_3_;
         this.field_76034_c = p_i1552_4_;
         this.field_76035_a = p_i1552_2_;
         this.field_76032_d = p_i1552_1_;
      }

      public final int func_76031_a() {
         return this.field_76035_a;
      }

      public final V func_76030_b() {
         return this.field_76033_b;
      }

      public final boolean equals(Object p_equals_1_) {
         if (!(p_equals_1_ instanceof IntHashMap.Entry)) {
            return false;
         } else {
            IntHashMap.Entry<V> entry = (IntHashMap.Entry)p_equals_1_;
            if (this.field_76035_a == entry.field_76035_a) {
               Object object = this.func_76030_b();
               Object object1 = entry.func_76030_b();
               if (object == object1 || object != null && object.equals(object1)) {
                  return true;
               }
            }

            return false;
         }
      }

      public final int hashCode() {
         return IntHashMap.func_76044_g(this.field_76035_a);
      }

      public final String toString() {
         return this.func_76031_a() + "=" + this.func_76030_b();
      }
   }
}
