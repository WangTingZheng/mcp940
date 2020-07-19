package net.minecraft.world.biome;

import java.util.Random;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BiomeVoidDecorator extends BiomeDecorator
{
    public void decorate(World worldIn, Random random, Biome biome, BlockPos pos)
    {
        BlockPos blockpos = worldIn.getSpawnPoint();
        int i = 16;
        double d0 = blockpos.distanceSq(pos.add(8, blockpos.getY(), 8));

        if (d0 <= 1024.0D)
        {
            BlockPos blockpos1 = new BlockPos(blockpos.getX() - 16, blockpos.getY() - 1, blockpos.getZ() - 16);
            BlockPos blockpos2 = new BlockPos(blockpos.getX() + 16, blockpos.getY() - 1, blockpos.getZ() + 16);
            BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos(blockpos1);

            for (int j = pos.getZ(); j < pos.getZ() + 16; ++j)
            {
                for (int k = pos.getX(); k < pos.getX() + 16; ++k)
                {
                    if (j >= blockpos1.getZ() && j <= blockpos2.getZ() && k >= blockpos1.getX() && k <= blockpos2.getX())
                    {
                        blockpos$mutableblockpos.setPos(k, blockpos$mutableblockpos.getY(), j);

                        if (blockpos.getX() == k && blockpos.getZ() == j)
                        {
                            worldIn.setBlockState(blockpos$mutableblockpos, Blocks.COBBLESTONE.getDefaultState(), 2);
                        }
                        else
                        {
                            worldIn.setBlockState(blockpos$mutableblockpos, Blocks.STONE.getDefaultState(), 2);
                        }
                    }
                }
            }
        }
    }
}
