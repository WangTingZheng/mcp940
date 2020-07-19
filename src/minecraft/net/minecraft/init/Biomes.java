package net.minecraft.init;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;

public abstract class Biomes
{
    public static final Biome OCEAN;
    public static final Biome DEFAULT;
    public static final Biome PLAINS;
    public static final Biome DESERT;
    public static final Biome EXTREME_HILLS;
    public static final Biome FOREST;
    public static final Biome TAIGA;
    public static final Biome SWAMPLAND;
    public static final Biome RIVER;
    public static final Biome HELL;

    /** Is the biome used for sky world. */
    public static final Biome SKY;
    public static final Biome FROZEN_OCEAN;
    public static final Biome FROZEN_RIVER;
    public static final Biome ICE_PLAINS;
    public static final Biome ICE_MOUNTAINS;
    public static final Biome MUSHROOM_ISLAND;
    public static final Biome MUSHROOM_ISLAND_SHORE;

    /** Beach biome. */
    public static final Biome BEACH;

    /** Desert Hills biome. */
    public static final Biome DESERT_HILLS;

    /** Forest Hills biome. */
    public static final Biome FOREST_HILLS;

    /** Taiga Hills biome. */
    public static final Biome TAIGA_HILLS;

    /** Extreme Hills Edge biome. */
    public static final Biome EXTREME_HILLS_EDGE;

    /** Jungle biome identifier */
    public static final Biome JUNGLE;
    public static final Biome JUNGLE_HILLS;
    public static final Biome JUNGLE_EDGE;
    public static final Biome DEEP_OCEAN;
    public static final Biome STONE_BEACH;
    public static final Biome COLD_BEACH;
    public static final Biome BIRCH_FOREST;
    public static final Biome BIRCH_FOREST_HILLS;
    public static final Biome ROOFED_FOREST;
    public static final Biome COLD_TAIGA;
    public static final Biome COLD_TAIGA_HILLS;
    public static final Biome REDWOOD_TAIGA;
    public static final Biome REDWOOD_TAIGA_HILLS;
    public static final Biome EXTREME_HILLS_WITH_TREES;
    public static final Biome SAVANNA;
    public static final Biome SAVANNA_PLATEAU;
    public static final Biome MESA;
    public static final Biome MESA_ROCK;
    public static final Biome MESA_CLEAR_ROCK;
    public static final Biome VOID;
    public static final Biome MUTATED_PLAINS;
    public static final Biome MUTATED_DESERT;
    public static final Biome MUTATED_EXTREME_HILLS;
    public static final Biome MUTATED_FOREST;
    public static final Biome MUTATED_TAIGA;
    public static final Biome MUTATED_SWAMPLAND;
    public static final Biome MUTATED_ICE_FLATS;
    public static final Biome MUTATED_JUNGLE;
    public static final Biome MUTATED_JUNGLE_EDGE;
    public static final Biome MUTATED_BIRCH_FOREST;
    public static final Biome MUTATED_BIRCH_FOREST_HILLS;
    public static final Biome MUTATED_ROOFED_FOREST;
    public static final Biome MUTATED_TAIGA_COLD;
    public static final Biome MUTATED_REDWOOD_TAIGA;
    public static final Biome MUTATED_REDWOOD_TAIGA_HILLS;
    public static final Biome MUTATED_EXTREME_HILLS_WITH_TREES;
    public static final Biome MUTATED_SAVANNA;
    public static final Biome MUTATED_SAVANNA_ROCK;
    public static final Biome MUTATED_MESA;
    public static final Biome MUTATED_MESA_ROCK;
    public static final Biome MUTATED_MESA_CLEAR_ROCK;

    private static Biome getRegisteredBiome(String id)
    {
        Biome biome = Biome.REGISTRY.getObject(new ResourceLocation(id));

        if (biome == null)
        {
            throw new IllegalStateException("Invalid Biome requested: " + id);
        }
        else
        {
            return biome;
        }
    }

    static
    {
        if (!Bootstrap.isRegistered())
        {
            throw new RuntimeException("Accessed Biomes before Bootstrap!");
        }
        else
        {
            OCEAN = getRegisteredBiome("ocean");
            DEFAULT = OCEAN;
            PLAINS = getRegisteredBiome("plains");
            DESERT = getRegisteredBiome("desert");
            EXTREME_HILLS = getRegisteredBiome("extreme_hills");
            FOREST = getRegisteredBiome("forest");
            TAIGA = getRegisteredBiome("taiga");
            SWAMPLAND = getRegisteredBiome("swampland");
            RIVER = getRegisteredBiome("river");
            HELL = getRegisteredBiome("hell");
            SKY = getRegisteredBiome("sky");
            FROZEN_OCEAN = getRegisteredBiome("frozen_ocean");
            FROZEN_RIVER = getRegisteredBiome("frozen_river");
            ICE_PLAINS = getRegisteredBiome("ice_flats");
            ICE_MOUNTAINS = getRegisteredBiome("ice_mountains");
            MUSHROOM_ISLAND = getRegisteredBiome("mushroom_island");
            MUSHROOM_ISLAND_SHORE = getRegisteredBiome("mushroom_island_shore");
            BEACH = getRegisteredBiome("beaches");
            DESERT_HILLS = getRegisteredBiome("desert_hills");
            FOREST_HILLS = getRegisteredBiome("forest_hills");
            TAIGA_HILLS = getRegisteredBiome("taiga_hills");
            EXTREME_HILLS_EDGE = getRegisteredBiome("smaller_extreme_hills");
            JUNGLE = getRegisteredBiome("jungle");
            JUNGLE_HILLS = getRegisteredBiome("jungle_hills");
            JUNGLE_EDGE = getRegisteredBiome("jungle_edge");
            DEEP_OCEAN = getRegisteredBiome("deep_ocean");
            STONE_BEACH = getRegisteredBiome("stone_beach");
            COLD_BEACH = getRegisteredBiome("cold_beach");
            BIRCH_FOREST = getRegisteredBiome("birch_forest");
            BIRCH_FOREST_HILLS = getRegisteredBiome("birch_forest_hills");
            ROOFED_FOREST = getRegisteredBiome("roofed_forest");
            COLD_TAIGA = getRegisteredBiome("taiga_cold");
            COLD_TAIGA_HILLS = getRegisteredBiome("taiga_cold_hills");
            REDWOOD_TAIGA = getRegisteredBiome("redwood_taiga");
            REDWOOD_TAIGA_HILLS = getRegisteredBiome("redwood_taiga_hills");
            EXTREME_HILLS_WITH_TREES = getRegisteredBiome("extreme_hills_with_trees");
            SAVANNA = getRegisteredBiome("savanna");
            SAVANNA_PLATEAU = getRegisteredBiome("savanna_rock");
            MESA = getRegisteredBiome("mesa");
            MESA_ROCK = getRegisteredBiome("mesa_rock");
            MESA_CLEAR_ROCK = getRegisteredBiome("mesa_clear_rock");
            VOID = getRegisteredBiome("void");
            MUTATED_PLAINS = getRegisteredBiome("mutated_plains");
            MUTATED_DESERT = getRegisteredBiome("mutated_desert");
            MUTATED_EXTREME_HILLS = getRegisteredBiome("mutated_extreme_hills");
            MUTATED_FOREST = getRegisteredBiome("mutated_forest");
            MUTATED_TAIGA = getRegisteredBiome("mutated_taiga");
            MUTATED_SWAMPLAND = getRegisteredBiome("mutated_swampland");
            MUTATED_ICE_FLATS = getRegisteredBiome("mutated_ice_flats");
            MUTATED_JUNGLE = getRegisteredBiome("mutated_jungle");
            MUTATED_JUNGLE_EDGE = getRegisteredBiome("mutated_jungle_edge");
            MUTATED_BIRCH_FOREST = getRegisteredBiome("mutated_birch_forest");
            MUTATED_BIRCH_FOREST_HILLS = getRegisteredBiome("mutated_birch_forest_hills");
            MUTATED_ROOFED_FOREST = getRegisteredBiome("mutated_roofed_forest");
            MUTATED_TAIGA_COLD = getRegisteredBiome("mutated_taiga_cold");
            MUTATED_REDWOOD_TAIGA = getRegisteredBiome("mutated_redwood_taiga");
            MUTATED_REDWOOD_TAIGA_HILLS = getRegisteredBiome("mutated_redwood_taiga_hills");
            MUTATED_EXTREME_HILLS_WITH_TREES = getRegisteredBiome("mutated_extreme_hills_with_trees");
            MUTATED_SAVANNA = getRegisteredBiome("mutated_savanna");
            MUTATED_SAVANNA_ROCK = getRegisteredBiome("mutated_savanna_rock");
            MUTATED_MESA = getRegisteredBiome("mutated_mesa");
            MUTATED_MESA_ROCK = getRegisteredBiome("mutated_mesa_rock");
            MUTATED_MESA_CLEAR_ROCK = getRegisteredBiome("mutated_mesa_clear_rock");
        }
    }
}
