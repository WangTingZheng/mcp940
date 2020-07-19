package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockCocoa extends BlockHorizontal implements IGrowable {
   public static final PropertyInteger field_176501_a = PropertyInteger.func_177719_a("age", 0, 2);
   protected static final AxisAlignedBB[] field_185535_b = new AxisAlignedBB[]{new AxisAlignedBB(0.6875D, 0.4375D, 0.375D, 0.9375D, 0.75D, 0.625D), new AxisAlignedBB(0.5625D, 0.3125D, 0.3125D, 0.9375D, 0.75D, 0.6875D), new AxisAlignedBB(0.4375D, 0.1875D, 0.25D, 0.9375D, 0.75D, 0.75D)};
   protected static final AxisAlignedBB[] field_185536_c = new AxisAlignedBB[]{new AxisAlignedBB(0.0625D, 0.4375D, 0.375D, 0.3125D, 0.75D, 0.625D), new AxisAlignedBB(0.0625D, 0.3125D, 0.3125D, 0.4375D, 0.75D, 0.6875D), new AxisAlignedBB(0.0625D, 0.1875D, 0.25D, 0.5625D, 0.75D, 0.75D)};
   protected static final AxisAlignedBB[] field_185537_d = new AxisAlignedBB[]{new AxisAlignedBB(0.375D, 0.4375D, 0.0625D, 0.625D, 0.75D, 0.3125D), new AxisAlignedBB(0.3125D, 0.3125D, 0.0625D, 0.6875D, 0.75D, 0.4375D), new AxisAlignedBB(0.25D, 0.1875D, 0.0625D, 0.75D, 0.75D, 0.5625D)};
   protected static final AxisAlignedBB[] field_185538_e = new AxisAlignedBB[]{new AxisAlignedBB(0.375D, 0.4375D, 0.6875D, 0.625D, 0.75D, 0.9375D), new AxisAlignedBB(0.3125D, 0.3125D, 0.5625D, 0.6875D, 0.75D, 0.9375D), new AxisAlignedBB(0.25D, 0.1875D, 0.4375D, 0.75D, 0.75D, 0.9375D)};

   public BlockCocoa() {
      super(Material.field_151585_k);
      this.func_180632_j(this.field_176227_L.func_177621_b().func_177226_a(field_185512_D, EnumFacing.NORTH).func_177226_a(field_176501_a, Integer.valueOf(0)));
      this.func_149675_a(true);
   }

   public void func_180650_b(World p_180650_1_, BlockPos p_180650_2_, IBlockState p_180650_3_, Random p_180650_4_) {
      if (!this.func_176499_e(p_180650_1_, p_180650_2_, p_180650_3_)) {
         this.func_176500_f(p_180650_1_, p_180650_2_, p_180650_3_);
      } else if (p_180650_1_.field_73012_v.nextInt(5) == 0) {
         int i = ((Integer)p_180650_3_.func_177229_b(field_176501_a)).intValue();
         if (i < 2) {
            p_180650_1_.func_180501_a(p_180650_2_, p_180650_3_.func_177226_a(field_176501_a, Integer.valueOf(i + 1)), 2);
         }
      }

   }

   public boolean func_176499_e(World p_176499_1_, BlockPos p_176499_2_, IBlockState p_176499_3_) {
      p_176499_2_ = p_176499_2_.func_177972_a((EnumFacing)p_176499_3_.func_177229_b(field_185512_D));
      IBlockState iblockstate = p_176499_1_.func_180495_p(p_176499_2_);
      return iblockstate.func_177230_c() == Blocks.field_150364_r && iblockstate.func_177229_b(BlockOldLog.field_176301_b) == BlockPlanks.EnumType.JUNGLE;
   }

   public boolean func_149686_d(IBlockState p_149686_1_) {
      return false;
   }

   public boolean func_149662_c(IBlockState p_149662_1_) {
      return false;
   }

   public AxisAlignedBB func_185496_a(IBlockState p_185496_1_, IBlockAccess p_185496_2_, BlockPos p_185496_3_) {
      int i = ((Integer)p_185496_1_.func_177229_b(field_176501_a)).intValue();
      switch((EnumFacing)p_185496_1_.func_177229_b(field_185512_D)) {
      case SOUTH:
         return field_185538_e[i];
      case NORTH:
      default:
         return field_185537_d[i];
      case WEST:
         return field_185536_c[i];
      case EAST:
         return field_185535_b[i];
      }
   }

   public IBlockState func_185499_a(IBlockState p_185499_1_, Rotation p_185499_2_) {
      return p_185499_1_.func_177226_a(field_185512_D, p_185499_2_.func_185831_a((EnumFacing)p_185499_1_.func_177229_b(field_185512_D)));
   }

   public IBlockState func_185471_a(IBlockState p_185471_1_, Mirror p_185471_2_) {
      return p_185471_1_.func_185907_a(p_185471_2_.func_185800_a((EnumFacing)p_185471_1_.func_177229_b(field_185512_D)));
   }

   public void func_180633_a(World p_180633_1_, BlockPos p_180633_2_, IBlockState p_180633_3_, EntityLivingBase p_180633_4_, ItemStack p_180633_5_) {
      EnumFacing enumfacing = EnumFacing.func_176733_a((double)p_180633_4_.field_70177_z);
      p_180633_1_.func_180501_a(p_180633_2_, p_180633_3_.func_177226_a(field_185512_D, enumfacing), 2);
   }

   public IBlockState func_180642_a(World p_180642_1_, BlockPos p_180642_2_, EnumFacing p_180642_3_, float p_180642_4_, float p_180642_5_, float p_180642_6_, int p_180642_7_, EntityLivingBase p_180642_8_) {
      if (!p_180642_3_.func_176740_k().func_176722_c()) {
         p_180642_3_ = EnumFacing.NORTH;
      }

      return this.func_176223_P().func_177226_a(field_185512_D, p_180642_3_.func_176734_d()).func_177226_a(field_176501_a, Integer.valueOf(0));
   }

   public void func_189540_a(IBlockState p_189540_1_, World p_189540_2_, BlockPos p_189540_3_, Block p_189540_4_, BlockPos p_189540_5_) {
      if (!this.func_176499_e(p_189540_2_, p_189540_3_, p_189540_1_)) {
         this.func_176500_f(p_189540_2_, p_189540_3_, p_189540_1_);
      }

   }

   private void func_176500_f(World p_176500_1_, BlockPos p_176500_2_, IBlockState p_176500_3_) {
      p_176500_1_.func_180501_a(p_176500_2_, Blocks.field_150350_a.func_176223_P(), 3);
      this.func_176226_b(p_176500_1_, p_176500_2_, p_176500_3_, 0);
   }

   public void func_180653_a(World p_180653_1_, BlockPos p_180653_2_, IBlockState p_180653_3_, float p_180653_4_, int p_180653_5_) {
      int i = ((Integer)p_180653_3_.func_177229_b(field_176501_a)).intValue();
      int j = 1;
      if (i >= 2) {
         j = 3;
      }

      for(int k = 0; k < j; ++k) {
         func_180635_a(p_180653_1_, p_180653_2_, new ItemStack(Items.field_151100_aR, 1, EnumDyeColor.BROWN.func_176767_b()));
      }

   }

   public ItemStack func_185473_a(World p_185473_1_, BlockPos p_185473_2_, IBlockState p_185473_3_) {
      return new ItemStack(Items.field_151100_aR, 1, EnumDyeColor.BROWN.func_176767_b());
   }

   public boolean func_176473_a(World p_176473_1_, BlockPos p_176473_2_, IBlockState p_176473_3_, boolean p_176473_4_) {
      return ((Integer)p_176473_3_.func_177229_b(field_176501_a)).intValue() < 2;
   }

   public boolean func_180670_a(World p_180670_1_, Random p_180670_2_, BlockPos p_180670_3_, IBlockState p_180670_4_) {
      return true;
   }

   public void func_176474_b(World p_176474_1_, Random p_176474_2_, BlockPos p_176474_3_, IBlockState p_176474_4_) {
      p_176474_1_.func_180501_a(p_176474_3_, p_176474_4_.func_177226_a(field_176501_a, Integer.valueOf(((Integer)p_176474_4_.func_177229_b(field_176501_a)).intValue() + 1)), 2);
   }

   public BlockRenderLayer func_180664_k() {
      return BlockRenderLayer.CUTOUT;
   }

   public IBlockState func_176203_a(int p_176203_1_) {
      return this.func_176223_P().func_177226_a(field_185512_D, EnumFacing.func_176731_b(p_176203_1_)).func_177226_a(field_176501_a, Integer.valueOf((p_176203_1_ & 15) >> 2));
   }

   public int func_176201_c(IBlockState p_176201_1_) {
      int i = 0;
      i = i | ((EnumFacing)p_176201_1_.func_177229_b(field_185512_D)).func_176736_b();
      i = i | ((Integer)p_176201_1_.func_177229_b(field_176501_a)).intValue() << 2;
      return i;
   }

   protected BlockStateContainer func_180661_e() {
      return new BlockStateContainer(this, new IProperty[]{field_185512_D, field_176501_a});
   }

   public BlockFaceShape func_193383_a(IBlockAccess p_193383_1_, IBlockState p_193383_2_, BlockPos p_193383_3_, EnumFacing p_193383_4_) {
      return BlockFaceShape.UNDEFINED;
   }
}
