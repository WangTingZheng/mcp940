package net.minecraft.pathfinding;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class PathNavigateGround extends PathNavigate {
   private boolean field_179694_f;

   public PathNavigateGround(EntityLiving p_i45875_1_, World p_i45875_2_) {
      super(p_i45875_1_, p_i45875_2_);
   }

   protected PathFinder func_179679_a() {
      this.field_179695_a = new WalkNodeProcessor();
      this.field_179695_a.func_186317_a(true);
      return new PathFinder(this.field_179695_a);
   }

   protected boolean func_75485_k() {
      return this.field_75515_a.field_70122_E || this.func_179684_h() && this.func_75506_l() || this.field_75515_a.func_184218_aH();
   }

   protected Vec3d func_75502_i() {
      return new Vec3d(this.field_75515_a.field_70165_t, (double)this.func_179687_p(), this.field_75515_a.field_70161_v);
   }

   public Path func_179680_a(BlockPos p_179680_1_) {
      if (this.field_75513_b.func_180495_p(p_179680_1_).func_185904_a() == Material.field_151579_a) {
         BlockPos blockpos;
         for(blockpos = p_179680_1_.func_177977_b(); blockpos.func_177956_o() > 0 && this.field_75513_b.func_180495_p(blockpos).func_185904_a() == Material.field_151579_a; blockpos = blockpos.func_177977_b()) {
            ;
         }

         if (blockpos.func_177956_o() > 0) {
            return super.func_179680_a(blockpos.func_177984_a());
         }

         while(blockpos.func_177956_o() < this.field_75513_b.func_72800_K() && this.field_75513_b.func_180495_p(blockpos).func_185904_a() == Material.field_151579_a) {
            blockpos = blockpos.func_177984_a();
         }

         p_179680_1_ = blockpos;
      }

      if (!this.field_75513_b.func_180495_p(p_179680_1_).func_185904_a().func_76220_a()) {
         return super.func_179680_a(p_179680_1_);
      } else {
         BlockPos blockpos1;
         for(blockpos1 = p_179680_1_.func_177984_a(); blockpos1.func_177956_o() < this.field_75513_b.func_72800_K() && this.field_75513_b.func_180495_p(blockpos1).func_185904_a().func_76220_a(); blockpos1 = blockpos1.func_177984_a()) {
            ;
         }

         return super.func_179680_a(blockpos1);
      }
   }

   public Path func_75494_a(Entity p_75494_1_) {
      return this.func_179680_a(new BlockPos(p_75494_1_));
   }

   private int func_179687_p() {
      if (this.field_75515_a.func_70090_H() && this.func_179684_h()) {
         int i = (int)this.field_75515_a.func_174813_aQ().field_72338_b;
         Block block = this.field_75513_b.func_180495_p(new BlockPos(MathHelper.func_76128_c(this.field_75515_a.field_70165_t), i, MathHelper.func_76128_c(this.field_75515_a.field_70161_v))).func_177230_c();
         int j = 0;

         while(block == Blocks.field_150358_i || block == Blocks.field_150355_j) {
            ++i;
            block = this.field_75513_b.func_180495_p(new BlockPos(MathHelper.func_76128_c(this.field_75515_a.field_70165_t), i, MathHelper.func_76128_c(this.field_75515_a.field_70161_v))).func_177230_c();
            ++j;
            if (j > 16) {
               return (int)this.field_75515_a.func_174813_aQ().field_72338_b;
            }
         }

         return i;
      } else {
         return (int)(this.field_75515_a.func_174813_aQ().field_72338_b + 0.5D);
      }
   }

   protected void func_75487_m() {
      super.func_75487_m();
      if (this.field_179694_f) {
         if (this.field_75513_b.func_175678_i(new BlockPos(MathHelper.func_76128_c(this.field_75515_a.field_70165_t), (int)(this.field_75515_a.func_174813_aQ().field_72338_b + 0.5D), MathHelper.func_76128_c(this.field_75515_a.field_70161_v)))) {
            return;
         }

         for(int i = 0; i < this.field_75514_c.func_75874_d(); ++i) {
            PathPoint pathpoint = this.field_75514_c.func_75877_a(i);
            if (this.field_75513_b.func_175678_i(new BlockPos(pathpoint.field_75839_a, pathpoint.field_75837_b, pathpoint.field_75838_c))) {
               this.field_75514_c.func_75871_b(i - 1);
               return;
            }
         }
      }

   }

   protected boolean func_75493_a(Vec3d p_75493_1_, Vec3d p_75493_2_, int p_75493_3_, int p_75493_4_, int p_75493_5_) {
      int i = MathHelper.func_76128_c(p_75493_1_.field_72450_a);
      int j = MathHelper.func_76128_c(p_75493_1_.field_72449_c);
      double d0 = p_75493_2_.field_72450_a - p_75493_1_.field_72450_a;
      double d1 = p_75493_2_.field_72449_c - p_75493_1_.field_72449_c;
      double d2 = d0 * d0 + d1 * d1;
      if (d2 < 1.0E-8D) {
         return false;
      } else {
         double d3 = 1.0D / Math.sqrt(d2);
         d0 = d0 * d3;
         d1 = d1 * d3;
         p_75493_3_ = p_75493_3_ + 2;
         p_75493_5_ = p_75493_5_ + 2;
         if (!this.func_179683_a(i, (int)p_75493_1_.field_72448_b, j, p_75493_3_, p_75493_4_, p_75493_5_, p_75493_1_, d0, d1)) {
            return false;
         } else {
            p_75493_3_ = p_75493_3_ - 2;
            p_75493_5_ = p_75493_5_ - 2;
            double d4 = 1.0D / Math.abs(d0);
            double d5 = 1.0D / Math.abs(d1);
            double d6 = (double)i - p_75493_1_.field_72450_a;
            double d7 = (double)j - p_75493_1_.field_72449_c;
            if (d0 >= 0.0D) {
               ++d6;
            }

            if (d1 >= 0.0D) {
               ++d7;
            }

            d6 = d6 / d0;
            d7 = d7 / d1;
            int k = d0 < 0.0D ? -1 : 1;
            int l = d1 < 0.0D ? -1 : 1;
            int i1 = MathHelper.func_76128_c(p_75493_2_.field_72450_a);
            int j1 = MathHelper.func_76128_c(p_75493_2_.field_72449_c);
            int k1 = i1 - i;
            int l1 = j1 - j;

            while(k1 * k > 0 || l1 * l > 0) {
               if (d6 < d7) {
                  d6 += d4;
                  i += k;
                  k1 = i1 - i;
               } else {
                  d7 += d5;
                  j += l;
                  l1 = j1 - j;
               }

               if (!this.func_179683_a(i, (int)p_75493_1_.field_72448_b, j, p_75493_3_, p_75493_4_, p_75493_5_, p_75493_1_, d0, d1)) {
                  return false;
               }
            }

            return true;
         }
      }
   }

   private boolean func_179683_a(int p_179683_1_, int p_179683_2_, int p_179683_3_, int p_179683_4_, int p_179683_5_, int p_179683_6_, Vec3d p_179683_7_, double p_179683_8_, double p_179683_10_) {
      int i = p_179683_1_ - p_179683_4_ / 2;
      int j = p_179683_3_ - p_179683_6_ / 2;
      if (!this.func_179692_b(i, p_179683_2_, j, p_179683_4_, p_179683_5_, p_179683_6_, p_179683_7_, p_179683_8_, p_179683_10_)) {
         return false;
      } else {
         for(int k = i; k < i + p_179683_4_; ++k) {
            for(int l = j; l < j + p_179683_6_; ++l) {
               double d0 = (double)k + 0.5D - p_179683_7_.field_72450_a;
               double d1 = (double)l + 0.5D - p_179683_7_.field_72449_c;
               if (d0 * p_179683_8_ + d1 * p_179683_10_ >= 0.0D) {
                  PathNodeType pathnodetype = this.field_179695_a.func_186319_a(this.field_75513_b, k, p_179683_2_ - 1, l, this.field_75515_a, p_179683_4_, p_179683_5_, p_179683_6_, true, true);
                  if (pathnodetype == PathNodeType.WATER) {
                     return false;
                  }

                  if (pathnodetype == PathNodeType.LAVA) {
                     return false;
                  }

                  if (pathnodetype == PathNodeType.OPEN) {
                     return false;
                  }

                  pathnodetype = this.field_179695_a.func_186319_a(this.field_75513_b, k, p_179683_2_, l, this.field_75515_a, p_179683_4_, p_179683_5_, p_179683_6_, true, true);
                  float f = this.field_75515_a.func_184643_a(pathnodetype);
                  if (f < 0.0F || f >= 8.0F) {
                     return false;
                  }

                  if (pathnodetype == PathNodeType.DAMAGE_FIRE || pathnodetype == PathNodeType.DANGER_FIRE || pathnodetype == PathNodeType.DAMAGE_OTHER) {
                     return false;
                  }
               }
            }
         }

         return true;
      }
   }

   private boolean func_179692_b(int p_179692_1_, int p_179692_2_, int p_179692_3_, int p_179692_4_, int p_179692_5_, int p_179692_6_, Vec3d p_179692_7_, double p_179692_8_, double p_179692_10_) {
      for(BlockPos blockpos : BlockPos.func_177980_a(new BlockPos(p_179692_1_, p_179692_2_, p_179692_3_), new BlockPos(p_179692_1_ + p_179692_4_ - 1, p_179692_2_ + p_179692_5_ - 1, p_179692_3_ + p_179692_6_ - 1))) {
         double d0 = (double)blockpos.func_177958_n() + 0.5D - p_179692_7_.field_72450_a;
         double d1 = (double)blockpos.func_177952_p() + 0.5D - p_179692_7_.field_72449_c;
         if (d0 * p_179692_8_ + d1 * p_179692_10_ >= 0.0D) {
            Block block = this.field_75513_b.func_180495_p(blockpos).func_177230_c();
            if (!block.func_176205_b(this.field_75513_b, blockpos)) {
               return false;
            }
         }
      }

      return true;
   }

   public void func_179688_b(boolean p_179688_1_) {
      this.field_179695_a.func_186321_b(p_179688_1_);
   }

   public void func_179691_c(boolean p_179691_1_) {
      this.field_179695_a.func_186317_a(p_179691_1_);
   }

   public boolean func_179686_g() {
      return this.field_179695_a.func_186323_c();
   }

   public void func_179693_d(boolean p_179693_1_) {
      this.field_179695_a.func_186316_c(p_179693_1_);
   }

   public boolean func_179684_h() {
      return this.field_179695_a.func_186322_e();
   }

   public void func_179685_e(boolean p_179685_1_) {
      this.field_179694_f = p_179685_1_;
   }
}
