package net.minecraft.world.gen.structure;

import java.util.Random;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.ChunkGeneratorEnd;

public class MapGenEndCity extends MapGenStructure
{
    private final int citySpacing = 20;
    private final int minCitySeparation = 11;
    private final ChunkGeneratorEnd endProvider;

    public MapGenEndCity(ChunkGeneratorEnd p_i46665_1_)
    {
        this.endProvider = p_i46665_1_;
    }

    public String getStructureName()
    {
        return "EndCity";
    }

    protected boolean canSpawnStructureAtCoords(int chunkX, int chunkZ)
    {
        int i = chunkX;
        int j = chunkZ;

        if (chunkX < 0)
        {
            chunkX -= 19;
        }

        if (chunkZ < 0)
        {
            chunkZ -= 19;
        }

        int k = chunkX / 20;
        int l = chunkZ / 20;
        Random random = this.world.setRandomSeed(k, l, 10387313);
        k = k * 20;
        l = l * 20;
        k = k + (random.nextInt(9) + random.nextInt(9)) / 2;
        l = l + (random.nextInt(9) + random.nextInt(9)) / 2;

        if (i == k && j == l && this.endProvider.isIslandChunk(i, j))
        {
            int i1 = getYPosForStructure(i, j, this.endProvider);
            return i1 >= 60;
        }
        else
        {
            return false;
        }
    }

    protected StructureStart getStructureStart(int chunkX, int chunkZ)
    {
        return new MapGenEndCity.Start(this.world, this.endProvider, this.rand, chunkX, chunkZ);
    }

    public BlockPos getNearestStructurePos(World worldIn, BlockPos pos, boolean findUnexplored)
    {
        this.world = worldIn;
        return findNearestStructurePosBySpacing(worldIn, this, pos, 20, 11, 10387313, true, 100, findUnexplored);
    }

    private static int getYPosForStructure(int p_191070_0_, int p_191070_1_, ChunkGeneratorEnd p_191070_2_)
    {
        Random random = new Random((long)(p_191070_0_ + p_191070_1_ * 10387313));
        Rotation rotation = Rotation.values()[random.nextInt(Rotation.values().length)];
        ChunkPrimer chunkprimer = new ChunkPrimer();
        p_191070_2_.setBlocksInChunk(p_191070_0_, p_191070_1_, chunkprimer);
        int i = 5;
        int j = 5;

        if (rotation == Rotation.CLOCKWISE_90)
        {
            i = -5;
        }
        else if (rotation == Rotation.CLOCKWISE_180)
        {
            i = -5;
            j = -5;
        }
        else if (rotation == Rotation.COUNTERCLOCKWISE_90)
        {
            j = -5;
        }

        int k = chunkprimer.findGroundBlockIdx(7, 7);
        int l = chunkprimer.findGroundBlockIdx(7, 7 + j);
        int i1 = chunkprimer.findGroundBlockIdx(7 + i, 7);
        int j1 = chunkprimer.findGroundBlockIdx(7 + i, 7 + j);
        int k1 = Math.min(Math.min(k, l), Math.min(i1, j1));
        return k1;
    }

    public static class Start extends StructureStart
    {
        private boolean isSizeable;

        public Start()
        {
        }

        public Start(World worldIn, ChunkGeneratorEnd chunkProvider, Random random, int chunkX, int chunkZ)
        {
            super(chunkX, chunkZ);
            this.create(worldIn, chunkProvider, random, chunkX, chunkZ);
        }

        private void create(World worldIn, ChunkGeneratorEnd chunkProvider, Random rnd, int chunkX, int chunkZ)
        {
            Random random = new Random((long)(chunkX + chunkZ * 10387313));
            Rotation rotation = Rotation.values()[random.nextInt(Rotation.values().length)];
            int i = MapGenEndCity.getYPosForStructure(chunkX, chunkZ, chunkProvider);

            if (i < 60)
            {
                this.isSizeable = false;
            }
            else
            {
                BlockPos blockpos = new BlockPos(chunkX * 16 + 8, i, chunkZ * 16 + 8);
                StructureEndCityPieces.startHouseTower(worldIn.getSaveHandler().getStructureTemplateManager(), blockpos, rotation, this.components, rnd);
                this.updateBoundingBox();
                this.isSizeable = true;
            }
        }

        public boolean isSizeableStructure()
        {
            return this.isSizeable;
        }
    }
}
