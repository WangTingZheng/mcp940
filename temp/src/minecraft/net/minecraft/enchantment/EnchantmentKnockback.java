package net.minecraft.enchantment;

import net.minecraft.inventory.EntityEquipmentSlot;

public class EnchantmentKnockback extends Enchantment {
   protected EnchantmentKnockback(Enchantment.Rarity p_i46727_1_, EntityEquipmentSlot... p_i46727_2_) {
      super(p_i46727_1_, EnumEnchantmentType.WEAPON, p_i46727_2_);
      this.func_77322_b("knockback");
   }

   public int func_77321_a(int p_77321_1_) {
      return 5 + 20 * (p_77321_1_ - 1);
   }

   public int func_77317_b(int p_77317_1_) {
      return super.func_77321_a(p_77317_1_) + 50;
   }

   public int func_77325_b() {
      return 2;
   }
}
