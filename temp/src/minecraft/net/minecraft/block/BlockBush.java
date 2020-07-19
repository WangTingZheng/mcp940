package net.minecraft.block;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockBush extends Block {
   protected static final AxisAlignedBB field_185515_b = new AxisAlignedBB(0.30000001192092896D, 0.0D, 0.30000001192092896D, 0.699999988079071D, 0.6000000238418579D, 0.699999988079071D);

   protected BlockBush() {
      this(Material.field_151585_k);
   }

   protected BlockBush(Material p_i45395_1_) {
      this(p_i45395_1_, p_i45395_1_.func_151565_r());
   }

   protected BlockBush(Material p_i46452_1_, MapColor p_i46452_2_) {
      super(p_i46452_1_, p_i46452_2_);
      this.func_149675_a(true);
      this.func_149647_a(CreativeTabs.field_78031_c);
   }

   public boolean func_176196_c(World p_176196_1_, BlockPos p_176196_2_) {
      return super.func_176196_c(p_176196_1_, p_176196_2_) && this.func_185514_i(p_176196_1_.func_180495_p(p_176196_2_.func_177977_b()));
   }

   protected boolean func_185514_i(IBlockState p_185514_1_) {
      return p_185514_1_.func_177230_c() == Blocks.field_150349_c || p_185514_1_.func_177230_c() == Blocks.field_150346_d || p_185514_1_.func_177230_c() == Blocks.field_150458_ak;
   }

   public void func_189540_a(IBlockState p_189540_1_, World p_189540_2_, BlockPos p_189540_3_, Block p_189540_4_, BlockPos p_189540_5_) {
      super.func_189540_a(p_189540_1_, p_189540_2_, p_189540_3_, p_189540_4_, p_189540_5_);
      this.func_176475_e(p_189540_2_, p_189540_3_, p_189540_1_);
   }

   public void func_180650_b(World p_180650_1_, BlockPos p_180650_2_, IBlockState p_180650_3_, Random p_180650_4_) {
      this.func_176475_e(p_180650_1_, p_180650_2_, p_180650_3_);
   }

   protected void func_176475_e(World p_176475_1_, BlockPos p_176475_2_, IBlockState p_176475_3_) {
      if (!this.func_180671_f(p_176475_1_, p_176475_2_, p_176475_3_)) {
         this.func_176226_b(p_176475_1_, p_176475_2_, p_176475_3_, 0);
         p_176475_1_.func_180501_a(p_176475_2_, Blocks.field_150350_a.func_176223_P(), 3);
      }

   }

   public boolean func_180671_f(World p_180671_1_, BlockPos p_180671_2_, IBlockState p_180671_3_) {
      return this.func_185514_i(p_180671_1_.func_180495_p(p_180671_2_.func_177977_b()));
   }

   public AxisAlignedBB func_185496_a(IBlockState p_185496_1_, IBlockAccess p_185496_2_, BlockPos p_185496_3_) {
      return field_185515_b;
   }

   @Nullable
   public AxisAlignedBB func_180646_a(IBlockState p_180646_1_, IBlockAccess p_180646_2_, BlockPos p_180646_3_) {
      return field_185506_k;
   }

   public boolean func_149662_c(IBlockState p_149662_1_) {
      return false;
   }

   public boolean func_149686_d(IBlockState p_149686_1_) {
      return false;
   }

   public BlockRenderLayer func_180664_k() {
      return BlockRenderLayer.CUTOUT;
   }

   public BlockFaceShape func_193383_a(IBlockAccess p_193383_1_, IBlockState p_193383_2_, BlockPos p_193383_3_, EnumFacing p_193383_4_) {
      return BlockFaceShape.UNDEFINED;
   }
}
