package net.minecraft.client.renderer.block.model.multipart;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Iterables;
import javax.annotation.Nullable;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;

public class ConditionAnd implements ICondition {
   private final Iterable<ICondition> field_188121_c;

   public ConditionAnd(Iterable<ICondition> p_i46566_1_) {
      this.field_188121_c = p_i46566_1_;
   }

   public Predicate<IBlockState> func_188118_a(final BlockStateContainer p_188118_1_) {
      return Predicates.and(Iterables.transform(this.field_188121_c, new Function<ICondition, Predicate<IBlockState>>() {
         @Nullable
         public Predicate<IBlockState> apply(@Nullable ICondition p_apply_1_) {
            return p_apply_1_ == null ? null : p_apply_1_.func_188118_a(p_188118_1_);
         }
      }));
   }
}
