package net.minecraft.util.datafix.fixes;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.datafix.IFixableData;

public class SpawnEggNames implements IFixableData
{
    private static final String[] ENTITY_IDS = new String[256];

    public int getFixVersion()
    {
        return 105;
    }

    public NBTTagCompound fixTagCompound(NBTTagCompound compound)
    {
        if ("minecraft:spawn_egg".equals(compound.getString("id")))
        {
            NBTTagCompound nbttagcompound = compound.getCompoundTag("tag");
            NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("EntityTag");
            short short1 = compound.getShort("Damage");

            if (!nbttagcompound1.hasKey("id", 8))
            {
                String s = ENTITY_IDS[short1 & 255];

                if (s != null)
                {
                    nbttagcompound1.setString("id", s);
                    nbttagcompound.setTag("EntityTag", nbttagcompound1);
                    compound.setTag("tag", nbttagcompound);
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
        String[] astring = ENTITY_IDS;
        astring[1] = "Item";
        astring[2] = "XPOrb";
        astring[7] = "ThrownEgg";
        astring[8] = "LeashKnot";
        astring[9] = "Painting";
        astring[10] = "Arrow";
        astring[11] = "Snowball";
        astring[12] = "Fireball";
        astring[13] = "SmallFireball";
        astring[14] = "ThrownEnderpearl";
        astring[15] = "EyeOfEnderSignal";
        astring[16] = "ThrownPotion";
        astring[17] = "ThrownExpBottle";
        astring[18] = "ItemFrame";
        astring[19] = "WitherSkull";
        astring[20] = "PrimedTnt";
        astring[21] = "FallingSand";
        astring[22] = "FireworksRocketEntity";
        astring[23] = "TippedArrow";
        astring[24] = "SpectralArrow";
        astring[25] = "ShulkerBullet";
        astring[26] = "DragonFireball";
        astring[30] = "ArmorStand";
        astring[41] = "Boat";
        astring[42] = "MinecartRideable";
        astring[43] = "MinecartChest";
        astring[44] = "MinecartFurnace";
        astring[45] = "MinecartTNT";
        astring[46] = "MinecartHopper";
        astring[47] = "MinecartSpawner";
        astring[40] = "MinecartCommandBlock";
        astring[48] = "Mob";
        astring[49] = "Monster";
        astring[50] = "Creeper";
        astring[51] = "Skeleton";
        astring[52] = "Spider";
        astring[53] = "Giant";
        astring[54] = "Zombie";
        astring[55] = "Slime";
        astring[56] = "Ghast";
        astring[57] = "PigZombie";
        astring[58] = "Enderman";
        astring[59] = "CaveSpider";
        astring[60] = "Silverfish";
        astring[61] = "Blaze";
        astring[62] = "LavaSlime";
        astring[63] = "EnderDragon";
        astring[64] = "WitherBoss";
        astring[65] = "Bat";
        astring[66] = "Witch";
        astring[67] = "Endermite";
        astring[68] = "Guardian";
        astring[69] = "Shulker";
        astring[90] = "Pig";
        astring[91] = "Sheep";
        astring[92] = "Cow";
        astring[93] = "Chicken";
        astring[94] = "Squid";
        astring[95] = "Wolf";
        astring[96] = "MushroomCow";
        astring[97] = "SnowMan";
        astring[98] = "Ozelot";
        astring[99] = "VillagerGolem";
        astring[100] = "EntityHorse";
        astring[101] = "Rabbit";
        astring[120] = "Villager";
        astring[200] = "EnderCrystal";
    }
}
