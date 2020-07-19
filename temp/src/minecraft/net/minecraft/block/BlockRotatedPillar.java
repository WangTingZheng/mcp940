package net.minecraft.block;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockRotatedPillar extends Block {
   public static final PropertyEnum<EnumFacing.Axis> field_176298_M = PropertyEnum.<EnumFacing.Axis>func_177709_a("axis", EnumFacing.Axis.class);

   protected BlockRotatedPillar(Material p_i45425_1_) {
      super(p_i45425_1_, p_i45425_1_.func_151565_r());
   }

   protected BlockRotatedPillar(Material p_i46385_1_, MapColor p_i46385_2_) {
      super(p_i46385_1_, p_i46385_2_);
   }

   public IBlockState func_185499_a(IBlockState p_185499_1_, Rotation p_185499_2_) {
      switch(p_185499_2_) {
      case COUNTERCLOCKWISE_90:
      case CLOCKWISE_90:
         switch((EnumFacing.Axis)p_185499_1_.func_177229_b(field_176298_M)) {
         case X:
            return p_185499_1_.func_177226_a(field_176298_M, EnumFacing.Axis.Z);
         case Z:
            return p_185499_1_.func_177226_a(field_176298_M, EnumFacing.Axis.X);
         default:
            return p_185499_1_;
         }
      default:
         return p_185499_1_;
      }
   }

   public IBlockState func_176203_a(int p_176203_1_) {
      EnumFacing.Axis enumfacing$axis = EnumFacing.Axis.Y;
      int i = p_176203_1_ & 12;
      if (i == 4) {
         enumfacing$axis = EnumFacing.Axis.X;
      } else if (i == 8) {
         enumfacing$axis = EnumFacing.Axis.Z;
      }

      return this.func_176223_P().func_177226_a(field_176298_M, enumfacing$axis);
   }

   public int func_176201_c(IBlockState p_176201_1_) {
      int i = 0;
      EnumFacing.Axis enumfacing$axis = (EnumFacing.Axis)p_176201_1_.func_177229_b(field_176298_M);
      if (enumfacing$axis == EnumFacing.Axis.X) {
         i |= 4;
      } else if (enumfacing$axis == EnumFacing.Axis.Z) {
         i |= 8;
      }

      return i;
   }

   protected BlockStateContainer func_180661_e() {
      return new BlockStateContainer(this, new IProperty[]{field_176298_M});
   }

   protected ItemStack func_180643_i(IBlockState p_180643_1_) {
      return new ItemStack(Item.func_150898_a(this));
   }

   public IBlockState func_180642_a(World p_180642_1_, BlockPos p_180642_2_, EnumFacing p_180642_3_, float p_180642_4_, float p_180642_5_, float p_180642_6_, int p_180642_7_, EntityLivingBase p_180642_8_) {
      return super.func_180642_a(p_180642_1_, p_180642_2_, p_180642_3_, p_180642_4_, p_180642_5_, p_180642_6_, p_180642_7_, p_180642_8_).func_177226_a(field_176298_M, p_180642_3_.func_176740_k());
   }
}
