package net.minecraft.block;

import java.util.Random;
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
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockHugeMushroom extends Block {
   public static final PropertyEnum<BlockHugeMushroom.EnumType> field_176380_a = PropertyEnum.<BlockHugeMushroom.EnumType>func_177709_a("variant", BlockHugeMushroom.EnumType.class);
   private final Block field_176379_b;

   public BlockHugeMushroom(Material p_i46392_1_, MapColor p_i46392_2_, Block p_i46392_3_) {
      super(p_i46392_1_, p_i46392_2_);
      this.func_180632_j(this.field_176227_L.func_177621_b().func_177226_a(field_176380_a, BlockHugeMushroom.EnumType.ALL_OUTSIDE));
      this.field_176379_b = p_i46392_3_;
   }

   public int func_149745_a(Random p_149745_1_) {
      return Math.max(0, p_149745_1_.nextInt(10) - 7);
   }

   public MapColor func_180659_g(IBlockState p_180659_1_, IBlockAccess p_180659_2_, BlockPos p_180659_3_) {
      switch((BlockHugeMushroom.EnumType)p_180659_1_.func_177229_b(field_176380_a)) {
      case ALL_STEM:
         return MapColor.field_151659_e;
      case ALL_INSIDE:
         return MapColor.field_151658_d;
      case STEM:
         return MapColor.field_151658_d;
      default:
         return super.func_180659_g(p_180659_1_, p_180659_2_, p_180659_3_);
      }
   }

   public Item func_180660_a(IBlockState p_180660_1_, Random p_180660_2_, int p_180660_3_) {
      return Item.func_150898_a(this.field_176379_b);
   }

   public ItemStack func_185473_a(World p_185473_1_, BlockPos p_185473_2_, IBlockState p_185473_3_) {
      return new ItemStack(this.field_176379_b);
   }

   public IBlockState func_180642_a(World p_180642_1_, BlockPos p_180642_2_, EnumFacing p_180642_3_, float p_180642_4_, float p_180642_5_, float p_180642_6_, int p_180642_7_, EntityLivingBase p_180642_8_) {
      return this.func_176223_P();
   }

   public IBlockState func_176203_a(int p_176203_1_) {
      return this.func_176223_P().func_177226_a(field_176380_a, BlockHugeMushroom.EnumType.func_176895_a(p_176203_1_));
   }

   public int func_176201_c(IBlockState p_176201_1_) {
      return ((BlockHugeMushroom.EnumType)p_176201_1_.func_177229_b(field_176380_a)).func_176896_a();
   }

   public IBlockState func_185499_a(IBlockState p_185499_1_, Rotation p_185499_2_) {
      switch(p_185499_2_) {
      case CLOCKWISE_180:
         switch((BlockHugeMushroom.EnumType)p_185499_1_.func_177229_b(field_176380_a)) {
         case STEM:
            break;
         case NORTH_WEST:
            return p_185499_1_.func_177226_a(field_176380_a, BlockHugeMushroom.EnumType.SOUTH_EAST);
         case NORTH:
            return p_185499_1_.func_177226_a(field_176380_a, BlockHugeMushroom.EnumType.SOUTH);
         case NORTH_EAST:
            return p_185499_1_.func_177226_a(field_176380_a, BlockHugeMushroom.EnumType.SOUTH_WEST);
         case WEST:
            return p_185499_1_.func_177226_a(field_176380_a, BlockHugeMushroom.EnumType.EAST);
         case EAST:
            return p_185499_1_.func_177226_a(field_176380_a, BlockHugeMushroom.EnumType.WEST);
         case SOUTH_WEST:
            return p_185499_1_.func_177226_a(field_176380_a, BlockHugeMushroom.EnumType.NORTH_EAST);
         case SOUTH:
            return p_185499_1_.func_177226_a(field_176380_a, BlockHugeMushroom.EnumType.NORTH);
         case SOUTH_EAST:
            return p_185499_1_.func_177226_a(field_176380_a, BlockHugeMushroom.EnumType.NORTH_WEST);
         default:
            return p_185499_1_;
         }
      case COUNTERCLOCKWISE_90:
         switch((BlockHugeMushroom.EnumType)p_185499_1_.func_177229_b(field_176380_a)) {
         case STEM:
            break;
         case NORTH_WEST:
            return p_185499_1_.func_177226_a(field_176380_a, BlockHugeMushroom.EnumType.SOUTH_WEST);
         case NORTH:
            return p_185499_1_.func_177226_a(field_176380_a, BlockHugeMushroom.EnumType.WEST);
         case NORTH_EAST:
            return p_185499_1_.func_177226_a(field_176380_a, BlockHugeMushroom.EnumType.NORTH_WEST);
         case WEST:
            return p_185499_1_.func_177226_a(field_176380_a, BlockHugeMushroom.EnumType.SOUTH);
         case EAST:
            return p_185499_1_.func_177226_a(field_176380_a, BlockHugeMushroom.EnumType.NORTH);
         case SOUTH_WEST:
            return p_185499_1_.func_177226_a(field_176380_a, BlockHugeMushroom.EnumType.SOUTH_EAST);
         case SOUTH:
            return p_185499_1_.func_177226_a(field_176380_a, BlockHugeMushroom.EnumType.EAST);
         case SOUTH_EAST:
            return p_185499_1_.func_177226_a(field_176380_a, BlockHugeMushroom.EnumType.NORTH_EAST);
         default:
            return p_185499_1_;
         }
      case CLOCKWISE_90:
         switch((BlockHugeMushroom.EnumType)p_185499_1_.func_177229_b(field_176380_a)) {
         case STEM:
            break;
         case NORTH_WEST:
            return p_185499_1_.func_177226_a(field_176380_a, BlockHugeMushroom.EnumType.NORTH_EAST);
         case NORTH:
            return p_185499_1_.func_177226_a(field_176380_a, BlockHugeMushroom.EnumType.EAST);
         case NORTH_EAST:
            return p_185499_1_.func_177226_a(field_176380_a, BlockHugeMushroom.EnumType.SOUTH_EAST);
         case WEST:
            return p_185499_1_.func_177226_a(field_176380_a, BlockHugeMushroom.EnumType.NORTH);
         case EAST:
            return p_185499_1_.func_177226_a(field_176380_a, BlockHugeMushroom.EnumType.SOUTH);
         case SOUTH_WEST:
            return p_185499_1_.func_177226_a(field_176380_a, BlockHugeMushroom.EnumType.NORTH_WEST);
         case SOUTH:
            return p_185499_1_.func_177226_a(field_176380_a, BlockHugeMushroom.EnumType.WEST);
         case SOUTH_EAST:
            return p_185499_1_.func_177226_a(field_176380_a, BlockHugeMushroom.EnumType.SOUTH_WEST);
         default:
            return p_185499_1_;
         }
      default:
         return p_185499_1_;
      }
   }

   public IBlockState func_185471_a(IBlockState p_185471_1_, Mirror p_185471_2_) {
      BlockHugeMushroom.EnumType blockhugemushroom$enumtype = (BlockHugeMushroom.EnumType)p_185471_1_.func_177229_b(field_176380_a);
      switch(p_185471_2_) {
      case LEFT_RIGHT:
         switch(blockhugemushroom$enumtype) {
         case NORTH_WEST:
            return p_185471_1_.func_177226_a(field_176380_a, BlockHugeMushroom.EnumType.SOUTH_WEST);
         case NORTH:
            return p_185471_1_.func_177226_a(field_176380_a, BlockHugeMushroom.EnumType.SOUTH);
         case NORTH_EAST:
            return p_185471_1_.func_177226_a(field_176380_a, BlockHugeMushroom.EnumType.SOUTH_EAST);
         case WEST:
         case EAST:
         default:
            return super.func_185471_a(p_185471_1_, p_185471_2_);
         case SOUTH_WEST:
            return p_185471_1_.func_177226_a(field_176380_a, BlockHugeMushroom.EnumType.NORTH_WEST);
         case SOUTH:
            return p_185471_1_.func_177226_a(field_176380_a, BlockHugeMushroom.EnumType.NORTH);
         case SOUTH_EAST:
            return p_185471_1_.func_177226_a(field_176380_a, BlockHugeMushroom.EnumType.NORTH_EAST);
         }
      case FRONT_BACK:
         switch(blockhugemushroom$enumtype) {
         case NORTH_WEST:
            return p_185471_1_.func_177226_a(field_176380_a, BlockHugeMushroom.EnumType.NORTH_EAST);
         case NORTH:
         case SOUTH:
         default:
            break;
         case NORTH_EAST:
            return p_185471_1_.func_177226_a(field_176380_a, BlockHugeMushroom.EnumType.NORTH_WEST);
         case WEST:
            return p_185471_1_.func_177226_a(field_176380_a, BlockHugeMushroom.EnumType.EAST);
         case EAST:
            return p_185471_1_.func_177226_a(field_176380_a, BlockHugeMushroom.EnumType.WEST);
         case SOUTH_WEST:
            return p_185471_1_.func_177226_a(field_176380_a, BlockHugeMushroom.EnumType.SOUTH_EAST);
         case SOUTH_EAST:
            return p_185471_1_.func_177226_a(field_176380_a, BlockHugeMushroom.EnumType.SOUTH_WEST);
         }
      }

      return super.func_185471_a(p_185471_1_, p_185471_2_);
   }

   protected BlockStateContainer func_180661_e() {
      return new BlockStateContainer(this, new IProperty[]{field_176380_a});
   }

   public static enum EnumType implements IStringSerializable {
      NORTH_WEST(1, "north_west"),
      NORTH(2, "north"),
      NORTH_EAST(3, "north_east"),
      WEST(4, "west"),
      CENTER(5, "center"),
      EAST(6, "east"),
      SOUTH_WEST(7, "south_west"),
      SOUTH(8, "south"),
      SOUTH_EAST(9, "south_east"),
      STEM(10, "stem"),
      ALL_INSIDE(0, "all_inside"),
      ALL_OUTSIDE(14, "all_outside"),
      ALL_STEM(15, "all_stem");

      private static final BlockHugeMushroom.EnumType[] field_176905_n = new BlockHugeMushroom.EnumType[16];
      private final int field_176906_o;
      private final String field_176914_p;

      private EnumType(int p_i45710_3_, String p_i45710_4_) {
         this.field_176906_o = p_i45710_3_;
         this.field_176914_p = p_i45710_4_;
      }

      public int func_176896_a() {
         return this.field_176906_o;
      }

      public String toString() {
         return this.field_176914_p;
      }

      public static BlockHugeMushroom.EnumType func_176895_a(int p_176895_0_) {
         if (p_176895_0_ < 0 || p_176895_0_ >= field_176905_n.length) {
            p_176895_0_ = 0;
         }

         BlockHugeMushroom.EnumType blockhugemushroom$enumtype = field_176905_n[p_176895_0_];
         return blockhugemushroom$enumtype == null ? field_176905_n[0] : blockhugemushroom$enumtype;
      }

      public String func_176610_l() {
         return this.field_176914_p;
      }

      static {
         for(BlockHugeMushroom.EnumType blockhugemushroom$enumtype : values()) {
            field_176905_n[blockhugemushroom$enumtype.func_176896_a()] = blockhugemushroom$enumtype;
         }

      }
   }
}
