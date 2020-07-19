package net.minecraft.world.gen.feature;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class WorldGenSpikes extends WorldGenerator {
   private boolean field_186145_a;
   private WorldGenSpikes.EndSpike field_186146_b;
   private BlockPos field_186147_c;

   public void func_186143_a(WorldGenSpikes.EndSpike p_186143_1_) {
      this.field_186146_b = p_186143_1_;
   }

   public void func_186144_a(boolean p_186144_1_) {
      this.field_186145_a = p_186144_1_;
   }

   public boolean func_180709_b(World p_180709_1_, Random p_180709_2_, BlockPos p_180709_3_) {
      if (this.field_186146_b == null) {
         throw new IllegalStateException("Decoration requires priming with a spike");
      } else {
         int i = this.field_186146_b.func_186148_c();

         for(BlockPos.MutableBlockPos blockpos$mutableblockpos : BlockPos.func_177975_b(new BlockPos(p_180709_3_.func_177958_n() - i, 0, p_180709_3_.func_177952_p() - i), new BlockPos(p_180709_3_.func_177958_n() + i, this.field_186146_b.func_186149_d() + 10, p_180709_3_.func_177952_p() + i))) {
            if (blockpos$mutableblockpos.func_177954_c((double)p_180709_3_.func_177958_n(), (double)blockpos$mutableblockpos.func_177956_o(), (double)p_180709_3_.func_177952_p()) <= (double)(i * i + 1) && blockpos$mutableblockpos.func_177956_o() < this.field_186146_b.func_186149_d()) {
               this.func_175903_a(p_180709_1_, blockpos$mutableblockpos, Blocks.field_150343_Z.func_176223_P());
            } else if (blockpos$mutableblockpos.func_177956_o() > 65) {
               this.func_175903_a(p_180709_1_, blockpos$mutableblockpos, Blocks.field_150350_a.func_176223_P());
            }
         }

         if (this.field_186146_b.func_186150_e()) {
            for(int j = -2; j <= 2; ++j) {
               for(int k = -2; k <= 2; ++k) {
                  if (MathHelper.func_76130_a(j) == 2 || MathHelper.func_76130_a(k) == 2) {
                     this.func_175903_a(p_180709_1_, new BlockPos(p_180709_3_.func_177958_n() + j, this.field_186146_b.func_186149_d(), p_180709_3_.func_177952_p() + k), Blocks.field_150411_aY.func_176223_P());
                     this.func_175903_a(p_180709_1_, new BlockPos(p_180709_3_.func_177958_n() + j, this.field_186146_b.func_186149_d() + 1, p_180709_3_.func_177952_p() + k), Blocks.field_150411_aY.func_176223_P());
                     this.func_175903_a(p_180709_1_, new BlockPos(p_180709_3_.func_177958_n() + j, this.field_186146_b.func_186149_d() + 2, p_180709_3_.func_177952_p() + k), Blocks.field_150411_aY.func_176223_P());
                  }

                  this.func_175903_a(p_180709_1_, new BlockPos(p_180709_3_.func_177958_n() + j, this.field_186146_b.func_186149_d() + 3, p_180709_3_.func_177952_p() + k), Blocks.field_150411_aY.func_176223_P());
               }
            }
         }

         EntityEnderCrystal entityendercrystal = new EntityEnderCrystal(p_180709_1_);
         entityendercrystal.func_184516_a(this.field_186147_c);
         entityendercrystal.func_184224_h(this.field_186145_a);
         entityendercrystal.func_70012_b((double)((float)p_180709_3_.func_177958_n() + 0.5F), (double)(this.field_186146_b.func_186149_d() + 1), (double)((float)p_180709_3_.func_177952_p() + 0.5F), p_180709_2_.nextFloat() * 360.0F, 0.0F);
         p_180709_1_.func_72838_d(entityendercrystal);
         this.func_175903_a(p_180709_1_, new BlockPos(p_180709_3_.func_177958_n(), this.field_186146_b.func_186149_d(), p_180709_3_.func_177952_p()), Blocks.field_150357_h.func_176223_P());
         return true;
      }
   }

   public void func_186142_a(@Nullable BlockPos p_186142_1_) {
      this.field_186147_c = p_186142_1_;
   }

   public static class EndSpike {
      private final int field_186155_a;
      private final int field_186156_b;
      private final int field_186157_c;
      private final int field_186158_d;
      private final boolean field_186159_e;
      private final AxisAlignedBB field_186160_f;

      public EndSpike(int p_i47020_1_, int p_i47020_2_, int p_i47020_3_, int p_i47020_4_, boolean p_i47020_5_) {
         this.field_186155_a = p_i47020_1_;
         this.field_186156_b = p_i47020_2_;
         this.field_186157_c = p_i47020_3_;
         this.field_186158_d = p_i47020_4_;
         this.field_186159_e = p_i47020_5_;
         this.field_186160_f = new AxisAlignedBB((double)(p_i47020_1_ - p_i47020_3_), 0.0D, (double)(p_i47020_2_ - p_i47020_3_), (double)(p_i47020_1_ + p_i47020_3_), 256.0D, (double)(p_i47020_2_ + p_i47020_3_));
      }

      public boolean func_186154_a(BlockPos p_186154_1_) {
         int i = this.field_186155_a - this.field_186157_c;
         int j = this.field_186156_b - this.field_186157_c;
         return p_186154_1_.func_177958_n() == (i & -16) && p_186154_1_.func_177952_p() == (j & -16);
      }

      public int func_186151_a() {
         return this.field_186155_a;
      }

      public int func_186152_b() {
         return this.field_186156_b;
      }

      public int func_186148_c() {
         return this.field_186157_c;
      }

      public int func_186149_d() {
         return this.field_186158_d;
      }

      public boolean func_186150_e() {
         return this.field_186159_e;
      }

      public AxisAlignedBB func_186153_f() {
         return this.field_186160_f;
      }
   }
}
