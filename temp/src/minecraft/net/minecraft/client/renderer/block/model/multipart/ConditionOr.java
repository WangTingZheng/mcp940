package net.minecraft.client.renderer.block.model.multipart;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Iterables;
import javax.annotation.Nullable;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;

public class ConditionOr implements ICondition {
   final Iterable<ICondition> field_188127_c;

   public ConditionOr(Iterable<ICondition> p_i46563_1_) {
      this.field_188127_c = p_i46563_1_;
   }

   public Predicate<IBlockState> func_188118_a(final BlockStateContainer p_188118_1_) {
      return Predicates.or(Iterables.transform(this.field_188127_c, new Function<ICondition, Predicate<IBlockState>>() {
         @Nullable
         public Predicate<IBlockState> apply(@Nullable ICondition p_apply_1_) {
            return p_apply_1_ == null ? null : p_apply_1_.func_188118_a(p_188118_1_);
         }
      }));
   }
}
