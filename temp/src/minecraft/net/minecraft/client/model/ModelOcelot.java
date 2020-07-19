package net.minecraft.client.model;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.util.math.MathHelper;

public class ModelOcelot extends ModelBase {
   private final ModelRenderer field_78161_a;
   private final ModelRenderer field_78159_b;
   private final ModelRenderer field_78160_c;
   private final ModelRenderer field_78157_d;
   private final ModelRenderer field_78158_e;
   private final ModelRenderer field_78155_f;
   private final ModelRenderer field_78156_g;
   private final ModelRenderer field_78162_h;
   private int field_78163_i = 1;

   public ModelOcelot() {
      this.func_78085_a("head.main", 0, 0);
      this.func_78085_a("head.nose", 0, 24);
      this.func_78085_a("head.ear1", 0, 10);
      this.func_78085_a("head.ear2", 6, 10);
      this.field_78156_g = new ModelRenderer(this, "head");
      this.field_78156_g.func_78786_a("main", -2.5F, -2.0F, -3.0F, 5, 4, 5);
      this.field_78156_g.func_78786_a("nose", -1.5F, 0.0F, -4.0F, 3, 2, 2);
      this.field_78156_g.func_78786_a("ear1", -2.0F, -3.0F, 0.0F, 1, 1, 2);
      this.field_78156_g.func_78786_a("ear2", 1.0F, -3.0F, 0.0F, 1, 1, 2);
      this.field_78156_g.func_78793_a(0.0F, 15.0F, -9.0F);
      this.field_78162_h = new ModelRenderer(this, 20, 0);
      this.field_78162_h.func_78790_a(-2.0F, 3.0F, -8.0F, 4, 16, 6, 0.0F);
      this.field_78162_h.func_78793_a(0.0F, 12.0F, -10.0F);
      this.field_78158_e = new ModelRenderer(this, 0, 15);
      this.field_78158_e.func_78789_a(-0.5F, 0.0F, 0.0F, 1, 8, 1);
      this.field_78158_e.field_78795_f = 0.9F;
      this.field_78158_e.func_78793_a(0.0F, 15.0F, 8.0F);
      this.field_78155_f = new ModelRenderer(this, 4, 15);
      this.field_78155_f.func_78789_a(-0.5F, 0.0F, 0.0F, 1, 8, 1);
      this.field_78155_f.func_78793_a(0.0F, 20.0F, 14.0F);
      this.field_78161_a = new ModelRenderer(this, 8, 13);
      this.field_78161_a.func_78789_a(-1.0F, 0.0F, 1.0F, 2, 6, 2);
      this.field_78161_a.func_78793_a(1.1F, 18.0F, 5.0F);
      this.field_78159_b = new ModelRenderer(this, 8, 13);
      this.field_78159_b.func_78789_a(-1.0F, 0.0F, 1.0F, 2, 6, 2);
      this.field_78159_b.func_78793_a(-1.1F, 18.0F, 5.0F);
      this.field_78160_c = new ModelRenderer(this, 40, 0);
      this.field_78160_c.func_78789_a(-1.0F, 0.0F, 0.0F, 2, 10, 2);
      this.field_78160_c.func_78793_a(1.2F, 13.8F, -5.0F);
      this.field_78157_d = new ModelRenderer(this, 40, 0);
      this.field_78157_d.func_78789_a(-1.0F, 0.0F, 0.0F, 2, 10, 2);
      this.field_78157_d.func_78793_a(-1.2F, 13.8F, -5.0F);
   }

   public void func_78088_a(Entity p_78088_1_, float p_78088_2_, float p_78088_3_, float p_78088_4_, float p_78088_5_, float p_78088_6_, float p_78088_7_) {
      this.func_78087_a(p_78088_2_, p_78088_3_, p_78088_4_, p_78088_5_, p_78088_6_, p_78088_7_, p_78088_1_);
      if (this.field_78091_s) {
         float f = 2.0F;
         GlStateManager.func_179094_E();
         GlStateManager.func_179152_a(0.75F, 0.75F, 0.75F);
         GlStateManager.func_179109_b(0.0F, 10.0F * p_78088_7_, 4.0F * p_78088_7_);
         this.field_78156_g.func_78785_a(p_78088_7_);
         GlStateManager.func_179121_F();
         GlStateManager.func_179094_E();
         GlStateManager.func_179152_a(0.5F, 0.5F, 0.5F);
         GlStateManager.func_179109_b(0.0F, 24.0F * p_78088_7_, 0.0F);
         this.field_78162_h.func_78785_a(p_78088_7_);
         this.field_78161_a.func_78785_a(p_78088_7_);
         this.field_78159_b.func_78785_a(p_78088_7_);
         this.field_78160_c.func_78785_a(p_78088_7_);
         this.field_78157_d.func_78785_a(p_78088_7_);
         this.field_78158_e.func_78785_a(p_78088_7_);
         this.field_78155_f.func_78785_a(p_78088_7_);
         GlStateManager.func_179121_F();
      } else {
         this.field_78156_g.func_78785_a(p_78088_7_);
         this.field_78162_h.func_78785_a(p_78088_7_);
         this.field_78158_e.func_78785_a(p_78088_7_);
         this.field_78155_f.func_78785_a(p_78088_7_);
         this.field_78161_a.func_78785_a(p_78088_7_);
         this.field_78159_b.func_78785_a(p_78088_7_);
         this.field_78160_c.func_78785_a(p_78088_7_);
         this.field_78157_d.func_78785_a(p_78088_7_);
      }

   }

   public void func_78087_a(float p_78087_1_, float p_78087_2_, float p_78087_3_, float p_78087_4_, float p_78087_5_, float p_78087_6_, Entity p_78087_7_) {
      this.field_78156_g.field_78795_f = p_78087_5_ * 0.017453292F;
      this.field_78156_g.field_78796_g = p_78087_4_ * 0.017453292F;
      if (this.field_78163_i != 3) {
         this.field_78162_h.field_78795_f = 1.5707964F;
         if (this.field_78163_i == 2) {
            this.field_78161_a.field_78795_f = MathHelper.func_76134_b(p_78087_1_ * 0.6662F) * p_78087_2_;
            this.field_78159_b.field_78795_f = MathHelper.func_76134_b(p_78087_1_ * 0.6662F + 0.3F) * p_78087_2_;
            this.field_78160_c.field_78795_f = MathHelper.func_76134_b(p_78087_1_ * 0.6662F + 3.1415927F + 0.3F) * p_78087_2_;
            this.field_78157_d.field_78795_f = MathHelper.func_76134_b(p_78087_1_ * 0.6662F + 3.1415927F) * p_78087_2_;
            this.field_78155_f.field_78795_f = 1.7278761F + 0.31415927F * MathHelper.func_76134_b(p_78087_1_) * p_78087_2_;
         } else {
            this.field_78161_a.field_78795_f = MathHelper.func_76134_b(p_78087_1_ * 0.6662F) * p_78087_2_;
            this.field_78159_b.field_78795_f = MathHelper.func_76134_b(p_78087_1_ * 0.6662F + 3.1415927F) * p_78087_2_;
            this.field_78160_c.field_78795_f = MathHelper.func_76134_b(p_78087_1_ * 0.6662F + 3.1415927F) * p_78087_2_;
            this.field_78157_d.field_78795_f = MathHelper.func_76134_b(p_78087_1_ * 0.6662F) * p_78087_2_;
            if (this.field_78163_i == 1) {
               this.field_78155_f.field_78795_f = 1.7278761F + 0.7853982F * MathHelper.func_76134_b(p_78087_1_) * p_78087_2_;
            } else {
               this.field_78155_f.field_78795_f = 1.7278761F + 0.47123894F * MathHelper.func_76134_b(p_78087_1_) * p_78087_2_;
            }
         }
      }

   }

   public void func_78086_a(EntityLivingBase p_78086_1_, float p_78086_2_, float p_78086_3_, float p_78086_4_) {
      EntityOcelot entityocelot = (EntityOcelot)p_78086_1_;
      this.field_78162_h.field_78797_d = 12.0F;
      this.field_78162_h.field_78798_e = -10.0F;
      this.field_78156_g.field_78797_d = 15.0F;
      this.field_78156_g.field_78798_e = -9.0F;
      this.field_78158_e.field_78797_d = 15.0F;
      this.field_78158_e.field_78798_e = 8.0F;
      this.field_78155_f.field_78797_d = 20.0F;
      this.field_78155_f.field_78798_e = 14.0F;
      this.field_78160_c.field_78797_d = 13.8F;
      this.field_78160_c.field_78798_e = -5.0F;
      this.field_78157_d.field_78797_d = 13.8F;
      this.field_78157_d.field_78798_e = -5.0F;
      this.field_78161_a.field_78797_d = 18.0F;
      this.field_78161_a.field_78798_e = 5.0F;
      this.field_78159_b.field_78797_d = 18.0F;
      this.field_78159_b.field_78798_e = 5.0F;
      this.field_78158_e.field_78795_f = 0.9F;
      if (entityocelot.func_70093_af()) {
         ++this.field_78162_h.field_78797_d;
         this.field_78156_g.field_78797_d += 2.0F;
         ++this.field_78158_e.field_78797_d;
         this.field_78155_f.field_78797_d += -4.0F;
         this.field_78155_f.field_78798_e += 2.0F;
         this.field_78158_e.field_78795_f = 1.5707964F;
         this.field_78155_f.field_78795_f = 1.5707964F;
         this.field_78163_i = 0;
      } else if (entityocelot.func_70051_ag()) {
         this.field_78155_f.field_78797_d = this.field_78158_e.field_78797_d;
         this.field_78155_f.field_78798_e += 2.0F;
         this.field_78158_e.field_78795_f = 1.5707964F;
         this.field_78155_f.field_78795_f = 1.5707964F;
         this.field_78163_i = 2;
      } else if (entityocelot.func_70906_o()) {
         this.field_78162_h.field_78795_f = 0.7853982F;
         this.field_78162_h.field_78797_d += -4.0F;
         this.field_78162_h.field_78798_e += 5.0F;
         this.field_78156_g.field_78797_d += -3.3F;
         ++this.field_78156_g.field_78798_e;
         this.field_78158_e.field_78797_d += 8.0F;
         this.field_78158_e.field_78798_e += -2.0F;
         this.field_78155_f.field_78797_d += 2.0F;
         this.field_78155_f.field_78798_e += -0.8F;
         this.field_78158_e.field_78795_f = 1.7278761F;
         this.field_78155_f.field_78795_f = 2.670354F;
         this.field_78160_c.field_78795_f = -0.15707964F;
         this.field_78160_c.field_78797_d = 15.8F;
         this.field_78160_c.field_78798_e = -7.0F;
         this.field_78157_d.field_78795_f = -0.15707964F;
         this.field_78157_d.field_78797_d = 15.8F;
         this.field_78157_d.field_78798_e = -7.0F;
         this.field_78161_a.field_78795_f = -1.5707964F;
         this.field_78161_a.field_78797_d = 21.0F;
         this.field_78161_a.field_78798_e = 1.0F;
         this.field_78159_b.field_78795_f = -1.5707964F;
         this.field_78159_b.field_78797_d = 21.0F;
         this.field_78159_b.field_78798_e = 1.0F;
         this.field_78163_i = 3;
      } else {
         this.field_78163_i = 1;
      }

   }
}
