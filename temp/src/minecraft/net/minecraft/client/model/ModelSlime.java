package net.minecraft.client.model;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;

public class ModelSlime extends ModelBase {
   ModelRenderer field_78200_a;
   ModelRenderer field_78198_b;
   ModelRenderer field_78199_c;
   ModelRenderer field_78197_d;

   public ModelSlime(int p_i1157_1_) {
      if (p_i1157_1_ > 0) {
         this.field_78200_a = new ModelRenderer(this, 0, p_i1157_1_);
         this.field_78200_a.func_78789_a(-3.0F, 17.0F, -3.0F, 6, 6, 6);
         this.field_78198_b = new ModelRenderer(this, 32, 0);
         this.field_78198_b.func_78789_a(-3.25F, 18.0F, -3.5F, 2, 2, 2);
         this.field_78199_c = new ModelRenderer(this, 32, 4);
         this.field_78199_c.func_78789_a(1.25F, 18.0F, -3.5F, 2, 2, 2);
         this.field_78197_d = new ModelRenderer(this, 32, 8);
         this.field_78197_d.func_78789_a(0.0F, 21.0F, -3.5F, 1, 1, 1);
      } else {
         this.field_78200_a = new ModelRenderer(this, 0, p_i1157_1_);
         this.field_78200_a.func_78789_a(-4.0F, 16.0F, -4.0F, 8, 8, 8);
      }

   }

   public void func_78088_a(Entity p_78088_1_, float p_78088_2_, float p_78088_3_, float p_78088_4_, float p_78088_5_, float p_78088_6_, float p_78088_7_) {
      this.func_78087_a(p_78088_2_, p_78088_3_, p_78088_4_, p_78088_5_, p_78088_6_, p_78088_7_, p_78088_1_);
      GlStateManager.func_179109_b(0.0F, 0.001F, 0.0F);
      this.field_78200_a.func_78785_a(p_78088_7_);
      if (this.field_78198_b != null) {
         this.field_78198_b.func_78785_a(p_78088_7_);
         this.field_78199_c.func_78785_a(p_78088_7_);
         this.field_78197_d.func_78785_a(p_78088_7_);
      }

   }
}
