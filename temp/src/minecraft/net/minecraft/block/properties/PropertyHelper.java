package net.minecraft.block.properties;

import com.google.common.base.MoreObjects;

public abstract class PropertyHelper<T extends Comparable<T>> implements IProperty<T> {
   private final Class<T> field_177704_a;
   private final String field_177703_b;

   protected PropertyHelper(String p_i45652_1_, Class<T> p_i45652_2_) {
      this.field_177704_a = p_i45652_2_;
      this.field_177703_b = p_i45652_1_;
   }

   public String func_177701_a() {
      return this.field_177703_b;
   }

   public Class<T> func_177699_b() {
      return this.field_177704_a;
   }

   public String toString() {
      return MoreObjects.toStringHelper(this).add("name", this.field_177703_b).add("clazz", this.field_177704_a).add("values", this.func_177700_c()).toString();
   }

   public boolean equals(Object p_equals_1_) {
      if (this == p_equals_1_) {
         return true;
      } else if (!(p_equals_1_ instanceof PropertyHelper)) {
         return false;
      } else {
         PropertyHelper<?> propertyhelper = (PropertyHelper)p_equals_1_;
         return this.field_177704_a.equals(propertyhelper.field_177704_a) && this.field_177703_b.equals(propertyhelper.field_177703_b);
      }
   }

   public int hashCode() {
      return 31 * this.field_177704_a.hashCode() + this.field_177703_b.hashCode();
   }
}
