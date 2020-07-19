package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockCrops extends BlockBush implements IGrowable {
   public static final PropertyInteger field_176488_a = PropertyInteger.func_177719_a("age", 0, 7);
   private static final AxisAlignedBB[] field_185530_a = new AxisAlignedBB[]{new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.125D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.25D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.375D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.5D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.625D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.75D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.875D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D)};

   protected BlockCrops() {
      this.func_180632_j(this.field_176227_L.func_177621_b().func_177226_a(this.func_185524_e(), Integer.valueOf(0)));
      this.func_149675_a(true);
      this.func_149647_a((CreativeTabs)null);
      this.func_149711_c(0.0F);
      this.func_149672_a(SoundType.field_185850_c);
      this.func_149649_H();
   }

   public AxisAlignedBB func_185496_a(IBlockState p_185496_1_, IBlockAccess p_185496_2_, BlockPos p_185496_3_) {
      return field_185530_a[((Integer)p_185496_1_.func_177229_b(this.func_185524_e())).intValue()];
   }

   protected boolean func_185514_i(IBlockState p_185514_1_) {
      return p_185514_1_.func_177230_c() == Blocks.field_150458_ak;
   }

   protected PropertyInteger func_185524_e() {
      return field_176488_a;
   }

   public int func_185526_g() {
      return 7;
   }

   protected int func_185527_x(IBlockState p_185527_1_) {
      return ((Integer)p_185527_1_.func_177229_b(this.func_185524_e())).intValue();
   }

   public IBlockState func_185528_e(int p_185528_1_) {
      return this.func_176223_P().func_177226_a(this.func_185524_e(), Integer.valueOf(p_185528_1_));
   }

   public boolean func_185525_y(IBlockState p_185525_1_) {
      return ((Integer)p_185525_1_.func_177229_b(this.func_185524_e())).intValue() >= this.func_185526_g();
   }

   public void func_180650_b(World p_180650_1_, BlockPos p_180650_2_, IBlockState p_180650_3_, Random p_180650_4_) {
      super.func_180650_b(p_180650_1_, p_180650_2_, p_180650_3_, p_180650_4_);
      if (p_180650_1_.func_175671_l(p_180650_2_.func_177984_a()) >= 9) {
         int i = this.func_185527_x(p_180650_3_);
         if (i < this.func_185526_g()) {
            float f = func_180672_a(this, p_180650_1_, p_180650_2_);
            if (p_180650_4_.nextInt((int)(25.0F / f) + 1) == 0) {
               p_180650_1_.func_180501_a(p_180650_2_, this.func_185528_e(i + 1), 2);
            }
         }
      }

   }

   public void func_176487_g(World p_176487_1_, BlockPos p_176487_2_, IBlockState p_176487_3_) {
      int i = this.func_185527_x(p_176487_3_) + this.func_185529_b(p_176487_1_);
      int j = this.func_185526_g();
      if (i > j) {
         i = j;
      }

      p_176487_1_.func_180501_a(p_176487_2_, this.func_185528_e(i), 2);
   }

   protected int func_185529_b(World p_185529_1_) {
      return MathHelper.func_76136_a(p_185529_1_.field_73012_v, 2, 5);
   }

   protected static float func_180672_a(Block p_180672_0_, World p_180672_1_, BlockPos p_180672_2_) {
      float f = 1.0F;
      BlockPos blockpos = p_180672_2_.func_177977_b();

      for(int i = -1; i <= 1; ++i) {
         for(int j = -1; j <= 1; ++j) {
            float f1 = 0.0F;
            IBlockState iblockstate = p_180672_1_.func_180495_p(blockpos.func_177982_a(i, 0, j));
            if (iblockstate.func_177230_c() == Blocks.field_150458_ak) {
               f1 = 1.0F;
               if (((Integer)iblockstate.func_177229_b(BlockFarmland.field_176531_a)).intValue() > 0) {
                  f1 = 3.0F;
               }
            }

            if (i != 0 || j != 0) {
               f1 /= 4.0F;
            }

            f += f1;
         }
      }

      BlockPos blockpos1 = p_180672_2_.func_177978_c();
      BlockPos blockpos2 = p_180672_2_.func_177968_d();
      BlockPos blockpos3 = p_180672_2_.func_177976_e();
      BlockPos blockpos4 = p_180672_2_.func_177974_f();
      boolean flag = p_180672_0_ == p_180672_1_.func_180495_p(blockpos3).func_177230_c() || p_180672_0_ == p_180672_1_.func_180495_p(blockpos4).func_177230_c();
      boolean flag1 = p_180672_0_ == p_180672_1_.func_180495_p(blockpos1).func_177230_c() || p_180672_0_ == p_180672_1_.func_180495_p(blockpos2).func_177230_c();
      if (flag && flag1) {
         f /= 2.0F;
      } else {
         boolean flag2 = p_180672_0_ == p_180672_1_.func_180495_p(blockpos3.func_177978_c()).func_177230_c() || p_180672_0_ == p_180672_1_.func_180495_p(blockpos4.func_177978_c()).func_177230_c() || p_180672_0_ == p_180672_1_.func_180495_p(blockpos4.func_177968_d()).func_177230_c() || p_180672_0_ == p_180672_1_.func_180495_p(blockpos3.func_177968_d()).func_177230_c();
         if (flag2) {
            f /= 2.0F;
         }
      }

      return f;
   }

   public boolean func_180671_f(World p_180671_1_, BlockPos p_180671_2_, IBlockState p_180671_3_) {
      return (p_180671_1_.func_175699_k(p_180671_2_) >= 8 || p_180671_1_.func_175678_i(p_180671_2_)) && this.func_185514_i(p_180671_1_.func_180495_p(p_180671_2_.func_177977_b()));
   }

   protected Item func_149866_i() {
      return Items.field_151014_N;
   }

   protected Item func_149865_P() {
      return Items.field_151015_O;
   }

   public void func_180653_a(World p_180653_1_, BlockPos p_180653_2_, IBlockState p_180653_3_, float p_180653_4_, int p_180653_5_) {
      super.func_180653_a(p_180653_1_, p_180653_2_, p_180653_3_, p_180653_4_, 0);
      if (!p_180653_1_.field_72995_K) {
         int i = this.func_185527_x(p_180653_3_);
         if (i >= this.func_185526_g()) {
            int j = 3 + p_180653_5_;

            for(int k = 0; k < j; ++k) {
               if (p_180653_1_.field_73012_v.nextInt(2 * this.func_185526_g()) <= i) {
                  func_180635_a(p_180653_1_, p_180653_2_, new ItemStack(this.func_149866_i()));
               }
            }
         }

      }
   }

   public Item func_180660_a(IBlockState p_180660_1_, Random p_180660_2_, int p_180660_3_) {
      return this.func_185525_y(p_180660_1_) ? this.func_149865_P() : this.func_149866_i();
   }

   public ItemStack func_185473_a(World p_185473_1_, BlockPos p_185473_2_, IBlockState p_185473_3_) {
      return new ItemStack(this.func_149866_i());
   }

   public boolean func_176473_a(World p_176473_1_, BlockPos p_176473_2_, IBlockState p_176473_3_, boolean p_176473_4_) {
      return !this.func_185525_y(p_176473_3_);
   }

   public boolean func_180670_a(World p_180670_1_, Random p_180670_2_, BlockPos p_180670_3_, IBlockState p_180670_4_) {
      return true;
   }

   public void func_176474_b(World p_176474_1_, Random p_176474_2_, BlockPos p_176474_3_, IBlockState p_176474_4_) {
      this.func_176487_g(p_176474_1_, p_176474_3_, p_176474_4_);
   }

   public IBlockState func_176203_a(int p_176203_1_) {
      return this.func_185528_e(p_176203_1_);
   }

   public int func_176201_c(IBlockState p_176201_1_) {
      return this.func_185527_x(p_176201_1_);
   }

   protected BlockStateContainer func_180661_e() {
      return new BlockStateContainer(this, new IProperty[]{field_176488_a});
   }
}
