package net.minecraft.util.datafix.fixes;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.datafix.IFixableData;

public class PotionItems implements IFixableData {
   private static final String[] field_188223_a = new String[128];

   public int func_188216_a() {
      return 102;
   }

   public NBTTagCompound func_188217_a(NBTTagCompound p_188217_1_) {
      if ("minecraft:potion".equals(p_188217_1_.func_74779_i("id"))) {
         NBTTagCompound nbttagcompound = p_188217_1_.func_74775_l("tag");
         short short1 = p_188217_1_.func_74765_d("Damage");
         if (!nbttagcompound.func_150297_b("Potion", 8)) {
            String s = field_188223_a[short1 & 127];
            nbttagcompound.func_74778_a("Potion", s == null ? "minecraft:water" : s);
            p_188217_1_.func_74782_a("tag", nbttagcompound);
            if ((short1 & 16384) == 16384) {
               p_188217_1_.func_74778_a("id", "minecraft:splash_potion");
            }
         }

         if (short1 != 0) {
            p_188217_1_.func_74777_a("Damage", (short)0);
         }
      }

      return p_188217_1_;
   }

   static {
      field_188223_a[0] = "minecraft:water";
      field_188223_a[1] = "minecraft:regeneration";
      field_188223_a[2] = "minecraft:swiftness";
      field_188223_a[3] = "minecraft:fire_resistance";
      field_188223_a[4] = "minecraft:poison";
      field_188223_a[5] = "minecraft:healing";
      field_188223_a[6] = "minecraft:night_vision";
      field_188223_a[7] = null;
      field_188223_a[8] = "minecraft:weakness";
      field_188223_a[9] = "minecraft:strength";
      field_188223_a[10] = "minecraft:slowness";
      field_188223_a[11] = "minecraft:leaping";
      field_188223_a[12] = "minecraft:harming";
      field_188223_a[13] = "minecraft:water_breathing";
      field_188223_a[14] = "minecraft:invisibility";
      field_188223_a[15] = null;
      field_188223_a[16] = "minecraft:awkward";
      field_188223_a[17] = "minecraft:regeneration";
      field_188223_a[18] = "minecraft:swiftness";
      field_188223_a[19] = "minecraft:fire_resistance";
      field_188223_a[20] = "minecraft:poison";
      field_188223_a[21] = "minecraft:healing";
      field_188223_a[22] = "minecraft:night_vision";
      field_188223_a[23] = null;
      field_188223_a[24] = "minecraft:weakness";
      field_188223_a[25] = "minecraft:strength";
      field_188223_a[26] = "minecraft:slowness";
      field_188223_a[27] = "minecraft:leaping";
      field_188223_a[28] = "minecraft:harming";
      field_188223_a[29] = "minecraft:water_breathing";
      field_188223_a[30] = "minecraft:invisibility";
      field_188223_a[31] = null;
      field_188223_a[32] = "minecraft:thick";
      field_188223_a[33] = "minecraft:strong_regeneration";
      field_188223_a[34] = "minecraft:strong_swiftness";
      field_188223_a[35] = "minecraft:fire_resistance";
      field_188223_a[36] = "minecraft:strong_poison";
      field_188223_a[37] = "minecraft:strong_healing";
      field_188223_a[38] = "minecraft:night_vision";
      field_188223_a[39] = null;
      field_188223_a[40] = "minecraft:weakness";
      field_188223_a[41] = "minecraft:strong_strength";
      field_188223_a[42] = "minecraft:slowness";
      field_188223_a[43] = "minecraft:strong_leaping";
      field_188223_a[44] = "minecraft:strong_harming";
      field_188223_a[45] = "minecraft:water_breathing";
      field_188223_a[46] = "minecraft:invisibility";
      field_188223_a[47] = null;
      field_188223_a[48] = null;
      field_188223_a[49] = "minecraft:strong_regeneration";
      field_188223_a[50] = "minecraft:strong_swiftness";
      field_188223_a[51] = "minecraft:fire_resistance";
      field_188223_a[52] = "minecraft:strong_poison";
      field_188223_a[53] = "minecraft:strong_healing";
      field_188223_a[54] = "minecraft:night_vision";
      field_188223_a[55] = null;
      field_188223_a[56] = "minecraft:weakness";
      field_188223_a[57] = "minecraft:strong_strength";
      field_188223_a[58] = "minecraft:slowness";
      field_188223_a[59] = "minecraft:strong_leaping";
      field_188223_a[60] = "minecraft:strong_harming";
      field_188223_a[61] = "minecraft:water_breathing";
      field_188223_a[62] = "minecraft:invisibility";
      field_188223_a[63] = null;
      field_188223_a[64] = "minecraft:mundane";
      field_188223_a[65] = "minecraft:long_regeneration";
      field_188223_a[66] = "minecraft:long_swiftness";
      field_188223_a[67] = "minecraft:long_fire_resistance";
      field_188223_a[68] = "minecraft:long_poison";
      field_188223_a[69] = "minecraft:healing";
      field_188223_a[70] = "minecraft:long_night_vision";
      field_188223_a[71] = null;
      field_188223_a[72] = "minecraft:long_weakness";
      field_188223_a[73] = "minecraft:long_strength";
      field_188223_a[74] = "minecraft:long_slowness";
      field_188223_a[75] = "minecraft:long_leaping";
      field_188223_a[76] = "minecraft:harming";
      field_188223_a[77] = "minecraft:long_water_breathing";
      field_188223_a[78] = "minecraft:long_invisibility";
      field_188223_a[79] = null;
      field_188223_a[80] = "minecraft:awkward";
      field_188223_a[81] = "minecraft:long_regeneration";
      field_188223_a[82] = "minecraft:long_swiftness";
      field_188223_a[83] = "minecraft:long_fire_resistance";
      field_188223_a[84] = "minecraft:long_poison";
      field_188223_a[85] = "minecraft:healing";
      field_188223_a[86] = "minecraft:long_night_vision";
      field_188223_a[87] = null;
      field_188223_a[88] = "minecraft:long_weakness";
      field_188223_a[89] = "minecraft:long_strength";
      field_188223_a[90] = "minecraft:long_slowness";
      field_188223_a[91] = "minecraft:long_leaping";
      field_188223_a[92] = "minecraft:harming";
      field_188223_a[93] = "minecraft:long_water_breathing";
      field_188223_a[94] = "minecraft:long_invisibility";
      field_188223_a[95] = null;
      field_188223_a[96] = "minecraft:thick";
      field_188223_a[97] = "minecraft:regeneration";
      field_188223_a[98] = "minecraft:swiftness";
      field_188223_a[99] = "minecraft:long_fire_resistance";
      field_188223_a[100] = "minecraft:poison";
      field_188223_a[101] = "minecraft:strong_healing";
      field_188223_a[102] = "minecraft:long_night_vision";
      field_188223_a[103] = null;
      field_188223_a[104] = "minecraft:long_weakness";
      field_188223_a[105] = "minecraft:strength";
      field_188223_a[106] = "minecraft:long_slowness";
      field_188223_a[107] = "minecraft:leaping";
      field_188223_a[108] = "minecraft:strong_harming";
      field_188223_a[109] = "minecraft:long_water_breathing";
      field_188223_a[110] = "minecraft:long_invisibility";
      field_188223_a[111] = null;
      field_188223_a[112] = null;
      field_188223_a[113] = "minecraft:regeneration";
      field_188223_a[114] = "minecraft:swiftness";
      field_188223_a[115] = "minecraft:long_fire_resistance";
      field_188223_a[116] = "minecraft:poison";
      field_188223_a[117] = "minecraft:strong_healing";
      field_188223_a[118] = "minecraft:long_night_vision";
      field_188223_a[119] = null;
      field_188223_a[120] = "minecraft:long_weakness";
      field_188223_a[121] = "minecraft:strength";
      field_188223_a[122] = "minecraft:long_slowness";
      field_188223_a[123] = "minecraft:leaping";
      field_188223_a[124] = "minecraft:strong_harming";
      field_188223_a[125] = "minecraft:long_water_breathing";
      field_188223_a[126] = "minecraft:long_invisibility";
      field_188223_a[127] = null;
   }
}
