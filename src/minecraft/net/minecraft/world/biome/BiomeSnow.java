package net.minecraft.world.biome;

import java.util.Iterator;
import java.util.Random;
import net.minecraft.entity.monster.EntityPolarBear;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityStray;
import net.minecraft.entity.passive.EntityRabbit;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenIcePath;
import net.minecraft.world.gen.feature.WorldGenIceSpike;
import net.minecraft.world.gen.feature.WorldGenTaiga2;

public class BiomeSnow extends Biome
{
    private final boolean superIcy;
    private final WorldGenIceSpike iceSpike = new WorldGenIceSpike();
    private final WorldGenIcePath icePatch = new WorldGenIcePath(4);

    public BiomeSnow(boolean superIcyIn, Biome.BiomeProperties properties)
    {
        super(properties);
        this.superIcy = superIcyIn;

        if (superIcyIn)
        {
            this.topBlock = Blocks.SNOW.getDefaultState();
        }

        this.spawnableCreatureList.clear();
        this.spawnableCreatureList.add(new Biome.SpawnListEntry(EntityRabbit.class, 10, 2, 3));
        this.spawnableCreatureList.add(new Biome.SpawnListEntry(EntityPolarBear.class, 1, 1, 2));
        Iterator<Biome.SpawnListEntry> iterator = this.spawnableMonsterList.iterator();

        while (iterator.hasNext())
        {
            Biome.SpawnListEntry biome$spawnlistentry = iterator.next();

            if (biome$spawnlistentry.entityClass == EntitySkeleton.class)
            {
                iterator.remove();
            }
        }

        this.spawnableMonsterList.add(new Biome.SpawnListEntry(EntitySkeleton.class, 20, 4, 4));
        this.spawnableMonsterList.add(new Biome.SpawnListEntry(EntityStray.class, 80, 4, 4));
    }

    /**
     * returns the chance a creature has to spawn.
     */
    public float getSpawningChance()
    {
        return 0.07F;
    }

    public void decorate(World worldIn, Random rand, BlockPos pos)
    {
        if (this.superIcy)
        {
            for (int i = 0; i < 3; ++i)
            {
                int j = rand.nextInt(16) + 8;
                int k = rand.nextInt(16) + 8;
                this.iceSpike.generate(worldIn, rand, worldIn.getHeight(pos.add(j, 0, k)));
            }

            for (int l = 0; l < 2; ++l)
            {
                int i1 = rand.nextInt(16) + 8;
                int j1 = rand.nextInt(16) + 8;
                this.icePatch.generate(worldIn, rand, worldIn.getHeight(pos.add(i1, 0, j1)));
            }
        }

        super.decorate(worldIn, rand, pos);
    }

    public WorldGenAbstractTree getRandomTreeFeature(Random rand)
    {
        return new WorldGenTaiga2(false);
    }
}
