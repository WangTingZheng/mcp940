package net.minecraft.client.model;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityArmorStand;

public class ModelArmorStandArmor extends ModelBiped {
   public ModelArmorStandArmor() {
      this(0.0F);
   }

   public ModelArmorStandArmor(float p_i46307_1_) {
      this(p_i46307_1_, 64, 32);
   }

   protected ModelArmorStandArmor(float p_i46308_1_, int p_i46308_2_, int p_i46308_3_) {
      super(p_i46308_1_, 0.0F, p_i46308_2_, p_i46308_3_);
   }

   public void func_78087_a(float p_78087_1_, float p_78087_2_, float p_78087_3_, float p_78087_4_, float p_78087_5_, float p_78087_6_, Entity p_78087_7_) {
      if (p_78087_7_ instanceof EntityArmorStand) {
         EntityArmorStand entityarmorstand = (EntityArmorStand)p_78087_7_;
         this.field_78116_c.field_78795_f = 0.017453292F * entityarmorstand.func_175418_s().func_179415_b();
         this.field_78116_c.field_78796_g = 0.017453292F * entityarmorstand.func_175418_s().func_179416_c();
         this.field_78116_c.field_78808_h = 0.017453292F * entityarmorstand.func_175418_s().func_179413_d();
         this.field_78116_c.func_78793_a(0.0F, 1.0F, 0.0F);
         this.field_78115_e.field_78795_f = 0.017453292F * entityarmorstand.func_175408_t().func_179415_b();
         this.field_78115_e.field_78796_g = 0.017453292F * entityarmorstand.func_175408_t().func_179416_c();
         this.field_78115_e.field_78808_h = 0.017453292F * entityarmorstand.func_175408_t().func_179413_d();
         this.field_178724_i.field_78795_f = 0.017453292F * entityarmorstand.func_175404_u().func_179415_b();
         this.field_178724_i.field_78796_g = 0.017453292F * entityarmorstand.func_175404_u().func_179416_c();
         this.field_178724_i.field_78808_h = 0.017453292F * entityarmorstand.func_175404_u().func_179413_d();
         this.field_178723_h.field_78795_f = 0.017453292F * entityarmorstand.func_175411_v().func_179415_b();
         this.field_178723_h.field_78796_g = 0.017453292F * entityarmorstand.func_175411_v().func_179416_c();
         this.field_178723_h.field_78808_h = 0.017453292F * entityarmorstand.func_175411_v().func_179413_d();
         this.field_178722_k.field_78795_f = 0.017453292F * entityarmorstand.func_175403_w().func_179415_b();
         this.field_178722_k.field_78796_g = 0.017453292F * entityarmorstand.func_175403_w().func_179416_c();
         this.field_178722_k.field_78808_h = 0.017453292F * entityarmorstand.func_175403_w().func_179413_d();
         this.field_178722_k.func_78793_a(1.9F, 11.0F, 0.0F);
         this.field_178721_j.field_78795_f = 0.017453292F * entityarmorstand.func_175407_x().func_179415_b();
         this.field_178721_j.field_78796_g = 0.017453292F * entityarmorstand.func_175407_x().func_179416_c();
         this.field_178721_j.field_78808_h = 0.017453292F * entityarmorstand.func_175407_x().func_179413_d();
         this.field_178721_j.func_78793_a(-1.9F, 11.0F, 0.0F);
         func_178685_a(this.field_78116_c, this.field_178720_f);
      }
   }
}
