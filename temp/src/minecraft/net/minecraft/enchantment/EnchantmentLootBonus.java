package net.minecraft.enchantment;

import net.minecraft.init.Enchantments;
import net.minecraft.inventory.EntityEquipmentSlot;

public class EnchantmentLootBonus extends Enchantment {
   protected EnchantmentLootBonus(Enchantment.Rarity p_i46726_1_, EnumEnchantmentType p_i46726_2_, EntityEquipmentSlot... p_i46726_3_) {
      super(p_i46726_1_, p_i46726_2_, p_i46726_3_);
      if (p_i46726_2_ == EnumEnchantmentType.DIGGER) {
         this.func_77322_b("lootBonusDigger");
      } else if (p_i46726_2_ == EnumEnchantmentType.FISHING_ROD) {
         this.func_77322_b("lootBonusFishing");
      } else {
         this.func_77322_b("lootBonus");
      }

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

   public boolean func_77326_a(Enchantment p_77326_1_) {
      return super.func_77326_a(p_77326_1_) && p_77326_1_ != Enchantments.field_185306_r;
   }
}
