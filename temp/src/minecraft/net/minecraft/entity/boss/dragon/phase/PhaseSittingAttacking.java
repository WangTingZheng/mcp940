package net.minecraft.entity.boss.dragon.phase;

import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.init.SoundEvents;

public class PhaseSittingAttacking extends PhaseSittingBase {
   private int field_188662_b;

   public PhaseSittingAttacking(EntityDragon p_i46787_1_) {
      super(p_i46787_1_);
   }

   public void func_188657_b() {
      this.field_188661_a.field_70170_p.func_184134_a(this.field_188661_a.field_70165_t, this.field_188661_a.field_70163_u, this.field_188661_a.field_70161_v, SoundEvents.field_187525_aO, this.field_188661_a.func_184176_by(), 2.5F, 0.8F + this.field_188661_a.func_70681_au().nextFloat() * 0.3F, false);
   }

   public void func_188659_c() {
      if (this.field_188662_b++ >= 40) {
         this.field_188661_a.func_184670_cT().func_188758_a(PhaseList.field_188746_f);
      }

   }

   public void func_188660_d() {
      this.field_188662_b = 0;
   }

   public PhaseList<PhaseSittingAttacking> func_188652_i() {
      return PhaseList.field_188748_h;
   }
}
