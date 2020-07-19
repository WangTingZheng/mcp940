package net.minecraft.enchantment;

import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

public class EnchantmentDigging extends Enchantment {
   protected EnchantmentDigging(Enchantment.Rarity p_i46732_1_, EntityEquipmentSlot... p_i46732_2_) {
      super(p_i46732_1_, EnumEnchantmentType.DIGGER, p_i46732_2_);
      this.func_77322_b("digging");
   }

   public int func_77321_a(int p_77321_1_) {
      return 1 + 10 * (p_77321_1_ - 1);
   }

   public int func_77317_b(int p_77317_1_) {
      return super.func_77321_a(p_77317_1_) + 50;
   }

   public int func_77325_b() {
      return 5;
   }

   public boolean func_92089_a(ItemStack p_92089_1_) {
      return p_92089_1_.func_77973_b() == Items.field_151097_aZ ? true : super.func_92089_a(p_92089_1_);
   }
}
