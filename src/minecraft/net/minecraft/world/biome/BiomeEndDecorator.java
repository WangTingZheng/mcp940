package net.minecraft.world.biome;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.ContiguousSet;
import com.google.common.collect.DiscreteDomain;
import com.google.common.collect.Lists;
import com.google.common.collect.Range;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenSpikes;

public class BiomeEndDecorator extends BiomeDecorator
{
    private static final LoadingCache<Long, WorldGenSpikes.EndSpike[]> SPIKE_CACHE = CacheBuilder.newBuilder().expireAfterWrite(5L, TimeUnit.MINUTES).<Long, WorldGenSpikes.EndSpike[]>build(new BiomeEndDecorator.SpikeCacheLoader());
    private final WorldGenSpikes spikeGen = new WorldGenSpikes();

    protected void genDecorations(Biome biomeIn, World worldIn, Random random)
    {
        this.generateOres(worldIn, random);
        WorldGenSpikes.EndSpike[] aworldgenspikes$endspike = getSpikesForWorld(worldIn);

        for (WorldGenSpikes.EndSpike worldgenspikes$endspike : aworldgenspikes$endspike)
        {
            if (worldgenspikes$endspike.doesStartInChunk(this.chunkPos))
            {
                this.spikeGen.setSpike(worldgenspikes$endspike);
                this.spikeGen.generate(worldIn, random, new BlockPos(worldgenspikes$endspike.getCenterX(), 45, worldgenspikes$endspike.getCenterZ()));
            }
        }
    }

    public static WorldGenSpikes.EndSpike[] getSpikesForWorld(World p_185426_0_)
    {
        Random random = new Random(p_185426_0_.getSeed());
        long i = random.nextLong() & 65535L;
        return SPIKE_CACHE.getUnchecked(Long.valueOf(i));
    }

    static class SpikeCacheLoader extends CacheLoader<Long, WorldGenSpikes.EndSpike[]>
    {
        private SpikeCacheLoader()
        {
        }

        public WorldGenSpikes.EndSpike[] load(Long p_load_1_) throws Exception
        {
            List<Integer> list = Lists.newArrayList(ContiguousSet.create(Range.closedOpen(Integer.valueOf(0), Integer.valueOf(10)), DiscreteDomain.integers()));
            Collections.shuffle(list, new Random(p_load_1_.longValue()));
            WorldGenSpikes.EndSpike[] aworldgenspikes$endspike = new WorldGenSpikes.EndSpike[10];

            for (int i = 0; i < 10; ++i)
            {
                int j = (int)(42.0D * Math.cos(2.0D * (-Math.PI + (Math.PI / 10D) * (double)i)));
                int k = (int)(42.0D * Math.sin(2.0D * (-Math.PI + (Math.PI / 10D) * (double)i)));
                int l = ((Integer)list.get(i)).intValue();
                int i1 = 2 + l / 3;
                int j1 = 76 + l * 3;
                boolean flag = l == 1 || l == 2;
                aworldgenspikes$endspike[i] = new WorldGenSpikes.EndSpike(j, k, i1, j1, flag);
            }

            return aworldgenspikes$endspike;
        }
    }
}
