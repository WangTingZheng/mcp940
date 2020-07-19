package net.minecraft.world.biome;

import java.util.Random;
import net.minecraft.block.BlockSilverfish;
import net.minecraft.entity.passive.EntityLlama;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenTaiga2;
import net.minecraft.world.gen.feature.WorldGenerator;

public class BiomeHills extends Biome
{
    private final WorldGenerator silverfishSpawner = new WorldGenMinable(Blocks.MONSTER_EGG.getDefaultState().withProperty(BlockSilverfish.VARIANT, BlockSilverfish.EnumType.STONE), 9);
    private final WorldGenTaiga2 spruceGenerator = new WorldGenTaiga2(false);
    private final BiomeHills.Type type;

    protected BiomeHills(BiomeHills.Type p_i46710_1_, Biome.BiomeProperties properties)
    {
        super(properties);

        if (p_i46710_1_ == BiomeHills.Type.EXTRA_TREES)
        {
            this.decorator.treesPerChunk = 3;
        }

        this.spawnableCreatureList.add(new Biome.SpawnListEntry(EntityLlama.class, 5, 4, 6));
        this.type = p_i46710_1_;
    }

    public WorldGenAbstractTree getRandomTreeFeature(Random rand)
    {
        return (WorldGenAbstractTree)(rand.nextInt(3) > 0 ? this.spruceGenerator : super.getRandomTreeFeature(rand));
    }

    public void decorate(World worldIn, Random rand, BlockPos pos)
    {
        super.decorate(worldIn, rand, pos);
        int i = 3 + rand.nextInt(6);

        for (int j = 0; j < i; ++j)
        {
            int k = rand.nextInt(16);
            int l = rand.nextInt(28) + 4;
            int i1 = rand.nextInt(16);
            BlockPos blockpos = pos.add(k, l, i1);

            if (worldIn.getBlockState(blockpos).getBlock() == Blocks.STONE)
            {
                worldIn.setBlockState(blockpos, Blocks.EMERALD_ORE.getDefaultState(), 2);
            }
        }

        for (int j1 = 0; j1 < 7; ++j1)
        {
            int k1 = rand.nextInt(16);
            int l1 = rand.nextInt(64);
            int i2 = rand.nextInt(16);
            this.silverfishSpawner.generate(worldIn, rand, pos.add(k1, l1, i2));
        }
    }

    public void genTerrainBlocks(World worldIn, Random rand, ChunkPrimer chunkPrimerIn, int x, int z, double noiseVal)
    {
        this.topBlock = Blocks.GRASS.getDefaultState();
        this.fillerBlock = Blocks.DIRT.getDefaultState();

        if ((noiseVal < -1.0D || noiseVal > 2.0D) && this.type == BiomeHills.Type.MUTATED)
        {
            this.topBlock = Blocks.GRAVEL.getDefaultState();
            this.fillerBlock = Blocks.GRAVEL.getDefaultState();
        }
        else if (noiseVal > 1.0D && this.type != BiomeHills.Type.EXTRA_TREES)
        {
            this.topBlock = Blocks.STONE.getDefaultState();
            this.fillerBlock = Blocks.STONE.getDefaultState();
        }

        this.generateBiomeTerrain(worldIn, rand, chunkPrimerIn, x, z, noiseVal);
    }

    public static enum Type
    {
        NORMAL,
        EXTRA_TREES,
        MUTATED;
    }
}
