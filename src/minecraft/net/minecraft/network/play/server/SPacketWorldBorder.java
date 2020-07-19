package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.world.border.WorldBorder;

public class SPacketWorldBorder implements Packet<INetHandlerPlayClient>
{
    private SPacketWorldBorder.Action action;
    private int size;
    private double centerX;
    private double centerZ;
    private double targetSize;
    private double diameter;
    private long timeUntilTarget;
    private int warningTime;
    private int warningDistance;

    public SPacketWorldBorder()
    {
    }

    public SPacketWorldBorder(WorldBorder border, SPacketWorldBorder.Action actionIn)
    {
        this.action = actionIn;
        this.centerX = border.getCenterX();
        this.centerZ = border.getCenterZ();
        this.diameter = border.getDiameter();
        this.targetSize = border.getTargetSize();
        this.timeUntilTarget = border.getTimeUntilTarget();
        this.size = border.getSize();
        this.warningDistance = border.getWarningDistance();
        this.warningTime = border.getWarningTime();
    }

    /**
     * Reads the raw packet data from the data stream.
     */
    public void readPacketData(PacketBuffer buf) throws IOException
    {
        this.action = (SPacketWorldBorder.Action)buf.readEnumValue(SPacketWorldBorder.Action.class);

        switch (this.action)
        {
            case SET_SIZE:
                this.targetSize = buf.readDouble();
                break;

            case LERP_SIZE:
                this.diameter = buf.readDouble();
                this.targetSize = buf.readDouble();
                this.timeUntilTarget = buf.readVarLong();
                break;

            case SET_CENTER:
                this.centerX = buf.readDouble();
                this.centerZ = buf.readDouble();
                break;

            case SET_WARNING_BLOCKS:
                this.warningDistance = buf.readVarInt();
                break;

            case SET_WARNING_TIME:
                this.warningTime = buf.readVarInt();
                break;

            case INITIALIZE:
                this.centerX = buf.readDouble();
                this.centerZ = buf.readDouble();
                this.diameter = buf.readDouble();
                this.targetSize = buf.readDouble();
                this.timeUntilTarget = buf.readVarLong();
                this.size = buf.readVarInt();
                this.warningDistance = buf.readVarInt();
                this.warningTime = buf.readVarInt();
        }
    }

    /**
     * Writes the raw packet data to the data stream.
     */
    public void writePacketData(PacketBuffer buf) throws IOException
    {
        buf.writeEnumValue(this.action);

        switch (this.action)
        {
            case SET_SIZE:
                buf.writeDouble(this.targetSize);
                break;

            case LERP_SIZE:
                buf.writeDouble(this.diameter);
                buf.writeDouble(this.targetSize);
                buf.writeVarLong(this.timeUntilTarget);
                break;

            case SET_CENTER:
                buf.writeDouble(this.centerX);
                buf.writeDouble(this.centerZ);
                break;

            case SET_WARNING_BLOCKS:
                buf.writeVarInt(this.warningDistance);
                break;

            case SET_WARNING_TIME:
                buf.writeVarInt(this.warningTime);
                break;

            case INITIALIZE:
                buf.writeDouble(this.centerX);
                buf.writeDouble(this.centerZ);
                buf.writeDouble(this.diameter);
                buf.writeDouble(this.targetSize);
                buf.writeVarLong(this.timeUntilTarget);
                buf.writeVarInt(this.size);
                buf.writeVarInt(this.warningDistance);
                buf.writeVarInt(this.warningTime);
        }
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    public void processPacket(INetHandlerPlayClient handler)
    {
        handler.handleWorldBorder(this);
    }

    public void apply(WorldBorder border)
    {
        switch (this.action)
        {
            case SET_SIZE:
                border.setTransition(this.targetSize);
                break;

            case LERP_SIZE:
                border.setTransition(this.diameter, this.targetSize, this.timeUntilTarget);
                break;

            case SET_CENTER:
                border.setCenter(this.centerX, this.centerZ);
                break;

            case SET_WARNING_BLOCKS:
                border.setWarningDistance(this.warningDistance);
                break;

            case SET_WARNING_TIME:
                border.setWarningTime(this.warningTime);
                break;

            case INITIALIZE:
                border.setCenter(this.centerX, this.centerZ);

                if (this.timeUntilTarget > 0L)
                {
                    border.setTransition(this.diameter, this.targetSize, this.timeUntilTarget);
                }
                else
                {
                    border.setTransition(this.targetSize);
                }

                border.setSize(this.size);
                border.setWarningDistance(this.warningDistance);
                border.setWarningTime(this.warningTime);
        }
    }

    public static enum Action
    {
        SET_SIZE,
        LERP_SIZE,
        SET_CENTER,
        INITIALIZE,
        SET_WARNING_TIME,
        SET_WARNING_BLOCKS;
    }
}
