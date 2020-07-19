package net.minecraft.client.model;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class ModelQuadruped extends ModelBase {
   public ModelRenderer field_78150_a = new ModelRenderer(this, 0, 0);
   public ModelRenderer field_78148_b;
   public ModelRenderer field_78149_c;
   public ModelRenderer field_78146_d;
   public ModelRenderer field_78147_e;
   public ModelRenderer field_78144_f;
   protected float field_78145_g = 8.0F;
   protected float field_78151_h = 4.0F;

   public ModelQuadruped(int p_i1154_1_, float p_i1154_2_) {
      this.field_78150_a.func_78790_a(-4.0F, -4.0F, -8.0F, 8, 8, 8, p_i1154_2_);
      this.field_78150_a.func_78793_a(0.0F, (float)(18 - p_i1154_1_), -6.0F);
      this.field_78148_b = new ModelRenderer(this, 28, 8);
      this.field_78148_b.func_78790_a(-5.0F, -10.0F, -7.0F, 10, 16, 8, p_i1154_2_);
      this.field_78148_b.func_78793_a(0.0F, (float)(17 - p_i1154_1_), 2.0F);
      this.field_78149_c = new ModelRenderer(this, 0, 16);
      this.field_78149_c.func_78790_a(-2.0F, 0.0F, -2.0F, 4, p_i1154_1_, 4, p_i1154_2_);
      this.field_78149_c.func_78793_a(-3.0F, (float)(24 - p_i1154_1_), 7.0F);
      this.field_78146_d = new ModelRenderer(this, 0, 16);
      this.field_78146_d.func_78790_a(-2.0F, 0.0F, -2.0F, 4, p_i1154_1_, 4, p_i1154_2_);
      this.field_78146_d.func_78793_a(3.0F, (float)(24 - p_i1154_1_), 7.0F);
      this.field_78147_e = new ModelRenderer(this, 0, 16);
      this.field_78147_e.func_78790_a(-2.0F, 0.0F, -2.0F, 4, p_i1154_1_, 4, p_i1154_2_);
      this.field_78147_e.func_78793_a(-3.0F, (float)(24 - p_i1154_1_), -5.0F);
      this.field_78144_f = new ModelRenderer(this, 0, 16);
      this.field_78144_f.func_78790_a(-2.0F, 0.0F, -2.0F, 4, p_i1154_1_, 4, p_i1154_2_);
      this.field_78144_f.func_78793_a(3.0F, (float)(24 - p_i1154_1_), -5.0F);
   }

   public void func_78088_a(Entity p_78088_1_, float p_78088_2_, float p_78088_3_, float p_78088_4_, float p_78088_5_, float p_78088_6_, float p_78088_7_) {
      this.func_78087_a(p_78088_2_, p_78088_3_, p_78088_4_, p_78088_5_, p_78088_6_, p_78088_7_, p_78088_1_);
      if (this.field_78091_s) {
         float f = 2.0F;
         GlStateManager.func_179094_E();
         GlStateManager.func_179109_b(0.0F, this.field_78145_g * p_78088_7_, this.field_78151_h * p_78088_7_);
         this.field_78150_a.func_78785_a(p_78088_7_);
         GlStateManager.func_179121_F();
         GlStateManager.func_179094_E();
         GlStateManager.func_179152_a(0.5F, 0.5F, 0.5F);
         GlStateManager.func_179109_b(0.0F, 24.0F * p_78088_7_, 0.0F);
         this.field_78148_b.func_78785_a(p_78088_7_);
         this.field_78149_c.func_78785_a(p_78088_7_);
         this.field_78146_d.func_78785_a(p_78088_7_);
         this.field_78147_e.func_78785_a(p_78088_7_);
         this.field_78144_f.func_78785_a(p_78088_7_);
         GlStateManager.func_179121_F();
      } else {
         this.field_78150_a.func_78785_a(p_78088_7_);
         this.field_78148_b.func_78785_a(p_78088_7_);
         this.field_78149_c.func_78785_a(p_78088_7_);
         this.field_78146_d.func_78785_a(p_78088_7_);
         this.field_78147_e.func_78785_a(p_78088_7_);
         this.field_78144_f.func_78785_a(p_78088_7_);
      }

   }

   public void func_78087_a(float p_78087_1_, float p_78087_2_, float p_78087_3_, float p_78087_4_, float p_78087_5_, float p_78087_6_, Entity p_78087_7_) {
      this.field_78150_a.field_78795_f = p_78087_5_ * 0.017453292F;
      this.field_78150_a.field_78796_g = p_78087_4_ * 0.017453292F;
      this.field_78148_b.field_78795_f = 1.5707964F;
      this.field_78149_c.field_78795_f = MathHelper.func_76134_b(p_78087_1_ * 0.6662F) * 1.4F * p_78087_2_;
      this.field_78146_d.field_78795_f = MathHelper.func_76134_b(p_78087_1_ * 0.6662F + 3.1415927F) * 1.4F * p_78087_2_;
      this.field_78147_e.field_78795_f = MathHelper.func_76134_b(p_78087_1_ * 0.6662F + 3.1415927F) * 1.4F * p_78087_2_;
      this.field_78144_f.field_78795_f = MathHelper.func_76134_b(p_78087_1_ * 0.6662F) * 1.4F * p_78087_2_;
   }
}
