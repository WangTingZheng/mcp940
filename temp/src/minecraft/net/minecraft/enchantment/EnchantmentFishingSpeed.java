package net.minecraft.enchantment;

import net.minecraft.inventory.EntityEquipmentSlot;

public class EnchantmentFishingSpeed extends Enchantment {
   protected EnchantmentFishingSpeed(Enchantment.Rarity p_i46729_1_, EnumEnchantmentType p_i46729_2_, EntityEquipmentSlot... p_i46729_3_) {
      super(p_i46729_1_, p_i46729_2_, p_i46729_3_);
      this.func_77322_b("fishingSpeed");
   }

   public int func_77321_a(int p_77321_1_) {
      return 15 + (p_77321_1_ - 1) * 9;
   }

   public int func_77317_b(int p_77317_1_) {
      return super.func_77321_a(p_77317_1_) + 50;
   }

   public int func_77325_b() {
      return 3;
   }
}
