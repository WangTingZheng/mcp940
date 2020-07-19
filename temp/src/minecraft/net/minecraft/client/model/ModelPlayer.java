package net.minecraft.client.model;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumHandSide;

public class ModelPlayer extends ModelBiped {
   public ModelRenderer field_178734_a;
   public ModelRenderer field_178732_b;
   public ModelRenderer field_178733_c;
   public ModelRenderer field_178731_d;
   public ModelRenderer field_178730_v;
   private final ModelRenderer field_178729_w;
   private final ModelRenderer field_178736_x;
   private final boolean field_178735_y;

   public ModelPlayer(float p_i46304_1_, boolean p_i46304_2_) {
      super(p_i46304_1_, 0.0F, 64, 64);
      this.field_178735_y = p_i46304_2_;
      this.field_178736_x = new ModelRenderer(this, 24, 0);
      this.field_178736_x.func_78790_a(-3.0F, -6.0F, -1.0F, 6, 6, 1, p_i46304_1_);
      this.field_178729_w = new ModelRenderer(this, 0, 0);
      this.field_178729_w.func_78787_b(64, 32);
      this.field_178729_w.func_78790_a(-5.0F, 0.0F, -1.0F, 10, 16, 1, p_i46304_1_);
      if (p_i46304_2_) {
         this.field_178724_i = new ModelRenderer(this, 32, 48);
         this.field_178724_i.func_78790_a(-1.0F, -2.0F, -2.0F, 3, 12, 4, p_i46304_1_);
         this.field_178724_i.func_78793_a(5.0F, 2.5F, 0.0F);
         this.field_178723_h = new ModelRenderer(this, 40, 16);
         this.field_178723_h.func_78790_a(-2.0F, -2.0F, -2.0F, 3, 12, 4, p_i46304_1_);
         this.field_178723_h.func_78793_a(-5.0F, 2.5F, 0.0F);
         this.field_178734_a = new ModelRenderer(this, 48, 48);
         this.field_178734_a.func_78790_a(-1.0F, -2.0F, -2.0F, 3, 12, 4, p_i46304_1_ + 0.25F);
         this.field_178734_a.func_78793_a(5.0F, 2.5F, 0.0F);
         this.field_178732_b = new ModelRenderer(this, 40, 32);
         this.field_178732_b.func_78790_a(-2.0F, -2.0F, -2.0F, 3, 12, 4, p_i46304_1_ + 0.25F);
         this.field_178732_b.func_78793_a(-5.0F, 2.5F, 10.0F);
      } else {
         this.field_178724_i = new ModelRenderer(this, 32, 48);
         this.field_178724_i.func_78790_a(-1.0F, -2.0F, -2.0F, 4, 12, 4, p_i46304_1_);
         this.field_178724_i.func_78793_a(5.0F, 2.0F, 0.0F);
         this.field_178734_a = new ModelRenderer(this, 48, 48);
         this.field_178734_a.func_78790_a(-1.0F, -2.0F, -2.0F, 4, 12, 4, p_i46304_1_ + 0.25F);
         this.field_178734_a.func_78793_a(5.0F, 2.0F, 0.0F);
         this.field_178732_b = new ModelRenderer(this, 40, 32);
         this.field_178732_b.func_78790_a(-3.0F, -2.0F, -2.0F, 4, 12, 4, p_i46304_1_ + 0.25F);
         this.field_178732_b.func_78793_a(-5.0F, 2.0F, 10.0F);
      }

      this.field_178722_k = new ModelRenderer(this, 16, 48);
      this.field_178722_k.func_78790_a(-2.0F, 0.0F, -2.0F, 4, 12, 4, p_i46304_1_);
      this.field_178722_k.func_78793_a(1.9F, 12.0F, 0.0F);
      this.field_178733_c = new ModelRenderer(this, 0, 48);
      this.field_178733_c.func_78790_a(-2.0F, 0.0F, -2.0F, 4, 12, 4, p_i46304_1_ + 0.25F);
      this.field_178733_c.func_78793_a(1.9F, 12.0F, 0.0F);
      this.field_178731_d = new ModelRenderer(this, 0, 32);
      this.field_178731_d.func_78790_a(-2.0F, 0.0F, -2.0F, 4, 12, 4, p_i46304_1_ + 0.25F);
      this.field_178731_d.func_78793_a(-1.9F, 12.0F, 0.0F);
      this.field_178730_v = new ModelRenderer(this, 16, 32);
      this.field_178730_v.func_78790_a(-4.0F, 0.0F, -2.0F, 8, 12, 4, p_i46304_1_ + 0.25F);
      this.field_178730_v.func_78793_a(0.0F, 0.0F, 0.0F);
   }

   public void func_78088_a(Entity p_78088_1_, float p_78088_2_, float p_78088_3_, float p_78088_4_, float p_78088_5_, float p_78088_6_, float p_78088_7_) {
      super.func_78088_a(p_78088_1_, p_78088_2_, p_78088_3_, p_78088_4_, p_78088_5_, p_78088_6_, p_78088_7_);
      GlStateManager.func_179094_E();
      if (this.field_78091_s) {
         float f = 2.0F;
         GlStateManager.func_179152_a(0.5F, 0.5F, 0.5F);
         GlStateManager.func_179109_b(0.0F, 24.0F * p_78088_7_, 0.0F);
         this.field_178733_c.func_78785_a(p_78088_7_);
         this.field_178731_d.func_78785_a(p_78088_7_);
         this.field_178734_a.func_78785_a(p_78088_7_);
         this.field_178732_b.func_78785_a(p_78088_7_);
         this.field_178730_v.func_78785_a(p_78088_7_);
      } else {
         if (p_78088_1_.func_70093_af()) {
            GlStateManager.func_179109_b(0.0F, 0.2F, 0.0F);
         }

         this.field_178733_c.func_78785_a(p_78088_7_);
         this.field_178731_d.func_78785_a(p_78088_7_);
         this.field_178734_a.func_78785_a(p_78088_7_);
         this.field_178732_b.func_78785_a(p_78088_7_);
         this.field_178730_v.func_78785_a(p_78088_7_);
      }

      GlStateManager.func_179121_F();
   }

   public void func_178727_b(float p_178727_1_) {
      func_178685_a(this.field_78116_c, this.field_178736_x);
      this.field_178736_x.field_78800_c = 0.0F;
      this.field_178736_x.field_78797_d = 0.0F;
      this.field_178736_x.func_78785_a(p_178727_1_);
   }

   public void func_178728_c(float p_178728_1_) {
      this.field_178729_w.func_78785_a(p_178728_1_);
   }

   public void func_78087_a(float p_78087_1_, float p_78087_2_, float p_78087_3_, float p_78087_4_, float p_78087_5_, float p_78087_6_, Entity p_78087_7_) {
      super.func_78087_a(p_78087_1_, p_78087_2_, p_78087_3_, p_78087_4_, p_78087_5_, p_78087_6_, p_78087_7_);
      func_178685_a(this.field_178722_k, this.field_178733_c);
      func_178685_a(this.field_178721_j, this.field_178731_d);
      func_178685_a(this.field_178724_i, this.field_178734_a);
      func_178685_a(this.field_178723_h, this.field_178732_b);
      func_178685_a(this.field_78115_e, this.field_178730_v);
      if (p_78087_7_.func_70093_af()) {
         this.field_178729_w.field_78797_d = 2.0F;
      } else {
         this.field_178729_w.field_78797_d = 0.0F;
      }

   }

   public void func_178719_a(boolean p_178719_1_) {
      super.func_178719_a(p_178719_1_);
      this.field_178734_a.field_78806_j = p_178719_1_;
      this.field_178732_b.field_78806_j = p_178719_1_;
      this.field_178733_c.field_78806_j = p_178719_1_;
      this.field_178731_d.field_78806_j = p_178719_1_;
      this.field_178730_v.field_78806_j = p_178719_1_;
      this.field_178729_w.field_78806_j = p_178719_1_;
      this.field_178736_x.field_78806_j = p_178719_1_;
   }

   public void func_187073_a(float p_187073_1_, EnumHandSide p_187073_2_) {
      ModelRenderer modelrenderer = this.func_187074_a(p_187073_2_);
      if (this.field_178735_y) {
         float f = 0.5F * (float)(p_187073_2_ == EnumHandSide.RIGHT ? 1 : -1);
         modelrenderer.field_78800_c += f;
         modelrenderer.func_78794_c(p_187073_1_);
         modelrenderer.field_78800_c -= f;
      } else {
         modelrenderer.func_78794_c(p_187073_1_);
      }

   }
}
