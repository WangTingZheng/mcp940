package net.minecraft.client.model;

import net.minecraft.entity.Entity;

public class ModelMinecart extends ModelBase {
   public ModelRenderer[] field_78154_a = new ModelRenderer[7];

   public ModelMinecart() {
      this.field_78154_a[0] = new ModelRenderer(this, 0, 10);
      this.field_78154_a[1] = new ModelRenderer(this, 0, 0);
      this.field_78154_a[2] = new ModelRenderer(this, 0, 0);
      this.field_78154_a[3] = new ModelRenderer(this, 0, 0);
      this.field_78154_a[4] = new ModelRenderer(this, 0, 0);
      this.field_78154_a[5] = new ModelRenderer(this, 44, 10);
      int i = 20;
      int j = 8;
      int k = 16;
      int l = 4;
      this.field_78154_a[0].func_78790_a(-10.0F, -8.0F, -1.0F, 20, 16, 2, 0.0F);
      this.field_78154_a[0].func_78793_a(0.0F, 4.0F, 0.0F);
      this.field_78154_a[5].func_78790_a(-9.0F, -7.0F, -1.0F, 18, 14, 1, 0.0F);
      this.field_78154_a[5].func_78793_a(0.0F, 4.0F, 0.0F);
      this.field_78154_a[1].func_78790_a(-8.0F, -9.0F, -1.0F, 16, 8, 2, 0.0F);
      this.field_78154_a[1].func_78793_a(-9.0F, 4.0F, 0.0F);
      this.field_78154_a[2].func_78790_a(-8.0F, -9.0F, -1.0F, 16, 8, 2, 0.0F);
      this.field_78154_a[2].func_78793_a(9.0F, 4.0F, 0.0F);
      this.field_78154_a[3].func_78790_a(-8.0F, -9.0F, -1.0F, 16, 8, 2, 0.0F);
      this.field_78154_a[3].func_78793_a(0.0F, 4.0F, -7.0F);
      this.field_78154_a[4].func_78790_a(-8.0F, -9.0F, -1.0F, 16, 8, 2, 0.0F);
      this.field_78154_a[4].func_78793_a(0.0F, 4.0F, 7.0F);
      this.field_78154_a[0].field_78795_f = 1.5707964F;
      this.field_78154_a[1].field_78796_g = 4.712389F;
      this.field_78154_a[2].field_78796_g = 1.5707964F;
      this.field_78154_a[3].field_78796_g = 3.1415927F;
      this.field_78154_a[5].field_78795_f = -1.5707964F;
   }

   public void func_78088_a(Entity p_78088_1_, float p_78088_2_, float p_78088_3_, float p_78088_4_, float p_78088_5_, float p_78088_6_, float p_78088_7_) {
      this.field_78154_a[5].field_78797_d = 4.0F - p_78088_4_;

      for(int i = 0; i < 6; ++i) {
         this.field_78154_a[i].func_78785_a(p_78088_7_);
      }

   }
}
