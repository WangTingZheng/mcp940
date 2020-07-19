package net.minecraft.client.model;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class ModelSilverfish extends ModelBase {
   private final ModelRenderer[] field_78171_a = new ModelRenderer[7];
   private final ModelRenderer[] field_78169_b;
   private final float[] field_78170_c = new float[7];
   private static final int[][] field_78167_d = new int[][]{{3, 2, 2}, {4, 3, 2}, {6, 4, 3}, {3, 3, 3}, {2, 2, 3}, {2, 1, 2}, {1, 1, 2}};
   private static final int[][] field_78168_e = new int[][]{{0, 0}, {0, 4}, {0, 9}, {0, 16}, {0, 22}, {11, 0}, {13, 4}};

   public ModelSilverfish() {
      float f = -3.5F;

      for(int i = 0; i < this.field_78171_a.length; ++i) {
         this.field_78171_a[i] = new ModelRenderer(this, field_78168_e[i][0], field_78168_e[i][1]);
         this.field_78171_a[i].func_78789_a((float)field_78167_d[i][0] * -0.5F, 0.0F, (float)field_78167_d[i][2] * -0.5F, field_78167_d[i][0], field_78167_d[i][1], field_78167_d[i][2]);
         this.field_78171_a[i].func_78793_a(0.0F, (float)(24 - field_78167_d[i][1]), f);
         this.field_78170_c[i] = f;
         if (i < this.field_78171_a.length - 1) {
            f += (float)(field_78167_d[i][2] + field_78167_d[i + 1][2]) * 0.5F;
         }
      }

      this.field_78169_b = new ModelRenderer[3];
      this.field_78169_b[0] = new ModelRenderer(this, 20, 0);
      this.field_78169_b[0].func_78789_a(-5.0F, 0.0F, (float)field_78167_d[2][2] * -0.5F, 10, 8, field_78167_d[2][2]);
      this.field_78169_b[0].func_78793_a(0.0F, 16.0F, this.field_78170_c[2]);
      this.field_78169_b[1] = new ModelRenderer(this, 20, 11);
      this.field_78169_b[1].func_78789_a(-3.0F, 0.0F, (float)field_78167_d[4][2] * -0.5F, 6, 4, field_78167_d[4][2]);
      this.field_78169_b[1].func_78793_a(0.0F, 20.0F, this.field_78170_c[4]);
      this.field_78169_b[2] = new ModelRenderer(this, 20, 18);
      this.field_78169_b[2].func_78789_a(-3.0F, 0.0F, (float)field_78167_d[4][2] * -0.5F, 6, 5, field_78167_d[1][2]);
      this.field_78169_b[2].func_78793_a(0.0F, 19.0F, this.field_78170_c[1]);
   }

   public void func_78088_a(Entity p_78088_1_, float p_78088_2_, float p_78088_3_, float p_78088_4_, float p_78088_5_, float p_78088_6_, float p_78088_7_) {
      this.func_78087_a(p_78088_2_, p_78088_3_, p_78088_4_, p_78088_5_, p_78088_6_, p_78088_7_, p_78088_1_);

      for(ModelRenderer modelrenderer : this.field_78171_a) {
         modelrenderer.func_78785_a(p_78088_7_);
      }

      for(ModelRenderer modelrenderer1 : this.field_78169_b) {
         modelrenderer1.func_78785_a(p_78088_7_);
      }

   }

   public void func_78087_a(float p_78087_1_, float p_78087_2_, float p_78087_3_, float p_78087_4_, float p_78087_5_, float p_78087_6_, Entity p_78087_7_) {
      for(int i = 0; i < this.field_78171_a.length; ++i) {
         this.field_78171_a[i].field_78796_g = MathHelper.func_76134_b(p_78087_3_ * 0.9F + (float)i * 0.15F * 3.1415927F) * 3.1415927F * 0.05F * (float)(1 + Math.abs(i - 2));
         this.field_78171_a[i].field_78800_c = MathHelper.func_76126_a(p_78087_3_ * 0.9F + (float)i * 0.15F * 3.1415927F) * 3.1415927F * 0.2F * (float)Math.abs(i - 2);
      }

      this.field_78169_b[0].field_78796_g = this.field_78171_a[2].field_78796_g;
      this.field_78169_b[1].field_78796_g = this.field_78171_a[4].field_78796_g;
      this.field_78169_b[1].field_78800_c = this.field_78171_a[4].field_78800_c;
      this.field_78169_b[2].field_78796_g = this.field_78171_a[1].field_78796_g;
      this.field_78169_b[2].field_78800_c = this.field_78171_a[1].field_78800_c;
   }
}
