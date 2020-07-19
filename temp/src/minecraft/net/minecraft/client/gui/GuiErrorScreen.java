package net.minecraft.client.gui;

import java.io.IOException;
import net.minecraft.client.resources.I18n;

public class GuiErrorScreen extends GuiScreen {
   private final String field_146313_a;
   private final String field_146312_f;

   public GuiErrorScreen(String p_i46319_1_, String p_i46319_2_) {
      this.field_146313_a = p_i46319_1_;
      this.field_146312_f = p_i46319_2_;
   }

   public void func_73866_w_() {
      super.func_73866_w_();
      this.field_146292_n.add(new GuiButton(0, this.field_146294_l / 2 - 100, 140, I18n.func_135052_a("gui.cancel")));
   }

   public void func_73863_a(int p_73863_1_, int p_73863_2_, float p_73863_3_) {
      this.func_73733_a(0, 0, this.field_146294_l, this.field_146295_m, -12574688, -11530224);
      this.func_73732_a(this.field_146289_q, this.field_146313_a, this.field_146294_l / 2, 90, 16777215);
      this.func_73732_a(this.field_146289_q, this.field_146312_f, this.field_146294_l / 2, 110, 16777215);
      super.func_73863_a(p_73863_1_, p_73863_2_, p_73863_3_);
   }

   protected void func_73869_a(char p_73869_1_, int p_73869_2_) throws IOException {
   }

   protected void func_146284_a(GuiButton p_146284_1_) throws IOException {
      this.field_146297_k.func_147108_a((GuiScreen)null);
   }
}
