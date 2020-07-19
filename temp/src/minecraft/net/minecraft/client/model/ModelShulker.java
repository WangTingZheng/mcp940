package net.minecraft.client.model;

import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityShulker;
import net.minecraft.util.math.MathHelper;

public class ModelShulker extends ModelBase {
   public final ModelRenderer field_187067_b;
   public final ModelRenderer field_187068_c;
   public ModelRenderer field_187066_a;

   public ModelShulker() {
      this.field_78089_u = 64;
      this.field_78090_t = 64;
      this.field_187068_c = new ModelRenderer(this);
      this.field_187067_b = new ModelRenderer(this);
      this.field_187066_a = new ModelRenderer(this);
      this.field_187068_c.func_78784_a(0, 0).func_78789_a(-8.0F, -16.0F, -8.0F, 16, 12, 16);
      this.field_187068_c.func_78793_a(0.0F, 24.0F, 0.0F);
      this.field_187067_b.func_78784_a(0, 28).func_78789_a(-8.0F, -8.0F, -8.0F, 16, 8, 16);
      this.field_187067_b.func_78793_a(0.0F, 24.0F, 0.0F);
      this.field_187066_a.func_78784_a(0, 52).func_78789_a(-3.0F, 0.0F, -3.0F, 6, 6, 6);
      this.field_187066_a.func_78793_a(0.0F, 12.0F, 0.0F);
   }

   public void func_78087_a(float p_78087_1_, float p_78087_2_, float p_78087_3_, float p_78087_4_, float p_78087_5_, float p_78087_6_, Entity p_78087_7_) {
      EntityShulker entityshulker = (EntityShulker)p_78087_7_;
      float f = p_78087_3_ - (float)entityshulker.field_70173_aa;
      float f1 = (0.5F + entityshulker.func_184688_a(f)) * 3.1415927F;
      float f2 = -1.0F + MathHelper.func_76126_a(f1);
      float f3 = 0.0F;
      if (f1 > 3.1415927F) {
         f3 = MathHelper.func_76126_a(p_78087_3_ * 0.1F) * 0.7F;
      }

      this.field_187068_c.func_78793_a(0.0F, 16.0F + MathHelper.func_76126_a(f1) * 8.0F + f3, 0.0F);
      if (entityshulker.func_184688_a(f) > 0.3F) {
         this.field_187068_c.field_78796_g = f2 * f2 * f2 * f2 * 3.1415927F * 0.125F;
      } else {
         this.field_187068_c.field_78796_g = 0.0F;
      }

      this.field_187066_a.field_78795_f = p_78087_5_ * 0.017453292F;
      this.field_187066_a.field_78796_g = p_78087_4_ * 0.017453292F;
   }

   public void func_78088_a(Entity p_78088_1_, float p_78088_2_, float p_78088_3_, float p_78088_4_, float p_78088_5_, float p_78088_6_, float p_78088_7_) {
      this.field_187067_b.func_78785_a(p_78088_7_);
      this.field_187068_c.func_78785_a(p_78088_7_);
   }
}
