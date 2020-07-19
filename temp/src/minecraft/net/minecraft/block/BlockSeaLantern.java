package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;

public class BlockSeaLantern extends Block {
   public BlockSeaLantern(Material p_i45685_1_) {
      super(p_i45685_1_);
      this.func_149647_a(CreativeTabs.field_78030_b);
   }

   public int func_149745_a(Random p_149745_1_) {
      return 2 + p_149745_1_.nextInt(2);
   }

   public int func_149679_a(int p_149679_1_, Random p_149679_2_) {
      return MathHelper.func_76125_a(this.func_149745_a(p_149679_2_) + p_149679_2_.nextInt(p_149679_1_ + 1), 1, 5);
   }

   public Item func_180660_a(IBlockState p_180660_1_, Random p_180660_2_, int p_180660_3_) {
      return Items.field_179563_cD;
   }

   public MapColor func_180659_g(IBlockState p_180659_1_, IBlockAccess p_180659_2_, BlockPos p_180659_3_) {
      return MapColor.field_151677_p;
   }

   protected boolean func_149700_E() {
      return true;
   }
}
