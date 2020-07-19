package net.minecraft.item;

import net.minecraft.block.BlockLeaves;

public class ItemLeaves extends ItemBlock {
   private final BlockLeaves field_150940_b;

   public ItemLeaves(BlockLeaves p_i45344_1_) {
      super(p_i45344_1_);
      this.field_150940_b = p_i45344_1_;
      this.func_77656_e(0);
      this.func_77627_a(true);
   }

   public int func_77647_b(int p_77647_1_) {
      return p_77647_1_ | 4;
   }

   public String func_77667_c(ItemStack p_77667_1_) {
      return super.func_77658_a() + "." + this.field_150940_b.func_176233_b(p_77667_1_.func_77960_j()).func_176840_c();
   }
}
