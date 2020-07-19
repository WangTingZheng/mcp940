package net.minecraft.init;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;

public abstract class Biomes {
   public static final Biome field_76771_b;
   public static final Biome field_180279_ad;
   public static final Biome field_76772_c;
   public static final Biome field_76769_d;
   public static final Biome field_76770_e;
   public static final Biome field_76767_f;
   public static final Biome field_76768_g;
   public static final Biome field_76780_h;
   public static final Biome field_76781_i;
   public static final Biome field_76778_j;
   public static final Biome field_76779_k;
   public static final Biome field_76776_l;
   public static final Biome field_76777_m;
   public static final Biome field_76774_n;
   public static final Biome field_76775_o;
   public static final Biome field_76789_p;
   public static final Biome field_76788_q;
   public static final Biome field_76787_r;
   public static final Biome field_76786_s;
   public static final Biome field_76785_t;
   public static final Biome field_76784_u;
   public static final Biome field_76783_v;
   public static final Biome field_76782_w;
   public static final Biome field_76792_x;
   public static final Biome field_150574_L;
   public static final Biome field_150575_M;
   public static final Biome field_150576_N;
   public static final Biome field_150577_O;
   public static final Biome field_150583_P;
   public static final Biome field_150582_Q;
   public static final Biome field_150585_R;
   public static final Biome field_150584_S;
   public static final Biome field_150579_T;
   public static final Biome field_150578_U;
   public static final Biome field_150581_V;
   public static final Biome field_150580_W;
   public static final Biome field_150588_X;
   public static final Biome field_150587_Y;
   public static final Biome field_150589_Z;
   public static final Biome field_150607_aa;
   public static final Biome field_150608_ab;
   public static final Biome field_185440_P;
   public static final Biome field_185441_Q;
   public static final Biome field_185442_R;
   public static final Biome field_185443_S;
   public static final Biome field_185444_T;
   public static final Biome field_150590_f;
   public static final Biome field_150599_m;
   public static final Biome field_185445_W;
   public static final Biome field_185446_X;
   public static final Biome field_185447_Y;
   public static final Biome field_185448_Z;
   public static final Biome field_185429_aa;
   public static final Biome field_185430_ab;
   public static final Biome field_185431_ac;
   public static final Biome field_185432_ad;
   public static final Biome field_185433_ae;
   public static final Biome field_185434_af;
   public static final Biome field_185435_ag;
   public static final Biome field_185436_ah;
   public static final Biome field_185437_ai;
   public static final Biome field_185438_aj;
   public static final Biome field_185439_ak;

   private static Biome func_185428_a(String p_185428_0_) {
      Biome biome = Biome.field_185377_q.func_82594_a(new ResourceLocation(p_185428_0_));
      if (biome == null) {
         throw new IllegalStateException("Invalid Biome requested: " + p_185428_0_);
      } else {
         return biome;
      }
   }

   static {
      if (!Bootstrap.func_179869_a()) {
         throw new RuntimeException("Accessed Biomes before Bootstrap!");
      } else {
         field_76771_b = func_185428_a("ocean");
         field_180279_ad = field_76771_b;
         field_76772_c = func_185428_a("plains");
         field_76769_d = func_185428_a("desert");
         field_76770_e = func_185428_a("extreme_hills");
         field_76767_f = func_185428_a("forest");
         field_76768_g = func_185428_a("taiga");
         field_76780_h = func_185428_a("swampland");
         field_76781_i = func_185428_a("river");
         field_76778_j = func_185428_a("hell");
         field_76779_k = func_185428_a("sky");
         field_76776_l = func_185428_a("frozen_ocean");
         field_76777_m = func_185428_a("frozen_river");
         field_76774_n = func_185428_a("ice_flats");
         field_76775_o = func_185428_a("ice_mountains");
         field_76789_p = func_185428_a("mushroom_island");
         field_76788_q = func_185428_a("mushroom_island_shore");
         field_76787_r = func_185428_a("beaches");
         field_76786_s = func_185428_a("desert_hills");
         field_76785_t = func_185428_a("forest_hills");
         field_76784_u = func_185428_a("taiga_hills");
         field_76783_v = func_185428_a("smaller_extreme_hills");
         field_76782_w = func_185428_a("jungle");
         field_76792_x = func_185428_a("jungle_hills");
         field_150574_L = func_185428_a("jungle_edge");
         field_150575_M = func_185428_a("deep_ocean");
         field_150576_N = func_185428_a("stone_beach");
         field_150577_O = func_185428_a("cold_beach");
         field_150583_P = func_185428_a("birch_forest");
         field_150582_Q = func_185428_a("birch_forest_hills");
         field_150585_R = func_185428_a("roofed_forest");
         field_150584_S = func_185428_a("taiga_cold");
         field_150579_T = func_185428_a("taiga_cold_hills");
         field_150578_U = func_185428_a("redwood_taiga");
         field_150581_V = func_185428_a("redwood_taiga_hills");
         field_150580_W = func_185428_a("extreme_hills_with_trees");
         field_150588_X = func_185428_a("savanna");
         field_150587_Y = func_185428_a("savanna_rock");
         field_150589_Z = func_185428_a("mesa");
         field_150607_aa = func_185428_a("mesa_rock");
         field_150608_ab = func_185428_a("mesa_clear_rock");
         field_185440_P = func_185428_a("void");
         field_185441_Q = func_185428_a("mutated_plains");
         field_185442_R = func_185428_a("mutated_desert");
         field_185443_S = func_185428_a("mutated_extreme_hills");
         field_185444_T = func_185428_a("mutated_forest");
         field_150590_f = func_185428_a("mutated_taiga");
         field_150599_m = func_185428_a("mutated_swampland");
         field_185445_W = func_185428_a("mutated_ice_flats");
         field_185446_X = func_185428_a("mutated_jungle");
         field_185447_Y = func_185428_a("mutated_jungle_edge");
         field_185448_Z = func_185428_a("mutated_birch_forest");
         field_185429_aa = func_185428_a("mutated_birch_forest_hills");
         field_185430_ab = func_185428_a("mutated_roofed_forest");
         field_185431_ac = func_185428_a("mutated_taiga_cold");
         field_185432_ad = func_185428_a("mutated_redwood_taiga");
         field_185433_ae = func_185428_a("mutated_redwood_taiga_hills");
         field_185434_af = func_185428_a("mutated_extreme_hills_with_trees");
         field_185435_ag = func_185428_a("mutated_savanna");
         field_185436_ah = func_185428_a("mutated_savanna_rock");
         field_185437_ai = func_185428_a("mutated_mesa");
         field_185438_aj = func_185428_a("mutated_mesa_rock");
         field_185439_ak = func_185428_a("mutated_mesa_clear_rock");
      }
   }
}
