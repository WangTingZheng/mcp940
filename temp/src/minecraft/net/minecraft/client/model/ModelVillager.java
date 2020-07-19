package net.minecraft.client.model;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class ModelVillager extends ModelBase {
   public ModelRenderer field_78191_a;
   public ModelRenderer field_78189_b;
   public ModelRenderer field_78190_c;
   public ModelRenderer field_78187_d;
   public ModelRenderer field_78188_e;
   public ModelRenderer field_82898_f;

   public ModelVillager(float p_i1163_1_) {
      this(p_i1163_1_, 0.0F, 64, 64);
   }

   public ModelVillager(float p_i1164_1_, float p_i1164_2_, int p_i1164_3_, int p_i1164_4_) {
      this.field_78191_a = (new ModelRenderer(this)).func_78787_b(p_i1164_3_, p_i1164_4_);
      this.field_78191_a.func_78793_a(0.0F, 0.0F + p_i1164_2_, 0.0F);
      this.field_78191_a.func_78784_a(0, 0).func_78790_a(-4.0F, -10.0F, -4.0F, 8, 10, 8, p_i1164_1_);
      this.field_82898_f = (new ModelRenderer(this)).func_78787_b(p_i1164_3_, p_i1164_4_);
      this.field_82898_f.func_78793_a(0.0F, p_i1164_2_ - 2.0F, 0.0F);
      this.field_82898_f.func_78784_a(24, 0).func_78790_a(-1.0F, -1.0F, -6.0F, 2, 4, 2, p_i1164_1_);
      this.field_78191_a.func_78792_a(this.field_82898_f);
      this.field_78189_b = (new ModelRenderer(this)).func_78787_b(p_i1164_3_, p_i1164_4_);
      this.field_78189_b.func_78793_a(0.0F, 0.0F + p_i1164_2_, 0.0F);
      this.field_78189_b.func_78784_a(16, 20).func_78790_a(-4.0F, 0.0F, -3.0F, 8, 12, 6, p_i1164_1_);
      this.field_78189_b.func_78784_a(0, 38).func_78790_a(-4.0F, 0.0F, -3.0F, 8, 18, 6, p_i1164_1_ + 0.5F);
      this.field_78190_c = (new ModelRenderer(this)).func_78787_b(p_i1164_3_, p_i1164_4_);
      this.field_78190_c.func_78793_a(0.0F, 0.0F + p_i1164_2_ + 2.0F, 0.0F);
      this.field_78190_c.func_78784_a(44, 22).func_78790_a(-8.0F, -2.0F, -2.0F, 4, 8, 4, p_i1164_1_);
      this.field_78190_c.func_78784_a(44, 22).func_78790_a(4.0F, -2.0F, -2.0F, 4, 8, 4, p_i1164_1_);
      this.field_78190_c.func_78784_a(40, 38).func_78790_a(-4.0F, 2.0F, -2.0F, 8, 4, 4, p_i1164_1_);
      this.field_78187_d = (new ModelRenderer(this, 0, 22)).func_78787_b(p_i1164_3_, p_i1164_4_);
      this.field_78187_d.func_78793_a(-2.0F, 12.0F + p_i1164_2_, 0.0F);
      this.field_78187_d.func_78790_a(-2.0F, 0.0F, -2.0F, 4, 12, 4, p_i1164_1_);
      this.field_78188_e = (new ModelRenderer(this, 0, 22)).func_78787_b(p_i1164_3_, p_i1164_4_);
      this.field_78188_e.field_78809_i = true;
      this.field_78188_e.func_78793_a(2.0F, 12.0F + p_i1164_2_, 0.0F);
      this.field_78188_e.func_78790_a(-2.0F, 0.0F, -2.0F, 4, 12, 4, p_i1164_1_);
   }

   public void func_78088_a(Entity p_78088_1_, float p_78088_2_, float p_78088_3_, float p_78088_4_, float p_78088_5_, float p_78088_6_, float p_78088_7_) {
      this.func_78087_a(p_78088_2_, p_78088_3_, p_78088_4_, p_78088_5_, p_78088_6_, p_78088_7_, p_78088_1_);
      this.field_78191_a.func_78785_a(p_78088_7_);
      this.field_78189_b.func_78785_a(p_78088_7_);
      this.field_78187_d.func_78785_a(p_78088_7_);
      this.field_78188_e.func_78785_a(p_78088_7_);
      this.field_78190_c.func_78785_a(p_78088_7_);
   }

   public void func_78087_a(float p_78087_1_, float p_78087_2_, float p_78087_3_, float p_78087_4_, float p_78087_5_, float p_78087_6_, Entity p_78087_7_) {
      this.field_78191_a.field_78796_g = p_78087_4_ * 0.017453292F;
      this.field_78191_a.field_78795_f = p_78087_5_ * 0.017453292F;
      this.field_78190_c.field_78797_d = 3.0F;
      this.field_78190_c.field_78798_e = -1.0F;
      this.field_78190_c.field_78795_f = -0.75F;
      this.field_78187_d.field_78795_f = MathHelper.func_76134_b(p_78087_1_ * 0.6662F) * 1.4F * p_78087_2_ * 0.5F;
      this.field_78188_e.field_78795_f = MathHelper.func_76134_b(p_78087_1_ * 0.6662F + 3.1415927F) * 1.4F * p_78087_2_ * 0.5F;
      this.field_78187_d.field_78796_g = 0.0F;
      this.field_78188_e.field_78796_g = 0.0F;
   }
}
