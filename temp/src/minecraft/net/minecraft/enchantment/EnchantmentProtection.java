package net.minecraft.enchantment;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Enchantments;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;

public class EnchantmentProtection extends Enchantment {
   public final EnchantmentProtection.Type field_77356_a;

   public EnchantmentProtection(Enchantment.Rarity p_i46723_1_, EnchantmentProtection.Type p_i46723_2_, EntityEquipmentSlot... p_i46723_3_) {
      super(p_i46723_1_, EnumEnchantmentType.ARMOR, p_i46723_3_);
      this.field_77356_a = p_i46723_2_;
      if (p_i46723_2_ == EnchantmentProtection.Type.FALL) {
         this.field_77351_y = EnumEnchantmentType.ARMOR_FEET;
      }

   }

   public int func_77321_a(int p_77321_1_) {
      return this.field_77356_a.func_185316_b() + (p_77321_1_ - 1) * this.field_77356_a.func_185315_c();
   }

   public int func_77317_b(int p_77317_1_) {
      return this.func_77321_a(p_77317_1_) + this.field_77356_a.func_185315_c();
   }

   public int func_77325_b() {
      return 4;
   }

   public int func_77318_a(int p_77318_1_, DamageSource p_77318_2_) {
      if (p_77318_2_.func_76357_e()) {
         return 0;
      } else if (this.field_77356_a == EnchantmentProtection.Type.ALL) {
         return p_77318_1_;
      } else if (this.field_77356_a == EnchantmentProtection.Type.FIRE && p_77318_2_.func_76347_k()) {
         return p_77318_1_ * 2;
      } else if (this.field_77356_a == EnchantmentProtection.Type.FALL && p_77318_2_ == DamageSource.field_76379_h) {
         return p_77318_1_ * 3;
      } else if (this.field_77356_a == EnchantmentProtection.Type.EXPLOSION && p_77318_2_.func_94541_c()) {
         return p_77318_1_ * 2;
      } else {
         return this.field_77356_a == EnchantmentProtection.Type.PROJECTILE && p_77318_2_.func_76352_a() ? p_77318_1_ * 2 : 0;
      }
   }

   public String func_77320_a() {
      return "enchantment.protect." + this.field_77356_a.func_185314_a();
   }

   public boolean func_77326_a(Enchantment p_77326_1_) {
      if (p_77326_1_ instanceof EnchantmentProtection) {
         EnchantmentProtection enchantmentprotection = (EnchantmentProtection)p_77326_1_;
         if (this.field_77356_a == enchantmentprotection.field_77356_a) {
            return false;
         } else {
            return this.field_77356_a == EnchantmentProtection.Type.FALL || enchantmentprotection.field_77356_a == EnchantmentProtection.Type.FALL;
         }
      } else {
         return super.func_77326_a(p_77326_1_);
      }
   }

   public static int func_92093_a(EntityLivingBase p_92093_0_, int p_92093_1_) {
      int i = EnchantmentHelper.func_185284_a(Enchantments.field_77329_d, p_92093_0_);
      if (i > 0) {
         p_92093_1_ -= MathHelper.func_76141_d((float)p_92093_1_ * (float)i * 0.15F);
      }

      return p_92093_1_;
   }

   public static double func_92092_a(EntityLivingBase p_92092_0_, double p_92092_1_) {
      int i = EnchantmentHelper.func_185284_a(Enchantments.field_185297_d, p_92092_0_);
      if (i > 0) {
         p_92092_1_ -= (double)MathHelper.func_76128_c(p_92092_1_ * (double)((float)i * 0.15F));
      }

      return p_92092_1_;
   }

   public static enum Type {
      ALL("all", 1, 11, 20),
      FIRE("fire", 10, 8, 12),
      FALL("fall", 5, 6, 10),
      EXPLOSION("explosion", 5, 8, 12),
      PROJECTILE("projectile", 3, 6, 15);

      private final String field_185322_f;
      private final int field_185323_g;
      private final int field_185324_h;
      private final int field_185325_i;

      private Type(String p_i47051_3_, int p_i47051_4_, int p_i47051_5_, int p_i47051_6_) {
         this.field_185322_f = p_i47051_3_;
         this.field_185323_g = p_i47051_4_;
         this.field_185324_h = p_i47051_5_;
         this.field_185325_i = p_i47051_6_;
      }

      public String func_185314_a() {
         return this.field_185322_f;
      }

      public int func_185316_b() {
         return this.field_185323_g;
      }

      public int func_185315_c() {
         return this.field_185324_h;
      }
   }
}
