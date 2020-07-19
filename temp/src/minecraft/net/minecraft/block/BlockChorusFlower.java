package net.minecraft.block;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockChorusFlower extends Block {
   public static final PropertyInteger field_185607_a = PropertyInteger.func_177719_a("age", 0, 5);

   protected BlockChorusFlower() {
      super(Material.field_151585_k, MapColor.field_151678_z);
      this.func_180632_j(this.field_176227_L.func_177621_b().func_177226_a(field_185607_a, Integer.valueOf(0)));
      this.func_149647_a(CreativeTabs.field_78031_c);
      this.func_149675_a(true);
   }

   public Item func_180660_a(IBlockState p_180660_1_, Random p_180660_2_, int p_180660_3_) {
      return Items.field_190931_a;
   }

   public void func_180650_b(World p_180650_1_, BlockPos p_180650_2_, IBlockState p_180650_3_, Random p_180650_4_) {
      if (!this.func_185606_b(p_180650_1_, p_180650_2_)) {
         p_180650_1_.func_175655_b(p_180650_2_, true);
      } else {
         BlockPos blockpos = p_180650_2_.func_177984_a();
         if (p_180650_1_.func_175623_d(blockpos) && blockpos.func_177956_o() < 256) {
            int i = ((Integer)p_180650_3_.func_177229_b(field_185607_a)).intValue();
            if (i < 5 && p_180650_4_.nextInt(1) == 0) {
               boolean flag = false;
               boolean flag1 = false;
               IBlockState iblockstate = p_180650_1_.func_180495_p(p_180650_2_.func_177977_b());
               Block block = iblockstate.func_177230_c();
               if (block == Blocks.field_150377_bs) {
                  flag = true;
               } else if (block == Blocks.field_185765_cR) {
                  int j = 1;

                  for(int k = 0; k < 4; ++k) {
                     Block block1 = p_180650_1_.func_180495_p(p_180650_2_.func_177979_c(j + 1)).func_177230_c();
                     if (block1 != Blocks.field_185765_cR) {
                        if (block1 == Blocks.field_150377_bs) {
                           flag1 = true;
                        }
                        break;
                     }

                     ++j;
                  }

                  int i1 = 4;
                  if (flag1) {
                     ++i1;
                  }

                  if (j < 2 || p_180650_4_.nextInt(i1) >= j) {
                     flag = true;
                  }
               } else if (iblockstate.func_185904_a() == Material.field_151579_a) {
                  flag = true;
               }

               if (flag && func_185604_a(p_180650_1_, blockpos, (EnumFacing)null) && p_180650_1_.func_175623_d(p_180650_2_.func_177981_b(2))) {
                  p_180650_1_.func_180501_a(p_180650_2_, Blocks.field_185765_cR.func_176223_P(), 2);
                  this.func_185602_a(p_180650_1_, blockpos, i);
               } else if (i < 4) {
                  int l = p_180650_4_.nextInt(4);
                  boolean flag2 = false;
                  if (flag1) {
                     ++l;
                  }

                  for(int j1 = 0; j1 < l; ++j1) {
                     EnumFacing enumfacing = EnumFacing.Plane.HORIZONTAL.func_179518_a(p_180650_4_);
                     BlockPos blockpos1 = p_180650_2_.func_177972_a(enumfacing);
                     if (p_180650_1_.func_175623_d(blockpos1) && p_180650_1_.func_175623_d(blockpos1.func_177977_b()) && func_185604_a(p_180650_1_, blockpos1, enumfacing.func_176734_d())) {
                        this.func_185602_a(p_180650_1_, blockpos1, i + 1);
                        flag2 = true;
                     }
                  }

                  if (flag2) {
                     p_180650_1_.func_180501_a(p_180650_2_, Blocks.field_185765_cR.func_176223_P(), 2);
                  } else {
                     this.func_185605_c(p_180650_1_, p_180650_2_);
                  }
               } else if (i == 4) {
                  this.func_185605_c(p_180650_1_, p_180650_2_);
               }

            }
         }
      }
   }

   private void func_185602_a(World p_185602_1_, BlockPos p_185602_2_, int p_185602_3_) {
      p_185602_1_.func_180501_a(p_185602_2_, this.func_176223_P().func_177226_a(field_185607_a, Integer.valueOf(p_185602_3_)), 2);
      p_185602_1_.func_175718_b(1033, p_185602_2_, 0);
   }

   private void func_185605_c(World p_185605_1_, BlockPos p_185605_2_) {
      p_185605_1_.func_180501_a(p_185605_2_, this.func_176223_P().func_177226_a(field_185607_a, Integer.valueOf(5)), 2);
      p_185605_1_.func_175718_b(1034, p_185605_2_, 0);
   }

   private static boolean func_185604_a(World p_185604_0_, BlockPos p_185604_1_, EnumFacing p_185604_2_) {
      for(EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
         if (enumfacing != p_185604_2_ && !p_185604_0_.func_175623_d(p_185604_1_.func_177972_a(enumfacing))) {
            return false;
         }
      }

      return true;
   }

   public boolean func_149686_d(IBlockState p_149686_1_) {
      return false;
   }

   public boolean func_149662_c(IBlockState p_149662_1_) {
      return false;
   }

   public boolean func_176196_c(World p_176196_1_, BlockPos p_176196_2_) {
      return super.func_176196_c(p_176196_1_, p_176196_2_) && this.func_185606_b(p_176196_1_, p_176196_2_);
   }

   public void func_189540_a(IBlockState p_189540_1_, World p_189540_2_, BlockPos p_189540_3_, Block p_189540_4_, BlockPos p_189540_5_) {
      if (!this.func_185606_b(p_189540_2_, p_189540_3_)) {
         p_189540_2_.func_175684_a(p_189540_3_, this, 1);
      }

   }

   public boolean func_185606_b(World p_185606_1_, BlockPos p_185606_2_) {
      IBlockState iblockstate = p_185606_1_.func_180495_p(p_185606_2_.func_177977_b());
      Block block = iblockstate.func_177230_c();
      if (block != Blocks.field_185765_cR && block != Blocks.field_150377_bs) {
         if (iblockstate.func_185904_a() == Material.field_151579_a) {
            int i = 0;

            for(EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
               IBlockState iblockstate1 = p_185606_1_.func_180495_p(p_185606_2_.func_177972_a(enumfacing));
               Block block1 = iblockstate1.func_177230_c();
               if (block1 == Blocks.field_185765_cR) {
                  ++i;
               } else if (iblockstate1.func_185904_a() != Material.field_151579_a) {
                  return false;
               }
            }

            return i == 1;
         } else {
            return false;
         }
      } else {
         return true;
      }
   }

   public void func_180657_a(World p_180657_1_, EntityPlayer p_180657_2_, BlockPos p_180657_3_, IBlockState p_180657_4_, @Nullable TileEntity p_180657_5_, ItemStack p_180657_6_) {
      super.func_180657_a(p_180657_1_, p_180657_2_, p_180657_3_, p_180657_4_, p_180657_5_, p_180657_6_);
      func_180635_a(p_180657_1_, p_180657_3_, new ItemStack(Item.func_150898_a(this)));
   }

   protected ItemStack func_180643_i(IBlockState p_180643_1_) {
      return ItemStack.field_190927_a;
   }

   public BlockRenderLayer func_180664_k() {
      return BlockRenderLayer.CUTOUT;
   }

   public IBlockState func_176203_a(int p_176203_1_) {
      return this.func_176223_P().func_177226_a(field_185607_a, Integer.valueOf(p_176203_1_));
   }

   public int func_176201_c(IBlockState p_176201_1_) {
      return ((Integer)p_176201_1_.func_177229_b(field_185607_a)).intValue();
   }

   protected BlockStateContainer func_180661_e() {
      return new BlockStateContainer(this, new IProperty[]{field_185607_a});
   }

   public static void func_185603_a(World p_185603_0_, BlockPos p_185603_1_, Random p_185603_2_, int p_185603_3_) {
      p_185603_0_.func_180501_a(p_185603_1_, Blocks.field_185765_cR.func_176223_P(), 2);
      func_185601_a(p_185603_0_, p_185603_1_, p_185603_2_, p_185603_1_, p_185603_3_, 0);
   }

   private static void func_185601_a(World p_185601_0_, BlockPos p_185601_1_, Random p_185601_2_, BlockPos p_185601_3_, int p_185601_4_, int p_185601_5_) {
      int i = p_185601_2_.nextInt(4) + 1;
      if (p_185601_5_ == 0) {
         ++i;
      }

      for(int j = 0; j < i; ++j) {
         BlockPos blockpos = p_185601_1_.func_177981_b(j + 1);
         if (!func_185604_a(p_185601_0_, blockpos, (EnumFacing)null)) {
            return;
         }

         p_185601_0_.func_180501_a(blockpos, Blocks.field_185765_cR.func_176223_P(), 2);
      }

      boolean flag = false;
      if (p_185601_5_ < 4) {
         int l = p_185601_2_.nextInt(4);
         if (p_185601_5_ == 0) {
            ++l;
         }

         for(int k = 0; k < l; ++k) {
            EnumFacing enumfacing = EnumFacing.Plane.HORIZONTAL.func_179518_a(p_185601_2_);
            BlockPos blockpos1 = p_185601_1_.func_177981_b(i).func_177972_a(enumfacing);
            if (Math.abs(blockpos1.func_177958_n() - p_185601_3_.func_177958_n()) < p_185601_4_ && Math.abs(blockpos1.func_177952_p() - p_185601_3_.func_177952_p()) < p_185601_4_ && p_185601_0_.func_175623_d(blockpos1) && p_185601_0_.func_175623_d(blockpos1.func_177977_b()) && func_185604_a(p_185601_0_, blockpos1, enumfacing.func_176734_d())) {
               flag = true;
               p_185601_0_.func_180501_a(blockpos1, Blocks.field_185765_cR.func_176223_P(), 2);
               func_185601_a(p_185601_0_, blockpos1, p_185601_2_, p_185601_3_, p_185601_4_, p_185601_5_ + 1);
            }
         }
      }

      if (!flag) {
         p_185601_0_.func_180501_a(p_185601_1_.func_177981_b(i), Blocks.field_185766_cS.func_176223_P().func_177226_a(field_185607_a, Integer.valueOf(5)), 2);
      }

   }

   public BlockFaceShape func_193383_a(IBlockAccess p_193383_1_, IBlockState p_193383_2_, BlockPos p_193383_3_, EnumFacing p_193383_4_) {
      return BlockFaceShape.UNDEFINED;
   }
}
