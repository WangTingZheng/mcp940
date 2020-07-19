package net.minecraft.enchantment;

import net.minecraft.util.WeightedRandom;

public class EnchantmentData extends WeightedRandom.Item {
   public final Enchantment field_76302_b;
   public final int field_76303_c;

   public EnchantmentData(Enchantment p_i1930_1_, int p_i1930_2_) {
      super(p_i1930_1_.func_77324_c().func_185270_a());
      this.field_76302_b = p_i1930_1_;
      this.field_76303_c = p_i1930_2_;
   }
}
