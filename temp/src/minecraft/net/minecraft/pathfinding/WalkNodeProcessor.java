package net.minecraft.pathfinding;

import com.google.common.collect.Sets;
import java.util.EnumSet;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockRailBase;
import net.minecraft.block.BlockWall;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLiving;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;

public class WalkNodeProcessor extends NodeProcessor {
   protected float field_176183_h;

   public void func_186315_a(IBlockAccess p_186315_1_, EntityLiving p_186315_2_) {
      super.func_186315_a(p_186315_1_, p_186315_2_);
      this.field_176183_h = p_186315_2_.func_184643_a(PathNodeType.WATER);
   }

   public void func_176163_a() {
      this.field_186326_b.func_184644_a(PathNodeType.WATER, this.field_176183_h);
      super.func_176163_a();
   }

   public PathPoint func_186318_b() {
      int i;
      if (this.func_186322_e() && this.field_186326_b.func_70090_H()) {
         i = (int)this.field_186326_b.func_174813_aQ().field_72338_b;
         BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos(MathHelper.func_76128_c(this.field_186326_b.field_70165_t), i, MathHelper.func_76128_c(this.field_186326_b.field_70161_v));

         for(Block block = this.field_176169_a.func_180495_p(blockpos$mutableblockpos).func_177230_c(); block == Blocks.field_150358_i || block == Blocks.field_150355_j; block = this.field_176169_a.func_180495_p(blockpos$mutableblockpos).func_177230_c()) {
            ++i;
            blockpos$mutableblockpos.func_181079_c(MathHelper.func_76128_c(this.field_186326_b.field_70165_t), i, MathHelper.func_76128_c(this.field_186326_b.field_70161_v));
         }
      } else if (this.field_186326_b.field_70122_E) {
         i = MathHelper.func_76128_c(this.field_186326_b.func_174813_aQ().field_72338_b + 0.5D);
      } else {
         BlockPos blockpos;
         for(blockpos = new BlockPos(this.field_186326_b); (this.field_176169_a.func_180495_p(blockpos).func_185904_a() == Material.field_151579_a || this.field_176169_a.func_180495_p(blockpos).func_177230_c().func_176205_b(this.field_176169_a, blockpos)) && blockpos.func_177956_o() > 0; blockpos = blockpos.func_177977_b()) {
            ;
         }

         i = blockpos.func_177984_a().func_177956_o();
      }

      BlockPos blockpos2 = new BlockPos(this.field_186326_b);
      PathNodeType pathnodetype1 = this.func_186331_a(this.field_186326_b, blockpos2.func_177958_n(), i, blockpos2.func_177952_p());
      if (this.field_186326_b.func_184643_a(pathnodetype1) < 0.0F) {
         Set<BlockPos> set = Sets.<BlockPos>newHashSet();
         set.add(new BlockPos(this.field_186326_b.func_174813_aQ().field_72340_a, (double)i, this.field_186326_b.func_174813_aQ().field_72339_c));
         set.add(new BlockPos(this.field_186326_b.func_174813_aQ().field_72340_a, (double)i, this.field_186326_b.func_174813_aQ().field_72334_f));
         set.add(new BlockPos(this.field_186326_b.func_174813_aQ().field_72336_d, (double)i, this.field_186326_b.func_174813_aQ().field_72339_c));
         set.add(new BlockPos(this.field_186326_b.func_174813_aQ().field_72336_d, (double)i, this.field_186326_b.func_174813_aQ().field_72334_f));

         for(BlockPos blockpos1 : set) {
            PathNodeType pathnodetype = this.func_186329_a(this.field_186326_b, blockpos1);
            if (this.field_186326_b.func_184643_a(pathnodetype) >= 0.0F) {
               return this.func_176159_a(blockpos1.func_177958_n(), blockpos1.func_177956_o(), blockpos1.func_177952_p());
            }
         }
      }

      return this.func_176159_a(blockpos2.func_177958_n(), i, blockpos2.func_177952_p());
   }

   public PathPoint func_186325_a(double p_186325_1_, double p_186325_3_, double p_186325_5_) {
      return this.func_176159_a(MathHelper.func_76128_c(p_186325_1_), MathHelper.func_76128_c(p_186325_3_), MathHelper.func_76128_c(p_186325_5_));
   }

   public int func_186320_a(PathPoint[] p_186320_1_, PathPoint p_186320_2_, PathPoint p_186320_3_, float p_186320_4_) {
      int i = 0;
      int j = 0;
      PathNodeType pathnodetype = this.func_186331_a(this.field_186326_b, p_186320_2_.field_75839_a, p_186320_2_.field_75837_b + 1, p_186320_2_.field_75838_c);
      if (this.field_186326_b.func_184643_a(pathnodetype) >= 0.0F) {
         j = MathHelper.func_76141_d(Math.max(1.0F, this.field_186326_b.field_70138_W));
      }

      BlockPos blockpos = (new BlockPos(p_186320_2_.field_75839_a, p_186320_2_.field_75837_b, p_186320_2_.field_75838_c)).func_177977_b();
      double d0 = (double)p_186320_2_.field_75837_b - (1.0D - this.field_176169_a.func_180495_p(blockpos).func_185900_c(this.field_176169_a, blockpos).field_72337_e);
      PathPoint pathpoint = this.func_186332_a(p_186320_2_.field_75839_a, p_186320_2_.field_75837_b, p_186320_2_.field_75838_c + 1, j, d0, EnumFacing.SOUTH);
      PathPoint pathpoint1 = this.func_186332_a(p_186320_2_.field_75839_a - 1, p_186320_2_.field_75837_b, p_186320_2_.field_75838_c, j, d0, EnumFacing.WEST);
      PathPoint pathpoint2 = this.func_186332_a(p_186320_2_.field_75839_a + 1, p_186320_2_.field_75837_b, p_186320_2_.field_75838_c, j, d0, EnumFacing.EAST);
      PathPoint pathpoint3 = this.func_186332_a(p_186320_2_.field_75839_a, p_186320_2_.field_75837_b, p_186320_2_.field_75838_c - 1, j, d0, EnumFacing.NORTH);
      if (pathpoint != null && !pathpoint.field_75842_i && pathpoint.func_75829_a(p_186320_3_) < p_186320_4_) {
         p_186320_1_[i++] = pathpoint;
      }

      if (pathpoint1 != null && !pathpoint1.field_75842_i && pathpoint1.func_75829_a(p_186320_3_) < p_186320_4_) {
         p_186320_1_[i++] = pathpoint1;
      }

      if (pathpoint2 != null && !pathpoint2.field_75842_i && pathpoint2.func_75829_a(p_186320_3_) < p_186320_4_) {
         p_186320_1_[i++] = pathpoint2;
      }

      if (pathpoint3 != null && !pathpoint3.field_75842_i && pathpoint3.func_75829_a(p_186320_3_) < p_186320_4_) {
         p_186320_1_[i++] = pathpoint3;
      }

      boolean flag = pathpoint3 == null || pathpoint3.field_186287_m == PathNodeType.OPEN || pathpoint3.field_186286_l != 0.0F;
      boolean flag1 = pathpoint == null || pathpoint.field_186287_m == PathNodeType.OPEN || pathpoint.field_186286_l != 0.0F;
      boolean flag2 = pathpoint2 == null || pathpoint2.field_186287_m == PathNodeType.OPEN || pathpoint2.field_186286_l != 0.0F;
      boolean flag3 = pathpoint1 == null || pathpoint1.field_186287_m == PathNodeType.OPEN || pathpoint1.field_186286_l != 0.0F;
      if (flag && flag3) {
         PathPoint pathpoint4 = this.func_186332_a(p_186320_2_.field_75839_a - 1, p_186320_2_.field_75837_b, p_186320_2_.field_75838_c - 1, j, d0, EnumFacing.NORTH);
         if (pathpoint4 != null && !pathpoint4.field_75842_i && pathpoint4.func_75829_a(p_186320_3_) < p_186320_4_) {
            p_186320_1_[i++] = pathpoint4;
         }
      }

      if (flag && flag2) {
         PathPoint pathpoint5 = this.func_186332_a(p_186320_2_.field_75839_a + 1, p_186320_2_.field_75837_b, p_186320_2_.field_75838_c - 1, j, d0, EnumFacing.NORTH);
         if (pathpoint5 != null && !pathpoint5.field_75842_i && pathpoint5.func_75829_a(p_186320_3_) < p_186320_4_) {
            p_186320_1_[i++] = pathpoint5;
         }
      }

      if (flag1 && flag3) {
         PathPoint pathpoint6 = this.func_186332_a(p_186320_2_.field_75839_a - 1, p_186320_2_.field_75837_b, p_186320_2_.field_75838_c + 1, j, d0, EnumFacing.SOUTH);
         if (pathpoint6 != null && !pathpoint6.field_75842_i && pathpoint6.func_75829_a(p_186320_3_) < p_186320_4_) {
            p_186320_1_[i++] = pathpoint6;
         }
      }

      if (flag1 && flag2) {
         PathPoint pathpoint7 = this.func_186332_a(p_186320_2_.field_75839_a + 1, p_186320_2_.field_75837_b, p_186320_2_.field_75838_c + 1, j, d0, EnumFacing.SOUTH);
         if (pathpoint7 != null && !pathpoint7.field_75842_i && pathpoint7.func_75829_a(p_186320_3_) < p_186320_4_) {
            p_186320_1_[i++] = pathpoint7;
         }
      }

      return i;
   }

   @Nullable
   private PathPoint func_186332_a(int p_186332_1_, int p_186332_2_, int p_186332_3_, int p_186332_4_, double p_186332_5_, EnumFacing p_186332_7_) {
      PathPoint pathpoint = null;
      BlockPos blockpos = new BlockPos(p_186332_1_, p_186332_2_, p_186332_3_);
      BlockPos blockpos1 = blockpos.func_177977_b();
      double d0 = (double)p_186332_2_ - (1.0D - this.field_176169_a.func_180495_p(blockpos1).func_185900_c(this.field_176169_a, blockpos1).field_72337_e);
      if (d0 - p_186332_5_ > 1.125D) {
         return null;
      } else {
         PathNodeType pathnodetype = this.func_186331_a(this.field_186326_b, p_186332_1_, p_186332_2_, p_186332_3_);
         float f = this.field_186326_b.func_184643_a(pathnodetype);
         double d1 = (double)this.field_186326_b.field_70130_N / 2.0D;
         if (f >= 0.0F) {
            pathpoint = this.func_176159_a(p_186332_1_, p_186332_2_, p_186332_3_);
            pathpoint.field_186287_m = pathnodetype;
            pathpoint.field_186286_l = Math.max(pathpoint.field_186286_l, f);
         }

         if (pathnodetype == PathNodeType.WALKABLE) {
            return pathpoint;
         } else {
            if (pathpoint == null && p_186332_4_ > 0 && pathnodetype != PathNodeType.FENCE && pathnodetype != PathNodeType.TRAPDOOR) {
               pathpoint = this.func_186332_a(p_186332_1_, p_186332_2_ + 1, p_186332_3_, p_186332_4_ - 1, p_186332_5_, p_186332_7_);
               if (pathpoint != null && (pathpoint.field_186287_m == PathNodeType.OPEN || pathpoint.field_186287_m == PathNodeType.WALKABLE) && this.field_186326_b.field_70130_N < 1.0F) {
                  double d2 = (double)(p_186332_1_ - p_186332_7_.func_82601_c()) + 0.5D;
                  double d3 = (double)(p_186332_3_ - p_186332_7_.func_82599_e()) + 0.5D;
                  AxisAlignedBB axisalignedbb = new AxisAlignedBB(d2 - d1, (double)p_186332_2_ + 0.001D, d3 - d1, d2 + d1, (double)((float)p_186332_2_ + this.field_186326_b.field_70131_O), d3 + d1);
                  AxisAlignedBB axisalignedbb1 = this.field_176169_a.func_180495_p(blockpos).func_185900_c(this.field_176169_a, blockpos);
                  AxisAlignedBB axisalignedbb2 = axisalignedbb.func_72321_a(0.0D, axisalignedbb1.field_72337_e - 0.002D, 0.0D);
                  if (this.field_186326_b.field_70170_p.func_184143_b(axisalignedbb2)) {
                     pathpoint = null;
                  }
               }
            }

            if (pathnodetype == PathNodeType.OPEN) {
               AxisAlignedBB axisalignedbb3 = new AxisAlignedBB((double)p_186332_1_ - d1 + 0.5D, (double)p_186332_2_ + 0.001D, (double)p_186332_3_ - d1 + 0.5D, (double)p_186332_1_ + d1 + 0.5D, (double)((float)p_186332_2_ + this.field_186326_b.field_70131_O), (double)p_186332_3_ + d1 + 0.5D);
               if (this.field_186326_b.field_70170_p.func_184143_b(axisalignedbb3)) {
                  return null;
               }

               if (this.field_186326_b.field_70130_N >= 1.0F) {
                  PathNodeType pathnodetype1 = this.func_186331_a(this.field_186326_b, p_186332_1_, p_186332_2_ - 1, p_186332_3_);
                  if (pathnodetype1 == PathNodeType.BLOCKED) {
                     pathpoint = this.func_176159_a(p_186332_1_, p_186332_2_, p_186332_3_);
                     pathpoint.field_186287_m = PathNodeType.WALKABLE;
                     pathpoint.field_186286_l = Math.max(pathpoint.field_186286_l, f);
                     return pathpoint;
                  }
               }

               int i = 0;

               while(p_186332_2_ > 0 && pathnodetype == PathNodeType.OPEN) {
                  --p_186332_2_;
                  if (i++ >= this.field_186326_b.func_82143_as()) {
                     return null;
                  }

                  pathnodetype = this.func_186331_a(this.field_186326_b, p_186332_1_, p_186332_2_, p_186332_3_);
                  f = this.field_186326_b.func_184643_a(pathnodetype);
                  if (pathnodetype != PathNodeType.OPEN && f >= 0.0F) {
                     pathpoint = this.func_176159_a(p_186332_1_, p_186332_2_, p_186332_3_);
                     pathpoint.field_186287_m = pathnodetype;
                     pathpoint.field_186286_l = Math.max(pathpoint.field_186286_l, f);
                     break;
                  }

                  if (f < 0.0F) {
                     return null;
                  }
               }
            }

            return pathpoint;
         }
      }
   }

   public PathNodeType func_186319_a(IBlockAccess p_186319_1_, int p_186319_2_, int p_186319_3_, int p_186319_4_, EntityLiving p_186319_5_, int p_186319_6_, int p_186319_7_, int p_186319_8_, boolean p_186319_9_, boolean p_186319_10_) {
      EnumSet<PathNodeType> enumset = EnumSet.<PathNodeType>noneOf(PathNodeType.class);
      PathNodeType pathnodetype = PathNodeType.BLOCKED;
      double d0 = (double)p_186319_5_.field_70130_N / 2.0D;
      BlockPos blockpos = new BlockPos(p_186319_5_);
      pathnodetype = this.func_193577_a(p_186319_1_, p_186319_2_, p_186319_3_, p_186319_4_, p_186319_6_, p_186319_7_, p_186319_8_, p_186319_9_, p_186319_10_, enumset, pathnodetype, blockpos);
      if (enumset.contains(PathNodeType.FENCE)) {
         return PathNodeType.FENCE;
      } else {
         PathNodeType pathnodetype1 = PathNodeType.BLOCKED;

         for(PathNodeType pathnodetype2 : enumset) {
            if (p_186319_5_.func_184643_a(pathnodetype2) < 0.0F) {
               return pathnodetype2;
            }

            if (p_186319_5_.func_184643_a(pathnodetype2) >= p_186319_5_.func_184643_a(pathnodetype1)) {
               pathnodetype1 = pathnodetype2;
            }
         }

         if (pathnodetype == PathNodeType.OPEN && p_186319_5_.func_184643_a(pathnodetype1) == 0.0F) {
            return PathNodeType.OPEN;
         } else {
            return pathnodetype1;
         }
      }
   }

   public PathNodeType func_193577_a(IBlockAccess p_193577_1_, int p_193577_2_, int p_193577_3_, int p_193577_4_, int p_193577_5_, int p_193577_6_, int p_193577_7_, boolean p_193577_8_, boolean p_193577_9_, EnumSet<PathNodeType> p_193577_10_, PathNodeType p_193577_11_, BlockPos p_193577_12_) {
      for(int i = 0; i < p_193577_5_; ++i) {
         for(int j = 0; j < p_193577_6_; ++j) {
            for(int k = 0; k < p_193577_7_; ++k) {
               int l = i + p_193577_2_;
               int i1 = j + p_193577_3_;
               int j1 = k + p_193577_4_;
               PathNodeType pathnodetype = this.func_186330_a(p_193577_1_, l, i1, j1);
               if (pathnodetype == PathNodeType.DOOR_WOOD_CLOSED && p_193577_8_ && p_193577_9_) {
                  pathnodetype = PathNodeType.WALKABLE;
               }

               if (pathnodetype == PathNodeType.DOOR_OPEN && !p_193577_9_) {
                  pathnodetype = PathNodeType.BLOCKED;
               }

               if (pathnodetype == PathNodeType.RAIL && !(p_193577_1_.func_180495_p(p_193577_12_).func_177230_c() instanceof BlockRailBase) && !(p_193577_1_.func_180495_p(p_193577_12_.func_177977_b()).func_177230_c() instanceof BlockRailBase)) {
                  pathnodetype = PathNodeType.FENCE;
               }

               if (i == 0 && j == 0 && k == 0) {
                  p_193577_11_ = pathnodetype;
               }

               p_193577_10_.add(pathnodetype);
            }
         }
      }

      return p_193577_11_;
   }

   private PathNodeType func_186329_a(EntityLiving p_186329_1_, BlockPos p_186329_2_) {
      return this.func_186331_a(p_186329_1_, p_186329_2_.func_177958_n(), p_186329_2_.func_177956_o(), p_186329_2_.func_177952_p());
   }

   private PathNodeType func_186331_a(EntityLiving p_186331_1_, int p_186331_2_, int p_186331_3_, int p_186331_4_) {
      return this.func_186319_a(this.field_176169_a, p_186331_2_, p_186331_3_, p_186331_4_, p_186331_1_, this.field_176168_c, this.field_176165_d, this.field_176166_e, this.func_186324_d(), this.func_186323_c());
   }

   public PathNodeType func_186330_a(IBlockAccess p_186330_1_, int p_186330_2_, int p_186330_3_, int p_186330_4_) {
      PathNodeType pathnodetype = this.func_189553_b(p_186330_1_, p_186330_2_, p_186330_3_, p_186330_4_);
      if (pathnodetype == PathNodeType.OPEN && p_186330_3_ >= 1) {
         Block block = p_186330_1_.func_180495_p(new BlockPos(p_186330_2_, p_186330_3_ - 1, p_186330_4_)).func_177230_c();
         PathNodeType pathnodetype1 = this.func_189553_b(p_186330_1_, p_186330_2_, p_186330_3_ - 1, p_186330_4_);
         pathnodetype = pathnodetype1 != PathNodeType.WALKABLE && pathnodetype1 != PathNodeType.OPEN && pathnodetype1 != PathNodeType.WATER && pathnodetype1 != PathNodeType.LAVA ? PathNodeType.WALKABLE : PathNodeType.OPEN;
         if (pathnodetype1 == PathNodeType.DAMAGE_FIRE || block == Blocks.field_189877_df) {
            pathnodetype = PathNodeType.DAMAGE_FIRE;
         }

         if (pathnodetype1 == PathNodeType.DAMAGE_CACTUS) {
            pathnodetype = PathNodeType.DAMAGE_CACTUS;
         }
      }

      pathnodetype = this.func_193578_a(p_186330_1_, p_186330_2_, p_186330_3_, p_186330_4_, pathnodetype);
      return pathnodetype;
   }

   public PathNodeType func_193578_a(IBlockAccess p_193578_1_, int p_193578_2_, int p_193578_3_, int p_193578_4_, PathNodeType p_193578_5_) {
      BlockPos.PooledMutableBlockPos blockpos$pooledmutableblockpos = BlockPos.PooledMutableBlockPos.func_185346_s();
      if (p_193578_5_ == PathNodeType.WALKABLE) {
         for(int i = -1; i <= 1; ++i) {
            for(int j = -1; j <= 1; ++j) {
               if (i != 0 || j != 0) {
                  Block block = p_193578_1_.func_180495_p(blockpos$pooledmutableblockpos.func_181079_c(i + p_193578_2_, p_193578_3_, j + p_193578_4_)).func_177230_c();
                  if (block == Blocks.field_150434_aF) {
                     p_193578_5_ = PathNodeType.DANGER_CACTUS;
                  } else if (block == Blocks.field_150480_ab) {
                     p_193578_5_ = PathNodeType.DANGER_FIRE;
                  }
               }
            }
         }
      }

      blockpos$pooledmutableblockpos.func_185344_t();
      return p_193578_5_;
   }

   protected PathNodeType func_189553_b(IBlockAccess p_189553_1_, int p_189553_2_, int p_189553_3_, int p_189553_4_) {
      BlockPos blockpos = new BlockPos(p_189553_2_, p_189553_3_, p_189553_4_);
      IBlockState iblockstate = p_189553_1_.func_180495_p(blockpos);
      Block block = iblockstate.func_177230_c();
      Material material = iblockstate.func_185904_a();
      if (material == Material.field_151579_a) {
         return PathNodeType.OPEN;
      } else if (block != Blocks.field_150415_aT && block != Blocks.field_180400_cw && block != Blocks.field_150392_bi) {
         if (block == Blocks.field_150480_ab) {
            return PathNodeType.DAMAGE_FIRE;
         } else if (block == Blocks.field_150434_aF) {
            return PathNodeType.DAMAGE_CACTUS;
         } else if (block instanceof BlockDoor && material == Material.field_151575_d && !((Boolean)iblockstate.func_177229_b(BlockDoor.field_176519_b)).booleanValue()) {
            return PathNodeType.DOOR_WOOD_CLOSED;
         } else if (block instanceof BlockDoor && material == Material.field_151573_f && !((Boolean)iblockstate.func_177229_b(BlockDoor.field_176519_b)).booleanValue()) {
            return PathNodeType.DOOR_IRON_CLOSED;
         } else if (block instanceof BlockDoor && ((Boolean)iblockstate.func_177229_b(BlockDoor.field_176519_b)).booleanValue()) {
            return PathNodeType.DOOR_OPEN;
         } else if (block instanceof BlockRailBase) {
            return PathNodeType.RAIL;
         } else if (!(block instanceof BlockFence) && !(block instanceof BlockWall) && (!(block instanceof BlockFenceGate) || ((Boolean)iblockstate.func_177229_b(BlockFenceGate.field_176466_a)).booleanValue())) {
            if (material == Material.field_151586_h) {
               return PathNodeType.WATER;
            } else if (material == Material.field_151587_i) {
               return PathNodeType.LAVA;
            } else {
               return block.func_176205_b(p_189553_1_, blockpos) ? PathNodeType.OPEN : PathNodeType.BLOCKED;
            }
         } else {
            return PathNodeType.FENCE;
         }
      } else {
         return PathNodeType.TRAPDOOR;
      }
   }
}
