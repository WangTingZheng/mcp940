package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockFalling extends Block {
   public static boolean field_149832_M;

   public BlockFalling() {
      super(Material.field_151595_p);
      this.func_149647_a(CreativeTabs.field_78030_b);
   }

   public BlockFalling(Material p_i45405_1_) {
      super(p_i45405_1_);
   }

   public void func_176213_c(World p_176213_1_, BlockPos p_176213_2_, IBlockState p_176213_3_) {
      p_176213_1_.func_175684_a(p_176213_2_, this, this.func_149738_a(p_176213_1_));
   }

   public void func_189540_a(IBlockState p_189540_1_, World p_189540_2_, BlockPos p_189540_3_, Block p_189540_4_, BlockPos p_189540_5_) {
      p_189540_2_.func_175684_a(p_189540_3_, this, this.func_149738_a(p_189540_2_));
   }

   public void func_180650_b(World p_180650_1_, BlockPos p_180650_2_, IBlockState p_180650_3_, Random p_180650_4_) {
      if (!p_180650_1_.field_72995_K) {
         this.func_176503_e(p_180650_1_, p_180650_2_);
      }

   }

   private void func_176503_e(World p_176503_1_, BlockPos p_176503_2_) {
      if (func_185759_i(p_176503_1_.func_180495_p(p_176503_2_.func_177977_b())) && p_176503_2_.func_177956_o() >= 0) {
         int i = 32;
         if (!field_149832_M && p_176503_1_.func_175707_a(p_176503_2_.func_177982_a(-32, -32, -32), p_176503_2_.func_177982_a(32, 32, 32))) {
            if (!p_176503_1_.field_72995_K) {
               EntityFallingBlock entityfallingblock = new EntityFallingBlock(p_176503_1_, (double)p_176503_2_.func_177958_n() + 0.5D, (double)p_176503_2_.func_177956_o(), (double)p_176503_2_.func_177952_p() + 0.5D, p_176503_1_.func_180495_p(p_176503_2_));
               this.func_149829_a(entityfallingblock);
               p_176503_1_.func_72838_d(entityfallingblock);
            }
         } else {
            p_176503_1_.func_175698_g(p_176503_2_);

            BlockPos blockpos;
            for(blockpos = p_176503_2_.func_177977_b(); func_185759_i(p_176503_1_.func_180495_p(blockpos)) && blockpos.func_177956_o() > 0; blockpos = blockpos.func_177977_b()) {
               ;
            }

            if (blockpos.func_177956_o() > 0) {
               p_176503_1_.func_175656_a(blockpos.func_177984_a(), this.func_176223_P());
            }
         }

      }
   }

   protected void func_149829_a(EntityFallingBlock p_149829_1_) {
   }

   public int func_149738_a(World p_149738_1_) {
      return 2;
   }

   public static boolean func_185759_i(IBlockState p_185759_0_) {
      Block block = p_185759_0_.func_177230_c();
      Material material = p_185759_0_.func_185904_a();
      return block == Blocks.field_150480_ab || material == Material.field_151579_a || material == Material.field_151586_h || material == Material.field_151587_i;
   }

   public void func_176502_a_(World p_176502_1_, BlockPos p_176502_2_, IBlockState p_176502_3_, IBlockState p_176502_4_) {
   }

   public void func_190974_b(World p_190974_1_, BlockPos p_190974_2_) {
   }

   public void func_180655_c(IBlockState p_180655_1_, World p_180655_2_, BlockPos p_180655_3_, Random p_180655_4_) {
      if (p_180655_4_.nextInt(16) == 0) {
         BlockPos blockpos = p_180655_3_.func_177977_b();
         if (func_185759_i(p_180655_2_.func_180495_p(blockpos))) {
            double d0 = (double)((float)p_180655_3_.func_177958_n() + p_180655_4_.nextFloat());
            double d1 = (double)p_180655_3_.func_177956_o() - 0.05D;
            double d2 = (double)((float)p_180655_3_.func_177952_p() + p_180655_4_.nextFloat());
            p_180655_2_.func_175688_a(EnumParticleTypes.FALLING_DUST, d0, d1, d2, 0.0D, 0.0D, 0.0D, Block.func_176210_f(p_180655_1_));
         }
      }

   }

   public int func_189876_x(IBlockState p_189876_1_) {
      return -16777216;
   }
}
