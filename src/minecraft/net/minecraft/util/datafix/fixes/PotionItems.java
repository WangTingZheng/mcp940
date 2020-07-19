package net.minecraft.util.datafix.fixes;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.datafix.IFixableData;

public class PotionItems implements IFixableData
{
    private static final String[] POTION_IDS = new String[128];

    public int getFixVersion()
    {
        return 102;
    }

    public NBTTagCompound fixTagCompound(NBTTagCompound compound)
    {
        if ("minecraft:potion".equals(compound.getString("id")))
        {
            NBTTagCompound nbttagcompound = compound.getCompoundTag("tag");
            short short1 = compound.getShort("Damage");

            if (!nbttagcompound.hasKey("Potion", 8))
            {
                String s = POTION_IDS[short1 & 127];
                nbttagcompound.setString("Potion", s == null ? "minecraft:water" : s);
                compound.setTag("tag", nbttagcompound);

                if ((short1 & 16384) == 16384)
                {
                    compound.setString("id", "minecraft:splash_potion");
                }
            }

            if (short1 != 0)
            {
                compound.setShort("Damage", (short)0);
            }
        }

        return compound;
    }

    static
    {
        POTION_IDS[0] = "minecraft:water";
        POTION_IDS[1] = "minecraft:regeneration";
        POTION_IDS[2] = "minecraft:swiftness";
        POTION_IDS[3] = "minecraft:fire_resistance";
        POTION_IDS[4] = "minecraft:poison";
        POTION_IDS[5] = "minecraft:healing";
        POTION_IDS[6] = "minecraft:night_vision";
        POTION_IDS[7] = null;
        POTION_IDS[8] = "minecraft:weakness";
        POTION_IDS[9] = "minecraft:strength";
        POTION_IDS[10] = "minecraft:slowness";
        POTION_IDS[11] = "minecraft:leaping";
        POTION_IDS[12] = "minecraft:harming";
        POTION_IDS[13] = "minecraft:water_breathing";
        POTION_IDS[14] = "minecraft:invisibility";
        POTION_IDS[15] = null;
        POTION_IDS[16] = "minecraft:awkward";
        POTION_IDS[17] = "minecraft:regeneration";
        POTION_IDS[18] = "minecraft:swiftness";
        POTION_IDS[19] = "minecraft:fire_resistance";
        POTION_IDS[20] = "minecraft:poison";
        POTION_IDS[21] = "minecraft:healing";
        POTION_IDS[22] = "minecraft:night_vision";
        POTION_IDS[23] = null;
        POTION_IDS[24] = "minecraft:weakness";
        POTION_IDS[25] = "minecraft:strength";
        POTION_IDS[26] = "minecraft:slowness";
        POTION_IDS[27] = "minecraft:leaping";
        POTION_IDS[28] = "minecraft:harming";
        POTION_IDS[29] = "minecraft:water_breathing";
        POTION_IDS[30] = "minecraft:invisibility";
        POTION_IDS[31] = null;
        POTION_IDS[32] = "minecraft:thick";
        POTION_IDS[33] = "minecraft:strong_regeneration";
        POTION_IDS[34] = "minecraft:strong_swiftness";
        POTION_IDS[35] = "minecraft:fire_resistance";
        POTION_IDS[36] = "minecraft:strong_poison";
        POTION_IDS[37] = "minecraft:strong_healing";
        POTION_IDS[38] = "minecraft:night_vision";
        POTION_IDS[39] = null;
        POTION_IDS[40] = "minecraft:weakness";
        POTION_IDS[41] = "minecraft:strong_strength";
        POTION_IDS[42] = "minecraft:slowness";
        POTION_IDS[43] = "minecraft:strong_leaping";
        POTION_IDS[44] = "minecraft:strong_harming";
        POTION_IDS[45] = "minecraft:water_breathing";
        POTION_IDS[46] = "minecraft:invisibility";
        POTION_IDS[47] = null;
        POTION_IDS[48] = null;
        POTION_IDS[49] = "minecraft:strong_regeneration";
        POTION_IDS[50] = "minecraft:strong_swiftness";
        POTION_IDS[51] = "minecraft:fire_resistance";
        POTION_IDS[52] = "minecraft:strong_poison";
        POTION_IDS[53] = "minecraft:strong_healing";
        POTION_IDS[54] = "minecraft:night_vision";
        POTION_IDS[55] = null;
        POTION_IDS[56] = "minecraft:weakness";
        POTION_IDS[57] = "minecraft:strong_strength";
        POTION_IDS[58] = "minecraft:slowness";
        POTION_IDS[59] = "minecraft:strong_leaping";
        POTION_IDS[60] = "minecraft:strong_harming";
        POTION_IDS[61] = "minecraft:water_breathing";
        POTION_IDS[62] = "minecraft:invisibility";
        POTION_IDS[63] = null;
        POTION_IDS[64] = "minecraft:mundane";
        POTION_IDS[65] = "minecraft:long_regeneration";
        POTION_IDS[66] = "minecraft:long_swiftness";
        POTION_IDS[67] = "minecraft:long_fire_resistance";
        POTION_IDS[68] = "minecraft:long_poison";
        POTION_IDS[69] = "minecraft:healing";
        POTION_IDS[70] = "minecraft:long_night_vision";
        POTION_IDS[71] = null;
        POTION_IDS[72] = "minecraft:long_weakness";
        POTION_IDS[73] = "minecraft:long_strength";
        POTION_IDS[74] = "minecraft:long_slowness";
        POTION_IDS[75] = "minecraft:long_leaping";
        POTION_IDS[76] = "minecraft:harming";
        POTION_IDS[77] = "minecraft:long_water_breathing";
        POTION_IDS[78] = "minecraft:long_invisibility";
        POTION_IDS[79] = null;
        POTION_IDS[80] = "minecraft:awkward";
        POTION_IDS[81] = "minecraft:long_regeneration";
        POTION_IDS[82] = "minecraft:long_swiftness";
        POTION_IDS[83] = "minecraft:long_fire_resistance";
        POTION_IDS[84] = "minecraft:long_poison";
        POTION_IDS[85] = "minecraft:healing";
        POTION_IDS[86] = "minecraft:long_night_vision";
        POTION_IDS[87] = null;
        POTION_IDS[88] = "minecraft:long_weakness";
        POTION_IDS[89] = "minecraft:long_strength";
        POTION_IDS[90] = "minecraft:long_slowness";
        POTION_IDS[91] = "minecraft:long_leaping";
        POTION_IDS[92] = "minecraft:harming";
        POTION_IDS[93] = "minecraft:long_water_breathing";
        POTION_IDS[94] = "minecraft:long_invisibility";
        POTION_IDS[95] = null;
        POTION_IDS[96] = "minecraft:thick";
        POTION_IDS[97] = "minecraft:regeneration";
        POTION_IDS[98] = "minecraft:swiftness";
        POTION_IDS[99] = "minecraft:long_fire_resistance";
        POTION_IDS[100] = "minecraft:poison";
        POTION_IDS[101] = "minecraft:strong_healing";
        POTION_IDS[102] = "minecraft:long_night_vision";
        POTION_IDS[103] = null;
        POTION_IDS[104] = "minecraft:long_weakness";
        POTION_IDS[105] = "minecraft:strength";
        POTION_IDS[106] = "minecraft:long_slowness";
        POTION_IDS[107] = "minecraft:leaping";
        POTION_IDS[108] = "minecraft:strong_harming";
        POTION_IDS[109] = "minecraft:long_water_breathing";
        POTION_IDS[110] = "minecraft:long_invisibility";
        POTION_IDS[111] = null;
        POTION_IDS[112] = null;
        POTION_IDS[113] = "minecraft:regeneration";
        POTION_IDS[114] = "minecraft:swiftness";
        POTION_IDS[115] = "minecraft:long_fire_resistance";
        POTION_IDS[116] = "minecraft:poison";
        POTION_IDS[117] = "minecraft:strong_healing";
        POTION_IDS[118] = "minecraft:long_night_vision";
        POTION_IDS[119] = null;
        POTION_IDS[120] = "minecraft:long_weakness";
        POTION_IDS[121] = "minecraft:strength";
        POTION_IDS[122] = "minecraft:long_slowness";
        POTION_IDS[123] = "minecraft:leaping";
        POTION_IDS[124] = "minecraft:strong_harming";
        POTION_IDS[125] = "minecraft:long_water_breathing";
        POTION_IDS[126] = "minecraft:long_invisibility";
        POTION_IDS[127] = null;
    }
}
