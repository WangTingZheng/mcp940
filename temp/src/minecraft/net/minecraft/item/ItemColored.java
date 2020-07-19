package net.minecraft.item;

import net.minecraft.block.Block;

public class ItemColored extends ItemBlock {
   private String[] field_150945_c;

   public ItemColored(Block p_i45332_1_, boolean p_i45332_2_) {
      super(p_i45332_1_);
      if (p_i45332_2_) {
         this.func_77656_e(0);
         this.func_77627_a(true);
      }

   }

   public int func_77647_b(int p_77647_1_) {
      return p_77647_1_;
   }

   public ItemColored func_150943_a(String[] p_150943_1_) {
      this.field_150945_c = p_150943_1_;
      return this;
   }

   public String func_77667_c(ItemStack p_77667_1_) {
      if (this.field_150945_c == null) {
         return super.func_77667_c(p_77667_1_);
      } else {
         int i = p_77667_1_.func_77960_j();
         return i >= 0 && i < this.field_150945_c.length ? super.func_77667_c(p_77667_1_) + "." + this.field_150945_c[i] : super.func_77667_c(p_77667_1_);
      }
   }
}
