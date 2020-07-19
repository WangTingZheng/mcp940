package net.minecraft.client.model;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntitySheep;

public class ModelSheep1 extends ModelQuadruped {
   private float field_78152_i;

   public ModelSheep1() {
      super(12, 0.0F);
      this.field_78150_a = new ModelRenderer(this, 0, 0);
      this.field_78150_a.func_78790_a(-3.0F, -4.0F, -4.0F, 6, 6, 6, 0.6F);
      this.field_78150_a.func_78793_a(0.0F, 6.0F, -8.0F);
      this.field_78148_b = new ModelRenderer(this, 28, 8);
      this.field_78148_b.func_78790_a(-4.0F, -10.0F, -7.0F, 8, 16, 6, 1.75F);
      this.field_78148_b.func_78793_a(0.0F, 5.0F, 2.0F);
      float f = 0.5F;
      this.field_78149_c = new ModelRenderer(this, 0, 16);
      this.field_78149_c.func_78790_a(-2.0F, 0.0F, -2.0F, 4, 6, 4, 0.5F);
      this.field_78149_c.func_78793_a(-3.0F, 12.0F, 7.0F);
      this.field_78146_d = new ModelRenderer(this, 0, 16);
      this.field_78146_d.func_78790_a(-2.0F, 0.0F, -2.0F, 4, 6, 4, 0.5F);
      this.field_78146_d.func_78793_a(3.0F, 12.0F, 7.0F);
      this.field_78147_e = new ModelRenderer(this, 0, 16);
      this.field_78147_e.func_78790_a(-2.0F, 0.0F, -2.0F, 4, 6, 4, 0.5F);
      this.field_78147_e.func_78793_a(-3.0F, 12.0F, -5.0F);
      this.field_78144_f = new ModelRenderer(this, 0, 16);
      this.field_78144_f.func_78790_a(-2.0F, 0.0F, -2.0F, 4, 6, 4, 0.5F);
      this.field_78144_f.func_78793_a(3.0F, 12.0F, -5.0F);
   }

   public void func_78086_a(EntityLivingBase p_78086_1_, float p_78086_2_, float p_78086_3_, float p_78086_4_) {
      super.func_78086_a(p_78086_1_, p_78086_2_, p_78086_3_, p_78086_4_);
      this.field_78150_a.field_78797_d = 6.0F + ((EntitySheep)p_78086_1_).func_70894_j(p_78086_4_) * 9.0F;
      this.field_78152_i = ((EntitySheep)p_78086_1_).func_70890_k(p_78086_4_);
   }

   public void func_78087_a(float p_78087_1_, float p_78087_2_, float p_78087_3_, float p_78087_4_, float p_78087_5_, float p_78087_6_, Entity p_78087_7_) {
      super.func_78087_a(p_78087_1_, p_78087_2_, p_78087_3_, p_78087_4_, p_78087_5_, p_78087_6_, p_78087_7_);
      this.field_78150_a.field_78795_f = this.field_78152_i;
   }
}
