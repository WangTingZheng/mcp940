package net.minecraft.entity.boss.dragon.phase;

import net.minecraft.entity.EntityAreaEffectCloud;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class PhaseSittingFlaming extends PhaseSittingBase {
   private int field_188664_b;
   private int field_188665_c;
   private EntityAreaEffectCloud field_188666_d;

   public PhaseSittingFlaming(EntityDragon p_i46786_1_) {
      super(p_i46786_1_);
   }

   public void func_188657_b() {
      ++this.field_188664_b;
      if (this.field_188664_b % 2 == 0 && this.field_188664_b < 10) {
         Vec3d vec3d = this.field_188661_a.func_184665_a(1.0F).func_72432_b();
         vec3d.func_178785_b(-0.7853982F);
         double d0 = this.field_188661_a.field_70986_h.field_70165_t;
         double d1 = this.field_188661_a.field_70986_h.field_70163_u + (double)(this.field_188661_a.field_70986_h.field_70131_O / 2.0F);
         double d2 = this.field_188661_a.field_70986_h.field_70161_v;

         for(int i = 0; i < 8; ++i) {
            double d3 = d0 + this.field_188661_a.func_70681_au().nextGaussian() / 2.0D;
            double d4 = d1 + this.field_188661_a.func_70681_au().nextGaussian() / 2.0D;
            double d5 = d2 + this.field_188661_a.func_70681_au().nextGaussian() / 2.0D;

            for(int j = 0; j < 6; ++j) {
               this.field_188661_a.field_70170_p.func_175688_a(EnumParticleTypes.DRAGON_BREATH, d3, d4, d5, -vec3d.field_72450_a * 0.07999999821186066D * (double)j, -vec3d.field_72448_b * 0.6000000238418579D, -vec3d.field_72449_c * 0.07999999821186066D * (double)j);
            }

            vec3d.func_178785_b(0.19634955F);
         }
      }

   }

   public void func_188659_c() {
      ++this.field_188664_b;
      if (this.field_188664_b >= 200) {
         if (this.field_188665_c >= 4) {
            this.field_188661_a.func_184670_cT().func_188758_a(PhaseList.field_188745_e);
         } else {
            this.field_188661_a.func_184670_cT().func_188758_a(PhaseList.field_188747_g);
         }
      } else if (this.field_188664_b == 10) {
         Vec3d vec3d = (new Vec3d(this.field_188661_a.field_70986_h.field_70165_t - this.field_188661_a.field_70165_t, 0.0D, this.field_188661_a.field_70986_h.field_70161_v - this.field_188661_a.field_70161_v)).func_72432_b();
         float f = 5.0F;
         double d0 = this.field_188661_a.field_70986_h.field_70165_t + vec3d.field_72450_a * 5.0D / 2.0D;
         double d1 = this.field_188661_a.field_70986_h.field_70161_v + vec3d.field_72449_c * 5.0D / 2.0D;
         double d2 = this.field_188661_a.field_70986_h.field_70163_u + (double)(this.field_188661_a.field_70986_h.field_70131_O / 2.0F);
         BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos(MathHelper.func_76128_c(d0), MathHelper.func_76128_c(d2), MathHelper.func_76128_c(d1));

         while(this.field_188661_a.field_70170_p.func_175623_d(blockpos$mutableblockpos)) {
            --d2;
            blockpos$mutableblockpos.func_181079_c(MathHelper.func_76128_c(d0), MathHelper.func_76128_c(d2), MathHelper.func_76128_c(d1));
         }

         d2 = (double)(MathHelper.func_76128_c(d2) + 1);
         this.field_188666_d = new EntityAreaEffectCloud(this.field_188661_a.field_70170_p, d0, d2, d1);
         this.field_188666_d.func_184481_a(this.field_188661_a);
         this.field_188666_d.func_184483_a(5.0F);
         this.field_188666_d.func_184486_b(200);
         this.field_188666_d.func_184491_a(EnumParticleTypes.DRAGON_BREATH);
         this.field_188666_d.func_184496_a(new PotionEffect(MobEffects.field_76433_i));
         this.field_188661_a.field_70170_p.func_72838_d(this.field_188666_d);
      }

   }

   public void func_188660_d() {
      this.field_188664_b = 0;
      ++this.field_188665_c;
   }

   public void func_188658_e() {
      if (this.field_188666_d != null) {
         this.field_188666_d.func_70106_y();
         this.field_188666_d = null;
      }

   }

   public PhaseList<PhaseSittingFlaming> func_188652_i() {
      return PhaseList.field_188746_f;
   }

   public void func_188663_j() {
      this.field_188665_c = 0;
   }
}
