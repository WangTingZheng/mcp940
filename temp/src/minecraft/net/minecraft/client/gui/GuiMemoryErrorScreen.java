package net.minecraft.client.gui;

import java.io.IOException;
import net.minecraft.client.resources.I18n;

public class GuiMemoryErrorScreen extends GuiScreen {
   public void func_73866_w_() {
      this.field_146292_n.clear();
      this.field_146292_n.add(new GuiOptionButton(0, this.field_146294_l / 2 - 155, this.field_146295_m / 4 + 120 + 12, I18n.func_135052_a("gui.toTitle")));
      this.field_146292_n.add(new GuiOptionButton(1, this.field_146294_l / 2 - 155 + 160, this.field_146295_m / 4 + 120 + 12, I18n.func_135052_a("menu.quit")));
   }

   protected void func_146284_a(GuiButton p_146284_1_) throws IOException {
      if (p_146284_1_.field_146127_k == 0) {
         this.field_146297_k.func_147108_a(new GuiMainMenu());
      } else if (p_146284_1_.field_146127_k == 1) {
         this.field_146297_k.func_71400_g();
      }

   }

   protected void func_73869_a(char p_73869_1_, int p_73869_2_) throws IOException {
   }

   public void func_73863_a(int p_73863_1_, int p_73863_2_, float p_73863_3_) {
      this.func_146276_q_();
      this.func_73732_a(this.field_146289_q, "Out of memory!", this.field_146294_l / 2, this.field_146295_m / 4 - 60 + 20, 16777215);
      this.func_73731_b(this.field_146289_q, "Minecraft has run out of memory.", this.field_146294_l / 2 - 140, this.field_146295_m / 4 - 60 + 60 + 0, 10526880);
      this.func_73731_b(this.field_146289_q, "This could be caused by a bug in the game or by the", this.field_146294_l / 2 - 140, this.field_146295_m / 4 - 60 + 60 + 18, 10526880);
      this.func_73731_b(this.field_146289_q, "Java Virtual Machine not being allocated enough", this.field_146294_l / 2 - 140, this.field_146295_m / 4 - 60 + 60 + 27, 10526880);
      this.func_73731_b(this.field_146289_q, "memory.", this.field_146294_l / 2 - 140, this.field_146295_m / 4 - 60 + 60 + 36, 10526880);
      this.func_73731_b(this.field_146289_q, "To prevent level corruption, the current game has quit.", this.field_146294_l / 2 - 140, this.field_146295_m / 4 - 60 + 60 + 54, 10526880);
      this.func_73731_b(this.field_146289_q, "We've tried to free up enough memory to let you go back to", this.field_146294_l / 2 - 140, this.field_146295_m / 4 - 60 + 60 + 63, 10526880);
      this.func_73731_b(this.field_146289_q, "the main menu and back to playing, but this may not have worked.", this.field_146294_l / 2 - 140, this.field_146295_m / 4 - 60 + 60 + 72, 10526880);
      this.func_73731_b(this.field_146289_q, "Please restart the game if you see this message again.", this.field_146294_l / 2 - 140, this.field_146295_m / 4 - 60 + 60 + 81, 10526880);
      super.func_73863_a(p_73863_1_, p_73863_2_, p_73863_3_);
   }
}
