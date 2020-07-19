package net.minecraft.block.properties;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import java.util.Collection;
import net.minecraft.util.EnumFacing;

public class PropertyDirection extends PropertyEnum<EnumFacing> {
   protected PropertyDirection(String p_i45650_1_, Collection<EnumFacing> p_i45650_2_) {
      super(p_i45650_1_, EnumFacing.class, p_i45650_2_);
   }

   public static PropertyDirection func_177714_a(String p_177714_0_) {
      return func_177712_a(p_177714_0_, Predicates.alwaysTrue());
   }

   public static PropertyDirection func_177712_a(String p_177712_0_, Predicate<EnumFacing> p_177712_1_) {
      return func_177713_a(p_177712_0_, Collections2.filter(Lists.newArrayList(EnumFacing.values()), p_177712_1_));
   }

   public static PropertyDirection func_177713_a(String p_177713_0_, Collection<EnumFacing> p_177713_1_) {
      return new PropertyDirection(p_177713_0_, p_177713_1_);
   }
}
