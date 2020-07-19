package net.minecraft.pathfinding;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class PathNavigateFlying extends PathNavigate {
   public PathNavigateFlying(EntityLiving p_i47412_1_, World p_i47412_2_) {
      super(p_i47412_1_, p_i47412_2_);
   }

   protected PathFinder func_179679_a() {
      this.field_179695_a = new FlyingNodeProcessor();
      this.field_179695_a.func_186317_a(true);
      return new PathFinder(this.field_179695_a);
   }

   protected boolean func_75485_k() {
      return this.func_192880_g() && this.func_75506_l() || !this.field_75515_a.func_184218_aH();
   }

   protected Vec3d func_75502_i() {
      return new Vec3d(this.field_75515_a.field_70165_t, this.field_75515_a.field_70163_u, this.field_75515_a.field_70161_v);
   }

   public Path func_75494_a(Entity p_75494_1_) {
      return this.func_179680_a(new BlockPos(p_75494_1_));
   }

   public void func_75501_e() {
      ++this.field_75510_g;
      if (this.field_188562_p) {
         this.func_188554_j();
      }

      if (!this.func_75500_f()) {
         if (this.func_75485_k()) {
            this.func_75508_h();
         } else if (this.field_75514_c != null && this.field_75514_c.func_75873_e() < this.field_75514_c.func_75874_d()) {
            Vec3d vec3d = this.field_75514_c.func_75881_a(this.field_75515_a, this.field_75514_c.func_75873_e());
            if (MathHelper.func_76128_c(this.field_75515_a.field_70165_t) == MathHelper.func_76128_c(vec3d.field_72450_a) && MathHelper.func_76128_c(this.field_75515_a.field_70163_u) == MathHelper.func_76128_c(vec3d.field_72448_b) && MathHelper.func_76128_c(this.field_75515_a.field_70161_v) == MathHelper.func_76128_c(vec3d.field_72449_c)) {
               this.field_75514_c.func_75872_c(this.field_75514_c.func_75873_e() + 1);
            }
         }

         this.func_192876_m();
         if (!this.func_75500_f()) {
            Vec3d vec3d1 = this.field_75514_c.func_75878_a(this.field_75515_a);
            this.field_75515_a.func_70605_aq().func_75642_a(vec3d1.field_72450_a, vec3d1.field_72448_b, vec3d1.field_72449_c, this.field_75511_d);
         }
      }
   }

   protected boolean func_75493_a(Vec3d p_75493_1_, Vec3d p_75493_2_, int p_75493_3_, int p_75493_4_, int p_75493_5_) {
      int i = MathHelper.func_76128_c(p_75493_1_.field_72450_a);
      int j = MathHelper.func_76128_c(p_75493_1_.field_72448_b);
      int k = MathHelper.func_76128_c(p_75493_1_.field_72449_c);
      double d0 = p_75493_2_.field_72450_a - p_75493_1_.field_72450_a;
      double d1 = p_75493_2_.field_72448_b - p_75493_1_.field_72448_b;
      double d2 = p_75493_2_.field_72449_c - p_75493_1_.field_72449_c;
      double d3 = d0 * d0 + d1 * d1 + d2 * d2;
      if (d3 < 1.0E-8D) {
         return false;
      } else {
         double d4 = 1.0D / Math.sqrt(d3);
         d0 = d0 * d4;
         d1 = d1 * d4;
         d2 = d2 * d4;
         double d5 = 1.0D / Math.abs(d0);
         double d6 = 1.0D / Math.abs(d1);
         double d7 = 1.0D / Math.abs(d2);
         double d8 = (double)i - p_75493_1_.field_72450_a;
         double d9 = (double)j - p_75493_1_.field_72448_b;
         double d10 = (double)k - p_75493_1_.field_72449_c;
         if (d0 >= 0.0D) {
            ++d8;
         }

         if (d1 >= 0.0D) {
            ++d9;
         }

         if (d2 >= 0.0D) {
            ++d10;
         }

         d8 = d8 / d0;
         d9 = d9 / d1;
         d10 = d10 / d2;
         int l = d0 < 0.0D ? -1 : 1;
         int i1 = d1 < 0.0D ? -1 : 1;
         int j1 = d2 < 0.0D ? -1 : 1;
         int k1 = MathHelper.func_76128_c(p_75493_2_.field_72450_a);
         int l1 = MathHelper.func_76128_c(p_75493_2_.field_72448_b);
         int i2 = MathHelper.func_76128_c(p_75493_2_.field_72449_c);
         int j2 = k1 - i;
         int k2 = l1 - j;
         int l2 = i2 - k;

         while(j2 * l > 0 || k2 * i1 > 0 || l2 * j1 > 0) {
            if (d8 < d10 && d8 <= d9) {
               d8 += d5;
               i += l;
               j2 = k1 - i;
            } else if (d9 < d8 && d9 <= d10) {
               d9 += d6;
               j += i1;
               k2 = l1 - j;
            } else {
               d10 += d7;
               k += j1;
               l2 = i2 - k;
            }
         }

         return true;
      }
   }

   public void func_192879_a(boolean p_192879_1_) {
      this.field_179695_a.func_186321_b(p_192879_1_);
   }

   public void func_192878_b(boolean p_192878_1_) {
      this.field_179695_a.func_186317_a(p_192878_1_);
   }

   public void func_192877_c(boolean p_192877_1_) {
      this.field_179695_a.func_186316_c(p_192877_1_);
   }

   public boolean func_192880_g() {
      return this.field_179695_a.func_186322_e();
   }

   public boolean func_188555_b(BlockPos p_188555_1_) {
      return this.field_75513_b.func_180495_p(p_188555_1_).func_185896_q();
   }
}
