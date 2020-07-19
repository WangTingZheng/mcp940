package net.minecraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;

public class GuiSlider extends GuiButton {
   private float field_175227_p = 1.0F;
   public boolean field_175228_o;
   private final String field_175226_q;
   private final float field_175225_r;
   private final float field_175224_s;
   private final GuiPageButtonList.GuiResponder field_175223_t;
   private GuiSlider.FormatHelper field_175222_u;

   public GuiSlider(GuiPageButtonList.GuiResponder p_i45541_1_, int p_i45541_2_, int p_i45541_3_, int p_i45541_4_, String p_i45541_5_, float p_i45541_6_, float p_i45541_7_, float p_i45541_8_, GuiSlider.FormatHelper p_i45541_9_) {
      super(p_i45541_2_, p_i45541_3_, p_i45541_4_, 150, 20, "");
      this.field_175226_q = p_i45541_5_;
      this.field_175225_r = p_i45541_6_;
      this.field_175224_s = p_i45541_7_;
      this.field_175227_p = (p_i45541_8_ - p_i45541_6_) / (p_i45541_7_ - p_i45541_6_);
      this.field_175222_u = p_i45541_9_;
      this.field_175223_t = p_i45541_1_;
      this.field_146126_j = this.func_175221_e();
   }

   public float func_175220_c() {
      return this.field_175225_r + (this.field_175224_s - this.field_175225_r) * this.field_175227_p;
   }

   public void func_175218_a(float p_175218_1_, boolean p_175218_2_) {
      this.field_175227_p = (p_175218_1_ - this.field_175225_r) / (this.field_175224_s - this.field_175225_r);
      this.field_146126_j = this.func_175221_e();
      if (p_175218_2_) {
         this.field_175223_t.func_175320_a(this.field_146127_k, this.func_175220_c());
      }

   }

   public float func_175217_d() {
      return this.field_175227_p;
   }

   private String func_175221_e() {
      return this.field_175222_u == null ? I18n.func_135052_a(this.field_175226_q) + ": " + this.func_175220_c() : this.field_175222_u.func_175318_a(this.field_146127_k, I18n.func_135052_a(this.field_175226_q), this.func_175220_c());
   }

   protected int func_146114_a(boolean p_146114_1_) {
      return 0;
   }

   protected void func_146119_b(Minecraft p_146119_1_, int p_146119_2_, int p_146119_3_) {
      if (this.field_146125_m) {
         if (this.field_175228_o) {
            this.field_175227_p = (float)(p_146119_2_ - (this.field_146128_h + 4)) / (float)(this.field_146120_f - 8);
            if (this.field_175227_p < 0.0F) {
               this.field_175227_p = 0.0F;
            }

            if (this.field_175227_p > 1.0F) {
               this.field_175227_p = 1.0F;
            }

            this.field_146126_j = this.func_175221_e();
            this.field_175223_t.func_175320_a(this.field_146127_k, this.func_175220_c());
         }

         GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
         this.func_73729_b(this.field_146128_h + (int)(this.field_175227_p * (float)(this.field_146120_f - 8)), this.field_146129_i, 0, 66, 4, 20);
         this.func_73729_b(this.field_146128_h + (int)(this.field_175227_p * (float)(this.field_146120_f - 8)) + 4, this.field_146129_i, 196, 66, 4, 20);
      }
   }

   public void func_175219_a(float p_175219_1_) {
      this.field_175227_p = p_175219_1_;
      this.field_146126_j = this.func_175221_e();
      this.field_175223_t.func_175320_a(this.field_146127_k, this.func_175220_c());
   }

   public boolean func_146116_c(Minecraft p_146116_1_, int p_146116_2_, int p_146116_3_) {
      if (super.func_146116_c(p_146116_1_, p_146116_2_, p_146116_3_)) {
         this.field_175227_p = (float)(p_146116_2_ - (this.field_146128_h + 4)) / (float)(this.field_146120_f - 8);
         if (this.field_175227_p < 0.0F) {
            this.field_175227_p = 0.0F;
         }

         if (this.field_175227_p > 1.0F) {
            this.field_175227_p = 1.0F;
         }

         this.field_146126_j = this.func_175221_e();
         this.field_175223_t.func_175320_a(this.field_146127_k, this.func_175220_c());
         this.field_175228_o = true;
         return true;
      } else {
         return false;
      }
   }

   public void func_146118_a(int p_146118_1_, int p_146118_2_) {
      this.field_175228_o = false;
   }

   public interface FormatHelper {
      String func_175318_a(int var1, String var2, float var3);
   }
}
