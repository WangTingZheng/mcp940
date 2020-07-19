package net.minecraft.entity.ai;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityCreature;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class RandomPositionGenerator {
   private static Vec3d field_75465_a = Vec3d.field_186680_a;

   @Nullable
   public static Vec3d func_75463_a(EntityCreature p_75463_0_, int p_75463_1_, int p_75463_2_) {
      return func_75462_c(p_75463_0_, p_75463_1_, p_75463_2_, (Vec3d)null);
   }

   @Nullable
   public static Vec3d func_191377_b(EntityCreature p_191377_0_, int p_191377_1_, int p_191377_2_) {
      return func_191379_a(p_191377_0_, p_191377_1_, p_191377_2_, (Vec3d)null, false);
   }

   @Nullable
   public static Vec3d func_75464_a(EntityCreature p_75464_0_, int p_75464_1_, int p_75464_2_, Vec3d p_75464_3_) {
      field_75465_a = p_75464_3_.func_178786_a(p_75464_0_.field_70165_t, p_75464_0_.field_70163_u, p_75464_0_.field_70161_v);
      return func_75462_c(p_75464_0_, p_75464_1_, p_75464_2_, field_75465_a);
   }

   @Nullable
   public static Vec3d func_75461_b(EntityCreature p_75461_0_, int p_75461_1_, int p_75461_2_, Vec3d p_75461_3_) {
      field_75465_a = (new Vec3d(p_75461_0_.field_70165_t, p_75461_0_.field_70163_u, p_75461_0_.field_70161_v)).func_178788_d(p_75461_3_);
      return func_75462_c(p_75461_0_, p_75461_1_, p_75461_2_, field_75465_a);
   }

   @Nullable
   private static Vec3d func_75462_c(EntityCreature p_75462_0_, int p_75462_1_, int p_75462_2_, @Nullable Vec3d p_75462_3_) {
      return func_191379_a(p_75462_0_, p_75462_1_, p_75462_2_, p_75462_3_, true);
   }

   @Nullable
   private static Vec3d func_191379_a(EntityCreature p_191379_0_, int p_191379_1_, int p_191379_2_, @Nullable Vec3d p_191379_3_, boolean p_191379_4_) {
      PathNavigate pathnavigate = p_191379_0_.func_70661_as();
      Random random = p_191379_0_.func_70681_au();
      boolean flag;
      if (p_191379_0_.func_110175_bO()) {
         double d0 = p_191379_0_.func_180486_cf().func_177954_c((double)MathHelper.func_76128_c(p_191379_0_.field_70165_t), (double)MathHelper.func_76128_c(p_191379_0_.field_70163_u), (double)MathHelper.func_76128_c(p_191379_0_.field_70161_v)) + 4.0D;
         double d1 = (double)(p_191379_0_.func_110174_bM() + (float)p_191379_1_);
         flag = d0 < d1 * d1;
      } else {
         flag = false;
      }

      boolean flag1 = false;
      float f = -99999.0F;
      int k1 = 0;
      int i = 0;
      int j = 0;

      for(int k = 0; k < 10; ++k) {
         int l = random.nextInt(2 * p_191379_1_ + 1) - p_191379_1_;
         int i1 = random.nextInt(2 * p_191379_2_ + 1) - p_191379_2_;
         int j1 = random.nextInt(2 * p_191379_1_ + 1) - p_191379_1_;
         if (p_191379_3_ == null || (double)l * p_191379_3_.field_72450_a + (double)j1 * p_191379_3_.field_72449_c >= 0.0D) {
            if (p_191379_0_.func_110175_bO() && p_191379_1_ > 1) {
               BlockPos blockpos = p_191379_0_.func_180486_cf();
               if (p_191379_0_.field_70165_t > (double)blockpos.func_177958_n()) {
                  l -= random.nextInt(p_191379_1_ / 2);
               } else {
                  l += random.nextInt(p_191379_1_ / 2);
               }

               if (p_191379_0_.field_70161_v > (double)blockpos.func_177952_p()) {
                  j1 -= random.nextInt(p_191379_1_ / 2);
               } else {
                  j1 += random.nextInt(p_191379_1_ / 2);
               }
            }

            BlockPos blockpos1 = new BlockPos((double)l + p_191379_0_.field_70165_t, (double)i1 + p_191379_0_.field_70163_u, (double)j1 + p_191379_0_.field_70161_v);
            if ((!flag || p_191379_0_.func_180485_d(blockpos1)) && pathnavigate.func_188555_b(blockpos1)) {
               if (!p_191379_4_) {
                  blockpos1 = func_191378_a(blockpos1, p_191379_0_);
                  if (func_191380_b(blockpos1, p_191379_0_)) {
                     continue;
                  }
               }

               float f1 = p_191379_0_.func_180484_a(blockpos1);
               if (f1 > f) {
                  f = f1;
                  k1 = l;
                  i = i1;
                  j = j1;
                  flag1 = true;
               }
            }
         }
      }

      if (flag1) {
         return new Vec3d((double)k1 + p_191379_0_.field_70165_t, (double)i + p_191379_0_.field_70163_u, (double)j + p_191379_0_.field_70161_v);
      } else {
         return null;
      }
   }

   private static BlockPos func_191378_a(BlockPos p_191378_0_, EntityCreature p_191378_1_) {
      if (!p_191378_1_.field_70170_p.func_180495_p(p_191378_0_).func_185904_a().func_76220_a()) {
         return p_191378_0_;
      } else {
         BlockPos blockpos;
         for(blockpos = p_191378_0_.func_177984_a(); blockpos.func_177956_o() < p_191378_1_.field_70170_p.func_72800_K() && p_191378_1_.field_70170_p.func_180495_p(blockpos).func_185904_a().func_76220_a(); blockpos = blockpos.func_177984_a()) {
            ;
         }

         return blockpos;
      }
   }

   private static boolean func_191380_b(BlockPos p_191380_0_, EntityCreature p_191380_1_) {
      return p_191380_1_.field_70170_p.func_180495_p(p_191380_0_).func_185904_a() == Material.field_151586_h;
   }
}
