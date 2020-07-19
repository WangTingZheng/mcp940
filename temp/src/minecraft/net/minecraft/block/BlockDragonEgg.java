package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockDragonEgg extends Block {
   protected static final AxisAlignedBB field_185660_a = new AxisAlignedBB(0.0625D, 0.0D, 0.0625D, 0.9375D, 1.0D, 0.9375D);

   public BlockDragonEgg() {
      super(Material.field_151566_D, MapColor.field_151646_E);
   }

   public AxisAlignedBB func_185496_a(IBlockState p_185496_1_, IBlockAccess p_185496_2_, BlockPos p_185496_3_) {
      return field_185660_a;
   }

   public void func_176213_c(World p_176213_1_, BlockPos p_176213_2_, IBlockState p_176213_3_) {
      p_176213_1_.func_175684_a(p_176213_2_, this, this.func_149738_a(p_176213_1_));
   }

   public void func_189540_a(IBlockState p_189540_1_, World p_189540_2_, BlockPos p_189540_3_, Block p_189540_4_, BlockPos p_189540_5_) {
      p_189540_2_.func_175684_a(p_189540_3_, this, this.func_149738_a(p_189540_2_));
   }

   public void func_180650_b(World p_180650_1_, BlockPos p_180650_2_, IBlockState p_180650_3_, Random p_180650_4_) {
      this.func_180683_d(p_180650_1_, p_180650_2_);
   }

   private void func_180683_d(World p_180683_1_, BlockPos p_180683_2_) {
      if (BlockFalling.func_185759_i(p_180683_1_.func_180495_p(p_180683_2_.func_177977_b())) && p_180683_2_.func_177956_o() >= 0) {
         int i = 32;
         if (!BlockFalling.field_149832_M && p_180683_1_.func_175707_a(p_180683_2_.func_177982_a(-32, -32, -32), p_180683_2_.func_177982_a(32, 32, 32))) {
            p_180683_1_.func_72838_d(new EntityFallingBlock(p_180683_1_, (double)((float)p_180683_2_.func_177958_n() + 0.5F), (double)p_180683_2_.func_177956_o(), (double)((float)p_180683_2_.func_177952_p() + 0.5F), this.func_176223_P()));
         } else {
            p_180683_1_.func_175698_g(p_180683_2_);

            BlockPos blockpos;
            for(blockpos = p_180683_2_; BlockFalling.func_185759_i(p_180683_1_.func_180495_p(blockpos)) && blockpos.func_177956_o() > 0; blockpos = blockpos.func_177977_b()) {
               ;
            }

            if (blockpos.func_177956_o() > 0) {
               p_180683_1_.func_180501_a(blockpos, this.func_176223_P(), 2);
            }
         }

      }
   }

   public boolean func_180639_a(World p_180639_1_, BlockPos p_180639_2_, IBlockState p_180639_3_, EntityPlayer p_180639_4_, EnumHand p_180639_5_, EnumFacing p_180639_6_, float p_180639_7_, float p_180639_8_, float p_180639_9_) {
      this.func_180684_e(p_180639_1_, p_180639_2_);
      return true;
   }

   public void func_180649_a(World p_180649_1_, BlockPos p_180649_2_, EntityPlayer p_180649_3_) {
      this.func_180684_e(p_180649_1_, p_180649_2_);
   }

   private void func_180684_e(World p_180684_1_, BlockPos p_180684_2_) {
      IBlockState iblockstate = p_180684_1_.func_180495_p(p_180684_2_);
      if (iblockstate.func_177230_c() == this) {
         for(int i = 0; i < 1000; ++i) {
            BlockPos blockpos = p_180684_2_.func_177982_a(p_180684_1_.field_73012_v.nextInt(16) - p_180684_1_.field_73012_v.nextInt(16), p_180684_1_.field_73012_v.nextInt(8) - p_180684_1_.field_73012_v.nextInt(8), p_180684_1_.field_73012_v.nextInt(16) - p_180684_1_.field_73012_v.nextInt(16));
            if (p_180684_1_.func_180495_p(blockpos).func_177230_c().field_149764_J == Material.field_151579_a) {
               if (p_180684_1_.field_72995_K) {
                  for(int j = 0; j < 128; ++j) {
                     double d0 = p_180684_1_.field_73012_v.nextDouble();
                     float f = (p_180684_1_.field_73012_v.nextFloat() - 0.5F) * 0.2F;
                     float f1 = (p_180684_1_.field_73012_v.nextFloat() - 0.5F) * 0.2F;
                     float f2 = (p_180684_1_.field_73012_v.nextFloat() - 0.5F) * 0.2F;
                     double d1 = (double)blockpos.func_177958_n() + (double)(p_180684_2_.func_177958_n() - blockpos.func_177958_n()) * d0 + (p_180684_1_.field_73012_v.nextDouble() - 0.5D) + 0.5D;
                     double d2 = (double)blockpos.func_177956_o() + (double)(p_180684_2_.func_177956_o() - blockpos.func_177956_o()) * d0 + p_180684_1_.field_73012_v.nextDouble() - 0.5D;
                     double d3 = (double)blockpos.func_177952_p() + (double)(p_180684_2_.func_177952_p() - blockpos.func_177952_p()) * d0 + (p_180684_1_.field_73012_v.nextDouble() - 0.5D) + 0.5D;
                     p_180684_1_.func_175688_a(EnumParticleTypes.PORTAL, d1, d2, d3, (double)f, (double)f1, (double)f2);
                  }
               } else {
                  p_180684_1_.func_180501_a(blockpos, iblockstate, 2);
                  p_180684_1_.func_175698_g(p_180684_2_);
               }

               return;
            }
         }

      }
   }

   public int func_149738_a(World p_149738_1_) {
      return 5;
   }

   public boolean func_149662_c(IBlockState p_149662_1_) {
      return false;
   }

   public boolean func_149686_d(IBlockState p_149686_1_) {
      return false;
   }

   public boolean func_176225_a(IBlockState p_176225_1_, IBlockAccess p_176225_2_, BlockPos p_176225_3_, EnumFacing p_176225_4_) {
      return true;
   }

   public BlockFaceShape func_193383_a(IBlockAccess p_193383_1_, IBlockState p_193383_2_, BlockPos p_193383_3_, EnumFacing p_193383_4_) {
      return BlockFaceShape.UNDEFINED;
   }
}
