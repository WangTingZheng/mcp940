package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

public class BlockMelon extends Block {
   protected BlockMelon() {
      super(Material.field_151572_C, MapColor.field_151672_u);
      this.func_149647_a(CreativeTabs.field_78030_b);
   }

   public Item func_180660_a(IBlockState p_180660_1_, Random p_180660_2_, int p_180660_3_) {
      return Items.field_151127_ba;
   }

   public int func_149745_a(Random p_149745_1_) {
      return 3 + p_149745_1_.nextInt(5);
   }

   public int func_149679_a(int p_149679_1_, Random p_149679_2_) {
      return Math.min(9, this.func_149745_a(p_149679_2_) + p_149679_2_.nextInt(1 + p_149679_1_));
   }
}
