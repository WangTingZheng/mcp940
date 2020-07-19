package net.minecraft.world.gen.structure;

import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import java.util.Iterator;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.crash.ICrashReportDetail;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ReportedException;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.MapGenBase;

public abstract class MapGenStructure extends MapGenBase {
   private MapGenStructureData field_143029_e;
   protected Long2ObjectMap<StructureStart> field_75053_d = new Long2ObjectOpenHashMap<StructureStart>(1024);

   public abstract String func_143025_a();

   protected final synchronized void func_180701_a(World p_180701_1_, final int p_180701_2_, final int p_180701_3_, int p_180701_4_, int p_180701_5_, ChunkPrimer p_180701_6_) {
      this.func_143027_a(p_180701_1_);
      if (!this.field_75053_d.containsKey(ChunkPos.func_77272_a(p_180701_2_, p_180701_3_))) {
         this.field_75038_b.nextInt();

         try {
            if (this.func_75047_a(p_180701_2_, p_180701_3_)) {
               StructureStart structurestart = this.func_75049_b(p_180701_2_, p_180701_3_);
               this.field_75053_d.put(ChunkPos.func_77272_a(p_180701_2_, p_180701_3_), structurestart);
               if (structurestart.func_75069_d()) {
                  this.func_143026_a(p_180701_2_, p_180701_3_, structurestart);
               }
            }

         } catch (Throwable throwable) {
            CrashReport crashreport = CrashReport.func_85055_a(throwable, "Exception preparing structure feature");
            CrashReportCategory crashreportcategory = crashreport.func_85058_a("Feature being prepared");
            crashreportcategory.func_189529_a("Is feature chunk", new ICrashReportDetail<String>() {
               public String call() throws Exception {
                  return MapGenStructure.this.func_75047_a(p_180701_2_, p_180701_3_) ? "True" : "False";
               }
            });
            crashreportcategory.func_71507_a("Chunk location", String.format("%d,%d", p_180701_2_, p_180701_3_));
            crashreportcategory.func_189529_a("Chunk pos hash", new ICrashReportDetail<String>() {
               public String call() throws Exception {
                  return String.valueOf(ChunkPos.func_77272_a(p_180701_2_, p_180701_3_));
               }
            });
            crashreportcategory.func_189529_a("Structure type", new ICrashReportDetail<String>() {
               public String call() throws Exception {
                  return MapGenStructure.this.getClass().getCanonicalName();
               }
            });
            throw new ReportedException(crashreport);
         }
      }
   }

   public synchronized boolean func_175794_a(World p_175794_1_, Random p_175794_2_, ChunkPos p_175794_3_) {
      this.func_143027_a(p_175794_1_);
      int i = (p_175794_3_.field_77276_a << 4) + 8;
      int j = (p_175794_3_.field_77275_b << 4) + 8;
      boolean flag = false;
      ObjectIterator objectiterator = this.field_75053_d.values().iterator();

      while(objectiterator.hasNext()) {
         StructureStart structurestart = (StructureStart)objectiterator.next();
         if (structurestart.func_75069_d() && structurestart.func_175788_a(p_175794_3_) && structurestart.func_75071_a().func_78885_a(i, j, i + 15, j + 15)) {
            structurestart.func_75068_a(p_175794_1_, p_175794_2_, new StructureBoundingBox(i, j, i + 15, j + 15));
            structurestart.func_175787_b(p_175794_3_);
            flag = true;
            this.func_143026_a(structurestart.func_143019_e(), structurestart.func_143018_f(), structurestart);
         }
      }

      return flag;
   }

   public boolean func_175795_b(BlockPos p_175795_1_) {
      if (this.field_75039_c == null) {
         return false;
      } else {
         this.func_143027_a(this.field_75039_c);
         return this.func_175797_c(p_175795_1_) != null;
      }
   }

   @Nullable
   protected StructureStart func_175797_c(BlockPos p_175797_1_) {
      ObjectIterator objectiterator = this.field_75053_d.values().iterator();

      label31:
      while(objectiterator.hasNext()) {
         StructureStart structurestart = (StructureStart)objectiterator.next();
         if (structurestart.func_75069_d() && structurestart.func_75071_a().func_175898_b(p_175797_1_)) {
            Iterator<StructureComponent> iterator = structurestart.func_186161_c().iterator();

            while(true) {
               if (!iterator.hasNext()) {
                  continue label31;
               }

               StructureComponent structurecomponent = iterator.next();
               if (structurecomponent.func_74874_b().func_175898_b(p_175797_1_)) {
                  break;
               }
            }

            return structurestart;
         }
      }

      return null;
   }

   public boolean func_175796_a(World p_175796_1_, BlockPos p_175796_2_) {
      this.func_143027_a(p_175796_1_);
      ObjectIterator objectiterator = this.field_75053_d.values().iterator();

      while(objectiterator.hasNext()) {
         StructureStart structurestart = (StructureStart)objectiterator.next();
         if (structurestart.func_75069_d() && structurestart.func_75071_a().func_175898_b(p_175796_2_)) {
            return true;
         }
      }

      return false;
   }

   @Nullable
   public abstract BlockPos func_180706_b(World var1, BlockPos var2, boolean var3);

   protected void func_143027_a(World p_143027_1_) {
      if (this.field_143029_e == null && p_143027_1_ != null) {
         this.field_143029_e = (MapGenStructureData)p_143027_1_.func_72943_a(MapGenStructureData.class, this.func_143025_a());
         if (this.field_143029_e == null) {
            this.field_143029_e = new MapGenStructureData(this.func_143025_a());
            p_143027_1_.func_72823_a(this.func_143025_a(), this.field_143029_e);
         } else {
            NBTTagCompound nbttagcompound = this.field_143029_e.func_143041_a();

            for(String s : nbttagcompound.func_150296_c()) {
               NBTBase nbtbase = nbttagcompound.func_74781_a(s);
               if (nbtbase.func_74732_a() == 10) {
                  NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbtbase;
                  if (nbttagcompound1.func_74764_b("ChunkX") && nbttagcompound1.func_74764_b("ChunkZ")) {
                     int i = nbttagcompound1.func_74762_e("ChunkX");
                     int j = nbttagcompound1.func_74762_e("ChunkZ");
                     StructureStart structurestart = MapGenStructureIO.func_143035_a(nbttagcompound1, p_143027_1_);
                     if (structurestart != null) {
                        this.field_75053_d.put(ChunkPos.func_77272_a(i, j), structurestart);
                     }
                  }
               }
            }
         }
      }

   }

   private void func_143026_a(int p_143026_1_, int p_143026_2_, StructureStart p_143026_3_) {
      this.field_143029_e.func_143043_a(p_143026_3_.func_143021_a(p_143026_1_, p_143026_2_), p_143026_1_, p_143026_2_);
      this.field_143029_e.func_76185_a();
   }

   protected abstract boolean func_75047_a(int var1, int var2);

   protected abstract StructureStart func_75049_b(int var1, int var2);

   protected static BlockPos func_191069_a(World p_191069_0_, MapGenStructure p_191069_1_, BlockPos p_191069_2_, int p_191069_3_, int p_191069_4_, int p_191069_5_, boolean p_191069_6_, int p_191069_7_, boolean p_191069_8_) {
      int i = p_191069_2_.func_177958_n() >> 4;
      int j = p_191069_2_.func_177952_p() >> 4;
      int k = 0;

      for(Random random = new Random(); k <= p_191069_7_; ++k) {
         for(int l = -k; l <= k; ++l) {
            boolean flag = l == -k || l == k;

            for(int i1 = -k; i1 <= k; ++i1) {
               boolean flag1 = i1 == -k || i1 == k;
               if (flag || flag1) {
                  int j1 = i + p_191069_3_ * l;
                  int k1 = j + p_191069_3_ * i1;
                  if (j1 < 0) {
                     j1 -= p_191069_3_ - 1;
                  }

                  if (k1 < 0) {
                     k1 -= p_191069_3_ - 1;
                  }

                  int l1 = j1 / p_191069_3_;
                  int i2 = k1 / p_191069_3_;
                  Random random1 = p_191069_0_.func_72843_D(l1, i2, p_191069_5_);
                  l1 = l1 * p_191069_3_;
                  i2 = i2 * p_191069_3_;
                  if (p_191069_6_) {
                     l1 = l1 + (random1.nextInt(p_191069_3_ - p_191069_4_) + random1.nextInt(p_191069_3_ - p_191069_4_)) / 2;
                     i2 = i2 + (random1.nextInt(p_191069_3_ - p_191069_4_) + random1.nextInt(p_191069_3_ - p_191069_4_)) / 2;
                  } else {
                     l1 = l1 + random1.nextInt(p_191069_3_ - p_191069_4_);
                     i2 = i2 + random1.nextInt(p_191069_3_ - p_191069_4_);
                  }

                  MapGenBase.func_191068_a(p_191069_0_.func_72905_C(), random, l1, i2);
                  random.nextInt();
                  if (p_191069_1_.func_75047_a(l1, i2)) {
                     if (!p_191069_8_ || !p_191069_0_.func_190526_b(l1, i2)) {
                        return new BlockPos((l1 << 4) + 8, 64, (i2 << 4) + 8);
                     }
                  } else if (k == 0) {
                     break;
                  }
               }
            }

            if (k == 0) {
               break;
            }
         }
      }

      return null;
   }
}
