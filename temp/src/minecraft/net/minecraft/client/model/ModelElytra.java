package net.minecraft.client.model;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.Vec3d;

public class ModelElytra extends ModelBase {
   private final ModelRenderer field_187060_a;
   private final ModelRenderer field_187061_b = new ModelRenderer(this, 22, 0);

   public ModelElytra() {
      this.field_187061_b.func_78790_a(-10.0F, 0.0F, 0.0F, 10, 20, 2, 1.0F);
      this.field_187060_a = new ModelRenderer(this, 22, 0);
      this.field_187060_a.field_78809_i = true;
      this.field_187060_a.func_78790_a(0.0F, 0.0F, 0.0F, 10, 20, 2, 1.0F);
   }

   public void func_78088_a(Entity p_78088_1_, float p_78088_2_, float p_78088_3_, float p_78088_4_, float p_78088_5_, float p_78088_6_, float p_78088_7_) {
      GlStateManager.func_179101_C();
      GlStateManager.func_179129_p();
      if (p_78088_1_ instanceof EntityLivingBase && ((EntityLivingBase)p_78088_1_).func_70631_g_()) {
         GlStateManager.func_179094_E();
         GlStateManager.func_179152_a(0.5F, 0.5F, 0.5F);
         GlStateManager.func_179109_b(0.0F, 1.5F, -0.1F);
         this.field_187061_b.func_78785_a(p_78088_7_);
         this.field_187060_a.func_78785_a(p_78088_7_);
         GlStateManager.func_179121_F();
      } else {
         this.field_187061_b.func_78785_a(p_78088_7_);
         this.field_187060_a.func_78785_a(p_78088_7_);
      }

   }

   public void func_78087_a(float p_78087_1_, float p_78087_2_, float p_78087_3_, float p_78087_4_, float p_78087_5_, float p_78087_6_, Entity p_78087_7_) {
      super.func_78087_a(p_78087_1_, p_78087_2_, p_78087_3_, p_78087_4_, p_78087_5_, p_78087_6_, p_78087_7_);
      float f = 0.2617994F;
      float f1 = -0.2617994F;
      float f2 = 0.0F;
      float f3 = 0.0F;
      if (p_78087_7_ instanceof EntityLivingBase && ((EntityLivingBase)p_78087_7_).func_184613_cA()) {
         float f4 = 1.0F;
         if (p_78087_7_.field_70181_x < 0.0D) {
            Vec3d vec3d = (new Vec3d(p_78087_7_.field_70159_w, p_78087_7_.field_70181_x, p_78087_7_.field_70179_y)).func_72432_b();
            f4 = 1.0F - (float)Math.pow(-vec3d.field_72448_b, 1.5D);
         }

         f = f4 * 0.34906584F + (1.0F - f4) * f;
         f1 = f4 * -1.5707964F + (1.0F - f4) * f1;
      } else if (p_78087_7_.func_70093_af()) {
         f = 0.6981317F;
         f1 = -0.7853982F;
         f2 = 3.0F;
         f3 = 0.08726646F;
      }

      this.field_187061_b.field_78800_c = 5.0F;
      this.field_187061_b.field_78797_d = f2;
      if (p_78087_7_ instanceof AbstractClientPlayer) {
         AbstractClientPlayer abstractclientplayer = (AbstractClientPlayer)p_78087_7_;
         abstractclientplayer.field_184835_a = (float)((double)abstractclientplayer.field_184835_a + (double)(f - abstractclientplayer.field_184835_a) * 0.1D);
         abstractclientplayer.field_184836_b = (float)((double)abstractclientplayer.field_184836_b + (double)(f3 - abstractclientplayer.field_184836_b) * 0.1D);
         abstractclientplayer.field_184837_c = (float)((double)abstractclientplayer.field_184837_c + (double)(f1 - abstractclientplayer.field_184837_c) * 0.1D);
         this.field_187061_b.field_78795_f = abstractclientplayer.field_184835_a;
         this.field_187061_b.field_78796_g = abstractclientplayer.field_184836_b;
         this.field_187061_b.field_78808_h = abstractclientplayer.field_184837_c;
      } else {
         this.field_187061_b.field_78795_f = f;
         this.field_187061_b.field_78808_h = f1;
         this.field_187061_b.field_78796_g = f3;
      }

      this.field_187060_a.field_78800_c = -this.field_187061_b.field_78800_c;
      this.field_187060_a.field_78796_g = -this.field_187061_b.field_78796_g;
      this.field_187060_a.field_78797_d = this.field_187061_b.field_78797_d;
      this.field_187060_a.field_78795_f = this.field_187061_b.field_78795_f;
      this.field_187060_a.field_78808_h = -this.field_187061_b.field_78808_h;
   }
}
