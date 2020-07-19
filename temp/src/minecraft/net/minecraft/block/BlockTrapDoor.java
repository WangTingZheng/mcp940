package net.minecraft.block;

import javax.annotation.Nullable;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockTrapDoor extends Block {
   public static final PropertyDirection field_176284_a = BlockHorizontal.field_185512_D;
   public static final PropertyBool field_176283_b = PropertyBool.func_177716_a("open");
   public static final PropertyEnum<BlockTrapDoor.DoorHalf> field_176285_M = PropertyEnum.<BlockTrapDoor.DoorHalf>func_177709_a("half", BlockTrapDoor.DoorHalf.class);
   protected static final AxisAlignedBB field_185734_d = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.1875D, 1.0D, 1.0D);
   protected static final AxisAlignedBB field_185735_e = new AxisAlignedBB(0.8125D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
   protected static final AxisAlignedBB field_185736_f = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.1875D);
   protected static final AxisAlignedBB field_185737_g = new AxisAlignedBB(0.0D, 0.0D, 0.8125D, 1.0D, 1.0D, 1.0D);
   protected static final AxisAlignedBB field_185732_B = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.1875D, 1.0D);
   protected static final AxisAlignedBB field_185733_C = new AxisAlignedBB(0.0D, 0.8125D, 0.0D, 1.0D, 1.0D, 1.0D);

   protected BlockTrapDoor(Material p_i45434_1_) {
      super(p_i45434_1_);
      this.func_180632_j(this.field_176227_L.func_177621_b().func_177226_a(field_176284_a, EnumFacing.NORTH).func_177226_a(field_176283_b, Boolean.valueOf(false)).func_177226_a(field_176285_M, BlockTrapDoor.DoorHalf.BOTTOM));
      this.func_149647_a(CreativeTabs.field_78028_d);
   }

   public AxisAlignedBB func_185496_a(IBlockState p_185496_1_, IBlockAccess p_185496_2_, BlockPos p_185496_3_) {
      AxisAlignedBB axisalignedbb;
      if (((Boolean)p_185496_1_.func_177229_b(field_176283_b)).booleanValue()) {
         switch((EnumFacing)p_185496_1_.func_177229_b(field_176284_a)) {
         case NORTH:
         default:
            axisalignedbb = field_185737_g;
            break;
         case SOUTH:
            axisalignedbb = field_185736_f;
            break;
         case WEST:
            axisalignedbb = field_185735_e;
            break;
         case EAST:
            axisalignedbb = field_185734_d;
         }
      } else if (p_185496_1_.func_177229_b(field_176285_M) == BlockTrapDoor.DoorHalf.TOP) {
         axisalignedbb = field_185733_C;
      } else {
         axisalignedbb = field_185732_B;
      }

      return axisalignedbb;
   }

   public boolean func_149662_c(IBlockState p_149662_1_) {
      return false;
   }

   public boolean func_149686_d(IBlockState p_149686_1_) {
      return false;
   }

   public boolean func_176205_b(IBlockAccess p_176205_1_, BlockPos p_176205_2_) {
      return !((Boolean)p_176205_1_.func_180495_p(p_176205_2_).func_177229_b(field_176283_b)).booleanValue();
   }

   public boolean func_180639_a(World p_180639_1_, BlockPos p_180639_2_, IBlockState p_180639_3_, EntityPlayer p_180639_4_, EnumHand p_180639_5_, EnumFacing p_180639_6_, float p_180639_7_, float p_180639_8_, float p_180639_9_) {
      if (this.field_149764_J == Material.field_151573_f) {
         return false;
      } else {
         p_180639_3_ = p_180639_3_.func_177231_a(field_176283_b);
         p_180639_1_.func_180501_a(p_180639_2_, p_180639_3_, 2);
         this.func_185731_a(p_180639_4_, p_180639_1_, p_180639_2_, ((Boolean)p_180639_3_.func_177229_b(field_176283_b)).booleanValue());
         return true;
      }
   }

   protected void func_185731_a(@Nullable EntityPlayer p_185731_1_, World p_185731_2_, BlockPos p_185731_3_, boolean p_185731_4_) {
      if (p_185731_4_) {
         int i = this.field_149764_J == Material.field_151573_f ? 1037 : 1007;
         p_185731_2_.func_180498_a(p_185731_1_, i, p_185731_3_, 0);
      } else {
         int j = this.field_149764_J == Material.field_151573_f ? 1036 : 1013;
         p_185731_2_.func_180498_a(p_185731_1_, j, p_185731_3_, 0);
      }

   }

   public void func_189540_a(IBlockState p_189540_1_, World p_189540_2_, BlockPos p_189540_3_, Block p_189540_4_, BlockPos p_189540_5_) {
      if (!p_189540_2_.field_72995_K) {
         boolean flag = p_189540_2_.func_175640_z(p_189540_3_);
         if (flag || p_189540_4_.func_176223_P().func_185897_m()) {
            boolean flag1 = ((Boolean)p_189540_1_.func_177229_b(field_176283_b)).booleanValue();
            if (flag1 != flag) {
               p_189540_2_.func_180501_a(p_189540_3_, p_189540_1_.func_177226_a(field_176283_b, Boolean.valueOf(flag)), 2);
               this.func_185731_a((EntityPlayer)null, p_189540_2_, p_189540_3_, flag);
            }
         }

      }
   }

   public IBlockState func_180642_a(World p_180642_1_, BlockPos p_180642_2_, EnumFacing p_180642_3_, float p_180642_4_, float p_180642_5_, float p_180642_6_, int p_180642_7_, EntityLivingBase p_180642_8_) {
      IBlockState iblockstate = this.func_176223_P();
      if (p_180642_3_.func_176740_k().func_176722_c()) {
         iblockstate = iblockstate.func_177226_a(field_176284_a, p_180642_3_).func_177226_a(field_176283_b, Boolean.valueOf(false));
         iblockstate = iblockstate.func_177226_a(field_176285_M, p_180642_5_ > 0.5F ? BlockTrapDoor.DoorHalf.TOP : BlockTrapDoor.DoorHalf.BOTTOM);
      } else {
         iblockstate = iblockstate.func_177226_a(field_176284_a, p_180642_8_.func_174811_aO().func_176734_d()).func_177226_a(field_176283_b, Boolean.valueOf(false));
         iblockstate = iblockstate.func_177226_a(field_176285_M, p_180642_3_ == EnumFacing.UP ? BlockTrapDoor.DoorHalf.BOTTOM : BlockTrapDoor.DoorHalf.TOP);
      }

      if (p_180642_1_.func_175640_z(p_180642_2_)) {
         iblockstate = iblockstate.func_177226_a(field_176283_b, Boolean.valueOf(true));
      }

      return iblockstate;
   }

   public boolean func_176198_a(World p_176198_1_, BlockPos p_176198_2_, EnumFacing p_176198_3_) {
      return true;
   }

   protected static EnumFacing func_176281_b(int p_176281_0_) {
      switch(p_176281_0_ & 3) {
      case 0:
         return EnumFacing.NORTH;
      case 1:
         return EnumFacing.SOUTH;
      case 2:
         return EnumFacing.WEST;
      case 3:
      default:
         return EnumFacing.EAST;
      }
   }

   protected static int func_176282_a(EnumFacing p_176282_0_) {
      switch(p_176282_0_) {
      case NORTH:
         return 0;
      case SOUTH:
         return 1;
      case WEST:
         return 2;
      case EAST:
      default:
         return 3;
      }
   }

   public BlockRenderLayer func_180664_k() {
      return BlockRenderLayer.CUTOUT;
   }

   public IBlockState func_176203_a(int p_176203_1_) {
      return this.func_176223_P().func_177226_a(field_176284_a, func_176281_b(p_176203_1_)).func_177226_a(field_176283_b, Boolean.valueOf((p_176203_1_ & 4) != 0)).func_177226_a(field_176285_M, (p_176203_1_ & 8) == 0 ? BlockTrapDoor.DoorHalf.BOTTOM : BlockTrapDoor.DoorHalf.TOP);
   }

   public int func_176201_c(IBlockState p_176201_1_) {
      int i = 0;
      i = i | func_176282_a((EnumFacing)p_176201_1_.func_177229_b(field_176284_a));
      if (((Boolean)p_176201_1_.func_177229_b(field_176283_b)).booleanValue()) {
         i |= 4;
      }

      if (p_176201_1_.func_177229_b(field_176285_M) == BlockTrapDoor.DoorHalf.TOP) {
         i |= 8;
      }

      return i;
   }

   public IBlockState func_185499_a(IBlockState p_185499_1_, Rotation p_185499_2_) {
      return p_185499_1_.func_177226_a(field_176284_a, p_185499_2_.func_185831_a((EnumFacing)p_185499_1_.func_177229_b(field_176284_a)));
   }

   public IBlockState func_185471_a(IBlockState p_185471_1_, Mirror p_185471_2_) {
      return p_185471_1_.func_185907_a(p_185471_2_.func_185800_a((EnumFacing)p_185471_1_.func_177229_b(field_176284_a)));
   }

   protected BlockStateContainer func_180661_e() {
      return new BlockStateContainer(this, new IProperty[]{field_176284_a, field_176283_b, field_176285_M});
   }

   public BlockFaceShape func_193383_a(IBlockAccess p_193383_1_, IBlockState p_193383_2_, BlockPos p_193383_3_, EnumFacing p_193383_4_) {
      return (p_193383_4_ == EnumFacing.UP && p_193383_2_.func_177229_b(field_176285_M) == BlockTrapDoor.DoorHalf.TOP || p_193383_4_ == EnumFacing.DOWN && p_193383_2_.func_177229_b(field_176285_M) == BlockTrapDoor.DoorHalf.BOTTOM) && !((Boolean)p_193383_2_.func_177229_b(field_176283_b)).booleanValue() ? BlockFaceShape.SOLID : BlockFaceShape.UNDEFINED;
   }

   public static enum DoorHalf implements IStringSerializable {
      TOP("top"),
      BOTTOM("bottom");

      private final String field_176671_c;

      private DoorHalf(String p_i45674_3_) {
         this.field_176671_c = p_i45674_3_;
      }

      public String toString() {
         return this.field_176671_c;
      }

      public String func_176610_l() {
         return this.field_176671_c;
      }
   }
}
