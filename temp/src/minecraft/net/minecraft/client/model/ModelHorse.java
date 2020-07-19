package net.minecraft.client.model;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.AbstractChestHorse;
import net.minecraft.entity.passive.AbstractHorse;
import net.minecraft.util.math.MathHelper;

public class ModelHorse extends ModelBase {
   private final ModelRenderer field_110709_a;
   private final ModelRenderer field_178711_b;
   private final ModelRenderer field_178712_c;
   private final ModelRenderer field_110705_d;
   private final ModelRenderer field_110706_e;
   private final ModelRenderer field_110703_f;
   private final ModelRenderer field_110704_g;
   private final ModelRenderer field_110716_h;
   private final ModelRenderer field_110717_i;
   private final ModelRenderer field_110714_j;
   private final ModelRenderer field_110715_k;
   private final ModelRenderer field_110712_l;
   private final ModelRenderer field_110713_m;
   private final ModelRenderer field_110710_n;
   private final ModelRenderer field_110711_o;
   private final ModelRenderer field_110719_v;
   private final ModelRenderer field_110718_w;
   private final ModelRenderer field_110722_x;
   private final ModelRenderer field_110721_y;
   private final ModelRenderer field_110720_z;
   private final ModelRenderer field_110688_A;
   private final ModelRenderer field_110689_B;
   private final ModelRenderer field_110690_C;
   private final ModelRenderer field_110684_D;
   private final ModelRenderer field_110685_E;
   private final ModelRenderer field_110686_F;
   private final ModelRenderer field_110687_G;
   private final ModelRenderer field_110695_H;
   private final ModelRenderer field_110696_I;
   private final ModelRenderer field_110697_J;
   private final ModelRenderer field_110698_K;
   private final ModelRenderer field_110691_L;
   private final ModelRenderer field_110692_M;
   private final ModelRenderer field_110693_N;
   private final ModelRenderer field_110694_O;
   private final ModelRenderer field_110700_P;
   private final ModelRenderer field_110699_Q;
   private final ModelRenderer field_110702_R;
   private final ModelRenderer field_110701_S;

   public ModelHorse() {
      this.field_78090_t = 128;
      this.field_78089_u = 128;
      this.field_110715_k = new ModelRenderer(this, 0, 34);
      this.field_110715_k.func_78789_a(-5.0F, -8.0F, -19.0F, 10, 10, 24);
      this.field_110715_k.func_78793_a(0.0F, 11.0F, 9.0F);
      this.field_110712_l = new ModelRenderer(this, 44, 0);
      this.field_110712_l.func_78789_a(-1.0F, -1.0F, 0.0F, 2, 2, 3);
      this.field_110712_l.func_78793_a(0.0F, 3.0F, 14.0F);
      this.field_110712_l.field_78795_f = -1.134464F;
      this.field_110713_m = new ModelRenderer(this, 38, 7);
      this.field_110713_m.func_78789_a(-1.5F, -2.0F, 3.0F, 3, 4, 7);
      this.field_110713_m.func_78793_a(0.0F, 3.0F, 14.0F);
      this.field_110713_m.field_78795_f = -1.134464F;
      this.field_110710_n = new ModelRenderer(this, 24, 3);
      this.field_110710_n.func_78789_a(-1.5F, -4.5F, 9.0F, 3, 4, 7);
      this.field_110710_n.func_78793_a(0.0F, 3.0F, 14.0F);
      this.field_110710_n.field_78795_f = -1.3962634F;
      this.field_110711_o = new ModelRenderer(this, 78, 29);
      this.field_110711_o.func_78789_a(-2.5F, -2.0F, -2.5F, 4, 9, 5);
      this.field_110711_o.func_78793_a(4.0F, 9.0F, 11.0F);
      this.field_110719_v = new ModelRenderer(this, 78, 43);
      this.field_110719_v.func_78789_a(-2.0F, 0.0F, -1.5F, 3, 5, 3);
      this.field_110719_v.func_78793_a(4.0F, 16.0F, 11.0F);
      this.field_110718_w = new ModelRenderer(this, 78, 51);
      this.field_110718_w.func_78789_a(-2.5F, 5.1F, -2.0F, 4, 3, 4);
      this.field_110718_w.func_78793_a(4.0F, 16.0F, 11.0F);
      this.field_110722_x = new ModelRenderer(this, 96, 29);
      this.field_110722_x.func_78789_a(-1.5F, -2.0F, -2.5F, 4, 9, 5);
      this.field_110722_x.func_78793_a(-4.0F, 9.0F, 11.0F);
      this.field_110721_y = new ModelRenderer(this, 96, 43);
      this.field_110721_y.func_78789_a(-1.0F, 0.0F, -1.5F, 3, 5, 3);
      this.field_110721_y.func_78793_a(-4.0F, 16.0F, 11.0F);
      this.field_110720_z = new ModelRenderer(this, 96, 51);
      this.field_110720_z.func_78789_a(-1.5F, 5.1F, -2.0F, 4, 3, 4);
      this.field_110720_z.func_78793_a(-4.0F, 16.0F, 11.0F);
      this.field_110688_A = new ModelRenderer(this, 44, 29);
      this.field_110688_A.func_78789_a(-1.9F, -1.0F, -2.1F, 3, 8, 4);
      this.field_110688_A.func_78793_a(4.0F, 9.0F, -8.0F);
      this.field_110689_B = new ModelRenderer(this, 44, 41);
      this.field_110689_B.func_78789_a(-1.9F, 0.0F, -1.6F, 3, 5, 3);
      this.field_110689_B.func_78793_a(4.0F, 16.0F, -8.0F);
      this.field_110690_C = new ModelRenderer(this, 44, 51);
      this.field_110690_C.func_78789_a(-2.4F, 5.1F, -2.1F, 4, 3, 4);
      this.field_110690_C.func_78793_a(4.0F, 16.0F, -8.0F);
      this.field_110684_D = new ModelRenderer(this, 60, 29);
      this.field_110684_D.func_78789_a(-1.1F, -1.0F, -2.1F, 3, 8, 4);
      this.field_110684_D.func_78793_a(-4.0F, 9.0F, -8.0F);
      this.field_110685_E = new ModelRenderer(this, 60, 41);
      this.field_110685_E.func_78789_a(-1.1F, 0.0F, -1.6F, 3, 5, 3);
      this.field_110685_E.func_78793_a(-4.0F, 16.0F, -8.0F);
      this.field_110686_F = new ModelRenderer(this, 60, 51);
      this.field_110686_F.func_78789_a(-1.6F, 5.1F, -2.1F, 4, 3, 4);
      this.field_110686_F.func_78793_a(-4.0F, 16.0F, -8.0F);
      this.field_110709_a = new ModelRenderer(this, 0, 0);
      this.field_110709_a.func_78789_a(-2.5F, -10.0F, -1.5F, 5, 5, 7);
      this.field_110709_a.func_78793_a(0.0F, 4.0F, -10.0F);
      this.field_110709_a.field_78795_f = 0.5235988F;
      this.field_178711_b = new ModelRenderer(this, 24, 18);
      this.field_178711_b.func_78789_a(-2.0F, -10.0F, -7.0F, 4, 3, 6);
      this.field_178711_b.func_78793_a(0.0F, 3.95F, -10.0F);
      this.field_178711_b.field_78795_f = 0.5235988F;
      this.field_178712_c = new ModelRenderer(this, 24, 27);
      this.field_178712_c.func_78789_a(-2.0F, -7.0F, -6.5F, 4, 2, 5);
      this.field_178712_c.func_78793_a(0.0F, 4.0F, -10.0F);
      this.field_178712_c.field_78795_f = 0.5235988F;
      this.field_110709_a.func_78792_a(this.field_178711_b);
      this.field_110709_a.func_78792_a(this.field_178712_c);
      this.field_110705_d = new ModelRenderer(this, 0, 0);
      this.field_110705_d.func_78789_a(0.45F, -12.0F, 4.0F, 2, 3, 1);
      this.field_110705_d.func_78793_a(0.0F, 4.0F, -10.0F);
      this.field_110705_d.field_78795_f = 0.5235988F;
      this.field_110706_e = new ModelRenderer(this, 0, 0);
      this.field_110706_e.func_78789_a(-2.45F, -12.0F, 4.0F, 2, 3, 1);
      this.field_110706_e.func_78793_a(0.0F, 4.0F, -10.0F);
      this.field_110706_e.field_78795_f = 0.5235988F;
      this.field_110703_f = new ModelRenderer(this, 0, 12);
      this.field_110703_f.func_78789_a(-2.0F, -16.0F, 4.0F, 2, 7, 1);
      this.field_110703_f.func_78793_a(0.0F, 4.0F, -10.0F);
      this.field_110703_f.field_78795_f = 0.5235988F;
      this.field_110703_f.field_78808_h = 0.2617994F;
      this.field_110704_g = new ModelRenderer(this, 0, 12);
      this.field_110704_g.func_78789_a(0.0F, -16.0F, 4.0F, 2, 7, 1);
      this.field_110704_g.func_78793_a(0.0F, 4.0F, -10.0F);
      this.field_110704_g.field_78795_f = 0.5235988F;
      this.field_110704_g.field_78808_h = -0.2617994F;
      this.field_110716_h = new ModelRenderer(this, 0, 12);
      this.field_110716_h.func_78789_a(-2.05F, -9.8F, -2.0F, 4, 14, 8);
      this.field_110716_h.func_78793_a(0.0F, 4.0F, -10.0F);
      this.field_110716_h.field_78795_f = 0.5235988F;
      this.field_110687_G = new ModelRenderer(this, 0, 34);
      this.field_110687_G.func_78789_a(-3.0F, 0.0F, 0.0F, 8, 8, 3);
      this.field_110687_G.func_78793_a(-7.5F, 3.0F, 10.0F);
      this.field_110687_G.field_78796_g = 1.5707964F;
      this.field_110695_H = new ModelRenderer(this, 0, 47);
      this.field_110695_H.func_78789_a(-3.0F, 0.0F, 0.0F, 8, 8, 3);
      this.field_110695_H.func_78793_a(4.5F, 3.0F, 10.0F);
      this.field_110695_H.field_78796_g = 1.5707964F;
      this.field_110696_I = new ModelRenderer(this, 80, 0);
      this.field_110696_I.func_78789_a(-5.0F, 0.0F, -3.0F, 10, 1, 8);
      this.field_110696_I.func_78793_a(0.0F, 2.0F, 2.0F);
      this.field_110697_J = new ModelRenderer(this, 106, 9);
      this.field_110697_J.func_78789_a(-1.5F, -1.0F, -3.0F, 3, 1, 2);
      this.field_110697_J.func_78793_a(0.0F, 2.0F, 2.0F);
      this.field_110698_K = new ModelRenderer(this, 80, 9);
      this.field_110698_K.func_78789_a(-4.0F, -1.0F, 3.0F, 8, 1, 2);
      this.field_110698_K.func_78793_a(0.0F, 2.0F, 2.0F);
      this.field_110692_M = new ModelRenderer(this, 74, 0);
      this.field_110692_M.func_78789_a(-0.5F, 6.0F, -1.0F, 1, 2, 2);
      this.field_110692_M.func_78793_a(5.0F, 3.0F, 2.0F);
      this.field_110691_L = new ModelRenderer(this, 70, 0);
      this.field_110691_L.func_78789_a(-0.5F, 0.0F, -0.5F, 1, 6, 1);
      this.field_110691_L.func_78793_a(5.0F, 3.0F, 2.0F);
      this.field_110694_O = new ModelRenderer(this, 74, 4);
      this.field_110694_O.func_78789_a(-0.5F, 6.0F, -1.0F, 1, 2, 2);
      this.field_110694_O.func_78793_a(-5.0F, 3.0F, 2.0F);
      this.field_110693_N = new ModelRenderer(this, 80, 0);
      this.field_110693_N.func_78789_a(-0.5F, 0.0F, -0.5F, 1, 6, 1);
      this.field_110693_N.func_78793_a(-5.0F, 3.0F, 2.0F);
      this.field_110700_P = new ModelRenderer(this, 74, 13);
      this.field_110700_P.func_78789_a(1.5F, -8.0F, -4.0F, 1, 2, 2);
      this.field_110700_P.func_78793_a(0.0F, 4.0F, -10.0F);
      this.field_110700_P.field_78795_f = 0.5235988F;
      this.field_110699_Q = new ModelRenderer(this, 74, 13);
      this.field_110699_Q.func_78789_a(-2.5F, -8.0F, -4.0F, 1, 2, 2);
      this.field_110699_Q.func_78793_a(0.0F, 4.0F, -10.0F);
      this.field_110699_Q.field_78795_f = 0.5235988F;
      this.field_110702_R = new ModelRenderer(this, 44, 10);
      this.field_110702_R.func_78789_a(2.6F, -6.0F, -6.0F, 0, 3, 16);
      this.field_110702_R.func_78793_a(0.0F, 4.0F, -10.0F);
      this.field_110701_S = new ModelRenderer(this, 44, 5);
      this.field_110701_S.func_78789_a(-2.6F, -6.0F, -6.0F, 0, 3, 16);
      this.field_110701_S.func_78793_a(0.0F, 4.0F, -10.0F);
      this.field_110714_j = new ModelRenderer(this, 58, 0);
      this.field_110714_j.func_78789_a(-1.0F, -11.5F, 5.0F, 2, 16, 4);
      this.field_110714_j.func_78793_a(0.0F, 4.0F, -10.0F);
      this.field_110714_j.field_78795_f = 0.5235988F;
      this.field_110717_i = new ModelRenderer(this, 80, 12);
      this.field_110717_i.func_78790_a(-2.5F, -10.1F, -7.0F, 5, 5, 12, 0.2F);
      this.field_110717_i.func_78793_a(0.0F, 4.0F, -10.0F);
      this.field_110717_i.field_78795_f = 0.5235988F;
   }

   public void func_78088_a(Entity p_78088_1_, float p_78088_2_, float p_78088_3_, float p_78088_4_, float p_78088_5_, float p_78088_6_, float p_78088_7_) {
      AbstractHorse abstracthorse = (AbstractHorse)p_78088_1_;
      float f = abstracthorse.func_110258_o(0.0F);
      boolean flag = abstracthorse.func_70631_g_();
      boolean flag1 = !flag && abstracthorse.func_110257_ck();
      boolean flag2 = abstracthorse instanceof AbstractChestHorse;
      boolean flag3 = !flag && flag2 && ((AbstractChestHorse)abstracthorse).func_190695_dh();
      float f1 = abstracthorse.func_110254_bY();
      boolean flag4 = abstracthorse.func_184207_aI();
      if (flag1) {
         this.field_110717_i.func_78785_a(p_78088_7_);
         this.field_110696_I.func_78785_a(p_78088_7_);
         this.field_110697_J.func_78785_a(p_78088_7_);
         this.field_110698_K.func_78785_a(p_78088_7_);
         this.field_110691_L.func_78785_a(p_78088_7_);
         this.field_110692_M.func_78785_a(p_78088_7_);
         this.field_110693_N.func_78785_a(p_78088_7_);
         this.field_110694_O.func_78785_a(p_78088_7_);
         this.field_110700_P.func_78785_a(p_78088_7_);
         this.field_110699_Q.func_78785_a(p_78088_7_);
         if (flag4) {
            this.field_110702_R.func_78785_a(p_78088_7_);
            this.field_110701_S.func_78785_a(p_78088_7_);
         }
      }

      if (flag) {
         GlStateManager.func_179094_E();
         GlStateManager.func_179152_a(f1, 0.5F + f1 * 0.5F, f1);
         GlStateManager.func_179109_b(0.0F, 0.95F * (1.0F - f1), 0.0F);
      }

      this.field_110711_o.func_78785_a(p_78088_7_);
      this.field_110719_v.func_78785_a(p_78088_7_);
      this.field_110718_w.func_78785_a(p_78088_7_);
      this.field_110722_x.func_78785_a(p_78088_7_);
      this.field_110721_y.func_78785_a(p_78088_7_);
      this.field_110720_z.func_78785_a(p_78088_7_);
      this.field_110688_A.func_78785_a(p_78088_7_);
      this.field_110689_B.func_78785_a(p_78088_7_);
      this.field_110690_C.func_78785_a(p_78088_7_);
      this.field_110684_D.func_78785_a(p_78088_7_);
      this.field_110685_E.func_78785_a(p_78088_7_);
      this.field_110686_F.func_78785_a(p_78088_7_);
      if (flag) {
         GlStateManager.func_179121_F();
         GlStateManager.func_179094_E();
         GlStateManager.func_179152_a(f1, f1, f1);
         GlStateManager.func_179109_b(0.0F, 1.35F * (1.0F - f1), 0.0F);
      }

      this.field_110715_k.func_78785_a(p_78088_7_);
      this.field_110712_l.func_78785_a(p_78088_7_);
      this.field_110713_m.func_78785_a(p_78088_7_);
      this.field_110710_n.func_78785_a(p_78088_7_);
      this.field_110716_h.func_78785_a(p_78088_7_);
      this.field_110714_j.func_78785_a(p_78088_7_);
      if (flag) {
         GlStateManager.func_179121_F();
         GlStateManager.func_179094_E();
         float f2 = 0.5F + f1 * f1 * 0.5F;
         GlStateManager.func_179152_a(f2, f2, f2);
         if (f <= 0.0F) {
            GlStateManager.func_179109_b(0.0F, 1.35F * (1.0F - f1), 0.0F);
         } else {
            GlStateManager.func_179109_b(0.0F, 0.9F * (1.0F - f1) * f + 1.35F * (1.0F - f1) * (1.0F - f), 0.15F * (1.0F - f1) * f);
         }
      }

      if (flag2) {
         this.field_110703_f.func_78785_a(p_78088_7_);
         this.field_110704_g.func_78785_a(p_78088_7_);
      } else {
         this.field_110705_d.func_78785_a(p_78088_7_);
         this.field_110706_e.func_78785_a(p_78088_7_);
      }

      this.field_110709_a.func_78785_a(p_78088_7_);
      if (flag) {
         GlStateManager.func_179121_F();
      }

      if (flag3) {
         this.field_110687_G.func_78785_a(p_78088_7_);
         this.field_110695_H.func_78785_a(p_78088_7_);
      }

   }

   private float func_110683_a(float p_110683_1_, float p_110683_2_, float p_110683_3_) {
      float f;
      for(f = p_110683_2_ - p_110683_1_; f < -180.0F; f += 360.0F) {
         ;
      }

      while(f >= 180.0F) {
         f -= 360.0F;
      }

      return p_110683_1_ + p_110683_3_ * f;
   }

   public void func_78086_a(EntityLivingBase p_78086_1_, float p_78086_2_, float p_78086_3_, float p_78086_4_) {
      super.func_78086_a(p_78086_1_, p_78086_2_, p_78086_3_, p_78086_4_);
      float f = this.func_110683_a(p_78086_1_.field_70760_ar, p_78086_1_.field_70761_aq, p_78086_4_);
      float f1 = this.func_110683_a(p_78086_1_.field_70758_at, p_78086_1_.field_70759_as, p_78086_4_);
      float f2 = p_78086_1_.field_70127_C + (p_78086_1_.field_70125_A - p_78086_1_.field_70127_C) * p_78086_4_;
      float f3 = f1 - f;
      float f4 = f2 * 0.017453292F;
      if (f3 > 20.0F) {
         f3 = 20.0F;
      }

      if (f3 < -20.0F) {
         f3 = -20.0F;
      }

      if (p_78086_3_ > 0.2F) {
         f4 += MathHelper.func_76134_b(p_78086_2_ * 0.4F) * 0.15F * p_78086_3_;
      }

      AbstractHorse abstracthorse = (AbstractHorse)p_78086_1_;
      float f5 = abstracthorse.func_110258_o(p_78086_4_);
      float f6 = abstracthorse.func_110223_p(p_78086_4_);
      float f7 = 1.0F - f6;
      float f8 = abstracthorse.func_110201_q(p_78086_4_);
      boolean flag = abstracthorse.field_110278_bp != 0;
      boolean flag1 = abstracthorse.func_110257_ck();
      boolean flag2 = abstracthorse.func_184207_aI();
      float f9 = (float)p_78086_1_.field_70173_aa + p_78086_4_;
      float f10 = MathHelper.func_76134_b(p_78086_2_ * 0.6662F + 3.1415927F);
      float f11 = f10 * 0.8F * p_78086_3_;
      this.field_110709_a.field_78797_d = 4.0F;
      this.field_110709_a.field_78798_e = -10.0F;
      this.field_110712_l.field_78797_d = 3.0F;
      this.field_110713_m.field_78798_e = 14.0F;
      this.field_110695_H.field_78797_d = 3.0F;
      this.field_110695_H.field_78798_e = 10.0F;
      this.field_110715_k.field_78795_f = 0.0F;
      this.field_110709_a.field_78795_f = 0.5235988F + f4;
      this.field_110709_a.field_78796_g = f3 * 0.017453292F;
      this.field_110709_a.field_78795_f = f6 * (0.2617994F + f4) + f5 * 2.1816616F + (1.0F - Math.max(f6, f5)) * this.field_110709_a.field_78795_f;
      this.field_110709_a.field_78796_g = f6 * f3 * 0.017453292F + (1.0F - Math.max(f6, f5)) * this.field_110709_a.field_78796_g;
      this.field_110709_a.field_78797_d = f6 * -6.0F + f5 * 11.0F + (1.0F - Math.max(f6, f5)) * this.field_110709_a.field_78797_d;
      this.field_110709_a.field_78798_e = f6 * -1.0F + f5 * -10.0F + (1.0F - Math.max(f6, f5)) * this.field_110709_a.field_78798_e;
      this.field_110712_l.field_78797_d = f6 * 9.0F + f7 * this.field_110712_l.field_78797_d;
      this.field_110713_m.field_78798_e = f6 * 18.0F + f7 * this.field_110713_m.field_78798_e;
      this.field_110695_H.field_78797_d = f6 * 5.5F + f7 * this.field_110695_H.field_78797_d;
      this.field_110695_H.field_78798_e = f6 * 15.0F + f7 * this.field_110695_H.field_78798_e;
      this.field_110715_k.field_78795_f = f6 * -0.7853982F + f7 * this.field_110715_k.field_78795_f;
      this.field_110705_d.field_78797_d = this.field_110709_a.field_78797_d;
      this.field_110706_e.field_78797_d = this.field_110709_a.field_78797_d;
      this.field_110703_f.field_78797_d = this.field_110709_a.field_78797_d;
      this.field_110704_g.field_78797_d = this.field_110709_a.field_78797_d;
      this.field_110716_h.field_78797_d = this.field_110709_a.field_78797_d;
      this.field_178711_b.field_78797_d = 0.02F;
      this.field_178712_c.field_78797_d = 0.0F;
      this.field_110714_j.field_78797_d = this.field_110709_a.field_78797_d;
      this.field_110705_d.field_78798_e = this.field_110709_a.field_78798_e;
      this.field_110706_e.field_78798_e = this.field_110709_a.field_78798_e;
      this.field_110703_f.field_78798_e = this.field_110709_a.field_78798_e;
      this.field_110704_g.field_78798_e = this.field_110709_a.field_78798_e;
      this.field_110716_h.field_78798_e = this.field_110709_a.field_78798_e;
      this.field_178711_b.field_78798_e = 0.02F - f8;
      this.field_178712_c.field_78798_e = f8;
      this.field_110714_j.field_78798_e = this.field_110709_a.field_78798_e;
      this.field_110705_d.field_78795_f = this.field_110709_a.field_78795_f;
      this.field_110706_e.field_78795_f = this.field_110709_a.field_78795_f;
      this.field_110703_f.field_78795_f = this.field_110709_a.field_78795_f;
      this.field_110704_g.field_78795_f = this.field_110709_a.field_78795_f;
      this.field_110716_h.field_78795_f = this.field_110709_a.field_78795_f;
      this.field_178711_b.field_78795_f = -0.09424778F * f8;
      this.field_178712_c.field_78795_f = 0.15707964F * f8;
      this.field_110714_j.field_78795_f = this.field_110709_a.field_78795_f;
      this.field_110705_d.field_78796_g = this.field_110709_a.field_78796_g;
      this.field_110706_e.field_78796_g = this.field_110709_a.field_78796_g;
      this.field_110703_f.field_78796_g = this.field_110709_a.field_78796_g;
      this.field_110704_g.field_78796_g = this.field_110709_a.field_78796_g;
      this.field_110716_h.field_78796_g = this.field_110709_a.field_78796_g;
      this.field_178711_b.field_78796_g = 0.0F;
      this.field_178712_c.field_78796_g = 0.0F;
      this.field_110714_j.field_78796_g = this.field_110709_a.field_78796_g;
      this.field_110687_G.field_78795_f = f11 / 5.0F;
      this.field_110695_H.field_78795_f = -f11 / 5.0F;
      float f12 = 0.2617994F * f6;
      float f13 = MathHelper.func_76134_b(f9 * 0.6F + 3.1415927F);
      this.field_110688_A.field_78797_d = -2.0F * f6 + 9.0F * f7;
      this.field_110688_A.field_78798_e = -2.0F * f6 + -8.0F * f7;
      this.field_110684_D.field_78797_d = this.field_110688_A.field_78797_d;
      this.field_110684_D.field_78798_e = this.field_110688_A.field_78798_e;
      this.field_110719_v.field_78797_d = this.field_110711_o.field_78797_d + MathHelper.func_76126_a(1.5707964F + f12 + f7 * -f10 * 0.5F * p_78086_3_) * 7.0F;
      this.field_110719_v.field_78798_e = this.field_110711_o.field_78798_e + MathHelper.func_76134_b(-1.5707964F + f12 + f7 * -f10 * 0.5F * p_78086_3_) * 7.0F;
      this.field_110721_y.field_78797_d = this.field_110722_x.field_78797_d + MathHelper.func_76126_a(1.5707964F + f12 + f7 * f10 * 0.5F * p_78086_3_) * 7.0F;
      this.field_110721_y.field_78798_e = this.field_110722_x.field_78798_e + MathHelper.func_76134_b(-1.5707964F + f12 + f7 * f10 * 0.5F * p_78086_3_) * 7.0F;
      float f14 = (-1.0471976F + f13) * f6 + f11 * f7;
      float f15 = (-1.0471976F - f13) * f6 + -f11 * f7;
      this.field_110689_B.field_78797_d = this.field_110688_A.field_78797_d + MathHelper.func_76126_a(1.5707964F + f14) * 7.0F;
      this.field_110689_B.field_78798_e = this.field_110688_A.field_78798_e + MathHelper.func_76134_b(-1.5707964F + f14) * 7.0F;
      this.field_110685_E.field_78797_d = this.field_110684_D.field_78797_d + MathHelper.func_76126_a(1.5707964F + f15) * 7.0F;
      this.field_110685_E.field_78798_e = this.field_110684_D.field_78798_e + MathHelper.func_76134_b(-1.5707964F + f15) * 7.0F;
      this.field_110711_o.field_78795_f = f12 + -f10 * 0.5F * p_78086_3_ * f7;
      this.field_110719_v.field_78795_f = -0.08726646F * f6 + (-f10 * 0.5F * p_78086_3_ - Math.max(0.0F, f10 * 0.5F * p_78086_3_)) * f7;
      this.field_110718_w.field_78795_f = this.field_110719_v.field_78795_f;
      this.field_110722_x.field_78795_f = f12 + f10 * 0.5F * p_78086_3_ * f7;
      this.field_110721_y.field_78795_f = -0.08726646F * f6 + (f10 * 0.5F * p_78086_3_ - Math.max(0.0F, -f10 * 0.5F * p_78086_3_)) * f7;
      this.field_110720_z.field_78795_f = this.field_110721_y.field_78795_f;
      this.field_110688_A.field_78795_f = f14;
      this.field_110689_B.field_78795_f = (this.field_110688_A.field_78795_f + 3.1415927F * Math.max(0.0F, 0.2F + f13 * 0.2F)) * f6 + (f11 + Math.max(0.0F, f10 * 0.5F * p_78086_3_)) * f7;
      this.field_110690_C.field_78795_f = this.field_110689_B.field_78795_f;
      this.field_110684_D.field_78795_f = f15;
      this.field_110685_E.field_78795_f = (this.field_110684_D.field_78795_f + 3.1415927F * Math.max(0.0F, 0.2F - f13 * 0.2F)) * f6 + (-f11 + Math.max(0.0F, -f10 * 0.5F * p_78086_3_)) * f7;
      this.field_110686_F.field_78795_f = this.field_110685_E.field_78795_f;
      this.field_110718_w.field_78797_d = this.field_110719_v.field_78797_d;
      this.field_110718_w.field_78798_e = this.field_110719_v.field_78798_e;
      this.field_110720_z.field_78797_d = this.field_110721_y.field_78797_d;
      this.field_110720_z.field_78798_e = this.field_110721_y.field_78798_e;
      this.field_110690_C.field_78797_d = this.field_110689_B.field_78797_d;
      this.field_110690_C.field_78798_e = this.field_110689_B.field_78798_e;
      this.field_110686_F.field_78797_d = this.field_110685_E.field_78797_d;
      this.field_110686_F.field_78798_e = this.field_110685_E.field_78798_e;
      if (flag1) {
         this.field_110696_I.field_78797_d = f6 * 0.5F + f7 * 2.0F;
         this.field_110696_I.field_78798_e = f6 * 11.0F + f7 * 2.0F;
         this.field_110697_J.field_78797_d = this.field_110696_I.field_78797_d;
         this.field_110698_K.field_78797_d = this.field_110696_I.field_78797_d;
         this.field_110691_L.field_78797_d = this.field_110696_I.field_78797_d;
         this.field_110693_N.field_78797_d = this.field_110696_I.field_78797_d;
         this.field_110692_M.field_78797_d = this.field_110696_I.field_78797_d;
         this.field_110694_O.field_78797_d = this.field_110696_I.field_78797_d;
         this.field_110687_G.field_78797_d = this.field_110695_H.field_78797_d;
         this.field_110697_J.field_78798_e = this.field_110696_I.field_78798_e;
         this.field_110698_K.field_78798_e = this.field_110696_I.field_78798_e;
         this.field_110691_L.field_78798_e = this.field_110696_I.field_78798_e;
         this.field_110693_N.field_78798_e = this.field_110696_I.field_78798_e;
         this.field_110692_M.field_78798_e = this.field_110696_I.field_78798_e;
         this.field_110694_O.field_78798_e = this.field_110696_I.field_78798_e;
         this.field_110687_G.field_78798_e = this.field_110695_H.field_78798_e;
         this.field_110696_I.field_78795_f = this.field_110715_k.field_78795_f;
         this.field_110697_J.field_78795_f = this.field_110715_k.field_78795_f;
         this.field_110698_K.field_78795_f = this.field_110715_k.field_78795_f;
         this.field_110702_R.field_78797_d = this.field_110709_a.field_78797_d;
         this.field_110701_S.field_78797_d = this.field_110709_a.field_78797_d;
         this.field_110717_i.field_78797_d = this.field_110709_a.field_78797_d;
         this.field_110700_P.field_78797_d = this.field_110709_a.field_78797_d;
         this.field_110699_Q.field_78797_d = this.field_110709_a.field_78797_d;
         this.field_110702_R.field_78798_e = this.field_110709_a.field_78798_e;
         this.field_110701_S.field_78798_e = this.field_110709_a.field_78798_e;
         this.field_110717_i.field_78798_e = this.field_110709_a.field_78798_e;
         this.field_110700_P.field_78798_e = this.field_110709_a.field_78798_e;
         this.field_110699_Q.field_78798_e = this.field_110709_a.field_78798_e;
         this.field_110702_R.field_78795_f = f4;
         this.field_110701_S.field_78795_f = f4;
         this.field_110717_i.field_78795_f = this.field_110709_a.field_78795_f;
         this.field_110700_P.field_78795_f = this.field_110709_a.field_78795_f;
         this.field_110699_Q.field_78795_f = this.field_110709_a.field_78795_f;
         this.field_110717_i.field_78796_g = this.field_110709_a.field_78796_g;
         this.field_110700_P.field_78796_g = this.field_110709_a.field_78796_g;
         this.field_110702_R.field_78796_g = this.field_110709_a.field_78796_g;
         this.field_110699_Q.field_78796_g = this.field_110709_a.field_78796_g;
         this.field_110701_S.field_78796_g = this.field_110709_a.field_78796_g;
         if (flag2) {
            this.field_110691_L.field_78795_f = -1.0471976F;
            this.field_110692_M.field_78795_f = -1.0471976F;
            this.field_110693_N.field_78795_f = -1.0471976F;
            this.field_110694_O.field_78795_f = -1.0471976F;
            this.field_110691_L.field_78808_h = 0.0F;
            this.field_110692_M.field_78808_h = 0.0F;
            this.field_110693_N.field_78808_h = 0.0F;
            this.field_110694_O.field_78808_h = 0.0F;
         } else {
            this.field_110691_L.field_78795_f = f11 / 3.0F;
            this.field_110692_M.field_78795_f = f11 / 3.0F;
            this.field_110693_N.field_78795_f = f11 / 3.0F;
            this.field_110694_O.field_78795_f = f11 / 3.0F;
            this.field_110691_L.field_78808_h = f11 / 5.0F;
            this.field_110692_M.field_78808_h = f11 / 5.0F;
            this.field_110693_N.field_78808_h = -f11 / 5.0F;
            this.field_110694_O.field_78808_h = -f11 / 5.0F;
         }
      }

      f12 = -1.3089969F + p_78086_3_ * 1.5F;
      if (f12 > 0.0F) {
         f12 = 0.0F;
      }

      if (flag) {
         this.field_110712_l.field_78796_g = MathHelper.func_76134_b(f9 * 0.7F);
         f12 = 0.0F;
      } else {
         this.field_110712_l.field_78796_g = 0.0F;
      }

      this.field_110713_m.field_78796_g = this.field_110712_l.field_78796_g;
      this.field_110710_n.field_78796_g = this.field_110712_l.field_78796_g;
      this.field_110713_m.field_78797_d = this.field_110712_l.field_78797_d;
      this.field_110710_n.field_78797_d = this.field_110712_l.field_78797_d;
      this.field_110713_m.field_78798_e = this.field_110712_l.field_78798_e;
      this.field_110710_n.field_78798_e = this.field_110712_l.field_78798_e;
      this.field_110712_l.field_78795_f = f12;
      this.field_110713_m.field_78795_f = f12;
      this.field_110710_n.field_78795_f = -0.2617994F + f12;
   }
}
