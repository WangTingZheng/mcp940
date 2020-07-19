package net.minecraft.client.model;

import net.minecraft.entity.Entity;

public class ModelSquid extends ModelBase {
   ModelRenderer field_78202_a;
   ModelRenderer[] field_78201_b = new ModelRenderer[8];

   public ModelSquid() {
      int i = -16;
      this.field_78202_a = new ModelRenderer(this, 0, 0);
      this.field_78202_a.func_78789_a(-6.0F, -8.0F, -6.0F, 12, 16, 12);
      this.field_78202_a.field_78797_d += 8.0F;

      for(int j = 0; j < this.field_78201_b.length; ++j) {
         this.field_78201_b[j] = new ModelRenderer(this, 48, 0);
         double d0 = (double)j * 3.141592653589793D * 2.0D / (double)this.field_78201_b.length;
         float f = (float)Math.cos(d0) * 5.0F;
         float f1 = (float)Math.sin(d0) * 5.0F;
         this.field_78201_b[j].func_78789_a(-1.0F, 0.0F, -1.0F, 2, 18, 2);
         this.field_78201_b[j].field_78800_c = f;
         this.field_78201_b[j].field_78798_e = f1;
         this.field_78201_b[j].field_78797_d = 15.0F;
         d0 = (double)j * 3.141592653589793D * -2.0D / (double)this.field_78201_b.length + 1.5707963267948966D;
         this.field_78201_b[j].field_78796_g = (float)d0;
      }

   }

   public void func_78087_a(float p_78087_1_, float p_78087_2_, float p_78087_3_, float p_78087_4_, float p_78087_5_, float p_78087_6_, Entity p_78087_7_) {
      for(ModelRenderer modelrenderer : this.field_78201_b) {
         modelrenderer.field_78795_f = p_78087_3_;
      }

   }

   public void func_78088_a(Entity p_78088_1_, float p_78088_2_, float p_78088_3_, float p_78088_4_, float p_78088_5_, float p_78088_6_, float p_78088_7_) {
      this.func_78087_a(p_78088_2_, p_78088_3_, p_78088_4_, p_78088_5_, p_78088_6_, p_78088_7_, p_78088_1_);
      this.field_78202_a.func_78785_a(p_78088_7_);

      for(ModelRenderer modelrenderer : this.field_78201_b) {
         modelrenderer.func_78785_a(p_78088_7_);
      }

   }
}
