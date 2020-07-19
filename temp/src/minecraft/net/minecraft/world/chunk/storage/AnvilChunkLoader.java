package net.minecraft.world.chunk.storage;

import com.google.common.collect.Maps;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.datafix.FixTypes;
import net.minecraft.util.datafix.IDataFixer;
import net.minecraft.util.datafix.IDataWalker;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.MinecraftException;
import net.minecraft.world.NextTickListEntry;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.NibbleArray;
import net.minecraft.world.storage.IThreadedFileIO;
import net.minecraft.world.storage.ThreadedFileIOBase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AnvilChunkLoader implements IChunkLoader, IThreadedFileIO {
   private static final Logger field_151505_a = LogManager.getLogger();
   private final Map<ChunkPos, NBTTagCompound> field_75828_a = Maps.<ChunkPos, NBTTagCompound>newConcurrentMap();
   private final Set<ChunkPos> field_193415_c = Collections.<ChunkPos>newSetFromMap(Maps.newConcurrentMap());
   private final File field_75825_d;
   private final DataFixer field_193416_e;
   private boolean field_183014_e;

   public AnvilChunkLoader(File p_i46673_1_, DataFixer p_i46673_2_) {
      this.field_75825_d = p_i46673_1_;
      this.field_193416_e = p_i46673_2_;
   }

   @Nullable
   public Chunk func_75815_a(World p_75815_1_, int p_75815_2_, int p_75815_3_) throws IOException {
      ChunkPos chunkpos = new ChunkPos(p_75815_2_, p_75815_3_);
      NBTTagCompound nbttagcompound = this.field_75828_a.get(chunkpos);
      if (nbttagcompound == null) {
         DataInputStream datainputstream = RegionFileCache.func_76549_c(this.field_75825_d, p_75815_2_, p_75815_3_);
         if (datainputstream == null) {
            return null;
         }

         nbttagcompound = this.field_193416_e.func_188257_a(FixTypes.CHUNK, CompressedStreamTools.func_74794_a(datainputstream));
      }

      return this.func_75822_a(p_75815_1_, p_75815_2_, p_75815_3_, nbttagcompound);
   }

   public boolean func_191063_a(int p_191063_1_, int p_191063_2_) {
      ChunkPos chunkpos = new ChunkPos(p_191063_1_, p_191063_2_);
      NBTTagCompound nbttagcompound = this.field_75828_a.get(chunkpos);
      return nbttagcompound != null ? true : RegionFileCache.func_191064_f(this.field_75825_d, p_191063_1_, p_191063_2_);
   }

   @Nullable
   protected Chunk func_75822_a(World p_75822_1_, int p_75822_2_, int p_75822_3_, NBTTagCompound p_75822_4_) {
      if (!p_75822_4_.func_150297_b("Level", 10)) {
         field_151505_a.error("Chunk file at {},{} is missing level data, skipping", Integer.valueOf(p_75822_2_), Integer.valueOf(p_75822_3_));
         return null;
      } else {
         NBTTagCompound nbttagcompound = p_75822_4_.func_74775_l("Level");
         if (!nbttagcompound.func_150297_b("Sections", 9)) {
            field_151505_a.error("Chunk file at {},{} is missing block data, skipping", Integer.valueOf(p_75822_2_), Integer.valueOf(p_75822_3_));
            return null;
         } else {
            Chunk chunk = this.func_75823_a(p_75822_1_, nbttagcompound);
            if (!chunk.func_76600_a(p_75822_2_, p_75822_3_)) {
               field_151505_a.error("Chunk file at {},{} is in the wrong location; relocating. (Expected {}, {}, got {}, {})", Integer.valueOf(p_75822_2_), Integer.valueOf(p_75822_3_), Integer.valueOf(p_75822_2_), Integer.valueOf(p_75822_3_), Integer.valueOf(chunk.field_76635_g), Integer.valueOf(chunk.field_76647_h));
               nbttagcompound.func_74768_a("xPos", p_75822_2_);
               nbttagcompound.func_74768_a("zPos", p_75822_3_);
               chunk = this.func_75823_a(p_75822_1_, nbttagcompound);
            }

            return chunk;
         }
      }
   }

   public void func_75816_a(World p_75816_1_, Chunk p_75816_2_) throws MinecraftException, IOException {
      p_75816_1_.func_72906_B();

      try {
         NBTTagCompound nbttagcompound = new NBTTagCompound();
         NBTTagCompound nbttagcompound1 = new NBTTagCompound();
         nbttagcompound.func_74782_a("Level", nbttagcompound1);
         nbttagcompound.func_74768_a("DataVersion", 1139);
         this.func_75820_a(p_75816_2_, p_75816_1_, nbttagcompound1);
         this.func_75824_a(p_75816_2_.func_76632_l(), nbttagcompound);
      } catch (Exception exception) {
         field_151505_a.error("Failed to save chunk", (Throwable)exception);
      }

   }

   protected void func_75824_a(ChunkPos p_75824_1_, NBTTagCompound p_75824_2_) {
      if (!this.field_193415_c.contains(p_75824_1_)) {
         this.field_75828_a.put(p_75824_1_, p_75824_2_);
      }

      ThreadedFileIOBase.func_178779_a().func_75735_a(this);
   }

   public boolean func_75814_c() {
      if (this.field_75828_a.isEmpty()) {
         if (this.field_183014_e) {
            field_151505_a.info("ThreadedAnvilChunkStorage ({}): All chunks are saved", (Object)this.field_75825_d.getName());
         }

         return false;
      } else {
         ChunkPos chunkpos = this.field_75828_a.keySet().iterator().next();

         boolean lvt_3_1_;
         try {
            this.field_193415_c.add(chunkpos);
            NBTTagCompound nbttagcompound = this.field_75828_a.remove(chunkpos);
            if (nbttagcompound != null) {
               try {
                  this.func_183013_b(chunkpos, nbttagcompound);
               } catch (Exception exception) {
                  field_151505_a.error("Failed to save chunk", (Throwable)exception);
               }
            }

            lvt_3_1_ = true;
         } finally {
            this.field_193415_c.remove(chunkpos);
         }

         return lvt_3_1_;
      }
   }

   private void func_183013_b(ChunkPos p_183013_1_, NBTTagCompound p_183013_2_) throws IOException {
      DataOutputStream dataoutputstream = RegionFileCache.func_76552_d(this.field_75825_d, p_183013_1_.field_77276_a, p_183013_1_.field_77275_b);
      CompressedStreamTools.func_74800_a(p_183013_2_, dataoutputstream);
      dataoutputstream.close();
   }

   public void func_75819_b(World p_75819_1_, Chunk p_75819_2_) throws IOException {
   }

   public void func_75817_a() {
   }

   public void func_75818_b() {
      try {
         this.field_183014_e = true;

         while(true) {
            if (this.func_75814_c()) {
               continue;
            }
         }
      } finally {
         this.field_183014_e = false;
      }

   }

   public static void func_189889_a(DataFixer p_189889_0_) {
      p_189889_0_.func_188258_a(FixTypes.CHUNK, new IDataWalker() {
         public NBTTagCompound func_188266_a(IDataFixer p_188266_1_, NBTTagCompound p_188266_2_, int p_188266_3_) {
            if (p_188266_2_.func_150297_b("Level", 10)) {
               NBTTagCompound nbttagcompound = p_188266_2_.func_74775_l("Level");
               if (nbttagcompound.func_150297_b("Entities", 9)) {
                  NBTTagList nbttaglist = nbttagcompound.func_150295_c("Entities", 10);

                  for(int i = 0; i < nbttaglist.func_74745_c(); ++i) {
                     nbttaglist.func_150304_a(i, p_188266_1_.func_188251_a(FixTypes.ENTITY, (NBTTagCompound)nbttaglist.func_179238_g(i), p_188266_3_));
                  }
               }

               if (nbttagcompound.func_150297_b("TileEntities", 9)) {
                  NBTTagList nbttaglist1 = nbttagcompound.func_150295_c("TileEntities", 10);

                  for(int j = 0; j < nbttaglist1.func_74745_c(); ++j) {
                     nbttaglist1.func_150304_a(j, p_188266_1_.func_188251_a(FixTypes.BLOCK_ENTITY, (NBTTagCompound)nbttaglist1.func_179238_g(j), p_188266_3_));
                  }
               }
            }

            return p_188266_2_;
         }
      });
   }

   private void func_75820_a(Chunk p_75820_1_, World p_75820_2_, NBTTagCompound p_75820_3_) {
      p_75820_3_.func_74768_a("xPos", p_75820_1_.field_76635_g);
      p_75820_3_.func_74768_a("zPos", p_75820_1_.field_76647_h);
      p_75820_3_.func_74772_a("LastUpdate", p_75820_2_.func_82737_E());
      p_75820_3_.func_74783_a("HeightMap", p_75820_1_.func_177445_q());
      p_75820_3_.func_74757_a("TerrainPopulated", p_75820_1_.func_177419_t());
      p_75820_3_.func_74757_a("LightPopulated", p_75820_1_.func_177423_u());
      p_75820_3_.func_74772_a("InhabitedTime", p_75820_1_.func_177416_w());
      ExtendedBlockStorage[] aextendedblockstorage = p_75820_1_.func_76587_i();
      NBTTagList nbttaglist = new NBTTagList();
      boolean flag = p_75820_2_.field_73011_w.func_191066_m();

      for(ExtendedBlockStorage extendedblockstorage : aextendedblockstorage) {
         if (extendedblockstorage != Chunk.field_186036_a) {
            NBTTagCompound nbttagcompound = new NBTTagCompound();
            nbttagcompound.func_74774_a("Y", (byte)(extendedblockstorage.func_76662_d() >> 4 & 255));
            byte[] abyte = new byte[4096];
            NibbleArray nibblearray = new NibbleArray();
            NibbleArray nibblearray1 = extendedblockstorage.func_186049_g().func_186017_a(abyte, nibblearray);
            nbttagcompound.func_74773_a("Blocks", abyte);
            nbttagcompound.func_74773_a("Data", nibblearray.func_177481_a());
            if (nibblearray1 != null) {
               nbttagcompound.func_74773_a("Add", nibblearray1.func_177481_a());
            }

            nbttagcompound.func_74773_a("BlockLight", extendedblockstorage.func_76661_k().func_177481_a());
            if (flag) {
               nbttagcompound.func_74773_a("SkyLight", extendedblockstorage.func_76671_l().func_177481_a());
            } else {
               nbttagcompound.func_74773_a("SkyLight", new byte[extendedblockstorage.func_76661_k().func_177481_a().length]);
            }

            nbttaglist.func_74742_a(nbttagcompound);
         }
      }

      p_75820_3_.func_74782_a("Sections", nbttaglist);
      p_75820_3_.func_74773_a("Biomes", p_75820_1_.func_76605_m());
      p_75820_1_.func_177409_g(false);
      NBTTagList nbttaglist1 = new NBTTagList();

      for(int i = 0; i < p_75820_1_.func_177429_s().length; ++i) {
         for(Entity entity : p_75820_1_.func_177429_s()[i]) {
            NBTTagCompound nbttagcompound2 = new NBTTagCompound();
            if (entity.func_70039_c(nbttagcompound2)) {
               p_75820_1_.func_177409_g(true);
               nbttaglist1.func_74742_a(nbttagcompound2);
            }
         }
      }

      p_75820_3_.func_74782_a("Entities", nbttaglist1);
      NBTTagList nbttaglist2 = new NBTTagList();

      for(TileEntity tileentity : p_75820_1_.func_177434_r().values()) {
         NBTTagCompound nbttagcompound3 = tileentity.func_189515_b(new NBTTagCompound());
         nbttaglist2.func_74742_a(nbttagcompound3);
      }

      p_75820_3_.func_74782_a("TileEntities", nbttaglist2);
      List<NextTickListEntry> list = p_75820_2_.func_72920_a(p_75820_1_, false);
      if (list != null) {
         long j = p_75820_2_.func_82737_E();
         NBTTagList nbttaglist3 = new NBTTagList();

         for(NextTickListEntry nextticklistentry : list) {
            NBTTagCompound nbttagcompound1 = new NBTTagCompound();
            ResourceLocation resourcelocation = Block.field_149771_c.func_177774_c(nextticklistentry.func_151351_a());
            nbttagcompound1.func_74778_a("i", resourcelocation == null ? "" : resourcelocation.toString());
            nbttagcompound1.func_74768_a("x", nextticklistentry.field_180282_a.func_177958_n());
            nbttagcompound1.func_74768_a("y", nextticklistentry.field_180282_a.func_177956_o());
            nbttagcompound1.func_74768_a("z", nextticklistentry.field_180282_a.func_177952_p());
            nbttagcompound1.func_74768_a("t", (int)(nextticklistentry.field_77180_e - j));
            nbttagcompound1.func_74768_a("p", nextticklistentry.field_82754_f);
            nbttaglist3.func_74742_a(nbttagcompound1);
         }

         p_75820_3_.func_74782_a("TileTicks", nbttaglist3);
      }

   }

   private Chunk func_75823_a(World p_75823_1_, NBTTagCompound p_75823_2_) {
      int i = p_75823_2_.func_74762_e("xPos");
      int j = p_75823_2_.func_74762_e("zPos");
      Chunk chunk = new Chunk(p_75823_1_, i, j);
      chunk.func_177420_a(p_75823_2_.func_74759_k("HeightMap"));
      chunk.func_177446_d(p_75823_2_.func_74767_n("TerrainPopulated"));
      chunk.func_177421_e(p_75823_2_.func_74767_n("LightPopulated"));
      chunk.func_177415_c(p_75823_2_.func_74763_f("InhabitedTime"));
      NBTTagList nbttaglist = p_75823_2_.func_150295_c("Sections", 10);
      int k = 16;
      ExtendedBlockStorage[] aextendedblockstorage = new ExtendedBlockStorage[16];
      boolean flag = p_75823_1_.field_73011_w.func_191066_m();

      for(int l = 0; l < nbttaglist.func_74745_c(); ++l) {
         NBTTagCompound nbttagcompound = nbttaglist.func_150305_b(l);
         int i1 = nbttagcompound.func_74771_c("Y");
         ExtendedBlockStorage extendedblockstorage = new ExtendedBlockStorage(i1 << 4, flag);
         byte[] abyte = nbttagcompound.func_74770_j("Blocks");
         NibbleArray nibblearray = new NibbleArray(nbttagcompound.func_74770_j("Data"));
         NibbleArray nibblearray1 = nbttagcompound.func_150297_b("Add", 7) ? new NibbleArray(nbttagcompound.func_74770_j("Add")) : null;
         extendedblockstorage.func_186049_g().func_186019_a(abyte, nibblearray, nibblearray1);
         extendedblockstorage.func_76659_c(new NibbleArray(nbttagcompound.func_74770_j("BlockLight")));
         if (flag) {
            extendedblockstorage.func_76666_d(new NibbleArray(nbttagcompound.func_74770_j("SkyLight")));
         }

         extendedblockstorage.func_76672_e();
         aextendedblockstorage[i1] = extendedblockstorage;
      }

      chunk.func_76602_a(aextendedblockstorage);
      if (p_75823_2_.func_150297_b("Biomes", 7)) {
         chunk.func_76616_a(p_75823_2_.func_74770_j("Biomes"));
      }

      NBTTagList nbttaglist1 = p_75823_2_.func_150295_c("Entities", 10);

      for(int j1 = 0; j1 < nbttaglist1.func_74745_c(); ++j1) {
         NBTTagCompound nbttagcompound1 = nbttaglist1.func_150305_b(j1);
         func_186050_a(nbttagcompound1, p_75823_1_, chunk);
         chunk.func_177409_g(true);
      }

      NBTTagList nbttaglist2 = p_75823_2_.func_150295_c("TileEntities", 10);

      for(int k1 = 0; k1 < nbttaglist2.func_74745_c(); ++k1) {
         NBTTagCompound nbttagcompound2 = nbttaglist2.func_150305_b(k1);
         TileEntity tileentity = TileEntity.func_190200_a(p_75823_1_, nbttagcompound2);
         if (tileentity != null) {
            chunk.func_150813_a(tileentity);
         }
      }

      if (p_75823_2_.func_150297_b("TileTicks", 9)) {
         NBTTagList nbttaglist3 = p_75823_2_.func_150295_c("TileTicks", 10);

         for(int l1 = 0; l1 < nbttaglist3.func_74745_c(); ++l1) {
            NBTTagCompound nbttagcompound3 = nbttaglist3.func_150305_b(l1);
            Block block;
            if (nbttagcompound3.func_150297_b("i", 8)) {
               block = Block.func_149684_b(nbttagcompound3.func_74779_i("i"));
            } else {
               block = Block.func_149729_e(nbttagcompound3.func_74762_e("i"));
            }

            p_75823_1_.func_180497_b(new BlockPos(nbttagcompound3.func_74762_e("x"), nbttagcompound3.func_74762_e("y"), nbttagcompound3.func_74762_e("z")), block, nbttagcompound3.func_74762_e("t"), nbttagcompound3.func_74762_e("p"));
         }
      }

      return chunk;
   }

   @Nullable
   public static Entity func_186050_a(NBTTagCompound p_186050_0_, World p_186050_1_, Chunk p_186050_2_) {
      Entity entity = func_186053_a(p_186050_0_, p_186050_1_);
      if (entity == null) {
         return null;
      } else {
         p_186050_2_.func_76612_a(entity);
         if (p_186050_0_.func_150297_b("Passengers", 9)) {
            NBTTagList nbttaglist = p_186050_0_.func_150295_c("Passengers", 10);

            for(int i = 0; i < nbttaglist.func_74745_c(); ++i) {
               Entity entity1 = func_186050_a(nbttaglist.func_150305_b(i), p_186050_1_, p_186050_2_);
               if (entity1 != null) {
                  entity1.func_184205_a(entity, true);
               }
            }
         }

         return entity;
      }
   }

   @Nullable
   public static Entity func_186054_a(NBTTagCompound p_186054_0_, World p_186054_1_, double p_186054_2_, double p_186054_4_, double p_186054_6_, boolean p_186054_8_) {
      Entity entity = func_186053_a(p_186054_0_, p_186054_1_);
      if (entity == null) {
         return null;
      } else {
         entity.func_70012_b(p_186054_2_, p_186054_4_, p_186054_6_, entity.field_70177_z, entity.field_70125_A);
         if (p_186054_8_ && !p_186054_1_.func_72838_d(entity)) {
            return null;
         } else {
            if (p_186054_0_.func_150297_b("Passengers", 9)) {
               NBTTagList nbttaglist = p_186054_0_.func_150295_c("Passengers", 10);

               for(int i = 0; i < nbttaglist.func_74745_c(); ++i) {
                  Entity entity1 = func_186054_a(nbttaglist.func_150305_b(i), p_186054_1_, p_186054_2_, p_186054_4_, p_186054_6_, p_186054_8_);
                  if (entity1 != null) {
                     entity1.func_184205_a(entity, true);
                  }
               }
            }

            return entity;
         }
      }
   }

   @Nullable
   protected static Entity func_186053_a(NBTTagCompound p_186053_0_, World p_186053_1_) {
      try {
         return EntityList.func_75615_a(p_186053_0_, p_186053_1_);
      } catch (RuntimeException var3) {
         return null;
      }
   }

   public static void func_186052_a(Entity p_186052_0_, World p_186052_1_) {
      if (p_186052_1_.func_72838_d(p_186052_0_) && p_186052_0_.func_184207_aI()) {
         for(Entity entity : p_186052_0_.func_184188_bt()) {
            func_186052_a(entity, p_186052_1_);
         }
      }

   }

   @Nullable
   public static Entity func_186051_a(NBTTagCompound p_186051_0_, World p_186051_1_, boolean p_186051_2_) {
      Entity entity = func_186053_a(p_186051_0_, p_186051_1_);
      if (entity == null) {
         return null;
      } else if (p_186051_2_ && !p_186051_1_.func_72838_d(entity)) {
         return null;
      } else {
         if (p_186051_0_.func_150297_b("Passengers", 9)) {
            NBTTagList nbttaglist = p_186051_0_.func_150295_c("Passengers", 10);

            for(int i = 0; i < nbttaglist.func_74745_c(); ++i) {
               Entity entity1 = func_186051_a(nbttaglist.func_150305_b(i), p_186051_1_, p_186051_2_);
               if (entity1 != null) {
                  entity1.func_184205_a(entity, true);
               }
            }
         }

         return entity;
      }
   }
}
