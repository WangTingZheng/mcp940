package net.minecraft.world.gen.feature;

import com.google.common.base.Predicate;
import java.util.Random;
import net.minecraft.block.BlockStone;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class WorldGenMinable extends WorldGenerator {
   private final IBlockState field_175920_a;
   private final int field_76541_b;
   private final Predicate<IBlockState> field_175919_c;

   public WorldGenMinable(IBlockState p_i45630_1_, int p_i45630_2_) {
      this(p_i45630_1_, p_i45630_2_, new WorldGenMinable.StonePredicate());
   }

   public WorldGenMinable(IBlockState p_i45631_1_, int p_i45631_2_, Predicate<IBlockState> p_i45631_3_) {
      this.field_175920_a = p_i45631_1_;
      this.field_76541_b = p_i45631_2_;
      this.field_175919_c = p_i45631_3_;
   }

   public boolean func_180709_b(World p_180709_1_, Random p_180709_2_, BlockPos p_180709_3_) {
      float f = p_180709_2_.nextFloat() * 3.1415927F;
      double d0 = (double)((float)(p_180709_3_.func_177958_n() + 8) + MathHelper.func_76126_a(f) * (float)this.field_76541_b / 8.0F);
      double d1 = (double)((float)(p_180709_3_.func_177958_n() + 8) - MathHelper.func_76126_a(f) * (float)this.field_76541_b / 8.0F);
      double d2 = (double)((float)(p_180709_3_.func_177952_p() + 8) + MathHelper.func_76134_b(f) * (float)this.field_76541_b / 8.0F);
      double d3 = (double)((float)(p_180709_3_.func_177952_p() + 8) - MathHelper.func_76134_b(f) * (float)this.field_76541_b / 8.0F);
      double d4 = (double)(p_180709_3_.func_177956_o() + p_180709_2_.nextInt(3) - 2);
      double d5 = (double)(p_180709_3_.func_177956_o() + p_180709_2_.nextInt(3) - 2);

      for(int i = 0; i < this.field_76541_b; ++i) {
         float f1 = (float)i / (float)this.field_76541_b;
         double d6 = d0 + (d1 - d0) * (double)f1;
         double d7 = d4 + (d5 - d4) * (double)f1;
         double d8 = d2 + (d3 - d2) * (double)f1;
         double d9 = p_180709_2_.nextDouble() * (double)this.field_76541_b / 16.0D;
         double d10 = (double)(MathHelper.func_76126_a(3.1415927F * f1) + 1.0F) * d9 + 1.0D;
         double d11 = (double)(MathHelper.func_76126_a(3.1415927F * f1) + 1.0F) * d9 + 1.0D;
         int j = MathHelper.func_76128_c(d6 - d10 / 2.0D);
         int k = MathHelper.func_76128_c(d7 - d11 / 2.0D);
         int l = MathHelper.func_76128_c(d8 - d10 / 2.0D);
         int i1 = MathHelper.func_76128_c(d6 + d10 / 2.0D);
         int j1 = MathHelper.func_76128_c(d7 + d11 / 2.0D);
         int k1 = MathHelper.func_76128_c(d8 + d10 / 2.0D);

         for(int l1 = j; l1 <= i1; ++l1) {
            double d12 = ((double)l1 + 0.5D - d6) / (d10 / 2.0D);
            if (d12 * d12 < 1.0D) {
               for(int i2 = k; i2 <= j1; ++i2) {
                  double d13 = ((double)i2 + 0.5D - d7) / (d11 / 2.0D);
                  if (d12 * d12 + d13 * d13 < 1.0D) {
                     for(int j2 = l; j2 <= k1; ++j2) {
                        double d14 = ((double)j2 + 0.5D - d8) / (d10 / 2.0D);
                        if (d12 * d12 + d13 * d13 + d14 * d14 < 1.0D) {
                           BlockPos blockpos = new BlockPos(l1, i2, j2);
                           if (this.field_175919_c.apply(p_180709_1_.func_180495_p(blockpos))) {
                              p_180709_1_.func_180501_a(blockpos, this.field_175920_a, 2);
                           }
                        }
                     }
                  }
               }
            }
         }
      }

      return true;
   }

   static class StonePredicate implements Predicate<IBlockState> {
      private StonePredicate() {
      }

      public boolean apply(IBlockState p_apply_1_) {
         if (p_apply_1_ != null && p_apply_1_.func_177230_c() == Blocks.field_150348_b) {
            BlockStone.EnumType blockstone$enumtype = (BlockStone.EnumType)p_apply_1_.func_177229_b(BlockStone.field_176247_a);
            return blockstone$enumtype.func_190912_e();
         } else {
            return false;
         }
      }
   }
}
