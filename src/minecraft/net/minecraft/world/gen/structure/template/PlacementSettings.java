package net.minecraft.world.gen.structure.template;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.gen.structure.StructureBoundingBox;

public class PlacementSettings
{
    private Mirror mirror = Mirror.NONE;
    private Rotation rotation = Rotation.NONE;
    private boolean ignoreEntities;
    @Nullable

    /**
     * the type of block in the world that will get replaced by the structure
     */
    private Block replacedBlock;
    @Nullable

    /** the chunk the structure is within */
    private ChunkPos chunk;
    @Nullable

    /** the bounds the structure is contained within */
    private StructureBoundingBox boundingBox;
    private boolean ignoreStructureBlock = true;
    private float integrity = 1.0F;
    @Nullable
    private Random random;
    @Nullable
    private Long setSeed;

    public PlacementSettings copy()
    {
        PlacementSettings placementsettings = new PlacementSettings();
        placementsettings.mirror = this.mirror;
        placementsettings.rotation = this.rotation;
        placementsettings.ignoreEntities = this.ignoreEntities;
        placementsettings.replacedBlock = this.replacedBlock;
        placementsettings.chunk = this.chunk;
        placementsettings.boundingBox = this.boundingBox;
        placementsettings.ignoreStructureBlock = this.ignoreStructureBlock;
        placementsettings.integrity = this.integrity;
        placementsettings.random = this.random;
        placementsettings.setSeed = this.setSeed;
        return placementsettings;
    }

    public PlacementSettings setMirror(Mirror mirrorIn)
    {
        this.mirror = mirrorIn;
        return this;
    }

    public PlacementSettings setRotation(Rotation rotationIn)
    {
        this.rotation = rotationIn;
        return this;
    }

    public PlacementSettings setIgnoreEntities(boolean ignoreEntitiesIn)
    {
        this.ignoreEntities = ignoreEntitiesIn;
        return this;
    }

    public PlacementSettings setReplacedBlock(Block replacedBlockIn)
    {
        this.replacedBlock = replacedBlockIn;
        return this;
    }

    public PlacementSettings setChunk(ChunkPos chunkPosIn)
    {
        this.chunk = chunkPosIn;
        return this;
    }

    public PlacementSettings setBoundingBox(StructureBoundingBox boundingBoxIn)
    {
        this.boundingBox = boundingBoxIn;
        return this;
    }

    public PlacementSettings setSeed(@Nullable Long seedIn)
    {
        this.setSeed = seedIn;
        return this;
    }

    public PlacementSettings setRandom(@Nullable Random randomIn)
    {
        this.random = randomIn;
        return this;
    }

    public PlacementSettings setIntegrity(float integrityIn)
    {
        this.integrity = integrityIn;
        return this;
    }

    public Mirror getMirror()
    {
        return this.mirror;
    }

    public PlacementSettings setIgnoreStructureBlock(boolean ignoreStructureBlockIn)
    {
        this.ignoreStructureBlock = ignoreStructureBlockIn;
        return this;
    }

    public Rotation getRotation()
    {
        return this.rotation;
    }

    public Random getRandom(@Nullable BlockPos seed)
    {
        if (this.random != null)
        {
            return this.random;
        }
        else if (this.setSeed != null)
        {
            return this.setSeed.longValue() == 0L ? new Random(System.currentTimeMillis()) : new Random(this.setSeed.longValue());
        }
        else if (seed == null)
        {
            return new Random(System.currentTimeMillis());
        }
        else
        {
            int i = seed.getX();
            int j = seed.getZ();
            return new Random((long)(i * i * 4987142 + i * 5947611) + (long)(j * j) * 4392871L + (long)(j * 389711) ^ 987234911L);
        }
    }

    public float getIntegrity()
    {
        return this.integrity;
    }

    public boolean getIgnoreEntities()
    {
        return this.ignoreEntities;
    }

    @Nullable
    public Block getReplacedBlock()
    {
        return this.replacedBlock;
    }

    @Nullable
    public StructureBoundingBox getBoundingBox()
    {
        if (this.boundingBox == null && this.chunk != null)
        {
            this.setBoundingBoxFromChunk();
        }

        return this.boundingBox;
    }

    public boolean getIgnoreStructureBlock()
    {
        return this.ignoreStructureBlock;
    }

    void setBoundingBoxFromChunk()
    {
        this.boundingBox = this.getBoundingBoxFromChunk(this.chunk);
    }

    @Nullable
    private StructureBoundingBox getBoundingBoxFromChunk(@Nullable ChunkPos pos)
    {
        if (pos == null)
        {
            return null;
        }
        else
        {
            int i = pos.x * 16;
            int j = pos.z * 16;
            return new StructureBoundingBox(i, 0, j, i + 16 - 1, 255, j + 16 - 1);
        }
    }
}
