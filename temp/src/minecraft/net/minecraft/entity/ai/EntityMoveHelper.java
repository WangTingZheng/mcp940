package net.minecraft.entity.ai;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.pathfinding.NodeProcessor;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.math.MathHelper;

public class EntityMoveHelper {
   protected final EntityLiving field_75648_a;
   protected double field_75646_b;
   protected double field_75647_c;
   protected double field_75644_d;
   protected double field_75645_e;
   protected float field_188489_f;
   protected float field_188490_g;
   public EntityMoveHelper.Action field_188491_h = EntityMoveHelper.Action.WAIT;

   public EntityMoveHelper(EntityLiving p_i1614_1_) {
      this.field_75648_a = p_i1614_1_;
   }

   public boolean func_75640_a() {
      return this.field_188491_h == EntityMoveHelper.Action.MOVE_TO;
   }

   public double func_75638_b() {
      return this.field_75645_e;
   }

   public void func_75642_a(double p_75642_1_, double p_75642_3_, double p_75642_5_, double p_75642_7_) {
      this.field_75646_b = p_75642_1_;
      this.field_75647_c = p_75642_3_;
      this.field_75644_d = p_75642_5_;
      this.field_75645_e = p_75642_7_;
      this.field_188491_h = EntityMoveHelper.Action.MOVE_TO;
   }

   public void func_188488_a(float p_188488_1_, float p_188488_2_) {
      this.field_188491_h = EntityMoveHelper.Action.STRAFE;
      this.field_188489_f = p_188488_1_;
      this.field_188490_g = p_188488_2_;
      this.field_75645_e = 0.25D;
   }

   public void func_188487_a(EntityMoveHelper p_188487_1_) {
      this.field_188491_h = p_188487_1_.field_188491_h;
      this.field_75646_b = p_188487_1_.field_75646_b;
      this.field_75647_c = p_188487_1_.field_75647_c;
      this.field_75644_d = p_188487_1_.field_75644_d;
      this.field_75645_e = Math.max(p_188487_1_.field_75645_e, 1.0D);
      this.field_188489_f = p_188487_1_.field_188489_f;
      this.field_188490_g = p_188487_1_.field_188490_g;
   }

   public void func_75641_c() {
      if (this.field_188491_h == EntityMoveHelper.Action.STRAFE) {
         float f = (float)this.field_75648_a.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111126_e();
         float f1 = (float)this.field_75645_e * f;
         float f2 = this.field_188489_f;
         float f3 = this.field_188490_g;
         float f4 = MathHelper.func_76129_c(f2 * f2 + f3 * f3);
         if (f4 < 1.0F) {
            f4 = 1.0F;
         }

         f4 = f1 / f4;
         f2 = f2 * f4;
         f3 = f3 * f4;
         float f5 = MathHelper.func_76126_a(this.field_75648_a.field_70177_z * 0.017453292F);
         float f6 = MathHelper.func_76134_b(this.field_75648_a.field_70177_z * 0.017453292F);
         float f7 = f2 * f6 - f3 * f5;
         float f8 = f3 * f6 + f2 * f5;
         PathNavigate pathnavigate = this.field_75648_a.func_70661_as();
         if (pathnavigate != null) {
            NodeProcessor nodeprocessor = pathnavigate.func_189566_q();
            if (nodeprocessor != null && nodeprocessor.func_186330_a(this.field_75648_a.field_70170_p, MathHelper.func_76128_c(this.field_75648_a.field_70165_t + (double)f7), MathHelper.func_76128_c(this.field_75648_a.field_70163_u), MathHelper.func_76128_c(this.field_75648_a.field_70161_v + (double)f8)) != PathNodeType.WALKABLE) {
               this.field_188489_f = 1.0F;
               this.field_188490_g = 0.0F;
               f1 = f;
            }
         }

         this.field_75648_a.func_70659_e(f1);
         this.field_75648_a.func_191989_p(this.field_188489_f);
         this.field_75648_a.func_184646_p(this.field_188490_g);
         this.field_188491_h = EntityMoveHelper.Action.WAIT;
      } else if (this.field_188491_h == EntityMoveHelper.Action.MOVE_TO) {
         this.field_188491_h = EntityMoveHelper.Action.WAIT;
         double d0 = this.field_75646_b - this.field_75648_a.field_70165_t;
         double d1 = this.field_75644_d - this.field_75648_a.field_70161_v;
         double d2 = this.field_75647_c - this.field_75648_a.field_70163_u;
         double d3 = d0 * d0 + d2 * d2 + d1 * d1;
         if (d3 < 2.500000277905201E-7D) {
            this.field_75648_a.func_191989_p(0.0F);
            return;
         }

         float f9 = (float)(MathHelper.func_181159_b(d1, d0) * 57.2957763671875D) - 90.0F;
         this.field_75648_a.field_70177_z = this.func_75639_a(this.field_75648_a.field_70177_z, f9, 90.0F);
         this.field_75648_a.func_70659_e((float)(this.field_75645_e * this.field_75648_a.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111126_e()));
         if (d2 > (double)this.field_75648_a.field_70138_W && d0 * d0 + d1 * d1 < (double)Math.max(1.0F, this.field_75648_a.field_70130_N)) {
            this.field_75648_a.func_70683_ar().func_75660_a();
            this.field_188491_h = EntityMoveHelper.Action.JUMPING;
         }
      } else if (this.field_188491_h == EntityMoveHelper.Action.JUMPING) {
         this.field_75648_a.func_70659_e((float)(this.field_75645_e * this.field_75648_a.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111126_e()));
         if (this.field_75648_a.field_70122_E) {
            this.field_188491_h = EntityMoveHelper.Action.WAIT;
         }
      } else {
         this.field_75648_a.func_191989_p(0.0F);
      }

   }

   protected float func_75639_a(float p_75639_1_, float p_75639_2_, float p_75639_3_) {
      float f = MathHelper.func_76142_g(p_75639_2_ - p_75639_1_);
      if (f > p_75639_3_) {
         f = p_75639_3_;
      }

      if (f < -p_75639_3_) {
         f = -p_75639_3_;
      }

      float f1 = p_75639_1_ + f;
      if (f1 < 0.0F) {
         f1 += 360.0F;
      } else if (f1 > 360.0F) {
         f1 -= 360.0F;
      }

      return f1;
   }

   public double func_179917_d() {
      return this.field_75646_b;
   }

   public double func_179919_e() {
      return this.field_75647_c;
   }

   public double func_179918_f() {
      return this.field_75644_d;
   }

   public static enum Action {
      WAIT,
      MOVE_TO,
      STRAFE,
      JUMPING;
   }
}
