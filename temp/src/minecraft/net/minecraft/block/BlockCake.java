package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockCake extends Block {
   public static final PropertyInteger field_176589_a = PropertyInteger.func_177719_a("bites", 0, 6);
   protected static final AxisAlignedBB[] field_185595_b = new AxisAlignedBB[]{new AxisAlignedBB(0.0625D, 0.0D, 0.0625D, 0.9375D, 0.5D, 0.9375D), new AxisAlignedBB(0.1875D, 0.0D, 0.0625D, 0.9375D, 0.5D, 0.9375D), new AxisAlignedBB(0.3125D, 0.0D, 0.0625D, 0.9375D, 0.5D, 0.9375D), new AxisAlignedBB(0.4375D, 0.0D, 0.0625D, 0.9375D, 0.5D, 0.9375D), new AxisAlignedBB(0.5625D, 0.0D, 0.0625D, 0.9375D, 0.5D, 0.9375D), new AxisAlignedBB(0.6875D, 0.0D, 0.0625D, 0.9375D, 0.5D, 0.9375D), new AxisAlignedBB(0.8125D, 0.0D, 0.0625D, 0.9375D, 0.5D, 0.9375D)};

   protected BlockCake() {
      super(Material.field_151568_F);
      this.func_180632_j(this.field_176227_L.func_177621_b().func_177226_a(field_176589_a, Integer.valueOf(0)));
      this.func_149675_a(true);
   }

   public AxisAlignedBB func_185496_a(IBlockState p_185496_1_, IBlockAccess p_185496_2_, BlockPos p_185496_3_) {
      return field_185595_b[((Integer)p_185496_1_.func_177229_b(field_176589_a)).intValue()];
   }

   public boolean func_149686_d(IBlockState p_149686_1_) {
      return false;
   }

   public boolean func_149662_c(IBlockState p_149662_1_) {
      return false;
   }

   public boolean func_180639_a(World p_180639_1_, BlockPos p_180639_2_, IBlockState p_180639_3_, EntityPlayer p_180639_4_, EnumHand p_180639_5_, EnumFacing p_180639_6_, float p_180639_7_, float p_180639_8_, float p_180639_9_) {
      if (!p_180639_1_.field_72995_K) {
         return this.func_180682_b(p_180639_1_, p_180639_2_, p_180639_3_, p_180639_4_);
      } else {
         ItemStack itemstack = p_180639_4_.func_184586_b(p_180639_5_);
         return this.func_180682_b(p_180639_1_, p_180639_2_, p_180639_3_, p_180639_4_) || itemstack.func_190926_b();
      }
   }

   private boolean func_180682_b(World p_180682_1_, BlockPos p_180682_2_, IBlockState p_180682_3_, EntityPlayer p_180682_4_) {
      if (!p_180682_4_.func_71043_e(false)) {
         return false;
      } else {
         p_180682_4_.func_71029_a(StatList.field_188076_J);
         p_180682_4_.func_71024_bL().func_75122_a(2, 0.1F);
         int i = ((Integer)p_180682_3_.func_177229_b(field_176589_a)).intValue();
         if (i < 6) {
            p_180682_1_.func_180501_a(p_180682_2_, p_180682_3_.func_177226_a(field_176589_a, Integer.valueOf(i + 1)), 3);
         } else {
            p_180682_1_.func_175698_g(p_180682_2_);
         }

         return true;
      }
   }

   public boolean func_176196_c(World p_176196_1_, BlockPos p_176196_2_) {
      return super.func_176196_c(p_176196_1_, p_176196_2_) ? this.func_176588_d(p_176196_1_, p_176196_2_) : false;
   }

   public void func_189540_a(IBlockState p_189540_1_, World p_189540_2_, BlockPos p_189540_3_, Block p_189540_4_, BlockPos p_189540_5_) {
      if (!this.func_176588_d(p_189540_2_, p_189540_3_)) {
         p_189540_2_.func_175698_g(p_189540_3_);
      }

   }

   private boolean func_176588_d(World p_176588_1_, BlockPos p_176588_2_) {
      return p_176588_1_.func_180495_p(p_176588_2_.func_177977_b()).func_185904_a().func_76220_a();
   }

   public int func_149745_a(Random p_149745_1_) {
      return 0;
   }

   public Item func_180660_a(IBlockState p_180660_1_, Random p_180660_2_, int p_180660_3_) {
      return Items.field_190931_a;
   }

   public ItemStack func_185473_a(World p_185473_1_, BlockPos p_185473_2_, IBlockState p_185473_3_) {
      return new ItemStack(Items.field_151105_aU);
   }

   public BlockRenderLayer func_180664_k() {
      return BlockRenderLayer.CUTOUT;
   }

   public IBlockState func_176203_a(int p_176203_1_) {
      return this.func_176223_P().func_177226_a(field_176589_a, Integer.valueOf(p_176203_1_));
   }

   public int func_176201_c(IBlockState p_176201_1_) {
      return ((Integer)p_176201_1_.func_177229_b(field_176589_a)).intValue();
   }

   protected BlockStateContainer func_180661_e() {
      return new BlockStateContainer(this, new IProperty[]{field_176589_a});
   }

   public int func_180641_l(IBlockState p_180641_1_, World p_180641_2_, BlockPos p_180641_3_) {
      return (7 - ((Integer)p_180641_1_.func_177229_b(field_176589_a)).intValue()) * 2;
   }

   public boolean func_149740_M(IBlockState p_149740_1_) {
      return true;
   }

   public BlockFaceShape func_193383_a(IBlockAccess p_193383_1_, IBlockState p_193383_2_, BlockPos p_193383_3_, EnumFacing p_193383_4_) {
      return BlockFaceShape.UNDEFINED;
   }
}
