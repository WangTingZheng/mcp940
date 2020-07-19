package net.minecraft.client.model;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.util.math.MathHelper;

public class ModelWolf extends ModelBase {
   public ModelRenderer field_78185_a;
   public ModelRenderer field_78183_b;
   public ModelRenderer field_78184_c;
   public ModelRenderer field_78181_d;
   public ModelRenderer field_78182_e;
   public ModelRenderer field_78179_f;
   ModelRenderer field_78180_g;
   ModelRenderer field_78186_h;

   public ModelWolf() {
      float f = 0.0F;
      float f1 = 13.5F;
      this.field_78185_a = new ModelRenderer(this, 0, 0);
      this.field_78185_a.func_78790_a(-2.0F, -3.0F, -2.0F, 6, 6, 4, 0.0F);
      this.field_78185_a.func_78793_a(-1.0F, 13.5F, -7.0F);
      this.field_78183_b = new ModelRenderer(this, 18, 14);
      this.field_78183_b.func_78790_a(-3.0F, -2.0F, -3.0F, 6, 9, 6, 0.0F);
      this.field_78183_b.func_78793_a(0.0F, 14.0F, 2.0F);
      this.field_78186_h = new ModelRenderer(this, 21, 0);
      this.field_78186_h.func_78790_a(-3.0F, -3.0F, -3.0F, 8, 6, 7, 0.0F);
      this.field_78186_h.func_78793_a(-1.0F, 14.0F, 2.0F);
      this.field_78184_c = new ModelRenderer(this, 0, 18);
      this.field_78184_c.func_78790_a(0.0F, 0.0F, -1.0F, 2, 8, 2, 0.0F);
      this.field_78184_c.func_78793_a(-2.5F, 16.0F, 7.0F);
      this.field_78181_d = new ModelRenderer(this, 0, 18);
      this.field_78181_d.func_78790_a(0.0F, 0.0F, -1.0F, 2, 8, 2, 0.0F);
      this.field_78181_d.func_78793_a(0.5F, 16.0F, 7.0F);
      this.field_78182_e = new ModelRenderer(this, 0, 18);
      this.field_78182_e.func_78790_a(0.0F, 0.0F, -1.0F, 2, 8, 2, 0.0F);
      this.field_78182_e.func_78793_a(-2.5F, 16.0F, -4.0F);
      this.field_78179_f = new ModelRenderer(this, 0, 18);
      this.field_78179_f.func_78790_a(0.0F, 0.0F, -1.0F, 2, 8, 2, 0.0F);
      this.field_78179_f.func_78793_a(0.5F, 16.0F, -4.0F);
      this.field_78180_g = new ModelRenderer(this, 9, 18);
      this.field_78180_g.func_78790_a(0.0F, 0.0F, -1.0F, 2, 8, 2, 0.0F);
      this.field_78180_g.func_78793_a(-1.0F, 12.0F, 8.0F);
      this.field_78185_a.func_78784_a(16, 14).func_78790_a(-2.0F, -5.0F, 0.0F, 2, 2, 1, 0.0F);
      this.field_78185_a.func_78784_a(16, 14).func_78790_a(2.0F, -5.0F, 0.0F, 2, 2, 1, 0.0F);
      this.field_78185_a.func_78784_a(0, 10).func_78790_a(-0.5F, 0.0F, -5.0F, 3, 3, 4, 0.0F);
   }

   public void func_78088_a(Entity p_78088_1_, float p_78088_2_, float p_78088_3_, float p_78088_4_, float p_78088_5_, float p_78088_6_, float p_78088_7_) {
      super.func_78088_a(p_78088_1_, p_78088_2_, p_78088_3_, p_78088_4_, p_78088_5_, p_78088_6_, p_78088_7_);
      this.func_78087_a(p_78088_2_, p_78088_3_, p_78088_4_, p_78088_5_, p_78088_6_, p_78088_7_, p_78088_1_);
      if (this.field_78091_s) {
         float f = 2.0F;
         GlStateManager.func_179094_E();
         GlStateManager.func_179109_b(0.0F, 5.0F * p_78088_7_, 2.0F * p_78088_7_);
         this.field_78185_a.func_78791_b(p_78088_7_);
         GlStateManager.func_179121_F();
         GlStateManager.func_179094_E();
         GlStateManager.func_179152_a(0.5F, 0.5F, 0.5F);
         GlStateManager.func_179109_b(0.0F, 24.0F * p_78088_7_, 0.0F);
         this.field_78183_b.func_78785_a(p_78088_7_);
         this.field_78184_c.func_78785_a(p_78088_7_);
         this.field_78181_d.func_78785_a(p_78088_7_);
         this.field_78182_e.func_78785_a(p_78088_7_);
         this.field_78179_f.func_78785_a(p_78088_7_);
         this.field_78180_g.func_78791_b(p_78088_7_);
         this.field_78186_h.func_78785_a(p_78088_7_);
         GlStateManager.func_179121_F();
      } else {
         this.field_78185_a.func_78791_b(p_78088_7_);
         this.field_78183_b.func_78785_a(p_78088_7_);
         this.field_78184_c.func_78785_a(p_78088_7_);
         this.field_78181_d.func_78785_a(p_78088_7_);
         this.field_78182_e.func_78785_a(p_78088_7_);
         this.field_78179_f.func_78785_a(p_78088_7_);
         this.field_78180_g.func_78791_b(p_78088_7_);
         this.field_78186_h.func_78785_a(p_78088_7_);
      }

   }

   public void func_78086_a(EntityLivingBase p_78086_1_, float p_78086_2_, float p_78086_3_, float p_78086_4_) {
      EntityWolf entitywolf = (EntityWolf)p_78086_1_;
      if (entitywolf.func_70919_bu()) {
         this.field_78180_g.field_78796_g = 0.0F;
      } else {
         this.field_78180_g.field_78796_g = MathHelper.func_76134_b(p_78086_2_ * 0.6662F) * 1.4F * p_78086_3_;
      }

      if (entitywolf.func_70906_o()) {
         this.field_78186_h.func_78793_a(-1.0F, 16.0F, -3.0F);
         this.field_78186_h.field_78795_f = 1.2566371F;
         this.field_78186_h.field_78796_g = 0.0F;
         this.field_78183_b.func_78793_a(0.0F, 18.0F, 0.0F);
         this.field_78183_b.field_78795_f = 0.7853982F;
         this.field_78180_g.func_78793_a(-1.0F, 21.0F, 6.0F);
         this.field_78184_c.func_78793_a(-2.5F, 22.0F, 2.0F);
         this.field_78184_c.field_78795_f = 4.712389F;
         this.field_78181_d.func_78793_a(0.5F, 22.0F, 2.0F);
         this.field_78181_d.field_78795_f = 4.712389F;
         this.field_78182_e.field_78795_f = 5.811947F;
         this.field_78182_e.func_78793_a(-2.49F, 17.0F, -4.0F);
         this.field_78179_f.field_78795_f = 5.811947F;
         this.field_78179_f.func_78793_a(0.51F, 17.0F, -4.0F);
      } else {
         this.field_78183_b.func_78793_a(0.0F, 14.0F, 2.0F);
         this.field_78183_b.field_78795_f = 1.5707964F;
         this.field_78186_h.func_78793_a(-1.0F, 14.0F, -3.0F);
         this.field_78186_h.field_78795_f = this.field_78183_b.field_78795_f;
         this.field_78180_g.func_78793_a(-1.0F, 12.0F, 8.0F);
         this.field_78184_c.func_78793_a(-2.5F, 16.0F, 7.0F);
         this.field_78181_d.func_78793_a(0.5F, 16.0F, 7.0F);
         this.field_78182_e.func_78793_a(-2.5F, 16.0F, -4.0F);
         this.field_78179_f.func_78793_a(0.5F, 16.0F, -4.0F);
         this.field_78184_c.field_78795_f = MathHelper.func_76134_b(p_78086_2_ * 0.6662F) * 1.4F * p_78086_3_;
         this.field_78181_d.field_78795_f = MathHelper.func_76134_b(p_78086_2_ * 0.6662F + 3.1415927F) * 1.4F * p_78086_3_;
         this.field_78182_e.field_78795_f = MathHelper.func_76134_b(p_78086_2_ * 0.6662F + 3.1415927F) * 1.4F * p_78086_3_;
         this.field_78179_f.field_78795_f = MathHelper.func_76134_b(p_78086_2_ * 0.6662F) * 1.4F * p_78086_3_;
      }

      this.field_78185_a.field_78808_h = entitywolf.func_70917_k(p_78086_4_) + entitywolf.func_70923_f(p_78086_4_, 0.0F);
      this.field_78186_h.field_78808_h = entitywolf.func_70923_f(p_78086_4_, -0.08F);
      this.field_78183_b.field_78808_h = entitywolf.func_70923_f(p_78086_4_, -0.16F);
      this.field_78180_g.field_78808_h = entitywolf.func_70923_f(p_78086_4_, -0.2F);
   }

   public void func_78087_a(float p_78087_1_, float p_78087_2_, float p_78087_3_, float p_78087_4_, float p_78087_5_, float p_78087_6_, Entity p_78087_7_) {
      super.func_78087_a(p_78087_1_, p_78087_2_, p_78087_3_, p_78087_4_, p_78087_5_, p_78087_6_, p_78087_7_);
      this.field_78185_a.field_78795_f = p_78087_5_ * 0.017453292F;
      this.field_78185_a.field_78796_g = p_78087_4_ * 0.017453292F;
      this.field_78180_g.field_78795_f = p_78087_3_;
   }
}
