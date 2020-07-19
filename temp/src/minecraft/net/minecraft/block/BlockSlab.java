package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public abstract class BlockSlab extends Block {
   public static final PropertyEnum<BlockSlab.EnumBlockHalf> field_176554_a = PropertyEnum.<BlockSlab.EnumBlockHalf>func_177709_a("half", BlockSlab.EnumBlockHalf.class);
   protected static final AxisAlignedBB field_185676_b = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.5D, 1.0D);
   protected static final AxisAlignedBB field_185677_c = new AxisAlignedBB(0.0D, 0.5D, 0.0D, 1.0D, 1.0D, 1.0D);

   public BlockSlab(Material p_i45714_1_) {
      this(p_i45714_1_, p_i45714_1_.func_151565_r());
   }

   public BlockSlab(Material p_i47249_1_, MapColor p_i47249_2_) {
      super(p_i47249_1_, p_i47249_2_);
      this.field_149787_q = this.func_176552_j();
      this.func_149713_g(255);
   }

   protected boolean func_149700_E() {
      return false;
   }

   public AxisAlignedBB func_185496_a(IBlockState p_185496_1_, IBlockAccess p_185496_2_, BlockPos p_185496_3_) {
      if (this.func_176552_j()) {
         return field_185505_j;
      } else {
         return p_185496_1_.func_177229_b(field_176554_a) == BlockSlab.EnumBlockHalf.TOP ? field_185677_c : field_185676_b;
      }
   }

   public boolean func_185481_k(IBlockState p_185481_1_) {
      return ((BlockSlab)p_185481_1_.func_177230_c()).func_176552_j() || p_185481_1_.func_177229_b(field_176554_a) == BlockSlab.EnumBlockHalf.TOP;
   }

   public BlockFaceShape func_193383_a(IBlockAccess p_193383_1_, IBlockState p_193383_2_, BlockPos p_193383_3_, EnumFacing p_193383_4_) {
      if (((BlockSlab)p_193383_2_.func_177230_c()).func_176552_j()) {
         return BlockFaceShape.SOLID;
      } else if (p_193383_4_ == EnumFacing.UP && p_193383_2_.func_177229_b(field_176554_a) == BlockSlab.EnumBlockHalf.TOP) {
         return BlockFaceShape.SOLID;
      } else {
         return p_193383_4_ == EnumFacing.DOWN && p_193383_2_.func_177229_b(field_176554_a) == BlockSlab.EnumBlockHalf.BOTTOM ? BlockFaceShape.SOLID : BlockFaceShape.UNDEFINED;
      }
   }

   public boolean func_149662_c(IBlockState p_149662_1_) {
      return this.func_176552_j();
   }

   public IBlockState func_180642_a(World p_180642_1_, BlockPos p_180642_2_, EnumFacing p_180642_3_, float p_180642_4_, float p_180642_5_, float p_180642_6_, int p_180642_7_, EntityLivingBase p_180642_8_) {
      IBlockState iblockstate = super.func_180642_a(p_180642_1_, p_180642_2_, p_180642_3_, p_180642_4_, p_180642_5_, p_180642_6_, p_180642_7_, p_180642_8_).func_177226_a(field_176554_a, BlockSlab.EnumBlockHalf.BOTTOM);
      if (this.func_176552_j()) {
         return iblockstate;
      } else {
         return p_180642_3_ != EnumFacing.DOWN && (p_180642_3_ == EnumFacing.UP || (double)p_180642_5_ <= 0.5D) ? iblockstate : iblockstate.func_177226_a(field_176554_a, BlockSlab.EnumBlockHalf.TOP);
      }
   }

   public int func_149745_a(Random p_149745_1_) {
      return this.func_176552_j() ? 2 : 1;
   }

   public boolean func_149686_d(IBlockState p_149686_1_) {
      return this.func_176552_j();
   }

   public boolean func_176225_a(IBlockState p_176225_1_, IBlockAccess p_176225_2_, BlockPos p_176225_3_, EnumFacing p_176225_4_) {
      if (this.func_176552_j()) {
         return super.func_176225_a(p_176225_1_, p_176225_2_, p_176225_3_, p_176225_4_);
      } else if (p_176225_4_ != EnumFacing.UP && p_176225_4_ != EnumFacing.DOWN && !super.func_176225_a(p_176225_1_, p_176225_2_, p_176225_3_, p_176225_4_)) {
         return false;
      } else {
         IBlockState iblockstate = p_176225_2_.func_180495_p(p_176225_3_.func_177972_a(p_176225_4_));
         boolean flag = func_185675_i(iblockstate) && iblockstate.func_177229_b(field_176554_a) == BlockSlab.EnumBlockHalf.TOP;
         boolean flag1 = func_185675_i(p_176225_1_) && p_176225_1_.func_177229_b(field_176554_a) == BlockSlab.EnumBlockHalf.TOP;
         if (flag1) {
            if (p_176225_4_ == EnumFacing.DOWN) {
               return true;
            } else if (p_176225_4_ == EnumFacing.UP && super.func_176225_a(p_176225_1_, p_176225_2_, p_176225_3_, p_176225_4_)) {
               return true;
            } else {
               return !func_185675_i(iblockstate) || !flag;
            }
         } else if (p_176225_4_ == EnumFacing.UP) {
            return true;
         } else if (p_176225_4_ == EnumFacing.DOWN && super.func_176225_a(p_176225_1_, p_176225_2_, p_176225_3_, p_176225_4_)) {
            return true;
         } else {
            return !func_185675_i(iblockstate) || flag;
         }
      }
   }

   protected static boolean func_185675_i(IBlockState p_185675_0_) {
      Block block = p_185675_0_.func_177230_c();
      return block == Blocks.field_150333_U || block == Blocks.field_150376_bx || block == Blocks.field_180389_cP || block == Blocks.field_185771_cX;
   }

   public abstract String func_150002_b(int var1);

   public abstract boolean func_176552_j();

   public abstract IProperty<?> func_176551_l();

   public abstract Comparable<?> func_185674_a(ItemStack var1);

   public static enum EnumBlockHalf implements IStringSerializable {
      TOP("top"),
      BOTTOM("bottom");

      private final String field_176988_c;

      private EnumBlockHalf(String p_i45713_3_) {
         this.field_176988_c = p_i45713_3_;
      }

      public String toString() {
         return this.field_176988_c;
      }

      public String func_176610_l() {
         return this.field_176988_c;
      }
   }
}
