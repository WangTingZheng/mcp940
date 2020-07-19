package net.minecraft.pathfinding;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class PathNavigateClimber extends PathNavigateGround {
   private BlockPos field_179696_f;

   public PathNavigateClimber(EntityLiving p_i45874_1_, World p_i45874_2_) {
      super(p_i45874_1_, p_i45874_2_);
   }

   public Path func_179680_a(BlockPos p_179680_1_) {
      this.field_179696_f = p_179680_1_;
      return super.func_179680_a(p_179680_1_);
   }

   public Path func_75494_a(Entity p_75494_1_) {
      this.field_179696_f = new BlockPos(p_75494_1_);
      return super.func_75494_a(p_75494_1_);
   }

   public boolean func_75497_a(Entity p_75497_1_, double p_75497_2_) {
      Path path = this.func_75494_a(p_75497_1_);
      if (path != null) {
         return this.func_75484_a(path, p_75497_2_);
      } else {
         this.field_179696_f = new BlockPos(p_75497_1_);
         this.field_75511_d = p_75497_2_;
         return true;
      }
   }

   public void func_75501_e() {
      if (!this.func_75500_f()) {
         super.func_75501_e();
      } else {
         if (this.field_179696_f != null) {
            double d0 = (double)(this.field_75515_a.field_70130_N * this.field_75515_a.field_70130_N);
            if (this.field_75515_a.func_174831_c(this.field_179696_f) >= d0 && (this.field_75515_a.field_70163_u <= (double)this.field_179696_f.func_177956_o() || this.field_75515_a.func_174831_c(new BlockPos(this.field_179696_f.func_177958_n(), MathHelper.func_76128_c(this.field_75515_a.field_70163_u), this.field_179696_f.func_177952_p())) >= d0)) {
               this.field_75515_a.func_70605_aq().func_75642_a((double)this.field_179696_f.func_177958_n(), (double)this.field_179696_f.func_177956_o(), (double)this.field_179696_f.func_177952_p(), this.field_75511_d);
            } else {
               this.field_179696_f = null;
            }
         }

      }
   }
}
