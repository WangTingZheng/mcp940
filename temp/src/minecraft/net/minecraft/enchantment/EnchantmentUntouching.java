package net.minecraft.enchantment;

import net.minecraft.init.Enchantments;
import net.minecraft.inventory.EntityEquipmentSlot;

public class EnchantmentUntouching extends Enchantment {
   protected EnchantmentUntouching(Enchantment.Rarity p_i46721_1_, EntityEquipmentSlot... p_i46721_2_) {
      super(p_i46721_1_, EnumEnchantmentType.DIGGER, p_i46721_2_);
      this.func_77322_b("untouching");
   }

   public int func_77321_a(int p_77321_1_) {
      return 15;
   }

   public int func_77317_b(int p_77317_1_) {
      return super.func_77321_a(p_77317_1_) + 50;
   }

   public int func_77325_b() {
      return 1;
   }

   public boolean func_77326_a(Enchantment p_77326_1_) {
      return super.func_77326_a(p_77326_1_) && p_77326_1_ != Enchantments.field_185308_t;
   }
}
