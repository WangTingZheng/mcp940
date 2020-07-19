package net.minecraft.client.gui;

import java.io.IOException;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.GameType;

public class GuiShareToLan extends GuiScreen {
   private final GuiScreen field_146598_a;
   private GuiButton field_146596_f;
   private GuiButton field_146597_g;
   private String field_146599_h = "survival";
   private boolean field_146600_i;

   public GuiShareToLan(GuiScreen p_i1055_1_) {
      this.field_146598_a = p_i1055_1_;
   }

   public void func_73866_w_() {
      this.field_146292_n.clear();
      this.field_146292_n.add(new GuiButton(101, this.field_146294_l / 2 - 155, this.field_146295_m - 28, 150, 20, I18n.func_135052_a("lanServer.start")));
      this.field_146292_n.add(new GuiButton(102, this.field_146294_l / 2 + 5, this.field_146295_m - 28, 150, 20, I18n.func_135052_a("gui.cancel")));
      this.field_146597_g = this.func_189646_b(new GuiButton(104, this.field_146294_l / 2 - 155, 100, 150, 20, I18n.func_135052_a("selectWorld.gameMode")));
      this.field_146596_f = this.func_189646_b(new GuiButton(103, this.field_146294_l / 2 + 5, 100, 150, 20, I18n.func_135052_a("selectWorld.allowCommands")));
      this.func_146595_g();
   }

   private void func_146595_g() {
      this.field_146597_g.field_146126_j = I18n.func_135052_a("selectWorld.gameMode") + ": " + I18n.func_135052_a("selectWorld.gameMode." + this.field_146599_h);
      this.field_146596_f.field_146126_j = I18n.func_135052_a("selectWorld.allowCommands") + " ";
      if (this.field_146600_i) {
         this.field_146596_f.field_146126_j = this.field_146596_f.field_146126_j + I18n.func_135052_a("options.on");
      } else {
         this.field_146596_f.field_146126_j = this.field_146596_f.field_146126_j + I18n.func_135052_a("options.off");
      }

   }

   protected void func_146284_a(GuiButton p_146284_1_) throws IOException {
      if (p_146284_1_.field_146127_k == 102) {
         this.field_146297_k.func_147108_a(this.field_146598_a);
      } else if (p_146284_1_.field_146127_k == 104) {
         if ("spectator".equals(this.field_146599_h)) {
            this.field_146599_h = "creative";
         } else if ("creative".equals(this.field_146599_h)) {
            this.field_146599_h = "adventure";
         } else if ("adventure".equals(this.field_146599_h)) {
            this.field_146599_h = "survival";
         } else {
            this.field_146599_h = "spectator";
         }

         this.func_146595_g();
      } else if (p_146284_1_.field_146127_k == 103) {
         this.field_146600_i = !this.field_146600_i;
         this.func_146595_g();
      } else if (p_146284_1_.field_146127_k == 101) {
         this.field_146297_k.func_147108_a((GuiScreen)null);
         String s = this.field_146297_k.func_71401_C().func_71206_a(GameType.func_77142_a(this.field_146599_h), this.field_146600_i);
         ITextComponent itextcomponent;
         if (s != null) {
            itextcomponent = new TextComponentTranslation("commands.publish.started", new Object[]{s});
         } else {
            itextcomponent = new TextComponentString("commands.publish.failed");
         }

         this.field_146297_k.field_71456_v.func_146158_b().func_146227_a(itextcomponent);
      }

   }

   public void func_73863_a(int p_73863_1_, int p_73863_2_, float p_73863_3_) {
      this.func_146276_q_();
      this.func_73732_a(this.field_146289_q, I18n.func_135052_a("lanServer.title"), this.field_146294_l / 2, 50, 16777215);
      this.func_73732_a(this.field_146289_q, I18n.func_135052_a("lanServer.otherPlayers"), this.field_146294_l / 2, 82, 16777215);
      super.func_73863_a(p_73863_1_, p_73863_2_, p_73863_3_);
   }
}
