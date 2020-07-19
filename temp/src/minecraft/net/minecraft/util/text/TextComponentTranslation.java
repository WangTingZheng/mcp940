package net.minecraft.util.text;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import java.util.Arrays;
import java.util.IllegalFormatException;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.util.text.translation.I18n;

public class TextComponentTranslation extends TextComponentBase {
   private final String field_150276_d;
   private final Object[] field_150277_e;
   private final Object field_150274_f = new Object();
   private long field_150275_g = -1L;
   @VisibleForTesting
   List<ITextComponent> field_150278_b = Lists.<ITextComponent>newArrayList();
   public static final Pattern field_150279_c = Pattern.compile("%(?:(\\d+)\\$)?([A-Za-z%]|$)");

   public TextComponentTranslation(String p_i45160_1_, Object... p_i45160_2_) {
      this.field_150276_d = p_i45160_1_;
      this.field_150277_e = p_i45160_2_;

      for(Object object : p_i45160_2_) {
         if (object instanceof ITextComponent) {
            ((ITextComponent)object).func_150256_b().func_150221_a(this.func_150256_b());
         }
      }

   }

   @VisibleForTesting
   synchronized void func_150270_g() {
      synchronized(this.field_150274_f) {
         long i = I18n.func_150827_a();
         if (i == this.field_150275_g) {
            return;
         }

         this.field_150275_g = i;
         this.field_150278_b.clear();
      }

      try {
         this.func_150269_b(I18n.func_74838_a(this.field_150276_d));
      } catch (TextComponentTranslationFormatException textcomponenttranslationformatexception) {
         this.field_150278_b.clear();

         try {
            this.func_150269_b(I18n.func_150826_b(this.field_150276_d));
         } catch (TextComponentTranslationFormatException var5) {
            throw textcomponenttranslationformatexception;
         }
      }

   }

   protected void func_150269_b(String p_150269_1_) {
      boolean flag = false;
      Matcher matcher = field_150279_c.matcher(p_150269_1_);
      int i = 0;
      int j = 0;

      try {
         int l;
         for(; matcher.find(j); j = l) {
            int k = matcher.start();
            l = matcher.end();
            if (k > j) {
               TextComponentString textcomponentstring = new TextComponentString(String.format(p_150269_1_.substring(j, k)));
               textcomponentstring.func_150256_b().func_150221_a(this.func_150256_b());
               this.field_150278_b.add(textcomponentstring);
            }

            String s2 = matcher.group(2);
            String s = p_150269_1_.substring(k, l);
            if ("%".equals(s2) && "%%".equals(s)) {
               TextComponentString textcomponentstring2 = new TextComponentString("%");
               textcomponentstring2.func_150256_b().func_150221_a(this.func_150256_b());
               this.field_150278_b.add(textcomponentstring2);
            } else {
               if (!"s".equals(s2)) {
                  throw new TextComponentTranslationFormatException(this, "Unsupported format: '" + s + "'");
               }

               String s1 = matcher.group(1);
               int i1 = s1 != null ? Integer.parseInt(s1) - 1 : i++;
               if (i1 < this.field_150277_e.length) {
                  this.field_150278_b.add(this.func_150272_a(i1));
               }
            }
         }

         if (j < p_150269_1_.length()) {
            TextComponentString textcomponentstring1 = new TextComponentString(String.format(p_150269_1_.substring(j)));
            textcomponentstring1.func_150256_b().func_150221_a(this.func_150256_b());
            this.field_150278_b.add(textcomponentstring1);
         }

      } catch (IllegalFormatException illegalformatexception) {
         throw new TextComponentTranslationFormatException(this, illegalformatexception);
      }
   }

   private ITextComponent func_150272_a(int p_150272_1_) {
      if (p_150272_1_ >= this.field_150277_e.length) {
         throw new TextComponentTranslationFormatException(this, p_150272_1_);
      } else {
         Object object = this.field_150277_e[p_150272_1_];
         ITextComponent itextcomponent;
         if (object instanceof ITextComponent) {
            itextcomponent = (ITextComponent)object;
         } else {
            itextcomponent = new TextComponentString(object == null ? "null" : object.toString());
            itextcomponent.func_150256_b().func_150221_a(this.func_150256_b());
         }

         return itextcomponent;
      }
   }

   public ITextComponent func_150255_a(Style p_150255_1_) {
      super.func_150255_a(p_150255_1_);

      for(Object object : this.field_150277_e) {
         if (object instanceof ITextComponent) {
            ((ITextComponent)object).func_150256_b().func_150221_a(this.func_150256_b());
         }
      }

      if (this.field_150275_g > -1L) {
         for(ITextComponent itextcomponent : this.field_150278_b) {
            itextcomponent.func_150256_b().func_150221_a(p_150255_1_);
         }
      }

      return this;
   }

   public Iterator<ITextComponent> iterator() {
      this.func_150270_g();
      return Iterators.<ITextComponent>concat(func_150262_a(this.field_150278_b), func_150262_a(this.field_150264_a));
   }

   public String func_150261_e() {
      this.func_150270_g();
      StringBuilder stringbuilder = new StringBuilder();

      for(ITextComponent itextcomponent : this.field_150278_b) {
         stringbuilder.append(itextcomponent.func_150261_e());
      }

      return stringbuilder.toString();
   }

   public TextComponentTranslation func_150259_f() {
      Object[] aobject = new Object[this.field_150277_e.length];

      for(int i = 0; i < this.field_150277_e.length; ++i) {
         if (this.field_150277_e[i] instanceof ITextComponent) {
            aobject[i] = ((ITextComponent)this.field_150277_e[i]).func_150259_f();
         } else {
            aobject[i] = this.field_150277_e[i];
         }
      }

      TextComponentTranslation textcomponenttranslation = new TextComponentTranslation(this.field_150276_d, aobject);
      textcomponenttranslation.func_150255_a(this.func_150256_b().func_150232_l());

      for(ITextComponent itextcomponent : this.func_150253_a()) {
         textcomponenttranslation.func_150257_a(itextcomponent.func_150259_f());
      }

      return textcomponenttranslation;
   }

   public boolean equals(Object p_equals_1_) {
      if (this == p_equals_1_) {
         return true;
      } else if (!(p_equals_1_ instanceof TextComponentTranslation)) {
         return false;
      } else {
         TextComponentTranslation textcomponenttranslation = (TextComponentTranslation)p_equals_1_;
         return Arrays.equals(this.field_150277_e, textcomponenttranslation.field_150277_e) && this.field_150276_d.equals(textcomponenttranslation.field_150276_d) && super.equals(p_equals_1_);
      }
   }

   public int hashCode() {
      int i = super.hashCode();
      i = 31 * i + this.field_150276_d.hashCode();
      i = 31 * i + Arrays.hashCode(this.field_150277_e);
      return i;
   }

   public String toString() {
      return "TranslatableComponent{key='" + this.field_150276_d + '\'' + ", args=" + Arrays.toString(this.field_150277_e) + ", siblings=" + this.field_150264_a + ", style=" + this.func_150256_b() + '}';
   }

   public String func_150268_i() {
      return this.field_150276_d;
   }

   public Object[] func_150271_j() {
      return this.field_150277_e;
   }
}
