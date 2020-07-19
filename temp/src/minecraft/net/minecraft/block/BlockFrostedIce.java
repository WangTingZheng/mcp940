package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class BlockFrostedIce extends BlockIce {
   public static final PropertyInteger field_185682_a = PropertyInteger.func_177719_a("age", 0, 3);

   public BlockFrostedIce() {
      this.func_180632_j(this.field_176227_L.func_177621_b().func_177226_a(field_185682_a, Integer.valueOf(0)));
   }

   public int func_176201_c(IBlockState p_176201_1_) {
      return ((Integer)p_176201_1_.func_177229_b(field_185682_a)).intValue();
   }

   public IBlockState func_176203_a(int p_176203_1_) {
      return this.func_176223_P().func_177226_a(field_185682_a, Integer.valueOf(MathHelper.func_76125_a(p_176203_1_, 0, 3)));
   }

   public void func_180650_b(World p_180650_1_, BlockPos p_180650_2_, IBlockState p_180650_3_, Random p_180650_4_) {
      if ((p_180650_4_.nextInt(3) == 0 || this.func_185680_c(p_180650_1_, p_180650_2_) < 4) && p_180650_1_.func_175671_l(p_180650_2_) > 11 - ((Integer)p_180650_3_.func_177229_b(field_185682_a)).intValue() - p_180650_3_.func_185891_c()) {
         this.func_185681_a(p_180650_1_, p_180650_2_, p_180650_3_, p_180650_4_, true);
      } else {
         p_180650_1_.func_175684_a(p_180650_2_, this, MathHelper.func_76136_a(p_180650_4_, 20, 40));
      }

   }

   public void func_189540_a(IBlockState p_189540_1_, World p_189540_2_, BlockPos p_189540_3_, Block p_189540_4_, BlockPos p_189540_5_) {
      if (p_189540_4_ == this) {
         int i = this.func_185680_c(p_189540_2_, p_189540_3_);
         if (i < 2) {
            this.func_185679_b(p_189540_2_, p_189540_3_);
         }
      }

   }

   private int func_185680_c(World p_185680_1_, BlockPos p_185680_2_) {
      int i = 0;

      for(EnumFacing enumfacing : EnumFacing.values()) {
         if (p_185680_1_.func_180495_p(p_185680_2_.func_177972_a(enumfacing)).func_177230_c() == this) {
            ++i;
            if (i >= 4) {
               return i;
            }
         }
      }

      return i;
   }

   protected void func_185681_a(World p_185681_1_, BlockPos p_185681_2_, IBlockState p_185681_3_, Random p_185681_4_, boolean p_185681_5_) {
      int i = ((Integer)p_185681_3_.func_177229_b(field_185682_a)).intValue();
      if (i < 3) {
         p_185681_1_.func_180501_a(p_185681_2_, p_185681_3_.func_177226_a(field_185682_a, Integer.valueOf(i + 1)), 2);
         p_185681_1_.func_175684_a(p_185681_2_, this, MathHelper.func_76136_a(p_185681_4_, 20, 40));
      } else {
         this.func_185679_b(p_185681_1_, p_185681_2_);
         if (p_185681_5_) {
            for(EnumFacing enumfacing : EnumFacing.values()) {
               BlockPos blockpos = p_185681_2_.func_177972_a(enumfacing);
               IBlockState iblockstate = p_185681_1_.func_180495_p(blockpos);
               if (iblockstate.func_177230_c() == this) {
                  this.func_185681_a(p_185681_1_, blockpos, iblockstate, p_185681_4_, false);
               }
            }
         }
      }

   }

   protected BlockStateContainer func_180661_e() {
      return new BlockStateContainer(this, new IProperty[]{field_185682_a});
   }

   public ItemStack func_185473_a(World p_185473_1_, BlockPos p_185473_2_, IBlockState p_185473_3_) {
      return ItemStack.field_190927_a;
   }
}
