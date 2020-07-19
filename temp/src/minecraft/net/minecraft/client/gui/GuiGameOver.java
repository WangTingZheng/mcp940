package net.minecraft.client.gui;

import java.io.IOException;
import javax.annotation.Nullable;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;

public class GuiGameOver extends GuiScreen {
   private int field_146347_a;
   private final ITextComponent field_184871_f;

   public GuiGameOver(@Nullable ITextComponent p_i46598_1_) {
      this.field_184871_f = p_i46598_1_;
   }

   public void func_73866_w_() {
      this.field_146292_n.clear();
      this.field_146347_a = 0;
      if (this.field_146297_k.field_71441_e.func_72912_H().func_76093_s()) {
         this.field_146292_n.add(new GuiButton(0, this.field_146294_l / 2 - 100, this.field_146295_m / 4 + 72, I18n.func_135052_a("deathScreen.spectate")));
         this.field_146292_n.add(new GuiButton(1, this.field_146294_l / 2 - 100, this.field_146295_m / 4 + 96, I18n.func_135052_a("deathScreen." + (this.field_146297_k.func_71387_A() ? "deleteWorld" : "leaveServer"))));
      } else {
         this.field_146292_n.add(new GuiButton(0, this.field_146294_l / 2 - 100, this.field_146295_m / 4 + 72, I18n.func_135052_a("deathScreen.respawn")));
         this.field_146292_n.add(new GuiButton(1, this.field_146294_l / 2 - 100, this.field_146295_m / 4 + 96, I18n.func_135052_a("deathScreen.titleScreen")));
         if (this.field_146297_k.func_110432_I() == null) {
            (this.field_146292_n.get(1)).field_146124_l = false;
         }
      }

      for(GuiButton guibutton : this.field_146292_n) {
         guibutton.field_146124_l = false;
      }

   }

   protected void func_73869_a(char p_73869_1_, int p_73869_2_) throws IOException {
   }

   protected void func_146284_a(GuiButton p_146284_1_) throws IOException {
      switch(p_146284_1_.field_146127_k) {
      case 0:
         this.field_146297_k.field_71439_g.func_71004_bE();
         this.field_146297_k.func_147108_a((GuiScreen)null);
         break;
      case 1:
         if (this.field_146297_k.field_71441_e.func_72912_H().func_76093_s()) {
            this.field_146297_k.func_147108_a(new GuiMainMenu());
         } else {
            GuiYesNo guiyesno = new GuiYesNo(this, I18n.func_135052_a("deathScreen.quit.confirm"), "", I18n.func_135052_a("deathScreen.titleScreen"), I18n.func_135052_a("deathScreen.respawn"), 0);
            this.field_146297_k.func_147108_a(guiyesno);
            guiyesno.func_146350_a(20);
         }
      }

   }

   public void func_73878_a(boolean p_73878_1_, int p_73878_2_) {
      if (p_73878_1_) {
         if (this.field_146297_k.field_71441_e != null) {
            this.field_146297_k.field_71441_e.func_72882_A();
         }

         this.field_146297_k.func_71403_a((WorldClient)null);
         this.field_146297_k.func_147108_a(new GuiMainMenu());
      } else {
         this.field_146297_k.field_71439_g.func_71004_bE();
         this.field_146297_k.func_147108_a((GuiScreen)null);
      }

   }

   public void func_73863_a(int p_73863_1_, int p_73863_2_, float p_73863_3_) {
      boolean flag = this.field_146297_k.field_71441_e.func_72912_H().func_76093_s();
      this.func_73733_a(0, 0, this.field_146294_l, this.field_146295_m, 1615855616, -1602211792);
      GlStateManager.func_179094_E();
      GlStateManager.func_179152_a(2.0F, 2.0F, 2.0F);
      this.func_73732_a(this.field_146289_q, I18n.func_135052_a(flag ? "deathScreen.title.hardcore" : "deathScreen.title"), this.field_146294_l / 2 / 2, 30, 16777215);
      GlStateManager.func_179121_F();
      if (this.field_184871_f != null) {
         this.func_73732_a(this.field_146289_q, this.field_184871_f.func_150254_d(), this.field_146294_l / 2, 85, 16777215);
      }

      this.func_73732_a(this.field_146289_q, I18n.func_135052_a("deathScreen.score") + ": " + TextFormatting.YELLOW + this.field_146297_k.field_71439_g.func_71037_bA(), this.field_146294_l / 2, 100, 16777215);
      if (this.field_184871_f != null && p_73863_2_ > 85 && p_73863_2_ < 85 + this.field_146289_q.field_78288_b) {
         ITextComponent itextcomponent = this.func_184870_b(p_73863_1_);
         if (itextcomponent != null && itextcomponent.func_150256_b().func_150210_i() != null) {
            this.func_175272_a(itextcomponent, p_73863_1_, p_73863_2_);
         }
      }

      super.func_73863_a(p_73863_1_, p_73863_2_, p_73863_3_);
   }

   @Nullable
   public ITextComponent func_184870_b(int p_184870_1_) {
      if (this.field_184871_f == null) {
         return null;
      } else {
         int i = this.field_146297_k.field_71466_p.func_78256_a(this.field_184871_f.func_150254_d());
         int j = this.field_146294_l / 2 - i / 2;
         int k = this.field_146294_l / 2 + i / 2;
         int l = j;
         if (p_184870_1_ >= j && p_184870_1_ <= k) {
            for(ITextComponent itextcomponent : this.field_184871_f) {
               l += this.field_146297_k.field_71466_p.func_78256_a(GuiUtilRenderComponents.func_178909_a(itextcomponent.func_150261_e(), false));
               if (l > p_184870_1_) {
                  return itextcomponent;
               }
            }

            return null;
         } else {
            return null;
         }
      }
   }

   public boolean func_73868_f() {
      return false;
   }

   public void func_73876_c() {
      super.func_73876_c();
      ++this.field_146347_a;
      if (this.field_146347_a == 20) {
         for(GuiButton guibutton : this.field_146292_n) {
            guibutton.field_146124_l = true;
         }
      }

   }
}
