package net.minecraft.util.registry;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import java.util.Iterator;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.util.IObjectIntIterable;
import net.minecraft.util.IntIdentityHashBiMap;

public class RegistryNamespaced<K, V> extends RegistrySimple<K, V> implements IObjectIntIterable<V> {
   protected final IntIdentityHashBiMap<V> field_148759_a = new IntIdentityHashBiMap<V>(256);
   protected final Map<V, K> field_148758_b;

   public RegistryNamespaced() {
      this.field_148758_b = ((BiMap)this.field_82596_a).inverse();
   }

   public void func_177775_a(int p_177775_1_, K p_177775_2_, V p_177775_3_) {
      this.field_148759_a.func_186814_a(p_177775_3_, p_177775_1_);
      this.func_82595_a(p_177775_2_, p_177775_3_);
   }

   protected Map<K, V> func_148740_a() {
      return HashBiMap.<K, V>create();
   }

   @Nullable
   public V func_82594_a(@Nullable K p_82594_1_) {
      return (V)super.func_82594_a(p_82594_1_);
   }

   @Nullable
   public K func_177774_c(V p_177774_1_) {
      return this.field_148758_b.get(p_177774_1_);
   }

   public boolean func_148741_d(K p_148741_1_) {
      return super.func_148741_d(p_148741_1_);
   }

   public int func_148757_b(@Nullable V p_148757_1_) {
      return this.field_148759_a.func_186815_a(p_148757_1_);
   }

   @Nullable
   public V func_148754_a(int p_148754_1_) {
      return this.field_148759_a.func_186813_a(p_148754_1_);
   }

   public Iterator<V> iterator() {
      return this.field_148759_a.iterator();
   }
}
