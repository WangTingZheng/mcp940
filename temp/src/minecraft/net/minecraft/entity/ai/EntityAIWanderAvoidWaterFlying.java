package net.minecraft.entity.ai;

import java.util.Iterator;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.entity.EntityCreature;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class EntityAIWanderAvoidWaterFlying extends EntityAIWanderAvoidWater {
   public EntityAIWanderAvoidWaterFlying(EntityCreature p_i47413_1_, double p_i47413_2_) {
      super(p_i47413_1_, p_i47413_2_);
   }

   @Nullable
   protected Vec3d func_190864_f() {
      Vec3d vec3d = null;
      if (this.field_75457_a.func_70090_H() || this.field_75457_a.func_191953_am()) {
         vec3d = RandomPositionGenerator.func_191377_b(this.field_75457_a, 15, 15);
      }

      if (this.field_75457_a.func_70681_au().nextFloat() >= this.field_190865_h) {
         vec3d = this.func_192385_j();
      }

      return vec3d == null ? super.func_190864_f() : vec3d;
   }

   @Nullable
   private Vec3d func_192385_j() {
      BlockPos blockpos = new BlockPos(this.field_75457_a);
      BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
      BlockPos.MutableBlockPos blockpos$mutableblockpos1 = new BlockPos.MutableBlockPos();
      Iterable<BlockPos.MutableBlockPos> iterable = BlockPos.MutableBlockPos.func_191531_b(MathHelper.func_76128_c(this.field_75457_a.field_70165_t - 3.0D), MathHelper.func_76128_c(this.field_75457_a.field_70163_u - 6.0D), MathHelper.func_76128_c(this.field_75457_a.field_70161_v - 3.0D), MathHelper.func_76128_c(this.field_75457_a.field_70165_t + 3.0D), MathHelper.func_76128_c(this.field_75457_a.field_70163_u + 6.0D), MathHelper.func_76128_c(this.field_75457_a.field_70161_v + 3.0D));
      Iterator iterator = iterable.iterator();

      BlockPos blockpos1;
      while(true) {
         if (!iterator.hasNext()) {
            return null;
         }

         blockpos1 = (BlockPos)iterator.next();
         if (!blockpos.equals(blockpos1)) {
            Block block = this.field_75457_a.field_70170_p.func_180495_p(blockpos$mutableblockpos1.func_189533_g(blockpos1).func_189536_c(EnumFacing.DOWN)).func_177230_c();
            boolean flag = block instanceof BlockLeaves || block == Blocks.field_150364_r || block == Blocks.field_150363_s;
            if (flag && this.field_75457_a.field_70170_p.func_175623_d(blockpos1) && this.field_75457_a.field_70170_p.func_175623_d(blockpos$mutableblockpos.func_189533_g(blockpos1).func_189536_c(EnumFacing.UP))) {
               break;
            }
         }
      }

      return new Vec3d((double)blockpos1.func_177958_n(), (double)blockpos1.func_177956_o(), (double)blockpos1.func_177952_p());
   }
}
