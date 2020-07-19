package net.minecraft.entity.boss.dragon.phase;

import javax.annotation.Nullable;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.gen.feature.WorldGenEndPodium;

public class PhaseLanding extends PhaseBase {
   private Vec3d field_188685_b;

   public PhaseLanding(EntityDragon p_i46788_1_) {
      super(p_i46788_1_);
   }

   public void func_188657_b() {
      Vec3d vec3d = this.field_188661_a.func_184665_a(1.0F).func_72432_b();
      vec3d.func_178785_b(-0.7853982F);
      double d0 = this.field_188661_a.field_70986_h.field_70165_t;
      double d1 = this.field_188661_a.field_70986_h.field_70163_u + (double)(this.field_188661_a.field_70986_h.field_70131_O / 2.0F);
      double d2 = this.field_188661_a.field_70986_h.field_70161_v;

      for(int i = 0; i < 8; ++i) {
         double d3 = d0 + this.field_188661_a.func_70681_au().nextGaussian() / 2.0D;
         double d4 = d1 + this.field_188661_a.func_70681_au().nextGaussian() / 2.0D;
         double d5 = d2 + this.field_188661_a.func_70681_au().nextGaussian() / 2.0D;
         this.field_188661_a.field_70170_p.func_175688_a(EnumParticleTypes.DRAGON_BREATH, d3, d4, d5, -vec3d.field_72450_a * 0.07999999821186066D + this.field_188661_a.field_70159_w, -vec3d.field_72448_b * 0.30000001192092896D + this.field_188661_a.field_70181_x, -vec3d.field_72449_c * 0.07999999821186066D + this.field_188661_a.field_70179_y);
         vec3d.func_178785_b(0.19634955F);
      }

   }

   public void func_188659_c() {
      if (this.field_188685_b == null) {
         this.field_188685_b = new Vec3d(this.field_188661_a.field_70170_p.func_175672_r(WorldGenEndPodium.field_186139_a));
      }

      if (this.field_188685_b.func_186679_c(this.field_188661_a.field_70165_t, this.field_188661_a.field_70163_u, this.field_188661_a.field_70161_v) < 1.0D) {
         ((PhaseSittingFlaming)this.field_188661_a.func_184670_cT().func_188757_b(PhaseList.field_188746_f)).func_188663_j();
         this.field_188661_a.func_184670_cT().func_188758_a(PhaseList.field_188747_g);
      }

   }

   public float func_188651_f() {
      return 1.5F;
   }

   public float func_188653_h() {
      float f = MathHelper.func_76133_a(this.field_188661_a.field_70159_w * this.field_188661_a.field_70159_w + this.field_188661_a.field_70179_y * this.field_188661_a.field_70179_y) + 1.0F;
      float f1 = Math.min(f, 40.0F);
      return f1 / f;
   }

   public void func_188660_d() {
      this.field_188685_b = null;
   }

   @Nullable
   public Vec3d func_188650_g() {
      return this.field_188685_b;
   }

   public PhaseList<PhaseLanding> func_188652_i() {
      return PhaseList.field_188744_d;
   }
}
