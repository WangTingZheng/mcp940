package net.minecraft.client.model;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityIronGolem;

public class ModelIronGolem extends ModelBase {
   public ModelRenderer field_78178_a;
   public ModelRenderer field_78176_b;
   public ModelRenderer field_78177_c;
   public ModelRenderer field_78174_d;
   public ModelRenderer field_78175_e;
   public ModelRenderer field_78173_f;

   public ModelIronGolem() {
      this(0.0F);
   }

   public ModelIronGolem(float p_i1161_1_) {
      this(p_i1161_1_, -7.0F);
   }

   public ModelIronGolem(float p_i46362_1_, float p_i46362_2_) {
      int i = 128;
      int j = 128;
      this.field_78178_a = (new ModelRenderer(this)).func_78787_b(128, 128);
      this.field_78178_a.func_78793_a(0.0F, 0.0F + p_i46362_2_, -2.0F);
      this.field_78178_a.func_78784_a(0, 0).func_78790_a(-4.0F, -12.0F, -5.5F, 8, 10, 8, p_i46362_1_);
      this.field_78178_a.func_78784_a(24, 0).func_78790_a(-1.0F, -5.0F, -7.5F, 2, 4, 2, p_i46362_1_);
      this.field_78176_b = (new ModelRenderer(this)).func_78787_b(128, 128);
      this.field_78176_b.func_78793_a(0.0F, 0.0F + p_i46362_2_, 0.0F);
      this.field_78176_b.func_78784_a(0, 40).func_78790_a(-9.0F, -2.0F, -6.0F, 18, 12, 11, p_i46362_1_);
      this.field_78176_b.func_78784_a(0, 70).func_78790_a(-4.5F, 10.0F, -3.0F, 9, 5, 6, p_i46362_1_ + 0.5F);
      this.field_78177_c = (new ModelRenderer(this)).func_78787_b(128, 128);
      this.field_78177_c.func_78793_a(0.0F, -7.0F, 0.0F);
      this.field_78177_c.func_78784_a(60, 21).func_78790_a(-13.0F, -2.5F, -3.0F, 4, 30, 6, p_i46362_1_);
      this.field_78174_d = (new ModelRenderer(this)).func_78787_b(128, 128);
      this.field_78174_d.func_78793_a(0.0F, -7.0F, 0.0F);
      this.field_78174_d.func_78784_a(60, 58).func_78790_a(9.0F, -2.5F, -3.0F, 4, 30, 6, p_i46362_1_);
      this.field_78175_e = (new ModelRenderer(this, 0, 22)).func_78787_b(128, 128);
      this.field_78175_e.func_78793_a(-4.0F, 18.0F + p_i46362_2_, 0.0F);
      this.field_78175_e.func_78784_a(37, 0).func_78790_a(-3.5F, -3.0F, -3.0F, 6, 16, 5, p_i46362_1_);
      this.field_78173_f = (new ModelRenderer(this, 0, 22)).func_78787_b(128, 128);
      this.field_78173_f.field_78809_i = true;
      this.field_78173_f.func_78784_a(60, 0).func_78793_a(5.0F, 18.0F + p_i46362_2_, 0.0F);
      this.field_78173_f.func_78790_a(-3.5F, -3.0F, -3.0F, 6, 16, 5, p_i46362_1_);
   }

   public void func_78088_a(Entity p_78088_1_, float p_78088_2_, float p_78088_3_, float p_78088_4_, float p_78088_5_, float p_78088_6_, float p_78088_7_) {
      this.func_78087_a(p_78088_2_, p_78088_3_, p_78088_4_, p_78088_5_, p_78088_6_, p_78088_7_, p_78088_1_);
      this.field_78178_a.func_78785_a(p_78088_7_);
      this.field_78176_b.func_78785_a(p_78088_7_);
      this.field_78175_e.func_78785_a(p_78088_7_);
      this.field_78173_f.func_78785_a(p_78088_7_);
      this.field_78177_c.func_78785_a(p_78088_7_);
      this.field_78174_d.func_78785_a(p_78088_7_);
   }

   public void func_78087_a(float p_78087_1_, float p_78087_2_, float p_78087_3_, float p_78087_4_, float p_78087_5_, float p_78087_6_, Entity p_78087_7_) {
      this.field_78178_a.field_78796_g = p_78087_4_ * 0.017453292F;
      this.field_78178_a.field_78795_f = p_78087_5_ * 0.017453292F;
      this.field_78175_e.field_78795_f = -1.5F * this.func_78172_a(p_78087_1_, 13.0F) * p_78087_2_;
      this.field_78173_f.field_78795_f = 1.5F * this.func_78172_a(p_78087_1_, 13.0F) * p_78087_2_;
      this.field_78175_e.field_78796_g = 0.0F;
      this.field_78173_f.field_78796_g = 0.0F;
   }

   public void func_78086_a(EntityLivingBase p_78086_1_, float p_78086_2_, float p_78086_3_, float p_78086_4_) {
      EntityIronGolem entityirongolem = (EntityIronGolem)p_78086_1_;
      int i = entityirongolem.func_70854_o();
      if (i > 0) {
         this.field_78177_c.field_78795_f = -2.0F + 1.5F * this.func_78172_a((float)i - p_78086_4_, 10.0F);
         this.field_78174_d.field_78795_f = -2.0F + 1.5F * this.func_78172_a((float)i - p_78086_4_, 10.0F);
      } else {
         int j = entityirongolem.func_70853_p();
         if (j > 0) {
            this.field_78177_c.field_78795_f = -0.8F + 0.025F * this.func_78172_a((float)j, 70.0F);
            this.field_78174_d.field_78795_f = 0.0F;
         } else {
            this.field_78177_c.field_78795_f = (-0.2F + 1.5F * this.func_78172_a(p_78086_2_, 13.0F)) * p_78086_3_;
            this.field_78174_d.field_78795_f = (-0.2F - 1.5F * this.func_78172_a(p_78086_2_, 13.0F)) * p_78086_3_;
         }
      }

   }

   private float func_78172_a(float p_78172_1_, float p_78172_2_) {
      return (Math.abs(p_78172_1_ % p_78172_2_ - p_78172_2_ * 0.5F) - p_78172_2_ * 0.25F) / (p_78172_2_ * 0.25F);
   }
}
