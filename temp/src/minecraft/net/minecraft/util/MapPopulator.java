package net.minecraft.util;

import com.google.common.collect.Maps;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;

public class MapPopulator {
   public static <K, V> Map<K, V> func_179400_b(Iterable<K> p_179400_0_, Iterable<V> p_179400_1_) {
      return func_179399_a(p_179400_0_, p_179400_1_, Maps.newLinkedHashMap());
   }

   public static <K, V> Map<K, V> func_179399_a(Iterable<K> p_179399_0_, Iterable<V> p_179399_1_, Map<K, V> p_179399_2_) {
      Iterator<V> iterator = p_179399_1_.iterator();

      for(K k : p_179399_0_) {
         p_179399_2_.put(k, iterator.next());
      }

      if (iterator.hasNext()) {
         throw new NoSuchElementException();
      } else {
         return p_179399_2_;
      }
   }
}
