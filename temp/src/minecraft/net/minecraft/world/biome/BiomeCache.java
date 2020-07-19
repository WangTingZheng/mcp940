package net.minecraft.world.biome;

import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import java.util.List;
import net.minecraft.server.MinecraftServer;

public class BiomeCache {
   private final BiomeProvider field_76844_a;
   private long field_76842_b;
   private final Long2ObjectMap<BiomeCache.Block> field_76843_c = new Long2ObjectOpenHashMap<BiomeCache.Block>(4096);
   private final List<BiomeCache.Block> field_76841_d = Lists.<BiomeCache.Block>newArrayList();

   public BiomeCache(BiomeProvider p_i1973_1_) {
      this.field_76844_a = p_i1973_1_;
   }

   public BiomeCache.Block func_76840_a(int p_76840_1_, int p_76840_2_) {
      p_76840_1_ = p_76840_1_ >> 4;
      p_76840_2_ = p_76840_2_ >> 4;
      long i = (long)p_76840_1_ & 4294967295L | ((long)p_76840_2_ & 4294967295L) << 32;
      BiomeCache.Block biomecache$block = (BiomeCache.Block)this.field_76843_c.get(i);
      if (biomecache$block == null) {
         biomecache$block = new BiomeCache.Block(p_76840_1_, p_76840_2_);
         this.field_76843_c.put(i, biomecache$block);
         this.field_76841_d.add(biomecache$block);
      }

      biomecache$block.field_76886_f = MinecraftServer.func_130071_aq();
      return biomecache$block;
   }

   public Biome func_180284_a(int p_180284_1_, int p_180284_2_, Biome p_180284_3_) {
      Biome biome = this.func_76840_a(p_180284_1_, p_180284_2_).func_76885_a(p_180284_1_, p_180284_2_);
      return biome == null ? p_180284_3_ : biome;
   }

   public void func_76838_a() {
      long i = MinecraftServer.func_130071_aq();
      long j = i - this.field_76842_b;
      if (j > 7500L || j < 0L) {
         this.field_76842_b = i;

         for(int k = 0; k < this.field_76841_d.size(); ++k) {
            BiomeCache.Block biomecache$block = this.field_76841_d.get(k);
            long l = i - biomecache$block.field_76886_f;
            if (l > 30000L || l < 0L) {
               this.field_76841_d.remove(k--);
               long i1 = (long)biomecache$block.field_76888_d & 4294967295L | ((long)biomecache$block.field_76889_e & 4294967295L) << 32;
               this.field_76843_c.remove(i1);
            }
         }
      }

   }

   public Biome[] func_76839_e(int p_76839_1_, int p_76839_2_) {
      return this.func_76840_a(p_76839_1_, p_76839_2_).field_76891_c;
   }

   public class Block {
      public Biome[] field_76891_c = new Biome[256];
      public int field_76888_d;
      public int field_76889_e;
      public long field_76886_f;

      public Block(int p_i1972_2_, int p_i1972_3_) {
         this.field_76888_d = p_i1972_2_;
         this.field_76889_e = p_i1972_3_;
         BiomeCache.this.field_76844_a.func_76931_a(this.field_76891_c, p_i1972_2_ << 4, p_i1972_3_ << 4, 16, 16, false);
      }

      public Biome func_76885_a(int p_76885_1_, int p_76885_2_) {
         return this.field_76891_c[p_76885_1_ & 15 | (p_76885_2_ & 15) << 4];
      }
   }
}
