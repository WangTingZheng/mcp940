package net.minecraft.network.play.client;

import com.google.common.collect.Lists;
import java.io.IOException;
import java.util.List;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;

public class CPacketRecipePlacement implements Packet<INetHandlerPlayServer>
{
    private int containerId;
    private short uid;
    private List<CPacketRecipePlacement.ItemMove> moveItemsFromGrid;
    private List<CPacketRecipePlacement.ItemMove> moveItemsToGrid;

    public CPacketRecipePlacement()
    {
    }

    public CPacketRecipePlacement(int p_i47425_1_, List<CPacketRecipePlacement.ItemMove> p_i47425_2_, List<CPacketRecipePlacement.ItemMove> p_i47425_3_, short p_i47425_4_)
    {
        this.containerId = p_i47425_1_;
        this.uid = p_i47425_4_;
        this.moveItemsFromGrid = p_i47425_2_;
        this.moveItemsToGrid = p_i47425_3_;
    }

    public int getContainerId()
    {
        return this.containerId;
    }

    public short getUid()
    {
        return this.uid;
    }

    /**
     * Reads the raw packet data from the data stream.
     */
    public void readPacketData(PacketBuffer buf) throws IOException
    {
        this.containerId = buf.readByte();
        this.uid = buf.readShort();
        this.moveItemsFromGrid = this.readMoveItems(buf);
        this.moveItemsToGrid = this.readMoveItems(buf);
    }

    private List<CPacketRecipePlacement.ItemMove> readMoveItems(PacketBuffer p_192611_1_) throws IOException
    {
        int i = p_192611_1_.readShort();
        List<CPacketRecipePlacement.ItemMove> list = Lists.<CPacketRecipePlacement.ItemMove>newArrayListWithCapacity(i);

        for (int j = 0; j < i; ++j)
        {
            ItemStack itemstack = p_192611_1_.readItemStack();
            byte b0 = p_192611_1_.readByte();
            byte b1 = p_192611_1_.readByte();
            list.add(new CPacketRecipePlacement.ItemMove(itemstack, b0, b1));
        }

        return list;
    }

    /**
     * Writes the raw packet data to the data stream.
     */
    public void writePacketData(PacketBuffer buf) throws IOException
    {
        buf.writeByte(this.containerId);
        buf.writeShort(this.uid);
        this.writeMoveItems(buf, this.moveItemsFromGrid);
        this.writeMoveItems(buf, this.moveItemsToGrid);
    }

    private void writeMoveItems(PacketBuffer buffer, List<CPacketRecipePlacement.ItemMove> p_192612_2_)
    {
        buffer.writeShort(p_192612_2_.size());

        for (CPacketRecipePlacement.ItemMove cpacketrecipeplacement$itemmove : p_192612_2_)
        {
            buffer.writeItemStack(cpacketrecipeplacement$itemmove.stack);
            buffer.writeByte(cpacketrecipeplacement$itemmove.srcSlot);
            buffer.writeByte(cpacketrecipeplacement$itemmove.destSlot);
        }
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    public void processPacket(INetHandlerPlayServer handler)
    {
        handler.handleRecipePlacement(this);
    }

    public List<CPacketRecipePlacement.ItemMove> getMoveItemsToGrid()
    {
        return this.moveItemsToGrid;
    }

    public List<CPacketRecipePlacement.ItemMove> getMoveItemsFromGrid()
    {
        return this.moveItemsFromGrid;
    }

    public static class ItemMove
    {
        public ItemStack stack;
        public int srcSlot;
        public int destSlot;

        public ItemMove(ItemStack p_i47401_1_, int p_i47401_2_, int p_i47401_3_)
        {
            this.stack = p_i47401_1_.copy();
            this.srcSlot = p_i47401_2_;
            this.destSlot = p_i47401_3_;
        }
    }
}
