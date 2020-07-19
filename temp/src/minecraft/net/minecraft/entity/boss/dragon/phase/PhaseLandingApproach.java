package net.minecraft.entity.boss.dragon.phase;

import javax.annotation.Nullable;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.gen.feature.WorldGenEndPodium;

public class PhaseLandingApproach extends PhaseBase {
   private Path field_188683_b;
   private Vec3d field_188684_c;

   public PhaseLandingApproach(EntityDragon p_i46789_1_) {
      super(p_i46789_1_);
   }

   public PhaseList<PhaseLandingApproach> func_188652_i() {
      return PhaseList.field_188743_c;
   }

   public void func_188660_d() {
      this.field_188683_b = null;
      this.field_188684_c = null;
   }

   public void func_188659_c() {
      double d0 = this.field_188684_c == null ? 0.0D : this.field_188684_c.func_186679_c(this.field_188661_a.field_70165_t, this.field_188661_a.field_70163_u, this.field_188661_a.field_70161_v);
      if (d0 < 100.0D || d0 > 22500.0D || this.field_188661_a.field_70123_F || this.field_188661_a.field_70124_G) {
         this.func_188681_j();
      }

   }

   @Nullable
   public Vec3d func_188650_g() {
      return this.field_188684_c;
   }

   private void func_188681_j() {
      if (this.field_188683_b == null || this.field_188683_b.func_75879_b()) {
         int i = this.field_188661_a.func_184671_o();
         BlockPos blockpos = this.field_188661_a.field_70170_p.func_175672_r(WorldGenEndPodium.field_186139_a);
         EntityPlayer entityplayer = this.field_188661_a.field_70170_p.func_184139_a(blockpos, 128.0D, 128.0D);
         int j;
         if (entityplayer != null) {
            Vec3d vec3d = (new Vec3d(entityplayer.field_70165_t, 0.0D, entityplayer.field_70161_v)).func_72432_b();
            j = this.field_188661_a.func_184663_l(-vec3d.field_72450_a * 40.0D, 105.0D, -vec3d.field_72449_c * 40.0D);
         } else {
            j = this.field_188661_a.func_184663_l(40.0D, (double)blockpos.func_177956_o(), 0.0D);
         }

         PathPoint pathpoint = new PathPoint(blockpos.func_177958_n(), blockpos.func_177956_o(), blockpos.func_177952_p());
         this.field_188683_b = this.field_188661_a.func_184666_a(i, j, pathpoint);
         if (this.field_188683_b != null) {
            this.field_188683_b.func_75875_a();
         }
      }

      this.func_188682_k();
      if (this.field_188683_b != null && this.field_188683_b.func_75879_b()) {
         this.field_188661_a.func_184670_cT().func_188758_a(PhaseList.field_188744_d);
      }

   }

   private void func_188682_k() {
      if (this.field_188683_b != null && !this.field_188683_b.func_75879_b()) {
         Vec3d vec3d = this.field_188683_b.func_186310_f();
         this.field_188683_b.func_75875_a();
         double d0 = vec3d.field_72450_a;
         double d1 = vec3d.field_72449_c;

         double d2;
         while(true) {
            d2 = vec3d.field_72448_b + (double)(this.field_188661_a.func_70681_au().nextFloat() * 20.0F);
            if (d2 >= vec3d.field_72448_b) {
               break;
            }
         }

         this.field_188684_c = new Vec3d(d0, d2, d1);
      }

   }
}
