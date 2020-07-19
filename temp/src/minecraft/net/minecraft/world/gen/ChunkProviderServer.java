package net.minecraft.world.gen;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.ReportedException;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.MinecraftException;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.chunk.storage.IChunkLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ChunkProviderServer implements IChunkProvider {
   private static final Logger field_147417_b = LogManager.getLogger();
   private final Set<Long> field_73248_b = Sets.<Long>newHashSet();
   private final IChunkGenerator field_186029_c;
   private final IChunkLoader field_73247_e;
   private final Long2ObjectMap<Chunk> field_73244_f = new Long2ObjectOpenHashMap<Chunk>(8192);
   private final WorldServer field_73251_h;

   public ChunkProviderServer(WorldServer p_i46838_1_, IChunkLoader p_i46838_2_, IChunkGenerator p_i46838_3_) {
      this.field_73251_h = p_i46838_1_;
      this.field_73247_e = p_i46838_2_;
      this.field_186029_c = p_i46838_3_;
   }

   public Collection<Chunk> func_189548_a() {
      return this.field_73244_f.values();
   }

   public void func_189549_a(Chunk p_189549_1_) {
      if (this.field_73251_h.field_73011_w.func_186056_c(p_189549_1_.field_76635_g, p_189549_1_.field_76647_h)) {
         this.field_73248_b.add(Long.valueOf(ChunkPos.func_77272_a(p_189549_1_.field_76635_g, p_189549_1_.field_76647_h)));
         p_189549_1_.field_189550_d = true;
      }

   }

   public void func_73240_a() {
      ObjectIterator objectiterator = this.field_73244_f.values().iterator();

      while(objectiterator.hasNext()) {
         Chunk chunk = (Chunk)objectiterator.next();
         this.func_189549_a(chunk);
      }

   }

   @Nullable
   public Chunk func_186026_b(int p_186026_1_, int p_186026_2_) {
      long i = ChunkPos.func_77272_a(p_186026_1_, p_186026_2_);
      Chunk chunk = (Chunk)this.field_73244_f.get(i);
      if (chunk != null) {
         chunk.field_189550_d = false;
      }

      return chunk;
   }

   @Nullable
   public Chunk func_186028_c(int p_186028_1_, int p_186028_2_) {
      Chunk chunk = this.func_186026_b(p_186028_1_, p_186028_2_);
      if (chunk == null) {
         chunk = this.func_73239_e(p_186028_1_, p_186028_2_);
         if (chunk != null) {
            this.field_73244_f.put(ChunkPos.func_77272_a(p_186028_1_, p_186028_2_), chunk);
            chunk.func_76631_c();
            chunk.func_186030_a(this, this.field_186029_c);
         }
      }

      return chunk;
   }

   public Chunk func_186025_d(int p_186025_1_, int p_186025_2_) {
      Chunk chunk = this.func_186028_c(p_186025_1_, p_186025_2_);
      if (chunk == null) {
         long i = ChunkPos.func_77272_a(p_186025_1_, p_186025_2_);

         try {
            chunk = this.field_186029_c.func_185932_a(p_186025_1_, p_186025_2_);
         } catch (Throwable throwable) {
            CrashReport crashreport = CrashReport.func_85055_a(throwable, "Exception generating new chunk");
            CrashReportCategory crashreportcategory = crashreport.func_85058_a("Chunk to be generated");
            crashreportcategory.func_71507_a("Location", String.format("%d,%d", p_186025_1_, p_186025_2_));
            crashreportcategory.func_71507_a("Position hash", Long.valueOf(i));
            crashreportcategory.func_71507_a("Generator", this.field_186029_c);
            throw new ReportedException(crashreport);
         }

         this.field_73244_f.put(i, chunk);
         chunk.func_76631_c();
         chunk.func_186030_a(this, this.field_186029_c);
      }

      return chunk;
   }

   @Nullable
   private Chunk func_73239_e(int p_73239_1_, int p_73239_2_) {
      try {
         Chunk chunk = this.field_73247_e.func_75815_a(this.field_73251_h, p_73239_1_, p_73239_2_);
         if (chunk != null) {
            chunk.func_177432_b(this.field_73251_h.func_82737_E());
            this.field_186029_c.func_180514_a(chunk, p_73239_1_, p_73239_2_);
         }

         return chunk;
      } catch (Exception exception) {
         field_147417_b.error("Couldn't load chunk", (Throwable)exception);
         return null;
      }
   }

   private void func_73243_a(Chunk p_73243_1_) {
      try {
         this.field_73247_e.func_75819_b(this.field_73251_h, p_73243_1_);
      } catch (Exception exception) {
         field_147417_b.error("Couldn't save entities", (Throwable)exception);
      }

   }

   private void func_73242_b(Chunk p_73242_1_) {
      try {
         p_73242_1_.func_177432_b(this.field_73251_h.func_82737_E());
         this.field_73247_e.func_75816_a(this.field_73251_h, p_73242_1_);
      } catch (IOException ioexception) {
         field_147417_b.error("Couldn't save chunk", (Throwable)ioexception);
      } catch (MinecraftException minecraftexception) {
         field_147417_b.error("Couldn't save chunk; already in use by another instance of Minecraft?", (Throwable)minecraftexception);
      }

   }

   public boolean func_186027_a(boolean p_186027_1_) {
      int i = 0;
      List<Chunk> list = Lists.newArrayList(this.field_73244_f.values());

      for(int j = 0; j < list.size(); ++j) {
         Chunk chunk = list.get(j);
         if (p_186027_1_) {
            this.func_73243_a(chunk);
         }

         if (chunk.func_76601_a(p_186027_1_)) {
            this.func_73242_b(chunk);
            chunk.func_177427_f(false);
            ++i;
            if (i == 24 && !p_186027_1_) {
               return false;
            }
         }
      }

      return true;
   }

   public void func_104112_b() {
      this.field_73247_e.func_75818_b();
   }

   public boolean func_73156_b() {
      if (!this.field_73251_h.field_73058_d) {
         if (!this.field_73248_b.isEmpty()) {
            Iterator<Long> iterator = this.field_73248_b.iterator();

            for(int i = 0; i < 100 && iterator.hasNext(); iterator.remove()) {
               Long olong = iterator.next();
               Chunk chunk = (Chunk)this.field_73244_f.get(olong);
               if (chunk != null && chunk.field_189550_d) {
                  chunk.func_76623_d();
                  this.func_73242_b(chunk);
                  this.func_73243_a(chunk);
                  this.field_73244_f.remove(olong);
                  ++i;
               }
            }
         }

         this.field_73247_e.func_75817_a();
      }

      return false;
   }

   public boolean func_73157_c() {
      return !this.field_73251_h.field_73058_d;
   }

   public String func_73148_d() {
      return "ServerChunkCache: " + this.field_73244_f.size() + " Drop: " + this.field_73248_b.size();
   }

   public List<Biome.SpawnListEntry> func_177458_a(EnumCreatureType p_177458_1_, BlockPos p_177458_2_) {
      return this.field_186029_c.func_177458_a(p_177458_1_, p_177458_2_);
   }

   @Nullable
   public BlockPos func_180513_a(World p_180513_1_, String p_180513_2_, BlockPos p_180513_3_, boolean p_180513_4_) {
      return this.field_186029_c.func_180513_a(p_180513_1_, p_180513_2_, p_180513_3_, p_180513_4_);
   }

   public boolean func_193413_a(World p_193413_1_, String p_193413_2_, BlockPos p_193413_3_) {
      return this.field_186029_c.func_193414_a(p_193413_1_, p_193413_2_, p_193413_3_);
   }

   public int func_73152_e() {
      return this.field_73244_f.size();
   }

   public boolean func_73149_a(int p_73149_1_, int p_73149_2_) {
      return this.field_73244_f.containsKey(ChunkPos.func_77272_a(p_73149_1_, p_73149_2_));
   }

   public boolean func_191062_e(int p_191062_1_, int p_191062_2_) {
      return this.field_73244_f.containsKey(ChunkPos.func_77272_a(p_191062_1_, p_191062_2_)) || this.field_73247_e.func_191063_a(p_191062_1_, p_191062_2_);
   }
}
