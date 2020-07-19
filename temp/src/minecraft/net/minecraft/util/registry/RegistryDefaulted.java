package net.minecraft.util.registry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class RegistryDefaulted<K, V> extends RegistrySimple<K, V> {
   private final V field_82597_b;

   public RegistryDefaulted(V p_i1366_1_) {
      this.field_82597_b = p_i1366_1_;
   }

   @Nonnull
   public V func_82594_a(@Nullable K p_82594_1_) {
      V v = (V)super.func_82594_a(p_82594_1_);
      return (V)(v == null ? this.field_82597_b : v);
   }
}
