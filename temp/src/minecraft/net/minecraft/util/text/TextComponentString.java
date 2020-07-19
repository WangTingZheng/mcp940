package net.minecraft.util.text;

public class TextComponentString extends TextComponentBase {
   private final String field_150267_b;

   public TextComponentString(String p_i45159_1_) {
      this.field_150267_b = p_i45159_1_;
   }

   public String func_150265_g() {
      return this.field_150267_b;
   }

   public String func_150261_e() {
      return this.field_150267_b;
   }

   public TextComponentString func_150259_f() {
      TextComponentString textcomponentstring = new TextComponentString(this.field_150267_b);
      textcomponentstring.func_150255_a(this.func_150256_b().func_150232_l());

      for(ITextComponent itextcomponent : this.func_150253_a()) {
         textcomponentstring.func_150257_a(itextcomponent.func_150259_f());
      }

      return textcomponentstring;
   }

   public boolean equals(Object p_equals_1_) {
      if (this == p_equals_1_) {
         return true;
      } else if (!(p_equals_1_ instanceof TextComponentString)) {
         return false;
      } else {
         TextComponentString textcomponentstring = (TextComponentString)p_equals_1_;
         return this.field_150267_b.equals(textcomponentstring.func_150265_g()) && super.equals(p_equals_1_);
      }
   }

   public String toString() {
      return "TextComponent{text='" + this.field_150267_b + '\'' + ", siblings=" + this.field_150264_a + ", style=" + this.func_150256_b() + '}';
   }
}
