package net.minecraft.client.model;

import net.minecraft.entity.Entity;

public class ModelShulkerBullet extends ModelBase {
   public ModelRenderer field_187069_a;

   public ModelShulkerBullet() {
      this.field_78090_t = 64;
      this.field_78089_u = 32;
      this.field_187069_a = new ModelRenderer(this);
      this.field_187069_a.func_78784_a(0, 0).func_78790_a(-4.0F, -4.0F, -1.0F, 8, 8, 2, 0.0F);
      this.field_187069_a.func_78784_a(0, 10).func_78790_a(-1.0F, -4.0F, -4.0F, 2, 8, 8, 0.0F);
      this.field_187069_a.func_78784_a(20, 0).func_78790_a(-4.0F, -1.0F, -4.0F, 8, 2, 8, 0.0F);
      this.field_187069_a.func_78793_a(0.0F, 0.0F, 0.0F);
   }

   public void func_78088_a(Entity p_78088_1_, float p_78088_2_, float p_78088_3_, float p_78088_4_, float p_78088_5_, float p_78088_6_, float p_78088_7_) {
      this.func_78087_a(p_78088_2_, p_78088_3_, p_78088_4_, p_78088_5_, p_78088_6_, p_78088_7_, p_78088_1_);
      this.field_187069_a.func_78785_a(p_78088_7_);
   }

   public void func_78087_a(float p_78087_1_, float p_78087_2_, float p_78087_3_, float p_78087_4_, float p_78087_5_, float p_78087_6_, Entity p_78087_7_) {
      super.func_78087_a(p_78087_1_, p_78087_2_, p_78087_3_, p_78087_4_, p_78087_5_, p_78087_6_, p_78087_7_);
      this.field_187069_a.field_78796_g = p_78087_4_ * 0.017453292F;
      this.field_187069_a.field_78795_f = p_78087_5_ * 0.017453292F;
   }
}
