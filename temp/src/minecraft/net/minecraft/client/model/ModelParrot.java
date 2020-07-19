package net.minecraft.client.model;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityParrot;
import net.minecraft.util.math.MathHelper;

public class ModelParrot extends ModelBase {
   ModelRenderer field_192764_a;
   ModelRenderer field_192765_b;
   ModelRenderer field_192766_c;
   ModelRenderer field_192767_d;
   ModelRenderer field_192768_e;
   ModelRenderer field_192769_f;
   ModelRenderer field_192770_g;
   ModelRenderer field_192771_h;
   ModelRenderer field_192772_i;
   ModelRenderer field_192773_j;
   ModelRenderer field_192774_k;
   private ModelParrot.State field_192775_l = ModelParrot.State.STANDING;

   public ModelParrot() {
      this.field_78090_t = 32;
      this.field_78089_u = 32;
      this.field_192764_a = new ModelRenderer(this, 2, 8);
      this.field_192764_a.func_78789_a(-1.5F, 0.0F, -1.5F, 3, 6, 3);
      this.field_192764_a.func_78793_a(0.0F, 16.5F, -3.0F);
      this.field_192765_b = new ModelRenderer(this, 22, 1);
      this.field_192765_b.func_78789_a(-1.5F, -1.0F, -1.0F, 3, 4, 1);
      this.field_192765_b.func_78793_a(0.0F, 21.07F, 1.16F);
      this.field_192766_c = new ModelRenderer(this, 19, 8);
      this.field_192766_c.func_78789_a(-0.5F, 0.0F, -1.5F, 1, 5, 3);
      this.field_192766_c.func_78793_a(1.5F, 16.94F, -2.76F);
      this.field_192767_d = new ModelRenderer(this, 19, 8);
      this.field_192767_d.func_78789_a(-0.5F, 0.0F, -1.5F, 1, 5, 3);
      this.field_192767_d.func_78793_a(-1.5F, 16.94F, -2.76F);
      this.field_192768_e = new ModelRenderer(this, 2, 2);
      this.field_192768_e.func_78789_a(-1.0F, -1.5F, -1.0F, 2, 3, 2);
      this.field_192768_e.func_78793_a(0.0F, 15.69F, -2.76F);
      this.field_192769_f = new ModelRenderer(this, 10, 0);
      this.field_192769_f.func_78789_a(-1.0F, -0.5F, -2.0F, 2, 1, 4);
      this.field_192769_f.func_78793_a(0.0F, -2.0F, -1.0F);
      this.field_192768_e.func_78792_a(this.field_192769_f);
      this.field_192770_g = new ModelRenderer(this, 11, 7);
      this.field_192770_g.func_78789_a(-0.5F, -1.0F, -0.5F, 1, 2, 1);
      this.field_192770_g.func_78793_a(0.0F, -0.5F, -1.5F);
      this.field_192768_e.func_78792_a(this.field_192770_g);
      this.field_192771_h = new ModelRenderer(this, 16, 7);
      this.field_192771_h.func_78789_a(-0.5F, 0.0F, -0.5F, 1, 2, 1);
      this.field_192771_h.func_78793_a(0.0F, -1.75F, -2.45F);
      this.field_192768_e.func_78792_a(this.field_192771_h);
      this.field_192772_i = new ModelRenderer(this, 2, 18);
      this.field_192772_i.func_78789_a(0.0F, -4.0F, -2.0F, 0, 5, 4);
      this.field_192772_i.func_78793_a(0.0F, -2.15F, 0.15F);
      this.field_192768_e.func_78792_a(this.field_192772_i);
      this.field_192773_j = new ModelRenderer(this, 14, 18);
      this.field_192773_j.func_78789_a(-0.5F, 0.0F, -0.5F, 1, 2, 1);
      this.field_192773_j.func_78793_a(1.0F, 22.0F, -1.05F);
      this.field_192774_k = new ModelRenderer(this, 14, 18);
      this.field_192774_k.func_78789_a(-0.5F, 0.0F, -0.5F, 1, 2, 1);
      this.field_192774_k.func_78793_a(-1.0F, 22.0F, -1.05F);
   }

   public void func_78088_a(Entity p_78088_1_, float p_78088_2_, float p_78088_3_, float p_78088_4_, float p_78088_5_, float p_78088_6_, float p_78088_7_) {
      this.field_192764_a.func_78785_a(p_78088_7_);
      this.field_192766_c.func_78785_a(p_78088_7_);
      this.field_192767_d.func_78785_a(p_78088_7_);
      this.field_192765_b.func_78785_a(p_78088_7_);
      this.field_192768_e.func_78785_a(p_78088_7_);
      this.field_192773_j.func_78785_a(p_78088_7_);
      this.field_192774_k.func_78785_a(p_78088_7_);
   }

   public void func_78087_a(float p_78087_1_, float p_78087_2_, float p_78087_3_, float p_78087_4_, float p_78087_5_, float p_78087_6_, Entity p_78087_7_) {
      float f = p_78087_3_ * 0.3F;
      this.field_192768_e.field_78795_f = p_78087_5_ * 0.017453292F;
      this.field_192768_e.field_78796_g = p_78087_4_ * 0.017453292F;
      this.field_192768_e.field_78808_h = 0.0F;
      this.field_192768_e.field_78800_c = 0.0F;
      this.field_192764_a.field_78800_c = 0.0F;
      this.field_192765_b.field_78800_c = 0.0F;
      this.field_192767_d.field_78800_c = -1.5F;
      this.field_192766_c.field_78800_c = 1.5F;
      if (this.field_192775_l != ModelParrot.State.FLYING) {
         if (this.field_192775_l == ModelParrot.State.SITTING) {
            return;
         }

         if (this.field_192775_l == ModelParrot.State.PARTY) {
            float f1 = MathHelper.func_76134_b((float)p_78087_7_.field_70173_aa);
            float f2 = MathHelper.func_76126_a((float)p_78087_7_.field_70173_aa);
            this.field_192768_e.field_78800_c = f1;
            this.field_192768_e.field_78797_d = 15.69F + f2;
            this.field_192768_e.field_78795_f = 0.0F;
            this.field_192768_e.field_78796_g = 0.0F;
            this.field_192768_e.field_78808_h = MathHelper.func_76126_a((float)p_78087_7_.field_70173_aa) * 0.4F;
            this.field_192764_a.field_78800_c = f1;
            this.field_192764_a.field_78797_d = 16.5F + f2;
            this.field_192766_c.field_78808_h = -0.0873F - p_78087_3_;
            this.field_192766_c.field_78800_c = 1.5F + f1;
            this.field_192766_c.field_78797_d = 16.94F + f2;
            this.field_192767_d.field_78808_h = 0.0873F + p_78087_3_;
            this.field_192767_d.field_78800_c = -1.5F + f1;
            this.field_192767_d.field_78797_d = 16.94F + f2;
            this.field_192765_b.field_78800_c = f1;
            this.field_192765_b.field_78797_d = 21.07F + f2;
            return;
         }

         this.field_192773_j.field_78795_f += MathHelper.func_76134_b(p_78087_1_ * 0.6662F) * 1.4F * p_78087_2_;
         this.field_192774_k.field_78795_f += MathHelper.func_76134_b(p_78087_1_ * 0.6662F + 3.1415927F) * 1.4F * p_78087_2_;
      }

      this.field_192768_e.field_78797_d = 15.69F + f;
      this.field_192765_b.field_78795_f = 1.015F + MathHelper.func_76134_b(p_78087_1_ * 0.6662F) * 0.3F * p_78087_2_;
      this.field_192765_b.field_78797_d = 21.07F + f;
      this.field_192764_a.field_78797_d = 16.5F + f;
      this.field_192766_c.field_78808_h = -0.0873F - p_78087_3_;
      this.field_192766_c.field_78797_d = 16.94F + f;
      this.field_192767_d.field_78808_h = 0.0873F + p_78087_3_;
      this.field_192767_d.field_78797_d = 16.94F + f;
      this.field_192773_j.field_78797_d = 22.0F + f;
      this.field_192774_k.field_78797_d = 22.0F + f;
   }

   public void func_78086_a(EntityLivingBase p_78086_1_, float p_78086_2_, float p_78086_3_, float p_78086_4_) {
      this.field_192772_i.field_78795_f = -0.2214F;
      this.field_192764_a.field_78795_f = 0.4937F;
      this.field_192766_c.field_78795_f = -0.6981F;
      this.field_192766_c.field_78796_g = -3.1415927F;
      this.field_192767_d.field_78795_f = -0.6981F;
      this.field_192767_d.field_78796_g = -3.1415927F;
      this.field_192773_j.field_78795_f = -0.0299F;
      this.field_192774_k.field_78795_f = -0.0299F;
      this.field_192773_j.field_78797_d = 22.0F;
      this.field_192774_k.field_78797_d = 22.0F;
      if (p_78086_1_ instanceof EntityParrot) {
         EntityParrot entityparrot = (EntityParrot)p_78086_1_;
         if (entityparrot.func_192004_dr()) {
            this.field_192773_j.field_78808_h = -0.34906584F;
            this.field_192774_k.field_78808_h = 0.34906584F;
            this.field_192775_l = ModelParrot.State.PARTY;
            return;
         }

         if (entityparrot.func_70906_o()) {
            float f = 1.9F;
            this.field_192768_e.field_78797_d = 17.59F;
            this.field_192765_b.field_78795_f = 1.5388988F;
            this.field_192765_b.field_78797_d = 22.97F;
            this.field_192764_a.field_78797_d = 18.4F;
            this.field_192766_c.field_78808_h = -0.0873F;
            this.field_192766_c.field_78797_d = 18.84F;
            this.field_192767_d.field_78808_h = 0.0873F;
            this.field_192767_d.field_78797_d = 18.84F;
            ++this.field_192773_j.field_78797_d;
            ++this.field_192774_k.field_78797_d;
            ++this.field_192773_j.field_78795_f;
            ++this.field_192774_k.field_78795_f;
            this.field_192775_l = ModelParrot.State.SITTING;
         } else if (entityparrot.func_192002_a()) {
            this.field_192773_j.field_78795_f += 0.6981317F;
            this.field_192774_k.field_78795_f += 0.6981317F;
            this.field_192775_l = ModelParrot.State.FLYING;
         } else {
            this.field_192775_l = ModelParrot.State.STANDING;
         }

         this.field_192773_j.field_78808_h = 0.0F;
         this.field_192774_k.field_78808_h = 0.0F;
      }

   }

   static enum State {
      FLYING,
      STANDING,
      SITTING,
      PARTY;
   }
}
