package net.minecraft.enchantment;

import net.minecraft.inventory.EntityEquipmentSlot;

public class EnchantmentArrowInfinite extends Enchantment {
   public EnchantmentArrowInfinite(Enchantment.Rarity p_i46736_1_, EntityEquipmentSlot... p_i46736_2_) {
      super(p_i46736_1_, EnumEnchantmentType.BOW, p_i46736_2_);
      this.func_77322_b("arrowInfinite");
   }

   public int func_77321_a(int p_77321_1_) {
      return 20;
   }

   public int func_77317_b(int p_77317_1_) {
      return 50;
   }

   public int func_77325_b() {
      return 1;
   }

   public boolean func_77326_a(Enchantment p_77326_1_) {
      return p_77326_1_ instanceof EnchantmentMending ? false : super.func_77326_a(p_77326_1_);
   }
}
