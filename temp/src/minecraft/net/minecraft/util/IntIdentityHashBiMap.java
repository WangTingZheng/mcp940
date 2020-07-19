package net.minecraft.util;

import com.google.common.base.Predicates;
import com.google.common.collect.Iterators;
import java.util.Arrays;
import java.util.Iterator;
import javax.annotation.Nullable;
import net.minecraft.util.math.MathHelper;

public class IntIdentityHashBiMap<K> implements IObjectIntIterable<K> {
   private static final Object field_186817_a = null;
   private K[] field_186818_b;
   private int[] field_186819_c;
   private K[] field_186820_d;
   private int field_186821_e;
   private int field_186822_f;

   public IntIdentityHashBiMap(int p_i46830_1_) {
      p_i46830_1_ = (int)((float)p_i46830_1_ / 0.8F);
      this.field_186818_b = (K[])(new Object[p_i46830_1_]);
      this.field_186819_c = new int[p_i46830_1_];
      this.field_186820_d = (K[])(new Object[p_i46830_1_]);
   }

   public int func_186815_a(@Nullable K p_186815_1_) {
      return this.func_186805_c(this.func_186816_b(p_186815_1_, this.func_186811_d(p_186815_1_)));
   }

   @Nullable
   public K func_186813_a(int p_186813_1_) {
      return (K)(p_186813_1_ >= 0 && p_186813_1_ < this.field_186820_d.length ? this.field_186820_d[p_186813_1_] : null);
   }

   private int func_186805_c(int p_186805_1_) {
      return p_186805_1_ == -1 ? -1 : this.field_186819_c[p_186805_1_];
   }

   public int func_186808_c(K p_186808_1_) {
      int i = this.func_186809_c();
      this.func_186814_a(p_186808_1_, i);
      return i;
   }

   private int func_186809_c() {
      while(this.field_186821_e < this.field_186820_d.length && this.field_186820_d[this.field_186821_e] != null) {
         ++this.field_186821_e;
      }

      return this.field_186821_e;
   }

   private void func_186807_d(int p_186807_1_) {
      K[] ak = this.field_186818_b;
      int[] aint = this.field_186819_c;
      this.field_186818_b = (K[])(new Object[p_186807_1_]);
      this.field_186819_c = new int[p_186807_1_];
      this.field_186820_d = (K[])(new Object[p_186807_1_]);
      this.field_186821_e = 0;
      this.field_186822_f = 0;

      for(int i = 0; i < ak.length; ++i) {
         if (ak[i] != null) {
            this.func_186814_a(ak[i], aint[i]);
         }
      }

   }

   public void func_186814_a(K p_186814_1_, int p_186814_2_) {
      int i = Math.max(p_186814_2_, this.field_186822_f + 1);
      if ((float)i >= (float)this.field_186818_b.length * 0.8F) {
         int j;
         for(j = this.field_186818_b.length << 1; j < p_186814_2_; j <<= 1) {
            ;
         }

         this.func_186807_d(j);
      }

      int k = this.func_186806_e(this.func_186811_d(p_186814_1_));
      this.field_186818_b[k] = p_186814_1_;
      this.field_186819_c[k] = p_186814_2_;
      this.field_186820_d[p_186814_2_] = p_186814_1_;
      ++this.field_186822_f;
      if (p_186814_2_ == this.field_186821_e) {
         ++this.field_186821_e;
      }

   }

   private int func_186811_d(@Nullable K p_186811_1_) {
      return (MathHelper.func_188208_f(System.identityHashCode(p_186811_1_)) & Integer.MAX_VALUE) % this.field_186818_b.length;
   }

   private int func_186816_b(@Nullable K p_186816_1_, int p_186816_2_) {
      for(int i = p_186816_2_; i < this.field_186818_b.length; ++i) {
         if (this.field_186818_b[i] == p_186816_1_) {
            return i;
         }

         if (this.field_186818_b[i] == field_186817_a) {
            return -1;
         }
      }

      for(int j = 0; j < p_186816_2_; ++j) {
         if (this.field_186818_b[j] == p_186816_1_) {
            return j;
         }

         if (this.field_186818_b[j] == field_186817_a) {
            return -1;
         }
      }

      return -1;
   }

   private int func_186806_e(int p_186806_1_) {
      for(int i = p_186806_1_; i < this.field_186818_b.length; ++i) {
         if (this.field_186818_b[i] == field_186817_a) {
            return i;
         }
      }

      for(int j = 0; j < p_186806_1_; ++j) {
         if (this.field_186818_b[j] == field_186817_a) {
            return j;
         }
      }

      throw new RuntimeException("Overflowed :(");
   }

   public Iterator<K> iterator() {
      return Iterators.filter(Iterators.forArray(this.field_186820_d), Predicates.notNull());
   }

   public void func_186812_a() {
      Arrays.fill(this.field_186818_b, (Object)null);
      Arrays.fill(this.field_186820_d, (Object)null);
      this.field_186821_e = 0;
      this.field_186822_f = 0;
   }

   public int func_186810_b() {
      return this.field_186822_f;
   }
}
