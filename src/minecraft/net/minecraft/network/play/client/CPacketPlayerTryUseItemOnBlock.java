package net.minecraft.network.play.client;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;

public class CPacketPlayerTryUseItemOnBlock implements Packet<INetHandlerPlayServer>
{
    private BlockPos position;
    private EnumFacing placedBlockDirection;
    private EnumHand hand;
    private float facingX;
    private float facingY;
    private float facingZ;

    public CPacketPlayerTryUseItemOnBlock()
    {
    }

    public CPacketPlayerTryUseItemOnBlock(BlockPos posIn, EnumFacing placedBlockDirectionIn, EnumHand handIn, float facingXIn, float facingYIn, float facingZIn)
    {
        this.position = posIn;
        this.placedBlockDirection = placedBlockDirectionIn;
        this.hand = handIn;
        this.facingX = facingXIn;
        this.facingY = facingYIn;
        this.facingZ = facingZIn;
    }

    /**
     * Reads the raw packet data from the data stream.
     */
    public void readPacketData(PacketBuffer buf) throws IOException
    {
        this.position = buf.readBlockPos();
        this.placedBlockDirection = (EnumFacing)buf.readEnumValue(EnumFacing.class);
        this.hand = (EnumHand)buf.readEnumValue(EnumHand.class);
        this.facingX = buf.readFloat();
        this.facingY = buf.readFloat();
        this.facingZ = buf.readFloat();
    }

    /**
     * Writes the raw packet data to the data stream.
     */
    public void writePacketData(PacketBuffer buf) throws IOException
    {
        buf.writeBlockPos(this.position);
        buf.writeEnumValue(this.placedBlockDirection);
        buf.writeEnumValue(this.hand);
        buf.writeFloat(this.facingX);
        buf.writeFloat(this.facingY);
        buf.writeFloat(this.facingZ);
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    public void processPacket(INetHandlerPlayServer handler)
    {
        handler.processTryUseItemOnBlock(this);
    }

    public BlockPos getPos()
    {
        return this.position;
    }

    public EnumFacing getDirection()
    {
        return this.placedBlockDirection;
    }

    public EnumHand getHand()
    {
        return this.hand;
    }

    public float getFacingX()
    {
        return this.facingX;
    }

    public float getFacingY()
    {
        return this.facingY;
    }

    public float getFacingZ()
    {
        return this.facingZ;
    }
}
