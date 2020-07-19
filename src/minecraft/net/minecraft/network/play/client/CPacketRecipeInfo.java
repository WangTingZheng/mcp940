package net.minecraft.network.play.client;

import java.io.IOException;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;

public class CPacketRecipeInfo implements Packet<INetHandlerPlayServer>
{
    private CPacketRecipeInfo.Purpose purpose;
    private IRecipe recipe;
    private boolean isGuiOpen;
    private boolean filteringCraftable;

    public CPacketRecipeInfo()
    {
    }

    public CPacketRecipeInfo(IRecipe p_i47518_1_)
    {
        this.purpose = CPacketRecipeInfo.Purpose.SHOWN;
        this.recipe = p_i47518_1_;
    }

    public CPacketRecipeInfo(boolean p_i47424_1_, boolean p_i47424_2_)
    {
        this.purpose = CPacketRecipeInfo.Purpose.SETTINGS;
        this.isGuiOpen = p_i47424_1_;
        this.filteringCraftable = p_i47424_2_;
    }

    /**
     * Reads the raw packet data from the data stream.
     */
    public void readPacketData(PacketBuffer buf) throws IOException
    {
        this.purpose = (CPacketRecipeInfo.Purpose)buf.readEnumValue(CPacketRecipeInfo.Purpose.class);

        if (this.purpose == CPacketRecipeInfo.Purpose.SHOWN)
        {
            this.recipe = CraftingManager.getRecipeById(buf.readInt());
        }
        else if (this.purpose == CPacketRecipeInfo.Purpose.SETTINGS)
        {
            this.isGuiOpen = buf.readBoolean();
            this.filteringCraftable = buf.readBoolean();
        }
    }

    /**
     * Writes the raw packet data to the data stream.
     */
    public void writePacketData(PacketBuffer buf) throws IOException
    {
        buf.writeEnumValue(this.purpose);

        if (this.purpose == CPacketRecipeInfo.Purpose.SHOWN)
        {
            buf.writeInt(CraftingManager.getIDForRecipe(this.recipe));
        }
        else if (this.purpose == CPacketRecipeInfo.Purpose.SETTINGS)
        {
            buf.writeBoolean(this.isGuiOpen);
            buf.writeBoolean(this.filteringCraftable);
        }
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    public void processPacket(INetHandlerPlayServer handler)
    {
        handler.handleRecipeBookUpdate(this);
    }

    public CPacketRecipeInfo.Purpose getPurpose()
    {
        return this.purpose;
    }

    public IRecipe getRecipe()
    {
        return this.recipe;
    }

    public boolean isGuiOpen()
    {
        return this.isGuiOpen;
    }

    public boolean isFilteringCraftable()
    {
        return this.filteringCraftable;
    }

    public static enum Purpose
    {
        SHOWN,
        SETTINGS;
    }
}
