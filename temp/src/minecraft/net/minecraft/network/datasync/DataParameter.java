package net.minecraft.network.datasync;

public class DataParameter<T> {
   private final int field_187157_a;
   private final DataSerializer<T> field_187158_b;

   public DataParameter(int p_i46841_1_, DataSerializer<T> p_i46841_2_) {
      this.field_187157_a = p_i46841_1_;
      this.field_187158_b = p_i46841_2_;
   }

   public int func_187155_a() {
      return this.field_187157_a;
   }

   public DataSerializer<T> func_187156_b() {
      return this.field_187158_b;
   }

   public boolean equals(Object p_equals_1_) {
      if (this == p_equals_1_) {
         return true;
      } else if (p_equals_1_ != null && this.getClass() == p_equals_1_.getClass()) {
         DataParameter<?> dataparameter = (DataParameter)p_equals_1_;
         return this.field_187157_a == dataparameter.field_187157_a;
      } else {
         return false;
      }
   }

   public int hashCode() {
      return this.field_187157_a;
   }
}
