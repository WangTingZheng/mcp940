package net.minecraft.enchantment;

import net.minecraft.inventory.EntityEquipmentSlot;

public class EnchantmentOxygen extends Enchantment {
   public EnchantmentOxygen(Enchantment.Rarity p_i46724_1_, EntityEquipmentSlot... p_i46724_2_) {
      super(p_i46724_1_, EnumEnchantmentType.ARMOR_HEAD, p_i46724_2_);
      this.func_77322_b("oxygen");
   }

   public int func_77321_a(int p_77321_1_) {
      return 10 * p_77321_1_;
   }

   public int func_77317_b(int p_77317_1_) {
      return this.func_77321_a(p_77317_1_) + 30;
   }

   public int func_77325_b() {
      return 3;
   }
}
