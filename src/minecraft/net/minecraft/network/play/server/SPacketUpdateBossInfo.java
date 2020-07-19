package net.minecraft.network.play.server;

import java.io.IOException;
import java.util.UUID;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.BossInfo;

public class SPacketUpdateBossInfo implements Packet<INetHandlerPlayClient>
{
    private UUID uniqueId;
    private SPacketUpdateBossInfo.Operation operation;
    private ITextComponent name;
    private float percent;
    private BossInfo.Color color;
    private BossInfo.Overlay overlay;
    private boolean darkenSky;
    private boolean playEndBossMusic;
    private boolean createFog;

    public SPacketUpdateBossInfo()
    {
    }

    public SPacketUpdateBossInfo(SPacketUpdateBossInfo.Operation operationIn, BossInfo data)
    {
        this.operation = operationIn;
        this.uniqueId = data.getUniqueId();
        this.name = data.getName();
        this.percent = data.getPercent();
        this.color = data.getColor();
        this.overlay = data.getOverlay();
        this.darkenSky = data.shouldDarkenSky();
        this.playEndBossMusic = data.shouldPlayEndBossMusic();
        this.createFog = data.shouldCreateFog();
    }

    /**
     * Reads the raw packet data from the data stream.
     */
    public void readPacketData(PacketBuffer buf) throws IOException
    {
        this.uniqueId = buf.readUniqueId();
        this.operation = (SPacketUpdateBossInfo.Operation)buf.readEnumValue(SPacketUpdateBossInfo.Operation.class);

        switch (this.operation)
        {
            case ADD:
                this.name = buf.readTextComponent();
                this.percent = buf.readFloat();
                this.color = (BossInfo.Color)buf.readEnumValue(BossInfo.Color.class);
                this.overlay = (BossInfo.Overlay)buf.readEnumValue(BossInfo.Overlay.class);
                this.setFlags(buf.readUnsignedByte());

            case REMOVE:
            default:
                break;

            case UPDATE_PCT:
                this.percent = buf.readFloat();
                break;

            case UPDATE_NAME:
                this.name = buf.readTextComponent();
                break;

            case UPDATE_STYLE:
                this.color = (BossInfo.Color)buf.readEnumValue(BossInfo.Color.class);
                this.overlay = (BossInfo.Overlay)buf.readEnumValue(BossInfo.Overlay.class);
                break;

            case UPDATE_PROPERTIES:
                this.setFlags(buf.readUnsignedByte());
        }
    }

    private void setFlags(int flags)
    {
        this.darkenSky = (flags & 1) > 0;
        this.playEndBossMusic = (flags & 2) > 0;
        this.createFog = (flags & 2) > 0;
    }

    /**
     * Writes the raw packet data to the data stream.
     */
    public void writePacketData(PacketBuffer buf) throws IOException
    {
        buf.writeUniqueId(this.uniqueId);
        buf.writeEnumValue(this.operation);

        switch (this.operation)
        {
            case ADD:
                buf.writeTextComponent(this.name);
                buf.writeFloat(this.percent);
                buf.writeEnumValue(this.color);
                buf.writeEnumValue(this.overlay);
                buf.writeByte(this.getFlags());

            case REMOVE:
            default:
                break;

            case UPDATE_PCT:
                buf.writeFloat(this.percent);
                break;

            case UPDATE_NAME:
                buf.writeTextComponent(this.name);
                break;

            case UPDATE_STYLE:
                buf.writeEnumValue(this.color);
                buf.writeEnumValue(this.overlay);
                break;

            case UPDATE_PROPERTIES:
                buf.writeByte(this.getFlags());
        }
    }

    private int getFlags()
    {
        int i = 0;

        if (this.darkenSky)
        {
            i |= 1;
        }

        if (this.playEndBossMusic)
        {
            i |= 2;
        }

        if (this.createFog)
        {
            i |= 2;
        }

        return i;
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    public void processPacket(INetHandlerPlayClient handler)
    {
        handler.handleUpdateBossInfo(this);
    }

    public UUID getUniqueId()
    {
        return this.uniqueId;
    }

    public SPacketUpdateBossInfo.Operation getOperation()
    {
        return this.operation;
    }

    public ITextComponent getName()
    {
        return this.name;
    }

    public float getPercent()
    {
        return this.percent;
    }

    public BossInfo.Color getColor()
    {
        return this.color;
    }

    public BossInfo.Overlay getOverlay()
    {
        return this.overlay;
    }

    public boolean shouldDarkenSky()
    {
        return this.darkenSky;
    }

    public boolean shouldPlayEndBossMusic()
    {
        return this.playEndBossMusic;
    }

    public boolean shouldCreateFog()
    {
        return this.createFog;
    }

    public static enum Operation
    {
        ADD,
        REMOVE,
        UPDATE_PCT,
        UPDATE_NAME,
        UPDATE_STYLE,
        UPDATE_PROPERTIES;
    }
}
