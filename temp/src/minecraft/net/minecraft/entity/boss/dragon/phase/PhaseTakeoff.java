package net.minecraft.entity.boss.dragon.phase;

import javax.annotation.Nullable;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.gen.feature.WorldGenEndPodium;

public class PhaseTakeoff extends PhaseBase {
   private boolean field_188697_b;
   private Path field_188698_c;
   private Vec3d field_188699_d;

   public PhaseTakeoff(EntityDragon p_i46783_1_) {
      super(p_i46783_1_);
   }

   public void func_188659_c() {
      if (!this.field_188697_b && this.field_188698_c != null) {
         BlockPos blockpos = this.field_188661_a.field_70170_p.func_175672_r(WorldGenEndPodium.field_186139_a);
         double d0 = this.field_188661_a.func_174831_c(blockpos);
         if (d0 > 100.0D) {
            this.field_188661_a.func_184670_cT().func_188758_a(PhaseList.field_188741_a);
         }
      } else {
         this.field_188697_b = false;
         this.func_188695_j();
      }

   }

   public void func_188660_d() {
      this.field_188697_b = true;
      this.field_188698_c = null;
      this.field_188699_d = null;
   }

   private void func_188695_j() {
      int i = this.field_188661_a.func_184671_o();
      Vec3d vec3d = this.field_188661_a.func_184665_a(1.0F);
      int j = this.field_188661_a.func_184663_l(-vec3d.field_72450_a * 40.0D, 105.0D, -vec3d.field_72449_c * 40.0D);
      if (this.field_188661_a.func_184664_cU() != null && this.field_188661_a.func_184664_cU().func_186092_c() > 0) {
         j = j % 12;
         if (j < 0) {
            j += 12;
         }
      } else {
         j = j - 12;
         j = j & 7;
         j = j + 12;
      }

      this.field_188698_c = this.field_188661_a.func_184666_a(i, j, (PathPoint)null);
      if (this.field_188698_c != null) {
         this.field_188698_c.func_75875_a();
         this.func_188696_k();
      }

   }

   private void func_188696_k() {
      Vec3d vec3d = this.field_188698_c.func_186310_f();
      this.field_188698_c.func_75875_a();

      double d0;
      while(true) {
         d0 = vec3d.field_72448_b + (double)(this.field_188661_a.func_70681_au().nextFloat() * 20.0F);
         if (d0 >= vec3d.field_72448_b) {
            break;
         }
      }

      this.field_188699_d = new Vec3d(vec3d.field_72450_a, d0, vec3d.field_72449_c);
   }

   @Nullable
   public Vec3d func_188650_g() {
      return this.field_188699_d;
   }

   public PhaseList<PhaseTakeoff> func_188652_i() {
      return PhaseList.field_188745_e;
   }
}
