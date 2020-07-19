package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockLadder extends Block {
   public static final PropertyDirection field_176382_a = BlockHorizontal.field_185512_D;
   protected static final AxisAlignedBB field_185687_b = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.1875D, 1.0D, 1.0D);
   protected static final AxisAlignedBB field_185688_c = new AxisAlignedBB(0.8125D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
   protected static final AxisAlignedBB field_185689_d = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.1875D);
   protected static final AxisAlignedBB field_185690_e = new AxisAlignedBB(0.0D, 0.0D, 0.8125D, 1.0D, 1.0D, 1.0D);

   protected BlockLadder() {
      super(Material.field_151594_q);
      this.func_180632_j(this.field_176227_L.func_177621_b().func_177226_a(field_176382_a, EnumFacing.NORTH));
      this.func_149647_a(CreativeTabs.field_78031_c);
   }

   public AxisAlignedBB func_185496_a(IBlockState p_185496_1_, IBlockAccess p_185496_2_, BlockPos p_185496_3_) {
      switch((EnumFacing)p_185496_1_.func_177229_b(field_176382_a)) {
      case NORTH:
         return field_185690_e;
      case SOUTH:
         return field_185689_d;
      case WEST:
         return field_185688_c;
      case EAST:
      default:
         return field_185687_b;
      }
   }

   public boolean func_149662_c(IBlockState p_149662_1_) {
      return false;
   }

   public boolean func_149686_d(IBlockState p_149686_1_) {
      return false;
   }

   public boolean func_176198_a(World p_176198_1_, BlockPos p_176198_2_, EnumFacing p_176198_3_) {
      if (this.func_193392_c(p_176198_1_, p_176198_2_.func_177976_e(), p_176198_3_)) {
         return true;
      } else if (this.func_193392_c(p_176198_1_, p_176198_2_.func_177974_f(), p_176198_3_)) {
         return true;
      } else if (this.func_193392_c(p_176198_1_, p_176198_2_.func_177978_c(), p_176198_3_)) {
         return true;
      } else {
         return this.func_193392_c(p_176198_1_, p_176198_2_.func_177968_d(), p_176198_3_);
      }
   }

   private boolean func_193392_c(World p_193392_1_, BlockPos p_193392_2_, EnumFacing p_193392_3_) {
      IBlockState iblockstate = p_193392_1_.func_180495_p(p_193392_2_);
      boolean flag = func_193382_c(iblockstate.func_177230_c());
      return !flag && iblockstate.func_193401_d(p_193392_1_, p_193392_2_, p_193392_3_) == BlockFaceShape.SOLID && !iblockstate.func_185897_m();
   }

   public IBlockState func_180642_a(World p_180642_1_, BlockPos p_180642_2_, EnumFacing p_180642_3_, float p_180642_4_, float p_180642_5_, float p_180642_6_, int p_180642_7_, EntityLivingBase p_180642_8_) {
      if (p_180642_3_.func_176740_k().func_176722_c() && this.func_193392_c(p_180642_1_, p_180642_2_.func_177972_a(p_180642_3_.func_176734_d()), p_180642_3_)) {
         return this.func_176223_P().func_177226_a(field_176382_a, p_180642_3_);
      } else {
         for(EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
            if (this.func_193392_c(p_180642_1_, p_180642_2_.func_177972_a(enumfacing.func_176734_d()), enumfacing)) {
               return this.func_176223_P().func_177226_a(field_176382_a, enumfacing);
            }
         }

         return this.func_176223_P();
      }
   }

   public void func_189540_a(IBlockState p_189540_1_, World p_189540_2_, BlockPos p_189540_3_, Block p_189540_4_, BlockPos p_189540_5_) {
      EnumFacing enumfacing = (EnumFacing)p_189540_1_.func_177229_b(field_176382_a);
      if (!this.func_193392_c(p_189540_2_, p_189540_3_.func_177972_a(enumfacing.func_176734_d()), enumfacing)) {
         this.func_176226_b(p_189540_2_, p_189540_3_, p_189540_1_, 0);
         p_189540_2_.func_175698_g(p_189540_3_);
      }

      super.func_189540_a(p_189540_1_, p_189540_2_, p_189540_3_, p_189540_4_, p_189540_5_);
   }

   public BlockRenderLayer func_180664_k() {
      return BlockRenderLayer.CUTOUT;
   }

   public IBlockState func_176203_a(int p_176203_1_) {
      EnumFacing enumfacing = EnumFacing.func_82600_a(p_176203_1_);
      if (enumfacing.func_176740_k() == EnumFacing.Axis.Y) {
         enumfacing = EnumFacing.NORTH;
      }

      return this.func_176223_P().func_177226_a(field_176382_a, enumfacing);
   }

   public int func_176201_c(IBlockState p_176201_1_) {
      return ((EnumFacing)p_176201_1_.func_177229_b(field_176382_a)).func_176745_a();
   }

   public IBlockState func_185499_a(IBlockState p_185499_1_, Rotation p_185499_2_) {
      return p_185499_1_.func_177226_a(field_176382_a, p_185499_2_.func_185831_a((EnumFacing)p_185499_1_.func_177229_b(field_176382_a)));
   }

   public IBlockState func_185471_a(IBlockState p_185471_1_, Mirror p_185471_2_) {
      return p_185471_1_.func_185907_a(p_185471_2_.func_185800_a((EnumFacing)p_185471_1_.func_177229_b(field_176382_a)));
   }

   protected BlockStateContainer func_180661_e() {
      return new BlockStateContainer(this, new IProperty[]{field_176382_a});
   }

   public BlockFaceShape func_193383_a(IBlockAccess p_193383_1_, IBlockState p_193383_2_, BlockPos p_193383_3_, EnumFacing p_193383_4_) {
      return BlockFaceShape.UNDEFINED;
   }
}
