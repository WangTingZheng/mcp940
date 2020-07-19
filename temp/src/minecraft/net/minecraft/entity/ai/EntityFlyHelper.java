package net.minecraft.entity.ai;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.util.math.MathHelper;

public class EntityFlyHelper extends EntityMoveHelper {
   public EntityFlyHelper(EntityLiving p_i47418_1_) {
      super(p_i47418_1_);
   }

   public void func_75641_c() {
      if (this.field_188491_h == EntityMoveHelper.Action.MOVE_TO) {
         this.field_188491_h = EntityMoveHelper.Action.WAIT;
         this.field_75648_a.func_189654_d(true);
         double d0 = this.field_75646_b - this.field_75648_a.field_70165_t;
         double d1 = this.field_75647_c - this.field_75648_a.field_70163_u;
         double d2 = this.field_75644_d - this.field_75648_a.field_70161_v;
         double d3 = d0 * d0 + d1 * d1 + d2 * d2;
         if (d3 < 2.500000277905201E-7D) {
            this.field_75648_a.func_70657_f(0.0F);
            this.field_75648_a.func_191989_p(0.0F);
            return;
         }

         float f = (float)(MathHelper.func_181159_b(d2, d0) * 57.2957763671875D) - 90.0F;
         this.field_75648_a.field_70177_z = this.func_75639_a(this.field_75648_a.field_70177_z, f, 10.0F);
         float f1;
         if (this.field_75648_a.field_70122_E) {
            f1 = (float)(this.field_75645_e * this.field_75648_a.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111126_e());
         } else {
            f1 = (float)(this.field_75645_e * this.field_75648_a.func_110148_a(SharedMonsterAttributes.field_193334_e).func_111126_e());
         }

         this.field_75648_a.func_70659_e(f1);
         double d4 = (double)MathHelper.func_76133_a(d0 * d0 + d2 * d2);
         float f2 = (float)(-(MathHelper.func_181159_b(d1, d4) * 57.2957763671875D));
         this.field_75648_a.field_70125_A = this.func_75639_a(this.field_75648_a.field_70125_A, f2, 10.0F);
         this.field_75648_a.func_70657_f(d1 > 0.0D ? f1 : -f1);
      } else {
         this.field_75648_a.func_189654_d(false);
         this.field_75648_a.func_70657_f(0.0F);
         this.field_75648_a.func_191989_p(0.0F);
      }

   }
}
