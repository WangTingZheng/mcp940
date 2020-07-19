package net.minecraft.pathfinding;

import com.google.common.collect.Sets;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class PathFinder {
   private final PathHeap field_75866_b = new PathHeap();
   private final Set<PathPoint> field_186337_b = Sets.<PathPoint>newHashSet();
   private final PathPoint[] field_75864_d = new PathPoint[32];
   private final NodeProcessor field_176190_c;

   public PathFinder(NodeProcessor p_i46652_1_) {
      this.field_176190_c = p_i46652_1_;
   }

   @Nullable
   public Path func_186333_a(IBlockAccess p_186333_1_, EntityLiving p_186333_2_, Entity p_186333_3_, float p_186333_4_) {
      return this.func_186334_a(p_186333_1_, p_186333_2_, p_186333_3_.field_70165_t, p_186333_3_.func_174813_aQ().field_72338_b, p_186333_3_.field_70161_v, p_186333_4_);
   }

   @Nullable
   public Path func_186336_a(IBlockAccess p_186336_1_, EntityLiving p_186336_2_, BlockPos p_186336_3_, float p_186336_4_) {
      return this.func_186334_a(p_186336_1_, p_186336_2_, (double)((float)p_186336_3_.func_177958_n() + 0.5F), (double)((float)p_186336_3_.func_177956_o() + 0.5F), (double)((float)p_186336_3_.func_177952_p() + 0.5F), p_186336_4_);
   }

   @Nullable
   private Path func_186334_a(IBlockAccess p_186334_1_, EntityLiving p_186334_2_, double p_186334_3_, double p_186334_5_, double p_186334_7_, float p_186334_9_) {
      this.field_75866_b.func_75848_a();
      this.field_176190_c.func_186315_a(p_186334_1_, p_186334_2_);
      PathPoint pathpoint = this.field_176190_c.func_186318_b();
      PathPoint pathpoint1 = this.field_176190_c.func_186325_a(p_186334_3_, p_186334_5_, p_186334_7_);
      Path path = this.func_186335_a(pathpoint, pathpoint1, p_186334_9_);
      this.field_176190_c.func_176163_a();
      return path;
   }

   @Nullable
   private Path func_186335_a(PathPoint p_186335_1_, PathPoint p_186335_2_, float p_186335_3_) {
      p_186335_1_.field_75836_e = 0.0F;
      p_186335_1_.field_75833_f = p_186335_1_.func_186281_c(p_186335_2_);
      p_186335_1_.field_75834_g = p_186335_1_.field_75833_f;
      this.field_75866_b.func_75848_a();
      this.field_186337_b.clear();
      this.field_75866_b.func_75849_a(p_186335_1_);
      PathPoint pathpoint = p_186335_1_;
      int i = 0;

      while(!this.field_75866_b.func_75845_e()) {
         ++i;
         if (i >= 200) {
            break;
         }

         PathPoint pathpoint1 = this.field_75866_b.func_75844_c();
         if (pathpoint1.equals(p_186335_2_)) {
            pathpoint = p_186335_2_;
            break;
         }

         if (pathpoint1.func_186281_c(p_186335_2_) < pathpoint.func_186281_c(p_186335_2_)) {
            pathpoint = pathpoint1;
         }

         pathpoint1.field_75842_i = true;
         int j = this.field_176190_c.func_186320_a(this.field_75864_d, pathpoint1, p_186335_2_, p_186335_3_);

         for(int k = 0; k < j; ++k) {
            PathPoint pathpoint2 = this.field_75864_d[k];
            float f = pathpoint1.func_186281_c(pathpoint2);
            pathpoint2.field_186284_j = pathpoint1.field_186284_j + f;
            pathpoint2.field_186285_k = f + pathpoint2.field_186286_l;
            float f1 = pathpoint1.field_75836_e + pathpoint2.field_186285_k;
            if (pathpoint2.field_186284_j < p_186335_3_ && (!pathpoint2.func_75831_a() || f1 < pathpoint2.field_75836_e)) {
               pathpoint2.field_75841_h = pathpoint1;
               pathpoint2.field_75836_e = f1;
               pathpoint2.field_75833_f = pathpoint2.func_186281_c(p_186335_2_) + pathpoint2.field_186286_l;
               if (pathpoint2.func_75831_a()) {
                  this.field_75866_b.func_75850_a(pathpoint2, pathpoint2.field_75836_e + pathpoint2.field_75833_f);
               } else {
                  pathpoint2.field_75834_g = pathpoint2.field_75836_e + pathpoint2.field_75833_f;
                  this.field_75866_b.func_75849_a(pathpoint2);
               }
            }
         }
      }

      if (pathpoint == p_186335_1_) {
         return null;
      } else {
         Path path = this.func_75853_a(p_186335_1_, pathpoint);
         return path;
      }
   }

   private Path func_75853_a(PathPoint p_75853_1_, PathPoint p_75853_2_) {
      int i = 1;

      for(PathPoint pathpoint = p_75853_2_; pathpoint.field_75841_h != null; pathpoint = pathpoint.field_75841_h) {
         ++i;
      }

      PathPoint[] apathpoint = new PathPoint[i];
      PathPoint pathpoint1 = p_75853_2_;
      --i;

      for(apathpoint[i] = p_75853_2_; pathpoint1.field_75841_h != null; apathpoint[i] = pathpoint1) {
         pathpoint1 = pathpoint1.field_75841_h;
         --i;
      }

      return new Path(apathpoint);
   }
}
