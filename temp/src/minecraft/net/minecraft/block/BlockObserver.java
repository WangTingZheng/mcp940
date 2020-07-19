package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockObserver extends BlockDirectional {
   public static final PropertyBool field_190963_a = PropertyBool.func_177716_a("powered");

   public BlockObserver() {
      super(Material.field_151576_e);
      this.func_180632_j(this.field_176227_L.func_177621_b().func_177226_a(field_176387_N, EnumFacing.SOUTH).func_177226_a(field_190963_a, Boolean.valueOf(false)));
      this.func_149647_a(CreativeTabs.field_78028_d);
   }

   protected BlockStateContainer func_180661_e() {
      return new BlockStateContainer(this, new IProperty[]{field_176387_N, field_190963_a});
   }

   public IBlockState func_185499_a(IBlockState p_185499_1_, Rotation p_185499_2_) {
      return p_185499_1_.func_177226_a(field_176387_N, p_185499_2_.func_185831_a((EnumFacing)p_185499_1_.func_177229_b(field_176387_N)));
   }

   public IBlockState func_185471_a(IBlockState p_185471_1_, Mirror p_185471_2_) {
      return p_185471_1_.func_185907_a(p_185471_2_.func_185800_a((EnumFacing)p_185471_1_.func_177229_b(field_176387_N)));
   }

   public void func_180650_b(World p_180650_1_, BlockPos p_180650_2_, IBlockState p_180650_3_, Random p_180650_4_) {
      if (((Boolean)p_180650_3_.func_177229_b(field_190963_a)).booleanValue()) {
         p_180650_1_.func_180501_a(p_180650_2_, p_180650_3_.func_177226_a(field_190963_a, Boolean.valueOf(false)), 2);
      } else {
         p_180650_1_.func_180501_a(p_180650_2_, p_180650_3_.func_177226_a(field_190963_a, Boolean.valueOf(true)), 2);
         p_180650_1_.func_175684_a(p_180650_2_, this, 2);
      }

      this.func_190961_e(p_180650_1_, p_180650_2_, p_180650_3_);
   }

   public void func_189540_a(IBlockState p_189540_1_, World p_189540_2_, BlockPos p_189540_3_, Block p_189540_4_, BlockPos p_189540_5_) {
   }

   public void func_190962_b(IBlockState p_190962_1_, World p_190962_2_, BlockPos p_190962_3_, Block p_190962_4_, BlockPos p_190962_5_) {
      if (!p_190962_2_.field_72995_K && p_190962_3_.func_177972_a((EnumFacing)p_190962_1_.func_177229_b(field_176387_N)).equals(p_190962_5_)) {
         this.func_190960_d(p_190962_1_, p_190962_2_, p_190962_3_);
      }

   }

   private void func_190960_d(IBlockState p_190960_1_, World p_190960_2_, BlockPos p_190960_3_) {
      if (!((Boolean)p_190960_1_.func_177229_b(field_190963_a)).booleanValue()) {
         if (!p_190960_2_.func_184145_b(p_190960_3_, this)) {
            p_190960_2_.func_175684_a(p_190960_3_, this, 2);
         }

      }
   }

   protected void func_190961_e(World p_190961_1_, BlockPos p_190961_2_, IBlockState p_190961_3_) {
      EnumFacing enumfacing = (EnumFacing)p_190961_3_.func_177229_b(field_176387_N);
      BlockPos blockpos = p_190961_2_.func_177972_a(enumfacing.func_176734_d());
      p_190961_1_.func_190524_a(blockpos, this, p_190961_2_);
      p_190961_1_.func_175695_a(blockpos, this, enumfacing);
   }

   public boolean func_149744_f(IBlockState p_149744_1_) {
      return true;
   }

   public int func_176211_b(IBlockState p_176211_1_, IBlockAccess p_176211_2_, BlockPos p_176211_3_, EnumFacing p_176211_4_) {
      return p_176211_1_.func_185911_a(p_176211_2_, p_176211_3_, p_176211_4_);
   }

   public int func_180656_a(IBlockState p_180656_1_, IBlockAccess p_180656_2_, BlockPos p_180656_3_, EnumFacing p_180656_4_) {
      return ((Boolean)p_180656_1_.func_177229_b(field_190963_a)).booleanValue() && p_180656_1_.func_177229_b(field_176387_N) == p_180656_4_ ? 15 : 0;
   }

   public void func_176213_c(World p_176213_1_, BlockPos p_176213_2_, IBlockState p_176213_3_) {
      if (!p_176213_1_.field_72995_K) {
         if (((Boolean)p_176213_3_.func_177229_b(field_190963_a)).booleanValue()) {
            this.func_180650_b(p_176213_1_, p_176213_2_, p_176213_3_, p_176213_1_.field_73012_v);
         }

         this.func_190960_d(p_176213_3_, p_176213_1_, p_176213_2_);
      }

   }

   public void func_180663_b(World p_180663_1_, BlockPos p_180663_2_, IBlockState p_180663_3_) {
      if (((Boolean)p_180663_3_.func_177229_b(field_190963_a)).booleanValue() && p_180663_1_.func_184145_b(p_180663_2_, this)) {
         this.func_190961_e(p_180663_1_, p_180663_2_, p_180663_3_.func_177226_a(field_190963_a, Boolean.valueOf(false)));
      }

   }

   public IBlockState func_180642_a(World p_180642_1_, BlockPos p_180642_2_, EnumFacing p_180642_3_, float p_180642_4_, float p_180642_5_, float p_180642_6_, int p_180642_7_, EntityLivingBase p_180642_8_) {
      return this.func_176223_P().func_177226_a(field_176387_N, EnumFacing.func_190914_a(p_180642_2_, p_180642_8_).func_176734_d());
   }

   public int func_176201_c(IBlockState p_176201_1_) {
      int i = 0;
      i = i | ((EnumFacing)p_176201_1_.func_177229_b(field_176387_N)).func_176745_a();
      if (((Boolean)p_176201_1_.func_177229_b(field_190963_a)).booleanValue()) {
         i |= 8;
      }

      return i;
   }

   public IBlockState func_176203_a(int p_176203_1_) {
      return this.func_176223_P().func_177226_a(field_176387_N, EnumFacing.func_82600_a(p_176203_1_ & 7));
   }
}
