package net.minecraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;

public class GuiListButton extends GuiButton {
   private boolean field_175216_o;
   private final String field_175215_p;
   private final GuiPageButtonList.GuiResponder field_175214_q;

   public GuiListButton(GuiPageButtonList.GuiResponder p_i45539_1_, int p_i45539_2_, int p_i45539_3_, int p_i45539_4_, String p_i45539_5_, boolean p_i45539_6_) {
      super(p_i45539_2_, p_i45539_3_, p_i45539_4_, 150, 20, "");
      this.field_175215_p = p_i45539_5_;
      this.field_175216_o = p_i45539_6_;
      this.field_146126_j = this.func_175213_c();
      this.field_175214_q = p_i45539_1_;
   }

   private String func_175213_c() {
      return I18n.func_135052_a(this.field_175215_p) + ": " + I18n.func_135052_a(this.field_175216_o ? "gui.yes" : "gui.no");
   }

   public void func_175212_b(boolean p_175212_1_) {
      this.field_175216_o = p_175212_1_;
      this.field_146126_j = this.func_175213_c();
      this.field_175214_q.func_175321_a(this.field_146127_k, p_175212_1_);
   }

   public boolean func_146116_c(Minecraft p_146116_1_, int p_146116_2_, int p_146116_3_) {
      if (super.func_146116_c(p_146116_1_, p_146116_2_, p_146116_3_)) {
         this.field_175216_o = !this.field_175216_o;
         this.field_146126_j = this.func_175213_c();
         this.field_175214_q.func_175321_a(this.field_146127_k, this.field_175216_o);
         return true;
      } else {
         return false;
      }
   }
}
