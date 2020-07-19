package net.minecraft.client.model;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.util.EnumHandSide;

public class ModelArmorStand extends ModelArmorStandArmor {
   public ModelRenderer field_178740_a;
   public ModelRenderer field_178738_b;
   public ModelRenderer field_178739_c;
   public ModelRenderer field_178737_d;

   public ModelArmorStand() {
      this(0.0F);
   }

   public ModelArmorStand(float p_i46306_1_) {
      super(p_i46306_1_, 64, 64);
      this.field_78116_c = new ModelRenderer(this, 0, 0);
      this.field_78116_c.func_78790_a(-1.0F, -7.0F, -1.0F, 2, 7, 2, p_i46306_1_);
      this.field_78116_c.func_78793_a(0.0F, 0.0F, 0.0F);
      this.field_78115_e = new ModelRenderer(this, 0, 26);
      this.field_78115_e.func_78790_a(-6.0F, 0.0F, -1.5F, 12, 3, 3, p_i46306_1_);
      this.field_78115_e.func_78793_a(0.0F, 0.0F, 0.0F);
      this.field_178723_h = new ModelRenderer(this, 24, 0);
      this.field_178723_h.func_78790_a(-2.0F, -2.0F, -1.0F, 2, 12, 2, p_i46306_1_);
      this.field_178723_h.func_78793_a(-5.0F, 2.0F, 0.0F);
      this.field_178724_i = new ModelRenderer(this, 32, 16);
      this.field_178724_i.field_78809_i = true;
      this.field_178724_i.func_78790_a(0.0F, -2.0F, -1.0F, 2, 12, 2, p_i46306_1_);
      this.field_178724_i.func_78793_a(5.0F, 2.0F, 0.0F);
      this.field_178721_j = new ModelRenderer(this, 8, 0);
      this.field_178721_j.func_78790_a(-1.0F, 0.0F, -1.0F, 2, 11, 2, p_i46306_1_);
      this.field_178721_j.func_78793_a(-1.9F, 12.0F, 0.0F);
      this.field_178722_k = new ModelRenderer(this, 40, 16);
      this.field_178722_k.field_78809_i = true;
      this.field_178722_k.func_78790_a(-1.0F, 0.0F, -1.0F, 2, 11, 2, p_i46306_1_);
      this.field_178722_k.func_78793_a(1.9F, 12.0F, 0.0F);
      this.field_178740_a = new ModelRenderer(this, 16, 0);
      this.field_178740_a.func_78790_a(-3.0F, 3.0F, -1.0F, 2, 7, 2, p_i46306_1_);
      this.field_178740_a.func_78793_a(0.0F, 0.0F, 0.0F);
      this.field_178740_a.field_78806_j = true;
      this.field_178738_b = new ModelRenderer(this, 48, 16);
      this.field_178738_b.func_78790_a(1.0F, 3.0F, -1.0F, 2, 7, 2, p_i46306_1_);
      this.field_178738_b.func_78793_a(0.0F, 0.0F, 0.0F);
      this.field_178739_c = new ModelRenderer(this, 0, 48);
      this.field_178739_c.func_78790_a(-4.0F, 10.0F, -1.0F, 8, 2, 2, p_i46306_1_);
      this.field_178739_c.func_78793_a(0.0F, 0.0F, 0.0F);
      this.field_178737_d = new ModelRenderer(this, 0, 32);
      this.field_178737_d.func_78790_a(-6.0F, 11.0F, -6.0F, 12, 1, 12, p_i46306_1_);
      this.field_178737_d.func_78793_a(0.0F, 12.0F, 0.0F);
      this.field_178720_f.field_78806_j = false;
   }

   public void func_78087_a(float p_78087_1_, float p_78087_2_, float p_78087_3_, float p_78087_4_, float p_78087_5_, float p_78087_6_, Entity p_78087_7_) {
      super.func_78087_a(p_78087_1_, p_78087_2_, p_78087_3_, p_78087_4_, p_78087_5_, p_78087_6_, p_78087_7_);
      if (p_78087_7_ instanceof EntityArmorStand) {
         EntityArmorStand entityarmorstand = (EntityArmorStand)p_78087_7_;
         this.field_178724_i.field_78806_j = entityarmorstand.func_175402_q();
         this.field_178723_h.field_78806_j = entityarmorstand.func_175402_q();
         this.field_178737_d.field_78806_j = !entityarmorstand.func_175414_r();
         this.field_178722_k.func_78793_a(1.9F, 12.0F, 0.0F);
         this.field_178721_j.func_78793_a(-1.9F, 12.0F, 0.0F);
         this.field_178740_a.field_78795_f = 0.017453292F * entityarmorstand.func_175408_t().func_179415_b();
         this.field_178740_a.field_78796_g = 0.017453292F * entityarmorstand.func_175408_t().func_179416_c();
         this.field_178740_a.field_78808_h = 0.017453292F * entityarmorstand.func_175408_t().func_179413_d();
         this.field_178738_b.field_78795_f = 0.017453292F * entityarmorstand.func_175408_t().func_179415_b();
         this.field_178738_b.field_78796_g = 0.017453292F * entityarmorstand.func_175408_t().func_179416_c();
         this.field_178738_b.field_78808_h = 0.017453292F * entityarmorstand.func_175408_t().func_179413_d();
         this.field_178739_c.field_78795_f = 0.017453292F * entityarmorstand.func_175408_t().func_179415_b();
         this.field_178739_c.field_78796_g = 0.017453292F * entityarmorstand.func_175408_t().func_179416_c();
         this.field_178739_c.field_78808_h = 0.017453292F * entityarmorstand.func_175408_t().func_179413_d();
         this.field_178737_d.field_78795_f = 0.0F;
         this.field_178737_d.field_78796_g = 0.017453292F * -p_78087_7_.field_70177_z;
         this.field_178737_d.field_78808_h = 0.0F;
      }
   }

   public void func_78088_a(Entity p_78088_1_, float p_78088_2_, float p_78088_3_, float p_78088_4_, float p_78088_5_, float p_78088_6_, float p_78088_7_) {
      super.func_78088_a(p_78088_1_, p_78088_2_, p_78088_3_, p_78088_4_, p_78088_5_, p_78088_6_, p_78088_7_);
      GlStateManager.func_179094_E();
      if (this.field_78091_s) {
         float f = 2.0F;
         GlStateManager.func_179152_a(0.5F, 0.5F, 0.5F);
         GlStateManager.func_179109_b(0.0F, 24.0F * p_78088_7_, 0.0F);
         this.field_178740_a.func_78785_a(p_78088_7_);
         this.field_178738_b.func_78785_a(p_78088_7_);
         this.field_178739_c.func_78785_a(p_78088_7_);
         this.field_178737_d.func_78785_a(p_78088_7_);
      } else {
         if (p_78088_1_.func_70093_af()) {
            GlStateManager.func_179109_b(0.0F, 0.2F, 0.0F);
         }

         this.field_178740_a.func_78785_a(p_78088_7_);
         this.field_178738_b.func_78785_a(p_78088_7_);
         this.field_178739_c.func_78785_a(p_78088_7_);
         this.field_178737_d.func_78785_a(p_78088_7_);
      }

      GlStateManager.func_179121_F();
   }

   public void func_187073_a(float p_187073_1_, EnumHandSide p_187073_2_) {
      ModelRenderer modelrenderer = this.func_187074_a(p_187073_2_);
      boolean flag = modelrenderer.field_78806_j;
      modelrenderer.field_78806_j = true;
      super.func_187073_a(p_187073_1_, p_187073_2_);
      modelrenderer.field_78806_j = flag;
   }
}
