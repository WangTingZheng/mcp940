package net.minecraft.block;

import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityEndPortal;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockEndPortal extends BlockContainer {
   protected static final AxisAlignedBB field_185568_a = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.75D, 1.0D);

   protected BlockEndPortal(Material p_i45404_1_) {
      super(p_i45404_1_);
      this.func_149715_a(1.0F);
   }

   public TileEntity func_149915_a(World p_149915_1_, int p_149915_2_) {
      return new TileEntityEndPortal();
   }

   public AxisAlignedBB func_185496_a(IBlockState p_185496_1_, IBlockAccess p_185496_2_, BlockPos p_185496_3_) {
      return field_185568_a;
   }

   public boolean func_176225_a(IBlockState p_176225_1_, IBlockAccess p_176225_2_, BlockPos p_176225_3_, EnumFacing p_176225_4_) {
      return p_176225_4_ == EnumFacing.DOWN ? super.func_176225_a(p_176225_1_, p_176225_2_, p_176225_3_, p_176225_4_) : false;
   }

   public void func_185477_a(IBlockState p_185477_1_, World p_185477_2_, BlockPos p_185477_3_, AxisAlignedBB p_185477_4_, List<AxisAlignedBB> p_185477_5_, @Nullable Entity p_185477_6_, boolean p_185477_7_) {
   }

   public boolean func_149662_c(IBlockState p_149662_1_) {
      return false;
   }

   public boolean func_149686_d(IBlockState p_149686_1_) {
      return false;
   }

   public int func_149745_a(Random p_149745_1_) {
      return 0;
   }

   public void func_180634_a(World p_180634_1_, BlockPos p_180634_2_, IBlockState p_180634_3_, Entity p_180634_4_) {
      if (!p_180634_4_.func_184218_aH() && !p_180634_4_.func_184207_aI() && p_180634_4_.func_184222_aU() && !p_180634_1_.field_72995_K && p_180634_4_.func_174813_aQ().func_72326_a(p_180634_3_.func_185900_c(p_180634_1_, p_180634_2_).func_186670_a(p_180634_2_))) {
         p_180634_4_.func_184204_a(1);
      }

   }

   public void func_180655_c(IBlockState p_180655_1_, World p_180655_2_, BlockPos p_180655_3_, Random p_180655_4_) {
      double d0 = (double)((float)p_180655_3_.func_177958_n() + p_180655_4_.nextFloat());
      double d1 = (double)((float)p_180655_3_.func_177956_o() + 0.8F);
      double d2 = (double)((float)p_180655_3_.func_177952_p() + p_180655_4_.nextFloat());
      double d3 = 0.0D;
      double d4 = 0.0D;
      double d5 = 0.0D;
      p_180655_2_.func_175688_a(EnumParticleTypes.SMOKE_NORMAL, d0, d1, d2, 0.0D, 0.0D, 0.0D);
   }

   public ItemStack func_185473_a(World p_185473_1_, BlockPos p_185473_2_, IBlockState p_185473_3_) {
      return ItemStack.field_190927_a;
   }

   public MapColor func_180659_g(IBlockState p_180659_1_, IBlockAccess p_180659_2_, BlockPos p_180659_3_) {
      return MapColor.field_151646_E;
   }

   public BlockFaceShape func_193383_a(IBlockAccess p_193383_1_, IBlockState p_193383_2_, BlockPos p_193383_3_, EnumFacing p_193383_4_) {
      return BlockFaceShape.UNDEFINED;
   }
}
