package net.minecraft.world.storage.loot;

import com.google.common.collect.Sets;
import java.io.File;
import java.util.Collections;
import java.util.Set;
import net.minecraft.util.ResourceLocation;

public class LootTableList
{
    private static final Set<ResourceLocation> LOOT_TABLES = Sets.<ResourceLocation>newHashSet();
    private static final Set<ResourceLocation> READ_ONLY_LOOT_TABLES = Collections.<ResourceLocation>unmodifiableSet(LOOT_TABLES);
    public static final ResourceLocation EMPTY = register("empty");
    public static final ResourceLocation CHESTS_SPAWN_BONUS_CHEST = register("chests/spawn_bonus_chest");
    public static final ResourceLocation CHESTS_END_CITY_TREASURE = register("chests/end_city_treasure");
    public static final ResourceLocation CHESTS_SIMPLE_DUNGEON = register("chests/simple_dungeon");
    public static final ResourceLocation CHESTS_VILLAGE_BLACKSMITH = register("chests/village_blacksmith");
    public static final ResourceLocation CHESTS_ABANDONED_MINESHAFT = register("chests/abandoned_mineshaft");
    public static final ResourceLocation CHESTS_NETHER_BRIDGE = register("chests/nether_bridge");
    public static final ResourceLocation CHESTS_STRONGHOLD_LIBRARY = register("chests/stronghold_library");
    public static final ResourceLocation CHESTS_STRONGHOLD_CROSSING = register("chests/stronghold_crossing");
    public static final ResourceLocation CHESTS_STRONGHOLD_CORRIDOR = register("chests/stronghold_corridor");
    public static final ResourceLocation CHESTS_DESERT_PYRAMID = register("chests/desert_pyramid");
    public static final ResourceLocation CHESTS_JUNGLE_TEMPLE = register("chests/jungle_temple");
    public static final ResourceLocation CHESTS_JUNGLE_TEMPLE_DISPENSER = register("chests/jungle_temple_dispenser");
    public static final ResourceLocation CHESTS_IGLOO_CHEST = register("chests/igloo_chest");
    public static final ResourceLocation CHESTS_WOODLAND_MANSION = register("chests/woodland_mansion");
    public static final ResourceLocation ENTITIES_WITCH = register("entities/witch");
    public static final ResourceLocation ENTITIES_BLAZE = register("entities/blaze");
    public static final ResourceLocation ENTITIES_CREEPER = register("entities/creeper");
    public static final ResourceLocation ENTITIES_SPIDER = register("entities/spider");
    public static final ResourceLocation ENTITIES_CAVE_SPIDER = register("entities/cave_spider");
    public static final ResourceLocation ENTITIES_GIANT = register("entities/giant");
    public static final ResourceLocation ENTITIES_SILVERFISH = register("entities/silverfish");
    public static final ResourceLocation ENTITIES_ENDERMAN = register("entities/enderman");
    public static final ResourceLocation ENTITIES_GUARDIAN = register("entities/guardian");
    public static final ResourceLocation ENTITIES_ELDER_GUARDIAN = register("entities/elder_guardian");
    public static final ResourceLocation ENTITIES_SHULKER = register("entities/shulker");
    public static final ResourceLocation ENTITIES_IRON_GOLEM = register("entities/iron_golem");
    public static final ResourceLocation ENTITIES_SNOWMAN = register("entities/snowman");
    public static final ResourceLocation ENTITIES_RABBIT = register("entities/rabbit");
    public static final ResourceLocation ENTITIES_CHICKEN = register("entities/chicken");
    public static final ResourceLocation ENTITIES_PIG = register("entities/pig");
    public static final ResourceLocation ENTITIES_POLAR_BEAR = register("entities/polar_bear");
    public static final ResourceLocation ENTITIES_HORSE = register("entities/horse");
    public static final ResourceLocation ENTITIES_DONKEY = register("entities/donkey");
    public static final ResourceLocation ENTITIES_MULE = register("entities/mule");
    public static final ResourceLocation ENTITIES_ZOMBIE_HORSE = register("entities/zombie_horse");
    public static final ResourceLocation ENTITIES_SKELETON_HORSE = register("entities/skeleton_horse");
    public static final ResourceLocation ENTITIES_COW = register("entities/cow");
    public static final ResourceLocation ENTITIES_MUSHROOM_COW = register("entities/mushroom_cow");
    public static final ResourceLocation ENTITIES_WOLF = register("entities/wolf");
    public static final ResourceLocation ENTITIES_OCELOT = register("entities/ocelot");
    public static final ResourceLocation ENTITIES_SHEEP = register("entities/sheep");
    public static final ResourceLocation ENTITIES_SHEEP_WHITE = register("entities/sheep/white");
    public static final ResourceLocation ENTITIES_SHEEP_ORANGE = register("entities/sheep/orange");
    public static final ResourceLocation ENTITIES_SHEEP_MAGENTA = register("entities/sheep/magenta");
    public static final ResourceLocation ENTITIES_SHEEP_LIGHT_BLUE = register("entities/sheep/light_blue");
    public static final ResourceLocation ENTITIES_SHEEP_YELLOW = register("entities/sheep/yellow");
    public static final ResourceLocation ENTITIES_SHEEP_LIME = register("entities/sheep/lime");
    public static final ResourceLocation ENTITIES_SHEEP_PINK = register("entities/sheep/pink");
    public static final ResourceLocation ENTITIES_SHEEP_GRAY = register("entities/sheep/gray");
    public static final ResourceLocation ENTITIES_SHEEP_SILVER = register("entities/sheep/silver");
    public static final ResourceLocation ENTITIES_SHEEP_CYAN = register("entities/sheep/cyan");
    public static final ResourceLocation ENTITIES_SHEEP_PURPLE = register("entities/sheep/purple");
    public static final ResourceLocation ENTITIES_SHEEP_BLUE = register("entities/sheep/blue");
    public static final ResourceLocation ENTITIES_SHEEP_BROWN = register("entities/sheep/brown");
    public static final ResourceLocation ENTITIES_SHEEP_GREEN = register("entities/sheep/green");
    public static final ResourceLocation ENTITIES_SHEEP_RED = register("entities/sheep/red");
    public static final ResourceLocation ENTITIES_SHEEP_BLACK = register("entities/sheep/black");
    public static final ResourceLocation ENTITIES_BAT = register("entities/bat");
    public static final ResourceLocation ENTITIES_SLIME = register("entities/slime");
    public static final ResourceLocation ENTITIES_MAGMA_CUBE = register("entities/magma_cube");
    public static final ResourceLocation ENTITIES_GHAST = register("entities/ghast");
    public static final ResourceLocation ENTITIES_SQUID = register("entities/squid");
    public static final ResourceLocation ENTITIES_ENDERMITE = register("entities/endermite");
    public static final ResourceLocation ENTITIES_ZOMBIE = register("entities/zombie");
    public static final ResourceLocation ENTITIES_ZOMBIE_PIGMAN = register("entities/zombie_pigman");
    public static final ResourceLocation ENTITIES_SKELETON = register("entities/skeleton");
    public static final ResourceLocation ENTITIES_WITHER_SKELETON = register("entities/wither_skeleton");
    public static final ResourceLocation ENTITIES_STRAY = register("entities/stray");
    public static final ResourceLocation ENTITIES_HUSK = register("entities/husk");
    public static final ResourceLocation ENTITIES_ZOMBIE_VILLAGER = register("entities/zombie_villager");
    public static final ResourceLocation ENTITIES_VILLAGER = register("entities/villager");
    public static final ResourceLocation ENTITIES_EVOCATION_ILLAGER = register("entities/evocation_illager");
    public static final ResourceLocation ENTITIES_VINDICATION_ILLAGER = register("entities/vindication_illager");
    public static final ResourceLocation ENTITIES_LLAMA = register("entities/llama");
    public static final ResourceLocation ENTITIES_PARROT = register("entities/parrot");
    public static final ResourceLocation ENTITIES_VEX = register("entities/vex");
    public static final ResourceLocation ENTITIES_ENDER_DRAGON = register("entities/ender_dragon");
    public static final ResourceLocation GAMEPLAY_FISHING = register("gameplay/fishing");
    public static final ResourceLocation GAMEPLAY_FISHING_JUNK = register("gameplay/fishing/junk");
    public static final ResourceLocation GAMEPLAY_FISHING_TREASURE = register("gameplay/fishing/treasure");
    public static final ResourceLocation GAMEPLAY_FISHING_FISH = register("gameplay/fishing/fish");

    private static ResourceLocation register(String id)
    {
        return register(new ResourceLocation("minecraft", id));
    }

    public static ResourceLocation register(ResourceLocation id)
    {
        if (LOOT_TABLES.add(id))
        {
            return id;
        }
        else
        {
            throw new IllegalArgumentException(id + " is already a registered built-in loot table");
        }
    }

    public static Set<ResourceLocation> getAll()
    {
        return READ_ONLY_LOOT_TABLES;
    }

    public static boolean test()
    {
        LootTableManager loottablemanager = new LootTableManager((File)null);

        for (ResourceLocation resourcelocation : READ_ONLY_LOOT_TABLES)
        {
            if (loottablemanager.getLootTableFromLocation(resourcelocation) == LootTable.EMPTY_LOOT_TABLE)
            {
                return false;
            }
        }

        return true;
    }
}
