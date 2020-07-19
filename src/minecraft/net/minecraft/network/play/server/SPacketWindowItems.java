package net.minecraft.network.play.server;

import java.io.IOException;
import java.util.List;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.NonNullList;

public class SPacketWindowItems implements Packet<INetHandlerPlayClient>
{
    private int windowId;
    private List<ItemStack> itemStacks;

    public SPacketWindowItems()
    {
    }

    public SPacketWindowItems(int p_i47317_1_, NonNullList<ItemStack> p_i47317_2_)
    {
        this.windowId = p_i47317_1_;
        this.itemStacks = NonNullList.<ItemStack>withSize(p_i47317_2_.size(), ItemStack.EMPTY);

        for (int i = 0; i < this.itemStacks.size(); ++i)
        {
            ItemStack itemstack = p_i47317_2_.get(i);
            this.itemStacks.set(i, itemstack.copy());
        }
    }

    /**
     * Reads the raw packet data from the data stream.
     */
    public void readPacketData(PacketBuffer buf) throws IOException
    {
        this.windowId = buf.readUnsignedByte();
        int i = buf.readShort();
        this.itemStacks = NonNullList.<ItemStack>withSize(i, ItemStack.EMPTY);

        for (int j = 0; j < i; ++j)
        {
            this.itemStacks.set(j, buf.readItemStack());
        }
    }

    /**
     * Writes the raw packet data to the data stream.
     */
    public void writePacketData(PacketBuffer buf) throws IOException
    {
        buf.writeByte(this.windowId);
        buf.writeShort(this.itemStacks.size());

        for (ItemStack itemstack : this.itemStacks)
        {
            buf.writeItemStack(itemstack);
        }
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    public void processPacket(INetHandlerPlayClient handler)
    {
        handler.handleWindowItems(this);
    }

    public int getWindowId()
    {
        return this.windowId;
    }

    public List<ItemStack> getItemStacks()
    {
        return this.itemStacks;
    }
}
