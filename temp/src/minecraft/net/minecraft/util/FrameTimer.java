package net.minecraft.util;

public class FrameTimer {
   private final long[] field_181752_a = new long[240];
   private int field_181753_b;
   private int field_181754_c;
   private int field_181755_d;

   public void func_181747_a(long p_181747_1_) {
      this.field_181752_a[this.field_181755_d] = p_181747_1_;
      ++this.field_181755_d;
      if (this.field_181755_d == 240) {
         this.field_181755_d = 0;
      }

      if (this.field_181754_c < 240) {
         this.field_181753_b = 0;
         ++this.field_181754_c;
      } else {
         this.field_181753_b = this.func_181751_b(this.field_181755_d + 1);
      }

   }

   public int func_181748_a(long p_181748_1_, int p_181748_3_) {
      double d0 = (double)p_181748_1_ / 1.6666666E7D;
      return (int)(d0 * (double)p_181748_3_);
   }

   public int func_181749_a() {
      return this.field_181753_b;
   }

   public int func_181750_b() {
      return this.field_181755_d;
   }

   public int func_181751_b(int p_181751_1_) {
      return p_181751_1_ % 240;
   }

   public long[] func_181746_c() {
      return this.field_181752_a;
   }
}
