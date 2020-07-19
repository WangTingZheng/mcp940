package net.minecraft.block.properties;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.Collection;
import java.util.Map;
import net.minecraft.util.IStringSerializable;

public class PropertyEnum<T extends Enum<T> & IStringSerializable> extends PropertyHelper<T> {
   private final ImmutableSet<T> field_177711_a;
   private final Map<String, T> field_177710_b = Maps.<String, T>newHashMap();

   protected PropertyEnum(String p_i45649_1_, Class<T> p_i45649_2_, Collection<T> p_i45649_3_) {
      super(p_i45649_1_, p_i45649_2_);
      this.field_177711_a = ImmutableSet.copyOf(p_i45649_3_);

      for(T t : p_i45649_3_) {
         String s = ((IStringSerializable)t).func_176610_l();
         if (this.field_177710_b.containsKey(s)) {
            throw new IllegalArgumentException("Multiple values have the same name '" + s + "'");
         }

         this.field_177710_b.put(s, t);
      }

   }

   public Collection<T> func_177700_c() {
      return this.field_177711_a;
   }

   public Optional<T> func_185929_b(String p_185929_1_) {
      return Optional.<T>fromNullable(this.field_177710_b.get(p_185929_1_));
   }

   public String func_177702_a(T p_177702_1_) {
      return ((IStringSerializable)p_177702_1_).func_176610_l();
   }

   public boolean equals(Object p_equals_1_) {
      if (this == p_equals_1_) {
         return true;
      } else if (p_equals_1_ instanceof PropertyEnum && super.equals(p_equals_1_)) {
         PropertyEnum<?> propertyenum = (PropertyEnum)p_equals_1_;
         return this.field_177711_a.equals(propertyenum.field_177711_a) && this.field_177710_b.equals(propertyenum.field_177710_b);
      } else {
         return false;
      }
   }

   public int hashCode() {
      int i = super.hashCode();
      i = 31 * i + this.field_177711_a.hashCode();
      i = 31 * i + this.field_177710_b.hashCode();
      return i;
   }

   public static <T extends Enum<T> & IStringSerializable> PropertyEnum<T> func_177709_a(String p_177709_0_, Class<T> p_177709_1_) {
      return func_177708_a(p_177709_0_, p_177709_1_, Predicates.alwaysTrue());
   }

   public static <T extends Enum<T> & IStringSerializable> PropertyEnum<T> func_177708_a(String p_177708_0_, Class<T> p_177708_1_, Predicate<T> p_177708_2_) {
      return func_177707_a(p_177708_0_, p_177708_1_, Collections2.filter(Lists.newArrayList(p_177708_1_.getEnumConstants()), p_177708_2_));
   }

   public static <T extends Enum<T> & IStringSerializable> PropertyEnum<T> func_177706_a(String p_177706_0_, Class<T> p_177706_1_, T... p_177706_2_) {
      return func_177707_a(p_177706_0_, p_177706_1_, Lists.newArrayList(p_177706_2_));
   }

   public static <T extends Enum<T> & IStringSerializable> PropertyEnum<T> func_177707_a(String p_177707_0_, Class<T> p_177707_1_, Collection<T> p_177707_2_) {
      return new PropertyEnum<T>(p_177707_0_, p_177707_1_, p_177707_2_);
   }
}
