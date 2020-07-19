package net.minecraft.enchantment;

import net.minecraft.inventory.EntityEquipmentSlot;

public class EnchantmentSweepingEdge extends Enchantment {
   public EnchantmentSweepingEdge(Enchantment.Rarity p_i47366_1_, EntityEquipmentSlot... p_i47366_2_) {
      super(p_i47366_1_, EnumEnchantmentType.WEAPON, p_i47366_2_);
   }

   public int func_77321_a(int p_77321_1_) {
      return 5 + (p_77321_1_ - 1) * 9;
   }

   public int func_77317_b(int p_77317_1_) {
      return this.func_77321_a(p_77317_1_) + 15;
   }

   public int func_77325_b() {
      return 3;
   }

   public static float func_191526_e(int p_191526_0_) {
      return 1.0F - 1.0F / (float)(p_191526_0_ + 1);
   }

   public String func_77320_a() {
      return "enchantment.sweeping";
   }
}
