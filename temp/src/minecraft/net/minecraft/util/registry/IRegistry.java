package net.minecraft.util.registry;

import java.util.Set;
import javax.annotation.Nullable;

public interface IRegistry<K, V> extends Iterable<V> {
   @Nullable
   V func_82594_a(K var1);

   void func_82595_a(K var1, V var2);

   Set<K> func_148742_b();
}
