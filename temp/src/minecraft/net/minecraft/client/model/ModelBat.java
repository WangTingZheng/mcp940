package net.minecraft.client.model;

import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.util.math.MathHelper;

public class ModelBat extends ModelBase {
   private final ModelRenderer field_82895_a;
   private final ModelRenderer field_82893_b;
   private final ModelRenderer field_82894_c;
   private final ModelRenderer field_82891_d;
   private final ModelRenderer field_82892_e;
   private final ModelRenderer field_82890_f;

   public ModelBat() {
      this.field_78090_t = 64;
      this.field_78089_u = 64;
      this.field_82895_a = new ModelRenderer(this, 0, 0);
      this.field_82895_a.func_78789_a(-3.0F, -3.0F, -3.0F, 6, 6, 6);
      ModelRenderer modelrenderer = new ModelRenderer(this, 24, 0);
      modelrenderer.func_78789_a(-4.0F, -6.0F, -2.0F, 3, 4, 1);
      this.field_82895_a.func_78792_a(modelrenderer);
      ModelRenderer modelrenderer1 = new ModelRenderer(this, 24, 0);
      modelrenderer1.field_78809_i = true;
      modelrenderer1.func_78789_a(1.0F, -6.0F, -2.0F, 3, 4, 1);
      this.field_82895_a.func_78792_a(modelrenderer1);
      this.field_82893_b = new ModelRenderer(this, 0, 16);
      this.field_82893_b.func_78789_a(-3.0F, 4.0F, -3.0F, 6, 12, 6);
      this.field_82893_b.func_78784_a(0, 34).func_78789_a(-5.0F, 16.0F, 0.0F, 10, 6, 1);
      this.field_82894_c = new ModelRenderer(this, 42, 0);
      this.field_82894_c.func_78789_a(-12.0F, 1.0F, 1.5F, 10, 16, 1);
      this.field_82892_e = new ModelRenderer(this, 24, 16);
      this.field_82892_e.func_78793_a(-12.0F, 1.0F, 1.5F);
      this.field_82892_e.func_78789_a(-8.0F, 1.0F, 0.0F, 8, 12, 1);
      this.field_82891_d = new ModelRenderer(this, 42, 0);
      this.field_82891_d.field_78809_i = true;
      this.field_82891_d.func_78789_a(2.0F, 1.0F, 1.5F, 10, 16, 1);
      this.field_82890_f = new ModelRenderer(this, 24, 16);
      this.field_82890_f.field_78809_i = true;
      this.field_82890_f.func_78793_a(12.0F, 1.0F, 1.5F);
      this.field_82890_f.func_78789_a(0.0F, 1.0F, 0.0F, 8, 12, 1);
      this.field_82893_b.func_78792_a(this.field_82894_c);
      this.field_82893_b.func_78792_a(this.field_82891_d);
      this.field_82894_c.func_78792_a(this.field_82892_e);
      this.field_82891_d.func_78792_a(this.field_82890_f);
   }

   public void func_78088_a(Entity p_78088_1_, float p_78088_2_, float p_78088_3_, float p_78088_4_, float p_78088_5_, float p_78088_6_, float p_78088_7_) {
      this.func_78087_a(p_78088_2_, p_78088_3_, p_78088_4_, p_78088_5_, p_78088_6_, p_78088_7_, p_78088_1_);
      this.field_82895_a.func_78785_a(p_78088_7_);
      this.field_82893_b.func_78785_a(p_78088_7_);
   }

   public void func_78087_a(float p_78087_1_, float p_78087_2_, float p_78087_3_, float p_78087_4_, float p_78087_5_, float p_78087_6_, Entity p_78087_7_) {
      if (((EntityBat)p_78087_7_).func_82235_h()) {
         this.field_82895_a.field_78795_f = p_78087_5_ * 0.017453292F;
         this.field_82895_a.field_78796_g = 3.1415927F - p_78087_4_ * 0.017453292F;
         this.field_82895_a.field_78808_h = 3.1415927F;
         this.field_82895_a.func_78793_a(0.0F, -2.0F, 0.0F);
         this.field_82894_c.func_78793_a(-3.0F, 0.0F, 3.0F);
         this.field_82891_d.func_78793_a(3.0F, 0.0F, 3.0F);
         this.field_82893_b.field_78795_f = 3.1415927F;
         this.field_82894_c.field_78795_f = -0.15707964F;
         this.field_82894_c.field_78796_g = -1.2566371F;
         this.field_82892_e.field_78796_g = -1.7278761F;
         this.field_82891_d.field_78795_f = this.field_82894_c.field_78795_f;
         this.field_82891_d.field_78796_g = -this.field_82894_c.field_78796_g;
         this.field_82890_f.field_78796_g = -this.field_82892_e.field_78796_g;
      } else {
         this.field_82895_a.field_78795_f = p_78087_5_ * 0.017453292F;
         this.field_82895_a.field_78796_g = p_78087_4_ * 0.017453292F;
         this.field_82895_a.field_78808_h = 0.0F;
         this.field_82895_a.func_78793_a(0.0F, 0.0F, 0.0F);
         this.field_82894_c.func_78793_a(0.0F, 0.0F, 0.0F);
         this.field_82891_d.func_78793_a(0.0F, 0.0F, 0.0F);
         this.field_82893_b.field_78795_f = 0.7853982F + MathHelper.func_76134_b(p_78087_3_ * 0.1F) * 0.15F;
         this.field_82893_b.field_78796_g = 0.0F;
         this.field_82894_c.field_78796_g = MathHelper.func_76134_b(p_78087_3_ * 1.3F) * 3.1415927F * 0.25F;
         this.field_82891_d.field_78796_g = -this.field_82894_c.field_78796_g;
         this.field_82892_e.field_78796_g = this.field_82894_c.field_78796_g * 0.5F;
         this.field_82890_f.field_78796_g = -this.field_82894_c.field_78796_g * 0.5F;
      }

   }
}
