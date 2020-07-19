package net.minecraft.client.model;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class ModelBook extends ModelBase {
   public ModelRenderer field_78102_a = (new ModelRenderer(this)).func_78784_a(0, 0).func_78789_a(-6.0F, -5.0F, 0.0F, 6, 10, 0);
   public ModelRenderer field_78100_b = (new ModelRenderer(this)).func_78784_a(16, 0).func_78789_a(0.0F, -5.0F, 0.0F, 6, 10, 0);
   public ModelRenderer field_78101_c = (new ModelRenderer(this)).func_78784_a(0, 10).func_78789_a(0.0F, -4.0F, -0.99F, 5, 8, 1);
   public ModelRenderer field_78098_d = (new ModelRenderer(this)).func_78784_a(12, 10).func_78789_a(0.0F, -4.0F, -0.01F, 5, 8, 1);
   public ModelRenderer field_78099_e = (new ModelRenderer(this)).func_78784_a(24, 10).func_78789_a(0.0F, -4.0F, 0.0F, 5, 8, 0);
   public ModelRenderer field_78096_f = (new ModelRenderer(this)).func_78784_a(24, 10).func_78789_a(0.0F, -4.0F, 0.0F, 5, 8, 0);
   public ModelRenderer field_78097_g = (new ModelRenderer(this)).func_78784_a(12, 0).func_78789_a(-1.0F, -5.0F, 0.0F, 2, 10, 0);

   public ModelBook() {
      this.field_78102_a.func_78793_a(0.0F, 0.0F, -1.0F);
      this.field_78100_b.func_78793_a(0.0F, 0.0F, 1.0F);
      this.field_78097_g.field_78796_g = 1.5707964F;
   }

   public void func_78088_a(Entity p_78088_1_, float p_78088_2_, float p_78088_3_, float p_78088_4_, float p_78088_5_, float p_78088_6_, float p_78088_7_) {
      this.func_78087_a(p_78088_2_, p_78088_3_, p_78088_4_, p_78088_5_, p_78088_6_, p_78088_7_, p_78088_1_);
      this.field_78102_a.func_78785_a(p_78088_7_);
      this.field_78100_b.func_78785_a(p_78088_7_);
      this.field_78097_g.func_78785_a(p_78088_7_);
      this.field_78101_c.func_78785_a(p_78088_7_);
      this.field_78098_d.func_78785_a(p_78088_7_);
      this.field_78099_e.func_78785_a(p_78088_7_);
      this.field_78096_f.func_78785_a(p_78088_7_);
   }

   public void func_78087_a(float p_78087_1_, float p_78087_2_, float p_78087_3_, float p_78087_4_, float p_78087_5_, float p_78087_6_, Entity p_78087_7_) {
      float f = (MathHelper.func_76126_a(p_78087_1_ * 0.02F) * 0.1F + 1.25F) * p_78087_4_;
      this.field_78102_a.field_78796_g = 3.1415927F + f;
      this.field_78100_b.field_78796_g = -f;
      this.field_78101_c.field_78796_g = f;
      this.field_78098_d.field_78796_g = -f;
      this.field_78099_e.field_78796_g = f - f * 2.0F * p_78087_2_;
      this.field_78096_f.field_78796_g = f - f * 2.0F * p_78087_3_;
      this.field_78101_c.field_78800_c = MathHelper.func_76126_a(f);
      this.field_78098_d.field_78800_c = MathHelper.func_76126_a(f);
      this.field_78099_e.field_78800_c = MathHelper.func_76126_a(f);
      this.field_78096_f.field_78800_c = MathHelper.func_76126_a(f);
   }
}
