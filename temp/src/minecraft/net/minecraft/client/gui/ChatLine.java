package net.minecraft.client.gui;

import net.minecraft.util.text.ITextComponent;

public class ChatLine {
   private final int field_74543_a;
   private final ITextComponent field_74541_b;
   private final int field_74542_c;

   public ChatLine(int p_i45000_1_, ITextComponent p_i45000_2_, int p_i45000_3_) {
      this.field_74541_b = p_i45000_2_;
      this.field_74543_a = p_i45000_1_;
      this.field_74542_c = p_i45000_3_;
   }

   public ITextComponent func_151461_a() {
      return this.field_74541_b;
   }

   public int func_74540_b() {
      return this.field_74543_a;
   }

   public int func_74539_c() {
      return this.field_74542_c;
   }
}
