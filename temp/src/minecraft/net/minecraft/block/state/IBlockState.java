package net.minecraft.block.state;

import com.google.common.collect.ImmutableMap;
import java.util.Collection;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;

public interface IBlockState extends IBlockBehaviors, IBlockProperties {
   Collection<IProperty<?>> func_177227_a();

   <T extends Comparable<T>> T func_177229_b(IProperty<T> var1);

   <T extends Comparable<T>, V extends T> IBlockState func_177226_a(IProperty<T> var1, V var2);

   <T extends Comparable<T>> IBlockState func_177231_a(IProperty<T> var1);

   ImmutableMap<IProperty<?>, Comparable<?>> func_177228_b();

   Block func_177230_c();
}
