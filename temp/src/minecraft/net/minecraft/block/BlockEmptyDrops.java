package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

public class BlockEmptyDrops extends Block {
   public BlockEmptyDrops(Material p_i46682_1_) {
      super(p_i46682_1_);
   }

   public int func_149745_a(Random p_149745_1_) {
      return 0;
   }

   public Item func_180660_a(IBlockState p_180660_1_, Random p_180660_2_, int p_180660_3_) {
      return Items.field_190931_a;
   }
}
