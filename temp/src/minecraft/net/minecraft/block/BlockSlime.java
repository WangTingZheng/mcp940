package net.minecraft.block;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockSlime extends BlockBreakable {
   public BlockSlime() {
      super(Material.field_151571_B, false, MapColor.field_151661_c);
      this.func_149647_a(CreativeTabs.field_78031_c);
      this.field_149765_K = 0.8F;
   }

   public BlockRenderLayer func_180664_k() {
      return BlockRenderLayer.TRANSLUCENT;
   }

   public void func_180658_a(World p_180658_1_, BlockPos p_180658_2_, Entity p_180658_3_, float p_180658_4_) {
      if (p_180658_3_.func_70093_af()) {
         super.func_180658_a(p_180658_1_, p_180658_2_, p_180658_3_, p_180658_4_);
      } else {
         p_180658_3_.func_180430_e(p_180658_4_, 0.0F);
      }

   }

   public void func_176216_a(World p_176216_1_, Entity p_176216_2_) {
      if (p_176216_2_.func_70093_af()) {
         super.func_176216_a(p_176216_1_, p_176216_2_);
      } else if (p_176216_2_.field_70181_x < 0.0D) {
         p_176216_2_.field_70181_x = -p_176216_2_.field_70181_x;
         if (!(p_176216_2_ instanceof EntityLivingBase)) {
            p_176216_2_.field_70181_x *= 0.8D;
         }
      }

   }

   public void func_176199_a(World p_176199_1_, BlockPos p_176199_2_, Entity p_176199_3_) {
      if (Math.abs(p_176199_3_.field_70181_x) < 0.1D && !p_176199_3_.func_70093_af()) {
         double d0 = 0.4D + Math.abs(p_176199_3_.field_70181_x) * 0.2D;
         p_176199_3_.field_70159_w *= d0;
         p_176199_3_.field_70179_y *= d0;
      }

      super.func_176199_a(p_176199_1_, p_176199_2_, p_176199_3_);
   }
}
