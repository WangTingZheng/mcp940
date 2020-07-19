package net.minecraft.block;

import javax.annotation.Nullable;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockLever extends Block {
   public static final PropertyEnum<BlockLever.EnumOrientation> field_176360_a = PropertyEnum.<BlockLever.EnumOrientation>func_177709_a("facing", BlockLever.EnumOrientation.class);
   public static final PropertyBool field_176359_b = PropertyBool.func_177716_a("powered");
   protected static final AxisAlignedBB field_185692_c = new AxisAlignedBB(0.3125D, 0.20000000298023224D, 0.625D, 0.6875D, 0.800000011920929D, 1.0D);
   protected static final AxisAlignedBB field_185693_d = new AxisAlignedBB(0.3125D, 0.20000000298023224D, 0.0D, 0.6875D, 0.800000011920929D, 0.375D);
   protected static final AxisAlignedBB field_185694_e = new AxisAlignedBB(0.625D, 0.20000000298023224D, 0.3125D, 1.0D, 0.800000011920929D, 0.6875D);
   protected static final AxisAlignedBB field_185695_f = new AxisAlignedBB(0.0D, 0.20000000298023224D, 0.3125D, 0.375D, 0.800000011920929D, 0.6875D);
   protected static final AxisAlignedBB field_185696_g = new AxisAlignedBB(0.25D, 0.0D, 0.25D, 0.75D, 0.6000000238418579D, 0.75D);
   protected static final AxisAlignedBB field_185691_B = new AxisAlignedBB(0.25D, 0.4000000059604645D, 0.25D, 0.75D, 1.0D, 0.75D);

   protected BlockLever() {
      super(Material.field_151594_q);
      this.func_180632_j(this.field_176227_L.func_177621_b().func_177226_a(field_176360_a, BlockLever.EnumOrientation.NORTH).func_177226_a(field_176359_b, Boolean.valueOf(false)));
      this.func_149647_a(CreativeTabs.field_78028_d);
   }

   @Nullable
   public AxisAlignedBB func_180646_a(IBlockState p_180646_1_, IBlockAccess p_180646_2_, BlockPos p_180646_3_) {
      return field_185506_k;
   }

   public boolean func_149662_c(IBlockState p_149662_1_) {
      return false;
   }

   public boolean func_149686_d(IBlockState p_149686_1_) {
      return false;
   }

   public boolean func_176198_a(World p_176198_1_, BlockPos p_176198_2_, EnumFacing p_176198_3_) {
      return func_181090_a(p_176198_1_, p_176198_2_, p_176198_3_);
   }

   public boolean func_176196_c(World p_176196_1_, BlockPos p_176196_2_) {
      for(EnumFacing enumfacing : EnumFacing.values()) {
         if (func_181090_a(p_176196_1_, p_176196_2_, enumfacing)) {
            return true;
         }
      }

      return false;
   }

   protected static boolean func_181090_a(World p_181090_0_, BlockPos p_181090_1_, EnumFacing p_181090_2_) {
      return BlockButton.func_181088_a(p_181090_0_, p_181090_1_, p_181090_2_);
   }

   public IBlockState func_180642_a(World p_180642_1_, BlockPos p_180642_2_, EnumFacing p_180642_3_, float p_180642_4_, float p_180642_5_, float p_180642_6_, int p_180642_7_, EntityLivingBase p_180642_8_) {
      IBlockState iblockstate = this.func_176223_P().func_177226_a(field_176359_b, Boolean.valueOf(false));
      if (func_181090_a(p_180642_1_, p_180642_2_, p_180642_3_)) {
         return iblockstate.func_177226_a(field_176360_a, BlockLever.EnumOrientation.func_176856_a(p_180642_3_, p_180642_8_.func_174811_aO()));
      } else {
         for(EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
            if (enumfacing != p_180642_3_ && func_181090_a(p_180642_1_, p_180642_2_, enumfacing)) {
               return iblockstate.func_177226_a(field_176360_a, BlockLever.EnumOrientation.func_176856_a(enumfacing, p_180642_8_.func_174811_aO()));
            }
         }

         if (p_180642_1_.func_180495_p(p_180642_2_.func_177977_b()).func_185896_q()) {
            return iblockstate.func_177226_a(field_176360_a, BlockLever.EnumOrientation.func_176856_a(EnumFacing.UP, p_180642_8_.func_174811_aO()));
         } else {
            return iblockstate;
         }
      }
   }

   public void func_189540_a(IBlockState p_189540_1_, World p_189540_2_, BlockPos p_189540_3_, Block p_189540_4_, BlockPos p_189540_5_) {
      if (this.func_181091_e(p_189540_2_, p_189540_3_, p_189540_1_) && !func_181090_a(p_189540_2_, p_189540_3_, ((BlockLever.EnumOrientation)p_189540_1_.func_177229_b(field_176360_a)).func_176852_c())) {
         this.func_176226_b(p_189540_2_, p_189540_3_, p_189540_1_, 0);
         p_189540_2_.func_175698_g(p_189540_3_);
      }

   }

   private boolean func_181091_e(World p_181091_1_, BlockPos p_181091_2_, IBlockState p_181091_3_) {
      if (this.func_176196_c(p_181091_1_, p_181091_2_)) {
         return true;
      } else {
         this.func_176226_b(p_181091_1_, p_181091_2_, p_181091_3_, 0);
         p_181091_1_.func_175698_g(p_181091_2_);
         return false;
      }
   }

   public AxisAlignedBB func_185496_a(IBlockState p_185496_1_, IBlockAccess p_185496_2_, BlockPos p_185496_3_) {
      switch((BlockLever.EnumOrientation)p_185496_1_.func_177229_b(field_176360_a)) {
      case EAST:
      default:
         return field_185695_f;
      case WEST:
         return field_185694_e;
      case SOUTH:
         return field_185693_d;
      case NORTH:
         return field_185692_c;
      case UP_Z:
      case UP_X:
         return field_185696_g;
      case DOWN_X:
      case DOWN_Z:
         return field_185691_B;
      }
   }

   public boolean func_180639_a(World p_180639_1_, BlockPos p_180639_2_, IBlockState p_180639_3_, EntityPlayer p_180639_4_, EnumHand p_180639_5_, EnumFacing p_180639_6_, float p_180639_7_, float p_180639_8_, float p_180639_9_) {
      if (p_180639_1_.field_72995_K) {
         return true;
      } else {
         p_180639_3_ = p_180639_3_.func_177231_a(field_176359_b);
         p_180639_1_.func_180501_a(p_180639_2_, p_180639_3_, 3);
         float f = ((Boolean)p_180639_3_.func_177229_b(field_176359_b)).booleanValue() ? 0.6F : 0.5F;
         p_180639_1_.func_184133_a((EntityPlayer)null, p_180639_2_, SoundEvents.field_187750_dc, SoundCategory.BLOCKS, 0.3F, f);
         p_180639_1_.func_175685_c(p_180639_2_, this, false);
         EnumFacing enumfacing = ((BlockLever.EnumOrientation)p_180639_3_.func_177229_b(field_176360_a)).func_176852_c();
         p_180639_1_.func_175685_c(p_180639_2_.func_177972_a(enumfacing.func_176734_d()), this, false);
         return true;
      }
   }

   public void func_180663_b(World p_180663_1_, BlockPos p_180663_2_, IBlockState p_180663_3_) {
      if (((Boolean)p_180663_3_.func_177229_b(field_176359_b)).booleanValue()) {
         p_180663_1_.func_175685_c(p_180663_2_, this, false);
         EnumFacing enumfacing = ((BlockLever.EnumOrientation)p_180663_3_.func_177229_b(field_176360_a)).func_176852_c();
         p_180663_1_.func_175685_c(p_180663_2_.func_177972_a(enumfacing.func_176734_d()), this, false);
      }

      super.func_180663_b(p_180663_1_, p_180663_2_, p_180663_3_);
   }

   public int func_180656_a(IBlockState p_180656_1_, IBlockAccess p_180656_2_, BlockPos p_180656_3_, EnumFacing p_180656_4_) {
      return ((Boolean)p_180656_1_.func_177229_b(field_176359_b)).booleanValue() ? 15 : 0;
   }

   public int func_176211_b(IBlockState p_176211_1_, IBlockAccess p_176211_2_, BlockPos p_176211_3_, EnumFacing p_176211_4_) {
      if (!((Boolean)p_176211_1_.func_177229_b(field_176359_b)).booleanValue()) {
         return 0;
      } else {
         return ((BlockLever.EnumOrientation)p_176211_1_.func_177229_b(field_176360_a)).func_176852_c() == p_176211_4_ ? 15 : 0;
      }
   }

   public boolean func_149744_f(IBlockState p_149744_1_) {
      return true;
   }

   public IBlockState func_176203_a(int p_176203_1_) {
      return this.func_176223_P().func_177226_a(field_176360_a, BlockLever.EnumOrientation.func_176853_a(p_176203_1_ & 7)).func_177226_a(field_176359_b, Boolean.valueOf((p_176203_1_ & 8) > 0));
   }

   public int func_176201_c(IBlockState p_176201_1_) {
      int i = 0;
      i = i | ((BlockLever.EnumOrientation)p_176201_1_.func_177229_b(field_176360_a)).func_176855_a();
      if (((Boolean)p_176201_1_.func_177229_b(field_176359_b)).booleanValue()) {
         i |= 8;
      }

      return i;
   }

   public IBlockState func_185499_a(IBlockState p_185499_1_, Rotation p_185499_2_) {
      switch(p_185499_2_) {
      case CLOCKWISE_180:
         switch((BlockLever.EnumOrientation)p_185499_1_.func_177229_b(field_176360_a)) {
         case EAST:
            return p_185499_1_.func_177226_a(field_176360_a, BlockLever.EnumOrientation.WEST);
         case WEST:
            return p_185499_1_.func_177226_a(field_176360_a, BlockLever.EnumOrientation.EAST);
         case SOUTH:
            return p_185499_1_.func_177226_a(field_176360_a, BlockLever.EnumOrientation.NORTH);
         case NORTH:
            return p_185499_1_.func_177226_a(field_176360_a, BlockLever.EnumOrientation.SOUTH);
         default:
            return p_185499_1_;
         }
      case COUNTERCLOCKWISE_90:
         switch((BlockLever.EnumOrientation)p_185499_1_.func_177229_b(field_176360_a)) {
         case EAST:
            return p_185499_1_.func_177226_a(field_176360_a, BlockLever.EnumOrientation.NORTH);
         case WEST:
            return p_185499_1_.func_177226_a(field_176360_a, BlockLever.EnumOrientation.SOUTH);
         case SOUTH:
            return p_185499_1_.func_177226_a(field_176360_a, BlockLever.EnumOrientation.EAST);
         case NORTH:
            return p_185499_1_.func_177226_a(field_176360_a, BlockLever.EnumOrientation.WEST);
         case UP_Z:
            return p_185499_1_.func_177226_a(field_176360_a, BlockLever.EnumOrientation.UP_X);
         case UP_X:
            return p_185499_1_.func_177226_a(field_176360_a, BlockLever.EnumOrientation.UP_Z);
         case DOWN_X:
            return p_185499_1_.func_177226_a(field_176360_a, BlockLever.EnumOrientation.DOWN_Z);
         case DOWN_Z:
            return p_185499_1_.func_177226_a(field_176360_a, BlockLever.EnumOrientation.DOWN_X);
         }
      case CLOCKWISE_90:
         switch((BlockLever.EnumOrientation)p_185499_1_.func_177229_b(field_176360_a)) {
         case EAST:
            return p_185499_1_.func_177226_a(field_176360_a, BlockLever.EnumOrientation.SOUTH);
         case WEST:
            return p_185499_1_.func_177226_a(field_176360_a, BlockLever.EnumOrientation.NORTH);
         case SOUTH:
            return p_185499_1_.func_177226_a(field_176360_a, BlockLever.EnumOrientation.WEST);
         case NORTH:
            return p_185499_1_.func_177226_a(field_176360_a, BlockLever.EnumOrientation.EAST);
         case UP_Z:
            return p_185499_1_.func_177226_a(field_176360_a, BlockLever.EnumOrientation.UP_X);
         case UP_X:
            return p_185499_1_.func_177226_a(field_176360_a, BlockLever.EnumOrientation.UP_Z);
         case DOWN_X:
            return p_185499_1_.func_177226_a(field_176360_a, BlockLever.EnumOrientation.DOWN_Z);
         case DOWN_Z:
            return p_185499_1_.func_177226_a(field_176360_a, BlockLever.EnumOrientation.DOWN_X);
         }
      default:
         return p_185499_1_;
      }
   }

   public IBlockState func_185471_a(IBlockState p_185471_1_, Mirror p_185471_2_) {
      return p_185471_1_.func_185907_a(p_185471_2_.func_185800_a(((BlockLever.EnumOrientation)p_185471_1_.func_177229_b(field_176360_a)).func_176852_c()));
   }

   protected BlockStateContainer func_180661_e() {
      return new BlockStateContainer(this, new IProperty[]{field_176360_a, field_176359_b});
   }

   public BlockFaceShape func_193383_a(IBlockAccess p_193383_1_, IBlockState p_193383_2_, BlockPos p_193383_3_, EnumFacing p_193383_4_) {
      return BlockFaceShape.UNDEFINED;
   }

   public static enum EnumOrientation implements IStringSerializable {
      DOWN_X(0, "down_x", EnumFacing.DOWN),
      EAST(1, "east", EnumFacing.EAST),
      WEST(2, "west", EnumFacing.WEST),
      SOUTH(3, "south", EnumFacing.SOUTH),
      NORTH(4, "north", EnumFacing.NORTH),
      UP_Z(5, "up_z", EnumFacing.UP),
      UP_X(6, "up_x", EnumFacing.UP),
      DOWN_Z(7, "down_z", EnumFacing.DOWN);

      private static final BlockLever.EnumOrientation[] field_176869_i = new BlockLever.EnumOrientation[values().length];
      private final int field_176866_j;
      private final String field_176867_k;
      private final EnumFacing field_176864_l;

      private EnumOrientation(int p_i45709_3_, String p_i45709_4_, EnumFacing p_i45709_5_) {
         this.field_176866_j = p_i45709_3_;
         this.field_176867_k = p_i45709_4_;
         this.field_176864_l = p_i45709_5_;
      }

      public int func_176855_a() {
         return this.field_176866_j;
      }

      public EnumFacing func_176852_c() {
         return this.field_176864_l;
      }

      public String toString() {
         return this.field_176867_k;
      }

      public static BlockLever.EnumOrientation func_176853_a(int p_176853_0_) {
         if (p_176853_0_ < 0 || p_176853_0_ >= field_176869_i.length) {
            p_176853_0_ = 0;
         }

         return field_176869_i[p_176853_0_];
      }

      public static BlockLever.EnumOrientation func_176856_a(EnumFacing p_176856_0_, EnumFacing p_176856_1_) {
         switch(p_176856_0_) {
         case DOWN:
            switch(p_176856_1_.func_176740_k()) {
            case X:
               return DOWN_X;
            case Z:
               return DOWN_Z;
            default:
               throw new IllegalArgumentException("Invalid entityFacing " + p_176856_1_ + " for facing " + p_176856_0_);
            }
         case UP:
            switch(p_176856_1_.func_176740_k()) {
            case X:
               return UP_X;
            case Z:
               return UP_Z;
            default:
               throw new IllegalArgumentException("Invalid entityFacing " + p_176856_1_ + " for facing " + p_176856_0_);
            }
         case NORTH:
            return NORTH;
         case SOUTH:
            return SOUTH;
         case WEST:
            return WEST;
         case EAST:
            return EAST;
         default:
            throw new IllegalArgumentException("Invalid facing: " + p_176856_0_);
         }
      }

      public String func_176610_l() {
         return this.field_176867_k;
      }

      static {
         for(BlockLever.EnumOrientation blocklever$enumorientation : values()) {
            field_176869_i[blocklever$enumorientation.func_176855_a()] = blocklever$enumorientation;
         }

      }
   }
}
