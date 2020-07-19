package net.minecraft.block;

import com.google.common.base.MoreObjects;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockTripWireHook extends Block {
   public static final PropertyDirection field_176264_a = BlockHorizontal.field_185512_D;
   public static final PropertyBool field_176263_b = PropertyBool.func_177716_a("powered");
   public static final PropertyBool field_176265_M = PropertyBool.func_177716_a("attached");
   protected static final AxisAlignedBB field_185743_d = new AxisAlignedBB(0.3125D, 0.0D, 0.625D, 0.6875D, 0.625D, 1.0D);
   protected static final AxisAlignedBB field_185744_e = new AxisAlignedBB(0.3125D, 0.0D, 0.0D, 0.6875D, 0.625D, 0.375D);
   protected static final AxisAlignedBB field_185745_f = new AxisAlignedBB(0.625D, 0.0D, 0.3125D, 1.0D, 0.625D, 0.6875D);
   protected static final AxisAlignedBB field_185746_g = new AxisAlignedBB(0.0D, 0.0D, 0.3125D, 0.375D, 0.625D, 0.6875D);

   public BlockTripWireHook() {
      super(Material.field_151594_q);
      this.func_180632_j(this.field_176227_L.func_177621_b().func_177226_a(field_176264_a, EnumFacing.NORTH).func_177226_a(field_176263_b, Boolean.valueOf(false)).func_177226_a(field_176265_M, Boolean.valueOf(false)));
      this.func_149647_a(CreativeTabs.field_78028_d);
      this.func_149675_a(true);
   }

   public AxisAlignedBB func_185496_a(IBlockState p_185496_1_, IBlockAccess p_185496_2_, BlockPos p_185496_3_) {
      switch((EnumFacing)p_185496_1_.func_177229_b(field_176264_a)) {
      case EAST:
      default:
         return field_185746_g;
      case WEST:
         return field_185745_f;
      case SOUTH:
         return field_185744_e;
      case NORTH:
         return field_185743_d;
      }
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
      EnumFacing enumfacing = p_176198_3_.func_176734_d();
      BlockPos blockpos = p_176198_2_.func_177972_a(enumfacing);
      IBlockState iblockstate = p_176198_1_.func_180495_p(blockpos);
      boolean flag = func_193382_c(iblockstate.func_177230_c());
      return !flag && p_176198_3_.func_176740_k().func_176722_c() && iblockstate.func_193401_d(p_176198_1_, blockpos, p_176198_3_) == BlockFaceShape.SOLID && !iblockstate.func_185897_m();
   }

   public boolean func_176196_c(World p_176196_1_, BlockPos p_176196_2_) {
      for(EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
         if (this.func_176198_a(p_176196_1_, p_176196_2_, enumfacing)) {
            return true;
         }
      }

      return false;
   }

   public IBlockState func_180642_a(World p_180642_1_, BlockPos p_180642_2_, EnumFacing p_180642_3_, float p_180642_4_, float p_180642_5_, float p_180642_6_, int p_180642_7_, EntityLivingBase p_180642_8_) {
      IBlockState iblockstate = this.func_176223_P().func_177226_a(field_176263_b, Boolean.valueOf(false)).func_177226_a(field_176265_M, Boolean.valueOf(false));
      if (p_180642_3_.func_176740_k().func_176722_c()) {
         iblockstate = iblockstate.func_177226_a(field_176264_a, p_180642_3_);
      }

      return iblockstate;
   }

   public void func_180633_a(World p_180633_1_, BlockPos p_180633_2_, IBlockState p_180633_3_, EntityLivingBase p_180633_4_, ItemStack p_180633_5_) {
      this.func_176260_a(p_180633_1_, p_180633_2_, p_180633_3_, false, false, -1, (IBlockState)null);
   }

   public void func_189540_a(IBlockState p_189540_1_, World p_189540_2_, BlockPos p_189540_3_, Block p_189540_4_, BlockPos p_189540_5_) {
      if (p_189540_4_ != this) {
         if (this.func_176261_e(p_189540_2_, p_189540_3_, p_189540_1_)) {
            EnumFacing enumfacing = (EnumFacing)p_189540_1_.func_177229_b(field_176264_a);
            if (!this.func_176198_a(p_189540_2_, p_189540_3_, enumfacing)) {
               this.func_176226_b(p_189540_2_, p_189540_3_, p_189540_1_, 0);
               p_189540_2_.func_175698_g(p_189540_3_);
            }
         }

      }
   }

   public void func_176260_a(World p_176260_1_, BlockPos p_176260_2_, IBlockState p_176260_3_, boolean p_176260_4_, boolean p_176260_5_, int p_176260_6_, @Nullable IBlockState p_176260_7_) {
      EnumFacing enumfacing = (EnumFacing)p_176260_3_.func_177229_b(field_176264_a);
      boolean flag = ((Boolean)p_176260_3_.func_177229_b(field_176265_M)).booleanValue();
      boolean flag1 = ((Boolean)p_176260_3_.func_177229_b(field_176263_b)).booleanValue();
      boolean flag2 = !p_176260_4_;
      boolean flag3 = false;
      int i = 0;
      IBlockState[] aiblockstate = new IBlockState[42];

      for(int j = 1; j < 42; ++j) {
         BlockPos blockpos = p_176260_2_.func_177967_a(enumfacing, j);
         IBlockState iblockstate = p_176260_1_.func_180495_p(blockpos);
         if (iblockstate.func_177230_c() == Blocks.field_150479_bC) {
            if (iblockstate.func_177229_b(field_176264_a) == enumfacing.func_176734_d()) {
               i = j;
            }
            break;
         }

         if (iblockstate.func_177230_c() != Blocks.field_150473_bD && j != p_176260_6_) {
            aiblockstate[j] = null;
            flag2 = false;
         } else {
            if (j == p_176260_6_) {
               iblockstate = (IBlockState)MoreObjects.firstNonNull(p_176260_7_, iblockstate);
            }

            boolean flag4 = !((Boolean)iblockstate.func_177229_b(BlockTripWire.field_176295_N)).booleanValue();
            boolean flag5 = ((Boolean)iblockstate.func_177229_b(BlockTripWire.field_176293_a)).booleanValue();
            flag3 |= flag4 && flag5;
            aiblockstate[j] = iblockstate;
            if (j == p_176260_6_) {
               p_176260_1_.func_175684_a(p_176260_2_, this, this.func_149738_a(p_176260_1_));
               flag2 &= flag4;
            }
         }
      }

      flag2 = flag2 & i > 1;
      flag3 = flag3 & flag2;
      IBlockState iblockstate1 = this.func_176223_P().func_177226_a(field_176265_M, Boolean.valueOf(flag2)).func_177226_a(field_176263_b, Boolean.valueOf(flag3));
      if (i > 0) {
         BlockPos blockpos1 = p_176260_2_.func_177967_a(enumfacing, i);
         EnumFacing enumfacing1 = enumfacing.func_176734_d();
         p_176260_1_.func_180501_a(blockpos1, iblockstate1.func_177226_a(field_176264_a, enumfacing1), 3);
         this.func_176262_b(p_176260_1_, blockpos1, enumfacing1);
         this.func_180694_a(p_176260_1_, blockpos1, flag2, flag3, flag, flag1);
      }

      this.func_180694_a(p_176260_1_, p_176260_2_, flag2, flag3, flag, flag1);
      if (!p_176260_4_) {
         p_176260_1_.func_180501_a(p_176260_2_, iblockstate1.func_177226_a(field_176264_a, enumfacing), 3);
         if (p_176260_5_) {
            this.func_176262_b(p_176260_1_, p_176260_2_, enumfacing);
         }
      }

      if (flag != flag2) {
         for(int k = 1; k < i; ++k) {
            BlockPos blockpos2 = p_176260_2_.func_177967_a(enumfacing, k);
            IBlockState iblockstate2 = aiblockstate[k];
            if (iblockstate2 != null && p_176260_1_.func_180495_p(blockpos2).func_185904_a() != Material.field_151579_a) {
               p_176260_1_.func_180501_a(blockpos2, iblockstate2.func_177226_a(field_176265_M, Boolean.valueOf(flag2)), 3);
            }
         }
      }

   }

   public void func_180645_a(World p_180645_1_, BlockPos p_180645_2_, IBlockState p_180645_3_, Random p_180645_4_) {
   }

   public void func_180650_b(World p_180650_1_, BlockPos p_180650_2_, IBlockState p_180650_3_, Random p_180650_4_) {
      this.func_176260_a(p_180650_1_, p_180650_2_, p_180650_3_, false, true, -1, (IBlockState)null);
   }

   private void func_180694_a(World p_180694_1_, BlockPos p_180694_2_, boolean p_180694_3_, boolean p_180694_4_, boolean p_180694_5_, boolean p_180694_6_) {
      if (p_180694_4_ && !p_180694_6_) {
         p_180694_1_.func_184133_a((EntityPlayer)null, p_180694_2_, SoundEvents.field_187907_gg, SoundCategory.BLOCKS, 0.4F, 0.6F);
      } else if (!p_180694_4_ && p_180694_6_) {
         p_180694_1_.func_184133_a((EntityPlayer)null, p_180694_2_, SoundEvents.field_187906_gf, SoundCategory.BLOCKS, 0.4F, 0.5F);
      } else if (p_180694_3_ && !p_180694_5_) {
         p_180694_1_.func_184133_a((EntityPlayer)null, p_180694_2_, SoundEvents.field_187905_ge, SoundCategory.BLOCKS, 0.4F, 0.7F);
      } else if (!p_180694_3_ && p_180694_5_) {
         p_180694_1_.func_184133_a((EntityPlayer)null, p_180694_2_, SoundEvents.field_187908_gh, SoundCategory.BLOCKS, 0.4F, 1.2F / (p_180694_1_.field_73012_v.nextFloat() * 0.2F + 0.9F));
      }

   }

   private void func_176262_b(World p_176262_1_, BlockPos p_176262_2_, EnumFacing p_176262_3_) {
      p_176262_1_.func_175685_c(p_176262_2_, this, false);
      p_176262_1_.func_175685_c(p_176262_2_.func_177972_a(p_176262_3_.func_176734_d()), this, false);
   }

   private boolean func_176261_e(World p_176261_1_, BlockPos p_176261_2_, IBlockState p_176261_3_) {
      if (!this.func_176196_c(p_176261_1_, p_176261_2_)) {
         this.func_176226_b(p_176261_1_, p_176261_2_, p_176261_3_, 0);
         p_176261_1_.func_175698_g(p_176261_2_);
         return false;
      } else {
         return true;
      }
   }

   public void func_180663_b(World p_180663_1_, BlockPos p_180663_2_, IBlockState p_180663_3_) {
      boolean flag = ((Boolean)p_180663_3_.func_177229_b(field_176265_M)).booleanValue();
      boolean flag1 = ((Boolean)p_180663_3_.func_177229_b(field_176263_b)).booleanValue();
      if (flag || flag1) {
         this.func_176260_a(p_180663_1_, p_180663_2_, p_180663_3_, true, false, -1, (IBlockState)null);
      }

      if (flag1) {
         p_180663_1_.func_175685_c(p_180663_2_, this, false);
         p_180663_1_.func_175685_c(p_180663_2_.func_177972_a(((EnumFacing)p_180663_3_.func_177229_b(field_176264_a)).func_176734_d()), this, false);
      }

      super.func_180663_b(p_180663_1_, p_180663_2_, p_180663_3_);
   }

   public int func_180656_a(IBlockState p_180656_1_, IBlockAccess p_180656_2_, BlockPos p_180656_3_, EnumFacing p_180656_4_) {
      return ((Boolean)p_180656_1_.func_177229_b(field_176263_b)).booleanValue() ? 15 : 0;
   }

   public int func_176211_b(IBlockState p_176211_1_, IBlockAccess p_176211_2_, BlockPos p_176211_3_, EnumFacing p_176211_4_) {
      if (!((Boolean)p_176211_1_.func_177229_b(field_176263_b)).booleanValue()) {
         return 0;
      } else {
         return p_176211_1_.func_177229_b(field_176264_a) == p_176211_4_ ? 15 : 0;
      }
   }

   public boolean func_149744_f(IBlockState p_149744_1_) {
      return true;
   }

   public BlockRenderLayer func_180664_k() {
      return BlockRenderLayer.CUTOUT_MIPPED;
   }

   public IBlockState func_176203_a(int p_176203_1_) {
      return this.func_176223_P().func_177226_a(field_176264_a, EnumFacing.func_176731_b(p_176203_1_ & 3)).func_177226_a(field_176263_b, Boolean.valueOf((p_176203_1_ & 8) > 0)).func_177226_a(field_176265_M, Boolean.valueOf((p_176203_1_ & 4) > 0));
   }

   public int func_176201_c(IBlockState p_176201_1_) {
      int i = 0;
      i = i | ((EnumFacing)p_176201_1_.func_177229_b(field_176264_a)).func_176736_b();
      if (((Boolean)p_176201_1_.func_177229_b(field_176263_b)).booleanValue()) {
         i |= 8;
      }

      if (((Boolean)p_176201_1_.func_177229_b(field_176265_M)).booleanValue()) {
         i |= 4;
      }

      return i;
   }

   public IBlockState func_185499_a(IBlockState p_185499_1_, Rotation p_185499_2_) {
      return p_185499_1_.func_177226_a(field_176264_a, p_185499_2_.func_185831_a((EnumFacing)p_185499_1_.func_177229_b(field_176264_a)));
   }

   public IBlockState func_185471_a(IBlockState p_185471_1_, Mirror p_185471_2_) {
      return p_185471_1_.func_185907_a(p_185471_2_.func_185800_a((EnumFacing)p_185471_1_.func_177229_b(field_176264_a)));
   }

   protected BlockStateContainer func_180661_e() {
      return new BlockStateContainer(this, new IProperty[]{field_176264_a, field_176263_b, field_176265_M});
   }

   public BlockFaceShape func_193383_a(IBlockAccess p_193383_1_, IBlockState p_193383_2_, BlockPos p_193383_3_, EnumFacing p_193383_4_) {
      return BlockFaceShape.UNDEFINED;
   }
}
