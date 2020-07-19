package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockCactus extends Block {
   public static final PropertyInteger field_176587_a = PropertyInteger.func_177719_a("age", 0, 15);
   protected static final AxisAlignedBB field_185593_b = new AxisAlignedBB(0.0625D, 0.0D, 0.0625D, 0.9375D, 0.9375D, 0.9375D);
   protected static final AxisAlignedBB field_185594_c = new AxisAlignedBB(0.0625D, 0.0D, 0.0625D, 0.9375D, 1.0D, 0.9375D);

   protected BlockCactus() {
      super(Material.field_151570_A);
      this.func_180632_j(this.field_176227_L.func_177621_b().func_177226_a(field_176587_a, Integer.valueOf(0)));
      this.func_149675_a(true);
      this.func_149647_a(CreativeTabs.field_78031_c);
   }

   public void func_180650_b(World p_180650_1_, BlockPos p_180650_2_, IBlockState p_180650_3_, Random p_180650_4_) {
      BlockPos blockpos = p_180650_2_.func_177984_a();
      if (p_180650_1_.func_175623_d(blockpos)) {
         int i;
         for(i = 1; p_180650_1_.func_180495_p(p_180650_2_.func_177979_c(i)).func_177230_c() == this; ++i) {
            ;
         }

         if (i < 3) {
            int j = ((Integer)p_180650_3_.func_177229_b(field_176587_a)).intValue();
            if (j == 15) {
               p_180650_1_.func_175656_a(blockpos, this.func_176223_P());
               IBlockState iblockstate = p_180650_3_.func_177226_a(field_176587_a, Integer.valueOf(0));
               p_180650_1_.func_180501_a(p_180650_2_, iblockstate, 4);
               iblockstate.func_189546_a(p_180650_1_, blockpos, this, p_180650_2_);
            } else {
               p_180650_1_.func_180501_a(p_180650_2_, p_180650_3_.func_177226_a(field_176587_a, Integer.valueOf(j + 1)), 4);
            }

         }
      }
   }

   public AxisAlignedBB func_180646_a(IBlockState p_180646_1_, IBlockAccess p_180646_2_, BlockPos p_180646_3_) {
      return field_185593_b;
   }

   public AxisAlignedBB func_180640_a(IBlockState p_180640_1_, World p_180640_2_, BlockPos p_180640_3_) {
      return field_185594_c.func_186670_a(p_180640_3_);
   }

   public boolean func_149686_d(IBlockState p_149686_1_) {
      return false;
   }

   public boolean func_149662_c(IBlockState p_149662_1_) {
      return false;
   }

   public boolean func_176196_c(World p_176196_1_, BlockPos p_176196_2_) {
      return super.func_176196_c(p_176196_1_, p_176196_2_) ? this.func_176586_d(p_176196_1_, p_176196_2_) : false;
   }

   public void func_189540_a(IBlockState p_189540_1_, World p_189540_2_, BlockPos p_189540_3_, Block p_189540_4_, BlockPos p_189540_5_) {
      if (!this.func_176586_d(p_189540_2_, p_189540_3_)) {
         p_189540_2_.func_175655_b(p_189540_3_, true);
      }

   }

   public boolean func_176586_d(World p_176586_1_, BlockPos p_176586_2_) {
      for(EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
         Material material = p_176586_1_.func_180495_p(p_176586_2_.func_177972_a(enumfacing)).func_185904_a();
         if (material.func_76220_a() || material == Material.field_151587_i) {
            return false;
         }
      }

      Block block = p_176586_1_.func_180495_p(p_176586_2_.func_177977_b()).func_177230_c();
      return block == Blocks.field_150434_aF || block == Blocks.field_150354_m && !p_176586_1_.func_180495_p(p_176586_2_.func_177984_a()).func_185904_a().func_76224_d();
   }

   public void func_180634_a(World p_180634_1_, BlockPos p_180634_2_, IBlockState p_180634_3_, Entity p_180634_4_) {
      p_180634_4_.func_70097_a(DamageSource.field_76367_g, 1.0F);
   }

   public BlockRenderLayer func_180664_k() {
      return BlockRenderLayer.CUTOUT;
   }

   public IBlockState func_176203_a(int p_176203_1_) {
      return this.func_176223_P().func_177226_a(field_176587_a, Integer.valueOf(p_176203_1_));
   }

   public int func_176201_c(IBlockState p_176201_1_) {
      return ((Integer)p_176201_1_.func_177229_b(field_176587_a)).intValue();
   }

   protected BlockStateContainer func_180661_e() {
      return new BlockStateContainer(this, new IProperty[]{field_176587_a});
   }

   public BlockFaceShape func_193383_a(IBlockAccess p_193383_1_, IBlockState p_193383_2_, BlockPos p_193383_3_, EnumFacing p_193383_4_) {
      return BlockFaceShape.UNDEFINED;
   }
}
