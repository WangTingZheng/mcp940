package net.minecraft.block;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockStem extends BlockBush implements IGrowable {
   public static final PropertyInteger field_176484_a = PropertyInteger.func_177719_a("age", 0, 7);
   public static final PropertyDirection field_176483_b = BlockTorch.field_176596_a;
   private final Block field_149877_a;
   protected static final AxisAlignedBB[] field_185521_d = new AxisAlignedBB[]{new AxisAlignedBB(0.375D, 0.0D, 0.375D, 0.625D, 0.125D, 0.625D), new AxisAlignedBB(0.375D, 0.0D, 0.375D, 0.625D, 0.25D, 0.625D), new AxisAlignedBB(0.375D, 0.0D, 0.375D, 0.625D, 0.375D, 0.625D), new AxisAlignedBB(0.375D, 0.0D, 0.375D, 0.625D, 0.5D, 0.625D), new AxisAlignedBB(0.375D, 0.0D, 0.375D, 0.625D, 0.625D, 0.625D), new AxisAlignedBB(0.375D, 0.0D, 0.375D, 0.625D, 0.75D, 0.625D), new AxisAlignedBB(0.375D, 0.0D, 0.375D, 0.625D, 0.875D, 0.625D), new AxisAlignedBB(0.375D, 0.0D, 0.375D, 0.625D, 1.0D, 0.625D)};

   protected BlockStem(Block p_i45430_1_) {
      this.func_180632_j(this.field_176227_L.func_177621_b().func_177226_a(field_176484_a, Integer.valueOf(0)).func_177226_a(field_176483_b, EnumFacing.UP));
      this.field_149877_a = p_i45430_1_;
      this.func_149675_a(true);
      this.func_149647_a((CreativeTabs)null);
   }

   public AxisAlignedBB func_185496_a(IBlockState p_185496_1_, IBlockAccess p_185496_2_, BlockPos p_185496_3_) {
      return field_185521_d[((Integer)p_185496_1_.func_177229_b(field_176484_a)).intValue()];
   }

   public IBlockState func_176221_a(IBlockState p_176221_1_, IBlockAccess p_176221_2_, BlockPos p_176221_3_) {
      int i = ((Integer)p_176221_1_.func_177229_b(field_176484_a)).intValue();
      p_176221_1_ = p_176221_1_.func_177226_a(field_176483_b, EnumFacing.UP);

      for(EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
         if (p_176221_2_.func_180495_p(p_176221_3_.func_177972_a(enumfacing)).func_177230_c() == this.field_149877_a && i == 7) {
            p_176221_1_ = p_176221_1_.func_177226_a(field_176483_b, enumfacing);
            break;
         }
      }

      return p_176221_1_;
   }

   protected boolean func_185514_i(IBlockState p_185514_1_) {
      return p_185514_1_.func_177230_c() == Blocks.field_150458_ak;
   }

   public void func_180650_b(World p_180650_1_, BlockPos p_180650_2_, IBlockState p_180650_3_, Random p_180650_4_) {
      super.func_180650_b(p_180650_1_, p_180650_2_, p_180650_3_, p_180650_4_);
      if (p_180650_1_.func_175671_l(p_180650_2_.func_177984_a()) >= 9) {
         float f = BlockCrops.func_180672_a(this, p_180650_1_, p_180650_2_);
         if (p_180650_4_.nextInt((int)(25.0F / f) + 1) == 0) {
            int i = ((Integer)p_180650_3_.func_177229_b(field_176484_a)).intValue();
            if (i < 7) {
               p_180650_3_ = p_180650_3_.func_177226_a(field_176484_a, Integer.valueOf(i + 1));
               p_180650_1_.func_180501_a(p_180650_2_, p_180650_3_, 2);
            } else {
               for(EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
                  if (p_180650_1_.func_180495_p(p_180650_2_.func_177972_a(enumfacing)).func_177230_c() == this.field_149877_a) {
                     return;
                  }
               }

               p_180650_2_ = p_180650_2_.func_177972_a(EnumFacing.Plane.HORIZONTAL.func_179518_a(p_180650_4_));
               Block block = p_180650_1_.func_180495_p(p_180650_2_.func_177977_b()).func_177230_c();
               if (p_180650_1_.func_180495_p(p_180650_2_).func_177230_c().field_149764_J == Material.field_151579_a && (block == Blocks.field_150458_ak || block == Blocks.field_150346_d || block == Blocks.field_150349_c)) {
                  p_180650_1_.func_175656_a(p_180650_2_, this.field_149877_a.func_176223_P());
               }
            }
         }

      }
   }

   public void func_176482_g(World p_176482_1_, BlockPos p_176482_2_, IBlockState p_176482_3_) {
      int i = ((Integer)p_176482_3_.func_177229_b(field_176484_a)).intValue() + MathHelper.func_76136_a(p_176482_1_.field_73012_v, 2, 5);
      p_176482_1_.func_180501_a(p_176482_2_, p_176482_3_.func_177226_a(field_176484_a, Integer.valueOf(Math.min(7, i))), 2);
   }

   public void func_180653_a(World p_180653_1_, BlockPos p_180653_2_, IBlockState p_180653_3_, float p_180653_4_, int p_180653_5_) {
      super.func_180653_a(p_180653_1_, p_180653_2_, p_180653_3_, p_180653_4_, p_180653_5_);
      if (!p_180653_1_.field_72995_K) {
         Item item = this.func_176481_j();
         if (item != null) {
            int i = ((Integer)p_180653_3_.func_177229_b(field_176484_a)).intValue();

            for(int j = 0; j < 3; ++j) {
               if (p_180653_1_.field_73012_v.nextInt(15) <= i) {
                  func_180635_a(p_180653_1_, p_180653_2_, new ItemStack(item));
               }
            }

         }
      }
   }

   @Nullable
   protected Item func_176481_j() {
      if (this.field_149877_a == Blocks.field_150423_aK) {
         return Items.field_151080_bb;
      } else {
         return this.field_149877_a == Blocks.field_150440_ba ? Items.field_151081_bc : null;
      }
   }

   public Item func_180660_a(IBlockState p_180660_1_, Random p_180660_2_, int p_180660_3_) {
      return Items.field_190931_a;
   }

   public ItemStack func_185473_a(World p_185473_1_, BlockPos p_185473_2_, IBlockState p_185473_3_) {
      Item item = this.func_176481_j();
      return item == null ? ItemStack.field_190927_a : new ItemStack(item);
   }

   public boolean func_176473_a(World p_176473_1_, BlockPos p_176473_2_, IBlockState p_176473_3_, boolean p_176473_4_) {
      return ((Integer)p_176473_3_.func_177229_b(field_176484_a)).intValue() != 7;
   }

   public boolean func_180670_a(World p_180670_1_, Random p_180670_2_, BlockPos p_180670_3_, IBlockState p_180670_4_) {
      return true;
   }

   public void func_176474_b(World p_176474_1_, Random p_176474_2_, BlockPos p_176474_3_, IBlockState p_176474_4_) {
      this.func_176482_g(p_176474_1_, p_176474_3_, p_176474_4_);
   }

   public IBlockState func_176203_a(int p_176203_1_) {
      return this.func_176223_P().func_177226_a(field_176484_a, Integer.valueOf(p_176203_1_));
   }

   public int func_176201_c(IBlockState p_176201_1_) {
      return ((Integer)p_176201_1_.func_177229_b(field_176484_a)).intValue();
   }

   protected BlockStateContainer func_180661_e() {
      return new BlockStateContainer(this, new IProperty[]{field_176484_a, field_176483_b});
   }
}
