package net.minecraft.block.state;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.Iterables;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;

public abstract class BlockStateBase implements IBlockState {
   private static final Joiner field_177234_a = Joiner.on(',');
   private static final Function<Entry<IProperty<?>, Comparable<?>>, String> field_177233_b = new Function<Entry<IProperty<?>, Comparable<?>>, String>() {
      @Nullable
      public String apply(@Nullable Entry<IProperty<?>, Comparable<?>> p_apply_1_) {
         if (p_apply_1_ == null) {
            return "<NULL>";
         } else {
            IProperty<?> iproperty = (IProperty)p_apply_1_.getKey();
            return iproperty.func_177701_a() + "=" + this.func_185886_a(iproperty, p_apply_1_.getValue());
         }
      }

      private <T extends Comparable<T>> String func_185886_a(IProperty<T> p_185886_1_, Comparable<?> p_185886_2_) {
         return p_185886_1_.func_177702_a(p_185886_2_);
      }
   };

   public <T extends Comparable<T>> IBlockState func_177231_a(IProperty<T> p_177231_1_) {
      return this.func_177226_a(p_177231_1_, (Comparable)func_177232_a(p_177231_1_.func_177700_c(), this.func_177229_b(p_177231_1_)));
   }

   protected static <T> T func_177232_a(Collection<T> p_177232_0_, T p_177232_1_) {
      Iterator<T> iterator = p_177232_0_.iterator();

      while(iterator.hasNext()) {
         if (iterator.next().equals(p_177232_1_)) {
            if (iterator.hasNext()) {
               return iterator.next();
            }

            return p_177232_0_.iterator().next();
         }
      }

      return iterator.next();
   }

   public String toString() {
      StringBuilder stringbuilder = new StringBuilder();
      stringbuilder.append(Block.field_149771_c.func_177774_c(this.func_177230_c()));
      if (!this.func_177228_b().isEmpty()) {
         stringbuilder.append("[");
         field_177234_a.appendTo(stringbuilder, Iterables.transform(this.func_177228_b().entrySet(), field_177233_b));
         stringbuilder.append("]");
      }

      return stringbuilder.toString();
   }
}
