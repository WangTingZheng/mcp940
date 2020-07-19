package net.minecraft.entity.boss.dragon.phase;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class PhaseSittingScanning extends PhaseSittingBase {
   private int field_188667_b;

   public PhaseSittingScanning(EntityDragon p_i46785_1_) {
      super(p_i46785_1_);
   }

   public void func_188659_c() {
      ++this.field_188667_b;
      EntityLivingBase entitylivingbase = this.field_188661_a.field_70170_p.func_184142_a(this.field_188661_a, 20.0D, 10.0D);
      if (entitylivingbase != null) {
         if (this.field_188667_b > 25) {
            this.field_188661_a.func_184670_cT().func_188758_a(PhaseList.field_188748_h);
         } else {
            Vec3d vec3d = (new Vec3d(entitylivingbase.field_70165_t - this.field_188661_a.field_70165_t, 0.0D, entitylivingbase.field_70161_v - this.field_188661_a.field_70161_v)).func_72432_b();
            Vec3d vec3d1 = (new Vec3d((double)MathHelper.func_76126_a(this.field_188661_a.field_70177_z * 0.017453292F), 0.0D, (double)(-MathHelper.func_76134_b(this.field_188661_a.field_70177_z * 0.017453292F)))).func_72432_b();
            float f = (float)vec3d1.func_72430_b(vec3d);
            float f1 = (float)(Math.acos((double)f) * 57.2957763671875D) + 0.5F;
            if (f1 < 0.0F || f1 > 10.0F) {
               double d0 = entitylivingbase.field_70165_t - this.field_188661_a.field_70986_h.field_70165_t;
               double d1 = entitylivingbase.field_70161_v - this.field_188661_a.field_70986_h.field_70161_v;
               double d2 = MathHelper.func_151237_a(MathHelper.func_76138_g(180.0D - MathHelper.func_181159_b(d0, d1) * 57.2957763671875D - (double)this.field_188661_a.field_70177_z), -100.0D, 100.0D);
               this.field_188661_a.field_70704_bt *= 0.8F;
               float f2 = MathHelper.func_76133_a(d0 * d0 + d1 * d1) + 1.0F;
               float f3 = f2;
               if (f2 > 40.0F) {
                  f2 = 40.0F;
               }

               this.field_188661_a.field_70704_bt = (float)((double)this.field_188661_a.field_70704_bt + d2 * (double)(0.7F / f2 / f3));
               this.field_188661_a.field_70177_z += this.field_188661_a.field_70704_bt;
            }
         }
      } else if (this.field_188667_b >= 100) {
         entitylivingbase = this.field_188661_a.field_70170_p.func_184142_a(this.field_188661_a, 150.0D, 150.0D);
         this.field_188661_a.func_184670_cT().func_188758_a(PhaseList.field_188745_e);
         if (entitylivingbase != null) {
            this.field_188661_a.func_184670_cT().func_188758_a(PhaseList.field_188749_i);
            ((PhaseChargingPlayer)this.field_188661_a.func_184670_cT().func_188757_b(PhaseList.field_188749_i)).func_188668_a(new Vec3d(entitylivingbase.field_70165_t, entitylivingbase.field_70163_u, entitylivingbase.field_70161_v));
         }
      }

   }

   public void func_188660_d() {
      this.field_188667_b = 0;
   }

   public PhaseList<PhaseSittingScanning> func_188652_i() {
      return PhaseList.field_188747_g;
   }
}
