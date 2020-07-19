package net.minecraft.client.model;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class ModelSpider extends ModelBase {
   public ModelRenderer field_78209_a;
   public ModelRenderer field_78207_b;
   public ModelRenderer field_78208_c;
   public ModelRenderer field_78205_d;
   public ModelRenderer field_78206_e;
   public ModelRenderer field_78203_f;
   public ModelRenderer field_78204_g;
   public ModelRenderer field_78212_h;
   public ModelRenderer field_78213_i;
   public ModelRenderer field_78210_j;
   public ModelRenderer field_78211_k;

   public ModelSpider() {
      float f = 0.0F;
      int i = 15;
      this.field_78209_a = new ModelRenderer(this, 32, 4);
      this.field_78209_a.func_78790_a(-4.0F, -4.0F, -8.0F, 8, 8, 8, 0.0F);
      this.field_78209_a.func_78793_a(0.0F, 15.0F, -3.0F);
      this.field_78207_b = new ModelRenderer(this, 0, 0);
      this.field_78207_b.func_78790_a(-3.0F, -3.0F, -3.0F, 6, 6, 6, 0.0F);
      this.field_78207_b.func_78793_a(0.0F, 15.0F, 0.0F);
      this.field_78208_c = new ModelRenderer(this, 0, 12);
      this.field_78208_c.func_78790_a(-5.0F, -4.0F, -6.0F, 10, 8, 12, 0.0F);
      this.field_78208_c.func_78793_a(0.0F, 15.0F, 9.0F);
      this.field_78205_d = new ModelRenderer(this, 18, 0);
      this.field_78205_d.func_78790_a(-15.0F, -1.0F, -1.0F, 16, 2, 2, 0.0F);
      this.field_78205_d.func_78793_a(-4.0F, 15.0F, 2.0F);
      this.field_78206_e = new ModelRenderer(this, 18, 0);
      this.field_78206_e.func_78790_a(-1.0F, -1.0F, -1.0F, 16, 2, 2, 0.0F);
      this.field_78206_e.func_78793_a(4.0F, 15.0F, 2.0F);
      this.field_78203_f = new ModelRenderer(this, 18, 0);
      this.field_78203_f.func_78790_a(-15.0F, -1.0F, -1.0F, 16, 2, 2, 0.0F);
      this.field_78203_f.func_78793_a(-4.0F, 15.0F, 1.0F);
      this.field_78204_g = new ModelRenderer(this, 18, 0);
      this.field_78204_g.func_78790_a(-1.0F, -1.0F, -1.0F, 16, 2, 2, 0.0F);
      this.field_78204_g.func_78793_a(4.0F, 15.0F, 1.0F);
      this.field_78212_h = new ModelRenderer(this, 18, 0);
      this.field_78212_h.func_78790_a(-15.0F, -1.0F, -1.0F, 16, 2, 2, 0.0F);
      this.field_78212_h.func_78793_a(-4.0F, 15.0F, 0.0F);
      this.field_78213_i = new ModelRenderer(this, 18, 0);
      this.field_78213_i.func_78790_a(-1.0F, -1.0F, -1.0F, 16, 2, 2, 0.0F);
      this.field_78213_i.func_78793_a(4.0F, 15.0F, 0.0F);
      this.field_78210_j = new ModelRenderer(this, 18, 0);
      this.field_78210_j.func_78790_a(-15.0F, -1.0F, -1.0F, 16, 2, 2, 0.0F);
      this.field_78210_j.func_78793_a(-4.0F, 15.0F, -1.0F);
      this.field_78211_k = new ModelRenderer(this, 18, 0);
      this.field_78211_k.func_78790_a(-1.0F, -1.0F, -1.0F, 16, 2, 2, 0.0F);
      this.field_78211_k.func_78793_a(4.0F, 15.0F, -1.0F);
   }

   public void func_78088_a(Entity p_78088_1_, float p_78088_2_, float p_78088_3_, float p_78088_4_, float p_78088_5_, float p_78088_6_, float p_78088_7_) {
      this.func_78087_a(p_78088_2_, p_78088_3_, p_78088_4_, p_78088_5_, p_78088_6_, p_78088_7_, p_78088_1_);
      this.field_78209_a.func_78785_a(p_78088_7_);
      this.field_78207_b.func_78785_a(p_78088_7_);
      this.field_78208_c.func_78785_a(p_78088_7_);
      this.field_78205_d.func_78785_a(p_78088_7_);
      this.field_78206_e.func_78785_a(p_78088_7_);
      this.field_78203_f.func_78785_a(p_78088_7_);
      this.field_78204_g.func_78785_a(p_78088_7_);
      this.field_78212_h.func_78785_a(p_78088_7_);
      this.field_78213_i.func_78785_a(p_78088_7_);
      this.field_78210_j.func_78785_a(p_78088_7_);
      this.field_78211_k.func_78785_a(p_78088_7_);
   }

   public void func_78087_a(float p_78087_1_, float p_78087_2_, float p_78087_3_, float p_78087_4_, float p_78087_5_, float p_78087_6_, Entity p_78087_7_) {
      this.field_78209_a.field_78796_g = p_78087_4_ * 0.017453292F;
      this.field_78209_a.field_78795_f = p_78087_5_ * 0.017453292F;
      float f = 0.7853982F;
      this.field_78205_d.field_78808_h = -0.7853982F;
      this.field_78206_e.field_78808_h = 0.7853982F;
      this.field_78203_f.field_78808_h = -0.58119464F;
      this.field_78204_g.field_78808_h = 0.58119464F;
      this.field_78212_h.field_78808_h = -0.58119464F;
      this.field_78213_i.field_78808_h = 0.58119464F;
      this.field_78210_j.field_78808_h = -0.7853982F;
      this.field_78211_k.field_78808_h = 0.7853982F;
      float f1 = -0.0F;
      float f2 = 0.3926991F;
      this.field_78205_d.field_78796_g = 0.7853982F;
      this.field_78206_e.field_78796_g = -0.7853982F;
      this.field_78203_f.field_78796_g = 0.3926991F;
      this.field_78204_g.field_78796_g = -0.3926991F;
      this.field_78212_h.field_78796_g = -0.3926991F;
      this.field_78213_i.field_78796_g = 0.3926991F;
      this.field_78210_j.field_78796_g = -0.7853982F;
      this.field_78211_k.field_78796_g = 0.7853982F;
      float f3 = -(MathHelper.func_76134_b(p_78087_1_ * 0.6662F * 2.0F + 0.0F) * 0.4F) * p_78087_2_;
      float f4 = -(MathHelper.func_76134_b(p_78087_1_ * 0.6662F * 2.0F + 3.1415927F) * 0.4F) * p_78087_2_;
      float f5 = -(MathHelper.func_76134_b(p_78087_1_ * 0.6662F * 2.0F + 1.5707964F) * 0.4F) * p_78087_2_;
      float f6 = -(MathHelper.func_76134_b(p_78087_1_ * 0.6662F * 2.0F + 4.712389F) * 0.4F) * p_78087_2_;
      float f7 = Math.abs(MathHelper.func_76126_a(p_78087_1_ * 0.6662F + 0.0F) * 0.4F) * p_78087_2_;
      float f8 = Math.abs(MathHelper.func_76126_a(p_78087_1_ * 0.6662F + 3.1415927F) * 0.4F) * p_78087_2_;
      float f9 = Math.abs(MathHelper.func_76126_a(p_78087_1_ * 0.6662F + 1.5707964F) * 0.4F) * p_78087_2_;
      float f10 = Math.abs(MathHelper.func_76126_a(p_78087_1_ * 0.6662F + 4.712389F) * 0.4F) * p_78087_2_;
      this.field_78205_d.field_78796_g += f3;
      this.field_78206_e.field_78796_g += -f3;
      this.field_78203_f.field_78796_g += f4;
      this.field_78204_g.field_78796_g += -f4;
      this.field_78212_h.field_78796_g += f5;
      this.field_78213_i.field_78796_g += -f5;
      this.field_78210_j.field_78796_g += f6;
      this.field_78211_k.field_78796_g += -f6;
      this.field_78205_d.field_78808_h += f7;
      this.field_78206_e.field_78808_h += -f7;
      this.field_78203_f.field_78808_h += f8;
      this.field_78204_g.field_78808_h += -f8;
      this.field_78212_h.field_78808_h += f9;
      this.field_78213_i.field_78808_h += -f9;
      this.field_78210_j.field_78808_h += f10;
      this.field_78211_k.field_78808_h += -f10;
   }
}
