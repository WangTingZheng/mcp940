package net.minecraft.client.model;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.AbstractSkeleton;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.math.MathHelper;

public class ModelSkeleton extends ModelBiped {
   public ModelSkeleton() {
      this(0.0F, false);
   }

   public ModelSkeleton(float p_i46303_1_, boolean p_i46303_2_) {
      super(p_i46303_1_, 0.0F, 64, 32);
      if (!p_i46303_2_) {
         this.field_178723_h = new ModelRenderer(this, 40, 16);
         this.field_178723_h.func_78790_a(-1.0F, -2.0F, -1.0F, 2, 12, 2, p_i46303_1_);
         this.field_178723_h.func_78793_a(-5.0F, 2.0F, 0.0F);
         this.field_178724_i = new ModelRenderer(this, 40, 16);
         this.field_178724_i.field_78809_i = true;
         this.field_178724_i.func_78790_a(-1.0F, -2.0F, -1.0F, 2, 12, 2, p_i46303_1_);
         this.field_178724_i.func_78793_a(5.0F, 2.0F, 0.0F);
         this.field_178721_j = new ModelRenderer(this, 0, 16);
         this.field_178721_j.func_78790_a(-1.0F, 0.0F, -1.0F, 2, 12, 2, p_i46303_1_);
         this.field_178721_j.func_78793_a(-2.0F, 12.0F, 0.0F);
         this.field_178722_k = new ModelRenderer(this, 0, 16);
         this.field_178722_k.field_78809_i = true;
         this.field_178722_k.func_78790_a(-1.0F, 0.0F, -1.0F, 2, 12, 2, p_i46303_1_);
         this.field_178722_k.func_78793_a(2.0F, 12.0F, 0.0F);
      }

   }

   public void func_78086_a(EntityLivingBase p_78086_1_, float p_78086_2_, float p_78086_3_, float p_78086_4_) {
      this.field_187076_m = ModelBiped.ArmPose.EMPTY;
      this.field_187075_l = ModelBiped.ArmPose.EMPTY;
      ItemStack itemstack = p_78086_1_.func_184586_b(EnumHand.MAIN_HAND);
      if (itemstack.func_77973_b() == Items.field_151031_f && ((AbstractSkeleton)p_78086_1_).func_184725_db()) {
         if (p_78086_1_.func_184591_cq() == EnumHandSide.RIGHT) {
            this.field_187076_m = ModelBiped.ArmPose.BOW_AND_ARROW;
         } else {
            this.field_187075_l = ModelBiped.ArmPose.BOW_AND_ARROW;
         }
      }

      super.func_78086_a(p_78086_1_, p_78086_2_, p_78086_3_, p_78086_4_);
   }

   public void func_78087_a(float p_78087_1_, float p_78087_2_, float p_78087_3_, float p_78087_4_, float p_78087_5_, float p_78087_6_, Entity p_78087_7_) {
      super.func_78087_a(p_78087_1_, p_78087_2_, p_78087_3_, p_78087_4_, p_78087_5_, p_78087_6_, p_78087_7_);
      ItemStack itemstack = ((EntityLivingBase)p_78087_7_).func_184614_ca();
      AbstractSkeleton abstractskeleton = (AbstractSkeleton)p_78087_7_;
      if (abstractskeleton.func_184725_db() && (itemstack.func_190926_b() || itemstack.func_77973_b() != Items.field_151031_f)) {
         float f = MathHelper.func_76126_a(this.field_78095_p * 3.1415927F);
         float f1 = MathHelper.func_76126_a((1.0F - (1.0F - this.field_78095_p) * (1.0F - this.field_78095_p)) * 3.1415927F);
         this.field_178723_h.field_78808_h = 0.0F;
         this.field_178724_i.field_78808_h = 0.0F;
         this.field_178723_h.field_78796_g = -(0.1F - f * 0.6F);
         this.field_178724_i.field_78796_g = 0.1F - f * 0.6F;
         this.field_178723_h.field_78795_f = -1.5707964F;
         this.field_178724_i.field_78795_f = -1.5707964F;
         this.field_178723_h.field_78795_f -= f * 1.2F - f1 * 0.4F;
         this.field_178724_i.field_78795_f -= f * 1.2F - f1 * 0.4F;
         this.field_178723_h.field_78808_h += MathHelper.func_76134_b(p_78087_3_ * 0.09F) * 0.05F + 0.05F;
         this.field_178724_i.field_78808_h -= MathHelper.func_76134_b(p_78087_3_ * 0.09F) * 0.05F + 0.05F;
         this.field_178723_h.field_78795_f += MathHelper.func_76126_a(p_78087_3_ * 0.067F) * 0.05F;
         this.field_178724_i.field_78795_f -= MathHelper.func_76126_a(p_78087_3_ * 0.067F) * 0.05F;
      }

   }

   public void func_187073_a(float p_187073_1_, EnumHandSide p_187073_2_) {
      float f = p_187073_2_ == EnumHandSide.RIGHT ? 1.0F : -1.0F;
      ModelRenderer modelrenderer = this.func_187074_a(p_187073_2_);
      modelrenderer.field_78800_c += f;
      modelrenderer.func_78794_c(p_187073_1_);
      modelrenderer.field_78800_c -= f;
   }
}
