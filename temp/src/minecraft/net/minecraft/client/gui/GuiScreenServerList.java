package net.minecraft.client.gui;

import java.io.IOException;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.resources.I18n;
import org.lwjgl.input.Keyboard;

public class GuiScreenServerList extends GuiScreen {
   private final GuiScreen field_146303_a;
   private final ServerData field_146301_f;
   private GuiTextField field_146302_g;

   public GuiScreenServerList(GuiScreen p_i1031_1_, ServerData p_i1031_2_) {
      this.field_146303_a = p_i1031_1_;
      this.field_146301_f = p_i1031_2_;
   }

   public void func_73876_c() {
      this.field_146302_g.func_146178_a();
   }

   public void func_73866_w_() {
      Keyboard.enableRepeatEvents(true);
      this.field_146292_n.clear();
      this.field_146292_n.add(new GuiButton(0, this.field_146294_l / 2 - 100, this.field_146295_m / 4 + 96 + 12, I18n.func_135052_a("selectServer.select")));
      this.field_146292_n.add(new GuiButton(1, this.field_146294_l / 2 - 100, this.field_146295_m / 4 + 120 + 12, I18n.func_135052_a("gui.cancel")));
      this.field_146302_g = new GuiTextField(2, this.field_146289_q, this.field_146294_l / 2 - 100, 116, 200, 20);
      this.field_146302_g.func_146203_f(128);
      this.field_146302_g.func_146195_b(true);
      this.field_146302_g.func_146180_a(this.field_146297_k.field_71474_y.field_74332_R);
      (this.field_146292_n.get(0)).field_146124_l = !this.field_146302_g.func_146179_b().isEmpty() && this.field_146302_g.func_146179_b().split(":").length > 0;
   }

   public void func_146281_b() {
      Keyboard.enableRepeatEvents(false);
      this.field_146297_k.field_71474_y.field_74332_R = this.field_146302_g.func_146179_b();
      this.field_146297_k.field_71474_y.func_74303_b();
   }

   protected void func_146284_a(GuiButton p_146284_1_) throws IOException {
      if (p_146284_1_.field_146124_l) {
         if (p_146284_1_.field_146127_k == 1) {
            this.field_146303_a.func_73878_a(false, 0);
         } else if (p_146284_1_.field_146127_k == 0) {
            this.field_146301_f.field_78845_b = this.field_146302_g.func_146179_b();
            this.field_146303_a.func_73878_a(true, 0);
         }

      }
   }

   protected void func_73869_a(char p_73869_1_, int p_73869_2_) throws IOException {
      if (this.field_146302_g.func_146201_a(p_73869_1_, p_73869_2_)) {
         (this.field_146292_n.get(0)).field_146124_l = !this.field_146302_g.func_146179_b().isEmpty() && this.field_146302_g.func_146179_b().split(":").length > 0;
      } else if (p_73869_2_ == 28 || p_73869_2_ == 156) {
         this.func_146284_a(this.field_146292_n.get(0));
      }

   }

   protected void func_73864_a(int p_73864_1_, int p_73864_2_, int p_73864_3_) throws IOException {
      super.func_73864_a(p_73864_1_, p_73864_2_, p_73864_3_);
      this.field_146302_g.func_146192_a(p_73864_1_, p_73864_2_, p_73864_3_);
   }

   public void func_73863_a(int p_73863_1_, int p_73863_2_, float p_73863_3_) {
      this.func_146276_q_();
      this.func_73732_a(this.field_146289_q, I18n.func_135052_a("selectServer.direct"), this.field_146294_l / 2, 20, 16777215);
      this.func_73731_b(this.field_146289_q, I18n.func_135052_a("addServer.enterIp"), this.field_146294_l / 2 - 100, 100, 10526880);
      this.field_146302_g.func_146194_f();
      super.func_73863_a(p_73863_1_, p_73863_2_, p_73863_3_);
   }
}
