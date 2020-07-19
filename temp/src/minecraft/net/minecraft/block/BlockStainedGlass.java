package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockStainedGlass extends BlockBreakable {
   public static final PropertyEnum<EnumDyeColor> field_176547_a = PropertyEnum.<EnumDyeColor>func_177709_a("color", EnumDyeColor.class);

   public BlockStainedGlass(Material p_i45427_1_) {
      super(p_i45427_1_, false);
      this.func_180632_j(this.field_176227_L.func_177621_b().func_177226_a(field_176547_a, EnumDyeColor.WHITE));
      this.func_149647_a(CreativeTabs.field_78030_b);
   }

   public int func_180651_a(IBlockState p_180651_1_) {
      return ((EnumDyeColor)p_180651_1_.func_177229_b(field_176547_a)).func_176765_a();
   }

   public void func_149666_a(CreativeTabs p_149666_1_, NonNullList<ItemStack> p_149666_2_) {
      for(EnumDyeColor enumdyecolor : EnumDyeColor.values()) {
         p_149666_2_.add(new ItemStack(this, 1, enumdyecolor.func_176765_a()));
      }

   }

   public MapColor func_180659_g(IBlockState p_180659_1_, IBlockAccess p_180659_2_, BlockPos p_180659_3_) {
      return MapColor.func_193558_a((EnumDyeColor)p_180659_1_.func_177229_b(field_176547_a));
   }

   public BlockRenderLayer func_180664_k() {
      return BlockRenderLayer.TRANSLUCENT;
   }

   public int func_149745_a(Random p_149745_1_) {
      return 0;
   }

   protected boolean func_149700_E() {
      return true;
   }

   public boolean func_149686_d(IBlockState p_149686_1_) {
      return false;
   }

   public IBlockState func_176203_a(int p_176203_1_) {
      return this.func_176223_P().func_177226_a(field_176547_a, EnumDyeColor.func_176764_b(p_176203_1_));
   }

   public void func_176213_c(World p_176213_1_, BlockPos p_176213_2_, IBlockState p_176213_3_) {
      if (!p_176213_1_.field_72995_K) {
         BlockBeacon.func_176450_d(p_176213_1_, p_176213_2_);
      }

   }

   public void func_180663_b(World p_180663_1_, BlockPos p_180663_2_, IBlockState p_180663_3_) {
      if (!p_180663_1_.field_72995_K) {
         BlockBeacon.func_176450_d(p_180663_1_, p_180663_2_);
      }

   }

   public int func_176201_c(IBlockState p_176201_1_) {
      return ((EnumDyeColor)p_176201_1_.func_177229_b(field_176547_a)).func_176765_a();
   }

   protected BlockStateContainer func_180661_e() {
      return new BlockStateContainer(this, new IProperty[]{field_176547_a});
   }
}
