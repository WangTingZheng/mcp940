package net.minecraft.client.model;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class ModelSnowMan extends ModelBase {
   public ModelRenderer field_78196_a;
   public ModelRenderer field_78194_b;
   public ModelRenderer field_78195_c;
   public ModelRenderer field_78192_d;
   public ModelRenderer field_78193_e;

   public ModelSnowMan() {
      float f = 4.0F;
      float f1 = 0.0F;
      this.field_78195_c = (new ModelRenderer(this, 0, 0)).func_78787_b(64, 64);
      this.field_78195_c.func_78790_a(-4.0F, -8.0F, -4.0F, 8, 8, 8, -0.5F);
      this.field_78195_c.func_78793_a(0.0F, 4.0F, 0.0F);
      this.field_78192_d = (new ModelRenderer(this, 32, 0)).func_78787_b(64, 64);
      this.field_78192_d.func_78790_a(-1.0F, 0.0F, -1.0F, 12, 2, 2, -0.5F);
      this.field_78192_d.func_78793_a(0.0F, 6.0F, 0.0F);
      this.field_78193_e = (new ModelRenderer(this, 32, 0)).func_78787_b(64, 64);
      this.field_78193_e.func_78790_a(-1.0F, 0.0F, -1.0F, 12, 2, 2, -0.5F);
      this.field_78193_e.func_78793_a(0.0F, 6.0F, 0.0F);
      this.field_78196_a = (new ModelRenderer(this, 0, 16)).func_78787_b(64, 64);
      this.field_78196_a.func_78790_a(-5.0F, -10.0F, -5.0F, 10, 10, 10, -0.5F);
      this.field_78196_a.func_78793_a(0.0F, 13.0F, 0.0F);
      this.field_78194_b = (new ModelRenderer(this, 0, 36)).func_78787_b(64, 64);
      this.field_78194_b.func_78790_a(-6.0F, -12.0F, -6.0F, 12, 12, 12, -0.5F);
      this.field_78194_b.func_78793_a(0.0F, 24.0F, 0.0F);
   }

   public void func_78087_a(float p_78087_1_, float p_78087_2_, float p_78087_3_, float p_78087_4_, float p_78087_5_, float p_78087_6_, Entity p_78087_7_) {
      super.func_78087_a(p_78087_1_, p_78087_2_, p_78087_3_, p_78087_4_, p_78087_5_, p_78087_6_, p_78087_7_);
      this.field_78195_c.field_78796_g = p_78087_4_ * 0.017453292F;
      this.field_78195_c.field_78795_f = p_78087_5_ * 0.017453292F;
      this.field_78196_a.field_78796_g = p_78087_4_ * 0.017453292F * 0.25F;
      float f = MathHelper.func_76126_a(this.field_78196_a.field_78796_g);
      float f1 = MathHelper.func_76134_b(this.field_78196_a.field_78796_g);
      this.field_78192_d.field_78808_h = 1.0F;
      this.field_78193_e.field_78808_h = -1.0F;
      this.field_78192_d.field_78796_g = 0.0F + this.field_78196_a.field_78796_g;
      this.field_78193_e.field_78796_g = 3.1415927F + this.field_78196_a.field_78796_g;
      this.field_78192_d.field_78800_c = f1 * 5.0F;
      this.field_78192_d.field_78798_e = -f * 5.0F;
      this.field_78193_e.field_78800_c = -f1 * 5.0F;
      this.field_78193_e.field_78798_e = f * 5.0F;
   }

   public void func_78088_a(Entity p_78088_1_, float p_78088_2_, float p_78088_3_, float p_78088_4_, float p_78088_5_, float p_78088_6_, float p_78088_7_) {
      this.func_78087_a(p_78088_2_, p_78088_3_, p_78088_4_, p_78088_5_, p_78088_6_, p_78088_7_, p_78088_1_);
      this.field_78196_a.func_78785_a(p_78088_7_);
      this.field_78194_b.func_78785_a(p_78088_7_);
      this.field_78195_c.func_78785_a(p_78088_7_);
      this.field_78192_d.func_78785_a(p_78088_7_);
      this.field_78193_e.func_78785_a(p_78088_7_);
   }
}
