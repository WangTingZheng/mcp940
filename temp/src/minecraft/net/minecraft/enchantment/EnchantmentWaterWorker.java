package net.minecraft.enchantment;

import net.minecraft.inventory.EntityEquipmentSlot;

public class EnchantmentWaterWorker extends Enchantment {
   public EnchantmentWaterWorker(Enchantment.Rarity p_i46719_1_, EntityEquipmentSlot... p_i46719_2_) {
      super(p_i46719_1_, EnumEnchantmentType.ARMOR_HEAD, p_i46719_2_);
      this.func_77322_b("waterWorker");
   }

   public int func_77321_a(int p_77321_1_) {
      return 1;
   }

   public int func_77317_b(int p_77317_1_) {
      return this.func_77321_a(p_77317_1_) + 40;
   }

   public int func_77325_b() {
      return 1;
   }
}
