package net.minecraft.world.chunk;

import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.BitArray;
import net.minecraft.util.math.MathHelper;

public class BlockStateContainer implements IBlockStatePaletteResizer
{
    private static final IBlockStatePalette REGISTRY_BASED_PALETTE = new BlockStatePaletteRegistry();
    protected static final IBlockState AIR_BLOCK_STATE = Blocks.AIR.getDefaultState();
    protected BitArray storage;
    protected IBlockStatePalette palette;
    private int bits;

    public BlockStateContainer()
    {
        this.setBits(4);
    }

    private static int getIndex(int x, int y, int z)
    {
        return y << 8 | z << 4 | x;
    }

    private void setBits(int bitsIn)
    {
        if (bitsIn != this.bits)
        {
            this.bits = bitsIn;

            if (this.bits <= 4)
            {
                this.bits = 4;
                this.palette = new BlockStatePaletteLinear(this.bits, this);
            }
            else if (this.bits <= 8)
            {
                this.palette = new BlockStatePaletteHashMap(this.bits, this);
            }
            else
            {
                this.palette = REGISTRY_BASED_PALETTE;
                this.bits = MathHelper.log2DeBruijn(Block.BLOCK_STATE_IDS.size());
            }

            this.palette.idFor(AIR_BLOCK_STATE);
            this.storage = new BitArray(this.bits, 4096);
        }
    }

    public int onResize(int bits, IBlockState state)
    {
        BitArray bitarray = this.storage;
        IBlockStatePalette iblockstatepalette = this.palette;
        this.setBits(bits);

        for (int i = 0; i < bitarray.size(); ++i)
        {
            IBlockState iblockstate = iblockstatepalette.getBlockState(bitarray.getAt(i));

            if (iblockstate != null)
            {
                this.set(i, iblockstate);
            }
        }

        return this.palette.idFor(state);
    }

    public void set(int x, int y, int z, IBlockState state)
    {
        this.set(getIndex(x, y, z), state);
    }

    protected void set(int index, IBlockState state)
    {
        int i = this.palette.idFor(state);
        this.storage.setAt(index, i);
    }

    public IBlockState get(int x, int y, int z)
    {
        return this.get(getIndex(x, y, z));
    }

    protected IBlockState get(int index)
    {
        IBlockState iblockstate = this.palette.getBlockState(this.storage.getAt(index));
        return iblockstate == null ? AIR_BLOCK_STATE : iblockstate;
    }

    public void read(PacketBuffer buf)
    {
        int i = buf.readByte();

        if (this.bits != i)
        {
            this.setBits(i);
        }

        this.palette.read(buf);
        buf.readLongArray(this.storage.getBackingLongArray());
    }

    public void write(PacketBuffer buf)
    {
        buf.writeByte(this.bits);
        this.palette.write(buf);
        buf.writeLongArray(this.storage.getBackingLongArray());
    }

    @Nullable
    public NibbleArray getDataForNBT(byte[] blockIds, NibbleArray data)
    {
        NibbleArray nibblearray = null;

        for (int i = 0; i < 4096; ++i)
        {
            int j = Block.BLOCK_STATE_IDS.get(this.get(i));
            int k = i & 15;
            int l = i >> 8 & 15;
            int i1 = i >> 4 & 15;

            if ((j >> 12 & 15) != 0)
            {
                if (nibblearray == null)
                {
                    nibblearray = new NibbleArray();
                }

                nibblearray.set(k, l, i1, j >> 12 & 15);
            }

            blockIds[i] = (byte)(j >> 4 & 255);
            data.set(k, l, i1, j & 15);
        }

        return nibblearray;
    }

    public void setDataFromNBT(byte[] blockIds, NibbleArray data, @Nullable NibbleArray blockIdExtension)
    {
        for (int i = 0; i < 4096; ++i)
        {
            int j = i & 15;
            int k = i >> 8 & 15;
            int l = i >> 4 & 15;
            int i1 = blockIdExtension == null ? 0 : blockIdExtension.get(j, k, l);
            int j1 = i1 << 12 | (blockIds[i] & 255) << 4 | data.get(j, k, l);
            this.set(i, Block.BLOCK_STATE_IDS.getByValue(j1));
        }
    }

    public int getSerializedSize()
    {
        return 1 + this.palette.getSerializedSize() + PacketBuffer.getVarIntSize(this.storage.size()) + this.storage.getBackingLongArray().length * 8;
    }
}
