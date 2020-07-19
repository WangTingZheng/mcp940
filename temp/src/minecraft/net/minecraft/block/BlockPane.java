package net.minecraft.block;

import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockPane extends Block {
   public static final PropertyBool field_176241_b = PropertyBool.func_177716_a("north");
   public static final PropertyBool field_176242_M = PropertyBool.func_177716_a("east");
   public static final PropertyBool field_176243_N = PropertyBool.func_177716_a("south");
   public static final PropertyBool field_176244_O = PropertyBool.func_177716_a("west");
   protected static final AxisAlignedBB[] field_185730_f = new AxisAlignedBB[]{new AxisAlignedBB(0.4375D, 0.0D, 0.4375D, 0.5625D, 1.0D, 0.5625D), new AxisAlignedBB(0.4375D, 0.0D, 0.4375D, 0.5625D, 1.0D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.4375D, 0.5625D, 1.0D, 0.5625D), new AxisAlignedBB(0.0D, 0.0D, 0.4375D, 0.5625D, 1.0D, 1.0D), new AxisAlignedBB(0.4375D, 0.0D, 0.0D, 0.5625D, 1.0D, 0.5625D), new AxisAlignedBB(0.4375D, 0.0D, 0.0D, 0.5625D, 1.0D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.5625D, 1.0D, 0.5625D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.5625D, 1.0D, 1.0D), new AxisAlignedBB(0.4375D, 0.0D, 0.4375D, 1.0D, 1.0D, 0.5625D), new AxisAlignedBB(0.4375D, 0.0D, 0.4375D, 1.0D, 1.0D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.4375D, 1.0D, 1.0D, 0.5625D), new AxisAlignedBB(0.0D, 0.0D, 0.4375D, 1.0D, 1.0D, 1.0D), new AxisAlignedBB(0.4375D, 0.0D, 0.0D, 1.0D, 1.0D, 0.5625D), new AxisAlignedBB(0.4375D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.5625D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D)};
   private final boolean field_150099_b;

   protected BlockPane(Material p_i45675_1_, boolean p_i45675_2_) {
      super(p_i45675_1_);
      this.func_180632_j(this.field_176227_L.func_177621_b().func_177226_a(field_176241_b, Boolean.valueOf(false)).func_177226_a(field_176242_M, Boolean.valueOf(false)).func_177226_a(field_176243_N, Boolean.valueOf(false)).func_177226_a(field_176244_O, Boolean.valueOf(false)));
      this.field_150099_b = p_i45675_2_;
      this.func_149647_a(CreativeTabs.field_78031_c);
   }

   public void func_185477_a(IBlockState p_185477_1_, World p_185477_2_, BlockPos p_185477_3_, AxisAlignedBB p_185477_4_, List<AxisAlignedBB> p_185477_5_, @Nullable Entity p_185477_6_, boolean p_185477_7_) {
      if (!p_185477_7_) {
         p_185477_1_ = this.func_176221_a(p_185477_1_, p_185477_2_, p_185477_3_);
      }

      func_185492_a(p_185477_3_, p_185477_4_, p_185477_5_, field_185730_f[0]);
      if (((Boolean)p_185477_1_.func_177229_b(field_176241_b)).booleanValue()) {
         func_185492_a(p_185477_3_, p_185477_4_, p_185477_5_, field_185730_f[func_185729_a(EnumFacing.NORTH)]);
      }

      if (((Boolean)p_185477_1_.func_177229_b(field_176243_N)).booleanValue()) {
         func_185492_a(p_185477_3_, p_185477_4_, p_185477_5_, field_185730_f[func_185729_a(EnumFacing.SOUTH)]);
      }

      if (((Boolean)p_185477_1_.func_177229_b(field_176242_M)).booleanValue()) {
         func_185492_a(p_185477_3_, p_185477_4_, p_185477_5_, field_185730_f[func_185729_a(EnumFacing.EAST)]);
      }

      if (((Boolean)p_185477_1_.func_177229_b(field_176244_O)).booleanValue()) {
         func_185492_a(p_185477_3_, p_185477_4_, p_185477_5_, field_185730_f[func_185729_a(EnumFacing.WEST)]);
      }

   }

   private static int func_185729_a(EnumFacing p_185729_0_) {
      return 1 << p_185729_0_.func_176736_b();
   }

   public AxisAlignedBB func_185496_a(IBlockState p_185496_1_, IBlockAccess p_185496_2_, BlockPos p_185496_3_) {
      p_185496_1_ = this.func_176221_a(p_185496_1_, p_185496_2_, p_185496_3_);
      return field_185730_f[func_185728_i(p_185496_1_)];
   }

   private static int func_185728_i(IBlockState p_185728_0_) {
      int i = 0;
      if (((Boolean)p_185728_0_.func_177229_b(field_176241_b)).booleanValue()) {
         i |= func_185729_a(EnumFacing.NORTH);
      }

      if (((Boolean)p_185728_0_.func_177229_b(field_176242_M)).booleanValue()) {
         i |= func_185729_a(EnumFacing.EAST);
      }

      if (((Boolean)p_185728_0_.func_177229_b(field_176243_N)).booleanValue()) {
         i |= func_185729_a(EnumFacing.SOUTH);
      }

      if (((Boolean)p_185728_0_.func_177229_b(field_176244_O)).booleanValue()) {
         i |= func_185729_a(EnumFacing.WEST);
      }

      return i;
   }

   public IBlockState func_176221_a(IBlockState p_176221_1_, IBlockAccess p_176221_2_, BlockPos p_176221_3_) {
      return p_176221_1_.func_177226_a(field_176241_b, Boolean.valueOf(this.func_193393_b(p_176221_2_, p_176221_2_.func_180495_p(p_176221_3_.func_177978_c()), p_176221_3_.func_177978_c(), EnumFacing.SOUTH))).func_177226_a(field_176243_N, Boolean.valueOf(this.func_193393_b(p_176221_2_, p_176221_2_.func_180495_p(p_176221_3_.func_177968_d()), p_176221_3_.func_177968_d(), EnumFacing.NORTH))).func_177226_a(field_176244_O, Boolean.valueOf(this.func_193393_b(p_176221_2_, p_176221_2_.func_180495_p(p_176221_3_.func_177976_e()), p_176221_3_.func_177976_e(), EnumFacing.EAST))).func_177226_a(field_176242_M, Boolean.valueOf(this.func_193393_b(p_176221_2_, p_176221_2_.func_180495_p(p_176221_3_.func_177974_f()), p_176221_3_.func_177974_f(), EnumFacing.WEST)));
   }

   public Item func_180660_a(IBlockState p_180660_1_, Random p_180660_2_, int p_180660_3_) {
      return !this.field_150099_b ? Items.field_190931_a : super.func_180660_a(p_180660_1_, p_180660_2_, p_180660_3_);
   }

   public boolean func_149662_c(IBlockState p_149662_1_) {
      return false;
   }

   public boolean func_149686_d(IBlockState p_149686_1_) {
      return false;
   }

   public boolean func_176225_a(IBlockState p_176225_1_, IBlockAccess p_176225_2_, BlockPos p_176225_3_, EnumFacing p_176225_4_) {
      return p_176225_2_.func_180495_p(p_176225_3_.func_177972_a(p_176225_4_)).func_177230_c() == this ? false : super.func_176225_a(p_176225_1_, p_176225_2_, p_176225_3_, p_176225_4_);
   }

   public final boolean func_193393_b(IBlockAccess p_193393_1_, IBlockState p_193393_2_, BlockPos p_193393_3_, EnumFacing p_193393_4_) {
      Block block = p_193393_2_.func_177230_c();
      BlockFaceShape blockfaceshape = p_193393_2_.func_193401_d(p_193393_1_, p_193393_3_, p_193393_4_);
      return !func_193394_e(block) && blockfaceshape == BlockFaceShape.SOLID || blockfaceshape == BlockFaceShape.MIDDLE_POLE_THIN;
   }

   protected static boolean func_193394_e(Block p_193394_0_) {
      return p_193394_0_ instanceof BlockShulkerBox || p_193394_0_ instanceof BlockLeaves || p_193394_0_ == Blocks.field_150461_bJ || p_193394_0_ == Blocks.field_150383_bp || p_193394_0_ == Blocks.field_150426_aN || p_193394_0_ == Blocks.field_150432_aD || p_193394_0_ == Blocks.field_180398_cJ || p_193394_0_ == Blocks.field_150331_J || p_193394_0_ == Blocks.field_150320_F || p_193394_0_ == Blocks.field_150332_K || p_193394_0_ == Blocks.field_150440_ba || p_193394_0_ == Blocks.field_150423_aK || p_193394_0_ == Blocks.field_150428_aP || p_193394_0_ == Blocks.field_180401_cv;
   }

   protected boolean func_149700_E() {
      return true;
   }

   public BlockRenderLayer func_180664_k() {
      return BlockRenderLayer.CUTOUT_MIPPED;
   }

   public int func_176201_c(IBlockState p_176201_1_) {
      return 0;
   }

   public IBlockState func_185499_a(IBlockState p_185499_1_, Rotation p_185499_2_) {
      switch(p_185499_2_) {
      case CLOCKWISE_180:
         return p_185499_1_.func_177226_a(field_176241_b, p_185499_1_.func_177229_b(field_176243_N)).func_177226_a(field_176242_M, p_185499_1_.func_177229_b(field_176244_O)).func_177226_a(field_176243_N, p_185499_1_.func_177229_b(field_176241_b)).func_177226_a(field_176244_O, p_185499_1_.func_177229_b(field_176242_M));
      case COUNTERCLOCKWISE_90:
         return p_185499_1_.func_177226_a(field_176241_b, p_185499_1_.func_177229_b(field_176242_M)).func_177226_a(field_176242_M, p_185499_1_.func_177229_b(field_176243_N)).func_177226_a(field_176243_N, p_185499_1_.func_177229_b(field_176244_O)).func_177226_a(field_176244_O, p_185499_1_.func_177229_b(field_176241_b));
      case CLOCKWISE_90:
         return p_185499_1_.func_177226_a(field_176241_b, p_185499_1_.func_177229_b(field_176244_O)).func_177226_a(field_176242_M, p_185499_1_.func_177229_b(field_176241_b)).func_177226_a(field_176243_N, p_185499_1_.func_177229_b(field_176242_M)).func_177226_a(field_176244_O, p_185499_1_.func_177229_b(field_176243_N));
      default:
         return p_185499_1_;
      }
   }

   public IBlockState func_185471_a(IBlockState p_185471_1_, Mirror p_185471_2_) {
      switch(p_185471_2_) {
      case LEFT_RIGHT:
         return p_185471_1_.func_177226_a(field_176241_b, p_185471_1_.func_177229_b(field_176243_N)).func_177226_a(field_176243_N, p_185471_1_.func_177229_b(field_176241_b));
      case FRONT_BACK:
         return p_185471_1_.func_177226_a(field_176242_M, p_185471_1_.func_177229_b(field_176244_O)).func_177226_a(field_176244_O, p_185471_1_.func_177229_b(field_176242_M));
      default:
         return super.func_185471_a(p_185471_1_, p_185471_2_);
      }
   }

   protected BlockStateContainer func_180661_e() {
      return new BlockStateContainer(this, new IProperty[]{field_176241_b, field_176242_M, field_176244_O, field_176243_N});
   }

   public BlockFaceShape func_193383_a(IBlockAccess p_193383_1_, IBlockState p_193383_2_, BlockPos p_193383_3_, EnumFacing p_193383_4_) {
      return p_193383_4_ != EnumFacing.UP && p_193383_4_ != EnumFacing.DOWN ? BlockFaceShape.MIDDLE_POLE_THIN : BlockFaceShape.CENTER_SMALL;
   }
}
