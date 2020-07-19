package net.minecraft.util.text;

import java.util.function.Function;
import java.util.function.Supplier;

public class TextComponentKeybind extends TextComponentBase {
   public static Function<String, Supplier<String>> field_193637_b = (p_193635_0_) -> {
      return () -> {
         return p_193634_0_;
      };
   };
   private final String field_193638_c;
   private Supplier<String> field_193639_d;

   public TextComponentKeybind(String p_i47521_1_) {
      this.field_193638_c = p_i47521_1_;
   }

   public String func_150261_e() {
      if (this.field_193639_d == null) {
         this.field_193639_d = (Supplier)field_193637_b.apply(this.field_193638_c);
      }

      return this.field_193639_d.get();
   }

   public TextComponentKeybind func_150259_f() {
      TextComponentKeybind textcomponentkeybind = new TextComponentKeybind(this.field_193638_c);
      textcomponentkeybind.func_150255_a(this.func_150256_b().func_150232_l());

      for(ITextComponent itextcomponent : this.func_150253_a()) {
         textcomponentkeybind.func_150257_a(itextcomponent.func_150259_f());
      }

      return textcomponentkeybind;
   }

   public boolean equals(Object p_equals_1_) {
      if (this == p_equals_1_) {
         return true;
      } else if (!(p_equals_1_ instanceof TextComponentKeybind)) {
         return false;
      } else {
         TextComponentKeybind textcomponentkeybind = (TextComponentKeybind)p_equals_1_;
         return this.field_193638_c.equals(textcomponentkeybind.field_193638_c) && super.equals(p_equals_1_);
      }
   }

   public String toString() {
      return "KeybindComponent{keybind='" + this.field_193638_c + '\'' + ", siblings=" + this.field_150264_a + ", style=" + this.func_150256_b() + '}';
   }

   public String func_193633_h() {
      return this.field_193638_c;
   }
}
