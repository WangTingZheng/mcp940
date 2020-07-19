package net.minecraft.client.model;

import net.minecraft.entity.Entity;

public class ModelEnderman extends ModelBiped {
   public boolean field_78126_a;
   public boolean field_78125_b;

   public ModelEnderman(float p_i46305_1_) {
      super(0.0F, -14.0F, 64, 32);
      float f = -14.0F;
      this.field_178720_f = new ModelRenderer(this, 0, 16);
      this.field_178720_f.func_78790_a(-4.0F, -8.0F, -4.0F, 8, 8, 8, p_i46305_1_ - 0.5F);
      this.field_178720_f.func_78793_a(0.0F, -14.0F, 0.0F);
      this.field_78115_e = new ModelRenderer(this, 32, 16);
      this.field_78115_e.func_78790_a(-4.0F, 0.0F, -2.0F, 8, 12, 4, p_i46305_1_);
      this.field_78115_e.func_78793_a(0.0F, -14.0F, 0.0F);
      this.field_178723_h = new ModelRenderer(this, 56, 0);
      this.field_178723_h.func_78790_a(-1.0F, -2.0F, -1.0F, 2, 30, 2, p_i46305_1_);
      this.field_178723_h.func_78793_a(-3.0F, -12.0F, 0.0F);
      this.field_178724_i = new ModelRenderer(this, 56, 0);
      this.field_178724_i.field_78809_i = true;
      this.field_178724_i.func_78790_a(-1.0F, -2.0F, -1.0F, 2, 30, 2, p_i46305_1_);
      this.field_178724_i.func_78793_a(5.0F, -12.0F, 0.0F);
      this.field_178721_j = new ModelRenderer(this, 56, 0);
      this.field_178721_j.func_78790_a(-1.0F, 0.0F, -1.0F, 2, 30, 2, p_i46305_1_);
      this.field_178721_j.func_78793_a(-2.0F, -2.0F, 0.0F);
      this.field_178722_k = new ModelRenderer(this, 56, 0);
      this.field_178722_k.field_78809_i = true;
      this.field_178722_k.func_78790_a(-1.0F, 0.0F, -1.0F, 2, 30, 2, p_i46305_1_);
      this.field_178722_k.func_78793_a(2.0F, -2.0F, 0.0F);
   }

   public void func_78087_a(float p_78087_1_, float p_78087_2_, float p_78087_3_, float p_78087_4_, float p_78087_5_, float p_78087_6_, Entity p_78087_7_) {
      super.func_78087_a(p_78087_1_, p_78087_2_, p_78087_3_, p_78087_4_, p_78087_5_, p_78087_6_, p_78087_7_);
      this.field_78116_c.field_78806_j = true;
      float f = -14.0F;
      this.field_78115_e.field_78795_f = 0.0F;
      this.field_78115_e.field_78797_d = -14.0F;
      this.field_78115_e.field_78798_e = -0.0F;
      this.field_178721_j.field_78795_f -= 0.0F;
      this.field_178722_k.field_78795_f -= 0.0F;
      this.field_178723_h.field_78795_f = (float)((double)this.field_178723_h.field_78795_f * 0.5D);
      this.field_178724_i.field_78795_f = (float)((double)this.field_178724_i.field_78795_f * 0.5D);
      this.field_178721_j.field_78795_f = (float)((double)this.field_178721_j.field_78795_f * 0.5D);
      this.field_178722_k.field_78795_f = (float)((double)this.field_178722_k.field_78795_f * 0.5D);
      float f1 = 0.4F;
      if (this.field_178723_h.field_78795_f > 0.4F) {
         this.field_178723_h.field_78795_f = 0.4F;
      }

      if (this.field_178724_i.field_78795_f > 0.4F) {
         this.field_178724_i.field_78795_f = 0.4F;
      }

      if (this.field_178723_h.field_78795_f < -0.4F) {
         this.field_178723_h.field_78795_f = -0.4F;
      }

      if (this.field_178724_i.field_78795_f < -0.4F) {
         this.field_178724_i.field_78795_f = -0.4F;
      }

      if (this.field_178721_j.field_78795_f > 0.4F) {
         this.field_178721_j.field_78795_f = 0.4F;
      }

      if (this.field_178722_k.field_78795_f > 0.4F) {
         this.field_178722_k.field_78795_f = 0.4F;
      }

      if (this.field_178721_j.field_78795_f < -0.4F) {
         this.field_178721_j.field_78795_f = -0.4F;
      }

      if (this.field_178722_k.field_78795_f < -0.4F) {
         this.field_178722_k.field_78795_f = -0.4F;
      }

      if (this.field_78126_a) {
         this.field_178723_h.field_78795_f = -0.5F;
         this.field_178724_i.field_78795_f = -0.5F;
         this.field_178723_h.field_78808_h = 0.05F;
         this.field_178724_i.field_78808_h = -0.05F;
      }

      this.field_178723_h.field_78798_e = 0.0F;
      this.field_178724_i.field_78798_e = 0.0F;
      this.field_178721_j.field_78798_e = 0.0F;
      this.field_178722_k.field_78798_e = 0.0F;
      this.field_178721_j.field_78797_d = -5.0F;
      this.field_178722_k.field_78797_d = -5.0F;
      this.field_78116_c.field_78798_e = -0.0F;
      this.field_78116_c.field_78797_d = -13.0F;
      this.field_178720_f.field_78800_c = this.field_78116_c.field_78800_c;
      this.field_178720_f.field_78797_d = this.field_78116_c.field_78797_d;
      this.field_178720_f.field_78798_e = this.field_78116_c.field_78798_e;
      this.field_178720_f.field_78795_f = this.field_78116_c.field_78795_f;
      this.field_178720_f.field_78796_g = this.field_78116_c.field_78796_g;
      this.field_178720_f.field_78808_h = this.field_78116_c.field_78808_h;
      if (this.field_78125_b) {
         float f2 = 1.0F;
         this.field_78116_c.field_78797_d -= 5.0F;
      }

   }
}
