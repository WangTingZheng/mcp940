package net.minecraft.block;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockReed extends Block {
   public static final PropertyInteger field_176355_a = PropertyInteger.func_177719_a("age", 0, 15);
   protected static final AxisAlignedBB field_185701_b = new AxisAlignedBB(0.125D, 0.0D, 0.125D, 0.875D, 1.0D, 0.875D);

   protected BlockReed() {
      super(Material.field_151585_k);
      this.func_180632_j(this.field_176227_L.func_177621_b().func_177226_a(field_176355_a, Integer.valueOf(0)));
      this.func_149675_a(true);
   }

   public AxisAlignedBB func_185496_a(IBlockState p_185496_1_, IBlockAccess p_185496_2_, BlockPos p_185496_3_) {
      return field_185701_b;
   }

   public void func_180650_b(World p_180650_1_, BlockPos p_180650_2_, IBlockState p_180650_3_, Random p_180650_4_) {
      if (p_180650_1_.func_180495_p(p_180650_2_.func_177977_b()).func_177230_c() == Blocks.field_150436_aH || this.func_176353_e(p_180650_1_, p_180650_2_, p_180650_3_)) {
         if (p_180650_1_.func_175623_d(p_180650_2_.func_177984_a())) {
            int i;
            for(i = 1; p_180650_1_.func_180495_p(p_180650_2_.func_177979_c(i)).func_177230_c() == this; ++i) {
               ;
            }

            if (i < 3) {
               int j = ((Integer)p_180650_3_.func_177229_b(field_176355_a)).intValue();
               if (j == 15) {
                  p_180650_1_.func_175656_a(p_180650_2_.func_177984_a(), this.func_176223_P());
                  p_180650_1_.func_180501_a(p_180650_2_, p_180650_3_.func_177226_a(field_176355_a, Integer.valueOf(0)), 4);
               } else {
                  p_180650_1_.func_180501_a(p_180650_2_, p_180650_3_.func_177226_a(field_176355_a, Integer.valueOf(j + 1)), 4);
               }
            }
         }

      }
   }

   public boolean func_176196_c(World p_176196_1_, BlockPos p_176196_2_) {
      Block block = p_176196_1_.func_180495_p(p_176196_2_.func_177977_b()).func_177230_c();
      if (block == this) {
         return true;
      } else if (block != Blocks.field_150349_c && block != Blocks.field_150346_d && block != Blocks.field_150354_m) {
         return false;
      } else {
         BlockPos blockpos = p_176196_2_.func_177977_b();

         for(EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
            IBlockState iblockstate = p_176196_1_.func_180495_p(blockpos.func_177972_a(enumfacing));
            if (iblockstate.func_185904_a() == Material.field_151586_h || iblockstate.func_177230_c() == Blocks.field_185778_de) {
               return true;
            }
         }

         return false;
      }
   }

   public void func_189540_a(IBlockState p_189540_1_, World p_189540_2_, BlockPos p_189540_3_, Block p_189540_4_, BlockPos p_189540_5_) {
      this.func_176353_e(p_189540_2_, p_189540_3_, p_189540_1_);
   }

   protected final boolean func_176353_e(World p_176353_1_, BlockPos p_176353_2_, IBlockState p_176353_3_) {
      if (this.func_176354_d(p_176353_1_, p_176353_2_)) {
         return true;
      } else {
         this.func_176226_b(p_176353_1_, p_176353_2_, p_176353_3_, 0);
         p_176353_1_.func_175698_g(p_176353_2_);
         return false;
      }
   }

   public boolean func_176354_d(World p_176354_1_, BlockPos p_176354_2_) {
      return this.func_176196_c(p_176354_1_, p_176354_2_);
   }

   @Nullable
   public AxisAlignedBB func_180646_a(IBlockState p_180646_1_, IBlockAccess p_180646_2_, BlockPos p_180646_3_) {
      return field_185506_k;
   }

   public Item func_180660_a(IBlockState p_180660_1_, Random p_180660_2_, int p_180660_3_) {
      return Items.field_151120_aE;
   }

   public boolean func_149662_c(IBlockState p_149662_1_) {
      return false;
   }

   public boolean func_149686_d(IBlockState p_149686_1_) {
      return false;
   }

   public ItemStack func_185473_a(World p_185473_1_, BlockPos p_185473_2_, IBlockState p_185473_3_) {
      return new ItemStack(Items.field_151120_aE);
   }

   public BlockRenderLayer func_180664_k() {
      return BlockRenderLayer.CUTOUT;
   }

   public IBlockState func_176203_a(int p_176203_1_) {
      return this.func_176223_P().func_177226_a(field_176355_a, Integer.valueOf(p_176203_1_));
   }

   public int func_176201_c(IBlockState p_176201_1_) {
      return ((Integer)p_176201_1_.func_177229_b(field_176355_a)).intValue();
   }

   protected BlockStateContainer func_180661_e() {
      return new BlockStateContainer(this, new IProperty[]{field_176355_a});
   }

   public BlockFaceShape func_193383_a(IBlockAccess p_193383_1_, IBlockState p_193383_2_, BlockPos p_193383_3_, EnumFacing p_193383_4_) {
      return BlockFaceShape.UNDEFINED;
   }
}
