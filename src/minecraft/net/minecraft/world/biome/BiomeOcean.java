package net.minecraft.world.biome;

public class BiomeOcean extends Biome
{
    public BiomeOcean(Biome.BiomeProperties properties)
    {
        super(properties);
        this.spawnableCreatureList.clear();
    }

    public Biome.TempCategory getTempCategory()
    {
        return Biome.TempCategory.OCEAN;
    }
}
