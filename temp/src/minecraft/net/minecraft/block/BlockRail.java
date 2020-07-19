package net.minecraft.block;

import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockRail extends BlockRailBase {
   public static final PropertyEnum<BlockRailBase.EnumRailDirection> field_176565_b = PropertyEnum.<BlockRailBase.EnumRailDirection>func_177709_a("shape", BlockRailBase.EnumRailDirection.class);

   protected BlockRail() {
      super(false);
      this.func_180632_j(this.field_176227_L.func_177621_b().func_177226_a(field_176565_b, BlockRailBase.EnumRailDirection.NORTH_SOUTH));
   }

   protected void func_189541_b(IBlockState p_189541_1_, World p_189541_2_, BlockPos p_189541_3_, Block p_189541_4_) {
      if (p_189541_4_.func_176223_P().func_185897_m() && (new BlockRailBase.Rail(p_189541_2_, p_189541_3_, p_189541_1_)).func_150650_a() == 3) {
         this.func_176564_a(p_189541_2_, p_189541_3_, p_189541_1_, false);
      }

   }

   public IProperty<BlockRailBase.EnumRailDirection> func_176560_l() {
      return field_176565_b;
   }

   public IBlockState func_176203_a(int p_176203_1_) {
      return this.func_176223_P().func_177226_a(field_176565_b, BlockRailBase.EnumRailDirection.func_177016_a(p_176203_1_));
   }

   public int func_176201_c(IBlockState p_176201_1_) {
      return ((BlockRailBase.EnumRailDirection)p_176201_1_.func_177229_b(field_176565_b)).func_177015_a();
   }

   public IBlockState func_185499_a(IBlockState p_185499_1_, Rotation p_185499_2_) {
      switch(p_185499_2_) {
      case CLOCKWISE_180:
         switch((BlockRailBase.EnumRailDirection)p_185499_1_.func_177229_b(field_176565_b)) {
         case ASCENDING_EAST:
            return p_185499_1_.func_177226_a(field_176565_b, BlockRailBase.EnumRailDirection.ASCENDING_WEST);
         case ASCENDING_WEST:
            return p_185499_1_.func_177226_a(field_176565_b, BlockRailBase.EnumRailDirection.ASCENDING_EAST);
         case ASCENDING_NORTH:
            return p_185499_1_.func_177226_a(field_176565_b, BlockRailBase.EnumRailDirection.ASCENDING_SOUTH);
         case ASCENDING_SOUTH:
            return p_185499_1_.func_177226_a(field_176565_b, BlockRailBase.EnumRailDirection.ASCENDING_NORTH);
         case SOUTH_EAST:
            return p_185499_1_.func_177226_a(field_176565_b, BlockRailBase.EnumRailDirection.NORTH_WEST);
         case SOUTH_WEST:
            return p_185499_1_.func_177226_a(field_176565_b, BlockRailBase.EnumRailDirection.NORTH_EAST);
         case NORTH_WEST:
            return p_185499_1_.func_177226_a(field_176565_b, BlockRailBase.EnumRailDirection.SOUTH_EAST);
         case NORTH_EAST:
            return p_185499_1_.func_177226_a(field_176565_b, BlockRailBase.EnumRailDirection.SOUTH_WEST);
         }
      case COUNTERCLOCKWISE_90:
         switch((BlockRailBase.EnumRailDirection)p_185499_1_.func_177229_b(field_176565_b)) {
         case ASCENDING_EAST:
            return p_185499_1_.func_177226_a(field_176565_b, BlockRailBase.EnumRailDirection.ASCENDING_NORTH);
         case ASCENDING_WEST:
            return p_185499_1_.func_177226_a(field_176565_b, BlockRailBase.EnumRailDirection.ASCENDING_SOUTH);
         case ASCENDING_NORTH:
            return p_185499_1_.func_177226_a(field_176565_b, BlockRailBase.EnumRailDirection.ASCENDING_WEST);
         case ASCENDING_SOUTH:
            return p_185499_1_.func_177226_a(field_176565_b, BlockRailBase.EnumRailDirection.ASCENDING_EAST);
         case SOUTH_EAST:
            return p_185499_1_.func_177226_a(field_176565_b, BlockRailBase.EnumRailDirection.NORTH_EAST);
         case SOUTH_WEST:
            return p_185499_1_.func_177226_a(field_176565_b, BlockRailBase.EnumRailDirection.SOUTH_EAST);
         case NORTH_WEST:
            return p_185499_1_.func_177226_a(field_176565_b, BlockRailBase.EnumRailDirection.SOUTH_WEST);
         case NORTH_EAST:
            return p_185499_1_.func_177226_a(field_176565_b, BlockRailBase.EnumRailDirection.NORTH_WEST);
         case NORTH_SOUTH:
            return p_185499_1_.func_177226_a(field_176565_b, BlockRailBase.EnumRailDirection.EAST_WEST);
         case EAST_WEST:
            return p_185499_1_.func_177226_a(field_176565_b, BlockRailBase.EnumRailDirection.NORTH_SOUTH);
         }
      case CLOCKWISE_90:
         switch((BlockRailBase.EnumRailDirection)p_185499_1_.func_177229_b(field_176565_b)) {
         case ASCENDING_EAST:
            return p_185499_1_.func_177226_a(field_176565_b, BlockRailBase.EnumRailDirection.ASCENDING_SOUTH);
         case ASCENDING_WEST:
            return p_185499_1_.func_177226_a(field_176565_b, BlockRailBase.EnumRailDirection.ASCENDING_NORTH);
         case ASCENDING_NORTH:
            return p_185499_1_.func_177226_a(field_176565_b, BlockRailBase.EnumRailDirection.ASCENDING_EAST);
         case ASCENDING_SOUTH:
            return p_185499_1_.func_177226_a(field_176565_b, BlockRailBase.EnumRailDirection.ASCENDING_WEST);
         case SOUTH_EAST:
            return p_185499_1_.func_177226_a(field_176565_b, BlockRailBase.EnumRailDirection.SOUTH_WEST);
         case SOUTH_WEST:
            return p_185499_1_.func_177226_a(field_176565_b, BlockRailBase.EnumRailDirection.NORTH_WEST);
         case NORTH_WEST:
            return p_185499_1_.func_177226_a(field_176565_b, BlockRailBase.EnumRailDirection.NORTH_EAST);
         case NORTH_EAST:
            return p_185499_1_.func_177226_a(field_176565_b, BlockRailBase.EnumRailDirection.SOUTH_EAST);
         case NORTH_SOUTH:
            return p_185499_1_.func_177226_a(field_176565_b, BlockRailBase.EnumRailDirection.EAST_WEST);
         case EAST_WEST:
            return p_185499_1_.func_177226_a(field_176565_b, BlockRailBase.EnumRailDirection.NORTH_SOUTH);
         }
      default:
         return p_185499_1_;
      }
   }

   public IBlockState func_185471_a(IBlockState p_185471_1_, Mirror p_185471_2_) {
      BlockRailBase.EnumRailDirection blockrailbase$enumraildirection = (BlockRailBase.EnumRailDirection)p_185471_1_.func_177229_b(field_176565_b);
      switch(p_185471_2_) {
      case LEFT_RIGHT:
         switch(blockrailbase$enumraildirection) {
         case ASCENDING_NORTH:
            return p_185471_1_.func_177226_a(field_176565_b, BlockRailBase.EnumRailDirection.ASCENDING_SOUTH);
         case ASCENDING_SOUTH:
            return p_185471_1_.func_177226_a(field_176565_b, BlockRailBase.EnumRailDirection.ASCENDING_NORTH);
         case SOUTH_EAST:
            return p_185471_1_.func_177226_a(field_176565_b, BlockRailBase.EnumRailDirection.NORTH_EAST);
         case SOUTH_WEST:
            return p_185471_1_.func_177226_a(field_176565_b, BlockRailBase.EnumRailDirection.NORTH_WEST);
         case NORTH_WEST:
            return p_185471_1_.func_177226_a(field_176565_b, BlockRailBase.EnumRailDirection.SOUTH_WEST);
         case NORTH_EAST:
            return p_185471_1_.func_177226_a(field_176565_b, BlockRailBase.EnumRailDirection.SOUTH_EAST);
         default:
            return super.func_185471_a(p_185471_1_, p_185471_2_);
         }
      case FRONT_BACK:
         switch(blockrailbase$enumraildirection) {
         case ASCENDING_EAST:
            return p_185471_1_.func_177226_a(field_176565_b, BlockRailBase.EnumRailDirection.ASCENDING_WEST);
         case ASCENDING_WEST:
            return p_185471_1_.func_177226_a(field_176565_b, BlockRailBase.EnumRailDirection.ASCENDING_EAST);
         case ASCENDING_NORTH:
         case ASCENDING_SOUTH:
         default:
            break;
         case SOUTH_EAST:
            return p_185471_1_.func_177226_a(field_176565_b, BlockRailBase.EnumRailDirection.SOUTH_WEST);
         case SOUTH_WEST:
            return p_185471_1_.func_177226_a(field_176565_b, BlockRailBase.EnumRailDirection.SOUTH_EAST);
         case NORTH_WEST:
            return p_185471_1_.func_177226_a(field_176565_b, BlockRailBase.EnumRailDirection.NORTH_EAST);
         case NORTH_EAST:
            return p_185471_1_.func_177226_a(field_176565_b, BlockRailBase.EnumRailDirection.NORTH_WEST);
         }
      }

      return super.func_185471_a(p_185471_1_, p_185471_2_);
   }

   protected BlockStateContainer func_180661_e() {
      return new BlockStateContainer(this, new IProperty[]{field_176565_b});
   }
}
