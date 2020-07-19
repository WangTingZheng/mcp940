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
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Mirror;
import net.minecraft.util.NonNullList;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockStainedGlassPane extends BlockPane {
   public static final PropertyEnum<EnumDyeColor> field_176245_a = PropertyEnum.<EnumDyeColor>func_177709_a("color", EnumDyeColor.class);

   public BlockStainedGlassPane() {
      super(Material.field_151592_s, false);
      this.func_180632_j(this.field_176227_L.func_177621_b().func_177226_a(field_176241_b, Boolean.valueOf(false)).func_177226_a(field_176242_M, Boolean.valueOf(false)).func_177226_a(field_176243_N, Boolean.valueOf(false)).func_177226_a(field_176244_O, Boolean.valueOf(false)).func_177226_a(field_176245_a, EnumDyeColor.WHITE));
      this.func_149647_a(CreativeTabs.field_78031_c);
   }

   public int func_180651_a(IBlockState p_180651_1_) {
      return ((EnumDyeColor)p_180651_1_.func_177229_b(field_176245_a)).func_176765_a();
   }

   public void func_149666_a(CreativeTabs p_149666_1_, NonNullList<ItemStack> p_149666_2_) {
      for(int i = 0; i < EnumDyeColor.values().length; ++i) {
         p_149666_2_.add(new ItemStack(this, 1, i));
      }

   }

   public MapColor func_180659_g(IBlockState p_180659_1_, IBlockAccess p_180659_2_, BlockPos p_180659_3_) {
      return MapColor.func_193558_a((EnumDyeColor)p_180659_1_.func_177229_b(field_176245_a));
   }

   public BlockRenderLayer func_180664_k() {
      return BlockRenderLayer.TRANSLUCENT;
   }

   public IBlockState func_176203_a(int p_176203_1_) {
      return this.func_176223_P().func_177226_a(field_176245_a, EnumDyeColor.func_176764_b(p_176203_1_));
   }

   public int func_176201_c(IBlockState p_176201_1_) {
      return ((EnumDyeColor)p_176201_1_.func_177229_b(field_176245_a)).func_176765_a();
   }

   public IBlockState func_185499_a(IBlockState p_185499_1_, Rotation p_185499_2_) {
      switch(p_185499_2_) {
      case CLOCKWISE_180:
         return p_185499_1_.func_177226_a(field_176241_b, p_185499_1_.func_177229_b(field_176243_N)).func_177226_a(field_176242_M, p_185499_1_.func_177229_b(field_176244_O)).func_177226_a(field_176243_N, p_185499_1_.func_177229_b(field_176241_b)).func_177226_a(field_176244_O, p_185499_1_.func_177229_b(field_176242_M));
      case COUNTERCLOCKWISE_90:
         return p_185499_1_.func_177226_a(field_176241_b, p_185499_1_.func_177229_b(field_176242_M)).func_177226_a(field_176242_M, p_185499_1_.func_177229_b(field_176243_N)).func_177226_a(field_176243_N, p_185499_1_.func_177229_b(field_176244_O)).func_177226_a(field_176244_O, p_185499_1_.func_177229_b(field_176241_b));
      case CLOCKWISE_90:
         return p_185499_1_.func_177226_a(field_176241_b, p_185499_1_.func_177229_b(field_176244_O)).func_177226_a(field_176242_M, p_185499_1_.func_177229_b(field_176241_b)).func_177226_a(field_176243_N, p_185499_1_.func_177229_b(field_176242_M)).func_177226_a(field_176244_O, p_185499_1_.func_177229_b(field_176243_N));
      default:
         return p_185499_1_;
      }
   }

   public IBlockState func_185471_a(IBlockState p_185471_1_, Mirror p_185471_2_) {
      switch(p_185471_2_) {
      case LEFT_RIGHT:
         return p_185471_1_.func_177226_a(field_176241_b, p_185471_1_.func_177229_b(field_176243_N)).func_177226_a(field_176243_N, p_185471_1_.func_177229_b(field_176241_b));
      case FRONT_BACK:
         return p_185471_1_.func_177226_a(field_176242_M, p_185471_1_.func_177229_b(field_176244_O)).func_177226_a(field_176244_O, p_185471_1_.func_177229_b(field_176242_M));
      default:
         return super.func_185471_a(p_185471_1_, p_185471_2_);
      }
   }

   protected BlockStateContainer func_180661_e() {
      return new BlockStateContainer(this, new IProperty[]{field_176241_b, field_176242_M, field_176244_O, field_176243_N, field_176245_a});
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
}
