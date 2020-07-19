package net.minecraft.util.text;

import com.google.common.base.Function;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nullable;

public abstract class TextComponentBase implements ITextComponent {
   protected List<ITextComponent> field_150264_a = Lists.<ITextComponent>newArrayList();
   private Style field_150263_b;

   public ITextComponent func_150257_a(ITextComponent p_150257_1_) {
      p_150257_1_.func_150256_b().func_150221_a(this.func_150256_b());
      this.field_150264_a.add(p_150257_1_);
      return this;
   }

   public List<ITextComponent> func_150253_a() {
      return this.field_150264_a;
   }

   public ITextComponent func_150258_a(String p_150258_1_) {
      return this.func_150257_a(new TextComponentString(p_150258_1_));
   }

   public ITextComponent func_150255_a(Style p_150255_1_) {
      this.field_150263_b = p_150255_1_;

      for(ITextComponent itextcomponent : this.field_150264_a) {
         itextcomponent.func_150256_b().func_150221_a(this.func_150256_b());
      }

      return this;
   }

   public Style func_150256_b() {
      if (this.field_150263_b == null) {
         this.field_150263_b = new Style();

         for(ITextComponent itextcomponent : this.field_150264_a) {
            itextcomponent.func_150256_b().func_150221_a(this.field_150263_b);
         }
      }

      return this.field_150263_b;
   }

   public Iterator<ITextComponent> iterator() {
      return Iterators.<ITextComponent>concat(Iterators.forArray(this), func_150262_a(this.field_150264_a));
   }

   public final String func_150260_c() {
      StringBuilder stringbuilder = new StringBuilder();

      for(ITextComponent itextcomponent : this) {
         stringbuilder.append(itextcomponent.func_150261_e());
      }

      return stringbuilder.toString();
   }

   public final String func_150254_d() {
      StringBuilder stringbuilder = new StringBuilder();

      for(ITextComponent itextcomponent : this) {
         String s = itextcomponent.func_150261_e();
         if (!s.isEmpty()) {
            stringbuilder.append(itextcomponent.func_150256_b().func_150218_j());
            stringbuilder.append(s);
            stringbuilder.append((Object)TextFormatting.RESET);
         }
      }

      return stringbuilder.toString();
   }

   public static Iterator<ITextComponent> func_150262_a(Iterable<ITextComponent> p_150262_0_) {
      Iterator<ITextComponent> iterator = Iterators.concat(Iterators.transform(p_150262_0_.iterator(), new Function<ITextComponent, Iterator<ITextComponent>>() {
         public Iterator<ITextComponent> apply(@Nullable ITextComponent p_apply_1_) {
            return p_apply_1_.iterator();
         }
      }));
      iterator = Iterators.transform(iterator, new Function<ITextComponent, ITextComponent>() {
         public ITextComponent apply(@Nullable ITextComponent p_apply_1_) {
            ITextComponent itextcomponent = p_apply_1_.func_150259_f();
            itextcomponent.func_150255_a(itextcomponent.func_150256_b().func_150206_m());
            return itextcomponent;
         }
      });
      return iterator;
   }

   public boolean equals(Object p_equals_1_) {
      if (this == p_equals_1_) {
         return true;
      } else if (!(p_equals_1_ instanceof TextComponentBase)) {
         return false;
      } else {
         TextComponentBase textcomponentbase = (TextComponentBase)p_equals_1_;
         return this.field_150264_a.equals(textcomponentbase.field_150264_a) && this.func_150256_b().equals(textcomponentbase.func_150256_b());
      }
   }

   public int hashCode() {
      return 31 * this.field_150263_b.hashCode() + this.field_150264_a.hashCode();
   }

   public String toString() {
      return "BaseComponent{style=" + this.field_150263_b + ", siblings=" + this.field_150264_a + '}';
   }
}
