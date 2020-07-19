package net.minecraft.world.end;

import java.util.List;
import java.util.Random;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldServer;
import net.minecraft.world.biome.BiomeEndDecorator;
import net.minecraft.world.gen.feature.WorldGenSpikes;

public enum DragonSpawnManager
{
    START {
        public void process(WorldServer worldIn, DragonFightManager manager, List<EntityEnderCrystal> crystals, int ticks, BlockPos pos)
        {
            BlockPos blockpos = new BlockPos(0, 128, 0);

            for (EntityEnderCrystal entityendercrystal : crystals)
            {
                entityendercrystal.setBeamTarget(blockpos);
            }

            manager.setRespawnState(PREPARING_TO_SUMMON_PILLARS);
        }
    },
    PREPARING_TO_SUMMON_PILLARS {
        public void process(WorldServer worldIn, DragonFightManager manager, List<EntityEnderCrystal> crystals, int ticks, BlockPos pos)
        {
            if (ticks < 100)
            {
                if (ticks == 0 || ticks == 50 || ticks == 51 || ticks == 52 || ticks >= 95)
                {
                    worldIn.playEvent(3001, new BlockPos(0, 128, 0), 0);
                }
            }
            else
            {
                manager.setRespawnState(SUMMONING_PILLARS);
            }
        }
    },
    SUMMONING_PILLARS {
        public void process(WorldServer worldIn, DragonFightManager manager, List<EntityEnderCrystal> crystals, int ticks, BlockPos pos)
        {
            int i = 40;
            boolean flag = ticks % 40 == 0;
            boolean flag1 = ticks % 40 == 39;

            if (flag || flag1)
            {
                WorldGenSpikes.EndSpike[] aworldgenspikes$endspike = BiomeEndDecorator.getSpikesForWorld(worldIn);
                int j = ticks / 40;

                if (j < aworldgenspikes$endspike.length)
                {
                    WorldGenSpikes.EndSpike worldgenspikes$endspike = aworldgenspikes$endspike[j];

                    if (flag)
                    {
                        for (EntityEnderCrystal entityendercrystal : crystals)
                        {
                            entityendercrystal.setBeamTarget(new BlockPos(worldgenspikes$endspike.getCenterX(), worldgenspikes$endspike.getHeight() + 1, worldgenspikes$endspike.getCenterZ()));
                        }
                    }
                    else
                    {
                        int k = 10;

                        for (BlockPos.MutableBlockPos blockpos$mutableblockpos : BlockPos.getAllInBoxMutable(new BlockPos(worldgenspikes$endspike.getCenterX() - 10, worldgenspikes$endspike.getHeight() - 10, worldgenspikes$endspike.getCenterZ() - 10), new BlockPos(worldgenspikes$endspike.getCenterX() + 10, worldgenspikes$endspike.getHeight() + 10, worldgenspikes$endspike.getCenterZ() + 10)))
                        {
                            worldIn.setBlockToAir(blockpos$mutableblockpos);
                        }

                        worldIn.createExplosion((Entity)null, (double)((float)worldgenspikes$endspike.getCenterX() + 0.5F), (double)worldgenspikes$endspike.getHeight(), (double)((float)worldgenspikes$endspike.getCenterZ() + 0.5F), 5.0F, true);
                        WorldGenSpikes worldgenspikes = new WorldGenSpikes();
                        worldgenspikes.setSpike(worldgenspikes$endspike);
                        worldgenspikes.setCrystalInvulnerable(true);
                        worldgenspikes.setBeamTarget(new BlockPos(0, 128, 0));
                        worldgenspikes.generate(worldIn, new Random(), new BlockPos(worldgenspikes$endspike.getCenterX(), 45, worldgenspikes$endspike.getCenterZ()));
                    }
                }
                else if (flag)
                {
                    manager.setRespawnState(SUMMONING_DRAGON);
                }
            }
        }
    },
    SUMMONING_DRAGON {
        public void process(WorldServer worldIn, DragonFightManager manager, List<EntityEnderCrystal> crystals, int ticks, BlockPos pos)
        {
            if (ticks >= 100)
            {
                manager.setRespawnState(END);
                manager.resetSpikeCrystals();

                for (EntityEnderCrystal entityendercrystal : crystals)
                {
                    entityendercrystal.setBeamTarget((BlockPos)null);
                    worldIn.createExplosion(entityendercrystal, entityendercrystal.posX, entityendercrystal.posY, entityendercrystal.posZ, 6.0F, false);
                    entityendercrystal.setDead();
                }
            }
            else if (ticks >= 80)
            {
                worldIn.playEvent(3001, new BlockPos(0, 128, 0), 0);
            }
            else if (ticks == 0)
            {
                for (EntityEnderCrystal entityendercrystal1 : crystals)
                {
                    entityendercrystal1.setBeamTarget(new BlockPos(0, 128, 0));
                }
            }
            else if (ticks < 5)
            {
                worldIn.playEvent(3001, new BlockPos(0, 128, 0), 0);
            }
        }
    },
    END {
        public void process(WorldServer worldIn, DragonFightManager manager, List<EntityEnderCrystal> crystals, int ticks, BlockPos pos)
        {
        }
    };

    private DragonSpawnManager()
    {
    }

    public abstract void process(WorldServer worldIn, DragonFightManager manager, List<EntityEnderCrystal> crystals, int ticks, BlockPos pos);
}
