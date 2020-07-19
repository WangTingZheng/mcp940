package net.minecraft.world;

public class WorldProviderSurface extends WorldProvider
{
    public DimensionType getDimensionType()
    {
        return DimensionType.OVERWORLD;
    }

    /**
     * Called to determine if the chunk at the given chunk coordinates within the provider's world can be dropped. Used
     * in WorldProviderSurface to prevent spawn chunks from being unloaded.
     */
    public boolean canDropChunk(int x, int z)
    {
        return !this.world.isSpawnChunk(x, z);
    }
}
