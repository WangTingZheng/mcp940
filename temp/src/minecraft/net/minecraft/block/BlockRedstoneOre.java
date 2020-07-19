package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockRedstoneOre extends Block {
   private final boolean field_150187_a;

   public BlockRedstoneOre(boolean p_i45420_1_) {
      super(Material.field_151576_e);
      if (p_i45420_1_) {
         this.func_149675_a(true);
      }

      this.field_150187_a = p_i45420_1_;
   }

   public int func_149738_a(World p_149738_1_) {
      return 30;
   }

   public void func_180649_a(World p_180649_1_, BlockPos p_180649_2_, EntityPlayer p_180649_3_) {
      this.func_176352_d(p_180649_1_, p_180649_2_);
      super.func_180649_a(p_180649_1_, p_180649_2_, p_180649_3_);
   }

   public void func_176199_a(World p_176199_1_, BlockPos p_176199_2_, Entity p_176199_3_) {
      this.func_176352_d(p_176199_1_, p_176199_2_);
      super.func_176199_a(p_176199_1_, p_176199_2_, p_176199_3_);
   }

   public boolean func_180639_a(World p_180639_1_, BlockPos p_180639_2_, IBlockState p_180639_3_, EntityPlayer p_180639_4_, EnumHand p_180639_5_, EnumFacing p_180639_6_, float p_180639_7_, float p_180639_8_, float p_180639_9_) {
      this.func_176352_d(p_180639_1_, p_180639_2_);
      return super.func_180639_a(p_180639_1_, p_180639_2_, p_180639_3_, p_180639_4_, p_180639_5_, p_180639_6_, p_180639_7_, p_180639_8_, p_180639_9_);
   }

   private void func_176352_d(World p_176352_1_, BlockPos p_176352_2_) {
      this.func_180691_e(p_176352_1_, p_176352_2_);
      if (this == Blocks.field_150450_ax) {
         p_176352_1_.func_175656_a(p_176352_2_, Blocks.field_150439_ay.func_176223_P());
      }

   }

   public void func_180650_b(World p_180650_1_, BlockPos p_180650_2_, IBlockState p_180650_3_, Random p_180650_4_) {
      if (this == Blocks.field_150439_ay) {
         p_180650_1_.func_175656_a(p_180650_2_, Blocks.field_150450_ax.func_176223_P());
      }

   }

   public Item func_180660_a(IBlockState p_180660_1_, Random p_180660_2_, int p_180660_3_) {
      return Items.field_151137_ax;
   }

   public int func_149679_a(int p_149679_1_, Random p_149679_2_) {
      return this.func_149745_a(p_149679_2_) + p_149679_2_.nextInt(p_149679_1_ + 1);
   }

   public int func_149745_a(Random p_149745_1_) {
      return 4 + p_149745_1_.nextInt(2);
   }

   public void func_180653_a(World p_180653_1_, BlockPos p_180653_2_, IBlockState p_180653_3_, float p_180653_4_, int p_180653_5_) {
      super.func_180653_a(p_180653_1_, p_180653_2_, p_180653_3_, p_180653_4_, p_180653_5_);
      if (this.func_180660_a(p_180653_3_, p_180653_1_.field_73012_v, p_180653_5_) != Item.func_150898_a(this)) {
         int i = 1 + p_180653_1_.field_73012_v.nextInt(5);
         this.func_180637_b(p_180653_1_, p_180653_2_, i);
      }

   }

   public void func_180655_c(IBlockState p_180655_1_, World p_180655_2_, BlockPos p_180655_3_, Random p_180655_4_) {
      if (this.field_150187_a) {
         this.func_180691_e(p_180655_2_, p_180655_3_);
      }

   }

   private void func_180691_e(World p_180691_1_, BlockPos p_180691_2_) {
      Random random = p_180691_1_.field_73012_v;
      double d0 = 0.0625D;

      for(int i = 0; i < 6; ++i) {
         double d1 = (double)((float)p_180691_2_.func_177958_n() + random.nextFloat());
         double d2 = (double)((float)p_180691_2_.func_177956_o() + random.nextFloat());
         double d3 = (double)((float)p_180691_2_.func_177952_p() + random.nextFloat());
         if (i == 0 && !p_180691_1_.func_180495_p(p_180691_2_.func_177984_a()).func_185914_p()) {
            d2 = (double)p_180691_2_.func_177956_o() + 0.0625D + 1.0D;
         }

         if (i == 1 && !p_180691_1_.func_180495_p(p_180691_2_.func_177977_b()).func_185914_p()) {
            d2 = (double)p_180691_2_.func_177956_o() - 0.0625D;
         }

         if (i == 2 && !p_180691_1_.func_180495_p(p_180691_2_.func_177968_d()).func_185914_p()) {
            d3 = (double)p_180691_2_.func_177952_p() + 0.0625D + 1.0D;
         }

         if (i == 3 && !p_180691_1_.func_180495_p(p_180691_2_.func_177978_c()).func_185914_p()) {
            d3 = (double)p_180691_2_.func_177952_p() - 0.0625D;
         }

         if (i == 4 && !p_180691_1_.func_180495_p(p_180691_2_.func_177974_f()).func_185914_p()) {
            d1 = (double)p_180691_2_.func_177958_n() + 0.0625D + 1.0D;
         }

         if (i == 5 && !p_180691_1_.func_180495_p(p_180691_2_.func_177976_e()).func_185914_p()) {
            d1 = (double)p_180691_2_.func_177958_n() - 0.0625D;
         }

         if (d1 < (double)p_180691_2_.func_177958_n() || d1 > (double)(p_180691_2_.func_177958_n() + 1) || d2 < 0.0D || d2 > (double)(p_180691_2_.func_177956_o() + 1) || d3 < (double)p_180691_2_.func_177952_p() || d3 > (double)(p_180691_2_.func_177952_p() + 1)) {
            p_180691_1_.func_175688_a(EnumParticleTypes.REDSTONE, d1, d2, d3, 0.0D, 0.0D, 0.0D);
         }
      }

   }

   protected ItemStack func_180643_i(IBlockState p_180643_1_) {
      return new ItemStack(Blocks.field_150450_ax);
   }

   public ItemStack func_185473_a(World p_185473_1_, BlockPos p_185473_2_, IBlockState p_185473_3_) {
      return new ItemStack(Item.func_150898_a(Blocks.field_150450_ax), 1, this.func_180651_a(p_185473_3_));
   }
}
