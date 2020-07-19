package net.minecraft.block.state.pattern;

import com.google.common.base.Predicate;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;

public class BlockMatcher implements Predicate<IBlockState> {
   private final Block field_177644_a;

   private BlockMatcher(Block p_i45654_1_) {
      this.field_177644_a = p_i45654_1_;
   }

   public static BlockMatcher func_177642_a(Block p_177642_0_) {
      return new BlockMatcher(p_177642_0_);
   }

   public boolean apply(@Nullable IBlockState p_apply_1_) {
      return p_apply_1_ != null && p_apply_1_.func_177230_c() == this.field_177644_a;
   }
}
