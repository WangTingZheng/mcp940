package net.minecraft.entity.boss.dragon.phase;

import javax.annotation.Nullable;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.util.math.Vec3d;

public class PhaseHover extends PhaseBase {
   private Vec3d field_188680_b;

   public PhaseHover(EntityDragon p_i46790_1_) {
      super(p_i46790_1_);
   }

   public void func_188659_c() {
      if (this.field_188680_b == null) {
         this.field_188680_b = new Vec3d(this.field_188661_a.field_70165_t, this.field_188661_a.field_70163_u, this.field_188661_a.field_70161_v);
      }

   }

   public boolean func_188654_a() {
      return true;
   }

   public void func_188660_d() {
      this.field_188680_b = null;
   }

   public float func_188651_f() {
      return 1.0F;
   }

   @Nullable
   public Vec3d func_188650_g() {
      return this.field_188680_b;
   }

   public PhaseList<PhaseHover> func_188652_i() {
      return PhaseList.field_188751_k;
   }
}
