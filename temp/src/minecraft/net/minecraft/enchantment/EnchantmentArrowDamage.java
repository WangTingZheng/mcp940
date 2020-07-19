package net.minecraft.enchantment;

import net.minecraft.inventory.EntityEquipmentSlot;

public class EnchantmentArrowDamage extends Enchantment {
   public EnchantmentArrowDamage(Enchantment.Rarity p_i46738_1_, EntityEquipmentSlot... p_i46738_2_) {
      super(p_i46738_1_, EnumEnchantmentType.BOW, p_i46738_2_);
      this.func_77322_b("arrowDamage");
   }

   public int func_77321_a(int p_77321_1_) {
      return 1 + (p_77321_1_ - 1) * 10;
   }

   public int func_77317_b(int p_77317_1_) {
      return this.func_77321_a(p_77317_1_) + 15;
   }

   public int func_77325_b() {
      return 5;
   }
}
