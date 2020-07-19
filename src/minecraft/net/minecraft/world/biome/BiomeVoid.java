package net.minecraft.world.biome;

public class BiomeVoid extends Biome
{
    public BiomeVoid(Biome.BiomeProperties properties)
    {
        super(properties);
        this.spawnableMonsterList.clear();
        this.spawnableCreatureList.clear();
        this.spawnableWaterCreatureList.clear();
        this.spawnableCaveCreatureList.clear();
        this.decorator = new BiomeVoidDecorator();
    }

    public boolean ignorePlayerSpawnSuitability()
    {
        return true;
    }
}
