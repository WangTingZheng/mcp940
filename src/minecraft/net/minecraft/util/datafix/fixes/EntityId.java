package net.minecraft.util.datafix.fixes;

import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.datafix.IFixableData;

public class EntityId implements IFixableData
{
    private static final Map<String, String> OLD_TO_NEW_ID_MAP = Maps.<String, String>newHashMap();

    public int getFixVersion()
    {
        return 704;
    }

    public NBTTagCompound fixTagCompound(NBTTagCompound compound)
    {
        String s = OLD_TO_NEW_ID_MAP.get(compound.getString("id"));

        if (s != null)
        {
            compound.setString("id", s);
        }

        return compound;
    }

    static
    {
        OLD_TO_NEW_ID_MAP.put("AreaEffectCloud", "minecraft:area_effect_cloud");
        OLD_TO_NEW_ID_MAP.put("ArmorStand", "minecraft:armor_stand");
        OLD_TO_NEW_ID_MAP.put("Arrow", "minecraft:arrow");
        OLD_TO_NEW_ID_MAP.put("Bat", "minecraft:bat");
        OLD_TO_NEW_ID_MAP.put("Blaze", "minecraft:blaze");
        OLD_TO_NEW_ID_MAP.put("Boat", "minecraft:boat");
        OLD_TO_NEW_ID_MAP.put("CaveSpider", "minecraft:cave_spider");
        OLD_TO_NEW_ID_MAP.put("Chicken", "minecraft:chicken");
        OLD_TO_NEW_ID_MAP.put("Cow", "minecraft:cow");
        OLD_TO_NEW_ID_MAP.put("Creeper", "minecraft:creeper");
        OLD_TO_NEW_ID_MAP.put("Donkey", "minecraft:donkey");
        OLD_TO_NEW_ID_MAP.put("DragonFireball", "minecraft:dragon_fireball");
        OLD_TO_NEW_ID_MAP.put("ElderGuardian", "minecraft:elder_guardian");
        OLD_TO_NEW_ID_MAP.put("EnderCrystal", "minecraft:ender_crystal");
        OLD_TO_NEW_ID_MAP.put("EnderDragon", "minecraft:ender_dragon");
        OLD_TO_NEW_ID_MAP.put("Enderman", "minecraft:enderman");
        OLD_TO_NEW_ID_MAP.put("Endermite", "minecraft:endermite");
        OLD_TO_NEW_ID_MAP.put("EyeOfEnderSignal", "minecraft:eye_of_ender_signal");
        OLD_TO_NEW_ID_MAP.put("FallingSand", "minecraft:falling_block");
        OLD_TO_NEW_ID_MAP.put("Fireball", "minecraft:fireball");
        OLD_TO_NEW_ID_MAP.put("FireworksRocketEntity", "minecraft:fireworks_rocket");
        OLD_TO_NEW_ID_MAP.put("Ghast", "minecraft:ghast");
        OLD_TO_NEW_ID_MAP.put("Giant", "minecraft:giant");
        OLD_TO_NEW_ID_MAP.put("Guardian", "minecraft:guardian");
        OLD_TO_NEW_ID_MAP.put("Horse", "minecraft:horse");
        OLD_TO_NEW_ID_MAP.put("Husk", "minecraft:husk");
        OLD_TO_NEW_ID_MAP.put("Item", "minecraft:item");
        OLD_TO_NEW_ID_MAP.put("ItemFrame", "minecraft:item_frame");
        OLD_TO_NEW_ID_MAP.put("LavaSlime", "minecraft:magma_cube");
        OLD_TO_NEW_ID_MAP.put("LeashKnot", "minecraft:leash_knot");
        OLD_TO_NEW_ID_MAP.put("MinecartChest", "minecraft:chest_minecart");
        OLD_TO_NEW_ID_MAP.put("MinecartCommandBlock", "minecraft:commandblock_minecart");
        OLD_TO_NEW_ID_MAP.put("MinecartFurnace", "minecraft:furnace_minecart");
        OLD_TO_NEW_ID_MAP.put("MinecartHopper", "minecraft:hopper_minecart");
        OLD_TO_NEW_ID_MAP.put("MinecartRideable", "minecraft:minecart");
        OLD_TO_NEW_ID_MAP.put("MinecartSpawner", "minecraft:spawner_minecart");
        OLD_TO_NEW_ID_MAP.put("MinecartTNT", "minecraft:tnt_minecart");
        OLD_TO_NEW_ID_MAP.put("Mule", "minecraft:mule");
        OLD_TO_NEW_ID_MAP.put("MushroomCow", "minecraft:mooshroom");
        OLD_TO_NEW_ID_MAP.put("Ozelot", "minecraft:ocelot");
        OLD_TO_NEW_ID_MAP.put("Painting", "minecraft:painting");
        OLD_TO_NEW_ID_MAP.put("Pig", "minecraft:pig");
        OLD_TO_NEW_ID_MAP.put("PigZombie", "minecraft:zombie_pigman");
        OLD_TO_NEW_ID_MAP.put("PolarBear", "minecraft:polar_bear");
        OLD_TO_NEW_ID_MAP.put("PrimedTnt", "minecraft:tnt");
        OLD_TO_NEW_ID_MAP.put("Rabbit", "minecraft:rabbit");
        OLD_TO_NEW_ID_MAP.put("Sheep", "minecraft:sheep");
        OLD_TO_NEW_ID_MAP.put("Shulker", "minecraft:shulker");
        OLD_TO_NEW_ID_MAP.put("ShulkerBullet", "minecraft:shulker_bullet");
        OLD_TO_NEW_ID_MAP.put("Silverfish", "minecraft:silverfish");
        OLD_TO_NEW_ID_MAP.put("Skeleton", "minecraft:skeleton");
        OLD_TO_NEW_ID_MAP.put("SkeletonHorse", "minecraft:skeleton_horse");
        OLD_TO_NEW_ID_MAP.put("Slime", "minecraft:slime");
        OLD_TO_NEW_ID_MAP.put("SmallFireball", "minecraft:small_fireball");
        OLD_TO_NEW_ID_MAP.put("SnowMan", "minecraft:snowman");
        OLD_TO_NEW_ID_MAP.put("Snowball", "minecraft:snowball");
        OLD_TO_NEW_ID_MAP.put("SpectralArrow", "minecraft:spectral_arrow");
        OLD_TO_NEW_ID_MAP.put("Spider", "minecraft:spider");
        OLD_TO_NEW_ID_MAP.put("Squid", "minecraft:squid");
        OLD_TO_NEW_ID_MAP.put("Stray", "minecraft:stray");
        OLD_TO_NEW_ID_MAP.put("ThrownEgg", "minecraft:egg");
        OLD_TO_NEW_ID_MAP.put("ThrownEnderpearl", "minecraft:ender_pearl");
        OLD_TO_NEW_ID_MAP.put("ThrownExpBottle", "minecraft:xp_bottle");
        OLD_TO_NEW_ID_MAP.put("ThrownPotion", "minecraft:potion");
        OLD_TO_NEW_ID_MAP.put("Villager", "minecraft:villager");
        OLD_TO_NEW_ID_MAP.put("VillagerGolem", "minecraft:villager_golem");
        OLD_TO_NEW_ID_MAP.put("Witch", "minecraft:witch");
        OLD_TO_NEW_ID_MAP.put("WitherBoss", "minecraft:wither");
        OLD_TO_NEW_ID_MAP.put("WitherSkeleton", "minecraft:wither_skeleton");
        OLD_TO_NEW_ID_MAP.put("WitherSkull", "minecraft:wither_skull");
        OLD_TO_NEW_ID_MAP.put("Wolf", "minecraft:wolf");
        OLD_TO_NEW_ID_MAP.put("XPOrb", "minecraft:xp_orb");
        OLD_TO_NEW_ID_MAP.put("Zombie", "minecraft:zombie");
        OLD_TO_NEW_ID_MAP.put("ZombieHorse", "minecraft:zombie_horse");
        OLD_TO_NEW_ID_MAP.put("ZombieVillager", "minecraft:zombie_villager");
    }
}
