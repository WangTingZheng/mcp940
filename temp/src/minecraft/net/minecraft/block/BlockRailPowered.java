package net.minecraft.block;

import com.google.common.base.Predicate;
import javax.annotation.Nullable;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockRailPowered extends BlockRailBase {
   public static final PropertyEnum<BlockRailBase.EnumRailDirection> field_176568_b = PropertyEnum.<BlockRailBase.EnumRailDirection>func_177708_a("shape", BlockRailBase.EnumRailDirection.class, new Predicate<BlockRailBase.EnumRailDirection>() {
      public boolean apply(@Nullable BlockRailBase.EnumRailDirection p_apply_1_) {
         return p_apply_1_ != BlockRailBase.EnumRailDirection.NORTH_EAST && p_apply_1_ != BlockRailBase.EnumRailDirection.NORTH_WEST && p_apply_1_ != BlockRailBase.EnumRailDirection.SOUTH_EAST && p_apply_1_ != BlockRailBase.EnumRailDirection.SOUTH_WEST;
      }
   });
   public static final PropertyBool field_176569_M = PropertyBool.func_177716_a("powered");

   protected BlockRailPowered() {
      super(true);
      this.func_180632_j(this.field_176227_L.func_177621_b().func_177226_a(field_176568_b, BlockRailBase.EnumRailDirection.NORTH_SOUTH).func_177226_a(field_176569_M, Boolean.valueOf(false)));
   }

   protected boolean func_176566_a(World p_176566_1_, BlockPos p_176566_2_, IBlockState p_176566_3_, boolean p_176566_4_, int p_176566_5_) {
      if (p_176566_5_ >= 8) {
         return false;
      } else {
         int i = p_176566_2_.func_177958_n();
         int j = p_176566_2_.func_177956_o();
         int k = p_176566_2_.func_177952_p();
         boolean flag = true;
         BlockRailBase.EnumRailDirection blockrailbase$enumraildirection = (BlockRailBase.EnumRailDirection)p_176566_3_.func_177229_b(field_176568_b);
         switch(blockrailbase$enumraildirection) {
         case NORTH_SOUTH:
            if (p_176566_4_) {
               ++k;
            } else {
               --k;
            }
            break;
         case EAST_WEST:
            if (p_176566_4_) {
               --i;
            } else {
               ++i;
            }
            break;
         case ASCENDING_EAST:
            if (p_176566_4_) {
               --i;
            } else {
               ++i;
               ++j;
               flag = false;
            }

            blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.EAST_WEST;
            break;
         case ASCENDING_WEST:
            if (p_176566_4_) {
               --i;
               ++j;
               flag = false;
            } else {
               ++i;
            }

            blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.EAST_WEST;
            break;
         case ASCENDING_NORTH:
            if (p_176566_4_) {
               ++k;
            } else {
               --k;
               ++j;
               flag = false;
            }

            blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.NORTH_SOUTH;
            break;
         case ASCENDING_SOUTH:
            if (p_176566_4_) {
               ++k;
               ++j;
               flag = false;
            } else {
               --k;
            }

            blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.NORTH_SOUTH;
         }

         if (this.func_176567_a(p_176566_1_, new BlockPos(i, j, k), p_176566_4_, p_176566_5_, blockrailbase$enumraildirection)) {
            return true;
         } else {
            return flag && this.func_176567_a(p_176566_1_, new BlockPos(i, j - 1, k), p_176566_4_, p_176566_5_, blockrailbase$enumraildirection);
         }
      }
   }

   protected boolean func_176567_a(World p_176567_1_, BlockPos p_176567_2_, boolean p_176567_3_, int p_176567_4_, BlockRailBase.EnumRailDirection p_176567_5_) {
      IBlockState iblockstate = p_176567_1_.func_180495_p(p_176567_2_);
      if (iblockstate.func_177230_c() != this) {
         return false;
      } else {
         BlockRailBase.EnumRailDirection blockrailbase$enumraildirection = (BlockRailBase.EnumRailDirection)iblockstate.func_177229_b(field_176568_b);
         if (p_176567_5_ != BlockRailBase.EnumRailDirection.EAST_WEST || blockrailbase$enumraildirection != BlockRailBase.EnumRailDirection.NORTH_SOUTH && blockrailbase$enumraildirection != BlockRailBase.EnumRailDirection.ASCENDING_NORTH && blockrailbase$enumraildirection != BlockRailBase.EnumRailDirection.ASCENDING_SOUTH) {
            if (p_176567_5_ != BlockRailBase.EnumRailDirection.NORTH_SOUTH || blockrailbase$enumraildirection != BlockRailBase.EnumRailDirection.EAST_WEST && blockrailbase$enumraildirection != BlockRailBase.EnumRailDirection.ASCENDING_EAST && blockrailbase$enumraildirection != BlockRailBase.EnumRailDirection.ASCENDING_WEST) {
               if (((Boolean)iblockstate.func_177229_b(field_176569_M)).booleanValue()) {
                  return p_176567_1_.func_175640_z(p_176567_2_) ? true : this.func_176566_a(p_176567_1_, p_176567_2_, iblockstate, p_176567_3_, p_176567_4_ + 1);
               } else {
                  return false;
               }
            } else {
               return false;
            }
         } else {
            return false;
         }
      }
   }

   protected void func_189541_b(IBlockState p_189541_1_, World p_189541_2_, BlockPos p_189541_3_, Block p_189541_4_) {
      boolean flag = ((Boolean)p_189541_1_.func_177229_b(field_176569_M)).booleanValue();
      boolean flag1 = p_189541_2_.func_175640_z(p_189541_3_) || this.func_176566_a(p_189541_2_, p_189541_3_, p_189541_1_, true, 0) || this.func_176566_a(p_189541_2_, p_189541_3_, p_189541_1_, false, 0);
      if (flag1 != flag) {
         p_189541_2_.func_180501_a(p_189541_3_, p_189541_1_.func_177226_a(field_176569_M, Boolean.valueOf(flag1)), 3);
         p_189541_2_.func_175685_c(p_189541_3_.func_177977_b(), this, false);
         if (((BlockRailBase.EnumRailDirection)p_189541_1_.func_177229_b(field_176568_b)).func_177018_c()) {
            p_189541_2_.func_175685_c(p_189541_3_.func_177984_a(), this, false);
         }
      }

   }

   public IProperty<BlockRailBase.EnumRailDirection> func_176560_l() {
      return field_176568_b;
   }

   public IBlockState func_176203_a(int p_176203_1_) {
      return this.func_176223_P().func_177226_a(field_176568_b, BlockRailBase.EnumRailDirection.func_177016_a(p_176203_1_ & 7)).func_177226_a(field_176569_M, Boolean.valueOf((p_176203_1_ & 8) > 0));
   }

   public int func_176201_c(IBlockState p_176201_1_) {
      int i = 0;
      i = i | ((BlockRailBase.EnumRailDirection)p_176201_1_.func_177229_b(field_176568_b)).func_177015_a();
      if (((Boolean)p_176201_1_.func_177229_b(field_176569_M)).booleanValue()) {
         i |= 8;
      }

      return i;
   }

   public IBlockState func_185499_a(IBlockState p_185499_1_, Rotation p_185499_2_) {
      switch(p_185499_2_) {
      case CLOCKWISE_180:
         switch((BlockRailBase.EnumRailDirection)p_185499_1_.func_177229_b(field_176568_b)) {
         case ASCENDING_EAST:
            return p_185499_1_.func_177226_a(field_176568_b, BlockRailBase.EnumRailDirection.ASCENDING_WEST);
         case ASCENDING_WEST:
            return p_185499_1_.func_177226_a(field_176568_b, BlockRailBase.EnumRailDirection.ASCENDING_EAST);
         case ASCENDING_NORTH:
            return p_185499_1_.func_177226_a(field_176568_b, BlockRailBase.EnumRailDirection.ASCENDING_SOUTH);
         case ASCENDING_SOUTH:
            return p_185499_1_.func_177226_a(field_176568_b, BlockRailBase.EnumRailDirection.ASCENDING_NORTH);
         case SOUTH_EAST:
            return p_185499_1_.func_177226_a(field_176568_b, BlockRailBase.EnumRailDirection.NORTH_WEST);
         case SOUTH_WEST:
            return p_185499_1_.func_177226_a(field_176568_b, BlockRailBase.EnumRailDirection.NORTH_EAST);
         case NORTH_WEST:
            return p_185499_1_.func_177226_a(field_176568_b, BlockRailBase.EnumRailDirection.SOUTH_EAST);
         case NORTH_EAST:
            return p_185499_1_.func_177226_a(field_176568_b, BlockRailBase.EnumRailDirection.SOUTH_WEST);
         }
      case COUNTERCLOCKWISE_90:
         switch((BlockRailBase.EnumRailDirection)p_185499_1_.func_177229_b(field_176568_b)) {
         case NORTH_SOUTH:
            return p_185499_1_.func_177226_a(field_176568_b, BlockRailBase.EnumRailDirection.EAST_WEST);
         case EAST_WEST:
            return p_185499_1_.func_177226_a(field_176568_b, BlockRailBase.EnumRailDirection.NORTH_SOUTH);
         case ASCENDING_EAST:
            return p_185499_1_.func_177226_a(field_176568_b, BlockRailBase.EnumRailDirection.ASCENDING_NORTH);
         case ASCENDING_WEST:
            return p_185499_1_.func_177226_a(field_176568_b, BlockRailBase.EnumRailDirection.ASCENDING_SOUTH);
         case ASCENDING_NORTH:
            return p_185499_1_.func_177226_a(field_176568_b, BlockRailBase.EnumRailDirection.ASCENDING_WEST);
         case ASCENDING_SOUTH:
            return p_185499_1_.func_177226_a(field_176568_b, BlockRailBase.EnumRailDirection.ASCENDING_EAST);
         case SOUTH_EAST:
            return p_185499_1_.func_177226_a(field_176568_b, BlockRailBase.EnumRailDirection.NORTH_EAST);
         case SOUTH_WEST:
            return p_185499_1_.func_177226_a(field_176568_b, BlockRailBase.EnumRailDirection.SOUTH_EAST);
         case NORTH_WEST:
            return p_185499_1_.func_177226_a(field_176568_b, BlockRailBase.EnumRailDirection.SOUTH_WEST);
         case NORTH_EAST:
            return p_185499_1_.func_177226_a(field_176568_b, BlockRailBase.EnumRailDirection.NORTH_WEST);
         }
      case CLOCKWISE_90:
         switch((BlockRailBase.EnumRailDirection)p_185499_1_.func_177229_b(field_176568_b)) {
         case NORTH_SOUTH:
            return p_185499_1_.func_177226_a(field_176568_b, BlockRailBase.EnumRailDirection.EAST_WEST);
         case EAST_WEST:
            return p_185499_1_.func_177226_a(field_176568_b, BlockRailBase.EnumRailDirection.NORTH_SOUTH);
         case ASCENDING_EAST:
            return p_185499_1_.func_177226_a(field_176568_b, BlockRailBase.EnumRailDirection.ASCENDING_SOUTH);
         case ASCENDING_WEST:
            return p_185499_1_.func_177226_a(field_176568_b, BlockRailBase.EnumRailDirection.ASCENDING_NORTH);
         case ASCENDING_NORTH:
            return p_185499_1_.func_177226_a(field_176568_b, BlockRailBase.EnumRailDirection.ASCENDING_EAST);
         case ASCENDING_SOUTH:
            return p_185499_1_.func_177226_a(field_176568_b, BlockRailBase.EnumRailDirection.ASCENDING_WEST);
         case SOUTH_EAST:
            return p_185499_1_.func_177226_a(field_176568_b, BlockRailBase.EnumRailDirection.SOUTH_WEST);
         case SOUTH_WEST:
            return p_185499_1_.func_177226_a(field_176568_b, BlockRailBase.EnumRailDirection.NORTH_WEST);
         case NORTH_WEST:
            return p_185499_1_.func_177226_a(field_176568_b, BlockRailBase.EnumRailDirection.NORTH_EAST);
         case NORTH_EAST:
            return p_185499_1_.func_177226_a(field_176568_b, BlockRailBase.EnumRailDirection.SOUTH_EAST);
         }
      default:
         return p_185499_1_;
      }
   }

   public IBlockState func_185471_a(IBlockState p_185471_1_, Mirror p_185471_2_) {
      BlockRailBase.EnumRailDirection blockrailbase$enumraildirection = (BlockRailBase.EnumRailDirection)p_185471_1_.func_177229_b(field_176568_b);
      switch(p_185471_2_) {
      case LEFT_RIGHT:
         switch(blockrailbase$enumraildirection) {
         case ASCENDING_NORTH:
            return p_185471_1_.func_177226_a(field_176568_b, BlockRailBase.EnumRailDirection.ASCENDING_SOUTH);
         case ASCENDING_SOUTH:
            return p_185471_1_.func_177226_a(field_176568_b, BlockRailBase.EnumRailDirection.ASCENDING_NORTH);
         case SOUTH_EAST:
            return p_185471_1_.func_177226_a(field_176568_b, BlockRailBase.EnumRailDirection.NORTH_EAST);
         case SOUTH_WEST:
            return p_185471_1_.func_177226_a(field_176568_b, BlockRailBase.EnumRailDirection.NORTH_WEST);
         case NORTH_WEST:
            return p_185471_1_.func_177226_a(field_176568_b, BlockRailBase.EnumRailDirection.SOUTH_WEST);
         case NORTH_EAST:
            return p_185471_1_.func_177226_a(field_176568_b, BlockRailBase.EnumRailDirection.SOUTH_EAST);
         default:
            return super.func_185471_a(p_185471_1_, p_185471_2_);
         }
      case FRONT_BACK:
         switch(blockrailbase$enumraildirection) {
         case ASCENDING_EAST:
            return p_185471_1_.func_177226_a(field_176568_b, BlockRailBase.EnumRailDirection.ASCENDING_WEST);
         case ASCENDING_WEST:
            return p_185471_1_.func_177226_a(field_176568_b, BlockRailBase.EnumRailDirection.ASCENDING_EAST);
         case ASCENDING_NORTH:
         case ASCENDING_SOUTH:
         default:
            break;
         case SOUTH_EAST:
            return p_185471_1_.func_177226_a(field_176568_b, BlockRailBase.EnumRailDirection.SOUTH_WEST);
         case SOUTH_WEST:
            return p_185471_1_.func_177226_a(field_176568_b, BlockRailBase.EnumRailDirection.SOUTH_EAST);
         case NORTH_WEST:
            return p_185471_1_.func_177226_a(field_176568_b, BlockRailBase.EnumRailDirection.NORTH_EAST);
         case NORTH_EAST:
            return p_185471_1_.func_177226_a(field_176568_b, BlockRailBase.EnumRailDirection.NORTH_WEST);
         }
      }

      return super.func_185471_a(p_185471_1_, p_185471_2_);
   }

   protected BlockStateContainer func_180661_e() {
      return new BlockStateContainer(this, new IProperty[]{field_176568_b, field_176569_M});
   }
}
