package net.minecraft.world.chunk;

import javax.annotation.Nullable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.network.PacketBuffer;

/**
 * 这个BlockStatePalette似乎也是一种方块状态，wiki上讲它存储着一个方块的以下信息：
 *  Name: Block-ID-name of the block
 *  Properties: Only for blocks that have different states. Properties of the block state
 *      property-name: The value of the block state property.
 *  名字：方块的id名字
 *  属性：只对有不同状态的方块，方块状态的属性
 *      属性名称：方块状态的属性值
 */
public interface IBlockStatePalette
{
    /**
     * 从BlockState对象上获取id
     * @param state IBlockState对象
     * @return id
     */
    int idFor(IBlockState state);

    /**
     * 使用id生成BlockState
     * @param indexKey 方块调色板id
     * @return IBlockState 对象
     */
    @Nullable

    /**
     * Gets the block state by the palette id.
     */
    IBlockState getBlockState(int indexKey);

    void read(PacketBuffer buf);

    void write(PacketBuffer buf);

    int getSerializedSize();
}
