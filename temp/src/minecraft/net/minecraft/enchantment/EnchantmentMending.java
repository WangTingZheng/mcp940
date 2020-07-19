package net.minecraft.enchantment;

import net.minecraft.inventory.EntityEquipmentSlot;

public class EnchantmentMending extends Enchantment {
   public EnchantmentMending(Enchantment.Rarity p_i46725_1_, EntityEquipmentSlot... p_i46725_2_) {
      super(p_i46725_1_, EnumEnchantmentType.BREAKABLE, p_i46725_2_);
      this.func_77322_b("mending");
   }

   public int func_77321_a(int p_77321_1_) {
      return p_77321_1_ * 25;
   }

   public int func_77317_b(int p_77317_1_) {
      return this.func_77321_a(p_77317_1_) + 50;
   }

   public boolean func_185261_e() {
      return true;
   }

   public int func_77325_b() {
      return 1;
   }
}
