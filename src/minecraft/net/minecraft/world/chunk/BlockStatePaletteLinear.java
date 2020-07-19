package net.minecraft.world.chunk;

import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.network.PacketBuffer;

public class BlockStatePaletteLinear implements IBlockStatePalette
{
    private final IBlockState[] states;
    private final IBlockStatePaletteResizer resizeHandler;
    private final int bits;
    private int arraySize;

    public BlockStatePaletteLinear(int bitsIn, IBlockStatePaletteResizer resizeHandlerIn)
    {
        this.states = new IBlockState[1 << bitsIn];
        this.bits = bitsIn;
        this.resizeHandler = resizeHandlerIn;
    }

    public int idFor(IBlockState state)
    {
        for (int i = 0; i < this.arraySize; ++i)
        {
            if (this.states[i] == state)
            {
                return i;
            }
        }

        int j = this.arraySize;

        if (j < this.states.length)
        {
            this.states[j] = state;
            ++this.arraySize;
            return j;
        }
        else
        {
            return this.resizeHandler.onResize(this.bits + 1, state);
        }
    }

    @Nullable

    /**
     * Gets the block state by the palette id.
     */
    public IBlockState getBlockState(int indexKey)
    {
        return indexKey >= 0 && indexKey < this.arraySize ? this.states[indexKey] : null;
    }

    public void read(PacketBuffer buf)
    {
        this.arraySize = buf.readVarInt();

        for (int i = 0; i < this.arraySize; ++i)
        {
            this.states[i] = Block.BLOCK_STATE_IDS.getByValue(buf.readVarInt());
        }
    }

    public void write(PacketBuffer buf)
    {
        buf.writeVarInt(this.arraySize);

        for (int i = 0; i < this.arraySize; ++i)
        {
            buf.writeVarInt(Block.BLOCK_STATE_IDS.get(this.states[i]));
        }
    }

    public int getSerializedSize()
    {
        int i = PacketBuffer.getVarIntSize(this.arraySize);

        for (int j = 0; j < this.arraySize; ++j)
        {
            i += PacketBuffer.getVarIntSize(Block.BLOCK_STATE_IDS.get(this.states[j]));
        }

        return i;
    }
}
