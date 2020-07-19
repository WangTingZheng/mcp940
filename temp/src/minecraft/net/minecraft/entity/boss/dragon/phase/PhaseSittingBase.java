package net.minecraft.entity.boss.dragon.phase;

import net.minecraft.entity.MultiPartEntityPart;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.util.DamageSource;

public abstract class PhaseSittingBase extends PhaseBase {
   public PhaseSittingBase(EntityDragon p_i46794_1_) {
      super(p_i46794_1_);
   }

   public boolean func_188654_a() {
      return true;
   }

   public float func_188656_a(MultiPartEntityPart p_188656_1_, DamageSource p_188656_2_, float p_188656_3_) {
      if (p_188656_2_.func_76364_f() instanceof EntityArrow) {
         p_188656_2_.func_76364_f().func_70015_d(1);
         return 0.0F;
      } else {
         return super.func_188656_a(p_188656_1_, p_188656_2_, p_188656_3_);
      }
   }
}
