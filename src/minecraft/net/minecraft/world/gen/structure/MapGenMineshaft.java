package net.minecraft.world.gen.structure;

import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeMesa;

public class MapGenMineshaft extends MapGenStructure
{
    private double chance = 0.004D;

    public MapGenMineshaft()
    {
    }

    public String getStructureName()
    {
        return "Mineshaft";
    }

    public MapGenMineshaft(Map<String, String> p_i2034_1_)
    {
        for (Entry<String, String> entry : p_i2034_1_.entrySet())
        {
            if (((String)entry.getKey()).equals("chance"))
            {
                this.chance = MathHelper.getDouble(entry.getValue(), this.chance);
            }
        }
    }

    protected boolean canSpawnStructureAtCoords(int chunkX, int chunkZ)
    {
        return this.rand.nextDouble() < this.chance && this.rand.nextInt(80) < Math.max(Math.abs(chunkX), Math.abs(chunkZ));
    }

    public BlockPos getNearestStructurePos(World worldIn, BlockPos pos, boolean findUnexplored)
    {
        int i = 1000;
        int j = pos.getX() >> 4;
        int k = pos.getZ() >> 4;

        for (int l = 0; l <= 1000; ++l)
        {
            for (int i1 = -l; i1 <= l; ++i1)
            {
                boolean flag = i1 == -l || i1 == l;

                for (int j1 = -l; j1 <= l; ++j1)
                {
                    boolean flag1 = j1 == -l || j1 == l;

                    if (flag || flag1)
                    {
                        int k1 = j + i1;
                        int l1 = k + j1;
                        this.rand.setSeed((long)(k1 ^ l1) ^ worldIn.getSeed());
                        this.rand.nextInt();

                        if (this.canSpawnStructureAtCoords(k1, l1) && (!findUnexplored || !worldIn.isChunkGeneratedAt(k1, l1)))
                        {
                            return new BlockPos((k1 << 4) + 8, 64, (l1 << 4) + 8);
                        }
                    }
                }
            }
        }

        return null;
    }

    protected StructureStart getStructureStart(int chunkX, int chunkZ)
    {
        Biome biome = this.world.getBiome(new BlockPos((chunkX << 4) + 8, 64, (chunkZ << 4) + 8));
        MapGenMineshaft.Type mapgenmineshaft$type = biome instanceof BiomeMesa ? MapGenMineshaft.Type.MESA : MapGenMineshaft.Type.NORMAL;
        return new StructureMineshaftStart(this.world, this.rand, chunkX, chunkZ, mapgenmineshaft$type);
    }

    public static enum Type
    {
        NORMAL,
        MESA;

        public static MapGenMineshaft.Type byId(int id)
        {
            return id >= 0 && id < values().length ? values()[id] : NORMAL;
        }
    }
}
