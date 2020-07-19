package net.minecraft.network.play.server;

import java.io.IOException;
import javax.annotation.Nullable;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.ResourceLocation;

public class SPacketSelectAdvancementsTab implements Packet<INetHandlerPlayClient>
{
    @Nullable
    private ResourceLocation tab;

    public SPacketSelectAdvancementsTab()
    {
    }

    public SPacketSelectAdvancementsTab(@Nullable ResourceLocation p_i47596_1_)
    {
        this.tab = p_i47596_1_;
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    public void processPacket(INetHandlerPlayClient handler)
    {
        handler.handleSelectAdvancementsTab(this);
    }

    /**
     * Reads the raw packet data from the data stream.
     */
    public void readPacketData(PacketBuffer buf) throws IOException
    {
        if (buf.readBoolean())
        {
            this.tab = buf.readResourceLocation();
        }
    }

    /**
     * Writes the raw packet data to the data stream.
     */
    public void writePacketData(PacketBuffer buf) throws IOException
    {
        buf.writeBoolean(this.tab != null);

        if (this.tab != null)
        {
            buf.writeResourceLocation(this.tab);
        }
    }

    @Nullable
    public ResourceLocation getTab()
    {
        return this.tab;
    }
}
