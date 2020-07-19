package net.minecraft.client.gui;

import java.io.IOException;
import java.util.List;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.ITextComponent;

public class GuiDisconnected extends GuiScreen {
   private final String field_146306_a;
   private final ITextComponent field_146304_f;
   private List<String> field_146305_g;
   private final GuiScreen field_146307_h;
   private int field_175353_i;

   public GuiDisconnected(GuiScreen p_i45020_1_, String p_i45020_2_, ITextComponent p_i45020_3_) {
      this.field_146307_h = p_i45020_1_;
      this.field_146306_a = I18n.func_135052_a(p_i45020_2_);
      this.field_146304_f = p_i45020_3_;
   }

   protected void func_73869_a(char p_73869_1_, int p_73869_2_) throws IOException {
   }

   public void func_73866_w_() {
      this.field_146292_n.clear();
      this.field_146305_g = this.field_146289_q.func_78271_c(this.field_146304_f.func_150254_d(), this.field_146294_l - 50);
      this.field_175353_i = this.field_146305_g.size() * this.field_146289_q.field_78288_b;
      this.field_146292_n.add(new GuiButton(0, this.field_146294_l / 2 - 100, Math.min(this.field_146295_m / 2 + this.field_175353_i / 2 + this.field_146289_q.field_78288_b, this.field_146295_m - 30), I18n.func_135052_a("gui.toMenu")));
   }

   protected void func_146284_a(GuiButton p_146284_1_) throws IOException {
      if (p_146284_1_.field_146127_k == 0) {
         this.field_146297_k.func_147108_a(this.field_146307_h);
      }

   }

   public void func_73863_a(int p_73863_1_, int p_73863_2_, float p_73863_3_) {
      this.func_146276_q_();
      this.func_73732_a(this.field_146289_q, this.field_146306_a, this.field_146294_l / 2, this.field_146295_m / 2 - this.field_175353_i / 2 - this.field_146289_q.field_78288_b * 2, 11184810);
      int i = this.field_146295_m / 2 - this.field_175353_i / 2;
      if (this.field_146305_g != null) {
         for(String s : this.field_146305_g) {
            this.func_73732_a(this.field_146289_q, s, this.field_146294_l / 2, i, 16777215);
            i += this.field_146289_q.field_78288_b;
         }
      }

      super.func_73863_a(p_73863_1_, p_73863_2_, p_73863_3_);
   }
}
