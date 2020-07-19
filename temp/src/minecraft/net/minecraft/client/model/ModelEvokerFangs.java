package net.minecraft.client.model;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class ModelEvokerFangs extends ModelBase {
   private final ModelRenderer field_191213_a = new ModelRenderer(this, 0, 0);
   private final ModelRenderer field_191214_b;
   private final ModelRenderer field_191215_c;

   public ModelEvokerFangs() {
      this.field_191213_a.func_78793_a(-5.0F, 22.0F, -5.0F);
      this.field_191213_a.func_78789_a(0.0F, 0.0F, 0.0F, 10, 12, 10);
      this.field_191214_b = new ModelRenderer(this, 40, 0);
      this.field_191214_b.func_78793_a(1.5F, 22.0F, -4.0F);
      this.field_191214_b.func_78789_a(0.0F, 0.0F, 0.0F, 4, 14, 8);
      this.field_191215_c = new ModelRenderer(this, 40, 0);
      this.field_191215_c.func_78793_a(-1.5F, 22.0F, 4.0F);
      this.field_191215_c.func_78789_a(0.0F, 0.0F, 0.0F, 4, 14, 8);
   }

   public void func_78088_a(Entity p_78088_1_, float p_78088_2_, float p_78088_3_, float p_78088_4_, float p_78088_5_, float p_78088_6_, float p_78088_7_) {
      float f = p_78088_2_ * 2.0F;
      if (f > 1.0F) {
         f = 1.0F;
      }

      f = 1.0F - f * f * f;
      this.field_191214_b.field_78808_h = 3.1415927F - f * 0.35F * 3.1415927F;
      this.field_191215_c.field_78808_h = 3.1415927F + f * 0.35F * 3.1415927F;
      this.field_191215_c.field_78796_g = 3.1415927F;
      float f1 = (p_78088_2_ + MathHelper.func_76126_a(p_78088_2_ * 2.7F)) * 0.6F * 12.0F;
      this.field_191214_b.field_78797_d = 24.0F - f1;
      this.field_191215_c.field_78797_d = this.field_191214_b.field_78797_d;
      this.field_191213_a.field_78797_d = this.field_191214_b.field_78797_d;
      this.field_191213_a.func_78785_a(p_78088_7_);
      this.field_191214_b.func_78785_a(p_78088_7_);
      this.field_191215_c.func_78785_a(p_78088_7_);
   }
}
