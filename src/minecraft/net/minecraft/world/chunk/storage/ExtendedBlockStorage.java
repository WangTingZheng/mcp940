package net.minecraft.world.chunk.storage;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.src.Reflector;
import net.minecraft.world.chunk.BlockStateContainer;
import net.minecraft.world.chunk.NibbleArray;

public class ExtendedBlockStorage
{
    /**
     * Contains the bottom-most Y block represented by this ExtendedBlockStorage. Typically a multiple of 16.
     */
    private final int yBase;

    /**
     * A total count of the number of non-air blocks in this block storage's Chunk.
     */
    private int blockRefCount;

    /**
     * Contains the number of blocks in this block storage's parent chunk that require random ticking. Used to cull the
     * Chunk from random tick updates for performance reasons.
     */
    private int tickRefCount;
    private final BlockStateContainer data;

    /** The NibbleArray containing a block of Block-light data. */
    private NibbleArray blockLight;

    /**
     * The NibbleArray containing skylight data.
     *  
     * Will be null if the provider for the world the chunk containing this block storage does not {@linkplain
     * net.minecraft.world.WorldProvider#hasSkylight have skylight}.
     */
    private NibbleArray skyLight;

    public ExtendedBlockStorage(int y, boolean storeSkylight)
    {
        this.yBase = y;
        this.data = new BlockStateContainer();
        this.blockLight = new NibbleArray();

        if (storeSkylight)
        {
            this.skyLight = new NibbleArray();
        }
    }

    public IBlockState get(int x, int y, int z)
    {
        return this.data.get(x, y, z);
    }

    public void set(int x, int y, int z, IBlockState state)
    {
        if (Reflector.IExtendedBlockState.isInstance(state))
        {
            state = (IBlockState)Reflector.call(state, Reflector.IExtendedBlockState_getClean);
        }

        IBlockState iblockstate = this.get(x, y, z);
        Block block = iblockstate.getBlock();
        Block block1 = state.getBlock();

        if (block != Blocks.AIR)
        {
            --this.blockRefCount;

            if (block.getTickRandomly())
            {
                --this.tickRefCount;
            }
        }

        if (block1 != Blocks.AIR)
        {
            ++this.blockRefCount;

            if (block1.getTickRandomly())
            {
                ++this.tickRefCount;
            }
        }

        this.data.set(x, y, z, state);
    }

    /**
     * Returns whether or not this block storage's Chunk is fully empty, based on its internal reference count.
     */
    public boolean isEmpty()
    {
        return this.blockRefCount == 0;
    }

    /**
     * Returns whether or not this block storage's Chunk will require random ticking, used to avoid looping through
     * random block ticks when there are no blocks that would randomly tick.
     */
    public boolean needsRandomTick()
    {
        return this.tickRefCount > 0;
    }

    /**
     * Returns the Y location of this ExtendedBlockStorage.
     */
    public int getYLocation()
    {
        return this.yBase;
    }

    /**
     * Sets the saved Sky-light value in the extended block storage structure.
     */
    public void setSkyLight(int x, int y, int z, int value)
    {
        this.skyLight.set(x, y, z, value);
    }

    /**
     * Gets the saved Sky-light value in the extended block storage structure.
     */
    public int getSkyLight(int x, int y, int z)
    {
        return this.skyLight.get(x, y, z);
    }

    /**
     * Sets the saved Block-light value in the extended block storage structure.
     */
    public void setBlockLight(int x, int y, int z, int value)
    {
        this.blockLight.set(x, y, z, value);
    }

    /**
     * Gets the saved Block-light value in the extended block storage structure.
     */
    public int getBlockLight(int x, int y, int z)
    {
        return this.blockLight.get(x, y, z);
    }

    public void recalculateRefCounts()
    {
        IBlockState iblockstate = Blocks.AIR.getDefaultState();
        int i = 0;
        int j = 0;

        for (int k = 0; k < 16; ++k)
        {
            for (int l = 0; l < 16; ++l)
            {
                for (int i1 = 0; i1 < 16; ++i1)
                {
                    IBlockState iblockstate1 = this.data.get(i1, k, l);

                    if (iblockstate1 != iblockstate)
                    {
                        ++i;
                        Block block = iblockstate1.getBlock();

                        if (block.getTickRandomly())
                        {
                            ++j;
                        }
                    }
                }
            }
        }

        this.blockRefCount = i;
        this.tickRefCount = j;
    }

    public BlockStateContainer getData()
    {
        return this.data;
    }

    /**
     * Returns the NibbleArray instance containing Block-light data.
     */
    public NibbleArray getBlockLight()
    {
        return this.blockLight;
    }

    /**
     * Returns the NibbleArray instance containing Sky-light data.
     */
    public NibbleArray getSkyLight()
    {
        return this.skyLight;
    }

    /**
     * Sets the NibbleArray instance used for Block-light values in this particular storage block.
     */
    public void setBlockLight(NibbleArray newBlocklightArray)
    {
        this.blockLight = newBlocklightArray;
    }

    /**
     * Sets the NibbleArray instance used for Sky-light values in this particular storage block.
     */
    public void setSkyLight(NibbleArray newSkylightArray)
    {
        this.skyLight = newSkylightArray;
    }
}
