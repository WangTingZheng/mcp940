package net.minecraft.entity.ai;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLiving;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.util.math.BlockPos;

public abstract class EntityAIDoorInteract extends EntityAIBase {
   protected EntityLiving field_75356_a;
   protected BlockPos field_179507_b = BlockPos.field_177992_a;
   protected BlockDoor field_151504_e;
   boolean field_75350_f;
   float field_75351_g;
   float field_75357_h;

   public EntityAIDoorInteract(EntityLiving p_i1621_1_) {
      this.field_75356_a = p_i1621_1_;
      if (!(p_i1621_1_.func_70661_as() instanceof PathNavigateGround)) {
         throw new IllegalArgumentException("Unsupported mob type for DoorInteractGoal");
      }
   }

   public boolean func_75250_a() {
      if (!this.field_75356_a.field_70123_F) {
         return false;
      } else {
         PathNavigateGround pathnavigateground = (PathNavigateGround)this.field_75356_a.func_70661_as();
         Path path = pathnavigateground.func_75505_d();
         if (path != null && !path.func_75879_b() && pathnavigateground.func_179686_g()) {
            for(int i = 0; i < Math.min(path.func_75873_e() + 2, path.func_75874_d()); ++i) {
               PathPoint pathpoint = path.func_75877_a(i);
               this.field_179507_b = new BlockPos(pathpoint.field_75839_a, pathpoint.field_75837_b + 1, pathpoint.field_75838_c);
               if (this.field_75356_a.func_70092_e((double)this.field_179507_b.func_177958_n(), this.field_75356_a.field_70163_u, (double)this.field_179507_b.func_177952_p()) <= 2.25D) {
                  this.field_151504_e = this.func_179506_a(this.field_179507_b);
                  if (this.field_151504_e != null) {
                     return true;
                  }
               }
            }

            this.field_179507_b = (new BlockPos(this.field_75356_a)).func_177984_a();
            this.field_151504_e = this.func_179506_a(this.field_179507_b);
            return this.field_151504_e != null;
         } else {
            return false;
         }
      }
   }

   public boolean func_75253_b() {
      return !this.field_75350_f;
   }

   public void func_75249_e() {
      this.field_75350_f = false;
      this.field_75351_g = (float)((double)((float)this.field_179507_b.func_177958_n() + 0.5F) - this.field_75356_a.field_70165_t);
      this.field_75357_h = (float)((double)((float)this.field_179507_b.func_177952_p() + 0.5F) - this.field_75356_a.field_70161_v);
   }

   public void func_75246_d() {
      float f = (float)((double)((float)this.field_179507_b.func_177958_n() + 0.5F) - this.field_75356_a.field_70165_t);
      float f1 = (float)((double)((float)this.field_179507_b.func_177952_p() + 0.5F) - this.field_75356_a.field_70161_v);
      float f2 = this.field_75351_g * f + this.field_75357_h * f1;
      if (f2 < 0.0F) {
         this.field_75350_f = true;
      }

   }

   private BlockDoor func_179506_a(BlockPos p_179506_1_) {
      IBlockState iblockstate = this.field_75356_a.field_70170_p.func_180495_p(p_179506_1_);
      Block block = iblockstate.func_177230_c();
      return block instanceof BlockDoor && iblockstate.func_185904_a() == Material.field_151575_d ? (BlockDoor)block : null;
   }
}
