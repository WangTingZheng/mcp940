package net.minecraft.block;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockCarpet extends Block {
   public static final PropertyEnum<EnumDyeColor> field_176330_a = PropertyEnum.<EnumDyeColor>func_177709_a("color", EnumDyeColor.class);
   protected static final AxisAlignedBB field_185758_b = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.0625D, 1.0D);

   protected BlockCarpet() {
      super(Material.field_151593_r);
      this.func_180632_j(this.field_176227_L.func_177621_b().func_177226_a(field_176330_a, EnumDyeColor.WHITE));
      this.func_149675_a(true);
      this.func_149647_a(CreativeTabs.field_78031_c);
   }

   public AxisAlignedBB func_185496_a(IBlockState p_185496_1_, IBlockAccess p_185496_2_, BlockPos p_185496_3_) {
      return field_185758_b;
   }

   public MapColor func_180659_g(IBlockState p_180659_1_, IBlockAccess p_180659_2_, BlockPos p_180659_3_) {
      return MapColor.func_193558_a((EnumDyeColor)p_180659_1_.func_177229_b(field_176330_a));
   }

   public boolean func_149662_c(IBlockState p_149662_1_) {
      return false;
   }

   public boolean func_149686_d(IBlockState p_149686_1_) {
      return false;
   }

   public boolean func_176196_c(World p_176196_1_, BlockPos p_176196_2_) {
      return super.func_176196_c(p_176196_1_, p_176196_2_) && this.func_176329_d(p_176196_1_, p_176196_2_);
   }

   public void func_189540_a(IBlockState p_189540_1_, World p_189540_2_, BlockPos p_189540_3_, Block p_189540_4_, BlockPos p_189540_5_) {
      this.func_176328_e(p_189540_2_, p_189540_3_, p_189540_1_);
   }

   private boolean func_176328_e(World p_176328_1_, BlockPos p_176328_2_, IBlockState p_176328_3_) {
      if (!this.func_176329_d(p_176328_1_, p_176328_2_)) {
         this.func_176226_b(p_176328_1_, p_176328_2_, p_176328_3_, 0);
         p_176328_1_.func_175698_g(p_176328_2_);
         return false;
      } else {
         return true;
      }
   }

   private boolean func_176329_d(World p_176329_1_, BlockPos p_176329_2_) {
      return !p_176329_1_.func_175623_d(p_176329_2_.func_177977_b());
   }

   public boolean func_176225_a(IBlockState p_176225_1_, IBlockAccess p_176225_2_, BlockPos p_176225_3_, EnumFacing p_176225_4_) {
      if (p_176225_4_ == EnumFacing.UP) {
         return true;
      } else {
         return p_176225_2_.func_180495_p(p_176225_3_.func_177972_a(p_176225_4_)).func_177230_c() == this ? true : super.func_176225_a(p_176225_1_, p_176225_2_, p_176225_3_, p_176225_4_);
      }
   }

   public int func_180651_a(IBlockState p_180651_1_) {
      return ((EnumDyeColor)p_180651_1_.func_177229_b(field_176330_a)).func_176765_a();
   }

   public void func_149666_a(CreativeTabs p_149666_1_, NonNullList<ItemStack> p_149666_2_) {
      for(int i = 0; i < 16; ++i) {
         p_149666_2_.add(new ItemStack(this, 1, i));
      }

   }

   public IBlockState func_176203_a(int p_176203_1_) {
      return this.func_176223_P().func_177226_a(field_176330_a, EnumDyeColor.func_176764_b(p_176203_1_));
   }

   public int func_176201_c(IBlockState p_176201_1_) {
      return ((EnumDyeColor)p_176201_1_.func_177229_b(field_176330_a)).func_176765_a();
   }

   protected BlockStateContainer func_180661_e() {
      return new BlockStateContainer(this, new IProperty[]{field_176330_a});
   }

   public BlockFaceShape func_193383_a(IBlockAccess p_193383_1_, IBlockState p_193383_2_, BlockPos p_193383_3_, EnumFacing p_193383_4_) {
      return p_193383_4_ == EnumFacing.DOWN ? BlockFaceShape.SOLID : BlockFaceShape.UNDEFINED;
   }
}
