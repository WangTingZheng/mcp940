package net.minecraft.block;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public abstract class BlockBasePressurePlate extends Block {
   protected static final AxisAlignedBB field_185509_a = new AxisAlignedBB(0.0625D, 0.0D, 0.0625D, 0.9375D, 0.03125D, 0.9375D);
   protected static final AxisAlignedBB field_185510_b = new AxisAlignedBB(0.0625D, 0.0D, 0.0625D, 0.9375D, 0.0625D, 0.9375D);
   protected static final AxisAlignedBB field_185511_c = new AxisAlignedBB(0.125D, 0.0D, 0.125D, 0.875D, 0.25D, 0.875D);

   protected BlockBasePressurePlate(Material p_i45740_1_) {
      this(p_i45740_1_, p_i45740_1_.func_151565_r());
   }

   protected BlockBasePressurePlate(Material p_i46401_1_, MapColor p_i46401_2_) {
      super(p_i46401_1_, p_i46401_2_);
      this.func_149647_a(CreativeTabs.field_78028_d);
      this.func_149675_a(true);
   }

   public AxisAlignedBB func_185496_a(IBlockState p_185496_1_, IBlockAccess p_185496_2_, BlockPos p_185496_3_) {
      boolean flag = this.func_176576_e(p_185496_1_) > 0;
      return flag ? field_185509_a : field_185510_b;
   }

   public int func_149738_a(World p_149738_1_) {
      return 20;
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

   public boolean func_176205_b(IBlockAccess p_176205_1_, BlockPos p_176205_2_) {
      return true;
   }

   public boolean func_181623_g() {
      return true;
   }

   public boolean func_176196_c(World p_176196_1_, BlockPos p_176196_2_) {
      return this.func_176577_m(p_176196_1_, p_176196_2_.func_177977_b());
   }

   public void func_189540_a(IBlockState p_189540_1_, World p_189540_2_, BlockPos p_189540_3_, Block p_189540_4_, BlockPos p_189540_5_) {
      if (!this.func_176577_m(p_189540_2_, p_189540_3_.func_177977_b())) {
         this.func_176226_b(p_189540_2_, p_189540_3_, p_189540_1_, 0);
         p_189540_2_.func_175698_g(p_189540_3_);
      }

   }

   private boolean func_176577_m(World p_176577_1_, BlockPos p_176577_2_) {
      return p_176577_1_.func_180495_p(p_176577_2_).func_185896_q() || p_176577_1_.func_180495_p(p_176577_2_).func_177230_c() instanceof BlockFence;
   }

   public void func_180645_a(World p_180645_1_, BlockPos p_180645_2_, IBlockState p_180645_3_, Random p_180645_4_) {
   }

   public void func_180650_b(World p_180650_1_, BlockPos p_180650_2_, IBlockState p_180650_3_, Random p_180650_4_) {
      if (!p_180650_1_.field_72995_K) {
         int i = this.func_176576_e(p_180650_3_);
         if (i > 0) {
            this.func_180666_a(p_180650_1_, p_180650_2_, p_180650_3_, i);
         }

      }
   }

   public void func_180634_a(World p_180634_1_, BlockPos p_180634_2_, IBlockState p_180634_3_, Entity p_180634_4_) {
      if (!p_180634_1_.field_72995_K) {
         int i = this.func_176576_e(p_180634_3_);
         if (i == 0) {
            this.func_180666_a(p_180634_1_, p_180634_2_, p_180634_3_, i);
         }

      }
   }

   protected void func_180666_a(World p_180666_1_, BlockPos p_180666_2_, IBlockState p_180666_3_, int p_180666_4_) {
      int i = this.func_180669_e(p_180666_1_, p_180666_2_);
      boolean flag = p_180666_4_ > 0;
      boolean flag1 = i > 0;
      if (p_180666_4_ != i) {
         p_180666_3_ = this.func_176575_a(p_180666_3_, i);
         p_180666_1_.func_180501_a(p_180666_2_, p_180666_3_, 2);
         this.func_176578_d(p_180666_1_, p_180666_2_);
         p_180666_1_.func_175704_b(p_180666_2_, p_180666_2_);
      }

      if (!flag1 && flag) {
         this.func_185508_c(p_180666_1_, p_180666_2_);
      } else if (flag1 && !flag) {
         this.func_185507_b(p_180666_1_, p_180666_2_);
      }

      if (flag1) {
         p_180666_1_.func_175684_a(new BlockPos(p_180666_2_), this, this.func_149738_a(p_180666_1_));
      }

   }

   protected abstract void func_185507_b(World var1, BlockPos var2);

   protected abstract void func_185508_c(World var1, BlockPos var2);

   public void func_180663_b(World p_180663_1_, BlockPos p_180663_2_, IBlockState p_180663_3_) {
      if (this.func_176576_e(p_180663_3_) > 0) {
         this.func_176578_d(p_180663_1_, p_180663_2_);
      }

      super.func_180663_b(p_180663_1_, p_180663_2_, p_180663_3_);
   }

   protected void func_176578_d(World p_176578_1_, BlockPos p_176578_2_) {
      p_176578_1_.func_175685_c(p_176578_2_, this, false);
      p_176578_1_.func_175685_c(p_176578_2_.func_177977_b(), this, false);
   }

   public int func_180656_a(IBlockState p_180656_1_, IBlockAccess p_180656_2_, BlockPos p_180656_3_, EnumFacing p_180656_4_) {
      return this.func_176576_e(p_180656_1_);
   }

   public int func_176211_b(IBlockState p_176211_1_, IBlockAccess p_176211_2_, BlockPos p_176211_3_, EnumFacing p_176211_4_) {
      return p_176211_4_ == EnumFacing.UP ? this.func_176576_e(p_176211_1_) : 0;
   }

   public boolean func_149744_f(IBlockState p_149744_1_) {
      return true;
   }

   public EnumPushReaction func_149656_h(IBlockState p_149656_1_) {
      return EnumPushReaction.DESTROY;
   }

   protected abstract int func_180669_e(World var1, BlockPos var2);

   protected abstract int func_176576_e(IBlockState var1);

   protected abstract IBlockState func_176575_a(IBlockState var1, int var2);

   public BlockFaceShape func_193383_a(IBlockAccess p_193383_1_, IBlockState p_193383_2_, BlockPos p_193383_3_, EnumFacing p_193383_4_) {
      return BlockFaceShape.UNDEFINED;
   }
}
