package net.minecraft.client.model;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class ModelBlaze extends ModelBase {
   private final ModelRenderer[] field_78106_a = new ModelRenderer[12];
   private final ModelRenderer field_78105_b;

   public ModelBlaze() {
      for(int i = 0; i < this.field_78106_a.length; ++i) {
         this.field_78106_a[i] = new ModelRenderer(this, 0, 16);
         this.field_78106_a[i].func_78789_a(0.0F, 0.0F, 0.0F, 2, 8, 2);
      }

      this.field_78105_b = new ModelRenderer(this, 0, 0);
      this.field_78105_b.func_78789_a(-4.0F, -4.0F, -4.0F, 8, 8, 8);
   }

   public void func_78088_a(Entity p_78088_1_, float p_78088_2_, float p_78088_3_, float p_78088_4_, float p_78088_5_, float p_78088_6_, float p_78088_7_) {
      this.func_78087_a(p_78088_2_, p_78088_3_, p_78088_4_, p_78088_5_, p_78088_6_, p_78088_7_, p_78088_1_);
      this.field_78105_b.func_78785_a(p_78088_7_);

      for(ModelRenderer modelrenderer : this.field_78106_a) {
         modelrenderer.func_78785_a(p_78088_7_);
      }

   }

   public void func_78087_a(float p_78087_1_, float p_78087_2_, float p_78087_3_, float p_78087_4_, float p_78087_5_, float p_78087_6_, Entity p_78087_7_) {
      float f = p_78087_3_ * 3.1415927F * -0.1F;

      for(int i = 0; i < 4; ++i) {
         this.field_78106_a[i].field_78797_d = -2.0F + MathHelper.func_76134_b(((float)(i * 2) + p_78087_3_) * 0.25F);
         this.field_78106_a[i].field_78800_c = MathHelper.func_76134_b(f) * 9.0F;
         this.field_78106_a[i].field_78798_e = MathHelper.func_76126_a(f) * 9.0F;
         ++f;
      }

      f = 0.7853982F + p_78087_3_ * 3.1415927F * 0.03F;

      for(int j = 4; j < 8; ++j) {
         this.field_78106_a[j].field_78797_d = 2.0F + MathHelper.func_76134_b(((float)(j * 2) + p_78087_3_) * 0.25F);
         this.field_78106_a[j].field_78800_c = MathHelper.func_76134_b(f) * 7.0F;
         this.field_78106_a[j].field_78798_e = MathHelper.func_76126_a(f) * 7.0F;
         ++f;
      }

      f = 0.47123894F + p_78087_3_ * 3.1415927F * -0.05F;

      for(int k = 8; k < 12; ++k) {
         this.field_78106_a[k].field_78797_d = 11.0F + MathHelper.func_76134_b(((float)k * 1.5F + p_78087_3_) * 0.5F);
         this.field_78106_a[k].field_78800_c = MathHelper.func_76134_b(f) * 5.0F;
         this.field_78106_a[k].field_78798_e = MathHelper.func_76126_a(f) * 5.0F;
         ++f;
      }

      this.field_78105_b.field_78796_g = p_78087_4_ * 0.017453292F;
      this.field_78105_b.field_78795_f = p_78087_5_ * 0.017453292F;
   }
}
