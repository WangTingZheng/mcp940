package net.minecraft.client.model;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class ModelChicken extends ModelBase {
   public ModelRenderer field_78142_a;
   public ModelRenderer field_78140_b;
   public ModelRenderer field_78141_c;
   public ModelRenderer field_78138_d;
   public ModelRenderer field_78139_e;
   public ModelRenderer field_78136_f;
   public ModelRenderer field_78137_g;
   public ModelRenderer field_78143_h;

   public ModelChicken() {
      int i = 16;
      this.field_78142_a = new ModelRenderer(this, 0, 0);
      this.field_78142_a.func_78790_a(-2.0F, -6.0F, -2.0F, 4, 6, 3, 0.0F);
      this.field_78142_a.func_78793_a(0.0F, 15.0F, -4.0F);
      this.field_78137_g = new ModelRenderer(this, 14, 0);
      this.field_78137_g.func_78790_a(-2.0F, -4.0F, -4.0F, 4, 2, 2, 0.0F);
      this.field_78137_g.func_78793_a(0.0F, 15.0F, -4.0F);
      this.field_78143_h = new ModelRenderer(this, 14, 4);
      this.field_78143_h.func_78790_a(-1.0F, -2.0F, -3.0F, 2, 2, 2, 0.0F);
      this.field_78143_h.func_78793_a(0.0F, 15.0F, -4.0F);
      this.field_78140_b = new ModelRenderer(this, 0, 9);
      this.field_78140_b.func_78790_a(-3.0F, -4.0F, -3.0F, 6, 8, 6, 0.0F);
      this.field_78140_b.func_78793_a(0.0F, 16.0F, 0.0F);
      this.field_78141_c = new ModelRenderer(this, 26, 0);
      this.field_78141_c.func_78789_a(-1.0F, 0.0F, -3.0F, 3, 5, 3);
      this.field_78141_c.func_78793_a(-2.0F, 19.0F, 1.0F);
      this.field_78138_d = new ModelRenderer(this, 26, 0);
      this.field_78138_d.func_78789_a(-1.0F, 0.0F, -3.0F, 3, 5, 3);
      this.field_78138_d.func_78793_a(1.0F, 19.0F, 1.0F);
      this.field_78139_e = new ModelRenderer(this, 24, 13);
      this.field_78139_e.func_78789_a(0.0F, 0.0F, -3.0F, 1, 4, 6);
      this.field_78139_e.func_78793_a(-4.0F, 13.0F, 0.0F);
      this.field_78136_f = new ModelRenderer(this, 24, 13);
      this.field_78136_f.func_78789_a(-1.0F, 0.0F, -3.0F, 1, 4, 6);
      this.field_78136_f.func_78793_a(4.0F, 13.0F, 0.0F);
   }

   public void func_78088_a(Entity p_78088_1_, float p_78088_2_, float p_78088_3_, float p_78088_4_, float p_78088_5_, float p_78088_6_, float p_78088_7_) {
      this.func_78087_a(p_78088_2_, p_78088_3_, p_78088_4_, p_78088_5_, p_78088_6_, p_78088_7_, p_78088_1_);
      if (this.field_78091_s) {
         float f = 2.0F;
         GlStateManager.func_179094_E();
         GlStateManager.func_179109_b(0.0F, 5.0F * p_78088_7_, 2.0F * p_78088_7_);
         this.field_78142_a.func_78785_a(p_78088_7_);
         this.field_78137_g.func_78785_a(p_78088_7_);
         this.field_78143_h.func_78785_a(p_78088_7_);
         GlStateManager.func_179121_F();
         GlStateManager.func_179094_E();
         GlStateManager.func_179152_a(0.5F, 0.5F, 0.5F);
         GlStateManager.func_179109_b(0.0F, 24.0F * p_78088_7_, 0.0F);
         this.field_78140_b.func_78785_a(p_78088_7_);
         this.field_78141_c.func_78785_a(p_78088_7_);
         this.field_78138_d.func_78785_a(p_78088_7_);
         this.field_78139_e.func_78785_a(p_78088_7_);
         this.field_78136_f.func_78785_a(p_78088_7_);
         GlStateManager.func_179121_F();
      } else {
         this.field_78142_a.func_78785_a(p_78088_7_);
         this.field_78137_g.func_78785_a(p_78088_7_);
         this.field_78143_h.func_78785_a(p_78088_7_);
         this.field_78140_b.func_78785_a(p_78088_7_);
         this.field_78141_c.func_78785_a(p_78088_7_);
         this.field_78138_d.func_78785_a(p_78088_7_);
         this.field_78139_e.func_78785_a(p_78088_7_);
         this.field_78136_f.func_78785_a(p_78088_7_);
      }

   }

   public void func_78087_a(float p_78087_1_, float p_78087_2_, float p_78087_3_, float p_78087_4_, float p_78087_5_, float p_78087_6_, Entity p_78087_7_) {
      this.field_78142_a.field_78795_f = p_78087_5_ * 0.017453292F;
      this.field_78142_a.field_78796_g = p_78087_4_ * 0.017453292F;
      this.field_78137_g.field_78795_f = this.field_78142_a.field_78795_f;
      this.field_78137_g.field_78796_g = this.field_78142_a.field_78796_g;
      this.field_78143_h.field_78795_f = this.field_78142_a.field_78795_f;
      this.field_78143_h.field_78796_g = this.field_78142_a.field_78796_g;
      this.field_78140_b.field_78795_f = 1.5707964F;
      this.field_78141_c.field_78795_f = MathHelper.func_76134_b(p_78087_1_ * 0.6662F) * 1.4F * p_78087_2_;
      this.field_78138_d.field_78795_f = MathHelper.func_76134_b(p_78087_1_ * 0.6662F + 3.1415927F) * 1.4F * p_78087_2_;
      this.field_78139_e.field_78808_h = p_78087_3_;
      this.field_78136_f.field_78808_h = -p_78087_3_;
   }
}
