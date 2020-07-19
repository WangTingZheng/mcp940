package net.minecraft.item;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemShears extends Item {
   public ItemShears() {
      this.func_77625_d(1);
      this.func_77656_e(238);
      this.func_77637_a(CreativeTabs.field_78040_i);
   }

   public boolean func_179218_a(ItemStack p_179218_1_, World p_179218_2_, IBlockState p_179218_3_, BlockPos p_179218_4_, EntityLivingBase p_179218_5_) {
      if (!p_179218_2_.field_72995_K) {
         p_179218_1_.func_77972_a(1, p_179218_5_);
      }

      Block block = p_179218_3_.func_177230_c();
      return p_179218_3_.func_185904_a() != Material.field_151584_j && block != Blocks.field_150321_G && block != Blocks.field_150329_H && block != Blocks.field_150395_bd && block != Blocks.field_150473_bD && block != Blocks.field_150325_L ? super.func_179218_a(p_179218_1_, p_179218_2_, p_179218_3_, p_179218_4_, p_179218_5_) : true;
   }

   public boolean func_150897_b(IBlockState p_150897_1_) {
      Block block = p_150897_1_.func_177230_c();
      return block == Blocks.field_150321_G || block == Blocks.field_150488_af || block == Blocks.field_150473_bD;
   }

   public float func_150893_a(ItemStack p_150893_1_, IBlockState p_150893_2_) {
      Block block = p_150893_2_.func_177230_c();
      if (block != Blocks.field_150321_G && p_150893_2_.func_185904_a() != Material.field_151584_j) {
         return block == Blocks.field_150325_L ? 5.0F : super.func_150893_a(p_150893_1_, p_150893_2_);
      } else {
         return 15.0F;
      }
   }
}
