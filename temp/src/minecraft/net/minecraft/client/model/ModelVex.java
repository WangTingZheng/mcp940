package net.minecraft.client.model;

import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityVex;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.math.MathHelper;

public class ModelVex extends ModelBiped {
   protected ModelRenderer field_191229_a;
   protected ModelRenderer field_191230_b;

   public ModelVex() {
      this(0.0F);
   }

   public ModelVex(float p_i47224_1_) {
      super(p_i47224_1_, 0.0F, 64, 64);
      this.field_178722_k.field_78806_j = false;
      this.field_178720_f.field_78806_j = false;
      this.field_178721_j = new ModelRenderer(this, 32, 0);
      this.field_178721_j.func_78790_a(-1.0F, -1.0F, -2.0F, 6, 10, 4, 0.0F);
      this.field_178721_j.func_78793_a(-1.9F, 12.0F, 0.0F);
      this.field_191230_b = new ModelRenderer(this, 0, 32);
      this.field_191230_b.func_78789_a(-20.0F, 0.0F, 0.0F, 20, 12, 1);
      this.field_191229_a = new ModelRenderer(this, 0, 32);
      this.field_191229_a.field_78809_i = true;
      this.field_191229_a.func_78789_a(0.0F, 0.0F, 0.0F, 20, 12, 1);
   }

   public void func_78088_a(Entity p_78088_1_, float p_78088_2_, float p_78088_3_, float p_78088_4_, float p_78088_5_, float p_78088_6_, float p_78088_7_) {
      super.func_78088_a(p_78088_1_, p_78088_2_, p_78088_3_, p_78088_4_, p_78088_5_, p_78088_6_, p_78088_7_);
      this.field_191230_b.func_78785_a(p_78088_7_);
      this.field_191229_a.func_78785_a(p_78088_7_);
   }

   public void func_78087_a(float p_78087_1_, float p_78087_2_, float p_78087_3_, float p_78087_4_, float p_78087_5_, float p_78087_6_, Entity p_78087_7_) {
      super.func_78087_a(p_78087_1_, p_78087_2_, p_78087_3_, p_78087_4_, p_78087_5_, p_78087_6_, p_78087_7_);
      EntityVex entityvex = (EntityVex)p_78087_7_;
      if (entityvex.func_190647_dj()) {
         if (entityvex.func_184591_cq() == EnumHandSide.RIGHT) {
            this.field_178723_h.field_78795_f = 3.7699115F;
         } else {
            this.field_178724_i.field_78795_f = 3.7699115F;
         }
      }

      this.field_178721_j.field_78795_f += 0.62831855F;
      this.field_191230_b.field_78798_e = 2.0F;
      this.field_191229_a.field_78798_e = 2.0F;
      this.field_191230_b.field_78797_d = 1.0F;
      this.field_191229_a.field_78797_d = 1.0F;
      this.field_191230_b.field_78796_g = 0.47123894F + MathHelper.func_76134_b(p_78087_3_ * 0.8F) * 3.1415927F * 0.05F;
      this.field_191229_a.field_78796_g = -this.field_191230_b.field_78796_g;
      this.field_191229_a.field_78808_h = -0.47123894F;
      this.field_191229_a.field_78795_f = 0.47123894F;
      this.field_191230_b.field_78795_f = 0.47123894F;
      this.field_191230_b.field_78808_h = 0.47123894F;
   }

   public int func_191228_a() {
      return 23;
   }
}
