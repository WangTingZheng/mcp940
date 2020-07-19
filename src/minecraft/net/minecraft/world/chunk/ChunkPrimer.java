package net.minecraft.world.chunk;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;

public class ChunkPrimer
{
    private static final IBlockState DEFAULT_STATE = Blocks.AIR.getDefaultState();
    //很可能是存储一个chunk中方块的id的，minecraft中一个section由16*16*16格的方块组成，16个section垂直排列，组成一个chunk，也就是一个chunk有16的四次方个方块，也就是65536
    private final char[] data = new char[65536];

    public IBlockState getBlockState(int x, int y, int z)
    {
        IBlockState iblockstate = Block.BLOCK_STATE_IDS.getByValue(this.data[getBlockIndex(x, y, z)]);
        return iblockstate == null ? DEFAULT_STATE : iblockstate;
    }

    public void setBlockState(int x, int y, int z, IBlockState state)
    {
        this.data[getBlockIndex(x, y, z)] = (char)Block.BLOCK_STATE_IDS.get(state);
    }

    /**
     * a<<b -> 把数字转换为二进制后左移b位，相当于a*2^b  a|b -> 把a和化为二进制后按位取或
     * 根据方块的坐标，获得方块在一个chunk中的id？？？这个坐标不是全局坐标，而是一个chunk内的局部坐标
     * 我们知道，按位取或的结果最大值，是由每一位中最长的哪一位决定的，这里的x，y都是1->16的，而z是1->16*16，然后x<<12表示的是x*4096，所以最大值是65536，而y的最大值是256
     * 所以这个函数的返回值的最大值是65536，也就是最后一个方块
     * 所以这个函数的作用是，输入一个chunk内的相对坐标，返回一个chunk内1-65536的唯一id
     * @param x 一个方块在某一个区块内的x坐标，范围是1-16
     * @param y 一个方块在某一个区块内的y坐标，范围是1-16
     * @param z 一个方块在某一个区块内的z坐标，也就是纵向的坐标，范围是1-256
     * @return 这个方块在这个chunk内的一个唯一id
     */
    private static int getBlockIndex(int x, int y, int z)
    {
        return x << 12 | z << 8 | y;
    }

    /**
     * Counting down from the highest block in the sky, find the first non-air block for the given location
     * (actually, looks like mostly checks x, z+1? And actually checks only the very top sky block of actual x, z)
     */
    public int findGroundBlockIdx(int x, int z)
    {
        int i = (x << 12 | z << 8) + 256 - 1;

        for (int j = 255; j >= 0; --j)
        {
            IBlockState iblockstate = Block.BLOCK_STATE_IDS.getByValue(this.data[i + j]);

            if (iblockstate != null && iblockstate != DEFAULT_STATE)
            {
                return j;
            }
        }

        return 0;
    }
}
