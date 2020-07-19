package net.minecraft.world.biome;

import net.minecraft.entity.passive.EntityMooshroom;
import net.minecraft.init.Blocks;

public class BiomeMushroomIsland extends Biome
{
    public BiomeMushroomIsland(Biome.BiomeProperties properties)
    {
        super(properties);
        this.decorator.treesPerChunk = -100;
        this.decorator.flowersPerChunk = -100;
        this.decorator.grassPerChunk = -100;
        this.decorator.mushroomsPerChunk = 1;
        this.decorator.bigMushroomsPerChunk = 1;
        this.topBlock = Blocks.MYCELIUM.getDefaultState();
        this.spawnableMonsterList.clear();
        this.spawnableCreatureList.clear();
        this.spawnableWaterCreatureList.clear();
        this.spawnableCreatureList.add(new Biome.SpawnListEntry(EntityMooshroom.class, 8, 4, 8));
    }
}
