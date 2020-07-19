package net.minecraft.world.chunk.storage;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.src.Reflector;
import net.minecraft.world.chunk.BlockStateContainer;
import net.minecraft.world.chunk.NibbleArray;

/**
 * 描述一个section的类
 * 一个section由16*16*16个block组成
 */
public class ExtendedBlockStorage
{
    /**
     * Contains the bottom-most Y block represented by this ExtendedBlockStorage. Typically a multiple of 16.
     * 存储着这个能代表这个section的最底层y轴的方块，通常是16的倍数
     */
    private final int yBase;

    /**
     * A total count of the number of non-air blocks in this block storage's Chunk.
     * 在这个section中，非空气方块的数量
     */
    private int blockRefCount;

    /**
     * Contains the number of blocks in this block storage's parent chunk that require random ticking. Used to cull the
     * Chunk from random tick updates for performance reasons.
     * 包含此section所属的父chunk中被标记的随机ticking的方块数量，由于性能问题，通常会在随机tick更新中剔除一些方块
     * 一般情况下，在一个chunk中会随机选择3个方块作为随机更新方块，在一个随机刻中，这些方块将会被更新
     */
    private int tickRefCount;
    private final BlockStateContainer data;

    /**
     * The NibbleArray containing a block of Block-light data.
     * 一个包含光照（点光源/局部光照？？？）数据的半字节数组
     */
    private NibbleArray blockLight;

    /**
     * The NibbleArray containing skylight data.
     *  一个包含skylight（全局光照？？？）数据的半字节数组
     * Will be null if the provider for the world the chunk containing this block storage does not {@linkplain
     * net.minecraft.world.WorldProvider# hasSkylight have skylight}.
     *
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

    /**
     * 设置某一个位置的方块的状态
     * @param x 方块在一个chunk的的x的位置
     * @param y 方块在一个chunk的的y的位置
     * @param z 方块在一个chunk的的z的位置
     * @param state 一个方块的状态
     */
    public void set(int x, int y, int z, IBlockState state)
    {
        if (Reflector.IExtendedBlockState.isInstance(state)) //判断这个state是不是由net.minecraftforge.common.property.IExtendedBlockState类继承IBlockState实现的
        {
            state = (IBlockState)Reflector.call(state, Reflector.IExtendedBlockState_getClean);
        }

        IBlockState iblockstate = this.get(x, y, z);  //从这个section中获得这个方块的状态，可能是旧的方块
        Block block = iblockstate.getBlock(); //从section中的这个状态中获得方块
        Block block1 = state.getBlock(); //从传入的状态中获得方块，可能是新的方块

        //下面这段要从一个section中剔除旧的方块
        if (block != Blocks.AIR)  //如果旧方块不是空气（不为空）
        {
            --this.blockRefCount; //就减少这个区块的非方块的数量，毕竟要把这个方块代替了

            if (block.getTickRandomly()) //如果这个方块是被挑选为随机刻更新的方块
            {
                --this.tickRefCount; //那么就减少随机刻方块的数量
            }
        }

        //下面这段要在一个section中添加新方块
        if (block1 != Blocks.AIR) //如果旧的方块不是空气方块（不为空）
        {
            ++this.blockRefCount; //增加非空方块的数量

            if (block1.getTickRandomly()) //如果这个方块被选择在随机刻中更新
            {
                ++this.tickRefCount; //增加随机刻方块的数量
            }
        }

        this.data.set(x, y, z, state);  //通过真正的set方法传入新的方块
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
     *
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

    /**
     * 通过遍历检查的方式重新计算整个section中的非空方块的数量和随机刻方块的数量
     */
    public void recalculateRefCounts()
    {
        IBlockState iblockstate = Blocks.AIR.getDefaultState();  //获取空气的方块状态
        int i = 0;
        int j = 0;

        for (int k = 0; k < 16; ++k) //遍历一个section中的所有方块
        {
            for (int l = 0; l < 16; ++l)
            {
                for (int i1 = 0; i1 < 16; ++i1)
                {
                    IBlockState iblockstate1 = this.data.get(i1, k, l); //从当前section中获取这个方块的状态

                    if (iblockstate1 != iblockstate)  //如果这个方块不是空气
                    {
                        ++i; //i的计数加一，也就是非空方块计数加一
                        Block block = iblockstate1.getBlock(); //从状态中获得方块

                        if (block.getTickRandomly())  //如果方块是随机刻方块
                        {
                            ++j;//j的计数加一，也就是随机刻方块数量加一
                        }
                    }
                }
            }
        }

        this.blockRefCount = i;  //重新赋值非空方块数量
        this.tickRefCount = j;  //重新赋值随机刻方块数量
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
