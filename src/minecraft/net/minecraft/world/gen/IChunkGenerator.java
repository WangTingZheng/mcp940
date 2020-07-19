package net.minecraft.world.gen;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;

public interface IChunkGenerator
{
    /**
     * Generates the chunk at the specified position, from scratch
     */
    Chunk generateChunk(int x, int z);

    /**
     * Generate initial structures in this chunk, e.g. mineshafts, temples, lakes, and dungeons
     */
    void populate(int x, int z);

    /**
     * Called to generate additional structures after initial worldgen, used by ocean monuments
     */
    boolean generateStructures(Chunk chunkIn, int x, int z);

    List<Biome.SpawnListEntry> getPossibleCreatures(EnumCreatureType creatureType, BlockPos pos);

    @Nullable
    BlockPos getNearestStructurePos(World worldIn, String structureName, BlockPos position, boolean findUnexplored);

    /**
     * Recreates data about structures intersecting given chunk (used for example by getPossibleCreatures), without
     * placing any blocks. When called for the first time before any chunk is generated - also initializes the internal
     * state needed by getPossibleCreatures.
     */
    void recreateStructures(Chunk chunkIn, int x, int z);

    boolean isInsideStructure(World worldIn, String structureName, BlockPos pos);
}
