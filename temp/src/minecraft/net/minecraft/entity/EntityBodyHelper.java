package net.minecraft.entity;

import net.minecraft.util.math.MathHelper;

public class EntityBodyHelper {
   private final EntityLivingBase field_75668_a;
   private int field_75666_b;
   private float field_75667_c;

   public EntityBodyHelper(EntityLivingBase p_i1611_1_) {
      this.field_75668_a = p_i1611_1_;
   }

   public void func_75664_a() {
      double d0 = this.field_75668_a.field_70165_t - this.field_75668_a.field_70169_q;
      double d1 = this.field_75668_a.field_70161_v - this.field_75668_a.field_70166_s;
      if (d0 * d0 + d1 * d1 > 2.500000277905201E-7D) {
         this.field_75668_a.field_70761_aq = this.field_75668_a.field_70177_z;
         this.field_75668_a.field_70759_as = this.func_75665_a(this.field_75668_a.field_70761_aq, this.field_75668_a.field_70759_as, 75.0F);
         this.field_75667_c = this.field_75668_a.field_70759_as;
         this.field_75666_b = 0;
      } else {
         if (this.field_75668_a.func_184188_bt().isEmpty() || !(this.field_75668_a.func_184188_bt().get(0) instanceof EntityLiving)) {
            float f = 75.0F;
            if (Math.abs(this.field_75668_a.field_70759_as - this.field_75667_c) > 15.0F) {
               this.field_75666_b = 0;
               this.field_75667_c = this.field_75668_a.field_70759_as;
            } else {
               ++this.field_75666_b;
               int i = 10;
               if (this.field_75666_b > 10) {
                  f = Math.max(1.0F - (float)(this.field_75666_b - 10) / 10.0F, 0.0F) * 75.0F;
               }
            }

            this.field_75668_a.field_70761_aq = this.func_75665_a(this.field_75668_a.field_70759_as, this.field_75668_a.field_70761_aq, f);
         }

      }
   }

   private float func_75665_a(float p_75665_1_, float p_75665_2_, float p_75665_3_) {
      float f = MathHelper.func_76142_g(p_75665_1_ - p_75665_2_);
      if (f < -p_75665_3_) {
         f = -p_75665_3_;
      }

      if (f >= p_75665_3_) {
         f = p_75665_3_;
      }

      return p_75665_1_ - f;
   }
}
