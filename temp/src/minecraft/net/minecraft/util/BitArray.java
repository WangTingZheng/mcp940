package net.minecraft.util;

import net.minecraft.util.math.MathHelper;
import org.apache.commons.lang3.Validate;

public class BitArray {
   private final long[] field_188145_a;
   private final int field_188146_b;
   private final long field_188147_c;
   private final int field_188148_d;

   public BitArray(int p_i46832_1_, int p_i46832_2_) {
      Validate.inclusiveBetween(1L, 32L, (long)p_i46832_1_);
      this.field_188148_d = p_i46832_2_;
      this.field_188146_b = p_i46832_1_;
      this.field_188147_c = (1L << p_i46832_1_) - 1L;
      this.field_188145_a = new long[MathHelper.func_154354_b(p_i46832_2_ * p_i46832_1_, 64) / 64];
   }

   public void func_188141_a(int p_188141_1_, int p_188141_2_) {
      Validate.inclusiveBetween(0L, (long)(this.field_188148_d - 1), (long)p_188141_1_);
      Validate.inclusiveBetween(0L, this.field_188147_c, (long)p_188141_2_);
      int i = p_188141_1_ * this.field_188146_b;
      int j = i / 64;
      int k = ((p_188141_1_ + 1) * this.field_188146_b - 1) / 64;
      int l = i % 64;
      this.field_188145_a[j] = this.field_188145_a[j] & ~(this.field_188147_c << l) | ((long)p_188141_2_ & this.field_188147_c) << l;
      if (j != k) {
         int i1 = 64 - l;
         int j1 = this.field_188146_b - i1;
         this.field_188145_a[k] = this.field_188145_a[k] >>> j1 << j1 | ((long)p_188141_2_ & this.field_188147_c) >> i1;
      }

   }

   public int func_188142_a(int p_188142_1_) {
      Validate.inclusiveBetween(0L, (long)(this.field_188148_d - 1), (long)p_188142_1_);
      int i = p_188142_1_ * this.field_188146_b;
      int j = i / 64;
      int k = ((p_188142_1_ + 1) * this.field_188146_b - 1) / 64;
      int l = i % 64;
      if (j == k) {
         return (int)(this.field_188145_a[j] >>> l & this.field_188147_c);
      } else {
         int i1 = 64 - l;
         return (int)((this.field_188145_a[j] >>> l | this.field_188145_a[k] << i1) & this.field_188147_c);
      }
   }

   public long[] func_188143_a() {
      return this.field_188145_a;
   }

   public int func_188144_b() {
      return this.field_188148_d;
   }
}
