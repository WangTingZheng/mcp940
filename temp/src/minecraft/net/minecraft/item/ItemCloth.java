package net.minecraft.item;

import net.minecraft.block.Block;

public class ItemCloth extends ItemBlock {
   public ItemCloth(Block p_i45358_1_) {
      super(p_i45358_1_);
      this.func_77656_e(0);
      this.func_77627_a(true);
   }

   public int func_77647_b(int p_77647_1_) {
      return p_77647_1_;
   }

   public String func_77667_c(ItemStack p_77667_1_) {
      return super.func_77658_a() + "." + EnumDyeColor.func_176764_b(p_77667_1_.func_77960_j()).func_176762_d();
   }
}
