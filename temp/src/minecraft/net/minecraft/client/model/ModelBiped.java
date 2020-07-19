package net.minecraft.client.model;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.math.MathHelper;

public class ModelBiped extends ModelBase {
   public ModelRenderer field_78116_c;
   public ModelRenderer field_178720_f;
   public ModelRenderer field_78115_e;
   public ModelRenderer field_178723_h;
   public ModelRenderer field_178724_i;
   public ModelRenderer field_178721_j;
   public ModelRenderer field_178722_k;
   public ModelBiped.ArmPose field_187075_l;
   public ModelBiped.ArmPose field_187076_m;
   public boolean field_78117_n;

   public ModelBiped() {
      this(0.0F);
   }

   public ModelBiped(float p_i1148_1_) {
      this(p_i1148_1_, 0.0F, 64, 32);
   }

   public ModelBiped(float p_i1149_1_, float p_i1149_2_, int p_i1149_3_, int p_i1149_4_) {
      this.field_187075_l = ModelBiped.ArmPose.EMPTY;
      this.field_187076_m = ModelBiped.ArmPose.EMPTY;
      this.field_78090_t = p_i1149_3_;
      this.field_78089_u = p_i1149_4_;
      this.field_78116_c = new ModelRenderer(this, 0, 0);
      this.field_78116_c.func_78790_a(-4.0F, -8.0F, -4.0F, 8, 8, 8, p_i1149_1_);
      this.field_78116_c.func_78793_a(0.0F, 0.0F + p_i1149_2_, 0.0F);
      this.field_178720_f = new ModelRenderer(this, 32, 0);
      this.field_178720_f.func_78790_a(-4.0F, -8.0F, -4.0F, 8, 8, 8, p_i1149_1_ + 0.5F);
      this.field_178720_f.func_78793_a(0.0F, 0.0F + p_i1149_2_, 0.0F);
      this.field_78115_e = new ModelRenderer(this, 16, 16);
      this.field_78115_e.func_78790_a(-4.0F, 0.0F, -2.0F, 8, 12, 4, p_i1149_1_);
      this.field_78115_e.func_78793_a(0.0F, 0.0F + p_i1149_2_, 0.0F);
      this.field_178723_h = new ModelRenderer(this, 40, 16);
      this.field_178723_h.func_78790_a(-3.0F, -2.0F, -2.0F, 4, 12, 4, p_i1149_1_);
      this.field_178723_h.func_78793_a(-5.0F, 2.0F + p_i1149_2_, 0.0F);
      this.field_178724_i = new ModelRenderer(this, 40, 16);
      this.field_178724_i.field_78809_i = true;
      this.field_178724_i.func_78790_a(-1.0F, -2.0F, -2.0F, 4, 12, 4, p_i1149_1_);
      this.field_178724_i.func_78793_a(5.0F, 2.0F + p_i1149_2_, 0.0F);
      this.field_178721_j = new ModelRenderer(this, 0, 16);
      this.field_178721_j.func_78790_a(-2.0F, 0.0F, -2.0F, 4, 12, 4, p_i1149_1_);
      this.field_178721_j.func_78793_a(-1.9F, 12.0F + p_i1149_2_, 0.0F);
      this.field_178722_k = new ModelRenderer(this, 0, 16);
      this.field_178722_k.field_78809_i = true;
      this.field_178722_k.func_78790_a(-2.0F, 0.0F, -2.0F, 4, 12, 4, p_i1149_1_);
      this.field_178722_k.func_78793_a(1.9F, 12.0F + p_i1149_2_, 0.0F);
   }

   public void func_78088_a(Entity p_78088_1_, float p_78088_2_, float p_78088_3_, float p_78088_4_, float p_78088_5_, float p_78088_6_, float p_78088_7_) {
      this.func_78087_a(p_78088_2_, p_78088_3_, p_78088_4_, p_78088_5_, p_78088_6_, p_78088_7_, p_78088_1_);
      GlStateManager.func_179094_E();
      if (this.field_78091_s) {
         float f = 2.0F;
         GlStateManager.func_179152_a(0.75F, 0.75F, 0.75F);
         GlStateManager.func_179109_b(0.0F, 16.0F * p_78088_7_, 0.0F);
         this.field_78116_c.func_78785_a(p_78088_7_);
         GlStateManager.func_179121_F();
         GlStateManager.func_179094_E();
         GlStateManager.func_179152_a(0.5F, 0.5F, 0.5F);
         GlStateManager.func_179109_b(0.0F, 24.0F * p_78088_7_, 0.0F);
         this.field_78115_e.func_78785_a(p_78088_7_);
         this.field_178723_h.func_78785_a(p_78088_7_);
         this.field_178724_i.func_78785_a(p_78088_7_);
         this.field_178721_j.func_78785_a(p_78088_7_);
         this.field_178722_k.func_78785_a(p_78088_7_);
         this.field_178720_f.func_78785_a(p_78088_7_);
      } else {
         if (p_78088_1_.func_70093_af()) {
            GlStateManager.func_179109_b(0.0F, 0.2F, 0.0F);
         }

         this.field_78116_c.func_78785_a(p_78088_7_);
         this.field_78115_e.func_78785_a(p_78088_7_);
         this.field_178723_h.func_78785_a(p_78088_7_);
         this.field_178724_i.func_78785_a(p_78088_7_);
         this.field_178721_j.func_78785_a(p_78088_7_);
         this.field_178722_k.func_78785_a(p_78088_7_);
         this.field_178720_f.func_78785_a(p_78088_7_);
      }

      GlStateManager.func_179121_F();
   }

   public void func_78087_a(float p_78087_1_, float p_78087_2_, float p_78087_3_, float p_78087_4_, float p_78087_5_, float p_78087_6_, Entity p_78087_7_) {
      boolean flag = p_78087_7_ instanceof EntityLivingBase && ((EntityLivingBase)p_78087_7_).func_184599_cB() > 4;
      this.field_78116_c.field_78796_g = p_78087_4_ * 0.017453292F;
      if (flag) {
         this.field_78116_c.field_78795_f = -0.7853982F;
      } else {
         this.field_78116_c.field_78795_f = p_78087_5_ * 0.017453292F;
      }

      this.field_78115_e.field_78796_g = 0.0F;
      this.field_178723_h.field_78798_e = 0.0F;
      this.field_178723_h.field_78800_c = -5.0F;
      this.field_178724_i.field_78798_e = 0.0F;
      this.field_178724_i.field_78800_c = 5.0F;
      float f = 1.0F;
      if (flag) {
         f = (float)(p_78087_7_.field_70159_w * p_78087_7_.field_70159_w + p_78087_7_.field_70181_x * p_78087_7_.field_70181_x + p_78087_7_.field_70179_y * p_78087_7_.field_70179_y);
         f = f / 0.2F;
         f = f * f * f;
      }

      if (f < 1.0F) {
         f = 1.0F;
      }

      this.field_178723_h.field_78795_f = MathHelper.func_76134_b(p_78087_1_ * 0.6662F + 3.1415927F) * 2.0F * p_78087_2_ * 0.5F / f;
      this.field_178724_i.field_78795_f = MathHelper.func_76134_b(p_78087_1_ * 0.6662F) * 2.0F * p_78087_2_ * 0.5F / f;
      this.field_178723_h.field_78808_h = 0.0F;
      this.field_178724_i.field_78808_h = 0.0F;
      this.field_178721_j.field_78795_f = MathHelper.func_76134_b(p_78087_1_ * 0.6662F) * 1.4F * p_78087_2_ / f;
      this.field_178722_k.field_78795_f = MathHelper.func_76134_b(p_78087_1_ * 0.6662F + 3.1415927F) * 1.4F * p_78087_2_ / f;
      this.field_178721_j.field_78796_g = 0.0F;
      this.field_178722_k.field_78796_g = 0.0F;
      this.field_178721_j.field_78808_h = 0.0F;
      this.field_178722_k.field_78808_h = 0.0F;
      if (this.field_78093_q) {
         this.field_178723_h.field_78795_f += -0.62831855F;
         this.field_178724_i.field_78795_f += -0.62831855F;
         this.field_178721_j.field_78795_f = -1.4137167F;
         this.field_178721_j.field_78796_g = 0.31415927F;
         this.field_178721_j.field_78808_h = 0.07853982F;
         this.field_178722_k.field_78795_f = -1.4137167F;
         this.field_178722_k.field_78796_g = -0.31415927F;
         this.field_178722_k.field_78808_h = -0.07853982F;
      }

      this.field_178723_h.field_78796_g = 0.0F;
      this.field_178723_h.field_78808_h = 0.0F;
      switch(this.field_187075_l) {
      case EMPTY:
         this.field_178724_i.field_78796_g = 0.0F;
         break;
      case BLOCK:
         this.field_178724_i.field_78795_f = this.field_178724_i.field_78795_f * 0.5F - 0.9424779F;
         this.field_178724_i.field_78796_g = 0.5235988F;
         break;
      case ITEM:
         this.field_178724_i.field_78795_f = this.field_178724_i.field_78795_f * 0.5F - 0.31415927F;
         this.field_178724_i.field_78796_g = 0.0F;
      }

      switch(this.field_187076_m) {
      case EMPTY:
         this.field_178723_h.field_78796_g = 0.0F;
         break;
      case BLOCK:
         this.field_178723_h.field_78795_f = this.field_178723_h.field_78795_f * 0.5F - 0.9424779F;
         this.field_178723_h.field_78796_g = -0.5235988F;
         break;
      case ITEM:
         this.field_178723_h.field_78795_f = this.field_178723_h.field_78795_f * 0.5F - 0.31415927F;
         this.field_178723_h.field_78796_g = 0.0F;
      }

      if (this.field_78095_p > 0.0F) {
         EnumHandSide enumhandside = this.func_187072_a(p_78087_7_);
         ModelRenderer modelrenderer = this.func_187074_a(enumhandside);
         float f1 = this.field_78095_p;
         this.field_78115_e.field_78796_g = MathHelper.func_76126_a(MathHelper.func_76129_c(f1) * 6.2831855F) * 0.2F;
         if (enumhandside == EnumHandSide.LEFT) {
            this.field_78115_e.field_78796_g *= -1.0F;
         }

         this.field_178723_h.field_78798_e = MathHelper.func_76126_a(this.field_78115_e.field_78796_g) * 5.0F;
         this.field_178723_h.field_78800_c = -MathHelper.func_76134_b(this.field_78115_e.field_78796_g) * 5.0F;
         this.field_178724_i.field_78798_e = -MathHelper.func_76126_a(this.field_78115_e.field_78796_g) * 5.0F;
         this.field_178724_i.field_78800_c = MathHelper.func_76134_b(this.field_78115_e.field_78796_g) * 5.0F;
         this.field_178723_h.field_78796_g += this.field_78115_e.field_78796_g;
         this.field_178724_i.field_78796_g += this.field_78115_e.field_78796_g;
         this.field_178724_i.field_78795_f += this.field_78115_e.field_78796_g;
         f1 = 1.0F - this.field_78095_p;
         f1 = f1 * f1;
         f1 = f1 * f1;
         f1 = 1.0F - f1;
         float f2 = MathHelper.func_76126_a(f1 * 3.1415927F);
         float f3 = MathHelper.func_76126_a(this.field_78095_p * 3.1415927F) * -(this.field_78116_c.field_78795_f - 0.7F) * 0.75F;
         modelrenderer.field_78795_f = (float)((double)modelrenderer.field_78795_f - ((double)f2 * 1.2D + (double)f3));
         modelrenderer.field_78796_g += this.field_78115_e.field_78796_g * 2.0F;
         modelrenderer.field_78808_h += MathHelper.func_76126_a(this.field_78095_p * 3.1415927F) * -0.4F;
      }

      if (this.field_78117_n) {
         this.field_78115_e.field_78795_f = 0.5F;
         this.field_178723_h.field_78795_f += 0.4F;
         this.field_178724_i.field_78795_f += 0.4F;
         this.field_178721_j.field_78798_e = 4.0F;
         this.field_178722_k.field_78798_e = 4.0F;
         this.field_178721_j.field_78797_d = 9.0F;
         this.field_178722_k.field_78797_d = 9.0F;
         this.field_78116_c.field_78797_d = 1.0F;
      } else {
         this.field_78115_e.field_78795_f = 0.0F;
         this.field_178721_j.field_78798_e = 0.1F;
         this.field_178722_k.field_78798_e = 0.1F;
         this.field_178721_j.field_78797_d = 12.0F;
         this.field_178722_k.field_78797_d = 12.0F;
         this.field_78116_c.field_78797_d = 0.0F;
      }

      this.field_178723_h.field_78808_h += MathHelper.func_76134_b(p_78087_3_ * 0.09F) * 0.05F + 0.05F;
      this.field_178724_i.field_78808_h -= MathHelper.func_76134_b(p_78087_3_ * 0.09F) * 0.05F + 0.05F;
      this.field_178723_h.field_78795_f += MathHelper.func_76126_a(p_78087_3_ * 0.067F) * 0.05F;
      this.field_178724_i.field_78795_f -= MathHelper.func_76126_a(p_78087_3_ * 0.067F) * 0.05F;
      if (this.field_187076_m == ModelBiped.ArmPose.BOW_AND_ARROW) {
         this.field_178723_h.field_78796_g = -0.1F + this.field_78116_c.field_78796_g;
         this.field_178724_i.field_78796_g = 0.1F + this.field_78116_c.field_78796_g + 0.4F;
         this.field_178723_h.field_78795_f = -1.5707964F + this.field_78116_c.field_78795_f;
         this.field_178724_i.field_78795_f = -1.5707964F + this.field_78116_c.field_78795_f;
      } else if (this.field_187075_l == ModelBiped.ArmPose.BOW_AND_ARROW) {
         this.field_178723_h.field_78796_g = -0.1F + this.field_78116_c.field_78796_g - 0.4F;
         this.field_178724_i.field_78796_g = 0.1F + this.field_78116_c.field_78796_g;
         this.field_178723_h.field_78795_f = -1.5707964F + this.field_78116_c.field_78795_f;
         this.field_178724_i.field_78795_f = -1.5707964F + this.field_78116_c.field_78795_f;
      }

      func_178685_a(this.field_78116_c, this.field_178720_f);
   }

   public void func_178686_a(ModelBase p_178686_1_) {
      super.func_178686_a(p_178686_1_);
      if (p_178686_1_ instanceof ModelBiped) {
         ModelBiped modelbiped = (ModelBiped)p_178686_1_;
         this.field_187075_l = modelbiped.field_187075_l;
         this.field_187076_m = modelbiped.field_187076_m;
         this.field_78117_n = modelbiped.field_78117_n;
      }

   }

   public void func_178719_a(boolean p_178719_1_) {
      this.field_78116_c.field_78806_j = p_178719_1_;
      this.field_178720_f.field_78806_j = p_178719_1_;
      this.field_78115_e.field_78806_j = p_178719_1_;
      this.field_178723_h.field_78806_j = p_178719_1_;
      this.field_178724_i.field_78806_j = p_178719_1_;
      this.field_178721_j.field_78806_j = p_178719_1_;
      this.field_178722_k.field_78806_j = p_178719_1_;
   }

   public void func_187073_a(float p_187073_1_, EnumHandSide p_187073_2_) {
      this.func_187074_a(p_187073_2_).func_78794_c(p_187073_1_);
   }

   protected ModelRenderer func_187074_a(EnumHandSide p_187074_1_) {
      return p_187074_1_ == EnumHandSide.LEFT ? this.field_178724_i : this.field_178723_h;
   }

   protected EnumHandSide func_187072_a(Entity p_187072_1_) {
      if (p_187072_1_ instanceof EntityLivingBase) {
         EntityLivingBase entitylivingbase = (EntityLivingBase)p_187072_1_;
         EnumHandSide enumhandside = entitylivingbase.func_184591_cq();
         return entitylivingbase.field_184622_au == EnumHand.MAIN_HAND ? enumhandside : enumhandside.func_188468_a();
      } else {
         return EnumHandSide.RIGHT;
      }
   }

   public static enum ArmPose {
      EMPTY,
      ITEM,
      BLOCK,
      BOW_AND_ARROW;
   }
}
