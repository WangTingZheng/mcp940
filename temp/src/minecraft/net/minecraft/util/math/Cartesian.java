package net.minecraft.util.math;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.UnmodifiableIterator;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import javax.annotation.Nullable;

public class Cartesian {
   public static <T> Iterable<T[]> func_179318_a(Class<T> p_179318_0_, Iterable<? extends Iterable<? extends T>> p_179318_1_) {
      return new Cartesian.Product<T[]>(p_179318_0_, (Iterable[])func_179322_b(Iterable.class, p_179318_1_));
   }

   public static <T> Iterable<List<T>> func_179321_a(Iterable<? extends Iterable<? extends T>> p_179321_0_) {
      return func_179323_b(func_179318_a(Object.class, p_179321_0_));
   }

   private static <T> Iterable<List<T>> func_179323_b(Iterable<Object[]> p_179323_0_) {
      return Iterables.transform(p_179323_0_, new Cartesian.GetList());
   }

   private static <T> T[] func_179322_b(Class<? super T> p_179322_0_, Iterable<? extends T> p_179322_1_) {
      List<T> list = Lists.<T>newArrayList();

      for(T t : p_179322_1_) {
         list.add(t);
      }

      return (T[])(list.toArray(func_179319_b(p_179322_0_, list.size())));
   }

   private static <T> T[] func_179319_b(Class<? super T> p_179319_0_, int p_179319_1_) {
      return (T[])((Object[])Array.newInstance(p_179319_0_, p_179319_1_));
   }

   static class GetList<T> implements Function<Object[], List<T>> {
      private GetList() {
      }

      public List<T> apply(@Nullable Object[] p_apply_1_) {
         return Arrays.<T>asList(p_apply_1_);
      }
   }

   static class Product<T> implements Iterable<T[]> {
      private final Class<T> field_179429_a;
      private final Iterable<? extends T>[] field_179428_b;

      private Product(Class<T> p_i46020_1_, Iterable<? extends T>[] p_i46020_2_) {
         this.field_179429_a = p_i46020_1_;
         this.field_179428_b = p_i46020_2_;
      }

      public Iterator<T[]> iterator() {
         return (Iterator<T[]>)(this.field_179428_b.length <= 0 ? Collections.singletonList(Cartesian.func_179319_b(this.field_179429_a, 0)).iterator() : new Cartesian.Product.ProductIterator(this.field_179429_a, this.field_179428_b));
      }

      static class ProductIterator<T> extends UnmodifiableIterator<T[]> {
         private int field_179426_a;
         private final Iterable<? extends T>[] field_179424_b;
         private final Iterator<? extends T>[] field_179425_c;
         private final T[] field_179423_d;

         private ProductIterator(Class<T> p_i46018_1_, Iterable<? extends T>[] p_i46018_2_) {
            this.field_179426_a = -2;
            this.field_179424_b = p_i46018_2_;
            this.field_179425_c = (Iterator[])Cartesian.func_179319_b(Iterator.class, this.field_179424_b.length);

            for(int i = 0; i < this.field_179424_b.length; ++i) {
               this.field_179425_c[i] = p_i46018_2_[i].iterator();
            }

            this.field_179423_d = (T[])Cartesian.func_179319_b(p_i46018_1_, this.field_179425_c.length);
         }

         private void func_179422_b() {
            this.field_179426_a = -1;
            Arrays.fill(this.field_179425_c, (Object)null);
            Arrays.fill(this.field_179423_d, (Object)null);
         }

         public boolean hasNext() {
            if (this.field_179426_a == -2) {
               this.field_179426_a = 0;

               for(Iterator<? extends T> iterator1 : this.field_179425_c) {
                  if (!iterator1.hasNext()) {
                     this.func_179422_b();
                     break;
                  }
               }

               return true;
            } else {
               if (this.field_179426_a >= this.field_179425_c.length) {
                  for(this.field_179426_a = this.field_179425_c.length - 1; this.field_179426_a >= 0; --this.field_179426_a) {
                     Iterator<? extends T> iterator = this.field_179425_c[this.field_179426_a];
                     if (iterator.hasNext()) {
                        break;
                     }

                     if (this.field_179426_a == 0) {
                        this.func_179422_b();
                        break;
                     }

                     iterator = this.field_179424_b[this.field_179426_a].iterator();
                     this.field_179425_c[this.field_179426_a] = iterator;
                     if (!iterator.hasNext()) {
                        this.func_179422_b();
                        break;
                     }
                  }
               }

               return this.field_179426_a >= 0;
            }
         }

         public T[] next() {
            if (!this.hasNext()) {
               throw new NoSuchElementException();
            } else {
               while(this.field_179426_a < this.field_179425_c.length) {
                  this.field_179423_d[this.field_179426_a] = this.field_179425_c[this.field_179426_a].next();
                  ++this.field_179426_a;
               }

               return (T[])((Object[])this.field_179423_d.clone());
            }
         }
      }
   }
}
