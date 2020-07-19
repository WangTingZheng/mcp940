package net.minecraft.world.chunk.storage;

import net.minecraft.init.Biomes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.chunk.NibbleArray;

public class ChunkLoader {
   public static ChunkLoader.AnvilConverterData func_76691_a(NBTTagCompound p_76691_0_) {
      int i = p_76691_0_.func_74762_e("xPos");
      int j = p_76691_0_.func_74762_e("zPos");
      ChunkLoader.AnvilConverterData chunkloader$anvilconverterdata = new ChunkLoader.AnvilConverterData(i, j);
      chunkloader$anvilconverterdata.field_76693_g = p_76691_0_.func_74770_j("Blocks");
      chunkloader$anvilconverterdata.field_76692_f = new NibbleArrayReader(p_76691_0_.func_74770_j("Data"), 7);
      chunkloader$anvilconverterdata.field_76695_e = new NibbleArrayReader(p_76691_0_.func_74770_j("SkyLight"), 7);
      chunkloader$anvilconverterdata.field_76694_d = new NibbleArrayReader(p_76691_0_.func_74770_j("BlockLight"), 7);
      chunkloader$anvilconverterdata.field_76697_c = p_76691_0_.func_74770_j("HeightMap");
      chunkloader$anvilconverterdata.field_76696_b = p_76691_0_.func_74767_n("TerrainPopulated");
      chunkloader$anvilconverterdata.field_76702_h = p_76691_0_.func_150295_c("Entities", 10);
      chunkloader$anvilconverterdata.field_151564_i = p_76691_0_.func_150295_c("TileEntities", 10);
      chunkloader$anvilconverterdata.field_151563_j = p_76691_0_.func_150295_c("TileTicks", 10);

      try {
         chunkloader$anvilconverterdata.field_76698_a = p_76691_0_.func_74763_f("LastUpdate");
      } catch (ClassCastException var5) {
         chunkloader$anvilconverterdata.field_76698_a = (long)p_76691_0_.func_74762_e("LastUpdate");
      }

      return chunkloader$anvilconverterdata;
   }

   public static void func_76690_a(ChunkLoader.AnvilConverterData p_76690_0_, NBTTagCompound p_76690_1_, BiomeProvider p_76690_2_) {
      p_76690_1_.func_74768_a("xPos", p_76690_0_.field_76701_k);
      p_76690_1_.func_74768_a("zPos", p_76690_0_.field_76699_l);
      p_76690_1_.func_74772_a("LastUpdate", p_76690_0_.field_76698_a);
      int[] aint = new int[p_76690_0_.field_76697_c.length];

      for(int i = 0; i < p_76690_0_.field_76697_c.length; ++i) {
         aint[i] = p_76690_0_.field_76697_c[i];
      }

      p_76690_1_.func_74783_a("HeightMap", aint);
      p_76690_1_.func_74757_a("TerrainPopulated", p_76690_0_.field_76696_b);
      NBTTagList nbttaglist = new NBTTagList();

      for(int j = 0; j < 8; ++j) {
         boolean flag = true;

         for(int k = 0; k < 16 && flag; ++k) {
            for(int l = 0; l < 16 && flag; ++l) {
               for(int i1 = 0; i1 < 16; ++i1) {
                  int j1 = k << 11 | i1 << 7 | l + (j << 4);
                  int k1 = p_76690_0_.field_76693_g[j1];
                  if (k1 != 0) {
                     flag = false;
                     break;
                  }
               }
            }
         }

         if (!flag) {
            byte[] abyte1 = new byte[4096];
            NibbleArray nibblearray = new NibbleArray();
            NibbleArray nibblearray1 = new NibbleArray();
            NibbleArray nibblearray2 = new NibbleArray();

            for(int j3 = 0; j3 < 16; ++j3) {
               for(int l1 = 0; l1 < 16; ++l1) {
                  for(int i2 = 0; i2 < 16; ++i2) {
                     int j2 = j3 << 11 | i2 << 7 | l1 + (j << 4);
                     int k2 = p_76690_0_.field_76693_g[j2];
                     abyte1[l1 << 8 | i2 << 4 | j3] = (byte)(k2 & 255);
                     nibblearray.func_76581_a(j3, l1, i2, p_76690_0_.field_76692_f.func_76686_a(j3, l1 + (j << 4), i2));
                     nibblearray1.func_76581_a(j3, l1, i2, p_76690_0_.field_76695_e.func_76686_a(j3, l1 + (j << 4), i2));
                     nibblearray2.func_76581_a(j3, l1, i2, p_76690_0_.field_76694_d.func_76686_a(j3, l1 + (j << 4), i2));
                  }
               }
            }

            NBTTagCompound nbttagcompound = new NBTTagCompound();
            nbttagcompound.func_74774_a("Y", (byte)(j & 255));
            nbttagcompound.func_74773_a("Blocks", abyte1);
            nbttagcompound.func_74773_a("Data", nibblearray.func_177481_a());
            nbttagcompound.func_74773_a("SkyLight", nibblearray1.func_177481_a());
            nbttagcompound.func_74773_a("BlockLight", nibblearray2.func_177481_a());
            nbttaglist.func_74742_a(nbttagcompound);
         }
      }

      p_76690_1_.func_74782_a("Sections", nbttaglist);
      byte[] abyte = new byte[256];
      BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

      for(int l2 = 0; l2 < 16; ++l2) {
         for(int i3 = 0; i3 < 16; ++i3) {
            blockpos$mutableblockpos.func_181079_c(p_76690_0_.field_76701_k << 4 | l2, 0, p_76690_0_.field_76699_l << 4 | i3);
            abyte[i3 << 4 | l2] = (byte)(Biome.func_185362_a(p_76690_2_.func_180300_a(blockpos$mutableblockpos, Biomes.field_180279_ad)) & 255);
         }
      }

      p_76690_1_.func_74773_a("Biomes", abyte);
      p_76690_1_.func_74782_a("Entities", p_76690_0_.field_76702_h);
      p_76690_1_.func_74782_a("TileEntities", p_76690_0_.field_151564_i);
      if (p_76690_0_.field_151563_j != null) {
         p_76690_1_.func_74782_a("TileTicks", p_76690_0_.field_151563_j);
      }

   }

   public static class AnvilConverterData {
      public long field_76698_a;
      public boolean field_76696_b;
      public byte[] field_76697_c;
      public NibbleArrayReader field_76694_d;
      public NibbleArrayReader field_76695_e;
      public NibbleArrayReader field_76692_f;
      public byte[] field_76693_g;
      public NBTTagList field_76702_h;
      public NBTTagList field_151564_i;
      public NBTTagList field_151563_j;
      public final int field_76701_k;
      public final int field_76699_l;

      public AnvilConverterData(int p_i1999_1_, int p_i1999_2_) {
         this.field_76701_k = p_i1999_1_;
         this.field_76699_l = p_i1999_2_;
      }
   }
}
