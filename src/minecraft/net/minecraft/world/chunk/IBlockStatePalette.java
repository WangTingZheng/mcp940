package net.minecraft.world.chunk;

import javax.annotation.Nullable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.network.PacketBuffer;

public interface IBlockStatePalette
{
    int idFor(IBlockState state);

    @Nullable

    /**
     * Gets the block state by the palette id.
     */
    IBlockState getBlockState(int indexKey);

    void read(PacketBuffer buf);

    void write(PacketBuffer buf);

    int getSerializedSize();
}
