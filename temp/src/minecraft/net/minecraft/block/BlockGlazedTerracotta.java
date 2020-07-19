package net.minecraft.block;

import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockGlazedTerracotta extends BlockHorizontal {
   public BlockGlazedTerracotta(EnumDyeColor p_i47400_1_) {
      super(Material.field_151576_e, MapColor.func_193558_a(p_i47400_1_));
      this.func_149711_c(1.4F);
      this.func_149672_a(SoundType.field_185851_d);
      String s = p_i47400_1_.func_176762_d();
      if (s.length() > 1) {
         String s1 = s.substring(0, 1).toUpperCase() + s.substring(1, s.length());
         this.func_149663_c("glazedTerracotta" + s1);
      }

      this.func_149647_a(CreativeTabs.field_78031_c);
   }

   protected BlockStateContainer func_180661_e() {
      return new BlockStateContainer(this, new IProperty[]{field_185512_D});
   }

   public IBlockState func_185499_a(IBlockState p_185499_1_, Rotation p_185499_2_) {
      return p_185499_1_.func_177226_a(field_185512_D, p_185499_2_.func_185831_a((EnumFacing)p_185499_1_.func_177229_b(field_185512_D)));
   }

   public IBlockState func_185471_a(IBlockState p_185471_1_, Mirror p_185471_2_) {
      return p_185471_1_.func_185907_a(p_185471_2_.func_185800_a((EnumFacing)p_185471_1_.func_177229_b(field_185512_D)));
   }

   public IBlockState func_180642_a(World p_180642_1_, BlockPos p_180642_2_, EnumFacing p_180642_3_, float p_180642_4_, float p_180642_5_, float p_180642_6_, int p_180642_7_, EntityLivingBase p_180642_8_) {
      return this.func_176223_P().func_177226_a(field_185512_D, p_180642_8_.func_174811_aO().func_176734_d());
   }

   public int func_176201_c(IBlockState p_176201_1_) {
      int i = 0;
      i = i | ((EnumFacing)p_176201_1_.func_177229_b(field_185512_D)).func_176736_b();
      return i;
   }

   public IBlockState func_176203_a(int p_176203_1_) {
      return this.func_176223_P().func_177226_a(field_185512_D, EnumFacing.func_176731_b(p_176203_1_));
   }

   public EnumPushReaction func_149656_h(IBlockState p_149656_1_) {
      return EnumPushReaction.PUSH_ONLY;
   }
}
