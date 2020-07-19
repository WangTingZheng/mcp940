package net.minecraft.src;

import java.util.Arrays;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ChunkCache;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;

public class ChunkCacheOF implements IBlockAccess
{
    private ChunkCache chunkCache;
    private int posX;
    private int posY;
    private int posZ;
    private int[] combinedLights;
    private IBlockState[] blockStates;
    private static ArrayCache cacheCombinedLights = new ArrayCache(Integer.TYPE, 16);
    private static ArrayCache cacheBlockStates = new ArrayCache(IBlockState.class, 16);
    private static final int ARRAY_SIZE = 8000;

    public ChunkCacheOF(ChunkCache p_i22_1_, BlockPos p_i22_2_, int p_i22_3_)
    {
        this.chunkCache = p_i22_1_;
        this.posX = p_i22_2_.getX() - p_i22_3_;
        this.posY = p_i22_2_.getY() - p_i22_3_;
        this.posZ = p_i22_2_.getZ() - p_i22_3_;
    }

    public int getCombinedLight(BlockPos pos, int lightValue)
    {
        if (this.combinedLights == null)
        {
            int k = this.chunkCache.getCombinedLight(pos, lightValue);

            if (Config.isDynamicLights() && !this.getBlockState(pos).isOpaqueCube())
            {
                k = DynamicLights.getCombinedLight(pos, k);
            }

            return k;
        }
        else
        {
            int i = this.getPositionIndex(pos);

            if (i >= 0 && i < this.combinedLights.length)
            {
                int j = this.combinedLights[i];

                if (j == -1)
                {
                    j = this.chunkCache.getCombinedLight(pos, lightValue);

                    if (Config.isDynamicLights() && !this.getBlockState(pos).isOpaqueCube())
                    {
                        j = DynamicLights.getCombinedLight(pos, j);
                    }

                    this.combinedLights[i] = j;
                }

                return j;
            }
            else
            {
                return this.chunkCache.getCombinedLight(pos, lightValue);
            }
        }
    }

    public IBlockState getBlockState(BlockPos pos)
    {
        if (this.blockStates == null)
        {
            return this.chunkCache.getBlockState(pos);
        }
        else
        {
            int i = this.getPositionIndex(pos);

            if (i >= 0 && i < this.blockStates.length)
            {
                IBlockState iblockstate = this.blockStates[i];

                if (iblockstate == null)
                {
                    iblockstate = this.chunkCache.getBlockState(pos);
                    this.blockStates[i] = iblockstate;
                }

                return iblockstate;
            }
            else
            {
                return this.chunkCache.getBlockState(pos);
            }
        }
    }

    private int getPositionIndex(BlockPos p_getPositionIndex_1_)
    {
        int i = p_getPositionIndex_1_.getX() - this.posX;
        int j = p_getPositionIndex_1_.getY() - this.posY;
        int k = p_getPositionIndex_1_.getZ() - this.posZ;
        return i * 400 + k * 20 + j;
    }

    public void renderStart()
    {
        if (this.combinedLights == null)
        {
            this.combinedLights = (int[])cacheCombinedLights.allocate(8000);
        }

        Arrays.fill(this.combinedLights, -1);

        if (this.blockStates == null)
        {
            this.blockStates = (IBlockState[])cacheBlockStates.allocate(8000);
        }

        Arrays.fill(this.blockStates, (Object)null);
    }

    public void renderFinish()
    {
        cacheCombinedLights.free(this.combinedLights);
        this.combinedLights = null;
        cacheBlockStates.free(this.blockStates);
        this.blockStates = null;
    }

    public boolean isEmpty()
    {
        return this.chunkCache.isEmpty();
    }

    public Biome getBiome(BlockPos pos)
    {
        return this.chunkCache.getBiome(pos);
    }

    public int getStrongPower(BlockPos pos, EnumFacing direction)
    {
        return this.chunkCache.getStrongPower(pos, direction);
    }

    public TileEntity getTileEntity(BlockPos pos)
    {
        return this.chunkCache.getTileEntity(pos, Chunk.EnumCreateEntityType.CHECK);
    }

    public TileEntity getTileEntity(BlockPos p_getTileEntity_1_, Chunk.EnumCreateEntityType p_getTileEntity_2_)
    {
        return this.chunkCache.getTileEntity(p_getTileEntity_1_, p_getTileEntity_2_);
    }

    public WorldType getWorldType()
    {
        return this.chunkCache.getWorldType();
    }

    /**
     * Checks to see if an air block exists at the provided location. Note that this only checks to see if the blocks
     * material is set to air, meaning it is possible for non-vanilla blocks to still pass this check.
     */
    public boolean isAirBlock(BlockPos pos)
    {
        return this.chunkCache.isAirBlock(pos);
    }

    public boolean isSideSolid(BlockPos p_isSideSolid_1_, EnumFacing p_isSideSolid_2_, boolean p_isSideSolid_3_)
    {
        return Reflector.callBoolean(this.chunkCache, Reflector.ForgeChunkCache_isSideSolid, p_isSideSolid_1_, p_isSideSolid_2_, p_isSideSolid_3_);
    }
}
