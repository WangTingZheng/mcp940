package net.minecraft.util.registry;

import com.google.common.collect.Maps;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import javax.annotation.Nullable;
import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RegistrySimple<K, V> implements IRegistry<K, V> {
   private static final Logger field_148743_a = LogManager.getLogger();
   protected final Map<K, V> field_82596_a = this.func_148740_a();
   private Object[] field_186802_b;

   protected Map<K, V> func_148740_a() {
      return Maps.<K, V>newHashMap();
   }

   @Nullable
   public V func_82594_a(@Nullable K p_82594_1_) {
      return this.field_82596_a.get(p_82594_1_);
   }

   public void func_82595_a(K p_82595_1_, V p_82595_2_) {
      Validate.notNull(p_82595_1_);
      Validate.notNull(p_82595_2_);
      this.field_186802_b = null;
      if (this.field_82596_a.containsKey(p_82595_1_)) {
         field_148743_a.debug("Adding duplicate key '{}' to registry", p_82595_1_);
      }

      this.field_82596_a.put(p_82595_1_, p_82595_2_);
   }

   public Set<K> func_148742_b() {
      return Collections.<K>unmodifiableSet(this.field_82596_a.keySet());
   }

   @Nullable
   public V func_186801_a(Random p_186801_1_) {
      if (this.field_186802_b == null) {
         Collection<?> collection = this.field_82596_a.values();
         if (collection.isEmpty()) {
            return (V)null;
         }

         this.field_186802_b = collection.toArray(new Object[collection.size()]);
      }

      return (V)this.field_186802_b[p_186801_1_.nextInt(this.field_186802_b.length)];
   }

   public boolean func_148741_d(K p_148741_1_) {
      return this.field_82596_a.containsKey(p_148741_1_);
   }

   public Iterator<V> iterator() {
      return this.field_82596_a.values().iterator();
   }
}
