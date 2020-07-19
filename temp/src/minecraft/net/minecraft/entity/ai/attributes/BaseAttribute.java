package net.minecraft.entity.ai.attributes;

import javax.annotation.Nullable;

public abstract class BaseAttribute implements IAttribute {
   private final IAttribute field_180373_a;
   private final String field_111115_a;
   private final double field_111113_b;
   private boolean field_111114_c;

   protected BaseAttribute(@Nullable IAttribute p_i45892_1_, String p_i45892_2_, double p_i45892_3_) {
      this.field_180373_a = p_i45892_1_;
      this.field_111115_a = p_i45892_2_;
      this.field_111113_b = p_i45892_3_;
      if (p_i45892_2_ == null) {
         throw new IllegalArgumentException("Name cannot be null!");
      }
   }

   public String func_111108_a() {
      return this.field_111115_a;
   }

   public double func_111110_b() {
      return this.field_111113_b;
   }

   public boolean func_111111_c() {
      return this.field_111114_c;
   }

   public BaseAttribute func_111112_a(boolean p_111112_1_) {
      this.field_111114_c = p_111112_1_;
      return this;
   }

   @Nullable
   public IAttribute func_180372_d() {
      return this.field_180373_a;
   }

   public int hashCode() {
      return this.field_111115_a.hashCode();
   }

   public boolean equals(Object p_equals_1_) {
      return p_equals_1_ instanceof IAttribute && this.field_111115_a.equals(((IAttribute)p_equals_1_).func_111108_a());
   }
}
