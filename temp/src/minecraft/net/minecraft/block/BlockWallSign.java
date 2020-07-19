package net.minecraft.block;

import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockWallSign extends BlockSign {
   public static final PropertyDirection field_176412_a = BlockHorizontal.field_185512_D;
   protected static final AxisAlignedBB field_185578_c = new AxisAlignedBB(0.0D, 0.28125D, 0.0D, 0.125D, 0.78125D, 1.0D);
   protected static final AxisAlignedBB field_185579_d = new AxisAlignedBB(0.875D, 0.28125D, 0.0D, 1.0D, 0.78125D, 1.0D);
   protected static final AxisAlignedBB field_185580_e = new AxisAlignedBB(0.0D, 0.28125D, 0.0D, 1.0D, 0.78125D, 0.125D);
   protected static final AxisAlignedBB field_185581_f = new AxisAlignedBB(0.0D, 0.28125D, 0.875D, 1.0D, 0.78125D, 1.0D);

   public BlockWallSign() {
      this.func_180632_j(this.field_176227_L.func_177621_b().func_177226_a(field_176412_a, EnumFacing.NORTH));
   }

   public AxisAlignedBB func_185496_a(IBlockState p_185496_1_, IBlockAccess p_185496_2_, BlockPos p_185496_3_) {
      switch((EnumFacing)p_185496_1_.func_177229_b(field_176412_a)) {
      case NORTH:
      default:
         return field_185581_f;
      case SOUTH:
         return field_185580_e;
      case WEST:
         return field_185579_d;
      case EAST:
         return field_185578_c;
      }
   }

   public void func_189540_a(IBlockState p_189540_1_, World p_189540_2_, BlockPos p_189540_3_, Block p_189540_4_, BlockPos p_189540_5_) {
      EnumFacing enumfacing = (EnumFacing)p_189540_1_.func_177229_b(field_176412_a);
      if (!p_189540_2_.func_180495_p(p_189540_3_.func_177972_a(enumfacing.func_176734_d())).func_185904_a().func_76220_a()) {
         this.func_176226_b(p_189540_2_, p_189540_3_, p_189540_1_, 0);
         p_189540_2_.func_175698_g(p_189540_3_);
      }

      super.func_189540_a(p_189540_1_, p_189540_2_, p_189540_3_, p_189540_4_, p_189540_5_);
   }

   public IBlockState func_176203_a(int p_176203_1_) {
      EnumFacing enumfacing = EnumFacing.func_82600_a(p_176203_1_);
      if (enumfacing.func_176740_k() == EnumFacing.Axis.Y) {
         enumfacing = EnumFacing.NORTH;
      }

      return this.func_176223_P().func_177226_a(field_176412_a, enumfacing);
   }

   public int func_176201_c(IBlockState p_176201_1_) {
      return ((EnumFacing)p_176201_1_.func_177229_b(field_176412_a)).func_176745_a();
   }

   public IBlockState func_185499_a(IBlockState p_185499_1_, Rotation p_185499_2_) {
      return p_185499_1_.func_177226_a(field_176412_a, p_185499_2_.func_185831_a((EnumFacing)p_185499_1_.func_177229_b(field_176412_a)));
   }

   public IBlockState func_185471_a(IBlockState p_185471_1_, Mirror p_185471_2_) {
      return p_185471_1_.func_185907_a(p_185471_2_.func_185800_a((EnumFacing)p_185471_1_.func_177229_b(field_176412_a)));
   }

   protected BlockStateContainer func_180661_e() {
      return new BlockStateContainer(this, new IProperty[]{field_176412_a});
   }
}
