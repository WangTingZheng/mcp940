package net.minecraft.world.biome;

public class BiomeRiver extends Biome
{
    public BiomeRiver(Biome.BiomeProperties properties)
    {
        super(properties);
        this.spawnableCreatureList.clear();
    }
}
