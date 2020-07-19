package net.minecraft.client.gui;

import java.io.IOException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.MathHelper;

public class GuiScreenOptionsSounds extends GuiScreen {
   private final GuiScreen field_146505_f;
   private final GameSettings field_146506_g;
   protected String field_146507_a = "Options";
   private String field_146508_h;

   public GuiScreenOptionsSounds(GuiScreen p_i45025_1_, GameSettings p_i45025_2_) {
      this.field_146505_f = p_i45025_1_;
      this.field_146506_g = p_i45025_2_;
   }

   public void func_73866_w_() {
      this.field_146507_a = I18n.func_135052_a("options.sounds.title");
      this.field_146508_h = I18n.func_135052_a("options.off");
      int i = 0;
      this.field_146292_n.add(new GuiScreenOptionsSounds.Button(SoundCategory.MASTER.ordinal(), this.field_146294_l / 2 - 155 + i % 2 * 160, this.field_146295_m / 6 - 12 + 24 * (i >> 1), SoundCategory.MASTER, true));
      i = i + 2;

      for(SoundCategory soundcategory : SoundCategory.values()) {
         if (soundcategory != SoundCategory.MASTER) {
            this.field_146292_n.add(new GuiScreenOptionsSounds.Button(soundcategory.ordinal(), this.field_146294_l / 2 - 155 + i % 2 * 160, this.field_146295_m / 6 - 12 + 24 * (i >> 1), soundcategory, false));
            ++i;
         }
      }

      int j = this.field_146294_l / 2 - 75;
      int k = this.field_146295_m / 6 - 12;
      ++i;
      this.field_146292_n.add(new GuiOptionButton(201, j, k + 24 * (i >> 1), GameSettings.Options.SHOW_SUBTITLES, this.field_146506_g.func_74297_c(GameSettings.Options.SHOW_SUBTITLES)));
      this.field_146292_n.add(new GuiButton(200, this.field_146294_l / 2 - 100, this.field_146295_m / 6 + 168, I18n.func_135052_a("gui.done")));
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
            this.field_146297_k.func_147108_a(this.field_146505_f);
         } else if (p_146284_1_.field_146127_k == 201) {
            this.field_146297_k.field_71474_y.func_74306_a(GameSettings.Options.SHOW_SUBTITLES, 1);
            p_146284_1_.field_146126_j = this.field_146297_k.field_71474_y.func_74297_c(GameSettings.Options.SHOW_SUBTITLES);
            this.field_146297_k.field_71474_y.func_74303_b();
         }

      }
   }

   public void func_73863_a(int p_73863_1_, int p_73863_2_, float p_73863_3_) {
      this.func_146276_q_();
      this.func_73732_a(this.field_146289_q, this.field_146507_a, this.field_146294_l / 2, 15, 16777215);
      super.func_73863_a(p_73863_1_, p_73863_2_, p_73863_3_);
   }

   protected String func_184097_a(SoundCategory p_184097_1_) {
      float f = this.field_146506_g.func_186711_a(p_184097_1_);
      return f == 0.0F ? this.field_146508_h : (int)(f * 100.0F) + "%";
   }

   class Button extends GuiButton {
      private final SoundCategory field_184063_r;
      private final String field_146152_s;
      public float field_146156_o = 1.0F;
      public boolean field_146155_p;

      public Button(int p_i46744_2_, int p_i46744_3_, int p_i46744_4_, SoundCategory p_i46744_5_, boolean p_i46744_6_) {
         super(p_i46744_2_, p_i46744_3_, p_i46744_4_, p_i46744_6_ ? 310 : 150, 20, "");
         this.field_184063_r = p_i46744_5_;
         this.field_146152_s = I18n.func_135052_a("soundCategory." + p_i46744_5_.func_187948_a());
         this.field_146126_j = this.field_146152_s + ": " + GuiScreenOptionsSounds.this.func_184097_a(p_i46744_5_);
         this.field_146156_o = GuiScreenOptionsSounds.this.field_146506_g.func_186711_a(p_i46744_5_);
      }

      protected int func_146114_a(boolean p_146114_1_) {
         return 0;
      }

      protected void func_146119_b(Minecraft p_146119_1_, int p_146119_2_, int p_146119_3_) {
         if (this.field_146125_m) {
            if (this.field_146155_p) {
               this.field_146156_o = (float)(p_146119_2_ - (this.field_146128_h + 4)) / (float)(this.field_146120_f - 8);
               this.field_146156_o = MathHelper.func_76131_a(this.field_146156_o, 0.0F, 1.0F);
               p_146119_1_.field_71474_y.func_186712_a(this.field_184063_r, this.field_146156_o);
               p_146119_1_.field_71474_y.func_74303_b();
               this.field_146126_j = this.field_146152_s + ": " + GuiScreenOptionsSounds.this.func_184097_a(this.field_184063_r);
            }

            GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
            this.func_73729_b(this.field_146128_h + (int)(this.field_146156_o * (float)(this.field_146120_f - 8)), this.field_146129_i, 0, 66, 4, 20);
            this.func_73729_b(this.field_146128_h + (int)(this.field_146156_o * (float)(this.field_146120_f - 8)) + 4, this.field_146129_i, 196, 66, 4, 20);
         }
      }

      public boolean func_146116_c(Minecraft p_146116_1_, int p_146116_2_, int p_146116_3_) {
         if (super.func_146116_c(p_146116_1_, p_146116_2_, p_146116_3_)) {
            this.field_146156_o = (float)(p_146116_2_ - (this.field_146128_h + 4)) / (float)(this.field_146120_f - 8);
            this.field_146156_o = MathHelper.func_76131_a(this.field_146156_o, 0.0F, 1.0F);
            p_146116_1_.field_71474_y.func_186712_a(this.field_184063_r, this.field_146156_o);
            p_146116_1_.field_71474_y.func_74303_b();
            this.field_146126_j = this.field_146152_s + ": " + GuiScreenOptionsSounds.this.func_184097_a(this.field_184063_r);
            this.field_146155_p = true;
            return true;
         } else {
            return false;
         }
      }

      public void func_146113_a(SoundHandler p_146113_1_) {
      }

      public void func_146118_a(int p_146118_1_, int p_146118_2_) {
         if (this.field_146155_p) {
            GuiScreenOptionsSounds.this.field_146297_k.func_147118_V().func_147682_a(PositionedSoundRecord.func_184371_a(SoundEvents.field_187909_gi, 1.0F));
         }

         this.field_146155_p = false;
      }
   }
}
