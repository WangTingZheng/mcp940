package net.minecraft.block;

import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public abstract class BlockButton extends BlockDirectional {
   public static final PropertyBool field_176584_b = PropertyBool.func_177716_a("powered");
   protected static final AxisAlignedBB field_185618_b = new AxisAlignedBB(0.3125D, 0.875D, 0.375D, 0.6875D, 1.0D, 0.625D);
   protected static final AxisAlignedBB field_185620_c = new AxisAlignedBB(0.3125D, 0.0D, 0.375D, 0.6875D, 0.125D, 0.625D);
   protected static final AxisAlignedBB field_185622_d = new AxisAlignedBB(0.3125D, 0.375D, 0.875D, 0.6875D, 0.625D, 1.0D);
   protected static final AxisAlignedBB field_185624_e = new AxisAlignedBB(0.3125D, 0.375D, 0.0D, 0.6875D, 0.625D, 0.125D);
   protected static final AxisAlignedBB field_185626_f = new AxisAlignedBB(0.875D, 0.375D, 0.3125D, 1.0D, 0.625D, 0.6875D);
   protected static final AxisAlignedBB field_185628_g = new AxisAlignedBB(0.0D, 0.375D, 0.3125D, 0.125D, 0.625D, 0.6875D);
   protected static final AxisAlignedBB field_185619_B = new AxisAlignedBB(0.3125D, 0.9375D, 0.375D, 0.6875D, 1.0D, 0.625D);
   protected static final AxisAlignedBB field_185621_C = new AxisAlignedBB(0.3125D, 0.0D, 0.375D, 0.6875D, 0.0625D, 0.625D);
   protected static final AxisAlignedBB field_185623_D = new AxisAlignedBB(0.3125D, 0.375D, 0.9375D, 0.6875D, 0.625D, 1.0D);
   protected static final AxisAlignedBB field_185625_E = new AxisAlignedBB(0.3125D, 0.375D, 0.0D, 0.6875D, 0.625D, 0.0625D);
   protected static final AxisAlignedBB field_185627_F = new AxisAlignedBB(0.9375D, 0.375D, 0.3125D, 1.0D, 0.625D, 0.6875D);
   protected static final AxisAlignedBB field_185629_G = new AxisAlignedBB(0.0D, 0.375D, 0.3125D, 0.0625D, 0.625D, 0.6875D);
   private final boolean field_150047_a;

   protected BlockButton(boolean p_i45396_1_) {
      super(Material.field_151594_q);
      this.func_180632_j(this.field_176227_L.func_177621_b().func_177226_a(field_176387_N, EnumFacing.NORTH).func_177226_a(field_176584_b, Boolean.valueOf(false)));
      this.func_149675_a(true);
      this.func_149647_a(CreativeTabs.field_78028_d);
      this.field_150047_a = p_i45396_1_;
   }

   @Nullable
   public AxisAlignedBB func_180646_a(IBlockState p_180646_1_, IBlockAccess p_180646_2_, BlockPos p_180646_3_) {
      return field_185506_k;
   }

   public int func_149738_a(World p_149738_1_) {
      return this.field_150047_a ? 30 : 20;
   }

   public boolean func_149662_c(IBlockState p_149662_1_) {
      return false;
   }

   public boolean func_149686_d(IBlockState p_149686_1_) {
      return false;
   }

   public boolean func_176198_a(World p_176198_1_, BlockPos p_176198_2_, EnumFacing p_176198_3_) {
      return func_181088_a(p_176198_1_, p_176198_2_, p_176198_3_);
   }

   public boolean func_176196_c(World p_176196_1_, BlockPos p_176196_2_) {
      for(EnumFacing enumfacing : EnumFacing.values()) {
         if (func_181088_a(p_176196_1_, p_176196_2_, enumfacing)) {
            return true;
         }
      }

      return false;
   }

   protected static boolean func_181088_a(World p_181088_0_, BlockPos p_181088_1_, EnumFacing p_181088_2_) {
      BlockPos blockpos = p_181088_1_.func_177972_a(p_181088_2_.func_176734_d());
      IBlockState iblockstate = p_181088_0_.func_180495_p(blockpos);
      boolean flag = iblockstate.func_193401_d(p_181088_0_, blockpos, p_181088_2_) == BlockFaceShape.SOLID;
      Block block = iblockstate.func_177230_c();
      if (p_181088_2_ == EnumFacing.UP) {
         return block == Blocks.field_150438_bZ || !func_193384_b(block) && flag;
      } else {
         return !func_193382_c(block) && flag;
      }
   }

   public IBlockState func_180642_a(World p_180642_1_, BlockPos p_180642_2_, EnumFacing p_180642_3_, float p_180642_4_, float p_180642_5_, float p_180642_6_, int p_180642_7_, EntityLivingBase p_180642_8_) {
      return func_181088_a(p_180642_1_, p_180642_2_, p_180642_3_) ? this.func_176223_P().func_177226_a(field_176387_N, p_180642_3_).func_177226_a(field_176584_b, Boolean.valueOf(false)) : this.func_176223_P().func_177226_a(field_176387_N, EnumFacing.DOWN).func_177226_a(field_176584_b, Boolean.valueOf(false));
   }

   public void func_189540_a(IBlockState p_189540_1_, World p_189540_2_, BlockPos p_189540_3_, Block p_189540_4_, BlockPos p_189540_5_) {
      if (this.func_176583_e(p_189540_2_, p_189540_3_, p_189540_1_) && !func_181088_a(p_189540_2_, p_189540_3_, (EnumFacing)p_189540_1_.func_177229_b(field_176387_N))) {
         this.func_176226_b(p_189540_2_, p_189540_3_, p_189540_1_, 0);
         p_189540_2_.func_175698_g(p_189540_3_);
      }

   }

   private boolean func_176583_e(World p_176583_1_, BlockPos p_176583_2_, IBlockState p_176583_3_) {
      if (this.func_176196_c(p_176583_1_, p_176583_2_)) {
         return true;
      } else {
         this.func_176226_b(p_176583_1_, p_176583_2_, p_176583_3_, 0);
         p_176583_1_.func_175698_g(p_176583_2_);
         return false;
      }
   }

   public AxisAlignedBB func_185496_a(IBlockState p_185496_1_, IBlockAccess p_185496_2_, BlockPos p_185496_3_) {
      EnumFacing enumfacing = (EnumFacing)p_185496_1_.func_177229_b(field_176387_N);
      boolean flag = ((Boolean)p_185496_1_.func_177229_b(field_176584_b)).booleanValue();
      switch(enumfacing) {
      case EAST:
         return flag ? field_185629_G : field_185628_g;
      case WEST:
         return flag ? field_185627_F : field_185626_f;
      case SOUTH:
         return flag ? field_185625_E : field_185624_e;
      case NORTH:
      default:
         return flag ? field_185623_D : field_185622_d;
      case UP:
         return flag ? field_185621_C : field_185620_c;
      case DOWN:
         return flag ? field_185619_B : field_185618_b;
      }
   }

   public boolean func_180639_a(World p_180639_1_, BlockPos p_180639_2_, IBlockState p_180639_3_, EntityPlayer p_180639_4_, EnumHand p_180639_5_, EnumFacing p_180639_6_, float p_180639_7_, float p_180639_8_, float p_180639_9_) {
      if (((Boolean)p_180639_3_.func_177229_b(field_176584_b)).booleanValue()) {
         return true;
      } else {
         p_180639_1_.func_180501_a(p_180639_2_, p_180639_3_.func_177226_a(field_176584_b, Boolean.valueOf(true)), 3);
         p_180639_1_.func_175704_b(p_180639_2_, p_180639_2_);
         this.func_185615_a(p_180639_4_, p_180639_1_, p_180639_2_);
         this.func_176582_b(p_180639_1_, p_180639_2_, (EnumFacing)p_180639_3_.func_177229_b(field_176387_N));
         p_180639_1_.func_175684_a(p_180639_2_, this, this.func_149738_a(p_180639_1_));
         return true;
      }
   }

   protected abstract void func_185615_a(@Nullable EntityPlayer var1, World var2, BlockPos var3);

   protected abstract void func_185617_b(World var1, BlockPos var2);

   public void func_180663_b(World p_180663_1_, BlockPos p_180663_2_, IBlockState p_180663_3_) {
      if (((Boolean)p_180663_3_.func_177229_b(field_176584_b)).booleanValue()) {
         this.func_176582_b(p_180663_1_, p_180663_2_, (EnumFacing)p_180663_3_.func_177229_b(field_176387_N));
      }

      super.func_180663_b(p_180663_1_, p_180663_2_, p_180663_3_);
   }

   public int func_180656_a(IBlockState p_180656_1_, IBlockAccess p_180656_2_, BlockPos p_180656_3_, EnumFacing p_180656_4_) {
      return ((Boolean)p_180656_1_.func_177229_b(field_176584_b)).booleanValue() ? 15 : 0;
   }

   public int func_176211_b(IBlockState p_176211_1_, IBlockAccess p_176211_2_, BlockPos p_176211_3_, EnumFacing p_176211_4_) {
      if (!((Boolean)p_176211_1_.func_177229_b(field_176584_b)).booleanValue()) {
         return 0;
      } else {
         return p_176211_1_.func_177229_b(field_176387_N) == p_176211_4_ ? 15 : 0;
      }
   }

   public boolean func_149744_f(IBlockState p_149744_1_) {
      return true;
   }

   public void func_180645_a(World p_180645_1_, BlockPos p_180645_2_, IBlockState p_180645_3_, Random p_180645_4_) {
   }

   public void func_180650_b(World p_180650_1_, BlockPos p_180650_2_, IBlockState p_180650_3_, Random p_180650_4_) {
      if (!p_180650_1_.field_72995_K) {
         if (((Boolean)p_180650_3_.func_177229_b(field_176584_b)).booleanValue()) {
            if (this.field_150047_a) {
               this.func_185616_e(p_180650_3_, p_180650_1_, p_180650_2_);
            } else {
               p_180650_1_.func_175656_a(p_180650_2_, p_180650_3_.func_177226_a(field_176584_b, Boolean.valueOf(false)));
               this.func_176582_b(p_180650_1_, p_180650_2_, (EnumFacing)p_180650_3_.func_177229_b(field_176387_N));
               this.func_185617_b(p_180650_1_, p_180650_2_);
               p_180650_1_.func_175704_b(p_180650_2_, p_180650_2_);
            }

         }
      }
   }

   public void func_180634_a(World p_180634_1_, BlockPos p_180634_2_, IBlockState p_180634_3_, Entity p_180634_4_) {
      if (!p_180634_1_.field_72995_K) {
         if (this.field_150047_a) {
            if (!((Boolean)p_180634_3_.func_177229_b(field_176584_b)).booleanValue()) {
               this.func_185616_e(p_180634_3_, p_180634_1_, p_180634_2_);
            }
         }
      }
   }

   private void func_185616_e(IBlockState p_185616_1_, World p_185616_2_, BlockPos p_185616_3_) {
      List<? extends Entity> list = p_185616_2_.<Entity>func_72872_a(EntityArrow.class, p_185616_1_.func_185900_c(p_185616_2_, p_185616_3_).func_186670_a(p_185616_3_));
      boolean flag = !list.isEmpty();
      boolean flag1 = ((Boolean)p_185616_1_.func_177229_b(field_176584_b)).booleanValue();
      if (flag && !flag1) {
         p_185616_2_.func_175656_a(p_185616_3_, p_185616_1_.func_177226_a(field_176584_b, Boolean.valueOf(true)));
         this.func_176582_b(p_185616_2_, p_185616_3_, (EnumFacing)p_185616_1_.func_177229_b(field_176387_N));
         p_185616_2_.func_175704_b(p_185616_3_, p_185616_3_);
         this.func_185615_a((EntityPlayer)null, p_185616_2_, p_185616_3_);
      }

      if (!flag && flag1) {
         p_185616_2_.func_175656_a(p_185616_3_, p_185616_1_.func_177226_a(field_176584_b, Boolean.valueOf(false)));
         this.func_176582_b(p_185616_2_, p_185616_3_, (EnumFacing)p_185616_1_.func_177229_b(field_176387_N));
         p_185616_2_.func_175704_b(p_185616_3_, p_185616_3_);
         this.func_185617_b(p_185616_2_, p_185616_3_);
      }

      if (flag) {
         p_185616_2_.func_175684_a(new BlockPos(p_185616_3_), this, this.func_149738_a(p_185616_2_));
      }

   }

   private void func_176582_b(World p_176582_1_, BlockPos p_176582_2_, EnumFacing p_176582_3_) {
      p_176582_1_.func_175685_c(p_176582_2_, this, false);
      p_176582_1_.func_175685_c(p_176582_2_.func_177972_a(p_176582_3_.func_176734_d()), this, false);
   }

   public IBlockState func_176203_a(int p_176203_1_) {
      EnumFacing enumfacing;
      switch(p_176203_1_ & 7) {
      case 0:
         enumfacing = EnumFacing.DOWN;
         break;
      case 1:
         enumfacing = EnumFacing.EAST;
         break;
      case 2:
         enumfacing = EnumFacing.WEST;
         break;
      case 3:
         enumfacing = EnumFacing.SOUTH;
         break;
      case 4:
         enumfacing = EnumFacing.NORTH;
         break;
      case 5:
      default:
         enumfacing = EnumFacing.UP;
      }

      return this.func_176223_P().func_177226_a(field_176387_N, enumfacing).func_177226_a(field_176584_b, Boolean.valueOf((p_176203_1_ & 8) > 0));
   }

   public int func_176201_c(IBlockState p_176201_1_) {
      int i;
      switch((EnumFacing)p_176201_1_.func_177229_b(field_176387_N)) {
      case EAST:
         i = 1;
         break;
      case WEST:
         i = 2;
         break;
      case SOUTH:
         i = 3;
         break;
      case NORTH:
         i = 4;
         break;
      case UP:
      default:
         i = 5;
         break;
      case DOWN:
         i = 0;
      }

      if (((Boolean)p_176201_1_.func_177229_b(field_176584_b)).booleanValue()) {
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
      return new BlockStateContainer(this, new IProperty[]{field_176387_N, field_176584_b});
   }

   public BlockFaceShape func_193383_a(IBlockAccess p_193383_1_, IBlockState p_193383_2_, BlockPos p_193383_3_, EnumFacing p_193383_4_) {
      return BlockFaceShape.UNDEFINED;
   }
}
