package net.minecraft.block;

import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.material.MapColor;
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
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockChorusPlant extends Block {
   public static final PropertyBool field_185609_a = PropertyBool.func_177716_a("north");
   public static final PropertyBool field_185610_b = PropertyBool.func_177716_a("east");
   public static final PropertyBool field_185611_c = PropertyBool.func_177716_a("south");
   public static final PropertyBool field_185612_d = PropertyBool.func_177716_a("west");
   public static final PropertyBool field_185613_e = PropertyBool.func_177716_a("up");
   public static final PropertyBool field_185614_f = PropertyBool.func_177716_a("down");

   protected BlockChorusPlant() {
      super(Material.field_151585_k, MapColor.field_151678_z);
      this.func_149647_a(CreativeTabs.field_78031_c);
      this.func_180632_j(this.field_176227_L.func_177621_b().func_177226_a(field_185609_a, Boolean.valueOf(false)).func_177226_a(field_185610_b, Boolean.valueOf(false)).func_177226_a(field_185611_c, Boolean.valueOf(false)).func_177226_a(field_185612_d, Boolean.valueOf(false)).func_177226_a(field_185613_e, Boolean.valueOf(false)).func_177226_a(field_185614_f, Boolean.valueOf(false)));
   }

   public IBlockState func_176221_a(IBlockState p_176221_1_, IBlockAccess p_176221_2_, BlockPos p_176221_3_) {
      Block block = p_176221_2_.func_180495_p(p_176221_3_.func_177977_b()).func_177230_c();
      Block block1 = p_176221_2_.func_180495_p(p_176221_3_.func_177984_a()).func_177230_c();
      Block block2 = p_176221_2_.func_180495_p(p_176221_3_.func_177978_c()).func_177230_c();
      Block block3 = p_176221_2_.func_180495_p(p_176221_3_.func_177974_f()).func_177230_c();
      Block block4 = p_176221_2_.func_180495_p(p_176221_3_.func_177968_d()).func_177230_c();
      Block block5 = p_176221_2_.func_180495_p(p_176221_3_.func_177976_e()).func_177230_c();
      return p_176221_1_.func_177226_a(field_185614_f, Boolean.valueOf(block == this || block == Blocks.field_185766_cS || block == Blocks.field_150377_bs)).func_177226_a(field_185613_e, Boolean.valueOf(block1 == this || block1 == Blocks.field_185766_cS)).func_177226_a(field_185609_a, Boolean.valueOf(block2 == this || block2 == Blocks.field_185766_cS)).func_177226_a(field_185610_b, Boolean.valueOf(block3 == this || block3 == Blocks.field_185766_cS)).func_177226_a(field_185611_c, Boolean.valueOf(block4 == this || block4 == Blocks.field_185766_cS)).func_177226_a(field_185612_d, Boolean.valueOf(block5 == this || block5 == Blocks.field_185766_cS));
   }

   public AxisAlignedBB func_185496_a(IBlockState p_185496_1_, IBlockAccess p_185496_2_, BlockPos p_185496_3_) {
      p_185496_1_ = p_185496_1_.func_185899_b(p_185496_2_, p_185496_3_);
      float f = 0.1875F;
      float f1 = ((Boolean)p_185496_1_.func_177229_b(field_185612_d)).booleanValue() ? 0.0F : 0.1875F;
      float f2 = ((Boolean)p_185496_1_.func_177229_b(field_185614_f)).booleanValue() ? 0.0F : 0.1875F;
      float f3 = ((Boolean)p_185496_1_.func_177229_b(field_185609_a)).booleanValue() ? 0.0F : 0.1875F;
      float f4 = ((Boolean)p_185496_1_.func_177229_b(field_185610_b)).booleanValue() ? 1.0F : 0.8125F;
      float f5 = ((Boolean)p_185496_1_.func_177229_b(field_185613_e)).booleanValue() ? 1.0F : 0.8125F;
      float f6 = ((Boolean)p_185496_1_.func_177229_b(field_185611_c)).booleanValue() ? 1.0F : 0.8125F;
      return new AxisAlignedBB((double)f1, (double)f2, (double)f3, (double)f4, (double)f5, (double)f6);
   }

   public void func_185477_a(IBlockState p_185477_1_, World p_185477_2_, BlockPos p_185477_3_, AxisAlignedBB p_185477_4_, List<AxisAlignedBB> p_185477_5_, @Nullable Entity p_185477_6_, boolean p_185477_7_) {
      if (!p_185477_7_) {
         p_185477_1_ = p_185477_1_.func_185899_b(p_185477_2_, p_185477_3_);
      }

      float f = 0.1875F;
      float f1 = 0.8125F;
      func_185492_a(p_185477_3_, p_185477_4_, p_185477_5_, new AxisAlignedBB(0.1875D, 0.1875D, 0.1875D, 0.8125D, 0.8125D, 0.8125D));
      if (((Boolean)p_185477_1_.func_177229_b(field_185612_d)).booleanValue()) {
         func_185492_a(p_185477_3_, p_185477_4_, p_185477_5_, new AxisAlignedBB(0.0D, 0.1875D, 0.1875D, 0.1875D, 0.8125D, 0.8125D));
      }

      if (((Boolean)p_185477_1_.func_177229_b(field_185610_b)).booleanValue()) {
         func_185492_a(p_185477_3_, p_185477_4_, p_185477_5_, new AxisAlignedBB(0.8125D, 0.1875D, 0.1875D, 1.0D, 0.8125D, 0.8125D));
      }

      if (((Boolean)p_185477_1_.func_177229_b(field_185613_e)).booleanValue()) {
         func_185492_a(p_185477_3_, p_185477_4_, p_185477_5_, new AxisAlignedBB(0.1875D, 0.8125D, 0.1875D, 0.8125D, 1.0D, 0.8125D));
      }

      if (((Boolean)p_185477_1_.func_177229_b(field_185614_f)).booleanValue()) {
         func_185492_a(p_185477_3_, p_185477_4_, p_185477_5_, new AxisAlignedBB(0.1875D, 0.0D, 0.1875D, 0.8125D, 0.1875D, 0.8125D));
      }

      if (((Boolean)p_185477_1_.func_177229_b(field_185609_a)).booleanValue()) {
         func_185492_a(p_185477_3_, p_185477_4_, p_185477_5_, new AxisAlignedBB(0.1875D, 0.1875D, 0.0D, 0.8125D, 0.8125D, 0.1875D));
      }

      if (((Boolean)p_185477_1_.func_177229_b(field_185611_c)).booleanValue()) {
         func_185492_a(p_185477_3_, p_185477_4_, p_185477_5_, new AxisAlignedBB(0.1875D, 0.1875D, 0.8125D, 0.8125D, 0.8125D, 1.0D));
      }

   }

   public int func_176201_c(IBlockState p_176201_1_) {
      return 0;
   }

   public void func_180650_b(World p_180650_1_, BlockPos p_180650_2_, IBlockState p_180650_3_, Random p_180650_4_) {
      if (!this.func_185608_b(p_180650_1_, p_180650_2_)) {
         p_180650_1_.func_175655_b(p_180650_2_, true);
      }

   }

   public Item func_180660_a(IBlockState p_180660_1_, Random p_180660_2_, int p_180660_3_) {
      return Items.field_185161_cS;
   }

   public int func_149745_a(Random p_149745_1_) {
      return p_149745_1_.nextInt(2);
   }

   public boolean func_149686_d(IBlockState p_149686_1_) {
      return false;
   }

   public boolean func_149662_c(IBlockState p_149662_1_) {
      return false;
   }

   public boolean func_176196_c(World p_176196_1_, BlockPos p_176196_2_) {
      return super.func_176196_c(p_176196_1_, p_176196_2_) ? this.func_185608_b(p_176196_1_, p_176196_2_) : false;
   }

   public void func_189540_a(IBlockState p_189540_1_, World p_189540_2_, BlockPos p_189540_3_, Block p_189540_4_, BlockPos p_189540_5_) {
      if (!this.func_185608_b(p_189540_2_, p_189540_3_)) {
         p_189540_2_.func_175684_a(p_189540_3_, this, 1);
      }

   }

   public boolean func_185608_b(World p_185608_1_, BlockPos p_185608_2_) {
      boolean flag = p_185608_1_.func_175623_d(p_185608_2_.func_177984_a());
      boolean flag1 = p_185608_1_.func_175623_d(p_185608_2_.func_177977_b());

      for(EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
         BlockPos blockpos = p_185608_2_.func_177972_a(enumfacing);
         Block block = p_185608_1_.func_180495_p(blockpos).func_177230_c();
         if (block == this) {
            if (!flag && !flag1) {
               return false;
            }

            Block block1 = p_185608_1_.func_180495_p(blockpos.func_177977_b()).func_177230_c();
            if (block1 == this || block1 == Blocks.field_150377_bs) {
               return true;
            }
         }
      }

      Block block2 = p_185608_1_.func_180495_p(p_185608_2_.func_177977_b()).func_177230_c();
      return block2 == this || block2 == Blocks.field_150377_bs;
   }

   public BlockRenderLayer func_180664_k() {
      return BlockRenderLayer.CUTOUT;
   }

   public boolean func_176225_a(IBlockState p_176225_1_, IBlockAccess p_176225_2_, BlockPos p_176225_3_, EnumFacing p_176225_4_) {
      Block block = p_176225_2_.func_180495_p(p_176225_3_.func_177972_a(p_176225_4_)).func_177230_c();
      return block != this && block != Blocks.field_185766_cS && (p_176225_4_ != EnumFacing.DOWN || block != Blocks.field_150377_bs);
   }

   protected BlockStateContainer func_180661_e() {
      return new BlockStateContainer(this, new IProperty[]{field_185609_a, field_185610_b, field_185611_c, field_185612_d, field_185613_e, field_185614_f});
   }

   public boolean func_176205_b(IBlockAccess p_176205_1_, BlockPos p_176205_2_) {
      return false;
   }

   public BlockFaceShape func_193383_a(IBlockAccess p_193383_1_, IBlockState p_193383_2_, BlockPos p_193383_3_, EnumFacing p_193383_4_) {
      return BlockFaceShape.UNDEFINED;
   }
}
