package net.minecraft.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.NonNullList;

public class ItemCoal extends Item {
   public ItemCoal() {
      this.func_77627_a(true);
      this.func_77656_e(0);
      this.func_77637_a(CreativeTabs.field_78035_l);
   }

   public String func_77667_c(ItemStack p_77667_1_) {
      return p_77667_1_.func_77960_j() == 1 ? "item.charcoal" : "item.coal";
   }

   public void func_150895_a(CreativeTabs p_150895_1_, NonNullList<ItemStack> p_150895_2_) {
      if (this.func_194125_a(p_150895_1_)) {
         p_150895_2_.add(new ItemStack(this, 1, 0));
         p_150895_2_.add(new ItemStack(this, 1, 1));
      }

   }
}
