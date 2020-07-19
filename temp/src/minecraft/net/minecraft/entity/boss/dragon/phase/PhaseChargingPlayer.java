package net.minecraft.entity.boss.dragon.phase;

import javax.annotation.Nullable;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.util.math.Vec3d;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PhaseChargingPlayer extends PhaseBase {
   private static final Logger field_188669_b = LogManager.getLogger();
   private Vec3d field_188670_c;
   private int field_188671_d;

   public PhaseChargingPlayer(EntityDragon p_i46793_1_) {
      super(p_i46793_1_);
   }

   public void func_188659_c() {
      if (this.field_188670_c == null) {
         field_188669_b.warn("Aborting charge player as no target was set.");
         this.field_188661_a.func_184670_cT().func_188758_a(PhaseList.field_188741_a);
      } else if (this.field_188671_d > 0 && this.field_188671_d++ >= 10) {
         this.field_188661_a.func_184670_cT().func_188758_a(PhaseList.field_188741_a);
      } else {
         double d0 = this.field_188670_c.func_186679_c(this.field_188661_a.field_70165_t, this.field_188661_a.field_70163_u, this.field_188661_a.field_70161_v);
         if (d0 < 100.0D || d0 > 22500.0D || this.field_188661_a.field_70123_F || this.field_188661_a.field_70124_G) {
            ++this.field_188671_d;
         }

      }
   }

   public void func_188660_d() {
      this.field_188670_c = null;
      this.field_188671_d = 0;
   }

   public void func_188668_a(Vec3d p_188668_1_) {
      this.field_188670_c = p_188668_1_;
   }

   public float func_188651_f() {
      return 3.0F;
   }

   @Nullable
   public Vec3d func_188650_g() {
      return this.field_188670_c;
   }

   public PhaseList<PhaseChargingPlayer> func_188652_i() {
      return PhaseList.field_188749_i;
   }
}
