package net.minecraft.block;

import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockPistonExtension extends BlockDirectional {
   public static final PropertyEnum<BlockPistonExtension.EnumPistonType> field_176325_b = PropertyEnum.<BlockPistonExtension.EnumPistonType>func_177709_a("type", BlockPistonExtension.EnumPistonType.class);
   public static final PropertyBool field_176327_M = PropertyBool.func_177716_a("short");
   protected static final AxisAlignedBB field_185635_c = new AxisAlignedBB(0.75D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
   protected static final AxisAlignedBB field_185637_d = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.25D, 1.0D, 1.0D);
   protected static final AxisAlignedBB field_185639_e = new AxisAlignedBB(0.0D, 0.0D, 0.75D, 1.0D, 1.0D, 1.0D);
   protected static final AxisAlignedBB field_185641_f = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.25D);
   protected static final AxisAlignedBB field_185643_g = new AxisAlignedBB(0.0D, 0.75D, 0.0D, 1.0D, 1.0D, 1.0D);
   protected static final AxisAlignedBB field_185634_B = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.25D, 1.0D);
   protected static final AxisAlignedBB field_185636_C = new AxisAlignedBB(0.375D, -0.25D, 0.375D, 0.625D, 0.75D, 0.625D);
   protected static final AxisAlignedBB field_185638_D = new AxisAlignedBB(0.375D, 0.25D, 0.375D, 0.625D, 1.25D, 0.625D);
   protected static final AxisAlignedBB field_185640_E = new AxisAlignedBB(0.375D, 0.375D, -0.25D, 0.625D, 0.625D, 0.75D);
   protected static final AxisAlignedBB field_185642_F = new AxisAlignedBB(0.375D, 0.375D, 0.25D, 0.625D, 0.625D, 1.25D);
   protected static final AxisAlignedBB field_185644_G = new AxisAlignedBB(-0.25D, 0.375D, 0.375D, 0.75D, 0.625D, 0.625D);
   protected static final AxisAlignedBB field_185645_I = new AxisAlignedBB(0.25D, 0.375D, 0.375D, 1.25D, 0.625D, 0.625D);
   protected static final AxisAlignedBB field_190964_J = new AxisAlignedBB(0.375D, 0.0D, 0.375D, 0.625D, 0.75D, 0.625D);
   protected static final AxisAlignedBB field_190965_K = new AxisAlignedBB(0.375D, 0.25D, 0.375D, 0.625D, 1.0D, 0.625D);
   protected static final AxisAlignedBB field_190966_L = new AxisAlignedBB(0.375D, 0.375D, 0.0D, 0.625D, 0.625D, 0.75D);
   protected static final AxisAlignedBB field_190967_M = new AxisAlignedBB(0.375D, 0.375D, 0.25D, 0.625D, 0.625D, 1.0D);
   protected static final AxisAlignedBB field_190968_N = new AxisAlignedBB(0.0D, 0.375D, 0.375D, 0.75D, 0.625D, 0.625D);
   protected static final AxisAlignedBB field_190969_O = new AxisAlignedBB(0.25D, 0.375D, 0.375D, 1.0D, 0.625D, 0.625D);

   public BlockPistonExtension() {
      super(Material.field_76233_E);
      this.func_180632_j(this.field_176227_L.func_177621_b().func_177226_a(field_176387_N, EnumFacing.NORTH).func_177226_a(field_176325_b, BlockPistonExtension.EnumPistonType.DEFAULT).func_177226_a(field_176327_M, Boolean.valueOf(false)));
      this.func_149672_a(SoundType.field_185851_d);
      this.func_149711_c(0.5F);
   }

   public AxisAlignedBB func_185496_a(IBlockState p_185496_1_, IBlockAccess p_185496_2_, BlockPos p_185496_3_) {
      switch((EnumFacing)p_185496_1_.func_177229_b(field_176387_N)) {
      case DOWN:
      default:
         return field_185634_B;
      case UP:
         return field_185643_g;
      case NORTH:
         return field_185641_f;
      case SOUTH:
         return field_185639_e;
      case WEST:
         return field_185637_d;
      case EAST:
         return field_185635_c;
      }
   }

   public void func_185477_a(IBlockState p_185477_1_, World p_185477_2_, BlockPos p_185477_3_, AxisAlignedBB p_185477_4_, List<AxisAlignedBB> p_185477_5_, @Nullable Entity p_185477_6_, boolean p_185477_7_) {
      func_185492_a(p_185477_3_, p_185477_4_, p_185477_5_, p_185477_1_.func_185900_c(p_185477_2_, p_185477_3_));
      func_185492_a(p_185477_3_, p_185477_4_, p_185477_5_, this.func_185633_i(p_185477_1_));
   }

   private AxisAlignedBB func_185633_i(IBlockState p_185633_1_) {
      boolean flag = ((Boolean)p_185633_1_.func_177229_b(field_176327_M)).booleanValue();
      switch((EnumFacing)p_185633_1_.func_177229_b(field_176387_N)) {
      case DOWN:
      default:
         return flag ? field_190965_K : field_185638_D;
      case UP:
         return flag ? field_190964_J : field_185636_C;
      case NORTH:
         return flag ? field_190967_M : field_185642_F;
      case SOUTH:
         return flag ? field_190966_L : field_185640_E;
      case WEST:
         return flag ? field_190969_O : field_185645_I;
      case EAST:
         return flag ? field_190968_N : field_185644_G;
      }
   }

   public boolean func_185481_k(IBlockState p_185481_1_) {
      return p_185481_1_.func_177229_b(field_176387_N) == EnumFacing.UP;
   }

   public void func_176208_a(World p_176208_1_, BlockPos p_176208_2_, IBlockState p_176208_3_, EntityPlayer p_176208_4_) {
      if (p_176208_4_.field_71075_bZ.field_75098_d) {
         BlockPos blockpos = p_176208_2_.func_177972_a(((EnumFacing)p_176208_3_.func_177229_b(field_176387_N)).func_176734_d());
         Block block = p_176208_1_.func_180495_p(blockpos).func_177230_c();
         if (block == Blocks.field_150331_J || block == Blocks.field_150320_F) {
            p_176208_1_.func_175698_g(blockpos);
         }
      }

      super.func_176208_a(p_176208_1_, p_176208_2_, p_176208_3_, p_176208_4_);
   }

   public void func_180663_b(World p_180663_1_, BlockPos p_180663_2_, IBlockState p_180663_3_) {
      super.func_180663_b(p_180663_1_, p_180663_2_, p_180663_3_);
      EnumFacing enumfacing = ((EnumFacing)p_180663_3_.func_177229_b(field_176387_N)).func_176734_d();
      p_180663_2_ = p_180663_2_.func_177972_a(enumfacing);
      IBlockState iblockstate = p_180663_1_.func_180495_p(p_180663_2_);
      if ((iblockstate.func_177230_c() == Blocks.field_150331_J || iblockstate.func_177230_c() == Blocks.field_150320_F) && ((Boolean)iblockstate.func_177229_b(BlockPistonBase.field_176320_b)).booleanValue()) {
         iblockstate.func_177230_c().func_176226_b(p_180663_1_, p_180663_2_, iblockstate, 0);
         p_180663_1_.func_175698_g(p_180663_2_);
      }

   }

   public boolean func_149662_c(IBlockState p_149662_1_) {
      return false;
   }

   public boolean func_149686_d(IBlockState p_149686_1_) {
      return false;
   }

   public boolean func_176196_c(World p_176196_1_, BlockPos p_176196_2_) {
      return false;
   }

   public boolean func_176198_a(World p_176198_1_, BlockPos p_176198_2_, EnumFacing p_176198_3_) {
      return false;
   }

   public int func_149745_a(Random p_149745_1_) {
      return 0;
   }

   public void func_189540_a(IBlockState p_189540_1_, World p_189540_2_, BlockPos p_189540_3_, Block p_189540_4_, BlockPos p_189540_5_) {
      EnumFacing enumfacing = (EnumFacing)p_189540_1_.func_177229_b(field_176387_N);
      BlockPos blockpos = p_189540_3_.func_177972_a(enumfacing.func_176734_d());
      IBlockState iblockstate = p_189540_2_.func_180495_p(blockpos);
      if (iblockstate.func_177230_c() != Blocks.field_150331_J && iblockstate.func_177230_c() != Blocks.field_150320_F) {
         p_189540_2_.func_175698_g(p_189540_3_);
      } else {
         iblockstate.func_189546_a(p_189540_2_, blockpos, p_189540_4_, p_189540_5_);
      }

   }

   public boolean func_176225_a(IBlockState p_176225_1_, IBlockAccess p_176225_2_, BlockPos p_176225_3_, EnumFacing p_176225_4_) {
      return true;
   }

   @Nullable
   public static EnumFacing func_176322_b(int p_176322_0_) {
      int i = p_176322_0_ & 7;
      return i > 5 ? null : EnumFacing.func_82600_a(i);
   }

   public ItemStack func_185473_a(World p_185473_1_, BlockPos p_185473_2_, IBlockState p_185473_3_) {
      return new ItemStack(p_185473_3_.func_177229_b(field_176325_b) == BlockPistonExtension.EnumPistonType.STICKY ? Blocks.field_150320_F : Blocks.field_150331_J);
   }

   public IBlockState func_176203_a(int p_176203_1_) {
      return this.func_176223_P().func_177226_a(field_176387_N, func_176322_b(p_176203_1_)).func_177226_a(field_176325_b, (p_176203_1_ & 8) > 0 ? BlockPistonExtension.EnumPistonType.STICKY : BlockPistonExtension.EnumPistonType.DEFAULT);
   }

   public int func_176201_c(IBlockState p_176201_1_) {
      int i = 0;
      i = i | ((EnumFacing)p_176201_1_.func_177229_b(field_176387_N)).func_176745_a();
      if (p_176201_1_.func_177229_b(field_176325_b) == BlockPistonExtension.EnumPistonType.STICKY) {
         i |= 8;
      }

      return i;
   }

   public IBlockState func_185499_a(IBlockState p_185499_1_, Rotation p_185499_2_) {
      return p_185499_1_.func_177226_a(field_176387_N, p_185499_2_.func_185831_a((EnumFacing)p_185499_1_.func_177229_b(field_176387_N)));
   }

   public IBlockState func_185471_a(IBlockState p_185471_1_, Mirror p_185471_2_) {
      return p_185471_1_.func_185907_a(p_185471_2_.func_185800_a((EnumFacing)p_185471_1_.func_177229_b(field_176387_N)));
   }

   protected BlockStateContainer func_180661_e() {
      return new BlockStateContainer(this, new IProperty[]{field_176387_N, field_176325_b, field_176327_M});
   }

   public BlockFaceShape func_193383_a(IBlockAccess p_193383_1_, IBlockState p_193383_2_, BlockPos p_193383_3_, EnumFacing p_193383_4_) {
      return p_193383_4_ == p_193383_2_.func_177229_b(field_176387_N) ? BlockFaceShape.SOLID : BlockFaceShape.UNDEFINED;
   }

   public static enum EnumPistonType implements IStringSerializable {
      DEFAULT("normal"),
      STICKY("sticky");

      private final String field_176714_c;

      private EnumPistonType(String p_i45666_3_) {
         this.field_176714_c = p_i45666_3_;
      }

      public String toString() {
         return this.field_176714_c;
      }

      public String func_176610_l() {
         return this.field_176714_c;
      }
   }
}
