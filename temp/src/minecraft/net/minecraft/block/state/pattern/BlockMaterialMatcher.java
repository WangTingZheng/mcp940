package net.minecraft.block.state.pattern;

import com.google.common.base.Predicate;
import javax.annotation.Nullable;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;

public class BlockMaterialMatcher implements Predicate<IBlockState> {
   private final Material field_189887_a;

   private BlockMaterialMatcher(Material p_i47150_1_) {
      this.field_189887_a = p_i47150_1_;
   }

   public static BlockMaterialMatcher func_189886_a(Material p_189886_0_) {
      return new BlockMaterialMatcher(p_189886_0_);
   }

   public boolean apply(@Nullable IBlockState p_apply_1_) {
      return p_apply_1_ != null && p_apply_1_.func_185904_a() == this.field_189887_a;
   }
}
