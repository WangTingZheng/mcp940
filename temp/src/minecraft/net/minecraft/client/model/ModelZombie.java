package net.minecraft.client.model;

import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.util.math.MathHelper;

public class ModelZombie extends ModelBiped {
   public ModelZombie() {
      this(0.0F, false);
   }

   public ModelZombie(float p_i1168_1_, boolean p_i1168_2_) {
      super(p_i1168_1_, 0.0F, 64, p_i1168_2_ ? 32 : 64);
   }

   public void func_78087_a(float p_78087_1_, float p_78087_2_, float p_78087_3_, float p_78087_4_, float p_78087_5_, float p_78087_6_, Entity p_78087_7_) {
      super.func_78087_a(p_78087_1_, p_78087_2_, p_78087_3_, p_78087_4_, p_78087_5_, p_78087_6_, p_78087_7_);
      boolean flag = p_78087_7_ instanceof EntityZombie && ((EntityZombie)p_78087_7_).func_184734_db();
      float f = MathHelper.func_76126_a(this.field_78095_p * 3.1415927F);
      float f1 = MathHelper.func_76126_a((1.0F - (1.0F - this.field_78095_p) * (1.0F - this.field_78095_p)) * 3.1415927F);
      this.field_178723_h.field_78808_h = 0.0F;
      this.field_178724_i.field_78808_h = 0.0F;
      this.field_178723_h.field_78796_g = -(0.1F - f * 0.6F);
      this.field_178724_i.field_78796_g = 0.1F - f * 0.6F;
      float f2 = -3.1415927F / (flag ? 1.5F : 2.25F);
      this.field_178723_h.field_78795_f = f2;
      this.field_178724_i.field_78795_f = f2;
      this.field_178723_h.field_78795_f += f * 1.2F - f1 * 0.4F;
      this.field_178724_i.field_78795_f += f * 1.2F - f1 * 0.4F;
      this.field_178723_h.field_78808_h += MathHelper.func_76134_b(p_78087_3_ * 0.09F) * 0.05F + 0.05F;
      this.field_178724_i.field_78808_h -= MathHelper.func_76134_b(p_78087_3_ * 0.09F) * 0.05F + 0.05F;
      this.field_178723_h.field_78795_f += MathHelper.func_76126_a(p_78087_3_ * 0.067F) * 0.05F;
      this.field_178724_i.field_78795_f -= MathHelper.func_76126_a(p_78087_3_ * 0.067F) * 0.05F;
   }
}
