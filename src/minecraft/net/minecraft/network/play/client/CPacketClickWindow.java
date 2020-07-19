package net.minecraft.network.play.client;

import java.io.IOException;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;

public class CPacketClickWindow implements Packet<INetHandlerPlayServer>
{
    /** The id of the window which was clicked. 0 for player inventory. */
    private int windowId;

    /** Id of the clicked slot */
    private int slotId;

    /** Button used */
    private int packedClickData;

    /** A unique number for the action, used for transaction handling */
    private short actionNumber;

    /** The item stack present in the slot */
    private ItemStack clickedItem = ItemStack.EMPTY;

    /** Inventory operation mode */
    private ClickType mode;

    public CPacketClickWindow()
    {
    }

    public CPacketClickWindow(int windowIdIn, int slotIdIn, int usedButtonIn, ClickType modeIn, ItemStack clickedItemIn, short actionNumberIn)
    {
        this.windowId = windowIdIn;
        this.slotId = slotIdIn;
        this.packedClickData = usedButtonIn;
        this.clickedItem = clickedItemIn.copy();
        this.actionNumber = actionNumberIn;
        this.mode = modeIn;
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    public void processPacket(INetHandlerPlayServer handler)
    {
        handler.processClickWindow(this);
    }

    /**
     * Reads the raw packet data from the data stream.
     */
    public void readPacketData(PacketBuffer buf) throws IOException
    {
        this.windowId = buf.readByte();
        this.slotId = buf.readShort();
        this.packedClickData = buf.readByte();
        this.actionNumber = buf.readShort();
        this.mode = (ClickType)buf.readEnumValue(ClickType.class);
        this.clickedItem = buf.readItemStack();
    }

    /**
     * Writes the raw packet data to the data stream.
     */
    public void writePacketData(PacketBuffer buf) throws IOException
    {
        buf.writeByte(this.windowId);
        buf.writeShort(this.slotId);
        buf.writeByte(this.packedClickData);
        buf.writeShort(this.actionNumber);
        buf.writeEnumValue(this.mode);
        buf.writeItemStack(this.clickedItem);
    }

    public int getWindowId()
    {
        return this.windowId;
    }

    public int getSlotId()
    {
        return this.slotId;
    }

    public int getUsedButton()
    {
        return this.packedClickData;
    }

    public short getActionNumber()
    {
        return this.actionNumber;
    }

    public ItemStack getClickedItem()
    {
        return this.clickedItem;
    }

    public ClickType getClickType()
    {
        return this.mode;
    }
}
