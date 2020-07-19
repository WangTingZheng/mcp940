package net.minecraft.init;

import javax.annotation.Nullable;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;

public class MobEffects {
   public static final Potion field_76424_c;
   public static final Potion field_76421_d;
   public static final Potion field_76422_e;
   public static final Potion field_76419_f;
   public static final Potion field_76420_g;
   public static final Potion field_76432_h;
   public static final Potion field_76433_i;
   public static final Potion field_76430_j;
   public static final Potion field_76431_k;
   public static final Potion field_76428_l;
   public static final Potion field_76429_m;
   public static final Potion field_76426_n;
   public static final Potion field_76427_o;
   public static final Potion field_76441_p;
   public static final Potion field_76440_q;
   public static final Potion field_76439_r;
   public static final Potion field_76438_s;
   public static final Potion field_76437_t;
   public static final Potion field_76436_u;
   public static final Potion field_82731_v;
   public static final Potion field_180152_w;
   public static final Potion field_76444_x;
   public static final Potion field_76443_y;
   public static final Potion field_188423_x;
   public static final Potion field_188424_y;
   public static final Potion field_188425_z;
   public static final Potion field_189112_A;

   @Nullable
   private static Potion func_188422_a(String p_188422_0_) {
      Potion potion = Potion.field_188414_b.func_82594_a(new ResourceLocation(p_188422_0_));
      if (potion == null) {
         throw new IllegalStateException("Invalid MobEffect requested: " + p_188422_0_);
      } else {
         return potion;
      }
   }

   static {
      if (!Bootstrap.func_179869_a()) {
         throw new RuntimeException("Accessed MobEffects before Bootstrap!");
      } else {
         field_76424_c = func_188422_a("speed");
         field_76421_d = func_188422_a("slowness");
         field_76422_e = func_188422_a("haste");
         field_76419_f = func_188422_a("mining_fatigue");
         field_76420_g = func_188422_a("strength");
         field_76432_h = func_188422_a("instant_health");
         field_76433_i = func_188422_a("instant_damage");
         field_76430_j = func_188422_a("jump_boost");
         field_76431_k = func_188422_a("nausea");
         field_76428_l = func_188422_a("regeneration");
         field_76429_m = func_188422_a("resistance");
         field_76426_n = func_188422_a("fire_resistance");
         field_76427_o = func_188422_a("water_breathing");
         field_76441_p = func_188422_a("invisibility");
         field_76440_q = func_188422_a("blindness");
         field_76439_r = func_188422_a("night_vision");
         field_76438_s = func_188422_a("hunger");
         field_76437_t = func_188422_a("weakness");
         field_76436_u = func_188422_a("poison");
         field_82731_v = func_188422_a("wither");
         field_180152_w = func_188422_a("health_boost");
         field_76444_x = func_188422_a("absorption");
         field_76443_y = func_188422_a("saturation");
         field_188423_x = func_188422_a("glowing");
         field_188424_y = func_188422_a("levitation");
         field_188425_z = func_188422_a("luck");
         field_189112_A = func_188422_a("unluck");
      }
   }
}
