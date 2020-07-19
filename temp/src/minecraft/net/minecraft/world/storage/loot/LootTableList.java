package net.minecraft.world.storage.loot;

import com.google.common.collect.Sets;
import java.io.File;
import java.util.Collections;
import java.util.Set;
import net.minecraft.util.ResourceLocation;

public class LootTableList {
   private static final Set<ResourceLocation> field_186391_ap = Sets.<ResourceLocation>newHashSet();
   private static final Set<ResourceLocation> field_186392_aq = Collections.<ResourceLocation>unmodifiableSet(field_186391_ap);
   public static final ResourceLocation field_186419_a = func_186373_a("empty");
   public static final ResourceLocation field_186420_b = func_186373_a("chests/spawn_bonus_chest");
   public static final ResourceLocation field_186421_c = func_186373_a("chests/end_city_treasure");
   public static final ResourceLocation field_186422_d = func_186373_a("chests/simple_dungeon");
   public static final ResourceLocation field_186423_e = func_186373_a("chests/village_blacksmith");
   public static final ResourceLocation field_186424_f = func_186373_a("chests/abandoned_mineshaft");
   public static final ResourceLocation field_186425_g = func_186373_a("chests/nether_bridge");
   public static final ResourceLocation field_186426_h = func_186373_a("chests/stronghold_library");
   public static final ResourceLocation field_186427_i = func_186373_a("chests/stronghold_crossing");
   public static final ResourceLocation field_186428_j = func_186373_a("chests/stronghold_corridor");
   public static final ResourceLocation field_186429_k = func_186373_a("chests/desert_pyramid");
   public static final ResourceLocation field_186430_l = func_186373_a("chests/jungle_temple");
   public static final ResourceLocation field_189420_m = func_186373_a("chests/jungle_temple_dispenser");
   public static final ResourceLocation field_186431_m = func_186373_a("chests/igloo_chest");
   public static final ResourceLocation field_191192_o = func_186373_a("chests/woodland_mansion");
   public static final ResourceLocation field_186432_n = func_186373_a("entities/witch");
   public static final ResourceLocation field_186433_o = func_186373_a("entities/blaze");
   public static final ResourceLocation field_186434_p = func_186373_a("entities/creeper");
   public static final ResourceLocation field_186435_q = func_186373_a("entities/spider");
   public static final ResourceLocation field_186436_r = func_186373_a("entities/cave_spider");
   public static final ResourceLocation field_186437_s = func_186373_a("entities/giant");
   public static final ResourceLocation field_186438_t = func_186373_a("entities/silverfish");
   public static final ResourceLocation field_186439_u = func_186373_a("entities/enderman");
   public static final ResourceLocation field_186440_v = func_186373_a("entities/guardian");
   public static final ResourceLocation field_186441_w = func_186373_a("entities/elder_guardian");
   public static final ResourceLocation field_186442_x = func_186373_a("entities/shulker");
   public static final ResourceLocation field_186443_y = func_186373_a("entities/iron_golem");
   public static final ResourceLocation field_186444_z = func_186373_a("entities/snowman");
   public static final ResourceLocation field_186393_A = func_186373_a("entities/rabbit");
   public static final ResourceLocation field_186394_B = func_186373_a("entities/chicken");
   public static final ResourceLocation field_186395_C = func_186373_a("entities/pig");
   public static final ResourceLocation field_189969_E = func_186373_a("entities/polar_bear");
   public static final ResourceLocation field_186396_D = func_186373_a("entities/horse");
   public static final ResourceLocation field_191190_H = func_186373_a("entities/donkey");
   public static final ResourceLocation field_191191_I = func_186373_a("entities/mule");
   public static final ResourceLocation field_186397_E = func_186373_a("entities/zombie_horse");
   public static final ResourceLocation field_186398_F = func_186373_a("entities/skeleton_horse");
   public static final ResourceLocation field_186399_G = func_186373_a("entities/cow");
   public static final ResourceLocation field_186400_H = func_186373_a("entities/mushroom_cow");
   public static final ResourceLocation field_186401_I = func_186373_a("entities/wolf");
   public static final ResourceLocation field_186402_J = func_186373_a("entities/ocelot");
   public static final ResourceLocation field_186403_K = func_186373_a("entities/sheep");
   public static final ResourceLocation field_186404_L = func_186373_a("entities/sheep/white");
   public static final ResourceLocation field_186405_M = func_186373_a("entities/sheep/orange");
   public static final ResourceLocation field_186406_N = func_186373_a("entities/sheep/magenta");
   public static final ResourceLocation field_186407_O = func_186373_a("entities/sheep/light_blue");
   public static final ResourceLocation field_186408_P = func_186373_a("entities/sheep/yellow");
   public static final ResourceLocation field_186409_Q = func_186373_a("entities/sheep/lime");
   public static final ResourceLocation field_186410_R = func_186373_a("entities/sheep/pink");
   public static final ResourceLocation field_186411_S = func_186373_a("entities/sheep/gray");
   public static final ResourceLocation field_186412_T = func_186373_a("entities/sheep/silver");
   public static final ResourceLocation field_186413_U = func_186373_a("entities/sheep/cyan");
   public static final ResourceLocation field_186414_V = func_186373_a("entities/sheep/purple");
   public static final ResourceLocation field_186415_W = func_186373_a("entities/sheep/blue");
   public static final ResourceLocation field_186416_X = func_186373_a("entities/sheep/brown");
   public static final ResourceLocation field_186417_Y = func_186373_a("entities/sheep/green");
   public static final ResourceLocation field_186418_Z = func_186373_a("entities/sheep/red");
   public static final ResourceLocation field_186376_aa = func_186373_a("entities/sheep/black");
   public static final ResourceLocation field_186377_ab = func_186373_a("entities/bat");
   public static final ResourceLocation field_186378_ac = func_186373_a("entities/slime");
   public static final ResourceLocation field_186379_ad = func_186373_a("entities/magma_cube");
   public static final ResourceLocation field_186380_ae = func_186373_a("entities/ghast");
   public static final ResourceLocation field_186381_af = func_186373_a("entities/squid");
   public static final ResourceLocation field_186382_ag = func_186373_a("entities/endermite");
   public static final ResourceLocation field_186383_ah = func_186373_a("entities/zombie");
   public static final ResourceLocation field_186384_ai = func_186373_a("entities/zombie_pigman");
   public static final ResourceLocation field_186385_aj = func_186373_a("entities/skeleton");
   public static final ResourceLocation field_186386_ak = func_186373_a("entities/wither_skeleton");
   public static final ResourceLocation field_189968_an = func_186373_a("entities/stray");
   public static final ResourceLocation field_191182_ar = func_186373_a("entities/husk");
   public static final ResourceLocation field_191183_as = func_186373_a("entities/zombie_villager");
   public static final ResourceLocation field_191184_at = func_186373_a("entities/villager");
   public static final ResourceLocation field_191185_au = func_186373_a("entities/evocation_illager");
   public static final ResourceLocation field_191186_av = func_186373_a("entities/vindication_illager");
   public static final ResourceLocation field_191187_aw = func_186373_a("entities/llama");
   public static final ResourceLocation field_192561_ax = func_186373_a("entities/parrot");
   public static final ResourceLocation field_191188_ax = func_186373_a("entities/vex");
   public static final ResourceLocation field_191189_ay = func_186373_a("entities/ender_dragon");
   public static final ResourceLocation field_186387_al = func_186373_a("gameplay/fishing");
   public static final ResourceLocation field_186388_am = func_186373_a("gameplay/fishing/junk");
   public static final ResourceLocation field_186389_an = func_186373_a("gameplay/fishing/treasure");
   public static final ResourceLocation field_186390_ao = func_186373_a("gameplay/fishing/fish");

   private static ResourceLocation func_186373_a(String p_186373_0_) {
      return func_186375_a(new ResourceLocation("minecraft", p_186373_0_));
   }

   public static ResourceLocation func_186375_a(ResourceLocation p_186375_0_) {
      if (field_186391_ap.add(p_186375_0_)) {
         return p_186375_0_;
      } else {
         throw new IllegalArgumentException(p_186375_0_ + " is already a registered built-in loot table");
      }
   }

   public static Set<ResourceLocation> func_186374_a() {
      return field_186392_aq;
   }

   public static boolean func_193579_b() {
      LootTableManager loottablemanager = new LootTableManager((File)null);

      for(ResourceLocation resourcelocation : field_186392_aq) {
         if (loottablemanager.func_186521_a(resourcelocation) == LootTable.field_186464_a) {
            return false;
         }
      }

      return true;
   }
}
