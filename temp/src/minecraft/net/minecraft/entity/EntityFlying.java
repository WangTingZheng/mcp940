package net.minecraft.entity;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public abstract class EntityFlying extends EntityLiving {
   public EntityFlying(World p_i1587_1_) {
      super(p_i1587_1_);
   }

   public void func_180430_e(float p_180430_1_, float p_180430_2_) {
   }

   protected void func_184231_a(double p_184231_1_, boolean p_184231_3_, IBlockState p_184231_4_, BlockPos p_184231_5_) {
   }

   public void func_191986_a(float p_191986_1_, float p_191986_2_, float p_191986_3_) {
      if (this.func_70090_H()) {
         this.func_191958_b(p_191986_1_, p_191986_2_, p_191986_3_, 0.02F);
         this.func_70091_d(MoverType.SELF, this.field_70159_w, this.field_70181_x, this.field_70179_y);
         this.field_70159_w *= 0.800000011920929D;
         this.field_70181_x *= 0.800000011920929D;
         this.field_70179_y *= 0.800000011920929D;
      } else if (this.func_180799_ab()) {
         this.func_191958_b(p_191986_1_, p_191986_2_, p_191986_3_, 0.02F);
         this.func_70091_d(MoverType.SELF, this.field_70159_w, this.field_70181_x, this.field_70179_y);
         this.field_70159_w *= 0.5D;
         this.field_70181_x *= 0.5D;
         this.field_70179_y *= 0.5D;
      } else {
         float f = 0.91F;
         if (this.field_70122_E) {
            f = this.field_70170_p.func_180495_p(new BlockPos(MathHelper.func_76128_c(this.field_70165_t), MathHelper.func_76128_c(this.func_174813_aQ().field_72338_b) - 1, MathHelper.func_76128_c(this.field_70161_v))).func_177230_c().field_149765_K * 0.91F;
         }

         float f1 = 0.16277136F / (f * f * f);
         this.func_191958_b(p_191986_1_, p_191986_2_, p_191986_3_, this.field_70122_E ? 0.1F * f1 : 0.02F);
         f = 0.91F;
         if (this.field_70122_E) {
            f = this.field_70170_p.func_180495_p(new BlockPos(MathHelper.func_76128_c(this.field_70165_t), MathHelper.func_76128_c(this.func_174813_aQ().field_72338_b) - 1, MathHelper.func_76128_c(this.field_70161_v))).func_177230_c().field_149765_K * 0.91F;
         }

         this.func_70091_d(MoverType.SELF, this.field_70159_w, this.field_70181_x, this.field_70179_y);
         this.field_70159_w *= (double)f;
         this.field_70181_x *= (double)f;
         this.field_70179_y *= (double)f;
      }

      this.field_184618_aE = this.field_70721_aZ;
      double d1 = this.field_70165_t - this.field_70169_q;
      double d0 = this.field_70161_v - this.field_70166_s;
      float f2 = MathHelper.func_76133_a(d1 * d1 + d0 * d0) * 4.0F;
      if (f2 > 1.0F) {
         f2 = 1.0F;
      }

      this.field_70721_aZ += (f2 - this.field_70721_aZ) * 0.4F;
      this.field_184619_aG += this.field_70721_aZ;
   }

   public boolean func_70617_f_() {
      return false;
   }
}
