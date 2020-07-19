package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockEndRod extends BlockDirectional {
   protected static final AxisAlignedBB field_185630_a = new AxisAlignedBB(0.375D, 0.0D, 0.375D, 0.625D, 1.0D, 0.625D);
   protected static final AxisAlignedBB field_185631_b = new AxisAlignedBB(0.375D, 0.375D, 0.0D, 0.625D, 0.625D, 1.0D);
   protected static final AxisAlignedBB field_185632_c = new AxisAlignedBB(0.0D, 0.375D, 0.375D, 1.0D, 0.625D, 0.625D);

   protected BlockEndRod() {
      super(Material.field_151594_q);
      this.func_180632_j(this.field_176227_L.func_177621_b().func_177226_a(field_176387_N, EnumFacing.UP));
      this.func_149647_a(CreativeTabs.field_78031_c);
   }

   public IBlockState func_185499_a(IBlockState p_185499_1_, Rotation p_185499_2_) {
      return p_185499_1_.func_177226_a(field_176387_N, p_185499_2_.func_185831_a((EnumFacing)p_185499_1_.func_177229_b(field_176387_N)));
   }

   public IBlockState func_185471_a(IBlockState p_185471_1_, Mirror p_185471_2_) {
      return p_185471_1_.func_177226_a(field_176387_N, p_185471_2_.func_185803_b((EnumFacing)p_185471_1_.func_177229_b(field_176387_N)));
   }

   public AxisAlignedBB func_185496_a(IBlockState p_185496_1_, IBlockAccess p_185496_2_, BlockPos p_185496_3_) {
      switch(((EnumFacing)p_185496_1_.func_177229_b(field_176387_N)).func_176740_k()) {
      case X:
      default:
         return field_185632_c;
      case Z:
         return field_185631_b;
      case Y:
         return field_185630_a;
      }
   }

   public boolean func_149662_c(IBlockState p_149662_1_) {
      return false;
   }

   public boolean func_149686_d(IBlockState p_149686_1_) {
      return false;
   }

   public boolean func_176196_c(World p_176196_1_, BlockPos p_176196_2_) {
      return true;
   }

   public IBlockState func_180642_a(World p_180642_1_, BlockPos p_180642_2_, EnumFacing p_180642_3_, float p_180642_4_, float p_180642_5_, float p_180642_6_, int p_180642_7_, EntityLivingBase p_180642_8_) {
      IBlockState iblockstate = p_180642_1_.func_180495_p(p_180642_2_.func_177972_a(p_180642_3_.func_176734_d()));
      if (iblockstate.func_177230_c() == Blocks.field_185764_cQ) {
         EnumFacing enumfacing = (EnumFacing)iblockstate.func_177229_b(field_176387_N);
         if (enumfacing == p_180642_3_) {
            return this.func_176223_P().func_177226_a(field_176387_N, p_180642_3_.func_176734_d());
         }
      }

      return this.func_176223_P().func_177226_a(field_176387_N, p_180642_3_);
   }

   public void func_180655_c(IBlockState p_180655_1_, World p_180655_2_, BlockPos p_180655_3_, Random p_180655_4_) {
      EnumFacing enumfacing = (EnumFacing)p_180655_1_.func_177229_b(field_176387_N);
      double d0 = (double)p_180655_3_.func_177958_n() + 0.55D - (double)(p_180655_4_.nextFloat() * 0.1F);
      double d1 = (double)p_180655_3_.func_177956_o() + 0.55D - (double)(p_180655_4_.nextFloat() * 0.1F);
      double d2 = (double)p_180655_3_.func_177952_p() + 0.55D - (double)(p_180655_4_.nextFloat() * 0.1F);
      double d3 = (double)(0.4F - (p_180655_4_.nextFloat() + p_180655_4_.nextFloat()) * 0.4F);
      if (p_180655_4_.nextInt(5) == 0) {
         p_180655_2_.func_175688_a(EnumParticleTypes.END_ROD, d0 + (double)enumfacing.func_82601_c() * d3, d1 + (double)enumfacing.func_96559_d() * d3, d2 + (double)enumfacing.func_82599_e() * d3, p_180655_4_.nextGaussian() * 0.005D, p_180655_4_.nextGaussian() * 0.005D, p_180655_4_.nextGaussian() * 0.005D);
      }

   }

   public BlockRenderLayer func_180664_k() {
      return BlockRenderLayer.CUTOUT;
   }

   public IBlockState func_176203_a(int p_176203_1_) {
      IBlockState iblockstate = this.func_176223_P();
      iblockstate = iblockstate.func_177226_a(field_176387_N, EnumFacing.func_82600_a(p_176203_1_));
      return iblockstate;
   }

   public int func_176201_c(IBlockState p_176201_1_) {
      return ((EnumFacing)p_176201_1_.func_177229_b(field_176387_N)).func_176745_a();
   }

   protected BlockStateContainer func_180661_e() {
      return new BlockStateContainer(this, new IProperty[]{field_176387_N});
   }

   public EnumPushReaction func_149656_h(IBlockState p_149656_1_) {
      return EnumPushReaction.NORMAL;
   }

   public BlockFaceShape func_193383_a(IBlockAccess p_193383_1_, IBlockState p_193383_2_, BlockPos p_193383_3_, EnumFacing p_193383_4_) {
      return BlockFaceShape.UNDEFINED;
   }
}
