package net.minecraft.util.text;

public class TextComponentSelector extends TextComponentBase {
   private final String field_179993_b;

   public TextComponentSelector(String p_i45996_1_) {
      this.field_179993_b = p_i45996_1_;
   }

   public String func_179992_g() {
      return this.field_179993_b;
   }

   public String func_150261_e() {
      return this.field_179993_b;
   }

   public TextComponentSelector func_150259_f() {
      TextComponentSelector textcomponentselector = new TextComponentSelector(this.field_179993_b);
      textcomponentselector.func_150255_a(this.func_150256_b().func_150232_l());

      for(ITextComponent itextcomponent : this.func_150253_a()) {
         textcomponentselector.func_150257_a(itextcomponent.func_150259_f());
      }

      return textcomponentselector;
   }

   public boolean equals(Object p_equals_1_) {
      if (this == p_equals_1_) {
         return true;
      } else if (!(p_equals_1_ instanceof TextComponentSelector)) {
         return false;
      } else {
         TextComponentSelector textcomponentselector = (TextComponentSelector)p_equals_1_;
         return this.field_179993_b.equals(textcomponentselector.field_179993_b) && super.equals(p_equals_1_);
      }
   }

   public String toString() {
      return "SelectorComponent{pattern='" + this.field_179993_b + '\'' + ", siblings=" + this.field_150264_a + ", style=" + this.func_150256_b() + '}';
   }
}
