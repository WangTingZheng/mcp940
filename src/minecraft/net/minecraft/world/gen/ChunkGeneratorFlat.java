package net.minecraft.world.gen;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.feature.WorldGenDungeons;
import net.minecraft.world.gen.feature.WorldGenLakes;
import net.minecraft.world.gen.structure.MapGenMineshaft;
import net.minecraft.world.gen.structure.MapGenScatteredFeature;
import net.minecraft.world.gen.structure.MapGenStronghold;
import net.minecraft.world.gen.structure.MapGenStructure;
import net.minecraft.world.gen.structure.MapGenVillage;
import net.minecraft.world.gen.structure.StructureOceanMonument;

public class ChunkGeneratorFlat implements IChunkGenerator
{
    private final World world;
    private final Random random;
    private final IBlockState[] cachedBlockIDs = new IBlockState[256];
    private final FlatGeneratorInfo flatWorldGenInfo;
    private final Map<String, MapGenStructure> structureGenerators = new HashMap<String, MapGenStructure>();
    private final boolean hasDecoration;
    private final boolean hasDungeons;
    private WorldGenLakes waterLakeGenerator;
    private WorldGenLakes lavaLakeGenerator;

    public ChunkGeneratorFlat(World worldIn, long seed, boolean generateStructures, String flatGeneratorSettings)
    {
        this.world = worldIn;
        this.random = new Random(seed);
        this.flatWorldGenInfo = FlatGeneratorInfo.createFlatGeneratorFromString(flatGeneratorSettings);

        if (generateStructures)
        {
            Map<String, Map<String, String>> map = this.flatWorldGenInfo.getWorldFeatures();

            if (map.containsKey("village"))
            {
                Map<String, String> map1 = (Map)map.get("village");

                if (!map1.containsKey("size"))
                {
                    map1.put("size", "1");
                }

                this.structureGenerators.put("Village", new MapGenVillage(map1));
            }

            if (map.containsKey("biome_1"))
            {
                this.structureGenerators.put("Temple", new MapGenScatteredFeature(map.get("biome_1")));
            }

            if (map.containsKey("mineshaft"))
            {
                this.structureGenerators.put("Mineshaft", new MapGenMineshaft(map.get("mineshaft")));
            }

            if (map.containsKey("stronghold"))
            {
                this.structureGenerators.put("Stronghold", new MapGenStronghold(map.get("stronghold")));
            }

            if (map.containsKey("oceanmonument"))
            {
                this.structureGenerators.put("Monument", new StructureOceanMonument(map.get("oceanmonument")));
            }
        }

        if (this.flatWorldGenInfo.getWorldFeatures().containsKey("lake"))
        {
            this.waterLakeGenerator = new WorldGenLakes(Blocks.WATER);
        }

        if (this.flatWorldGenInfo.getWorldFeatures().containsKey("lava_lake"))
        {
            this.lavaLakeGenerator = new WorldGenLakes(Blocks.LAVA);
        }

        this.hasDungeons = this.flatWorldGenInfo.getWorldFeatures().containsKey("dungeon");
        int j = 0;
        int k = 0;
        boolean flag = true;

        for (FlatLayerInfo flatlayerinfo : this.flatWorldGenInfo.getFlatLayers())
        {
            for (int i = flatlayerinfo.getMinY(); i < flatlayerinfo.getMinY() + flatlayerinfo.getLayerCount(); ++i)
            {
                IBlockState iblockstate = flatlayerinfo.getLayerMaterial();

                if (iblockstate.getBlock() != Blocks.AIR)
                {
                    flag = false;
                    this.cachedBlockIDs[i] = iblockstate;
                }
            }

            if (flatlayerinfo.getLayerMaterial().getBlock() == Blocks.AIR)
            {
                k += flatlayerinfo.getLayerCount();
            }
            else
            {
                j += flatlayerinfo.getLayerCount() + k;
                k = 0;
            }
        }

        worldIn.setSeaLevel(j);
        this.hasDecoration = flag && this.flatWorldGenInfo.getBiome() != Biome.getIdForBiome(Biomes.VOID) ? false : this.flatWorldGenInfo.getWorldFeatures().containsKey("decoration");
    }

    /**
     * Generates the chunk at the specified position, from scratch
     */
    public Chunk generateChunk(int x, int z)
    {
        ChunkPrimer chunkprimer = new ChunkPrimer();

        for (int i = 0; i < this.cachedBlockIDs.length; ++i)
        {
            IBlockState iblockstate = this.cachedBlockIDs[i];

            if (iblockstate != null)
            {
                for (int j = 0; j < 16; ++j)
                {
                    for (int k = 0; k < 16; ++k)
                    {
                        chunkprimer.setBlockState(j, i, k, iblockstate);
                    }
                }
            }
        }

        for (MapGenBase mapgenbase : this.structureGenerators.values())
        {
            mapgenbase.generate(this.world, x, z, chunkprimer);
        }

        Chunk chunk = new Chunk(this.world, chunkprimer, x, z);
        Biome[] abiome = this.world.getBiomeProvider().getBiomes((Biome[])null, x * 16, z * 16, 16, 16);
        byte[] abyte = chunk.getBiomeArray();

        for (int l = 0; l < abyte.length; ++l)
        {
            abyte[l] = (byte)Biome.getIdForBiome(abiome[l]);
        }

        chunk.generateSkylightMap();
        return chunk;
    }

    /**
     * Generate initial structures in this chunk, e.g. mineshafts, temples, lakes, and dungeons
     */
    public void populate(int x, int z)
    {
        int i = x * 16;
        int j = z * 16;
        BlockPos blockpos = new BlockPos(i, 0, j);
        Biome biome = this.world.getBiome(new BlockPos(i + 16, 0, j + 16));
        boolean flag = false;
        this.random.setSeed(this.world.getSeed());
        long k = this.random.nextLong() / 2L * 2L + 1L;
        long l = this.random.nextLong() / 2L * 2L + 1L;
        this.random.setSeed((long)x * k + (long)z * l ^ this.world.getSeed());
        ChunkPos chunkpos = new ChunkPos(x, z);

        for (MapGenStructure mapgenstructure : this.structureGenerators.values())
        {
            boolean flag1 = mapgenstructure.generateStructure(this.world, this.random, chunkpos);

            if (mapgenstructure instanceof MapGenVillage)
            {
                flag |= flag1;
            }
        }

        if (this.waterLakeGenerator != null && !flag && this.random.nextInt(4) == 0)
        {
            this.waterLakeGenerator.generate(this.world, this.random, blockpos.add(this.random.nextInt(16) + 8, this.random.nextInt(256), this.random.nextInt(16) + 8));
        }

        if (this.lavaLakeGenerator != null && !flag && this.random.nextInt(8) == 0)
        {
            BlockPos blockpos1 = blockpos.add(this.random.nextInt(16) + 8, this.random.nextInt(this.random.nextInt(248) + 8), this.random.nextInt(16) + 8);

            if (blockpos1.getY() < this.world.getSeaLevel() || this.random.nextInt(10) == 0)
            {
                this.lavaLakeGenerator.generate(this.world, this.random, blockpos1);
            }
        }

        if (this.hasDungeons)
        {
            for (int i1 = 0; i1 < 8; ++i1)
            {
                (new WorldGenDungeons()).generate(this.world, this.random, blockpos.add(this.random.nextInt(16) + 8, this.random.nextInt(256), this.random.nextInt(16) + 8));
            }
        }

        if (this.hasDecoration)
        {
            biome.decorate(this.world, this.random, blockpos);
        }
    }

    /**
     * Called to generate additional structures after initial worldgen, used by ocean monuments
     */
    public boolean generateStructures(Chunk chunkIn, int x, int z)
    {
        return false;
    }

    public List<Biome.SpawnListEntry> getPossibleCreatures(EnumCreatureType creatureType, BlockPos pos)
    {
        Biome biome = this.world.getBiome(pos);
        return biome.getSpawnableList(creatureType);
    }

    @Nullable
    public BlockPos getNearestStructurePos(World worldIn, String structureName, BlockPos position, boolean findUnexplored)
    {
        MapGenStructure mapgenstructure = this.structureGenerators.get(structureName);
        return mapgenstructure != null ? mapgenstructure.getNearestStructurePos(worldIn, position, findUnexplored) : null;
    }

    public boolean isInsideStructure(World worldIn, String structureName, BlockPos pos)
    {
        MapGenStructure mapgenstructure = this.structureGenerators.get(structureName);
        return mapgenstructure != null ? mapgenstructure.isInsideStructure(pos) : false;
    }

    /**
     * Recreates data about structures intersecting given chunk (used for example by getPossibleCreatures), without
     * placing any blocks. When called for the first time before any chunk is generated - also initializes the internal
     * state needed by getPossibleCreatures.
     */
    public void recreateStructures(Chunk chunkIn, int x, int z)
    {
        for (MapGenStructure mapgenstructure : this.structureGenerators.values())
        {
            mapgenstructure.generate(this.world, x, z, (ChunkPrimer)null);
        }
    }
}
