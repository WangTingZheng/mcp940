package net.minecraft.init;

import com.google.common.collect.Sets;
import java.util.Set;
import net.minecraft.potion.PotionType;
import net.minecraft.util.ResourceLocation;

public class PotionTypes {
   private static final Set<PotionType> field_185228_K;
   public static final PotionType field_185229_a;
   public static final PotionType field_185230_b;
   public static final PotionType field_185231_c;
   public static final PotionType field_185232_d;
   public static final PotionType field_185233_e;
   public static final PotionType field_185234_f;
   public static final PotionType field_185235_g;
   public static final PotionType field_185236_h;
   public static final PotionType field_185237_i;
   public static final PotionType field_185238_j;
   public static final PotionType field_185239_k;
   public static final PotionType field_185240_l;
   public static final PotionType field_185241_m;
   public static final PotionType field_185242_n;
   public static final PotionType field_185243_o;
   public static final PotionType field_185244_p;
   public static final PotionType field_185245_q;
   public static final PotionType field_185246_r;
   public static final PotionType field_185247_s;
   public static final PotionType field_185248_t;
   public static final PotionType field_185249_u;
   public static final PotionType field_185250_v;
   public static final PotionType field_185251_w;
   public static final PotionType field_185252_x;
   public static final PotionType field_185253_y;
   public static final PotionType field_185254_z;
   public static final PotionType field_185218_A;
   public static final PotionType field_185219_B;
   public static final PotionType field_185220_C;
   public static final PotionType field_185221_D;
   public static final PotionType field_185222_E;
   public static final PotionType field_185223_F;
   public static final PotionType field_185224_G;
   public static final PotionType field_185225_H;
   public static final PotionType field_185226_I;
   public static final PotionType field_185227_J;

   private static PotionType func_185217_a(String p_185217_0_) {
      PotionType potiontype = PotionType.field_185176_a.func_82594_a(new ResourceLocation(p_185217_0_));
      if (!field_185228_K.add(potiontype)) {
         throw new IllegalStateException("Invalid Potion requested: " + p_185217_0_);
      } else {
         return potiontype;
      }
   }

   static {
      if (!Bootstrap.func_179869_a()) {
         throw new RuntimeException("Accessed Potions before Bootstrap!");
      } else {
         field_185228_K = Sets.<PotionType>newHashSet();
         field_185229_a = func_185217_a("empty");
         field_185230_b = func_185217_a("water");
         field_185231_c = func_185217_a("mundane");
         field_185232_d = func_185217_a("thick");
         field_185233_e = func_185217_a("awkward");
         field_185234_f = func_185217_a("night_vision");
         field_185235_g = func_185217_a("long_night_vision");
         field_185236_h = func_185217_a("invisibility");
         field_185237_i = func_185217_a("long_invisibility");
         field_185238_j = func_185217_a("leaping");
         field_185239_k = func_185217_a("long_leaping");
         field_185240_l = func_185217_a("strong_leaping");
         field_185241_m = func_185217_a("fire_resistance");
         field_185242_n = func_185217_a("long_fire_resistance");
         field_185243_o = func_185217_a("swiftness");
         field_185244_p = func_185217_a("long_swiftness");
         field_185245_q = func_185217_a("strong_swiftness");
         field_185246_r = func_185217_a("slowness");
         field_185247_s = func_185217_a("long_slowness");
         field_185248_t = func_185217_a("water_breathing");
         field_185249_u = func_185217_a("long_water_breathing");
         field_185250_v = func_185217_a("healing");
         field_185251_w = func_185217_a("strong_healing");
         field_185252_x = func_185217_a("harming");
         field_185253_y = func_185217_a("strong_harming");
         field_185254_z = func_185217_a("poison");
         field_185218_A = func_185217_a("long_poison");
         field_185219_B = func_185217_a("strong_poison");
         field_185220_C = func_185217_a("regeneration");
         field_185221_D = func_185217_a("long_regeneration");
         field_185222_E = func_185217_a("strong_regeneration");
         field_185223_F = func_185217_a("strength");
         field_185224_G = func_185217_a("long_strength");
         field_185225_H = func_185217_a("strong_strength");
         field_185226_I = func_185217_a("weakness");
         field_185227_J = func_185217_a("long_weakness");
         field_185228_K.clear();
      }
   }
}
