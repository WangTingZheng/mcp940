package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockStaticLiquid extends BlockLiquid {
   protected BlockStaticLiquid(Material p_i45429_1_) {
      super(p_i45429_1_);
      this.func_149675_a(false);
      if (p_i45429_1_ == Material.field_151587_i) {
         this.func_149675_a(true);
      }

   }

   public void func_189540_a(IBlockState p_189540_1_, World p_189540_2_, BlockPos p_189540_3_, Block p_189540_4_, BlockPos p_189540_5_) {
      if (!this.func_176365_e(p_189540_2_, p_189540_3_, p_189540_1_)) {
         this.func_176370_f(p_189540_2_, p_189540_3_, p_189540_1_);
      }

   }

   private void func_176370_f(World p_176370_1_, BlockPos p_176370_2_, IBlockState p_176370_3_) {
      BlockDynamicLiquid blockdynamicliquid = func_176361_a(this.field_149764_J);
      p_176370_1_.func_180501_a(p_176370_2_, blockdynamicliquid.func_176223_P().func_177226_a(field_176367_b, p_176370_3_.func_177229_b(field_176367_b)), 2);
      p_176370_1_.func_175684_a(p_176370_2_, blockdynamicliquid, this.func_149738_a(p_176370_1_));
   }

   public void func_180650_b(World p_180650_1_, BlockPos p_180650_2_, IBlockState p_180650_3_, Random p_180650_4_) {
      if (this.field_149764_J == Material.field_151587_i) {
         if (p_180650_1_.func_82736_K().func_82766_b("doFireTick")) {
            int i = p_180650_4_.nextInt(3);
            if (i > 0) {
               BlockPos blockpos = p_180650_2_;

               for(int j = 0; j < i; ++j) {
                  blockpos = blockpos.func_177982_a(p_180650_4_.nextInt(3) - 1, 1, p_180650_4_.nextInt(3) - 1);
                  if (blockpos.func_177956_o() >= 0 && blockpos.func_177956_o() < 256 && !p_180650_1_.func_175667_e(blockpos)) {
                     return;
                  }

                  Block block = p_180650_1_.func_180495_p(blockpos).func_177230_c();
                  if (block.field_149764_J == Material.field_151579_a) {
                     if (this.func_176369_e(p_180650_1_, blockpos)) {
                        p_180650_1_.func_175656_a(blockpos, Blocks.field_150480_ab.func_176223_P());
                        return;
                     }
                  } else if (block.field_149764_J.func_76230_c()) {
                     return;
                  }
               }
            } else {
               for(int k = 0; k < 3; ++k) {
                  BlockPos blockpos1 = p_180650_2_.func_177982_a(p_180650_4_.nextInt(3) - 1, 0, p_180650_4_.nextInt(3) - 1);
                  if (blockpos1.func_177956_o() >= 0 && blockpos1.func_177956_o() < 256 && !p_180650_1_.func_175667_e(blockpos1)) {
                     return;
                  }

                  if (p_180650_1_.func_175623_d(blockpos1.func_177984_a()) && this.func_176368_m(p_180650_1_, blockpos1)) {
                     p_180650_1_.func_175656_a(blockpos1.func_177984_a(), Blocks.field_150480_ab.func_176223_P());
                  }
               }
            }

         }
      }
   }

   protected boolean func_176369_e(World p_176369_1_, BlockPos p_176369_2_) {
      for(EnumFacing enumfacing : EnumFacing.values()) {
         if (this.func_176368_m(p_176369_1_, p_176369_2_.func_177972_a(enumfacing))) {
            return true;
         }
      }

      return false;
   }

   private boolean func_176368_m(World p_176368_1_, BlockPos p_176368_2_) {
      return p_176368_2_.func_177956_o() >= 0 && p_176368_2_.func_177956_o() < 256 && !p_176368_1_.func_175667_e(p_176368_2_) ? false : p_176368_1_.func_180495_p(p_176368_2_).func_185904_a().func_76217_h();
   }
}
