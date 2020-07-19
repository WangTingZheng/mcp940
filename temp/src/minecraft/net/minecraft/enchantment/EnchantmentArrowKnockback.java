package net.minecraft.enchantment;

import net.minecraft.inventory.EntityEquipmentSlot;

public class EnchantmentArrowKnockback extends Enchantment {
   public EnchantmentArrowKnockback(Enchantment.Rarity p_i46735_1_, EntityEquipmentSlot... p_i46735_2_) {
      super(p_i46735_1_, EnumEnchantmentType.BOW, p_i46735_2_);
      this.func_77322_b("arrowKnockback");
   }

   public int func_77321_a(int p_77321_1_) {
      return 12 + (p_77321_1_ - 1) * 20;
   }

   public int func_77317_b(int p_77317_1_) {
      return this.func_77321_a(p_77317_1_) + 25;
   }

   public int func_77325_b() {
      return 2;
   }
}
