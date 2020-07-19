package net.minecraft.client.model;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityPolarBear;

public class ModelPolarBear extends ModelQuadruped {
   public ModelPolarBear() {
      super(12, 0.0F);
      this.field_78090_t = 128;
      this.field_78089_u = 64;
      this.field_78150_a = new ModelRenderer(this, 0, 0);
      this.field_78150_a.func_78790_a(-3.5F, -3.0F, -3.0F, 7, 7, 7, 0.0F);
      this.field_78150_a.func_78793_a(0.0F, 10.0F, -16.0F);
      this.field_78150_a.func_78784_a(0, 44).func_78790_a(-2.5F, 1.0F, -6.0F, 5, 3, 3, 0.0F);
      this.field_78150_a.func_78784_a(26, 0).func_78790_a(-4.5F, -4.0F, -1.0F, 2, 2, 1, 0.0F);
      ModelRenderer modelrenderer = this.field_78150_a.func_78784_a(26, 0);
      modelrenderer.field_78809_i = true;
      modelrenderer.func_78790_a(2.5F, -4.0F, -1.0F, 2, 2, 1, 0.0F);
      this.field_78148_b = new ModelRenderer(this);
      this.field_78148_b.func_78784_a(0, 19).func_78790_a(-5.0F, -13.0F, -7.0F, 14, 14, 11, 0.0F);
      this.field_78148_b.func_78784_a(39, 0).func_78790_a(-4.0F, -25.0F, -7.0F, 12, 12, 10, 0.0F);
      this.field_78148_b.func_78793_a(-2.0F, 9.0F, 12.0F);
      int i = 10;
      this.field_78149_c = new ModelRenderer(this, 50, 22);
      this.field_78149_c.func_78790_a(-2.0F, 0.0F, -2.0F, 4, 10, 8, 0.0F);
      this.field_78149_c.func_78793_a(-3.5F, 14.0F, 6.0F);
      this.field_78146_d = new ModelRenderer(this, 50, 22);
      this.field_78146_d.func_78790_a(-2.0F, 0.0F, -2.0F, 4, 10, 8, 0.0F);
      this.field_78146_d.func_78793_a(3.5F, 14.0F, 6.0F);
      this.field_78147_e = new ModelRenderer(this, 50, 40);
      this.field_78147_e.func_78790_a(-2.0F, 0.0F, -2.0F, 4, 10, 6, 0.0F);
      this.field_78147_e.func_78793_a(-2.5F, 14.0F, -7.0F);
      this.field_78144_f = new ModelRenderer(this, 50, 40);
      this.field_78144_f.func_78790_a(-2.0F, 0.0F, -2.0F, 4, 10, 6, 0.0F);
      this.field_78144_f.func_78793_a(2.5F, 14.0F, -7.0F);
      --this.field_78149_c.field_78800_c;
      ++this.field_78146_d.field_78800_c;
      this.field_78149_c.field_78798_e += 0.0F;
      this.field_78146_d.field_78798_e += 0.0F;
      --this.field_78147_e.field_78800_c;
      ++this.field_78144_f.field_78800_c;
      --this.field_78147_e.field_78798_e;
      --this.field_78144_f.field_78798_e;
      this.field_78151_h += 2.0F;
   }

   public void func_78088_a(Entity p_78088_1_, float p_78088_2_, float p_78088_3_, float p_78088_4_, float p_78088_5_, float p_78088_6_, float p_78088_7_) {
      this.func_78087_a(p_78088_2_, p_78088_3_, p_78088_4_, p_78088_5_, p_78088_6_, p_78088_7_, p_78088_1_);
      if (this.field_78091_s) {
         float f = 2.0F;
         this.field_78145_g = 16.0F;
         this.field_78151_h = 4.0F;
         GlStateManager.func_179094_E();
         GlStateManager.func_179152_a(0.6666667F, 0.6666667F, 0.6666667F);
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
      super.func_78087_a(p_78087_1_, p_78087_2_, p_78087_3_, p_78087_4_, p_78087_5_, p_78087_6_, p_78087_7_);
      float f = p_78087_3_ - (float)p_78087_7_.field_70173_aa;
      float f1 = ((EntityPolarBear)p_78087_7_).func_189795_r(f);
      f1 = f1 * f1;
      float f2 = 1.0F - f1;
      this.field_78148_b.field_78795_f = 1.5707964F - f1 * 3.1415927F * 0.35F;
      this.field_78148_b.field_78797_d = 9.0F * f2 + 11.0F * f1;
      this.field_78147_e.field_78797_d = 14.0F * f2 + -6.0F * f1;
      this.field_78147_e.field_78798_e = -8.0F * f2 + -4.0F * f1;
      this.field_78147_e.field_78795_f -= f1 * 3.1415927F * 0.45F;
      this.field_78144_f.field_78797_d = this.field_78147_e.field_78797_d;
      this.field_78144_f.field_78798_e = this.field_78147_e.field_78798_e;
      this.field_78144_f.field_78795_f -= f1 * 3.1415927F * 0.45F;
      this.field_78150_a.field_78797_d = 10.0F * f2 + -12.0F * f1;
      this.field_78150_a.field_78798_e = -16.0F * f2 + -3.0F * f1;
      this.field_78150_a.field_78795_f += f1 * 3.1415927F * 0.15F;
   }
}
