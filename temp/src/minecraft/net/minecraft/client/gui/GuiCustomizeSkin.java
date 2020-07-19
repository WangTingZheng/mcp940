package net.minecraft.client.gui;

import java.io.IOException;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.player.EnumPlayerModelParts;

public class GuiCustomizeSkin extends GuiScreen {
   private final GuiScreen field_175361_a;
   private String field_175360_f;

   public GuiCustomizeSkin(GuiScreen p_i45516_1_) {
      this.field_175361_a = p_i45516_1_;
   }

   public void func_73866_w_() {
      int i = 0;
      this.field_175360_f = I18n.func_135052_a("options.skinCustomisation.title");

      for(EnumPlayerModelParts enumplayermodelparts : EnumPlayerModelParts.values()) {
         this.field_146292_n.add(new GuiCustomizeSkin.ButtonPart(enumplayermodelparts.func_179328_b(), this.field_146294_l / 2 - 155 + i % 2 * 160, this.field_146295_m / 6 + 24 * (i >> 1), 150, 20, enumplayermodelparts));
         ++i;
      }

      this.field_146292_n.add(new GuiOptionButton(199, this.field_146294_l / 2 - 155 + i % 2 * 160, this.field_146295_m / 6 + 24 * (i >> 1), GameSettings.Options.MAIN_HAND, this.field_146297_k.field_71474_y.func_74297_c(GameSettings.Options.MAIN_HAND)));
      ++i;
      if (i % 2 == 1) {
         ++i;
      }

      this.field_146292_n.add(new GuiButton(200, this.field_146294_l / 2 - 100, this.field_146295_m / 6 + 24 * (i >> 1), I18n.func_135052_a("gui.done")));
   }

   protected void func_73869_a(char p_73869_1_, int p_73869_2_) throws IOException {
      if (p_73869_2_ == 1) {
         this.field_146297_k.field_71474_y.func_74303_b();
      }

      super.func_73869_a(p_73869_1_, p_73869_2_);
   }

   protected void func_146284_a(GuiButton p_146284_1_) throws IOException {
      if (p_146284_1_.field_146124_l) {
         if (p_146284_1_.field_146127_k == 200) {
            this.field_146297_k.field_71474_y.func_74303_b();
            this.field_146297_k.func_147108_a(this.field_175361_a);
         } else if (p_146284_1_.field_146127_k == 199) {
            this.field_146297_k.field_71474_y.func_74306_a(GameSettings.Options.MAIN_HAND, 1);
            p_146284_1_.field_146126_j = this.field_146297_k.field_71474_y.func_74297_c(GameSettings.Options.MAIN_HAND);
            this.field_146297_k.field_71474_y.func_82879_c();
         } else if (p_146284_1_ instanceof GuiCustomizeSkin.ButtonPart) {
            EnumPlayerModelParts enumplayermodelparts = ((GuiCustomizeSkin.ButtonPart)p_146284_1_).field_175234_p;
            this.field_146297_k.field_71474_y.func_178877_a(enumplayermodelparts);
            p_146284_1_.field_146126_j = this.func_175358_a(enumplayermodelparts);
         }

      }
   }

   public void func_73863_a(int p_73863_1_, int p_73863_2_, float p_73863_3_) {
      this.func_146276_q_();
      this.func_73732_a(this.field_146289_q, this.field_175360_f, this.field_146294_l / 2, 20, 16777215);
      super.func_73863_a(p_73863_1_, p_73863_2_, p_73863_3_);
   }

   private String func_175358_a(EnumPlayerModelParts p_175358_1_) {
      String s;
      if (this.field_146297_k.field_71474_y.func_178876_d().contains(p_175358_1_)) {
         s = I18n.func_135052_a("options.on");
      } else {
         s = I18n.func_135052_a("options.off");
      }

      return p_175358_1_.func_179326_d().func_150254_d() + ": " + s;
   }

   class ButtonPart extends GuiButton {
      private final EnumPlayerModelParts field_175234_p;

      private ButtonPart(int p_i45514_2_, int p_i45514_3_, int p_i45514_4_, int p_i45514_5_, int p_i45514_6_, EnumPlayerModelParts p_i45514_7_) {
         super(p_i45514_2_, p_i45514_3_, p_i45514_4_, p_i45514_5_, p_i45514_6_, GuiCustomizeSkin.this.func_175358_a(p_i45514_7_));
         this.field_175234_p = p_i45514_7_;
      }
   }
}
