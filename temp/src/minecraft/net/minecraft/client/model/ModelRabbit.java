package net.minecraft.client.model;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityRabbit;
import net.minecraft.util.math.MathHelper;

public class ModelRabbit extends ModelBase {
   private final ModelRenderer field_178698_a;
   private final ModelRenderer field_178696_b;
   private final ModelRenderer field_178697_c;
   private final ModelRenderer field_178694_d;
   private final ModelRenderer field_178695_e;
   private final ModelRenderer field_178692_f;
   private final ModelRenderer field_178693_g;
   private final ModelRenderer field_178704_h;
   private final ModelRenderer field_178705_i;
   private final ModelRenderer field_178702_j;
   private final ModelRenderer field_178703_k;
   private final ModelRenderer field_178700_l;
   private float field_178701_m;

   public ModelRabbit() {
      this.func_78085_a("head.main", 0, 0);
      this.func_78085_a("head.nose", 0, 24);
      this.func_78085_a("head.ear1", 0, 10);
      this.func_78085_a("head.ear2", 6, 10);
      this.field_178698_a = new ModelRenderer(this, 26, 24);
      this.field_178698_a.func_78789_a(-1.0F, 5.5F, -3.7F, 2, 1, 7);
      this.field_178698_a.func_78793_a(3.0F, 17.5F, 3.7F);
      this.field_178698_a.field_78809_i = true;
      this.func_178691_a(this.field_178698_a, 0.0F, 0.0F, 0.0F);
      this.field_178696_b = new ModelRenderer(this, 8, 24);
      this.field_178696_b.func_78789_a(-1.0F, 5.5F, -3.7F, 2, 1, 7);
      this.field_178696_b.func_78793_a(-3.0F, 17.5F, 3.7F);
      this.field_178696_b.field_78809_i = true;
      this.func_178691_a(this.field_178696_b, 0.0F, 0.0F, 0.0F);
      this.field_178697_c = new ModelRenderer(this, 30, 15);
      this.field_178697_c.func_78789_a(-1.0F, 0.0F, 0.0F, 2, 4, 5);
      this.field_178697_c.func_78793_a(3.0F, 17.5F, 3.7F);
      this.field_178697_c.field_78809_i = true;
      this.func_178691_a(this.field_178697_c, -0.34906584F, 0.0F, 0.0F);
      this.field_178694_d = new ModelRenderer(this, 16, 15);
      this.field_178694_d.func_78789_a(-1.0F, 0.0F, 0.0F, 2, 4, 5);
      this.field_178694_d.func_78793_a(-3.0F, 17.5F, 3.7F);
      this.field_178694_d.field_78809_i = true;
      this.func_178691_a(this.field_178694_d, -0.34906584F, 0.0F, 0.0F);
      this.field_178695_e = new ModelRenderer(this, 0, 0);
      this.field_178695_e.func_78789_a(-3.0F, -2.0F, -10.0F, 6, 5, 10);
      this.field_178695_e.func_78793_a(0.0F, 19.0F, 8.0F);
      this.field_178695_e.field_78809_i = true;
      this.func_178691_a(this.field_178695_e, -0.34906584F, 0.0F, 0.0F);
      this.field_178692_f = new ModelRenderer(this, 8, 15);
      this.field_178692_f.func_78789_a(-1.0F, 0.0F, -1.0F, 2, 7, 2);
      this.field_178692_f.func_78793_a(3.0F, 17.0F, -1.0F);
      this.field_178692_f.field_78809_i = true;
      this.func_178691_a(this.field_178692_f, -0.17453292F, 0.0F, 0.0F);
      this.field_178693_g = new ModelRenderer(this, 0, 15);
      this.field_178693_g.func_78789_a(-1.0F, 0.0F, -1.0F, 2, 7, 2);
      this.field_178693_g.func_78793_a(-3.0F, 17.0F, -1.0F);
      this.field_178693_g.field_78809_i = true;
      this.func_178691_a(this.field_178693_g, -0.17453292F, 0.0F, 0.0F);
      this.field_178704_h = new ModelRenderer(this, 32, 0);
      this.field_178704_h.func_78789_a(-2.5F, -4.0F, -5.0F, 5, 4, 5);
      this.field_178704_h.func_78793_a(0.0F, 16.0F, -1.0F);
      this.field_178704_h.field_78809_i = true;
      this.func_178691_a(this.field_178704_h, 0.0F, 0.0F, 0.0F);
      this.field_178705_i = new ModelRenderer(this, 52, 0);
      this.field_178705_i.func_78789_a(-2.5F, -9.0F, -1.0F, 2, 5, 1);
      this.field_178705_i.func_78793_a(0.0F, 16.0F, -1.0F);
      this.field_178705_i.field_78809_i = true;
      this.func_178691_a(this.field_178705_i, 0.0F, -0.2617994F, 0.0F);
      this.field_178702_j = new ModelRenderer(this, 58, 0);
      this.field_178702_j.func_78789_a(0.5F, -9.0F, -1.0F, 2, 5, 1);
      this.field_178702_j.func_78793_a(0.0F, 16.0F, -1.0F);
      this.field_178702_j.field_78809_i = true;
      this.func_178691_a(this.field_178702_j, 0.0F, 0.2617994F, 0.0F);
      this.field_178703_k = new ModelRenderer(this, 52, 6);
      this.field_178703_k.func_78789_a(-1.5F, -1.5F, 0.0F, 3, 3, 2);
      this.field_178703_k.func_78793_a(0.0F, 20.0F, 7.0F);
      this.field_178703_k.field_78809_i = true;
      this.func_178691_a(this.field_178703_k, -0.3490659F, 0.0F, 0.0F);
      this.field_178700_l = new ModelRenderer(this, 32, 9);
      this.field_178700_l.func_78789_a(-0.5F, -2.5F, -5.5F, 1, 1, 1);
      this.field_178700_l.func_78793_a(0.0F, 16.0F, -1.0F);
      this.field_178700_l.field_78809_i = true;
      this.func_178691_a(this.field_178700_l, 0.0F, 0.0F, 0.0F);
   }

   private void func_178691_a(ModelRenderer p_178691_1_, float p_178691_2_, float p_178691_3_, float p_178691_4_) {
      p_178691_1_.field_78795_f = p_178691_2_;
      p_178691_1_.field_78796_g = p_178691_3_;
      p_178691_1_.field_78808_h = p_178691_4_;
   }

   public void func_78088_a(Entity p_78088_1_, float p_78088_2_, float p_78088_3_, float p_78088_4_, float p_78088_5_, float p_78088_6_, float p_78088_7_) {
      this.func_78087_a(p_78088_2_, p_78088_3_, p_78088_4_, p_78088_5_, p_78088_6_, p_78088_7_, p_78088_1_);
      if (this.field_78091_s) {
         float f = 1.5F;
         GlStateManager.func_179094_E();
         GlStateManager.func_179152_a(0.56666666F, 0.56666666F, 0.56666666F);
         GlStateManager.func_179109_b(0.0F, 22.0F * p_78088_7_, 2.0F * p_78088_7_);
         this.field_178704_h.func_78785_a(p_78088_7_);
         this.field_178702_j.func_78785_a(p_78088_7_);
         this.field_178705_i.func_78785_a(p_78088_7_);
         this.field_178700_l.func_78785_a(p_78088_7_);
         GlStateManager.func_179121_F();
         GlStateManager.func_179094_E();
         GlStateManager.func_179152_a(0.4F, 0.4F, 0.4F);
         GlStateManager.func_179109_b(0.0F, 36.0F * p_78088_7_, 0.0F);
         this.field_178698_a.func_78785_a(p_78088_7_);
         this.field_178696_b.func_78785_a(p_78088_7_);
         this.field_178697_c.func_78785_a(p_78088_7_);
         this.field_178694_d.func_78785_a(p_78088_7_);
         this.field_178695_e.func_78785_a(p_78088_7_);
         this.field_178692_f.func_78785_a(p_78088_7_);
         this.field_178693_g.func_78785_a(p_78088_7_);
         this.field_178703_k.func_78785_a(p_78088_7_);
         GlStateManager.func_179121_F();
      } else {
         GlStateManager.func_179094_E();
         GlStateManager.func_179152_a(0.6F, 0.6F, 0.6F);
         GlStateManager.func_179109_b(0.0F, 16.0F * p_78088_7_, 0.0F);
         this.field_178698_a.func_78785_a(p_78088_7_);
         this.field_178696_b.func_78785_a(p_78088_7_);
         this.field_178697_c.func_78785_a(p_78088_7_);
         this.field_178694_d.func_78785_a(p_78088_7_);
         this.field_178695_e.func_78785_a(p_78088_7_);
         this.field_178692_f.func_78785_a(p_78088_7_);
         this.field_178693_g.func_78785_a(p_78088_7_);
         this.field_178704_h.func_78785_a(p_78088_7_);
         this.field_178705_i.func_78785_a(p_78088_7_);
         this.field_178702_j.func_78785_a(p_78088_7_);
         this.field_178703_k.func_78785_a(p_78088_7_);
         this.field_178700_l.func_78785_a(p_78088_7_);
         GlStateManager.func_179121_F();
      }

   }

   public void func_78087_a(float p_78087_1_, float p_78087_2_, float p_78087_3_, float p_78087_4_, float p_78087_5_, float p_78087_6_, Entity p_78087_7_) {
      float f = p_78087_3_ - (float)p_78087_7_.field_70173_aa;
      EntityRabbit entityrabbit = (EntityRabbit)p_78087_7_;
      this.field_178700_l.field_78795_f = p_78087_5_ * 0.017453292F;
      this.field_178704_h.field_78795_f = p_78087_5_ * 0.017453292F;
      this.field_178705_i.field_78795_f = p_78087_5_ * 0.017453292F;
      this.field_178702_j.field_78795_f = p_78087_5_ * 0.017453292F;
      this.field_178700_l.field_78796_g = p_78087_4_ * 0.017453292F;
      this.field_178704_h.field_78796_g = p_78087_4_ * 0.017453292F;
      this.field_178705_i.field_78796_g = this.field_178700_l.field_78796_g - 0.2617994F;
      this.field_178702_j.field_78796_g = this.field_178700_l.field_78796_g + 0.2617994F;
      this.field_178701_m = MathHelper.func_76126_a(entityrabbit.func_175521_o(f) * 3.1415927F);
      this.field_178697_c.field_78795_f = (this.field_178701_m * 50.0F - 21.0F) * 0.017453292F;
      this.field_178694_d.field_78795_f = (this.field_178701_m * 50.0F - 21.0F) * 0.017453292F;
      this.field_178698_a.field_78795_f = this.field_178701_m * 50.0F * 0.017453292F;
      this.field_178696_b.field_78795_f = this.field_178701_m * 50.0F * 0.017453292F;
      this.field_178692_f.field_78795_f = (this.field_178701_m * -40.0F - 11.0F) * 0.017453292F;
      this.field_178693_g.field_78795_f = (this.field_178701_m * -40.0F - 11.0F) * 0.017453292F;
   }

   public void func_78086_a(EntityLivingBase p_78086_1_, float p_78086_2_, float p_78086_3_, float p_78086_4_) {
      super.func_78086_a(p_78086_1_, p_78086_2_, p_78086_3_, p_78086_4_);
      this.field_178701_m = MathHelper.func_76126_a(((EntityRabbit)p_78086_1_).func_175521_o(p_78086_4_) * 3.1415927F);
   }
}
