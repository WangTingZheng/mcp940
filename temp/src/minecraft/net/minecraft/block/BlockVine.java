package net.minecraft.block;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockVine extends Block {
   public static final PropertyBool field_176277_a = PropertyBool.func_177716_a("up");
   public static final PropertyBool field_176273_b = PropertyBool.func_177716_a("north");
   public static final PropertyBool field_176278_M = PropertyBool.func_177716_a("east");
   public static final PropertyBool field_176279_N = PropertyBool.func_177716_a("south");
   public static final PropertyBool field_176280_O = PropertyBool.func_177716_a("west");
   public static final PropertyBool[] field_176274_P = new PropertyBool[]{field_176277_a, field_176273_b, field_176279_N, field_176280_O, field_176278_M};
   protected static final AxisAlignedBB field_185757_g = new AxisAlignedBB(0.0D, 0.9375D, 0.0D, 1.0D, 1.0D, 1.0D);
   protected static final AxisAlignedBB field_185753_B = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.0625D, 1.0D, 1.0D);
   protected static final AxisAlignedBB field_185754_C = new AxisAlignedBB(0.9375D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
   protected static final AxisAlignedBB field_185755_D = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.0625D);
   protected static final AxisAlignedBB field_185756_E = new AxisAlignedBB(0.0D, 0.0D, 0.9375D, 1.0D, 1.0D, 1.0D);

   public BlockVine() {
      super(Material.field_151582_l);
      this.func_180632_j(this.field_176227_L.func_177621_b().func_177226_a(field_176277_a, Boolean.valueOf(false)).func_177226_a(field_176273_b, Boolean.valueOf(false)).func_177226_a(field_176278_M, Boolean.valueOf(false)).func_177226_a(field_176279_N, Boolean.valueOf(false)).func_177226_a(field_176280_O, Boolean.valueOf(false)));
      this.func_149675_a(true);
      this.func_149647_a(CreativeTabs.field_78031_c);
   }

   @Nullable
   public AxisAlignedBB func_180646_a(IBlockState p_180646_1_, IBlockAccess p_180646_2_, BlockPos p_180646_3_) {
      return field_185506_k;
   }

   public AxisAlignedBB func_185496_a(IBlockState p_185496_1_, IBlockAccess p_185496_2_, BlockPos p_185496_3_) {
      p_185496_1_ = p_185496_1_.func_185899_b(p_185496_2_, p_185496_3_);
      int i = 0;
      AxisAlignedBB axisalignedbb = field_185505_j;
      if (((Boolean)p_185496_1_.func_177229_b(field_176277_a)).booleanValue()) {
         axisalignedbb = field_185757_g;
         ++i;
      }

      if (((Boolean)p_185496_1_.func_177229_b(field_176273_b)).booleanValue()) {
         axisalignedbb = field_185755_D;
         ++i;
      }

      if (((Boolean)p_185496_1_.func_177229_b(field_176278_M)).booleanValue()) {
         axisalignedbb = field_185754_C;
         ++i;
      }

      if (((Boolean)p_185496_1_.func_177229_b(field_176279_N)).booleanValue()) {
         axisalignedbb = field_185756_E;
         ++i;
      }

      if (((Boolean)p_185496_1_.func_177229_b(field_176280_O)).booleanValue()) {
         axisalignedbb = field_185753_B;
         ++i;
      }

      return i == 1 ? axisalignedbb : field_185505_j;
   }

   public IBlockState func_176221_a(IBlockState p_176221_1_, IBlockAccess p_176221_2_, BlockPos p_176221_3_) {
      BlockPos blockpos = p_176221_3_.func_177984_a();
      return p_176221_1_.func_177226_a(field_176277_a, Boolean.valueOf(p_176221_2_.func_180495_p(blockpos).func_193401_d(p_176221_2_, blockpos, EnumFacing.DOWN) == BlockFaceShape.SOLID));
   }

   public boolean func_149662_c(IBlockState p_149662_1_) {
      return false;
   }

   public boolean func_149686_d(IBlockState p_149686_1_) {
      return false;
   }

   public boolean func_176200_f(IBlockAccess p_176200_1_, BlockPos p_176200_2_) {
      return true;
   }

   public boolean func_176198_a(World p_176198_1_, BlockPos p_176198_2_, EnumFacing p_176198_3_) {
      return p_176198_3_ != EnumFacing.DOWN && p_176198_3_ != EnumFacing.UP && this.func_193395_a(p_176198_1_, p_176198_2_, p_176198_3_);
   }

   public boolean func_193395_a(World p_193395_1_, BlockPos p_193395_2_, EnumFacing p_193395_3_) {
      Block block = p_193395_1_.func_180495_p(p_193395_2_.func_177984_a()).func_177230_c();
      return this.func_193396_c(p_193395_1_, p_193395_2_.func_177972_a(p_193395_3_.func_176734_d()), p_193395_3_) && (block == Blocks.field_150350_a || block == Blocks.field_150395_bd || this.func_193396_c(p_193395_1_, p_193395_2_.func_177984_a(), EnumFacing.UP));
   }

   private boolean func_193396_c(World p_193396_1_, BlockPos p_193396_2_, EnumFacing p_193396_3_) {
      IBlockState iblockstate = p_193396_1_.func_180495_p(p_193396_2_);
      return iblockstate.func_193401_d(p_193396_1_, p_193396_2_, p_193396_3_) == BlockFaceShape.SOLID && !func_193397_e(iblockstate.func_177230_c());
   }

   protected static boolean func_193397_e(Block p_193397_0_) {
      return p_193397_0_ instanceof BlockShulkerBox || p_193397_0_ == Blocks.field_150461_bJ || p_193397_0_ == Blocks.field_150383_bp || p_193397_0_ == Blocks.field_150359_w || p_193397_0_ == Blocks.field_150399_cn || p_193397_0_ == Blocks.field_150331_J || p_193397_0_ == Blocks.field_150320_F || p_193397_0_ == Blocks.field_150332_K || p_193397_0_ == Blocks.field_150415_aT;
   }

   private boolean func_176269_e(World p_176269_1_, BlockPos p_176269_2_, IBlockState p_176269_3_) {
      IBlockState iblockstate = p_176269_3_;

      for(EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
         PropertyBool propertybool = func_176267_a(enumfacing);
         if (((Boolean)p_176269_3_.func_177229_b(propertybool)).booleanValue() && !this.func_193395_a(p_176269_1_, p_176269_2_, enumfacing.func_176734_d())) {
            IBlockState iblockstate1 = p_176269_1_.func_180495_p(p_176269_2_.func_177984_a());
            if (iblockstate1.func_177230_c() != this || !((Boolean)iblockstate1.func_177229_b(propertybool)).booleanValue()) {
               p_176269_3_ = p_176269_3_.func_177226_a(propertybool, Boolean.valueOf(false));
            }
         }
      }

      if (func_176268_d(p_176269_3_) == 0) {
         return false;
      } else {
         if (iblockstate != p_176269_3_) {
            p_176269_1_.func_180501_a(p_176269_2_, p_176269_3_, 2);
         }

         return true;
      }
   }

   public void func_189540_a(IBlockState p_189540_1_, World p_189540_2_, BlockPos p_189540_3_, Block p_189540_4_, BlockPos p_189540_5_) {
      if (!p_189540_2_.field_72995_K && !this.func_176269_e(p_189540_2_, p_189540_3_, p_189540_1_)) {
         this.func_176226_b(p_189540_2_, p_189540_3_, p_189540_1_, 0);
         p_189540_2_.func_175698_g(p_189540_3_);
      }

   }

   public void func_180650_b(World p_180650_1_, BlockPos p_180650_2_, IBlockState p_180650_3_, Random p_180650_4_) {
      if (!p_180650_1_.field_72995_K) {
         if (p_180650_1_.field_73012_v.nextInt(4) == 0) {
            int i = 4;
            int j = 5;
            boolean flag = false;

            label181:
            for(int k = -4; k <= 4; ++k) {
               for(int l = -4; l <= 4; ++l) {
                  for(int i1 = -1; i1 <= 1; ++i1) {
                     if (p_180650_1_.func_180495_p(p_180650_2_.func_177982_a(k, i1, l)).func_177230_c() == this) {
                        --j;
                        if (j <= 0) {
                           flag = true;
                           break label181;
                        }
                     }
                  }
               }
            }

            EnumFacing enumfacing1 = EnumFacing.func_176741_a(p_180650_4_);
            BlockPos blockpos2 = p_180650_2_.func_177984_a();
            if (enumfacing1 == EnumFacing.UP && p_180650_2_.func_177956_o() < 255 && p_180650_1_.func_175623_d(blockpos2)) {
               IBlockState iblockstate2 = p_180650_3_;

               for(EnumFacing enumfacing2 : EnumFacing.Plane.HORIZONTAL) {
                  if (p_180650_4_.nextBoolean() && this.func_193395_a(p_180650_1_, blockpos2, enumfacing2.func_176734_d())) {
                     iblockstate2 = iblockstate2.func_177226_a(func_176267_a(enumfacing2), Boolean.valueOf(true));
                  } else {
                     iblockstate2 = iblockstate2.func_177226_a(func_176267_a(enumfacing2), Boolean.valueOf(false));
                  }
               }

               if (((Boolean)iblockstate2.func_177229_b(field_176273_b)).booleanValue() || ((Boolean)iblockstate2.func_177229_b(field_176278_M)).booleanValue() || ((Boolean)iblockstate2.func_177229_b(field_176279_N)).booleanValue() || ((Boolean)iblockstate2.func_177229_b(field_176280_O)).booleanValue()) {
                  p_180650_1_.func_180501_a(blockpos2, iblockstate2, 2);
               }

            } else if (enumfacing1.func_176740_k().func_176722_c() && !((Boolean)p_180650_3_.func_177229_b(func_176267_a(enumfacing1))).booleanValue()) {
               if (!flag) {
                  BlockPos blockpos4 = p_180650_2_.func_177972_a(enumfacing1);
                  IBlockState iblockstate3 = p_180650_1_.func_180495_p(blockpos4);
                  Block block1 = iblockstate3.func_177230_c();
                  if (block1.field_149764_J == Material.field_151579_a) {
                     EnumFacing enumfacing3 = enumfacing1.func_176746_e();
                     EnumFacing enumfacing4 = enumfacing1.func_176735_f();
                     boolean flag1 = ((Boolean)p_180650_3_.func_177229_b(func_176267_a(enumfacing3))).booleanValue();
                     boolean flag2 = ((Boolean)p_180650_3_.func_177229_b(func_176267_a(enumfacing4))).booleanValue();
                     BlockPos blockpos = blockpos4.func_177972_a(enumfacing3);
                     BlockPos blockpos1 = blockpos4.func_177972_a(enumfacing4);
                     if (flag1 && this.func_193395_a(p_180650_1_, blockpos.func_177972_a(enumfacing3), enumfacing3)) {
                        p_180650_1_.func_180501_a(blockpos4, this.func_176223_P().func_177226_a(func_176267_a(enumfacing3), Boolean.valueOf(true)), 2);
                     } else if (flag2 && this.func_193395_a(p_180650_1_, blockpos1.func_177972_a(enumfacing4), enumfacing4)) {
                        p_180650_1_.func_180501_a(blockpos4, this.func_176223_P().func_177226_a(func_176267_a(enumfacing4), Boolean.valueOf(true)), 2);
                     } else if (flag1 && p_180650_1_.func_175623_d(blockpos) && this.func_193395_a(p_180650_1_, blockpos, enumfacing1)) {
                        p_180650_1_.func_180501_a(blockpos, this.func_176223_P().func_177226_a(func_176267_a(enumfacing1.func_176734_d()), Boolean.valueOf(true)), 2);
                     } else if (flag2 && p_180650_1_.func_175623_d(blockpos1) && this.func_193395_a(p_180650_1_, blockpos1, enumfacing1)) {
                        p_180650_1_.func_180501_a(blockpos1, this.func_176223_P().func_177226_a(func_176267_a(enumfacing1.func_176734_d()), Boolean.valueOf(true)), 2);
                     }
                  } else if (iblockstate3.func_193401_d(p_180650_1_, blockpos4, enumfacing1) == BlockFaceShape.SOLID) {
                     p_180650_1_.func_180501_a(p_180650_2_, p_180650_3_.func_177226_a(func_176267_a(enumfacing1), Boolean.valueOf(true)), 2);
                  }

               }
            } else {
               if (p_180650_2_.func_177956_o() > 1) {
                  BlockPos blockpos3 = p_180650_2_.func_177977_b();
                  IBlockState iblockstate = p_180650_1_.func_180495_p(blockpos3);
                  Block block = iblockstate.func_177230_c();
                  if (block.field_149764_J == Material.field_151579_a) {
                     IBlockState iblockstate1 = p_180650_3_;

                     for(EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
                        if (p_180650_4_.nextBoolean()) {
                           iblockstate1 = iblockstate1.func_177226_a(func_176267_a(enumfacing), Boolean.valueOf(false));
                        }
                     }

                     if (((Boolean)iblockstate1.func_177229_b(field_176273_b)).booleanValue() || ((Boolean)iblockstate1.func_177229_b(field_176278_M)).booleanValue() || ((Boolean)iblockstate1.func_177229_b(field_176279_N)).booleanValue() || ((Boolean)iblockstate1.func_177229_b(field_176280_O)).booleanValue()) {
                        p_180650_1_.func_180501_a(blockpos3, iblockstate1, 2);
                     }
                  } else if (block == this) {
                     IBlockState iblockstate4 = iblockstate;

                     for(EnumFacing enumfacing5 : EnumFacing.Plane.HORIZONTAL) {
                        PropertyBool propertybool = func_176267_a(enumfacing5);
                        if (p_180650_4_.nextBoolean() && ((Boolean)p_180650_3_.func_177229_b(propertybool)).booleanValue()) {
                           iblockstate4 = iblockstate4.func_177226_a(propertybool, Boolean.valueOf(true));
                        }
                     }

                     if (((Boolean)iblockstate4.func_177229_b(field_176273_b)).booleanValue() || ((Boolean)iblockstate4.func_177229_b(field_176278_M)).booleanValue() || ((Boolean)iblockstate4.func_177229_b(field_176279_N)).booleanValue() || ((Boolean)iblockstate4.func_177229_b(field_176280_O)).booleanValue()) {
                        p_180650_1_.func_180501_a(blockpos3, iblockstate4, 2);
                     }
                  }
               }

            }
         }
      }
   }

   public IBlockState func_180642_a(World p_180642_1_, BlockPos p_180642_2_, EnumFacing p_180642_3_, float p_180642_4_, float p_180642_5_, float p_180642_6_, int p_180642_7_, EntityLivingBase p_180642_8_) {
      IBlockState iblockstate = this.func_176223_P().func_177226_a(field_176277_a, Boolean.valueOf(false)).func_177226_a(field_176273_b, Boolean.valueOf(false)).func_177226_a(field_176278_M, Boolean.valueOf(false)).func_177226_a(field_176279_N, Boolean.valueOf(false)).func_177226_a(field_176280_O, Boolean.valueOf(false));
      return p_180642_3_.func_176740_k().func_176722_c() ? iblockstate.func_177226_a(func_176267_a(p_180642_3_.func_176734_d()), Boolean.valueOf(true)) : iblockstate;
   }

   public Item func_180660_a(IBlockState p_180660_1_, Random p_180660_2_, int p_180660_3_) {
      return Items.field_190931_a;
   }

   public int func_149745_a(Random p_149745_1_) {
      return 0;
   }

   public void func_180657_a(World p_180657_1_, EntityPlayer p_180657_2_, BlockPos p_180657_3_, IBlockState p_180657_4_, @Nullable TileEntity p_180657_5_, ItemStack p_180657_6_) {
      if (!p_180657_1_.field_72995_K && p_180657_6_.func_77973_b() == Items.field_151097_aZ) {
         p_180657_2_.func_71029_a(StatList.func_188055_a(this));
         func_180635_a(p_180657_1_, p_180657_3_, new ItemStack(Blocks.field_150395_bd, 1, 0));
      } else {
         super.func_180657_a(p_180657_1_, p_180657_2_, p_180657_3_, p_180657_4_, p_180657_5_, p_180657_6_);
      }

   }

   public BlockRenderLayer func_180664_k() {
      return BlockRenderLayer.CUTOUT;
   }

   public IBlockState func_176203_a(int p_176203_1_) {
      return this.func_176223_P().func_177226_a(field_176279_N, Boolean.valueOf((p_176203_1_ & 1) > 0)).func_177226_a(field_176280_O, Boolean.valueOf((p_176203_1_ & 2) > 0)).func_177226_a(field_176273_b, Boolean.valueOf((p_176203_1_ & 4) > 0)).func_177226_a(field_176278_M, Boolean.valueOf((p_176203_1_ & 8) > 0));
   }

   public int func_176201_c(IBlockState p_176201_1_) {
      int i = 0;
      if (((Boolean)p_176201_1_.func_177229_b(field_176279_N)).booleanValue()) {
         i |= 1;
      }

      if (((Boolean)p_176201_1_.func_177229_b(field_176280_O)).booleanValue()) {
         i |= 2;
      }

      if (((Boolean)p_176201_1_.func_177229_b(field_176273_b)).booleanValue()) {
         i |= 4;
      }

      if (((Boolean)p_176201_1_.func_177229_b(field_176278_M)).booleanValue()) {
         i |= 8;
      }

      return i;
   }

   protected BlockStateContainer func_180661_e() {
      return new BlockStateContainer(this, new IProperty[]{field_176277_a, field_176273_b, field_176278_M, field_176279_N, field_176280_O});
   }

   public IBlockState func_185499_a(IBlockState p_185499_1_, Rotation p_185499_2_) {
      switch(p_185499_2_) {
      case CLOCKWISE_180:
         return p_185499_1_.func_177226_a(field_176273_b, p_185499_1_.func_177229_b(field_176279_N)).func_177226_a(field_176278_M, p_185499_1_.func_177229_b(field_176280_O)).func_177226_a(field_176279_N, p_185499_1_.func_177229_b(field_176273_b)).func_177226_a(field_176280_O, p_185499_1_.func_177229_b(field_176278_M));
      case COUNTERCLOCKWISE_90:
         return p_185499_1_.func_177226_a(field_176273_b, p_185499_1_.func_177229_b(field_176278_M)).func_177226_a(field_176278_M, p_185499_1_.func_177229_b(field_176279_N)).func_177226_a(field_176279_N, p_185499_1_.func_177229_b(field_176280_O)).func_177226_a(field_176280_O, p_185499_1_.func_177229_b(field_176273_b));
      case CLOCKWISE_90:
         return p_185499_1_.func_177226_a(field_176273_b, p_185499_1_.func_177229_b(field_176280_O)).func_177226_a(field_176278_M, p_185499_1_.func_177229_b(field_176273_b)).func_177226_a(field_176279_N, p_185499_1_.func_177229_b(field_176278_M)).func_177226_a(field_176280_O, p_185499_1_.func_177229_b(field_176279_N));
      default:
         return p_185499_1_;
      }
   }

   public IBlockState func_185471_a(IBlockState p_185471_1_, Mirror p_185471_2_) {
      switch(p_185471_2_) {
      case LEFT_RIGHT:
         return p_185471_1_.func_177226_a(field_176273_b, p_185471_1_.func_177229_b(field_176279_N)).func_177226_a(field_176279_N, p_185471_1_.func_177229_b(field_176273_b));
      case FRONT_BACK:
         return p_185471_1_.func_177226_a(field_176278_M, p_185471_1_.func_177229_b(field_176280_O)).func_177226_a(field_176280_O, p_185471_1_.func_177229_b(field_176278_M));
      default:
         return super.func_185471_a(p_185471_1_, p_185471_2_);
      }
   }

   public static PropertyBool func_176267_a(EnumFacing p_176267_0_) {
      switch(p_176267_0_) {
      case UP:
         return field_176277_a;
      case NORTH:
         return field_176273_b;
      case SOUTH:
         return field_176279_N;
      case WEST:
         return field_176280_O;
      case EAST:
         return field_176278_M;
      default:
         throw new IllegalArgumentException(p_176267_0_ + " is an invalid choice");
      }
   }

   public static int func_176268_d(IBlockState p_176268_0_) {
      int i = 0;

      for(PropertyBool propertybool : field_176274_P) {
         if (((Boolean)p_176268_0_.func_177229_b(propertybool)).booleanValue()) {
            ++i;
         }
      }

      return i;
   }

   public BlockFaceShape func_193383_a(IBlockAccess p_193383_1_, IBlockState p_193383_2_, BlockPos p_193383_3_, EnumFacing p_193383_4_) {
      return BlockFaceShape.UNDEFINED;
   }
}
