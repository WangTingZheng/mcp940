package net.minecraft.client.model;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class ModelEnderMite extends ModelBase {
   private static final int[][] field_178716_a = new int[][]{{4, 3, 2}, {6, 4, 5}, {3, 3, 1}, {1, 2, 1}};
   private static final int[][] field_178714_b = new int[][]{{0, 0}, {0, 5}, {0, 14}, {0, 18}};
   private static final int field_178715_c = field_178716_a.length;
   private final ModelRenderer[] field_178713_d;

   public ModelEnderMite() {
      this.field_178713_d = new ModelRenderer[field_178715_c];
      float f = -3.5F;

      for(int i = 0; i < this.field_178713_d.length; ++i) {
         this.field_178713_d[i] = new ModelRenderer(this, field_178714_b[i][0], field_178714_b[i][1]);
         this.field_178713_d[i].func_78789_a((float)field_178716_a[i][0] * -0.5F, 0.0F, (float)field_178716_a[i][2] * -0.5F, field_178716_a[i][0], field_178716_a[i][1], field_178716_a[i][2]);
         this.field_178713_d[i].func_78793_a(0.0F, (float)(24 - field_178716_a[i][1]), f);
         if (i < this.field_178713_d.length - 1) {
            f += (float)(field_178716_a[i][2] + field_178716_a[i + 1][2]) * 0.5F;
         }
      }

   }

   public void func_78088_a(Entity p_78088_1_, float p_78088_2_, float p_78088_3_, float p_78088_4_, float p_78088_5_, float p_78088_6_, float p_78088_7_) {
      this.func_78087_a(p_78088_2_, p_78088_3_, p_78088_4_, p_78088_5_, p_78088_6_, p_78088_7_, p_78088_1_);

      for(ModelRenderer modelrenderer : this.field_178713_d) {
         modelrenderer.func_78785_a(p_78088_7_);
      }

   }

   public void func_78087_a(float p_78087_1_, float p_78087_2_, float p_78087_3_, float p_78087_4_, float p_78087_5_, float p_78087_6_, Entity p_78087_7_) {
      for(int i = 0; i < this.field_178713_d.length; ++i) {
         this.field_178713_d[i].field_78796_g = MathHelper.func_76134_b(p_78087_3_ * 0.9F + (float)i * 0.15F * 3.1415927F) * 3.1415927F * 0.01F * (float)(1 + Math.abs(i - 2));
         this.field_178713_d[i].field_78800_c = MathHelper.func_76126_a(p_78087_3_ * 0.9F + (float)i * 0.15F * 3.1415927F) * 3.1415927F * 0.1F * (float)Math.abs(i - 2);
      }

   }
}
