package net.minecraft.block.properties;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSet;
import java.util.Collection;

public class PropertyBool extends PropertyHelper<Boolean> {
   private final ImmutableSet<Boolean> field_177717_a = ImmutableSet.<Boolean>of(Boolean.valueOf(true), Boolean.valueOf(false));

   protected PropertyBool(String p_i45651_1_) {
      super(p_i45651_1_, Boolean.class);
   }

   public Collection<Boolean> func_177700_c() {
      return this.field_177717_a;
   }

   public static PropertyBool func_177716_a(String p_177716_0_) {
      return new PropertyBool(p_177716_0_);
   }

   public Optional<Boolean> func_185929_b(String p_185929_1_) {
      return !"true".equals(p_185929_1_) && !"false".equals(p_185929_1_) ? Optional.absent() : Optional.of(Boolean.valueOf(p_185929_1_));
   }

   public String func_177702_a(Boolean p_177702_1_) {
      return p_177702_1_.toString();
   }

   public boolean equals(Object p_equals_1_) {
      if (this == p_equals_1_) {
         return true;
      } else if (p_equals_1_ instanceof PropertyBool && super.equals(p_equals_1_)) {
         PropertyBool propertybool = (PropertyBool)p_equals_1_;
         return this.field_177717_a.equals(propertybool.field_177717_a);
      } else {
         return false;
      }
   }

   public int hashCode() {
      return 31 * super.hashCode() + this.field_177717_a.hashCode();
   }
}
