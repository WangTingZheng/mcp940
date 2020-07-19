package net.minecraft.enchantment;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;

public class EnchantmentDamage extends Enchantment {
   private static final String[] field_77359_A = new String[]{"all", "undead", "arthropods"};
   private static final int[] field_77360_B = new int[]{1, 5, 5};
   private static final int[] field_77362_C = new int[]{11, 8, 8};
   private static final int[] field_77358_D = new int[]{20, 20, 20};
   public final int field_77361_a;

   public EnchantmentDamage(Enchantment.Rarity p_i46734_1_, int p_i46734_2_, EntityEquipmentSlot... p_i46734_3_) {
      super(p_i46734_1_, EnumEnchantmentType.WEAPON, p_i46734_3_);
      this.field_77361_a = p_i46734_2_;
   }

   public int func_77321_a(int p_77321_1_) {
      return field_77360_B[this.field_77361_a] + (p_77321_1_ - 1) * field_77362_C[this.field_77361_a];
   }

   public int func_77317_b(int p_77317_1_) {
      return this.func_77321_a(p_77317_1_) + field_77358_D[this.field_77361_a];
   }

   public int func_77325_b() {
      return 5;
   }

   public float func_152376_a(int p_152376_1_, EnumCreatureAttribute p_152376_2_) {
      if (this.field_77361_a == 0) {
         return 1.0F + (float)Math.max(0, p_152376_1_ - 1) * 0.5F;
      } else if (this.field_77361_a == 1 && p_152376_2_ == EnumCreatureAttribute.UNDEAD) {
         return (float)p_152376_1_ * 2.5F;
      } else {
         return this.field_77361_a == 2 && p_152376_2_ == EnumCreatureAttribute.ARTHROPOD ? (float)p_152376_1_ * 2.5F : 0.0F;
      }
   }

   public String func_77320_a() {
      return "enchantment.damage." + field_77359_A[this.field_77361_a];
   }

   public boolean func_77326_a(Enchantment p_77326_1_) {
      return !(p_77326_1_ instanceof EnchantmentDamage);
   }

   public boolean func_92089_a(ItemStack p_92089_1_) {
      return p_92089_1_.func_77973_b() instanceof ItemAxe ? true : super.func_92089_a(p_92089_1_);
   }

   public void func_151368_a(EntityLivingBase p_151368_1_, Entity p_151368_2_, int p_151368_3_) {
      if (p_151368_2_ instanceof EntityLivingBase) {
         EntityLivingBase entitylivingbase = (EntityLivingBase)p_151368_2_;
         if (this.field_77361_a == 2 && entitylivingbase.func_70668_bt() == EnumCreatureAttribute.ARTHROPOD) {
            int i = 20 + p_151368_1_.func_70681_au().nextInt(10 * p_151368_3_);
            entitylivingbase.func_70690_d(new PotionEffect(MobEffects.field_76421_d, i, 3));
         }
      }

   }
}
