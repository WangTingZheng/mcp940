package net.minecraft.enchantment;

import net.minecraft.inventory.EntityEquipmentSlot;

public class EnchantmentBindingCurse extends Enchantment {
   public EnchantmentBindingCurse(Enchantment.Rarity p_i47254_1_, EntityEquipmentSlot... p_i47254_2_) {
      super(p_i47254_1_, EnumEnchantmentType.WEARABLE, p_i47254_2_);
      this.func_77322_b("binding_curse");
   }

   public int func_77321_a(int p_77321_1_) {
      return 25;
   }

   public int func_77317_b(int p_77317_1_) {
      return 50;
   }

   public int func_77325_b() {
      return 1;
   }

   public boolean func_185261_e() {
      return true;
   }

   public boolean func_190936_d() {
      return true;
   }
}
