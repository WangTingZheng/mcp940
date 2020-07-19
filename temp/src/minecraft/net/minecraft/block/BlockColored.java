package net.minecraft.block;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BlockColored extends Block {
   public static final PropertyEnum<EnumDyeColor> field_176581_a = PropertyEnum.<EnumDyeColor>func_177709_a("color", EnumDyeColor.class);

   public BlockColored(Material p_i45398_1_) {
      super(p_i45398_1_);
      this.func_180632_j(this.field_176227_L.func_177621_b().func_177226_a(field_176581_a, EnumDyeColor.WHITE));
      this.func_149647_a(CreativeTabs.field_78030_b);
   }

   public int func_180651_a(IBlockState p_180651_1_) {
      return ((EnumDyeColor)p_180651_1_.func_177229_b(field_176581_a)).func_176765_a();
   }

   public void func_149666_a(CreativeTabs p_149666_1_, NonNullList<ItemStack> p_149666_2_) {
      for(EnumDyeColor enumdyecolor : EnumDyeColor.values()) {
         p_149666_2_.add(new ItemStack(this, 1, enumdyecolor.func_176765_a()));
      }

   }

   public MapColor func_180659_g(IBlockState p_180659_1_, IBlockAccess p_180659_2_, BlockPos p_180659_3_) {
      return MapColor.func_193558_a((EnumDyeColor)p_180659_1_.func_177229_b(field_176581_a));
   }

   public IBlockState func_176203_a(int p_176203_1_) {
      return this.func_176223_P().func_177226_a(field_176581_a, EnumDyeColor.func_176764_b(p_176203_1_));
   }

   public int func_176201_c(IBlockState p_176201_1_) {
      return ((EnumDyeColor)p_176201_1_.func_177229_b(field_176581_a)).func_176765_a();
   }

   protected BlockStateContainer func_180661_e() {
      return new BlockStateContainer(this, new IProperty[]{field_176581_a});
   }
}
