package net.minecraft.block;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockConcretePowder extends BlockFalling {
   public static final PropertyEnum<EnumDyeColor> field_192426_a = PropertyEnum.<EnumDyeColor>func_177709_a("color", EnumDyeColor.class);

   public BlockConcretePowder() {
      super(Material.field_151595_p);
      this.func_180632_j(this.field_176227_L.func_177621_b().func_177226_a(field_192426_a, EnumDyeColor.WHITE));
      this.func_149647_a(CreativeTabs.field_78030_b);
   }

   public void func_176502_a_(World p_176502_1_, BlockPos p_176502_2_, IBlockState p_176502_3_, IBlockState p_176502_4_) {
      if (p_176502_4_.func_185904_a().func_76224_d()) {
         p_176502_1_.func_180501_a(p_176502_2_, Blocks.field_192443_dR.func_176223_P().func_177226_a(BlockColored.field_176581_a, p_176502_3_.func_177229_b(field_192426_a)), 3);
      }

   }

   protected boolean func_192425_e(World p_192425_1_, BlockPos p_192425_2_, IBlockState p_192425_3_) {
      boolean flag = false;

      for(EnumFacing enumfacing : EnumFacing.values()) {
         if (enumfacing != EnumFacing.DOWN) {
            BlockPos blockpos = p_192425_2_.func_177972_a(enumfacing);
            if (p_192425_1_.func_180495_p(blockpos).func_185904_a() == Material.field_151586_h) {
               flag = true;
               break;
            }
         }
      }

      if (flag) {
         p_192425_1_.func_180501_a(p_192425_2_, Blocks.field_192443_dR.func_176223_P().func_177226_a(BlockColored.field_176581_a, p_192425_3_.func_177229_b(field_192426_a)), 3);
      }

      return flag;
   }

   public void func_189540_a(IBlockState p_189540_1_, World p_189540_2_, BlockPos p_189540_3_, Block p_189540_4_, BlockPos p_189540_5_) {
      if (!this.func_192425_e(p_189540_2_, p_189540_3_, p_189540_1_)) {
         super.func_189540_a(p_189540_1_, p_189540_2_, p_189540_3_, p_189540_4_, p_189540_5_);
      }

   }

   public void func_176213_c(World p_176213_1_, BlockPos p_176213_2_, IBlockState p_176213_3_) {
      if (!this.func_192425_e(p_176213_1_, p_176213_2_, p_176213_3_)) {
         super.func_176213_c(p_176213_1_, p_176213_2_, p_176213_3_);
      }

   }

   public int func_180651_a(IBlockState p_180651_1_) {
      return ((EnumDyeColor)p_180651_1_.func_177229_b(field_192426_a)).func_176765_a();
   }

   public void func_149666_a(CreativeTabs p_149666_1_, NonNullList<ItemStack> p_149666_2_) {
      for(EnumDyeColor enumdyecolor : EnumDyeColor.values()) {
         p_149666_2_.add(new ItemStack(this, 1, enumdyecolor.func_176765_a()));
      }

   }

   public MapColor func_180659_g(IBlockState p_180659_1_, IBlockAccess p_180659_2_, BlockPos p_180659_3_) {
      return MapColor.func_193558_a((EnumDyeColor)p_180659_1_.func_177229_b(field_192426_a));
   }

   public IBlockState func_176203_a(int p_176203_1_) {
      return this.func_176223_P().func_177226_a(field_192426_a, EnumDyeColor.func_176764_b(p_176203_1_));
   }

   public int func_176201_c(IBlockState p_176201_1_) {
      return ((EnumDyeColor)p_176201_1_.func_177229_b(field_192426_a)).func_176765_a();
   }

   protected BlockStateContainer func_180661_e() {
      return new BlockStateContainer(this, new IProperty[]{field_192426_a});
   }
}
