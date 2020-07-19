package net.minecraft.client.model;

import java.util.Random;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class ModelGhast extends ModelBase {
   ModelRenderer field_78128_a;
   ModelRenderer[] field_78127_b = new ModelRenderer[9];

   public ModelGhast() {
      int i = -16;
      this.field_78128_a = new ModelRenderer(this, 0, 0);
      this.field_78128_a.func_78789_a(-8.0F, -8.0F, -8.0F, 16, 16, 16);
      this.field_78128_a.field_78797_d += 8.0F;
      Random random = new Random(1660L);

      for(int j = 0; j < this.field_78127_b.length; ++j) {
         this.field_78127_b[j] = new ModelRenderer(this, 0, 0);
         float f = (((float)(j % 3) - (float)(j / 3 % 2) * 0.5F + 0.25F) / 2.0F * 2.0F - 1.0F) * 5.0F;
         float f1 = ((float)(j / 3) / 2.0F * 2.0F - 1.0F) * 5.0F;
         int k = random.nextInt(7) + 8;
         this.field_78127_b[j].func_78789_a(-1.0F, 0.0F, -1.0F, 2, k, 2);
         this.field_78127_b[j].field_78800_c = f;
         this.field_78127_b[j].field_78798_e = f1;
         this.field_78127_b[j].field_78797_d = 15.0F;
      }

   }

   public void func_78087_a(float p_78087_1_, float p_78087_2_, float p_78087_3_, float p_78087_4_, float p_78087_5_, float p_78087_6_, Entity p_78087_7_) {
      for(int i = 0; i < this.field_78127_b.length; ++i) {
         this.field_78127_b[i].field_78795_f = 0.2F * MathHelper.func_76126_a(p_78087_3_ * 0.3F + (float)i) + 0.4F;
      }

   }

   public void func_78088_a(Entity p_78088_1_, float p_78088_2_, float p_78088_3_, float p_78088_4_, float p_78088_5_, float p_78088_6_, float p_78088_7_) {
      this.func_78087_a(p_78088_2_, p_78088_3_, p_78088_4_, p_78088_5_, p_78088_6_, p_78088_7_, p_78088_1_);
      GlStateManager.func_179094_E();
      GlStateManager.func_179109_b(0.0F, 0.6F, 0.0F);
      this.field_78128_a.func_78785_a(p_78088_7_);

      for(ModelRenderer modelrenderer : this.field_78127_b) {
         modelrenderer.func_78785_a(p_78088_7_);
      }

      GlStateManager.func_179121_F();
   }
}
