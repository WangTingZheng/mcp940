package net.minecraft.client.gui;

import java.io.IOException;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;

public class GuiVideoSettings extends GuiScreen {
   private final GuiScreen field_146498_f;
   protected String field_146500_a = "Video Settings";
   private final GameSettings field_146499_g;
   private GuiListExtended field_146501_h;
   private static final GameSettings.Options[] field_146502_i = new GameSettings.Options[]{GameSettings.Options.GRAPHICS, GameSettings.Options.RENDER_DISTANCE, GameSettings.Options.AMBIENT_OCCLUSION, GameSettings.Options.FRAMERATE_LIMIT, GameSettings.Options.ANAGLYPH, GameSettings.Options.VIEW_BOBBING, GameSettings.Options.GUI_SCALE, GameSettings.Options.ATTACK_INDICATOR, GameSettings.Options.GAMMA, GameSettings.Options.RENDER_CLOUDS, GameSettings.Options.PARTICLES, GameSettings.Options.USE_FULLSCREEN, GameSettings.Options.ENABLE_VSYNC, GameSettings.Options.MIPMAP_LEVELS, GameSettings.Options.USE_VBO, GameSettings.Options.ENTITY_SHADOWS};

   public GuiVideoSettings(GuiScreen p_i1062_1_, GameSettings p_i1062_2_) {
      this.field_146498_f = p_i1062_1_;
      this.field_146499_g = p_i1062_2_;
   }

   public void func_73866_w_() {
      this.field_146500_a = I18n.func_135052_a("options.videoTitle");
      this.field_146292_n.clear();
      this.field_146292_n.add(new GuiButton(200, this.field_146294_l / 2 - 100, this.field_146295_m - 27, I18n.func_135052_a("gui.done")));
      if (OpenGlHelper.field_176083_O) {
         this.field_146501_h = new GuiOptionsRowList(this.field_146297_k, this.field_146294_l, this.field_146295_m, 32, this.field_146295_m - 32, 25, field_146502_i);
      } else {
         GameSettings.Options[] agamesettings$options = new GameSettings.Options[field_146502_i.length - 1];
         int i = 0;

         for(GameSettings.Options gamesettings$options : field_146502_i) {
            if (gamesettings$options == GameSettings.Options.USE_VBO) {
               break;
            }

            agamesettings$options[i] = gamesettings$options;
            ++i;
         }

         this.field_146501_h = new GuiOptionsRowList(this.field_146297_k, this.field_146294_l, this.field_146295_m, 32, this.field_146295_m - 32, 25, agamesettings$options);
      }

   }

   public void func_146274_d() throws IOException {
      super.func_146274_d();
      this.field_146501_h.func_178039_p();
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
            this.field_146297_k.func_147108_a(this.field_146498_f);
         }

      }
   }

   protected void func_73864_a(int p_73864_1_, int p_73864_2_, int p_73864_3_) throws IOException {
      int i = this.field_146499_g.field_74335_Z;
      super.func_73864_a(p_73864_1_, p_73864_2_, p_73864_3_);
      this.field_146501_h.func_148179_a(p_73864_1_, p_73864_2_, p_73864_3_);
      if (this.field_146499_g.field_74335_Z != i) {
         ScaledResolution scaledresolution = new ScaledResolution(this.field_146297_k);
         int j = scaledresolution.func_78326_a();
         int k = scaledresolution.func_78328_b();
         this.func_146280_a(this.field_146297_k, j, k);
      }

   }

   protected void func_146286_b(int p_146286_1_, int p_146286_2_, int p_146286_3_) {
      int i = this.field_146499_g.field_74335_Z;
      super.func_146286_b(p_146286_1_, p_146286_2_, p_146286_3_);
      this.field_146501_h.func_148181_b(p_146286_1_, p_146286_2_, p_146286_3_);
      if (this.field_146499_g.field_74335_Z != i) {
         ScaledResolution scaledresolution = new ScaledResolution(this.field_146297_k);
         int j = scaledresolution.func_78326_a();
         int k = scaledresolution.func_78328_b();
         this.func_146280_a(this.field_146297_k, j, k);
      }

   }

   public void func_73863_a(int p_73863_1_, int p_73863_2_, float p_73863_3_) {
      this.func_146276_q_();
      this.field_146501_h.func_148128_a(p_73863_1_, p_73863_2_, p_73863_3_);
      this.func_73732_a(this.field_146289_q, this.field_146500_a, this.field_146294_l / 2, 5, 16777215);
      super.func_73863_a(p_73863_1_, p_73863_2_, p_73863_3_);
   }
}
