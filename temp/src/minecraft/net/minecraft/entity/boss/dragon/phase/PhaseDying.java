package net.minecraft.entity.boss.dragon.phase;

import javax.annotation.Nullable;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.gen.feature.WorldGenEndPodium;

public class PhaseDying extends PhaseBase {
   private Vec3d field_188672_b;
   private int field_188673_c;

   public PhaseDying(EntityDragon p_i46792_1_) {
      super(p_i46792_1_);
   }

   public void func_188657_b() {
      if (this.field_188673_c++ % 10 == 0) {
         float f = (this.field_188661_a.func_70681_au().nextFloat() - 0.5F) * 8.0F;
         float f1 = (this.field_188661_a.func_70681_au().nextFloat() - 0.5F) * 4.0F;
         float f2 = (this.field_188661_a.func_70681_au().nextFloat() - 0.5F) * 8.0F;
         this.field_188661_a.field_70170_p.func_175688_a(EnumParticleTypes.EXPLOSION_HUGE, this.field_188661_a.field_70165_t + (double)f, this.field_188661_a.field_70163_u + 2.0D + (double)f1, this.field_188661_a.field_70161_v + (double)f2, 0.0D, 0.0D, 0.0D);
      }

   }

   public void func_188659_c() {
      ++this.field_188673_c;
      if (this.field_188672_b == null) {
         BlockPos blockpos = this.field_188661_a.field_70170_p.func_175645_m(WorldGenEndPodium.field_186139_a);
         this.field_188672_b = new Vec3d((double)blockpos.func_177958_n(), (double)blockpos.func_177956_o(), (double)blockpos.func_177952_p());
      }

      double d0 = this.field_188672_b.func_186679_c(this.field_188661_a.field_70165_t, this.field_188661_a.field_70163_u, this.field_188661_a.field_70161_v);
      if (d0 >= 100.0D && d0 <= 22500.0D && !this.field_188661_a.field_70123_F && !this.field_188661_a.field_70124_G) {
         this.field_188661_a.func_70606_j(1.0F);
      } else {
         this.field_188661_a.func_70606_j(0.0F);
      }

   }

   public void func_188660_d() {
      this.field_188672_b = null;
      this.field_188673_c = 0;
   }

   public float func_188651_f() {
      return 3.0F;
   }

   @Nullable
   public Vec3d func_188650_g() {
      return this.field_188672_b;
   }

   public PhaseList<PhaseDying> func_188652_i() {
      return PhaseList.field_188750_j;
   }
}
