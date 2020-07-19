package net.minecraft.world.biome;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BiomeColorHelper
{
    private static final BiomeColorHelper.ColorResolver GRASS_COLOR = new BiomeColorHelper.ColorResolver()
    {
        public int getColorAtPos(Biome biome, BlockPos blockPosition)
        {
            return biome.getGrassColorAtPos(blockPosition);
        }
    };
    private static final BiomeColorHelper.ColorResolver FOLIAGE_COLOR = new BiomeColorHelper.ColorResolver()
    {
        public int getColorAtPos(Biome biome, BlockPos blockPosition)
        {
            return biome.getFoliageColorAtPos(blockPosition);
        }
    };
    private static final BiomeColorHelper.ColorResolver WATER_COLOR = new BiomeColorHelper.ColorResolver()
    {
        public int getColorAtPos(Biome biome, BlockPos blockPosition)
        {
            return biome.getWaterColor();
        }
    };

    private static int getColorAtPos(IBlockAccess blockAccess, BlockPos pos, BiomeColorHelper.ColorResolver colorResolver)
    {
        int i = 0;
        int j = 0;
        int k = 0;

        for (BlockPos.MutableBlockPos blockpos$mutableblockpos : BlockPos.getAllInBoxMutable(pos.add(-1, 0, -1), pos.add(1, 0, 1)))
        {
            int l = colorResolver.getColorAtPos(blockAccess.getBiome(blockpos$mutableblockpos), blockpos$mutableblockpos);
            i += (l & 16711680) >> 16;
            j += (l & 65280) >> 8;
            k += l & 255;
        }

        return (i / 9 & 255) << 16 | (j / 9 & 255) << 8 | k / 9 & 255;
    }

    public static int getGrassColorAtPos(IBlockAccess blockAccess, BlockPos pos)
    {
        return getColorAtPos(blockAccess, pos, GRASS_COLOR);
    }

    public static int getFoliageColorAtPos(IBlockAccess blockAccess, BlockPos pos)
    {
        return getColorAtPos(blockAccess, pos, FOLIAGE_COLOR);
    }

    public static int getWaterColorAtPos(IBlockAccess blockAccess, BlockPos pos)
    {
        return getColorAtPos(blockAccess, pos, WATER_COLOR);
    }

    interface ColorResolver
    {
        int getColorAtPos(Biome biome, BlockPos blockPosition);
    }
}
