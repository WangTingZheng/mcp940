package net.minecraft.client.model;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.AbstractIllager;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.math.MathHelper;

public class ModelIllager extends ModelBase {
   public ModelRenderer field_191217_a;
   public ModelRenderer field_193775_b;
   public ModelRenderer field_191218_b;
   public ModelRenderer field_191219_c;
   public ModelRenderer field_191220_d;
   public ModelRenderer field_191221_e;
   public ModelRenderer field_191222_f;
   public ModelRenderer field_191223_g;
   public ModelRenderer field_191224_h;

   public ModelIllager(float p_i47227_1_, float p_i47227_2_, int p_i47227_3_, int p_i47227_4_) {
      this.field_191217_a = (new ModelRenderer(this)).func_78787_b(p_i47227_3_, p_i47227_4_);
      this.field_191217_a.func_78793_a(0.0F, 0.0F + p_i47227_2_, 0.0F);
      this.field_191217_a.func_78784_a(0, 0).func_78790_a(-4.0F, -10.0F, -4.0F, 8, 10, 8, p_i47227_1_);
      this.field_193775_b = (new ModelRenderer(this, 32, 0)).func_78787_b(p_i47227_3_, p_i47227_4_);
      this.field_193775_b.func_78790_a(-4.0F, -10.0F, -4.0F, 8, 12, 8, p_i47227_1_ + 0.45F);
      this.field_191217_a.func_78792_a(this.field_193775_b);
      this.field_193775_b.field_78806_j = false;
      this.field_191222_f = (new ModelRenderer(this)).func_78787_b(p_i47227_3_, p_i47227_4_);
      this.field_191222_f.func_78793_a(0.0F, p_i47227_2_ - 2.0F, 0.0F);
      this.field_191222_f.func_78784_a(24, 0).func_78790_a(-1.0F, -1.0F, -6.0F, 2, 4, 2, p_i47227_1_);
      this.field_191217_a.func_78792_a(this.field_191222_f);
      this.field_191218_b = (new ModelRenderer(this)).func_78787_b(p_i47227_3_, p_i47227_4_);
      this.field_191218_b.func_78793_a(0.0F, 0.0F + p_i47227_2_, 0.0F);
      this.field_191218_b.func_78784_a(16, 20).func_78790_a(-4.0F, 0.0F, -3.0F, 8, 12, 6, p_i47227_1_);
      this.field_191218_b.func_78784_a(0, 38).func_78790_a(-4.0F, 0.0F, -3.0F, 8, 18, 6, p_i47227_1_ + 0.5F);
      this.field_191219_c = (new ModelRenderer(this)).func_78787_b(p_i47227_3_, p_i47227_4_);
      this.field_191219_c.func_78793_a(0.0F, 0.0F + p_i47227_2_ + 2.0F, 0.0F);
      this.field_191219_c.func_78784_a(44, 22).func_78790_a(-8.0F, -2.0F, -2.0F, 4, 8, 4, p_i47227_1_);
      ModelRenderer modelrenderer = (new ModelRenderer(this, 44, 22)).func_78787_b(p_i47227_3_, p_i47227_4_);
      modelrenderer.field_78809_i = true;
      modelrenderer.func_78790_a(4.0F, -2.0F, -2.0F, 4, 8, 4, p_i47227_1_);
      this.field_191219_c.func_78792_a(modelrenderer);
      this.field_191219_c.func_78784_a(40, 38).func_78790_a(-4.0F, 2.0F, -2.0F, 8, 4, 4, p_i47227_1_);
      this.field_191220_d = (new ModelRenderer(this, 0, 22)).func_78787_b(p_i47227_3_, p_i47227_4_);
      this.field_191220_d.func_78793_a(-2.0F, 12.0F + p_i47227_2_, 0.0F);
      this.field_191220_d.func_78790_a(-2.0F, 0.0F, -2.0F, 4, 12, 4, p_i47227_1_);
      this.field_191221_e = (new ModelRenderer(this, 0, 22)).func_78787_b(p_i47227_3_, p_i47227_4_);
      this.field_191221_e.field_78809_i = true;
      this.field_191221_e.func_78793_a(2.0F, 12.0F + p_i47227_2_, 0.0F);
      this.field_191221_e.func_78790_a(-2.0F, 0.0F, -2.0F, 4, 12, 4, p_i47227_1_);
      this.field_191223_g = (new ModelRenderer(this, 40, 46)).func_78787_b(p_i47227_3_, p_i47227_4_);
      this.field_191223_g.func_78790_a(-3.0F, -2.0F, -2.0F, 4, 12, 4, p_i47227_1_);
      this.field_191223_g.func_78793_a(-5.0F, 2.0F + p_i47227_2_, 0.0F);
      this.field_191224_h = (new ModelRenderer(this, 40, 46)).func_78787_b(p_i47227_3_, p_i47227_4_);
      this.field_191224_h.field_78809_i = true;
      this.field_191224_h.func_78790_a(-1.0F, -2.0F, -2.0F, 4, 12, 4, p_i47227_1_);
      this.field_191224_h.func_78793_a(5.0F, 2.0F + p_i47227_2_, 0.0F);
   }

   public void func_78088_a(Entity p_78088_1_, float p_78088_2_, float p_78088_3_, float p_78088_4_, float p_78088_5_, float p_78088_6_, float p_78088_7_) {
      this.func_78087_a(p_78088_2_, p_78088_3_, p_78088_4_, p_78088_5_, p_78088_6_, p_78088_7_, p_78088_1_);
      this.field_191217_a.func_78785_a(p_78088_7_);
      this.field_191218_b.func_78785_a(p_78088_7_);
      this.field_191220_d.func_78785_a(p_78088_7_);
      this.field_191221_e.func_78785_a(p_78088_7_);
      AbstractIllager abstractillager = (AbstractIllager)p_78088_1_;
      if (abstractillager.func_193077_p() == AbstractIllager.IllagerArmPose.CROSSED) {
         this.field_191219_c.func_78785_a(p_78088_7_);
      } else {
         this.field_191223_g.func_78785_a(p_78088_7_);
         this.field_191224_h.func_78785_a(p_78088_7_);
      }

   }

   public void func_78087_a(float p_78087_1_, float p_78087_2_, float p_78087_3_, float p_78087_4_, float p_78087_5_, float p_78087_6_, Entity p_78087_7_) {
      this.field_191217_a.field_78796_g = p_78087_4_ * 0.017453292F;
      this.field_191217_a.field_78795_f = p_78087_5_ * 0.017453292F;
      this.field_191219_c.field_78797_d = 3.0F;
      this.field_191219_c.field_78798_e = -1.0F;
      this.field_191219_c.field_78795_f = -0.75F;
      this.field_191220_d.field_78795_f = MathHelper.func_76134_b(p_78087_1_ * 0.6662F) * 1.4F * p_78087_2_ * 0.5F;
      this.field_191221_e.field_78795_f = MathHelper.func_76134_b(p_78087_1_ * 0.6662F + 3.1415927F) * 1.4F * p_78087_2_ * 0.5F;
      this.field_191220_d.field_78796_g = 0.0F;
      this.field_191221_e.field_78796_g = 0.0F;
      AbstractIllager.IllagerArmPose abstractillager$illagerarmpose = ((AbstractIllager)p_78087_7_).func_193077_p();
      if (abstractillager$illagerarmpose == AbstractIllager.IllagerArmPose.ATTACKING) {
         float f = MathHelper.func_76126_a(this.field_78095_p * 3.1415927F);
         float f1 = MathHelper.func_76126_a((1.0F - (1.0F - this.field_78095_p) * (1.0F - this.field_78095_p)) * 3.1415927F);
         this.field_191223_g.field_78808_h = 0.0F;
         this.field_191224_h.field_78808_h = 0.0F;
         this.field_191223_g.field_78796_g = 0.15707964F;
         this.field_191224_h.field_78796_g = -0.15707964F;
         if (((EntityLivingBase)p_78087_7_).func_184591_cq() == EnumHandSide.RIGHT) {
            this.field_191223_g.field_78795_f = -1.8849558F + MathHelper.func_76134_b(p_78087_3_ * 0.09F) * 0.15F;
            this.field_191224_h.field_78795_f = -0.0F + MathHelper.func_76134_b(p_78087_3_ * 0.19F) * 0.5F;
            this.field_191223_g.field_78795_f += f * 2.2F - f1 * 0.4F;
            this.field_191224_h.field_78795_f += f * 1.2F - f1 * 0.4F;
         } else {
            this.field_191223_g.field_78795_f = -0.0F + MathHelper.func_76134_b(p_78087_3_ * 0.19F) * 0.5F;
            this.field_191224_h.field_78795_f = -1.8849558F + MathHelper.func_76134_b(p_78087_3_ * 0.09F) * 0.15F;
            this.field_191223_g.field_78795_f += f * 1.2F - f1 * 0.4F;
            this.field_191224_h.field_78795_f += f * 2.2F - f1 * 0.4F;
         }

         this.field_191223_g.field_78808_h += MathHelper.func_76134_b(p_78087_3_ * 0.09F) * 0.05F + 0.05F;
         this.field_191224_h.field_78808_h -= MathHelper.func_76134_b(p_78087_3_ * 0.09F) * 0.05F + 0.05F;
         this.field_191223_g.field_78795_f += MathHelper.func_76126_a(p_78087_3_ * 0.067F) * 0.05F;
         this.field_191224_h.field_78795_f -= MathHelper.func_76126_a(p_78087_3_ * 0.067F) * 0.05F;
      } else if (abstractillager$illagerarmpose == AbstractIllager.IllagerArmPose.SPELLCASTING) {
         this.field_191223_g.field_78798_e = 0.0F;
         this.field_191223_g.field_78800_c = -5.0F;
         this.field_191224_h.field_78798_e = 0.0F;
         this.field_191224_h.field_78800_c = 5.0F;
         this.field_191223_g.field_78795_f = MathHelper.func_76134_b(p_78087_3_ * 0.6662F) * 0.25F;
         this.field_191224_h.field_78795_f = MathHelper.func_76134_b(p_78087_3_ * 0.6662F) * 0.25F;
         this.field_191223_g.field_78808_h = 2.3561945F;
         this.field_191224_h.field_78808_h = -2.3561945F;
         this.field_191223_g.field_78796_g = 0.0F;
         this.field_191224_h.field_78796_g = 0.0F;
      } else if (abstractillager$illagerarmpose == AbstractIllager.IllagerArmPose.BOW_AND_ARROW) {
         this.field_191223_g.field_78796_g = -0.1F + this.field_191217_a.field_78796_g;
         this.field_191223_g.field_78795_f = -1.5707964F + this.field_191217_a.field_78795_f;
         this.field_191224_h.field_78795_f = -0.9424779F + this.field_191217_a.field_78795_f;
         this.field_191224_h.field_78796_g = this.field_191217_a.field_78796_g - 0.4F;
         this.field_191224_h.field_78808_h = 1.5707964F;
      }

   }

   public ModelRenderer func_191216_a(EnumHandSide p_191216_1_) {
      return p_191216_1_ == EnumHandSide.LEFT ? this.field_191224_h : this.field_191223_g;
   }
}
