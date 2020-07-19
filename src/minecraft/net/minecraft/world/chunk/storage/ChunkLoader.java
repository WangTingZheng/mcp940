package net.minecraft.world.chunk.storage;

import net.minecraft.init.Biomes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.chunk.NibbleArray;

public class ChunkLoader
{
    public static ChunkLoader.AnvilConverterData load(NBTTagCompound nbt)
    {
        int i = nbt.getInteger("xPos");
        int j = nbt.getInteger("zPos");
        ChunkLoader.AnvilConverterData chunkloader$anvilconverterdata = new ChunkLoader.AnvilConverterData(i, j);
        chunkloader$anvilconverterdata.blocks = nbt.getByteArray("Blocks");
        chunkloader$anvilconverterdata.data = new NibbleArrayReader(nbt.getByteArray("Data"), 7);
        chunkloader$anvilconverterdata.skyLight = new NibbleArrayReader(nbt.getByteArray("SkyLight"), 7);
        chunkloader$anvilconverterdata.blockLight = new NibbleArrayReader(nbt.getByteArray("BlockLight"), 7);
        chunkloader$anvilconverterdata.heightmap = nbt.getByteArray("HeightMap");
        chunkloader$anvilconverterdata.terrainPopulated = nbt.getBoolean("TerrainPopulated");
        chunkloader$anvilconverterdata.entities = nbt.getTagList("Entities", 10);
        chunkloader$anvilconverterdata.tileEntities = nbt.getTagList("TileEntities", 10);
        chunkloader$anvilconverterdata.tileTicks = nbt.getTagList("TileTicks", 10);

        try
        {
            chunkloader$anvilconverterdata.lastUpdated = nbt.getLong("LastUpdate");
        }
        catch (ClassCastException var5)
        {
            chunkloader$anvilconverterdata.lastUpdated = (long)nbt.getInteger("LastUpdate");
        }

        return chunkloader$anvilconverterdata;
    }

    public static void convertToAnvilFormat(ChunkLoader.AnvilConverterData converterData, NBTTagCompound compound, BiomeProvider provider)
    {
        compound.setInteger("xPos", converterData.x);
        compound.setInteger("zPos", converterData.z);
        compound.setLong("LastUpdate", converterData.lastUpdated);
        int[] aint = new int[converterData.heightmap.length];

        for (int i = 0; i < converterData.heightmap.length; ++i)
        {
            aint[i] = converterData.heightmap[i];
        }

        compound.setIntArray("HeightMap", aint);
        compound.setBoolean("TerrainPopulated", converterData.terrainPopulated);
        NBTTagList nbttaglist = new NBTTagList();

        for (int j = 0; j < 8; ++j)
        {
            boolean flag = true;

            for (int k = 0; k < 16 && flag; ++k)
            {
                for (int l = 0; l < 16 && flag; ++l)
                {
                    for (int i1 = 0; i1 < 16; ++i1)
                    {
                        int j1 = k << 11 | i1 << 7 | l + (j << 4);
                        int k1 = converterData.blocks[j1];

                        if (k1 != 0)
                        {
                            flag = false;
                            break;
                        }
                    }
                }
            }

            if (!flag)
            {
                byte[] abyte1 = new byte[4096];
                NibbleArray nibblearray = new NibbleArray();
                NibbleArray nibblearray1 = new NibbleArray();
                NibbleArray nibblearray2 = new NibbleArray();

                for (int j3 = 0; j3 < 16; ++j3)
                {
                    for (int l1 = 0; l1 < 16; ++l1)
                    {
                        for (int i2 = 0; i2 < 16; ++i2)
                        {
                            int j2 = j3 << 11 | i2 << 7 | l1 + (j << 4);
                            int k2 = converterData.blocks[j2];
                            abyte1[l1 << 8 | i2 << 4 | j3] = (byte)(k2 & 255);
                            nibblearray.set(j3, l1, i2, converterData.data.get(j3, l1 + (j << 4), i2));
                            nibblearray1.set(j3, l1, i2, converterData.skyLight.get(j3, l1 + (j << 4), i2));
                            nibblearray2.set(j3, l1, i2, converterData.blockLight.get(j3, l1 + (j << 4), i2));
                        }
                    }
                }

                NBTTagCompound nbttagcompound = new NBTTagCompound();
                nbttagcompound.setByte("Y", (byte)(j & 255));
                nbttagcompound.setByteArray("Blocks", abyte1);
                nbttagcompound.setByteArray("Data", nibblearray.getData());
                nbttagcompound.setByteArray("SkyLight", nibblearray1.getData());
                nbttagcompound.setByteArray("BlockLight", nibblearray2.getData());
                nbttaglist.appendTag(nbttagcompound);
            }
        }

        compound.setTag("Sections", nbttaglist);
        byte[] abyte = new byte[256];
        BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

        for (int l2 = 0; l2 < 16; ++l2)
        {
            for (int i3 = 0; i3 < 16; ++i3)
            {
                blockpos$mutableblockpos.setPos(converterData.x << 4 | l2, 0, converterData.z << 4 | i3);
                abyte[i3 << 4 | l2] = (byte)(Biome.getIdForBiome(provider.getBiome(blockpos$mutableblockpos, Biomes.DEFAULT)) & 255);
            }
        }

        compound.setByteArray("Biomes", abyte);
        compound.setTag("Entities", converterData.entities);
        compound.setTag("TileEntities", converterData.tileEntities);

        if (converterData.tileTicks != null)
        {
            compound.setTag("TileTicks", converterData.tileTicks);
        }
    }

    public static class AnvilConverterData
    {
        public long lastUpdated;
        public boolean terrainPopulated;
        public byte[] heightmap;
        public NibbleArrayReader blockLight;
        public NibbleArrayReader skyLight;
        public NibbleArrayReader data;
        public byte[] blocks;
        public NBTTagList entities;
        public NBTTagList tileEntities;
        public NBTTagList tileTicks;
        public final int x;
        public final int z;

        public AnvilConverterData(int xIn, int zIn)
        {
            this.x = xIn;
            this.z = zIn;
        }
    }
}
