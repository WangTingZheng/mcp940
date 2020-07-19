package net.minecraft.entity.ai;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.MathHelper;

public class EntityAILeapAtTarget extends EntityAIBase {
   EntityLiving field_75328_a;
   EntityLivingBase field_75326_b;
   float field_75327_c;

   public EntityAILeapAtTarget(EntityLiving p_i1630_1_, float p_i1630_2_) {
      this.field_75328_a = p_i1630_1_;
      this.field_75327_c = p_i1630_2_;
      this.func_75248_a(5);
   }

   public boolean func_75250_a() {
      this.field_75326_b = this.field_75328_a.func_70638_az();
      if (this.field_75326_b == null) {
         return false;
      } else {
         double d0 = this.field_75328_a.func_70068_e(this.field_75326_b);
         if (d0 >= 4.0D && d0 <= 16.0D) {
            if (!this.field_75328_a.field_70122_E) {
               return false;
            } else {
               return this.field_75328_a.func_70681_au().nextInt(5) == 0;
            }
         } else {
            return false;
         }
      }
   }

   public boolean func_75253_b() {
      return !this.field_75328_a.field_70122_E;
   }

   public void func_75249_e() {
      double d0 = this.field_75326_b.field_70165_t - this.field_75328_a.field_70165_t;
      double d1 = this.field_75326_b.field_70161_v - this.field_75328_a.field_70161_v;
      float f = MathHelper.func_76133_a(d0 * d0 + d1 * d1);
      if ((double)f >= 1.0E-4D) {
         this.field_75328_a.field_70159_w += d0 / (double)f * 0.5D * 0.800000011920929D + this.field_75328_a.field_70159_w * 0.20000000298023224D;
         this.field_75328_a.field_70179_y += d1 / (double)f * 0.5D * 0.800000011920929D + this.field_75328_a.field_70179_y * 0.20000000298023224D;
      }

      this.field_75328_a.field_70181_x = (double)this.field_75327_c;
   }
}
