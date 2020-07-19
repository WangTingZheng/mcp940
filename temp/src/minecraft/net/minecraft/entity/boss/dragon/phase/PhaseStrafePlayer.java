package net.minecraft.entity.boss.dragon.phase;

import javax.annotation.Nullable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityDragonFireball;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PhaseStrafePlayer extends PhaseBase {
   private static final Logger field_188689_b = LogManager.getLogger();
   private int field_188690_c;
   private Path field_188691_d;
   private Vec3d field_188692_e;
   private EntityLivingBase field_188693_f;
   private boolean field_188694_g;

   public PhaseStrafePlayer(EntityDragon p_i46784_1_) {
      super(p_i46784_1_);
   }

   public void func_188659_c() {
      if (this.field_188693_f == null) {
         field_188689_b.warn("Skipping player strafe phase because no player was found");
         this.field_188661_a.func_184670_cT().func_188758_a(PhaseList.field_188741_a);
      } else {
         if (this.field_188691_d != null && this.field_188691_d.func_75879_b()) {
            double d0 = this.field_188693_f.field_70165_t;
            double d1 = this.field_188693_f.field_70161_v;
            double d2 = d0 - this.field_188661_a.field_70165_t;
            double d3 = d1 - this.field_188661_a.field_70161_v;
            double d4 = (double)MathHelper.func_76133_a(d2 * d2 + d3 * d3);
            double d5 = Math.min(0.4000000059604645D + d4 / 80.0D - 1.0D, 10.0D);
            this.field_188692_e = new Vec3d(d0, this.field_188693_f.field_70163_u + d5, d1);
         }

         double d12 = this.field_188692_e == null ? 0.0D : this.field_188692_e.func_186679_c(this.field_188661_a.field_70165_t, this.field_188661_a.field_70163_u, this.field_188661_a.field_70161_v);
         if (d12 < 100.0D || d12 > 22500.0D) {
            this.func_188687_j();
         }

         double d13 = 64.0D;
         if (this.field_188693_f.func_70068_e(this.field_188661_a) < 4096.0D) {
            if (this.field_188661_a.func_70685_l(this.field_188693_f)) {
               ++this.field_188690_c;
               Vec3d vec3d1 = (new Vec3d(this.field_188693_f.field_70165_t - this.field_188661_a.field_70165_t, 0.0D, this.field_188693_f.field_70161_v - this.field_188661_a.field_70161_v)).func_72432_b();
               Vec3d vec3d = (new Vec3d((double)MathHelper.func_76126_a(this.field_188661_a.field_70177_z * 0.017453292F), 0.0D, (double)(-MathHelper.func_76134_b(this.field_188661_a.field_70177_z * 0.017453292F)))).func_72432_b();
               float f1 = (float)vec3d.func_72430_b(vec3d1);
               float f = (float)(Math.acos((double)f1) * 57.2957763671875D);
               f = f + 0.5F;
               if (this.field_188690_c >= 5 && f >= 0.0F && f < 10.0F) {
                  double d14 = 1.0D;
                  Vec3d vec3d2 = this.field_188661_a.func_70676_i(1.0F);
                  double d6 = this.field_188661_a.field_70986_h.field_70165_t - vec3d2.field_72450_a * 1.0D;
                  double d7 = this.field_188661_a.field_70986_h.field_70163_u + (double)(this.field_188661_a.field_70986_h.field_70131_O / 2.0F) + 0.5D;
                  double d8 = this.field_188661_a.field_70986_h.field_70161_v - vec3d2.field_72449_c * 1.0D;
                  double d9 = this.field_188693_f.field_70165_t - d6;
                  double d10 = this.field_188693_f.field_70163_u + (double)(this.field_188693_f.field_70131_O / 2.0F) - (d7 + (double)(this.field_188661_a.field_70986_h.field_70131_O / 2.0F));
                  double d11 = this.field_188693_f.field_70161_v - d8;
                  this.field_188661_a.field_70170_p.func_180498_a((EntityPlayer)null, 1017, new BlockPos(this.field_188661_a), 0);
                  EntityDragonFireball entitydragonfireball = new EntityDragonFireball(this.field_188661_a.field_70170_p, this.field_188661_a, d9, d10, d11);
                  entitydragonfireball.func_70012_b(d6, d7, d8, 0.0F, 0.0F);
                  this.field_188661_a.field_70170_p.func_72838_d(entitydragonfireball);
                  this.field_188690_c = 0;
                  if (this.field_188691_d != null) {
                     while(!this.field_188691_d.func_75879_b()) {
                        this.field_188691_d.func_75875_a();
                     }
                  }

                  this.field_188661_a.func_184670_cT().func_188758_a(PhaseList.field_188741_a);
               }
            } else if (this.field_188690_c > 0) {
               --this.field_188690_c;
            }
         } else if (this.field_188690_c > 0) {
            --this.field_188690_c;
         }

      }
   }

   private void func_188687_j() {
      if (this.field_188691_d == null || this.field_188691_d.func_75879_b()) {
         int i = this.field_188661_a.func_184671_o();
         int j = i;
         if (this.field_188661_a.func_70681_au().nextInt(8) == 0) {
            this.field_188694_g = !this.field_188694_g;
            j = i + 6;
         }

         if (this.field_188694_g) {
            ++j;
         } else {
            --j;
         }

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

         this.field_188691_d = this.field_188661_a.func_184666_a(i, j, (PathPoint)null);
         if (this.field_188691_d != null) {
            this.field_188691_d.func_75875_a();
         }
      }

      this.func_188688_k();
   }

   private void func_188688_k() {
      if (this.field_188691_d != null && !this.field_188691_d.func_75879_b()) {
         Vec3d vec3d = this.field_188691_d.func_186310_f();
         this.field_188691_d.func_75875_a();
         double d0 = vec3d.field_72450_a;
         double d2 = vec3d.field_72449_c;

         double d1;
         while(true) {
            d1 = vec3d.field_72448_b + (double)(this.field_188661_a.func_70681_au().nextFloat() * 20.0F);
            if (d1 >= vec3d.field_72448_b) {
               break;
            }
         }

         this.field_188692_e = new Vec3d(d0, d1, d2);
      }

   }

   public void func_188660_d() {
      this.field_188690_c = 0;
      this.field_188692_e = null;
      this.field_188691_d = null;
      this.field_188693_f = null;
   }

   public void func_188686_a(EntityLivingBase p_188686_1_) {
      this.field_188693_f = p_188686_1_;
      int i = this.field_188661_a.func_184671_o();
      int j = this.field_188661_a.func_184663_l(this.field_188693_f.field_70165_t, this.field_188693_f.field_70163_u, this.field_188693_f.field_70161_v);
      int k = MathHelper.func_76128_c(this.field_188693_f.field_70165_t);
      int l = MathHelper.func_76128_c(this.field_188693_f.field_70161_v);
      double d0 = (double)k - this.field_188661_a.field_70165_t;
      double d1 = (double)l - this.field_188661_a.field_70161_v;
      double d2 = (double)MathHelper.func_76133_a(d0 * d0 + d1 * d1);
      double d3 = Math.min(0.4000000059604645D + d2 / 80.0D - 1.0D, 10.0D);
      int i1 = MathHelper.func_76128_c(this.field_188693_f.field_70163_u + d3);
      PathPoint pathpoint = new PathPoint(k, i1, l);
      this.field_188691_d = this.field_188661_a.func_184666_a(i, j, pathpoint);
      if (this.field_188691_d != null) {
         this.field_188691_d.func_75875_a();
         this.func_188688_k();
      }

   }

   @Nullable
   public Vec3d func_188650_g() {
      return this.field_188692_e;
   }

   public PhaseList<PhaseStrafePlayer> func_188652_i() {
      return PhaseList.field_188742_b;
   }
}
