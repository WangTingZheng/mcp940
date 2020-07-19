package net.minecraft.block.state.pattern;

import com.google.common.base.Predicate;
import com.google.common.collect.Maps;
import java.util.Map;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;

public class BlockStateMatcher implements Predicate<IBlockState> {
   public static final Predicate<IBlockState> field_185928_a = new Predicate<IBlockState>() {
      public boolean apply(@Nullable IBlockState p_apply_1_) {
         return true;
      }
   };
   private final BlockStateContainer field_177641_a;
   private final Map<IProperty<?>, Predicate<?>> field_177640_b = Maps.<IProperty<?>, Predicate<?>>newHashMap();

   private BlockStateMatcher(BlockStateContainer p_i45653_1_) {
      this.field_177641_a = p_i45653_1_;
   }

   public static BlockStateMatcher func_177638_a(Block p_177638_0_) {
      return new BlockStateMatcher(p_177638_0_.func_176194_O());
   }

   public boolean apply(@Nullable IBlockState p_apply_1_) {
      if (p_apply_1_ != null && p_apply_1_.func_177230_c().equals(this.field_177641_a.func_177622_c())) {
         if (this.field_177640_b.isEmpty()) {
            return true;
         } else {
            for(Entry<IProperty<?>, Predicate<?>> entry : this.field_177640_b.entrySet()) {
               if (!this.func_185927_a(p_apply_1_, entry.getKey(), entry.getValue())) {
                  return false;
               }
            }

            return true;
         }
      } else {
         return false;
      }
   }

   protected <T extends Comparable<T>> boolean func_185927_a(IBlockState p_185927_1_, IProperty<T> p_185927_2_, Predicate<?> p_185927_3_) {
      return p_185927_3_.apply(p_185927_1_.func_177229_b(p_185927_2_));
   }

   public <V extends Comparable<V>> BlockStateMatcher func_177637_a(IProperty<V> p_177637_1_, Predicate<? extends V> p_177637_2_) {
      if (!this.field_177641_a.func_177623_d().contains(p_177637_1_)) {
         throw new IllegalArgumentException(this.field_177641_a + " cannot support property " + p_177637_1_);
      } else {
         this.field_177640_b.put(p_177637_1_, p_177637_2_);
         return this;
      }
   }
}
