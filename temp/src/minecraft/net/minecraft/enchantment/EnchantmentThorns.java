package net.minecraft.enchantment;

import java.util.Random;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Enchantments;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;

public class EnchantmentThorns extends Enchantment {
   public EnchantmentThorns(Enchantment.Rarity p_i46722_1_, EntityEquipmentSlot... p_i46722_2_) {
      super(p_i46722_1_, EnumEnchantmentType.ARMOR_CHEST, p_i46722_2_);
      this.func_77322_b("thorns");
   }

   public int func_77321_a(int p_77321_1_) {
      return 10 + 20 * (p_77321_1_ - 1);
   }

   public int func_77317_b(int p_77317_1_) {
      return super.func_77321_a(p_77317_1_) + 50;
   }

   public int func_77325_b() {
      return 3;
   }

   public boolean func_92089_a(ItemStack p_92089_1_) {
      return p_92089_1_.func_77973_b() instanceof ItemArmor ? true : super.func_92089_a(p_92089_1_);
   }

   public void func_151367_b(EntityLivingBase p_151367_1_, Entity p_151367_2_, int p_151367_3_) {
      Random random = p_151367_1_.func_70681_au();
      ItemStack itemstack = EnchantmentHelper.func_92099_a(Enchantments.field_92091_k, p_151367_1_);
      if (func_92094_a(p_151367_3_, random)) {
         if (p_151367_2_ != null) {
            p_151367_2_.func_70097_a(DamageSource.func_92087_a(p_151367_1_), (float)func_92095_b(p_151367_3_, random));
         }

         if (!itemstack.func_190926_b()) {
            itemstack.func_77972_a(3, p_151367_1_);
         }
      } else if (!itemstack.func_190926_b()) {
         itemstack.func_77972_a(1, p_151367_1_);
      }

   }

   public static boolean func_92094_a(int p_92094_0_, Random p_92094_1_) {
      if (p_92094_0_ <= 0) {
         return false;
      } else {
         return p_92094_1_.nextFloat() < 0.15F * (float)p_92094_0_;
      }
   }

   public static int func_92095_b(int p_92095_0_, Random p_92095_1_) {
      return p_92095_0_ > 10 ? p_92095_0_ - 10 : 1 + p_92095_1_.nextInt(4);
   }
}
