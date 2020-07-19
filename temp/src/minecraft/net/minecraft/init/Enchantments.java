package net.minecraft.init;

import javax.annotation.Nullable;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.util.ResourceLocation;

public class Enchantments {
   public static final Enchantment field_180310_c = func_185295_a("protection");
   public static final Enchantment field_77329_d = func_185295_a("fire_protection");
   public static final Enchantment field_180309_e = func_185295_a("feather_falling");
   public static final Enchantment field_185297_d = func_185295_a("blast_protection");
   public static final Enchantment field_180308_g = func_185295_a("projectile_protection");
   public static final Enchantment field_185298_f = func_185295_a("respiration");
   public static final Enchantment field_185299_g = func_185295_a("aqua_affinity");
   public static final Enchantment field_92091_k = func_185295_a("thorns");
   public static final Enchantment field_185300_i = func_185295_a("depth_strider");
   public static final Enchantment field_185301_j = func_185295_a("frost_walker");
   public static final Enchantment field_190941_k = func_185295_a("binding_curse");
   public static final Enchantment field_185302_k = func_185295_a("sharpness");
   public static final Enchantment field_185303_l = func_185295_a("smite");
   public static final Enchantment field_180312_n = func_185295_a("bane_of_arthropods");
   public static final Enchantment field_180313_o = func_185295_a("knockback");
   public static final Enchantment field_77334_n = func_185295_a("fire_aspect");
   public static final Enchantment field_185304_p = func_185295_a("looting");
   public static final Enchantment field_191530_r = func_185295_a("sweeping");
   public static final Enchantment field_185305_q = func_185295_a("efficiency");
   public static final Enchantment field_185306_r = func_185295_a("silk_touch");
   public static final Enchantment field_185307_s = func_185295_a("unbreaking");
   public static final Enchantment field_185308_t = func_185295_a("fortune");
   public static final Enchantment field_185309_u = func_185295_a("power");
   public static final Enchantment field_185310_v = func_185295_a("punch");
   public static final Enchantment field_185311_w = func_185295_a("flame");
   public static final Enchantment field_185312_x = func_185295_a("infinity");
   public static final Enchantment field_151370_z = func_185295_a("luck_of_the_sea");
   public static final Enchantment field_151369_A = func_185295_a("lure");
   public static final Enchantment field_185296_A = func_185295_a("mending");
   public static final Enchantment field_190940_C = func_185295_a("vanishing_curse");

   @Nullable
   private static Enchantment func_185295_a(String p_185295_0_) {
      Enchantment enchantment = Enchantment.field_185264_b.func_82594_a(new ResourceLocation(p_185295_0_));
      if (enchantment == null) {
         throw new IllegalStateException("Invalid Enchantment requested: " + p_185295_0_);
      } else {
         return enchantment;
      }
   }

   static {
      if (!Bootstrap.func_179869_a()) {
         throw new RuntimeException("Accessed Enchantments before Bootstrap!");
      }
   }
}
