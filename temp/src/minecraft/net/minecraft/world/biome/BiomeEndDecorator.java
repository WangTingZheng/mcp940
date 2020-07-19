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

public class BiomeEndDecorator extends BiomeDecorator {
   private static final LoadingCache<Long, WorldGenSpikes.EndSpike[]> field_185427_L = CacheBuilder.newBuilder().expireAfterWrite(5L, TimeUnit.MINUTES).<Long, WorldGenSpikes.EndSpike[]>build(new BiomeEndDecorator.SpikeCacheLoader());
   private final WorldGenSpikes field_76835_L = new WorldGenSpikes();

   protected void func_150513_a(Biome p_150513_1_, World p_150513_2_, Random p_150513_3_) {
      this.func_76797_b(p_150513_2_, p_150513_3_);
      WorldGenSpikes.EndSpike[] aworldgenspikes$endspike = func_185426_a(p_150513_2_);

      for(WorldGenSpikes.EndSpike worldgenspikes$endspike : aworldgenspikes$endspike) {
         if (worldgenspikes$endspike.func_186154_a(this.field_180294_c)) {
            this.field_76835_L.func_186143_a(worldgenspikes$endspike);
            this.field_76835_L.func_180709_b(p_150513_2_, p_150513_3_, new BlockPos(worldgenspikes$endspike.func_186151_a(), 45, worldgenspikes$endspike.func_186152_b()));
         }
      }

   }

   public static WorldGenSpikes.EndSpike[] func_185426_a(World p_185426_0_) {
      Random random = new Random(p_185426_0_.func_72905_C());
      long i = random.nextLong() & 65535L;
      return field_185427_L.getUnchecked(Long.valueOf(i));
   }

   static class SpikeCacheLoader extends CacheLoader<Long, WorldGenSpikes.EndSpike[]> {
      private SpikeCacheLoader() {
      }

      public WorldGenSpikes.EndSpike[] load(Long p_load_1_) throws Exception {
         List<Integer> list = Lists.newArrayList(ContiguousSet.create(Range.closedOpen(Integer.valueOf(0), Integer.valueOf(10)), DiscreteDomain.integers()));
         Collections.shuffle(list, new Random(p_load_1_.longValue()));
         WorldGenSpikes.EndSpike[] aworldgenspikes$endspike = new WorldGenSpikes.EndSpike[10];

         for(int i = 0; i < 10; ++i) {
            int j = (int)(42.0D * Math.cos(2.0D * (-3.141592653589793D + 0.3141592653589793D * (double)i)));
            int k = (int)(42.0D * Math.sin(2.0D * (-3.141592653589793D + 0.3141592653589793D * (double)i)));
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
