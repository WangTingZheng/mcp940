package net.minecraft.client.model;

import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.util.math.MathHelper;

public class ModelZombieVillager extends ModelBiped {
   public ModelZombieVillager() {
      this(0.0F, 0.0F, false);
   }

   public ModelZombieVillager(float p_i1165_1_, float p_i1165_2_, boolean p_i1165_3_) {
      super(p_i1165_1_, 0.0F, 64, p_i1165_3_ ? 32 : 64);
      if (p_i1165_3_) {
         this.field_78116_c = new ModelRenderer(this, 0, 0);
         this.field_78116_c.func_78790_a(-4.0F, -10.0F, -4.0F, 8, 8, 8, p_i1165_1_);
         this.field_78116_c.func_78793_a(0.0F, 0.0F + p_i1165_2_, 0.0F);
         this.field_78115_e = new ModelRenderer(this, 16, 16);
         this.field_78115_e.func_78793_a(0.0F, 0.0F + p_i1165_2_, 0.0F);
         this.field_78115_e.func_78790_a(-4.0F, 0.0F, -2.0F, 8, 12, 4, p_i1165_1_ + 0.1F);
         this.field_178721_j = new ModelRenderer(this, 0, 16);
         this.field_178721_j.func_78793_a(-2.0F, 12.0F + p_i1165_2_, 0.0F);
         this.field_178721_j.func_78790_a(-2.0F, 0.0F, -2.0F, 4, 12, 4, p_i1165_1_ + 0.1F);
         this.field_178722_k = new ModelRenderer(this, 0, 16);
         this.field_178722_k.field_78809_i = true;
         this.field_178722_k.func_78793_a(2.0F, 12.0F + p_i1165_2_, 0.0F);
         this.field_178722_k.func_78790_a(-2.0F, 0.0F, -2.0F, 4, 12, 4, p_i1165_1_ + 0.1F);
      } else {
         this.field_78116_c = new ModelRenderer(this, 0, 0);
         this.field_78116_c.func_78793_a(0.0F, p_i1165_2_, 0.0F);
         this.field_78116_c.func_78784_a(0, 0).func_78790_a(-4.0F, -10.0F, -4.0F, 8, 10, 8, p_i1165_1_);
         this.field_78116_c.func_78784_a(24, 0).func_78790_a(-1.0F, -3.0F, -6.0F, 2, 4, 2, p_i1165_1_);
         this.field_78115_e = new ModelRenderer(this, 16, 20);
         this.field_78115_e.func_78793_a(0.0F, 0.0F + p_i1165_2_, 0.0F);
         this.field_78115_e.func_78790_a(-4.0F, 0.0F, -3.0F, 8, 12, 6, p_i1165_1_);
         this.field_78115_e.func_78784_a(0, 38).func_78790_a(-4.0F, 0.0F, -3.0F, 8, 18, 6, p_i1165_1_ + 0.05F);
         this.field_178723_h = new ModelRenderer(this, 44, 38);
         this.field_178723_h.func_78790_a(-3.0F, -2.0F, -2.0F, 4, 12, 4, p_i1165_1_);
         this.field_178723_h.func_78793_a(-5.0F, 2.0F + p_i1165_2_, 0.0F);
         this.field_178724_i = new ModelRenderer(this, 44, 38);
         this.field_178724_i.field_78809_i = true;
         this.field_178724_i.func_78790_a(-1.0F, -2.0F, -2.0F, 4, 12, 4, p_i1165_1_);
         this.field_178724_i.func_78793_a(5.0F, 2.0F + p_i1165_2_, 0.0F);
         this.field_178721_j = new ModelRenderer(this, 0, 22);
         this.field_178721_j.func_78793_a(-2.0F, 12.0F + p_i1165_2_, 0.0F);
         this.field_178721_j.func_78790_a(-2.0F, 0.0F, -2.0F, 4, 12, 4, p_i1165_1_);
         this.field_178722_k = new ModelRenderer(this, 0, 22);
         this.field_178722_k.field_78809_i = true;
         this.field_178722_k.func_78793_a(2.0F, 12.0F + p_i1165_2_, 0.0F);
         this.field_178722_k.func_78790_a(-2.0F, 0.0F, -2.0F, 4, 12, 4, p_i1165_1_);
      }

   }

   public void func_78087_a(float p_78087_1_, float p_78087_2_, float p_78087_3_, float p_78087_4_, float p_78087_5_, float p_78087_6_, Entity p_78087_7_) {
      super.func_78087_a(p_78087_1_, p_78087_2_, p_78087_3_, p_78087_4_, p_78087_5_, p_78087_6_, p_78087_7_);
      EntityZombie entityzombie = (EntityZombie)p_78087_7_;
      float f = MathHelper.func_76126_a(this.field_78095_p * 3.1415927F);
      float f1 = MathHelper.func_76126_a((1.0F - (1.0F - this.field_78095_p) * (1.0F - this.field_78095_p)) * 3.1415927F);
      this.field_178723_h.field_78808_h = 0.0F;
      this.field_178724_i.field_78808_h = 0.0F;
      this.field_178723_h.field_78796_g = -(0.1F - f * 0.6F);
      this.field_178724_i.field_78796_g = 0.1F - f * 0.6F;
      float f2 = -3.1415927F / (entityzombie.func_184734_db() ? 1.5F : 2.25F);
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
