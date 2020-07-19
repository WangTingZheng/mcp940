package net.minecraft.util;

import com.google.common.base.Predicates;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nullable;

public class ObjectIntIdentityMap<T> implements IObjectIntIterable<T> {
   private final IdentityHashMap<T, Integer> field_148749_a;
   private final List<T> field_148748_b;

   public ObjectIntIdentityMap() {
      this(512);
   }

   public ObjectIntIdentityMap(int p_i46984_1_) {
      this.field_148748_b = Lists.<T>newArrayListWithExpectedSize(p_i46984_1_);
      this.field_148749_a = new IdentityHashMap<T, Integer>(p_i46984_1_);
   }

   public void func_148746_a(T p_148746_1_, int p_148746_2_) {
      this.field_148749_a.put(p_148746_1_, Integer.valueOf(p_148746_2_));

      while(this.field_148748_b.size() <= p_148746_2_) {
         this.field_148748_b.add((Object)null);
      }

      this.field_148748_b.set(p_148746_2_, p_148746_1_);
   }

   public int func_148747_b(T p_148747_1_) {
      Integer integer = this.field_148749_a.get(p_148747_1_);
      return integer == null ? -1 : integer.intValue();
   }

   @Nullable
   public final T func_148745_a(int p_148745_1_) {
      return (T)(p_148745_1_ >= 0 && p_148745_1_ < this.field_148748_b.size() ? this.field_148748_b.get(p_148745_1_) : null);
   }

   public Iterator<T> iterator() {
      return Iterators.filter(this.field_148748_b.iterator(), Predicates.notNull());
   }

   public int func_186804_a() {
      return this.field_148749_a.size();
   }
}
