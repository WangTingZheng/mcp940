package net.minecraft.util.registry;

import java.util.Random;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.apache.commons.lang3.Validate;

public class RegistryNamespacedDefaultedByKey<K, V> extends RegistryNamespaced<K, V> {
   private final K field_148760_d;
   private V field_148761_e;

   public RegistryNamespacedDefaultedByKey(K p_i46017_1_) {
      this.field_148760_d = p_i46017_1_;
   }

   public void func_177775_a(int p_177775_1_, K p_177775_2_, V p_177775_3_) {
      if (this.field_148760_d.equals(p_177775_2_)) {
         this.field_148761_e = p_177775_3_;
      }

      super.func_177775_a(p_177775_1_, p_177775_2_, p_177775_3_);
   }

   public void func_177776_a() {
      Validate.notNull(this.field_148761_e, "Missing default of DefaultedMappedRegistry: " + this.field_148760_d);
   }

   public int func_148757_b(V p_148757_1_) {
      int i = super.func_148757_b(p_148757_1_);
      return i == -1 ? super.func_148757_b(this.field_148761_e) : i;
   }

   @Nonnull
   public K func_177774_c(V p_177774_1_) {
      K k = (K)super.func_177774_c(p_177774_1_);
      return (K)(k == null ? this.field_148760_d : k);
   }

   @Nonnull
   public V func_82594_a(@Nullable K p_82594_1_) {
      V v = (V)super.func_82594_a(p_82594_1_);
      return (V)(v == null ? this.field_148761_e : v);
   }

   @Nonnull
   public V func_148754_a(int p_148754_1_) {
      V v = (V)super.func_148754_a(p_148754_1_);
      return (V)(v == null ? this.field_148761_e : v);
   }

   @Nonnull
   public V func_186801_a(Random p_186801_1_) {
      V v = (V)super.func_186801_a(p_186801_1_);
      return (V)(v == null ? this.field_148761_e : v);
   }
}
