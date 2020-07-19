package net.minecraft.client.renderer.block.model.multipart;

import com.google.common.base.Function;
import com.google.common.base.MoreObjects;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;

public class ConditionPropertyValue implements ICondition {
   private static final Splitter field_188124_c = Splitter.on('|').omitEmptyStrings();
   private final String field_188125_d;
   private final String field_188126_e;

   public ConditionPropertyValue(String p_i46565_1_, String p_i46565_2_) {
      this.field_188125_d = p_i46565_1_;
      this.field_188126_e = p_i46565_2_;
   }

   public Predicate<IBlockState> func_188118_a(BlockStateContainer p_188118_1_) {
      final IProperty<?> iproperty = p_188118_1_.func_185920_a(this.field_188125_d);
      if (iproperty == null) {
         throw new RuntimeException(this.toString() + ": Definition: " + p_188118_1_ + " has no property: " + this.field_188125_d);
      } else {
         String s = this.field_188126_e;
         boolean flag = !s.isEmpty() && s.charAt(0) == '!';
         if (flag) {
            s = s.substring(1);
         }

         List<String> list = field_188124_c.splitToList(s);
         if (list.isEmpty()) {
            throw new RuntimeException(this.toString() + ": has an empty value: " + this.field_188126_e);
         } else {
            Predicate<IBlockState> predicate;
            if (list.size() == 1) {
               predicate = this.func_188123_a(iproperty, s);
            } else {
               predicate = Predicates.or(Iterables.transform(list, new Function<String, Predicate<IBlockState>>() {
                  @Nullable
                  public Predicate<IBlockState> apply(@Nullable String p_apply_1_) {
                     return ConditionPropertyValue.this.func_188123_a(iproperty, p_apply_1_);
                  }
               }));
            }

            return flag ? Predicates.not(predicate) : predicate;
         }
      }
   }

   private Predicate<IBlockState> func_188123_a(final IProperty<?> p_188123_1_, String p_188123_2_) {
      final Optional<?> optional = p_188123_1_.func_185929_b(p_188123_2_);
      if (!optional.isPresent()) {
         throw new RuntimeException(this.toString() + ": has an unknown value: " + this.field_188126_e);
      } else {
         return new Predicate<IBlockState>() {
            public boolean apply(@Nullable IBlockState p_apply_1_) {
               return p_apply_1_ != null && p_apply_1_.func_177229_b(p_188123_1_).equals(optional.get());
            }
         };
      }
   }

   public String toString() {
      return MoreObjects.toStringHelper(this).add("key", this.field_188125_d).add("value", this.field_188126_e).toString();
   }
}
