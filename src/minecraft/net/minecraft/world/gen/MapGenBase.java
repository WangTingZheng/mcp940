package net.minecraft.world.gen;

import java.util.Random;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkPrimer;

public class MapGenBase
{
    /** The number of Chunks to gen-check in any given direction. */
    protected int range = 8;

    /** The RNG used by the MapGen classes. */
    protected Random rand = new Random();

    /** This world object. */
    protected World world;

    public void generate(World worldIn, int x, int z, ChunkPrimer primer)
    {
        int i = this.range;
        this.world = worldIn;
        this.rand.setSeed(worldIn.getSeed());
        long j = this.rand.nextLong();
        long k = this.rand.nextLong();

        for (int l = x - i; l <= x + i; ++l)
        {
            for (int i1 = z - i; i1 <= z + i; ++i1)
            {
                long j1 = (long)l * j;
                long k1 = (long)i1 * k;
                this.rand.setSeed(j1 ^ k1 ^ worldIn.getSeed());
                this.recursiveGenerate(worldIn, l, i1, x, z, primer);
            }
        }
    }

    public static void setupChunkSeed(long p_191068_0_, Random p_191068_2_, int p_191068_3_, int p_191068_4_)
    {
        p_191068_2_.setSeed(p_191068_0_);
        long i = p_191068_2_.nextLong();
        long j = p_191068_2_.nextLong();
        long k = (long)p_191068_3_ * i;
        long l = (long)p_191068_4_ * j;
        p_191068_2_.setSeed(k ^ l ^ p_191068_0_);
    }

    /**
     * Recursively called by generate()
     */
    protected void recursiveGenerate(World worldIn, int chunkX, int chunkZ, int originalX, int originalZ, ChunkPrimer chunkPrimerIn)
    {
    }
}
