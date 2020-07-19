package net.minecraft.enchantment;

import net.minecraft.init.Enchantments;
import net.minecraft.inventory.EntityEquipmentSlot;

public class EnchantmentWaterWalker extends Enchantment {
   public EnchantmentWaterWalker(Enchantment.Rarity p_i46720_1_, EntityEquipmentSlot... p_i46720_2_) {
      super(p_i46720_1_, EnumEnchantmentType.ARMOR_FEET, p_i46720_2_);
      this.func_77322_b("waterWalker");
   }

   public int func_77321_a(int p_77321_1_) {
      return p_77321_1_ * 10;
   }

   public int func_77317_b(int p_77317_1_) {
      return this.func_77321_a(p_77317_1_) + 15;
   }

   public int func_77325_b() {
      return 3;
   }

   public boolean func_77326_a(Enchantment p_77326_1_) {
      return super.func_77326_a(p_77326_1_) && p_77326_1_ != Enchantments.field_185301_j;
   }
}
