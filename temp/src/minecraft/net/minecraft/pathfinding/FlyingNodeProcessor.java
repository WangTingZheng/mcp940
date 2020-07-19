package net.minecraft.pathfinding;

import com.google.common.collect.Sets;
import java.util.EnumSet;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLiving;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;

public class FlyingNodeProcessor extends WalkNodeProcessor {
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
      } else {
         i = MathHelper.func_76128_c(this.field_186326_b.func_174813_aQ().field_72338_b + 0.5D);
      }

      BlockPos blockpos1 = new BlockPos(this.field_186326_b);
      PathNodeType pathnodetype1 = this.func_192558_a(this.field_186326_b, blockpos1.func_177958_n(), i, blockpos1.func_177952_p());
      if (this.field_186326_b.func_184643_a(pathnodetype1) < 0.0F) {
         Set<BlockPos> set = Sets.<BlockPos>newHashSet();
         set.add(new BlockPos(this.field_186326_b.func_174813_aQ().field_72340_a, (double)i, this.field_186326_b.func_174813_aQ().field_72339_c));
         set.add(new BlockPos(this.field_186326_b.func_174813_aQ().field_72340_a, (double)i, this.field_186326_b.func_174813_aQ().field_72334_f));
         set.add(new BlockPos(this.field_186326_b.func_174813_aQ().field_72336_d, (double)i, this.field_186326_b.func_174813_aQ().field_72339_c));
         set.add(new BlockPos(this.field_186326_b.func_174813_aQ().field_72336_d, (double)i, this.field_186326_b.func_174813_aQ().field_72334_f));

         for(BlockPos blockpos : set) {
            PathNodeType pathnodetype = this.func_192559_a(this.field_186326_b, blockpos);
            if (this.field_186326_b.func_184643_a(pathnodetype) >= 0.0F) {
               return super.func_176159_a(blockpos.func_177958_n(), blockpos.func_177956_o(), blockpos.func_177952_p());
            }
         }
      }

      return super.func_176159_a(blockpos1.func_177958_n(), i, blockpos1.func_177952_p());
   }

   public PathPoint func_186325_a(double p_186325_1_, double p_186325_3_, double p_186325_5_) {
      return super.func_176159_a(MathHelper.func_76128_c(p_186325_1_), MathHelper.func_76128_c(p_186325_3_), MathHelper.func_76128_c(p_186325_5_));
   }

   public int func_186320_a(PathPoint[] p_186320_1_, PathPoint p_186320_2_, PathPoint p_186320_3_, float p_186320_4_) {
      int i = 0;
      PathPoint pathpoint = this.func_176159_a(p_186320_2_.field_75839_a, p_186320_2_.field_75837_b, p_186320_2_.field_75838_c + 1);
      PathPoint pathpoint1 = this.func_176159_a(p_186320_2_.field_75839_a - 1, p_186320_2_.field_75837_b, p_186320_2_.field_75838_c);
      PathPoint pathpoint2 = this.func_176159_a(p_186320_2_.field_75839_a + 1, p_186320_2_.field_75837_b, p_186320_2_.field_75838_c);
      PathPoint pathpoint3 = this.func_176159_a(p_186320_2_.field_75839_a, p_186320_2_.field_75837_b, p_186320_2_.field_75838_c - 1);
      PathPoint pathpoint4 = this.func_176159_a(p_186320_2_.field_75839_a, p_186320_2_.field_75837_b + 1, p_186320_2_.field_75838_c);
      PathPoint pathpoint5 = this.func_176159_a(p_186320_2_.field_75839_a, p_186320_2_.field_75837_b - 1, p_186320_2_.field_75838_c);
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

      if (pathpoint4 != null && !pathpoint4.field_75842_i && pathpoint4.func_75829_a(p_186320_3_) < p_186320_4_) {
         p_186320_1_[i++] = pathpoint4;
      }

      if (pathpoint5 != null && !pathpoint5.field_75842_i && pathpoint5.func_75829_a(p_186320_3_) < p_186320_4_) {
         p_186320_1_[i++] = pathpoint5;
      }

      boolean flag = pathpoint3 == null || pathpoint3.field_186286_l != 0.0F;
      boolean flag1 = pathpoint == null || pathpoint.field_186286_l != 0.0F;
      boolean flag2 = pathpoint2 == null || pathpoint2.field_186286_l != 0.0F;
      boolean flag3 = pathpoint1 == null || pathpoint1.field_186286_l != 0.0F;
      boolean flag4 = pathpoint4 == null || pathpoint4.field_186286_l != 0.0F;
      boolean flag5 = pathpoint5 == null || pathpoint5.field_186286_l != 0.0F;
      if (flag && flag3) {
         PathPoint pathpoint6 = this.func_176159_a(p_186320_2_.field_75839_a - 1, p_186320_2_.field_75837_b, p_186320_2_.field_75838_c - 1);
         if (pathpoint6 != null && !pathpoint6.field_75842_i && pathpoint6.func_75829_a(p_186320_3_) < p_186320_4_) {
            p_186320_1_[i++] = pathpoint6;
         }
      }

      if (flag && flag2) {
         PathPoint pathpoint7 = this.func_176159_a(p_186320_2_.field_75839_a + 1, p_186320_2_.field_75837_b, p_186320_2_.field_75838_c - 1);
         if (pathpoint7 != null && !pathpoint7.field_75842_i && pathpoint7.func_75829_a(p_186320_3_) < p_186320_4_) {
            p_186320_1_[i++] = pathpoint7;
         }
      }

      if (flag1 && flag3) {
         PathPoint pathpoint8 = this.func_176159_a(p_186320_2_.field_75839_a - 1, p_186320_2_.field_75837_b, p_186320_2_.field_75838_c + 1);
         if (pathpoint8 != null && !pathpoint8.field_75842_i && pathpoint8.func_75829_a(p_186320_3_) < p_186320_4_) {
            p_186320_1_[i++] = pathpoint8;
         }
      }

      if (flag1 && flag2) {
         PathPoint pathpoint9 = this.func_176159_a(p_186320_2_.field_75839_a + 1, p_186320_2_.field_75837_b, p_186320_2_.field_75838_c + 1);
         if (pathpoint9 != null && !pathpoint9.field_75842_i && pathpoint9.func_75829_a(p_186320_3_) < p_186320_4_) {
            p_186320_1_[i++] = pathpoint9;
         }
      }

      if (flag && flag4) {
         PathPoint pathpoint10 = this.func_176159_a(p_186320_2_.field_75839_a, p_186320_2_.field_75837_b + 1, p_186320_2_.field_75838_c - 1);
         if (pathpoint10 != null && !pathpoint10.field_75842_i && pathpoint10.func_75829_a(p_186320_3_) < p_186320_4_) {
            p_186320_1_[i++] = pathpoint10;
         }
      }

      if (flag1 && flag4) {
         PathPoint pathpoint11 = this.func_176159_a(p_186320_2_.field_75839_a, p_186320_2_.field_75837_b + 1, p_186320_2_.field_75838_c + 1);
         if (pathpoint11 != null && !pathpoint11.field_75842_i && pathpoint11.func_75829_a(p_186320_3_) < p_186320_4_) {
            p_186320_1_[i++] = pathpoint11;
         }
      }

      if (flag2 && flag4) {
         PathPoint pathpoint12 = this.func_176159_a(p_186320_2_.field_75839_a + 1, p_186320_2_.field_75837_b + 1, p_186320_2_.field_75838_c);
         if (pathpoint12 != null && !pathpoint12.field_75842_i && pathpoint12.func_75829_a(p_186320_3_) < p_186320_4_) {
            p_186320_1_[i++] = pathpoint12;
         }
      }

      if (flag3 && flag4) {
         PathPoint pathpoint13 = this.func_176159_a(p_186320_2_.field_75839_a - 1, p_186320_2_.field_75837_b + 1, p_186320_2_.field_75838_c);
         if (pathpoint13 != null && !pathpoint13.field_75842_i && pathpoint13.func_75829_a(p_186320_3_) < p_186320_4_) {
            p_186320_1_[i++] = pathpoint13;
         }
      }

      if (flag && flag5) {
         PathPoint pathpoint14 = this.func_176159_a(p_186320_2_.field_75839_a, p_186320_2_.field_75837_b - 1, p_186320_2_.field_75838_c - 1);
         if (pathpoint14 != null && !pathpoint14.field_75842_i && pathpoint14.func_75829_a(p_186320_3_) < p_186320_4_) {
            p_186320_1_[i++] = pathpoint14;
         }
      }

      if (flag1 && flag5) {
         PathPoint pathpoint15 = this.func_176159_a(p_186320_2_.field_75839_a, p_186320_2_.field_75837_b - 1, p_186320_2_.field_75838_c + 1);
         if (pathpoint15 != null && !pathpoint15.field_75842_i && pathpoint15.func_75829_a(p_186320_3_) < p_186320_4_) {
            p_186320_1_[i++] = pathpoint15;
         }
      }

      if (flag2 && flag5) {
         PathPoint pathpoint16 = this.func_176159_a(p_186320_2_.field_75839_a + 1, p_186320_2_.field_75837_b - 1, p_186320_2_.field_75838_c);
         if (pathpoint16 != null && !pathpoint16.field_75842_i && pathpoint16.func_75829_a(p_186320_3_) < p_186320_4_) {
            p_186320_1_[i++] = pathpoint16;
         }
      }

      if (flag3 && flag5) {
         PathPoint pathpoint17 = this.func_176159_a(p_186320_2_.field_75839_a - 1, p_186320_2_.field_75837_b - 1, p_186320_2_.field_75838_c);
         if (pathpoint17 != null && !pathpoint17.field_75842_i && pathpoint17.func_75829_a(p_186320_3_) < p_186320_4_) {
            p_186320_1_[i++] = pathpoint17;
         }
      }

      return i;
   }

   @Nullable
   protected PathPoint func_176159_a(int p_176159_1_, int p_176159_2_, int p_176159_3_) {
      PathPoint pathpoint = null;
      PathNodeType pathnodetype = this.func_192558_a(this.field_186326_b, p_176159_1_, p_176159_2_, p_176159_3_);
      float f = this.field_186326_b.func_184643_a(pathnodetype);
      if (f >= 0.0F) {
         pathpoint = super.func_176159_a(p_176159_1_, p_176159_2_, p_176159_3_);
         pathpoint.field_186287_m = pathnodetype;
         pathpoint.field_186286_l = Math.max(pathpoint.field_186286_l, f);
         if (pathnodetype == PathNodeType.WALKABLE) {
            ++pathpoint.field_186286_l;
         }
      }

      return pathnodetype != PathNodeType.OPEN && pathnodetype != PathNodeType.WALKABLE ? pathpoint : pathpoint;
   }

   public PathNodeType func_186319_a(IBlockAccess p_186319_1_, int p_186319_2_, int p_186319_3_, int p_186319_4_, EntityLiving p_186319_5_, int p_186319_6_, int p_186319_7_, int p_186319_8_, boolean p_186319_9_, boolean p_186319_10_) {
      EnumSet<PathNodeType> enumset = EnumSet.<PathNodeType>noneOf(PathNodeType.class);
      PathNodeType pathnodetype = PathNodeType.BLOCKED;
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

   public PathNodeType func_186330_a(IBlockAccess p_186330_1_, int p_186330_2_, int p_186330_3_, int p_186330_4_) {
      PathNodeType pathnodetype = this.func_189553_b(p_186330_1_, p_186330_2_, p_186330_3_, p_186330_4_);
      if (pathnodetype == PathNodeType.OPEN && p_186330_3_ >= 1) {
         Block block = p_186330_1_.func_180495_p(new BlockPos(p_186330_2_, p_186330_3_ - 1, p_186330_4_)).func_177230_c();
         PathNodeType pathnodetype1 = this.func_189553_b(p_186330_1_, p_186330_2_, p_186330_3_ - 1, p_186330_4_);
         if (pathnodetype1 != PathNodeType.DAMAGE_FIRE && block != Blocks.field_189877_df && pathnodetype1 != PathNodeType.LAVA) {
            if (pathnodetype1 == PathNodeType.DAMAGE_CACTUS) {
               pathnodetype = PathNodeType.DAMAGE_CACTUS;
            } else {
               pathnodetype = pathnodetype1 != PathNodeType.WALKABLE && pathnodetype1 != PathNodeType.OPEN && pathnodetype1 != PathNodeType.WATER ? PathNodeType.WALKABLE : PathNodeType.OPEN;
            }
         } else {
            pathnodetype = PathNodeType.DAMAGE_FIRE;
         }
      }

      pathnodetype = this.func_193578_a(p_186330_1_, p_186330_2_, p_186330_3_, p_186330_4_, pathnodetype);
      return pathnodetype;
   }

   private PathNodeType func_192559_a(EntityLiving p_192559_1_, BlockPos p_192559_2_) {
      return this.func_192558_a(p_192559_1_, p_192559_2_.func_177958_n(), p_192559_2_.func_177956_o(), p_192559_2_.func_177952_p());
   }

   private PathNodeType func_192558_a(EntityLiving p_192558_1_, int p_192558_2_, int p_192558_3_, int p_192558_4_) {
      return this.func_186319_a(this.field_176169_a, p_192558_2_, p_192558_3_, p_192558_4_, p_192558_1_, this.field_176168_c, this.field_176165_d, this.field_176166_e, this.func_186324_d(), this.func_186323_c());
   }
}
