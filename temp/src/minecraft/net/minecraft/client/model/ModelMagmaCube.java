package net.minecraft.client.model;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMagmaCube;

public class ModelMagmaCube extends ModelBase {
   ModelRenderer[] field_78109_a = new ModelRenderer[8];
   ModelRenderer field_78108_b;

   public ModelMagmaCube() {
      for(int i = 0; i < this.field_78109_a.length; ++i) {
         int j = 0;
         int k = i;
         if (i == 2) {
            j = 24;
            k = 10;
         } else if (i == 3) {
            j = 24;
            k = 19;
         }

         this.field_78109_a[i] = new ModelRenderer(this, j, k);
         this.field_78109_a[i].func_78789_a(-4.0F, (float)(16 + i), -4.0F, 8, 1, 8);
      }

      this.field_78108_b = new ModelRenderer(this, 0, 16);
      this.field_78108_b.func_78789_a(-2.0F, 18.0F, -2.0F, 4, 4, 4);
   }

   public void func_78086_a(EntityLivingBase p_78086_1_, float p_78086_2_, float p_78086_3_, float p_78086_4_) {
      EntityMagmaCube entitymagmacube = (EntityMagmaCube)p_78086_1_;
      float f = entitymagmacube.field_70812_c + (entitymagmacube.field_70811_b - entitymagmacube.field_70812_c) * p_78086_4_;
      if (f < 0.0F) {
         f = 0.0F;
      }

      for(int i = 0; i < this.field_78109_a.length; ++i) {
         this.field_78109_a[i].field_78797_d = (float)(-(4 - i)) * f * 1.7F;
      }

   }

   public void func_78088_a(Entity p_78088_1_, float p_78088_2_, float p_78088_3_, float p_78088_4_, float p_78088_5_, float p_78088_6_, float p_78088_7_) {
      this.func_78087_a(p_78088_2_, p_78088_3_, p_78088_4_, p_78088_5_, p_78088_6_, p_78088_7_, p_78088_1_);
      this.field_78108_b.func_78785_a(p_78088_7_);

      for(ModelRenderer modelrenderer : this.field_78109_a) {
         modelrenderer.func_78785_a(p_78088_7_);
      }

   }
}
