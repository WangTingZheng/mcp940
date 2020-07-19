package net.minecraft.block;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockHay extends BlockRotatedPillar {
   public BlockHay() {
      super(Material.field_151577_b, MapColor.field_151673_t);
      this.func_180632_j(this.field_176227_L.func_177621_b().func_177226_a(field_176298_M, EnumFacing.Axis.Y));
      this.func_149647_a(CreativeTabs.field_78030_b);
   }

   public void func_180658_a(World p_180658_1_, BlockPos p_180658_2_, Entity p_180658_3_, float p_180658_4_) {
      p_180658_3_.func_180430_e(p_180658_4_, 0.2F);
   }
}
